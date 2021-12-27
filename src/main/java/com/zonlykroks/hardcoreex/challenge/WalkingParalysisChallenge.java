package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * No jumping challenge
 * Stops the player able to jump.
 *
 * @author zOnlyKroks, Qboi123
 */
public class WalkingParalysisChallenge extends Challenge {

    public WalkingParalysisChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPlayerMotion(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        double x = -player.getMotion().x;
        double y = player.getMotion().y;
        double z = -player.getMotion().z;
        player.setMotion(0, y, 0);
    }
}
