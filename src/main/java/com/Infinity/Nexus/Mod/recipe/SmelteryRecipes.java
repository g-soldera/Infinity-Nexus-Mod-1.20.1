package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.SmelteryBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import com.Infinity.Nexus.Core.utils.ModUtils;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SmelteryRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int[] inputCount;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;


    public SmelteryRecipes(NonNullList<Ingredient> inputItems,int[] inputCount, ItemStack output, ResourceLocation id, int duration, int energy) {
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
        int componentSlot = SqueezerBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);

        for (int i = 1; i < inputItems.size(); i++) {

            if (!inputItems.get(i).test(pContainer.getItem(i-1))) {
                return false;
            }

        }

        return (inputItems.get(0).test(stack)) &&
                (inputItems.get(1).test(pContainer.getItem(0)));
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return inputItems;
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

    public int getEnergy() {
        return energy;
    }

    public int[] getInputCount() {
        return inputCount;
    }

    public int getDuration() {
        return duration;
    }

    public static class Type implements RecipeType<SmelteryRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "melting";
    }

    public static class Serializer implements RecipeSerializer<SmelteryRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "melting");

        @Override
        public SmelteryRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            int inputCount[] = {1,1,1,1};

            NonNullList<Ingredient> inputs = NonNullList.withSize(Math.min(ingredients.size(), 4), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputCount[i] = ingredients.get(i).getAsJsonObject().get("count").getAsInt();;
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new SmelteryRecipes(inputs,inputCount, output, pRecipeId, duration, energy);
        }

        @Override
        public @Nullable SmelteryRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            //3
            int[] inputCount = pBuffer.readVarIntArray();
            int duration = pBuffer.readInt();
            int energy = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new SmelteryRecipes(inputs,inputCount, output, pRecipeId, duration, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SmelteryRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                //2
                ing.toNetwork(pBuffer);
            }
            //3
            pBuffer.writeVarIntArray(pRecipe.inputCount);
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}