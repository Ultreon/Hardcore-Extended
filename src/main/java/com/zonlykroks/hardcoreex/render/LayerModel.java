package com.zonlykroks.hardcoreex.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LayerModel extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public LayerModel(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> entityRendererIn) {
        super(entityRendererIn);
    }

    private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");

    @Override
    public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, @NotNull AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ClientChallengeManager.get().isEnabled(ModChallenges.ONLY_FISH)) {
            matrixStackIn.pushPose();
//            VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityTranslucentCull(SALMON_LOCATION));
//            SalmonModel<Entity> model = new SalmonModel<>();
//            model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//            if (entitylivingbaseIn.isInWater() && entitylivingbaseIn.isSprinting()) {
//                matrixStackIn.mulPose(new Quaternion(270, 0, 0, true));
//            }
//            model.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, packedLightIn + 1, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }
}
