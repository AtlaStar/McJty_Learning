package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModStructureSetTags extends TagsProvider<StructureSet>
{

    public static final TagKey<StructureSet> MYSTERIOUS_DIMENSION_STRUCTURE_SET = TagKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "mysterious_dimension_structure_set"));

    protected ModStructureSetTags(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, BuiltinRegistries.STRUCTURE_SETS, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(MYSTERIOUS_DIMENSION_STRUCTURE_SET)
                .add(ResourceKey.create(BuiltinRegistries.STRUCTURE_SETS.key(), new ResourceLocation(McJtyLearning.MODID, "portal")))
                .add(ResourceKey.create(BuiltinRegistries.STRUCTURE_SETS.key(), new ResourceLocation(McJtyLearning.MODID, "thiefden")));
    }

    @Override
    public String getName() {
        return "McJtyLearning tags";
    }
}
