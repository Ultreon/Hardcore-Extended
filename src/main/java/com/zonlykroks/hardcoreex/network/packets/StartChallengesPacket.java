package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.network.PacketToServer;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;

public class StartChallengesPacket extends PacketToServer<StartChallengesPacket> {
    public StartChallengesPacket(PacketBuffer buffer) {

    }

    public StartChallengesPacket() {

    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        ServerChallengesManager.get().start();
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
