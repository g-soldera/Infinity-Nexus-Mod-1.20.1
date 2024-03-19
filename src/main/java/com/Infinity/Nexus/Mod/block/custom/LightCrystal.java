package com.Infinity.Nexus.Mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LightCrystal extends ButtonBlock {
    protected static final VoxelShape CEILING_AABB_X =         Block.box(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape PRESSED_CEILING_AABB_X = Block.box(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape CEILING_AABB_Z =         Block.box(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape PRESSED_CEILING_AABB_Z = Block.box(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape FLOOR_AABB_X =           Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape PRESSED_FLOOR_AABB_X =   Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape FLOOR_AABB_Z =           Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape PRESSED_FLOOR_AABB_Z =   Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape NORTH_AABB =             Block.box(6.0D, 6.0D, 12.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape PRESSED_NORTH_AABB =     Block.box(6.0D, 6.0D, 12.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape SOUTH_AABB =             Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 4.0D);
    protected static final VoxelShape PRESSED_SOUTH_AABB =     Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 4.0D);
    protected static final VoxelShape WEST_AABB =              Block.box(12.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    protected static final VoxelShape PRESSED_WEST_AABB =      Block.box(12.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    protected static final VoxelShape EAST_AABB =              Block.box(0.0D, 6.0D, 6.0D, 4.0D, 10.0D, 10.0D);
    protected static final VoxelShape PRESSED_EAST_AABB =      Block.box(0.0D, 6.0D, 6.0D, 4.0D, 10.0D, 10.0D);
    public LightCrystal(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
        super(properties, type, ticksToStayPressed, arrowsCanPress);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(POWERED);
        switch ((AttachFace)pState.getValue(FACE)) {
            case FLOOR:
                if (direction.getAxis() == Direction.Axis.X) {
                    return flag ? PRESSED_FLOOR_AABB_X : FLOOR_AABB_X;
                }

                return flag ? PRESSED_FLOOR_AABB_Z : FLOOR_AABB_Z;
            case WALL:
                VoxelShape voxelshape;
                switch (direction) {
                    case EAST:
                        voxelshape = flag ? PRESSED_EAST_AABB : EAST_AABB;
                        break;
                    case WEST:
                        voxelshape = flag ? PRESSED_WEST_AABB : WEST_AABB;
                        break;
                    case SOUTH:
                        voxelshape = flag ? PRESSED_SOUTH_AABB : SOUTH_AABB;
                        break;
                    case NORTH:
                    case UP:
                    case DOWN:
                        voxelshape = flag ? PRESSED_NORTH_AABB : NORTH_AABB;
                        break;
                    default:
                        throw new IncompatibleClassChangeError();
                }

                return voxelshape;
            case CEILING:
            default:
                if (direction.getAxis() == Direction.Axis.X) {
                    return flag ? PRESSED_CEILING_AABB_X : CEILING_AABB_X;
                } else {
                    return flag ? PRESSED_CEILING_AABB_Z : CEILING_AABB_Z;
                }
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }
}
