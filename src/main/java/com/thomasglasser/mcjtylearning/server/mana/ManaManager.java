package com.thomasglasser.mcjtylearning.server.mana;

import com.thomasglasser.mcjtylearning.init.Messages;
import com.thomasglasser.mcjtylearning.init.config.ManaConfig;
import com.thomasglasser.mcjtylearning.network.SyncManaToClientPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ManaManager extends SavedData
{
    private final Map<ChunkPos, Mana> manaMap = new HashMap<>();
    private final Random random = new Random();
    int counter = 0;

    public ManaManager()
    {}

    public ManaManager(CompoundTag tag)
    {
        ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);

        for (Tag t : list)
        {
            CompoundTag manaTag = (CompoundTag) t;
            Mana mana = new Mana(manaTag.getInt("mana"));
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));

            manaMap.put(pos, mana);
        }
    }

    public static ManaManager get(Level level)
    {
        if (level.isClientSide)
        {
            throw new RuntimeException("Don't access this client-side!");
        }

        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag list = new ListTag();

        manaMap.forEach((chunkPos, mana) ->
        {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", chunkPos.x);
            manaTag.putInt("z", chunkPos.z);
            manaTag.putInt("mana", mana.getMana());

            list.add(manaTag);
        });

        pCompoundTag.put("mana", list);
        return pCompoundTag;
    }

    private Mana getManaInternal(BlockPos pos)
    {
        ChunkPos chunkPos = new ChunkPos(pos);
        return manaMap.computeIfAbsent(chunkPos, cp -> new Mana(random.nextInt(ManaConfig.CHUNK_MAX_MANA.get()) + ManaConfig.CHUNK_MIN_MANA.get()));
    }

    public int getMana(BlockPos pos)
    {
        Mana mana = getManaInternal(pos);
        return mana.getMana();
    }

    public int extractMana(BlockPos pos)
    {
        Mana mana = getManaInternal(pos);
        int present = mana.getMana();

        if (present > 0)
        {
            mana.setMana(--present);
            setDirty();
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public void tick(Level level)
    {
        counter--;
        if (counter <= 0)
        {
            counter = 10;

            // TODO: Keep previous data and only send if changed

            level.players().forEach(player ->
            {
                if (player instanceof ServerPlayer serverPlayer)
                {
                    int playerMana = serverPlayer.getCapability(PlayerManaProvider.PLAYER_MANA)
                            .map(PlayerMana::getMana)
                            .orElse(-1);
                    int chunkMana = getMana(serverPlayer.blockPosition());

                    Messages.sendToPlayer(new SyncManaToClientPacket(playerMana, chunkMana), serverPlayer);
                }
            });

            // TODO: Slowly regenerate mana in chunks
        }
    }
}
