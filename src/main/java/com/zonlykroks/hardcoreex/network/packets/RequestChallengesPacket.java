package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.event.handlers.CommonEvents;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.PacketToServer;
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
            Networking.sendToClient(new OpenChallengesMenuPacket(), Objects.requireNonNull(sender));
        } else {
            Networking.sendToClient(new ChallengesStartedPacket(), Objects.requireNonNull(sender));
        }
    }

    public void toBytes(PacketBuffer buffer) {

    }

}
