package net.minecraft.client.gui;

import java.util.concurrent.atomic.*;
import net.minecraft.client.renderer.texture.*;
import Mood.Matrix.DefaultParticles.*;
import Mood.Designs.MainMenuDes.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.*;
import java.io.*;
import java.util.*;
import Mood.Designs.*;
import Mood.Gui.Minigames.impl.badgirls.*;
import net.minecraft.util.*;
import Mood.Gui.*;
import net.minecraft.realms.*;
import java.net.*;
import net.minecraft.world.storage.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger field_175373_f;
    private static final Logger logger;
    private static final Random field_175374_h;
    public static float modeSlide;
    private float updateCounter;
    public static double introTrans;
    private String splashText;
    private MButton buttonResetDemo;
    public static boolean originalNames;
    private int panoramaTimer;
    public static double namesFadeOutTimer;
    private DynamicTexture viewportTexture;
    private boolean field_175375_v;
    private final Object field_104025_t;
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    private static final ResourceLocation splashTexts;
    private static final ResourceLocation[] titlePanoramaPaths;
    public static final String field_96138_a;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation field_110351_G;
    private GuiButton field_175372_K;
    public static float animatedMouseX;
    public static float animatedMouseY;
    public ParticleEngine pe;
    public float zoom1;
    public float zoom2;
    public float zoom3;
    public float zoom4;
    public float zoom5;
    protected GuiTextFields userField;
    
    static {
        field_175373_f = new AtomicInteger(0);
        logger = LogManager.getLogger();
        field_175374_h = new Random();
        splashTexts = new ResourceLocation("texts/splashes.txt");
        titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
        field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }
    
    public GuiMainMenu() {
        this.field_175375_v = true;
        this.field_104025_t = new Object();
        this.pe = new ParticleEngine();
        this.zoom1 = 1.0f;
        this.zoom2 = 1.0f;
        this.zoom3 = 1.0f;
        this.zoom4 = 1.0f;
        this.zoom5 = 1.0f;
        this.field_146972_A = GuiMainMenu.field_96138_a;
        this.splashText = "missingno";
        final ArrayList arrayList = Lists.newArrayList();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(GuiMainMenu.splashTexts).getInputStream(), Charsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String trim = line.trim();
            if (!trim.isEmpty()) {
                arrayList.add(trim);
            }
        }
        if (!arrayList.isEmpty()) {
            do {
                this.splashText = arrayList.get(GuiMainMenu.field_175374_h.nextInt(arrayList.size()));
            } while (this.splashText.hashCode() == 125780783);
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        this.updateCounter = GuiMainMenu.field_175374_h.nextFloat();
        this.field_92025_p = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
            this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }
    
    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.userField.textboxKeyTyped(c, n);
        if (c == '\t') {
            if (this.userField.isFocused()) {
                this.userField.setFocused(false);
            }
            else {
                this.userField.setFocused(true);
            }
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        final ScaledResolution scaledResolution = new ScaledResolution(GuiMainMenu.mc, GuiMainMenu.mc.displayWidth, GuiMainMenu.mc.displayHeight);
        GuiMainMenu.introTrans = ScaledResolution.getScaledHeight();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = GuiMainMenu.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if (instance.get(2) + 1 == 11 && instance.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        }
        else if (instance.get(2) + 1 == 6 && instance.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        }
        else if (instance.get(2) + 1 == 12 && instance.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        }
        else if (instance.get(2) + 1 == 1 && instance.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        else if (instance.get(2) + 1 == 10 && instance.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        final int n = GuiMainMenu.height / 4 + 48;
        if (GuiMainMenu.mc.isDemo()) {
            this.addDemoButtons(n, 24);
        }
        else {
            this.addSingleplayerMultiplayerButtons(n, 24);
        }
        this.userField = new GuiTextFields(10, Minecraft.fontRendererObj, GuiMainMenu.width / 2 - 100, 233, 200, 13);
        this.buttonList.add(new MButton(0, GuiMainMenu.width / 2 - 100, n + 60 + 12, 98, 20, I18n.format("Options", new Object[0])));
        this.buttonList.add(new MButton(4, GuiMainMenu.width / 2 + 2, n + 60 + 12, 98, 20, I18n.format("Quit Game", new Object[0])));
        this.buttonList.add(new MButton(1337, GuiMainMenu.width / 2 + 2, n + 104 + 11, 98, 20, I18n.format("Session Login", new Object[0])));
        final Object field_104025_t = this.field_104025_t;
        // monitorenter(field_104025_t2 = this.field_104025_t)
        this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
        this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
        final int max = Math.max(this.field_92023_s, this.field_92024_r);
        this.field_92022_t = (GuiMainMenu.width - max) / 2;
        this.field_92021_u = 0;
        this.field_92020_v = this.field_92022_t + max;
        this.field_92019_w = this.field_92021_u + 24;
    }
    // monitorexit(field_104025_t2)
    
    private void addSingleplayerMultiplayerButtons(final int n, final int n2) {
        this.buttonList.add(new MButton(1, GuiMainMenu.width / 2 - 100, n, I18n.format("Singleplayer", new Object[0])));
        this.buttonList.add(new MButton(2, GuiMainMenu.width / 2 - 100, n + n2 * 1, 98, 20, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new MButton(5, GuiMainMenu.width / 2 + 2, n + n2 * 1, 98, 20, "Server History"));
        this.buttonList.add(new MButton(55, GuiMainMenu.width / 2 - 100, n + n2 * 2, 98, 20, "Tools"));
        this.buttonList.add(new MButton(56, GuiMainMenu.width / 2 + 2, n + n2 * 2, 98, 20, "Design"));
    }
    
    private void addDemoButtons(final int n, final int n2) {
        this.buttonList.add(new MButton(11, GuiMainMenu.width / 2 - 100, n, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new MButton(12, GuiMainMenu.width / 2 - 100, n + n2 * 1, I18n.format("menu.resetdemo", new Object[0])));
        if (GuiMainMenu.mc.getSaveLoader().getWorldInfo("Demo_World") == null) {
            this.buttonResetDemo.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            GuiMainMenu.mc.displayGuiScreen(new GuiOptions(this, GuiMainMenu.mc.gameSettings));
        }
        if (guiButton.id == 56) {
            GuiMainMenu.mc.displayGuiScreen(new Designs(this));
        }
        if (guiButton.id == 1) {
            GuiMainMenu.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (guiButton.id == 2) {
            GuiMainMenu.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (guiButton.id == 5) {
            GuiMainMenu.mc.displayGuiScreen(new GuiElmultal18(this));
        }
        if (guiButton.id == 4) {
            GuiMainMenu.mc.shutdown();
        }
        final int id = guiButton.id;
        if (guiButton.id == 55) {
            GuiMainMenu.mc.displayGuiScreen(new GuiToolsMenu());
        }
        if (guiButton.id == 1337 && !this.userField.getText().equals("")) {
            Minecraft.session = new Session(this.userField.getText(), Minecraft.session.getSessionID(), this.userField.getText(), "legacy");
        }
        if (guiButton.id == 14) {
            GuiMainMenu.mc.displayGuiScreen(new GuiUUIDSpoof(this));
        }
        if (guiButton.id == 12) {
            final WorldInfo worldInfo = GuiMainMenu.mc.getSaveLoader().getWorldInfo("Demo_World");
            if (worldInfo != null) {
                GuiMainMenu.mc.displayGuiScreen(GuiSelectWorld.func_152129_a(this, worldInfo.getWorldName(), 12));
            }
        }
    }
    
    private void switchToRealms() {
        new RealmsBridge().switchToRealms(this);
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b && n == 12) {
            final ISaveFormat saveLoader = GuiMainMenu.mc.getSaveLoader();
            saveLoader.flushCache();
            saveLoader.deleteWorldDirectory("Demo_World");
            GuiMainMenu.mc.displayGuiScreen(this);
        }
        else if (n == 13) {
            if (b) {
                final Class<?> forName = Class.forName("java.awt.Desktop");
                forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new URI(this.field_104024_v));
            }
            GuiMainMenu.mc.displayGuiScreen(this);
        }
    }
    
    public static void Browse(final String s) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(s));
        }
    }
    
    private void drawPanorama(final int n, final int n2, final float n3) {
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.matrixMode(5889);
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.matrixMode(5888);
        GlStateManager.depthMask(true);
    }
    
    private void rotateAndBlurSkybox(final float n) {
        GuiMainMenu.mc.getTextureManager().bindTexture(this.field_110351_G);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        final Tessellator instance = Tessellator.getInstance();
        instance.getWorldRenderer().startDrawingQuads();
        instance.draw();
        GlStateManager.colorMask(true, true, true, true);
    }
    
    private void renderSkybox(final int n, final int n2, final float n3) {
        GuiMainMenu.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(n, n2, n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        GuiMainMenu.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, GuiMainMenu.mc.displayWidth, GuiMainMenu.mc.displayHeight);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        final float n4 = (GuiMainMenu.width > GuiMainMenu.height) ? (120.0f / GuiMainMenu.width) : (120.0f / GuiMainMenu.height);
        final float n5 = GuiMainMenu.height * n4 / 256.0f;
        final float n6 = GuiMainMenu.width * n4 / 256.0f;
        worldRenderer.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        final int width = GuiMainMenu.width;
        final int height = GuiMainMenu.height;
        worldRenderer.addVertexWithUV(0.0, height, this.zLevel, 0.5f - n5, 0.5f + n6);
        worldRenderer.addVertexWithUV(width, height, this.zLevel, 0.5f - n5, 0.5f - n6);
        worldRenderer.addVertexWithUV(width, 0.0, this.zLevel, 0.5f + n5, 0.5f - n6);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5f + n5, 0.5f + n6);
        instance.draw();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (GuiMainMenu.introTrans > 0.0) {
            GuiMainMenu.introTrans -= GuiMainMenu.introTrans / 7.0;
        }
        Tessellator.getInstance().getWorldRenderer();
        final int n4 = GuiMainMenu.width / 2 - 2;
        GuiMainMenu.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiMainMenu.mc, GuiMainMenu.mc.displayWidth, GuiMainMenu.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiMainMenu.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - GuiMainMenu.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        final float n5 = -16 + ScaledResolution.getScaledWidth() / 2 - 144.5f + 8.0f;
        final float n6 = ScaledResolution.getScaledHeight() / 2 + 14.5f - 8.0f + 0.5f;
        final String s = "§7§o(Cracked) §rMinecraft§c 1.8";
        final String s2 = "§rCopyright§c Mojang AB";
        final String s3 = "§rDo not§c distribute!";
        this.drawString(this.fontRendererObj, "§cAndy§r Mod §7§o(Utility Client)", 8, 8, 16777215);
        this.drawString(this.fontRendererObj, "§cCopyright§r iTzMatthew1337", 8, 18, 16777215);
        this.drawString(this.fontRendererObj, "§cMinden jog§r fenntartva.", 8, 28, 16777215);
        this.drawString(this.fontRendererObj, s, GuiMainMenu.width - this.fontRendererObj.getStringWidth(s) - 8, 8, 16777215);
        this.drawString(this.fontRendererObj, s2, GuiMainMenu.width - this.fontRendererObj.getStringWidth(s2) - 8, 18, 16777215);
        this.drawString(this.fontRendererObj, s3, GuiMainMenu.width - this.fontRendererObj.getStringWidth(s3) - 8, 28, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "https://www.youtube.com/c/Cl3sTerMan", GuiMainMenu.width / 2, GuiMainMenu.height - 16, 16777215);
        this.pe.render(n3, n3);
        Gui.drawRect(GuiMainMenu.width / 2 - 105, GuiMainMenu.height / 4 + 42, GuiMainMenu.width / 2 + 105, GuiMainMenu.height / 4 + 75 + 113, 2147459111);
        Gui.drawRect(GuiMainMenu.width / 2 - 105, GuiMainMenu.height / 4 + 42, GuiMainMenu.width / 2 - 104, GuiMainMenu.height / 4 + 75 + 113, Color.DARK_GRAY.getRGB());
        Gui.drawRect(GuiMainMenu.width / 2 + 104, GuiMainMenu.height / 4 + 42, GuiMainMenu.width / 2 + 105, GuiMainMenu.height / 4 + 75 + 113, Color.DARK_GRAY.getRGB());
        Gui.drawRect(GuiMainMenu.width / 2 - 105, GuiMainMenu.height / 4 + 42, GuiMainMenu.width / 2 + 105, GuiMainMenu.height / 4 - 72 + 113, Color.DARK_GRAY.getRGB());
        Gui.drawRect(GuiMainMenu.width / 2 - 105, GuiMainMenu.height / 4 + 189, GuiMainMenu.width / 2 + 105, GuiMainMenu.height / 4 + 75 + 113, Color.DARK_GRAY.getRGB());
        Gui.drawRect(GuiMainMenu.width / 6 - 104, GuiMainMenu.height / 2 + 42, GuiMainMenu.width / 9 + 105, GuiMainMenu.height / 4 + 75 + 188, Integer.MIN_VALUE);
        this.userField.drawTextBox();
        Gui.drawCenteredString(this.fontRendererObj, "§n§lCHANGELOG", GuiMainMenu.width / 8, GuiMainMenu.height - 130, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6    Sexy Main Menu", GuiMainMenu.width / 16, GuiMainMenu.height - 110, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6Modded Gui ", GuiMainMenu.width / 16, GuiMainMenu.height - 100, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6          Hacker Items Added", GuiMainMenu.width / 16, GuiMainMenu.height - 90, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6ExploitFixer", GuiMainMenu.width / 16, GuiMainMenu.height - 80, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6 OP-Sign Hack", GuiMainMenu.width / 16, GuiMainMenu.height - 70, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6       World Downloader", GuiMainMenu.width / 16, GuiMainMenu.height - 60, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6              Player Session Stealer", GuiMainMenu.width / 16, GuiMainMenu.height - 50, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6      Version Switcher", GuiMainMenu.width / 16, GuiMainMenu.height - 40, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6     LabyMod Fucker", GuiMainMenu.width / 16, GuiMainMenu.height - 30, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§6      Legit UUID-Spoof", GuiMainMenu.width / 16, GuiMainMenu.height - 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.userField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
        final Object field_104025_t = this.field_104025_t;
        // monitorenter(field_104025_t2 = this.field_104025_t)
        if (this.field_92025_p.length() > 0 && n >= this.field_92022_t && n <= this.field_92020_v && n2 >= this.field_92021_u && n2 <= this.field_92019_w) {
            final GuiConfirmOpenLink guiConfirmOpenLink = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
            guiConfirmOpenLink.disableSecurityWarning();
            GuiMainMenu.mc.displayGuiScreen(guiConfirmOpenLink);
        }
        // monitorexit(field_104025_t2)
        final ScaledResolution scaledResolution = new ScaledResolution(GuiMainMenu.mc, GuiMainMenu.mc.displayWidth, GuiMainMenu.mc.displayHeight);
    }
    
    public boolean isMouseHoveringRect1(final float n, final float n2, final float n3, final float n4, final int n5, final int n6) {
        return n5 >= n && n6 >= n2 && n5 <= n + n3 && n6 <= n2 + n4;
    }
    
    public boolean isMouseHoveringRect2(final float n, final float n2, final float n3, final float n4, final int n5, final int n6) {
        return n5 >= n && n6 >= n2 && n5 <= n3 && n6 <= n4;
    }
}
