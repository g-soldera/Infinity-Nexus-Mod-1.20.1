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

    public static final ForgeFlowingFluid.Properties LUBRICANT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.LUBRICANT, LUBRICANT_SOURCE, LUBRICANT_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.LUBRICANT)
            .bucket(ModItemsAdditions.BUCKET_LUBRICANT);
    public static final ForgeFlowingFluid.Properties ETHANOL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
        ModFluidType.ETHANOL, ETHANOL_SOURCE, ETHANOL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.ETHANOL)
            .bucket(ModItemsAdditions.BUCKET_ETHANOL);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
