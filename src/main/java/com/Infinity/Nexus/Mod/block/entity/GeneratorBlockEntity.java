package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.block.custom.Generator;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorMenu;
import com.Infinity.Nexus.Mod.utils.ModEnergyStorage;
import com.Infinity.Nexus.Mod.utils.ModUtils;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import java.util.Objects;
import java.util.Optional;

public class GeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int[] UPGRADE_SLOTS = {1, 2, 3, 4};
    private static final int COMPONENT_SLOT = 5;
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
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;
    private int fuel = 0;
    private int ENERGY_TRANSFER;


    public GeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GeneratorBlockEntity.this.progress;
                    case 1 -> GeneratorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GeneratorBlockEntity.this.progress = pValue;
                    case 1 -> GeneratorBlockEntity.this.maxProgress = pValue;
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
                Direction localDir = this.getBlockState().getValue(Generator.FACING);

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
        return Component.literal(Component.translatable("block.infinity_nexus_mod.generator").getString()+" LV "+getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GeneratorMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("generator.progress", progress);
        pTag.putInt("generator.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("generator.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("generator.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;
        pLevel.setBlock(pPos, pState.setValue(Generator.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }

        if(getMachineLevel() <= 0){
            return;
        }

        findEnergyCap();

        if(!canInsertEnergy(this)){
            return;
        }

        if (fuel <= 0){
            if(!hasFuel(this)){
                return;
            }
            setMaxTransfer();
            removeFuel();
        }
        pLevel.setBlock(pPos, pState.setValue(Generator.LIT, machineLevel+8), 3);
        insertEnergy();
        increaseCraftingProgress();
        decreaseFuel();
        setChanged(pLevel, pPos, pState);


        if (hasProgressFinished()) {
            resetProgress();
        }
    }

    private void setMaxTransfer() {
        int energy = (getMachineLevel() + (ModUtils.getSpeed(this.itemHandler, UPGRADE_SLOTS) + ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS))) * 30;
        ENERGY_TRANSFER = energy <= 0 ? 30 : energy;
    }

    private void findEnergyCap() {
        try{
            Level level = getLevel();
            BlockPos pos = getBlockPos();

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                assert level != null;
                BlockEntity neighborBlockEntity = level.getBlockEntity(neighborPos);
                if (neighborBlockEntity != null) {
                    neighborBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy ->{
                        int amount = Math.min(ENERGY_STORAGE.getEnergyStored(), ENERGY_TRANSFER);
                        if(energy.canReceive() && (energy.getMaxEnergyStored() - energy.getEnergyStored()) >= amount){
                            energy.receiveEnergy(amount, false);
                            ENERGY_STORAGE.extractEnergy(amount, false);
                        }
                    });
                }
            }
        } catch (Exception e){
            System.out.println("&f[INM&f]&4: Failed to find energy cap.");
            e.printStackTrace();
        }
    }

    private void decreaseFuel() {
        fuel -= ((getMachineLevel() + 1) + (ModUtils.getSpeed(this.itemHandler, UPGRADE_SLOTS)));
    }

    private void removeFuel() {
        if(hasFuel(this)){
            this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        }
    }

    private void insertEnergy() {
        this.ENERGY_STORAGE.receiveEnergy(ENERGY_TRANSFER, false);
    }

    private boolean canInsertEnergy(GeneratorBlockEntity generatorBlockEntity) {
        return (generatorBlockEntity.ENERGY_STORAGE.getEnergyStored() + ENERGY_TRANSFER) < generatorBlockEntity.ENERGY_STORAGE.getMaxEnergyStored();
    }

    private boolean hasFuel(GeneratorBlockEntity generatorBlockEntity) {
        ItemStack fuel = generatorBlockEntity.itemHandler.getStackInSlot(INPUT_SLOT);
        int burnTime = ForgeHooks.getBurnTime(fuel, null);
        setMaxProgress(burnTime);
        this.fuel = burnTime;
        return burnTime > 0;
    }


    private void setMaxProgress(int burnTime) {
        maxProgress = burnTime;
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
    private void increaseCraftingProgress() {
        progress += ((getMachineLevel() + 1) + (ModUtils.getSpeed(this.itemHandler, UPGRADE_SLOTS)));
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
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
        {
            if (this.itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()) {
                this.itemHandler.setStackInSlot(COMPONENT_SLOT, itemStack.copy());
                player.getMainHandItem().shrink(1);
                this.setChanged();
            }else{
                ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
                this.itemHandler.setStackInSlot(COMPONENT_SLOT, itemStack.copy());
                player.getMainHandItem().shrink(1);
                this.setChanged();
                ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), component);
                this.level.addFreshEntity(itemEntity);
            }
            assert level != null;
            level.playSound(null, this.getBlockPos(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        {
            for(int i = 0; i < UPGRADE_SLOTS.length; i++ ){
                if (this.itemHandler.getStackInSlot(UPGRADE_SLOTS[i]).isEmpty()) {
                    this.itemHandler.setStackInSlot(UPGRADE_SLOTS[i], itemStack.copy());
                    player.getMainHandItem().shrink(1);
                    assert level != null;
                    level.playSound(null, this.getBlockPos(), SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    this.setChanged();
                }
            }
        }
    }
}