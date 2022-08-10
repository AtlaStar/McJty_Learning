package com.thomasglasser.mcjtylearning.server.world.ores;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import com.thomasglasser.mcjtylearning.server.world.dimensions.Dimensions;
import com.thomasglasser.mcjtylearning.server.world.ores.filters.MysteriousBiomeFilter;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;

import java.util.List;

public class MysteriousOres
{
    public static PlacedFeature createStoneGeneration()
    {
        OreConfiguration configuration = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Elements.MYSTERIOUS_ORE.get().defaultBlockState(), 5);
        return registerPlacedFeature(new ConfiguredFeature<>(Feature.ORE, configuration),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                new MysteriousBiomeFilter(key -> !Dimensions.MYSTERIOUS.equals(key)),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
    }

    public static PlacedFeature createMysteriousGeneration()
    {
        OreConfiguration configuration = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Elements.MYSTERIOUS_ORE.get().defaultBlockState(), 5);
        return registerPlacedFeature(new ConfiguredFeature<>(Feature.ORE, configuration),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                new MysteriousBiomeFilter(Dimensions.MYSTERIOUS::equals),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature registerPlacedFeature(ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers)
    {
        return new PlacedFeature(Holder.direct(feature), List.of(placementModifiers));
    }
}
