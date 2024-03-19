package com.Infinity.Nexus.Mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Teste {
    public Teste(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spawn").then(Commands.literal("circle")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        return 1;
    }

}