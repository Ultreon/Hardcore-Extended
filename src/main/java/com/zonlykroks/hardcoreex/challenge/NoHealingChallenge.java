package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * No healing challenge.
 * Stops the player being healed.
 *
 * @author zOnlyKroks
 */
public class NoHealingChallenge extends Challenge {

    public NoHealingChallenge() {
        super();
    }

    @Override
    protected void tick() {

    }

    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            event.setCanceled(true);
        }
    }

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
