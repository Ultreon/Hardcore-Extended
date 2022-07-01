package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.config.Config;
import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.init.ModItems;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.render.LayerModel;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.client.Minecraft;
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
 * @see Mod
 */
@Mod("hardcoreex")
@Mod.EventBusSubscriber(modid = HardcoreExtended.MOD_ID)
public class HardcoreExtended {
    /**
     * The mod's ID.
     * It's basically just {@code hardcoreex}.
     * @since 0.1
     */
    public static final String MOD_ID = "hardcoreex";

    /**
     * The mod's logger.
     * @since 0.1
     */
    public static final Logger LOGGER = LogManager.getLogger();
    private static MinecraftServer server;

    /**
     * HardcoreExtended constructor.
     *
     * @author zOnlyKroks, Qboi123
     * @since 0.1
     */
    public HardcoreExtended() {
        // Add listeners to the mod event bus
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        // Sync and initialize the mod's configuration
        Config.sync();
        Config.init();

        // Register the mod's network handler
        Networking.initialize();

        MinecraftForge.EVENT_BUS.register(PlayerJoinWorldEvent.class);

        // Register the object initializers to the mod's event bus
        ModItems.ITEMS.register(modEventBus);
        ModChallenges.CHALLENGES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        // Register the mod's server and client handlers
        ClientChallengeManager.initEvents(modEventBus);
        ServerChallengesManager.initEvents(modEventBus);
    }

    /**
     * Get a resource location from the mod's ID and the given path.
     *
     * @param path The path to the resource.
     * @return The resource location.
     * @since 1.0
     * @author Qboi123
     * @see #MOD_ID
     */
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Get Minecraft's server instance.
     * This will be null if there's no running server.
     *
     * @return The server instance.
     * @since 1.0
     * @author Qboi123
     */
    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }

    /**
     * Handles client and server setup.
     *
     * @param event The event for setting up on the client or server.
     * @since 1.0
     * @author Qboi123
     */
    public void setup(final FMLCommonSetupEvent event) {

    }

    /**
     * Handles client setup.
     *
     * @param event The event for setting up on the client.
     * @since 1.0
     * @author Qboi123
     */
    @SubscribeEvent
    public void doClientStuff(FMLClientSetupEvent event) {
        // Register the layer renderer
        Minecraft.getInstance().getRenderManager().getSkinMap().forEach((s, playerRenderer) ->
                playerRenderer.addLayer(new LayerModel(playerRenderer)));
    }

    /**
     * Handles the starting of the server.
     *
     * @param event The event for starting the server.
     * @since 1.0
     * @author Qboi123
     */
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }


    /**
     * Handles the stopping of the server.
     *
     * @param event The event for stopping the server.
     * @since 1.0
     * @author Qboi123
     */
    @SubscribeEvent
    public static void onServerStoppedEvent(FMLServerStoppedEvent event) {
        server = null;
    }


    /**
     * Main item group for the Hardcore Extended Mod.
     *
     * @since 1.0
     */
    public static final HardcoreExTab TAB = new HardcoreExTab();

}
