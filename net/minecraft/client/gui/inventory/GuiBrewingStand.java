package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiBrewingStand extends GuiContainer
{
    private static final ResourceLocation brewingStandGuiTextures;
    private final InventoryPlayer field_175384_v;
    private IInventory tileBrewingStand;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000746";
        brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
    }
    
    public GuiBrewingStand(final InventoryPlayer field_175384_v, final IInventory tileBrewingStand) {
        super(new ContainerBrewingStand(field_175384_v, tileBrewingStand));
        this.field_175384_v = field_175384_v;
        this.tileBrewingStand = tileBrewingStand;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.tileBrewingStand.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.field_175384_v.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiBrewingStand.mc.getTextureManager().bindTexture(GuiBrewingStand.brewingStandGuiTextures);
        final int n4 = (GuiBrewingStand.width - this.xSize) / 2;
        final int n5 = (GuiBrewingStand.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        final int field = this.tileBrewingStand.getField(0);
        if (field > 0) {
            final int n6 = (int)(28.0f * (1.0f - field / 400.0f));
            if (0 > 0) {
                this.drawTexturedModalRect(n4 + 97, n5 + 16, 176, 0, 9, 0);
            }
            switch (field / 2 % 7) {
                case 0: {}
                case 1: {}
                case 2: {}
                case 3: {}
                case 4: {}
            }
            if (0 > 0) {
                this.drawTexturedModalRect(n4 + 65, n5 + 14 + 29 - 0, 185, 29, 12, 0);
            }
        }
    }
}
