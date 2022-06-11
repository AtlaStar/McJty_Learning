package com.thomasglasser.mcjtylearning.blocks;

import com.thomasglasser.mcjtylearning.blocks.entities.GeneratorBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GeneratorBlock extends Block implements EntityBlock
{
    public static final String MESSAGE_GENERATOR = "message.generator";

    private static final VoxelShape SHAPE_DOWN = Shapes.box(8, .2, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_UP = Shapes.box(0, 8, 0, 1, .8, 1);
    private static final VoxelShape SHAPE_NORTH = Shapes.box(0, 8, .2, 1, 1, 1);
    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0, 8, 0, 1, 1, .8);
    private static final VoxelShape SHAPE_WEST = Shapes.box(.2, 8, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_EAST = Shapes.box(8, 0, 0, .8, 1, 1);

    public GeneratorBlock()
    {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
                .noOcclusion()
        );
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable(MESSAGE_GENERATOR).withStyle(ChatFormatting.BLUE));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(BlockStateProperties.FACING))
                {
                    case DOWN -> SHAPE_DOWN;
                    case UP -> SHAPE_UP;
                    case NORTH -> SHAPE_NORTH;
                    case SOUTH -> SHAPE_SOUTH;
                    case WEST -> SHAPE_WEST;
                    case EAST -> SHAPE_EAST;
                };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GeneratorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide())
        {
            return (lvl, pos, state, be) ->
            {
                if (be instanceof GeneratorBlockEntity generator) generator.tickServer();
            };
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof GeneratorBlockEntity generator)
            {
                Direction direction = pHit.getDirection();
                Direction facing = pState.getValue(BlockStateProperties.FACING);
                if (direction == facing)
                {
                    Vec3 hit = pHit.getLocation().subtract(pPos.getX(), pPos.getY(), pPos.getZ());
                    double x = getXFromHit(facing, hit);
                    double y = getYFromHit(facing, hit);

                    if (x < .5 && y > .5)
                    {
                        generator.setCollecting(!generator.isCollecting());
                    }
                    else if (x > .5 && y > .5)
                    {
                        generator.setGenerating(!generator.isGenerating());
                    }
                    else if (x > .5 && y < .5)
                    {
                        ItemStack item = pPlayer.getItemInHand(pHand);
                        if (item.getItem() instanceof BlockItem blockItem)
                        {
                            var blockState = blockItem.getBlock().defaultBlockState();
                            generator.setGeneratingBlock(blockState);
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private double getXFromHit(Direction facing, Vec3 hit)
    {
        return switch (facing)
                {
                    case UP -> hit.z;
                    case DOWN -> 1 - hit.z;
                    case NORTH, SOUTH, WEST, EAST -> hit.y;
                };
    }

    private double getYFromHit(Direction facing, Vec3 hit)
    {
        return switch (facing)
                {
                    case UP, DOWN, NORTH -> 1 - hit.x;
                    case SOUTH -> hit.x;
                    case WEST -> hit.z;
                    case EAST -> 1 - hit.z;
                };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.FACING);
    }
}
