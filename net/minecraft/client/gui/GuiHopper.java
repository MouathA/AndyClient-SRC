package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiHopper extends GuiContainer
{
    private static final ResourceLocation field_147085_u;
    private IInventory field_147084_v;
    private IInventory field_147083_w;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000759";
        field_147085_u = new ResourceLocation("textures/gui/container/hopper.png");
    }
    
    public GuiHopper(final InventoryPlayer field_147084_v, final IInventory field_147083_w) {
        Minecraft.getMinecraft();
        super(new ContainerHopper(field_147084_v, field_147083_w, Minecraft.thePlayer));
        this.field_147084_v = field_147084_v;
        this.field_147083_w = field_147083_w;
        this.allowUserInput = false;
        this.ySize = 133;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.field_147083_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.field_147084_v.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiHopper.mc.getTextureManager().bindTexture(GuiHopper.field_147085_u);
        this.drawTexturedModalRect((GuiHopper.width - this.xSize) / 2, (GuiHopper.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
}
