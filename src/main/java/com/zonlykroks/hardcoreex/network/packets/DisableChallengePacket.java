package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.PacketToServer;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class DisableChallengePacket extends PacketToServer<DisableChallengePacket> {
    private final ResourceLocation registryName;

    public DisableChallengePacket(FriendlyByteBuf buffer) {
        this.registryName = buffer.readResourceLocation();
    }

    public DisableChallengePacket(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @Override
    protected void handle(@NotNull Connection connection, @NotNull ServerPlayer sender) {
        if (PlayerJoinWorldEvent.isInitialized()) {
            return;
        }

        Challenge challenge = ModChallenges.getRegistry().getValue(this.registryName);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        ServerChallengesManager.get().disable(challenge);
        Networking.sendToAllClients(new ChallengeDisabledPacket(this.registryName));
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(registryName);
    }
}
