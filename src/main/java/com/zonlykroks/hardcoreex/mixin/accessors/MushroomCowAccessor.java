package com.zonlykroks.hardcoreex.mixin.accessors;

import net.minecraft.world.entity.animal.MushroomCow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MushroomCow.class)
public interface MushroomCowAccessor {
    @Invoker("setMushroomType")
    void setMushroomType1(MushroomCow.MushroomType type);
}
