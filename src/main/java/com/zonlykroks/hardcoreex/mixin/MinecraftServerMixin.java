package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.event.ServerSaveEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    public abstract Path func_240776_a_(FolderName p_240776_1_);

    @Inject(method = "save(ZZZ)Z", at = @At("HEAD"))
    public void hardcoreExSave(boolean suppressLog, boolean flush, boolean forced, CallbackInfoReturnable<Boolean> cir) {
        MinecraftForge.EVENT_BUS.post(new ServerSaveEvent((MinecraftServer) (Object) this, flush, forced));
    }
}
