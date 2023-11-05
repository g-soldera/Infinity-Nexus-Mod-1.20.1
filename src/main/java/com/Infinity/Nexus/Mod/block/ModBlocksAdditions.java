package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.Asphalt;
import com.Infinity.Nexus.Mod.block.custom.Assembler;
import com.Infinity.Nexus.Mod.block.custom.Crusher;
import com.Infinity.Nexus.Mod.block.custom.Press;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.Infinity.Nexus.Mod.block.custom.Crusher.LIT;

public class ModBlocksAdditions {
        public static final DeferredRegister<Block> BLOCKS =
                DeferredRegister.create(ForgeRegistries.BLOCKS, InfinityNexusMod.MOD_ID);

        public static final RegistryObject<Block> INFINITY_BLOCK = registerBlock("infinity_block",() -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK)));
        public static final RegistryObject<Block> RAW_INFINITY_BLOCK = registerBlock("raw_infinity_block",() -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
        public static final RegistryObject<Block> INFINITY_ORE = registerBlock("infinity_ore",() -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
        public static final RegistryObject<Block> DEEPSLATE_INFINITY_ORE = registerBlock("deepslate_infinity_ore",() -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));

        public static final RegistryObject<Block> EXPLORAR_PORTAL_FRAME = registerBlock("explorar_portal_frame",() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE)));
        public static final RegistryObject<Block> EXPLORAR_PORTAL = registerBlock("explorar_portal",() -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.SPORE_BLOSSOM)));

        public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt",() -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE)));

        public static final RegistryObject<Block> CRUSHER = registerBlock("crusher",() -> new Crusher(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(LIT) ? 2 : 0).noOcclusion()));
        public static final RegistryObject<Block> PRESS = registerBlock("press",() -> new Press(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(LIT) ? 2 : 0).noOcclusion()));
        public static final RegistryObject<Block> ASSEMBLY = registerBlock("assembler",() -> new Assembler(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(LIT) ? 2 : 0).noOcclusion()));



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
