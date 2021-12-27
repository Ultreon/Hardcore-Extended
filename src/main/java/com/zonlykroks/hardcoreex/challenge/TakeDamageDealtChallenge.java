package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * No jumping challenge
 * Stops the player able to jump.
 *
 * @author zOnlyKroks, Qboi123
 */
public class TakeDamageDealtChallenge extends Challenge {
    public TakeDamageDealtChallenge() {
        super();
    }

    @SubscribeEvent
    public void onAttack(LivingDamageEvent event) {
        Entity trueSource = event.getSource().getTrueSource();
        if (trueSource instanceof PlayerEntity) {
            float amount = event.getAmount();
            trueSource.attackEntityFrom(event.getSource(), amount);
        }
    }
}
