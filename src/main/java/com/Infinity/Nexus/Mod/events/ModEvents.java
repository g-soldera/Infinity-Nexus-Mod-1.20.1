package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
//import com.Infinity.Nexus.Mod.command.Progression;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class ModEvents {
        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            //new Progression(event.getDispatcher());

            ConfigCommand.register(event.getDispatcher());
        }
}
