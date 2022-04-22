package net.minecraft.client.gui;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiLabel extends Gui
{
    protected int field_146167_a;
    protected int field_146161_f;
    public int field_146162_g;
    public int field_146174_h;
    private List field_146173_k;
    public int field_175204_i;
    private boolean centered;
    public boolean visible;
    private boolean labelBgEnabled;
    private int field_146168_n;
    private int field_146169_o;
    private int field_146166_p;
    private int field_146165_q;
    private FontRenderer fontRenderer;
    private int field_146163_s;
    private static final String __OBFID;
    
    public GuiLabel(final FontRenderer fontRenderer, final int field_175204_i, final int field_146162_g, final int field_146174_h, final int field_146167_a, final int field_146161_f, final int field_146168_n) {
        this.field_146167_a = 200;
        this.field_146161_f = 20;
        this.visible = true;
        this.fontRenderer = fontRenderer;
        this.field_175204_i = field_175204_i;
        this.field_146162_g = field_146162_g;
        this.field_146174_h = field_146174_h;
        this.field_146167_a = field_146167_a;
        this.field_146161_f = field_146161_f;
        this.field_146173_k = Lists.newArrayList();
        this.centered = false;
        this.labelBgEnabled = false;
        this.field_146168_n = field_146168_n;
        this.field_146169_o = -1;
        this.field_146166_p = -1;
        this.field_146165_q = -1;
        this.field_146163_s = 0;
    }
    
    public void func_175202_a(final String s) {
        this.field_146173_k.add(I18n.format(s, new Object[0]));
    }
    
    public GuiLabel func_175203_a() {
        this.centered = true;
        return this;
    }
    
    public void drawLabel(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.drawLabelBackground(minecraft, n, n2);
            final int n3 = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2 - this.field_146173_k.size() * 10 / 2;
            while (0 < this.field_146173_k.size()) {
                if (this.centered) {
                    Gui.drawCenteredString(this.fontRenderer, this.field_146173_k.get(0), this.field_146162_g + this.field_146167_a / 2, n3 + 0, this.field_146168_n);
                }
                else {
                    this.drawString(this.fontRenderer, this.field_146173_k.get(0), this.field_146162_g, n3 + 0, this.field_146168_n);
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    protected void drawLabelBackground(final Minecraft minecraft, final int n, final int n2) {
        if (this.labelBgEnabled) {
            final int n3 = this.field_146167_a + this.field_146163_s * 2;
            final int n4 = this.field_146161_f + this.field_146163_s * 2;
            final int n5 = this.field_146162_g - this.field_146163_s;
            final int n6 = this.field_146174_h - this.field_146163_s;
            Gui.drawRect(n5, n6, n5 + n3, n6 + n4, this.field_146169_o);
            this.drawHorizontalLine(n5, n5 + n3, n6, this.field_146166_p);
            this.drawHorizontalLine(n5, n5 + n3, n6 + n4, this.field_146165_q);
            this.drawVerticalLine(n5, n6, n6 + n4, this.field_146166_p);
            this.drawVerticalLine(n5 + n3, n6, n6 + n4, this.field_146165_q);
        }
    }
    
    static {
        __OBFID = "CL_00000671";
    }
}
