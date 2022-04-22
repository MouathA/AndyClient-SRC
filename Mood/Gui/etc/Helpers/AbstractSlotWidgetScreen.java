package Mood.Gui.etc.Helpers;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.*;

public abstract class AbstractSlotWidgetScreen extends GuiScreen
{
    private List list;
    
    @Override
    public void setWorldAndResolution(final Minecraft minecraft, final int n, final int n2) {
        super.setWorldAndResolution(minecraft, n, n2);
        this.list = new List(n, n2, this.getTop(), this.getBottom(), this.getSlotHeight(), this.getDefaultSelection(), this);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.list.drawScreen(n, n2, n3);
        super.drawScreen(n, n2, n3);
    }
    
    public SlotEntry getSelected() {
        return this.getSlotEntries().get(this.list.getSelected());
    }
    
    public abstract int getDefaultSelection();
    
    public abstract int getSlotHeight();
    
    public abstract int getTop();
    
    public abstract int getBottom();
    
    public abstract java.util.List getSlotEntries();
    
    class List extends AbstractSlotWidget
    {
        final AbstractSlotWidgetScreen this$0;
        
        public List(final AbstractSlotWidgetScreen this$0, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final GuiScreen guiScreen) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, n6, guiScreen);
        }
        
        @Override
        public java.util.List getSlotEntries() {
            return this.this$0.getSlotEntries();
        }
    }
}
