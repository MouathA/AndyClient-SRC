package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class GuiOptionSlider extends GuiButton
{
    private float sliderValue;
    public boolean dragging;
    private GameSettings.Options options;
    private final float field_146132_r;
    private final float field_146131_s;
    private static final String __OBFID;
    
    public GuiOptionSlider(final int n, final int n2, final int n3, final GameSettings.Options options) {
        this(n, n2, n3, options, 0.0f, 1.0f);
    }
    
    public GuiOptionSlider(final int n, final int n2, final int n3, final GameSettings.Options options, final float field_146132_r, final float field_146131_s) {
        super(n, n2, n3, 150, 20, "");
        this.sliderValue = 1.0f;
        this.options = options;
        this.field_146132_r = field_146132_r;
        this.field_146131_s = field_146131_s;
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.sliderValue = options.normalizeValue(minecraft.gameSettings.getOptionFloatValue(options));
        this.displayString = minecraft.gameSettings.getKeyBinding(options);
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
                final float denormalizeValue = this.options.denormalizeValue(this.sliderValue);
                minecraft.gameSettings.setOptionFloatValue(this.options, denormalizeValue);
                this.sliderValue = this.options.normalizeValue(denormalizeValue);
                this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
            }
            minecraft.getTextureManager().bindTexture(GuiOptionSlider.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderValue = (n - (this.xPosition + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
            minecraft.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
            this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.dragging = false;
    }
    
    static {
        __OBFID = "CL_00000680";
    }
}
