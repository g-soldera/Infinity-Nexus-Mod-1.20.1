package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.MinerBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.item.custom.ComponentItem;
import com.Infinity.Nexus.Mod.item.custom.UpgradeItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Miner extends BaseEntityBlock {

    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 17);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public Miner(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MinerBlockEntity) {
                ((MinerBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            boolean component = pPlayer.getMainHandItem().getItem() instanceof ComponentItem;
            boolean upgrade = pPlayer.getMainHandItem().getItem() instanceof UpgradeItem;

            if(entity instanceof MinerBlockEntity) {
                if(!(component || upgrade)) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (MinerBlockEntity) entity, pPos);
                }else if (component) {
                        ((MinerBlockEntity) entity).setMachineLevel(pPlayer.getMainHandItem(), pPlayer);
                        pPlayer.closeContainer();
                        return InteractionResult.FAIL;

                }else{
                        ((MinerBlockEntity) entity).setUpgradeLevel(pPlayer.getMainHandItem(), pPlayer);
                        pPlayer.closeContainer();
                        return InteractionResult.FAIL;
                }
                ((MinerBlockEntity) entity).resetVerify();
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MinerBlockEntity(pPos, pState);
    }


    public void spawnParticles(BlockState pState, Level pLevel, BlockPos pPos, ParticleOptions source) {
        pLevel.addParticle(source, pPos.getX() + 0.5D, pPos.getY() +0.5D, pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.MINER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        Player player = pPlacer instanceof Player ? (Player) pPlacer : null;
        if (player != null) {
            MinerBlockEntity miner = ((MinerBlockEntity) pLevel.getBlockEntity(pPos));

            // Criar um novo NBT e adicionar o UUID do jogador
            CompoundTag nbt = new CompoundTag();
            nbt.putUUID("ownerUUID", player.getUUID());
            nbt.putInt("ownerNotifyDelay", 0);
            nbt.putInt("ownerNotifyMaxDelay", 600);

            // Definir a NBT do bloco
            miner.setCustomBlockData(nbt);
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
            if (Screen.hasShiftDown()) {
                components.add(Component.translatable("item.infinity_nexus.miner_description"));
            } else {
                components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
            }

            super.appendHoverText(stack, level, components, flag);

    }
}