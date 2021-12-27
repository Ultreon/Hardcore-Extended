package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.ChallengeScreen;
import com.zonlykroks.hardcoreex.event.CommonEvents;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RequestChallengesPacket extends PacketToServer<RequestChallengesPacket> {
    public RequestChallengesPacket(PacketBuffer buffer) {

    }

    public RequestChallengesPacket() {

    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        HardcoreExtended.LOGGER.info("Challenges requested.");
        if (CommonEvents.isFirstJoin()) {
            // Initialization here.
            for (Challenge challenge : ModChallenges.getRegistry().getValues()) {
                if (ChallengeManager.server.isEnabled(challenge)) {
                    Networking.sendToClient(new ChallengeEnabledPacket(challenge.getRegistryName()), Objects.requireNonNull(sender));
                }
            }
            Networking.sendToClient(new Accepted(), Objects.requireNonNull(sender));
        } else {
            Networking.sendToClient(new Rejected(), Objects.requireNonNull(sender));
        }
    }

    public void toBytes(PacketBuffer buffer) {

    }

    public static class Accepted extends PacketToClient<Accepted> {
        public Accepted(PacketBuffer buffer) {

        }

        public Accepted() {

        }

        @Override
        protected void handle(NetworkManager connection) {
            HardcoreExtended.LOGGER.info("Challenges accepted.");
            Minecraft.getInstance().displayGuiScreen(new ChallengeScreen(null));
        }

        public void toBytes(PacketBuffer buffer) {

        }
    }

    public static class Rejected extends PacketToClient<Rejected> {
        public Rejected(PacketBuffer buffer) {

        }

        public Rejected() {

        }

        @Override
        protected void handle(NetworkManager connection) {
            HardcoreExtended.LOGGER.info("Challenges rejected.");
            Minecraft.getInstance().displayGuiScreen(null);
        }

        public void toBytes(PacketBuffer buffer) {

        }
    }
}
