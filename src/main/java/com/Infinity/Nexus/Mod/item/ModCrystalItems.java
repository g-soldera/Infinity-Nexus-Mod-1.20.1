package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCrystalItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Item> AMBER_CRYSTAL = ITEMS.register("amber_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.COMMON), 1));
    public static final RegistryObject<Item> MARINE_CRYSTAL = ITEMS.register("marine_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.COMMON), 2));
    public static final RegistryObject<Item> CITRIUM_CRYSTAL = ITEMS.register("citrium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.UNCOMMON), 3));
    public static final RegistryObject<Item> RUBIUM_CRYSTAL = ITEMS.register("rubium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.UNCOMMON), 4));
    public static final RegistryObject<Item> DEMETRIUM_CRYSTAL = ITEMS.register("demetrium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.RARE), 5));
    public static final RegistryObject<Item> AGATE_CRYSTAL = ITEMS.register("agate_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.RARE), 6));
    public static final RegistryObject<Item> DARIUM_CRYSTAL = ITEMS.register("darium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.EPIC), 7));


        public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}