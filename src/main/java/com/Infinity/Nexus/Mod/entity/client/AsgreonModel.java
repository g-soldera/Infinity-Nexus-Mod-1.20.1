package com.Infinity.Nexus.Mod.entity.client;

import com.Infinity.Nexus.Mod.entity.animations.ModAnimmationDefinitions;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class AsgreonModel<T extends Asgreon> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "asgreon-vanilla"), "main");
	private final ModelPart asgreon;
	private final ModelPart head;

	public AsgreonModel(ModelPart root) {
		this.asgreon = root.getChild("asgreon");
		this.head = asgreon.getChild("body").getChild("torso").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition asgreon = partdefinition.addOrReplaceChild("asgreon", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.5F));

		PartDefinition body = asgreon.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(-2, 39).addBox(-13.0F, -21.0F, -10.0F, 27.0F, 40.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -41.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -26.0F));

		PartDefinition skull = head.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(69, 29).addBox(-6.0F, -11.75F, -2.25F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -49.25F, 20.25F));

		PartDefinition cube_r1 = skull.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-3, 5).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, -3.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition horn = skull.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, -7.0782F, 2.1129F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = horn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 102).addBox(-13.5F, -1.6194F, -0.5278F, 5.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(-2.5F, -1.6194F, -0.5278F, 5.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -4.0648F, 0.1775F, -1.3963F, 0.0F, 0.0F));

		PartDefinition horn2 = horn.addOrReplaceChild("horn2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 68.6367F, -30.5987F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r3 = horn2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(103, 15).addBox(-12.5F, -0.6194F, -8.4722F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(103, 15).addBox(-1.5F, -0.6194F, -8.4722F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -78.8966F, -2.8323F, -1.3963F, 0.0F, 0.0F));

		PartDefinition left_ear = skull.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -57.0F, 30.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 41.25F, -29.75F));

		PartDefinition right_ear = skull.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -57.0F, 30.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 41.25F, -29.75F));

		PartDefinition left_eye = skull.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.offsetAndRotation(3.4052F, -4.65F, -3.0052F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r4 = left_eye.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(57, 46).addBox(-3.175F, -4.0546F, -2.5333F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(55, 46).addBox(-3.075F, -2.8546F, -2.3333F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0198F, 1.2525F, -3.0532F, -1.309F, 1.309F, -1.5708F));

		PartDefinition left_eyelid = skull.addOrReplaceChild("left_eyelid", CubeListBuilder.create(), PartPose.offset(5.05F, 54.35F, -29.65F));

		PartDefinition cube_r5 = left_eyelid.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(42, 85).addBox(-0.925F, -2.1F, -1.6198F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.625F, -59.0F, 27.0198F, 0.0F, 1.5708F, 0.0F));

		PartDefinition right_eyelid = skull.addOrReplaceChild("right_eyelid", CubeListBuilder.create(), PartPose.offset(-5.05F, 54.35F, -29.65F));

		PartDefinition cube_r6 = right_eyelid.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(42, 85).mirror().addBox(-0.075F, -2.1F, -1.6198F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.625F, -59.0F, 27.0198F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_eye = skull.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4052F, -4.65F, -3.0052F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r7 = right_eye.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(57, 46).mirror().addBox(1.175F, -2.1228F, -3.0509F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(55, 46).mirror().addBox(1.075F, -0.9228F, -2.8509F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0198F, -0.4206F, -1.5696F, -1.309F, -1.309F, 1.5708F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 88).addBox(-1.5F, -0.0508F, -0.0984F, 3.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(57, 0).addBox(-2.5F, -0.0508F, 12.9016F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.1081F, 7.9764F, -1.309F, 0.0F, 0.0F));

		PartDefinition left_back_leg = body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(78, 5).addBox(-2.0F, 0.5F, -2.0F, 6.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -24.5F, -1.5F));

		PartDefinition cube_r8 = left_back_leg.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(103, 67).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 13.5F, -2.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition left_back_knee = left_back_leg.addOrReplaceChild("left_back_knee", CubeListBuilder.create().texOffs(100, 64).addBox(-2.0F, 9.0F, -16.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 14.0F));

		PartDefinition cube_r9 = left_back_knee.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(103, 67).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 18.0F, -16.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition left_back_heel = left_back_knee.addOrReplaceChild("left_back_heel", CubeListBuilder.create().texOffs(52, 103).addBox(-2.0F, 8.0F, -19.5F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(59, 110).addBox(3.0F, 9.0F, -20.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 109).addBox(1.75F, 9.0F, -21.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(57, 108).addBox(0.5F, 9.0F, -22.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(57, 108).addBox(-0.75F, 9.0F, -22.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(58, 109).addBox(-2.0F, 9.0F, -21.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -0.5F));

		PartDefinition right_back_leg = body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(78, 5).mirror().addBox(-3.0F, 1.5F, -1.0F, 6.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, -25.5F, -2.5F));

		PartDefinition cube_r10 = right_back_leg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(103, 67).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5F, -1.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_back_knee = right_back_leg.addOrReplaceChild("right_back_knee", CubeListBuilder.create().texOffs(100, 64).addBox(-3.0F, 9.0F, -16.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 15.0F));

		PartDefinition cube_r11 = right_back_knee.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(103, 67).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 18.0F, -16.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_back_heel = right_back_knee.addOrReplaceChild("right_back_heel", CubeListBuilder.create().texOffs(52, 103).addBox(-3.0F, 8.0F, -19.5F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(59, 110).addBox(-3.0F, 9.0F, -20.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 109).addBox(-1.75F, 9.0F, -21.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(57, 108).addBox(-0.5F, 9.0F, -22.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(57, 108).addBox(0.75F, 9.0F, -22.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(58, 109).addBox(2.0F, 9.0F, -21.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -0.5F));

		PartDefinition right_front_leg = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(100, 111).mirror().addBox(-3.5F, -5.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.5F, -54.0F, -1.0F));

		PartDefinition right_front_knee = right_front_leg.addOrReplaceChild("right_front_knee", CubeListBuilder.create().texOffs(54, 105).mirror().addBox(-12.5F, -39.0F, 19.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.5F, 44.0F, -22.0F));

		PartDefinition left_front_leg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(100, 111).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(17.5F, -54.0F, -1.0F));

		PartDefinition left_front_knee = left_front_leg.addOrReplaceChild("left_front_knee", CubeListBuilder.create().texOffs(54, 105).addBox(7.5F, -39.0F, 19.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, 44.0F, -22.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Asgreon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimmationDefinitions.ASGREON_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimmationDefinitions.ASGREON_IDLE, ageInTicks, 1f);

	}

	private void applyHeadRotation(Asgreon entity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0f, 30.0f);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0f, 45.0f);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		asgreon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return asgreon;
	}
}