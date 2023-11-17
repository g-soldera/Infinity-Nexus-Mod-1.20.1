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

    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BE =
            BLOCK_ENTITY.register("crusher_block_entity", () ->
                    BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocksAdditions.CRUSHER.get()).build(null));

    public static final RegistryObject<BlockEntityType<PressBlockEntity>> PRESS_BE =
            BLOCK_ENTITY.register("press_block_entity", () ->
                    BlockEntityType.Builder.of(PressBlockEntity::new, ModBlocksAdditions.PRESS.get()).build(null));

    public static final RegistryObject<BlockEntityType<AssemblerBlockEntity>> ASSEMBLY_BE =
            BLOCK_ENTITY.register("assembly_block_entity", () ->
                    BlockEntityType.Builder.of(AssemblerBlockEntity::new, ModBlocksAdditions.ASSEMBLY.get()).build(null));

    public static final RegistryObject<BlockEntityType<SqueezerBlockEntity>> SQUEEZER_BE =
            BLOCK_ENTITY.register("squeezer_block_entity", () ->
                    BlockEntityType.Builder.of(SqueezerBlockEntity::new, ModBlocksAdditions.SQUEEZER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FermentingBarrelBlockEntity>> FERMENTING_BE =
            BLOCK_ENTITY.register("fermenting_barrel_block_entity", () ->
                    BlockEntityType.Builder.of(FermentingBarrelBlockEntity::new, ModBlocksAdditions.FERMENTING_BARREL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY.register(eventBus);
    }
}
