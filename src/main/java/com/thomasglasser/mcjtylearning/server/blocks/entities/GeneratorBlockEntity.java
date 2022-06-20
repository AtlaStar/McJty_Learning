package com.thomasglasser.mcjtylearning.server.blocks.entities;

import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.init.config.GeneratorConfig;
import com.thomasglasser.mcjtylearning.tools.CustomEnergyStorage;
import com.thomasglasser.mcjtylearning.tools.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GeneratorBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> GENERATING_BLOCK = new ModelProperty<>();

    public static final ModelProperty<Boolean> GENERATING = new ModelProperty<>();
    public static final ModelProperty<Boolean> COLLECTING = new ModelProperty<>();
    public static final ModelProperty<Boolean> ACTUALLY_GENERATING = new ModelProperty<>();

    public static final int INPUT_SLOTS = 5;
    public static final int OUTPUT_SLOTS = 1;

    private boolean generating = false;
    private boolean collecting = false;
    private boolean actuallyGenerating = false;

    private BlockState generatingBlock;

    private int collectingTicker = 0;
    private AABB collectingBox = null;

    private int generatingCounter = 0;

    private final ItemStackHandler inputItems = createInputItemHandler();
    private final LazyOptional<IItemHandler> inputItemHandler = LazyOptional.of(() -> inputItems);

    private final ItemStackHandler outputItems = createOutputItemHandler();
    private final LazyOptional<IItemHandler> outputItemHandler = LazyOptional.of(() -> outputItems);

    private final LazyOptional<IItemHandler> combinedItemHandler = LazyOptional.of(this::createCombinedItemHandler);

    private final CustomEnergyStorage energy = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energy);

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.GENERATOR_BLOCK_ENTITY.get(), pos, state);
    }

    public boolean isGenerating() {
        return generating;
    }

    public void setGenerating(boolean generating) {
        this.generating = generating;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public boolean isCollecting() {
        return collecting;
    }

    public void setCollecting(boolean collecting) {
        this.collecting = collecting;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public void setGeneratingBlock(BlockState generatingBlock)
    {
        if (generatingBlock.is(Tags.Blocks.ORES))
        {
            this.generatingBlock = generatingBlock;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public void tickServer()
    {
        if (collecting)
        {
            collectingTicker--;
            if (collectingTicker <= 0)
            {
                collectingTicker = GeneratorConfig.COLLECTING_DELAY.get();
                collectItems();
            }
        }

        boolean areWeGenerating = false;
        if (generating)
        {
            areWeGenerating = generateOres();
        }
        if (areWeGenerating != actuallyGenerating)
        {
            actuallyGenerating = areWeGenerating;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private void collectItems()
    {
        if (collectingBox == null)
        {
            collectingBox = new AABB(getBlockPos()).inflate(1);
        }

        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, collectingBox,
                itemEntity ->
                {
                    ItemStack item = itemEntity.getItem();
                    return item.is(Tags.Items.INGOTS);
                });

        for (ItemEntity itemEntity : entities)
        {
            ItemStack item = itemEntity.getItem();
            ItemStack remainder = ItemHandlerHelper.insertItem(inputItems, item, false);

            if (remainder.isEmpty())
            {
                itemEntity.kill();
            }
            else
            {
                itemEntity.setItem(remainder);
            }
        }
    }

    private boolean generateOres()
    {
        if (generatingBlock == null)
        {
            return false;
        }

        if (energy.getEnergyStored() < GeneratorConfig.ENERGY_GENERATE.get())
        {
            return false;
        }

        boolean areWeGenerating = false;

        for (int i = 0; i < inputItems.getSlots(); i++)
        {
            ItemStack item = inputItems.getStackInSlot(i);

            if (!item.isEmpty())
            {
                energy.consumeEnergy(GeneratorConfig.ENERGY_GENERATE.get());
                item = item.copy();
                item.shrink(1);
                inputItems.setStackInSlot(i, item);
                generatingCounter++;
                areWeGenerating = true;
                setChanged();
                if (generatingCounter >= GeneratorConfig.INGOTS_PER_ORE.get())
                {
                    generatingCounter = 0;
                    ItemStack remaining = ItemHandlerHelper.insertItem(outputItems, new ItemStack(generatingBlock.getBlock().asItem()), false);
                    Tools.spawnInWorld(level, worldPosition, remaining);
                }
            }
        }
        return areWeGenerating;
    }

    private ItemStackHandler createInputItemHandler() {
        return new ItemStackHandler(INPUT_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }
        };
    }

    private ItemStackHandler createOutputItemHandler() {
        return new ItemStackHandler(OUTPUT_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    private IItemHandler createCombinedItemHandler() {
        return new CombinedInvWrapper(inputItems, outputItems) {
            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }
        };
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(GeneratorConfig.ENERGY_CAPACITY.get(), GeneratorConfig.ENERGY_RECEIVE.get()) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveClientData(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loadClientData(tag);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        boolean oldGenerating = generating;
        boolean oldCollecting = collecting;
        boolean oldActuallyGenerating = actuallyGenerating;
        BlockState oldGeneratingBlock = generatingBlock;

        CompoundTag tag = pkt.getTag();
        handleUpdateTag(tag);
        if (oldGenerating != generating || oldCollecting != collecting || oldActuallyGenerating != actuallyGenerating || !Objects.equals(generatingBlock, oldGeneratingBlock)) {
            ModelDataManager.requestModelDataRefresh(this);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputItemHandler.invalidate();
        outputItemHandler.invalidate();
        combinedItemHandler.invalidate();
        energyHandler.invalidate();
    }

    @Override
    public @NotNull IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(GENERATING_BLOCK, generatingBlock)
                .withInitial(GENERATING, generating)
                .withInitial(ACTUALLY_GENERATING, actuallyGenerating)
                .withInitial(COLLECTING, collecting)
                .build();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveClientData(pTag);
        pTag.put("Inventory", inputItems.serializeNBT());
        pTag.put("Energy", energy.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Generating", generatingCounter);
    }

    private void saveClientData(CompoundTag tag) {
        CompoundTag infoTag = tag.getCompound("Info");
        tag.put("Info", infoTag);
        infoTag.putBoolean("generating", generating);
        infoTag.putBoolean("collecting", collecting);
        tag.putBoolean("actuallyGenerating", actuallyGenerating);

        if (generatingBlock != null) {
            infoTag.put("block", NbtUtils.writeBlockState(generatingBlock));
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadClientData(pTag);
        if (pTag.contains("Inventory")) {
            inputItems.deserializeNBT(pTag.getCompound("Inventory"));
        }
        if (pTag.contains("Energy")) {
            energy.deserializeNBT(pTag.get("Energy"));
        }
        if (pTag.contains("Info"))
        {
            generatingCounter = pTag.getCompound("Info").getInt("Generating");
        }
    }

    private void loadClientData(CompoundTag tag) {
        if (tag.contains("Info"))
        {
            CompoundTag infoTag = tag.getCompound("Info");
            generating = infoTag.getBoolean("generating");
            collecting = infoTag.getBoolean("collecting");

            if (infoTag.contains("block")) {
                generatingBlock = NbtUtils.readBlockState(infoTag.getCompound("block"));
            }
        }
        actuallyGenerating = tag.getBoolean("actuallyGenerating");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return combinedItemHandler.cast();
            } else if (side == Direction.DOWN) {
                return outputItemHandler.cast();
            } else {
                return inputItemHandler.cast();
            }
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyHandler.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }
}
