package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class ImperialInfinityArmorItem extends  ArmorItem{
    private static int delay;
    private static int maxDelay = 80*20;
    public ImperialInfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide() && pEntity instanceof Player player && pSlotId < 4) {
            if (hasFullSuitOfArmorOn(player)) {
                player.getAbilities().mayfly = true;
                player.getFoodData().setSaturation(20);
                player.getFoodData().setFoodLevel(20);
                if (delay >= maxDelay) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 1000, 1, false, false));
                    delay = 0;
                }else{
                    delay++;
                }
                player.onUpdateAbilities();
            }else{
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.onUpdateAbilities();
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        boolean armor =
                boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
        return armor;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}