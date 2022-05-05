package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(DataGenerator generator, ModBlockTags blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(ItemTags.LOGS)
                .add(Registration.FROST_LOG_ITEM.get());
        tag(Tags.Items.ORES)
                .add(Registration.VERITE_ORE_ITEM.get());
    }

    @Override
    public String getName()
    {
        return "McJty Learning Tags";
    }
}
