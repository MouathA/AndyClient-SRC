package Mood.Gui.Minigames.impl.badgirls.impl;

import Mood.Gui.Minigames.impl.badgirls.Ads.*;
import Mood.UIButtons.*;
import Mood.Gui.Minigames.impl.badgirls.Buttons.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class Niki extends GuiScreen
{
    private final List XDDD;
    private final Hirdetesek Ads;
    public static boolean Informaciok;
    protected GuiScreen prevScreen;
    public static float X;
    public static float Y;
    ScaledResolution sr;
    
    static {
        Niki.Informaciok = false;
    }
    
    public Niki(final GuiScreen prevScreen) {
        this.XDDD = new ArrayList();
        this.Ads = new Hirdetesek();
        this.XDDD.add("www.rosszlanyok.hu");
        this.XDDD.add("Hey, ne verd ki! Szexelj k\u00f6zeli anyuk\u00e1kkal");
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new UIButtons(1, Niki.width / 2 + 4 + 155, Niki.height - 28, 20, 20, ">"));
        this.buttonList.add(new UIButtons(10, Niki.width / 2 - 130, Niki.height - 28, 20, 20, "<"));
        this.buttonList.add(new Gomb(2, Niki.width - 103, 102, 100, 40, "Hivjuk is fel", "MooDTool/badminecraftgirls/Kellekek/telefon.png"));
        this.buttonList.add(new Gomb(3, Niki.width - 103, 180, 100, 40, "Kos\u00e1rba tesz", "MooDTool/badminecraftgirls/Kellekek/bevasarlokocsi.png"));
        this.buttonList.add(new Gomb(4, Niki.width - 80, 252, 50, 40, "§4§lInf\u00f3k r\u00f3lam <3", "MooDTool/badminecraftgirls/Kellekek/informaciok.png"));
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 4) {
            Niki.Informaciok = true;
        }
        final int id = guiButton.id;
        final int id2 = guiButton.id;
        if (guiButton.id == 1) {
            Niki.mc.displayGuiScreen(new DreamGirls(this));
            Niki.Informaciok = false;
        }
        if (guiButton.id == 10) {
            Niki.mc.displayGuiScreen(this.prevScreen);
            Niki.Informaciok = false;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            Niki.mc.displayGuiScreen(this.prevScreen);
        }
    }
    
    public String EzLeszAWatermark() {
        switch ((int)(Minecraft.getSystemTime() / 500L % 4L)) {
            default: {
                return "§a";
            }
            case 1:
            case 3: {
                return "§e";
            }
            case 2: {
                return "§b";
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Niki.mc.getTextureManager().bindTexture(new ResourceLocation("MooDTool/badminecraftgirls/Rosszlanyokhattere.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - Niki.X + ScaledResolution.getScaledWidth(), -9.0f - Niki.Y / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1040.0f, 597.0f, 640.0f, 597.0f);
        RenderHelper.renderImage("MooDTool/badminecraftgirls/Girls/Niki.jpg", Niki.width / 2 - 50, 100, 125, 200);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Niki.mc, Niki.mc.displayWidth, Niki.mc.displayHeight);
        for (final String s : this.XDDD) {
            GL11.glScaled(2.0, 2.0, 2.0);
            Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.EzLeszAWatermark()) + s, Niki.width / 4, 0, -1);
            final int n4;
            n4 += 10;
        }
        this.Ads.render(Niki.width, Niki.height, n);
        Gui.drawRect(Niki.width / 6 - 104, Niki.height / 2 + 42, Niki.width / 9 + 105, Niki.height / 4 + 75 + 188, Integer.MIN_VALUE);
        this.drawString(this.fontRendererObj, "§r §nInform\u00e1ci\u00f3k:", 2, Niki.height - 130, -1);
        if (Niki.Informaciok) {
            this.drawString(this.fontRendererObj, " 19 \u00e9ves", 2, Niki.height - 115, -1);
            this.drawString(this.fontRendererObj, " 163 cm", 2, Niki.height - 105, -1);
            this.drawString(this.fontRendererObj, " 65 kg, 85 mell", 2, Niki.height - 95, -1);
            this.drawString(this.fontRendererObj, " Norm\u00e1l alkat, Borotv\u00e1lt punci", 2, Niki.height - 85, -1);
            this.drawString(this.fontRendererObj, " F\u00e9lhossz\u00fa, Barna haj, (...)", 2, Niki.height - 75, -1);
            this.drawString(this.fontRendererObj, " Tetov\u00e1l\u00e1som van, Piercingem (...)", 2, Niki.height - 65, -1);
            this.drawString(this.fontRendererObj, "§r §e§oTelefonsz\u00e1m: §e§o§n+36/70/737-89-92", 2, Niki.height - 55, -1);
        }
        super.drawScreen(n, n2, n3);
    }
}
