package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.utils.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinityArmorItem extends ArmorItem {
    private static int delay;
    private static int maxDelay = 80*20;

    public InfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(!pLevel.isClientSide() && pEntity instanceof Player player) {
                if (hasFullSuitOfArmorOn(player)) {
                    player.getAbilities().mayfly = true;
                    //Ad player effects
                    player.fireImmune();
                    player.getFoodData().setSaturation(5);
                    player.getFoodData().setFoodLevel(19);
                    if (delay >= maxDelay) {
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000, 1, false, false));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1000, 1, false, false));
                        delay = 0;
                    }else{
                        delay++;
                    }
                    player.onUpdateAbilities();
                } else {
                    player.getAbilities().mayfly = false;
                    player.onUpdateAbilities();
                }
        }
    }


    public static boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();

        boolean armor =
                boots == ModItemsAdditions.INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.INFINITY_HELMET.get();
        return armor;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("item.infinity_nexus.infinity_armor"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}