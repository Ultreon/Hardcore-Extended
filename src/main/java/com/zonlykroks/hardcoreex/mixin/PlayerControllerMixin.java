package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class PlayerControllerMixin {
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$attack(Player p_78764_1_, Entity p_78764_2_, CallbackInfo ci) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_ATTACK) && p_78764_2_ instanceof LivingEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$startDestroyBlock(BlockPos loc, Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_BLOCK_BREAKING)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$continueDestroyBlock(BlockPos posBlock, Direction directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_BLOCK_BREAKING)) {
            cir.setReturnValue(false);
        }
    }
}
