package com.thomasglasser.mcjtylearning.init.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ManaConfig
{
    public static ForgeConfigSpec.IntValue CHUNK_MIN_MANA;
    public static ForgeConfigSpec.IntValue CHUNK_MAX_MANA;

    public static ForgeConfigSpec.IntValue HUD_X;
    public static ForgeConfigSpec.IntValue HUD_Y;
    public static ForgeConfigSpec.IntValue HUD_COLOR;

    public static void registerServer(ForgeConfigSpec.Builder SERVER_BUILDER)
    {
        SERVER_BUILDER.comment("Settings for mana").push("mana");

        CHUNK_MIN_MANA = SERVER_BUILDER
                .comment("Minimum amount of mana in a chunk")
                .defineInRange("minMana", 10, 0, Integer.MAX_VALUE);

        CHUNK_MAX_MANA = SERVER_BUILDER
                .comment("Maximum amount of mana in a chunk")
                .defineInRange("maxMana", 100, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    public static void registerClient(ForgeConfigSpec.Builder CLIENT_BUILDER)
    {
        CLIENT_BUILDER.comment("Settings for mana").push("mana");

        HUD_X = CLIENT_BUILDER
                .comment("X location of the mana HUD on-screen\n(Set to -1 to disable HUD)")
                .defineInRange("hudX", 10, -1, Integer.MAX_VALUE);

        HUD_Y = CLIENT_BUILDER
                .comment("Y location of the mana HUD on-screen\n(Set to -1 to disable HUD)")
                .defineInRange("hudY", 10, -1, Integer.MAX_VALUE);

        HUD_COLOR = CLIENT_BUILDER
                .comment("Color of the mana HUD text")
                .defineInRange("hudColor", 0xffffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        CLIENT_BUILDER.pop();
    }
}
