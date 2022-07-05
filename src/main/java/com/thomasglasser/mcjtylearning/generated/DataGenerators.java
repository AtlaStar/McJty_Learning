package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        boolean onServer = event.includeServer();
        boolean onClient = event.includeClient();

        ModBlockTags blockTags = new ModBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(onServer, blockTags);
        generator.addProvider(onServer, new ModItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(onServer, new ModRecipes(generator));
        generator.addProvider(onServer, new ModLootTables(generator));

        generator.addProvider(onClient, new ModBlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(onClient, new ModItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(onClient, new ModLanguageProvider(generator, "en_us"));
    }
}
