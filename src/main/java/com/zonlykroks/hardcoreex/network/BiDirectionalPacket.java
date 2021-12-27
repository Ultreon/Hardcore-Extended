package com.zonlykroks.hardcoreex.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BiDirectionalPacket<T extends BiDirectionalPacket<T>> extends BasePacket<T> {
    public BiDirectionalPacket() {
        super();
    }

    @Override
    public final void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        switch (ctx.getDirection()) {
            case PLAY_TO_CLIENT:
                ctx.enqueueWork(() -> handleClient(ctx.getNetworkManager()));
                break;
            case PLAY_TO_SERVER:
                ctx.enqueueWork(() -> handleServer(ctx.getNetworkManager(), ctx.getSender()));
                break;
            default:
                break;
        }
        ctx.setPacketHandled(true);
    }

    protected abstract void handleClient(NetworkManager manager);

    protected abstract void handleServer(NetworkManager manager, ServerPlayerEntity sender);
}
