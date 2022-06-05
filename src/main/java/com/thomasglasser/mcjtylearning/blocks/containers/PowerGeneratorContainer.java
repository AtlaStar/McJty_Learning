package com.thomasglasser.mcjtylearning.blocks.containers;

import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.varia.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class PowerGeneratorContainer extends AbstractContainerMenu
{
    private BlockEntity blockEntity;
    private Player player;
    private IItemHandler playerInventory;

    public PowerGeneratorContainer(int windowId, BlockPos pos, Inventory playerInventory, Player player)
    {
        super(Registration.POWER_GENERATOR_CONTAINER.get(), windowId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.player = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null)
        {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 64, 24));
            });
        }

        layoutPlayerInventorySlots(10, 70);
        trackPower();
    }

    private void trackPower()
    {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (pValue & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (pValue << 16));
                });
            }
        });
    }

    public int getEnergy()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, Registration.POWER_GENERATOR.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot != null && slot.hasItem())
        {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();

            if (pIndex == 0)
            {
                if (!this.moveItemStackTo(stack, 1, 37, true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemStack);
            }
            else
            {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0)
                {
                    if (!this.moveItemStackTo(stack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (pIndex < 28)
                {
                    if (this.moveItemStackTo(stack, 28, 37, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (pIndex < 37 && !this.moveItemStackTo(stack, 1, 28, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if (stack.getCount() ==  itemStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, stack);
        }

        return itemStack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
    {
        for (int i = 0; i < amount; i++)
        {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
    {
        for (int j = 0; j < verAmount; j++)
        {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow)
    {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 10);
    }
}
