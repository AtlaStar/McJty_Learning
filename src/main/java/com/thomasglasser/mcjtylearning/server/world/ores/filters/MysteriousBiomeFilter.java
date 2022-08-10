package com.thomasglasser.mcjtylearning.server.world.ores.filters;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.function.Predicate;

public class MysteriousBiomeFilter extends PlacementFilter
{
    private final Predicate<ResourceKey<Level>> levelTest;

    public MysteriousBiomeFilter(Predicate<ResourceKey<Level>> levelTest)
    {
        this.levelTest = levelTest;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource randomSource, BlockPos pos) {
        if (levelTest.test(context.getLevel().getLevel().dimension()))
        {
            PlacedFeature placedFeature = context.topFeature().orElseThrow(() -> new IllegalStateException("Tried to biome check and unregistered feature!"));
            Biome biome = context.getLevel().getBiome(pos).get();
            return biome.getGenerationSettings().hasFeature(placedFeature);
        }
        return false;
    }

    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifierType.BIOME_FILTER;
    }
}
