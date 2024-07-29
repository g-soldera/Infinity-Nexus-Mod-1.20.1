package com.Infinity.Nexus.Mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

public class Teste {
    public Teste(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("inm").then(Commands.literal("hand")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        player.sendSystemMessage(Component.literal("Item: " + heldItem.getItem().getDescription().getString()));
        player.sendSystemMessage(Component.literal("Quantidade: " + heldItem.getCount()));
        player.sendSystemMessage(Component.literal("Meta: " + heldItem.getDamageValue()));

        // printa as tags do item
        player.sendSystemMessage(Component.literal("Tags: " + heldItem.serializeNBT()));
        return 1;
    }

}