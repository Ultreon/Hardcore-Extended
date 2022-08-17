package com.zonlykroks.hardcoreex.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zonlykroks.hardcoreex.config.ConfigBuilder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class WorldSelectionScreenMixin {
    @Shadow
    private CreateWorldScreen.SelectedGameMode gameMode;

    @Shadow
    private Button btnGameMode;

    @Shadow
    protected abstract void setGameMode(CreateWorldScreen.SelectedGameMode p_228200_1_);

    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            this.setGameMode(CreateWorldScreen.SelectedGameMode.HARDCORE);
            this.btnGameMode.active = false;
        }
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            this.setGameMode(CreateWorldScreen.SelectedGameMode.HARDCORE);
            this.btnGameMode.active = false;
        }
    }
}
