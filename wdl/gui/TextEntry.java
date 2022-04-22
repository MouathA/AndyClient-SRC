package wdl.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;

class TextEntry implements GuiListExtended.IGuiListEntry
{
    private final String text;
    private final int color;
    protected final Minecraft mc;
    
    public TextEntry(final Minecraft minecraft, final String s) {
        this(minecraft, s, 1048575);
    }
    
    public TextEntry(final Minecraft mc, final String text, final int color) {
        this.mc = mc;
        this.text = text;
        this.color = color;
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        if (n3 < 0) {
            return;
        }
        Utils.drawStringWithShadow(this.text, n2, n3 + 1, this.color);
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return false;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
}
