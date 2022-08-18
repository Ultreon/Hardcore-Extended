package com.zonlykroks.hardcoreex.challenge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
            PoseStack pose = event.getPoseStack();
            LivingEntity entity = event.getPlayer();

            pose.pushPose();
            Salmon salmon = new Salmon(EntityType.SALMON, entity.level);
            salmon.animationPosition = entity.animationPosition;
            salmon.animationSpeed = entity.animationSpeed;
            salmon.tickCount = entity.tickCount;
            salmon.animationSpeedOld = entity.animationSpeedOld;
            salmon.attackAnim = entity.attackAnim;
            salmon.oAttackAnim = entity.oAttackAnim;
            salmon.swinging = entity.swinging;
            salmon.swingingArm = entity.swingingArm;
            salmon.swingTime = entity.swingTime;
            salmon.fallDistance = entity.fallDistance;
            salmon.hurtDuration = entity.hurtDuration;
            salmon.hurtTime = entity.hurtTime;
            salmon.deathTime = entity.deathTime;
            salmon.hurtDir = entity.hurtDir;
            salmon.hurtMarked = entity.hurtMarked;
            salmon.noCulling = entity.noCulling;
            salmon.noPhysics = entity.noPhysics;
            salmon.setNoGravity(entity.isNoGravity());
            salmon.setBaby(entity.isBaby());
            salmon.setInvisible(entity.isInvisible());
            salmon.setInvulnerable(entity.isInvulnerable());
            salmon.setRemainingFireTicks(entity.getRemainingFireTicks());
            salmon.setItemInHand(InteractionHand.MAIN_HAND, entity.getItemInHand(InteractionHand.MAIN_HAND));
            salmon.setItemInHand(InteractionHand.OFF_HAND, entity.getItemInHand(InteractionHand.OFF_HAND));
            salmon.setInvulnerable(entity.isInvulnerable());
            salmon.setAbsorptionAmount(entity.getAbsorptionAmount());
            salmon.setHealth(entity.getHealth());

            Optional<BlockPos> sleepingPos = entity.getSleepingPos();
            sleepingPos.ifPresent(salmon::setSleepingPos);
            Minecraft.getInstance().getEntityRenderDispatcher().render(salmon, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), event.getPlayer().getYRot(), event.getPlayer().getXRot(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
            pose.popPose();
        }
    }
}
