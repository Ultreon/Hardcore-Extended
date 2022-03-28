package com.zonlykroks.hardcoreex.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ConfigItem extends Item {
    public ConfigItem(Properties properties) {
        super(properties.maxStackSize(1));
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }
}
