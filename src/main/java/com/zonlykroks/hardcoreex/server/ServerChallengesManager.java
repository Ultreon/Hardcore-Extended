package com.zonlykroks.hardcoreex.server;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.event.ServerSaveEvent;
import com.zonlykroks.hardcoreex.init.ModChallenges;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.packets.ChallengeDisabledPacket;
import com.zonlykroks.hardcoreex.network.packets.ChallengeEnabledPacket;
import com.zonlykroks.hardcoreex.network.packets.ChallengesStartedPacket;
import com.zonlykroks.hardcoreex.network.packets.OpenChallengesMenuPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.OpEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Server side challenge manager.
 *
 * @author Qboi123
 * @since 1.0
 */
public class ServerChallengesManager extends ChallengeManager {
    @Nullable
    private static ServerChallengesManager instance;

    private static final Object initLock = new Object();
    private static boolean initialized;
    private boolean started = false;

    /**
     * Initializes events for the challenge manager.
     *
     * @param eventBus The event bus to register events to.
     */
    public static void initEvents(IEventBus eventBus) {
        synchronized (initLock) {
            if (!initialized) {
                initialized = true;
                MinecraftForge.EVENT_BUS.addListener(ServerChallengesManager::serverStart);
                MinecraftForge.EVENT_BUS.addListener(ServerChallengesManager::serverStop);
                MinecraftForge.EVENT_BUS.addListener(ServerChallengesManager::onServerSave);
            }
        }
    }

    private static void serverStart(FMLServerStartingEvent event) {
        instance = new ServerChallengesManager(event.getServer());

        MinecraftServer server = event.getServer();
        CompoundNBT worldData = new CompoundNBT();
        ListNBT challengesData = instance.save(new ListNBT());
        worldData.put("ChallengesEnabled", challengesData);

        try {
            worldData = CompressedStreamTools.readCompressed(server.func_240776_a_(new FolderName("hardcoreex.dat")).toFile());
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ListNBT challengesEnabled = worldData.getList("ChallengesEnabled", 8);
        instance.load(challengesEnabled);
    }

    private static void serverStop(FMLServerStoppingEvent event) {
        if (instance != null) {
            instance.disableAll();
        }

        instance = null;
    }

    private static void onServerSave(ServerSaveEvent event) {
        CompoundNBT data = new CompoundNBT();
        ListNBT challenges = ServerChallengesManager.get().save(new ListNBT());
        data.put("ChallengesEnabled", challenges);

        try {
            CompressedStreamTools.writeCompressed(data, event.getServerFile("hardcoreex.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disableAll() {
        for (Challenge challenge : new ArrayList<>(enabled)) {
            disable(challenge);
        }
    }

    private final MinecraftServer server;

    private ServerChallengesManager(MinecraftServer server) {
        super();
        this.server = server;
    }

    /**
     * Gets the server challenge manager.
     *
     * @return The server challenge manager.
     */
    public static ServerChallengesManager get() {
        return instance;
    }

    /**
     * Enable or disable a challenge.
     *
     * @param challenge The challenge to enable or disable.
     * @param value True to enable, false to disable.
     */
    @Override
    public void set(Challenge challenge, boolean value) {
        if (started) {
            return;
        }

        if (value && !enabled.contains(challenge)) {
            enabled.add(challenge);
            challenge.onEnable();
            Networking.sendToAllClients(new ChallengeEnabledPacket(challenge.getRegistryName()));
        } else if (!value && enabled.contains(challenge)) {
            enabled.remove(challenge);
            challenge.onDisable();
            Networking.sendToAllClients(new ChallengeDisabledPacket(challenge.getRegistryName()));
        }
    }

    private ListNBT save(ListNBT nbt) {
        for (Challenge challenge : enabled) {
            if (challenge.getRegistryName() != null) {
                nbt.add(StringNBT.valueOf(challenge.getRegistryName().toString()));
            }
        }
        return nbt;
    }

    private void load(ListNBT nbt) {
        for (INBT element : nbt) {
            if (element instanceof StringNBT) {
                String string = element.getString();
                ResourceLocation rl = new ResourceLocation(string);
                Challenge value = ModChallenges.getRegistry().getValue(rl);
                if (value != null) {
                    enabled.add(value);
                    value.onEnable();
                } else {
                    HardcoreExtended.LOGGER.warn("Challenge was not found: " + string);
                }
            }
        }
    }

    public MinecraftServer getServer() {
        return server;
    }

    public void start() {
        started = true;
        server.setWhitelistEnabled(true);
        for (OpEntry entry : new ArrayList<>(server.getPlayerList().getOppedPlayers().getEntries())) {
            server.getPlayerList().removeOp(Objects.requireNonNull(entry.getValue()));
        }
        Networking.sendToAllClients(new ChallengesStartedPacket());
    }

    public boolean isStarted() {
        return started;
    }

    public void sendChallenges(ServerPlayerEntity player) {
        if (started) {
            for (Challenge challenge : enabled) {
                Networking.sendToClient(new ChallengeEnabledPacket(challenge.getRegistryName()), player);
            }
            Networking.sendToClient(new ChallengesStartedPacket(), player);
        } else {
            Networking.sendToClient(new OpenChallengesMenuPacket(), player);
        }
    }
}
