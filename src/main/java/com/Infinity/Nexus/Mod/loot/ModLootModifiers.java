package com.Infinity.Nexus.Mod.loot;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIERS_SERIALIZER.register("add_item",AddItemModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_SUS_SAND_ITEM =
        LOOT_MODIFIERS_SERIALIZER.register("add_sus_sand_item",AddSusSandItemModifier.CODEC);



    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS_SERIALIZER.register(eventBus);
    }
}
