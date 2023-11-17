package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabAdditions {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_addition",
                                                            //Tab Icon
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItemsAdditions.INFINITY_INGOT.get()))
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CRUSHER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.PRESS.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ASSEMBLY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SQUEEZER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.FERMENTING_BARREL.get()));
                        //-------------------------//-------------------------//
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_INGOT.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_NUGGET.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_DUST.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_INFINITY.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_LEAD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_ALUMINUM.get()));

                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFINITY_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.LEAD_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ALUMINUM_BLOCK.get()));

                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_INFINITY_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_LEAD_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get()));

                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFINITY_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.LEAD_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ALUMINUM_ORE.get()));
                        
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SINGULARITY.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.PORTAL_ACTIVATOR.get()));

                        pOutput.accept(new ItemStack(ModBlocksAdditions.ASPHALT.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.ITEM_DISLOCATOR.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SWORD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_PAXEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_PICKAXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_AXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SHOVEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_HOE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_BOW.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_3D_SWORD.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_HELMET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_CHESTPLATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_LEGGINGS.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_BOOTS.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_HOE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_BOW.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_3D_SWORD.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.BUCKET_LUBRICANT.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.REDSTONE_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BASIC_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.REINFORCED_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LOGIC_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ADVANCED_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.REFINED_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INTEGRAL_COMPONENT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_COMPONENT.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.SPEED_UPGRADE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STRENGTH_UPGRADE.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.ALCOHOL_BOTTLE.get()));

                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
