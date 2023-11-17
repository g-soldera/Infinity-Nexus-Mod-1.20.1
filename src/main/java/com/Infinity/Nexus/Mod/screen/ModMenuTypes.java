package com.Infinity.Nexus.Mod.screen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerMenu;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<MenuType<CrusherMenu>> CRUSHER_MENU =
            registerMenuType("crusher_menu", CrusherMenu::new);

    public static final RegistryObject<MenuType<PressMenu>> PRESS_MENU =
            registerMenuType("press_menu", PressMenu::new);

    public static final RegistryObject<MenuType<AssemblerMenu>> ASSEMBLY_MENU =
            registerMenuType("assembler_menu", AssemblerMenu::new);

    public static final RegistryObject<MenuType<SqueezerMenu>> SQUEEZER_MENU =
            registerMenuType("squeezer_menu", SqueezerMenu::new);
    public static final RegistryObject<MenuType<FermentationBarrelMenu>> FERMENTATION_BARREL_MENU =
            registerMenuType("fermentation_barre_menu", FermentationBarrelMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
