package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Mod.block.entity.WrappedHandler;
import com.Infinity.Nexus.Mod.item.ModCrystalItems;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.ComponentItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Map;

public class ModUtils {

    public static int getComponentLevel(ItemStack stack) {
        if (stack.getItem() == ModItemsAdditions.REDSTONE_COMPONENT.get()) {
            return 1;
        }
        if (stack.getItem() == ModItemsAdditions.BASIC_COMPONENT.get()) {
            return 2;
        }
        if (stack.getItem() == ModItemsAdditions.REINFORCED_COMPONENT.get()) {
            return 3;
        }
        if (stack.getItem() == ModItemsAdditions.LOGIC_COMPONENT.get()) {
            return 4;
        }
        if (stack.getItem() == ModItemsAdditions.ADVANCED_COMPONENT.get()) {
            return 5;
        }
        if (stack.getItem() == ModItemsAdditions.REFINED_COMPONENT.get()) {
            return 6;
        }
        if (stack.getItem() == ModItemsAdditions.INTEGRAL_COMPONENT.get()) {
            return 7;
        }
        if (stack.getItem() == ModItemsAdditions.INFINITY_COMPONENT.get()) {
            return 8;
        }
        if (stack.getItem() == ModItemsAdditions.ANCESTRAL_COMPONENT.get()) {
            return 9;
        }
        return 0;
    }


    public static int getSpeed(ItemStackHandler itemHandler, int[] upgradeSlots) {
        int speed = 0;
        for (int i : upgradeSlots) {
            if (itemHandler.getStackInSlot(i).getItem() == ModItemsAdditions.SPEED_UPGRADE.get()) {
                speed++;
            }
        }
        return speed;
    }
    public static int getStrength(ItemStackHandler itemHandler, int[] upgradeSlots) {
        int strength = 0;
        for (int i : upgradeSlots) {
            if (itemHandler.getStackInSlot(i).getItem() == ModItemsAdditions.STRENGTH_UPGRADE.get()) {
                strength ++;
            }
        }
        return strength;
    }

    public static boolean isUpgrade(ItemStack stack) {
        return stack.getItem() == ModItemsAdditions.STRENGTH_UPGRADE.get()
                || stack.getItem() == ModItemsAdditions.SPEED_UPGRADE.get();

    }
    public static boolean isComponent(ItemStack stack) {
        return stack.getItem() instanceof ComponentItem;
    }

    public static boolean canPlaceItemInContainer(ItemStack itemStack, int slotIndex, IItemHandler iItemHandler) {
        ItemStack slotStack = iItemHandler.getStackInSlot(slotIndex);

        if (!slotStack.isEmpty()) {
            if (!itemStack.is(slotStack.getItem())) {
                return false;
            }

            if (!ItemStack.isSameItemSameTags(itemStack, slotStack)) {
                return false;
            }

            int slotCapacity = iItemHandler.getSlotLimit(slotIndex);
            int stackCapacity = itemStack.getMaxStackSize();
            int slotCount = slotStack.getCount();
            int itemCount = itemStack.getCount();

            if (itemCount + slotCount > slotCapacity) {
                return false;
            }

            if (itemCount + slotCount > stackCapacity) {
                return false;
            }
        }

        return true;
    }

    public static ItemStack getComponentItem(int value) {
        return switch (value) {
            case 1 -> new ItemStack(ModItemsAdditions.REDSTONE_COMPONENT.get());
            case 2 -> new ItemStack(ModItemsAdditions.BASIC_COMPONENT.get());
            case 3 -> new ItemStack(ModItemsAdditions.REINFORCED_COMPONENT.get());
            case 4 -> new ItemStack(ModItemsAdditions.LOGIC_COMPONENT.get());
            case 5 -> new ItemStack(ModItemsAdditions.ADVANCED_COMPONENT.get());
            case 6 -> new ItemStack(ModItemsAdditions.REFINED_COMPONENT.get());
            case 7 -> new ItemStack(ModItemsAdditions.INTEGRAL_COMPONENT.get());
            case 8 -> new ItemStack(ModItemsAdditions.INFINITY_COMPONENT.get());
            case 9 -> new ItemStack(ModItemsAdditions.ANCESTRAL_COMPONENT.get());
            default -> ItemStack.EMPTY;
        };
    }
    public static ItemStack getCrystalType(int value) {
        return switch (value) {
            case 1 -> new ItemStack(ModCrystalItems.AMBER_CRYSTAL.get());
            case 2 -> new ItemStack(ModCrystalItems.MARINE_CRYSTAL.get());
            case 3 -> new ItemStack(ModCrystalItems.CITRIUM_CRYSTAL.get());
            case 4 -> new ItemStack(ModCrystalItems.RUBIUM_CRYSTAL.get());
            case 5 -> new ItemStack(ModCrystalItems.DEMETRIUM_CRYSTAL.get());
            case 6 -> new ItemStack(ModCrystalItems.AGATE_CRYSTAL.get());
            default -> new ItemStack(ModCrystalItems.DARIUM_CRYSTAL.get());
        };
    }

    public static boolean isSolarComponent(ItemStack stack) {
        return true;
    }
}
