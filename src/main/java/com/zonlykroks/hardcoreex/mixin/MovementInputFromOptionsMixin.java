package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class MovementInputFromOptionsMixin extends Input {
    @Final
    private Options gameSettings;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$tick(boolean isForced, CallbackInfo ci) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.NO_WALKING)) {
            this.jumping = this.gameSettings.keyJump.isDown();
            this.shiftKeyDown = this.gameSettings.keyShift.isDown();
            ci.cancel();
        }
    }
}
