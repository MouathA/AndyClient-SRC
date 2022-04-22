package Mood.Gui.Minigames.impl.badgirls.impl;

import Mood.Gui.Minigames.impl.badgirls.Ads.*;
import Mood.UIButtons.*;
import Mood.Gui.Minigames.impl.badgirls.Buttons.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class Juzsomg extends GuiScreen
{
    private final List XDDD;
    private final Hirdetesek Ads;
    public static boolean Informaciok;
    protected GuiScreen prevScreen;
    public static float X;
    public static float Y;
    ScaledResolution sr;
    
    static {
        Juzsomg.Informaciok = false;
    }
    
    public Juzsomg(final GuiScreen prevScreen) {
        this.XDDD = new ArrayList();
        this.Ads = new Hirdetesek();
        this.XDDD.add("www.WTF.com");
        this.XDDD.add("Nyomasd a sz\u00e1j\u00e1ba!");
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new UIButtons(1, Juzsomg.width / 2 + 4 + 155, Juzsomg.height - 28, 20, 20, ">"));
        this.buttonList.add(new UIButtons(10, Juzsomg.width / 2 - 130, Juzsomg.height - 28, 20, 20, "<"));
        this.buttonList.add(new Gomb(2, Juzsomg.width - 103, 102, 100, 40, "Hivjuk is fel", "MooDTool/badminecraftgirls/Kellekek/telefon.png"));
        this.buttonList.add(new Gomb(3, Juzsomg.width - 103, 180, 100, 40, "Kos\u00e1rba tesz", "MooDTool/badminecraftgirls/Kellekek/bevasarlokocsi.png"));
        this.buttonList.add(new Gomb(4, Juzsomg.width - 80, 252, 50, 40, "§4§lInf\u00f3k r\u00f3lam <3", "MooDTool/badminecraftgirls/Kellekek/informaciok.png"));
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 4) {
            Juzsomg.Informaciok = true;
        }
        final int id = guiButton.id;
        final int id2 = guiButton.id;
        if (guiButton.id == 1) {
            PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 5.0f);
            Juzsomg.Informaciok = false;
        }
        if (guiButton.id == 10) {
            Juzsomg.mc.displayGuiScreen(this.prevScreen);
            Juzsomg.Informaciok = false;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            Juzsomg.mc.displayGuiScreen(this.prevScreen);
        }
    }
    
    public String EzLeszAWatermark() {
        switch ((int)(Minecraft.getSystemTime() / 500L % 4L)) {
            default: {
                return "§4";
            }
            case 1:
            case 3: {
                return "§c";
            }
            case 2: {
                return "§6";
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Juzsomg.mc.getTextureManager().bindTexture(new ResourceLocation("MooDTool/badminecraftgirls/juzsohatter.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - Juzsomg.X + ScaledResolution.getScaledWidth(), -9.0f - Juzsomg.Y / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1040.0f, 597.0f, 640.0f, 597.0f);
        RenderHelper.renderImage("MooDTool/badminecraftgirls/Girls/Juzsomg.jpg", Juzsomg.width / 2 - 50, 100, 125, 200);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Juzsomg.mc, Juzsomg.mc.displayWidth, Juzsomg.mc.displayHeight);
        for (final String s : this.XDDD) {
            GL11.glScaled(2.0, 2.0, 2.0);
            Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.EzLeszAWatermark()) + s, Juzsomg.width / 4, 0, -1);
            final int n4;
            n4 += 10;
        }
        this.Ads.render(Juzsomg.width, Juzsomg.height, n);
        Gui.drawRect(Juzsomg.width / 6 - 104, Juzsomg.height / 2 + 42, Juzsomg.width / 9 + 105, Juzsomg.height / 4 + 75 + 188, Integer.MIN_VALUE);
        this.drawString(this.fontRendererObj, "§r §nInform\u00e1ci\u00f3k:", 2, Juzsomg.height - 130, -1);
        if (Juzsomg.Informaciok) {
            this.drawString(this.fontRendererObj, " §cSzeretem a lucskos faszt", 2, Juzsomg.height - 115, -1);
            this.drawString(this.fontRendererObj, " §c\u00dagy d\u00f6nt\u00f6ttem kipr\u00f3b\u00e1lom", 2, Juzsomg.height - 105, -1);
            this.drawString(this.fontRendererObj, " §cmilyen is ez a mulats\u00e1g. ;)", 2, Juzsomg.height - 95, -1);
            this.drawString(this.fontRendererObj, " §cBorotv\u00e1lt a puncim", 2, Juzsomg.height - 85, -1);
            this.drawString(this.fontRendererObj, " §cPls tedd bel\u00e9m!", 2, Juzsomg.height - 75, -1);
            this.drawString(this.fontRendererObj, " §4SMS-T nem fogadok el!", 2, Juzsomg.height - 65, -1);
            this.drawString(this.fontRendererObj, "§r §e§oTelefonsz\u00e1m: §e§o§n+www.melytorok.hu", 2, Juzsomg.height - 55, -1);
        }
        super.drawScreen(n, n2, n3);
    }
}
