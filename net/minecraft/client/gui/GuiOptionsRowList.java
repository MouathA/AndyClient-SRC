package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import com.google.common.collect.*;

public class GuiOptionsRowList extends GuiListExtended
{
    private final List field_148184_k;
    private static final String __OBFID;
    
    public GuiOptionsRowList(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5, final GameSettings.Options... array) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_148184_k = Lists.newArrayList();
        this.field_148163_i = false;
        while (0 < array.length) {
            this.field_148184_k.add(new Row(this.func_148182_a(minecraft, n / 2 - 155, 0, array[0]), this.func_148182_a(minecraft, n / 2 - 155 + 160, 0, (0 < array.length - 1) ? array[1] : null)));
            final int n6;
            n6 += 2;
        }
    }
    
    private GuiButton func_148182_a(final Minecraft minecraft, final int n, final int n2, final GameSettings.Options options) {
        if (options == null) {
            return null;
        }
        final int returnEnumOrdinal = options.returnEnumOrdinal();
        return options.getEnumFloat() ? new GuiOptionSlider(returnEnumOrdinal, n, n2, options) : new GuiOptionButton(returnEnumOrdinal, n, n2, options, minecraft.gameSettings.getKeyBinding(options));
    }
    
    public Row func_180792_c(final int n) {
        return this.field_148184_k.get(n);
    }
    
    @Override
    protected int getSize() {
        return this.field_148184_k.size();
    }
    
    @Override
    public int getListWidth() {
        return 400;
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 32;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.func_180792_c(n);
    }
    
    static {
        __OBFID = "CL_00000677";
    }
    
    public static class Row implements IGuiListEntry
    {
        private final Minecraft field_148325_a;
        private final GuiButton field_148323_b;
        private final GuiButton field_148324_c;
        private static final String __OBFID;
        
        public Row(final GuiButton field_148323_b, final GuiButton field_148324_c) {
            this.field_148325_a = Minecraft.getMinecraft();
            this.field_148323_b = field_148323_b;
            this.field_148324_c = field_148324_c;
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            if (this.field_148323_b != null) {
                this.field_148323_b.yPosition = n3;
                this.field_148323_b.drawButton(this.field_148325_a, n6, n7);
            }
            if (this.field_148324_c != null) {
                this.field_148324_c.yPosition = n3;
                this.field_148324_c.drawButton(this.field_148325_a, n6, n7);
            }
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.field_148323_b.mousePressed(this.field_148325_a, n2, n3)) {
                if (this.field_148323_b instanceof GuiOptionButton) {
                    this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
                    this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
                }
                return true;
            }
            if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, n2, n3)) {
                if (this.field_148324_c instanceof GuiOptionButton) {
                    this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
                    this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
                }
                return true;
            }
            return false;
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.field_148323_b != null) {
                this.field_148323_b.mouseReleased(n2, n3);
            }
            if (this.field_148324_c != null) {
                this.field_148324_c.mouseReleased(n2, n3);
            }
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        static {
            __OBFID = "CL_00000678";
        }
    }
}
