package com.thomasglasser.mcjtylearning.client.bindings;

import com.thomasglasser.mcjtylearning.init.Messages;
import com.thomasglasser.mcjtylearning.network.GatherManaPacket;
import net.minecraftforge.client.event.InputEvent;

public class KeyInputHandler
{
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.gatherManaKeyMapping.consumeClick())
        {
            Messages.sendToServer(new GatherManaPacket());
        }
    }
}
