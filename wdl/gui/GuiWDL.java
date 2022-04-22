package wdl.gui;

import wdl.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import java.util.*;
import wdl.update.*;
import net.minecraft.client.*;
import wdl.chan.*;

public class GuiWDL extends GuiScreen
{
    private String displayedTooltip;
    private String title;
    private GuiScreen parent;
    private GuiTextField worldname;
    private GuiWDLButtonList list;
    
    public GuiWDL(final GuiScreen parent) {
        this.displayedTooltip = null;
        this.title = "";
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        if (WDL.isMultiworld && WDL.worldName.isEmpty()) {
            GuiWDL.mc.displayGuiScreen(new GuiWDLMultiworldSelect(I18n.format("wdl.gui.multiworldSelect.title.changeOptions", new Object[0]), new GuiWDLMultiworldSelect.WorldSelectionCallback() {
                final GuiWDL this$0;
                
                @Override
                public void onWorldSelected(final String worldName) {
                    WDL.worldName = worldName;
                    WDL.isMultiworld = true;
                    WDL.propsFound = true;
                    WDL.worldProps = WDL.loadWorldProps(worldName);
                    GuiWDL.mc.displayGuiScreen(this.this$0);
                }
                
                @Override
                public void onCancel() {
                    GuiWDL.mc.displayGuiScreen(null);
                }
            }));
            return;
        }
        if (!WDL.propsFound) {
            GuiWDL.mc.displayGuiScreen(new GuiWDLMultiworld(new GuiWDLMultiworld.MultiworldCallback() {
                final GuiWDL this$0;
                
                @Override
                public void onSelect(final boolean isMultiworld) {
                    WDL.isMultiworld = isMultiworld;
                    if (WDL.isMultiworld) {
                        GuiWDL.mc.displayGuiScreen(new GuiWDLMultiworldSelect(I18n.format("wdl.gui.multiworldSelect.title.changeOptions", new Object[0]), new GuiWDLMultiworldSelect.WorldSelectionCallback() {
                            final GuiWDL$2 this$1;
                            
                            @Override
                            public void onWorldSelected(final String worldName) {
                                WDL.worldName = worldName;
                                WDL.isMultiworld = true;
                                WDL.propsFound = true;
                                WDL.worldProps = WDL.loadWorldProps(worldName);
                                GuiWDL.mc.displayGuiScreen(GuiWDL$2.access$0(this.this$1));
                            }
                            
                            @Override
                            public void onCancel() {
                                GuiWDL.mc.displayGuiScreen(null);
                            }
                        }));
                    }
                    else {
                        WDL.baseProps.setProperty("LinkedWorlds", "");
                        WDL.propsFound = true;
                        GuiWDL.mc.displayGuiScreen(this.this$0);
                    }
                }
                
                @Override
                public void onCancel() {
                    GuiWDL.mc.displayGuiScreen(null);
                }
                
                static GuiWDL access$0(final GuiWDL$2 multiworldCallback) {
                    return multiworldCallback.this$0;
                }
            }));
            return;
        }
        this.buttonList.clear();
        this.title = I18n.format("wdl.gui.wdl.title", WDL.baseFolderName.replace('@', ':'));
        if (WDL.baseProps.getProperty("ServerName").isEmpty()) {
            WDL.baseProps.setProperty("ServerName", WDL.getServerName());
        }
        (this.worldname = new GuiTextField(42, this.fontRendererObj, GuiWDL.width / 2 - 155, 19, 150, 18)).setText(WDL.baseProps.getProperty("ServerName"));
        this.buttonList.add(new GuiButton(100, GuiWDL.width / 2 - 100, GuiWDL.height - 29, I18n.format("gui.done", new Object[0])));
        this.list = new GuiWDLButtonList();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (!guiButton.enabled) {
            return;
        }
        if (guiButton.id == 100) {
            GuiWDL.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (this.worldname != null) {
            WDL.baseProps.setProperty("ServerName", this.worldname.getText());
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.list.func_148179_a(n, n2, n3);
        super.mouseClicked(n, n2, n3);
        this.worldname.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.worldname.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        this.worldname.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.displayedTooltip = null;
        this.list.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDL.width / 2, 8, 16777215);
        final String format = I18n.format("wdl.gui.wdl.worldname", new Object[0]);
        this.drawString(this.fontRendererObj, format, this.worldname.xPosition - this.fontRendererObj.getStringWidth(String.valueOf(format) + " "), 26, 16777215);
        this.worldname.drawTextBox();
        super.drawScreen(n, n2, n3);
        Utils.drawGuiInfoBox(this.displayedTooltip, GuiWDL.width, GuiWDL.height, 48);
    }
    
    static void access$0(final GuiWDL guiWDL, final String displayedTooltip) {
        guiWDL.displayedTooltip = displayedTooltip;
    }
    
    private class GuiWDLButtonList extends GuiListExtended
    {
        private List entries;
        final GuiWDL this$0;
        
        public GuiWDLButtonList(final GuiWDL this$0) {
            this.this$0 = this$0;
            super(GuiWDL.mc, GuiWDL.width, GuiWDL.height, 39, GuiWDL.height - 32, 20);
            this.entries = new ArrayList() {
                final GuiWDLButtonList this$1;
                
                {
                    this.add(this$1.new ButtonEntry("worldOverrides", new GuiWDLWorld(GuiWDLButtonList.access$1(this$1)), true));
                    this.add(this$1.new ButtonEntry("generatorOverrides", new GuiWDLGenerator(GuiWDLButtonList.access$1(this$1)), true));
                    this.add(this$1.new ButtonEntry("playerOverrides", new GuiWDLPlayer(GuiWDLButtonList.access$1(this$1)), true));
                    this.add(this$1.new ButtonEntry("entityOptions", new GuiWDLEntities(GuiWDLButtonList.access$1(this$1)), true));
                    this.add(this$1.new ButtonEntry("backupOptions", new GuiWDLBackup(GuiWDLButtonList.access$1(this$1)), true));
                    this.add(this$1.new ButtonEntry("messageOptions", new GuiWDLMessages(GuiWDLButtonList.access$1(this$1)), false));
                    this.add(this$1.new ButtonEntry("permissionsInfo", new GuiWDLPermissions(GuiWDLButtonList.access$1(this$1)), false));
                    this.add(this$1.new ButtonEntry("about", new GuiWDLAbout(GuiWDLButtonList.access$1(this$1)), false));
                    if (WDLUpdateChecker.hasNewVersion()) {
                        this.add(0, this$1.new ButtonEntry("updates.hasNew", new GuiWDLUpdates(GuiWDLButtonList.access$1(this$1)), false));
                    }
                    else {
                        this.add(this$1.new ButtonEntry("updates", new GuiWDLUpdates(GuiWDLButtonList.access$1(this$1)), false));
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
        
        static Minecraft access$0(final GuiWDLButtonList list) {
            return list.mc;
        }
        
        static GuiWDL access$1(final GuiWDLButtonList list) {
            return list.this$0;
        }
        
        private class ButtonEntry implements IGuiListEntry
        {
            private final GuiButton button;
            private final GuiScreen toOpen;
            private final String tooltip;
            final GuiWDLButtonList this$1;
            
            public ButtonEntry(final GuiWDLButtonList this$1, final String s, final GuiScreen toOpen, final boolean b) {
                this.this$1 = this$1;
                this.button = new GuiButton(0, 0, 0, I18n.format("wdl.gui.wdl." + s + ".name", new Object[0]));
                this.toOpen = toOpen;
                if (b) {
                    this.button.enabled = WDLPluginChannels.canDownloadAtAll();
                }
                this.tooltip = I18n.format("wdl.gui.wdl." + s + ".description", new Object[0]);
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int yPosition, final int n3, final int n4, final int n5, final int n6, final boolean b) {
                this.button.xPosition = GuiWDL.width / 2 - 100;
                this.button.yPosition = yPosition;
                this.button.drawButton(GuiWDLButtonList.access$0(this.this$1), n5, n6);
                if (this.button.isMouseOver()) {
                    GuiWDL.access$0(GuiWDLButtonList.access$1(this.this$1), this.tooltip);
                }
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.button.mousePressed(GuiWDLButtonList.access$0(this.this$1), n2, n3)) {
                    GuiWDLButtonList.access$0(this.this$1).displayGuiScreen(this.toOpen);
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
