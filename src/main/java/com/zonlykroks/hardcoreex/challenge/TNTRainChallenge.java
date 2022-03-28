package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * No jumping challenge
 * Stops the player able to jump.
 *
 * @author zOnlyKroks, Qboi123
 */
public class TNTRainChallenge extends Challenge {
    private int ticks;
    private boolean started = false;
    private boolean joined = false;
    private final int delaySecs = 15;
    private final int delay = 20 * delaySecs;
    private Vector3d startingCoords;

    public TNTRainChallenge() {
        super();
    }

    @Override
    public void onEnable() {
        ticks = 0;
        super.onEnable();
    }

    @Override
    protected void tick() {

    }

    @Override
    protected void playerTick(@NotNull PlayerEntity player) {
        if (!started && player instanceof ServerPlayerEntity) {
            if (joined) {
                if (!startingCoords.equals(player.getPositionVec())) {
                    start((ServerPlayerEntity) player);
                }
            }
        }
    }

    @Override
    protected void worldTick(@NotNull ServerWorld world) {

    }

    @Override
    protected void serverTick(@NotNull MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerList().getPlayers();
        players.forEach(player -> {
            player.sendStatusMessage(new StringTextComponent(Math.round((delay - ticks) / 20f) + " seconds!"), true);
        });

        if (started) {
            ticks++;
            if (ticks >= delay) {
                ticks = 0;
                players.forEach(this::execute);
            }
        }
    }

    private void start(ServerPlayerEntity player) {
        started = true;
        List<ServerPlayerEntity> players = player.getServerWorld().getServer().getPlayerList().getPlayers();
        players.forEach(p -> {
            p.sendStatusMessage(new StringTextComponent("TNT rain started!"), true);
        });

    }

    @SubscribeEvent
    protected void onPlayerMove(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity && !started) {
            joined = true;
            this.startingCoords = entity.getPositionVec();
        }
    }

    private void execute(PlayerEntity player) {
        BlockPos position = player.getPosition();
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity p = (ServerPlayerEntity) player;
            if (p.interactionManager.getGameType() == GameType.SURVIVAL) {
                ServerWorld serverWorld = p.getServerWorld();
                TNTEntity entity = (TNTEntity) EntityType.TNT.spawn(serverWorld, null, player, position.add(0, 1, 0), SpawnReason.NATURAL, false, false);
                if (entity != null) {
                    entity.setFuse(20 * 5);
                } else {
                    HardcoreExtended.LOGGER.warn("TNT entity was spawned as null in a tnt rain challenge.");
                }
            }
        }
    }

    public int getDelay() {
        return delay;
    }

    public int getDelaySecs() {
        return delaySecs;
    }
}
