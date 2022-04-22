package shadersmod.client;

import net.minecraft.client.gui.*;
import java.util.*;
import optifine.*;

class GuiSlotShaders extends GuiSlot
{
    private ArrayList shaderslist;
    private int selectedIndex;
    private long lastClickedCached;
    final GuiShaders shadersGui;
    
    public GuiSlotShaders(final GuiShaders shadersGui, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(shadersGui.getMc(), n, n2, n3, n4, n5);
        this.lastClickedCached = 0L;
        this.shadersGui = shadersGui;
        this.updateList();
        this.amountScrolled = 0.0f;
        final int n6 = this.selectedIndex * n5;
        final int n7 = (n4 - n3) / 2;
        if (n6 > n7) {
            this.scrollBy(n6 - n7);
        }
    }
    
    @Override
    public int getListWidth() {
        return this.width - 20;
    }
    
    public void updateList() {
        this.shaderslist = Shaders.listOfShaders();
        this.selectedIndex = 0;
        while (0 < this.shaderslist.size()) {
            if (this.shaderslist.get(0).equals(Shaders.currentshadername)) {
                this.selectedIndex = 0;
                break;
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected int getSize() {
        return this.shaderslist.size();
    }
    
    @Override
    protected void elementClicked(final int selectedIndex, final boolean b, final int n, final int n2) {
        if (selectedIndex != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
            this.selectedIndex = selectedIndex;
            this.lastClickedCached = this.lastClicked;
            Shaders.setShaderPack(this.shaderslist.get(selectedIndex));
            this.shadersGui.updateButtons();
        }
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return n == this.selectedIndex;
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 6;
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        String s = this.shaderslist.get(n);
        if (s.equals(Shaders.packNameNone)) {
            s = Lang.get("of.options.shaders.packNone");
        }
        else if (s.equals(Shaders.packNameDefault)) {
            s = Lang.get("of.options.shaders.packDefault");
        }
        this.shadersGui.drawCenteredString(s, this.width / 2, n3 + 1, 16777215);
    }
    
    public int getSelectedIndex() {
        return this.selectedIndex;
    }
}
