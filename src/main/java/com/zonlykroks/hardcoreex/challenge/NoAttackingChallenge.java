package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoAttackingChallenge extends Challenge {

    public NoAttackingChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    /**
     * Event handler for living entity damage.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
     */
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            event.setCanceled(true);
        }
    }

    /**
     * Event handler for living entity hurting.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
     */
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            event.setCanceled(true);
        }
    }

    /**
     * Event handler for living entity death.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
     */
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            event.setCanceled(true);
        }
    }
}
