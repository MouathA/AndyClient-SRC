package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class GuiFurnace extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures;
    private final InventoryPlayer field_175383_v;
    private IInventory tileFurnace;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000758";
        furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
    }
    
    public GuiFurnace(final InventoryPlayer field_175383_v, final IInventory tileFurnace) {
        super(new ContainerFurnace(field_175383_v, tileFurnace));
        this.field_175383_v = field_175383_v;
        this.tileFurnace = tileFurnace;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.field_175383_v.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiFurnace.mc.getTextureManager().bindTexture(GuiFurnace.furnaceGuiTextures);
        final int n4 = (GuiFurnace.width - this.xSize) / 2;
        final int n5 = (GuiFurnace.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        if (TileEntityFurnace.func_174903_a(this.tileFurnace)) {
            final int func_175382_i = this.func_175382_i(13);
            this.drawTexturedModalRect(n4 + 56, n5 + 36 + 12 - func_175382_i, 176, 12 - func_175382_i, 14, func_175382_i + 1);
        }
        this.drawTexturedModalRect(n4 + 79, n5 + 34, 176, 14, this.func_175381_h(24) + 1, 16);
    }
    
    private int func_175381_h(final int n) {
        final int field = this.tileFurnace.getField(2);
        final int field2 = this.tileFurnace.getField(3);
        return (field2 != 0 && field != 0) ? (field * n / field2) : 0;
    }
    
    private int func_175382_i(final int n) {
        this.tileFurnace.getField(1);
        if (200 == 0) {}
        return this.tileFurnace.getField(0) * n / 200;
    }
}
