package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The no-damage challenge!
 * When you take damage, the challenge is failed.
 *
 * @author Qboi123
 */
public class AlwaysNightChallenge extends Challenge {
    public AlwaysNightChallenge() {
        super();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        MinecraftServer server = HardcoreExtended.getServer();

        if (server != null) {
            GameRules.BooleanValue booleanValue = server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
            booleanValue.set(true, server);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        MinecraftServer server = HardcoreExtended.getServer();

        if (server != null) {
            GameRules.BooleanValue booleanValue = server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
            booleanValue.set(false, server);

            for (ServerWorld world : server.getWorlds()) {
                IServerWorldInfo worldInfo = (IServerWorldInfo) (world.getWorldInfo());
                worldInfo.setDayTime(18000);
            }
        }
    }
}
