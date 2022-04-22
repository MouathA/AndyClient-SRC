package net.minecraft.client.gui;

import net.minecraft.command.server.*;
import org.apache.logging.log4j.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.io.*;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger field_146488_a;
    private GuiTextField commandTextField;
    private GuiTextField field_146486_g;
    private final CommandBlockLogic localCommandBlock;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private GuiButton field_175390_s;
    private boolean field_175389_t;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000748";
        field_146488_a = LogManager.getLogger();
    }
    
    public GuiCommandBlock(final CommandBlockLogic localCommandBlock) {
        this.localCommandBlock = localCommandBlock;
    }
    
    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton(0, GuiCommandBlock.width / 2 - 4 - 150, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.cancelBtn = new GuiButton(1, GuiCommandBlock.width / 2 + 4, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.field_175390_s = new GuiButton(4, GuiCommandBlock.width / 2 + 150 - 20, 150, 20, 20, "O"));
        (this.commandTextField = new GuiTextField(2, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 50, 300, 20)).setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCustomName());
        (this.field_146486_g = new GuiTextField(3, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 150, 276, 20)).setMaxStringLength(32767);
        this.field_146486_g.setEnabled(false);
        this.field_146486_g.setText("-");
        this.field_175389_t = this.localCommandBlock.func_175571_m();
        this.func_175388_a();
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.localCommandBlock.func_175573_a(this.field_175389_t);
                GuiCommandBlock.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 0) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(packetBuffer);
                packetBuffer.writeString(this.commandTextField.getText());
                packetBuffer.writeBoolean(this.localCommandBlock.func_175571_m());
                GuiCommandBlock.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", packetBuffer));
                if (!this.localCommandBlock.func_175571_m()) {
                    this.localCommandBlock.func_145750_b(null);
                }
                GuiCommandBlock.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 4) {
                this.localCommandBlock.func_175573_a(!this.localCommandBlock.func_175571_m());
                this.func_175388_a();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.commandTextField.textboxKeyTyped(c, n);
        this.field_146486_g.textboxKeyTyped(c, n);
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
        if (n != 28 && n != 156) {
            if (n == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.commandTextField.mouseClicked(n, n2, n3);
        this.field_146486_g.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), GuiCommandBlock.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), GuiCommandBlock.width / 2 - 150, 37, 10526880);
        this.commandTextField.drawTextBox();
        this.drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), GuiCommandBlock.width / 2 - 150, 75 + 0 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj = this.fontRendererObj;
        final String format = I18n.format("advMode.randomPlayer", new Object[0]);
        final int n4 = GuiCommandBlock.width / 2 - 150;
        final int n5 = 75;
        final int n6 = 1;
        int n7 = 0;
        ++n7;
        this.drawString(fontRendererObj, format, n4, n5 + n6 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj2 = this.fontRendererObj;
        final String format2 = I18n.format("advMode.allPlayers", new Object[0]);
        final int n8 = GuiCommandBlock.width / 2 - 150;
        final int n9 = 75;
        final int n10 = 1;
        ++n7;
        this.drawString(fontRendererObj2, format2, n8, n9 + n10 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj3 = this.fontRendererObj;
        final String format3 = I18n.format("advMode.allEntities", new Object[0]);
        final int n11 = GuiCommandBlock.width / 2 - 150;
        final int n12 = 75;
        final int n13 = 1;
        ++n7;
        this.drawString(fontRendererObj3, format3, n11, n12 + n13 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj4 = this.fontRendererObj;
        final String s = "";
        final int n14 = GuiCommandBlock.width / 2 - 150;
        final int n15 = 75;
        final int n16 = 1;
        ++n7;
        this.drawString(fontRendererObj4, s, n14, n15 + n16 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.field_146486_g.getText().length() > 0) {
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), GuiCommandBlock.width / 2 - 150, 75 + 1 * this.fontRendererObj.FONT_HEIGHT + 16, 10526880);
            this.field_146486_g.drawTextBox();
        }
        super.drawScreen(n, n2, n3);
    }
    
    private void func_175388_a() {
        if (this.localCommandBlock.func_175571_m()) {
            this.field_175390_s.displayString = "O";
            if (this.localCommandBlock.getLastOutput() != null) {
                this.field_146486_g.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
            }
        }
        else {
            this.field_175390_s.displayString = "X";
            this.field_146486_g.setText("-");
        }
    }
}
