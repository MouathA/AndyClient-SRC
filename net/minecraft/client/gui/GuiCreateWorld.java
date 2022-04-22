package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.world.*;
import java.io.*;
import net.minecraft.world.storage.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class GuiCreateWorld extends GuiScreen
{
    private GuiScreen field_146332_f;
    private GuiTextField field_146333_g;
    private GuiTextField field_146335_h;
    private String field_146336_i;
    private String field_146342_r;
    private String field_175300_s;
    private boolean field_146341_s;
    private boolean field_146340_t;
    private boolean field_146339_u;
    private boolean field_146338_v;
    private boolean field_146337_w;
    private boolean field_146345_x;
    private boolean field_146344_y;
    private GuiButton field_146343_z;
    private GuiButton field_146324_A;
    private GuiButton field_146325_B;
    private GuiButton field_146326_C;
    private GuiButton field_146320_D;
    private GuiButton field_146321_E;
    private GuiButton field_146322_F;
    private String field_146323_G;
    private String field_146328_H;
    private String field_146329_I;
    private String field_146330_J;
    private int field_146331_K;
    public String field_146334_a;
    private static final String[] field_146327_L;
    private static final String __OBFID;
    private static final String[] lIIllIllIIllIIIl;
    private static String[] lIIllIllIIllIlIl;
    
    static {
        lllIIlIIIllIlIIl();
        lllIIlIIIllIlIII();
        __OBFID = GuiCreateWorld.lIIllIllIIllIIIl[0];
        field_146327_L = new String[] { GuiCreateWorld.lIIllIllIIllIIIl[1], GuiCreateWorld.lIIllIllIIllIIIl[2], GuiCreateWorld.lIIllIllIIllIIIl[3], GuiCreateWorld.lIIllIllIIllIIIl[4], GuiCreateWorld.lIIllIllIIllIIIl[5], GuiCreateWorld.lIIllIllIIllIIIl[6], GuiCreateWorld.lIIllIllIIllIIIl[7], GuiCreateWorld.lIIllIllIIllIIIl[8], GuiCreateWorld.lIIllIllIIllIIIl[9], GuiCreateWorld.lIIllIllIIllIIIl[10], GuiCreateWorld.lIIllIllIIllIIIl[11], GuiCreateWorld.lIIllIllIIllIIIl[12], GuiCreateWorld.lIIllIllIIllIIIl[13], GuiCreateWorld.lIIllIllIIllIIIl[14], GuiCreateWorld.lIIllIllIIllIIIl[15], GuiCreateWorld.lIIllIllIIllIIIl[16], GuiCreateWorld.lIIllIllIIllIIIl[17], GuiCreateWorld.lIIllIllIIllIIIl[18], GuiCreateWorld.lIIllIllIIllIIIl[19], GuiCreateWorld.lIIllIllIIllIIIl[20], GuiCreateWorld.lIIllIllIIllIIIl[21], GuiCreateWorld.lIIllIllIIllIIIl[22], GuiCreateWorld.lIIllIllIIllIIIl[23], GuiCreateWorld.lIIllIllIIllIIIl[24] };
    }
    
    public GuiCreateWorld(final GuiScreen field_146332_f) {
        this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[25];
        this.field_146341_s = true;
        this.field_146334_a = GuiCreateWorld.lIIllIllIIllIIIl[26];
        this.field_146332_f = field_146332_f;
        this.field_146329_I = GuiCreateWorld.lIIllIllIIllIIIl[27];
        this.field_146330_J = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[28], new Object[0]);
    }
    
    @Override
    public void updateScreen() {
        this.field_146333_g.updateCursorCounter();
        this.field_146335_h.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiCreateWorld.width / 2 - 155, GuiCreateWorld.height - 28, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[29], new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiCreateWorld.width / 2 + 5, GuiCreateWorld.height - 28, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[30], new Object[0])));
        this.buttonList.add(this.field_146343_z = new GuiButton(2, GuiCreateWorld.width / 2 - 75, 115, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[31], new Object[0])));
        this.buttonList.add(this.field_146324_A = new GuiButton(3, GuiCreateWorld.width / 2 - 75, 187, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[32], new Object[0])));
        this.buttonList.add(this.field_146325_B = new GuiButton(4, GuiCreateWorld.width / 2 - 155, 100, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[33], new Object[0])));
        this.field_146325_B.visible = false;
        this.buttonList.add(this.field_146326_C = new GuiButton(7, GuiCreateWorld.width / 2 + 5, 151, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[34], new Object[0])));
        this.field_146326_C.visible = false;
        this.buttonList.add(this.field_146320_D = new GuiButton(5, GuiCreateWorld.width / 2 + 5, 100, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[35], new Object[0])));
        this.field_146320_D.visible = false;
        this.buttonList.add(this.field_146321_E = new GuiButton(6, GuiCreateWorld.width / 2 - 155, 151, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[36], new Object[0])));
        this.field_146321_E.visible = false;
        this.buttonList.add(this.field_146322_F = new GuiButton(8, GuiCreateWorld.width / 2 + 5, 120, 150, 20, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[37], new Object[0])));
        this.field_146322_F.visible = false;
        (this.field_146333_g = new GuiTextField(9, this.fontRendererObj, GuiCreateWorld.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146333_g.setText(this.field_146330_J);
        (this.field_146335_h = new GuiTextField(10, this.fontRendererObj, GuiCreateWorld.width / 2 - 100, 60, 200, 20)).setText(this.field_146329_I);
        this.func_146316_a(this.field_146344_y);
        this.func_146314_g();
        this.func_146319_h();
    }
    
    private void func_146314_g() {
        this.field_146336_i = this.field_146333_g.getText().trim();
        final char[] allowedCharactersArray = ChatAllowedCharacters.allowedCharactersArray;
        for (int length = allowedCharactersArray.length, i = 0; i < length; ++i) {
            this.field_146336_i = this.field_146336_i.replace(allowedCharactersArray[i], '_');
        }
        if (StringUtils.isEmpty(this.field_146336_i)) {
            this.field_146336_i = GuiCreateWorld.lIIllIllIIllIIIl[38];
        }
        this.field_146336_i = func_146317_a(GuiCreateWorld.mc.getSaveLoader(), this.field_146336_i);
    }
    
    private void func_146319_h() {
        this.field_146343_z.displayString = String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[39], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[40] + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[41] + this.field_146342_r, new Object[0]);
        this.field_146323_G = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[42] + this.field_146342_r + GuiCreateWorld.lIIllIllIIllIIIl[43], new Object[0]);
        this.field_146328_H = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[44] + this.field_146342_r + GuiCreateWorld.lIIllIllIIllIIIl[45], new Object[0]);
        this.field_146325_B.displayString = String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[46], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[47];
        if (this.field_146341_s) {
            this.field_146325_B.displayString = String.valueOf(this.field_146325_B.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[48], new Object[0]);
        }
        else {
            this.field_146325_B.displayString = String.valueOf(this.field_146325_B.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[49], new Object[0]);
        }
        this.field_146326_C.displayString = String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[50], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[51];
        if (this.field_146338_v && !this.field_146337_w) {
            this.field_146326_C.displayString = String.valueOf(this.field_146326_C.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[52], new Object[0]);
        }
        else {
            this.field_146326_C.displayString = String.valueOf(this.field_146326_C.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[53], new Object[0]);
        }
        this.field_146320_D.displayString = String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[54], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[55] + I18n.format(WorldType.worldTypes[this.field_146331_K].getTranslateName(), new Object[0]);
        this.field_146321_E.displayString = String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[56], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[57];
        if (this.field_146340_t && !this.field_146337_w) {
            this.field_146321_E.displayString = String.valueOf(this.field_146321_E.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[58], new Object[0]);
        }
        else {
            this.field_146321_E.displayString = String.valueOf(this.field_146321_E.displayString) + I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[59], new Object[0]);
        }
    }
    
    public static String func_146317_a(final ISaveFormat saveFormat, String s) {
        s = s.replaceAll(GuiCreateWorld.lIIllIllIIllIIIl[60], GuiCreateWorld.lIIllIllIIllIIIl[61]);
        final String[] field_146327_L = GuiCreateWorld.field_146327_L;
        for (int length = field_146327_L.length, i = 0; i < length; ++i) {
            if (s.equalsIgnoreCase(field_146327_L[i])) {
                s = GuiCreateWorld.lIIllIllIIllIIIl[62] + s + GuiCreateWorld.lIIllIllIIllIIIl[63];
            }
        }
        while (saveFormat.getWorldInfo(s) != null) {
            s = String.valueOf(s) + GuiCreateWorld.lIIllIllIIllIIIl[64];
        }
        return s;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                GuiCreateWorld.mc.displayGuiScreen(this.field_146332_f);
            }
            else if (guiButton.id == 0) {
                GuiCreateWorld.mc.displayGuiScreen(null);
                if (this.field_146345_x) {
                    return;
                }
                this.field_146345_x = true;
                long nextLong = new Random().nextLong();
                final String text = this.field_146335_h.getText();
                if (!StringUtils.isEmpty(text)) {
                    try {
                        final long long1 = Long.parseLong(text);
                        if (long1 != 0L) {
                            nextLong = long1;
                        }
                    }
                    catch (NumberFormatException ex) {
                        nextLong = text.hashCode();
                    }
                }
                final WorldSettings worldSettings = new WorldSettings(nextLong, WorldSettings.GameType.getByName(this.field_146342_r), this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.field_146331_K]);
                worldSettings.setWorldName(this.field_146334_a);
                if (this.field_146338_v && !this.field_146337_w) {
                    worldSettings.enableBonusChest();
                }
                if (this.field_146340_t && !this.field_146337_w) {
                    worldSettings.enableCommands();
                }
                GuiCreateWorld.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), worldSettings);
            }
            else if (guiButton.id == 3) {
                this.func_146315_i();
            }
            else if (guiButton.id == 2) {
                if (this.field_146342_r.equals(GuiCreateWorld.lIIllIllIIllIIIl[65])) {
                    if (!this.field_146339_u) {
                        this.field_146340_t = false;
                    }
                    this.field_146337_w = false;
                    this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[66];
                    this.field_146337_w = true;
                    this.field_146321_E.enabled = false;
                    this.field_146326_C.enabled = false;
                    this.func_146319_h();
                }
                else if (this.field_146342_r.equals(GuiCreateWorld.lIIllIllIIllIIIl[67])) {
                    if (!this.field_146339_u) {
                        this.field_146340_t = true;
                    }
                    this.field_146337_w = false;
                    this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[68];
                    this.func_146319_h();
                    this.field_146337_w = false;
                    this.field_146321_E.enabled = true;
                    this.field_146326_C.enabled = true;
                }
                else {
                    if (!this.field_146339_u) {
                        this.field_146340_t = false;
                    }
                    this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[69];
                    this.func_146319_h();
                    this.field_146321_E.enabled = true;
                    this.field_146326_C.enabled = true;
                    this.field_146337_w = false;
                }
                this.func_146319_h();
            }
            else if (guiButton.id == 4) {
                this.field_146341_s = !this.field_146341_s;
                this.func_146319_h();
            }
            else if (guiButton.id == 7) {
                this.field_146338_v = !this.field_146338_v;
                this.func_146319_h();
            }
            else if (guiButton.id == 5) {
                ++this.field_146331_K;
                if (this.field_146331_K >= WorldType.worldTypes.length) {
                    this.field_146331_K = 0;
                }
                while (!this.func_175299_g()) {
                    ++this.field_146331_K;
                    if (this.field_146331_K >= WorldType.worldTypes.length) {
                        this.field_146331_K = 0;
                    }
                }
                this.field_146334_a = GuiCreateWorld.lIIllIllIIllIIIl[70];
                this.func_146319_h();
                this.func_146316_a(this.field_146344_y);
            }
            else if (guiButton.id == 6) {
                this.field_146339_u = true;
                this.field_146340_t = !this.field_146340_t;
                this.func_146319_h();
            }
            else if (guiButton.id == 8) {
                if (WorldType.worldTypes[this.field_146331_K] == WorldType.FLAT) {
                    GuiCreateWorld.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.field_146334_a));
                }
                else {
                    GuiCreateWorld.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.field_146334_a));
                }
            }
        }
    }
    
    private boolean func_175299_g() {
        final WorldType worldType = WorldType.worldTypes[this.field_146331_K];
        return worldType != null && worldType.getCanBeCreated() && (worldType != WorldType.DEBUG_WORLD || GuiScreen.isShiftKeyDown());
    }
    
    private void func_146315_i() {
        this.func_146316_a(!this.field_146344_y);
    }
    
    private void func_146316_a(final boolean field_146344_y) {
        this.field_146344_y = field_146344_y;
        if (WorldType.worldTypes[this.field_146331_K] == WorldType.DEBUG_WORLD) {
            this.field_146343_z.visible = !this.field_146344_y;
            this.field_146343_z.enabled = false;
            if (this.field_175300_s == null) {
                this.field_175300_s = this.field_146342_r;
            }
            this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[71];
            this.field_146325_B.visible = false;
            this.field_146326_C.visible = false;
            this.field_146320_D.visible = this.field_146344_y;
            this.field_146321_E.visible = false;
            this.field_146322_F.visible = false;
        }
        else {
            this.field_146343_z.visible = !this.field_146344_y;
            this.field_146343_z.enabled = true;
            if (this.field_175300_s != null) {
                this.field_146342_r = this.field_175300_s;
                this.field_175300_s = null;
            }
            this.field_146325_B.visible = (this.field_146344_y && WorldType.worldTypes[this.field_146331_K] != WorldType.CUSTOMIZED);
            this.field_146326_C.visible = this.field_146344_y;
            this.field_146320_D.visible = this.field_146344_y;
            this.field_146321_E.visible = this.field_146344_y;
            this.field_146322_F.visible = (this.field_146344_y && (WorldType.worldTypes[this.field_146331_K] == WorldType.FLAT || WorldType.worldTypes[this.field_146331_K] == WorldType.CUSTOMIZED));
        }
        this.func_146319_h();
        if (this.field_146344_y) {
            this.field_146324_A.displayString = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[72], new Object[0]);
        }
        else {
            this.field_146324_A.displayString = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[73], new Object[0]);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.field_146333_g.isFocused() && !this.field_146344_y) {
            this.field_146333_g.textboxKeyTyped(c, n);
            this.field_146330_J = this.field_146333_g.getText();
        }
        else if (this.field_146335_h.isFocused() && this.field_146344_y) {
            this.field_146335_h.textboxKeyTyped(c, n);
            this.field_146329_I = this.field_146335_h.getText();
        }
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.buttonList.get(0).enabled = (this.field_146333_g.getText().length() > 0);
        this.func_146314_g();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_146344_y) {
            this.field_146335_h.mouseClicked(n, n2, n3);
        }
        else {
            this.field_146333_g.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawMateFasza();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[74], new Object[0]), GuiCreateWorld.width / 2, 20, -1);
        if (this.field_146344_y) {
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[75], new Object[0]), GuiCreateWorld.width / 2 - 100, 47, -6250336);
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[76], new Object[0]), GuiCreateWorld.width / 2 - 100, 85, -6250336);
            if (this.field_146325_B.visible) {
                this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[77], new Object[0]), GuiCreateWorld.width / 2 - 150, 122, -6250336);
            }
            if (this.field_146321_E.visible) {
                this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[78], new Object[0]), GuiCreateWorld.width / 2 - 150, 172, -6250336);
            }
            this.field_146335_h.drawTextBox();
            if (WorldType.worldTypes[this.field_146331_K].showWorldInfoNotice()) {
                this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.field_146331_K].func_151359_c(), new Object[0]), this.field_146320_D.xPosition + 2, this.field_146320_D.yPosition + 22, this.field_146320_D.getButtonWidth(), 10526880);
            }
        }
        else {
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[79], new Object[0]), GuiCreateWorld.width / 2 - 100, 47, -6250336);
            this.drawString(this.fontRendererObj, String.valueOf(I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[80], new Object[0])) + GuiCreateWorld.lIIllIllIIllIIIl[81] + this.field_146336_i, GuiCreateWorld.width / 2 - 100, 85, -6250336);
            this.field_146333_g.drawTextBox();
            this.drawString(this.fontRendererObj, this.field_146323_G, GuiCreateWorld.width / 2 - 100, 137, -6250336);
            this.drawString(this.fontRendererObj, this.field_146328_H, GuiCreateWorld.width / 2 - 100, 149, -6250336);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146318_a(final WorldInfo worldInfo) {
        this.field_146330_J = I18n.format(GuiCreateWorld.lIIllIllIIllIIIl[82], worldInfo.getWorldName());
        this.field_146329_I = new StringBuilder(String.valueOf(worldInfo.getSeed())).toString();
        this.field_146331_K = worldInfo.getTerrainType().getWorldTypeID();
        this.field_146334_a = worldInfo.getGeneratorOptions();
        this.field_146341_s = worldInfo.isMapFeaturesEnabled();
        this.field_146340_t = worldInfo.areCommandsAllowed();
        if (worldInfo.isHardcoreModeEnabled()) {
            this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[83];
        }
        else if (worldInfo.getGameType().isSurvivalOrAdventure()) {
            this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[84];
        }
        else if (worldInfo.getGameType().isCreative()) {
            this.field_146342_r = GuiCreateWorld.lIIllIllIIllIIIl[85];
        }
    }
    
    private static void lllIIlIIIllIlIII() {
        (lIIllIllIIllIIIl = new String[86])[0] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[0], GuiCreateWorld.lIIllIllIIllIlIl[1]);
        GuiCreateWorld.lIIllIllIIllIIIl[1] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[2], GuiCreateWorld.lIIllIllIIllIlIl[3]);
        GuiCreateWorld.lIIllIllIIllIIIl[2] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[4], GuiCreateWorld.lIIllIllIIllIlIl[5]);
        GuiCreateWorld.lIIllIllIIllIIIl[3] = lllIIlIIIllIIIIl(GuiCreateWorld.lIIllIllIIllIlIl[6], GuiCreateWorld.lIIllIllIIllIlIl[7]);
        GuiCreateWorld.lIIllIllIIllIIIl[4] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[8], GuiCreateWorld.lIIllIllIIllIlIl[9]);
        GuiCreateWorld.lIIllIllIIllIIIl[5] = lllIIlIIIllIIIIl(GuiCreateWorld.lIIllIllIIllIlIl[10], GuiCreateWorld.lIIllIllIIllIlIl[11]);
        GuiCreateWorld.lIIllIllIIllIIIl[6] = lllIIlIIIllIIIIl(GuiCreateWorld.lIIllIllIIllIlIl[12], GuiCreateWorld.lIIllIllIIllIlIl[13]);
        GuiCreateWorld.lIIllIllIIllIIIl[7] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[14], GuiCreateWorld.lIIllIllIIllIlIl[15]);
        GuiCreateWorld.lIIllIllIIllIIIl[8] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[16], GuiCreateWorld.lIIllIllIIllIlIl[17]);
        GuiCreateWorld.lIIllIllIIllIIIl[9] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[18], GuiCreateWorld.lIIllIllIIllIlIl[19]);
        GuiCreateWorld.lIIllIllIIllIIIl[10] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[20], GuiCreateWorld.lIIllIllIIllIlIl[21]);
        GuiCreateWorld.lIIllIllIIllIIIl[11] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[22], GuiCreateWorld.lIIllIllIIllIlIl[23]);
        GuiCreateWorld.lIIllIllIIllIIIl[12] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[24], GuiCreateWorld.lIIllIllIIllIlIl[25]);
        GuiCreateWorld.lIIllIllIIllIIIl[13] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[26], GuiCreateWorld.lIIllIllIIllIlIl[27]);
        GuiCreateWorld.lIIllIllIIllIIIl[14] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[28], GuiCreateWorld.lIIllIllIIllIlIl[29]);
        GuiCreateWorld.lIIllIllIIllIIIl[15] = lllIIlIIIllIIIIl(GuiCreateWorld.lIIllIllIIllIlIl[30], GuiCreateWorld.lIIllIllIIllIlIl[31]);
        GuiCreateWorld.lIIllIllIIllIIIl[16] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[32], GuiCreateWorld.lIIllIllIIllIlIl[33]);
        GuiCreateWorld.lIIllIllIIllIIIl[17] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[34], GuiCreateWorld.lIIllIllIIllIlIl[35]);
        GuiCreateWorld.lIIllIllIIllIIIl[18] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[36], GuiCreateWorld.lIIllIllIIllIlIl[37]);
        GuiCreateWorld.lIIllIllIIllIIIl[19] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[38], GuiCreateWorld.lIIllIllIIllIlIl[39]);
        GuiCreateWorld.lIIllIllIIllIIIl[20] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[40], GuiCreateWorld.lIIllIllIIllIlIl[41]);
        GuiCreateWorld.lIIllIllIIllIIIl[21] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[42], GuiCreateWorld.lIIllIllIIllIlIl[43]);
        GuiCreateWorld.lIIllIllIIllIIIl[22] = lllIIlIIIllIIIlI(GuiCreateWorld.lIIllIllIIllIlIl[44], GuiCreateWorld.lIIllIllIIllIlIl[45]);
        GuiCreateWorld.lIIllIllIIllIIIl[23] = lllIIlIIIllIIIII(GuiCreateWorld.lIIllIllIIllIlIl[46], GuiCreateWorld.lIIllIllIIllIlIl[47]);
        GuiCreateWorld.lIIllIllIIllIIIl[24] = lllIIlIIIlIlllll(GuiCreateWorld.lIIllIllIIllIlIl[48], GuiCreateWorld.lIIllIllIIllIlIl[49]);
        GuiCreateWorld.lIIllIllIIllIIIl[25] = lllIIlIIIllIIIIl("2yNupFxomffi79VDZ7gCHA==", "iftOe");
        GuiCreateWorld.lIIllIllIIllIIIl[26] = lllIIlIIIllIIIlI("TbkzRgt6Le0=", "vXaQw");
        GuiCreateWorld.lIIllIllIIllIIIl[27] = lllIIlIIIllIIIIl("QtdmSxi65FArXeBXwmJ1qw==", "AzDyw");
        GuiCreateWorld.lIIllIllIIllIIIl[28] = lllIIlIIIllIIIIl("iSlLLy6RQeq43SYv9xPrmRpaG7TzvVrILyY9/ZaPnE0=", "XDSJw");
        GuiCreateWorld.lIIllIllIIllIIIl[29] = lllIIlIIIllIIIII("ArM+8nvARAoJKUzDZiKIc6Gs6QL2bABO", "aLVas");
        GuiCreateWorld.lIIllIllIIllIIIl[30] = lllIIlIIIllIIIIl("d9CP/EKXD+4ew4qumBINBQ==", "jXJFl");
        GuiCreateWorld.lIIllIllIIllIIIl[31] = lllIIlIIIllIIIlI("xoUr+4aY4sDUuv6B2rGgO+686R/kpVBL", "hZkSe");
        GuiCreateWorld.lIIllIllIIllIIIl[32] = lllIIlIIIllIIIlI("jt2szvQqw5fI+qcck3pINt2bpP3jNK4KPGIFt7u/950=", "mfwEN");
        GuiCreateWorld.lIIllIllIIllIIIl[33] = lllIIlIIIllIIIII("XuSR/2N28KcUPGGvQmCwxi5L3oeiOQNf", "Xkhbj");
        GuiCreateWorld.lIIllIllIIllIIIl[34] = lllIIlIIIllIIIlI("YN5ZJOHRHHl3zK+01IntzPTcgvwRgaUT", "aHASQ");
        GuiCreateWorld.lIIllIllIIllIIIl[35] = lllIIlIIIllIIIlI("an4Egp9454lfl/m5jcabx6PKhyKLLkTL", "bywFS");
        GuiCreateWorld.lIIllIllIIllIIIl[36] = lllIIlIIIlIlllll("FgcfFhARNRwBHwFMEh8fChUwHB4IAx0XAA==", "ebsss");
        GuiCreateWorld.lIIllIllIIllIIIl[37] = lllIIlIIIllIIIIl("j0+B38nLG0ahke8eriKCyOPnTglH6+z1Y5xZPyFO9/o=", "IbWJu");
        GuiCreateWorld.lIIllIllIIllIIIl[38] = lllIIlIIIllIIIlI("PYXyQzgXLIs=", "zrNWA");
        GuiCreateWorld.lIIllIllIIllIIIl[39] = lllIIlIIIllIIIII("8OPvBmFXQ+Npd19mQz1DJi6l3aE6qZPT", "qqeQz");
        GuiCreateWorld.lIIllIllIIllIIIl[40] = lllIIlIIIlIlllll("TkY=", "tfKEO");
        GuiCreateWorld.lIIllIllIIllIIIl[41] = lllIIlIIIllIIIII("+XLwdQuOD9PgRKY9BRSpkOjBQfs1gK+L", "zRlKq");
        GuiCreateWorld.lIIllIllIIllIIIl[42] = lllIIlIIIllIIIIl("A+/e+U3jJmxHPiIUnd296t1RLCtF4ERWN1zK6yE7zeY=", "UPIWJ");
        GuiCreateWorld.lIIllIllIIllIIIl[43] = lllIIlIIIllIIIII("5Me847wyTwQ=", "ebrRk");
        GuiCreateWorld.lIIllIllIIllIIIl[44] = lllIIlIIIllIIIIl("2WfESdE/lQdgIORSzRrF2h6eJSchMuxduuxzdxvdAdg=", "LhIzN");
        GuiCreateWorld.lIIllIllIIllIIIl[45] = lllIIlIIIlIlllll("TDszAyJQ", "bWZmG");
        GuiCreateWorld.lIIllIllIIllIIIl[46] = lllIIlIIIllIIIII("0pUGPZl0017OsgOhrgeAxqZlBi50mDZO", "xmMTU");
        GuiCreateWorld.lIIllIllIIllIIIl[47] = lllIIlIIIllIIIIl("SMZgPlxJyfxoX3ESwPlmDw==", "Xgqpa");
        GuiCreateWorld.lIIllIllIIllIIIl[48] = lllIIlIIIllIIIII("cxkelrCH9u0mf1bGiLS7Vw==", "gAqfp");
        GuiCreateWorld.lIIllIllIIllIIIl[49] = lllIIlIIIlIlllll("FQQ7MzgUB2E1MRw=", "ztOZW");
        GuiCreateWorld.lIIllIllIIllIIIl[50] = lllIIlIIIllIIIII("Xstt+UNRXyh05PUvydj5McpzMyE0EtTa", "mKOko");
        GuiCreateWorld.lIIllIllIIllIIIl[51] = lllIIlIIIlIlllll("aw==", "KLGEz");
        GuiCreateWorld.lIIllIllIIllIIIl[52] = lllIIlIIIllIIIIl("fqqTq2zAQCObNasCLX9+Sg==", "luizS");
        GuiCreateWorld.lIIllIllIIllIIIl[53] = lllIIlIIIllIIIII("JAC7qKVLg9cjz6RIS8K0Vw==", "ylpuu");
        GuiCreateWorld.lIIllIllIIllIIIl[54] = lllIIlIIIllIIIIl("CeyZBgYFW7+76ZVPUcU1Ivqd/LwGObfyRepgG6coJ1w=", "Jtuqx");
        GuiCreateWorld.lIIllIllIIllIIIl[55] = lllIIlIIIllIIIII("KGMLovDJDWw=", "nasbI");
        GuiCreateWorld.lIIllIllIIllIIIl[56] = lllIIlIIIllIIIII("J+QPbQrYZQ+UDJlLTQ8W/cSUErseQnuGh6fvKiKczWI=", "gihKP");
        GuiCreateWorld.lIIllIllIIllIIIl[57] = lllIIlIIIllIIIIl("uqpQVsWQT7iY7Sb1Pz3BRg==", "jaSbg");
        GuiCreateWorld.lIIllIllIIllIIIl[58] = lllIIlIIIllIIIIl("MSkvFpqq55CKzPLeA1uEJw==", "BDWfK");
        GuiCreateWorld.lIIllIllIIllIIIl[59] = lllIIlIIIlIlllll("OjQxEAI7N2sWCzM=", "UDEym");
        GuiCreateWorld.lIIllIllIIllIIIl[60] = lllIIlIIIllIIIII("TCh8rm0k7L4=", "DFmSt");
        GuiCreateWorld.lIIllIllIIllIIIl[61] = lllIIlIIIllIIIlI("enBmifeQ73c=", "YNskG");
        GuiCreateWorld.lIIllIllIIllIIIl[62] = lllIIlIIIllIIIIl("HWGgK1RPRbw+64aLpOMwgg==", "HGgrl");
        GuiCreateWorld.lIIllIllIIllIIIl[63] = lllIIlIIIllIIIII("RGgSnP9uo9E=", "KdmOF");
        GuiCreateWorld.lIIllIllIIllIIIl[64] = lllIIlIIIlIlllll("Rg==", "kINFB");
        GuiCreateWorld.lIIllIllIIllIIIl[65] = lllIIlIIIllIIIIl("pEn0U06mhtnMLDpDsmSaIw==", "FuXHm");
        GuiCreateWorld.lIIllIllIIllIIIl[66] = lllIIlIIIllIIIII("5WXtyyiZ8IvZ9Mtk8CImrw==", "THvMf");
        GuiCreateWorld.lIIllIllIIllIIIl[67] = lllIIlIIIllIIIIl("77chEtw7MeM5EwhCMU+GRQ==", "Rbiaw");
        GuiCreateWorld.lIIllIllIIllIIIl[68] = lllIIlIIIlIlllll("CwUoEAcBASg=", "hwMqs");
        GuiCreateWorld.lIIllIllIIllIIIl[69] = lllIIlIIIllIIIII("Yqiz00sDyDieQW6VoJnJfA==", "ugAZg");
        GuiCreateWorld.lIIllIllIIllIIIl[70] = lllIIlIIIllIIIlI("icN8I06eMBU=", "FEwSo");
        GuiCreateWorld.lIIllIllIIllIIIl[71] = lllIIlIIIllIIIII("7pVbNhzcezIOA35HW8lplw==", "lqPFe");
        GuiCreateWorld.lIIllIllIIllIIIl[72] = lllIIlIIIllIIIlI("CeQM/OtZvoba9UBRoRDSmw==", "OZJgN");
        GuiCreateWorld.lIIllIllIIllIIIl[73] = lllIIlIIIllIIIIl("cYkqmI1xhwXJrw+C1UUE//LBMnS+cnxmRAjKYhp19lo=", "QaPEy");
        GuiCreateWorld.lIIllIllIIllIIIl[74] = lllIIlIIIlIlllll("BwQcCSYANh8eKRBPEx4gFRUV", "taplE");
        GuiCreateWorld.lIIllIllIIllIIIl[75] = lllIIlIIIllIIIII("0L2I5g5t/Cu8qkc9pSA2UY96Ak6vc0yP", "tCbfV");
        GuiCreateWorld.lIIllIllIIllIIIl[76] = lllIIlIIIllIIIII("HAO8rZ/6JAc+UN47gDVwV9zmKAzb5QXD", "WSYJe");
        GuiCreateWorld.lIIllIllIIllIIIl[77] = lllIIlIIIlIlllll("HDIBNjQbAAIhOwt5ADInKTIMJyIdMh59PgExAg==", "oWmSW");
        GuiCreateWorld.lIIllIllIIllIIIl[78] = lllIIlIIIllIIIII("y1CqU5fFke/obmYfSkpLmWbzsrKnS3ewDS3N1JtWxrg=", "nFhns");
        GuiCreateWorld.lIIllIllIIllIIIl[79] = lllIIlIIIllIIIlI("XAjrKdjNs7As7I4Jk4Bv1feRKS9IRsEU", "yjHLl");
        GuiCreateWorld.lIIllIllIIllIIIl[80] = lllIIlIIIllIIIII("RyE/3/ySSwaz2TUSxP4GJcg+GoTajqvGf8fOM9jKu1c=", "LiHwq");
        GuiCreateWorld.lIIllIllIIllIIIl[81] = lllIIlIIIlIlllll("Qw==", "cdLnv");
        GuiCreateWorld.lIIllIllIIllIIIl[82] = lllIIlIIIllIIIIl("nAWvx+pn3xzLFC+N/Bf4OOJvGbZxk4s+f7/77K0MLjE=", "iwEfy");
        GuiCreateWorld.lIIllIllIIllIIIl[83] = lllIIlIIIlIlllll("EgwDDg4VHxQ=", "zmqjm");
        GuiCreateWorld.lIIllIllIIllIIIl[84] = lllIIlIIIllIIIIl("P3CzvJf0CpXQWTzALRgKNw==", "lXGgq");
        GuiCreateWorld.lIIllIllIIllIIIl[85] = lllIIlIIIllIIIlI("KneoM6/1sDHR3XrEpS+eTg==", "GoTaa");
        GuiCreateWorld.lIIllIllIIllIlIl = null;
    }
    
    private static void lllIIlIIIllIlIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        GuiCreateWorld.lIIllIllIIllIlIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIIlIIIlIlllll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lllIIlIIIllIIIIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIIlIIIllIIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lllIIlIIIllIIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
