package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import java.text.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger;
    private final DateFormat field_146633_h;
    protected GuiScreen field_146632_a;
    protected String field_146628_f;
    private boolean field_146634_i;
    private int field_146640_r;
    private java.util.List field_146639_s;
    private List field_146638_t;
    private String field_146637_u;
    private String field_146636_v;
    private String[] field_146635_w;
    private boolean field_146643_x;
    private GuiButton field_146642_y;
    private GuiButton field_146641_z;
    private GuiButton field_146630_A;
    private GuiButton field_146631_B;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000711";
        logger = LogManager.getLogger();
    }
    
    public GuiSelectWorld(final GuiScreen field_146632_a) {
        this.field_146633_h = new SimpleDateFormat();
        this.field_146628_f = "Select world";
        this.field_146635_w = new String[4];
        this.field_146632_a = field_146632_a;
    }
    
    @Override
    public void initGui() {
        this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);
        this.func_146627_h();
        this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
        this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
        (this.field_146638_t = new List(GuiSelectWorld.mc)).registerScrollButtons(4, 5);
        this.func_146618_g();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146638_t.func_178039_p();
    }
    
    private void func_146627_h() throws AnvilConverterException {
        Collections.sort((java.util.List<Comparable>)(this.field_146639_s = GuiSelectWorld.mc.getSaveLoader().getSaveList()));
        this.field_146640_r = -1;
    }
    
    protected String func_146621_a(final int n) {
        return this.field_146639_s.get(n).getFileName();
    }
    
    protected String func_146614_d(final int n) {
        String s = this.field_146639_s.get(n).getDisplayName();
        if (StringUtils.isEmpty(s)) {
            s = String.valueOf(I18n.format("selectWorld.world", new Object[0])) + " " + (n + 1);
        }
        return s;
    }
    
    public void func_146618_g() {
        this.buttonList.add(this.field_146641_z = new GuiButton(1, GuiSelectWorld.width / 2 - 154, GuiSelectWorld.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
        this.buttonList.add(new GuiButton(3, GuiSelectWorld.width / 2 + 4, GuiSelectWorld.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(this.field_146630_A = new GuiButton(6, GuiSelectWorld.width / 2 - 154, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
        this.buttonList.add(this.field_146642_y = new GuiButton(2, GuiSelectWorld.width / 2 - 76, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
        this.buttonList.add(this.field_146631_B = new GuiButton(7, GuiSelectWorld.width / 2 + 4, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiSelectWorld.width / 2 + 82, GuiSelectWorld.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146641_z.enabled = false;
        this.field_146642_y.enabled = false;
        this.field_146630_A.enabled = false;
        this.field_146631_B.enabled = false;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                final String func_146614_d = this.func_146614_d(this.field_146640_r);
                if (func_146614_d != null) {
                    this.field_146643_x = true;
                    GuiSelectWorld.mc.displayGuiScreen(func_152129_a(this, func_146614_d, this.field_146640_r));
                }
            }
            else if (guiButton.id == 1) {
                this.func_146615_e(this.field_146640_r);
            }
            else if (guiButton.id == 3) {
                GuiSelectWorld.mc.displayGuiScreen(new GuiCreateWorld(this));
            }
            else if (guiButton.id == 6) {
                GuiSelectWorld.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
            }
            else if (guiButton.id == 0) {
                GuiSelectWorld.mc.displayGuiScreen(this.field_146632_a);
            }
            else if (guiButton.id == 7) {
                final GuiCreateWorld guiCreateWorld = new GuiCreateWorld(this);
                final ISaveHandler saveLoader = GuiSelectWorld.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
                final WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
                saveLoader.flush();
                guiCreateWorld.func_146318_a(loadWorldInfo);
                GuiSelectWorld.mc.displayGuiScreen(guiCreateWorld);
            }
            else {
                this.field_146638_t.actionPerformed(guiButton);
            }
        }
    }
    
    public void func_146615_e(final int n) {
        GuiSelectWorld.mc.displayGuiScreen(null);
        if (!this.field_146634_i) {
            this.field_146634_i = true;
            String s = this.func_146621_a(n);
            if (s == null) {
                s = "World" + n;
            }
            String s2 = this.func_146614_d(n);
            if (s2 == null) {
                s2 = "World" + n;
            }
            if (GuiSelectWorld.mc.getSaveLoader().canLoadWorld(s)) {
                GuiSelectWorld.mc.launchIntegratedServer(s, s2, null);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (this.field_146643_x) {
            this.field_146643_x = false;
            if (b) {
                final ISaveFormat saveLoader = GuiSelectWorld.mc.getSaveLoader();
                saveLoader.flushCache();
                saveLoader.deleteWorldDirectory(this.func_146621_a(n));
                this.func_146627_h();
            }
            GuiSelectWorld.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_146638_t.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146628_f, GuiSelectWorld.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public static GuiYesNo func_152129_a(final GuiYesNoCallback guiYesNoCallback, final String s, final int n) {
        return new GuiYesNo(guiYesNoCallback, I18n.format("selectWorld.deleteQuestion", new Object[0]), "'" + s + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), n);
    }
    
    static java.util.List access$0(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146639_s;
    }
    
    static void access$1(final GuiSelectWorld guiSelectWorld, final int field_146640_r) {
        guiSelectWorld.field_146640_r = field_146640_r;
    }
    
    static int access$2(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146640_r;
    }
    
    static GuiButton access$3(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146641_z;
    }
    
    static GuiButton access$4(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146642_y;
    }
    
    static GuiButton access$5(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146630_A;
    }
    
    static GuiButton access$6(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146631_B;
    }
    
    static String access$7(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146637_u;
    }
    
    static DateFormat access$8(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146633_h;
    }
    
    static String access$9(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146636_v;
    }
    
    static String[] access$10(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146635_w;
    }
    
    class List extends GuiSlot
    {
        private static final String __OBFID;
        final GuiSelectWorld this$0;
        
        public List(final GuiSelectWorld this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiSelectWorld.width, GuiSelectWorld.height, 32, GuiSelectWorld.height - 64, 36);
        }
        
        @Override
        protected int getSize() {
            return GuiSelectWorld.access$0(this.this$0).size();
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            GuiSelectWorld.access$1(this.this$0, n);
            final boolean b2 = GuiSelectWorld.access$2(this.this$0) >= 0 && GuiSelectWorld.access$2(this.this$0) < this.getSize();
            GuiSelectWorld.access$3(this.this$0).enabled = b2;
            GuiSelectWorld.access$4(this.this$0).enabled = b2;
            GuiSelectWorld.access$5(this.this$0).enabled = b2;
            GuiSelectWorld.access$6(this.this$0).enabled = b2;
            if (b && b2) {
                this.this$0.func_146615_e(n);
            }
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == GuiSelectWorld.access$2(this.this$0);
        }
        
        @Override
        protected int getContentHeight() {
            return GuiSelectWorld.access$0(this.this$0).size() * 36;
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final SaveFormatComparator saveFormatComparator = GuiSelectWorld.access$0(this.this$0).get(n);
            String s = saveFormatComparator.getDisplayName();
            if (StringUtils.isEmpty(s)) {
                s = String.valueOf(GuiSelectWorld.access$7(this.this$0)) + " " + (n + 1);
            }
            final String string = String.valueOf(new StringBuilder(String.valueOf(saveFormatComparator.getFileName())).append(" (").append(GuiSelectWorld.access$8(this.this$0).format(new Date(saveFormatComparator.getLastTimePlayed()))).toString()) + ")";
            final String s2 = "";
            String s3;
            if (saveFormatComparator.requiresConversion()) {
                s3 = String.valueOf(GuiSelectWorld.access$9(this.this$0)) + " " + s2;
            }
            else {
                s3 = GuiSelectWorld.access$10(this.this$0)[saveFormatComparator.getEnumGameType().getID()];
                if (saveFormatComparator.isHardcoreModeEnabled()) {
                    s3 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
                }
                if (saveFormatComparator.getCheatsEnabled()) {
                    s3 = String.valueOf(s3) + ", " + I18n.format("selectWorld.cheats", new Object[0]);
                }
            }
            this.this$0.drawString(this.this$0.fontRendererObj, s, n2 + 2, n3 + 1, 16777215);
            this.this$0.drawString(this.this$0.fontRendererObj, string, n2 + 2, n3 + 12, 8421504);
            this.this$0.drawString(this.this$0.fontRendererObj, s3, n2 + 2, n3 + 12 + 10, 8421504);
        }
        
        static {
            __OBFID = "CL_00000712";
        }
    }
}
