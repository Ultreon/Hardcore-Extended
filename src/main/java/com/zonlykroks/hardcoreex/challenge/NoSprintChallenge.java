package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class NoSprintChallenge extends Challenge {

    public NoSprintChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    protected void playerTick(@NotNull Player player) {
        if (player.isSprinting()) {
            Minecraft.getInstance().options.keySprint.setDown(false);
            player.setSprinting(false);
        }
    }
}
