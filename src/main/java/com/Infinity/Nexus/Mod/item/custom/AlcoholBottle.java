package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.context.UseOnContext;

public class AlcoholBottle extends PotionItem {
    public AlcoholBottle(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() instanceof ServerPlayer player) {
            ItemStack stack = pContext.getItemInHand();

            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return InteractionResult.PASS;
    }
}