package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Elements.VERITE_ORE_ITEM.getId().getPath(), modLoc("block/verite_ore"));
        withExistingParent(Elements.MYSTERIOUS_ORE_ITEM.getId().getPath(), modLoc("block/mysterious_ore"));
        withExistingParent(Elements.DEEPSLATE_MYSTERIOUS_ORE_ITEM.getId().getPath(), modLoc("block/deepslate_mysterious_ore"));
        withExistingParent(Elements.NETHER_MYSTERIOUS_ORE_ITEM.getId().getPath(), modLoc("block/nether_mysterious_ore"));
        withExistingParent(Elements.END_MYSTERIOUS_ORE_ITEM.getId().getPath(), modLoc("block/end_mysterious_ore"));
        withExistingParent(Elements.POWER_GENERATOR.getId().getPath(), modLoc("block/powergen/main"));
        withExistingParent(Elements.GENERATOR.getId().getPath(), modLoc("block/generator"));
        withExistingParent(Elements.THIEF_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        cubeColumn(Elements.FROST_LOG_ITEM.getId().getPath(), modLoc("block/frost_log"), modLoc("block/frost_log_top"));

        singleTexture(Elements.RAW_VERITE_CHUNK.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/raw_verite_chunk"));
        singleTexture(Elements.VERITE_INGOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/verite_ingot"));
        singleTexture(Elements.RAW_MYSTERIOUS_CHUNK.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/raw_mysterious_chunk"));
        singleTexture(Elements.MYSTERIOUS_INGOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/mysterious_ingot"));
    }
}
