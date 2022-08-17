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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.world.level.storage.LevelResource;
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
        CompoundTag worldData = new CompoundTag();
        ListTag challengesData = instance.save(new ListTag());
        worldData.put("ChallengesEnabled", challengesData);

        try {
            worldData = NbtIo.readCompressed(server.getWorldPath(new LevelResource("hardcoreex.dat")).toFile());
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ListTag challengesEnabled = worldData.getList("ChallengesEnabled", 8);
        instance.load(challengesEnabled);
    }

    private static void serverStop(FMLServerStoppingEvent event) {
        if (instance != null) {
            instance.disableAll();
        }

        instance = null;
    }

    private static void onServerSave(ServerSaveEvent event) {
        CompoundTag data = new CompoundTag();
        ListTag challenges = ServerChallengesManager.get().save(new ListTag());
        data.put("ChallengesEnabled", challenges);

        try {
            NbtIo.writeCompressed(data, event.getServerFile("hardcoreex.dat"));
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

    private ListTag save(ListTag nbt) {
        for (Challenge challenge : enabled) {
            if (challenge.getRegistryName() != null) {
                nbt.add(StringTag.valueOf(challenge.getRegistryName().toString()));
            }
        }
        return nbt;
    }

    private void load(ListTag nbt) {
        for (Tag element : nbt) {
            if (element instanceof StringTag) {
                String string = element.getAsString();
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
        server.setEnforceWhitelist(true);
        for (ServerOpListEntry entry : new ArrayList<>(server.getPlayerList().getOps().getEntries())) {
            server.getPlayerList().deop(Objects.requireNonNull(entry.getUser()));
        }
        Networking.sendToAllClients(new ChallengesStartedPacket());
    }

    public boolean isStarted() {
        return started;
    }

    public void sendChallenges(ServerPlayer player) {
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
