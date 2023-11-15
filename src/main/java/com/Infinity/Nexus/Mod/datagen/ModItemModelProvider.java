package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output,ExistingFileHelper existingFileHelper) {
        super(output, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItemsAdditions.INFINITY_INGOT);
        simpleItem(ModItemsAdditions.LEAD_INGOT);
        simpleItem(ModItemsAdditions.ALUMINUM_INGOT);
        simpleItem(ModItemsAdditions.INFINITY_NUGGET);
        simpleItem(ModItemsAdditions.LEAD_NUGGET);
        simpleItem(ModItemsAdditions.ALUMINUM_NUGGET);
        simpleItem(ModItemsAdditions.INFINITY_DUST);
        simpleItem(ModItemsAdditions.LEAD_DUST);
        simpleItem(ModItemsAdditions.ALUMINUM_DUST);
        simpleItem(ModItemsAdditions.RAW_INFINITY);
        simpleItem(ModItemsAdditions.RAW_LEAD);
        simpleItem(ModItemsAdditions.RAW_ALUMINUM);
        simpleItem(ModItemsAdditions.INFINITY_SINGULARITY);
        simpleItem(ModItemsAdditions.ITEM_DISLOCATOR);
        simpleItem(ModItemsAdditions.PORTAL_ACTIVATOR);
        simpleItem(ModItemsAdditions.INFINITY_SWORD);
        simpleItem(ModItemsAdditions.INFINITY_PAXEL);
        simpleItem(ModItemsAdditions.INFINITY_PICKAXE);
        simpleItem(ModItemsAdditions.INFINITY_AXE);
        simpleItem(ModItemsAdditions.INFINITY_SHOVEL);
        simpleItem(ModItemsAdditions.INFINITY_HOE);
        simpleItem(ModItemsAdditions.INFINITY_HELMET);
        simpleItem(ModItemsAdditions.INFINITY_CHESTPLATE);
        simpleItem(ModItemsAdditions.INFINITY_LEGGINGS);
        simpleItem(ModItemsAdditions.INFINITY_BOOTS);
        simpleItem(ModItemsAdditions.INFINITY_BOW);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_SWORD);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_AXE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_HOE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_HELMET);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_BOW);
        simpleItem(ModItemsAdditions.SPEED_UPGRADE);
        simpleItem(ModItemsAdditions.STRENGTH_UPGRADE);
        simpleItem(ModItemsAdditions.MEMBER_BAG);
        simpleItem(ModItemsAdditions.SUPPORTER_BAG);
        simpleItem(ModItemsAdditions.VIP_BAG);
        simpleItem(ModItemsAdditions.VIPPLUS_BAG);
        simpleItem(ModItemsAdditions.VIPNEXUS_BAG);
        simpleItem(ModItemsAdditions.VIPINFINITY_BAG);
        simpleItem(ModItemsAdditions.REDSTONE_COMPONENT);
        simpleItem(ModItemsAdditions.BASIC_COMPONENT);
        simpleItem(ModItemsAdditions.REINFORCED_COMPONENT);
        simpleItem(ModItemsAdditions.LOGIC_COMPONENT);
        simpleItem(ModItemsAdditions.ADVANCED_COMPONENT);
        simpleItem(ModItemsAdditions.REFINED_COMPONENT);
        simpleItem(ModItemsAdditions.INTEGRAL_COMPONENT);
        simpleItem(ModItemsAdditions.INFINITY_COMPONENT);
        simpleItem(ModItemsAdditions.BUCKET_LUBRICANT);


        simpleItem(ModItemsProgression.GOLD_ROD_CAST);
        simpleItem(ModItemsProgression.GOLD_SCREW_CAST);
        simpleItem(ModItemsProgression.GOLD_SHEET_CAST);
        simpleItem(ModItemsProgression.GOLD_WIRE_CAST);
        simpleItem(ModItemsProgression.RAW_ROD_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_SCREW_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_SHEET_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_WIRE_CLAY_MODEL);
        simpleItem(ModItemsProgression.ROD_CLAY_MODEL);
        simpleItem(ModItemsProgression.SCREW_CLAY_MODEL);
        simpleItem(ModItemsProgression.SHEET_CLAY_MODEL);
        simpleItem(ModItemsProgression.WIRE_CLAY_MODEL);
        simpleItem(ModItemsProgression.COPPER_WIRE);
        simpleItem(ModItemsProgression.ALUMINUM_WIRE);
        simpleItem(ModItemsProgression.LEAD_WIRE);
        simpleItem(ModItemsProgression.IRON_WIRE);
        simpleItem(ModItemsProgression.GOLD_WIRE);
        simpleItem(ModItemsProgression.STEEL_WIRE);
        simpleItem(ModItemsProgression.INDUSTRIAL_WIRE);
        simpleItem(ModItemsProgression.INFINITY_WIRE);
        simpleItem(ModItemsProgression.COPPER_SCREW);
        simpleItem(ModItemsProgression.ALUMINUM_SCREW);
        simpleItem(ModItemsProgression.LEAD_SCREW);
        simpleItem(ModItemsProgression.IRON_SCREW);
        simpleItem(ModItemsProgression.GOLD_SCREW);
        simpleItem(ModItemsProgression.STEEL_SCREW);
        simpleItem(ModItemsProgression.INDUSTRIAL_SCREW);
        simpleItem(ModItemsProgression.INFINITY_SCREW);
        simpleItem(ModItemsProgression.COPPER_ROD);
        simpleItem(ModItemsProgression.ALUMINUM_ROD);
        simpleItem(ModItemsProgression.LEAD_ROD);
        simpleItem(ModItemsProgression.IRON_ROD);
        simpleItem(ModItemsProgression.GOLD_ROD);
        simpleItem(ModItemsProgression.STEEL_ROD);
        simpleItem(ModItemsProgression.INDUSTRIAL_ROD);
        simpleItem(ModItemsProgression.INFINITY_ROD);
        simpleItem(ModItemsProgression.COPPER_SHEET);
        simpleItem(ModItemsProgression.ALUMINUM_SHEET);
        simpleItem(ModItemsProgression.LEAD_SHEET);
        simpleItem(ModItemsProgression.IRON_SHEET);
        simpleItem(ModItemsProgression.GOLD_SHEET);
        simpleItem(ModItemsProgression.STEEL_SHEET);
        simpleItem(ModItemsProgression.INDUSTRIAL_SHEET);
        simpleItem(ModItemsProgression.INFINITY_SHEET);

    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        System.out.println("Biruleibe "+item.getId());
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(InfinityNexusMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
