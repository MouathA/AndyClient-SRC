package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import DTool.util.*;
import font.*;
import Mood.Designs.*;
import net.minecraft.client.audio.*;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures;
    public int width;
    private boolean isRenderedString;
    public boolean isRendered;
    public int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public int exampleSlide;
    public boolean visible;
    protected boolean hovered;
    public int mouseX;
    public int mouseY;
    private DelayTimer timer;
    private AnimationTimer anim;
    
    static {
        buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    }
    
    public GuiButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, 200, 20, s);
    }
    
    public int getMouseX() {
        return this.mouseX;
    }
    
    public int getMouseY() {
        return this.mouseY;
    }
    
    public GuiButton(final int id, final int xPosition, final int yPosition, final int width, final int height, final String displayString) {
        this.isRenderedString = true;
        this.isRendered = true;
        this.exampleSlide = 0;
        this.mouseX = 0;
        this.mouseY = 0;
        this.timer = new DelayTimer();
        this.anim = new AnimationTimer(5);
        this.enabled = true;
        this.visible = true;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.displayString = displayString;
        this.mouseX = this.mouseX;
        this.mouseY = this.mouseY;
    }
    
    protected int getHoverState(final boolean b) {
        if (this.enabled) {
            if (b) {}
        }
        return 2;
    }
    
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            final int n3 = (int)(this.anim.getValue() * 200.0);
            if (10 < 10 || !this.enabled) {}
            final int rgb = new Color(255, 255, 255, 10).getRGB();
            final int rgb2 = new Color(177, 177, 177).getRGB();
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(minecraft, n, n2);
            final int n4 = (int)(this.anim.getValue() * 200.0);
            if (1 < 1 || !this.enabled) {}
            GuiRenderUtils.drawBorderedRect(this.xPosition + 0.4f, this.yPosition + 0.4f, (float)this.width, (float)this.height, 1.0f, new Color(255, 255, 255, 1).getRGB(), 1);
            GuiRenderUtils.drawRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, -1744830464);
            GuiRenderUtils.drawBorderedRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, 1.0f, new Color(255, 255, 255, 1).getRGB(), this.enabled ? rgb : rgb2);
            FontUtil.normal.drawCenteredString(this.displayString, (float)(this.xPosition + this.width / 3), (float)(this.yPosition + (this.height - 5) / 2), 14737632);
            if (this.enabled && this.hovered) {
                GuiRenderUtils.drawBorderedRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, 1.0f, new Color(255, 255, 255, 10).getRGB(), 10);
            }
            if (Designs.White) {
                final int xPosition = this.xPosition;
                final int yPosition = this.yPosition;
                final int width = this.width;
                final int height = this.height;
                if (this.hovered) {
                    if (this.exampleSlide < width) {
                        this.exampleSlide += 5;
                    }
                }
                else if (this.exampleSlide > 0) {
                    this.exampleSlide -= 5;
                }
                Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, Integer.MAX_VALUE);
                this.mousePressed(minecraft, this.getMouseX(), this.getMouseY());
                FontUtil.normal.drawCenteredString(this.displayString, (float)(this.xPosition + this.width / 3), (float)(this.yPosition + (this.height - 5) / 2), 14737632);
            }
        }
    }
    
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
    }
    
    public void mouseReleased(final int n, final int n2) {
    }
    
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        return this.enabled && this.visible && n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
    }
    
    public boolean isMouseOver() {
        return this.hovered;
    }
    
    public void drawButtonForegroundLayer(final int n, final int n2) {
    }
    
    public void playPressSound(final SoundHandler soundHandler) {
        soundHandler.playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
    }
    
    public void setRendered(final boolean isRendered) {
        this.isRendered = isRendered;
    }
    
    public int getButtonWidth() {
        return this.width;
    }
    
    public void func_175211_a(final int width) {
        this.width = width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setDisplayString(final String displayString) {
        this.displayString = displayString;
    }
    
    public void setRenderedString(final boolean isRenderedString) {
        this.isRenderedString = isRenderedString;
    }
}
