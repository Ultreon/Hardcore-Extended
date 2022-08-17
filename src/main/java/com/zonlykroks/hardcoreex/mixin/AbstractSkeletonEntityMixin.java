package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonEntityMixin extends Monster implements RangedAttackMob {
    protected AbstractSkeletonEntityMixin(EntityType<? extends Monster> p_i48553_1_, Level p_i48553_2_) {
        super(p_i48553_1_, p_i48553_2_);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    protected boolean isSunBurnTick() {
        if (level.isClientSide()) {
            return DistExecutor.unsafeRunForDist(() -> () -> {
                if (ClientChallengeManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
                    return false;
                }
                return super.isSunBurnTick();
            }, () -> () -> super.isSunBurnTick());
        } else if (ServerChallengesManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
            return false;
        }
        return super.isSunBurnTick();
    }
}
