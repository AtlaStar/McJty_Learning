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
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(Elements.POWER_GENERATOR_CONTAINER.get(), PowerGeneratorScreen::new);

            PowerGeneratorRenderer.register();
        });

        MinecraftForge.EVENT_BUS.addListener(KeyInputHandler::onKeyInput);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(RegisterGeometryLoaders event)
    {
        event.register(GeneratorModelLoader.GENERATOR_LOADER.getPath(), new GeneratorModelLoader());
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
        if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS))
        {
            return;
        }
        event.addSprite(PowerGeneratorRenderer.HALO);
    }

    @SubscribeEvent
    public static void onRegisterGUIOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "mana", ManaOverlay.MANA_HUD);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event)
    {
        KeyBindings.init(event);
    }
}