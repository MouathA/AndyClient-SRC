package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen field_146608_a;
    private final GameSettings game_settings_2;
    private final java.util.List field_146604_g;
    private final java.util.List field_146609_h;
    private String field_146610_i;
    private String[] field_146607_r;
    private List field_146606_s;
    private GuiButton field_146605_t;
    private static final String __OBFID;
    
    public GuiSnooper(final GuiScreen field_146608_a, final GameSettings game_settings_2) {
        this.field_146604_g = Lists.newArrayList();
        this.field_146609_h = Lists.newArrayList();
        this.field_146608_a = field_146608_a;
        this.game_settings_2 = game_settings_2;
    }
    
    @Override
    public void initGui() {
        this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
        final String format = I18n.format("options.snooper.desc", new Object[0]);
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator iterator = this.fontRendererObj.listFormattedStringToWidth(format, GuiSnooper.width - 30).iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.field_146607_r = arrayList.toArray(new String[0]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.buttonList.add(this.field_146605_t = new GuiButton(1, GuiSnooper.width / 2 - 152, GuiSnooper.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton(2, GuiSnooper.width / 2 + 2, GuiSnooper.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
        final boolean b = GuiSnooper.mc.getIntegratedServer() != null && GuiSnooper.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (final Map.Entry<Object, String> entry : new TreeMap<Object, String>(GuiSnooper.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add(String.valueOf(b ? "C " : "") + entry.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), GuiSnooper.width - 220));
        }
        if (b) {
            for (final Map.Entry<String, Object> entry2 : new TreeMap<String, Object>(GuiSnooper.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_146604_g.add("S " + entry2.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry2.getValue(), GuiSnooper.width - 220));
            }
        }
        this.field_146606_s = new List();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146606_s.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                GuiSnooper.mc.displayGuiScreen(this.field_146608_a);
            }
            if (guiButton.id == 1) {
                this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146606_s.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146610_i, GuiSnooper.width / 2, 8, 16777215);
        final String[] field_146607_r = this.field_146607_r;
        while (0 < field_146607_r.length) {
            Gui.drawCenteredString(this.fontRendererObj, field_146607_r[0], GuiSnooper.width / 2, 22, 8421504);
            final int n4 = 22 + this.fontRendererObj.FONT_HEIGHT;
            int n5 = 0;
            ++n5;
        }
        super.drawScreen(n, n2, n3);
    }
    
    static java.util.List access$0(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146604_g;
    }
    
    static java.util.List access$1(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146609_h;
    }
    
    static {
        __OBFID = "CL_00000714";
    }
    
    class List extends GuiSlot
    {
        private static final String __OBFID;
        final GuiSnooper this$0;
        
        public List(final GuiSnooper this$0) {
            this.this$0 = this$0;
            super(GuiSnooper.mc, GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, this$0.fontRendererObj.FONT_HEIGHT + 1);
        }
        
        @Override
        protected int getSize() {
            return GuiSnooper.access$0(this.this$0).size();
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$0(this.this$0).get(n), 10, n3, 16777215);
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$1(this.this$0).get(n), 230, n3, 16777215);
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 10;
        }
        
        static {
            __OBFID = "CL_00000715";
        }
    }
}
