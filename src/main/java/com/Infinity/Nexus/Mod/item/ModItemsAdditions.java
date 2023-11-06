package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.config.ModCommonConfigs;
import com.Infinity.Nexus.Mod.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsAdditions {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Item> INFINITY_INGOT = ITEMS.register("infinity_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_NUGGET = ITEMS.register("infinity_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_DUST = ITEMS.register("infinity_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_INFINITY = ITEMS.register("raw_infinity",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_SINGULARITY = ITEMS.register("infinity_singularity",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ITEM_DISLOCATOR = ITEMS.register("item_dislocator",() -> new ItemDislocator(new Item.Properties()));
    public static final RegistryObject<Item> PORTAL_ACTIVATOR = ITEMS.register("catalyst",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INFINITY_SWORD = ITEMS.register("infinity_sword",
            () -> new ModSword(ModToolTiers.INFINITY, ModCommonConfigs.infinity_sword_attack_damage_modifier, ModCommonConfigs.infinity_sword_attack_speed_modifier,//+4
                    new Item.Properties().stacksTo(1).fireResistant(),
                    Component.translatable("tooltip.infinity_nexus_mod.infinity_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 2),
                            new MobEffectInstance(MobEffects.WITHER, 200, 2)
            }));
    public static final RegistryObject<Item> INFINITY_3D_SWORD = ITEMS.register("infinity_3d_sword",
            () -> new ModSword(ModToolTiers.INFINITY, 50, 0,//+4
                    new Item.Properties().stacksTo(1).fireResistant(),
                    Component.translatable("tooltip.infinity_nexus_mod.infinity_3d_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 2),
                            new MobEffectInstance(MobEffects.WITHER, 200, 2)
                    }));
    public static final RegistryObject<Item> INFINITY_PAXEL = ITEMS.register("infinity_paxel", () -> new PaxelItem(ModToolTiers.INFINITY, 45f, -2f, Item.Properties::fireResistant, Component.translatable("tooltip.infinity_nexus_mod.infinity_paxel"), false));
    public static final RegistryObject<Item> INFINITY_PICKAXE = ITEMS.register("infinity_pickaxe", () -> new PickaxeItems(ModToolTiers.INFINITY, ModCommonConfigs.tools_infinity_damage, ModCommonConfigs.tools_infinity_speed, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_SHOVEL = ITEMS.register("infinity_shovel", () -> new ShovelItems(ModToolTiers.INFINITY, ModCommonConfigs.tools_infinity_damage, ModCommonConfigs.tools_infinity_speed, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_AXE = ITEMS.register("infinity_axe", () -> new AxeItems(ModToolTiers.INFINITY, ModCommonConfigs.tools_infinity_damage, ModCommonConfigs.tools_infinity_speed, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_HOE = ITEMS.register("infinity_hoe", () -> new HoeItems(ModToolTiers.INFINITY, ModCommonConfigs.tools_infinity_damage, ModCommonConfigs.tools_infinity_speed, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_BOW = ITEMS.register("infinity_bow", () -> new ModBow(ModToolTiers.INFINITY, new Item.Properties().durability(-1).fireResistant(), 1));

    public static final RegistryObject<Item> INFINITY_HELMET = ITEMS.register("infinity_helmet", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> INFINITY_CHESTPLATE = ITEMS.register("infinity_chestplate", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> INFINITY_LEGGINGS = ITEMS.register("infinity_leggings", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> INFINITY_BOOTS = ITEMS.register("infinity_boots", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> IMPERIAL_INFINITY_SWORD = ITEMS.register("imperial_infinity_sword",
            () -> new ModSword(ModToolTiers.IMPERIAL, ModCommonConfigs.infinity_sword_attack_damage_modifier, ModCommonConfigs.infinity_sword_attack_speed_modifier,
                    new Item.Properties().stacksTo(1).fireResistant(),
                    Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 3),
                            new MobEffectInstance(MobEffects.WITHER, 200, 3),
                            new MobEffectInstance(MobEffects.POISON, 200, 3)
                    }));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_3D_SWORD = ITEMS.register("imperial_infinity_3d_sword",
            () -> new ModSword(ModToolTiers.IMPERIAL, 0, 0,
                    new Item.Properties().stacksTo(1).fireResistant(),
                    Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_3d_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 3),
                            new MobEffectInstance(MobEffects.WITHER, 200, 3),
                            new MobEffectInstance(MobEffects.POISON, 200, 3)
                    }));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_PAXEL = ITEMS.register("imperial_infinity_paxel", () -> new PaxelItem(ModToolTiers.IMPERIAL, 55f, 55f, Item.Properties::fireResistant, Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_paxel"), true));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_PICKAXE = ITEMS.register("imperial_infinity_pickaxe", () -> new PickaxeItems(ModToolTiers.IMPERIAL, 0,0, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_SHOVEL = ITEMS.register("imperial_infinity_shovel", () -> new ShovelItems(ModToolTiers.IMPERIAL, 0,0, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_AXE = ITEMS.register("imperial_infinity_axe", () -> new AxeItems(ModToolTiers.IMPERIAL, 0,0, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_HOE = ITEMS.register("imperial_infinity_hoe", () -> new HoeItems(ModToolTiers.IMPERIAL, 0,0, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_BOW = ITEMS.register("imperial_infinity_bow", () -> new ModBow(ModToolTiers.IMPERIAL, new Item.Properties().durability(-1).fireResistant(),1));

    public static final RegistryObject<Item> IMPERIAL_INFINITY_HELMET = ITEMS.register("imperial_infinity_helmet", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_CHESTPLATE = ITEMS.register("imperial_infinity_chestplate", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_LEGGINGS = ITEMS.register("imperial_infinity_leggings", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_BOOTS = ITEMS.register("imperial_infinity_boots", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STRENGTH_UPGRADE = ITEMS.register("strength_upgrade", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}