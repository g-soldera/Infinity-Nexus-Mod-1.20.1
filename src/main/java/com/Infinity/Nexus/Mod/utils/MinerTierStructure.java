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

public class MinerTierStructure {

    public static boolean hasStructure(int tier, BlockPos pos, Level level) {

        switch (tier) {
            case 1:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.COPPER_MACHINE_CASING.get(), level, tier+1, 2);
            case 2:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()-2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-2, pos.getY()-4, pos.getZ()+2), 4, ModBlocksProgression.IRON_MACHINE_CASING.get(), level, tier, 2);
            case 3:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.TIN_MACHINE_CASING.get(), level, tier+1, 3);
            case 4:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()-3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-3, pos.getY()-6, pos.getZ()+3), 6, ModBlocksProgression.NICKEL_MACHINE_CASING.get(), level, tier, 3);
            case 5:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRASS_MACHINE_CASING.get(), level, tier+1, 4);
            case 6:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRONZE_MACHINE_CASING.get(), level, tier, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()-4), 8, ModBlocksProgression.BRONZE_MACHINE_CASING.get(), level, tier, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRONZE_MACHINE_CASING.get(), level, tier, 4) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-4, pos.getY()-8, pos.getZ()+4), 8, ModBlocksProgression.BRONZE_MACHINE_CASING.get(), level, tier, 4);
            case 7:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier+1, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier+1, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier+1, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.STEEL_MACHINE_CASING.get(), level, tier+1, 5);
            case 8:
                return
                        verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()-5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()+5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier, 5) &&
                                verificaEstrutura(pos, new BlockPos(pos.getX()-5, pos.getY()-10, pos.getZ()+5), 10, ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get(), level, tier, 5);
            default:
                return false;
        }
    }
    public static boolean verificaEstrutura(BlockPos pPos, BlockPos pilarPos, int altura, Block blocoComposicao, Level level, int tier, int radius) {
        for (int i = 0; i < altura; i++) {
            BlockPos pos = pilarPos.above(i);
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() != blocoComposicao && blockState.is(Blocks.AIR)) {
                //TODO
                level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
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
                    if (blockState.getBlock() != blocoComposicao && blockState.is(Blocks.AIR)) {
                        //TODO
                        level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }
        for (Direction direction : Direction.values()) {
            for (int i = 1; i <= radius; i++) {
                BlockPos pos = pPos.below().offset(direction.getStepX() * i, direction.getStepY() * i, direction.getStepZ() * i);
                BlockState blockState = level.getBlockState(pos);
                if (blockState.getBlock() != blocoComposicao && direction != Direction.DOWN && direction != Direction.UP && blockState.is(Blocks.AIR)) {
                    //TODO
                    level.setBlockAndUpdate(pos, ModBlocksAdditions.STRUCTURAL_BLOCK.get().defaultBlockState());
                }
            }
        }
        return true;
    }
}
