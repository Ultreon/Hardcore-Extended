package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.client.gui.screen.ChallengeScreen;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;

public class OpenChallengesMenuPacket extends PacketToClient<OpenChallengesMenuPacket> {
    public OpenChallengesMenuPacket(PacketBuffer buffer) {

    }

    public OpenChallengesMenuPacket() {

    }

    @Override
    protected void handle(NetworkManager connection) {
        HardcoreExtended.LOGGER.info("Opening challenges menu.");
        Minecraft.getInstance().displayGuiScreen(new ChallengeScreen(null));
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
