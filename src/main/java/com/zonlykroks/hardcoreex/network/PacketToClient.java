package com.zonlykroks.hardcoreex.network;

import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class PacketToClient<T extends PacketToClient<T>> extends BasePacket<T> {
    public PacketToClient() {
        super();
    }

    @Override
    public final void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            ctx.enqueueWork(() -> handle(ctx.getNetworkManager()));
        ctx.setPacketHandled(true);
    }

    protected abstract void handle(NetworkManager connection);
}
