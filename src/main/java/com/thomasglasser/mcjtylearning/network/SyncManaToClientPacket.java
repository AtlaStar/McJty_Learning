package com.thomasglasser.mcjtylearning.network;

import com.thomasglasser.mcjtylearning.client.mana.ManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncManaToClientPacket
{
    private final int playerMana;
    private final int chunkMana;

    public SyncManaToClientPacket(int playerMana, int chunkMana)
    {
        this.playerMana = playerMana;
        this.chunkMana = chunkMana;
    }

    public SyncManaToClientPacket(FriendlyByteBuf buf)
    {
        playerMana = buf.readInt();
        chunkMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(playerMana);
        buf.writeInt(chunkMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() ->
        {
            ManaData.set(playerMana, chunkMana);
        });

        return true;
    }
}
