package com.zonlykroks.hardcoreex.mixin.accessors;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("entityData")
    SynchedEntityData getEntityData(); // Lol, don't make it default / abstract, it crashes the mixin.
}
