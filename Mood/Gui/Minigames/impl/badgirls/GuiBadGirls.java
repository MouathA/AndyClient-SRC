package Mood.Gui.Minigames.impl.badgirls;

import Mood.Gui.Minigames.impl.badgirls.Ads.*;
import Mood.UIButtons.*;
import Mood.Gui.Minigames.impl.badgirls.Buttons.*;
import java.io.*;
import Mood.Gui.Minigames.impl.badgirls.impl.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class GuiBadGirls extends GuiScreen
{
    private final List XDDD;
    private final Hirdetesek Ads;
    public static boolean Informaciok;
    protected GuiScreen prevScreen;
    public static float X;
    public static float Y;
    ScaledResolution sr;
    
    static {
        GuiBadGirls.Informaciok = false;
    }
    
    public GuiBadGirls(final GuiScreen prevScreen) {
        this.XDDD = new ArrayList();
        this.Ads = new Hirdetesek();
        this.XDDD.add("www.rosszlanyok.hu");
        this.XDDD.add("Hey, ne verd ki! Szexelj k\u00f6zeli anyuk\u00e1kkal");
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new UIButtons(1, GuiBadGirls.width / 2 + 4 + 155, GuiBadGirls.height - 28, 20, 20, ">"));
        this.buttonList.add(new UIButtons(10, GuiBadGirls.width / 2 - 130, GuiBadGirls.height - 28, 20, 20, "<"));
        this.buttonList.add(new Gomb(2, GuiBadGirls.width - 103, 102, 100, 40, "Hivjuk is fel", "MooDTool/badminecraftgirls/Kellekek/telefon.png"));
        this.buttonList.add(new Gomb(3, GuiBadGirls.width - 103, 180, 100, 40, "Kos\u00e1rba tesz", "MooDTool/badminecraftgirls/Kellekek/bevasarlokocsi.png"));
        this.buttonList.add(new Gomb(4, GuiBadGirls.width - 80, 252, 50, 40, "§4§lInf\u00f3k r\u00f3lam <3", "MooDTool/badminecraftgirls/Kellekek/informaciok.png"));
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 4) {
            GuiBadGirls.Informaciok = true;
        }
        final int id = guiButton.id;
        final int id2 = guiButton.id;
        if (guiButton.id == 1) {
            GuiBadGirls.mc.displayGuiScreen(new Dia(this));
            GuiBadGirls.Informaciok = false;
        }
        if (guiButton.id == 10) {
            GuiBadGirls.mc.displayGuiScreen(this.prevScreen);
            GuiBadGirls.Informaciok = false;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiBadGirls.mc.displayGuiScreen(this.prevScreen);
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
        GuiBadGirls.mc.getTextureManager().bindTexture(new ResourceLocation("MooDTool/badminecraftgirls/Rosszlanyokhattere.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiBadGirls.X + ScaledResolution.getScaledWidth(), -9.0f - GuiBadGirls.Y / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1040.0f, 597.0f, 640.0f, 597.0f);
        RenderHelper.renderImage("MooDTool/badminecraftgirls/Girls/Dorka.jpg", GuiBadGirls.width / 2 - 50, 100, 125, 200);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiBadGirls.mc, GuiBadGirls.mc.displayWidth, GuiBadGirls.mc.displayHeight);
        for (final String s : this.XDDD) {
            GL11.glScaled(2.0, 2.0, 2.0);
            Gui.drawCenteredString(this.fontRendererObj, String.valueOf(this.EzLeszAWatermark()) + s, GuiBadGirls.width / 4, 0, -1);
            final int n4;
            n4 += 10;
        }
        this.Ads.render(GuiBadGirls.width, GuiBadGirls.height, n);
        Gui.drawRect(GuiBadGirls.width / 6 - 104, GuiBadGirls.height / 2 + 42, GuiBadGirls.width / 9 + 105, GuiBadGirls.height / 4 + 75 + 188, Integer.MIN_VALUE);
        this.drawString(this.fontRendererObj, "§r §nInform\u00e1ci\u00f3k:", 2, GuiBadGirls.height - 130, -1);
        if (GuiBadGirls.Informaciok) {
            this.drawString(this.fontRendererObj, " Ennek a rosszl\u00e1nynak", 2, GuiBadGirls.height - 115, -1);
            this.drawString(this.fontRendererObj, " Nincsenek inform\u00e1ci\u00f3i.", 2, GuiBadGirls.height - 105, -1);
        }
        super.drawScreen(n, n2, n3);
    }
}
