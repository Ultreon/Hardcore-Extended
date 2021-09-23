package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Path;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    public abstract Path func_240776_a_(FolderName p_240776_1_);

    @Inject(method = "save(ZZZ)Z", at = @At("HEAD"))
    public void save(boolean suppressLog, boolean flush, boolean forced, CallbackInfoReturnable<Boolean> cir) {
        CompoundNBT hardcoreEx_data = new CompoundNBT();
        ListNBT hardcoreEx_challenges = ChallengeManager.server.write(new ListNBT());
        hardcoreEx_data.put("ChallengesEnabled", hardcoreEx_challenges);

        try {
            CompressedStreamTools.writeCompressed(hardcoreEx_data, func_240776_a_(new FolderName("hardcoreex.dat")).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
