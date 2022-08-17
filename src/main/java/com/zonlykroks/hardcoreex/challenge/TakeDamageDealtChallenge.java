package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        Entity trueSource = event.getSource().getEntity();
        if (trueSource instanceof Player) {
            float amount = event.getAmount();
            trueSource.hurt(event.getSource(), amount);
        }
    }
}
