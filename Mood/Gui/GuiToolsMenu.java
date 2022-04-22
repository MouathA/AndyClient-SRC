package Mood.Gui;

import Mood.Matrix.DefaultParticles.*;
import Mood.Designs.MainMenuDes.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiToolsMenu extends GuiScreen
{
    public ParticleEngine pe;
    protected GuiScreen prevScreen;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public GuiToolsMenu() {
        this.pe = new ParticleEngine();
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        final int n = GuiToolsMenu.height / 4 + 48;
        final int n2 = GuiToolsMenu.height / 4 + 48;
        final int n3 = GuiToolsMenu.height / 4 + 48;
        this.buttonList.add(new MButton(1, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3 - 50, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(2, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3 - 25, 100, 20, "Port Scanner"));
        this.buttonList.add(new MButton(3, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3, 100, 20, "Server Finder"));
        this.buttonList.add(new MButton(4, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3 + 25, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(5, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3 + 50, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(6, GuiToolsMenu.width / 2 - 205 + 100, n3 - 3 + 75, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(7, GuiToolsMenu.width / 2 + 6, n3 - 3 - 50, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(8, GuiToolsMenu.width / 2 + 6, n3 - 3 - 25, 100, 20, "Resolver"));
        this.buttonList.add(new MButton(12, GuiToolsMenu.width / 2 + 6, n3 - 3, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(10, GuiToolsMenu.width / 2 + 6, n3 - 3 + 25, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(11, GuiToolsMenu.width / 2 + 6, n3 - 3 + 50, 100, 20, "Discord WHSR"));
        this.buttonList.add(new MButton(12, GuiToolsMenu.width / 2 + 6, n3 - 3 + 75, 100, 20, "§cPremium"));
        this.buttonList.add(new MButton(0, GuiToolsMenu.width / 2 - 100, n3 - 3 + 75 + 35, 200, 20, "M\u00e9gse"));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GuiToolsMenu.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiToolsMenu.mc, GuiToolsMenu.mc.displayWidth, GuiToolsMenu.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiToolsMenu.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - GuiToolsMenu.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        final int n4 = GuiToolsMenu.height / 4 - 40;
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiToolsMenu.mc.displayGuiScreen(new GuiMainMenu());
        }
        super.keyTyped(c, n);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            GuiToolsMenu.mc.displayGuiScreen(this.prevScreen);
        }
        final int id = guiButton.id;
        if (guiButton.id == 2) {
            GuiToolsMenu.mc.displayGuiScreen(new GuiPortScanner(this));
        }
        if (guiButton.id == 3) {
            GuiToolsMenu.mc.displayGuiScreen(new GuiServerFinder(this));
        }
        final int id2 = guiButton.id;
        if (guiButton.id == 8) {
            GuiToolsMenu.mc.displayGuiScreen(new ResolverGui(this));
        }
        if (guiButton.id == 11) {
            GuiToolsMenu.mc.displayGuiScreen(new GuiDiscordWeebHookSpammer(this));
        }
        final int id3 = guiButton.id;
        final int id4 = guiButton.id;
    }
}
