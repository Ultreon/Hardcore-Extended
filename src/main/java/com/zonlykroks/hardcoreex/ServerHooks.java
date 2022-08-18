package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Class for server side hooks.
 *
 * @author zOnlyKroks, Qboi123
 * @since 0.1
 */
@Mod.EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = HardcoreExtended.MOD_ID)
public class ServerHooks {
    /**
     * @since 0.1
     */
    private static final boolean firstJoin = false;

    /**
     * Handles the post-startup of the server.
     *
     * @param event The event.
     * @author zOnlyKroks, Qboi123
     * @since 0.1
     */
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
    }

    /**
     * Handles the joining of an entity to the world.
     *
     * @param event The event.
     * @since 0.1
     * @author zOnlyKroks, Qboi123
     */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            ServerChallengesManager.get().sendChallenges(player);
        }
    }

    /**
     * Check if the player is joining for the first time.
     *
     * @return Whether the player is joining for the first time or not.
     */
    public static boolean isFirstJoin() {
        return firstJoin && PlayerJoinWorldEvent.isFirstJoin();
    }
}
