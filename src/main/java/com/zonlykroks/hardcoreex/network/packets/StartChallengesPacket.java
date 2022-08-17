package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.network.PacketToServer;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class StartChallengesPacket extends PacketToServer<StartChallengesPacket> {
    public StartChallengesPacket(FriendlyByteBuf buffer) {

    }

    public StartChallengesPacket() {

    }

    @Override
    protected void handle(@NotNull Connection connection, @NotNull ServerPlayer sender) {
        ServerChallengesManager.get().start();
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
