package com.thomasglasser.mcjtylearning.init.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PowerGeneratorConfig
{
    public static ForgeConfigSpec.IntValue POWER_GENERATOR_CAPACITY; // = 50000;
    public static ForgeConfigSpec.IntValue POWER_GENERATOR_GENERATE; // = 60;
    public static ForgeConfigSpec.IntValue POWER_GENERATOR_SEND; // = 200;

    public static ForgeConfigSpec.DoubleValue RENDER_SCALE;

    public static void registerServer(ForgeConfigSpec.Builder SERVER_BUILDER)
    {
        SERVER_BUILDER.comment("Settings for the power generator").push("powergenerator");

        POWER_GENERATOR_CAPACITY = SERVER_BUILDER
                .comment("Amount of energy the power generator can store")
                .defineInRange("capacity", 50000, 1, Integer.MAX_VALUE);

        POWER_GENERATOR_GENERATE = SERVER_BUILDER
                .comment("Amount of energy generated (per tick) by the power generator")
                .defineInRange("generate", 60, 1, Integer.MAX_VALUE);

        POWER_GENERATOR_SEND = SERVER_BUILDER
                .comment("Amount of energy transferred out per tick")
                .defineInRange("send", 200, 1, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    public static void registerClient(ForgeConfigSpec.Builder CLIENT_BUILDER)
    {
        CLIENT_BUILDER.comment("Settings for the power generator").push("powergenerator");

        RENDER_SCALE = CLIENT_BUILDER
                .comment("Size of the light shine")
                .defineInRange("scale", .3, 0.000001, Integer.MAX_VALUE);

        CLIENT_BUILDER.pop();
    }
}
