package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.items.custom.ComponentItem;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Mod.block.custom.MatterCondenser;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.GeneratorHandler;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.MatterCondenserHandler;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserMenu;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MatterCondenserBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ModItemsProgression.RESIDUAL_MATTER.get());
                case 1 -> false;
                case 2 -> ModUtils.isComponent(stack);

                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int COMPONENT_SLOT = 2;
    private static final int CAPACITY = 2147483640;
    private static final int TRANSFER = 2147483640;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(CAPACITY, TRANSFER/10) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }

        };
    }

    private static final int ENERGY_REQ = 1;
    private static final int EXTRATED_ENERGY = CAPACITY / 5000;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.UP), MatterCondenserHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.DOWN), MatterCondenserHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.NORTH), MatterCondenserHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.SOUTH), MatterCondenserHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.EAST), MatterCondenserHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MatterCondenserHandler.extract(i, Direction.WEST), MatterCondenserHandler::insert)));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = CAPACITY / 20;
    private int catalystLevel = 0;
    private int amplifier = 0;


    public MatterCondenserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MATTER_CONDENSER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MatterCondenserBlockEntity.this.progress;
                    case 1 -> MatterCondenserBlockEntity.this.maxProgress;
                    case 2 -> MatterCondenserBlockEntity.this.catalystLevel;
                    case 3 -> MatterCondenserBlockEntity.this.amplifier;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MatterCondenserBlockEntity.this.progress = pValue;
                    case 1 -> MatterCondenserBlockEntity.this.maxProgress = pValue;
                    case 2 -> MatterCondenserBlockEntity.this.catalystLevel = pValue;
                    case 3 -> MatterCondenserBlockEntity.this.amplifier = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
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
                Direction localDir = this.getBlockState().getValue(MatterCondenser.FACING);

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
        ItemStack catalystStack = new ItemStack(ModItemsProgression.RESIDUAL_MATTER.get());
        catalystStack.setCount(catalystLevel);
        ItemEntity catalyst = new ItemEntity( level, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, catalystStack);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        level.addFreshEntity(catalyst);
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.matter_condenser").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CondenserMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public int getProgress() {
        return this.progress;
    }
    public int getMaxProgress() {
        return this.maxProgress;
    }
    public int getCatalystLevel() {
        return this.catalystLevel;
    }
    public int getAmplifier() {
        return this.amplifier;
    }
    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("matter_condenser.progress", progress);
        pTag.putInt("matter_condenser.catalyst_level", catalystLevel);
        pTag.putInt("matter_condenser.amplifier", amplifier);
        pTag.putInt("matter_condenser.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("matter_condenser.progress");
        catalystLevel = pTag.getInt("matter_condenser.catalyst_level");
        amplifier = pTag.getInt("matter_condenser.amplifier");
        ENERGY_STORAGE.setEnergy(pTag.getInt("matter_condenser.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }
        increaseCatalystLevel();

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1;
        pLevel.setBlock(pPos, pState.setValue(MatterCondenser.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }
        if (!hasEnoughEnergy()) {
            return;
        }

        if(!hasFreeSlot()){
            return;
        }

        if(!(itemHandler.getStackInSlot(COMPONENT_SLOT).getItem() instanceof ComponentItem)){
            return;
        }

        pLevel.setBlock(pPos, pState.setValue(MatterCondenser.LIT, machineLevel+9), 3);
        increaseCraftingProgress(machineLevel);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            resetProgress();
        }
    }
    private boolean hasFreeSlot() {
        return canInsertItemIntoOutputSlot(ModItemsProgression.UNSTABLE_MATTER.get()) && canInsertAmountIntoOutputSlot(1);
    }

    private void increaseCatalystLevel() {
        if(itemHandler.getStackInSlot(INPUT_SLOT).is(ModItemsProgression.RESIDUAL_MATTER.get())){
            if(catalystLevel < 500000){
                itemHandler.extractItem(INPUT_SLOT, 1, false);
                catalystLevel++;
            }
        }
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        this.itemHandler.insertItem(OUTPUT_SLOT, new ItemStack(ModItemsProgression.UNSTABLE_MATTER.get()), false);

        level.playSound(null, this.getBlockPos(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.3f, 1.0f);
    }

    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress(int machineLevel) {
        int energyStored = ENERGY_STORAGE.getEnergyStored();

        int extractedEnergy = Math.min(EXTRATED_ENERGY * (machineLevel + 1), energyStored);

        if (energyStored >= extractedEnergy) {

            if(catalystLevel > 0 && amplifier <= 0){
                catalystLevel --;
                amplifier = 20;
            }
            if (amplifier > 0) {
                progress += (int) (extractedEnergy * 1.2f);
                extractedEnergy = (int) (extractedEnergy * 0.8);
                amplifier --;
            }else{
                progress += extractedEnergy;
            }
            extractEnergy(extractedEnergy);
        }
    }
    private void extractEnergy(int extractedEnergy) {
        this.ENERGY_STORAGE.extractEnergy(Math.max(extractedEnergy, 1), false);
    }
    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getOutputSlot() {
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
}