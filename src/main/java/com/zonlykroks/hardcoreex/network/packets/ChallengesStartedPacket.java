package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
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
        HardcoreExtended.LOGGER.info("Server said that the challenge has been started!");
        Minecraft.getInstance().setScreen(null);
        ClientChallengeManager.get().handleStart();
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
