package com.zonlykroks.hardcoreex.challenge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class FishChallenge extends Challenge {

    public FishChallenge() {
        super();
    }

    private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    protected void playerTick(@NotNull Player player) {
        super.playerTick(player);
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (isEnabled(true)) {
            PoseStack matrices = event.getMatrixStack();
            MultiBufferSource buffer = event.getBuffers();
            LivingEntity entity = event.getPlayer();

            matrices.pushPose();
            VertexConsumer vertices = buffer.getBuffer(RenderType.entityTranslucentCull(SALMON_LOCATION));
            SalmonModel<Entity> model = new SalmonModel<>();
            model.setupAnim(entity, 0.0f, 0.0f, entity.tickCount, entity.yRot, entity.xRot);
            matrices.mulPose(new Quaternion(0, 360f - entity.getViewYRot(event.getPartialRenderTick())/* - 180 % 360*/, 0, true));
            matrices.mulPose(new Quaternion(entity.getViewXRot(event.getPartialRenderTick()) + 180f, 0, 0, true));
//            matrices.rotate(new Quaternion(0, 0, 0, true));
            matrices.translate(0, -1.5, 0);
            model.renderToBuffer(matrices, vertices, event.getLight(), event.getLight() + 1, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.popPose();
            event.setCanceled(true);
        }
    }
}
