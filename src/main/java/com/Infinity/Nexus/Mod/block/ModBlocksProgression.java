package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocksProgression {
        public static final DeferredRegister<Block> BLOCKS =
                DeferredRegister.create(ForgeRegistries.BLOCKS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Block> SILVER_MACHINE_CASING = registerBlock("silver_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> TIN_MACHINE_CASING = registerBlock("tin_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> LEAD_MACHINE_CASING = registerBlock("lead_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> NICKEL_MACHINE_CASING = registerBlock("nickel_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> BRASS_MACHINE_CASING = registerBlock("brass_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> BRONZE_MACHINE_CASING = registerBlock("bronze_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> STEEL_MACHINE_CASING = registerBlock("steel_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.ANVIL).mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> ALUMINUM_MACHINE_CASING = registerBlock("aluminum_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> PLASTIC_MACHINE_CASING = registerBlock("plastic_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.LODESTONE).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> GLASS_MACHINE_CASING = registerBlock("glass_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(1.0f, 6.0f).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> INDUSTRIAL_MACHINE_CASING = registerBlock("industrial_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(6.0f, 6.0f).sound(SoundType.LANTERN).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryObject<Block> INFINITY_MACHINE_CASING = registerBlock("infinity_machine_casing",() -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(4.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
            RegistryObject<T> toReturn = BLOCKS.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        }

        private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
            return ModItemsAdditions.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }

        public static void register(IEventBus eventBus) {
            BLOCKS.register(eventBus);
        }
}
