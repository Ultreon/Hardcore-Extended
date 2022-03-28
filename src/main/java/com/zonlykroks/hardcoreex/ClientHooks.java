package com.zonlykroks.hardcoreex;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HardcoreExtended.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientHooks {
    @SubscribeEvent
    public static void onDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event) {
//        ClientChallengeManager.clear();
    }
}
