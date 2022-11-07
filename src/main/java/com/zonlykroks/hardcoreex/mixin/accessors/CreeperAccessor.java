package com.zonlykroks.hardcoreex.mixin.accessors;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Creeper.class)
public interface CreeperAccessor {
    @Accessor("DATA_IS_POWERED")
    static EntityDataAccessor<Boolean> getDataIsPowered() {
        throw new Error("Mixin didn't apply.");
    }
}
