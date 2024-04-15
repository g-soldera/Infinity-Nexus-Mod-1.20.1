package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.DisplayBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class Display extends BaseEntityBlock {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 4);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public Display(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        return switch (pState.getValue(LIT)) {
            case 1 : if(facing == Direction.NORTH || facing == Direction.SOUTH){
                yield Stream.of(
                        Block.box(2, 0, 6, 14, 3, 10),
                        Block.box(7, 0, 7.7, 9, 15, 8.3)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }else{
                yield Stream.of(
                        Block.box(6, 0, 2, 10, 3, 14),
                        Block.box(7.7, 0, 7, 8.3, 15, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }

            case 2 : if(facing == Direction.NORTH){
                yield Stream.of(
                        Block.box(2, 0, 4, 14, 2, 8),
                        Block.box(3, 3, 7, 13, 4, 8),
                        Block.box(3, 4, 8, 13, 5, 9),
                        Block.box(3, 5, 9, 13, 6, 10),
                        Block.box(3, 6, 10, 13, 7, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }else if (facing == Direction.SOUTH){
                yield Stream.of(
                        Block.box(2, 0, 8, 14, 2, 12),
                        Block.box(3, 3, 8, 13, 4, 9),
                        Block.box(3, 4, 7, 13, 5, 8),
                        Block.box(3, 5, 6, 13, 6, 7),
                        Block.box(3, 6, 5, 13, 7, 6)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }else if (facing == Direction.EAST){
                yield Stream.of(
                        Block.box(8, 0, 2, 12, 2, 14),
                        Block.box(8, 3, 3, 9, 4, 13),
                        Block.box(7, 4, 3, 8, 5, 13),
                        Block.box(6, 5, 3, 7, 6, 13),
                        Block.box(5, 6, 3, 6, 7, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }else{
                yield Stream.of(
                        Block.box(4, 0, 2, 8, 2, 14),
                        Block.box(7, 3, 3, 8, 4, 13),
                        Block.box(8, 4, 3, 9, 5, 13),
                        Block.box(9, 5, 3, 10, 6, 13),
                        Block.box(10, 6, 3, 11, 7, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            }

            default : yield Block.box(4, 0, 4, 12, 2, 12);
        };
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            setStack(pPlayer.getMainHandItem().copy(), (DisplayBlockEntity) pLevel.getBlockEntity(pPos), pPlayer);
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private void setStack(ItemStack pStack, DisplayBlockEntity pBlockEntity, Player player) {
        pBlockEntity.setRenderStack(pStack, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DisplayBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.DISPLAY_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}