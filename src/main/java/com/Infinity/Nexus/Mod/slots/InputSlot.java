package com.Infinity.Nexus.Mod.slots;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class InputSlot extends SlotItemHandler {
    public InputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 64;
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if(stack.getItem() == ModItemsAdditions.SPEED_UPGRADE.get() || stack.getItem() == ModItemsAdditions.STRENGTH_UPGRADE.get()){
            return false;
        }
        return true;
    }
    @Override
    public int getMaxStackSize() {
        return 64;
    }
}