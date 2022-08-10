package com.thomasglasser.mcjtylearning.server.blocks;

import com.thomasglasser.mcjtylearning.server.world.dimensions.Dimensions;
import com.thomasglasser.mcjtylearning.tools.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PortalBlock extends Block
{
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, .8, 1);

    public PortalBlock()
    {
        super(Properties.of(Material.PORTAL).sound(SoundType.METAL).strength(-1, 3600000).noLootTable());
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof ServerPlayer player)
        {
            if (pLevel.dimension().equals(Dimensions.MYSTERIOUS))
            {
                teleportTo(player, pPos.north(), Level.OVERWORLD);
            }
            else
            {
                teleportTo(player, pPos.north(), Dimensions.MYSTERIOUS);
            }
        }
    }

    public void teleportTo(ServerPlayer player, BlockPos pos, ResourceKey<Level> id)
    {
        ServerLevel level = player.getServer().getLevel(id);
        Tools.teleport(player, level, pos, true);
    }
}
