package com.zonlykroks.hardcoreex.event;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.LogicalSide;

public class ChallengeFailedEvent extends PlayerEvent {
    private final Challenge challenge;
    private final LogicalSide side;

    public ChallengeFailedEvent(Challenge challenge, PlayerEntity player, LogicalSide side) {
        super(player);
        this.challenge = challenge;
        this.side = side;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public LogicalSide getSide() {
        return side;
    }
}
