package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetHandlerPlayClient netHandlerPlayClient;
    private int progress;
    private static final String __OBFID;
    
    public GuiDownloadTerrain(final NetHandlerPlayClient netHandlerPlayClient) {
        this.netHandlerPlayClient = netHandlerPlayClient;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
    }
    
    @Override
    public void updateScreen() {
        ++this.progress;
        if (this.progress % 20 == 0) {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground(0);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), GuiDownloadTerrain.width / 2, GuiDownloadTerrain.height / 2 - 50, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        __OBFID = "CL_00000708";
    }
}
