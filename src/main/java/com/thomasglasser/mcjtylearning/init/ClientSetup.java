package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.client.bindings.KeyBindings;
import com.thomasglasser.mcjtylearning.client.bindings.KeyInputHandler;
import com.thomasglasser.mcjtylearning.client.blocks.models.loaders.GeneratorModelLoader;
import com.thomasglasser.mcjtylearning.client.blocks.renderers.PowerGeneratorRenderer;
import com.thomasglasser.mcjtylearning.client.blocks.screens.PowerGeneratorScreen;
import com.thomasglasser.mcjtylearning.client.entities.models.ThiefModel;
import com.thomasglasser.mcjtylearning.client.entities.renderers.ThiefRenderer;
import com.thomasglasser.mcjtylearning.client.overlays.ManaOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.HOTBAR_ELEMENT;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(Elements.POWER_GENERATOR_CONTAINER.get(), PowerGeneratorScreen::new);

            ItemBlockRenderTypes.setRenderLayer(Elements.POWER_GENERATOR.get(), RenderType.translucent());

            PowerGeneratorRenderer.register();
        });

        MinecraftForge.EVENT_BUS.addListener(KeyInputHandler::onKeyInput);
        KeyBindings.init();

        OverlayRegistry.registerOverlayAbove(HOTBAR_ELEMENT, "mana", ManaOverlay.MANA_HUD);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(GeneratorModelLoader.GENERATOR_LOADER, new GeneratorModelLoader());
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThiefModel.THIEF_LAYER, ThiefModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(Elements.THIEF.get(), ThiefRenderer::new);
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