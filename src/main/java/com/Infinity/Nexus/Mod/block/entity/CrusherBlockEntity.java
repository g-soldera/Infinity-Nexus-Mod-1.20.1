package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Crusher;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.CrusherHandler;
import com.Infinity.Nexus.Mod.recipe.CrusherRecipes;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
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
import net.minecraft.world.item.Item;
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
import java.util.Optional;

public class CrusherBlockEntity extends BlockEntity implements MenuProvider {
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
        return new ModEnergyStorage(capacity, 640) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }
        };
    }

    private static final int ENERGY_REQ = 32;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.UP), CrusherHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.DOWN), CrusherHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.NORTH), CrusherHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.SOUTH), CrusherHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.EAST), CrusherHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> CrusherHandler.extract(i, Direction.WEST), CrusherHandler::insert)));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;


    public CrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRUSHER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CrusherBlockEntity.this.progress = pValue;
                    case 1 -> CrusherBlockEntity.this.maxProgress = pValue;
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
                Direction localDir = this.getBlockState().getValue(Crusher.FACING);

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
        return Component.translatable("block.infinity_nexus_mod.crusher").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrusherMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("crusher.progress", progress);
        pTag.putInt("crusher.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("crusher.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("crusher.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;
        pLevel.setBlock(pPos, pState.setValue(Crusher.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }


        if (!hasRecipe()) {
            resetProgress();
            return;
        }

        setMaxProgress(machineLevel);
        if (!hasEnoughEnergy()) {
            return;
        }

        pLevel.setBlock(pPos, pState.setValue(Crusher.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractEnergy(this);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem(machineLevel + 1);
            resetProgress();
        }
    }

    private void extractEnergy(CrusherBlockEntity crusherBlockEntity) {
        int energy = getCurrentRecipe().get().getEnergy();
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = crusherBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = ((energy + (machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        crusherBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem(int machineLevel) {
        Optional<CrusherRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        int duplicate = recipe.get().canDuplicate() ? Math.round(result.getCount() * (machineLevel / 2.0F)) : result.getCount();
        this.itemHandler.extractItem(INPUT_SLOT, recipe.get().getInputCount(), false);

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + duplicate));
        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) < 1) {
            level.playSound(null, this.getBlockPos(), SoundEvents.BASALT_BREAK, SoundSource.BLOCKS, 0.3f, 1.0f);
        }
    }

    private boolean hasRecipe() {
        Optional<CrusherRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<CrusherRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(CrusherRecipes.Type.INSTANCE, inventory, this.level);
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
    private void increaseCraftingProgress() {
        progress ++;
    }
    private void setMaxProgress(int machineLevel) {
        int duration = getCurrentRecipe().get().getDuration();
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS);

        duration = duration / Math.max((machineLevel + speed), 1);
        maxProgress = Math.max(duration, 5);
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

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}