package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator generator, String lang) {
        super(generator, McJtyLearning.MODID, lang);
    }

    @Override
    protected void addTranslations() {
        add(Registration.VERITE_ORE.get(), "Verite Ore");
        add(Registration.FROST_LOG.get(), "Frost Log");
        add(Registration.RAW_VERITE_CHUNK.get(), "Raw Verite Chunk");
        add(Registration.VERITE_INGOT.get(), "Verite Ingot");
    }
}
