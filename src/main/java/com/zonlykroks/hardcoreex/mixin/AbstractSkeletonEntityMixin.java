package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends MonsterEntity implements IRangedAttackMob {
    protected AbstractSkeletonEntityMixin(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
        super(p_i48553_1_, p_i48553_2_);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    protected boolean isInDaylight() {
        if (world.isRemote()) {
            return DistExecutor.unsafeRunForDist(() -> () -> {
                if (ClientChallengeManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
                    return false;
                }
                return super.isInDaylight();
            }, () -> () -> super.isInDaylight());
        } else if (ServerChallengesManager.get().isEnabled(ModChallenges.APOCALYPSE)) {
            return false;
        }
        return super.isInDaylight();
    }
}
