package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.*;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocksAdditions {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Block> INFINITY_BLOCK = registerBlock("infinity_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> NICKEL_BLOCK = registerBlock("nickel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> BRASS_BLOCK = registerBlock("brass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER)));


    public static final RegistryObject<Block> RAW_INFINITY_BLOCK = registerBlock("raw_infinity_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_ALUMINUM_BLOCK = registerBlock("raw_aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_NICKEL_BLOCK = registerBlock("raw_nickel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> RAW_URANIUM_BLOCK = registerBlock("raw_uranium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));



    public static final RegistryObject<Block> INFINITY_ORE = registerBlock("infinity_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> NICKEL_ORE = registerBlock("nickel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DEEPSLATE_INFINITY_ORE = registerBlock("deepslate_infinity_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = registerBlock("deepslate_aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_NICKEL_ORE = registerBlock("deepslate_nickel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE)));

    public static final RegistryObject<Block> EXPLORAR_PORTAL_FRAME = registerBlock("explorar_portal_frame", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> EXPLORAR_PORTAL = registerBlock("explorar_portal", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.SPORE_BLOSSOM)));

    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt", () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE)));

    public static final RegistryObject<Block> MOB_CRUSHER = registerBlock("mob_crusher", () -> new MobCrusher(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(MobCrusher.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> CRUSHER = registerBlock("crusher", () -> new Crusher(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Crusher.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> PRESS = registerBlock("press", () -> new Press(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Press.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> ASSEMBLY = registerBlock("assembler", () -> new Assembler(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Assembler.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> SQUEEZER = registerBlock("squeezer", () -> new Squeezer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Squeezer.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> SMELTERY = registerBlock("smeltery", () -> new Smeltery(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Smeltery.LIT) >= 8 ? 2 : 0).noOcclusion()));
    public static final RegistryObject<Block> GENERATOR = registerBlock("generator", () -> new Generator(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Generator.LIT) >= 8 ? 2 : 0).noOcclusion()));

    public static final RegistryObject<Block> FERMENTATION_BARREL = registerBlock("fermentation_barrel", () -> new FermentationBarrel(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.WOOD).strength(1.0f, 6.0f).noOcclusion()));
    public static final RegistryObject<LiquidBlock> LUBRICANT = BLOCKS.register("lubricant", () -> new LiquidBlock(ModFluids.LUBRICANT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> ETHANOL = BLOCKS.register("ethanol", () -> new LiquidBlock(ModFluids.ETHANOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> OIL = BLOCKS.register("oil", () -> new LiquidBlock(ModFluids.OIL_FLOWING, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> VINEGAR = BLOCKS.register("vinegar", () -> new LiquidBlock(ModFluids.VINEGAR_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> SUGARCANE_JUICE = BLOCKS.register("sugarcane_juice", () -> new LiquidBlock(ModFluids.SUGARCANE_JUICE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> WINE = BLOCKS.register("wine", () -> new LiquidBlock(ModFluids.WINE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> EXPERIENCE = BLOCKS.register("experience", () -> new LiquidBlock(ModFluids.EXPERIENCE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

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
