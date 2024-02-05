package com.Infinity.Nexus.Mod.screen.miner;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.screen.miner.MinerMenu;
import com.Infinity.Nexus.Mod.screen.renderer.EnergyInfoArea;
import com.Infinity.Nexus.Mod.screen.renderer.InfoArea;
import com.Infinity.Nexus.Mod.utils.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class MinerScreen extends AbstractContainerScreen<MinerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/gui/miner_gui.png");

    private EnergyInfoArea energyInfoArea;

    public MinerScreen(MinerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 159, y + 6, menu.getBlockEntity().getEnergyStorage());
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderEnergyAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 159,  6, 6, 62)) {
            pGuiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);


        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.render(guiGraphics);
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
        int hasStructure = menu.getBlockEntity().getHasStructure();
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
            guiGraphics.renderFakeItem(new ItemStack(ModItemsAdditions.REDSTONE_COMPONENT.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Component: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItemsAdditions.REDSTONE_COMPONENT.get()), x + 178, index - 4);
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
        if (hasStructure == 0){
            guiGraphics.drawString(this.font, "Structure: [Missing]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(ModBlocksAdditions.STRUCTURAL_BLOCK.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Structure: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModBlocksAdditions.STRUCTURAL_BLOCK.get()), x + 178, index - 4);
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
            guiGraphics.drawString(this.font, "Recipe: [Missing]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, "Recipe: [Ok]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }
        if (hasRedstoneSignal == 0 && hasComponent == 1 && hasEnoughEnergy == 1 &&
                hasStructure == 1 && hasSlotFree == 1 && hasRecipe == 0){
            guiGraphics.drawString(this.font, "Crafting: [ON]", x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }else {
            guiGraphics.drawString(this.font, "Crafting: [OFF]", x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }

    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}