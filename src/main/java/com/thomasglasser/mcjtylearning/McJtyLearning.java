package com.thomasglasser.mcjtylearning;

import com.mojang.logging.LogUtils;
import com.thomasglasser.mcjtylearning.init.Configuration;
import com.thomasglasser.mcjtylearning.init.ModSetup;
import com.thomasglasser.mcjtylearning.init.Elements;
import com.thomasglasser.mcjtylearning.init.ClientSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

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
        ModSetup.setup();
        Elements.init();
        Configuration.register();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::init));
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
