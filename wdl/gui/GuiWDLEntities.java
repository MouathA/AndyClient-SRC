package wdl.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import wdl.chan.*;
import net.minecraft.client.gui.*;
import wdl.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;

public class GuiWDLEntities extends GuiScreen
{
    private GuiEntityList entityList;
    private GuiScreen parent;
    private GuiButton rangeModeButton;
    private GuiButton presetsButton;
    private String mode;
    
    public GuiWDLEntities(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(200, GuiWDLEntities.width / 2 - 100, GuiWDLEntities.height - 29, "OK"));
        this.rangeModeButton = new GuiButton(100, GuiWDLEntities.width / 2 - 155, 18, 150, 20, this.getRangeModeText());
        this.presetsButton = new GuiButton(101, GuiWDLEntities.width / 2 + 5, 18, 150, 20, I18n.format("wdl.gui.entities.rangePresets", new Object[0]));
        this.mode = WDL.worldProps.getProperty("Entity.TrackDistanceMode");
        this.presetsButton.enabled = this.shouldEnablePresetsButton();
        this.buttonList.add(this.rangeModeButton);
        this.buttonList.add(this.presetsButton);
        this.entityList = new GuiEntityList();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.entityList.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 100) {
            this.cycleRangeMode();
        }
        if (guiButton.id == 101 && guiButton.enabled) {
            GuiWDLEntities.mc.displayGuiScreen(new GuiWDLEntityRangePresets(this));
        }
        if (guiButton.id == 200) {
            GuiWDLEntities.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (this.entityList.func_148179_a(n, n2, n3)) {
            return;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.entityList.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.entityList.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.entities.title", new Object[0]), GuiWDLEntities.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    private void cycleRangeMode() {
        if (this.mode.equals("default")) {
            if (WDLPluginChannels.hasServerEntityRange()) {
                this.mode = "server";
            }
            else {
                this.mode = "user";
            }
        }
        else if (this.mode.equals("server")) {
            this.mode = "user";
        }
        else {
            this.mode = "default";
        }
        WDL.worldProps.setProperty("Entity.TrackDistanceMode", this.mode);
        this.rangeModeButton.displayString = this.getRangeModeText();
        this.presetsButton.enabled = this.shouldEnablePresetsButton();
    }
    
    private String getRangeModeText() {
        return I18n.format("wdl.gui.entities.trackDistanceMode." + WDL.worldProps.getProperty("Entity.TrackDistanceMode"), new Object[0]);
    }
    
    private boolean shouldEnablePresetsButton() {
        return this.mode.equals("user");
    }
    
    static FontRenderer access$0(final GuiWDLEntities guiWDLEntities) {
        return guiWDLEntities.fontRendererObj;
    }
    
    static String access$1(final GuiWDLEntities guiWDLEntities) {
        return guiWDLEntities.mode;
    }
    
    private class GuiEntityList extends GuiListExtended
    {
        private int largestWidth;
        private int totalWidth;
        private List entries;
        final GuiWDLEntities this$0;
        
        public GuiEntityList(final GuiWDLEntities this$0) {
            this.this$0 = this$0;
            super(GuiWDLEntities.mc, GuiWDLEntities.width, GuiWDLEntities.height, 39, GuiWDLEntities.height - 32, 20);
            this.entries = new ArrayList() {
                final GuiEntityList this$1;
                
                {
                    final Multimap entitiesByGroup = EntityUtils.getEntitiesByGroup();
                    final ArrayList list = new ArrayList<String>(entitiesByGroup.keySet());
                    list.remove("Passive");
                    list.remove("Hostile");
                    list.remove("Other");
                    Collections.sort((List<Comparable>)list);
                    list.add(0, "Hostile");
                    list.add(1, "Passive");
                    list.add("Other");
                    for (final String s : list) {
                        final CategoryEntry categoryEntry = this$1.new CategoryEntry(s);
                        this.add(categoryEntry);
                        final ArrayList list2 = new ArrayList<String>(entitiesByGroup.get(s));
                        Collections.sort((List<Comparable>)list2);
                        for (final String s2 : list2) {
                            this.add(this$1.new EntityEntry(categoryEntry, s2));
                            if (GuiWDLEntities.access$0(GuiEntityList.access$5(this$1)).getStringWidth(s2) > 0) {
                                continue;
                            }
                        }
                    }
                    GuiEntityList.access$0(this$1, 0);
                    GuiEntityList.access$2(this$1, GuiEntityList.access$1(this$1) + 255);
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
        
        @Override
        protected int getScrollBarX() {
            return GuiWDLEntities.width / 2 + this.totalWidth / 2 + 10;
        }
        
        static void access$0(final GuiEntityList list, final int largestWidth) {
            list.largestWidth = largestWidth;
        }
        
        static int access$1(final GuiEntityList list) {
            return list.largestWidth;
        }
        
        static void access$2(final GuiEntityList list, final int totalWidth) {
            list.totalWidth = totalWidth;
        }
        
        static Minecraft access$3(final GuiEntityList list) {
            return list.mc;
        }
        
        static int access$4(final GuiEntityList list) {
            return list.totalWidth;
        }
        
        static GuiWDLEntities access$5(final GuiEntityList list) {
            return list.this$0;
        }
        
        private class CategoryEntry implements IGuiListEntry
        {
            private final String group;
            private final int labelWidth;
            private final GuiButton enableGroupButton;
            private boolean groupEnabled;
            final GuiEntityList this$1;
            
            public CategoryEntry(final GuiEntityList this$1, final String group) {
                this.this$1 = this$1;
                this.group = group;
                this.labelWidth = Minecraft.fontRendererObj.getStringWidth(group);
                this.groupEnabled = WDL.worldProps.getProperty("EntityGroup." + group + ".Enabled", "true").equals("true");
                this.enableGroupButton = new GuiButton(0, 0, 0, 90, 18, this.getButtonText());
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int yPosition, final int n3, final int n4, final int n5, final int n6, final boolean b) {
                Minecraft.fontRendererObj.drawString(this.group, n2 + 55 - this.labelWidth / 2, yPosition + n4 - Minecraft.fontRendererObj.FONT_HEIGHT - 1, 16777215);
                this.enableGroupButton.xPosition = n2 + 110;
                this.enableGroupButton.yPosition = yPosition;
                this.enableGroupButton.displayString = this.getButtonText();
                this.enableGroupButton.drawButton(GuiEntityList.access$3(this.this$1), n5, n6);
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.enableGroupButton.mousePressed(GuiEntityList.access$3(this.this$1), n2, n3)) {
                    this.groupEnabled ^= true;
                    this.enableGroupButton.playPressSound(Minecraft.getSoundHandler());
                    this.enableGroupButton.displayString = this.getButtonText();
                    WDL.worldProps.setProperty("EntityGroup." + this.group + ".Enabled", Boolean.toString(this.groupEnabled));
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            boolean isGroupEnabled() {
                return this.groupEnabled;
            }
            
            private String getButtonText() {
                if (this.groupEnabled) {
                    return I18n.format("wdl.gui.entities.group.enabled", new Object[0]);
                }
                return I18n.format("wdl.gui.entities.group.disabled", new Object[0]);
            }
        }
        
        private class EntityEntry implements IGuiListEntry
        {
            private final CategoryEntry category;
            private final String entity;
            private final GuiButton onOffButton;
            private final GuiSlider rangeSlider;
            private boolean entityEnabled;
            private int range;
            private String cachedMode;
            final GuiEntityList this$1;
            
            public EntityEntry(final GuiEntityList this$1, final CategoryEntry category, final String entity) {
                this.this$1 = this$1;
                this.category = category;
                this.entity = entity;
                this.entityEnabled = WDL.worldProps.getProperty("Entity." + entity + ".Enabled", "true").equals("true");
                this.range = EntityUtils.getEntityTrackDistance(entity);
                this.onOffButton = new GuiButton(0, 0, 0, 75, 18, this.getButtonText());
                this.onOffButton.enabled = category.isGroupEnabled();
                this.rangeSlider = new GuiSlider(1, 0, 0, 150, 18, "wdl.gui.entities.trackDistance", this.range, 256);
                this.cachedMode = GuiWDLEntities.access$1(GuiEntityList.access$5(this$1));
                this.rangeSlider.enabled = this.cachedMode.equals("user");
            }
            
            @Override
            public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
                final int xPosition = GuiWDLEntities.width / 2 - GuiEntityList.access$4(this.this$1) / 2 + GuiEntityList.access$1(this.this$1) + 10;
                Minecraft.fontRendererObj.drawString(this.entity, xPosition - GuiEntityList.access$1(this.this$1) - 10, n3 + n5 / 2 - Minecraft.fontRendererObj.FONT_HEIGHT / 2, 16777215);
                this.onOffButton.xPosition = xPosition;
                this.onOffButton.yPosition = n3;
                this.onOffButton.enabled = this.category.isGroupEnabled();
                this.onOffButton.displayString = this.getButtonText();
                this.rangeSlider.xPosition = xPosition + 85;
                this.rangeSlider.yPosition = n3;
                if (!this.cachedMode.equals(GuiWDLEntities.access$1(GuiEntityList.access$5(this.this$1)))) {
                    this.cachedMode = GuiWDLEntities.access$1(GuiEntityList.access$5(this.this$1));
                    this.rangeSlider.enabled = this.cachedMode.equals("user");
                    this.rangeSlider.setValue(EntityUtils.getEntityTrackDistance(this.entity));
                }
                this.onOffButton.drawButton(GuiEntityList.access$3(this.this$1), n6, n7);
                this.rangeSlider.drawButton(GuiEntityList.access$3(this.this$1), n6, n7);
            }
            
            @Override
            public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                if (this.onOffButton.mousePressed(GuiEntityList.access$3(this.this$1), n2, n3)) {
                    this.entityEnabled ^= true;
                    this.onOffButton.playPressSound(Minecraft.getSoundHandler());
                    this.onOffButton.displayString = this.getButtonText();
                    WDL.worldProps.setProperty("Entity." + this.entity + ".Enabled", Boolean.toString(this.entityEnabled));
                    return true;
                }
                if (this.rangeSlider.mousePressed(GuiEntityList.access$3(this.this$1), n2, n3)) {
                    this.range = this.rangeSlider.getValue();
                    WDL.worldProps.setProperty("Entity." + this.entity + ".TrackDistance", Integer.toString(this.range));
                    return true;
                }
                return false;
            }
            
            @Override
            public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
                this.rangeSlider.mouseReleased(n2, n3);
                if (this.cachedMode.equals("user")) {
                    this.range = this.rangeSlider.getValue();
                    WDL.worldProps.setProperty("Entity." + this.entity + ".TrackDistance", Integer.toString(this.range));
                }
            }
            
            @Override
            public void setSelected(final int n, final int n2, final int n3) {
            }
            
            private String getButtonText() {
                if (this.category.isGroupEnabled() && this.entityEnabled) {
                    return I18n.format("wdl.gui.entities.entity.included", new Object[0]);
                }
                return I18n.format("wdl.gui.entities.entity.ignored", new Object[0]);
            }
        }
    }
}
