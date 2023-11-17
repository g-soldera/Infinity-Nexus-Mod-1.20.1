package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.config.ModCommonConfigs;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsAdditions {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Item> INFINITY_INGOT = ITEMS.register("infinity_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INVAR_INGOT = ITEMS.register("invar_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register("brass_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",() -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> INFINITY_NUGGET = ITEMS.register("infinity_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_NUGGET = ITEMS.register("aluminum_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_NUGGET = ITEMS.register("nickel_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INVAR_NUGGET = ITEMS.register("invar_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_NUGGET = ITEMS.register("brass_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INFINITY_DUST = ITEMS.register("infinity_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_DUST = ITEMS.register("aluminum_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_DUST = ITEMS.register("nickel_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_DUST = ITEMS.register("tin_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INVAR_DUST = ITEMS.register("invar_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_DUST = ITEMS.register("uranium_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_DUST = ITEMS.register("brass_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register("bronze_dust",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_DUST = ITEMS.register("steel_dust",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_INFINITY = ITEMS.register("raw_infinity",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ALUMINUM = ITEMS.register("raw_aluminum",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_NICKEL = ITEMS.register("raw_nickel",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_URANIUM = ITEMS.register("raw_uranium",() -> new Item(new Item.Properties()));

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
    public static final RegistryObject<Item> INFINITY_PAXEL = ITEMS.register("infinity_paxel", () -> new PaxelItem(ModToolTiers.INFINITY, 45f, 25f, Item.Properties::fireResistant, Component.translatable("tooltip.infinity_nexus_mod.infinity_paxel"), false));
    public static final RegistryObject<Item> INFINITY_PICKAXE = ITEMS.register("infinity_pickaxe", () -> new PickaxeItems(ModToolTiers.INFINITY, 25, 20, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_SHOVEL = ITEMS.register("infinity_shovel", () -> new ShovelItems(ModToolTiers.INFINITY, 25, 20, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_AXE = ITEMS.register("infinity_axe", () -> new AxeItems(ModToolTiers.INFINITY, 25, 20, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_HOE = ITEMS.register("infinity_hoe", () -> new HoeItems(ModToolTiers.INFINITY, 25, 20, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> INFINITY_BOW = ITEMS.register("infinity_bow", () -> new ModBow(ModToolTiers.INFINITY, new Item.Properties().durability(-1).fireResistant(), 50));

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
    public static final RegistryObject<Item> IMPERIAL_INFINITY_PAXEL = ITEMS.register("imperial_infinity_paxel", () -> new PaxelItem(ModToolTiers.IMPERIAL, 55f, 35f, Item.Properties::fireResistant, Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_paxel"), true));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_PICKAXE = ITEMS.register("imperial_infinity_pickaxe", () -> new PickaxeItems(ModToolTiers.IMPERIAL, 30,24, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_SHOVEL = ITEMS.register("imperial_infinity_shovel", () -> new ShovelItems(ModToolTiers.IMPERIAL, 30,24, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_AXE = ITEMS.register("imperial_infinity_axe", () -> new AxeItems(ModToolTiers.IMPERIAL, 30,24, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_HOE = ITEMS.register("imperial_infinity_hoe", () -> new HoeItems(ModToolTiers.IMPERIAL, 30,24, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_BOW = ITEMS.register("imperial_infinity_bow", () -> new ModBow(ModToolTiers.IMPERIAL, new Item.Properties().durability(-1).fireResistant(),60));

    public static final RegistryObject<Item> IMPERIAL_INFINITY_HELMET = ITEMS.register("imperial_infinity_helmet", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_CHESTPLATE = ITEMS.register("imperial_infinity_chestplate", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_LEGGINGS = ITEMS.register("imperial_infinity_leggings", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> IMPERIAL_INFINITY_BOOTS = ITEMS.register("imperial_infinity_boots", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STRENGTH_UPGRADE = ITEMS.register("strength_upgrade", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BUCKET_LUBRICANT = ITEMS.register("bucket_lubricant", () -> new BucketItem(ModFluids.LUBRICANT_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final RegistryObject<Item> BUCKET_ETHANOL = ITEMS.register("bucket_ethanol", () -> new BucketItem(ModFluids.ETHANOL_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));

    public static final RegistryObject<Item> ALCOHOL_BOTTLE = ITEMS.register("alcohol_bottle", () -> new BottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));



    public static final RegistryObject<Item> MEMBER_BAG = ITEMS.register("bag_member", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SUPPORTER_BAG = ITEMS.register("bag_apoiador", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VIP_BAG = ITEMS.register("bag_vip", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VIPPLUS_BAG = ITEMS.register("bag_vipplus", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VIPNEXUS_BAG = ITEMS.register("bag_vipnexus", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VIPINFINITY_BAG = ITEMS.register("bag_vipinfinity", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC)));


    public static final RegistryObject<Item> REDSTONE_COMPONENT = ITEMS.register("redstone_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> BASIC_COMPONENT = ITEMS.register("basic_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> REINFORCED_COMPONENT = ITEMS.register("reinforced_component", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LOGIC_COMPONENT = ITEMS.register("logic_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ADVANCED_COMPONENT = ITEMS.register("advanced_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REFINED_COMPONENT = ITEMS.register("refined_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> INTEGRAL_COMPONENT = ITEMS.register("integral_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INFINITY_COMPONENT = ITEMS.register("infinity_component", () -> new ComponentItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}