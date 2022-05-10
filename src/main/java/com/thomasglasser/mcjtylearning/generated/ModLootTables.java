package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;

public class ModLootTables extends BaseLootTableProvider {
    public ModLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables()
    {
        lootTables.put(Registration.VERITE_ORE.get(), createSilkTouchTable("verite_ore", Registration.VERITE_ORE.get(), Registration.RAW_VERITE_CHUNK.get(), 1, 3));

        lootTables.put(Registration.FROST_LOG.get(), createSimpleTable("frost_log", Registration.FROST_LOG.get()));
    }
}
