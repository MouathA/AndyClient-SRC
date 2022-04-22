package Mood.Gui.Minigames;

import Mood.UIButtons.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import Mood.Gui.Minigames.impl.Snake.*;
import Mood.Gui.Minigames.impl.memes.*;
import Mood.Gui.Minigames.impl.badgirls.*;
import java.io.*;

public class MinigameSelector extends GuiScreen
{
    private GuiButton button;
    
    @Override
    public void initGui() {
        final ScaledResolution scaledResolution = new ScaledResolution(MinigameSelector.mc);
        this.buttonList.add(new UIButtons(3, MinigameSelector.width / 2 - 145, MinigameSelector.height / 4 + 1 + 20 + 1, 100, 20, "Snake Game"));
        this.buttonList.add(new UIButtons(4, MinigameSelector.width / 2 - 145, MinigameSelector.height / 4 + 25 + 20 + 1, 100, 20, "Kapd el A Csicskat"));
        this.buttonList.add(new UIButtons(5, MinigameSelector.width / 2 - 145, MinigameSelector.height / 4 + 49 + 20 + 1, 100, 20, "www.rosszlanyok.hu"));
        this.drawDefaultBackground();
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(MinigameSelector.mc);
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("Minigames", new Object[0]), MinigameSelector.width / 2, 40, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        final int id = guiButton.id;
        if (guiButton.id == 3) {
            new GameFrame();
        }
        if (guiButton.id == 4) {
            MinigameSelector.mc.displayGuiScreen(new CsicskakBazisa());
        }
        if (guiButton.id == 5) {
            MinigameSelector.mc.displayGuiScreen(new GuiElmultal18(this));
        }
    }
}
