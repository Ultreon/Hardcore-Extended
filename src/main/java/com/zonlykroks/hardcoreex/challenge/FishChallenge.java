package com.zonlykroks.hardcoreex.challenge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
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
    protected void playerTick(@NotNull PlayerEntity player) {
        super.playerTick(player);
    }

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
            matrices.rotate(new Quaternion(0, 360f - entity.getYaw(event.getPartialRenderTick())/* - 180 % 360*/, 0, true));
            matrices.rotate(new Quaternion(entity.getPitch(event.getPartialRenderTick()) + 180f, 0, 0, true));
//            matrices.rotate(new Quaternion(0, 0, 0, true));
            matrices.translate(0, -1.5, 0);
            model.render(matrices, vertices, event.getLight(), event.getLight() + 1, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
            event.setCanceled(true);
        }
    }
}
