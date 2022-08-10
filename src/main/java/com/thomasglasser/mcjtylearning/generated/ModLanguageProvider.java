package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.client.bindings.KeyBindings;
import com.thomasglasser.mcjtylearning.network.GatherManaPacket;
import com.thomasglasser.mcjtylearning.server.blocks.GeneratorBlock;
import com.thomasglasser.mcjtylearning.server.blocks.PowerGeneratorBlock;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator generator, String lang) {
        super(generator, McJtyLearning.MODID, lang);
    }

    @Override
    protected void addTranslations() {
        add(Elements.VERITE_ORE.get(), "Verite Ore");
        add(Elements.MYSTERIOUS_ORE.get(), "Mysterious Ore");
        add(Elements.DEEPSLATE_MYSTERIOUS_ORE.get(), "Deepslate Mysterious Ore");
        add(Elements.NETHER_MYSTERIOUS_ORE.get(), "Nether Mysterious Ore");
        add(Elements.END_MYSTERIOUS_ORE.get(), "End Mysterious Ore");
        add(Elements.FROST_LOG.get(), "Frost Log");
        add(Elements.POWER_GENERATOR.get(), "Power Generator");
        add(Elements.GENERATOR.get(), "Generator");
        add(Elements.PORTAL.get(), "Mysterious Portal");

        add(Elements.RAW_VERITE_CHUNK.get(), "Raw Verite Chunk");
        add(Elements.RAW_MYSTERIOUS_CHUNK.get(), "Raw Mysterious Chunk");
        add(Elements.VERITE_INGOT.get(), "Verite Ingot");
        add(Elements.MYSTERIOUS_INGOT.get(), "Mysterious Ingot");
        add(Elements.THIEF_SPAWN_EGG.get(), "Thief Spawn Egg");

        add(PowerGeneratorBlock.MESSAGE_POWER_GENERATOR, "Power Generator generating %s per tick!");
        add(PowerGeneratorBlock.SCREEN_TUTORIAL_POWER_GENERATOR, "Power Generator");
        add(GeneratorBlock.MESSAGE_GENERATOR, "Generate ores from ingots!");

        add(Elements.THIEF.get(), "Thief");

        add(KeyBindings.KEY_CATEGORIES_MANA, "Mana");
        add(KeyBindings.KEY_GATHER_MANA, "Gather Mana");

        add(GatherManaPacket.MESSAGE_NO_MANA, "No mana left in this chunk");
    }
}
