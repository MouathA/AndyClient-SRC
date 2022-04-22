package net.minecraft.realms;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class RealmsSliderButton extends RealmsButton
{
    public float value;
    public boolean sliding;
    private final float minValue;
    private final float maxValue;
    private int steps;
    private static final String __OBFID;
    
    public RealmsSliderButton(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this(n, n2, n3, n4, n6, 0, 1.0f, (float)n5);
    }
    
    public RealmsSliderButton(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final float minValue, final float maxValue) {
        super(n, n2, n3, n4, 20, "");
        this.value = 1.0f;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = this.toPct((float)n6);
        this.getProxy().displayString = this.getMessage();
    }
    
    public String getMessage() {
        return "";
    }
    
    public float toPct(final float n) {
        return MathHelper.clamp_float((this.clamp(n) - this.minValue) / (this.maxValue - this.minValue), 0.0f, 1.0f);
    }
    
    public float toValue(final float n) {
        return this.clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(n, 0.0f, 1.0f));
    }
    
    public float clamp(float clampSteps) {
        clampSteps = this.clampSteps(clampSteps);
        return MathHelper.clamp_float(clampSteps, this.minValue, this.maxValue);
    }
    
    protected float clampSteps(float n) {
        if (this.steps > 0) {
            n = (float)(this.steps * Math.round(n / this.steps));
        }
        return n;
    }
    
    @Override
    public int getYImage(final boolean b) {
        return 0;
    }
    
    @Override
    public void renderBg(final int n, final int n2) {
        if (this.getProxy().visible) {
            if (this.sliding) {
                this.value = (n - (this.getProxy().xPosition + 4)) / (float)(this.getProxy().getButtonWidth() - 8);
                this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
                final float value = this.toValue(this.value);
                this.clicked(value);
                this.value = this.toPct(value);
                this.getProxy().displayString = this.getMessage();
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(RealmsSliderButton.WIDGETS_LOCATION);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - 8)), this.getProxy().yPosition, 0, 66, 4, 20);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - 8)) + 4, this.getProxy().yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public void clicked(final int n, final int n2) {
        this.value = (n - (this.getProxy().xPosition + 4)) / (float)(this.getProxy().getButtonWidth() - 8);
        this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
        this.clicked(this.toValue(this.value));
        this.getProxy().displayString = this.getMessage();
        this.sliding = true;
    }
    
    public void clicked(final float n) {
    }
    
    @Override
    public void released(final int n, final int n2) {
        this.sliding = false;
    }
    
    static {
        __OBFID = "CL_00001834";
    }
}
