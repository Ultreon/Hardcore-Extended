package com.zonlykroks.hardcoreex.challenge;

import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DoubleDamageChallenge extends Challenge {
    @SubscribeEvent
    public void onLivingDrops(LivingDamageEvent event) {
        event.setAmount(event.getAmount() * 2);
    }
}
