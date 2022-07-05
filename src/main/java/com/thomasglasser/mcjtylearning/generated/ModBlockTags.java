package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Elements.VERITE_ORE.get())
                .add(Elements.MYSTERIOUS_ORE.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE.get())
                .add(Elements.END_MYSTERIOUS_ORE.get())
                .add(Elements.POWER_GENERATOR.get())
                .add(Elements.GENERATOR.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(Elements.FROST_LOG.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Elements.VERITE_ORE.get())
                .add(Elements.MYSTERIOUS_ORE.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE.get())
                .add(Elements.END_MYSTERIOUS_ORE.get())
                .add(Elements.POWER_GENERATOR.get())
                .add(Elements.GENERATOR.get());

        tag(Tags.Blocks.ORES)
                .add(Elements.VERITE_ORE.get())
                .add(Elements.MYSTERIOUS_ORE.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE.get())
                .add(Elements.END_MYSTERIOUS_ORE.get());

        tag(BlockTags.LOGS)
                .add(Elements.FROST_LOG.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(Elements.FROST_LOG.get());

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .add(Elements.GENERATOR.get());
    }

    @Override
    public String getName()
    {
        return "McJty Learning Tags";
    }
}
