package com.Infinity.Nexus.Mod.worldgen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> INFINITY_ORE = registerKey("infinity_ore");
    public static final ResourceKey<BiomeModifier> LEAD_ORE = registerKey("lead_ore");
    public static final ResourceKey<BiomeModifier> ALUMINUM_ORE = registerKey("aluminum_ore");
    public static final ResourceKey<BiomeModifier> NICKEL_ORE = registerKey("nickel_ore");
    public static final ResourceKey<BiomeModifier> ZINC_ORE = registerKey("zinc_ore");
    public static final ResourceKey<BiomeModifier> SILVER_ORE = registerKey("silver_ore");
    public static final ResourceKey<BiomeModifier> TIN_ORE = registerKey("tin_ore");
    public static final ResourceKey<BiomeModifier> URANIUM_ORE = registerKey("uranium_ore");





    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(INFINITY_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.INFINITY_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(LEAD_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LEAD_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ALUMINUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ALUMINUM_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(NICKEL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NICKEL_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ZINC_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZINC_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SILVER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SILVER_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(TIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(URANIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.URANIUM_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));
    }


    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(InfinityNexusMod.MOD_ID, name));
    }
}
