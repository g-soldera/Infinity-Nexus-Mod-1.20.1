package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemDislocator extends Item {


    public ItemDislocator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand ihand) {
        if (level.isClientSide() && !Screen.hasShiftDown()) {
            CompoundTag itemNBT = player.getMainHandItem().getOrCreateTag();
            player.sendSystemMessage(Component.translatable(itemNBT.getBoolean("onofre")
                    ? "chat.infinity_nexus_mod.item_dislocator_on"
                    : "chat.infinity_nexus_mod.item_dislocator_off"));
        }
        return super.use(level, player, ihand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
