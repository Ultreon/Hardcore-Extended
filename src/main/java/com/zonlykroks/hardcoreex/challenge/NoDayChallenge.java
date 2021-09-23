package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The no-damage challenge!
 * When you take damage, the challenge is failed.
 *
 * @author Qboi123
 */
public class NoDayChallenge extends Challenge {
    public NoDayChallenge() {
        super();
    }

    /**
     * An example tick event.
     * Used to do things like in the no-day challenge.
     * <p>
     * Not needed to use it for this challenge, so it's empty.
     */
    public void tick() {
    }

    @Override
    public void onDisable() {
        MinecraftServer server = HardcoreExtended.getServer();

        HardcoreExtended.LOGGER.error("DISABLING NO DAY CHALLENGE");

        if (server != null) {
            GameRules.BooleanValue booleanValue = server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
            booleanValue.set(true, server);
        }
    }

    @Override
    public void onEnable() {
        MinecraftServer server = HardcoreExtended.getServer();

        HardcoreExtended.LOGGER.error("ENABLING NO DAY CHALLENGE");
        HardcoreExtended.LOGGER.error("SERVER=" + server);

        if (server != null) {
            GameRules.BooleanValue booleanValue = server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
            booleanValue.set(false, server);

            for (ServerWorld world : server.getWorlds()) {
                IServerWorldInfo worldInfo = (IServerWorldInfo) (world.getWorldInfo());
                worldInfo.setDayTime(18000);
            }
        }
    }

    /**
     * An example event.
     * This event checks for damage by the player.
     *
     * @param event the event used for the no-damage challenge.
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        // Check if there's a client player.
        if (Minecraft.getInstance().player != null) {
            // Check if the entity is the client player.
            if (event.getEntityLiving().getEntityId() == Minecraft.getInstance().player.getEntityId()) {
                // Fail challenge.
                this.failChallenge();
            }
        }
    }
}
