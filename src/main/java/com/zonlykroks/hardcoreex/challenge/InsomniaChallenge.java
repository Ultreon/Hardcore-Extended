package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InsomniaChallenge extends Challenge {

    public InsomniaChallenge() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (event.getPlayer() != null) {
            event.setCanceled(true);
            event.getPlayer().displayClientMessage(new TranslatableComponent("message.hardcoreex.insomnia.not_tired"), true);
        }
    }
}
