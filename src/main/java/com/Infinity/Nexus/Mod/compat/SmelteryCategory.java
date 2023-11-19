package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.SmelteryRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SmelteryCategory implements IRecipeCategory<SmelteryRecipes> {

    public static final ResourceLocation UID = new ResourceLocation(InfinityNexusMod.MOD_ID, "smelting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/gui/jei/smeltery_gui.png");

    public static final RecipeType<SmelteryRecipes> SMELTERY_TYPE = new RecipeType<>(UID, SmelteryRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SmelteryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.SMELTERY.get()));
    }

    @Override
    public RecipeType<SmelteryRecipes> getRecipeType() {
        return SMELTERY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.smeltery");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmelteryRecipes recipe, IFocusGroup focuses) {
        ItemStack input =  recipe.getIngredients().get(1).getItems()[0];
        input.setCount(recipe.getInputCount());

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addItemStack(input);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 47).addItemStack(recipe.getResultItem(null));

        builder.addSlot(RecipeIngredientRole.CATALYST, 24, 52).addIngredients(recipe.getIngredients().get(0));

    }
}
