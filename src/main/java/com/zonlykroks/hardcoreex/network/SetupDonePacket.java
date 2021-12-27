package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.event.PlayerJoinWorldEvent;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetupDonePacket {
    public SetupDonePacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                PlayerJoinWorldEvent.setupComplete(context.get().getSender());
            }
        });
        context.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
