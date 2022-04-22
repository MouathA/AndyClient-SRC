package net.minecraft.realms;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class RealmsButton
{
    protected static final ResourceLocation WIDGETS_LOCATION;
    private GuiButtonRealmsProxy proxy;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001890";
        WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
    }
    
    public RealmsButton(final int n, final int n2, final int n3, final String s) {
        this.proxy = new GuiButtonRealmsProxy(this, n, n2, n3, s);
    }
    
    public RealmsButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        this.proxy = new GuiButtonRealmsProxy(this, n, n2, n3, s, n4, n5);
    }
    
    public GuiButton getProxy() {
        return this.proxy;
    }
    
    public int id() {
        return this.proxy.getId();
    }
    
    public boolean active() {
        return this.proxy.getEnabled();
    }
    
    public void active(final boolean enabled) {
        this.proxy.setEnabled(enabled);
    }
    
    public void msg(final String text) {
        this.proxy.setText(text);
    }
    
    public int getWidth() {
        return this.proxy.getButtonWidth();
    }
    
    public int getHeight() {
        return this.proxy.func_175232_g();
    }
    
    public int y() {
        return this.proxy.getPositionY();
    }
    
    public void render(final int n, final int n2) {
        this.proxy.drawButton(Minecraft.getMinecraft(), n, n2);
    }
    
    public void clicked(final int n, final int n2) {
    }
    
    public void released(final int n, final int n2) {
    }
    
    public void blit(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public void renderBg(final int n, final int n2) {
    }
    
    public int getYImage(final boolean b) {
        return this.proxy.func_154312_c(b);
    }
}
