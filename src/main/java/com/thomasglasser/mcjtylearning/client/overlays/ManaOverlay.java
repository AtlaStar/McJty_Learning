package com.thomasglasser.mcjtylearning.client.overlays;

import com.thomasglasser.mcjtylearning.client.mana.ManaData;
import com.thomasglasser.mcjtylearning.init.config.ManaConfig;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ManaOverlay
{
    public static final IIngameOverlay MANA_HUD = ((gui, poseStack, partialTick, width, height) ->
    {
        String toDisplay = ManaData.getPlayerMana() + " / " + ManaData.getChunkMana();
        if (ManaConfig.HUD_X.get() > -1 && ManaConfig.HUD_Y.get() > -1)
            gui.getFont().draw(poseStack, toDisplay, ManaConfig.HUD_X.get(), ManaConfig.HUD_Y.get(), ManaConfig.HUD_COLOR.get());
    });
}
