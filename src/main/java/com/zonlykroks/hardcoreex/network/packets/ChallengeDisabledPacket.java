package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChallengeDisabledPacket extends PacketToClient<ChallengeDisabledPacket> {
    private final ResourceLocation registryName;

    public ChallengeDisabledPacket(PacketBuffer buffer) {
        this.registryName = buffer.readResourceLocation();
    }

    public ChallengeDisabledPacket(Challenge challenge) {
        this(challenge.getRegistryName());
    }

    public ChallengeDisabledPacket(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handle(NetworkManager connection) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.registryName);
        if (challenge == null) {
            HardcoreExtended.LOGGER.error("Challenge not found on client: " + registryName);
            return;
        }
        ClientChallengeManager.onDisabled(this);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(registryName);
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public Challenge getChallenge() {
        return ModChallenges.getRegistry().getValue(this.registryName);
    }
}
