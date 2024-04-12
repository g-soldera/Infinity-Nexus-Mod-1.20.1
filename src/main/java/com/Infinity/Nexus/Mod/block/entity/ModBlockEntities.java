package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public  static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<MobCrusherBlockEntity>> MOBCRUSHER_BE =
            BLOCK_ENTITY.register("mob_crusher_block_entity", () ->
                    BlockEntityType.Builder.of(MobCrusherBlockEntity::new, ModBlocksAdditions.MOB_CRUSHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BE =
            BLOCK_ENTITY.register("crusher_block_entity", () ->
                    BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocksAdditions.CRUSHER.get()).build(null));

    public static final RegistryObject<BlockEntityType<PressBlockEntity>> PRESS_BE =
            BLOCK_ENTITY.register("press_block_entity", () ->
                    BlockEntityType.Builder.of(PressBlockEntity::new, ModBlocksAdditions.PRESS.get()).build(null));

    public static final RegistryObject<BlockEntityType<AssemblerBlockEntity>> ASSEMBLY_BE =
            BLOCK_ENTITY.register("assembly_block_entity", () ->
                    BlockEntityType.Builder.of(AssemblerBlockEntity::new, ModBlocksAdditions.ASSEMBLY.get()).build(null));

    public static final RegistryObject<BlockEntityType<FactoryBlockEntity>> FACTORY_BE =
            BLOCK_ENTITY.register("factory_block_entity", () ->
                    BlockEntityType.Builder.of(FactoryBlockEntity::new, ModBlocksAdditions.FACTORY.get()).build(null));

    public static final RegistryObject<BlockEntityType<SqueezerBlockEntity>> SQUEEZER_BE =
            BLOCK_ENTITY.register("squeezer_block_entity", () ->
                    BlockEntityType.Builder.of(SqueezerBlockEntity::new, ModBlocksAdditions.SQUEEZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SmelteryBlockEntity>> SMELTERY_BE =
            BLOCK_ENTITY.register("smeltery_block_entity", () ->
                    BlockEntityType.Builder.of(SmelteryBlockEntity::new, ModBlocksAdditions.SMELTERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE =
            BLOCK_ENTITY.register("generator_block_entity", () ->
                    BlockEntityType.Builder.of(GeneratorBlockEntity::new, ModBlocksAdditions.GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<MinerBlockEntity>> MINER_BE =
            BLOCK_ENTITY.register("miner_block_entity", () ->
                    BlockEntityType.Builder.of(MinerBlockEntity::new, ModBlocksAdditions.MINER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BE =
            BLOCK_ENTITY.register("fermentation_barrel_block_entity", () ->
                    BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ModBlocksAdditions.FERMENTATION_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<RecyclerBlockEntity>> RECYCLER_BE =
            BLOCK_ENTITY.register("recycler_block_entity", () ->
                    BlockEntityType.Builder.of(RecyclerBlockEntity::new, ModBlocksAdditions.RECYCLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MatterCondenserBlockEntity>> MATTER_CONDENSER_BE =
            BLOCK_ENTITY.register("matter_condenser_block_entity", () ->
                    BlockEntityType.Builder.of(MatterCondenserBlockEntity::new, ModBlocksAdditions.MATTER_CONDENSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<SolarBlockEntity>> SOLAR_BE =
            BLOCK_ENTITY.register("solar_block_entity", () ->
                    BlockEntityType.Builder.of(SolarBlockEntity::new, ModBlocksAdditions.SOLAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<DisplayBlockEntity>> DISPLAY_BE =
            BLOCK_ENTITY.register("display_block_entity", () ->
                    BlockEntityType.Builder.of(DisplayBlockEntity::new, ModBlocksAdditions.DISPLAY.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY.register(eventBus);
    }
}
