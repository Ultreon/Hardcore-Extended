package com.zonlykroks.hardcoreex.init;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.items.ConfigItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HardcoreExtended.MOD_ID);

    public static final RegistryObject<Item> CONFIG_ITEM = ITEMS.register("config_item",
            () -> new ConfigItem(new Item.Properties().group(HardcoreExtended.TAB).maxStackSize(1)));
}
