package com.Infinity.Nexus.Mod.slots;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ComponentSlot extends SlotItemHandler {
    public ComponentSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        Item item = stack.getItem();
        return item == ModItemsAdditions.REDSTONE_COMPONENT.get()
                || item == ModItemsAdditions.BASIC_COMPONENT.get()
                || item == ModItemsAdditions.REINFORCED_COMPONENT.get()
                || item == ModItemsAdditions.LOGIC_COMPONENT.get()
                || item == ModItemsAdditions.ADVANCED_COMPONENT.get()
                || item == ModItemsAdditions.REFINED_COMPONENT.get()
                || item == ModItemsAdditions.INTEGRAL_COMPONENT.get()
                || item == ModItemsAdditions.INTEGRAL_COMPONENT.get();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}