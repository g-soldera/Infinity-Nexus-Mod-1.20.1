package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class ComponentItem extends Item {
    public ComponentItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()){
            System.out.println("Client");
            return InteractionResult.FAIL;
        }
        if(pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof SqueezerBlockEntity entity){
            if(pContext.getPlayer().getMainHandItem().getItem() instanceof ComponentItem){
                pContext.getPlayer().getMainHandItem().shrink(1);
                entity.setUpgradeLevel(pContext.getPlayer().getMainHandItem());
                return InteractionResult.SUCCESS;

            }
        }
        return InteractionResult.SUCCESS;
    }
}
