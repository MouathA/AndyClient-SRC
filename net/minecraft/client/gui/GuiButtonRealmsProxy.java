package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiButtonRealmsProxy extends GuiButton
{
    private RealmsButton field_154318_o;
    private static final String __OBFID;
    
    public GuiButtonRealmsProxy(final RealmsButton field_154318_o, final int n, final int n2, final int n3, final String s) {
        super(n, n2, n3, s);
        this.field_154318_o = field_154318_o;
    }
    
    public GuiButtonRealmsProxy(final RealmsButton field_154318_o, final int n, final int n2, final int n3, final String s, final int n4, final int n5) {
        super(n, n2, n3, n4, n5, s);
        this.field_154318_o = field_154318_o;
    }
    
    public int getId() {
        return super.id;
    }
    
    public boolean getEnabled() {
        return super.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        super.enabled = enabled;
    }
    
    public void setText(final String displayString) {
        super.displayString = displayString;
    }
    
    @Override
    public int getButtonWidth() {
        return super.getButtonWidth();
    }
    
    public int getPositionY() {
        return super.yPosition;
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.field_154318_o.clicked(n, n2);
        }
        return super.mousePressed(minecraft, n, n2);
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.field_154318_o.released(n, n2);
    }
    
    public void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        this.field_154318_o.renderBg(n, n2);
    }
    
    public RealmsButton getRealmsButton() {
        return this.field_154318_o;
    }
    
    public int getHoverState(final boolean b) {
        return this.field_154318_o.getYImage(b);
    }
    
    public int func_154312_c(final boolean b) {
        return super.getHoverState(b);
    }
    
    public int func_175232_g() {
        return this.height;
    }
    
    static {
        __OBFID = "CL_00001848";
    }
}
