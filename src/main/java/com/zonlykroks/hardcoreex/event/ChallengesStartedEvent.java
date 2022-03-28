package com.zonlykroks.hardcoreex.event;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import java.util.Set;

public class ChallengesStartedEvent extends Event {
    private final Set<Challenge> enabled;
    private final LogicalSide side;

    public ChallengesStartedEvent(Set<Challenge> enabled, LogicalSide side) {
        this.enabled = enabled;
        this.side = side;
    }

    public Set<Challenge> getEnabled() {
        return enabled;
    }

    public LogicalSide getSide() {
        return side;
    }
}
