package com.zonlykroks.hardcoreex.event;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

@Mod.EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = HardcoreExtended.MOD_ID)
public class CommonEvents {
    private static boolean firstJoin = false;

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        CompoundNBT worldData = new CompoundNBT();
        ListNBT challengesData = ChallengeManager.client.write(new ListNBT());
        worldData.put("ChallengesEnabled", challengesData);

        try {
            worldData = CompressedStreamTools.readCompressed(server.func_240776_a_(new FolderName("hardcoreex.dat")).toFile());
            ListNBT challengesEnabled = worldData.getList("ChallengesEnabled", 8);
            ChallengeManager.server.read(challengesEnabled);
        } catch (FileNotFoundException ignored) {
            firstJoin = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFirstJoin() {
        return firstJoin && PlayerJoinWorldEvent.isFirstJoin();
    }
}
