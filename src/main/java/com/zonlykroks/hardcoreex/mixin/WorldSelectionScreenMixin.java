package com.zonlykroks.hardcoreex.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.widget.button.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class WorldSelectionScreenMixin {
    @Shadow
    private CreateWorldScreen.GameMode field_228197_f_;

    @Shadow
    private Button btnGameMode;

    @Shadow
    protected abstract void func_228200_a_(CreateWorldScreen.GameMode p_228200_1_);

    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        this.func_228200_a_(CreateWorldScreen.GameMode.HARDCORE);
        this.btnGameMode.active = false;
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {
        this.func_228200_a_(CreateWorldScreen.GameMode.HARDCORE);
        this.btnGameMode.active = false;
    }
}
