package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.PacketToServer;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EnableChallengePacket extends PacketToServer<EnableChallengePacket> {
    private final ResourceLocation challenge;

    public EnableChallengePacket(PacketBuffer buffer) {
        this.challenge = buffer.readResourceLocation();
    }

    public EnableChallengePacket(ResourceLocation challenge) {
        this.challenge = challenge;
    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        if (PlayerJoinWorldEvent.isInitialized()) {
            return;
        }
        if (ServerChallengesManager.get().isStarted()) {
            return;
        }

        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        ServerChallengesManager.get().enable(challenge);
        Networking.sendToAllClients(new ChallengeEnabledPacket(this.challenge));
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(challenge);
    }
}
