package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.common.SingularMessage;
import com.zonlykroks.hardcoreex.init.ModTags;
import com.zonlykroks.hardcoreex.mixin.accessors.CreeperAccessor;
import com.zonlykroks.hardcoreex.mixin.accessors.EntityAccessor;
import com.zonlykroks.hardcoreex.mixin.accessors.MushroomCowAccessor;
import com.zonlykroks.hardcoreex.mixin.accessors.VillagerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class LightningStormChallenge extends Challenge {
    private static final Object NON_EXISTENT_TAG_WARN = new Object();
    private static final Marker MARKER = MarkerFactory.getMarker("LightningStorm");

    @Override
    protected void playerTick(@NotNull Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerLevel world = serverPlayer.getLevel();
            BlockPos pos = serverPlayer.blockPosition();
            Random rng = serverPlayer.getRandom();
            if (rng.nextInt(100) > 90) {
                int x = pos.getX() + rng.nextInt(200) - 100;
                int z = pos.getZ() + rng.nextInt(200) - 100;
                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

                pos = new BlockPos(x, y, z);
                if (world.getChunkSource().isPositionTicking(new ChunkPos(pos).toLong())) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(world, null, null, player, pos, MobSpawnType.NATURAL, true, false);
                    if (lightningBolt != null) {
                        world.addFreshEntity(lightningBolt);
                    }
                }
            }
        }
    }

    @Override
    protected void worldTick(@NotNull ServerLevel world) {
        world.setWeatherParameters(0, 3000, true, true);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean livingUpdate(@NotNull LivingEntity entity) {
        if (entity.getLevel() instanceof ServerLevel level) {
            if (entity instanceof Creeper creeper) {
                ((EntityAccessor) creeper).getEntityData().set(CreeperAccessor.getDataIsPowered(), true);
            } else pig:if (entity instanceof Pig pig) {
                ZombifiedPiglin zombifiedpiglin = EntityType.ZOMBIFIED_PIGLIN.create(level);
                if (zombifiedpiglin == null) break pig;
                zombifiedpiglin.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
                zombifiedpiglin.moveTo(pig.getX(), pig.getY(), pig.getZ(), pig.getYRot(), pig.getXRot());
                zombifiedpiglin.setNoAi(pig.isNoAi());
                zombifiedpiglin.setBaby(pig.isBaby());
                if (pig.hasCustomName()) {
                    zombifiedpiglin.setCustomName(pig.getCustomName());
                    zombifiedpiglin.setCustomNameVisible(pig.isCustomNameVisible());
                }

                zombifiedpiglin.setPersistenceRequired();
                net.minecraftforge.event.ForgeEventFactory.onLivingConvert(pig, zombifiedpiglin);
                level.addFreshEntity(zombifiedpiglin);
                pig.discard();
            } else if (entity instanceof MushroomCow mushroomCow) {
                if (mushroomCow.getMushroomType().equals(MushroomCow.MushroomType.RED)) {
                    ((MushroomCowAccessor) mushroomCow).setMushroomType1(MushroomCow.MushroomType.BROWN);
                    mushroomCow.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
                }
            } else if (entity instanceof Villager villager) {
                VillagerAccessor.getLogger().info("Villager {} was energized by the lightning storm.", villager);
                Witch witch = EntityType.WITCH.create(level);
                witch.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                witch.finalizeSpawn(level, level.getCurrentDifficultyAt(witch.blockPosition()), MobSpawnType.CONVERSION, null, null);
                witch.setNoAi(villager.isNoAi());
                if (villager.hasCustomName()) {
                    witch.setCustomName(villager.getCustomName());
                    witch.setCustomNameVisible(villager.isCustomNameVisible());
                }

                witch.setPersistenceRequired();
                net.minecraftforge.event.ForgeEventFactory.onLivingConvert(villager, witch);
                level.addFreshEntityWithPassengers(witch);
                ((VillagerAccessor) villager).releaseAllPois1();
                villager.discard();
            } else {
                Registry.ENTITY_TYPE.getTag(ModTags.Entities.ENERGIZABLE).ifPresentOrElse(tag -> {
                    if (tag.contains(entity.getType().builtInRegistryHolder())) {
                        try {
                            Method updateEntityMethod = entity.getClass().getMethod("thunderHit", ServerLevel.class, LightningBolt.class);
                            Method originEntityMethod = Entity.class.getMethod("thunderHit", ServerLevel.class, LightningBolt.class);
                            if (updateEntityMethod.equals(originEntityMethod)) {
                                return;
                            }

                            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            lightningBolt.setPos(entity.position());
                            updateEntityMethod.invoke(entity, level, lightningBolt);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }, () -> SingularMessage.exec(NON_EXISTENT_TAG_WARN, () -> LOGGER.warn(MARKER, "Non existent tag: " + ModTags.Entities.ENERGIZABLE.location() + " (for registry " + ModTags.Entities.ENERGIZABLE.registry().location() + ")")));
            }
        }

        return super.livingUpdate(entity);
    }
}
