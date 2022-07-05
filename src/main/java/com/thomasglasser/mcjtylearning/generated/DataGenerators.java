package com.thomasglasser.mcjtylearning.generated;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.sun.jna.platform.unix.Resource;
import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import com.thomasglasser.mcjtylearning.server.worldgen.ores.ModOres;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        RegistryAccess registryAccess = RegistryAccess.builtinCopy();
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        boolean onServer = event.includeServer();
        boolean onClient = event.includeClient();

        //Server
        ModBlockTags blockTags = new ModBlockTags(generator, existingFileHelper);
        generator.addProvider(onServer, blockTags);
        generator.addProvider(onServer, new ModItemTags(generator, blockTags, existingFileHelper));
        generator.addProvider(onServer, new ModRecipes(generator));
        generator.addProvider(onServer, new ModLootTables(generator));

        //Client
        generator.addProvider(onClient, new ModBlockStates(generator, existingFileHelper));
        generator.addProvider(onClient, new ModItemModels(generator, existingFileHelper));
        generator.addProvider(onClient, new ModLanguageProvider(generator, "en_us"));

        //Worldgen
        ResourceLocation mysteriousOreGenerationRL = new ResourceLocation(McJtyLearning.MODID, "mysterious_ore_generation");

        Map<ResourceLocation, PlacedFeature> map = Map.of(mysteriousOreGenerationRL, Elements.MYSTERIOUS_ORE_PLACED.get());

        JsonCodecProvider provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, McJtyLearning.MODID, registryOps, Registry.PLACED_FEATURE_REGISTRY, map);
        generator.addProvider(onServer, provider);
    }
}
