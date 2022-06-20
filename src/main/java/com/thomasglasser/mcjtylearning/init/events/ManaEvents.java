package com.thomasglasser.mcjtylearning.init.events;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.server.mana.ManaManager;
import com.thomasglasser.mcjtylearning.server.mana.PlayerMana;
import com.thomasglasser.mcjtylearning.server.mana.PlayerManaProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ManaEvents
{
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            if (!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent())
            {
                event.addCapability(new ResourceLocation(McJtyLearning.MODID, "playermana"), new PlayerManaProvider());
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(oldStore ->
            {
                event.getPlayer().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(newStore ->
                {
                    newStore.copyMana(oldStore);
                });
            });
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(PlayerMana.class);
    }

    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.world.isClientSide || event.phase == TickEvent.Phase.START)
        {
            return;
        }

        ManaManager manager = ManaManager.get(event.world);
        manager.tick(event.world);
    }
}
