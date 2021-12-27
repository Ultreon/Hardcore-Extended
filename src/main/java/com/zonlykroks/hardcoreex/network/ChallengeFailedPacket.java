package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.gui.ChallengeFailedScreen;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ChallengeFailedPacket extends BiDirectionalPacket<ChallengeFailedPacket> {
    private final ResourceLocation challenge;

    public ChallengeFailedPacket(PacketBuffer buffer) {
        this.challenge = buffer.readResourceLocation();
    }

    public ChallengeFailedPacket(ResourceLocation challenge) {
        this.challenge = challenge;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleClient(NetworkManager connection) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
        if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

        Minecraft mc = Minecraft.getInstance();
        mc.displayGuiScreen(new ChallengeFailedScreen(challenge));
    }

    @Override
    protected void handleServer(NetworkManager manager, ServerPlayerEntity sender) {
        Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);

        if (challenge == null) HardcoreExtended.LOGGER.error("Challenge not found on server.");
        if (challenge != null) challenge.failChallenge(Objects.requireNonNull(sender));
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(challenge);
    }
}
