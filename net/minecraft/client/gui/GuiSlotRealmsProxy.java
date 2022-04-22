package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiSlotRealmsProxy extends GuiSlot
{
    private final RealmsScrolledSelectionList selectionList;
    private static final String __OBFID;
    
    public GuiSlotRealmsProxy(final RealmsScrolledSelectionList selectionList, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.selectionList = selectionList;
    }
    
    @Override
    protected int getSize() {
        return this.selectionList.getItemCount();
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.selectionList.selectItem(n, b, n2, n3);
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.selectionList.isSelectedItem(n);
    }
    
    @Override
    protected void drawBackground() {
        this.selectionList.renderBackground();
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.selectionList.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    public int func_154338_k() {
        return super.width;
    }
    
    public int func_154339_l() {
        return super.mouseY;
    }
    
    public int func_154337_m() {
        return super.mouseX;
    }
    
    @Override
    protected int getContentHeight() {
        return this.selectionList.getMaxPosition();
    }
    
    @Override
    protected int getScrollBarX() {
        return this.selectionList.getScrollbarPosition();
    }
    
    @Override
    public void func_178039_p() {
        super.func_178039_p();
    }
    
    static {
        __OBFID = "CL_00001846";
    }
}
