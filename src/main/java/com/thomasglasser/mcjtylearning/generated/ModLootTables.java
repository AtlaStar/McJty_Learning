package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.init.Elements;
import com.thomasglasser.mcjtylearning.tools.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;

public class ModLootTables extends BaseLootTableProvider {
    public ModLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables()
    {
        lootTables.put(Elements.VERITE_ORE.get(), createSilkTouchTable("verite_ore", Elements.VERITE_ORE.get(), Elements.RAW_VERITE_CHUNK.get(), 1, 3));
        lootTables.put(Elements.MYSTERIOUS_ORE.get(), createSilkTouchTable("mysterious_ore", Elements.MYSTERIOUS_ORE.get(), Elements.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        lootTables.put(Elements.DEEPSLATE_MYSTERIOUS_ORE.get(), createSilkTouchTable("deepslate_mysterious_ore", Elements.DEEPSLATE_MYSTERIOUS_ORE.get(), Elements.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        lootTables.put(Elements.NETHER_MYSTERIOUS_ORE.get(), createSilkTouchTable("nether_mysterious_ore", Elements.NETHER_MYSTERIOUS_ORE.get(), Elements.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        lootTables.put(Elements.END_MYSTERIOUS_ORE.get(), createSilkTouchTable("end_mysterious_ore", Elements.END_MYSTERIOUS_ORE.get(), Elements.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));

        lootTables.put(Elements.FROST_LOG.get(), createSimpleTable("frost_log", Elements.FROST_LOG.get()));

        lootTables.put(Elements.POWER_GENERATOR.get(), createStandardTable("power_generator", Elements.POWER_GENERATOR.get(), Elements.POWER_GENERATOR_BLOCK_ENTITY.get()));
        lootTables.put(Elements.GENERATOR.get(), createStandardTable("generator", Elements.GENERATOR.get(), Elements.GENERATOR_BLOCK_ENTITY.get()));
    }
}
