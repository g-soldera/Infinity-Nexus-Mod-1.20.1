package com.Infinity.Nexus.Mod.datagen.loot;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTablesAdditions extends BlockLootSubProvider {


    public ModBlockLootTablesAdditions(){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocksAdditions.INFINITY_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.INFINITY_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        this.dropSelf(ModBlocksAdditions.LEAD_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.LEAD_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get());

        this.dropSelf(ModBlocksAdditions.ALUMINUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.ALUMINUM_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

        this.dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get());
        this.dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL.get());
        this.dropSelf(ModBlocksAdditions.ASPHALT.get());

        this.dropSelf(ModBlocksAdditions.PRESS.get());
        this.dropSelf(ModBlocksAdditions.CRUSHER.get());
        this.dropSelf(ModBlocksAdditions.ASSEMBLY.get());


        this.dropSelf(ModBlocksProgression.WOOD_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.COPPER_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.LEAD_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.GOLD_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.IRON_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.PLASTIC_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.GLASS_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.ENERGIZED_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.STEEL_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.INFINITY_MACHINE_CASING.get());


        this.add(ModBlocksAdditions.INFINITY_ORE.get(), block -> createOreDrop( ModBlocksAdditions.INFINITY_ORE.get(), ModItemsAdditions.RAW_INFINITY.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Stream<Block> additions = ModBlocksAdditions.BLOCKS.getEntries().stream().map(RegistryObject::get);
        Stream<Block> progression = ModBlocksProgression.BLOCKS.getEntries().stream().map(RegistryObject::get);


        return Stream.concat(additions, progression)::iterator;
    }
}
