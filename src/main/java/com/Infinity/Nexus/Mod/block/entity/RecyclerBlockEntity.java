package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Mod.block.custom.Recycler;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.PressHandler;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.RecyclerHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerMenu;
import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class RecyclerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 1 -> false;
                case 2, 3, 4, 5 -> ModUtils.isUpgrade(stack);
                case 6 -> ModUtils.isComponent(stack);

                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int[] UPGRADE_SLOTS = {2, 3, 4, 5};
    private static final int COMPONENT_SLOT = 6;
    private static final int capacity = 60000;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(capacity, 32000) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }
            @Override
            public boolean canExtract() {
                return true;
            }
        };
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.UP), RecyclerHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.DOWN), RecyclerHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.NORTH), RecyclerHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.SOUTH), RecyclerHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.EAST), RecyclerHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> RecyclerHandler.extract(i, Direction.WEST), RecyclerHandler::insert)));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;
    private static final int ENERGY_REQ = 1500;

    public RecyclerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RECYCLER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> RecyclerBlockEntity.this.progress;
                    case 1 -> RecyclerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> RecyclerBlockEntity.this.progress = pValue;
                    case 1 -> RecyclerBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Recycler.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }


        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }


    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.recycler").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RecyclerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("recycler.progress", progress);
        pTag.putInt("recycler.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("recycler.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("recycler.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;
        pLevel.setBlock(pPos, pState.setValue(Recycler.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }

        if(getMachineLevel() <= 0){
            return;
        }

        if (!hasEnoughEnergy()) {
            return;
        }
        if(itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()){
            return;
        }
        setMaxProgress(machineLevel);
        pLevel.setBlock(pPos, pState.setValue(Recycler.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void craftItem() {

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
        int chance = new Random().nextInt(100);
        if(chance < 5){
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(ModItemsProgression.RESIDUAL_MATTER.get(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + 1));
        }
        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) < 1){
            level.playSound(null, this.getBlockPos(), SoundEvents.GRAVEL_FALL, SoundSource.BLOCKS, 0.3f, 1.0f);
        }
    }

    private void increaseCraftingProgress() {
        progress ++;
    }
    private void setMaxProgress(int machineLevel) {
        int duration = 130;
        int halfDuration = duration / 2;
        int speedReduction = halfDuration / 16;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS); //16

        int reducedDuration = speed * speedReduction;
        int reducedLevel = machineLevel * (halfDuration / 8);
        duration = duration - reducedDuration - reducedLevel;

        maxProgress = Math.max(duration, ConfigUtils.assembler_minimum_tick);
    }
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }
    private void resetProgress() {
        progress = 0;
    }


    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }
    public static int getOuputSlot() {
        return OUTPUT_SLOT;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}