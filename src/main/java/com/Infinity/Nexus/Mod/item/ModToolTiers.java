package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.config.ModCommonConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier INFINITY = TierSortingRegistry.registerTier(
            new ForgeTier(
                    ModCommonConfigs.tier_infinity_mining_level,
                    ModCommonConfigs.tier_infinity_mining_durability,
                    ModCommonConfigs.tier_infinity_harvest_speed,
                    ModCommonConfigs.tier_infinity_attack_damage_bonus,
                    ModCommonConfigs.tier_infinity_enchant_value,
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get())),
            new ResourceLocation(InfinityNexusMod.MOD_ID, "infinity"), List.of(Tiers.NETHERITE), List.of());

    public static final Tier IMPERIAL = TierSortingRegistry.registerTier(
            new ForgeTier(
                    ModCommonConfigs.tier_imperial_infinity_mining_level,
                    ModCommonConfigs.tier_imperial_infinity_mining_durability,
                    ModCommonConfigs.tier_imperial_infinity_harvest_speed,
                    ModCommonConfigs.tier_imperial_infinity_attack_damage_bonus,
                    ModCommonConfigs.tier_imperial_infinity_enchant_value,
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get())),
            new ResourceLocation(InfinityNexusMod.MOD_ID, "imperial"), List.of(Tiers.NETHERITE), List.of());
}
