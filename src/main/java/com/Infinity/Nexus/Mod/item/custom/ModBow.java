package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBow extends BowItem {
    private final int damage;
    protected final Tier tier;
    public ModBow(Tier tier, Properties properties, int damage) {
        super(properties);
        this.damage = damage;
        this.tier = tier;
    }

    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.setBaseDamage(damage);
        return arrow;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§7When in any hand"));
        tooltip.add(Component.translatable("§2"+this.damage+" Attack Damage"));
    }
    @Override
    public int getEnchantmentValue() {
        return super.getEnchantmentValue();
    }

    public Tier getTier() {
        return tier;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }
}


