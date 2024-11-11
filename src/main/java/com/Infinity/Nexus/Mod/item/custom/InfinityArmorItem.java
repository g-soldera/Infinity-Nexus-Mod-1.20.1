package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.client.ImperialInfinityArmorRenderer;
import com.Infinity.Nexus.Mod.item.client.InfinityArmorRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents the Infinity Armor item, a powerful armor set that provides flight capabilities
 * and various effects when wearing a complete set. This armor requires fuel for flight and
 * provides unique particle effects during movement.
 */
public class InfinityArmorItem extends ArmorItem implements GeoItem {
    private static final int EFFECT_DURATION = 20 * 50;
    private static final int EFFECT_AMPLIFIER = 1;
    private static final int MAX_FOOD_LEVEL = 19;
    private static final int MAX_SATURATION = 5;
    private static final int PARTICLE_SOUND_DELAY = 250;
    private static final double PARTICLE_VELOCITY = 0.3;
    private static final double PARTICLE_Y_VELOCITY = -0.2D;
    private static final double PARTICLE_RISE_VELOCITY = 0.02D;
    
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static int particleDelay;
    private static boolean onGround;
    private static int fuel;

    /**
     * Constructs a new Infinity Armor item with specified material, type and properties.
     *
     * @param material The material of the armor
     * @param type The type of armor piece (helmet, chestplate, leggings, boots)
     * @param settings The item properties
     */
    public InfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    /**
     * Handles per-tick updates for the armor item, managing effects, abilities, and particles.
     *
     * @param pStack The ItemStack being ticked
     * @param pLevel The current level
     * @param pEntity The entity wearing the armor
     * @param pSlotId The slot ID where the item is located
     * @param pIsSelected Whether the item is currently selected
     */
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!(pEntity instanceof Player player)) {
            return;
        }

        boolean hasFullSet = hasFullSuitOfArmorOn(player);
        
        if (pLevel.isClientSide()) {
            handleClientEffects(player, pLevel, hasFullSet);
            return;
        }

        updatePlayerAbilities(player, pLevel, hasFullSet);
        if (hasFullSet) {
            applyArmorEffects(player);
        }
    }

    /**
     * Manages client-side visual and audio effects for the armor.
     *
     * @param player The player wearing the armor
     * @param level The current level
     * @param hasFullSet Whether the player has a complete set of armor
     */
    private void handleClientEffects(Player player, Level level, boolean hasFullSet) {
        if (!hasFullSet || !ConfigUtils.infinity_armor_can_fly || !hasFuel(player)) {
            return;
        }

        if (player.getAbilities().flying) {
            renderParticles(player, level);
        }
    }

    /**
     * Updates player abilities based on armor status and fuel availability.
     *
     * @param player The player to update
     * @param level The current level
     * @param hasFullSet Whether the player has a complete set of armor
     */
    private void updatePlayerAbilities(Player player, Level level, boolean hasFullSet) {
        if (hasFullSet && ConfigUtils.infinity_armor_can_fly && hasFuel(player)) {
            player.getAbilities().mayfly = true;
        } else {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }
        player.onUpdateAbilities();
    }

    /**
     * Applies beneficial status effects to the player wearing the full armor set.
     *
     * @param player The player to receive the effects
     */
    private void applyArmorEffects(Player player) {
        player.fireImmune();
        player.getFoodData().setSaturation(MAX_SATURATION);
        player.getFoodData().setFoodLevel(MAX_FOOD_LEVEL);
        
        MobEffectInstance[] effects = {
            new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false),
            new MobEffectInstance(MobEffects.REGENERATION, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false)
        };
        
        for (MobEffectInstance effect : effects) {
            player.addEffect(effect);
        }
    }

    /**
     * Manages particle effects based on player movement state.
     *
     * @param player The player wearing the armor
     * @param level The current level
     */
    private void renderParticles(Player player, Level level) {
        if (player.getAbilities().flying) {
            if (!player.onGround()) {
                onGround = false;
                renderFlightParticles(player, level);
            }
        } else if (!onGround && player.onGround()) {
            onGround = true;
            renderLandingParticles(player, level);
        }
    }

    /**
     * Creates particle effects during flight.
     *
     * @param player The flying player
     * @param level The current level
     */
    private void renderFlightParticles(Player player, Level level) {
        double yaw = -player.getYRot() + 90;
        double v = PARTICLE_VELOCITY * Math.sin(Math.toRadians(yaw));
        
        double x1 = player.getX() + v;
        double x2 = player.getX() - v;
        double z1 = player.getZ() + PARTICLE_VELOCITY * Math.cos(Math.toRadians(yaw));
        double z2 = player.getZ() - PARTICLE_VELOCITY * Math.cos(Math.toRadians(yaw));
        
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x1, player.getY(), z1, 0.0D, PARTICLE_Y_VELOCITY, 0.0D);
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x2, player.getY(), z2, 0.0D, PARTICLE_Y_VELOCITY, 0.0D);
        
        handleFlightSound(player, level);
    }

    /**
     * Manages periodic flight sound effects.
     *
     * @param player The flying player
     * @param level The current level
     */
    private void handleFlightSound(Player player, Level level) {
        particleDelay++;
        if (particleDelay >= PARTICLE_SOUND_DELAY) {
            particleDelay = 0;
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), 
                               SoundEvents.CHORUS_FLOWER_GROW, SoundSource.AMBIENT, 0.5F, 0.4F, false);
        }
    }

    /**
     * Creates particle effects when landing.
     *
     * @param player The landing player
     * @param level The current level
     */
    private void renderLandingParticles(Player player, Level level) {
        double[] radii = {0.5, 1.0, 1.5};
        int[] stepSizes = {15, 10, 5};

        for (int i = 0; i < radii.length; i++) {
            spawnCircularParticles(player, level, radii[i], stepSizes[i]);
        }
        
        playLandingSounds(player, level);
    }

    /**
     * Creates circular particle patterns around the player.
     *
     * @param player The player at the center
     * @param level The current level
     * @param radius The radius of the circle
     * @param stepSize The spacing between particles
     */
    private void spawnCircularParticles(Player player, Level level, double radius, int stepSize) {
        for (int j = 0; j < 360; j += stepSize) {
            double x = player.getX() + radius * Math.cos(Math.toRadians(j));
            double z = player.getZ() + radius * Math.sin(Math.toRadians(j));
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
            level.addParticle(ParticleTypes.FIREWORK, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
            level.addParticle(ParticleTypes.SMALL_FLAME, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
        }
    }

    /**
     * Plays sound effects when landing.
     *
     * @param player The landing player
     * @param level The current level
     */
    private void playLandingSounds(Player player, Level level) {
        level.playLocalSound(player.getX(), player.getY(), player.getZ(), 
                           SoundEvents.PLAYER_BIG_FALL, SoundSource.AMBIENT, 0.5F, 0.5F, false);
        level.playLocalSound(player.getX(), player.getY(), player.getZ(), 
                           SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 0.5F, 0.1F, false);
    }

    /**
     * Checks and manages fuel consumption for flight capabilities.
     *
     * @param player The player to check fuel for
     * @return Whether flight is currently fueled
     */
    private boolean hasFuel(Player player) {
        if(ConfigUtils.infinity_armor_need_fuel) {
            ItemStack breastplate = player.getInventory().getArmor(2);
            int fuelInItem = breastplate.getOrCreateTag().getInt("Fuel");
            if (fuelInItem > 0) {
                if(fuel < 20){
                    fuel++;
                }else{
                    fuel = 0;
                    if(!player.onGround()) {
                        breastplate.getOrCreateTag().putInt("Fuel", fuelInItem - 1);
                    }
                }
                return true;
            } else {
                ItemStack fuelItem = ConfigUtils.infinity_armor_fuel.getDefaultInstance();
                if (player.getInventory().findSlotMatchingItem(fuelItem) > -1) {
                    player.getInventory().removeItem(player.getInventory().findSlotMatchingItem(fuelItem), 1);
                    player.getInventory().getArmor(2).getOrCreateTag().putInt("Fuel", ConfigUtils.infinity_armor_fuel_time);
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the player is wearing a complete set of Infinity Armor.
     *
     * @param player The player to check
     * @return Whether the player has a complete set equipped
     */
    private boolean hasFullSuitOfArmorOn(Player player) {
        if (player == null) return false;
        
        return player.getInventory().armor.stream().allMatch(stack -> {
            if (stack.isEmpty()) return false;
            Item item = stack.getItem();
            return item == ModItemsAdditions.INFINITY_BOOTS.get() ||
                   item == ModItemsAdditions.INFINITY_LEGGINGS.get() ||
                   item == ModItemsAdditions.INFINITY_CHESTPLATE.get() ||
                   item == ModItemsAdditions.INFINITY_HELMET.get();
        });
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("item.infinity_nexus.infinity_armor"));
            if(stack.is(ModItemsAdditions.INFINITY_CHESTPLATE.get())) {
                components.add(Component.translatable("tooltip.infinity_nexus.infinity_armor_can_fly").append(Component.literal(ConfigUtils.infinity_armor_can_fly ? "§a✅" : "§c❎")));
                components.add(Component.translatable("tooltip.infinity_nexus.infinity_armor_need_fuel").append(Component.literal(ConfigUtils.infinity_armor_need_fuel ? "§a✅" : "§c❎")));
                components.add(Component.translatable("tooltip.infinity_nexus.infinity_armor_fuel").append(Component.literal(getArmorFuel(stack))));
            }
            } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    private String getArmorFuel(ItemStack stack) {
        if(stack.is(ModItemsAdditions.INFINITY_CHESTPLATE.get())) {
            int fuel = stack.getOrCreateTag().getInt("Fuel");
            if (fuel > 0) {
                return (fuel/20) + "s";
            }else{
                return "§c" + ConfigUtils.infinity_armor_fuel.getDefaultInstance().getHoverName().getString();
            }
        }
        return "None";
    }

    // Create our armor model/renderer for forge and return it
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new InfinityArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    // Let's add our animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            // Apply our generic idle animation.
            // Whether it plays or not is decided down below.
            state.setAnimation(DefaultAnimations.IDLE);
            state.getController().setAnimation(RawAnimation.begin().then("misc.idle", Animation.LoopType.LOOP));

            // Let's gather some data from the state to use below
            // This is the entity that is currently wearing/holding the item
            Entity entity = state.getData(DataTickets.ENTITY);

            // We'll just have ArmorStands always animate, so we can return here
            if (entity instanceof Player)
                return PlayState.CONTINUE;

            // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
            // Let's collect the armor pieces the entity is currently wearing
            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : entity.getArmorSlots()) {
                // We can stop immediately if any of the slots are empty
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }

            // Check each of the pieces match our set
            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                    ModItemsAdditions.INFINITY_HELMET.get(),
                    ModItemsAdditions.INFINITY_CHESTPLATE.get(),
                    ModItemsAdditions.INFINITY_LEGGINGS.get(),
                    ModItemsAdditions.INFINITY_BOOTS.get()));

            // Play the animation if the full set is being worn, otherwise stop
            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}