package com.thomasglasser.mcjtylearning.server.entities.goals;

import com.thomasglasser.mcjtylearning.server.entities.Thief;
import com.thomasglasser.mcjtylearning.tools.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Random;

public class ThiefFindChestGoal extends MoveToBlockGoal
{
    private final Thief thief;
    private final Random random = new Random();

    private int stealingCounter = 20;

    public ThiefFindChestGoal(Thief mob, double speedModifier)
    {
        super(mob, speedModifier, 16);
        this.thief = mob;
    }

    @Override
    public void stop() {
        super.stop();
        thief.setStealing(false);
        BlockEntity blockEntity = mob.level.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity)
        {
            mob.level.blockEvent(blockPos, blockEntity.getBlockState().getBlock(), 1, 0);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget())
        {
            BlockEntity blockEntity = mob.level.getBlockEntity(blockPos);
            if (blockEntity instanceof ChestBlockEntity chest)
            {
                if (thief.isStealing())
                {
                    stealingCounter--;
                    if (stealingCounter <= 0)
                    {
                        stealingCounter = 20;
                        ItemStack stack = extractRandomItem(chest);
                        if (!stack.isEmpty())
                        {
                            Tools.spawnInWorld(mob.level, blockPos.above(), stack);
                        }
                    }
                }
                else
                {
                    mob.level.blockEvent(blockPos, blockEntity.getBlockState().getBlock(), 1, 1);
                    stealingCounter = 20;
                    thief.setStealing(true);
                }
            }
        }
    }

    private ItemStack extractRandomItem(BlockEntity blockEntity)
    {
        return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).map(handler ->
        {
            for (int i = 0; i < handler.getSlots(); i++)
            {
                ItemStack stack = handler.getStackInSlot(i);
                if (!stack.isEmpty())
                {
                    if (random.nextFloat() < .3f)
                    {
                        return handler.extractItem(i, 1, false);
                    }
                }
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.isEmptyBlock(pPos.above())) return false;
        BlockState blockState = pLevel.getBlockState(pPos);
        return blockState.is(Blocks.CHEST);
    }
}
