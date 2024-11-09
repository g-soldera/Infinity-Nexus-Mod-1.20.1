package com.Infinity.Nexus.Mod.effect;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
    DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<MobEffect> FLY_EFFECT = MOB_EFFECTS.register("fly_effect",
            () -> new FlyEffect(MobEffectCategory.BENEFICIAL, ChatFormatting.RED.getColor()).addAttributeModifier(Attributes.FLYING_SPEED,
                    "ea575584-4ff4-4c96-a1a3-f2024d9fd898", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
