package com.zonlykroks.hardcoreex.event;

import com.zonlykroks.hardcoreex.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerJoinWorldEvent {
    @SubscribeEvent
    public static void drawLast(PlayerEvent.PlayerLoggedInEvent event) {


        if (event.getPlayer() != null && !event.getPlayer().inventory.hasItemStack(new ItemStack(ModItems.CONFIG_ITEM.get()))) {
            event.getPlayer().inventory.addItemStackToInventory(new ItemStack(ModItems.CONFIG_ITEM.get()));
        }
    }
}
