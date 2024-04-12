package com.Infinity.Nexus.Mod;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.entity.client.AsgreonRenderer;
import com.Infinity.Nexus.Mod.fluid.ModFluidType;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModCrystalItems;
import com.Infinity.Nexus.Mod.item.ModItemProperties;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.loot.ModLootModifiers;
import com.Infinity.Nexus.Mod.networking.ModMessages;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerScreen;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserScreen;
import com.Infinity.Nexus.Mod.screen.factory.FactoryScreen;
import com.Infinity.Nexus.Mod.screen.miner.MinerScreen;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherScreen;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelScreen;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorScreen;
import com.Infinity.Nexus.Mod.screen.press.PressScreen;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerScreen;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryScreen;
import com.Infinity.Nexus.Mod.screen.solar.solar.SolarScreen;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerScreen;
import com.Infinity.Nexus.Mod.tab.ModTabAdditions;
import com.Infinity.Nexus.Mod.tab.ModTabProgression;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(InfinityNexusMod.MOD_ID)
public class InfinityNexusMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "infinity_nexus_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public InfinityNexusMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocksAdditions.register(modEventBus);
        ModBlocksProgression.register(modEventBus);

        ModItemsAdditions.register(modEventBus);
        ModItemsProgression.register(modEventBus);
        ModCrystalItems.register(modEventBus);

        ModTabAdditions.register(modEventBus);
        ModTabProgression.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ModEntities.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidType.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "InfinityNexus/infinity_nexus_mod-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            ModItemProperties.addCustomItemProperties();

            MenuScreens.register(ModMenuTypes.MOB_CRUSHER_MENU.get(), MobCrusherScreen::new);
            MenuScreens.register(ModMenuTypes.CRUSHER_MENU.get(), CrusherScreen::new);
            MenuScreens.register(ModMenuTypes.PRESS_MENU.get(), PressScreen::new);
            MenuScreens.register(ModMenuTypes.ASSEMBLY_MENU.get(), AssemblerScreen::new);
            MenuScreens.register(ModMenuTypes.FACTORY_MENU.get(), FactoryScreen::new);
            MenuScreens.register(ModMenuTypes.SQUEEZER_MENU.get(), SqueezerScreen::new);
            MenuScreens.register(ModMenuTypes.SMELTERY_MENU.get(), SmelteryScreen::new);
            MenuScreens.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.MINER_MENU.get(), MinerScreen::new);
            MenuScreens.register(ModMenuTypes.FERMENTATION_BARREL_MENU.get(), FermentationBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.RECYCLER_MENU.get(), RecyclerScreen::new);
            MenuScreens.register(ModMenuTypes.MATTER_CONDENSER_MENU.get(), CondenserScreen::new);
            MenuScreens.register(ModMenuTypes.SOLAR_MENU.get(), SolarScreen::new);



            ItemBlockRenderTypes.setRenderLayer(ModFluids.LUBRICANT_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LUBRICANT_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.ETHANOL_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.ETHANOL_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SUGARCANE_JUICE_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SUGARCANE_JUICE_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.WINE_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.WINE_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.EXPERIENCE_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.EXPERIENCE_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.STARLIQUID_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.STARLIQUID_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.STRUCTURAL_BLOCK.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.VOXEL_BLOCK.get(), RenderType.cutoutMipped());

            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.RED_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.BLUE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.GREEN_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.YELLOW_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.PURPLE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.ORANGE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.WHITE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.PINK_LIGHT_CRYSTAL.get(), RenderType.translucent());

            EntityRenderers.register(ModEntities.ASGREON.get(), AsgreonRenderer::new);

        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("   §4_____§5_   __§9__________§3_   ______§b_______  __");
        LOGGER.info("  §4/_  _§5/ | / §9/ ____/  _§3/ | / /  _§b/_  __| \\/ /");
        LOGGER.info("   §4/ /§5/  |/ §9/ /_   / /§3/  |/ // /  §b/ /   \\  / ");
        LOGGER.info(" §4_/ /§5/ /|  §9/ __/ _/ /§3/ /|  // /  §b/ /    / /  ");
        LOGGER.info("§4/___§5/_/ |_§9/_/   /___§3/_/ |_/___/ §b/_/    /_/   ");
        LOGGER.info("§b          Infinity Nexus Mod");

    }
}
