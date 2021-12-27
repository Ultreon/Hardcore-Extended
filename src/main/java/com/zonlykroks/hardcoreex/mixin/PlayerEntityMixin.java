package com.zonlykroks.hardcoreex.mixin;

import com.mojang.datafixers.util.Either;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "jump()V", at = @At("HEAD"), cancellable = true)
    public void attackEntity(CallbackInfo ci) {
        if (ChallengeManager.client.isEnabled(ModChallenges.NO_JUMPING)) {
            ci.cancel();
        }
    }

    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    public void getStandingEyeHeight(Pose poseIn, EntitySize sizeIn, CallbackInfoReturnable<Float> cir) {
        DistExecutor.unsafeRunForDist(() -> () -> {
            if (ChallengeManager.client.isEnabled(ModChallenges.ONLY_FISH)) {
                cir.setReturnValue(sizeIn.height * 0.65F);
            }
            return null;
        }, () -> () -> {
            if (ChallengeManager.server.isEnabled(ModChallenges.ONLY_FISH)) {
                cir.setReturnValue(sizeIn.height * 0.65F);
            }
            return null;
        });
    }

    @Inject(method = "trySleep(Lnet/minecraft/util/math/BlockPos;)Lcom/mojang/datafixers/util/Either;", at = @At("HEAD"), cancellable = true)
    public void trySleep(BlockPos at, CallbackInfoReturnable<Either<PlayerEntity.SleepResult, Unit>> cir) {
        if (ChallengeManager.client.isEnabled(ModChallenges.NO_SLEEP)) {
            cir.setReturnValue(Either.left(PlayerEntity.SleepResult.OTHER_PROBLEM));
        }
    }

    @Override
    public void setSprinting(boolean sprinting) {
        DistExecutor.unsafeRunForDist(() -> () -> {
            ChallengeManager manager = ChallengeManager.client;
            if (manager.isEnabled(ModChallenges.NO_SPRINT)) super.setSprinting(false);
            else super.setSprinting(sprinting);
            return null;
        }, () -> () -> {
            ChallengeManager manager = ChallengeManager.server;
            if (manager.isEnabled(ModChallenges.NO_SPRINT)) super.setSprinting(false);
            else super.setSprinting(sprinting);
            return null;
        });
    }

    @Override
    public @NotNull EntitySize getSize(@NotNull Pose poseIn) {
        return DistExecutor.unsafeRunForDist(() -> () -> {
            if (ChallengeManager.client.isEnabled(ModChallenges.ONLY_FISH)) {
                return EntityType.SALMON.getSize();
            }
            return super.getSize(poseIn);
        }, () -> () -> {
            if (ChallengeManager.server.isEnabled(ModChallenges.ONLY_FISH)) {
                return EntityType.SALMON.getSize();
            }
            return super.getSize(poseIn);
        });
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     * @author Qboi123
     */
    @Overwrite
    public void livingTick() {
        if (ChallengeManager.server.isEnabled(ModChallenges.ONLY_FISH)) {
            if (!this.isInWater() && this.onGround && this.collidedVertically) {
                this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F));
                this.onGround = false;
                this.isAirBorne = true;
                this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
            }

            setSprinting(true);
        }

        super.livingTick();
    }

    /**
     * @author
     */
    @Overwrite
    public boolean isSwimming() {
        return true;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void baseTick() {
        if (ChallengeManager.server.isEnabled(ModChallenges.ONLY_FISH)) {
            int i = this.getAir();
            super.baseTick();
            this.updateAir(i);
            return;
        }
        super.baseTick();
    }

    protected void updateAir(int p_209207_1_) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(p_209207_1_ - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(300);
        }

    }
}
