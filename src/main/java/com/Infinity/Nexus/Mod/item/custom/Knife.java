package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Knife extends Item {
    public Knife(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack stack = itemStack.copy();
        stack.hurt(1, RandomSource.create(), null);
        return stack.getDamageValue() >= stack.getMaxDamage() ? ItemStack.EMPTY : stack;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
}
