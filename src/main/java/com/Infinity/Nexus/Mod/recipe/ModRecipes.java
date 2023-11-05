package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CrusherRecipes>> CRUSHER_SERIALIZER =
            SERIALIZER.register("crushing", () -> CrusherRecipes.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PressRecipes>> PRESS_SERIALIZER =
            SERIALIZER.register("pressing", () -> PressRecipes.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<AssemblerRecipes>> ASSEMBLY_SERIALIZER =
            SERIALIZER.register("assembler", () -> AssemblerRecipes.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }
}
