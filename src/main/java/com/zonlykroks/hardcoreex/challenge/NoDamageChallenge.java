package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The no-damage challenge!
 * When you take damage, the challenge is failed.
 *
 * @author Qboi123
 */
public class NoDamageChallenge extends Challenge {
    public NoDamageChallenge() {
        super();
    }

    /**
     * An example tick event.
     * Used to do things like in the no-day challenge.
     * <p>
     * Not needed to use it for this challenge, so it's empty.
     */
    public void tick() {

    }

    /**
     * An example event.
     * This event checks for damage by the player.
     *
     * @param event the event used for the no-damage challenge.
     */
    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        if (event.getAmount() == 0f) {
            return;
        }

        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        // Check for server side player entity.
        if (event.getEntityLiving() instanceof ServerPlayerEntity) {
            // Fail challenge.
            this.failChallenge((PlayerEntity) event.getEntityLiving());
        }
    }

    /**
     * An example event.
     * This event checks for damage by the player.
     *
     * @param event the event used for the no-damage challenge.
     */
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        // Check for server side player entity.
        if (event.getEntityLiving() instanceof ServerPlayerEntity) {
            // Fail challenge.
            this.failChallenge((PlayerEntity) event.getEntityLiving());
        }
    }
}
