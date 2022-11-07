package com.zonlykroks.hardcoreex.challenge;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.widgets.ChallengeCompatibility;
import com.zonlykroks.hardcoreex.common.IChallengeProvider;
import com.zonlykroks.hardcoreex.event.ChallengeFailedEvent;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.packets.ChallengeFailedPacket;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * @author Qboi123
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public abstract class Challenge extends ForgeRegistryEntry<Challenge> implements IChallengeProvider {
    protected static final Logger LOGGER = LoggerFactory.getLogger("Challenges");
    protected final Marker marker = MarkerFactory.getMarker(getClass().getSimpleName());
    private boolean enabled = false;
    private ForgeConfigSpec.BooleanValue configSpec;

    private boolean started;

    public Challenge() {

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
     * This is used for ticking the challenge.
     *
     * @param event the client tick event needed.
     */
    @SubscribeEvent
    public final void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tick();
        }
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
    public final void onPlayerTick(@NotNull TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            playerTick(event.player);
        }
    }

    @SubscribeEvent
    public final void onCheckSpawn(@NotNull LivingSpawnEvent.CheckSpawn event) {
        if (event.getEntity().getLevel().isClientSide) return;

        if (checkSpawn(event.getEntityLiving(), new Vec3(event.getX(), event.getY(), event.getZ()), event.getSpawner(), event.getSpawnReason())) {
            event.setResult(Event.Result.DENY); // Bruh needs to be a result, not cancel.
        }
    }

    @SubscribeEvent
    public final void onLivingUpdate(@NotNull LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().getLevel().isClientSide) return;

        event.setCanceled(livingUpdate(event.getEntityLiving()));
    }

    protected boolean checkSpawn(LivingEntity entity, Vec3 vec3, @Nullable BaseSpawner spawner, MobSpawnType spawnReason) {
        return false;
    }

    protected boolean livingUpdate(LivingEntity entity) {
        return false;
    }

    /**
     * Server tick event handling
     */
    @SubscribeEvent()
    public final void onServerTick(@NotNull TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            serverTick(Objects.requireNonNull(HardcoreExtended.getServer()));
        }
    }

    /**
     * Server tick event handling
     */
    @SubscribeEvent
    public final void onServerTick(@NotNull TickEvent.WorldTickEvent event) {
        worldTick((ServerLevel) event.world);
    }

    protected void playerTick(@NotNull Player player) {

    }

    public TranslatableComponent getDescription() {
        if (getRegistryName() != null) {
            return new TranslatableComponent("challenge." +
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
        return ClientChallengeManager.get().isEnabled(this);
    }

    private boolean isEnabled$client() {
        return ClientChallengeManager.get().isEnabled(this);
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
        return new ChallengeCompatibility(true, new TextComponent("TEST"));
    }

    /**
     * Let the player know that the challenge was failed.
     */
    public final void failChallenge(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.setGameMode(GameType.SPECTATOR);
            Networking.sendToClient(new ChallengeFailedPacket(this), (ServerPlayer) player);
            MinecraftForge.EVENT_BUS.post(new ChallengeFailedEvent(this, player, LogicalSide.SERVER));
        } else {
            Networking.sendToServer(new ChallengeFailedPacket(this));
        }
    }

    @SubscribeEvent
    public final void onDeath(@NotNull LivingDeathEvent event) {
        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        LivingEntity livingEntity = event.getEntityLiving();

        // Check for server side player entity.
        if (livingEntity instanceof ServerPlayer player) {
            // Fail challenge.
            ((ServerPlayer) livingEntity).setGameMode(GameType.SPECTATOR);
            Networking.sendToClient(new ChallengeFailedPacket((Challenge) null), player);
            MinecraftForge.EVENT_BUS.post(new ChallengeFailedEvent(this, player, LogicalSide.SERVER));
        }
    }

    protected void worldTick(@NotNull ServerLevel world) {

    }

    protected void serverTick(@NotNull MinecraftServer server) {

    }

    protected void onSpawnEntity(@NotNull Entity entity, @NotNull SpawnData data, @NotNull MobSpawnType reason) {

    }
}
