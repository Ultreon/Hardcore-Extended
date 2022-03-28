package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LightningStormChallenge extends Challenge {
    @Override
    protected void playerTick(@NotNull PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            ServerWorld world = serverPlayer.getServerWorld();
            BlockPos pos = serverPlayer.getPosition();
            Random rng = serverPlayer.getRNG();
            if (rng.nextInt(50) > 15) {
                int x = pos.getX() + rng.nextInt(200) - 100;
                int z = pos.getZ() + rng.nextInt(200) - 100;
                int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z);

                pos = new BlockPos(x, y, z);
                if (world.getChunkProvider().isChunkLoaded(new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4))) {
                    LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(world, null, null, player, pos, SpawnReason.NATURAL, true, false);
                    if (lightningBolt != null) {
                        world.addEntity(lightningBolt);
                    }
                }
            }
        }
    }
}
