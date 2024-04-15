package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MatterCondenserRecipes implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> input;
    private final ItemStack output;
    private final int energy;


    public MatterCondenserRecipes(NonNullList<Ingredient> input,ItemStack output,int energy, ResourceLocation id) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.energy = energy;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return input.get(0).test(pContainer.getItem(2));
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return input;
    }
    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return MatterCondenserRecipes.Type.INSTANCE;
    }

    public static class Type implements RecipeType<MatterCondenserRecipes> {
        public static final MatterCondenserRecipes.Type INSTANCE = new MatterCondenserRecipes.Type();
        public static final String ID = "matter_condenser";
    }
    public static class Serializer implements RecipeSerializer<MatterCondenserRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "matter_condenser");



        @Override
        public MatterCondenserRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new MatterCondenserRecipes(inputs, output,energy, pRecipeId);
        }

        @Override
        public @Nullable MatterCondenserRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();
            int energy = pBuffer.readInt();
            return new MatterCondenserRecipes(inputs,output,energy, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MatterCondenserRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                //2
                ing.toNetwork(pBuffer);
            }
            //3
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}