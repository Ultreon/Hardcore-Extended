package com.zonlykroks.hardcoreex.event.handlers;

import com.zonlykroks.hardcoreex.init.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerJoinWorldEvent {
    private static Boolean firstJoin = null;
    private static ServerPlayerEntity creator;

    @SubscribeEvent
    public static void drawLast(PlayerEvent.PlayerLoggedInEvent event) {
        if (firstJoin == null) {
            firstJoin = true;
        }

        if (event.getPlayer() != null && !event.getPlayer().inventory.hasItemStack(new ItemStack(ModItems.CONFIG_ITEM.get()))) {
            event.getPlayer().inventory.addItemStackToInventory(new ItemStack(ModItems.CONFIG_ITEM.get()));
        }
    }

    public static void setupComplete(ServerPlayerEntity creator) {
        firstJoin = false;
        PlayerJoinWorldEvent.creator = creator;
    }

    public static boolean isInitialized() {
        return creator != null;
    }

    public static ServerPlayerEntity getCreator() {
        return creator;
    }

    public static boolean isFirstJoin() {
        return firstJoin;
    }
}
