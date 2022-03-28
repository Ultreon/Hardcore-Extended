package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends LivingEntity {
    protected ZombieEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "shouldBurnInDay", at = @At("HEAD"), cancellable = true)
    public void hardcoreex_isInDaylight(CallbackInfoReturnable<Boolean> cir) {
        if (world.isRemote()) {
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
