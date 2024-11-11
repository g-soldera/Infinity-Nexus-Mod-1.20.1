package com.Infinity.Nexus.Mod.item.armor;

import net.minecraft.world.entity.player.Player;

/**
 * Interface that defines behavior for armor items that provide flight capabilities
 */
public interface IFlyableArmor {
    /**
     * Checks if the armor can currently enable flight
     * @param player The player wearing the armor
     * @return true if flight can be enabled, false otherwise
     */
    boolean canEnableFlight(Player player);
    
    /**
     * Handles flight-related effects and mechanics
     * @param player The player wearing the armor
     */
    void handleFlight(Player player);
    
    /**
     * Checks if the player has a complete set of this armor type equipped
     * @param player The player to check
     * @return true if wearing full set, false otherwise
     */
    boolean hasFullArmorSet(Player player);
}
