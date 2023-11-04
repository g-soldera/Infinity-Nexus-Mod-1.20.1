package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.utils.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinityArmorItem extends ArmorItem {
    public InfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(!pLevel.isClientSide() && pEntity instanceof Player) {
            Player player = (Player) pEntity;
            if (hasFullSuitOfArmorOn(player)) {
                player.getAbilities().mayfly = true;
                //Ad player effects
                player.fireImmune();
                player.getFoodData().setSaturation(5);
                player.getFoodData().setFoodLevel(19);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1, false, false));
                player.onUpdateAbilities();
            }else{
                player.getAbilities().mayfly = false;
                player.onUpdateAbilities();
            }
        }
    }


    public static boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0).getItem().getDefaultInstance();
        ItemStack leggings = player.getInventory().getArmor(1).getItem().getDefaultInstance();
        ItemStack breastplate = player.getInventory().getArmor(2).getItem().getDefaultInstance();
        ItemStack helmet = player.getInventory().getArmor(3).getItem().getDefaultInstance();

        boolean armor =
                boots.is(ModTags.Items.INFINITY_BOOTS)
                && leggings.is(ModTags.Items.INFINITY_LEGGINGS)
                && breastplate.is(ModTags.Items.INFINITY_CHESTPLATE)
                && helmet.is(ModTags.Items.INFINITY_HELMET);
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