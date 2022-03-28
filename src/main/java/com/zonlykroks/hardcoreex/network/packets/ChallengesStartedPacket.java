package com.zonlykroks.hardcoreex.network.packets;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;

public class ChallengesStartedPacket extends PacketToClient<ChallengesStartedPacket> {
    public ChallengesStartedPacket(PacketBuffer buffer) {

    }

    public ChallengesStartedPacket() {

    }

    @Override
    protected void handle(NetworkManager connection) {
        Minecraft.getInstance().displayGuiScreen(null);
        ClientChallengeManager.get().sendStart();
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
