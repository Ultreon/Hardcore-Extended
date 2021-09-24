package com.zonlykroks.hardcoreex.mixin;

import com.mojang.datafixers.util.Either;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
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

    @Inject(method = "trySleep(Lnet/minecraft/util/math/BlockPos;)Lcom/mojang/datafixers/util/Either;", at = @At("HEAD"), cancellable = true)
    public void trySleep(BlockPos at, CallbackInfoReturnable<Either<PlayerEntity.SleepResult, Unit>> cir) {
        if (ChallengeManager.client.isEnabled(ModChallenges.NO_SLEEP)) {
            cir.setReturnValue(Either.left(PlayerEntity.SleepResult.OTHER_PROBLEM));
        }
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(ChallengeManager.client.isEnabled(ModChallenges.NO_SPRINT) ? false : sprinting);
    }
}
