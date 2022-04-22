package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;

public class GuiListButton extends GuiButton
{
    private boolean field_175216_o;
    private String field_175215_p;
    private final GuiPageButtonList.GuiResponder field_175214_q;
    private static final String __OBFID;
    
    public GuiListButton(final GuiPageButtonList.GuiResponder field_175214_q, final int n, final int n2, final int n3, final String field_175215_p, final boolean field_175216_o) {
        super(n, n2, n3, 150, 20, "");
        this.field_175215_p = field_175215_p;
        this.field_175216_o = field_175216_o;
        this.displayString = this.func_175213_c();
        this.field_175214_q = field_175214_q;
    }
    
    private String func_175213_c() {
        return String.valueOf(I18n.format(this.field_175215_p, new Object[0])) + ": " + (this.field_175216_o ? I18n.format("gui.yes", new Object[0]) : I18n.format("gui.no", new Object[0]));
    }
    
    public void func_175212_b(final boolean field_175216_o) {
        this.field_175216_o = field_175216_o;
        this.displayString = this.func_175213_c();
        this.field_175214_q.func_175321_a(this.id, field_175216_o);
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.field_175216_o = !this.field_175216_o;
            this.displayString = this.func_175213_c();
            this.field_175214_q.func_175321_a(this.id, this.field_175216_o);
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00001953";
    }
}
