package com.Infinity.Nexus.Mod.fluid;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import java.util.Vector;

public class ModFluidType {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");


    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, InfinityNexusMod.MOD_ID);


    public static final RegistryObject<FluidType> LUBRICANT = registryFluidType("lubricant",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1FFBD26,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(9).density(15)));

    public static final RegistryObject<FluidType> ETHANOL = registryFluidType("ethanol",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA17AE2FF,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));
    public static final RegistryObject<FluidType> OIL = registryFluidType("oil",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA11E1E1E,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(1).density(15)));

    public static final RegistryObject<FluidType> VINEGAR = registryFluidType("vinegar",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA17F006E,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final RegistryObject<FluidType> SUGARCANE_JUICE = registryFluidType("sugarcane_juice",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1714700,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final RegistryObject<FluidType> WINE = registryFluidType("wine",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA14800FF,
            new Vector3f(224f / 225f, 56f / 225f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));


    public static RegistryObject<FluidType> registryFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name,()-> fluidType);

    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
