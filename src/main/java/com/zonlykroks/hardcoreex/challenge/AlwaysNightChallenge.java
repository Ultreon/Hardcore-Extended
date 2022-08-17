package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.ServerLevelData;

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
            GameRules.BooleanValue booleanValue = server.getGameRules().getRule(GameRules.RULE_DAYLIGHT);
            booleanValue.set(true, server);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        MinecraftServer server = HardcoreExtended.getServer();

        if (server != null) {
            GameRules.BooleanValue booleanValue = server.getGameRules().getRule(GameRules.RULE_DAYLIGHT);
            booleanValue.set(false, server);

            for (ServerLevel world : server.getAllLevels()) {
                ServerLevelData worldInfo = (ServerLevelData) (world.getLevelData());
                worldInfo.setDayTime(18000);
            }
        }
    }
}
