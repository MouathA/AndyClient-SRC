package wdl.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import wdl.api.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class GuiWDLExtensions extends GuiScreen
{
    private int bottomLocation;
    private int selectedModIndex;
    private final GuiScreen parent;
    private ModList list;
    private ModDetailList detailsList;
    private boolean dragging;
    private int dragOffset;
    
    private void updateDetailsList(final WDLApi.ModInfo modInfo) {
        this.detailsList.clearLines();
        if (modInfo != null) {
            this.detailsList.addLine(modInfo.getInfo());
        }
    }
    
    public GuiWDLExtensions(final GuiScreen parent) {
        this.selectedModIndex = -1;
        this.dragging = false;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.bottomLocation = GuiWDLExtensions.height - 100;
        this.dragging = false;
        this.list = new ModList();
        this.detailsList = new ModDetailList();
        this.buttonList.add(new GuiButton(0, GuiWDLExtensions.width / 2 - 100, GuiWDLExtensions.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            GuiWDLExtensions.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
        this.detailsList.func_178039_p();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n2 > this.bottomLocation && n2 < this.bottomLocation + 19) {
            this.dragging = true;
            this.dragOffset = n2 - this.bottomLocation;
            return;
        }
        if (this.list.func_148179_a(n, n2, n3)) {
            return;
        }
        if (this.detailsList.func_148179_a(n, n2, n3)) {
            return;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        this.dragging = false;
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        if (this.detailsList.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        if (this.dragging) {
            this.bottomLocation = n2 - this.dragOffset;
        }
        if (this.bottomLocation < 31) {
            this.bottomLocation = 31;
        }
        if (this.bottomLocation > GuiWDLExtensions.height - 32 - 8) {
            this.bottomLocation = GuiWDLExtensions.height - 32 - 8;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (this.bottomLocation < 56) {
            this.bottomLocation = 56;
        }
        if (this.bottomLocation > GuiWDLExtensions.height - 19 - 32 - 33) {
            this.bottomLocation = GuiWDLExtensions.height - 19 - 32 - 33;
        }
        this.list.drawScreen(n, n2, n3);
        this.detailsList.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.extensions.title", new Object[0]), GuiWDLExtensions.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static FontRenderer access$0(final GuiWDLExtensions guiWDLExtensions) {
        return guiWDLExtensions.fontRendererObj;
    }
    
    static int access$1(final GuiWDLExtensions guiWDLExtensions) {
        return guiWDLExtensions.selectedModIndex;
    }
    
    static void access$2(final GuiWDLExtensions guiWDLExtensions, final int selectedModIndex) {
        guiWDLExtensions.selectedModIndex = selectedModIndex;
    }
    
    static void access$3(final GuiWDLExtensions guiWDLExtensions, final WDLApi.ModInfo modInfo) {
        guiWDLExtensions.updateDetailsList(modInfo);
    }
    
    static int access$4(final GuiWDLExtensions guiWDLExtensions) {
        return guiWDLExtensions.bottomLocation;
    }
    
    private class ModDetailList extends TextList
    {
        final GuiWDLExtensions this$0;
        
        public ModDetailList(final GuiWDLExtensions this$0) {
            this.this$0 = this$0;
            super(GuiWDLExtensions.mc, GuiWDLExtensions.width, GuiWDLExtensions.height - GuiWDLExtensions.access$4(this$0), 19, 32);
        }
        
        @Override
        public void drawScreen(final int n, final int n2, final float n3) {
            GlStateManager.translate(0.0f, (float)GuiWDLExtensions.access$4(this.this$0), 0.0f);
            this.height = GuiWDLExtensions.height - GuiWDLExtensions.access$4(this.this$0);
            this.bottom = this.height - 32;
            super.drawScreen(n, n2, n3);
            Gui.drawCenteredString(GuiWDLExtensions.access$0(this.this$0), I18n.format("wdl.gui.extensions.detailsCaption", new Object[0]), GuiWDLExtensions.width / 2, 5, 16777215);
            GlStateManager.translate(0.0f, (float)(-GuiWDLExtensions.access$4(this.this$0)), 0.0f);
        }
        
        @Override
        protected void overlayBackground(final int n, final int n2, final int n3, final int n4) {
            if (n == 0) {
                super.overlayBackground(n, n2, n3, n4);
                return;
            }
            GlStateManager.translate(0.0f, (float)(-GuiWDLExtensions.access$4(this.this$0)), 0.0f);
            super.overlayBackground(n + GuiWDLExtensions.access$4(this.this$0), n2 + GuiWDLExtensions.access$4(this.this$0), n3, n4);
            GlStateManager.translate(0.0f, (float)GuiWDLExtensions.access$4(this.this$0), 0.0f);
        }
        
        @Override
        public void func_178039_p() {
            this.mouseY -= GuiWDLExtensions.access$4(this.this$0);
            if (this.mouseY > 0) {
                super.func_178039_p();
            }
            this.mouseY += GuiWDLExtensions.access$4(this.this$0);
        }
    }
    
    private class ModList extends GuiListExtended
    {
        private List entries;
        final GuiWDLExtensions this$0;
        
        public ModList(final GuiWDLExtensions this$0) {
            this.this$0 = this$0;
            super(GuiWDLExtensions.mc, GuiWDLExtensions.width, GuiWDLExtensions.access$4(this$0), 23, GuiWDLExtensions.access$4(this$0), 22);
            this.entries = new ArrayList() {
                final ModList this$1;
                
                {
                    final Iterator<WDLApi.ModInfo> iterator = WDLApi.getWDLMods().values().iterator();
                    while (iterator.hasNext()) {
                        this.add(this$1.new ModEntry(iterator.next()));
                    }
                }
            };
            this.showSelectionBox = true;
        }
        
        @Override
        public void drawScreen(final int n, final int n2, final float n3) {
            final int access$4 = GuiWDLExtensions.access$4(this.this$0);
            this.bottom = access$4;
            this.height = access$4;
            super.drawScreen(n, n2, n3);
        }
        
        @Override
        public IGuiListEntry getListEntry(final int n) {
            return this.entries.get(n);
        }
        
        @Override
        protected int getSize() {
            return this.entries.size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == GuiWDLExtensions.access$1(this.this$0);
        }
        
        @Override
        public int getListWidth() {
            return GuiWDLExtensions.width - 20;
        }
        
        @Override
        protected int getScrollBarX() {
            return GuiWDLExtensions.width - 10;
        }
        
        @Override
        public void func_178039_p() {
            if (this.mouseY < GuiWDLExtensions.access$4(this.this$0)) {
                super.func_178039_p();
            }
        }
        
        static Minecraft access$0(final ModList list) {
            return list.mc;
        }
        
        static GuiWDLExtensions access$1(final ModList list) {
            return list.this$0;
        }
        
        private class ModEntry implements IGuiListEntry
        {
            public final WDLApi.ModInfo mod;
            private final String modDescription;
            private String label;
            private GuiButton button;
            private GuiButton disableButton;
            final ModList this$1;
            
            public ModEntry(final ModList this$1, final WDLApi.ModInfo mod) {
                this.this$1 = this$1;
                this.mod = mod;
                this.modDescription = I18n.format("wdl.gui.extensions.modVersion", mod.getDisplayName(), mod.version);
                if (!mod.isEnabled()) {
                    this.label = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.ITALIC).append(this.modDescription).toString();
                }
                else {
                    this.label = this.modDescription;
                }
                if (mod.mod instanceof IWDLModWithGui) {
                    final IWDLModWithGui iwdlModWithGui = (IWDLModWithGui)mod.mod;
                    final String buttonName = iwdlModWithGui.getButtonName();
                    if (buttonName == null || buttonName.isEmpty()) {
                        I18n.format("wdl.gui.extensions.defaultSettingsButtonText", new Object[0]);
                    }
                    this.button = new GuiButton(0, 0, 0, 80, 20, iwdlModWithGui.getButtonName());
                }
                this.disableButton = new GuiButton(0, 0, 0, 80, 20, I18n.format("wdl.gui.extensions." + (mod.isEnabled() ? "enabled" : "disabled"), new Object[0]));
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
                if (this.button != null) {
                    this.button.xPosition = GuiWDLExtensions.width - 180;
                    this.button.yPosition = n3 - 1;
                    this.button.drawButton(ModList.access$0(this.this$1), n6, n7);
                }
                this.disableButton.xPosition = GuiWDLExtensions.width - 92;
                this.disableButton.yPosition = n3 - 1;
                this.disableButton.drawButton(ModList.access$0(this.this$1), n6, n7);
                GuiWDLExtensions.access$0(ModList.access$1(this.this$1)).drawString(this.label, n2, n3 + n5 / 2 - GuiWDLExtensions.access$0(ModList.access$1(this.this$1)).FONT_HEIGHT / 2, 16777215);
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.button != null && this.button.mousePressed(ModList.access$0(this.this$1), n2, n3)) {
                    if (this.mod.mod instanceof IWDLModWithGui) {
                        ((IWDLModWithGui)this.mod.mod).openGui(ModList.access$1(this.this$1));
                    }
                    this.button.playPressSound(Minecraft.getSoundHandler());
                    return true;
                }
                if (this.disableButton.mousePressed(ModList.access$0(this.this$1), n2, n3)) {
                    this.mod.toggleEnabled();
                    this.disableButton.playPressSound(Minecraft.getSoundHandler());
                    this.disableButton.displayString = I18n.format("wdl.gui.extensions." + (this.mod.isEnabled() ? "enabled" : "disabled"), new Object[0]);
                    if (!this.mod.isEnabled()) {
                        this.label = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.ITALIC).append(this.modDescription).toString();
                    }
                    else {
                        this.label = this.modDescription;
                    }
                    return true;
                }
                if (GuiWDLExtensions.access$1(ModList.access$1(this.this$1)) != n) {
                    GuiWDLExtensions.access$2(ModList.access$1(this.this$1), n);
                    Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
                    GuiWDLExtensions.access$3(ModList.access$1(this.this$1), this.mod);
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.button != null) {
                    this.button.mouseReleased(n2, n3);
                }
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
        }
    }
}
