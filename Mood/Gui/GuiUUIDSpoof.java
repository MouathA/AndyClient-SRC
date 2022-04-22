package Mood.Gui;

import Mood.UIButtons.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;

public class GuiUUIDSpoof extends GuiScreen
{
    protected GuiTextField ipField;
    protected GuiTextField fakeNickField;
    protected GuiTextField realNickField;
    protected GuiScreen prevScreen;
    
    public GuiUUIDSpoof(final GuiScreen prevScreen) {
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new UIButtons(1, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 4 + 95, 200, 20, "§3§lMent\u00e9s"));
        this.buttonList.add(new UIButtons(2, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 4 + 95 + 20 + 4, 200, 20, "§3§lM\u00e9gse"));
        this.buttonList.add(new UIButtons(3, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 4 + 95 + 20 + 28, 200, 20, this.getEnableButtonText()));
        this.realNickField = new GuiTextField(2, this.fontRendererObj, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 5, 200, 20);
        this.fakeNickField = new GuiTextField(1, this.fontRendererObj, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 5 + 40, 200, 20);
        (this.ipField = new GuiTextField(0, this.fontRendererObj, GuiUUIDSpoof.width / 2 - 100, GuiUUIDSpoof.height / 5 + 80, 200, 20)).setText(GuiUUIDSpoof.mc.getFakeIp());
        this.fakeNickField.setText(GuiUUIDSpoof.mc.getFakeNick());
        final GuiTextField realNickField = this.realNickField;
        final Minecraft mc = GuiUUIDSpoof.mc;
        realNickField.setText(Minecraft.getSession().getUsername());
    }
    
    private String getEnableButtonText() {
        return GuiUUIDSpoof.mc.isUUIDHack ? "§a§lBekapcsolva" : "§c§lKikapcsolva";
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            final Minecraft mc = GuiUUIDSpoof.mc;
            final Session session = Minecraft.getSession();
            GuiUUIDSpoof.mc.setSession(new Session(this.realNickField.getText(), session.getPlayerID(), session.getToken(), Session.Type.LEGACY.name()));
            GuiUUIDSpoof.mc.setFakeNick(this.fakeNickField.getText());
            GuiUUIDSpoof.mc.setFakeIp(this.ipField.getText());
            GuiUUIDSpoof.mc.displayGuiScreen(this.prevScreen);
            if (GuiUUIDSpoof.mc.getCurrentServerData() != null) {
                final Minecraft mc2 = GuiUUIDSpoof.mc;
                if (Minecraft.theWorld != null) {
                    final ServerData currentServerData = GuiUUIDSpoof.mc.getCurrentServerData();
                    final Minecraft mc3 = GuiUUIDSpoof.mc;
                    Minecraft.theWorld.sendQuittingDisconnectingPacket();
                    GuiUUIDSpoof.mc.loadWorld(null);
                    GuiUUIDSpoof.mc.displayGuiScreen(new GuiConnecting(this.prevScreen, GuiUUIDSpoof.mc, currentServerData));
                    return;
                }
            }
            GuiUUIDSpoof.mc.displayGuiScreen(this.prevScreen);
        }
        else if (guiButton.id == 2) {
            GuiUUIDSpoof.mc.displayGuiScreen(this.prevScreen);
        }
        else if (guiButton.id == 3) {
            GuiUUIDSpoof.mc.isUUIDHack = !GuiUUIDSpoof.mc.isUUIDHack;
            guiButton.displayString = this.getEnableButtonText();
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.ipField.mouseClicked(n, n2, n3);
        this.fakeNickField.mouseClicked(n, n2, n3);
        this.realNickField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiUUIDSpoof.mc.displayGuiScreen(this.prevScreen);
            return;
        }
        if (n == 15) {
            if (this.realNickField.isFocused()) {
                this.realNickField.setFocused(false);
                this.fakeNickField.setFocused(true);
            }
            else if (this.fakeNickField.isFocused()) {
                this.fakeNickField.setFocused(false);
                this.ipField.setFocused(true);
            }
            else if (this.ipField.isFocused()) {
                this.ipField.setFocused(false);
                this.realNickField.setFocused(true);
            }
        }
        if (this.ipField.isFocused()) {
            this.ipField.textboxKeyTyped(c, n);
        }
        if (this.fakeNickField.isFocused()) {
            this.fakeNickField.textboxKeyTyped(c, n);
        }
        if (this.realNickField.isFocused()) {
            this.realNickField.textboxKeyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, "§7\u00cdrdbe ide az \u00e1ldozatod nev\u00e9t:", GuiUUIDSpoof.width / 2, this.realNickField.yPosition - 15, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§7\u00cdrdbe ide m\u00e9gegyszer:", GuiUUIDSpoof.width / 2, this.fakeNickField.yPosition - 15, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "§7BungeeHack:", GuiUUIDSpoof.width / 2, this.ipField.yPosition - 15, 16777215);
        this.fakeNickField.drawTextBox();
        this.realNickField.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
}
