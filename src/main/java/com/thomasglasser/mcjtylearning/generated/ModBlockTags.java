package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTags extends BlockTagsProvider
{
    public static final TagKey<Block> MYSTERIOUS_ORE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "mysterious_ore"));

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

        tag(BlockTags.PORTALS)
                .add(Elements.PORTAL.get());

        tag(MYSTERIOUS_ORE)
                .add(Elements.MYSTERIOUS_ORE.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE.get())
                .add(Elements.END_MYSTERIOUS_ORE.get());
    }

    @Override
    public String getName()
    {
        return "McJty Learning Tags";
    }
}
