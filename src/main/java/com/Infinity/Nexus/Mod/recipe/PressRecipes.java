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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PressRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int time;
    private final int count;
    private final int resultCount;

    public PressRecipes(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, int time, int count, int resultCount) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.time = time;
        this.count = count;
        this.resultCount = count;

    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return pContainer.getItem(0).getCount() >= count && inputItems.get(0).test(pContainer.getItem(0)) && inputItems.get(1).test(pContainer.getItem(1));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public  ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }
    public int getTime() {
        return time;
    }
    public int getCount() {
        return count;
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
        return Type.INSTANCE;
    }

    public int getResultCount() {
        return resultCount;
    }

    public static class Type implements RecipeType<PressRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pressing";
    }

    public static class Serializer implements RecipeSerializer<PressRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "pressing");

        @Override
        public PressRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int resultCount = GsonHelper.getAsJsonObject(pSerializedRecipe, "output").get("count").getAsInt();
            int time = GsonHelper.getAsJsonObject(pSerializedRecipe, "time").get("value").getAsInt();
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            int count = ingredients.get(0).getAsJsonObject().get("count").getAsInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new PressRecipes(inputs, output, pRecipeId, time, count, resultCount);
        }

        @Override
        public @Nullable PressRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            int time = 0;
            int count = 0;
            int resultCount = 0;
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new PressRecipes(inputs, output, pRecipeId, time, count, resultCount);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PressRecipes pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}