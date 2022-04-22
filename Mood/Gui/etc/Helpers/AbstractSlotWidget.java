package Mood.Gui.etc.Helpers;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.util.*;

public abstract class AbstractSlotWidget extends GuiSlot
{
    private final GuiScreen parent;
    protected int selected;
    
    public GuiScreen getParent() {
        return this.parent;
    }
    
    public int getSelected() {
        return this.selected;
    }
    
    public AbstractSlotWidget(final int n, final int n2, final int n3, final int n4, final int n5, final int selected, final GuiScreen parent) {
        super(Minecraft.getInstance(), n, n2, n3, n4, n5);
        this.selected = selected;
        this.parent = parent;
    }
    
    public AbstractSlotWidget(final int n, final int n2, final int n3, final int n4, final int n5, final GuiScreen guiScreen) {
        this(n, n2, n3, n4, n5, -1, guiScreen);
    }
    
    @Override
    protected int getSize() {
        return this.getSlotEntries().size();
    }
    
    @Override
    protected void elementClicked(final int selected, final boolean b, final int n, final int n2) {
        this.selected = selected;
        this.getSlotEntries().get(selected).onClick(b, selected, n, n2, this.parent);
    }
    
    @Override
    protected void drawBackground() {
        this.parent.drawDefaultBackground();
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.selected == n;
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.getSlotEntries().get(n).draw(n, n2, n3, this.getListWidth(), this.getSlotHeight(), n5, n6, this.parent);
    }
    
    public abstract List getSlotEntries();
}
