package com.zonlykroks.hardcoreex.mixin.accessors;

import net.minecraft.world.entity.npc.Villager;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Villager.class)
public interface VillagerAccessor {
    @Accessor("LOGGER")
    static Logger getLogger() {
        throw new Error("Mixin didn't apply");
    }

    @Invoker("releaseAllPois")
    void releaseAllPois1();
}
