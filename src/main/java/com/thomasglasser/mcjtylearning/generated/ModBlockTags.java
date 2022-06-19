package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
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
                .add(Registration.VERITE_ORE.get())
                .add(Registration.POWER_GENERATOR.get())
                .add(Registration.GENERATOR.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(Registration.FROST_LOG.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Registration.VERITE_ORE.get())
                .add(Registration.POWER_GENERATOR.get())
                .add(Registration.GENERATOR.get());

        tag(Tags.Blocks.ORES)
                .add(Registration.VERITE_ORE.get());

        tag(BlockTags.LOGS)
                .add(Registration.FROST_LOG.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(Registration.FROST_LOG.get());

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .add(Registration.GENERATOR.get());
    }

    @Override
    public String getName()
    {
        return "McJty Learning Tags";
    }
}
