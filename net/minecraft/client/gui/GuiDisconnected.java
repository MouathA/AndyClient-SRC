package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import Mood.*;
import net.minecraft.client.multiplayer.*;
import java.io.*;
import java.util.*;

public class GuiDisconnected extends GuiScreen
{
    private String reason;
    private IChatComponent message;
    private List multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    private GuiButton Button_ReConnect;
    private int update;
    private boolean sholdReconnect;
    
    public GuiDisconnected(final GuiScreen parentScreen, final String s, final IChatComponent message) {
        this.update = 0;
        this.sholdReconnect = true;
        this.parentScreen = parentScreen;
        this.reason = I18n.format(s, new Object[0]);
        this.message = message;
    }
    
    @Override
    public void updateScreen() {
        if (this.sholdReconnect) {
            switch (this.update) {
                case 20: {
                    if (this.Button_ReConnect != null) {
                        this.Button_ReConnect.setDisplayString("Reconnect: 4");
                        break;
                    }
                    break;
                }
                case 40: {
                    if (this.Button_ReConnect != null) {
                        this.Button_ReConnect.setDisplayString("Reconnect: 3");
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.Button_ReConnect != null) {
                        this.Button_ReConnect.setDisplayString("Reconnect: 2");
                        break;
                    }
                    break;
                }
                case 80: {
                    if (this.Button_ReConnect != null) {
                        this.Button_ReConnect.setDisplayString("Reconnect: 1");
                        break;
                    }
                    break;
                }
            }
            ++this.update;
            if (this.update >= 100) {
                if (HackedItemUtils.LastServer != null) {
                    GuiDisconnected.mc.displayGuiScreen(new GuiConnecting(HackedItemUtils.GuiMultiplayer_Gui, GuiDisconnected.mc, HackedItemUtils.LastServer.getIp(), HackedItemUtils.LastServer.getPort()));
                }
                else {
                    this.Button_ReConnect.setDisplayString("Reconnect: Failed");
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), GuiDisconnected.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, GuiDisconnected.width / 2 - 100, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
        this.Button_ReConnect = new GuiButton(1, GuiDisconnected.width / 2 - 100, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 25, "Reconnect: 5");
        this.buttonList.add(this.Button_ReConnect);
        this.buttonList.add(new GuiButton(2, GuiDisconnected.width / 2 - 100, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 50, "Tools"));
        this.buttonList.add(new GuiButton(3, GuiDisconnected.width / 2 + 103, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 25, 20, 20, "Off"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        final int id = guiButton.id;
        if (guiButton.id == 0) {
            GuiDisconnected.mc.displayGuiScreen(HackedItemUtils.GuiMultiplayer_Gui);
            if (guiButton.id == 1) {
                GuiDisconnected.mc.displayGuiScreen(new GuiConnecting(HackedItemUtils.GuiMultiplayer_Gui, GuiDisconnected.mc, HackedItemUtils.LastServer.getIp(), HackedItemUtils.LastServer.getPort()));
            }
            if (guiButton.id == 3) {
                if (this.sholdReconnect) {
                    this.sholdReconnect = false;
                    this.update = 0;
                    this.Button_ReConnect.setDisplayString("Reconnect");
                }
                else {
                    this.sholdReconnect = true;
                    this.update = 0;
                    this.Button_ReConnect.setDisplayString("Reconnect: 5");
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.reason, GuiDisconnected.width / 2, GuiDisconnected.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int n4 = GuiDisconnected.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            final Iterator<String> iterator = this.multilineMessage.iterator();
            while (iterator.hasNext()) {
                Gui.drawCenteredString(this.fontRendererObj, iterator.next(), GuiDisconnected.width / 2, n4, 16777215);
                n4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(n, n2, n3);
    }
}
