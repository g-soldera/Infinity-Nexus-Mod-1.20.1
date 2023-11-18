package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.PressBlockEntity;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.utils.ModUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class PressRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int inputCount;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;

    public PressRecipes(NonNullList<Ingredient> inputItems,int inputCount, ItemStack output, ResourceLocation id, int duration, int energy) {
        this.inputItems = inputItems;
        this.inputCount = inputCount;
        this.output = output;
        this.id = id;
        this.duration = duration;
        this.energy = energy;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        int componentSlot = PressBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);
        return (inputItems.get(0).test(stack) || ModUtils.getComponentLevel(stack) >= ModUtils.getComponentLevel(inputItems.get(0).getItems()[0])) &&
                (inputItems.get(1).test(pContainer.getItem(0)))&&
                inputItems.get(2).test(pContainer.getItem(1));
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

    public int getDuration() {
        return duration;
    }

    public int getEnergy() {
        return energy;
    }
    public int getInputCount() {
        return inputCount;
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


    public static class Type implements RecipeType<PressRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pressing";
    }

    public static class Serializer implements RecipeSerializer<PressRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "pressing");

        @Override
        public @NotNull PressRecipes fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            int inputCount = ingredients.get(1).getAsJsonObject().get("count").getAsInt();

            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new PressRecipes(inputs, inputCount, output, pRecipeId, duration, energy);
        }

        @Override
        public @Nullable PressRecipes fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
                //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            //3
            int inputCount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            int energy = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new PressRecipes(inputs, inputCount, output, pRecipeId, duration, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PressRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                //2
                ing.toNetwork(pBuffer);
            }
            //3
            pBuffer.writeInt(pRecipe.inputCount);
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}