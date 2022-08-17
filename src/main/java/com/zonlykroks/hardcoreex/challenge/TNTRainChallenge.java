package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
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
    private Vec3 startingCoords;

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
    protected void playerTick(@NotNull Player player) {
        if (!started && player instanceof ServerPlayer) {
            if (joined) {
                if (!startingCoords.equals(player.position())) {
                    start((ServerPlayer) player);
                }
            }
        }
    }

    @Override
    protected void worldTick(@NotNull ServerLevel world) {

    }

    @Override
    protected void serverTick(@NotNull MinecraftServer server) {
        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        players.forEach(player -> {
            player.displayClientMessage(new TextComponent(Math.round((delay - ticks) / 20f) + " seconds!"), true);
        });

        if (started) {
            ticks++;
            if (ticks >= delay) {
                ticks = 0;
                players.forEach(this::execute);
            }
        }
    }

    private void start(ServerPlayer player) {
        started = true;
        List<ServerPlayer> players = player.getLevel().getServer().getPlayerList().getPlayers();
        players.forEach(p -> {
            p.displayClientMessage(new TextComponent("TNT rain started!"), true);
        });

    }

    @SubscribeEvent
    protected void onPlayerMove(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer && !started) {
            joined = true;
            this.startingCoords = entity.position();
        }
    }

    private void execute(Player player) {
        BlockPos position = player.blockPosition();
        if (player instanceof ServerPlayer) {
            ServerPlayer p = (ServerPlayer) player;
            if (p.gameMode.getGameModeForPlayer() == GameType.SURVIVAL) {
                ServerLevel serverWorld = p.getLevel();
                PrimedTnt entity = (PrimedTnt) EntityType.TNT.spawn(serverWorld, null, player, position.offset(0, 1, 0), MobSpawnType.NATURAL, false, false);
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
