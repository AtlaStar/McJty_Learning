package com.thomasglasser.mcjtylearning.server.blocks.entities;

import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.init.config.PowerGeneratorConfig;
import com.thomasglasser.mcjtylearning.tools.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class PowerGeneratorBlockEntity extends BlockEntity
{
    private final ItemStackHandler itemStackHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);

    private final CustomEnergyStorage energyStorage = createEnergy();
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    private int counter;

    public PowerGeneratorBlockEntity(BlockPos pPos, BlockState pState)
    {
        super(Registration.POWER_GENERATOR_BLOCK_ENTITY.get(), pPos, pState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    public void tickServer()
    {
        if (counter > 0)
        {
            energyStorage.addEnergy(PowerGeneratorConfig.POWER_GENERATOR_GENERATE.get());
            counter--;
            setChanged();
        }

        if (counter <= 0)
        {
            ItemStack stack = itemStackHandler.getStackInSlot(0);
            int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
            if (burnTime > 0)
            {
                itemStackHandler.extractItem(0, 1, false);
                counter = burnTime;
                setChanged();
            }
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0)
        {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0), Block.UPDATE_ALL);
        }

        sendOutPower();
    }

    private void sendOutPower()
    {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0)
        {
            for (Direction direction : Direction.values())
            {
                BlockEntity blockEntity = level.getBlockEntity(worldPosition.relative(direction));
                if (blockEntity != null)
                {
                    boolean doContinue = blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), PowerGeneratorConfig.POWER_GENERATOR_SEND.get()), false);
                            capacity.addAndGet(-received);
                            energyStorage.consumeEnergy(received);
                            setChanged();
                            return capacity.get() > 0;
                        } else {
                            return true;
                        }
                    }).orElse(true);

                    if(!doContinue)
                    {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("Inventory"))
        {
            itemStackHandler.deserializeNBT(pTag.getCompound("Inventory"));
        }
        if (pTag.contains("Energy"))
        {
            itemStackHandler.deserializeNBT(pTag.getCompound("Energy"));
        }
        if (pTag.contains("Info"))
        {
            counter = pTag.getCompound("Info").getInt("Counter");
        }
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemStackHandler.serializeNBT());
        pTag.put("Energy", energyStorage.serializeNBT());

        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Counter", counter);
        pTag.put("Info", infoTag);
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(1)
        {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0)
                {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private CustomEnergyStorage createEnergy()
    {
        return new CustomEnergyStorage(PowerGeneratorConfig.POWER_GENERATOR_CAPACITY.get(), 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> pCap, @Nullable Direction pSide)
    {
        if (pCap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return handler.cast();
        }
        if (pCap == CapabilityEnergy.ENERGY)
        {
            return energy.cast();
        }
        return super.getCapability(pCap, pSide);
    }
}
