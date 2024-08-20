package com.Infinity.Nexus.Mod.screen.mobcrusher;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.renderer.EnergyInfoArea;
import com.Infinity.Nexus.Core.renderer.FluidTankRenderer;
import com.Infinity.Nexus.Core.renderer.InfoArea;
import com.Infinity.Nexus.Core.renderer.RenderScreenTooltips;
import com.Infinity.Nexus.Core.utils.MouseUtil;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class MobCrusherScreen extends AbstractContainerScreen<MobCrusherMenu> implements GuiEventListener {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/gui/mob_crusher_gui.png");
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer fluidRenderer;
    public MobCrusherScreen(MobCrusherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignEnergyInfoArea();
        assignFluidTank();
    }
    private void assignFluidTank() {
        fluidRenderer = new FluidTankRenderer(MobCrusherBlockEntity.getFluidCapacity(), true, 6, 62);
    }
    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 159, y + 6, menu.getBlockEntity().getEnergyStorage());
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(Screen.hasShiftDown() || isMouseAboveArea(pMouseX, pMouseY, x, y, - 15, + 10, 17, 54)) {
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 15, y + 10, 193, 84, 18, 131);
        }else{
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 3, y + 10, 193, 84, 18, 131);
        }

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.render(guiGraphics);
        fluidRenderer.render(guiGraphics, x+146, y+6, menu.blockEntity.getFluid());
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 81, y + 29, 176, 0, 16, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int index = y;

        int hasRedstoneSignal = menu.getBlockEntity().getHasRedstoneSignal();
        int hasComponent = menu.getBlockEntity().getHasComponent();
        int hasEnoughEnergy = menu.getBlockEntity().getHasEnoughEnergy();
        int hasSlotFree = menu.getBlockEntity().getHasSlotFree();
        int hasRecipe = menu.getBlockEntity().getHasRecipe();

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if (hasRedstoneSignal == 1) {
            guiGraphics.drawString(this.font, "Redstone: [ON]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index-4);
            index += 15;
        }else{
            guiGraphics.drawString(this.font, "Redstone: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index - 4);
            index += 15;
        }
        if (hasComponent == 0){
            guiGraphics.drawString(this.font, "Component: [Missing]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(ModItems.REDSTONE_COMPONENT.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Component: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItems.REDSTONE_COMPONENT.get()), x + 178, index - 4);
            index += 15;
        }
        if (hasEnoughEnergy == 0){
            guiGraphics.drawString(this.font, "Energy: [Missing]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Energy: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index - 4);
            index += 15;
        }
        if (hasSlotFree == 0){
            guiGraphics.drawString(this.font, "Slot: [Missing]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CHEST), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Slot: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CHEST), x + 178, index - 4);
            index += 15;
        }
        if (hasRecipe == 1){
            guiGraphics.drawString(this.font, "Mobs: [Scanning]", x + 196, index, 0XB6FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Mobs: [Scanning]", x + 196, index, 0XB6FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }
        if (hasComponent >= 1){
            int range = hasComponent - 1;
            guiGraphics.drawString(this.font, "Range: "+ (range + range + 1) + "x"+ (range + range + 1)+ "^3", x + 196, index, 0XB6FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItemsAdditions.TERRAIN_MARKER.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Range: 0", x + 196, index, 0XB6FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItemsAdditions.TERRAIN_MARKER.get()), x + 178, index - 4);
            index += 15;
        }
        if (hasRedstoneSignal == 0 && hasComponent == 1 && hasEnoughEnergy == 1
                && hasSlotFree == 1){
            guiGraphics.drawString(this.font, "Killing: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }else {
            guiGraphics.drawString(this.font, "Killing: [OFF]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }
    }
    private void renderFluidAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                         FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 159,  6, 6, 62)) {
            pGuiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if (Screen.hasShiftDown()) {
            if (isMouseAboveArea(pMouseX, pMouseY, x, y, -12, 10, 17, 53)) {
                RenderScreenTooltips.renderUpgradeSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 7, 28, 17, 17)) {
                RenderScreenTooltips.renderComponentSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 145, 6, 6, 62)) {
                List<Component> components = List.of(Component.literal("Experience"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 79, 10, 53, 53)) {
                List<Component> components = List.of(Component.literal("Output Slot"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 10, 17, 17)) {
                List<Component> components = List.of(Component.literal("Sword Slot"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 28, 17, 17)) {
                List<Component> linkingTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.linking_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, linkingTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 46, 17, 17)) {
                List<Component> fuelTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.fuel_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, fuelTooltip, pMouseX, pMouseY, x, y);
            }
        }
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);


        renderEnergyAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);
        renderFluidAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y, menu.blockEntity.getFluid(), 146,6, fluidRenderer);
        renderTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}