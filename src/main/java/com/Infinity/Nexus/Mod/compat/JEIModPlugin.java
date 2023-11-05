package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.recipe.AssemblerRecipes;
import com.Infinity.Nexus.Mod.recipe.CrusherRecipes;
import com.Infinity.Nexus.Mod.recipe.PressRecipes;
import com.Infinity.Nexus.Mod.screen.assembly.AssemblerScreen;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import com.Infinity.Nexus.Mod.screen.press.PressScreen;
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
        registration.addRecipeCategories(new PressCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AssemblerCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        //-----------------------------------------Registry--------------------------------------------------//
        List<CrusherRecipes> crusherRecipes = recipeManager.getAllRecipesFor(CrusherRecipes.Type.INSTANCE);
        List<PressRecipes> pressRecipes = recipeManager.getAllRecipesFor(PressRecipes.Type.INSTANCE);
        List<AssemblerRecipes> assemblyRecipes = recipeManager.getAllRecipesFor(AssemblerRecipes.Type.INSTANCE);


        registration.addRecipes(CrusherCategory.CRUSHER_TYPE, crusherRecipes);
        registration.addRecipes(PressCategory.PRESS_TYPE, pressRecipes);
        registration.addRecipes(AssemblerCategory.ASSEMBLY_TYPE, assemblyRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
       registration.addRecipeClickArea(CrusherScreen.class,80, 30,24,16, CrusherCategory.CRUSHER_TYPE);
       registration.addRecipeClickArea(PressScreen.class,80, 30,24,16, PressCategory.PRESS_TYPE);
       registration.addRecipeClickArea(AssemblerScreen.class,80, 30,24,16, AssemblerCategory.ASSEMBLY_TYPE);
    }
}
