package com.zonlykroks.hardcoreex.client.gui.widgets;

import net.minecraft.util.text.ITextComponent;

public class ChallengeCompatibility {
    private final boolean compatible;
    private final ITextComponent confirmMessage;

    public ChallengeCompatibility(boolean compatible, ITextComponent confirmMessage) {
        this.compatible = compatible;
        this.confirmMessage = confirmMessage;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public ITextComponent getConfirmMessage() {
        return confirmMessage;
    }
}
