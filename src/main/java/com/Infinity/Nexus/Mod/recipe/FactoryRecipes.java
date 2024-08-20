package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.FactoryBlockEntity;
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

import java.util.ArrayList;
import java.util.List;

public class FactoryRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int[] amountInput;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;

    public FactoryRecipes(NonNullList<Ingredient> inputItems, int[] amountInput, ItemStack output, ResourceLocation id, int duration, int energy) {
        this.inputItems = inputItems;
        this.amountInput = amountInput;
        this.output = output;
        this.id = id;
        this.duration = duration;
        this.energy = energy;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        int matches = 0;
        int componentSlot = FactoryBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);
        List<Integer> slotsVerificados = new ArrayList<>();

        for (int i = 1; i < 17; i++) {
            for (int j = 0; j < 16; j++) {
                if (inputItems.get(i).test(pContainer.getItem(j)) && !slotsVerificados.contains(j) && pContainer.getItem(j).getCount() == amountInput[i]) {
                    matches++;
                    slotsVerificados.add(j);
                    break;
                }
            }
        }
        if (matches < 16) {
            return false;
        }
        /*
        int componentSlot = FactoryBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);

        // Criar um conjunto para rastrear os slots já verificados e os ingredientes associados a eles

        for (int i = 1; i < 17; i++) {
            if (!inputItems.get(i).test(pContainer.getItem(i - 1)) || pContainer.getItem(i - 1).getCount() < amountInput[i]) {
                return false;
            }
        }

         */
        return inputItems.get(0).test(stack);

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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
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

    public int getDuration() {
        return duration;
    }

    public int getEnergy() {
        return energy;
    }

    public int[] getAmountInput() {
        return amountInput;
    }

    public static class Type implements RecipeType<FactoryRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "factory";
    }

    public static class Serializer implements RecipeSerializer<FactoryRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "factory");

        @Override
        public FactoryRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(17, Ingredient.EMPTY);

            int[] amountInput = new int[17];

            for (int i = 0; i < ingredients.size(); i++) {
                JsonObject ingredientJson = ingredients.get(i).getAsJsonObject();
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));

                if (ingredientJson.has("count")) {
                    amountInput[i] = ingredientJson.get("count").getAsInt(); // Armazenamos o valor de "count" em inputSize
                } else {
                    amountInput[i] = 1; // Definimos o valor padrão como 1 caso "count" não esteja presente
                }
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new FactoryRecipes(inputs, amountInput, output, pRecipeId, duration, energy);
        }

        @Override
        public @Nullable FactoryRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            int[] inputSize = pBuffer.readVarIntArray();
            int duration = pBuffer.readInt();
            int energy = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new FactoryRecipes(inputs, inputSize, output, pRecipeId, duration, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FactoryRecipes pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeVarIntArray(pRecipe.amountInput);
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}