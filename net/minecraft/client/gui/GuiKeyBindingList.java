package net.minecraft.client.gui;

import net.minecraft.client.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;

public class GuiKeyBindingList extends GuiListExtended
{
    private final GuiControls field_148191_k;
    private final Minecraft mc;
    private final IGuiListEntry[] listEntries;
    private int maxListLabelWidth;
    private static final String __OBFID;
    
    public GuiKeyBindingList(final GuiControls field_148191_k, final Minecraft mc) {
        super(mc, GuiControls.width, GuiControls.height, 63, GuiControls.height - 32, 20);
        this.maxListLabelWidth = 0;
        this.field_148191_k = field_148191_k;
        this.mc = mc;
        final KeyBinding[] array = (KeyBinding[])ArrayUtils.clone(mc.gameSettings.keyBindings);
        this.listEntries = new IGuiListEntry[array.length + KeyBinding.getKeybinds().size()];
        Arrays.sort(array);
        Object o = null;
        final KeyBinding[] array2 = array;
        while (0 < array.length) {
            final KeyBinding keyBinding = array2[0];
            final String keyCategory = keyBinding.getKeyCategory();
            int n2 = 0;
            if (!keyCategory.equals(o)) {
                o = keyCategory;
                final IGuiListEntry[] listEntries = this.listEntries;
                final int n = 0;
                ++n2;
                listEntries[n] = new CategoryEntry(keyCategory);
            }
            final int stringWidth = Minecraft.fontRendererObj.getStringWidth(I18n.format(keyBinding.getKeyDescription(), new Object[0]));
            if (stringWidth > this.maxListLabelWidth) {
                this.maxListLabelWidth = stringWidth;
            }
            final IGuiListEntry[] listEntries2 = this.listEntries;
            final int n3 = 0;
            ++n2;
            listEntries2[n3] = new KeyEntry(keyBinding, null);
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    protected int getSize() {
        return this.listEntries.length;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.listEntries[n];
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + 32;
    }
    
    static Minecraft access$0(final GuiKeyBindingList list) {
        return list.mc;
    }
    
    static GuiControls access$1(final GuiKeyBindingList list) {
        return list.field_148191_k;
    }
    
    static int access$2(final GuiKeyBindingList list) {
        return list.maxListLabelWidth;
    }
    
    static {
        __OBFID = "CL_00000732";
    }
    
    public class CategoryEntry implements IGuiListEntry
    {
        private final String labelText;
        private final int labelWidth;
        private static final String __OBFID;
        final GuiKeyBindingList this$0;
        
        public CategoryEntry(final GuiKeyBindingList this$0, final String s) {
            this.this$0 = this$0;
            this.labelText = I18n.format(s, new Object[0]);
            this.labelWidth = Minecraft.fontRendererObj.getStringWidth(this.labelText);
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
            final String labelText = this.labelText;
            final GuiScreen currentScreen = GuiKeyBindingList.access$0(this.this$0).currentScreen;
            fontRendererObj.drawString(labelText, GuiScreen.width / 2 - this.labelWidth / 2, n3 + n5 - Minecraft.fontRendererObj.FONT_HEIGHT - 1, 16777215);
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            return false;
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        static {
            __OBFID = "CL_00000734";
        }
    }
    
    public class KeyEntry implements IGuiListEntry
    {
        private final KeyBinding field_148282_b;
        private final String field_148283_c;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnReset;
        private static final String __OBFID;
        final GuiKeyBindingList this$0;
        
        private KeyEntry(final GuiKeyBindingList this$0, final KeyBinding field_148282_b) {
            this.this$0 = this$0;
            this.field_148282_b = field_148282_b;
            this.field_148283_c = I18n.format(field_148282_b.getKeyDescription(), new Object[0]);
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, I18n.format(field_148282_b.getKeyDescription(), new Object[0]));
            this.btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            final boolean b2 = GuiKeyBindingList.access$1(this.this$0).buttonId == this.field_148282_b;
            Minecraft.fontRendererObj.drawString(this.field_148283_c, n2 + 90 - GuiKeyBindingList.access$2(this.this$0), n3 + n5 / 2 - Minecraft.fontRendererObj.FONT_HEIGHT / 2, 16777215);
            this.btnReset.xPosition = n2 + 190;
            this.btnReset.yPosition = n3;
            this.btnReset.enabled = (this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault());
            this.btnReset.drawButton(GuiKeyBindingList.access$0(this.this$0), n6, n7);
            this.btnChangeKeyBinding.xPosition = n2 + 105;
            this.btnChangeKeyBinding.yPosition = n3;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
            if (this.field_148282_b.getKeyCode() != 0) {
                final KeyBinding[] keyBindings = GuiKeyBindingList.access$0(this.this$0).gameSettings.keyBindings;
                while (0 < keyBindings.length) {
                    final KeyBinding keyBinding = keyBindings[0];
                    if (keyBinding != this.field_148282_b && keyBinding.getKeyCode() == this.field_148282_b.getKeyCode()) {
                        break;
                    }
                    int n8 = 0;
                    ++n8;
                }
            }
            if (b2) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
            }
            else if (true) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
            }
            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$0(this.this$0), n6, n7);
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$0(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$1(this.this$0).buttonId = this.field_148282_b;
                return true;
            }
            if (this.btnReset.mousePressed(GuiKeyBindingList.access$0(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$0(this.this$0).gameSettings.setOptionKeyBinding(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
                return true;
            }
            return false;
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.btnChangeKeyBinding.mouseReleased(n2, n3);
            this.btnReset.mouseReleased(n2, n3);
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        KeyEntry(final GuiKeyBindingList list, final KeyBinding keyBinding, final Object o) {
            this(list, keyBinding);
        }
        
        static {
            __OBFID = "CL_00000735";
        }
    }
}
