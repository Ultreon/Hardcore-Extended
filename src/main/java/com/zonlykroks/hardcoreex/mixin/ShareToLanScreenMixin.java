package com.zonlykroks.hardcoreex.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zonlykroks.hardcoreex.config.ConfigBuilder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShareToLanScreen.class)
public abstract class ShareToLanScreenMixin {
    @Shadow
    private GameType gameMode;

    @Inject(method = "lambda$init$0", at = @At("HEAD"))
    public void hardcoreex$lambda$init$0(CycleButton<?> p_169429_, GameType p_169430_, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            p_169429_.active = false;
        }
    }

    @Inject(method = "lambda$init$1", at = @At("HEAD"))
    public void hardcoreex$lambda$init$1(CycleButton<?> p_169432_, Boolean p_169433_, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            p_169432_.active = false;
        }
    }

    @Inject(method = "lambda$init$2", at = @At("HEAD"))
    public void hardcoreex$lambda$init$2(Button p_96660_, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            p_96660_.active = false;
        }
    }

    @Inject(method = "lambda$init$3", at = @At("HEAD"))
    public void hardcoreex$lambda$init$3(Button p_96657_, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            p_96657_.active = false;
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void hardcoreex$render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            gameMode = GameType.SURVIVAL;
        }
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void hardcoreex$init(CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            this.gameMode = GameType.SURVIVAL;
        }
    }
}
