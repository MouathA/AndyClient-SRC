package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

public abstract class GuiResourcePackList extends GuiListExtended
{
    protected final Minecraft mc;
    protected final List field_148204_l;
    private static final String __OBFID;
    
    public GuiResourcePackList(final Minecraft mc, final int n, final int n2, final List field_148204_l) {
        super(mc, n, n2, 32, n2 - 55 + 4, 36);
        this.mc = mc;
        this.field_148204_l = field_148204_l;
        this.field_148163_i = false;
        this.setHasListHeader(true, (int)(Minecraft.fontRendererObj.FONT_HEIGHT * 1.5f));
    }
    
    @Override
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
        final String string = new StringBuilder().append(EnumChatFormatting.UNDERLINE).append(EnumChatFormatting.BOLD).append(this.getListHeader()).toString();
        Minecraft.fontRendererObj.drawString(string, n + this.width / 2 - Minecraft.fontRendererObj.getStringWidth(string) / 2, Math.min(this.top + 3, n2), 16777215);
    }
    
    protected abstract String getListHeader();
    
    public List getList() {
        return this.field_148204_l;
    }
    
    @Override
    protected int getSize() {
        return this.getList().size();
    }
    
    @Override
    public ResourcePackListEntry getListEntry(final int n) {
        return this.getList().get(n);
    }
    
    @Override
    public int getListWidth() {
        return this.width;
    }
    
    @Override
    protected int getScrollBarX() {
        return this.right - 6;
    }
    
    public IGuiListEntry getListEntry1(final int n) {
        return this.getListEntry(n);
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
    }
    
    static {
        __OBFID = "CL_00000825";
    }
}
