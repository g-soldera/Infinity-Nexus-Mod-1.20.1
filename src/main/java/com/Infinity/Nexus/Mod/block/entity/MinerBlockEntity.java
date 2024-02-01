package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.block.custom.Miner;
import com.Infinity.Nexus.Mod.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.ComponentItem;
import com.Infinity.Nexus.Mod.screen.miner.MinerMenu;
import com.Infinity.Nexus.Mod.utils.MinerTierStructure;
import com.Infinity.Nexus.Mod.utils.ModEnergyStorage;
import com.Infinity.Nexus.Mod.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MinerBlockEntity extends BlockEntity implements MenuProvider {
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
                //TODO
                case 14 -> true;
                case 15 -> stack.is(ModItemsAdditions.LINKING_TOOL.get().asItem());
                case 16 -> true;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int[] OUTPUT_SLOT = {0,1,2,3,4,5,6,7,8};
    private static final int[] UPGRADE_SLOTS = {9,10,11,12};
    private static final int COMPONENT_SLOT = 13;
    private static final int LINK_SLOT = 15;
    private static final int FORTUNE_SLOT = 14;
    private static final int SMELTER_SLOT = 16;
    private static final int capacity = 100000;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(capacity, capacity) {
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


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i < 8, (i, s) -> !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 80;
    private int verify = 0;
    private int maxVerify = 7;
    private int structure;


    public MinerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MinerBlockEntity.this.progress;
                    case 1 -> MinerBlockEntity.this.maxProgress;
                    case 3 -> MinerBlockEntity.this.verify;
                    case 4 -> MinerBlockEntity.this.maxVerify;
                    case 5 -> MinerBlockEntity.this.structure;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MinerBlockEntity.this.progress = pValue;
                    case 1 -> MinerBlockEntity.this.maxProgress = pValue;
                    case 3 -> MinerBlockEntity.this.verify = pValue;
                    case 4 -> MinerBlockEntity.this.maxVerify = pValue;
                    case 5 -> MinerBlockEntity.this.structure = pValue;
                }
            }

            @Override
            public int getCount() {
                return 5;
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
                Direction localDir = this.getBlockState().getValue(Miner.FACING);

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
        return Component.translatable("block.infinity_nexus_mod.miner").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MinerMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("miner.progress", progress);
        pTag.putInt("miner.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("miner.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("miner.energy"));
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;

        if (isRedstonePowered(pPos)) {
            pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            return;
        }

        if (!hasProgressFinished(machineLevel)) {
            increaseCraftingProgress();
            return;
        }

        if (!hasEmptySlot()) {
            pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            return;
        }

        resetProgress();
        if(!hasComponent()){
            pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            return;
        }

        setMaxProgress(machineLevel);
        if (!hasEnoughEnergy(machineLevel)) {
            pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            return;
        }
        if(hasRecipe(pPos, machineLevel)) {
            pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel + 8), 3);
            craftItem(pPos, machineLevel);
            extractEnergy(this, machineLevel);
            setChanged(pLevel, pPos, pState);
        }
    }

    private boolean hasEmptySlot() {
        boolean hasFreeSpace = false;
        for (int slot : OUTPUT_SLOT) {
            if(itemHandler.getStackInSlot(slot).isEmpty()) {
                hasFreeSpace = true;
                break;
            }
        }
        return hasFreeSpace;
    }

    private boolean hasComponent() {
        return itemHandler.getStackInSlot(COMPONENT_SLOT).getItem() instanceof ComponentItem;
    }

    private void setMaxProgress(int machineLevel) {
        maxProgress = 180 - (machineLevel * 20) - (Math.min(ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS), 2) * 10);
    }

    private void extractEnergy(MinerBlockEntity minerBlockEntity, int machineLevel) {
        int energy = ((machineLevel + 1) * 1000);
        int speed = Math.max(ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS), 2) + machineLevel;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = energy * speed;

        int extractEnergy = var1 + strength;
        minerBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }

    private boolean hasEnoughEnergy(int machineLevel) {

        int energy = ((machineLevel + 1) * 1000);
        int speed = Math.max(ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS), 2) + machineLevel;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = energy * speed;

        int extractEnergy = var1 + strength;
        return ENERGY_STORAGE.getEnergyStored() >= extractEnergy;
    }

    private void resetProgress() {
        progress = 0;
    }
    private boolean isOre(ItemStack stack) {
        List<TagKey<Item>> tags = stack.getTags().toList();
                return tags.toString().contains("forge:ores");
    }
    private ItemStack getDrop(ItemStack stack, int fortune, float luck) {
        if(!(stack.getItem() instanceof BlockItem)) {
            return null;
        }
        //TODO Get Drop From stack block
        IFFakePlayer player = new IFFakePlayer((ServerLevel) this.level);
        Block block = ((BlockItem) stack.getItem()).getBlock();
        AtomicReference<ItemStack> dropItem = new AtomicReference<ItemStack>(ItemStack.EMPTY);

        List<ItemStack> drops = new ArrayList<>(Block.getDrops(block.defaultBlockState(), (ServerLevel) level, this.getBlockPos(), null, player, player.getItemInHandPickaxe(InteractionHand.MAIN_HAND, fortune, luck)));

        drops.forEach(stack1 -> {
            int drop = new Random().nextInt(drops.size());
                dropItem.set(drops.get(drop).copy());

        });
        return dropItem.get();
    }

    private void craftItem(BlockPos pos, int machineLevel) {
            ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
            if (component.getDamageValue() >= component.getMaxDamage() && component.getItem() != ModItemsAdditions.INFINITY_COMPONENT.get()) {
                component.shrink(1);
                level.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }

            int fortune = getFortuneLevel();
            ItemStack output = getOutputItem(pos, fortune, machineLevel);

            if ((output.getItem() == ModItemsAdditions.RAW_INFINITY.get() && machineLevel < 6) || (output.getItem() == Blocks.ANCIENT_DEBRIS.asItem() && machineLevel < 4)) {
                return;
            }
            insertItemOnInventory(output);
            component.hurt(1, this.level.random, null);
            level.playSound(null, this.getBlockPos(), SoundEvents.BEE_HURT, SoundSource.BLOCKS, 0.4f, 1.0f);


    }

    private boolean hasRecipe(BlockPos pos,int machineLevel) {

        if(this.verify >= maxVerify) {
           this.structure = MinerTierStructure.hasStructure(machineLevel+1, pos, level) ? 1 : 0;
           this.verify = 0;
        }
        this.verify++;
        return this.structure > 0;
    }

    private ItemStack getOutputItem(BlockPos pos, int fortune, int machineLevel) {
        List<ItemStack> drops = new ArrayList<>();
        int radio = ((int) Math.floor((double) machineLevel / 2)+1);

        int startX = pos.getX() - radio;;
        int startY = pos.getY() - ((int) Math.floor((double) (machineLevel + 4) / 2) * 2);
        int startZ = pos.getZ() - radio;

        int endX = pos.getX() + radio;
        int endY = pos.getY() - 2;
        int endZ = pos.getZ() + radio;

        int randomX = startX + new Random().nextInt(endX - (startX-1));
        int randomY = startY + new Random().nextInt(endY - (startY-1));
        int randomZ = startZ + new Random().nextInt(endZ - (startZ-1));

        BlockPos novoBlockPos = new BlockPos(randomX, randomY, randomZ);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (blockPos.equals(novoBlockPos)) {
                        //level.setBlock(blockPos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                        BlockState blockState = level.getBlockState(blockPos);
                        ItemStack blockStack = new ItemStack(blockState.getBlock().asItem());
                        if (blockState.isAir() || isOre(blockStack)) {
                            ItemStack drop = getDrop(blockStack, fortune, 0);
                            drops.add(Objects.requireNonNullElse(drop, ItemStack.EMPTY));
                        }
                    }
                }
            }
        }
        if(drops.isEmpty()){
            drops.add(ItemStack.EMPTY);
        }
        return drops.get(new Random().nextInt(drops.size()));
    }

    private int getFortuneLevel() {
        ItemStack bookItem = itemHandler.getStackInSlot(FORTUNE_SLOT);
        int enchantmentLevel = 0;
        CompoundTag bookTag = bookItem.getTag();
        if (bookTag != null && bookTag.contains("StoredEnchantments")) {
            ListTag enchantmentsTag = bookTag.getList("StoredEnchantments", 10);
            for (int i = 0; i < enchantmentsTag.size(); i++) {
                CompoundTag enchantmentTag = enchantmentsTag.getCompound(i);
                String enchantmentId = enchantmentTag.getString("id");
                if (enchantmentId.equals("minecraft:fortune")) {
                    enchantmentLevel = enchantmentTag.getShort("lvl");
                    break;
                }
            }
        }
        //{StoredEnchantments:[{id:"minecraft:fortune",lvl:3s}]}
        return enchantmentLevel;

    }
    private void insertItemOnInventory(ItemStack itemStack) {
        try {
            if(itemHandler.getStackInSlot(LINK_SLOT).is(ModItemsAdditions.LINKING_TOOL.get())){
                ItemStack linkingTool = itemHandler.getStackInSlot(LINK_SLOT).copy();
                AtomicBoolean success = new AtomicBoolean(false);
                String name = linkingTool.getDisplayName().getString();
                if(linkingTool.hasCustomHoverName()) {
                    String[] parts = name.substring(1, name.length() - 1).split(",");
                    int xl = 0;
                    int yl = 0;
                    int zl = 0;
                    String facel = "up";

                    for (String part : parts) {
                        String[] keyValue = part.split("=");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("x")) {
                            xl = Integer.parseInt(value);
                        } else if (key.equals("y")) {
                            yl = Integer.parseInt(value);
                        } else if (key.equals("z")) {
                            zl = Integer.parseInt(value);
                        } else if (key.equals("face")) {
                            facel = value;
                        }
                    }
                    BlockEntity blockEntity = this.level.getBlockEntity(new BlockPos(xl, yl, zl));
                    if (blockEntity != null && canLink(blockEntity)) {
                        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, getLinkedSide(facel)).ifPresent(iItemHandler -> {
                            for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                if (ModUtils.canPlaceItemInContainer(itemStack.copy(), slot, iItemHandler) && iItemHandler.isItemValid(slot, itemStack.copy())) {
                                    iItemHandler.insertItem(slot, itemStack.copy(), false);
                                    success.set(true);
                                    break;
                                }
                            }

                            for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                for (int outputSlot : OUTPUT_SLOT) {
                                    if (!itemHandler.getStackInSlot(outputSlot).isEmpty() && iItemHandler.isItemValid(slot, itemStack.copy()) && ModUtils.canPlaceItemInContainer(itemHandler.getStackInSlot(outputSlot).copy(), slot, iItemHandler)) {
                                        iItemHandler.insertItem(slot, itemHandler.getStackInSlot(outputSlot).copy(), false);
                                        itemHandler.extractItem(outputSlot, itemHandler.getStackInSlot(outputSlot).getCount(), false);
                                        success.set(true);
                                        break;
                                    }
                                }
                            }
                        });
                    }
                }
                if(!success.get()) {
                    insertItemOnSelfInventory(itemStack);
                }
            }else{
                insertItemOnSelfInventory(itemStack);
            }

        }catch (Exception e){
            System.out.println("§f[INM§f]§c: Failed to insert item in: " + this.getBlockPos());
        }
    }
    private Direction getLinkedSide(String side) {
        switch (side){
            case "up":
                return Direction.UP;
            case "down":
                return Direction.DOWN;
            case "north":
                return Direction.NORTH;
            case "south":
                return Direction.SOUTH;
            case "west":
                return Direction.WEST;
            case "east":
                return Direction.EAST;
            default:
                return Direction.UP;
        }
    }
    private boolean canLink(BlockEntity blockEntity) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(blockEntity.getBlockPos())) < 100;
    }
    private void insertItemOnSelfInventory(ItemStack itemStack){
        for (int slot : OUTPUT_SLOT) {
            if (ModUtils.canPlaceItemInContainer(itemStack, slot, this.itemHandler)) {
                this.itemHandler.insertItem(slot, itemStack, false);
                break;
            }
        }
    }
    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished(int machineLevel) {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress() {
        progress ++;
    }

    public static int getInputSlot() {
        return 0;
    }

    public static int getOutputSlot() {
        return 0;
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