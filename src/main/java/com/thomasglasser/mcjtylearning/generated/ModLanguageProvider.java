package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.server.blocks.GeneratorBlock;
import com.thomasglasser.mcjtylearning.server.blocks.PowerGeneratorBlock;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator generator, String lang) {
        super(generator, McJtyLearning.MODID, lang);
    }

    @Override
    protected void addTranslations() {
        add(Registration.VERITE_ORE.get(), "Verite Ore");
        add(Registration.FROST_LOG.get(), "Frost Log");
        add(Registration.POWER_GENERATOR.get(), "Power Generator");
        add(Registration.GENERATOR.get(), "Generator");

        add(Registration.RAW_VERITE_CHUNK.get(), "Raw Verite Chunk");
        add(Registration.VERITE_INGOT.get(), "Verite Ingot");
        add(Registration.THIEF_SPAWN_EGG.get(), "Thief Spawn Egg");

        add(PowerGeneratorBlock.MESSAGE_POWER_GENERATOR, "Power Generator generating %s per tick!");
        add(PowerGeneratorBlock.SCREEN_TUTORIAL_POWER_GENERATOR, "Power Generator");
        add(GeneratorBlock.MESSAGE_GENERATOR, "Generate ores from ingots!");

        add(Registration.THIEF.get(), "Thief");
    }
}
