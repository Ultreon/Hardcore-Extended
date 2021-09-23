package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CEnableChallengePacket {
    private final ResourceLocation challenge;

    public CEnableChallengePacket(PacketBuffer buffer) {
        this.challenge = buffer.readResourceLocation();
    }

    public CEnableChallengePacket(ResourceLocation challenge) {
        this.challenge = challenge;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunForDist(() -> () -> this.handle0(context), () -> () -> null);
    }

    @OnlyIn(Dist.CLIENT)
    private <T> T handle0(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                Challenge challenge = ModChallenges.getRegistry().getValue(this.challenge);
                if (challenge == null) throw new IllegalStateException("Challenge not found on client.");

                ChallengeManager.server.disable(challenge);
                Network.sendToAllClients(new SEnableChallengePacket(this.challenge));
            }
        });
        context.get().setPacketHandled(true);
        return null;
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(challenge);
    }
}
