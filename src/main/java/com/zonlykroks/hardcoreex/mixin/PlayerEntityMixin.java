package com.zonlykroks.hardcoreex.mixin;

import com.mojang.datafixers.util.Either;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public abstract void setForcedPose(@Nullable Pose pose);

    @Shadow
    public abstract void addStat(Stat<?> stat);

    @Shadow
    public abstract void addExhaustion(float exhaustion);

    @Shadow
    public abstract boolean isSwimming();

    @Shadow
    public abstract void addStat(Stat<?> stat, int amount);

    @Shadow
    public abstract void addStat(ResourceLocation p_195067_1_, int p_195067_2_);

    @Shadow
    public abstract void addStat(ResourceLocation stat);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "jump()V", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_jump(CallbackInfo ci) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_JUMPING)) {
            ci.cancel();
        }
    }

    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn, CallbackInfoReturnable<Float> cir) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            cir.setReturnValue(EntityType.SALMON.getDimensions().height * 0.65F);
        }
    }

    @Inject(method = "addMovementStat", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_, CallbackInfo ci) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            if (this.isInWater()) {
                if (this.isSwimming()) {
                    int i = Math.round(Mth.sqrt(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
                    if (i > 0) {
                        this.addStat(Stats.SWIM_ONE_CM, i);
                    }
                    ci.cancel();
                }
            } else {
                int l = Math.round(Mth.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
                this.addExhaustion(0.2F * (float) l * 0.01F);
            }
        }
    }

    @Inject(method = "trySleep(Lnet/minecraft/util/math/BlockPos;)Lcom/mojang/datafixers/util/Either;", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_trySleep(BlockPos at, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_SLEEP)) {
            cir.setReturnValue(Either.left(Player.BedSleepingProblem.OTHER_PROBLEM));
        }
    }

    @Inject(method = "getSize", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_getSize(Pose p_213305_1_, CallbackInfoReturnable<EntityDimensions> cir) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            cir.setReturnValue(EntityType.SALMON.getDimensions());
        }
    }

    @Override
    public void setSprinting(boolean sprinting) {
        ChallengeManager manager = ChallengeManager.getForEntity(this);
        if (manager.isEnabled(ModChallenges.NO_SPRINT)) {
            super.setSprinting(false);
        } else {
            super.setSprinting(sprinting);
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose poseIn) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            return EntityType.SALMON.getDimensions();
        }
        return super.getDimensions(poseIn);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     *
     * @author Qboi123
     */
    @Inject(method = "livingTick", at = @At("HEAD"))
    public void hardcoreex_livingTick(CallbackInfo ci) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            if (!this.isInWater() && this.onGround && this.verticalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
                this.onGround = false;
                this.hasImpulse = true;
                this.playSound(this.hardcoreex_getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
            }

            setSwimming(true);
            setSprinting(true);
            setForcedPose(Pose.SWIMMING);
        }
    }

    @Inject(method = "updateSwimming", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_updateSwimming(CallbackInfo ci) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            ci.cancel();
        }
    }

    protected SoundEvent hardcoreex_getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void baseTick() {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            int i = this.getAirSupply();
            super.baseTick();
            this.hardcoreExUpdateAir(i);
            return;
        }
        super.baseTick();
    }

    protected void hardcoreExUpdateAir(int p_209207_1_) {
        if (ChallengeManager.getForEntity(this).isEnabled(ModChallenges.ONLY_FISH)) {
            if (this.isAlive() && !this.isInWaterOrBubble()) {
                this.setAirSupply(p_209207_1_ - 1);
                if (this.getAirSupply() == -20) {
                    this.setAirSupply(0);
                    this.hurt(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAirSupply(300);
            }
        }
    }
}
