package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;

public class ChallengesStartedPacket extends PacketToClient<ChallengesStartedPacket> {
    public ChallengesStartedPacket(FriendlyByteBuf buffer) {

    }

    public ChallengesStartedPacket() {

    }

    @Override
    protected void handle(Connection connection) {
        Minecraft.getInstance().setScreen(null);
        ClientChallengeManager.get().sendStart();
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
