package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The no-damage challenge!
 * When you take damage, the challenge is failed.
 *
 * @author Qboi123
 */
public class InstaDeathChallenge extends Challenge {
    /**
     * Constructor for this challenge object.
     */
    public InstaDeathChallenge() {
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
        if (event.getEntityLiving() instanceof ServerPlayer) {
            // Fail challenge.
            this.failChallenge((Player) event.getEntityLiving());
        }
    }
}
