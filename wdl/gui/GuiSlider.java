package wdl.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;

class GuiSlider extends GuiButton
{
    private float sliderValue;
    private boolean dragging;
    private final String text;
    private final int max;
    
    public GuiSlider(final int n, final int n2, final int n3, final int n4, final int n5, final String text, final int value, final int max) {
        super(n, n2, n3, n4, n5, text);
        this.text = text;
        this.max = max;
        this.setValue(value);
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
                this.dragging = true;
                this.displayString = I18n.format(this.text, this.getValue());
            }
            minecraft.getTextureManager().bindTexture(GuiSlider.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.enabled) {
                this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
                this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            }
            else {
                this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 46, 4, 20);
                this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 46, 4, 20);
            }
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderValue = (n - (this.xPosition + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
            this.displayString = I18n.format(this.text, this.getValue());
            return this.dragging = true;
        }
        return false;
    }
    
    public int getValue() {
        return (int)(this.sliderValue * this.max);
    }
    
    public void setValue(final int n) {
        this.sliderValue = n / (float)this.max;
        this.displayString = I18n.format(this.text, this.getValue());
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.dragging = false;
    }
}
