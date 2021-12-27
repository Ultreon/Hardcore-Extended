package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.config.Config;
import com.zonlykroks.hardcoreex.event.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.init.ModItems;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.render.LayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/**
 * HardcoreExtended Extended Mod class.
 * In this class the mod will be initialized.
 *
 * @author zOnlyKroks, Qboi123
 * @since 0.1
 */
@Mod("hardcoreex")
@Mod.EventBusSubscriber(modid = HardcoreExtended.MOD_ID)
public class HardcoreExtended {
    public static final String MOD_ID = "hardcoreex";
    public static final Logger LOGGER = LogManager.getLogger();
    private static MinecraftServer server;

    /**
     * HardcoreExtended constructor.
     */
    public HardcoreExtended() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);


        Config.sync();
        Config.init();

        Networking.initialize();

        MinecraftForge.EVENT_BUS.register(PlayerJoinWorldEvent.class);


        ModItems.ITEMS.register(modEventBus);
        ModChallenges.CHALLENGES.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }

    /**
     * Common setup event.
     *
     * @param event ...
     */
    public void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void doClientStuff(FMLClientSetupEvent event) {
        Minecraft.getInstance().getRenderManager().getSkinMap().forEach((s, playerRenderer) -> {
            playerRenderer.addLayer(new LayerModel(playerRenderer));
        });
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStoppedEvent(FMLServerStoppedEvent event) {
        server = null;
    }


    /**
     * Main item group for the Hardcore Extended Mod.
     * <p>
     * Todo: need this to be a standalone class.
     */
    public static final ItemGroup TAB = new ItemGroup("HardcoreExtendedItemGroup ") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CONFIG_ITEM.get());
        }
    };

}
