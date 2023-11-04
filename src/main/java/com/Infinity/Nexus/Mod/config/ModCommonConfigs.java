package com.Infinity.Nexus.Mod.config;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonConfigs
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue T_I_LEVEL = BUILDER.comment("Infinity Tools Mining Level").defineInRange("tier_infinity_mining_level", 7,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue T_I_DURABILITY = BUILDER.comment("Infinity Tools Durability").defineInRange("tier_infinity_mining_durability", -1,-1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue T_I_E_VALUE = BUILDER.comment("Infinity Tools Enchantment Value").defineInRange("tier_infinity_enchant_value", 80,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_H_SPEED = BUILDER.comment("Infinity Tools Harvest Speed").define("tier_infinity_harvest_speed", 50.0f);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_A_D_BONUS = BUILDER.comment("Infinity Tools Attack Damage Bonus").define("tier_infinity_attack_damage_bonus", 4f);
    private static final ForgeConfigSpec.IntValue T_I_DAMAGE= BUILDER.comment("Infinity Tools Damage").defineInRange("tools_infinity_damage", 25,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_SPEED = BUILDER.comment("Infinity Tools Speed").define("tools_infinity_speed", 7.0f);
    private static final ForgeConfigSpec.IntValue S_I_A_D_MODIFIER = BUILDER.comment("Infinity Sword Attack Damage Modifier").defineInRange("infinity_sword_attack_damage_modifier", 40,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> S_I_A_S_MODIFIER = BUILDER.comment("Infinity Sword Attack Speed Modifier").define("infinity_sword_attack_speed_modifier", 7f);

    private static final ForgeConfigSpec.IntValue T_I_I_LEVEL = BUILDER.comment("Imperial Infinity Tools Mining Level").defineInRange("tier_imperial_infinity_mining_level", 8,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue T_I_I_DURABILITY = BUILDER.comment("Imperial Infinity Tools Durability").defineInRange("tier_imperial_infinity_mining_durability", -1,-1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue T_I_I_E_VALUE = BUILDER.comment("Imperial Infinity Tools Enchantment Value").defineInRange("tier_imperial_infinity_enchant_value", 120,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_I_H_SPEED = BUILDER.comment("Imperial Infinity Tools Harvest Speed").define("tier_imperial_infinity_harvest_speed", 100.0f);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_I_A_D_BONUS = BUILDER.comment("Imperial Infinity Tools Attack Damage Bonus").define("tier_imperial_infinity_attack_damage_bonus", 4f);
    private static final ForgeConfigSpec.IntValue T_I_I_DAMAGE= BUILDER.comment("Imperial Infinity Tools Damage").defineInRange("tools_imperial_infinity_damage", 35,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> T_I_I_SPEED = BUILDER.comment("Imperial Infinity Tools Speed").define("tools_imperial_infinity_speed", 10.0f);
    private static final ForgeConfigSpec.IntValue S_I_I_A_D_MODIFIER = BUILDER.comment("Imperial Infinity Sword Attack Damage Modifier").defineInRange("imperial_infinity_sword_attack_damage_modifier", 50,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.ConfigValue<Float> S_I_I_A_S_MODIFIER = BUILDER.comment("Imperial Infinity Sword Attack Speed Modifier").define("imperial_infinity_sword_attack_speed_modifier", 10f);


    // a list of strings that are treated as resource locations for items
    //private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
    //        .comment("A list of items to log on common setup.")
    //        .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), ModCommonConfigs::validateItemName);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int tier_infinity_mining_level;
    public static int tier_infinity_mining_durability;
    public static int tier_infinity_enchant_value;
    public static float tier_infinity_harvest_speed;
    public static float tier_infinity_attack_damage_bonus;
    public static int tools_infinity_damage;
    public static float tools_infinity_speed;
    public static int infinity_sword_attack_damage_modifier;
    public static float infinity_sword_attack_speed_modifier;

    public static int tier_imperial_infinity_mining_level;
    public static int tier_imperial_infinity_mining_durability;
    public static int tier_imperial_infinity_enchant_value;
    public static float tier_imperial_infinity_harvest_speed;
    public static float tier_imperial_infinity_attack_damage_bonus;
    public static int tools_imperial_infinity_damage;
    public static float tools_imperial_infinity_speed;
    public static int imperial_infinity_sword_attack_damage_modifier;
    public static float imperial_infinity_sword_attack_speed_modifier;



    //public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        System.out.println("\n\n\n\nLoading config\n\n\n\n");
        tier_infinity_mining_level = T_I_LEVEL.get();
        tier_infinity_mining_durability = T_I_DURABILITY.get();
        tier_infinity_enchant_value = T_I_E_VALUE.get();
        tier_infinity_harvest_speed = T_I_H_SPEED.get();
        tier_infinity_attack_damage_bonus = T_I_A_D_BONUS.get();
        tools_infinity_damage = T_I_DAMAGE.get();
        tools_infinity_speed = T_I_SPEED.get();
        infinity_sword_attack_damage_modifier = S_I_A_D_MODIFIER.get();
        infinity_sword_attack_speed_modifier = S_I_A_S_MODIFIER.get();

        tier_imperial_infinity_mining_level = T_I_I_LEVEL.get();
        tier_imperial_infinity_mining_durability = T_I_I_DURABILITY.get();
        tier_imperial_infinity_enchant_value = T_I_I_E_VALUE.get();
        tier_imperial_infinity_harvest_speed = T_I_I_H_SPEED.get();
        tier_imperial_infinity_attack_damage_bonus = T_I_I_A_D_BONUS.get();
        tools_imperial_infinity_damage = T_I_I_DAMAGE.get();
        tools_imperial_infinity_speed = T_I_I_SPEED.get();
        imperial_infinity_sword_attack_damage_modifier = S_I_I_A_D_MODIFIER.get();
        imperial_infinity_sword_attack_speed_modifier = S_I_I_A_S_MODIFIER.get();



        // convert the list of strings into a set of items
        //items = ITEM_STRINGS.get().stream()
        //        .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
        //        .collect(Collectors.toSet());
    }
}
