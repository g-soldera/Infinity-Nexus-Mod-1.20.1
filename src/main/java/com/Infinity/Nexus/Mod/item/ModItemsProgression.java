package com.Infinity.Nexus.Mod.item;

import ca.weblite.objc.Proxy;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.custom.ComponentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsProgression {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InfinityNexusMod.MOD_ID);
    //Casts
    public static final RegistryObject<Item> GOLD_WIRE_CAST = ITEMS.register("gold_wire_cast", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> GOLD_SCREW_CAST = ITEMS.register("gold_screw_cast", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> GOLD_SHEET_CAST = ITEMS.register("gold_sheet_cast", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> GOLD_ROD_CAST = ITEMS.register("gold_rod_cast", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));

    //Models
    public static final RegistryObject<Item> RAW_WIRE_CLAY_MODEL = ITEMS.register("raw_wire_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> RAW_SCREW_CLAY_MODEL = ITEMS.register("raw_screw_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> RAW_SHEET_CLAY_MODEL = ITEMS.register("raw_sheet_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> RAW_ROD_CLAY_MODEL = ITEMS.register("raw_rod_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> WIRE_CLAY_MODEL = ITEMS.register("wire_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SCREW_CLAY_MODEL = ITEMS.register("screw_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SHEET_CLAY_MODEL = ITEMS.register("sheet_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> ROD_CLAY_MODEL = ITEMS.register("rod_clay_model", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));

    //Wires
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> ALUMINUM_WIRE = ITEMS.register("aluminum_wire", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> LEAD_WIRE = ITEMS.register("lead_wire", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> IRON_WIRE = ITEMS.register("iron_wire", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEEL_WIRE = ITEMS.register("steel_wire", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INDUSTRIAL_WIRE = ITEMS.register("industrial_wire", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INFINITY_WIRE = ITEMS.register("infinity_wire", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    //Screws
    public static final RegistryObject<Item> COPPER_SCREW = ITEMS.register("copper_screw", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> ALUMINUM_SCREW = ITEMS.register("aluminum_screw", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> LEAD_SCREW = ITEMS.register("lead_screw", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> IRON_SCREW = ITEMS.register("iron_screw", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLD_SCREW = ITEMS.register("gold_screw", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEEL_SCREW = ITEMS.register("steel_screw", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INDUSTRIAL_SCREW = ITEMS.register("industrial_screw", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INFINITY_SCREW = ITEMS.register("infinity_screw", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    //Rods
    public static final RegistryObject<Item> COPPER_ROD = ITEMS.register("copper_rod", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> ALUMINUM_ROD = ITEMS.register("aluminum_rod", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> LEAD_ROD = ITEMS.register("lead_rod", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLD_ROD = ITEMS.register("gold_rod", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEEL_ROD = ITEMS.register("steel_rod", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INDUSTRIAL_ROD = ITEMS.register("industrial_rod", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INFINITY_ROD = ITEMS.register("infinity_rod", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    //Sheets
    public static final RegistryObject<Item> COPPER_SHEET = ITEMS.register("copper_sheet", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> ALUMINUM_SHEET = ITEMS.register("aluminum_sheet", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> LEAD_SHEET = ITEMS.register("lead_sheet", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> IRON_SHEET = ITEMS.register("iron_sheet", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLD_SHEET = ITEMS.register("gold_sheet", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEEL_SHEET = ITEMS.register("steel_sheet", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INDUSTRIAL_SHEET = ITEMS.register("industrial_sheet", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INFINITY_SHEET = ITEMS.register("infinity_sheet", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BIO_MASS = ITEMS.register("bio_mass", () -> new ComponentItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}