package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FishChallenge extends Challenge {

    public FishChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    @SubscribeEvent
    public void waterCheck(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.isInWater()) {
            event.player.setAir(300);
        } else if (!player.isInWater()) {
            player.attackEntityFrom(DamageSource.DROWN, 0.5F);
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
