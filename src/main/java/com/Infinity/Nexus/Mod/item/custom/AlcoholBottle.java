package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AlcoholBottle extends Item {
    public AlcoholBottle(Properties pProperties) {
        super(pProperties);
    }

    //@Override
    //public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
    //    if(pLevel.isClientSide()) return;
    //    ServerPlayer player = (ServerPlayer) pEntity;
    //    Vec3 playerPos = player.position();
    //    ServerLevel level = (ServerLevel) pLevel;
    //    System.out.println("Tick");
//
    //    int numParticles = 50; // Número de partículas para formar as linhas
    //    double lineLength = 1.0; // Comprimento das linhas
    //    double angleOffset = Math.PI / 4; // Ângulo de inclinação das linhas (45 graus em radianos)
//
    //    for (int i = 0; i < numParticles; i++) {
    //        double progress = (double) i / numParticles;
    //        double yOffset1 = progress * lineLength; // Linha diagonal da esquerda para a direita
    //        double yOffset2 = progress * lineLength; // Linha diagonal da direita para a esquerda
//
    //        double xOffset1 = progress * lineLength * Math.cos(angleOffset);
    //        double zOffset1 = progress * lineLength * Math.sin(angleOffset);
    //        double xOffset2 = progress * lineLength * Math.cos(-angleOffset);
    //        double zOffset2 = progress * lineLength * Math.sin(-angleOffset);
//
    //        level.sendParticles(ParticleTypes.ENCHANT, playerPos.x + xOffset1, (playerPos.y + 1) + yOffset1, playerPos.z + zOffset1, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    //        level.sendParticles(ParticleTypes.ENCHANT, playerPos.x + xOffset2, (playerPos.y + 1) + yOffset2, playerPos.z + zOffset2, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    //    }
//
//
    //    super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    //}
}
