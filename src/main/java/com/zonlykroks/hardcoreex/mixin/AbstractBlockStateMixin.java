package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    public void getDrops(LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (ServerChallengesManager.get().isEnabled(ModChallenges.RANDOM_BLOCK_DROPS.get())) {
            Item[] items = ForgeRegistries.ITEMS.getValues().toArray(new Item[]{});
            cir.setReturnValue(Collections.singletonList(new ItemStack(items[new Random().nextInt(items.length)])));
        }
    }
}
