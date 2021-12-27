package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChallengeEnabledPacket extends PacketToClient<ChallengeEnabledPacket> {
    private final ResourceLocation challenge;

    public ChallengeEnabledPacket(PacketBuffer buffer) {
        this.challenge = buffer.readResourceLocation();
    }

    public ChallengeEnabledPacket(ResourceLocation challenge) {
        this.challenge = challenge;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handle(NetworkManager connection) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

//        ChallengeManager.client.enable(challenge);
        ChallengeManager.client.readPacket(this);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(challenge);
    }

    public ResourceLocation getChallenge() {
        return challenge;
    }
}
