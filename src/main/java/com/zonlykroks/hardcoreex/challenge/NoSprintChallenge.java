package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class NoSprintChallenge extends Challenge {

    public NoSprintChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    protected void playerTick(PlayerEntity player) {
        if (player.isSprinting()) {
            Minecraft.getInstance().gameSettings.keyBindSprint.setPressed(false);
            player.setSprinting(false);
        }
    }
}
