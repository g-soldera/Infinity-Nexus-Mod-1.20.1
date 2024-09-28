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

public class InfinityArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static int delay;
    private static int maxDelay = 80*20;
    private static int fuel;
    private static int particleDelay;
    private static boolean onGround;

    public InfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
            if (pEntity instanceof Player player && pSlotId < 4) {
                if (hasFullSuitOfArmorOn(player)) {
                    if (ConfigUtils.infinity_armor_can_fly && hasFuel(player)) {
                        if(!pLevel.isClientSide()) {
                            player.getAbilities().mayfly = true;
                        }else{
                            renderParticles(player, pLevel);
                        }
                    } else {
                        if(!pLevel.isClientSide()) {
                            player.getAbilities().flying = false;
                            player.getAbilities().mayfly = false;
                        }
                    }
                    if(!pLevel.isClientSide()) {
                        //Ad player effects
                        player.fireImmune();
                        player.getFoodData().setSaturation(5);
                        player.getFoodData().setFoodLevel(19);
                        if (delay >= maxDelay) {
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000, 1, false, false));
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1000, 1, false, false));
                            delay = 0;
                        } else {
                            delay++;
                        }
                    }
                } else {
                    if(!pLevel.isClientSide()) {
                        player.getAbilities().flying = false;
                        player.getAbilities().mayfly = false;
                    }
                }
                player.onUpdateAbilities();
            }
    }

private void renderParticles(Player player, Level pLevel) {
    if(!player.onGround() && player.getAbilities().flying) {
        onGround = false;
        double pitch = player.getXRot();
        double yaw = -player.getYRot()+90; // multiplicar por -1 para inverter a rotação
        double x = player.getX() + 0.3 * Math.sin(Math.toRadians(yaw));
        double y = player.getY();
        double z = player.getZ() + 0.3 * Math.cos(Math.toRadians(yaw));
        pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0D, -0.2D, 0.0D);

        x = player.getX() - 0.3 * Math.sin(Math.toRadians(yaw));
        z = player.getZ() - 0.3 * Math.cos(Math.toRadians(yaw));
        pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0D, -0.2D, 0.0D);
        particleDelay++;
        if(particleDelay >= 250) {
            particleDelay = 0;
            pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.CHORUS_FLOWER_GROW, SoundSource.AMBIENT, 0.5F, 0.4F, false);
        }
    }else{
        if(player.onGround() && !onGround) {
            if(!player.isFallFlying()){onGround = true;}
            double radius = 0.5;

            double[] radii = {radius + 0.5, radius + 1.0, radius + 1.5};
            int[] stepSizes = {15, 10, 5};

            for (int i = 0; i < radii.length; i++) {
                radius = radii[i];
                int stepSize = stepSizes[i];

                for (int j = 0; j < 360; j += stepSize) {
                    double x = player.getX() + radius * Math.cos(Math.toRadians(j));
                    double z = player.getZ() + radius * Math.sin(Math.toRadians(j));
                    pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, player.getY(), z, 0.0D, 0.02D, 0.0D);
                    pLevel.addParticle(ParticleTypes.FIREWORK, x, player.getY(), z, 0.0D, 0.02D, 0.0D);
                    pLevel.addParticle(ParticleTypes.SMALL_FLAME, x, player.getY(), z, 0.0D, 0.02D, 0.0D);
                }
            }
            pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BIG_FALL, SoundSource.AMBIENT, 0.5F, 0.5F, false);
            pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 0.5F, 0.1F, false);
        }
    }
}

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
        }else{
            return true;
        }
    }


    public static boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();

        boolean armor =
                boots == ModItemsAdditions.INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.INFINITY_HELMET.get();
        return armor;
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