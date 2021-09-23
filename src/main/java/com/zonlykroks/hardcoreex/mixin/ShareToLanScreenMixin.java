package com.zonlykroks.hardcoreex.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.ShareToLanScreen;
import net.minecraft.client.gui.widget.button.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShareToLanScreen.class)
public abstract class ShareToLanScreenMixin {
    @Shadow
    private String gameMode;

    @Shadow
    private Button gameModeButton;

    @Shadow
    private boolean allowCheats;

    @Shadow
    private Button allowCheatsButton;

    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        this.gameMode = "survival";
        this.allowCheats = false;
        this.gameModeButton.active = false;
        this.allowCheatsButton.active = false;
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {
        this.gameMode = "survival";
        this.allowCheats = false;
        this.gameModeButton.active = false;
        this.allowCheatsButton.active = false;
    }
}
