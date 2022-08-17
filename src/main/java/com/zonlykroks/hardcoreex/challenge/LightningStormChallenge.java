package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LightningStormChallenge extends Challenge {
    @Override
    protected void playerTick(@NotNull Player player) {
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ServerLevel world = serverPlayer.getLevel();
            BlockPos pos = serverPlayer.blockPosition();
            Random rng = serverPlayer.getRandom();
            if (rng.nextInt(50) > 15) {
                int x = pos.getX() + rng.nextInt(200) - 100;
                int z = pos.getZ() + rng.nextInt(200) - 100;
                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

                pos = new BlockPos(x, y, z);
                if (world.getChunkSource().isEntityTickingChunk(new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4))) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(world, null, null, player, pos, MobSpawnType.NATURAL, true, false);
                    if (lightningBolt != null) {
                        world.addFreshEntity(lightningBolt);
                    }
                }
            }
        }
    }
}
