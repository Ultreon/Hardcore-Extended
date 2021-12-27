package com.zonlykroks.hardcoreex.challenge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FishChallenge extends Challenge {

    public FishChallenge() {
        super();
    }

    private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");

//    @SubscribeEvent
//    public void waterCheck(TickEvent.PlayerTickEvent event) {
//        PlayerEntity player = event.player;
//        if (player.isInWater()) {
//            event.player.setAir(300);
//        } else if (!player.isInWater()) {
//            player.attackEntityFrom(DamageSource.DROWN, 0.5F);
//        }
//    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (isEnabled(true)) {
            MatrixStack matrices = event.getMatrixStack();
            IRenderTypeBuffer buffer = event.getBuffers();
            LivingEntity entity = event.getPlayer();

            matrices.push();
            IVertexBuilder vertices = buffer.getBuffer(RenderType.getEntityTranslucentCull(SALMON_LOCATION));
            SalmonModel<Entity> model = new SalmonModel<>();
            model.setRotationAngles(entity, 0.0f, 0.0f, entity.ticksExisted, entity.rotationYaw, entity.rotationPitch);
            matrices.translate(0, 0, 0);
            matrices.rotate(new Quaternion(0, 0, 180, true));
//            if (entity.isInWater() && entity.isSprinting()) {
                matrices.rotate(new Quaternion(entity.rotationPitch, entity.rotationYawHead - 180 % 360, 0, true));
//            }
            model.render(matrices, vertices, event.getLight(), event.getLight() + 1, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
            event.setCanceled(true);
        }
    }
}
