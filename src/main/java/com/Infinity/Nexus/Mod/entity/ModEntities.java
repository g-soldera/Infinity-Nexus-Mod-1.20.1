package com.Infinity.Nexus.Mod.entity;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.Infinity.Nexus.Mod.entity.custom.Flaron;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<EntityType<Asgreon>> ASGREON = ENTITIES_TYPES.register("asgreon",
            () -> EntityType.Builder.of(Asgreon::new, MobCategory.CREATURE).sized(0.8f, 1.7f).build("asgreon"));
    public static final RegistryObject<EntityType<Flaron>> FLARON = ENTITIES_TYPES.register("flaron",
            () -> EntityType.Builder.of(Flaron::new, MobCategory.CREATURE).sized(0.8f, 1.7f).build("flaron"));

    public static void register(IEventBus eventBus){
        ENTITIES_TYPES.register(eventBus);
    }
}
