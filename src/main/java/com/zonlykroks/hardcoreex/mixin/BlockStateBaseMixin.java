package com.zonlykroks.hardcoreex.mixin;

import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    public void hardcoreex$overrideDrops(LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        ServerChallengesManager manager = ServerChallengesManager.get();
        if (manager != null && manager.isEnabled(ModChallenges.RANDOM_BLOCK_DROPS.get())) {
            Item[] items = ForgeRegistries.ITEMS.getValues().toArray(new Item[]{});
            cir.setReturnValue(Collections.singletonList(new ItemStack(items[new Random().nextInt(items.length)])));
        }
    }
}
