package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.tools.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;

public class ModLootTables extends BaseLootTableProvider {
    public ModLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables()
    {
        lootTables.put(Registration.VERITE_ORE.get(), createSilkTouchTable("verite_ore", Registration.VERITE_ORE.get(), Registration.RAW_VERITE_CHUNK.get(), 1, 3));

        lootTables.put(Registration.FROST_LOG.get(), createSimpleTable("frost_log", Registration.FROST_LOG.get()));

        lootTables.put(Registration.POWER_GENERATOR.get(), createStandardTable("power_generator", Registration.POWER_GENERATOR.get(), Registration.POWER_GENERATOR_BLOCK_ENTITY.get()));
        lootTables.put(Registration.GENERATOR.get(), createStandardTable("generator", Registration.GENERATOR.get(), Registration.GENERATOR_BLOCK_ENTITY.get()));
    }
}
