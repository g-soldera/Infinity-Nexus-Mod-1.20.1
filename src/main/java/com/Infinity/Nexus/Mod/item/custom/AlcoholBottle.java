package com.Infinity.Nexus.Mod.item.custom;

import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;

public class AlcoholBottle extends PotionItem {
    public AlcoholBottle(Properties properties) {
        super(properties);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof ServerPlayer player) {
            ItemStack stack = player.getMainHandItem();
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2000, 1));
            player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 2000, 1));
            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }
}