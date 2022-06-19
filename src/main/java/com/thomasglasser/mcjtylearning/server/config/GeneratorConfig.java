package com.thomasglasser.mcjtylearning.server.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneratorConfig
{
    public static ForgeConfigSpec.IntValue COLLECTING_DELAY; //= 10;
    public static ForgeConfigSpec.IntValue INGOTS_PER_ORE;
    public static ForgeConfigSpec.IntValue ENERGY_CAPACITY;
    public static ForgeConfigSpec.IntValue ENERGY_RECEIVE;
    public static ForgeConfigSpec.IntValue ENERGY_GENERATE;

    public static void registerServer(ForgeConfigSpec.Builder SERVER_BUILDER)
    {
        SERVER_BUILDER.comment("Settings for the generator").push("generator");

        COLLECTING_DELAY = SERVER_BUILDER
                .comment("Delay (in ticks) before items are collected")
                .defineInRange("collectingDelay", 10, 1, Integer.MAX_VALUE);

        INGOTS_PER_ORE = SERVER_BUILDER
                .comment("Number of ingots generated from one ore")
                .defineInRange("ingotsPerOre", 10, 1, Integer.MAX_VALUE);

        ENERGY_CAPACITY = SERVER_BUILDER
                .comment("Amount of energy the generator is able to hold")
                .defineInRange("capacity", 100000, 1, Integer.MAX_VALUE);

        ENERGY_RECEIVE = SERVER_BUILDER
                .comment("Amount of energy the generator can receive per block side")
                .defineInRange("receive", 1000, 1, Integer.MAX_VALUE);

        ENERGY_GENERATE = SERVER_BUILDER
                .comment("Amount of energy needed to process one ore block")
                .defineInRange("generate", 500, 1, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }
}
