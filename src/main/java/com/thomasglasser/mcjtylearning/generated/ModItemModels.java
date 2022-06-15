package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.VERITE_ORE_ITEM.getId().getPath(), modLoc("block/verite_ore"));
        withExistingParent(Registration.POWER_GENERATOR.getId().getPath(), modLoc("block/powergen/main"));
        withExistingParent(Registration.GENERATOR.getId().getPath(), modLoc("block/generator"));

        cubeColumn(Registration.FROST_LOG_ITEM.getId().getPath(), modLoc("block/frost_log"), modLoc("block/frost_log_top"));

        singleTexture(Registration.RAW_VERITE_CHUNK.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/raw_verite_chunk"));
        singleTexture(Registration.VERITE_INGOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/verite_ingot"));
    }
}
