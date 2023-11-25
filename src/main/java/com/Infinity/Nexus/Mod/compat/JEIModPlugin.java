package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.recipe.*;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerScreen;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelScreen;
import com.Infinity.Nexus.Mod.screen.press.PressScreen;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryScreen;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
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
        registration.addRecipeCategories(new SqueezerCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SmelteryCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FermentationBarrelCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        //-----------------------------------------Registry--------------------------------------------------//
        List<CrusherRecipes> crusherRecipes = recipeManager.getAllRecipesFor(CrusherRecipes.Type.INSTANCE);
        List<PressRecipes> pressRecipes = recipeManager.getAllRecipesFor(PressRecipes.Type.INSTANCE);
        List<AssemblerRecipes> assemblyRecipes = recipeManager.getAllRecipesFor(AssemblerRecipes.Type.INSTANCE);
        List<SqueezerRecipes> squeezerRecipes = recipeManager.getAllRecipesFor(SqueezerRecipes.Type.INSTANCE);
        List<SmelteryRecipes> smelteryRecipes = recipeManager.getAllRecipesFor(SmelteryRecipes.Type.INSTANCE);
        List<FermentationBarrelRecipes> fermentationBarrelRecipes = recipeManager.getAllRecipesFor(FermentationBarrelRecipes.Type.INSTANCE);


        registration.addRecipes(CrusherCategory.CRUSHER_TYPE, crusherRecipes);
        System.out.println("Registry: " + crusherRecipes.size() + " for Crusher");
        registration.addRecipes(PressCategory.PRESS_TYPE, pressRecipes);
        System.out.println("Registry: " + pressRecipes.size() + " for Press");
        registration.addRecipes(AssemblerCategory.ASSEMBLY_TYPE, assemblyRecipes);
        System.out.println("Registry: " + assemblyRecipes.size() + " for Assembly");
        registration.addRecipes(SqueezerCategory.SQUEEZER_TYPE, squeezerRecipes);
        System.out.println("Registry: " + squeezerRecipes.size() + " for Squeezer");
        registration.addRecipes(SmelteryCategory.SMELTERY_TYPE, smelteryRecipes);
        System.out.println("Registry: " + smelteryRecipes.size() + " for Smeltery");
        registration.addRecipes(FermentationBarrelCategory.FERMENTATION_BARREL_TYPE, fermentationBarrelRecipes);
        System.out.println("Registry: " + fermentationBarrelRecipes.size() + " for Fermentation Barrel");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //TODO
        IModPlugin.super.registerRecipeTransferHandlers(registration);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
       registration.addRecipeClickArea(CrusherScreen.class,162, -10,8,9, CrusherCategory.CRUSHER_TYPE);
       registration.addRecipeClickArea(PressScreen.class,162, -10,8,9, PressCategory.PRESS_TYPE);
       registration.addRecipeClickArea(AssemblerScreen.class,162, -10,8,9, AssemblerCategory.ASSEMBLY_TYPE);
       registration.addRecipeClickArea(SqueezerScreen.class,162, -10,8,9, SqueezerCategory.SQUEEZER_TYPE);
       registration.addRecipeClickArea(SmelteryScreen.class,162, -10,8,9, SmelteryCategory.SMELTERY_TYPE);
       registration.addRecipeClickArea(FermentationBarrelScreen.class,162, -10,8,9, FermentationBarrelCategory.FERMENTATION_BARREL_TYPE);
    }
}
