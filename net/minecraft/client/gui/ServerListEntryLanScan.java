package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry
{
    private final Minecraft field_148288_a;
    private static final String __OBFID;
    
    public ServerListEntryLanScan() {
        this.field_148288_a = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final int n8 = n3 + n5 / 2 - Minecraft.fontRendererObj.FONT_HEIGHT / 2;
        final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
        final String format = I18n.format("lanServer.scanning", new Object[0]);
        final GuiScreen currentScreen = this.field_148288_a.currentScreen;
        fontRendererObj.drawString(format, GuiScreen.width / 2 - Minecraft.fontRendererObj.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, n8, 16777215);
        String s = null;
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                s = "O o o";
                break;
            }
            case 1:
            case 3: {
                s = "o O o";
                break;
            }
            case 2: {
                s = "o o O";
                break;
            }
        }
        final FontRenderer fontRendererObj2 = Minecraft.fontRendererObj;
        final String s2 = s;
        final GuiScreen currentScreen2 = this.field_148288_a.currentScreen;
        fontRendererObj2.drawString(s2, GuiScreen.width / 2 - Minecraft.fontRendererObj.getStringWidth(s) / 2, n8 + Minecraft.fontRendererObj.FONT_HEIGHT, 8421504);
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return false;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    static {
        __OBFID = "CL_00000815";
    }
}
