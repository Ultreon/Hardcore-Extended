package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerController.class)
public abstract class PlayerControllerMixin {
    @Inject(method = "attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void attackEntity(PlayerEntity p_78764_1_, Entity p_78764_2_, CallbackInfo ci) {
        if (ChallengeManager.client.isEnabled(ModChallenges.NO_ATTACK) && p_78764_2_ instanceof LivingEntity) {
            ci.cancel();
        }
    }

}
