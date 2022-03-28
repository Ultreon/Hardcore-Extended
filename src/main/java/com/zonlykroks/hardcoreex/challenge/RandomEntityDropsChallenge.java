package com.zonlykroks.hardcoreex.challenge;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/**
 * No jumping challenge
 * Stops the player able to jump.
 *
 * @author zOnlyKroks, Qboi123
 */
public class RandomEntityDropsChallenge extends RandomSomethingChallenge {
    public RandomEntityDropsChallenge() {
        super();
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        event.getDrops().clear();

        for (ItemEntity entity : event.getDrops()) {
            Item[] items = ForgeRegistries.ITEMS.getValues().toArray(new Item[]{});
            entity.setItem(new ItemStack(items[new Random().nextInt(items.length)], 1));
        }
        event.setCanceled(false);
    }
}
