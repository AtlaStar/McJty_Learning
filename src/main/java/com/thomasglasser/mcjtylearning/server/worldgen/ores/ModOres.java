package com.thomasglasser.mcjtylearning.server.worldgen.ores;

import com.thomasglasser.mcjtylearning.init.Elements;
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

public class ModOres
{
    //Replace all with config values
    public static final int MYSTERIOUS_ORE_VEIN_SIZE = 5;
    public static final int MYSTERIOUS_ORE_AMOUNT = 5;
    public static final int DEEPSLATE_MYSTERIOUS_ORE_VEIN_SIZE = 5;
    public static final int DEEPSLATE_MYSTERIOUS_ORE_AMOUNT = 5;
    public static final int NETHER_MYSTERIOUS_ORE_VEIN_SIZE = 5;
    public static final int NETHER_MYSTERIOUS_ORE_AMOUNT = 5;
    public static final int END_MYSTERIOUS_ORE_VEIN_SIZE = 5;
    public static final int END_MYSTERIOUS_ORE_AMOUNT = 5;

    public static final RuleTest IN_ENDSTONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static Holder<PlacedFeature> DEEPSLATE_MYSTERIOUS_ORE_GENERATION;
    public static Holder<PlacedFeature> NETHER_MYSTERIOUS_ORE_GENERATION;
    public static Holder<PlacedFeature> END_MYSTERIOUS_ORE_GENERATION;
}
