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

public class DisableChallengePacket extends PacketToServer<DisableChallengePacket> {
    private final ResourceLocation registryName;

    public DisableChallengePacket(PacketBuffer buffer) {
        this.registryName = buffer.readResourceLocation();
    }

    public DisableChallengePacket(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        if (PlayerJoinWorldEvent.isInitialized()) {
            return;
        }

        Challenge challenge = ModChallenges.getRegistry().getValue(this.registryName);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        ServerChallengesManager.get().disable(challenge);
        Networking.sendToAllClients(new ChallengeDisabledPacket(this.registryName));
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(registryName);
    }
}
