package com.Infinity.Nexus.Mod.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FlightManager {
    private static final long DOUBLE_PRESS_TIME = 500;
    private static final long TOGGLE_COOLDOWN = 2300;
    private static final double VERTICAL_SPEED = 0.4D;
    private static final double FLIGHT_SPEED = 0.6D;

    private static long lastPressTime = 0;
    private static long lastToggleTime = 0;
    private static boolean isFlying = false;

    public static boolean isFlying() {
        return isFlying;
    }

    public static void handleFlightActivation(Player player) {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastToggleTime < TOGGLE_COOLDOWN) {
            return;
        }

        if (currentTime - lastPressTime <= DOUBLE_PRESS_TIME) {
            toggleFlight(player);
            lastPressTime = 0; 
        } else {
            lastPressTime = currentTime;
        }
    }

    public static void disableFlight(Player player) {
        isFlying = false;
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.fallDistance = 0F;
        player.onUpdateAbilities();
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, -0.3D, motion.z);
        player.hasImpulse = true;
    }

    private static void toggleFlight(Player player) {
        isFlying = !isFlying;
        lastToggleTime = System.currentTimeMillis();
        
        if (isFlying) {
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true;
        } else {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.fallDistance = 0F;
        }
        player.onUpdateAbilities();
    }

    public static void handleFlightMovement(Player player) {
        if (!isFlying) {
            player.fallDistance = 0F;
            return;
        }

        player.fallDistance = 0F;
        
        if (player.isCrouching()) {
            player.setDeltaMovement(player.getDeltaMovement().x, -VERTICAL_SPEED, player.getDeltaMovement().z);
        } else if (player.getDeltaMovement().y > 0) {
            player.setDeltaMovement(player.getDeltaMovement().x, VERTICAL_SPEED, player.getDeltaMovement().z);
        } else {
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
        }

        if (player.zza != 0 || player.xxa != 0) {
            Vec3 moveVector = new Vec3(player.xxa, 0, player.zza).normalize();
            player.setDeltaMovement(
                moveVector.x * FLIGHT_SPEED,
                player.getDeltaMovement().y,
                moveVector.z * FLIGHT_SPEED
            );
        }
    }
}
