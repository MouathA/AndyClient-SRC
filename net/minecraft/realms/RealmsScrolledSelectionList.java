package net.minecraft.realms;

import net.minecraft.client.gui.*;

public class RealmsScrolledSelectionList
{
    private final GuiSlotRealmsProxy proxy;
    private static final String __OBFID;
    
    public RealmsScrolledSelectionList(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.proxy = new GuiSlotRealmsProxy(this, n, n2, n3, n4, n5);
    }
    
    public void render(final int n, final int n2, final float n3) {
        this.proxy.drawScreen(n, n2, n3);
    }
    
    public int width() {
        return this.proxy.func_154338_k();
    }
    
    public int ym() {
        return this.proxy.func_154339_l();
    }
    
    public int xm() {
        return this.proxy.func_154337_m();
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
        return this.proxy.func_154338_k() / 2 + 124;
    }
    
    public void mouseEvent() {
        this.proxy.func_178039_p();
    }
    
    public void scroll(final int n) {
        this.proxy.scrollBy(n);
    }
    
    public int getScroll() {
        return this.proxy.getAmountScrolled();
    }
    
    protected void renderList(final int n, final int n2, final int n3, final int n4) {
    }
    
    static {
        __OBFID = "CL_00001863";
    }
}
