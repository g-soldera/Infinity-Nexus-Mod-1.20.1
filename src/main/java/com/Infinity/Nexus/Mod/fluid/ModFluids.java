package com.Infinity.Nexus.Mod.fluid;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> LUBRICANT_SOURCE = FLUIDS.register("lubricant_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.LUBRICANT_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> LUBRICANT_FLOWING = FLUIDS.register("flowing_lubricant_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.LUBRICANT_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> ETHANOL_SOURCE = FLUIDS.register("ethanol_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.ETHANOL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> ETHANOL_FLOWING = FLUIDS.register("flowing_ethanol_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.ETHANOL_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> OIL_SOURCE = FLUIDS.register("oil_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OIL_FLOWING = FLUIDS.register("flowing_oil_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> VINEGAR_SOURCE = FLUIDS.register("vinegar_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.VINEGAR_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> VINEGAR_FLOWING = FLUIDS.register("flowing_vinegar_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.VINEGAR_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SUGARCANE_JUICE_SOURCE = FLUIDS.register("sugarcane_juice_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.SUGARCANE_JUICE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SUGARCANE_JUICE_FLOWING = FLUIDS.register("flowing_sugarcane_juice_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.SUGARCANE_JUICE_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> WINE_SOURCE = FLUIDS.register("wine_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> WINE_FLOWING = FLUIDS.register("flowing_wine_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.WINE_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> EXPERIENCE_SOURCE = FLUIDS.register("experience_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.EXPERIENCE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> EXPERIENCE_FLOWING = FLUIDS.register("flowing_experience_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.EXPERIENCE_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties LUBRICANT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.LUBRICANT, LUBRICANT_SOURCE, LUBRICANT_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.LUBRICANT)
            .bucket(ModItemsAdditions.BUCKET_LUBRICANT);
    public static final ForgeFlowingFluid.Properties ETHANOL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.ETHANOL, ETHANOL_SOURCE, ETHANOL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.ETHANOL)
            .bucket(ModItemsAdditions.BUCKET_ETHANOL);
    public static final ForgeFlowingFluid.Properties OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.OIL, OIL_SOURCE, OIL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.OIL)
            .bucket(ModItemsAdditions.BUCKET_OIL);
    public static final ForgeFlowingFluid.Properties VINEGAR_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.VINEGAR, VINEGAR_SOURCE, VINEGAR_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.VINEGAR)
            .bucket(ModItemsAdditions.BUCKET_VINEGAR);
    public static final ForgeFlowingFluid.Properties SUGARCANE_JUICE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.SUGARCANE_JUICE, SUGARCANE_JUICE_SOURCE, SUGARCANE_JUICE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.SUGARCANE_JUICE)
            .bucket(ModItemsAdditions.BUCKET_SUGARCANE_JUICE);
    public static final ForgeFlowingFluid.Properties WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.WINE, WINE_SOURCE, WINE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.WINE)
            .bucket(ModItemsAdditions.BUCKET_WINE);
    public static final ForgeFlowingFluid.Properties EXPERIENCE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.EXPERIENCE, EXPERIENCE_SOURCE, EXPERIENCE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.EXPERIENCE)
            .bucket(ModItemsAdditions.BUCKET_EXPERIENCE);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
