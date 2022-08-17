package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.client.gui.screen.ChallengeScreen;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;

public class OpenChallengesMenuPacket extends PacketToClient<OpenChallengesMenuPacket> {
    public OpenChallengesMenuPacket(FriendlyByteBuf buffer) {

    }

    public OpenChallengesMenuPacket() {

    }

    @Override
    protected void handle(Connection connection) {
        HardcoreExtended.LOGGER.info("Opening challenges menu.");
        Minecraft.getInstance().setScreen(new ChallengeScreen(null));
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
