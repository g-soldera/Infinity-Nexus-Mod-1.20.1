package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final List<ItemLike> LEAD_SMELTING = List.of(ModItemsAdditions.RAW_LEAD.get(), ModBlocksAdditions.LEAD_ORE.get(), ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get());
    private final List<ItemLike> ALUMINUM_SMELTING = List.of(ModItemsAdditions.RAW_ALUMINUM.get(), ModBlocksAdditions.ALUMINUM_ORE.get(), ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.INFINITY_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.LEAD_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter, "lead_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ALUMINUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter, "aluminum_block_from_ingot");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Items.GOLD_INGOT).define('A', Blocks.WHITE_CONCRETE).define('B', ModItemsAdditions.INFINITY_DUST.get()).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "explorar_portal_frame");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASPHALT.get()).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Blocks.GRAVEL).define('A', Blocks.SAND).define('B', Items.GRAY_DYE).unlockedBy("has_gray_dye", inventoryTrigger(ItemPredicate.Builder.item().of(Items.GRAY_DYE).build())).save(pWriter, "asphalt");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.PORTAL_ACTIVATOR.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', ModItemsAdditions.INFINITY_DUST.get()).define('A', Items.ENDER_PEARL).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "catalyst");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_ROD_CLAY_MODEL.get()).pattern("  #").pattern(" A ").pattern("#  ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_rod");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get()).pattern("#").pattern("A").pattern(" ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_screw");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_sheet");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get()).pattern("#").pattern("A").pattern("#").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_wire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.CRUSHER.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Items.FLINT)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "crusher");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASSEMBLY.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Blocks.CRAFTING_TABLE)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "assembly");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.PRESS.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Blocks.ANVIL)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "press");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.REDSTONE_COMPONENT.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.REDSTONE)
                .define('D', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_redstone_component", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(pWriter, "redstone_component");




        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINITY_INGOT.get(), 9).requires(ModBlocksAdditions.INFINITY_BLOCK.get()).unlockedBy("has_infinity_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.INFINITY_BLOCK.get()).build())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 9).requires(ModBlocksAdditions.LEAD_BLOCK.get()).unlockedBy("has_lead_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.LEAD_BLOCK.get()).build())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 9).requires(ModBlocksAdditions.ALUMINUM_BLOCK.get()).unlockedBy("has_raw_infinity_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.ALUMINUM_BLOCK.get()).build())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), 9).requires(ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), 9).requires(ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), 9).requires(ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_INFINITY.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_LEAD.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_ALUMINUM.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.INFINITY_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get());

        oreSmelting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 200, "lead_ingot");
        oreBlasting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 100, "lead_ingot");
        oreSmelting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 200, "aluminum_ingot");
        oreBlasting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 100, "aluminum_ingot");

        simpleCookingRecipe(pWriter,"smoking", RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_ROD_CLAY_MODEL.get(), ModItemsProgression.ROD_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get(), ModItemsProgression.SCREW_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get(), ModItemsProgression.SHEET_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get(), ModItemsProgression.WIRE_CLAY_MODEL.get(), 0.25f);

    }





    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  InfinityNexusMod.MOD_ID + ":" +(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}