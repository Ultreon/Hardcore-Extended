package com.zonlykroks.hardcoreex.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

@Mod.EventBusSubscriber
public class Config {

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER;

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT;

    static {
        ConfigBuilder.init(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER = SERVER_BUILDER.build();
        CLIENT = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        HardcoreExtended.LOGGER.info("Loading config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        HardcoreExtended.LOGGER.info("Built config: " + path);
        file.load();
        HardcoreExtended.LOGGER.info("Loaded config: " + path);
        config.setConfig(file);
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT);
    }

    public static void sync() {
        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("hardcoreex-client.toml").toString());
        Config.loadConfig(Config.SERVER, FMLPaths.CONFIGDIR.get().resolve("hardcoreex-server.toml").toString());
    }

    @SubscribeEvent
    public static void sync(ModConfigEvent.Loading event) {
        sync();
    }

    @SubscribeEvent
    public static void sync(ModConfigEvent.Reloading event) {
        sync();
    }

}
