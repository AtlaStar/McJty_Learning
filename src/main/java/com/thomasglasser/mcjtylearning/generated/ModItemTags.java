package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTags extends ItemTagsProvider
{
    public static final TagKey<Item> MYSTERIOUS_ORE_ITEM = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(McJtyLearning.MODID, "mysterious_ore"));

    public ModItemTags(DataGenerator generator, ModBlockTags blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(ItemTags.LOGS)
                .add(Elements.FROST_LOG_ITEM.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(Elements.FROST_LOG_ITEM.get());

        tag(Tags.Items.ORES)
                .add(Elements.VERITE_ORE_ITEM.get())
                .add(Elements.MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.END_MYSTERIOUS_ORE_ITEM.get());

        tag(Tags.Items.INGOTS)
                .add(Elements.VERITE_INGOT.get())
                .add(Elements.MYSTERIOUS_INGOT.get());

        tag(Tags.Items.RAW_MATERIALS)
                .add(Elements.RAW_VERITE_CHUNK.get())
                .add(Elements.RAW_MYSTERIOUS_CHUNK.get());

        tag(Tags.Items.STORAGE_BLOCKS)
                .add(Elements.GENERATOR_ITEM.get());

        tag(MYSTERIOUS_ORE_ITEM)
                .add(Elements.MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.DEEPSLATE_MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.NETHER_MYSTERIOUS_ORE_ITEM.get())
                .add(Elements.END_MYSTERIOUS_ORE_ITEM.get());
    }

    @Override
    public String getName()
    {
        return "McJty Learning Tags";
    }
}
