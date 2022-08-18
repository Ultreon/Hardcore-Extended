package com.zonlykroks.hardcoreex.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zonlykroks.hardcoreex.config.ConfigBuilder;
import net.minecraft.client.gui.components.CycleButton;
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
    protected abstract void setGameMode(CreateWorldScreen.SelectedGameMode p_228200_1_);

    @Shadow
    private CycleButton<CreateWorldScreen.SelectedGameMode> modeButton;

    @Inject(method = "render", at = @At("HEAD"))
    public void hardcoreex$render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            this.setGameMode(CreateWorldScreen.SelectedGameMode.HARDCORE);
            this.modeButton.active = false;
        }
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void hardcoreex$init(CallbackInfo ci) {
        if (ConfigBuilder.forceHardcore.get()) {
            this.setGameMode(CreateWorldScreen.SelectedGameMode.HARDCORE);
            this.modeButton.active = false;
        }
    }
}
