package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabEconomy {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_economy",
                                                            //Tab Icon
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get()))
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//
                        //pOutput.accept(new ItemStack(ModBlocksProgression.COPPER_MACHINE_CASING.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.MEMBER_BAG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SUPPORTER_BAG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.VIP_BAG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.VIPPLUS_BAG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.VIPNEXUS_BAG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.VIPINFINITY_BAG.get()));

                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
