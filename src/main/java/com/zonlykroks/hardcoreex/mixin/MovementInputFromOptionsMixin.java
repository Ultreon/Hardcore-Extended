package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public abstract class MovementInputFromOptionsMixin extends MovementInput {
    @Shadow @Final private GameSettings gameSettings;

    @Inject(method = "tickMovement(Z)V", at = @At("HEAD"), cancellable = true)
    public void tickMovement(boolean isForced, CallbackInfo ci) {
        if (ChallengeManager.client.isEnabled(ModChallenges.NO_WALKING)) {
            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneaking = this.gameSettings.keyBindSneak.isKeyDown();
            if (isForced) {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
                this.moveForward = (float)((double)this.moveForward * 0.3D);
            }
            ci.cancel();
        }
    }
}
