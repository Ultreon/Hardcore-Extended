package com.zonlykroks.hardcoreex;

import com.zonlykroks.hardcoreex.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class HardcoreExTab extends ItemGroup {
    public HardcoreExTab() {
        super("HardcoreExtendedItemGroup ");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.CONFIG_ITEM.get());
    }
}
