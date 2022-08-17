package com.zonlykroks.hardcoreex.client.gui.widgets;

import net.minecraft.network.chat.Component;

public class ChallengeCompatibility {
    private final boolean compatible;
    private final Component confirmMessage;

    public ChallengeCompatibility(boolean compatible, Component confirmMessage) {
        this.compatible = compatible;
        this.confirmMessage = confirmMessage;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public Component getConfirmMessage() {
        return confirmMessage;
    }
}
