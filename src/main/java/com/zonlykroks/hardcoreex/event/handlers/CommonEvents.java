package com.zonlykroks.hardcoreex.event.handlers;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = HardcoreExtended.MOD_ID)
public class CommonEvents {
    private static final boolean firstJoin = false;

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {

    }

    public static boolean isFirstJoin() {
        return firstJoin && PlayerJoinWorldEvent.isFirstJoin();
    }
}