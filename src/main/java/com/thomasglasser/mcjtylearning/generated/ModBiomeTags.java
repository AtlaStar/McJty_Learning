package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ModBiomeTags extends TagsProvider<Biome>
{
    public static final TagKey<Biome> HAS_ORE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "has_ore"));
    public static final TagKey<Biome> HAS_PORTAL = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "has_structure/portal"));
    public static final TagKey<Biome> HAS_THIEFDEN = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "has_structure/thiefden"));

    public ModBiomeTags(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, BuiltinRegistries.BIOME, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ForgeRegistries.BIOMES.getValues().forEach(biome -> {
            tag(HAS_ORE).add(TagEntry.tag(BiomeTags.IS_OVERWORLD.location()));
            tag(HAS_ORE).add(TagEntry.tag(BiomeTags.IS_NETHER.location()));
            tag(HAS_ORE).add(TagEntry.tag(BiomeTags.IS_END.location()));
            tag(HAS_PORTAL).add(biome);
            tag(HAS_THIEFDEN).add(biome);
        });
    }

    @Override
    public String getName() {
        return "McJtyLearning Tags";
    }
}
