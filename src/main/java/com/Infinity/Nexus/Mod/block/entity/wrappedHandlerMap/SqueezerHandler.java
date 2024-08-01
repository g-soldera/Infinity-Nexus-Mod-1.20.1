package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class SqueezerHandler {

    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 3 -> true; //Output
            case 4,5,6,7,8 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
            case 2 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
            case 4,5,6,7 -> ModUtils.isUpgrade(stack);
            case 8 -> ModUtils.isComponent(stack);
            default -> false;
        };
    }
}
