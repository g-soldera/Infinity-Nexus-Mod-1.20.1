package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.block.custom.Squeezer;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.recipe.SqueezerRecipes;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerMenu;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SqueezerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1 -> !ModUtils.isUpgrade(stack) || !ModUtils.isComponent(stack);
                case 2 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 3 -> false;
                case 4,5,6,7 -> ModUtils.isUpgrade(stack);
                case 8 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FLUID_SLOT = 2;
    private static final int OUTPUT_FLUID_SLOT = 3;
    private static final int[] UPGRADE_SLOTS = {4, 5, 6, 7};
    private static final int COMPONENT_SLOT = 8;
    private static final int capacity = 60000;
    private static final int maxTransfer = 500;
    private static final int fluidCapacity = 5000;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private final FluidTank FLUID_STORAGE = createFluidStorage();

    private FluidTank createFluidStorage() {
        return new FluidTank(fluidCapacity) {
            @Override
            public void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(capacity, maxTransfer) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT, (i, s) -> i == INPUT_SLOT && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private int onofre;

    public SqueezerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SQUEEZER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> SqueezerBlockEntity.this.progress;
                    case 1 -> SqueezerBlockEntity.this.maxProgress;
                    case 2 -> SqueezerBlockEntity.this.onofre;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> SqueezerBlockEntity.this.progress = pValue;
                    case 1 -> SqueezerBlockEntity.this.maxProgress = pValue;
                    case 2 -> SqueezerBlockEntity.this.onofre = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Squeezer.FACING);

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
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE);

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
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
        return Component.literal(Component.translatable("block.infinity_nexus_mod.squeezer").getString()+" LV "+getMachineLevel());
    }
    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SqueezerMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public static long getFluidCapacity() {
        return fluidCapacity;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("squeezer.progress", progress);
        pTag.putInt("squeezer.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(pTag);
        pTag.putInt("squeezer.onofre", data.get(2));

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("squeezer.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("squeezer.energy"));
        FLUID_STORAGE.readFromNBT(pTag);
        onofre = pTag.getInt("squeezer.onofre");
    }

    public int getOnofre() {
        return data.get(2);
    }
    public void setOnofre(int value) {
        data.set(2, value);
    }

    public FluidStack getFluid() {
        return FLUID_STORAGE.getFluid();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        try {
            if (pLevel.isClientSide) {
                return;
            }
            fillUpOnFluid(FLUID_SLOT);

            int machineLevel = getMachineLevel() - 1 <= 0 ? 0 : getMachineLevel() - 1;
            ;
            pLevel.setBlock(pPos, pState.setValue(Squeezer.LIT, machineLevel), 3);
            if (isRedstonePowered(pPos)) {
                return;
            }

            if (!hasRecipe()) {
                resetProgress();
                return;
            }
            setMaxProgress();
            if (!hasEnoughEnergy()) {
                return;
            }
            if(!canInsertOutputFluid()){
                return;
            }
            pLevel.setBlock(pPos, pState.setValue(Squeezer.LIT, machineLevel + 8), 3);
            increaseCraftingProgress();
            extractEnergy(this);
            setChanged(pLevel, pPos, pState);


            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean canInsertOutputFluid() {
        return FLUID_STORAGE.getSpace() >= getCurrentRecipe().get().getFluid().getAmount()  &&
                FLUID_STORAGE.getFluid().isFluidEqual(getCurrentRecipe().get().getFluid()) ||
                FLUID_STORAGE.getFluid().isEmpty();
    }

    private void fillUpOnFluid(int fluidInputSlot) {
        try {
            ItemStack fluidStack = this.itemHandler.getStackInSlot(fluidInputSlot);
            if (fluidStack.isEmpty()) {
                return;
            }
            fluidStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
                if (fluidStack.getItem() instanceof BucketItem bucketItem ) {
                    FluidStack fluidStackToDrain = new FluidStack(FLUID_STORAGE.getFluid(), 1000);

                    ItemStack filledBucket = new ItemStack(FLUID_STORAGE.getFluid().getFluid().getBucket());

                    if (isValidBucket(filledBucket, bucketItem)) {
                        System.out.println(filledBucket.getDisplayName());
                        FLUID_STORAGE.drain(fluidStackToDrain, IFluidHandler.FluidAction.EXECUTE);
                        itemHandler.insertItem(OUTPUT_FLUID_SLOT, filledBucket, false);
                        itemHandler.extractItem(FLUID_SLOT, 1, false);
                        itemHandler.setStackInSlot(OUTPUT_FLUID_SLOT, filledBucket);
                    }
                } else {
                    if (canInsertFluidOnItem(iFluidHandlerItem)) {
                        FluidStack fluidStackToFill = new FluidStack(FLUID_STORAGE.getFluid(), 10);
                        FLUID_STORAGE.drain(fluidStackToFill, IFluidHandler.FluidAction.EXECUTE);
                        iFluidHandlerItem.fill(fluidStackToFill, IFluidHandler.FluidAction.EXECUTE);
                    }else{
                        if(canInsertFluidItemInOutputSlot()) {
                            this.itemHandler.extractItem(FLUID_SLOT, 1, false);
                            this.itemHandler.setStackInSlot(OUTPUT_FLUID_SLOT, iFluidHandlerItem.getContainer());
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isValidBucket(ItemStack filledBucket, BucketItem bucketItem) {
        return !filledBucket.isEmpty() && itemHandler.getStackInSlot(OUTPUT_FLUID_SLOT).isEmpty() && bucketItem == Items.BUCKET && FLUID_STORAGE.getFluid().getAmount() >= 1000;
    }


    private boolean canInsertFluidItemInOutputSlot() {
        return itemHandler.getStackInSlot(OUTPUT_FLUID_SLOT).isEmpty()
                && !FLUID_STORAGE.isEmpty();
    }

    private boolean canInsertFluidOnItem(IFluidHandlerItem iFluidHandlerItem) {
        //TODO para cada tank
        return iFluidHandlerItem.getFluidInTank(0).isFluidEqual(FLUID_STORAGE.getFluid())
                && iFluidHandlerItem.getFluidInTank(0).getAmount() < iFluidHandlerItem.getTankCapacity(0)
                || iFluidHandlerItem.getFluidInTank(0).isEmpty();
    }

    private void setMaxProgress() {
        maxProgress = getCurrentRecipe().get().getDuration();
    }

    private void extractEnergy(SqueezerBlockEntity squeezerBlockEntity) {
        int energy = getCurrentRecipe().get().getEnergy();
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = squeezerBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = ((energy + (machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        squeezerBlockEntity.ENERGY_STORAGE.extractEnergy(extractEnergy, false);
    }
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ((getCurrentRecipe().get().getEnergy() + (getMachineLevel()*20)) / maxProgress);
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<SqueezerRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT, recipe.get().getInputCount(), false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

        FluidStack fluidStack = recipe.get().getFluid();
        this.FLUID_STORAGE.fill(new FluidStack(fluidStack, fluidStack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
    }

    private boolean hasRecipe() {
        Optional<SqueezerRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private Optional<SqueezerRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(SqueezerRecipes.Type.INSTANCE, inventory, this.level);
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
        progress += ((ModUtils.getSpeed(this.itemHandler, UPGRADE_SLOTS)+1) + getMachineLevel()+1);
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

    public void setUpgradeLevel(ItemStack itemStack) {
        {
            if (this.itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()) {
                this.itemHandler.setStackInSlot(COMPONENT_SLOT, itemStack);
            }
        }
    }
}