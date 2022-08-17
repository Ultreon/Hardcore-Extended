package com.zonlykroks.hardcoreex.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ConfigItem extends Item {
    public ConfigItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand handIn) {
        return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
    }
}
