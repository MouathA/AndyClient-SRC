package net.minecraft.client.gui;

import Mood.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private int field_146347_a;
    private boolean field_146346_f;
    private static final String __OBFID;
    public int respawnCountTick;
    
    public GuiGameOver() {
        this.field_146346_f = false;
        this.respawnCountTick = 0;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final Client instance = Client.INSTANCE;
        if (!Client.getModuleByName("BetterDeathScreen").toggled) {
            final Client instance2 = Client.INSTANCE;
            if (!Client.getModuleByName("BetterDeathScreen").toggled) {
                final Minecraft mc = GuiGameOver.mc;
                if (Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                    if (GuiGameOver.mc.isIntegratedServerRunning()) {
                        this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
                    }
                    else {
                        this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
                    }
                }
                else {
                    this.buttonList.add(new GuiButton(0, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
                    this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
                    final Minecraft mc2 = GuiGameOver.mc;
                    if (Minecraft.getSession() == null) {
                        this.buttonList.get(1).enabled = false;
                    }
                }
                final Iterator<GuiButton> iterator = this.buttonList.iterator();
                while (iterator.hasNext()) {
                    iterator.next().enabled = false;
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                final Minecraft mc = GuiGameOver.mc;
                Minecraft.thePlayer.respawnPlayer();
                GuiGameOver.mc.displayGuiScreen(null);
                break;
            }
            case 1: {
                final GuiYesNo guiYesNo = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
                GuiGameOver.mc.displayGuiScreen(guiYesNo);
                guiYesNo.setButtonDelay(20);
                break;
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            final Minecraft mc = GuiGameOver.mc;
            Minecraft.theWorld.sendQuittingDisconnectingPacket();
            GuiGameOver.mc.loadWorld(null);
            GuiGameOver.mc.displayGuiScreen(new GuiMainMenu());
        }
        else {
            final Minecraft mc2 = GuiGameOver.mc;
            Minecraft.thePlayer.respawnPlayer();
            GuiGameOver.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final Client instance = Client.INSTANCE;
        if (!Client.getModuleByName("BetterDeathScreen").toggled) {
            this.drawGradientRect(0, 0, GuiGameOver.width, GuiGameOver.height, 1615855616, -1602211792);
        }
        final Client instance2 = Client.INSTANCE;
        if (Client.getModuleByName("BetterDeathScreen").toggled) {
            Gui.drawRect(0, GuiGameOver.height / 2 - 60, GuiGameOver.width, GuiGameOver.height / 2 + 60, Integer.MIN_VALUE);
            if (this.respawnCountTick < 400) {
                this.respawnCountTick += 10;
            }
            else {
                this.respawnCountTick = 0;
                final Minecraft mc = GuiGameOver.mc;
                Minecraft.thePlayer.respawnPlayer();
                GuiGameOver.mc.displayGuiScreen(null);
            }
        }
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        final Minecraft mc2 = GuiGameOver.mc;
        final boolean hardcoreModeEnabled = Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled();
        final String s = hardcoreModeEnabled ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
        final Client instance3 = Client.INSTANCE;
        if (!Client.getModuleByName("BetterDeathScreen").toggled) {
            Gui.drawCenteredString(this.fontRendererObj, s, GuiGameOver.width / 2 / 2, 30, 16777215);
        }
        else {
            Gui.drawCenteredString(this.fontRendererObj, "§4Wasted", GuiGameOver.width / 2 / 2, GuiGameOver.height / 4 - 5, 16777215);
        }
        if (hardcoreModeEnabled) {
            final Client instance4 = Client.INSTANCE;
            if (!Client.getModuleByName("BetterDeathScreen").toggled) {
                Gui.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), GuiGameOver.width / 2, 144, 16777215);
            }
        }
        final Client instance5 = Client.INSTANCE;
        if (!Client.getModuleByName("BetterDeathScreen").toggled) {
            final FontRenderer fontRendererObj = this.fontRendererObj;
            final StringBuilder append = new StringBuilder(String.valueOf(I18n.format("deathScreen.score", new Object[0]))).append(": ").append(EnumChatFormatting.YELLOW);
            final Minecraft mc3 = GuiGameOver.mc;
            Gui.drawCenteredString(fontRendererObj, append.append(Minecraft.thePlayer.getScore()).toString(), GuiGameOver.width / 2, 100, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final Client instance = Client.INSTANCE;
        if (!Client.getModuleByName("BetterDeathScreen").toggled) {
            ++this.field_146347_a;
            if (this.field_146347_a == 20) {
                final Iterator<GuiButton> iterator = this.buttonList.iterator();
                while (iterator.hasNext()) {
                    iterator.next().enabled = true;
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00000690";
    }
}
