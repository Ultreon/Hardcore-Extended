package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NonViolent extends Challenge {

    public NonViolent() {
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
        if (event.getSource().getEntity() instanceof Player) {
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
        if (event.getSource().getEntity() instanceof Player) {
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
        if (event.getSource().getEntity() instanceof Player) {
            event.setCanceled(true);
        }
    }
}
