package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class PickaxeItems extends PickaxeItem {

    public Tier tier;

    public PickaxeItems(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
        this.tier = tier;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }

}
