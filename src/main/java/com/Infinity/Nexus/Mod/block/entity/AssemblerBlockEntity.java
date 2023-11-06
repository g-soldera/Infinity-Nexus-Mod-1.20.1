package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.block.custom.Assembler;
import com.Infinity.Nexus.Mod.recipe.AssemblerRecipes;
import com.Infinity.Nexus.Mod.screen.assembly.AssemblerMenu;
import com.Infinity.Nexus.Mod.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

import static com.Infinity.Nexus.Mod.block.custom.Assembler.LIT;

public class AssemblerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    private static final int INPUT_SLOT = 7;
    private static final int OUTPUT_SLOT = 8;

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256){
        @Override
        public void onEnergyChanged() {
            setChanged();
        }
    };
    private static final int ENERGY_REQ = 32;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP,    LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))),
                    Direction.DOWN,  LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))),
                    Direction.EAST,  LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))),
                    Direction.WEST,  LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 8, (i, s) -> i <= 7 && canInsert(i))));

    protected final ContainerData data;
    private int progress = 0;
    public int maxProgress = 0;

    public AssemblerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ASSEMBLY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AssemblerBlockEntity.this.progress;
                    case 1 -> AssemblerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AssemblerBlockEntity.this.progress = pValue;
                    case 1 -> AssemblerBlockEntity.this.maxProgress = pValue;
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

        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyStorage.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Assembler.FACING);

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

    public boolean canInsert(int slots) {
        return this.itemHandler.getStackInSlot(slots).getCount() < 1;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
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
        return Component.translatable("block.infinity_nexus_mod.assembly");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AssemblerMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public int getEnergyStorage() {
        return ENERGY_STORAGE.getEnergyStored();
    }
    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("assembly.progress", progress);
        pTag.putInt("assembly_energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("assembly.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("assembly_energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.isClientSide) {
            if(!isRedstonePowered(pPos)) {
                if (hasEnoughEnergy() && hasRecipe()) {
                    pLevel.setBlock(pPos, pState.setValue(LIT, true), 3);
                    increaseCraftingProgress();
                    extractEnergy(this);
                    setChanged(pLevel, pPos, pState);

                    if (hasProgressFinished()) {
                        craftItem();
                        resetProgress();
                    }
                } else {
                    resetProgress();
                    pLevel.setBlock(pPos, pState.setValue(LIT, false), 3);
                }
            }else{
                pLevel.setBlock(pPos, pState.setValue(LIT, false), 3);
            }
        }
    }



    private void extractEnergy(AssemblerBlockEntity assemblyBlockEntity) {
        assemblyBlockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private boolean hasEnoughEnergy() {

        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<AssemblerRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        for (int i = 0; i <= INPUT_SLOT; i++) {
            this.itemHandler.extractItem(i, 1, false);
        }

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        Optional<AssemblerRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<AssemblerRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(AssemblerRecipes.Type.INSTANCE, inventory, this.level);
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
        maxProgress = getCurrentRecipe().get().getTime();
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_SLOT;
    }


}