package com.zonlykroks.hardcoreex.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigBuilder {
    public static ForgeConfigSpec.BooleanValue forceHardcore;

    @SuppressWarnings("CommentedOutCode")
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        ///////////////////////////
        //     Client config     //
        ///////////////////////////
        client.comment("HardcoreExtended Extended Challenge Config");

        forceHardcore = client.comment("Force hardcore mode when creating a world.")
                .define("challenges.forceHardcore", true);

//        ///////////////////////////
//        //     Server config     //
//        ///////////////////////////
//        server.comment("HardcoreExtended Extended Challenge Config");
    }

}
