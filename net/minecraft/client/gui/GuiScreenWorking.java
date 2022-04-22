package net.minecraft.client.gui;

import net.minecraft.util.*;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate
{
    private String field_146591_a;
    private String field_146589_f;
    private int field_146590_g;
    private boolean field_146592_h;
    private static final String __OBFID;
    
    public GuiScreenWorking() {
        this.field_146591_a = "";
        this.field_146589_f = "";
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.resetProgressAndMessage(s);
    }
    
    @Override
    public void resetProgressAndMessage(final String field_146591_a) {
        this.field_146591_a = field_146591_a;
        this.displayLoadingString("Working...");
    }
    
    @Override
    public void displayLoadingString(final String field_146589_f) {
        this.field_146589_f = field_146589_f;
        this.setLoadingProgress(0);
    }
    
    @Override
    public void setLoadingProgress(final int field_146590_g) {
        this.field_146590_g = field_146590_g;
    }
    
    @Override
    public void setDoneWorking() {
        this.field_146592_h = true;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.field_146592_h) {
            GuiScreenWorking.mc.displayGuiScreen(null);
        }
        else {
            this.drawDefaultBackground();
            Gui.drawCenteredString(this.fontRendererObj, this.field_146591_a, GuiScreenWorking.width / 2, 70, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + " " + this.field_146590_g + "%", GuiScreenWorking.width / 2, 90, 16777215);
            super.drawScreen(n, n2, n3);
        }
    }
    
    static {
        __OBFID = "CL_00000707";
    }
}
