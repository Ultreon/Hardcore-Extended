package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Qboi123
 */
@Deprecated
public class ChallengeInfo implements INameable {
    private final Challenge challenge;

    public ChallengeInfo(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public @NotNull ITextComponent getName() {
        return challenge.getLocalizedName();
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
