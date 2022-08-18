package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieEntityMixin extends LivingEntity {
    protected ZombieEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, Level p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "isSunSensitive", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$isSunSensitive(CallbackInfoReturnable<Boolean> cir) {
        if (level.isClientSide()) {
            DistExecutor.unsafeRunForDist(() -> () -> {
                if (ClientChallengeManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
                    cir.setReturnValue(false);
                }
                return null;
            }, () -> () -> null);
        } else {
            if (ServerChallengesManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
                cir.setReturnValue(false);
            }
        }
    }
}
