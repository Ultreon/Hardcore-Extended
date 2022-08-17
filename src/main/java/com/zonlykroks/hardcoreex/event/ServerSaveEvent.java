package com.zonlykroks.hardcoreex.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.eventbus.api.Event;

import java.io.File;

public class ServerSaveEvent extends Event {
    private final MinecraftServer server;
    private final boolean flush;
    private final boolean forced;

    public ServerSaveEvent(MinecraftServer server, boolean flush, boolean forced) {
        this.server = server;
        this.flush = flush;
        this.forced = forced;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public boolean isFlush() {
        return flush;
    }

    public boolean isForced() {
        return forced;
    }

    public File getServerFile(String s) {
        return server.getWorldPath(new LevelResource(s)).toFile();
    }
}
