package com.Infinity.Nexus.Mod.command;

import com.google.common.collect.BoundType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/*
public class Progression {
    private static final Map<String, String> validInputs = new LinkedHashMap<>();
    static {
        validInputs.put("987654321012", "&f𓆉&r");
        validInputs.put("123456789012", "&f𓆌&r");
        validInputs.put("876543210987", "&f𓆏&r");
        validInputs.put("234567890123", "&f𓆗&r");
        validInputs.put("789012345678", "&f𓆘&r");
        validInputs.put("345678901234", "&f𓆙&r");
        validInputs.put("890123456789", "&f𓆚&r");
        validInputs.put("456789012345", "&f𓆐&r");
        validInputs.put("901234567890", "&f𓆑&r");
        validInputs.put("567890123456", "&f𓆒&r");
        validInputs.put("012345678901", "&f𓆓&r");
        validInputs.put("543210987654", "&f𓆔&r");
        validInputs.put("109876543210", "&f𓆕&r");
        validInputs.put("765432109876", "&f𓆖&r");
        validInputs.put("321098765432", "&f𓆊&r");
        validInputs.put("876543510987", "&f𓆍&r");
        validInputs.put("234567590123", "&f𓆣&r");
        validInputs.put("789012545678", "&f𓆤&r");
        validInputs.put("345678502234", "&f𓆥&r");
        validInputs.put("890123552789", "&f𓆦&r");
        validInputs.put("456789512345", "&f𓆧&r");
        validInputs.put("901234562890", "&f𓆨&r");
        validInputs.put("567890522456", "&f𓆛&r");
        validInputs.put("012345572901", "&f𓆜&r");
        validInputs.put("543210582654", "&f𓆝&r");
        validInputs.put("109876542210", "&f𓆞&r");
        validInputs.put("765432502876", "&f𓆟&r");
        validInputs.put("321098562432", "&f𓆠&r");
        validInputs.put("876543512987", "&f𓆡&r");
        validInputs.put("234567592123", "&f𓆢&r");
        validInputs.put("789012342678", "&f𓆣&r");
        validInputs.put("345678902234", "&f𓆤&r");
        validInputs.put("890123452789", "&f𓆥&r");
        validInputs.put("456789119345", "&f𓆦&r");
        validInputs.put("901234162890", "&f𓆧&r");
        validInputs.put("567890122456", "&f𓆨&r");
        validInputs.put("012345672901", "&f𓆛&r");
        validInputs.put("543210982654", "&f𓆜&r");
        validInputs.put("109876642210", "&f𓆝&r");
        validInputs.put("765432102876", "&f𓆞&r");
        validInputs.put("321098762432", "&f𓆟&r");
        validInputs.put("876543212987", "&f𓆠&r");
        validInputs.put("234567892123", "&f𓆡&r");
        validInputs.put("789612345678", "&f𓆢&r");
        validInputs.put("345678908234", "&f𓄿&r");
        validInputs.put("890623456789", "&f𓅀&r");
        validInputs.put("456689012345", "&f𓅁&r");
        validInputs.put("901634567890", "&f𓅂&r");
        validInputs.put("567690123456", "&f𓅃&r");
        validInputs.put("012645678901", "&f𓅄&r");
        validInputs.put("543610987654", "&f𓅇&r");
        validInputs.put("109676543210", "&f𓅈&r");
        validInputs.put("765632109876", "&f𓅉&r");
        validInputs.put("321698765432", "&f𓅊&r");
        validInputs.put("876643210987", "&f𓅋&r");
        validInputs.put("234667890123", "&f𓅌&r");
        validInputs.put("799612345678", "&f𓅍&r");
        validInputs.put("395678901234", "&f𓅎&r");
        validInputs.put("890625456789", "&f𓅏&r");
        validInputs.put("496689012345", "&f𓅐&r");
        validInputs.put("991634567890", "&f𓅑&r");
        validInputs.put("597690123456", "&f𓅒&r");
        validInputs.put("092645678901", "&f𓅓&r");
        validInputs.put("593610987654", "&f𓅔&r");
        validInputs.put("199676543210", "&f𓅕&r");
        validInputs.put("765432409876", "&f𓅖&r");
        validInputs.put("321098465432", "&f𓅗&r");
        validInputs.put("876543410987", "&f𓅘&r");
        validInputs.put("234567490123", "&f𓅙&r");
        validInputs.put("789012445678", "&f𓅚&r");
        validInputs.put("345678401234", "&f𓅛&r");
        validInputs.put("890623436789", "&f𓅜&r");
        validInputs.put("456689032345", "&f𓅝&r");
        validInputs.put("901634537890", "&f𓅞&r");
        validInputs.put("567690133456", "&f𓅟&r");
        validInputs.put("012345612901", "&f𓅠&r");
        validInputs.put("543210957354", "&f𓅡&r");
        validInputs.put("109876553310", "&f𓅢&r");
        validInputs.put("765432159376", "&f𓅣&r");
        validInputs.put("321098755332", "&f𓅤&r");
        validInputs.put("876543210387", "&f𓅥&r");
        validInputs.put("234567190323", "&f𓅦&r");
        validInputs.put("789012145378", "&f𓅧&r");
        validInputs.put("345678101334", "&f𓅨&r");
        validInputs.put("890123156389", "&f𓅩&r");
        validInputs.put("456789112345", "&f𓅪&r");
        validInputs.put("901234567390", "&f𓅫&r");
        validInputs.put("567890123356", "&f𓅬&r");
        validInputs.put("012345678301", "&f𓅭&r");
        validInputs.put("543210987354", "&f𓅮&r");
        validInputs.put("109876543310", "&f𓅯&r");
        validInputs.put("765432109376", "&f𓅰&r");
        validInputs.put("321098765332", "&f𓅱&r");
        validInputs.put("876543320387", "&f𓅲&r");
        validInputs.put("234567890323", "&f𓅳&r");
        validInputs.put("789012345378", "&f𓅴&r");
        validInputs.put("345678901334", "&f𓅵&r");
        validInputs.put("890123456389", "&f𓅶&r");
        validInputs.put("456789122345", "&f𓅷&r");
        validInputs.put("456789120345", "&f\uD83E\uDD93&r");
    }
    public Progression(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> progressionCommand = Commands.literal("progression")
                .executes(this::noPermission)
                .then(Commands.argument("level", StringArgumentType.greedyString())
                        .executes(this::update)
                );

        dispatcher.register(progressionCommand);
    }

    private int update(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String playerName = player.getName().getString();
        String input = StringArgumentType.getString(context, "level");
        MinecraftServer server = player.getServer();
        if (validInputs.containsKey(input)) {

            int index = getIndexByKey(input);

            String value = validInputs.get(input);

            context.getSource().sendSuccess(() -> Component.literal("lp user " + playerName + " meta addsuffix " +index+ " " + value), true);
            context.getSource().sendSuccess(() -> Component.literal("lp user " + playerName + " meta removesuffix " +(index-1)+ " " + getValueByIndex(index-1)), true);
        } else {
            System.out.println("[I.N.M]: O Player " + playerName + " não tem permissão para executar esse comando ou está tentando burlar a progressão");
        }
        return 1;
    }

    public static int getIndexByKey(String key) {
        ArrayList<String> keysList = new ArrayList<>(validInputs.keySet());
        return keysList.indexOf(key);
    }
    public static String getValueByIndex(int index) {
        ArrayList<String> valuesList = new ArrayList<>(validInputs.values());
        if (index >= 0 && index < valuesList.size()) {
            return valuesList.get(index);
        }
        return null; // Retorna null se o índice estiver fora dos limites
    }
    private int noPermission(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("&cError: You do not have permission"), true);
        return 1;
    }
}
*/