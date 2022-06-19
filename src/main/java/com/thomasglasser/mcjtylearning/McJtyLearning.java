package com.thomasglasser.mcjtylearning;

import com.mojang.logging.LogUtils;
import com.thomasglasser.mcjtylearning.init.Configuration;
import com.thomasglasser.mcjtylearning.init.ModSetup;
import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.init.ClientSetup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(McJtyLearning.MODID)
public class McJtyLearning
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    //Save MODID as String
    public static final String MODID = "mcjtylearning";

    public McJtyLearning()
    {
        Registration.init();
        Configuration.register();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::init));
    }
}
