package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        if (isEnabled(event.getEntityLiving().getCommandSenderWorld().isClientSide)) {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                double x = player.getDeltaMovement().x;
                double z = player.getDeltaMovement().z;

                player.setDeltaMovement(x, -0.1, z); // Set motion downwards if already was in air.}
            }
        }
    }
}
