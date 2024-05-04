package com.Infinity.Nexus.Mod.block.entity.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

public class SetUpgradeLevel {
    public static void setUpgradeLevel(ItemStack itemStack, Player player, BlockEntity blockEntity, int[] UPGRADE_SLOTS, ItemStackHandler itemHandler) {
        for(int i = 0; i < UPGRADE_SLOTS.length; i++ ){
            if (itemHandler.getStackInSlot(UPGRADE_SLOTS[i]).isEmpty()) {
                ItemStack stack = itemStack.copy();
                stack.setCount(1);
                itemHandler.setStackInSlot(UPGRADE_SLOTS[i], stack);
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);
                }
                blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.BLOCKS, 1.0f, 1.0f);
                blockEntity.setChanged();
                break;
            }
        }
    }
}
