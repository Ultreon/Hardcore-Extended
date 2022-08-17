package com.zonlykroks.hardcoreex.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SelfDestructingItem extends Item {
    public SelfDestructingItem() {
        super(new Item.Properties());
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        stack.setCount(0);
    }
}
