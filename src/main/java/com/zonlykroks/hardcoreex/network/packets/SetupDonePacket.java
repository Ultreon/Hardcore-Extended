package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.network.PacketToServer;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class SetupDonePacket extends PacketToServer<SetupDonePacket> {
    public SetupDonePacket() {

    }

    @Override
    protected void handle(@NotNull Connection connection, @NotNull ServerPlayer sender) {
        PlayerJoinWorldEvent.setupComplete(sender);
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
