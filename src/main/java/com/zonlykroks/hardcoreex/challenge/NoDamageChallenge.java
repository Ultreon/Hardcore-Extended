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
    /**
     * Constructor for this challenge object.
     */
    public NoDamageChallenge() {
        super();
    }

    /**
     * Event handler for living entity damage.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
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
}
