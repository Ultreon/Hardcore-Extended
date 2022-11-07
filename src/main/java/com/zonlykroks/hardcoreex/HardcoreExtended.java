package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.config.Config;
import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.init.ModItems;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static final Logger LOGGER = LoggerFactory.getLogger("HardcoreExtended");
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
     * @author Qboi123
     * @see #MOD_ID
     * @since 1.0
     */
    public static ResourceLocation res(String path) {
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
//        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((s, playerRenderer) ->
//                playerRenderer.addLayer(new LayerModel(playerRenderer)));
    }

    /**
     * Handles the starting of the server.
     *
     * @param event The event for starting the server.
     * @author Qboi123
     * @since 1.0
     */
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        server = event.getServer();
    }


    /**
     * Handles the stopping of the server.
     *
     * @param event The event for stopping the server.
     * @author Qboi123
     * @since 1.0
     */
    @SubscribeEvent
    public static void onServerStoppedEvent(ServerStoppedEvent event) {
        server = null;
    }


    /**
     * Main item group for the Hardcore Extended Mod.
     *
     * @since 1.0
     */
    public static final HardcoreExTab TAB = new HardcoreExTab();

}
