package net.minecraft.client.gui;

import net.minecraft.client.*;

public abstract class GuiListExtended extends GuiSlot
{
    private static final String __OBFID;
    
    public GuiListExtended(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(minecraft, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.getListEntry(n).drawEntry(n, n2, n3, this.getListWidth(), n4, n5, n6, this.getSlotIndexFromScreenCoords(n5, n6) == n);
    }
    
    @Override
    protected void func_178040_a(final int n, final int n2, final int n3) {
        this.getListEntry(n).setSelected(n, n2, n3);
    }
    
    public boolean func_148179_a(final int n, final int n2, final int n3) {
        if (this.isMouseYWithinSlotBounds(n2)) {
            final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
            if (slotIndexFromScreenCoords >= 0 && this.getListEntry(slotIndexFromScreenCoords).mousePressed(slotIndexFromScreenCoords, n, n2, n3, n - (this.left + this.width / 2 - this.getListWidth() / 2 + 2), n2 - (this.top + 4 - this.getAmountScrolled() + slotIndexFromScreenCoords * this.slotHeight + this.headerPadding))) {
                this.setEnabled(false);
                return true;
            }
        }
        return false;
    }
    
    public boolean func_148181_b(final int n, final int n2, final int n3) {
        while (0 < this.getSize()) {
            this.getListEntry(0).mouseReleased(0, n, n2, n3, n - (this.left + this.width / 2 - this.getListWidth() / 2 + 2), n2 - (this.top + 4 - this.getAmountScrolled() + 0 * this.slotHeight + this.headerPadding));
            int n4 = 0;
            ++n4;
        }
        this.setEnabled(true);
        return false;
    }
    
    public abstract IGuiListEntry getListEntry(final int p0);
    
    static {
        __OBFID = "CL_00000674";
    }
    
    public interface IGuiListEntry
    {
        void setSelected(final int p0, final int p1, final int p2);
        
        void drawEntry(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
        
        boolean mousePressed(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
        
        void mouseReleased(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    }
}
