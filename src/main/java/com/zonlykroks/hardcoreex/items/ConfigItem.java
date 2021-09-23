package com.zonlykroks.hardcoreex.items;

import com.zonlykroks.hardcoreex.client.gui.ChallengeScreen;
import net.minecraft.client.Minecraft;
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
        // Check if on Client.
        if (worldIn.isRemote) {
            // Get Minecraft instance.
            Minecraft mc = Minecraft.getInstance();

            mc.displayGuiScreen(new ChallengeScreen(mc.currentScreen));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
