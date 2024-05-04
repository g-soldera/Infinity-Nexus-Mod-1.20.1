package com.Infinity.Nexus.Mod.worldgen.portal;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class PortalTeleport implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public PortalTeleport(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        int y = 61;

        if (!insideDimension) {
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        while (!destinationWorld.canSeeSky(destinationPos.above())) {
            destinationPos = destinationPos.above();
        }

        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        if (insideDimension) {
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10),
                    destinationPos.above(10).east(10))) {
                if (destinationWorld.getBlockState(checkPos).getBlock() == ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()) {
                    doSetBlock = false;
                    break;
                }
            }

            if (doSetBlock) {
                destinationWorld.setBlock(destinationPos, ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get().defaultBlockState(), 3);
            }
        }

        return entity;
    }
}
