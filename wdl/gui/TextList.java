package wdl.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.util.*;

class TextList extends GuiListExtended
{
    public final int topMargin;
    public final int bottomMargin;
    private List entries;
    
    public TextList(final Minecraft minecraft, final int n, final int n2, final int topMargin, final int bottomMargin) {
        super(minecraft, n, n2, topMargin, n2 - bottomMargin, Minecraft.fontRendererObj.FONT_HEIGHT + 1);
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.entries = new ArrayList();
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.entries.get(n);
    }
    
    @Override
    protected int getSize() {
        return this.entries.size();
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 10;
    }
    
    @Override
    public int getListWidth() {
        return this.width - 18;
    }
    
    public void addLine(final String s) {
        final Iterator<String> iterator = Utils.wordWrap(s, this.getListWidth()).iterator();
        while (iterator.hasNext()) {
            this.entries.add(new TextEntry(this.mc, iterator.next(), 16777215));
        }
    }
    
    public void addBlankLine() {
        this.entries.add(new TextEntry(this.mc, "", 16777215));
    }
    
    public void addLinkLine(final String s, final String s2) {
        final Iterator<String> iterator = Utils.wordWrap(s, this.getListWidth()).iterator();
        while (iterator.hasNext()) {
            this.entries.add(new LinkEntry(this.mc, iterator.next(), s2));
        }
    }
    
    public void clearLines() {
        this.entries.clear();
    }
}
