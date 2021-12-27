package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * No healing challenge.
 * Stops the player being healed.
 *
 * @author zOnlyKroks
 */
public class NoRegenerationChallenge extends Challenge {

    public NoRegenerationChallenge() {
        super();
    }

    @Override
    protected void tick() {

    }

    /**
     * Event handler for living entity healing.
     * Cancels the event if the entity is a player.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
     */
    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            event.setCanceled(true);
        }
    }
}
