package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * No jumping challenge
 * Stops the player able to jump.
 *
 * @author zOnlyKroks, Qboi123
 */
public class JumpParalysisChallenge extends Challenge {
    public JumpParalysisChallenge() {
        super();
    }

    @SubscribeEvent
    public void playerJump(LivingEvent.LivingJumpEvent event) {
        if (isEnabled(event.getEntityLiving().getEntityWorld().isRemote)) {
            Entity entity = event.getEntity();
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                double x = player.getMotion().x;
                double z = player.getMotion().z;

                player.setMotion(x, -0.1, z); // Set motion downwards if already was in air.}
            }
        }
    }
}
