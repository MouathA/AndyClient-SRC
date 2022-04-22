package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;

public class GuiErrorScreen extends GuiScreen
{
    private String field_146313_a;
    private String field_146312_f;
    private static final String __OBFID;
    
    public GuiErrorScreen(final String field_146313_a, final String field_146312_f) {
        this.field_146313_a = field_146313_a;
        this.field_146312_f = field_146312_f;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, GuiErrorScreen.width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawGradientRect(0, 0, GuiErrorScreen.width, GuiErrorScreen.height, -12574688, -11530224);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146313_a, GuiErrorScreen.width / 2, 90, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146312_f, GuiErrorScreen.width / 2, 110, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        GuiErrorScreen.mc.displayGuiScreen(null);
    }
    
    static {
        __OBFID = "CL_00000696";
    }
}
