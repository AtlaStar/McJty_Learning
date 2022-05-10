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
        withExistingParent(Registration.VERITE_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/verite_ore"));

        cubeColumn(Registration.FROST_LOG_ITEM.get().getRegistryName().getPath(), modLoc("block/frost_log"), modLoc("block/frost_log_top"));

        singleTexture(Registration.RAW_VERITE_CHUNK.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/raw_verite_chunk"));
        singleTexture(Registration.VERITE_INGOT.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/verite_ingot"));
    }
}
