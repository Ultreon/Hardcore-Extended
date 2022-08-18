package com.zonlykroks.hardcoreex.init;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.items.SelfDestructingItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HardcoreExtended.MOD_ID);

    public static final RegistryObject<Item> CONFIG_ITEM = ITEMS.register("config_item", SelfDestructingItem::new);
}
