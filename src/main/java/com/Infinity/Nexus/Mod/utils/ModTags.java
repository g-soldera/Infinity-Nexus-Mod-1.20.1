package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> PAXEL_MINEABLE = tag("mineable/paxel_mineable");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS = forgeTag("storage_blocks");
        public static final TagKey<Block> FORGE_ORES = forgeTag("ores");
        public static final TagKey<Block> FORGE_ORES_SINGULAR = forgeTag("ore_rates/singular");
        public static final TagKey<Block> FORGE_ORES_IN_GROUND_STONE = forgeTag("ore_in_ground/stone");
        public static final TagKey<Block> FORGE_ORES_IN_GROUND_DEEPSLATE = forgeTag("ore_in_ground/deepslate");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(InfinityNexusMod.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }
    public static class Items {
        public static final TagKey<Item> PAXELS = forgeTag("paxels");
        public static final TagKey<Item> INFINITY_HELMET = tag("infinity_helmet");
        public static final TagKey<Item> INFINITY_CHESTPLATE = tag("infinity_chestplate");
        public static final TagKey<Item> INFINITY_LEGGINGS = tag("infinity_leggings");
        public static final TagKey<Item> INFINITY_BOOTS = tag("infinity_boots");
        public static final TagKey<Item> FORGE_INGOTS = forgeTag("ingots");
        public static final TagKey<Item> FORGE_NUGGETS = forgeTag("nuggets");
        public static final TagKey<Item> FORGE_RAW = forgeTag("raw_materials");
        public static final TagKey<Item> FORGE_DUSTS = forgeTag("dusts");
        public static final TagKey<Item> FORGE_PLATES = forgeTag("plates");
        public static final TagKey<Item> FORGE_ROD = forgeTag("rods");
        public static final TagKey<Item> FORGE_SCREW = forgeTag("screws");
        public static final TagKey<Item> FORGE_WIRES = forgeTag("wires");
        public static final TagKey<Item> UP_1 = tag("up_1");
        public static final TagKey<Item> UP_2 = tag("up_2");
        public static final TagKey<Item> UP_3 = tag("up_3");
        public static final TagKey<Item> UP_4 = tag("up_4");
        public static final TagKey<Item> UP_5 = tag("up_5");
        public static final TagKey<Item> UP_6 = tag("up_6");
        public static final TagKey<Item> UP_7 = tag("up_7");
        public static final TagKey<Item> UP_8 = tag("up_8");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(InfinityNexusMod.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}
