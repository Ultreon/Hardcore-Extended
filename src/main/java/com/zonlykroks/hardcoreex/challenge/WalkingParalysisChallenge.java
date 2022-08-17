package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
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
        Player player = event.player;
        double x = -player.getDeltaMovement().x;
        double y = player.getDeltaMovement().y;
        double z = -player.getDeltaMovement().z;
        player.setDeltaMovement(0, y, 0);
    }
}
