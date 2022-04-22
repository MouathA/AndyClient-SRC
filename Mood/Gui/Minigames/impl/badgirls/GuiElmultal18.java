package Mood.Gui.Minigames.impl.badgirls;

import Mood.Gui.Minigames.impl.badgirls.Buttons.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class GuiElmultal18 extends GuiScreen
{
    protected GuiScreen prevScreen;
    public static float X;
    public static float Y;
    ScaledResolution sr;
    
    public GuiElmultal18(final GuiScreen prevScreen) {
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new VorosGomb(1, GuiElmultal18.width / 2 - 100, GuiElmultal18.height / 4 + 70, 200, 20, "Elm\u00falt\u00e1l (18) ?"));
        this.buttonList.add(new VorosGomb(2, GuiElmultal18.width / 2 - 100, GuiElmultal18.height / 4 + 70 + 20 + 4, 200, 20, "B\u00f6ng\u00e9sz\u00e9si Adatok ment\u00e9se"));
        this.buttonList.add(new VorosGomb(3, GuiElmultal18.width / 2 - 100, GuiElmultal18.height / 4 + 70 + 20 + 28, 200, 20, "M\u00e9gse"));
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            GuiElmultal18.mc.displayGuiScreen(new GuiBadGirls(this));
        }
        else {
            GuiElmultal18.mc.displayGuiScreen(this.prevScreen);
        }
        if (guiButton.id == 2) {
            Minecraft.getMinecraft().shutdownMinecraftApplet();
        }
        else {
            final int id = guiButton.id;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiElmultal18.mc.displayGuiScreen(this.prevScreen);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GuiElmultal18.mc.getTextureManager().bindTexture(new ResourceLocation("MooDTool/badminecraftgirls/FigyelemFelhivo.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiElmultal18.X + ScaledResolution.getScaledWidth(), -9.0f - GuiElmultal18.Y / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1040.0f, 1097.0f, 1400.0f, 1097.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiElmultal18.mc, GuiElmultal18.mc.displayWidth, GuiElmultal18.mc.displayHeight);
        super.drawScreen(n, n2, n3);
    }
}
