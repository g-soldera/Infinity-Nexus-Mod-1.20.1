package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.AssemblerBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.PressBlockEntity;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.utils.ModTags;
import com.Infinity.Nexus.Mod.utils.ModUtils;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AssemblerRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;

    public AssemblerRecipes(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, int duration, int energy) {
        this.inputItems = inputItems;
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
        StringBuilder verifiedSlots = new StringBuilder();
        int componentSlot = AssemblerBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);

        // Para cada ingrediente na receita
        for (int i = 1; i < inputItems.size(); i++) {
            // Compare com cada slot de 0 a 6
            for (int j = 0; j < 8; j++) {
                if (inputItems.get(i).test(pContainer.getItem(j)) && !verifiedSlots.toString().contains(String.valueOf(j))) {
                    matches++;
                    verifiedSlots.append(" ").append(j);
                    break;
                }
            }
        }
        if (matches < 8) {
            return false;
        }
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

    public static class Type implements RecipeType<AssemblerRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "assembler";
    }

    public static class Serializer implements RecipeSerializer<AssemblerRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "assembler");

        @Override
        public AssemblerRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new AssemblerRecipes(inputs, output,pRecipeId,duration, energy);
        }

        @Override
        public @Nullable AssemblerRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            int duration = pBuffer.readInt();
            int energy = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new AssemblerRecipes(inputs, output, pRecipeId, duration, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AssemblerRecipes pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}