package com.Infinity.Nexus.Mod.block.entity.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

public class SetMachineLevel {
    public static void setMachineLevel(ItemStack itemStack, Player player, BlockEntity blockEntity, int COMPONENT_SLOT, ItemStackHandler itemHandler) {
        if (itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()) {
            itemHandler.setStackInSlot(COMPONENT_SLOT, itemStack.copy());
            if (!player.isCreative()) {
                player.getMainHandItem().shrink(1);
            }
        }else{
            ItemStack component = itemHandler.getStackInSlot(COMPONENT_SLOT);
            itemHandler.setStackInSlot(COMPONENT_SLOT, itemStack.copy());
            ItemEntity itemEntity = new ItemEntity(blockEntity.getLevel(), player.getX(), player.getY(), player.getZ(), component);
            if (!player.isCreative()) {
                player.getMainHandItem().shrink(1);
                blockEntity.getLevel().addFreshEntity(itemEntity);
            }
        }
        blockEntity.setChanged();
        blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.BLOCKS, 1.0f, 1.0f);
    }
}
