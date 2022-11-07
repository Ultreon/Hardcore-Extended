package com.zonlykroks.hardcoreex.challenge.manager;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.SideExecutor;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.packets.ChallengeDisabledPacket;
import com.zonlykroks.hardcoreex.network.packets.ChallengeEnabledPacket;
import com.zonlykroks.hardcoreex.server.ServerChallengesManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ChallengeManager {
    protected final Set<Challenge> enabled = new HashSet<>();
    protected static final Logger LOGGER = LoggerFactory.getLogger("ChallengeManager");

    @Deprecated
    public static final ChallengeManager server = null;
    @Deprecated
    public static final ChallengeManager client = null;

    protected ChallengeManager() {

    }

    public static ChallengeManager getForEntity(Entity entity) {
        if (entity instanceof Player) {
            return SideExecutor.unsafeGetForSide((Player) entity, () -> ClientChallengeManager::get, () -> ServerChallengesManager::get);
        } else {
            return SideExecutor.unsafeGetForSide(entity, () -> ClientChallengeManager::get, () -> ServerChallengesManager::get);
        }
    }

    public static ChallengeManager getForWorld(Level world) {
        return SideExecutor.unsafeGetForSide(world, () -> ClientChallengeManager::get, () -> ServerChallengesManager::get);
    }

    public static ChallengeManager getForSide(LogicalSide side) {
        return SideExecutor.unsafeGetForSide(side, () -> ClientChallengeManager::get, () -> ServerChallengesManager::get);
    }

    public void enable(Challenge challenge) {
        set(challenge, true);
    }

    public void disable(Challenge challenge) {
        set(challenge, false);
    }

    public abstract void set(Challenge challenge, boolean value);

    public void readPacket(ChallengeEnabledPacket packet) {
        Challenge challenge = ModChallenges.getRegistry().getValue(packet.getRegistryName());
        if (!enabled.contains(challenge) && challenge != null) {
            enabled.add(challenge);
            challenge.onEnable();
        }
    }

    public void readPacket(ChallengeDisabledPacket packet) {
        Challenge challenge = ModChallenges.getRegistry().getValue(packet.getRegistryName());
        if (enabled.contains(challenge) && challenge != null) {
            enabled.add(challenge);
            challenge.onDisable();
        }
    }

    public boolean isEnabled(Challenge challenge) {
        return enabled.contains(challenge);
    }

    public boolean isEnabled(Supplier<? extends Challenge> supplier) {
        return isEnabled(supplier.get());
    }

    public boolean isDisabled(Challenge challenge) {
        return !isEnabled(challenge);
    }

    public boolean isDisabled(Supplier<? extends Challenge> challenge) {
        return !isEnabled(challenge);
    }

    @OnlyIn(Dist.CLIENT)
    private void clear() {
        enabled.clear();
        HardcoreExtended.LOGGER.info("Cleared client challenges.");
    }

    @Deprecated
    public void invalidateClient() {
        client.clear();
    }
}
