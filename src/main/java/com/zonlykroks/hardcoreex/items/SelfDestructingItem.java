package com.zonlykroks.hardcoreex.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SelfDestructingItem extends Item {
    public SelfDestructingItem() {
        super(new Item.Properties());
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        stack.setCount(0);
    }
}
