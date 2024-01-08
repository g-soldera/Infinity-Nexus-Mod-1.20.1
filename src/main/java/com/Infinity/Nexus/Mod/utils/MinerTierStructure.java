package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MinerTierStructure {

    public static boolean hasStructure(int tier, BlockPos pos, Level level) {

        switch (tier) {
            case 1:
                         boolean t1 =  verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2);
                         boolean t2 =  verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2);
                         boolean t3 =  verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2);
                         boolean t4 =  verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2);
                         return t1 && t2 && t3 && t4;
            case 2:
                        boolean t5 = verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2);
                        boolean t6 = verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2);
                        boolean t7 = verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2);
                        boolean t8 = verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2);
                        return t5 && t6 && t7 && t8;
            case 3:
                        boolean t9  = verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3);
                        boolean t10 = verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3);
                        boolean t11 = verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3);
                        boolean t12 = verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3);
                        return t9 && t10 && t11 && t12;
            case 4:
                        boolean t13 = verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3);
                        boolean t14 = verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3);
                        boolean t15 = verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3);
                        boolean t16 = verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3);
                        return t13 && t14 && t15 && t16;
            case 5:
                        boolean t17 = verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4);
                        boolean t18 = verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4);
                        boolean t19 = verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4);
                        boolean t20 = verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4);
                        return t17 && t18 && t19 && t20;
            case 6:
                        boolean t21 = verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier, 4);
                        boolean t22 = verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier, 4);
                        boolean t23 = verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier, 4);
                        boolean t24 = verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier, 4);
                        return t21 && t22 && t23 && t24;
            case 7:
                        boolean t25 = verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier+1, 5);
                        boolean t26 = verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier+1, 5);
                        boolean t27 = verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier+1, 5);
                        boolean t28 = verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier+1, 5);
                        return t25 && t26 && t27 && t28;
            case 8:
                        boolean t29 = verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INFINITY_MACHINE_CASING.get(), level, tier, 5);
                        boolean t30 = verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INFINITY_MACHINE_CASING.get(), level, tier, 5);
                        boolean t31 = verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INFINITY_MACHINE_CASING.get(), level, tier, 5);
                        boolean t32 = verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INFINITY_MACHINE_CASING.get(), level, tier, 5);
                        return t29 && t30 && t31 && t32;
            default:
                return false;
        }
    }
    public static boolean verificaEstrutura(BlockPos pPos, BlockPos pilarPos, int altura, Block blocoComposicao, Level level, int tier, int radius) {
        boolean hasPilars = true;
        boolean hasArc = true;
        boolean hasRadios = true;
        for (int i = 0; i < altura; i++) {
            BlockPos pos = pilarPos.above(i);
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() != blocoComposicao && blockState.getBlock() == ModBlocksAdditions.STRUCTURAL_BLOCK.get() ||  blockState.is(Blocks.AIR)) {
                //TODO
                level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
                hasPilars = false;
            }
        }
        int minX = pPos.getX() - radius;
        int maxX = pPos.getX() + radius;
        int minZ = pPos.getZ() - radius;
        int maxZ = pPos.getZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (x == minX || x == maxX || z == minZ || z == maxZ) {
                    BlockPos pos = new BlockPos(x, pPos.getY(), z).below();
                    BlockState blockState = level.getBlockState(pos);
                    if (blockState.getBlock() != blocoComposicao && blockState.getBlock() == ModBlocksAdditions.STRUCTURAL_BLOCK.get() || blockState.is(Blocks.AIR)) {
                        //TODO
                        level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
                        hasArc = false;
                    }
                }
            }
        }
        for (Direction direction : Direction.values()) {
            for (int i = 1; i <= radius; i++) {
                BlockPos pos = pPos.below().offset(direction.getStepX() * i, direction.getStepY() * i, direction.getStepZ() * i);
                BlockState blockState = level.getBlockState(pos);
                if (blockState.getBlock() != blocoComposicao && direction != Direction.DOWN && direction != Direction.UP && blockState.is(Blocks.AIR) || blockState.getBlock() == ModBlocksAdditions.STRUCTURAL_BLOCK.get()) {
                    //TODO
                    level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
                    hasRadios = false;
                }
            }
        }
        return hasArc && hasPilars && hasRadios;
    }
}
