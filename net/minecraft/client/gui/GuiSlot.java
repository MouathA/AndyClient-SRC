package net.minecraft.client.gui;

import net.minecraft.client.*;
import Mood.Matrix.DefaultParticles.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import Mood.Matrix.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public abstract class GuiSlot
{
    protected final Minecraft mc;
    protected int width;
    protected int height;
    public ParticleEngine pe;
    protected int top;
    protected int bottom;
    protected int right;
    protected int left;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    protected boolean field_148163_i;
    protected float initialClickY;
    protected float scrollMultiplier;
    protected float amountScrolled;
    protected int selectedElement;
    protected long lastClicked;
    protected boolean field_178041_q;
    protected boolean showSelectionBox;
    protected boolean hasListHeader;
    protected int headerPadding;
    private boolean enabled;
    private static final String __OBFID;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public GuiSlot(final Minecraft mc, final int n, final int height, final int top, final int bottom, final int slotHeight) {
        this.pe = new ParticleEngine();
        this.field_148163_i = true;
        this.initialClickY = -2.0f;
        this.selectedElement = -1;
        this.field_178041_q = true;
        this.showSelectionBox = true;
        this.enabled = true;
        this.mc = mc;
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = slotHeight;
        this.left = 0;
        this.right = n;
    }
    
    public void setDimensions(final int n, final int height, final int top, final int bottom) {
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.left = 0;
        this.right = n;
    }
    
    public void setShowSelectionBox(final boolean showSelectionBox) {
        this.showSelectionBox = showSelectionBox;
    }
    
    protected void setHasListHeader(final boolean hasListHeader, final int headerPadding) {
        this.hasListHeader = hasListHeader;
        this.headerPadding = headerPadding;
        if (!hasListHeader) {
            this.headerPadding = 0;
        }
    }
    
    protected abstract int getSize();
    
    protected abstract void elementClicked(final int p0, final boolean p1, final int p2, final int p3);
    
    protected abstract boolean isSelected(final int p0);
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }
    
    protected abstract void drawBackground();
    
    protected void func_178040_a(final int n, final int n2, final int n3) {
    }
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
    }
    
    protected void func_148132_a(final int n, final int n2) {
    }
    
    protected void func_148142_b(final int n, final int n2) {
    }
    
    public int getSlotIndexFromScreenCoords(final int n, final int n2) {
        final int n3 = this.left + this.width / 2 - this.getListWidth() / 2;
        final int n4 = this.left + this.width / 2 + this.getListWidth() / 2;
        final int n5 = n2 - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        final int n6 = n5 / this.slotHeight;
        return (n < this.getScrollBarX() && n >= n3 && n <= n4 && n6 >= 0 && n5 >= 0 && n6 < this.getSize()) ? n6 : -1;
    }
    
    public void registerScrollButtons(final int scrollUpButtonID, final int scrollDownButtonID) {
        this.scrollUpButtonID = scrollUpButtonID;
        this.scrollDownButtonID = scrollDownButtonID;
    }
    
    protected void bindAmountScrolled() {
        this.func_148135_f();
        if (0 < 0) {}
        if (this.field_148163_i || 0 < 0) {}
        this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0f, 0);
    }
    
    public int func_148135_f() {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }
    
    public int getAmountScrolled() {
        return (int)this.amountScrolled;
    }
    
    public boolean isMouseYWithinSlotBounds(final int n) {
        return n >= this.top && n <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right;
    }
    
    public void scrollBy(final int n) {
        this.amountScrolled += n;
        this.bindAmountScrolled();
        this.initialClickY = -2.0f;
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.scrollUpButtonID) {
                this.amountScrolled -= this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
            else if (guiButton.id == this.scrollDownButtonID) {
                this.amountScrolled += this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void handleMouseInput() {
        if (this.isMouseYWithinSlotBounds(this.mouseY)) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                final int n = (this.width - this.getListWidth()) / 2;
                final int n2 = (this.width + this.getListWidth()) / 2;
                final int n3 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                final int selectedElement = n3 / this.slotHeight;
                if (selectedElement < this.getSize() && this.mouseX >= 1 && this.mouseX <= n2 && selectedElement >= 0 && n3 >= 0) {
                    this.elementClicked(selectedElement, false, this.mouseX, this.mouseY);
                    this.selectedElement = selectedElement;
                }
                else if (this.mouseX >= 1 && this.mouseX <= n2 && n3 < 0) {
                    this.func_148132_a(this.mouseX - 1, this.mouseY - this.top + (int)this.amountScrolled - 4);
                }
            }
            if (Mouse.isButtonDown(0) && this.getEnabled()) {
                if (this.initialClickY != -1.0f) {
                    if (this.initialClickY >= 0.0f) {
                        this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = (float)this.mouseY;
                    }
                }
                else if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                    final int n4 = (this.width - this.getListWidth()) / 2;
                    final int n5 = (this.width + this.getListWidth()) / 2;
                    final int n6 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                    final int selectedElement2 = n6 / this.slotHeight;
                    if (selectedElement2 < this.getSize() && this.mouseX >= n4 && this.mouseX <= n5 && selectedElement2 >= 0 && n6 >= 0) {
                        this.elementClicked(selectedElement2, selectedElement2 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L, this.mouseX, this.mouseY);
                        this.selectedElement = selectedElement2;
                        this.lastClicked = Minecraft.getSystemTime();
                    }
                    else if (this.mouseX >= n4 && this.mouseX <= n5 && n6 < 0) {
                        this.func_148132_a(this.mouseX - n4, this.mouseY - this.top + (int)this.amountScrolled - 4);
                    }
                    final int scrollBarX = this.getScrollBarX();
                    final int n7 = scrollBarX + 6;
                    if (this.mouseX >= scrollBarX && this.mouseX <= n7) {
                        this.scrollMultiplier = -1.0f;
                        this.func_148135_f();
                        if (1 < 1) {}
                        this.scrollMultiplier /= (this.bottom - this.top - MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 32, this.bottom - this.top - 8)) / 1;
                    }
                    else {
                        this.scrollMultiplier = 1.0f;
                    }
                    if (true) {
                        this.initialClickY = (float)this.mouseY;
                    }
                    else {
                        this.initialClickY = -2.0f;
                    }
                }
                else {
                    this.initialClickY = -2.0f;
                }
            }
            else {
                this.initialClickY = -1.0f;
            }
            Mouse.getEventDWheel();
            if (true) {
                if (1 <= 0) {
                    if (1 < 0) {}
                }
                this.amountScrolled += 1 * this.slotHeight / 2;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float n) {
        if (this.field_178041_q) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            final int scrollBarX = this.getScrollBarX();
            final int n2 = scrollBarX + 6;
            this.bindAmountScrolled();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final float n3 = 32.0f;
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178991_c(2105376);
            worldRenderer.addVertexWithUV(this.left, this.bottom, 0.0, this.left / n3, (this.bottom + (int)this.amountScrolled) / n3);
            worldRenderer.addVertexWithUV(this.right, this.bottom, 0.0, this.right / n3, (this.bottom + (int)this.amountScrolled) / n3);
            worldRenderer.addVertexWithUV(this.right, this.top, 0.0, this.right / n3, (this.top + (int)this.amountScrolled) / n3);
            worldRenderer.addVertexWithUV(this.left, this.top, 0.0, this.left / n3, (this.top + (int)this.amountScrolled) / n3);
            instance.draw();
            final int n4 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            final int n5 = this.top + 4 - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n4, n5, instance);
            }
            this.drawSelectionBox(n4, n5, mouseX, mouseY);
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.shadeModel(7425);
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178974_a(0, 0);
            worldRenderer.addVertexWithUV(this.left, this.top + 4, 0.0, 0.0, 1.0);
            worldRenderer.addVertexWithUV(this.right, this.top + 4, 0.0, 1.0, 1.0);
            worldRenderer.func_178974_a(0, 255);
            worldRenderer.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
            worldRenderer.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178974_a(0, 255);
            worldRenderer.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
            worldRenderer.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
            worldRenderer.func_178974_a(0, 0);
            worldRenderer.addVertexWithUV(this.right, this.bottom - 4, 0.0, 1.0, 0.0);
            worldRenderer.addVertexWithUV(this.left, this.bottom - 4, 0.0, 0.0, 0.0);
            instance.draw();
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
            if (MatrixBGManagerForInGame.Particle) {
                this.pe.render(n, n);
            }
            this.func_148142_b(mouseX, mouseY);
            GlStateManager.shadeModel(7424);
        }
    }
    
    public void func_178039_p() {
        if (this.isMouseYWithinSlotBounds(this.mouseY)) {
            if (Mouse.isButtonDown(0) && this.getEnabled()) {
                if (this.initialClickY == -1.0f) {
                    if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                        final int n = this.width / 2 - this.getListWidth() / 2;
                        final int n2 = this.width / 2 + this.getListWidth() / 2;
                        final int n3 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        final int selectedElement = n3 / this.slotHeight;
                        if (this.mouseX >= n && this.mouseX <= n2 && selectedElement >= 0 && n3 >= 0 && selectedElement < this.getSize()) {
                            this.elementClicked(selectedElement, selectedElement == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L, this.mouseX, this.mouseY);
                            this.selectedElement = selectedElement;
                            this.lastClicked = Minecraft.getSystemTime();
                        }
                        else if (this.mouseX >= n && this.mouseX <= n2 && n3 < 0) {
                            this.func_148132_a(this.mouseX - n, this.mouseY - this.top + (int)this.amountScrolled - 4);
                        }
                        final int scrollBarX = this.getScrollBarX();
                        final int n4 = scrollBarX + 6;
                        if (this.mouseX >= scrollBarX && this.mouseX <= n4) {
                            this.scrollMultiplier = -1.0f;
                            this.func_148135_f();
                            if (1 < 1) {}
                            this.scrollMultiplier /= (this.bottom - this.top - MathHelper.clamp_int((int)((this.bottom - this.top) * (this.bottom - this.top) / (float)this.getContentHeight()), 32, this.bottom - this.top - 8)) / (float)1;
                        }
                        else {
                            this.scrollMultiplier = 1.0f;
                        }
                        if (true) {
                            this.initialClickY = (float)this.mouseY;
                        }
                        else {
                            this.initialClickY = -2.0f;
                        }
                    }
                    else {
                        this.initialClickY = -2.0f;
                    }
                }
                else if (this.initialClickY >= 0.0f) {
                    this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = (float)this.mouseY;
                }
            }
            else {
                this.initialClickY = -1.0f;
            }
            Mouse.getEventDWheel();
            if (true) {
                if (1 <= 0) {
                    if (1 < 0) {}
                }
                this.amountScrolled += 1 * this.slotHeight / 2;
            }
        }
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean getEnabled() {
        return this.enabled;
    }
    
    public int getListWidth() {
        return 220;
    }
    
    protected void drawSelectionBox(final int n, final int n2, final int n3, final int n4) {
        final int size = this.getSize();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        while (0 < size) {
            final int n5 = n2 + 0 * this.slotHeight + this.headerPadding;
            final int n6 = this.slotHeight - 4;
            if (n5 > this.bottom || n5 + n6 < this.top) {
                this.func_178040_a(0, n, n5);
            }
            if (this.showSelectionBox && this.isSelected(0)) {
                final int n7 = this.left + (this.width / 2 - this.getListWidth() / 2);
                final int n8 = this.left + this.width / 2 + this.getListWidth() / 2;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178991_c(Color.LIGHT_GRAY.getRGB());
                worldRenderer.addVertexWithUV(n7, n5 + n6 + 2, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(n8, n5 + n6 + 2, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(n8, n5 - 2, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(n7, n5 - 2, 0.0, 0.0, 0.0);
                worldRenderer.func_178991_c(Integer.MIN_VALUE);
                worldRenderer.addVertexWithUV(n7 + 1, n5 + n6 + 1, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(n8 - 1, n5 + n6 + 1, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(n8 - 1, n5 - 1, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(n7 + 1, n5 - 1, 0.0, 0.0, 0.0);
                instance.draw();
            }
            this.drawSlot(0, n, n5, n6, n3, n4);
            int n9 = 0;
            ++n9;
        }
    }
    
    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }
    
    protected void overlayBackground(final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n5 = 32.0f;
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178974_a(4210752, n4);
        worldRenderer.addVertexWithUV(this.left, n2, 0.0, 0.0, n2 / n5);
        worldRenderer.addVertexWithUV(this.left + this.width, n2, 0.0, this.width / n5, n2 / n5);
        worldRenderer.func_178974_a(4210752, n3);
        worldRenderer.addVertexWithUV(this.left + this.width, n, 0.0, this.width / n5, n / n5);
        worldRenderer.addVertexWithUV(this.left, n, 0.0, 0.0, n / n5);
        instance.draw();
    }
    
    public void setSlotXBoundsFromLeft(final int left) {
        this.left = left;
        this.right = left + this.width;
    }
    
    public int getSlotHeight() {
        return this.slotHeight;
    }
    
    static {
        __OBFID = "CL_00000679";
    }
}
