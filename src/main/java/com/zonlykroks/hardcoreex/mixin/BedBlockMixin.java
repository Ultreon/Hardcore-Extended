package com.zonlykroks.hardcoreex.mixin;

import net.minecraft.block.BedBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin {
//    @Inject(method = "onBlockActivated(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/BlockRayTraceResult;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
//    public void attackEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, CallbackInfoReturnable<ActionResultType> cir) {
//        if (ChallengeManager.client.isEnabled(ModChallenges.NO_SLEEP)) {
//            cir.setReturnValue(ActionResultType.FAIL);
//        }
//    }
}
