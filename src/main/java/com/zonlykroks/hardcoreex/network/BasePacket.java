package com.zonlykroks.hardcoreex.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

abstract class BasePacket<T extends BasePacket<T>> {
    public BasePacket() {
    }

    protected abstract boolean handle(Supplier<NetworkEvent.Context> context);

    public final boolean handlePacket(Supplier<NetworkEvent.Context> context) {
        try {
            handle(context);
        } catch (Throwable t) {
            System.err.println("Couldn't handle packet.");
            t.printStackTrace();
        }
        return true;
    }

    public abstract void toBytes(PacketBuffer buffer);
}
