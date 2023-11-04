package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.recipe.CrusherRecipes;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(InfinityNexusMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
        registration.addRecipeCategories(new CrusherCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<CrusherRecipes> crusherRecipes = recipeManager.getAllRecipesFor(CrusherRecipes.Type.INSTANCE);
        //-----------------------------------------Registry--------------------------------------------------//
        registration.addRecipes(CrusherCategory.CRUSHER_TYPE, crusherRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
       registration.addRecipeClickArea(CrusherScreen.class,80, 30,24,16, CrusherCategory.CRUSHER_TYPE);
    }
}
