package com.thomasglasser.mcjtylearning.server.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana
{
    private int mana;

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public void addMana(int mana)
    {
        this.mana += mana;
    }

    public void copyMana(PlayerMana source)
    {
        mana = source.mana;
    }

    public void saveNBT(CompoundTag tag)
    {
        tag.putInt("mana", mana);
    }

    public void loadNBT(CompoundTag tag)
    {
        mana = tag.getInt("mana");
    }
}
