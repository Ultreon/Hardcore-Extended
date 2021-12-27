package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.ChallengeFailedScreen;
import com.zonlykroks.hardcoreex.client.gui.widgets.ChallengeCompatibility;
import com.zonlykroks.hardcoreex.common.IChallengeProvider;
import com.zonlykroks.hardcoreex.network.ChallengeFailedPacket;
import com.zonlykroks.hardcoreex.network.Networking;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * @author Qboi123
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public abstract class Challenge implements IChallengeProvider, IForgeRegistryEntry<Challenge> {
    private boolean enabled = false;
    private ForgeConfigSpec.BooleanValue configSpec;

    private ResourceLocation registryName;
    private boolean started;

    public Challenge() {

        if (ChallengeManager.client.isEnabled(this) && !this.enabled) {
            ChallengeManager.client.enable(this);
        }
        if (!ChallengeManager.client.isEnabled(this) && this.enabled) {
            ChallengeManager.client.disable(this);
        }
    }

    @Override
    public Challenge getChallenge() {
        return this;
    }

    @Deprecated
    public final void enable() {
        MinecraftForge.EVENT_BUS.register(this);
        this.enabled = true;
        this.configSpec.set(true);
        this.configSpec.save();
    }

    @Deprecated
    public final void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        this.enabled = false;
        this.configSpec.set(false);
        this.configSpec.save();
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    /**
     * Get and return the config specification.
     *
     * @return the config specification.
     */
    public ForgeConfigSpec.BooleanValue getConfigSpec() {
        return this.configSpec;
    }

    /**
     * Set the config specification.
     *
     * @param configSpec the config specification.
     */
    protected void setConfigSpec(ForgeConfigSpec.BooleanValue configSpec) {
        this.configSpec = configSpec;
    }

    /**
     * Set the challenge's registry name.
     *
     * @param name the registry name to set to.
     * @return the challenge object where this method called from.
     */
    @Override
    public Challenge setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    /**
     * This is used for ticking the challenge.
     *
     * @param event the client tick event needed.
     */
    @SubscribeEvent
    public final void onClientTick(TickEvent.ClientTickEvent event) {
        tick();
    }

    /**
     * Tick the challenge, in fact a client-tick event.
     */
    protected void tick() {

    }

    /**
     * Player tick event handling
     */
    @SubscribeEvent
    public final void onPlayerTick(TickEvent.PlayerTickEvent event) {
        playerTick(event.player);
    }

    /**
     * Server tick event handling
     */
    @SubscribeEvent()
    public final void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            serverTick(Objects.requireNonNull(HardcoreExtended.getServer()));
        }
    }

    /**
     * Server tick event handling
     */
    @SubscribeEvent
    public final void onServerTick(TickEvent.WorldTickEvent event) {
        worldTick((ServerWorld) event.world);
    }

    protected void playerTick(PlayerEntity player) {

    }

//    @SubscribeEvent
//    protected void onPlayerMove(EntityJoinWorldEvent event) {
//        Entity entity = event.getEntity();
//        if (entity instanceof ServerPlayerEntity && !started) {
//            joined = true;
//            this.startingCoords = entity.getPositionVec();
//        }
//    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public Class<Challenge> getRegistryType() {
        return Challenge.class;
    }

    public TranslationTextComponent getLocalizedName() {
        if (getRegistryName() != null) {
            return new TranslationTextComponent("challenge." +
                    getRegistryName().getNamespace() + "." +
                    getRegistryName().getPath().replaceAll("/", "."));
        } else {
            throw new NullPointerException("Registry name is null.");
        }
    }

    /**
     * <h1>Get the Texture Location</h1>
     * In this method the texture will be located using the registry name.<br>
     * A registry name is a resource location that contains the Mod ID and a path.<br>
     * <br>
     * <!--Get texture location, it's relative to "assets/mod-id/"-->
     * <!--Replacing mod-id with the mod's id given in the resource location.-->
     *
     * <h2>Info:</h2>
     *
     * @return the texture location/
     */
    public ResourceLocation getTextureLocation() {
        assert getRegistryName() != null;
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/gui/challenges/" + getRegistryName().getPath() + ".png");
    }

    /**
     * Receive boolean value of whether the challenge is enabled or not.
     * True means it's enabled. Opposite of {@link #isDisabled(boolean)}
     *
     * @param clientSide true if it's called client side, false for serve side.
     * @return if the challenge is enabled.
     */
    public boolean isEnabled(boolean clientSide) {
        return clientSide ? this.isEnabled$client() : this.isEnabled$server();
    }

    private boolean isEnabled$server() {
        return ChallengeManager.client.isEnabled(this);
    }

    private boolean isEnabled$client() {
        return ChallengeManager.client.isEnabled(this);
    }

    /**
     * Receive boolean value of whether the challenge is disabled or not.
     * True means it's disabled. Opposite of {@link #isEnabled(boolean)}
     *
     * @param clientSide true if it's called client side, false for serve side.
     * @return if the challenge is disabled.
     */
    public boolean isDisabled(boolean clientSide) {
        return !isEnabled(clientSide);
    }

    /**
     * @return the challenge compatibility.
     */
    public ChallengeCompatibility getCompatibility() {
        return new ChallengeCompatibility(true, new StringTextComponent("TEST"));
    }

    /**
     * Let the player know that the challenge was failed.
     */
    public final void failChallenge(PlayerEntity player) {

        DistExecutor.unsafeRunForDist(() -> () -> failChallengeClient(player), () -> () -> {
            if (player instanceof ServerPlayerEntity) {
                return failChallengeServer((ServerPlayerEntity) player);
            } else {
                HardcoreExtended.LOGGER.error("Expected " + ServerPlayerEntity.class.getName() + ", got: " + player.getClass().getName());
                return null;
            }
        });

    }

    @Nullable
    private <T> T failChallengeServer(ServerPlayerEntity player) {
        Networking.sendToClient(new ChallengeFailedPacket(this.registryName), player);
        return null;
    }

    @Nullable
    private <T> T failChallengeClient(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            return failChallengeServer((ServerPlayerEntity) player);
        }
        Networking.sendToServer(new ChallengeFailedPacket(this.registryName));
        Minecraft.getInstance().displayGuiScreen(new ChallengeFailedScreen(this));
        return null;
    }

    @SubscribeEvent
    public final void onDeath(LivingDeathEvent event) {
        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        // Check for server side player entity.
        if (event.getEntityLiving() instanceof ServerPlayerEntity) {
            // Fail challenge.
            this.failChallenge((PlayerEntity) event.getEntityLiving());
        }
    }

    protected void worldTick(ServerWorld world) {

    }

    protected void serverTick(MinecraftServer server) {

    }
}
