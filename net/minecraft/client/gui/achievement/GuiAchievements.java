package net.minecraft.client.gui.achievement;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class GuiAchievements extends GuiScreen implements IProgressMeter
{
    private static final int field_146572_y;
    private static final int field_146571_z;
    private static final int field_146559_A;
    private static final int field_146560_B;
    private static final ResourceLocation field_146561_C;
    protected GuiScreen parentScreen;
    protected int field_146555_f;
    protected int field_146557_g;
    protected int field_146563_h;
    protected int field_146564_i;
    protected float field_146570_r;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private StatFileWriter statFileWriter;
    private boolean loadingAchievements;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000722";
        field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
        field_146571_z = AchievementList.minDisplayRow * 24 - 112;
        field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
        field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
        field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }
    
    public GuiAchievements(final GuiScreen parentScreen, final StatFileWriter statFileWriter) {
        this.field_146555_f = 256;
        this.field_146557_g = 202;
        this.field_146570_r = 1.0f;
        this.loadingAchievements = true;
        this.parentScreen = parentScreen;
        this.statFileWriter = statFileWriter;
        final double field_146569_s = AchievementList.openInventory.displayColumn * 24 - 1 - 12;
        this.field_146565_w = field_146569_s;
        this.field_146567_u = field_146569_s;
        this.field_146569_s = field_146569_s;
        final double field_146568_t = AchievementList.openInventory.displayRow * 24 - 1;
        this.field_146573_x = field_146568_t;
        this.field_146566_v = field_146568_t;
        this.field_146568_t = field_146568_t;
    }
    
    @Override
    public void initGui() {
        GuiAchievements.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, GuiAchievements.width / 2 + 24, GuiAchievements.height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (!this.loadingAchievements && guiButton.id == 1) {
            GuiAchievements.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == GuiAchievements.mc.gameSettings.keyBindInventory.getKeyCode()) {
            GuiAchievements.mc.displayGuiScreen(null);
            GuiAchievements.mc.setIngameFocus();
        }
        else {
            super.keyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int field_146563_h, final int field_146564_i, final float n) {
        if (this.loadingAchievements) {
            this.drawDefaultBackground();
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), GuiAchievements.width / 2, GuiAchievements.height / 2, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, GuiAchievements.lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % GuiAchievements.lanSearchStates.length)], GuiAchievements.width / 2, GuiAchievements.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        }
        else {
            if (Mouse.isButtonDown(0)) {
                final int n2 = (GuiAchievements.width - this.field_146555_f) / 2;
                final int n3 = (GuiAchievements.height - this.field_146557_g) / 2;
                final int n4 = n2 + 8;
                final int n5 = n3 + 17;
                if ((this.field_146554_D == 0 || this.field_146554_D == 1) && field_146563_h >= n4 && field_146563_h < n4 + 224 && field_146564_i >= n5 && field_146564_i < n5 + 155) {
                    if (this.field_146554_D == 0) {
                        this.field_146554_D = 1;
                    }
                    else {
                        this.field_146567_u -= (field_146563_h - this.field_146563_h) * this.field_146570_r;
                        this.field_146566_v -= (field_146564_i - this.field_146564_i) * this.field_146570_r;
                        final double field_146567_u = this.field_146567_u;
                        this.field_146569_s = field_146567_u;
                        this.field_146565_w = field_146567_u;
                        final double field_146566_v = this.field_146566_v;
                        this.field_146568_t = field_146566_v;
                        this.field_146573_x = field_146566_v;
                    }
                    this.field_146563_h = field_146563_h;
                    this.field_146564_i = field_146564_i;
                }
            }
            else {
                this.field_146554_D = 0;
            }
            final int dWheel = Mouse.getDWheel();
            final float field_146570_r = this.field_146570_r;
            if (dWheel < 0) {
                this.field_146570_r += 0.25f;
            }
            else if (dWheel > 0) {
                this.field_146570_r -= 0.25f;
            }
            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0f, 2.0f);
            if (this.field_146570_r != field_146570_r) {
                final float n6 = field_146570_r - this.field_146570_r;
                final float n7 = field_146570_r * this.field_146555_f;
                final float n8 = field_146570_r * this.field_146557_g;
                final float n9 = this.field_146570_r * this.field_146555_f;
                final float n10 = this.field_146570_r * this.field_146557_g;
                this.field_146567_u -= (n9 - n7) * 0.5f;
                this.field_146566_v -= (n10 - n8) * 0.5f;
                final double field_146567_u2 = this.field_146567_u;
                this.field_146569_s = field_146567_u2;
                this.field_146565_w = field_146567_u2;
                final double field_146566_v2 = this.field_146566_v;
                this.field_146568_t = field_146566_v2;
                this.field_146573_x = field_146566_v2;
            }
            if (this.field_146565_w < GuiAchievements.field_146572_y) {
                this.field_146565_w = GuiAchievements.field_146572_y;
            }
            if (this.field_146573_x < GuiAchievements.field_146571_z) {
                this.field_146573_x = GuiAchievements.field_146571_z;
            }
            if (this.field_146565_w >= GuiAchievements.field_146559_A) {
                this.field_146565_w = GuiAchievements.field_146559_A - 1;
            }
            if (this.field_146573_x >= GuiAchievements.field_146560_B) {
                this.field_146573_x = GuiAchievements.field_146560_B - 1;
            }
            this.drawDefaultBackground();
            this.drawAchievementScreen(field_146563_h, field_146564_i, n);
            this.drawTitle();
        }
    }
    
    @Override
    public void doneLoading() {
        if (this.loadingAchievements) {
            this.loadingAchievements = false;
        }
    }
    
    @Override
    public void updateScreen() {
        if (!this.loadingAchievements) {
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            final double n = this.field_146565_w - this.field_146567_u;
            final double n2 = this.field_146573_x - this.field_146566_v;
            if (n * n + n2 * n2 < 4.0) {
                this.field_146567_u += n;
                this.field_146566_v += n2;
            }
            else {
                this.field_146567_u += n * 0.85;
                this.field_146566_v += n2 * 0.85;
            }
        }
    }
    
    protected void drawTitle() {
        this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), (GuiAchievements.width - this.field_146555_f) / 2 + 15, (GuiAchievements.height - this.field_146557_g) / 2 + 5, 4210752);
    }
    
    protected void drawAchievementScreen(final int n, final int n2, final float n3) {
        int n4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * n3);
        int n5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * n3);
        if (n4 < GuiAchievements.field_146572_y) {
            n4 = GuiAchievements.field_146572_y;
        }
        if (n5 < GuiAchievements.field_146571_z) {
            n5 = GuiAchievements.field_146571_z;
        }
        if (n4 >= GuiAchievements.field_146559_A) {
            n4 = GuiAchievements.field_146559_A - 1;
        }
        if (n5 >= GuiAchievements.field_146560_B) {
            n5 = GuiAchievements.field_146560_B - 1;
        }
        final int n6 = (GuiAchievements.width - this.field_146555_f) / 2;
        final int n7 = (GuiAchievements.height - this.field_146557_g) / 2;
        final int n8 = n6 + 16;
        final int n9 = n7 + 17;
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(518);
        GlStateManager.translate((float)n8, (float)n9, -200.0f);
        GlStateManager.scale(1.0f / this.field_146570_r, 1.0f / this.field_146570_r, 0.0f);
        final int n10 = n4 + 288 >> 4;
        final int n11 = n5 + 288 >> 4;
        final int n12 = (n4 + 288) % 16;
        final int n13 = (n5 + 288) % 16;
        final Random random = new Random();
        final float n14 = 16.0f / this.field_146570_r;
        final float n15 = 16.0f / this.field_146570_r;
        int n17 = 0;
        int n19 = 0;
        while (0 * n14 - n13 < 155.0f) {
            final float n16 = 0.6f - (n11 + 0) / 25.0f * 0.3f;
            GlStateManager.color(n16, n16, n16, 1.0f);
            while (0 * n15 - n12 < 224.0f) {
                final Random random2 = random;
                final Minecraft mc = GuiAchievements.mc;
                random2.setSeed(Minecraft.getSession().getPlayerID().hashCode() + n10 + 0 + (n11 + 0) * 16);
                n17 = random.nextInt(1 + n11 + 0) + (n11 + 0) / 2;
                TextureAtlasSprite textureAtlasSprite = this.func_175371_a(Blocks.sand);
                if (0 <= 37 && n11 + 0 != 35) {
                    if (0 == 22) {
                        if (random.nextInt(2) == 0) {
                            textureAtlasSprite = this.func_175371_a(Blocks.diamond_ore);
                        }
                        else {
                            textureAtlasSprite = this.func_175371_a(Blocks.redstone_ore);
                        }
                    }
                    else if (0 == 10) {
                        textureAtlasSprite = this.func_175371_a(Blocks.iron_ore);
                    }
                    else if (0 == 8) {
                        textureAtlasSprite = this.func_175371_a(Blocks.coal_ore);
                    }
                    else if (0 > 4) {
                        textureAtlasSprite = this.func_175371_a(Blocks.stone);
                    }
                    else if (0 > 0) {
                        textureAtlasSprite = this.func_175371_a(Blocks.dirt);
                    }
                }
                else {
                    textureAtlasSprite = this.func_175371_a(Blocks.bedrock);
                }
                GuiAchievements.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.func_175175_a(0 - n12, 0 - n13, textureAtlasSprite, 16, 16);
                int n18 = 0;
                ++n18;
            }
            ++n19;
        }
        GlStateManager.depthFunc(515);
        GuiAchievements.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
        while (0 < AchievementList.achievementList.size()) {
            final Achievement achievement = AchievementList.achievementList.get(0);
            if (achievement.parentAchievement != null) {
                final int n18 = achievement.displayColumn * 24 - n4 + 11;
                n17 = achievement.displayRow * 24 - n5 + 11;
                final int n20 = achievement.parentAchievement.displayColumn * 24 - n4 + 11;
                final int n21 = achievement.parentAchievement.displayRow * 24 - n5 + 11;
                final boolean hasAchievementUnlocked = this.statFileWriter.hasAchievementUnlocked(achievement);
                final boolean canUnlockAchievement = this.statFileWriter.canUnlockAchievement(achievement);
                if (this.statFileWriter.func_150874_c(achievement) <= 4) {
                    if (!hasAchievementUnlocked) {
                        if (canUnlockAchievement) {}
                    }
                    this.drawHorizontalLine(0, n20, 0, -16711936);
                    this.drawVerticalLine(n20, 0, n21, -16711936);
                    if (0 > n20) {
                        this.drawTexturedModalRect(-18, -5, 114, 234, 7, 11);
                    }
                    else if (0 < n20) {
                        this.drawTexturedModalRect(11, -5, 107, 234, 7, 11);
                    }
                    else if (0 > n21) {
                        this.drawTexturedModalRect(-5, -18, 96, 234, 11, 7);
                    }
                    else if (0 < n21) {
                        this.drawTexturedModalRect(-5, 11, 96, 241, 11, 7);
                    }
                }
            }
            ++n19;
        }
        Achievement achievement2 = null;
        final float n22 = (n - n8) * this.field_146570_r;
        final float n23 = (n2 - n9) * this.field_146570_r;
        while (0 < AchievementList.achievementList.size()) {
            final Achievement achievement3 = AchievementList.achievementList.get(0);
            final int n24 = achievement3.displayColumn * 24 - n4;
            final int n25 = achievement3.displayRow * 24 - n5;
            Label_1308: {
                if (n24 >= -24 && n25 >= -24 && n24 <= 224.0f * this.field_146570_r && n25 <= 155.0f * this.field_146570_r) {
                    final int func_150874_c = this.statFileWriter.func_150874_c(achievement3);
                    if (this.statFileWriter.hasAchievementUnlocked(achievement3)) {
                        final float n26 = 0.75f;
                        GlStateManager.color(n26, n26, n26, 1.0f);
                    }
                    else if (this.statFileWriter.canUnlockAchievement(achievement3)) {
                        final float n27 = 1.0f;
                        GlStateManager.color(n27, n27, n27, 1.0f);
                    }
                    else if (func_150874_c < 3) {
                        final float n28 = 0.3f;
                        GlStateManager.color(n28, n28, n28, 1.0f);
                    }
                    else if (func_150874_c == 3) {
                        final float n29 = 0.2f;
                        GlStateManager.color(n29, n29, n29, 1.0f);
                    }
                    else {
                        if (func_150874_c != 4) {
                            break Label_1308;
                        }
                        final float n30 = 0.1f;
                        GlStateManager.color(n30, n30, n30, 1.0f);
                    }
                    GuiAchievements.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
                    if (achievement3.getSpecial()) {
                        this.drawTexturedModalRect(n24 - 2, n25 - 2, 26, 202, 26, 26);
                    }
                    else {
                        this.drawTexturedModalRect(n24 - 2, n25 - 2, 0, 202, 26, 26);
                    }
                    if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                        final float n31 = 0.1f;
                        GlStateManager.color(n31, n31, n31, 1.0f);
                        this.itemRender.func_175039_a(false);
                    }
                    this.itemRender.func_180450_b(achievement3.theItemStack, n24 + 3, n25 + 3);
                    GlStateManager.blendFunc(770, 771);
                    if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                        this.itemRender.func_175039_a(true);
                    }
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    if (n22 >= n24 && n22 <= n24 + 22 && n23 >= n25 && n23 <= n25 + 22) {
                        achievement2 = achievement3;
                    }
                }
            }
            ++n17;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiAchievements.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
        this.drawTexturedModalRect(n6, n7, 0, 0, this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(515);
        super.drawScreen(n, n2, n3);
        if (achievement2 != null) {
            String s = achievement2.getStatName().getUnformattedText();
            final String description = achievement2.getDescription();
            final int n32 = n + 12;
            final int n33 = n2 - 4;
            final int func_150874_c2 = this.statFileWriter.func_150874_c(achievement2);
            if (this.statFileWriter.canUnlockAchievement(achievement2)) {
                final int max = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                int splitStringWidth = this.fontRendererObj.splitStringWidth(description, max);
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    splitStringWidth += 12;
                }
                this.drawGradientRect(n32 - 3, n33 - 3, n32 + max + 3, n33 - 16711936 + 3 + 12, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(description, n32, n33 + 12, max, -6250336);
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    this.fontRendererObj.func_175063_a(I18n.format("achievement.taken", new Object[0]), (float)n32, (float)(n33 - 16711936 + 4), -7302913);
                }
            }
            else if (func_150874_c2 == 3) {
                s = I18n.format("achievement.unknown", new Object[0]);
                final int max2 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                final String unformattedText = new ChatComponentTranslation("achievement.requires", new Object[] { achievement2.parentAchievement.getStatName() }).getUnformattedText();
                this.drawGradientRect(n32 - 3, n33 - 3, n32 + max2 + 3, n33 + this.fontRendererObj.splitStringWidth(unformattedText, max2) + 12 + 3, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(unformattedText, n32, n33 + 12, max2, -9416624);
            }
            else if (func_150874_c2 < 3) {
                final int max3 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                final String unformattedText2 = new ChatComponentTranslation("achievement.requires", new Object[] { achievement2.parentAchievement.getStatName() }).getUnformattedText();
                this.drawGradientRect(n32 - 3, n33 - 3, n32 + max3 + 3, n33 + this.fontRendererObj.splitStringWidth(unformattedText2, max3) + 12 + 3, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(unformattedText2, n32, n33 + 12, max3, -9416624);
            }
            else {
                s = null;
            }
            if (s != null) {
                this.fontRendererObj.func_175063_a(s, (float)n32, (float)n33, this.statFileWriter.canUnlockAchievement(achievement2) ? (achievement2.getSpecial() ? -128 : -1) : (achievement2.getSpecial() ? -8355776 : -8355712));
            }
        }
    }
    
    private TextureAtlasSprite func_175371_a(final Block block) {
        return Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178122_a(block.getDefaultState());
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return !this.loadingAchievements;
    }
}
