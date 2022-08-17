package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
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
        if (!(living instanceof Player)) {
            BlockPos pos = event.getEntityLiving().blockPosition();
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            LevelAccessor world = event.getWorld();
            if (world instanceof ServerLevel) {
                @Nullable LivingEntity entity = null;
                if (!(living instanceof EnderMan) && !(living instanceof AbstractPiglin) && !(living instanceof Zombie) && !(living instanceof AbstractSkeleton) && !(living instanceof WaterAnimal) && !(living instanceof EnderDragon) && !(living instanceof WitherBoss)) {
                    if (living instanceof Pig) {
                        living.remove(false);
                        entity = EntityType.ZOGLIN.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                    } else if (living.isInWater()) {
                        entity = EntityType.DROWNED.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                    } else if (living.isInLava()) {
                        entity = EntityType.ZOGLIN.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                    } else if ((living.isOnGround())) {
                        Biome.BiomeCategory category = world.getBiome(living.blockPosition()).getBiomeCategory();
                        switch (category) {
                            case DESERT:
                            case MESA:
                                entity = EntityType.HUSK.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                            case NETHER:
                                entity = EntityType.ZOGLIN.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                            case OCEAN:
                            case RIVER:
                                entity = EntityType.DROWNED.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                            case ICY:
                                entity = EntityType.STRAY.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                            case JUNGLE:
                                entity = EntityType.CREEPER.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                            default:
                                entity = EntityType.ZOMBIE.create((ServerLevel) world, null, null, null, new BlockPos((int) x, (int) y, (int) z), MobSpawnType.NATURAL, true, false);
                                break;
                        }
                    }
                }

                if (entity != null) {
                    living.remove(false);
                    world.addFreshEntity(entity);
                }
            }
        }
    }
}
