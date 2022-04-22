package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class GuiScreenAddServer extends GuiScreen
{
    private final GuiScreen parentScreen;
    private final ServerData serverData;
    private GuiTextField serverIPField;
    private GuiTextField serverNameField;
    private GuiButton serverResourcePacks;
    private static final String __OBFID;
    
    public GuiScreenAddServer(final GuiScreen parentScreen, final ServerData serverData) {
        this.parentScreen = parentScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void updateScreen() {
        this.serverNameField.updateCursorCounter();
        this.serverIPField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiScreenAddServer.width / 2 - 100, GuiScreenAddServer.height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiScreenAddServer.width / 2 - 100, GuiScreenAddServer.height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.serverResourcePacks = new GuiButton(2, GuiScreenAddServer.width / 2 - 100, GuiScreenAddServer.height / 4 + 72, String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText()));
        (this.serverNameField = new GuiTextField(0, this.fontRendererObj, GuiScreenAddServer.width / 2 - 100, 66, 200, 20)).setFocused(true);
        this.serverNameField.setText(this.serverData.serverName);
        (this.serverIPField = new GuiTextField(1, this.fontRendererObj, GuiScreenAddServer.width / 2 - 100, 106, 200, 20)).setMaxStringLength(128);
        this.serverIPField.setText(this.serverData.serverIP);
        this.buttonList.get(0).enabled = (this.serverIPField.getText().length() > 0 && this.serverIPField.getText().split(":").length > 0 && this.serverNameField.getText().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
                this.serverResourcePacks.displayString = String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText();
            }
            else if (guiButton.id == 1) {
                this.parentScreen.confirmClicked(false, 0);
            }
            else if (guiButton.id == 0) {
                this.serverData.serverName = this.serverNameField.getText();
                this.serverData.serverIP = this.serverIPField.getText();
                this.parentScreen.confirmClicked(true, 0);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.serverNameField.textboxKeyTyped(c, n);
        this.serverIPField.textboxKeyTyped(c, n);
        if (n == 15) {
            this.serverNameField.setFocused(!this.serverNameField.isFocused());
            this.serverIPField.setFocused(!this.serverIPField.isFocused());
        }
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.buttonList.get(0).enabled = (this.serverIPField.getText().length() > 0 && this.serverIPField.getText().split(":").length > 0 && this.serverNameField.getText().length() > 0);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.serverIPField.mouseClicked(n, n2, n3);
        this.serverNameField.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), GuiScreenAddServer.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), GuiScreenAddServer.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), GuiScreenAddServer.width / 2 - 100, 94, 10526880);
        this.serverNameField.drawTextBox();
        this.serverIPField.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00000695";
    }
}
