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

public class FermentationBarrelRecipes implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final int duration;
    private final FluidStack inputFluidStack;
    private final NonNullList<Ingredient> input;
    private final int inputCount;
    private final ItemStack output;


    public FermentationBarrelRecipes(NonNullList<Ingredient> input,int inputCount, FluidStack inputFluidStack, ItemStack output, ResourceLocation id, int duration) {
        this.id = id;
        this.duration = duration;
        this.inputFluidStack = inputFluidStack;
        this.input = input;
        this.inputCount = inputCount;
        this.output = output;
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

    public FluidStack getInputFluidStack() {
        return inputFluidStack;
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

    public int getInputCount() {
        return inputCount;
    }
    public static class Type implements RecipeType<FermentationBarrelRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fermentation";
    }

    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "fermentation");

        @Override
        public FermentationBarrelRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            int inputCount = ingredients.get(0).getAsJsonObject().get("count").getAsInt();

            FluidStack input = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(pSerializedRecipe.get("fluidInputType").getAsString()))),
                    pSerializedRecipe.get("fluidInputAmount").getAsInt());

            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();

            return new FermentationBarrelRecipes(inputs,inputCount, input, output, pRecipeId, duration);
        }

        @Override
        public @Nullable FermentationBarrelRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            int inputCount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            FluidStack outputFluid = pBuffer.readFluidStack();
            ItemStack output = pBuffer.readItem();
            return new FermentationBarrelRecipes(inputs,inputCount, outputFluid, output, pRecipeId, duration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FermentationBarrelRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                //2
                ing.toNetwork(pBuffer);
            }
            //3
            pBuffer.writeInt(pRecipe.inputCount);
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeFluidStack(pRecipe.inputFluidStack);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}