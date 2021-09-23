package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.ChallengeScreen;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class RequestChallengesPacket {
    public RequestChallengesPacket(PacketBuffer buffer) {

    }

    public RequestChallengesPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            HardcoreExtended.LOGGER.info("Challenges requested.");
            if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                HardcoreExtended.LOGGER.info("Challenges requested.");
                // Initialization here.
                for (Challenge challenge : ModChallenges.getRegistry().getValues()) {
                    if (ChallengeManager.server.isEnabled(challenge)) {
                        Network.sendToClient(new SEnableChallengePacket(challenge.getRegistryName()), Objects.requireNonNull(context.get().getSender()));
                    }
                }
                Network.sendToClient(new Accepted(), Objects.requireNonNull(context.get().getSender()));
            }
        });
        context.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buffer) {

    }

    public static class Accepted {
        public Accepted(PacketBuffer buffer) {

        }

        public Accepted() {

        }

        public void handle(Supplier<NetworkEvent.Context> context) {
            DistExecutor.unsafeRunForDist(() -> () -> this.handle0(context), () -> () -> null);
        }

        @OnlyIn(Dist.CLIENT)
        private <T> T handle0(Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                HardcoreExtended.LOGGER.info("Challenges accepted.");
                if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                    HardcoreExtended.LOGGER.info("Challenges accepted.");
                    Minecraft.getInstance().displayGuiScreen(new ChallengeScreen(null));
                }
            });
            context.get().setPacketHandled(true);
            return null;
        }

        public void toBytes(PacketBuffer buffer) {

        }

    }
}
