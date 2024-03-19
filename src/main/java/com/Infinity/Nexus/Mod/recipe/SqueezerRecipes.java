package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SqueezerRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int inputCount;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;
    private final int energy;
    private final FluidStack fluid;

    public SqueezerRecipes(NonNullList<Ingredient> inputItems, int inputCount, ItemStack output, ResourceLocation id, int duration, int energy, FluidStack fluidStack) {
        this.inputItems = inputItems;
        this.inputCount = inputCount;
        this.output = output;
        this.id = id;
        this.duration = duration;
        this.energy = energy;
        this.fluid = fluidStack;

    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        int componentSlot = SqueezerBlockEntity.getComponentSlot();
        ItemStack stack = pContainer.getItem(componentSlot);
        return (inputItems.get(0).test(stack)) &&
                (inputItems.get(1).test(pContainer.getItem(0)));
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

    public FluidStack getFluid() {
        return fluid;
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


    public static class Type implements RecipeType<SqueezerRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "squeezing";
    }

    public static class Serializer implements RecipeSerializer<SqueezerRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "squeezing");

        @Override
        public @NotNull SqueezerRecipes fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            int inputCount = ingredients.get(1).getAsJsonObject().get("count").getAsInt();

            FluidStack fluidStack = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(pSerializedRecipe.get("fluidType").getAsString()))),
            pSerializedRecipe.get("fluidAmount").getAsInt());

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = pSerializedRecipe.get("duration").getAsInt();
            int energy = pSerializedRecipe.get("energy").getAsInt();

            return new SqueezerRecipes(inputs, inputCount, output, pRecipeId, duration, energy, fluidStack);
        }

        @Override
        public @Nullable SqueezerRecipes fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            FluidStack fluidStack = pBuffer.readFluidStack();

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            //3
            int inputCount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            int energy = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();

            return new SqueezerRecipes(inputs, inputCount, output, pRecipeId, duration, energy, fluidStack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SqueezerRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());
            pBuffer.writeFluidStack(pRecipe.fluid);

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