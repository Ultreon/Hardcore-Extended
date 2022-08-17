package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChallengeEnabledPacket extends PacketToClient<ChallengeEnabledPacket> {
    private final ResourceLocation registryName;

    public ChallengeEnabledPacket(FriendlyByteBuf buffer) {
        this.registryName = buffer.readResourceLocation();
    }

    public ChallengeEnabledPacket(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handle(Connection connection) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.registryName);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");
        ClientChallengeManager.onEnabled(this);

//        ClientChallengeManager.get().enable(challenge);
        ClientChallengeManager.get().readPacket(this);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(registryName);
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public Challenge getChallenge() {
        return ModChallenges.getRegistry().getValue(registryName);
    }
}
