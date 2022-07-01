package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.event.handlers.PlayerJoinWorldEvent;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

/**
 * Class for server side hooks.
 *
 * @author zOnlyKroks, Qboi123
 * @since 0.1
 */
@Mod.EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = HardcoreExtended.MOD_ID)
public class ServerHooks {
    /**
     * @since 0.1
     */
    private static final boolean firstJoin = false;

    /**
     * Handles the post-startup of the server.
     *
     * @param event The event.
     * @since 0.1
     * @author zOnlyKroks, Qboi123
     */
    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
//        MinecraftServer server = event.getServer();
//        CompoundNBT worldData = new CompoundNBT();
//        ListNBT challengesData = ServerChallengesManager.get().write(new ListNBT());
//        worldData.put("ChallengesEnabled", challengesData);
//
//        try {
//            worldData = CompressedStreamTools.readCompressed(server.func_240776_a_(new FolderName("hardcoreex.dat")).toFile());
//            ListNBT challengesEnabled = worldData.getList("ChallengesEnabled", 8);
//            ServerChallengesManager.get().read(challengesEnabled);
//        } catch (FileNotFoundException ignored) {
//            firstJoin = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Handles the joining of an entity to the world.
     *
     * @param event The event.
     * @since 0.1
     * @author zOnlyKroks, Qboi123
     */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            ServerChallengesManager.get().sendChallenges(player);
        }
    }

    /**
     * Check if the player is joining for the first time.
     *
     * @return Whether the player is joining for the first time or not.
     */
    public static boolean isFirstJoin() {
        return firstJoin && PlayerJoinWorldEvent.isFirstJoin();
    }
}
