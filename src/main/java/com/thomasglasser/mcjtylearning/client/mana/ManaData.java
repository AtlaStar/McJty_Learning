package com.thomasglasser.mcjtylearning.client.mana;

public class ManaData
{
    private static int playerMana;
    private static int chunkMana;

    public static void set(int playerMana, int chunkMana)
    {
        ManaData.playerMana = playerMana;
        ManaData.chunkMana = chunkMana;
    }

    public static int getPlayerMana()
    {
        return playerMana;
    }

    public static int getChunkMana()
    {
        return chunkMana;
    }
}
