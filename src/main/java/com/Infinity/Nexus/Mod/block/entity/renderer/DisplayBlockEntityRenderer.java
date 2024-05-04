package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.custom.ItemDisplay;
import com.Infinity.Nexus.Mod.block.entity.DisplayBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class DisplayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity> {


    float[] x = {1.032f, 0.782f, 0.5f,   0.218f, -0.032f, 1.032f,-0.04f,1.032f,-0.04f,1.032f,-0.04f,1.032f,0.782f,0.5f,0.218f,-0.032f};
    float[] y = {1.175f, 1.145f, 1.115f, 1.145f, 1.175f,  1.145f, 1.145f, 1.115f, 1.115f, 1.145f, 1.145f, 1.175f, 1.145f, 1.115f, 1.145f, 1.175f};
    float[] z = {1.032f, 1.032f, 1.032f, 1.032f, 1.032f, 0.782f,0.782f,0.5f,0.5f,0.218f,0.218f,-0.032f,-0.032f,-0.032f,-0.032f,-0.032f};
    public DisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(DisplayBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getRenderStack(0);

        if(itemStack != ItemStack.EMPTY) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            if (itemStack.getItem() instanceof BlockItem) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 0.375, 0.5);
                pPoseStack.scale(0.5001f, 0.5001f, 0.5001f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.YN.rotationDegrees(180));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
                pPoseStack.popPose();

            } else if (itemStack.getItem() instanceof SwordItem) {

                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 0.6, 0.5);
                pPoseStack.scale(0.8f, 0.8f, 0.8f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.ZN.rotationDegrees(225));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();

            } else if (itemStack.getItem() instanceof DiggerItem) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 0.6, 0.5);
                pPoseStack.scale(0.8f, 0.8f, 0.8f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.ZN.rotationDegrees(45));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();
            }else{
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 0.4, 0.5);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.XN.rotationDegrees(45));
                pPoseStack.mulPose(Axis.YN.rotationDegrees(180));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();
            }
        }else{
            //System.out.println("Empty");
            //System.out.println(itemStack);
        }

    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int blight = pLevel.getBrightness(LightLayer.BLOCK, pPos);
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(blight, slight);
    }
}
