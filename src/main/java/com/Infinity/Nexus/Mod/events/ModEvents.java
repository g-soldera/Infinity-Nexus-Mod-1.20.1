package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.command.Teste;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.HammerItem;
import com.Infinity.Nexus.Mod.flight.FlightManager;
import com.Infinity.Nexus.Mod.item.custom.ImperialInfinityArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new Teste(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            int range = mainHandItem.getOrCreateTag().getInt("range");
            BlockPos initalBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initalBlockPos)) {
                return;
            }
            if(mainHandItem.getItem() == ModItemsAdditions.INFINITY_HAMMER.get()) {
                for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(range+1, initalBlockPos, serverPlayer)) {
                    if (pos == initalBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    // Have to add them to a Set otherwise, the same code right here will get called for each block!
                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
            if(mainHandItem.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get()) {
                for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(range+2, initalBlockPos, serverPlayer)) {
                    if (pos == initalBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    // Have to add them to a Set otherwise, the same code right here will get called for each block!
                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (event.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
                checkArmorAndDisableFlight(player);
            }
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        Player player = event.getPlayer();
        if (!player.level().isClientSide()) {
            if (event.getEntity().getItem().getItem() instanceof ImperialInfinityArmorItem) {
                checkArmorAndDisableFlight(player);
            }
        }
    }

    private static void checkArmorAndDisableFlight(Player player) {
        if (!hasFullImperialArmorSet(player)) {
            FlightManager.disableFlight(player);
        }
    }

    private static boolean hasFullImperialArmorSet(Player player) {
        return player.getInventory().getArmor(0).getItem() == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                && player.getInventory().getArmor(1).getItem() == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                && player.getInventory().getArmor(2).getItem() == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                && player.getInventory().getArmor(3).getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
    }
}
