package com.zonlykroks.hardcoreex.client;

import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.event.ChallengesStartedEvent;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.packets.ChallengeDisabledPacket;
import com.zonlykroks.hardcoreex.network.packets.ChallengeEnabledPacket;
import com.zonlykroks.hardcoreex.network.packets.ChallengesStartedPacket;
import com.zonlykroks.hardcoreex.network.packets.StartChallengesPacket;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Collections;
import java.util.Objects;

public class ClientChallengeManager extends ChallengeManager {
    @Nullable
    private static ClientChallengeManager instance;
    private static final Marker MARKER = MarkerFactory.getMarker("Server");
    private static final Object initLock = new Object();
    private static boolean initialized = false;

    private boolean started;

    private ClientChallengeManager() {
        super();
    }

    public static void initEvents(IEventBus eventBus) {
        synchronized (initLock) {
            if (!initialized) {
                initialized = true;
                MinecraftForge.EVENT_BUS.addListener(ClientChallengeManager::onDisconnect);
                MinecraftForge.EVENT_BUS.addListener(ClientChallengeManager::onConnected);
            }
        }
    }

    public static ClientChallengeManager temp() {
        return new ClientChallengeManager() {
            @Override
            public void set(Challenge challenge, boolean value) {
                if (value) {
                    enabled.add(challenge);
                } else {
                    enabled.remove(challenge);
                }
            }
        };
    }

    public static void onStarted(ChallengesStartedPacket packet) {
        if (instance != null) {
            instance.handleStart();
        } else {
            LOGGER.warn(MARKER, "Got challenges started packet while disconnected.");
        }
    }

    public static void onEnabled(ChallengeEnabledPacket packet) {
        if (instance != null) {
            instance.enabled.add(Objects.requireNonNull(packet.getChallenge()));
        } else {
            LOGGER.warn(MARKER, "Got challenge enabled packet while disconnected.");
        }
    }

    public static void onDisabled(ChallengeDisabledPacket packet) {
        if (instance != null) {
            instance.enabled.remove(Objects.requireNonNull(packet.getChallenge()));
        } else {
            LOGGER.warn(MARKER, "Got challenge disabled packet while disconnected.");
        }
    }

    private static void onDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        LOGGER.debug(MARKER, "Cleaning up challenge manager.");

        if (instance != null) {
            instance.enabled.clear();
        }
        instance = null;
    }

    private static void onConnected(ClientPlayerNetworkEvent.LoggedInEvent event) {
        LOGGER.debug(MARKER, "Connected to a server, setting up client-side.");

        instance = new ClientChallengeManager();
    }

    public static ClientChallengeManager get() {
        return instance;
    }

    @Deprecated
    @Override
    public void set(Challenge challenge, boolean value) {
//        if (value && !enabled.contains(challenge)) {
//            Networking.sendToServer(new EnableChallengePacket(challenge.getRegistryName()));
//        } else if (!value && enabled.contains(challenge)) {
//            Networking.sendToServer(new DisableChallengePacket(challenge.getRegistryName()));
//        }
    }

    public void handleStart() {
        started = true;
        MinecraftForge.EVENT_BUS.post(new ChallengesStartedEvent(Collections.unmodifiableSet(enabled), LogicalSide.CLIENT));
    }

    public boolean isStarted() {
        return started;
    }

    public void sendStart() {
        if (!started) {
            Networking.sendToServer(new StartChallengesPacket());
        }
    }
}
