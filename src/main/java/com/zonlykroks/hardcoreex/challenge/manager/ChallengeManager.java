package com.zonlykroks.hardcoreex.challenge.manager;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.*;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ChallengeManager {
    private static final Set<Challenge> challengesEnabled = new HashSet<>();

    public static final ChallengeManager server = new ChallengeManager();
    public static final ChallengeManager client = new ChallengeManager();

    private ChallengeManager() {

    }

    public void enable(Challenge challenge) {
        set(challenge, true);
    }

    public void disable(Challenge challenge) {
        set(challenge, false);
    }

    public void set(Challenge challenge, boolean value) {
        if (value && !challengesEnabled.contains(challenge)) {
            challengesEnabled.add(challenge);
            challenge.onEnable();
            if (this == client) {
                Network.sendToServer(new CEnableChallengePacket(challenge.getRegistryName()));
            }
        } else if (!value && challengesEnabled.contains(challenge)) {
            challengesEnabled.remove(challenge);
            challenge.onDisable();
            if (this == client) {
                Network.sendToServer(new CDisableChallengePacket(challenge.getRegistryName()));
            }
        }
    }

    public void readPacket(SEnableChallengePacket packet) {
        Challenge challenge = ModChallenges.getRegistry().getValue(packet.getChallenge());
        if (!challengesEnabled.contains(challenge) && challenge != null) {
            challengesEnabled.add(challenge);
            challenge.onEnable();
        }
    }

    public void readPacket(SDisableChallengePacket packet) {
        Challenge challenge = ModChallenges.getRegistry().getValue(packet.getChallenge());
        if (challengesEnabled.contains(challenge) && challenge != null) {
            challengesEnabled.add(challenge);
            challenge.onDisable();
        }
    }

    public boolean isEnabled(Challenge challenge) {
        return challengesEnabled.contains(challenge);
    }

    public ListNBT write(ListNBT nbt) {
        for (Challenge challenge : challengesEnabled) {
            if (challenge.getRegistryName() != null) {
                nbt.add(StringNBT.valueOf(challenge.getRegistryName().toString()));
            }
        }
        return nbt;
    }

    public void read(ListNBT nbt) {
        for (INBT element : nbt) {
            if (element instanceof StringNBT) {
                String string = element.getString();
                ResourceLocation rl = new ResourceLocation(string);
                Challenge value = ModChallenges.getRegistry().getValue(rl);
                if (value != null) {
                    challengesEnabled.add(value);
                } else {
                    HardcoreExtended.LOGGER.warn("Challenge was not found: " + string);
                }
            }
        }
    }

    public boolean isEnabled(Supplier<? extends Challenge> noAttack) {
        return isEnabled(noAttack.get());
    }

    public boolean isDisabled(Challenge challenge) {
        return !isEnabled(challenge);
    }

    public boolean isDisabled(Supplier<? extends Challenge> noAttack) {
        return !isEnabled(noAttack);
    }
}
