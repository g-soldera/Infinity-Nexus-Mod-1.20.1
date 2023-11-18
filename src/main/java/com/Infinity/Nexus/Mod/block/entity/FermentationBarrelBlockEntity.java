package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.block.custom.FermentationBarrel;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.recipe.FermentationBarrelRecipes;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FermentationBarrelBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 2 -> true;
                case 1,3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank inputFluidHandler = new FluidTank(INPUT_FLUID_CAPACITY) {
        @Override
        public void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };
    private static final int INPUT_TO_FILL_SLOT = 0;
    private static final int OUTPUT_FROM_FILL_SLOT = 1;
    private static final int INPUT_TO_DRAIN_SLOT = 2;
    private static final int OUTPUT_FROM_DRAIN_SLOT = 3;
    private final FluidTank FLUID_STORAGE_INPUT = inputFluidHandler;
    private static final int INPUT_FLUID_CAPACITY = 8000;
    private static final int OUTPUT_FLUID_CAPACITY = 8000;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_FROM_DRAIN_SLOT || i == OUTPUT_FROM_FILL_SLOT, (i, s) -> i == INPUT_TO_DRAIN_SLOT  || i == INPUT_TO_FILL_SLOT  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;


    public FermentationBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FERMENTATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress = pValue;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress = pValue;
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
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(FermentationBarrel.FACING);

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
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE_INPUT);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
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
        return Component.translatable("block.infinity_nexus_mod.fermentation_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FermentationBarrelMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public static long getInputFluidCapacity() {
        return INPUT_FLUID_CAPACITY;
    }
    public static long getOutputFluidCapacity() {
        return OUTPUT_FLUID_CAPACITY;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fermentation_barrel.progress", progress);
        pTag.putInt("fermentation_barrel.max_progress", maxProgress);
        pTag = FLUID_STORAGE_INPUT.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("fermentation_barrel.progress");
        maxProgress = pTag.getInt("fermentation_barrel.max_progress");
        FLUID_STORAGE_INPUT.readFromNBT(pTag);
    }
    public FluidStack getFluidInInputTank() {
        return FLUID_STORAGE_INPUT.getFluid();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }
        //TODO
        transferItemFluidToTank(INPUT_TO_DRAIN_SLOT);
        if (isRedstonePowered(pPos)) {
            return;
        }
        if (!hasRecipe()) {
            resetProgress();
            return;
        }
        setMaxProgress();
        if(!canInsertOutputItem()){
            return;
        }
        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);


        if (hasProgressFinished()) {
            craftItem();
            resetProgress();
        }
    }

    private void transferItemFluidToTank(int fluidInputSlot) {
        this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            int drainAmount = Math.min(this.FLUID_STORAGE_INPUT.getSpace(), 1000);

            FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(stack.getFluid() == ModFluids.LUBRICANT_SOURCE.get()) {
                stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(stack, iFluidHandlerItem.getContainer());
            }
        });
    }

    private void fillTankWithFluid(FluidStack stack, ItemStack container) {
        this.FLUID_STORAGE_INPUT.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);



        if(stack.getAmount() <= 0 || this.FLUID_STORAGE_INPUT.getCapacity() == this.FLUID_STORAGE_INPUT.getFluidAmount() || container.getItem() instanceof BucketItem) {
            this.itemHandler.extractItem(INPUT_TO_DRAIN_SLOT, 1, false);
            this.itemHandler.insertItem(OUTPUT_FROM_DRAIN_SLOT, container, false);
        }

    }

    private boolean canInsertOutputItem() {
        return itemHandler.getStackInSlot(3).isEmpty()
                || itemHandler.getStackInSlot(3).getItem() == getCurrentRecipe().get().getResultItem(null).getItem()
                && (itemHandler.getStackInSlot(3).getCount() + getCurrentRecipe().get().getResultItem(null).getCount()) <= itemHandler.getStackInSlot(3).getItem().getMaxStackSize();
    }
    private void setMaxProgress() {
        maxProgress = getCurrentRecipe().get().getDuration();
    }


    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<FermentationBarrelRecipes> recipe = getCurrentRecipe();
        //TODO: Fix
            this.level.playLocalSound(this.getBlockPos(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            this.FLUID_STORAGE_INPUT.drain(recipe.get().getInputFluidStack(), IFluidHandler.FluidAction.EXECUTE);
            itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem(null).getItem(),recipe.get().getResultItem(null).getCount() + itemHandler.getStackInSlot(3).getCount()));
            itemHandler.getStackInSlot(2).shrink(recipe.get().getInputCount());
    }

    private boolean hasRecipe() {
        Optional<FermentationBarrelRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        //TODO
        return true;//recipe.get().getInputFluidStack() == FLUID_STORAGE_INPUT.getFluid()
               // && itemHandler.getStackInSlot(3).getItem() == Items.GLASS_BOTTLE
               // && FLUID_STORAGE_INPUT.getFluidAmount() >= recipe.get().getInputFluidStack().getAmount();
    }

    private Optional<FermentationBarrelRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FermentationBarrelRecipes.Type.INSTANCE, inventory, this.level);
    }
    private boolean canInsertDrainedItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_FROM_DRAIN_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_FROM_DRAIN_SLOT).is(item);
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

    public static int getInputSlot() {
        return INPUT_TO_DRAIN_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_FROM_DRAIN_SLOT;
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

}