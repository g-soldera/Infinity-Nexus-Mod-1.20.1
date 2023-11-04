package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class AxeItems extends AxeItem {

    public Tier tier;

    public AxeItems(Tier tier, float damage, float speed, Properties properties) {
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
