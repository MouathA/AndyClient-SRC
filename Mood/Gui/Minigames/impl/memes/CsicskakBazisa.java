package Mood.Gui.Minigames.impl.memes;

import Mood.UIButtons.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class CsicskakBazisa extends GuiScreen
{
    private GuiButton button;
    private final Csicskak CsicskuszMinigameusz;
    
    public CsicskakBazisa() {
        this.CsicskuszMinigameusz = new Csicskak();
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new UIButtons(1337, 5, CsicskakBazisa.height - 25, 20, 20, "§l<"));
        this.drawDefaultBackground();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);
        Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(CsicskakBazisa.mc);
        this.CsicskuszMinigameusz.render(CsicskakBazisa.width, CsicskakBazisa.height, n);
        super.drawScreen(n, n2, n3);
    }
}
