package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsSimpleScrolledSelectionList field_178050_u;
    private static final String __OBFID;
    
    public GuiSimpleScrolledSelectionListProxy(final RealmsSimpleScrolledSelectionList field_178050_u, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.field_178050_u = field_178050_u;
    }
    
    @Override
    protected int getSize() {
        return this.field_178050_u.getItemCount();
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.field_178050_u.selectItem(n, b, n2, n3);
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.field_178050_u.isSelectedItem(n);
    }
    
    @Override
    protected void drawBackground() {
        this.field_178050_u.renderBackground();
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_178050_u.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    public int func_178048_e() {
        return super.width;
    }
    
    public int func_178047_f() {
        return super.mouseY;
    }
    
    public int func_178049_g() {
        return super.mouseX;
    }
    
    @Override
    protected int getContentHeight() {
        return this.field_178050_u.getMaxPosition();
    }
    
    @Override
    protected int getScrollBarX() {
        return this.field_178050_u.getScrollbarPosition();
    }
    
    @Override
    public void func_178039_p() {
        super.func_178039_p();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float n) {
        if (this.field_178041_q) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.drawBackground();
            final int scrollBarX = this.getScrollBarX();
            final int n2 = scrollBarX + 6;
            this.bindAmountScrolled();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n3 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            final int n4 = this.top + 4 - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n3, n4, instance);
            }
            this.drawSelectionBox(n3, n4, mouseX, mouseY);
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.shadeModel(7425);
            final int func_148135_f = this.func_148135_f();
            if (func_148135_f > 0) {
                final int clamp_int = MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 32, this.bottom - this.top - 8);
                int top = (int)this.amountScrolled * (this.bottom - this.top - clamp_int) / func_148135_f + this.top;
                if (top < this.top) {
                    top = this.top;
                }
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178974_a(0, 255);
                worldRenderer.addVertexWithUV(scrollBarX, this.bottom, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(n2, this.bottom, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(n2, this.top, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(scrollBarX, this.top, 0.0, 0.0, 0.0);
                instance.draw();
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178974_a(8421504, 255);
                worldRenderer.addVertexWithUV(scrollBarX, top + clamp_int, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(n2, top + clamp_int, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(n2, top, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(scrollBarX, top, 0.0, 0.0, 0.0);
                instance.draw();
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178974_a(12632256, 255);
                worldRenderer.addVertexWithUV(scrollBarX, top + clamp_int - 1, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(n2 - 1, top + clamp_int - 1, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(n2 - 1, top, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(scrollBarX, top, 0.0, 0.0, 0.0);
                instance.draw();
            }
            this.func_148142_b(mouseX, mouseY);
            GlStateManager.shadeModel(7424);
        }
    }
    
    static {
        __OBFID = "CL_00001938";
    }
}
