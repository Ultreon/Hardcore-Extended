package com.zonlykroks.hardcoreex.init;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static class Entities {
        public static final TagKey<EntityType<?>> ENERGIZABLE = create("energizable");

        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, HardcoreExtended.res(name));
        }
    }
}
