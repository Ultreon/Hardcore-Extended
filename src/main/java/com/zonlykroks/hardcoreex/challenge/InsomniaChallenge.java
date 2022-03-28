package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.util.text.TranslationTextComponent;
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
            event.getPlayer().sendStatusMessage(new TranslationTextComponent("message.hardcoreex.insomnia.not_tired"), true);
        }
    }
}
