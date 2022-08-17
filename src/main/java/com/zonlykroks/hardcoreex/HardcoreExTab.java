package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class HardcoreExTab extends CreativeModeTab {
    public HardcoreExTab() {
        super("HardcoreExtendedItemGroup ");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.CONFIG_ITEM.get());
    }
}
