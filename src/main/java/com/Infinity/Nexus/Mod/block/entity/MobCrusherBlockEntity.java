package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Miner.config.Config;
import com.Infinity.Nexus.Mod.block.custom.MobCrusher;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherMenu;
import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MobCrusherBlockEntity extends BlockEntity implements MenuProvider {
    private static final int[] OUTPUT_SLOT = {0,1,2,3,4,5,6,7,8};
    private static final int[] UPGRADE_SLOTS = {9,10,11,12};
    private static final int COMPONENT_SLOT = 13;
    private static final int SWORD_SLOT = 14;
    private static final int LINK_SLOT = 15;
    private static final int FUEL_SLOT = 16;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;
    private int hasRedstoneSignal = 0;
    private int stillCrafting = 0;
    private int hasSlotFree = 0;
    private int hasComponent = 0;
    private int hasEnoughEnergy = 0;
    private int hasRecipe = 0;
    private static final int ENERGY_CAPACITY = 60000;
    private static final int ENERGY_TRANSFER = 640;
    private static final int ENERGY_REQ = 32;
    private final FluidTank FLUID_STORAGE = createFluidStorage();
    private static final int FluidStorageCapacity = 10000;
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private final ItemStackHandler itemHandler = new ItemStackHandler(17) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7,8 -> !ModUtils.isUpgrade(stack) || !ModUtils.isComponent(stack);
                case 9,10,11,12 -> ModUtils.isUpgrade(stack);
                case 13 -> ModUtils.isComponent(stack);
                case 14 -> stack.getItem() instanceof SwordItem;
                case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
                case 16 -> ForgeHooks.getBurnTime(stack, null) > 0;
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))),
            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))),
            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))),
            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))),
            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))),
            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i <= 8, (i, s) -> ModUtils.canInsert(itemHandler, i, s))));

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
        }
    };

    private FluidTank createFluidStorage() {
        return new FluidTank(FluidStorageCapacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (!getLevel().isClientSide) {
                    getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }
    public MobCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOBCRUSHER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress;

                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe;

                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress = pValue;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress = pValue;

                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal = pValue;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting = pValue;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree = pValue;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent = pValue;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy = pValue;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe = pValue;
                }
            }

            @Override
            public int getCount() {
                return 8;
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
                Direction localDir = this.getBlockState().getValue(MobCrusher.FACING);

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
        return Component.translatable("block.infinity_nexus_mod.mob_crusher").append(" LV "+ getMachineLevel());
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MobCrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public static long getFluidCapacity() {
        return FluidStorageCapacity;
    }
    public FluidStack getFluid() {
        return this.FLUID_STORAGE.getFluid();
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    public int getHasRedstoneSignal() {
        return data.get(2);
    }
    public int getStillCrafting() {
        return data.get(3);
    }
    public int getHasSlotFree() {
        return data.get(4);
    }
    public int getHasComponent() {
        return data.get(5);
    }
    public int getHasEnoughEnergy() {
        return data.get(6);
    }
    public int getHasRecipe() {
        return data.get(7);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("mobCrusher.progress", progress);
        pTag.putInt("mobCrusher.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(pTag);

        pTag.putInt("mobCrusher.hasRedstoneSignal", getHasRedstoneSignal());
        pTag.putInt("mobCrusher.stillCrafting", getStillCrafting());
        pTag.putInt("mobCrusher.hasSlotFree", getHasSlotFree());
        pTag.putInt("mobCrusher.hasComponent", getHasComponent());
        pTag.putInt("mobCrusher.hasEnoughEnergy", getHasEnoughEnergy());
        pTag.putInt("mobCrusher.hasRecipe", getHasRecipe());

        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("mobCrusher.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("mobCrusher.energy"));
        FLUID_STORAGE.readFromNBT(pTag);

        hasRedstoneSignal = pTag.getInt("mobCrusher.hasRedstoneSignal");
        stillCrafting = pTag.getInt("mobCrusher.stillCrafting");
        hasSlotFree = pTag.getInt("mobCrusher.hasSlotFree");
        hasComponent = pTag.getInt("mobCrusher.hasComponent");
        hasEnoughEnergy = pTag.getInt("mobCrusher.hasEnoughEnergy");
        hasRecipe = pTag.getInt("mobCrusher.hasRecipe");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;
        pLevel.setBlock(pPos, pState.setValue(MobCrusher.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            this.data.set(2, 1);
            return;
        }
        this.data.set(2, 0);

        setMaxProgress();
        if (!hasEnoughEnergy()) {
            verifySolidFuel();
            this.data.set(6, 0);
            return;
        }
        this.data.set(6, 1);

        if(!hasMobInside(machineLevel, pPos, pLevel)) {
            this.data.set(7, 0);
            return;
        }
        this.data.set(7, 1);

        increaseCraftingProgress();

        if (hasProgressFinished()) {
            this.data.set(3, 1);
            pLevel.setBlock(pPos, pState.setValue(MobCrusher.LIT, machineLevel+9), 3);
            verifyMobs(pLevel, pPos, machineLevel);
            extractEnergy(this);
            setChanged(pLevel, pPos, pState);
            resetProgress();
        }
        this.data.set(3, 0);
    }


    private boolean hasMobInside(int machinelevel, BlockPos pPos, Level pLevel) {
        machinelevel = machinelevel + 1;
        List<Mob> mobs = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, new AABB(pPos.offset( machinelevel * -1, 0,  machinelevel * -1), pPos.offset(+machinelevel,3,+machinelevel))));
        return !mobs.isEmpty();
    }
    private void extractEnergy(MobCrusherBlockEntity mobCrusherBlockEntity) {
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = mobCrusherBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = (((machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        mobCrusherBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    public int getMachineLevel(){
        if(ModUtils.isComponent(this.itemHandler.getStackInSlot(COMPONENT_SLOT))){
            this.data.set(5, ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT)));
        }else{
            this.data.set(5, 0);
        }
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
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
    private void setMaxProgress() {
        maxProgress = 20;
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
    private  void execute(Mob mob, BlockPos pPos, int machineLevel) {

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        Player player = new IFFakePlayer((ServerLevel) this.level);
        DamageSource source = player.damageSources().playerAttack(player);
        LootTable table = Objects.requireNonNull(this.level.getServer()).getLootData().getLootTable(mob.getLootTable());
        LootParams.Builder context = new LootParams.Builder((ServerLevel) this.level)
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                .withParameter(LootContextParams.KILLER_ENTITY, player)
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, player)
                .withParameter(LootContextParams.TOOL, itemHandler.getStackInSlot(SWORD_SLOT));
        table.getRandomItems(context.create(LootContextParamSets.ENTITY)).forEach(stack ->{
            insertItemOnInventory(stack);
                for(int loot = 0; loot < machineLevel; loot++) {
                    int rand = RandomSource.create().nextInt(10);
                    if (rand == 0) {
                        insertItemOnInventory(stack);
                    }
                }
        });

        List<ItemEntity> extra = new ArrayList<>();
        try {
            if (mob.captureDrops() == null) mob.captureDrops(new ArrayList<>());
            ObfuscationReflectionHelper.findMethod(Mob.class, "m_7472_", DamageSource.class, int.class, boolean.class).invoke(mob, source, 0, true);
            if (mob.captureDrops() != null) {
                extra.addAll(mob.captureDrops());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ForgeHooks.onLivingDrops(mob, source, extra, 3, true);
        extra.forEach(itemEntity -> {
            insertItemOnInventory(itemEntity.getItem());
            itemEntity.remove(Entity.RemovalReason.KILLED);
        });
        mob.setHealth(0);
        insertExpense(mob.getExperienceReward());
    }

    private void insertExpense(int experienceReward) {
        //TODO Fluid Experience
        FluidStack fluidStack = new FluidStack(ModFluids.EXPERIENCE_SOURCE.get(), experienceReward);
        this.FLUID_STORAGE.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }

    public void verifyMobs(Level pLevel, BlockPos pPos, int machinelevel) {
        try {
            machinelevel = machinelevel + 1;
            List<Mob> entities = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, new AABB(pPos.offset( machinelevel * -1, 0,  machinelevel * -1), pPos.offset(+machinelevel,3,+machinelevel))));
            this.data.set(4,0);
            if (!entities.isEmpty()) {
                boolean hasFreeSlots = hasFreeSlots();
                if(!hasFreeSlots && entities.size() > 30) {
                    entities.forEach(Entity::discard);
                    notifyOwner();
                }else if(hasFreeSlots){
                    this.data.set(4,1);
                    for (Mob entity : entities) {
                        if (entity != null) {
                            if (entity.isAlive()) {
                                execute(entity, pPos, machinelevel);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("§f[INM§f]§4: Failed to kill mobs in: " + pPos);
            e.printStackTrace();
        }
    }
    private void verifySolidFuel(){
        ItemStack slotItem = itemHandler.getStackInSlot(FUEL_SLOT);
        int burnTime = ForgeHooks.getBurnTime(slotItem, null);
        if(burnTime > 1){
            while(itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0 && this.getEnergyStorage().getEnergyStored() + burnTime < this.getEnergyStorage().getMaxEnergyStored()){
                this.getEnergyStorage().receiveEnergy(burnTime, false);
                itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }
    private void notifyOwner() {
        //TODO
    }

    private boolean hasFreeSlots() {
        AtomicInteger freeSlots = new AtomicInteger(0);
        for (int slot : OUTPUT_SLOT) {
            if (this.itemHandler.getStackInSlot(slot).isEmpty()) {
                freeSlots.getAndIncrement();
            }
        }
        return freeSlots.get() > 0;
    }
    private void insertItemOnInventory(ItemStack itemStack) {
        try {
            if(itemHandler.getStackInSlot(LINK_SLOT).is(ModItems.LINKING_TOOL.get())){
                ItemStack linkingTool = itemHandler.getStackInSlot(LINK_SLOT).copy();
                boolean success = false;
                String name = linkingTool.getDisplayName().getString();

                if(linkingTool.hasCustomHoverName()) {
                    //success = LinkInventory.insertItem(name, this.level, itemStack, itemHandler, OUTPUT_SLOT);
                }
                if(!success) {
                    insertItemOnSelfInventory(itemStack);
                }
            }else{
                insertItemOnSelfInventory(itemStack);
            }

        }catch (Exception e){
            System.out.println("§f[INM§f]§4: Failed to insert item in: " + this.getBlockPos());
            e.printStackTrace();
        }
    }
    private void insertItemOnSelfInventory(ItemStack itemStack){
        for (int slot : OUTPUT_SLOT) {
            if (ModUtils.canPlaceItemInContainer(itemStack, slot, this.itemHandler)) {
                this.itemHandler.insertItem(slot, itemStack, false);
                break;
            }
        }
    }
    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}