package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.network.PacketToServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;

public class SetupDonePacket extends PacketToServer<SetupDonePacket> {
    public SetupDonePacket() {

    }

    @Override
    protected void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender) {
        PlayerJoinWorldEvent.setupComplete(sender);
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
