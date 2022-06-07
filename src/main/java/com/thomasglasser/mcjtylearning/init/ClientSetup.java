package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.client.screens.PowerGeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.POWER_GENERATOR_CONTAINER.get(), PowerGeneratorScreen::new);
            ItemBlockRenderTypes.setRenderLayer(Registration.POWER_GENERATOR.get(), RenderType.translucent());
        });
    }
}