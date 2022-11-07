package com.zonlykroks.hardcoreex.client;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Class for client side hooks.
 *
 * @author zOnlyKroks, Qboi123
 * @since 0.1
 */
@Mod.EventBusSubscriber(modid = HardcoreExtended.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    /**
     * Handles player logging out of the server.
     *
     * @param event The event.
     */
    @SubscribeEvent
    public static void onDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event) {
//        ClientChallengeManager.clear();
    }
}
