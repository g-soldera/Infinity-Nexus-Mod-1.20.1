package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class LinkingTool extends Item {
    public LinkingTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            return InteractionResult.FAIL;
        }

        BlockPos pos = pContext.getClickedPos();
        ItemStack itemInHand = pContext.getItemInHand();
        CompoundTag itemTag  = pContext.getItemInHand().getOrCreateTag();

        if (Screen.hasShiftDown()) {
            // Add pos to item tag
            itemTag.putInt("X", pos.getX());
            itemTag.putInt("Y", pos.getY());
            itemTag.putInt("Z", pos.getZ());
            //add side face
            itemTag.putInt("Side", this.getSideValue(pContext.getClickedFace()));

            // Add pos to item subtitle
            itemInHand.setHoverName(Component.literal("Side: " + pContext.getClickedFace().getName()+" Position: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
        } else {
            // Remove pos from item tag
            itemTag.remove("X");
            itemTag.remove("Y");
            itemTag.remove("Z");
            itemTag.remove("Side");

            // Remove item subtitle
            itemInHand.resetHoverName();
        }

        pContext.getLevel().playLocalSound(
                Objects.requireNonNull(pContext.getPlayer()).getX(),
                pContext.getPlayer().getY(),
                pContext.getPlayer().getZ(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.AMBIENT,
                1.0F,
                1.0F,
                false
        );

        return InteractionResult.SUCCESS;
    }

    private int getSideValue(Direction clickedFace) {
        switch (clickedFace) {
            case UP:
                return 0;
            case DOWN:
                return 1;
            case NORTH:
                return 2;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            case EAST:
                return 5;
            default:
                return 0;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
