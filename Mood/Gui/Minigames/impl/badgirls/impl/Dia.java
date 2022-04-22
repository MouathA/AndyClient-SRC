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

public class Dia extends GuiScreen
{
    private final List XDDD;
    private final Hirdetesek Ads;
    public static boolean Informaciok;
    protected GuiScreen prevScreen;
    public static float X;
    public static float Y;
    ScaledResolution sr;
    
    static {
        Dia.Informaciok = false;
    }
    
    public Dia(final GuiScreen prevScreen) {
        this.XDDD = new ArrayList();
        this.Ads = new Hirdetesek();
        this.XDDD.add("www.rosszlanyok.hu");
        this.XDDD.add("Hey, ne verd ki! Szexelj k\u00f6zeli anyuk\u00e1kkal");
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new UIButtons(1, Dia.width / 2 + 4 + 155, Dia.height - 28, 20, 20, ">"));
        this.buttonList.add(new UIButtons(10, Dia.width / 2 - 130, Dia.height - 28, 20, 20, "<"));
        this.buttonList.add(new Gomb(2, Dia.width - 103, 102, 100, 40, "Hivjuk is fel", "MooDTool/badminecraftgirls/Kellekek/telefon.png"));
        this.buttonList.add(new Gomb(3, Dia.width - 103, 180, 100, 40, "Kos\u00e1rba tesz", "MooDTool/badminecraftgirls/Kellekek/bevasarlokocsi.png"));
        this.buttonList.add(new Gomb(4, Dia.width - 80, 252, 50, 40, "§4§lInf\u00f3k r\u00f3lam <3", "MooDTool/badminecraftgirls/Kellekek/informaciok.png"));
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 4) {
            Dia.Informaciok = true;
        }
        final int id = guiButton.id;
        final int id2 = guiButton.id;
        if (guiButton.id == 1) {
            Dia.mc.displayGuiScreen(new Niki(this));
            Dia.Informaciok = false;
        }
        if (guiButton.id == 10) {
            Dia.mc.displayGuiScreen(this.prevScreen);
            Dia.Informaciok = false;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            Dia.mc.displayGuiScreen(this.prevScreen);
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
        Dia.mc.getTextureManager().bindTexture(new ResourceLocation("MooDTool/badminecraftgirls/Rosszlanyokhattere.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - Dia.X + ScaledResolution.getScaledWidth(), -9.0f - Dia.Y / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1040.0f, 597.0f, 640.0f, 597.0f);
        RenderHelper.renderImage("MooDTool/badminecraftgirls/Girls/Dia.jpg", Dia.width / 2 - 50, 100, 125, 200);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Dia.mc, Dia.mc.displayWidth, Dia.mc.displayHeight);
        for (final String s : this.XDDD) {
            GL11.glScaled(2.0, 2.0, 2.0);
            Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.EzLeszAWatermark()) + s, Dia.width / 4, 0, -1);
            final int n4;
            n4 += 10;
        }
        this.Ads.render(Dia.width, Dia.height, n);
        Gui.drawRect(Dia.width / 6 - 104, Dia.height / 2 + 42, Dia.width / 9 + 105, Dia.height / 4 + 75 + 188, Integer.MIN_VALUE);
        this.drawString(this.fontRendererObj, "§r §nInform\u00e1ci\u00f3k:", 2, Dia.height - 130, -1);
        if (Dia.Informaciok) {
            this.drawString(this.fontRendererObj, " 21 \u00e9ves", 2, Dia.height - 115, -1);
            this.drawString(this.fontRendererObj, " 160 cm", 2, Dia.height - 105, -1);
            this.drawString(this.fontRendererObj, " 53 kg, 80 mell", 2, Dia.height - 95, -1);
            this.drawString(this.fontRendererObj, " V\u00e9kony alkat, Borotv\u00e1lt punci", 2, Dia.height - 85, -1);
            this.drawString(this.fontRendererObj, " Hossz\u00fa, Sz\u0151k\u00e9sbarna haj, (...)", 2, Dia.height - 75, -1);
            this.drawString(this.fontRendererObj, " Eredeti cici", 2, Dia.height - 65, -1);
            this.drawString(this.fontRendererObj, "§r §e§oTelefonsz\u00e1m: §e§o§n+36308750851", 2, Dia.height - 55, -1);
        }
        super.drawScreen(n, n2, n3);
    }
}
