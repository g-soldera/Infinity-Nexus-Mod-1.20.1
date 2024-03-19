package com.Infinity.Nexus.Mod.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.Infinity.Nexus.Mod.entity.layers.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AsgreonRenderer extends MobRenderer<Asgreon, AsgreonModel<Asgreon> > {
    private static final ResourceLocation ASGREON_LOCATION = new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/entity/asgreon/asgreon.png");

    public AsgreonRenderer(EntityRendererProvider.Context context) {
        super(context, new AsgreonModel<>(context.bakeLayer(ModModelLayers.ASGREON_LAYER)), 0.5f);
    }

    @Override
    public void render(Asgreon pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Asgreon entity) {
        return ASGREON_LOCATION;
    }
}
