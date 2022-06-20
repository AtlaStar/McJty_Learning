package com.thomasglasser.mcjtylearning.network;

import com.thomasglasser.mcjtylearning.server.mana.ManaManager;
import com.thomasglasser.mcjtylearning.server.mana.PlayerManaProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GatherManaPacket
{
    public static final String MESSAGE_NO_MANA = "message.nomana";

    public GatherManaPacket()
    {}

    public GatherManaPacket(FriendlyByteBuf buf)
    {}

    public void toBytes(FriendlyByteBuf buf)
    {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() ->
        {
            ServerPlayer player = ctx.getSender();
            int extracted = ManaManager.get(player.level).extractMana(player.blockPosition());

            if (extracted <= 0)
            {
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_MANA).withStyle(ChatFormatting.RED));
            }
            else
            {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana ->
                {
                    playerMana.addMana(extracted);
                });
            }
        });

        return true;
    }
}
