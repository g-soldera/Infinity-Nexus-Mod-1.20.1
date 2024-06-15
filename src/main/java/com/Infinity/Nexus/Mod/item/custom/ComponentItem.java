package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.utils.ModUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentItem extends Item {
    private final String durability;

    public ComponentItem(Properties pProperties, String durability) {
        super(pProperties);
        this.durability = durability;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.getItem() == ModItemsAdditions.ANCESTRAL_COMPONENT.get()) {
            pTooltipComponents.add(Component.literal("§2Uses:" + (pStack.getOrCreateTag()
                    .contains("isInfinite") ? " §5∞ " : " ") + "§e" + (pStack.getOrCreateTag().contains("Uses") ? pStack.getOrCreateTag()
                    .getInt("Uses") : 10)));
        }else{
            pTooltipComponents.add(Component.literal(durability));
        }
        pTooltipComponents.add(Component.literal("§eLevel: §9"+ ModUtils.getComponentLevel(pStack)));
        pTooltipComponents.add(Component.translatable("chat.infinity_nexus_mod.component_install"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()){
            if(pPlayer.isShiftKeyDown()){
                ItemStack mainHand = pPlayer.getMainHandItem();
                ItemStack offHand = pPlayer.getOffhandItem();

                if(mainHand.getItem().equals(ModItemsAdditions.ANCESTRAL_COMPONENT.get()) && !mainHand.getOrCreateTag().getBoolean("isInfinite")){
                    if(offHand.getItem().equals(ModItemsAdditions.INFINITY_COMPONENT.get())){
                        pPlayer.getMainHandItem().setDamageValue(0);
                        mainHand.getOrCreateTag().putBoolean("isInfinite", true);
                        offHand.shrink(1);
                    }
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getOrCreateTag().getBoolean("isInfinite");
    }
}
