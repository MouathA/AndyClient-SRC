package wdl.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import wdl.*;
import wdl.api.*;
import java.util.*;
import net.minecraft.client.*;

public class GuiWDLMessages extends GuiScreen
{
    private String hoveredButtonDescription;
    private GuiScreen parent;
    private GuiMessageTypeList list;
    private GuiButton enableAllButton;
    private GuiButton resetButton;
    
    public GuiWDLMessages(final GuiScreen parent) {
        this.hoveredButtonDescription = null;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.enableAllButton = new GuiButton(100, GuiWDLMessages.width / 2 - 155, 18, 150, 20, this.getAllEnabledText());
        this.buttonList.add(this.enableAllButton);
        this.resetButton = new GuiButton(101, GuiWDLMessages.width / 2 + 5, 18, 150, 20, I18n.format("wdl.gui.messages.reset", new Object[0]));
        this.buttonList.add(this.resetButton);
        this.list = new GuiMessageTypeList();
        this.buttonList.add(new GuiButton(102, GuiWDLMessages.width / 2 - 100, GuiWDLMessages.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (!guiButton.enabled) {
            return;
        }
        if (guiButton.id == 100) {
            WDLMessages.enableAllMessages ^= true;
            WDL.baseProps.setProperty("Messages.enableAll", Boolean.toString(WDLMessages.enableAllMessages));
            guiButton.displayString = this.getAllEnabledText();
        }
        else if (guiButton.id == 101) {
            GuiWDLMessages.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("wdl.gui.messages.reset.confirm.title", new Object[0]), I18n.format("wdl.gui.messages.reset.confirm.subtitle", new Object[0]), 101));
        }
        else if (guiButton.id == 102) {
            GuiWDLMessages.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void confirmClicked(final boolean p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifeq            7
        //     4: iload_2        
        //     5: bipush          101
        //     7: getstatic       wdl/gui/GuiWDLMessages.mc:Lnet/minecraft/client/Minecraft;
        //    10: aload_0        
        //    11: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    14: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0007 (coming from #0005).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (this.list.func_148179_a(n, n2, n3)) {
            return;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.hoveredButtonDescription = null;
        this.drawDefaultBackground();
        this.list.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.messages.message.title", new Object[0]), GuiWDLMessages.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
        if (this.hoveredButtonDescription != null) {
            Utils.drawGuiInfoBox(this.hoveredButtonDescription, GuiWDLMessages.width, GuiWDLMessages.height, 48);
        }
        else if (this.enableAllButton.isMouseOver()) {
            Utils.drawGuiInfoBox(I18n.format("wdl.gui.messages.all.description", new Object[0]), GuiWDLMessages.width, GuiWDLMessages.height, 48);
        }
        else if (this.resetButton.isMouseOver()) {
            Utils.drawGuiInfoBox(I18n.format("wdl.gui.messages.reset.description", new Object[0]), GuiWDLMessages.width, GuiWDLMessages.height, 48);
        }
    }
    
    private String getAllEnabledText() {
        return I18n.format("wdl.gui.messages.all." + WDLMessages.enableAllMessages, new Object[0]);
    }
    
    static FontRenderer access$0(final GuiWDLMessages guiWDLMessages) {
        return guiWDLMessages.fontRendererObj;
    }
    
    static void access$1(final GuiWDLMessages guiWDLMessages, final String hoveredButtonDescription) {
        guiWDLMessages.hoveredButtonDescription = hoveredButtonDescription;
    }
    
    private class GuiMessageTypeList extends GuiListExtended
    {
        private List entries;
        final GuiWDLMessages this$0;
        
        public GuiMessageTypeList(final GuiWDLMessages this$0) {
            this.this$0 = this$0;
            super(GuiWDLMessages.mc, GuiWDLMessages.width, GuiWDLMessages.height, 39, GuiWDLMessages.height - 32, 20);
            this.entries = new ArrayList() {
                final GuiMessageTypeList this$1;
                
                {
                    for (final Map.Entry<K, Collection> entry : WDLMessages.getTypes().asMap().entrySet()) {
                        this.add(this$1.new CategoryEntry((MessageTypeCategory)entry.getKey()));
                        final Iterator<IWDLMessageType> iterator2 = entry.getValue().iterator();
                        while (iterator2.hasNext()) {
                            this.add(this$1.new MessageTypeEntry(iterator2.next(), (MessageTypeCategory)entry.getKey()));
                        }
                    }
                }
            };
        }
        
        @Override
        public IGuiListEntry getListEntry(final int n) {
            return this.entries.get(n);
        }
        
        @Override
        protected int getSize() {
            return this.entries.size();
        }
        
        static Minecraft access$0(final GuiMessageTypeList list) {
            return list.mc;
        }
        
        static GuiWDLMessages access$1(final GuiMessageTypeList list) {
            return list.this$0;
        }
        
        private class CategoryEntry implements IGuiListEntry
        {
            private final GuiButton button;
            private final MessageTypeCategory category;
            final GuiMessageTypeList this$1;
            
            public CategoryEntry(final GuiMessageTypeList this$1, final MessageTypeCategory category) {
                this.this$1 = this$1;
                this.category = category;
                this.button = new GuiButton(0, 0, 0, 80, 20, "");
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int yPosition, final int n3, final int n4, final int n5, final int n6, final boolean b) {
                Gui.drawCenteredString(GuiWDLMessages.access$0(GuiMessageTypeList.access$1(this.this$1)), this.category.getDisplayName(), GuiWDLMessages.width / 2 - 40, yPosition + n4 - Minecraft.fontRendererObj.FONT_HEIGHT - 1, 16777215);
                this.button.xPosition = GuiWDLMessages.width / 2 + 20;
                this.button.yPosition = yPosition;
                this.button.displayString = I18n.format("wdl.gui.messages.group." + WDLMessages.isGroupEnabled(this.category), new Object[0]);
                this.button.enabled = WDLMessages.enableAllMessages;
                this.button.drawButton(GuiMessageTypeList.access$0(this.this$1), n5, n6);
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.button.mousePressed(GuiMessageTypeList.access$0(this.this$1), n2, n3)) {
                    WDLMessages.toggleGroupEnabled(this.category);
                    this.button.playPressSound(Minecraft.getSoundHandler());
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            }
        }
        
        private class MessageTypeEntry implements IGuiListEntry
        {
            private final GuiButton button;
            private final IWDLMessageType type;
            private final MessageTypeCategory category;
            final GuiMessageTypeList this$1;
            
            public MessageTypeEntry(final GuiMessageTypeList this$1, final IWDLMessageType type, final MessageTypeCategory category) {
                this.this$1 = this$1;
                this.type = type;
                this.button = new GuiButton(0, 0, 0, type.toString());
                this.category = category;
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int yPosition, final int n3, final int n4, final int n5, final int n6, final boolean b) {
                this.button.xPosition = GuiWDLMessages.width / 2 - 100;
                this.button.yPosition = yPosition;
                this.button.displayString = I18n.format("wdl.gui.messages.message." + WDLMessages.isEnabled(this.type), this.type.getDisplayName());
                this.button.enabled = (WDLMessages.enableAllMessages && WDLMessages.isGroupEnabled(this.category));
                this.button.drawButton(GuiMessageTypeList.access$0(this.this$1), n5, n6);
                if (this.button.isMouseOver()) {
                    GuiWDLMessages.access$1(GuiMessageTypeList.access$1(this.this$1), this.type.getDescription());
                }
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.button.mousePressed(GuiMessageTypeList.access$0(this.this$1), n2, n3)) {
                    WDLMessages.toggleEnabled(this.type);
                    this.button.playPressSound(Minecraft.getSoundHandler());
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            }
        }
    }
}
