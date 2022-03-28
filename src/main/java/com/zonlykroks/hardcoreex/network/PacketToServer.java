package com.zonlykroks.hardcoreex.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class PacketToServer<T extends PacketToServer<T>> extends BasePacket<T> {
    public PacketToServer() {
        super();
    }

    @Override
    public final boolean handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER)
            ctx.enqueueWork(() -> handle(ctx.getNetworkManager(), Objects.requireNonNull(ctx.getSender(), "Server player was not found while on server side")));
        ctx.setPacketHandled(true);
        return true;
    }

    protected abstract void handle(@NotNull NetworkManager connection, @NotNull ServerPlayerEntity sender);
}
