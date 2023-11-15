package com.Infinity.Nexus.Mod.networking.packet;

import com.Infinity.Nexus.Mod.block.entity.AssemblerBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.CrusherBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.PressBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import com.Infinity.Nexus.Mod.screen.assembly.AssemblerMenu;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
                  if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof AssemblerBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
            }else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CrusherBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
            }else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof PressBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
            }else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof SqueezerBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
            }

        });
        return true;
    }
}