package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> MINEABLE_WITH_PAXEL = tag("paxel_mineable");
        public static final TagKey<Block> PICKAXE = tag("mineable/pickaxe");
        public static final TagKey<Block> AXE = tag("mineable/axe");
        public static final TagKey<Block> SHOVEL = tag("mineable/shovel");
        public static final TagKey<Block> HOE = tag("mineable/heo");



        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(InfinityNexusMod.MOD_ID,name));
        }
    }
    public static class Items {
        public static final TagKey<Item> INFINITY_HELMET = tag("infinity_helmet");
        public static final TagKey<Item> INFINITY_CHESTPLATE = tag("infinity_chestplate");
        public static final TagKey<Item> INFINITY_LEGGINGS = tag("infinity_leggings");
        public static final TagKey<Item> INFINITY_BOOTS = tag("infinity_boots");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(InfinityNexusMod.MOD_ID, name));
        }
    }

}
