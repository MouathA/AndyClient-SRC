package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import Mood.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;
import DTool.modules.visuals.*;
import java.util.*;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation field_147017_u;
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000749";
        field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    }
    
    public GuiChest(final IInventory upperChestInventory, final IInventory lowerChestInventory) {
        Minecraft.getMinecraft();
        super(new ContainerChest(upperChestInventory, lowerChestInventory, Minecraft.thePlayer));
        this.upperChestInventory = upperChestInventory;
        this.lowerChestInventory = lowerChestInventory;
        this.allowUserInput = false;
        this.inventoryRows = lowerChestInventory.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int n = (GuiChest.height - this.ySize) / 2 + 5;
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("AddToChestButtons").toggled) {
            this.buttonList.add(new GuiButton(1, GuiChest.width / 2 - 5, n, 40, 11, "    Steal"));
            this.buttonList.add(new GuiButton(2, GuiChest.width / 2 + 40, n, 40, 11, "    Store"));
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            new Thread(this::lambda$0).start();
        }
        else if (guiButton.id == 2) {
            new Thread(this::lambda$1).start();
        }
        super.actionPerformed(guiButton);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiChest.mc.getTextureManager().bindTexture(GuiChest.field_147017_u);
        final int n4 = (GuiChest.width - this.xSize) / 2;
        final int n5 = (GuiChest.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(n4, n5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    private void lambda$0() {
        while (0 < this.inventoryRows * 9) {
            final Slot slot = this.inventorySlots.inventorySlots.get(0);
            if (slot.getStack() != null && GuiChest.mc.currentScreen == this) {
                Thread.sleep(AddToChestButtons.delay * 10L + new Random().nextInt(4));
                this.handleMouseClick(slot, slot.slotNumber, 0, 1);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void lambda$1() {
        for (int i = this.inventoryRows * 9; i < this.inventoryRows * 9 + 44; ++i) {
            final Slot slot = this.inventorySlots.inventorySlots.get(i);
            if (slot.getStack() != null && GuiChest.mc.currentScreen == this) {
                Thread.sleep(AddToChestButtons.delay * 10L + new Random().nextInt(4));
                this.handleMouseClick(slot, slot.slotNumber, 0, 1);
            }
        }
    }
}
