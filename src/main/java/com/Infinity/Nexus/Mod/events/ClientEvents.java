package com.Infinity.Nexus.Mod.event;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.Infinity.Nexus.Mod.flight.FlightManager;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        
        if (player == null) return;
        
        if (event.getKey() == KeyBindings.FLIGHT_KEY.getKey().getValue() && event.getAction() == 1) {
            if (hasFullSuitOfArmorOn(player)) {
                FlightManager.handleFlightActivation(player);
            } else {
                player.sendSystemMessage(Component.literal("§c[Debug] Você precisa da armadura completa!"));
            }
        }
    }

    private static boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        
        return boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
    }
} 