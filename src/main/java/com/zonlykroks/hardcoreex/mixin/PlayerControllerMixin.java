package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerController.class)
public abstract class PlayerControllerMixin {
    @Inject(method = "attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void attackEntity(PlayerEntity p_78764_1_, Entity p_78764_2_, CallbackInfo ci) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_ATTACK) && p_78764_2_ instanceof LivingEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "clickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z", at = @At("HEAD"), cancellable = true)
    public void clickBlock(BlockPos loc, Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_BLOCK_BREAKING)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamageBlock(BlockPos posBlock, Direction directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_BLOCK_BREAKING)) {
            cir.setReturnValue(false);
        }
    }
}
