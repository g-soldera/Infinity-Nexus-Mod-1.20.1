package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FermentingBarrelRecipes implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final int duration;
    private final FluidStack inputFluidStack;
    private final FluidStack outputFluidStack;


    public FermentingBarrelRecipes(FluidStack inputFluidStack, FluidStack outputFluidStack, ResourceLocation id, int duration) {
        this.id = id;
        this.duration = duration;
        this.inputFluidStack = inputFluidStack;
        this.outputFluidStack = outputFluidStack;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public FluidStack getInputFluidStack() {
        return inputFluidStack;
    }
    public FluidStack getOutputFluidStack() {
        return outputFluidStack;
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

    public static class Type implements RecipeType<FermentingBarrelRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fermenting";
    }

    public static class Serializer implements RecipeSerializer<FermentingBarrelRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "fermenting");

        @Override
        public FermentingBarrelRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            FluidStack output = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(pSerializedRecipe.get("fluidOutputType").getAsString()))),
                    pSerializedRecipe.get("fluidOutputAmount").getAsInt());

            FluidStack input = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(pSerializedRecipe.get("fluidInputType").getAsString()))),
                    pSerializedRecipe.get("fluidInputAmount").getAsInt());


            int duration = pSerializedRecipe.get("duration").getAsInt();

            return new FermentingBarrelRecipes(input, output, pRecipeId, duration);
        }

        @Override
        public @Nullable FermentingBarrelRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            FluidStack inputFluid = pBuffer.readFluidStack();
            //2
            int duration = pBuffer.readInt();
            FluidStack outputFluid = pBuffer.readFluidStack();
            return new FermentingBarrelRecipes(inputFluid, outputFluid, pRecipeId, duration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FermentingBarrelRecipes pRecipe) {
            //1
            pBuffer.writeFluidStack(pRecipe.inputFluidStack);
            //2
            pBuffer.writeInt(pRecipe.duration);
            pBuffer.writeFluidStack(pRecipe.outputFluidStack);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}