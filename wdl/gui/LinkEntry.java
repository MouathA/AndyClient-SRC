package wdl.gui;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;

class LinkEntry extends TextEntry
{
    private final String link;
    private final int textWidth;
    private final int linkWidth;
    
    public LinkEntry(final Minecraft minecraft, final String s, final String link) {
        super(minecraft, s, 5592575);
        this.link = link;
        this.textWidth = Minecraft.fontRendererObj.getStringWidth(s);
        this.linkWidth = Minecraft.fontRendererObj.getStringWidth(link);
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        if (n3 < 0) {
            return;
        }
        super.drawEntry(n, n2, n3, n4, n5, n6, n7, b);
        final int n8 = n6 - n2;
        final int n9 = n7 - n3;
        if (n8 >= 0 && n8 <= this.textWidth && n9 >= 0 && n9 <= n5) {
            int n10 = n6 - 2;
            if (n10 + this.linkWidth + 4 > n4 + n2) {
                n10 = n4 + n2 - (4 + this.linkWidth);
            }
            Gui.drawRect(n10, n7 - 2, n10 + this.linkWidth + 4, n7 + Minecraft.fontRendererObj.FONT_HEIGHT + 2, Integer.MIN_VALUE);
            Utils.drawStringWithShadow(this.link, n10 + 2, n7, 16777215);
        }
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n5 >= 0 && n5 <= this.textWidth) {
            Utils.openLink(this.link);
            return true;
        }
        return false;
    }
}
