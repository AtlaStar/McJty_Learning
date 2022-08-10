package com.thomasglasser.mcjtylearning.server.world.dimensions;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dimensions
{
    public static final ResourceKey<Level> MYSTERIOUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "mysterious:"));

    public static void register()
    {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(McJtyLearning.MODID, "mysterious_chunk_generation"), MysteriousChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(McJtyLearning.MODID, "biomes"), MysteriousBiomeProvider.CODEC);
    }
}
