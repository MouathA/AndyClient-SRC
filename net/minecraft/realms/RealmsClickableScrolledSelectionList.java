package net.minecraft.realms;

import net.minecraft.client.gui.*;

public class RealmsClickableScrolledSelectionList
{
    private final GuiClickableScrolledSelectionListProxy proxy;
    private static final String __OBFID;
    
    public RealmsClickableScrolledSelectionList(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.proxy = new GuiClickableScrolledSelectionListProxy(this, n, n2, n3, n4, n5);
    }
    
    public void render(final int n, final int n2, final float n3) {
        this.proxy.drawScreen(n, n2, n3);
    }
    
    public int width() {
        return this.proxy.func_178044_e();
    }
    
    public int ym() {
        return this.proxy.func_178042_f();
    }
    
    public int xm() {
        return this.proxy.func_178045_g();
    }
    
    protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
    }
    
    public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.renderItem(n, n2, n3, n4, Tezzelator.instance, n5, n6);
    }
    
    public int getItemCount() {
        return 0;
    }
    
    public void selectItem(final int n, final boolean b, final int n2, final int n3) {
    }
    
    public boolean isSelectedItem(final int n) {
        return false;
    }
    
    public void renderBackground() {
    }
    
    public int getMaxPosition() {
        return 0;
    }
    
    public int getScrollbarPosition() {
        return this.proxy.func_178044_e() / 2 + 124;
    }
    
    public void mouseEvent() {
        this.proxy.func_178039_p();
    }
    
    public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
    }
    
    public void scroll(final int n) {
        this.proxy.scrollBy(n);
    }
    
    public int getScroll() {
        return this.proxy.getAmountScrolled();
    }
    
    protected void renderList(final int n, final int n2, final int n3, final int n4) {
    }
    
    public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
    }
    
    public void renderSelected(final int n, final int n2, final int n3, final Tezzelator tezzelator) {
    }
    
    public void setLeftPos(final int slotXBoundsFromLeft) {
        this.proxy.setSlotXBoundsFromLeft(slotXBoundsFromLeft);
    }
    
    static {
        __OBFID = "CL_00002366";
    }
}
