package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.event.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DisableChallengePacket extends PacketToServer<DisableChallengePacket> {
    private final ResourceLocation challenge;

    public DisableChallengePacket(PacketBuffer buffer) {
        this.challenge = buffer.readResourceLocation();
    }

    public DisableChallengePacket(ResourceLocation challenge) {
        this.challenge = challenge;
    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        if (PlayerJoinWorldEvent.isInitialized()) {
            return;
        }

        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        ChallengeManager.server.disable(challenge);
        Networking.sendToAllClients(new ChallengeDisabledPacket(this.challenge));
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(challenge);
    }
}
