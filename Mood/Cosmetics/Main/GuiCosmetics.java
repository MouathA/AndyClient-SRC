package Mood.Cosmetics.Main;

import Mood.UIButtons.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class GuiCosmetics extends GuiScreen
{
    private UIButtons button;
    public static boolean CosmeticWings;
    public static boolean ILikeCMDBlocks;
    public static boolean Susanoo;
    
    static {
        GuiCosmetics.CosmeticWings = false;
        GuiCosmetics.ILikeCMDBlocks = false;
        GuiCosmetics.Susanoo = false;
    }
    
    public GuiCosmetics(final GuiIngameMenu guiIngameMenu) {
    }
    
    public GuiCosmetics(final GuiMainMenu guiMainMenu) {
    }
    
    @Override
    public void initGui() {
        final ScaledResolution scaledResolution = new ScaledResolution(GuiCosmetics.mc);
        this.buttonList.add(new UIButtons(3, GuiCosmetics.width / 2 - 145, GuiCosmetics.height / 4 + 1 + 20 + 1, 100, 20, this.WingsButton()));
        this.buttonList.add(new UIButtons(4, GuiCosmetics.width / 2 - 145, GuiCosmetics.height / 4 + 25 + 20 + 1, 100, 20, this.ILikeCMDBlocksButton()));
        this.buttonList.add(new UIButtons(5, GuiCosmetics.width / 2 - 145, GuiCosmetics.height / 4 + 49 + 20 + 1, 100, 20, this.SusanooButton()));
        this.drawDefaultBackground();
    }
    
    private String WingsButton() {
        return GuiCosmetics.CosmeticWings ? "Wings (On)" : "Wings (Off)";
    }
    
    private String ILikeCMDBlocksButton() {
        return GuiCosmetics.ILikeCMDBlocks ? "ILikeCMDBlocks (On)" : "ILikeCMDBlocks (Off)";
    }
    
    private String SusanooButton() {
        return GuiCosmetics.Susanoo ? "Susanoo (On)" : "Susanoo (Off)";
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(GuiCosmetics.mc);
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("Menu", new Object[0]), GuiCosmetics.width / 2, 40, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        final int id = guiButton.id;
        if (guiButton.id == 3) {
            GuiCosmetics.CosmeticWings = !GuiCosmetics.CosmeticWings;
            guiButton.displayString = this.WingsButton();
        }
        if (guiButton.id == 4) {
            GuiCosmetics.ILikeCMDBlocks = !GuiCosmetics.ILikeCMDBlocks;
            guiButton.displayString = this.ILikeCMDBlocksButton();
        }
        if (guiButton.id == 5) {
            GuiCosmetics.Susanoo = !GuiCosmetics.Susanoo;
            guiButton.displayString = this.SusanooButton();
        }
    }
}
