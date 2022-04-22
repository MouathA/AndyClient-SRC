package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import wdl.*;
import Mood.Matrix.*;
import Mood.Gui.Minigames.*;
import Mood.Gui.*;
import net.minecraft.client.*;
import net.minecraft.realms.*;
import Mood.Cosmetics.Main.*;
import java.net.*;
import net.minecraft.client.gui.achievement.*;
import Mood.Host.*;
import net.minecraft.client.multiplayer.*;
import java.io.*;
import org.lwjgl.opengl.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import Mood.Helpers.*;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;
    private ServerData data;
    private ServerPingRenderer serverPingRenderer;
    private boolean isAutoReconnectClicked;
    private static final String __OBFID;
    private int highestTps;
    private final ArrayList tps;
    private int lowestTps;
    private int reload;
    private int redLineTimer;
    private int fade;
    
    public GuiIngameMenu() {
        this.serverPingRenderer = new ServerPingRenderer();
        this.isAutoReconnectClicked = false;
        this.highestTps = 0;
        this.tps = new ArrayList();
        this.lowestTps = Integer.MAX_VALUE;
        this.reload = 2;
        this.redLineTimer = 10;
        this.fade = -155;
    }
    
    @Override
    public void initGui() {
        this.redLineTimer = 0;
        this.field_146445_a = 0;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 120 - 16, I18n.format("menu.returnToMenu", new Object[0])));
        if (!GuiIngameMenu.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = (this.isAutoReconnectClicked ? "Biztosan ki szeretn\u00e9l jelentkezni?" : I18n.format("menu.disconnect", new Object[0]));
        }
        this.buttonList.add(new GuiButton(4, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 24 - 16, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 96 - 16, 98, 20, I18n.format("menu.options", new Object[0])));
        final GuiButton guiButton;
        this.buttonList.add(guiButton = new GuiButton(7, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 96 - 16, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 48 - 16, 98, 20, I18n.format("   Achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 48 - 16, 98, 20, I18n.format("gui.stats", new Object[0])));
        guiButton.enabled = (GuiIngameMenu.mc.isSingleplayer() && !GuiIngameMenu.mc.getIntegratedServer().getPublic());
        this.buttonList.add(new GuiButton(1337, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 72 - 16, 98, 20, "          Kozv. Csatlakozas"));
        this.buttonList.add(new GuiButton(64, GuiIngameMenu.width / 2 - 45, 5, 100, 20, "Minigames"));
        this.buttonList.add(new GuiButton(65, GuiIngameMenu.width / 2 + 60, 5, 100, 20, "Music (Pm)"));
        this.buttonList.add(new GuiButton(66, GuiIngameMenu.width / 2 - 150, 5, 100, 20, "Design"));
        this.buttonList.add(new GuiButton(56, GuiIngameMenu.width - 103, 72, 98, 20, "Resolve SRV"));
        this.buttonList.add(new GuiButton(10101, GuiIngameMenu.width - 103, 28, 98, 20, "            Instant Crasher (Pm)"));
        this.buttonList.add(new GuiButton(10101, GuiIngameMenu.width - 103, 50, 98, 20, "          Send NullPing (Pm)"));
        if (!GuiIngameMenu.mc.isSingleplayer()) {
            this.buttonList.add(new GuiButton(58, 5, 50, 98, 20, "       Check Host Ping"));
            this.buttonList.add(new GuiButton(59, 5, 72, 98, 20, "       Check Host TCP"));
        }
        this.buttonList.add(new GuiButton(10, 5, 6, 98, 20, I18n.format("Tools", new Object[0])));
        this.buttonList.add(new GuiButton(1001, 5, 28, 98, 20, I18n.format("Cosmetic", new Object[0])));
        this.buttonList.add(new GuiButton(1002, GuiIngameMenu.width - 103, 6, 98, 20, this.ParticleButton()));
        if (!GuiIngameMenu.mc.isSingleplayer()) {
            this.buttonList.add(new GuiButton(88, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 - 16 + 145, 200, 20, "Ujracsatlakozas"));
        }
        this.buttonList.add(new GuiButton(13, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 72 - 16, 98, 20, "   IP Masolasa..."));
        WDLHooks.injectWDLButtons(this, this.buttonList);
    }
    
    private String ParticleButton() {
        return MatrixBGManagerForInGame.Particle ? "Particles (On)" : "Particles (Off)";
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        WDLHooks.handleWDLButtonClick(this, guiButton);
        if (guiButton.id == 1002) {
            MatrixBGManagerForInGame.Particle = !MatrixBGManagerForInGame.Particle;
            guiButton.displayString = this.ParticleButton();
        }
        if (guiButton.id == 64) {
            GuiIngameMenu.mc.displayGuiScreen(new MinigameSelector());
        }
        if (guiButton.id == 10) {
            GuiIngameMenu.mc.displayGuiScreen(new GuiToolsMenu());
        }
        switch (guiButton.id) {
            case 0: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiOptions(this, GuiIngameMenu.mc.gameSettings));
            }
            case 1: {
                if (!this.isAutoReconnectClicked && !GuiIngameMenu.mc.isIntegratedServerRunning()) {
                    this.buttonList.get(0).displayString = "            Biztosan ki szeretnel jelentkezni?";
                    this.isAutoReconnectClicked = true;
                    return;
                }
                final boolean integratedServerRunning = GuiIngameMenu.mc.isIntegratedServerRunning();
                final boolean connectedToRealms = GuiIngameMenu.mc.isConnectedToRealms();
                guiButton.enabled = false;
                final Minecraft mc = GuiIngameMenu.mc;
                Minecraft.theWorld.sendQuittingDisconnectingPacket();
                GuiIngameMenu.mc.loadWorld(null);
                if (integratedServerRunning) {
                    GuiIngameMenu.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                if (connectedToRealms) {
                    new RealmsBridge().switchToRealms(new GuiMainMenu());
                    break;
                }
                GuiIngameMenu.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                break;
            }
            case 4: {
                GuiIngameMenu.mc.displayGuiScreen(null);
                GuiIngameMenu.mc.setIngameFocus();
            }
            case 1001: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiCosmetics(this));
            }
            case 56: {
                GuiScreen.setClipboardString(InetAddress.getByName(ServerAddress.fromString(GuiIngameMenu.mc.getCurrentServerData().serverIP).getIP()).getHostAddress());
            }
            case 5: {
                final Minecraft mc2 = GuiIngameMenu.mc;
                final Minecraft mc3 = GuiIngameMenu.mc;
                mc2.displayGuiScreen(new GuiAchievements(this, Minecraft.thePlayer.getStatFileWriter()));
            }
            case 1337: {
                if (GuiIngameMenu.mc.isSingleplayer()) {
                    return;
                }
            }
            case 6: {
                final Minecraft mc4 = GuiIngameMenu.mc;
                final Minecraft mc5 = GuiIngameMenu.mc;
                mc4.displayGuiScreen(new GuiStats(this, Minecraft.thePlayer.getStatFileWriter()));
            }
            case 7: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiShareToLan(this));
            }
            case 10: {}
            case 13: {
                GuiScreen.setClipboardString(GuiIngameMenu.mc.getCurrentServerData().serverIP);
            }
            case 14: {}
            case 58: {
                GuiIngameMenu.mc.displayGuiScreen(new CheckHostScreen(GuiIngameMenu.mc.getCurrentServerData().serverIP, "Ping", this));
            }
            case 59: {
                GuiIngameMenu.mc.displayGuiScreen(new CheckHostScreen(GuiIngameMenu.mc.getCurrentServerData().serverIP, "TCP", this));
            }
            case 88: {
                this.data = GuiIngameMenu.mc.getCurrentServerData();
                final Minecraft mc6 = GuiIngameMenu.mc;
                Minecraft.theWorld.sendQuittingDisconnectingPacket();
                GuiIngameMenu.mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), GuiIngameMenu.mc, this.data));
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }
    
    private void renderTpsBox() {
        final float n = (float)(GuiIngameMenu.width / 2 - 310);
        final float n2 = (float)(GuiIngameMenu.height - 250);
        if (this.tps.size() >= 80) {
            Gui.drawRect((int)n, (int)n2 + 97, (int)(n + 202.0f), (int)(n2 + 200.0f - 2.0f), Integer.MIN_VALUE);
        }
        float n3 = 0.0f;
        int n5 = 0;
        if (!this.tps.isEmpty()) {
            float n4 = 0.0f;
            while (0 < this.tps.size()) {
                n4 += this.tps.get(0);
                ++n5;
            }
            n3 = n4 / this.tps.size();
        }
        String.format("%.2f", n3);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4d(1.0, 0.0, 0.0, 1.0);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(3);
        double n6 = this.tps.size() * 2.5;
        while (n6 >= 202.0) {
            this.tps.remove(0);
            n6 = this.tps.size() * 2.5;
            this.highestTps = 0;
            while (0 < this.tps.size()) {
                if (this.tps.get(0) > this.highestTps) {
                    this.highestTps = this.tps.get(0);
                }
                if (this.tps.get(0) < this.lowestTps) {
                    this.lowestTps = this.tps.get(0);
                }
                int n7 = 0;
                ++n7;
            }
        }
        while (0 < this.tps.size()) {
            final float n8 = (float)(Math.max(1, this.highestTps) + 20);
            final float min = Math.min(n8, this.tps.get(0));
            final float n9 = (float)Math.max(0, this.lowestTps - 20);
            final double n10 = (min - n9) / (n8 - n9) * 200.0f - ((this.tps.size() < 80) ? (this.tps.get(0) * 6) : 0);
            if (this.tps.size() >= 80) {
                if (this.redLineTimer < 3300) {
                    ++this.redLineTimer;
                }
                else {
                    GL11.glVertex2d(n + 2.5 + 0 * 2.5, n2 + 200.0f - 2.0 - n10);
                }
            }
            ++n5;
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScaled(0.5, 0.5, 0.5);
        final float n11 = (float)(Math.max(1, this.highestTps) + 20);
        final float n12 = (float)Math.max(0, this.lowestTps - 20);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (!GuiIngameMenu.mc.isSingleplayer()) {
            this.serverPingRenderer.render(n, n2, 70, false, GuiIngameMenu.mc.getCurrentServerData().serverIP);
        }
        super.drawScreen(n, n2, n3);
        if (this.serverPingRenderer.hoveringText != null && !this.serverPingRenderer.hoveringText.isEmpty()) {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.serverPingRenderer.hoveringText)), n, n2);
        }
        if (this.reload < ((this.tps.size() < 80) ? 5 : 200)) {
            this.reload += 5;
        }
        else {
            this.tps.add((int)ServerPerformanceCalculator.lastTps);
            this.reload = 0;
        }
        if (!ServerPerformanceCalculator.getFormatTps().isEmpty()) {
            this.renderTpsBox();
        }
    }
    
    static {
        __OBFID = "CL_00000703";
    }
}
