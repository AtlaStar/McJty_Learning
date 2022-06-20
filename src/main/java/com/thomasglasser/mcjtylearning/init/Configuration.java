package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.init.config.ManaConfig;
import com.thomasglasser.mcjtylearning.init.config.GeneratorConfig;
import com.thomasglasser.mcjtylearning.init.config.PowerGeneratorConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Configuration
{
    public static void register()
    {
        // registerCommon();
        registerServer();
        registerClient();
    }

    /*
    private static void registerCommon()
    {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        //TODO: Add entries (Waiting on oregen)

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }
    */

    private static void registerServer()
    {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        GeneratorConfig.registerServer(SERVER_BUILDER);
        PowerGeneratorConfig.registerServer(SERVER_BUILDER);
        ManaConfig.registerServer(SERVER_BUILDER);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

    private static void registerClient()
    {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        PowerGeneratorConfig.registerClient(CLIENT_BUILDER);
        ManaConfig.registerClient(CLIENT_BUILDER);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
