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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
        if (pContext.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = pContext.getClickedPos();
        ItemStack itemInHand = pContext.getItemInHand();
        CompoundTag itemTag = itemInHand.getOrCreateTag();
        Player player = pContext.getPlayer();

        if(player == null) {
            if (Screen.hasShiftDown()) {
                itemTag.putInt("X", pos.getX());
                itemTag.putInt("Y", pos.getY());
                itemTag.putInt("Z", pos.getZ());
                itemTag.putInt("Side", this.getSideValue(pContext.getClickedFace()));

                itemInHand.setHoverName(Component.literal(
                        "Side: " + pContext.getClickedFace().getName() +
                                " Position: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()
                ));

                pContext.getPlayer().setItemInHand(pContext.getHand(), itemInHand);

            } else {
                itemTag.remove("X");
                itemTag.remove("Y");
                itemTag.remove("Z");
                itemTag.remove("Side");

                itemInHand.resetHoverName();

                pContext.getPlayer().setItemInHand(pContext.getHand(), itemInHand);
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
        }

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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.infinity_nexus.linking_tool"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
