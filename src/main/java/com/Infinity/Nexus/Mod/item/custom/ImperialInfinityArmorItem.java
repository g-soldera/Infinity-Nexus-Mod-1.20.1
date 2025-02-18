package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.client.ImperialInfinityArmorRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;
import java.util.function.Consumer;


public class ImperialInfinityArmorItem extends  ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static int delay;
    private static int maxDelay = 80*20;
    public ImperialInfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide() && pEntity instanceof Player player) {
            if (hasFullSuitOfArmorOn(player)) {
                if (ConfigUtils.imperial_infinity_armor_can_fly && !player.getAbilities().flying) {
                    player.getAbilities().mayfly = true;
                }
                player.setCustomName(Component.literal("§6Imperial " + player.getName().getString()));
                player.getFoodData().setSaturation(20);
                player.getFoodData().setFoodLevel(20);
                //player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 1000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 1000, 1, false, false));
            } else {
                player.setCustomName(Component.literal(player.getName().getString()));
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
            }
            player.onUpdateAbilities();
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        boolean armor =
                boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
        return armor;
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }
    // Create our armor model/renderer for forge and return it
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new ImperialInfinityArmorRenderer();

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
            state.setAnimation(DefaultAnimations.IDLE);
            state.getController().setAnimation(RawAnimation.begin().then("misc.idle", Animation.LoopType.LOOP));

            Entity entity = state.getData(DataTickets.ENTITY);
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

            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                    ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get(),
                    ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get(),
                    ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get(),
                    ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()));

            // Play the animation if the full set is being worn, otherwise stop
            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}