package com.Infinity.Nexus.Mod.block.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Catwalk extends Block {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 16);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public Catwalk(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int litValue = pState.getValue(LIT);
        Direction direction = pState.getValue(FACING);
        return switch (litValue) {
            case 0 -> switch (direction) {
                case WEST -> Block.box(0, 0, 0, 16, 16, 1);
                case EAST -> Block.box(0, 0, 15, 16, 16, 16);
                case SOUTH -> Block.box(0, 0, 0, 1, 16, 16);
                default -> Block.box(15, 0, 0, 16, 16, 16);
            };
            case 1 -> switch (direction) {
                case WEST, EAST -> Stream.of(Block.box(0, 0, 0, 16, 16, 1), Block.box(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(0, 0, 0, 1, 16, 16), Block.box(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 2 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 0, 0, 16, 16, 1), Block.box(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(0, 0, 15, 16, 16, 16), Block.box(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(0, 0, 0, 16, 16, 1), Block.box(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(0, 0, 15, 16, 16, 16), Block.box(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 3 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 4 -> switch (direction) {
                case WEST, EAST -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 15, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(15, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 5, 10 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(15, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 0, 1, 16, 16), Block.box(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 0, 16, 16, 1), Block.box(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(15, 0, 0, 16, 16, 16), Block.box(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 11, 13 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 3, 1, 4, 4, 15), Block.box(4, 7, 1, 8, 8, 15), Block.box(8, 11, 1, 12, 12, 15), Block.box(12, 15, 1, 16, 16, 15), Block.box(0, 0, 0, 4, 16, 1), Block.box(4, 4, 0, 8, 20, 1), Block.box(8, 8, 0, 12, 24, 1), Block.box(12, 12, 0, 16, 28, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(12, 3, 1, 16, 4, 15), Block.box(8, 7, 1, 12, 8, 15), Block.box(4, 11, 1, 8, 12, 15), Block.box(0, 15, 1, 4, 16, 15), Block.box(12, 0, 15, 16, 16, 16), Block.box(8, 4, 15, 12, 20, 16), Block.box(4, 8, 15, 8, 24, 16), Block.box(0, 12, 15, 4, 28, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(1, 3, 12, 15, 4, 16), Block.box(1, 7, 8, 15, 8, 12), Block.box(1, 11, 4, 15, 12, 8), Block.box(1, 15, 0, 15, 16, 4), Block.box(0, 12, 0, 1, 28, 4), Block.box(0, 8, 4, 1, 24, 8), Block.box(0, 4, 8, 1, 20, 12), Block.box(0, 0, 12, 1, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(1, 3, 0, 15, 4, 4), Block.box(1, 7, 4, 15, 8, 8), Block.box(1, 11, 8, 15, 12, 12), Block.box(1, 15, 12, 15, 16, 16), Block.box(15, 0, 0, 16, 16, 4), Block.box(15, 4, 4, 16, 20, 8), Block.box(15, 8, 8, 16, 24, 12), Block.box(15, 12, 12, 16, 28, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };

            case 12, 14 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 3, 1, 4, 4, 15), Block.box(4, 7, 1, 8, 8, 15), Block.box(8, 11, 1, 12, 12, 15), Block.box(12, 15, 1, 16, 16, 15), Block.box(0, 0, 15, 4, 16, 16), Block.box(4, 4, 15, 8, 20, 16), Block.box(8, 8, 15, 12, 24, 16), Block.box(12, 12, 15, 16, 28, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(12, 3, 1, 16, 4, 15), Block.box(8, 7, 1, 12, 8, 15), Block.box(4, 11, 1, 8, 12, 15), Block.box(0, 15, 1, 4, 16, 15), Block.box(12, 0, 0, 16, 16, 1), Block.box(8, 4, 0, 12, 20, 1), Block.box(4, 8, 0, 8, 24, 1), Block.box(0, 12, 0, 4, 28, 1)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(1, 3, 12, 15, 4, 16), Block.box(1, 7, 8, 15, 8, 12), Block.box(1, 11, 4, 15, 12, 8), Block.box(1, 15, 0, 15, 16, 4), Block.box(15, 16, 0, 16, 28, 4), Block.box(15, 12, 4, 16, 24, 8), Block.box(15, 8, 8, 16, 20, 12), Block.box(15, 0, 12, 16, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(1, 3, 0, 15, 4, 4), Block.box(1, 7, 4, 15, 8, 8), Block.box(1, 11, 8, 15, 12, 12), Block.box(1, 15, 12, 15, 16, 16), Block.box(0, 0, 0, 1, 16, 4), Block.box(0, 4, 4, 1, 20, 8), Block.box(0, 8, 8, 1, 24, 12), Block.box(0, 12, 12, 1, 28, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            case 15, 16 -> switch (direction) {
                case WEST -> Stream.of(Block.box(0, 3, 1, 4, 4, 15), Block.box(4, 7, 1, 8, 8, 15), Block.box(8, 11, 1, 12, 12, 15), Block.box(12, 15, 1, 16, 16, 15)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case EAST -> Stream.of(Block.box(12, 3, 1, 16, 4, 15), Block.box(8, 7, 1, 12, 8, 15), Block.box(4, 11, 1, 8, 12, 15), Block.box(0, 15, 1, 4, 16, 15)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                case SOUTH -> Stream.of(Block.box(1, 3, 12, 15, 4, 16), Block.box(1, 7, 8, 15, 8, 12), Block.box(1, 11, 4, 15, 12, 8), Block.box(1, 15, 0, 15, 16, 4)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                default -> Stream.of(Block.box(1, 3, 0, 15, 4, 4), Block.box(1, 7, 4, 15, 8, 8), Block.box(1, 11, 8, 15, 12, 12), Block.box(1, 15, 12, 15, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
            };
            default -> Block.box(0, 0, 0, 16, 1, 16);
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
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide() && pEntity instanceof ServerPlayer player) {
            int lit[] = {11,12,13,14,15,16};
            if(Arrays.stream(lit).anyMatch(i -> pState.getValue(LIT) == i)){
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 3, false, false));
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if(pPlayer.getOffhandItem().getItem() == Items.STICK) {
                System.out.println(pState.getValue(LIT));
                if (Screen.hasControlDown()) {
                    pPlayer.sendSystemMessage(Component.literal("Rotação Alterada"));
                    pLevel.setBlock(pPos, pState.cycle(FACING), 3);
                }else if(Screen.hasAltDown()){
                    BlockState blockState = pState.getValue(LIT) > 0 ? pState.setValue(LIT, pState.getValue(LIT) - 1) : pState.setValue(LIT, 16);
                    pLevel.setBlock(pPos, blockState, 3);
                }else {
                    pLevel.setBlock(pPos, pState.cycle(LIT), 3);
                    pPlayer.sendSystemMessage(Component.literal("Display Alterado"));
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> components, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.infinity_nexus.catwalk"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(pStack, pLevel, components, pFlag);
    }
}