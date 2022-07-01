package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

public class ApocalypseChallenge extends Challenge {
    public ApocalypseChallenge() {
        super();
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (!(living instanceof PlayerEntity)) {
            BlockPos pos = event.getEntityLiving().getPosition();
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            IWorld world = event.getWorld();
            if (world instanceof ServerWorld) {
                @Nullable LivingEntity entity = null;
                if (!(living instanceof EndermanEntity) && !(living instanceof AbstractPiglinEntity) && !(living instanceof ZombieEntity) && !(living instanceof AbstractSkeletonEntity) && !(living instanceof WaterMobEntity) && !(living instanceof EnderDragonEntity) && !(living instanceof WitherEntity)) {
                    if (living instanceof PigEntity) {
                        living.remove(false);
                        entity = EntityType.ZOGLIN.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                    } else if (living.isInWater()) {
                        entity = EntityType.DROWNED.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                    } else if (living.isInLava()) {
                        entity = EntityType.ZOGLIN.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                    } else if ((living.isOnGround())) {
                        Biome.Category category = world.getBiome(living.getPosition()).getCategory();
                        switch (category) {
                            case DESERT:
                            case MESA:
                                entity = EntityType.HUSK.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                            case NETHER:
                                entity = EntityType.ZOGLIN.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                            case OCEAN:
                            case RIVER:
                                entity = EntityType.DROWNED.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                            case ICY:
                                entity = EntityType.STRAY.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                            case JUNGLE:
                                entity = EntityType.CREEPER.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                            default:
                                entity = EntityType.ZOMBIE.create((ServerWorld) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), SpawnReason.NATURAL, true, false);
                                break;
                        }
                    }
                }

                if (entity != null) {
                    living.remove(false);
                    world.addEntity(entity);
                }
            }
        }
    }
}
