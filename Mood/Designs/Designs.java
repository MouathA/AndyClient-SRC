package Mood.Designs;

import Mood.Designs.MainMenuDes.*;
import Mood.Matrix.DefaultParticles.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class Designs extends GuiScreen
{
    private MButton button;
    public static boolean White;
    public static boolean Classic;
    public static boolean HoveringText;
    public ParticleEngine pe;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    static {
        Designs.White = false;
        Designs.Classic = true;
        Designs.HoveringText = false;
    }
    
    public Designs(final GuiIngameMenu guiIngameMenu) {
        this.pe = new ParticleEngine();
    }
    
    public Designs(final GuiMainMenu guiMainMenu) {
        this.pe = new ParticleEngine();
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        final ScaledResolution scaledResolution = new ScaledResolution(Designs.mc);
        this.buttonList.add(new MButton(3, Designs.width / 2 - 145, Designs.height / 4 + 1 + 20 + 1, 100, 20, this.WhiteButton()));
        this.buttonList.add(new MButton(4, Designs.width / 2 - 145, Designs.height / 4 + 25 + 20 + 1, 100, 20, this.ClassicTextField()));
        this.buttonList.add(new MButton(5, Designs.width / 2 - 145, Designs.height / 4 + 49 + 20 + 1, 100, 20, "§cPremium"));
        this.drawDefaultBackground();
    }
    
    private String WhiteButton() {
        return Designs.White ? "White Button (On)" : "White Button (Off)";
    }
    
    private String ClassicTextField() {
        return Designs.Classic ? "Classic TextFd (On)" : "Classic TextFd (Off)";
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Designs.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Designs.mc, Designs.mc.displayWidth, Designs.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - Designs.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - Designs.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        final int id = guiButton.id;
        if (guiButton.id == 3) {
            Designs.White = !Designs.White;
            guiButton.displayString = this.WhiteButton();
        }
        if (guiButton.id == 4) {
            Designs.Classic = !Designs.Classic;
            guiButton.displayString = this.ClassicTextField();
        }
    }
}
