package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.server.entities.Thief;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = McJtyLearning.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    public static void init(FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event)
    {
        event.put(Registration.THIEF.get(), Thief.prepareAttributes().build());
    }
}
