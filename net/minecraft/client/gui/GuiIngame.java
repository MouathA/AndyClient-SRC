package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import Mood.*;
import net.minecraft.potion.*;
import java.awt.*;
import DTool.util.*;
import net.minecraft.entity.player.*;
import java.util.stream.*;
import java.util.function.*;
import optifine.*;
import net.minecraft.client.resources.*;
import net.minecraft.inventory.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.border.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class GuiIngame extends Gui
{
    private static final ResourceLocation vignetteTexPath;
    private static final ResourceLocation widgetsTexPath;
    private static final ResourceLocation pumpkinBlurTexPath;
    private final Random rand;
    private final Minecraft mc;
    private final RenderItem itemRenderer;
    private final GuiNewChat persistantChatGUI;
    private final GuiStreamIndicator streamIndicator;
    private int updateCounter;
    private String recordPlaying;
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    public float prevVignetteBrightness;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack;
    private final GuiOverlayDebug overlayDebug;
    private final GuiSpectator field_175197_u;
    private final GuiPlayerTabOverlay overlayPlayerList;
    private int field_175195_w;
    private String field_175201_x;
    private String field_175200_y;
    private int field_175199_z;
    private int field_175192_A;
    private int field_175193_B;
    private int field_175194_C;
    private int field_175189_D;
    private long field_175190_E;
    private long field_175191_F;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000661";
        vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
        widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
        pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    }
    
    public GuiIngame(final Minecraft mc) {
        this.rand = new Random();
        this.recordPlaying = "";
        this.prevVignetteBrightness = 1.0f;
        this.field_175201_x = "";
        this.field_175200_y = "";
        this.field_175194_C = 0;
        this.field_175189_D = 0;
        this.field_175190_E = 0L;
        this.field_175191_F = 0L;
        this.mc = mc;
        this.itemRenderer = mc.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mc);
        this.field_175197_u = new GuiSpectator(mc);
        this.persistantChatGUI = new GuiNewChat(mc);
        this.streamIndicator = new GuiStreamIndicator(mc);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mc, this);
        this.func_175177_a();
    }
    
    public void Adatok() {
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawStringWithShadow("§6FPS§8§l>§r§e " + Minecraft.debugFPS, 1.0, 30.0, -1);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawStringWithShadow("§6X§8§l>§r§e " + Math.round(Minecraft.thePlayer.posX), 1.0, 40.0, -1);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawStringWithShadow("§6Y§8§l>§r§e " + Math.round(Minecraft.thePlayer.posY), 1.0, 50.0, -1);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawStringWithShadow("§6Z§8§l>§r§e " + Math.round(Minecraft.thePlayer.posZ), 1.0, 60.0, -1);
    }
    
    public void func_175177_a() {
        this.field_175199_z = 10;
        this.field_175192_A = 70;
        this.field_175193_B = 20;
    }
    
    public void func_175180_a(final float n) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final int scaledWidth = ScaledResolution.getScaledWidth();
        final int scaledHeight = ScaledResolution.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        this.Adatok();
        if (Config.isVignetteEnabled()) {
            this.func_180480_a(Minecraft.thePlayer.getBrightness(n), scaledResolution);
        }
        else {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
        final ItemStack armorItemInSlot = Minecraft.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && armorItemInSlot != null && armorItemInSlot.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            final Client instance = Client.INSTANCE;
            if (!Client.getModuleByName("NoPumpkin").toggled) {
                this.func_180476_e(scaledResolution);
            }
        }
        if (!Minecraft.thePlayer.isPotionActive(Potion.confusion)) {
            final float n2 = Minecraft.thePlayer.prevTimeInPortal + (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * n;
            if (n2 > 0.0f) {
                this.func_180474_b(n2, scaledResolution);
            }
        }
        if (Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
            this.field_175197_u.func_175264_a(scaledResolution, n);
        }
        else {
            this.func_180479_a(scaledResolution, n);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        if (this.func_175183_b() && this.mc.gameSettings.thirdPersonView < 1) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            this.drawTexturedModalRect(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (Minecraft.playerController.shouldDrawHUD()) {
            this.func_180477_d(scaledResolution);
        }
        if (Minecraft.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            final int sleepTimer = Minecraft.thePlayer.getSleepTimer();
            float n3 = sleepTimer / 100.0f;
            if (n3 > 1.0f) {
                n3 = 1.0f - (sleepTimer - 100) / 10.0f;
            }
            final int n4 = (int)(220.0f * n3) << 24 | 0x101020;
            Gui.drawRect(0, 0, scaledWidth, scaledHeight, 255);
            this.mc.mcProfiler.endSection();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n5 = scaledWidth / 2 - 91;
        if (Minecraft.thePlayer.isRidingHorse()) {
            this.func_175186_a(scaledResolution, n5);
        }
        else if (Minecraft.playerController.gameIsSurvivalOrAdventure()) {
            this.func_175176_b(scaledResolution, n5);
        }
        if (this.mc.gameSettings.heldItemTooltips && !Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
            this.func_175182_a(scaledResolution);
        }
        else if (Minecraft.thePlayer.func_175149_v()) {
            this.field_175197_u.func_175263_a(scaledResolution);
        }
        if (this.mc.isDemo()) {
            this.func_175185_b(scaledResolution);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.func_175237_a(scaledResolution);
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            final float n6 = this.recordPlayingUpFor - n;
            final int n7 = (int)(n6 * 255.0f / 20.0f);
            if (255 > 255) {}
            if (255 > 8) {
                GlStateManager.translate((float)(scaledWidth / 2), (float)(scaledHeight - 68), 0.0f);
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                if (this.recordIsPlaying) {
                    final int n8 = Color.HSBtoRGB(n6 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                this.func_175179_f().drawString(this.recordPlaying, -this.func_175179_f().getStringWidth(this.recordPlaying) / 2, -4, -1);
            }
            this.mc.mcProfiler.endSection();
        }
        if (this.field_175195_w > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            final float n9 = this.field_175195_w - n;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
                final int n10 = (int)((this.field_175199_z + this.field_175192_A + this.field_175193_B - n9) * 255.0f / this.field_175199_z);
            }
            if (this.field_175195_w <= this.field_175193_B) {
                final int n11 = (int)(n9 * 255.0f / this.field_175193_B);
            }
            MathHelper.clamp_int(255, 0, 255);
            if (255 > 8) {
                GlStateManager.translate((float)(scaledWidth / 2), (float)(scaledHeight / 2), 0.0f);
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.scale(4.0f, 4.0f, 4.0f);
                this.func_175179_f().func_175065_a(this.field_175201_x, (float)(-this.func_175179_f().getStringWidth(this.field_175201_x) / 2), -10.0f, 16777215, true);
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                this.func_175179_f().func_175065_a(this.field_175200_y, (float)(-this.func_175179_f().getStringWidth(this.field_175200_y) / 2), 5.0f, 16777215, true);
            }
            this.mc.mcProfiler.endSection();
        }
        final Scoreboard scoreboard = Minecraft.theWorld.getScoreboard();
        ScoreObjective objectiveInDisplaySlot = null;
        final ScorePlayerTeam playersTeam = scoreboard.getPlayersTeam(Minecraft.thePlayer.getName());
        if (playersTeam != null) {
            final int func_175746_b = playersTeam.func_178775_l().func_175746_b();
            if (func_175746_b >= 0) {
                objectiveInDisplaySlot = scoreboard.getObjectiveInDisplaySlot(3 + func_175746_b);
            }
        }
        final ScoreObjective scoreObjective = (objectiveInDisplaySlot != null) ? objectiveInDisplaySlot : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreObjective != null) {
            this.func_180475_a(scoreObjective, scaledResolution);
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.translate(0.0f, (float)(scaledHeight - 48), 0.0f);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        final ScoreObjective objectiveInDisplaySlot2 = scoreboard.getObjectiveInDisplaySlot(0);
        if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning() || Minecraft.thePlayer.sendQueue.func_175106_d().size() > 1 || objectiveInDisplaySlot2 != null)) {
            this.overlayPlayerList.func_175246_a(true);
            this.overlayPlayerList.func_175249_a(scaledWidth, scoreboard, objectiveInDisplaySlot2);
        }
        else {
            this.overlayPlayerList.func_175246_a(false);
        }
        Client.hud.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderImage(new ResourceLocation("MooDTool/Andy.png"), 1.0f, 1.0f, 100.0f, 30.0f);
    }
    
    protected void func_180479_a(final ScaledResolution scaledResolution, final float n) {
        if (this.mc.func_175606_aa() instanceof EntityPlayer) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.widgetsTexPath);
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.func_175606_aa();
            final int n2 = ScaledResolution.getScaledWidth() / 2;
            final float zLevel = this.zLevel;
            this.zLevel = -90.0f;
            this.zLevel = zLevel;
            Minecraft.getInstance();
            if (Minecraft.thePlayer.inventory.currentItem == 0) {
                Minecraft.getInstance();
                Gui.drawRect(ScaledResolution.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, ScaledResolution.getScaledHeight() - 22, ScaledResolution.getScaledWidth() / 2 + 91 - 160, ScaledResolution.getScaledHeight(), Integer.MAX_VALUE);
            }
            else {
                Minecraft.getInstance();
                Minecraft.getInstance();
                Gui.drawRect(ScaledResolution.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, ScaledResolution.getScaledHeight() - 22, ScaledResolution.getScaledWidth() / 2 + 91 - 20 * (8 - Minecraft.thePlayer.inventory.currentItem), ScaledResolution.getScaledHeight(), Integer.MAX_VALUE);
            }
            Gui.drawRect(ScaledResolution.getScaledWidth() / 2 - 91, ScaledResolution.getScaledHeight() - 22, ScaledResolution.getScaledWidth() / 2 + 91, ScaledResolution.getScaledHeight(), Integer.MIN_VALUE);
            Gui.drawRect(0, ScaledResolution.getScaledHeight() - 22, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), Integer.MIN_VALUE);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            while (0 < 9) {
                this.func_175184_a(0, ScaledResolution.getScaledWidth() / 2 - 90 + 0 + 2, ScaledResolution.getScaledHeight() - 16 - 3, n, entityPlayer);
                int n3 = 0;
                ++n3;
            }
            final Client instance = Client.INSTANCE;
            if (Client.getModuleByName("HUD").toggled && !this.mc.isSingleplayer()) {
                Minecraft.fontRendererObj.drawStringWithShadow("§6Players§8>§7§7 " + StreamSupport.stream(Minecraft.theWorld.getLoadedEntityList().spliterator(), false).filter(GuiIngame::lambda$0).count(), 1.0, 343.0, -1);
                Minecraft.fontRendererObj.drawStringWithShadow("§6ServerIP§8>§7§7 " + this.mc.getCurrentServerData().serverIP, 1.0, 333.0, -1);
                Minecraft.fontRendererObj.drawStringWithShadow("§6Ping§8>§7§7 " + this.mc.getCurrentServerData().pingToServer + "ms", 413.0, 333.0, -1);
                Minecraft.fontRendererObj.drawStringWithShadow("§6ServerBrand§8>§7§7 " + Minecraft.thePlayer.getClientBrand().replaceAll("\\(.*?\\)", "").replace("  ", " "), 412.0, 343.0, -1);
            }
        }
    }
    
    public void func_175186_a(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        final int n2 = (int)(Minecraft.thePlayer.getHorseJumpPower() * 183);
        final int n3 = ScaledResolution.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(n, n3, 0, 84, 182, 5);
        if (n2 > 0) {
            this.drawTexturedModalRect(n, n3, 0, 89, n2, 5);
        }
        this.mc.mcProfiler.endSection();
    }
    
    public void func_175176_b(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        if (Minecraft.thePlayer.xpBarCap() > 0) {
            final int n2 = (int)(Minecraft.thePlayer.experience * 8453921);
            final int n3 = ScaledResolution.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(n, n3, 0, 64, 8453920, 5);
            if (n2 > 0) {
                this.drawTexturedModalRect(n, n3, 0, 69, n2, 5);
            }
        }
        this.mc.mcProfiler.endSection();
        if (Minecraft.thePlayer.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            if (Config.isCustomColors()) {
                CustomColors.getExpBarTextColor(8453920);
            }
            final String string = new StringBuilder().append(Minecraft.thePlayer.experienceLevel).toString();
            final int n4 = (ScaledResolution.getScaledWidth() - this.func_175179_f().getStringWidth(string)) / 2;
            final int n5 = ScaledResolution.getScaledHeight() - 31 - 4;
            this.func_175179_f().drawString(string, n4 + 1, n5, 0);
            this.func_175179_f().drawString(string, n4 - 1, n5, 0);
            this.func_175179_f().drawString(string, n4, n5 + 1, 0);
            this.func_175179_f().drawString(string, n4, n5 - 1, 0);
            this.func_175179_f().drawString(string, n4, n5, 8453920);
            this.mc.mcProfiler.endSection();
        }
    }
    
    public void func_175182_a(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("toolHighlight");
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            String s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = EnumChatFormatting.ITALIC + s;
            }
            final int n = (ScaledResolution.getScaledWidth() - this.func_175179_f().getStringWidth(s)) / 2;
            int n2 = ScaledResolution.getScaledHeight() - 59;
            if (!Minecraft.playerController.shouldDrawHUD()) {
                n2 += 14;
            }
            final int n3 = (int)(this.remainingHighlightTicks * 256.0f / 10.0f);
            if (255 > 255) {}
            if (255 > 0) {
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.func_175179_f().func_175063_a(s, (float)n, (float)n2, -1);
            }
        }
        this.mc.mcProfiler.endSection();
    }
    
    public void func_175185_b(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("demo");
        String s;
        if (Minecraft.theWorld.getTotalWorldTime() >= 120500L) {
            s = I18n.format("demo.demoExpired", new Object[0]);
        }
        else {
            s = I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - Minecraft.theWorld.getTotalWorldTime())));
        }
        this.func_175179_f().func_175063_a(s, (float)(ScaledResolution.getScaledWidth() - this.func_175179_f().getStringWidth(s) - 10), 5.0f, 16777215);
        this.mc.mcProfiler.endSection();
    }
    
    protected boolean func_175183_b() {
        return (!this.mc.gameSettings.showDebugInfo || Minecraft.thePlayer.func_175140_cp() || this.mc.gameSettings.field_178879_v) && (!Minecraft.playerController.enableEverythingIsScrewedUpMode() || this.mc.pointedEntity != null || (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && Minecraft.theWorld.getTileEntity(this.mc.objectMouseOver.func_178782_a()) instanceof IInventory));
    }
    
    public void func_180478_c(final ScaledResolution scaledResolution) {
        this.streamIndicator.render(ScaledResolution.getScaledWidth() - 10, 10);
    }
    
    private void func_180475_a(final ScoreObjective scoreObjective, final ScaledResolution scaledResolution) {
        final Scoreboard scoreboard = scoreObjective.getScoreboard();
        final Collection sortedScores = scoreboard.getSortedScores(scoreObjective);
        final ArrayList arrayList = Lists.newArrayList(Iterables.filter(sortedScores, new com.google.common.base.Predicate() {
            private static final String __OBFID;
            final GuiIngame this$0;
            
            public boolean func_178903_a(final Score score) {
                return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_178903_a((Score)o);
            }
            
            static {
                __OBFID = "CL_00001958";
            }
        }));
        ArrayList<Score> arrayList2;
        if (arrayList.size() > 15) {
            arrayList2 = (ArrayList<Score>)Lists.newArrayList(Iterables.skip(arrayList, sortedScores.size() - 15));
        }
        else {
            arrayList2 = (ArrayList<Score>)arrayList;
        }
        int n = this.func_175179_f().getStringWidth(scoreObjective.getDisplayName());
        for (final Score score : arrayList2) {
            n = Math.max(n, this.func_175179_f().getStringWidth(String.valueOf(ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.getPlayerName()), score.getPlayerName())) + ": " + EnumChatFormatting.RED + score.getScorePoints()));
        }
        final int n2 = ScaledResolution.getScaledHeight() / 2 + arrayList2.size() * this.func_175179_f().FONT_HEIGHT / 3;
        final int n3 = ScaledResolution.getScaledWidth() - n - 3;
        for (final Score score2 : arrayList2) {
            int n4 = 0;
            ++n4;
            final String formatPlayerName = ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score2.getPlayerName()), score2.getPlayerName());
            final String string = new StringBuilder().append(EnumChatFormatting.RED).append(score2.getScorePoints()).toString();
            final int n5 = n2 - 0 * this.func_175179_f().FONT_HEIGHT;
            final int n6 = ScaledResolution.getScaledWidth() - 3 + 2;
            Gui.drawRect(n3 - 2, n5, n6, n5 + this.func_175179_f().FONT_HEIGHT, 1342177280);
            this.func_175179_f().drawString(formatPlayerName, n3, n5, 553648127);
            this.func_175179_f().drawString(string, n6 - this.func_175179_f().getStringWidth(string), n5, 553648127);
            if (0 == arrayList2.size()) {
                final String displayName = scoreObjective.getDisplayName();
                Gui.drawRect(n3 - 2, n5 - this.func_175179_f().FONT_HEIGHT - 1, n6, n5 - 1, 1610612736);
                Gui.drawRect(n3 - 2, n5 - 1, n6, n5, 1342177280);
                this.func_175179_f().drawString(displayName, n3 + n / 2 - this.func_175179_f().getStringWidth(displayName) / 2, n5 - this.func_175179_f().FONT_HEIGHT, 553648127);
            }
        }
    }
    
    private void func_180477_d(final ScaledResolution p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //     4: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //     7: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    10: ifeq            1293
        //    13: aload_0        
        //    14: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //    17: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //    20: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    23: astore_2       
        //    24: aload_2        
        //    25: invokevirtual   net/minecraft/entity/player/EntityPlayer.getHealth:()F
        //    28: invokestatic    net/minecraft/util/MathHelper.ceiling_float_int:(F)I
        //    31: istore_3       
        //    32: aload_0        
        //    33: getfield        net/minecraft/client/gui/GuiIngame.field_175191_F:J
        //    36: aload_0        
        //    37: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //    40: i2l            
        //    41: lcmp           
        //    42: ifle            72
        //    45: aload_0        
        //    46: getfield        net/minecraft/client/gui/GuiIngame.field_175191_F:J
        //    49: aload_0        
        //    50: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //    53: i2l            
        //    54: lsub           
        //    55: ldc2_w          3
        //    58: ldiv           
        //    59: ldc2_w          2
        //    62: lrem           
        //    63: lconst_1       
        //    64: lcmp           
        //    65: ifne            72
        //    68: iconst_1       
        //    69: goto            73
        //    72: iconst_0       
        //    73: istore          4
        //    75: iload_3        
        //    76: aload_0        
        //    77: getfield        net/minecraft/client/gui/GuiIngame.field_175194_C:I
        //    80: if_icmpge       112
        //    83: aload_2        
        //    84: getfield        net/minecraft/entity/player/EntityPlayer.hurtResistantTime:I
        //    87: ifle            112
        //    90: aload_0        
        //    91: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //    94: putfield        net/minecraft/client/gui/GuiIngame.field_175190_E:J
        //    97: aload_0        
        //    98: aload_0        
        //    99: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //   102: bipush          20
        //   104: iadd           
        //   105: i2l            
        //   106: putfield        net/minecraft/client/gui/GuiIngame.field_175191_F:J
        //   109: goto            146
        //   112: iload_3        
        //   113: aload_0        
        //   114: getfield        net/minecraft/client/gui/GuiIngame.field_175194_C:I
        //   117: if_icmple       146
        //   120: aload_2        
        //   121: getfield        net/minecraft/entity/player/EntityPlayer.hurtResistantTime:I
        //   124: ifle            146
        //   127: aload_0        
        //   128: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   131: putfield        net/minecraft/client/gui/GuiIngame.field_175190_E:J
        //   134: aload_0        
        //   135: aload_0        
        //   136: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //   139: bipush          10
        //   141: iadd           
        //   142: i2l            
        //   143: putfield        net/minecraft/client/gui/GuiIngame.field_175191_F:J
        //   146: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   149: aload_0        
        //   150: getfield        net/minecraft/client/gui/GuiIngame.field_175190_E:J
        //   153: lsub           
        //   154: ldc2_w          1000
        //   157: lcmp           
        //   158: ifle            178
        //   161: aload_0        
        //   162: iload_3        
        //   163: putfield        net/minecraft/client/gui/GuiIngame.field_175194_C:I
        //   166: aload_0        
        //   167: iload_3        
        //   168: putfield        net/minecraft/client/gui/GuiIngame.field_175189_D:I
        //   171: aload_0        
        //   172: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   175: putfield        net/minecraft/client/gui/GuiIngame.field_175190_E:J
        //   178: aload_0        
        //   179: iload_3        
        //   180: putfield        net/minecraft/client/gui/GuiIngame.field_175194_C:I
        //   183: aload_0        
        //   184: getfield        net/minecraft/client/gui/GuiIngame.field_175189_D:I
        //   187: istore          5
        //   189: aload_0        
        //   190: getfield        net/minecraft/client/gui/GuiIngame.rand:Ljava/util/Random;
        //   193: aload_0        
        //   194: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //   197: ldc_w           312871
        //   200: imul           
        //   201: i2l            
        //   202: invokevirtual   java/util/Random.setSeed:(J)V
        //   205: aload_2        
        //   206: invokevirtual   net/minecraft/entity/player/EntityPlayer.getFoodStats:()Lnet/minecraft/util/FoodStats;
        //   209: astore          7
        //   211: aload           7
        //   213: invokevirtual   net/minecraft/util/FoodStats.getFoodLevel:()I
        //   216: istore          8
        //   218: aload           7
        //   220: invokevirtual   net/minecraft/util/FoodStats.getPrevFoodLevel:()I
        //   223: istore          9
        //   225: aload_2        
        //   226: getstatic       net/minecraft/entity/SharedMonsterAttributes.maxHealth:Lnet/minecraft/entity/ai/attributes/IAttribute;
        //   229: invokevirtual   net/minecraft/entity/player/EntityPlayer.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
        //   232: astore          10
        //   234: invokestatic    net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   237: iconst_2       
        //   238: idiv           
        //   239: bipush          91
        //   241: isub           
        //   242: istore          11
        //   244: invokestatic    net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   247: iconst_2       
        //   248: idiv           
        //   249: bipush          91
        //   251: iadd           
        //   252: istore          12
        //   254: invokestatic    net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //   257: bipush          39
        //   259: isub           
        //   260: istore          13
        //   262: aload           10
        //   264: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.getAttributeValue:()D
        //   269: d2f            
        //   270: fstore          14
        //   272: aload_2        
        //   273: invokevirtual   net/minecraft/entity/player/EntityPlayer.getAbsorptionAmount:()F
        //   276: fstore          15
        //   278: fload           14
        //   280: fload           15
        //   282: fadd           
        //   283: fconst_2       
        //   284: fdiv           
        //   285: ldc_w           10.0
        //   288: fdiv           
        //   289: invokestatic    net/minecraft/util/MathHelper.ceiling_float_int:(F)I
        //   292: istore          16
        //   294: bipush          10
        //   296: iload           16
        //   298: iconst_2       
        //   299: isub           
        //   300: isub           
        //   301: iconst_3       
        //   302: invokestatic    java/lang/Math.max:(II)I
        //   305: istore          17
        //   307: iload           13
        //   309: iload           16
        //   311: iconst_1       
        //   312: isub           
        //   313: iload           17
        //   315: imul           
        //   316: isub           
        //   317: bipush          10
        //   319: isub           
        //   320: istore          18
        //   322: fload           15
        //   324: fstore          19
        //   326: aload_2        
        //   327: invokevirtual   net/minecraft/entity/player/EntityPlayer.getTotalArmorValue:()I
        //   330: istore          20
        //   332: aload_2        
        //   333: getstatic       net/minecraft/potion/Potion.regeneration:Lnet/minecraft/potion/Potion;
        //   336: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   339: ifeq            358
        //   342: aload_0        
        //   343: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //   346: fload           14
        //   348: ldc_w           5.0
        //   351: fadd           
        //   352: invokestatic    net/minecraft/util/MathHelper.ceiling_float_int:(F)I
        //   355: irem           
        //   356: istore          21
        //   358: aload_0        
        //   359: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //   362: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   365: ldc_w           "armor"
        //   368: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   371: goto            451
        //   374: iload           20
        //   376: ifle            448
        //   379: iload           11
        //   381: iconst_0       
        //   382: iadd           
        //   383: istore          23
        //   385: iconst_1       
        //   386: iload           20
        //   388: if_icmpge       406
        //   391: aload_0        
        //   392: iconst_0       
        //   393: iload           18
        //   395: bipush          34
        //   397: bipush          9
        //   399: bipush          9
        //   401: bipush          9
        //   403: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   406: iconst_1       
        //   407: iload           20
        //   409: if_icmpne       427
        //   412: aload_0        
        //   413: iconst_0       
        //   414: iload           18
        //   416: bipush          25
        //   418: bipush          9
        //   420: bipush          9
        //   422: bipush          9
        //   424: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   427: iconst_1       
        //   428: iload           20
        //   430: if_icmple       448
        //   433: aload_0        
        //   434: iconst_0       
        //   435: iload           18
        //   437: bipush          16
        //   439: bipush          9
        //   441: bipush          9
        //   443: bipush          9
        //   445: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   448: iinc            22, 1
        //   451: iconst_0       
        //   452: bipush          10
        //   454: if_icmplt       374
        //   457: aload_0        
        //   458: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //   461: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   464: ldc_w           "health"
        //   467: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   470: fload           14
        //   472: fload           15
        //   474: fadd           
        //   475: fconst_2       
        //   476: fdiv           
        //   477: invokestatic    net/minecraft/util/MathHelper.ceiling_float_int:(F)I
        //   480: iconst_1       
        //   481: isub           
        //   482: istore          22
        //   484: goto            762
        //   487: aload_2        
        //   488: getstatic       net/minecraft/potion/Potion.poison:Lnet/minecraft/potion/Potion;
        //   491: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   494: ifeq            503
        //   497: iinc            23, 36
        //   500: goto            516
        //   503: aload_2        
        //   504: getstatic       net/minecraft/potion/Potion.wither:Lnet/minecraft/potion/Potion;
        //   507: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   510: ifeq            516
        //   513: iinc            23, 72
        //   516: iload           4
        //   518: ifeq            521
        //   521: iconst_1       
        //   522: i2f            
        //   523: ldc_w           10.0
        //   526: fdiv           
        //   527: invokestatic    net/minecraft/util/MathHelper.ceiling_float_int:(F)I
        //   530: iconst_1       
        //   531: isub           
        //   532: istore          24
        //   534: iload           11
        //   536: iconst_0       
        //   537: iadd           
        //   538: istore          25
        //   540: iload           13
        //   542: bipush          16
        //   544: iload           17
        //   546: imul           
        //   547: isub           
        //   548: istore          26
        //   550: iload_3        
        //   551: iconst_4       
        //   552: if_icmpgt       568
        //   555: iload           26
        //   557: aload_0        
        //   558: getfield        net/minecraft/client/gui/GuiIngame.rand:Ljava/util/Random;
        //   561: iconst_2       
        //   562: invokevirtual   java/util/Random.nextInt:(I)I
        //   565: iadd           
        //   566: istore          26
        //   568: iconst_0       
        //   569: iconst_m1      
        //   570: if_icmpne       576
        //   573: iinc            26, -2
        //   576: aload_2        
        //   577: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   580: invokevirtual   net/minecraft/world/World.getWorldInfo:()Lnet/minecraft/world/storage/WorldInfo;
        //   583: invokevirtual   net/minecraft/world/storage/WorldInfo.isHardcoreModeEnabled:()Z
        //   586: ifeq            589
        //   589: aload_0        
        //   590: iconst_0       
        //   591: iload           26
        //   593: bipush          25
        //   595: bipush          45
        //   597: bipush          9
        //   599: bipush          9
        //   601: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   604: iload           4
        //   606: ifeq            651
        //   609: iconst_1       
        //   610: iload           5
        //   612: if_icmpge       630
        //   615: aload_0        
        //   616: iconst_0       
        //   617: iload           26
        //   619: bipush          54
        //   621: bipush          45
        //   623: bipush          9
        //   625: bipush          9
        //   627: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   630: iconst_1       
        //   631: iload           5
        //   633: if_icmpne       651
        //   636: aload_0        
        //   637: iconst_0       
        //   638: iload           26
        //   640: bipush          63
        //   642: bipush          45
        //   644: bipush          9
        //   646: bipush          9
        //   648: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   651: fload           19
        //   653: fconst_0       
        //   654: fcmpl          
        //   655: ifle            719
        //   658: fload           19
        //   660: fload           15
        //   662: fcmpl          
        //   663: ifne            694
        //   666: fload           15
        //   668: fconst_2       
        //   669: frem           
        //   670: fconst_1       
        //   671: fcmpl          
        //   672: ifne            694
        //   675: aload_0        
        //   676: iconst_0       
        //   677: iload           26
        //   679: sipush          153
        //   682: bipush          45
        //   684: bipush          9
        //   686: bipush          9
        //   688: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   691: goto            710
        //   694: aload_0        
        //   695: iconst_0       
        //   696: iload           26
        //   698: sipush          144
        //   701: bipush          45
        //   703: bipush          9
        //   705: bipush          9
        //   707: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   710: fload           19
        //   712: fconst_2       
        //   713: fsub           
        //   714: fstore          19
        //   716: goto            759
        //   719: iconst_1       
        //   720: iload_3        
        //   721: if_icmpge       739
        //   724: aload_0        
        //   725: iconst_0       
        //   726: iload           26
        //   728: bipush          36
        //   730: bipush          45
        //   732: bipush          9
        //   734: bipush          9
        //   736: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   739: iconst_1       
        //   740: iload_3        
        //   741: if_icmpne       759
        //   744: aload_0        
        //   745: iconst_0       
        //   746: iload           26
        //   748: bipush          45
        //   750: bipush          45
        //   752: bipush          9
        //   754: bipush          9
        //   756: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   759: iinc            22, -1
        //   762: iconst_0       
        //   763: ifge            487
        //   766: aload_2        
        //   767: getfield        net/minecraft/entity/player/EntityPlayer.ridingEntity:Lnet/minecraft/entity/Entity;
        //   770: astore          27
        //   772: aload           27
        //   774: ifnonnull       979
        //   777: aload_0        
        //   778: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //   781: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   784: ldc_w           "food"
        //   787: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   790: goto            970
        //   793: iload           13
        //   795: istore          28
        //   797: aload_2        
        //   798: getstatic       net/minecraft/potion/Potion.hunger:Lnet/minecraft/potion/Potion;
        //   801: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   804: ifeq            810
        //   807: iinc            24, 36
        //   810: aload_2        
        //   811: invokevirtual   net/minecraft/entity/player/EntityPlayer.getFoodStats:()Lnet/minecraft/util/FoodStats;
        //   814: invokevirtual   net/minecraft/util/FoodStats.getSaturationLevel:()F
        //   817: fconst_0       
        //   818: fcmpg          
        //   819: ifgt            851
        //   822: aload_0        
        //   823: getfield        net/minecraft/client/gui/GuiIngame.updateCounter:I
        //   826: iload           8
        //   828: iconst_3       
        //   829: imul           
        //   830: iconst_1       
        //   831: iadd           
        //   832: irem           
        //   833: ifne            851
        //   836: iload           13
        //   838: aload_0        
        //   839: getfield        net/minecraft/client/gui/GuiIngame.rand:Ljava/util/Random;
        //   842: iconst_3       
        //   843: invokevirtual   java/util/Random.nextInt:(I)I
        //   846: iconst_1       
        //   847: isub           
        //   848: iadd           
        //   849: istore          28
        //   851: iconst_0       
        //   852: ifeq            855
        //   855: iload           12
        //   857: iconst_0       
        //   858: isub           
        //   859: bipush          9
        //   861: isub           
        //   862: istore          26
        //   864: aload_0        
        //   865: iload           26
        //   867: iconst_5       
        //   868: bipush          25
        //   870: bipush          27
        //   872: bipush          9
        //   874: bipush          9
        //   876: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   879: iconst_0       
        //   880: ifeq            925
        //   883: iconst_1       
        //   884: iload           9
        //   886: if_icmpge       904
        //   889: aload_0        
        //   890: iload           26
        //   892: iconst_5       
        //   893: bipush          70
        //   895: bipush          27
        //   897: bipush          9
        //   899: bipush          9
        //   901: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   904: iconst_1       
        //   905: iload           9
        //   907: if_icmpne       925
        //   910: aload_0        
        //   911: iload           26
        //   913: iconst_5       
        //   914: bipush          79
        //   916: bipush          27
        //   918: bipush          9
        //   920: bipush          9
        //   922: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   925: iconst_1       
        //   926: iload           8
        //   928: if_icmpge       946
        //   931: aload_0        
        //   932: iload           26
        //   934: iconst_5       
        //   935: bipush          52
        //   937: bipush          27
        //   939: bipush          9
        //   941: bipush          9
        //   943: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   946: iconst_1       
        //   947: iload           8
        //   949: if_icmpne       967
        //   952: aload_0        
        //   953: iload           26
        //   955: iconst_5       
        //   956: bipush          61
        //   958: bipush          27
        //   960: bipush          9
        //   962: bipush          9
        //   964: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //   967: iinc            23, 1
        //   970: iconst_0       
        //   971: bipush          10
        //   973: if_icmplt       793
        //   976: goto            1157
        //   979: aload           27
        //   981: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   984: ifeq            1157
        //   987: aload_0        
        //   988: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //   991: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   994: ldc_w           "mountHealth"
        //   997: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1000: aload           27
        //  1002: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //  1005: astore          29
        //  1007: aload           29
        //  1009: invokevirtual   net/minecraft/entity/EntityLivingBase.getHealth:()F
        //  1012: f2d            
        //  1013: invokestatic    java/lang/Math.ceil:(D)D
        //  1016: d2i            
        //  1017: istore          28
        //  1019: aload           29
        //  1021: invokevirtual   net/minecraft/entity/EntityLivingBase.getMaxHealth:()F
        //  1024: fstore          30
        //  1026: fload           30
        //  1028: ldc_w           0.5
        //  1031: fadd           
        //  1032: f2i            
        //  1033: iconst_2       
        //  1034: idiv           
        //  1035: istore          25
        //  1037: iconst_0       
        //  1038: bipush          30
        //  1040: if_icmple       1043
        //  1043: iload           13
        //  1045: istore          26
        //  1047: goto            1153
        //  1050: iconst_0       
        //  1051: bipush          10
        //  1053: invokestatic    java/lang/Math.min:(II)I
        //  1056: istore          32
        //  1058: iconst_0       
        //  1059: iload           32
        //  1061: isub           
        //  1062: istore          25
        //  1064: goto            1141
        //  1067: iconst_0       
        //  1068: ifeq            1071
        //  1071: iload           12
        //  1073: iconst_0       
        //  1074: isub           
        //  1075: bipush          9
        //  1077: isub           
        //  1078: istore          36
        //  1080: aload_0        
        //  1081: iload           36
        //  1083: iload           26
        //  1085: bipush          61
        //  1087: bipush          9
        //  1089: bipush          9
        //  1091: bipush          9
        //  1093: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //  1096: iconst_1       
        //  1097: iconst_5       
        //  1098: if_icmpge       1117
        //  1101: aload_0        
        //  1102: iload           36
        //  1104: iload           26
        //  1106: bipush          88
        //  1108: bipush          9
        //  1110: bipush          9
        //  1112: bipush          9
        //  1114: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //  1117: iconst_1       
        //  1118: iconst_5       
        //  1119: if_icmpne       1138
        //  1122: aload_0        
        //  1123: iload           36
        //  1125: iload           26
        //  1127: bipush          97
        //  1129: bipush          9
        //  1131: bipush          9
        //  1133: bipush          9
        //  1135: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //  1138: iinc            33, 1
        //  1141: iconst_0       
        //  1142: iload           32
        //  1144: if_icmplt       1067
        //  1147: iinc            26, -10
        //  1150: iinc            31, 20
        //  1153: iconst_0       
        //  1154: ifgt            1050
        //  1157: aload_0        
        //  1158: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //  1161: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1164: ldc_w           "air"
        //  1167: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1170: aload_2        
        //  1171: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //  1174: invokevirtual   net/minecraft/entity/player/EntityPlayer.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //  1177: ifeq            1283
        //  1180: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1183: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getAir:()I
        //  1186: istore          23
        //  1188: bipush          -2
        //  1190: i2d            
        //  1191: ldc2_w          10.0
        //  1194: dmul           
        //  1195: ldc2_w          300.0
        //  1198: ddiv           
        //  1199: invokestatic    net/minecraft/util/MathHelper.ceiling_double_int:(D)I
        //  1202: istore          28
        //  1204: iconst_0       
        //  1205: i2d            
        //  1206: ldc2_w          10.0
        //  1209: dmul           
        //  1210: ldc2_w          300.0
        //  1213: ddiv           
        //  1214: invokestatic    net/minecraft/util/MathHelper.ceiling_double_int:(D)I
        //  1217: iconst_5       
        //  1218: isub           
        //  1219: istore          24
        //  1221: goto            1277
        //  1224: iconst_0       
        //  1225: iconst_5       
        //  1226: if_icmpge       1253
        //  1229: aload_0        
        //  1230: iload           12
        //  1232: iconst_0       
        //  1233: isub           
        //  1234: bipush          9
        //  1236: isub           
        //  1237: iload           18
        //  1239: bipush          16
        //  1241: bipush          18
        //  1243: bipush          9
        //  1245: bipush          9
        //  1247: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //  1250: goto            1274
        //  1253: aload_0        
        //  1254: iload           12
        //  1256: iconst_0       
        //  1257: isub           
        //  1258: bipush          9
        //  1260: isub           
        //  1261: iload           18
        //  1263: bipush          25
        //  1265: bipush          18
        //  1267: bipush          9
        //  1269: bipush          9
        //  1271: invokevirtual   net/minecraft/client/gui/GuiIngame.drawTexturedModalRect:(IIIIII)V
        //  1274: iinc            25, 1
        //  1277: iconst_0       
        //  1278: bipush          21
        //  1280: if_icmplt       1224
        //  1283: aload_0        
        //  1284: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //  1287: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1290: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1293: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            final int scaledWidth = ScaledResolution.getScaledWidth();
            final int n = scaledWidth / 2 - 1;
            final int n2 = (int)(BossStatus.healthScale * 183);
            this.drawTexturedModalRect(n, 12, 0, 74, 182, 5);
            this.drawTexturedModalRect(n, 12, 0, 74, 182, 5);
            if (n2 > 0) {
                this.drawTexturedModalRect(n, 12, 0, 79, n2, 5);
            }
            final String bossName = BossStatus.bossName;
            if (Config.isCustomColors()) {
                CustomColors.getBossTextColor(16777215);
            }
            this.func_175179_f().func_175063_a(bossName, (float)(scaledWidth / 2 - this.func_175179_f().getStringWidth(bossName) / 2), 2, 16777215);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        }
    }
    
    private void func_180476_e(final ScaledResolution scaledResolution) {
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.pumpkinBlurTexPath);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, ScaledResolution.getScaledHeight(), -90.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), 0.0, -90.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void func_180480_a(float clamp_float, final ScaledResolution scaledResolution) {
        if (Config.isVignetteEnabled()) {
            clamp_float = 1.0f - clamp_float;
            clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 1.0f);
            final WorldBorder worldBorder = Minecraft.theWorld.getWorldBorder();
            final float n = (float)worldBorder.getClosestDistance(Minecraft.thePlayer);
            final double max = Math.max(worldBorder.getWarningDistance(), Math.min(worldBorder.func_177749_o() * worldBorder.getWarningTime() * 1000.0, Math.abs(worldBorder.getTargetSize() - worldBorder.getDiameter())));
            float n2;
            if (n < max) {
                n2 = 1.0f - (float)(n / max);
            }
            else {
                n2 = 0.0f;
            }
            this.prevVignetteBrightness += (float)((clamp_float - this.prevVignetteBrightness) * 0.01);
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
            if (n2 > 0.0f) {
                GlStateManager.color(0.0f, n2, n2, 1.0f);
            }
            else {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(GuiIngame.vignetteTexPath);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(0.0, ScaledResolution.getScaledHeight(), -90.0, 0.0, 1.0);
            worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0, 1.0, 1.0);
            worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), 0.0, -90.0, 1.0, 0.0);
            worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
            instance.draw();
            GlStateManager.depthMask(true);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
    }
    
    private void func_180474_b(float n, final ScaledResolution scaledResolution) {
        if (n < 1.0f) {
            n *= n;
            n *= n;
            n = n * 0.8f + 0.2f;
        }
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final TextureAtlasSprite func_178122_a = this.mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(Blocks.portal.getDefaultState());
        final float minU = func_178122_a.getMinU();
        final float minV = func_178122_a.getMinV();
        final float maxU = func_178122_a.getMaxU();
        final float maxV = func_178122_a.getMaxV();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, ScaledResolution.getScaledHeight(), -90.0, minU, maxV);
        worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0, maxU, maxV);
        worldRenderer.addVertexWithUV(ScaledResolution.getScaledWidth(), 0.0, -90.0, maxU, minV);
        worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, minU, minV);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void func_175184_a(final int p0, final int p1, final int p2, final float p3, final EntityPlayer p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     5: getfield        net/minecraft/entity/player/InventoryPlayer.mainInventory:[Lnet/minecraft/item/ItemStack;
        //     8: iload_1        
        //     9: aaload         
        //    10: astore          6
        //    12: aload           6
        //    14: ifnull          118
        //    17: aload           6
        //    19: getfield        net/minecraft/item/ItemStack.animationsToGo:I
        //    22: i2f            
        //    23: fload           4
        //    25: fsub           
        //    26: fstore          7
        //    28: fload           7
        //    30: fconst_0       
        //    31: fcmpl          
        //    32: ifle            89
        //    35: fconst_1       
        //    36: fload           7
        //    38: ldc_w           5.0
        //    41: fdiv           
        //    42: fadd           
        //    43: fstore          8
        //    45: iload_2        
        //    46: bipush          8
        //    48: iadd           
        //    49: i2f            
        //    50: iload_3        
        //    51: bipush          12
        //    53: iadd           
        //    54: i2f            
        //    55: fconst_0       
        //    56: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    59: fconst_1       
        //    60: fload           8
        //    62: fdiv           
        //    63: fload           8
        //    65: fconst_1       
        //    66: fadd           
        //    67: fconst_2       
        //    68: fdiv           
        //    69: fconst_1       
        //    70: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //    73: iload_2        
        //    74: bipush          8
        //    76: iadd           
        //    77: ineg           
        //    78: i2f            
        //    79: iload_3        
        //    80: bipush          12
        //    82: iadd           
        //    83: ineg           
        //    84: i2f            
        //    85: fconst_0       
        //    86: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    89: aload_0        
        //    90: getfield        net/minecraft/client/gui/GuiIngame.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //    93: aload           6
        //    95: iload_2        
        //    96: iload_3        
        //    97: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.func_180450_b:(Lnet/minecraft/item/ItemStack;II)V
        //   100: fload           7
        //   102: fconst_0       
        //   103: fcmpl          
        //   104: aload_0        
        //   105: getfield        net/minecraft/client/gui/GuiIngame.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //   108: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   111: aload           6
        //   113: iload_2        
        //   114: iload_3        
        //   115: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.func_175030_a:(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;II)V
        //   118: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0118 (coming from #0115).
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
    
    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        if (this.field_175195_w > 0) {
            --this.field_175195_w;
            if (this.field_175195_w <= 0) {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }
        ++this.updateCounter;
        this.streamIndicator.func_152439_a();
        if (Minecraft.thePlayer != null) {
            final ItemStack currentItem = Minecraft.thePlayer.inventory.getCurrentItem();
            if (currentItem == null) {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && currentItem.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(currentItem, this.highlightingItemStack) && (currentItem.isItemStackDamageable() || currentItem.getMetadata() == this.highlightingItemStack.getMetadata())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            }
            else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = currentItem;
        }
    }
    
    public void setRecordPlayingMessage(final String s) {
        this.setRecordPlaying(I18n.format("record.nowPlaying", s), true);
    }
    
    public void setRecordPlaying(final String recordPlaying, final boolean recordIsPlaying) {
        this.recordPlaying = recordPlaying;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = recordIsPlaying;
    }
    
    public void func_175178_a(final String field_175201_x, final String field_175200_y, final int field_175199_z, final int field_175192_A, final int field_175193_B) {
        if (field_175201_x == null && field_175200_y == null && field_175199_z < 0 && field_175192_A < 0 && field_175193_B < 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
            this.field_175195_w = 0;
        }
        else if (field_175201_x != null) {
            this.field_175201_x = field_175201_x;
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
        }
        else if (field_175200_y != null) {
            this.field_175200_y = field_175200_y;
        }
        else {
            if (field_175199_z >= 0) {
                this.field_175199_z = field_175199_z;
            }
            if (field_175192_A >= 0) {
                this.field_175192_A = field_175192_A;
            }
            if (field_175193_B >= 0) {
                this.field_175193_B = field_175193_B;
            }
            if (this.field_175195_w > 0) {
                this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
            }
        }
    }
    
    public void func_175188_a(final IChatComponent chatComponent, final boolean b) {
        this.setRecordPlaying(chatComponent.getUnformattedText(), b);
    }
    
    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }
    
    public int getUpdateCounter() {
        return this.updateCounter;
    }
    
    public FontRenderer func_175179_f() {
        return Minecraft.fontRendererObj;
    }
    
    public GuiSpectator func_175187_g() {
        return this.field_175197_u;
    }
    
    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }
    
    private static boolean lambda$0(final Object o) {
        return o instanceof EntityPlayer;
    }
}
