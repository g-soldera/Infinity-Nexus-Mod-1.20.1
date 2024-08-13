package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.CrusherBlockEntity;
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

public class CrusherRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int inputCount;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;
    private final boolean canDuplicate;


    public CrusherRecipes(NonNullList<Ingredient> inputItems, int inputCount, ItemStack output, ResourceLocation id, int duration, int energy, boolean canDuplicate) {
        this.inputItems = inputItems;
        this.inputCount = inputCount;
        this.output = output;
        this.id = id;
        this.duration = duration;
        this.energy = energy;
        this.canDuplicate = canDuplicate;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        int componentSlot = CrusherBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);
        return (inputItems.get(0).test(stack)) &&
        inputItems.get(1).test(pContainer.getItem(0)) && pContainer.getItem(0).getCount() >= getInputCount();
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

    public int getInputCount() {
        return inputCount;
    }

    public int getDuration() {
        return duration;
    }

    public boolean canDuplicate() {
        return canDuplicate;
    }

    public static class Type implements RecipeType<CrusherRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "crushing";
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "crushing");

        @Override
        public CrusherRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");


            int inputCount = ingredients.get(1).getAsJsonObject().get("count").getAsInt();

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();
            boolean canDuplicate = pSerializedRecipe.get("can_duplicate").getAsBoolean();

            return new CrusherRecipes(inputs, inputCount, output, pRecipeId, duration, energy, canDuplicate);
        }

        @Override
        public @Nullable CrusherRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
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
            boolean canDuplicate = pBuffer.readBoolean();
            ItemStack output = pBuffer.readItem();
            return new CrusherRecipes(inputs, inputCount, output, pRecipeId, duration, energy, canDuplicate);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrusherRecipes pRecipe) {
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
            pBuffer.writeBoolean(pRecipe.canDuplicate);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}