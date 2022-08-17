package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.gui.screen.ChallengeFailedScreen;
import com.zonlykroks.hardcoreex.event.ChallengeFailedEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.BiDirectionalPacket;
import com.zonlykroks.hardcoreex.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.Nullable;

public class ChallengeFailedPacket extends BiDirectionalPacket<ChallengeFailedPacket> {
    @Nullable
    private final ResourceLocation challenge;

    public ChallengeFailedPacket(FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            this.challenge = buffer.readResourceLocation();
        } else {
            this.challenge = null;
        }
    }

    public ChallengeFailedPacket(@Nullable Challenge challenge) {
        this.challenge = challenge == null ? null : challenge.getRegistryName();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleClient(Connection connection) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        Minecraft mc = Minecraft.getInstance();
        MinecraftForge.EVENT_BUS.post(new ChallengeFailedEvent(challenge, Minecraft.getInstance().player, LogicalSide.CLIENT));
        mc.setScreen(new ChallengeFailedScreen(challenge));
    }

    @Override
    protected void handleServer(Connection manager, ServerPlayer sender) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);

        if (challenge == null) {
            HardcoreExtended.LOGGER.error("Challenge not found on server.");
        } else {
            challenge.failChallenge(sender);
            Networking.sendToClient(new ChallengeFailedPacket(challenge), sender);
        }
    }

    public void toBytes(FriendlyByteBuf buffer) {
        if (challenge != null) {
            buffer.writeResourceLocation(challenge);
        }
    }
}
