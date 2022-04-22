package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiDispenser extends GuiContainer
{
    private static final ResourceLocation dispenserGuiTextures;
    private final InventoryPlayer field_175376_w;
    public IInventory field_175377_u;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000765";
        dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
    }
    
    public GuiDispenser(final InventoryPlayer field_175376_w, final IInventory field_175377_u) {
        super(new ContainerDispenser(field_175376_w, field_175377_u));
        this.field_175376_w = field_175376_w;
        this.field_175377_u = field_175377_u;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.field_175377_u.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.field_175376_w.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiDispenser.mc.getTextureManager().bindTexture(GuiDispenser.dispenserGuiTextures);
        this.drawTexturedModalRect((GuiDispenser.width - this.xSize) / 2, (GuiDispenser.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
}
