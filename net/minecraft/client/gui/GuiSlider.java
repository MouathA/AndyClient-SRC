package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiSlider extends GuiButton
{
    private float field_175227_p;
    public boolean field_175228_o;
    private String field_175226_q;
    private final float field_175225_r;
    private final float field_175224_s;
    private final GuiPageButtonList.GuiResponder field_175223_t;
    private FormatHelper field_175222_u;
    private static final String __OBFID;
    
    public GuiSlider(final GuiPageButtonList.GuiResponder field_175223_t, final int n, final int n2, final int n3, final String field_175226_q, final float field_175225_r, final float field_175224_s, final float n4, final FormatHelper field_175222_u) {
        super(n, n2, n3, 150, 20, "");
        this.field_175227_p = 1.0f;
        this.field_175226_q = field_175226_q;
        this.field_175225_r = field_175225_r;
        this.field_175224_s = field_175224_s;
        this.field_175227_p = (n4 - field_175225_r) / (field_175224_s - field_175225_r);
        this.field_175222_u = field_175222_u;
        this.field_175223_t = field_175223_t;
        this.displayString = this.func_175221_e();
    }
    
    public float func_175220_c() {
        return this.field_175225_r + (this.field_175224_s - this.field_175225_r) * this.field_175227_p;
    }
    
    public void func_175218_a(final float n, final boolean b) {
        this.field_175227_p = (n - this.field_175225_r) / (this.field_175224_s - this.field_175225_r);
        this.displayString = this.func_175221_e();
        if (b) {
            this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
        }
    }
    
    public float func_175217_d() {
        return this.field_175227_p;
    }
    
    private String func_175221_e() {
        return (this.field_175222_u == null) ? (String.valueOf(I18n.format(this.field_175226_q, new Object[0])) + ": " + this.func_175220_c()) : this.field_175222_u.func_175318_a(this.id, I18n.format(this.field_175226_q, new Object[0]), this.func_175220_c());
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.field_175228_o) {
                this.field_175227_p = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                if (this.field_175227_p < 0.0f) {
                    this.field_175227_p = 0.0f;
                }
                if (this.field_175227_p > 1.0f) {
                    this.field_175227_p = 1.0f;
                }
                this.displayString = this.func_175221_e();
                this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    public void func_175219_a(final float field_175227_p) {
        this.field_175227_p = field_175227_p;
        this.displayString = this.func_175221_e();
        this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.field_175227_p = (n - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.field_175227_p < 0.0f) {
                this.field_175227_p = 0.0f;
            }
            if (this.field_175227_p > 1.0f) {
                this.field_175227_p = 1.0f;
            }
            this.displayString = this.func_175221_e();
            this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
            return this.field_175228_o = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.field_175228_o = false;
    }
    
    static {
        __OBFID = "CL_00001954";
    }
    
    public interface FormatHelper
    {
        String func_175318_a(final int p0, final String p1, final float p2);
    }
}
