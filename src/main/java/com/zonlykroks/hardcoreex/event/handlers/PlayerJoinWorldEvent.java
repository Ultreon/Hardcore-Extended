package com.zonlykroks.hardcoreex.event.handlers;

import com.zonlykroks.hardcoreex.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerJoinWorldEvent {
    private static Boolean firstJoin = null;
    private static ServerPlayer creator;

    @SubscribeEvent
    public static void drawLast(PlayerEvent.PlayerLoggedInEvent event) {
        if (firstJoin == null) {
            firstJoin = true;
        }

        if (event.getPlayer() != null && !event.getPlayer().getInventory().contains(new ItemStack(ModItems.CONFIG_ITEM.get()))) {
            event.getPlayer().getInventory().add(new ItemStack(ModItems.CONFIG_ITEM.get()));
        }
    }

    public static void setupComplete(ServerPlayer creator) {
        firstJoin = false;
        PlayerJoinWorldEvent.creator = creator;
    }

    public static boolean isInitialized() {
        return creator != null;
    }

    public static ServerPlayer getCreator() {
        return creator;
    }

    public static boolean isFirstJoin() {
        return firstJoin;
    }
}
