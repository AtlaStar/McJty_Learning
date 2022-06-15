package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.client.models.loaders.GeneratorModelLoader;
import com.thomasglasser.mcjtylearning.client.renderers.PowerGeneratorRenderer;
import com.thomasglasser.mcjtylearning.client.screens.PowerGeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.POWER_GENERATOR_CONTAINER.get(), PowerGeneratorScreen::new);
            ItemBlockRenderTypes.setRenderLayer(Registration.POWER_GENERATOR.get(), RenderType.translucent());
            PowerGeneratorRenderer.register();
        });
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(GeneratorModelLoader.GENERATOR_LOADER, new GeneratorModelLoader());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event)
    {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS))
        {
            return;
        }
        event.addSprite(PowerGeneratorRenderer.HALO);
    }
}