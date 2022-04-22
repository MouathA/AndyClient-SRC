package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;

public class GuiShareToLan extends GuiScreen
{
    private final GuiScreen field_146598_a;
    private GuiButton field_146596_f;
    private GuiButton field_146597_g;
    private String field_146599_h;
    private boolean field_146600_i;
    private static final String __OBFID;
    
    public GuiShareToLan(final GuiScreen field_146598_a) {
        this.field_146599_h = "survival";
        this.field_146598_a = field_146598_a;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(101, GuiShareToLan.width / 2 - 155, GuiShareToLan.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
        this.buttonList.add(new GuiButton(102, GuiShareToLan.width / 2 + 5, GuiShareToLan.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.field_146597_g = new GuiButton(104, GuiShareToLan.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(this.field_146596_f = new GuiButton(103, GuiShareToLan.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
        this.func_146595_g();
    }
    
    private void func_146595_g() {
        this.field_146597_g.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + " " + I18n.format("selectWorld.gameMode." + this.field_146599_h, new Object[0]);
        this.field_146596_f.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
        if (this.field_146600_i) {
            this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.on", new Object[0]);
        }
        else {
            this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.off", new Object[0]);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 102) {
            GuiShareToLan.mc.displayGuiScreen(this.field_146598_a);
        }
        else if (guiButton.id == 104) {
            if (this.field_146599_h.equals("spectator")) {
                this.field_146599_h = "creative";
            }
            else if (this.field_146599_h.equals("creative")) {
                this.field_146599_h = "adventure";
            }
            else if (this.field_146599_h.equals("adventure")) {
                this.field_146599_h = "survival";
            }
            else {
                this.field_146599_h = "spectator";
            }
            this.func_146595_g();
        }
        else if (guiButton.id == 103) {
            this.field_146600_i = !this.field_146600_i;
            this.func_146595_g();
        }
        else if (guiButton.id == 101) {
            GuiShareToLan.mc.displayGuiScreen(null);
            final String shareToLAN = GuiShareToLan.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
            ChatComponentStyle chatComponentStyle;
            if (shareToLAN != null) {
                chatComponentStyle = new ChatComponentTranslation("commands.publish.started", new Object[] { shareToLAN });
            }
            else {
                chatComponentStyle = new ChatComponentText("commands.publish.failed");
            }
            final Minecraft mc = GuiShareToLan.mc;
            Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponentStyle);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), GuiShareToLan.width / 2, 50, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), GuiShareToLan.width / 2, 82, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00000713";
    }
}
