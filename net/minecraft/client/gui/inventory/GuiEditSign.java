package net.minecraft.client.gui.inventory;

import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import Mood.*;
import net.minecraft.event.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;

public class GuiEditSign extends GuiScreen
{
    private TileEntitySign tileSign;
    private int updateCounter;
    TileEntitySign p_175141_1_;
    private int editLine;
    protected GuiTextField Command;
    public String command;
    protected GuiTextField Lagg;
    public static boolean cmdsign;
    public static boolean laggsign;
    private GuiButton doneBtn;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000764";
        GuiEditSign.cmdsign = false;
        GuiEditSign.laggsign = false;
    }
    
    public GuiEditSign(final TileEntitySign tileSign) {
        this.tileSign = tileSign;
    }
    
    public void setCommand(final String s) {
        this.command = s.replace("\"", "\\\\\"");
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(6, GuiEditSign.width - 105, 8, 100, 20, "           Command"));
        this.buttonList.add(new GuiButton(3, GuiEditSign.width - 105, 52, 100, 20, "        Lagg"));
        (this.Command = new GuiTextField(2, this.fontRendererObj, GuiEditSign.width - 105, 31, 100, 18)).setText("/op " + Minecraft.session.getUsername());
        this.buttonList.add(this.doneBtn = new GuiButton(0, GuiEditSign.width / 2 - 100, GuiEditSign.height / 4 + 120, I18n.format("                     Done", new Object[0])));
        this.tileSign.setEditable(false);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.Command.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient netHandler = GuiEditSign.mc.getNetHandler();
        if (netHandler != null) {
            netHandler.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }
        this.tileSign.setEditable(true);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.tileSign.markDirty();
                GuiEditSign.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 5) {
                GuiEditSign.cmdsign = false;
            }
            else if (guiButton.id == 3) {
                GuiEditSign.mc.openScreen(null);
                GuiEditSign.laggsign = true;
                Segito.msg("A Folyamat sikeresen el lett ind\u00edtva!");
                Segito.msg("A le\u00e1ll\u00edt\u00e1shoz, k\u00e9rj\u00fck \u00edrja be az al\u00e1bbi parancsot:");
                Segito.msg("§b'-laggsign kikapcs'");
            }
            else if (guiButton.id == 6) {
                GuiEditSign.cmdsign = true;
                this.setCommand(this.Command.getText());
                final boolean b = true;
                GuiEditSign.cmdsign = true;
                if (b) {
                    final ChatStyle chatStyle = new ChatStyle();
                    chatStyle.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.command));
                    this.tileSign.signText[0].setChatStyle(chatStyle);
                    GuiEditSign.mc.openScreen(null);
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 200) {
            this.editLine = (this.editLine - 1 & 0x3);
        }
        if (n == 15) {
            if (this.Command.isFocused()) {
                this.Command.setFocused(false);
                this.Lagg.setFocused(true);
            }
            else if (this.Lagg.isFocused()) {
                this.Lagg.setFocused(false);
                this.Command.setFocused(true);
            }
            else if (this.Command.isFocused()) {
                this.Command.setFocused(false);
                this.Command.setFocused(true);
            }
        }
        if (n == 208 || n == 28 || n == 156) {
            this.editLine = (this.editLine + 1 & 0x3);
        }
        String s = this.tileSign.signText[this.editLine].getUnformattedText();
        if (n == 14 && s.length() > 0) {
            s = s.substring(0, s.length() - 1);
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c) && this.fontRendererObj.getStringWidth(String.valueOf(s) + c) <= 90) {
            s = String.valueOf(s) + c;
        }
        this.tileSign.signText[this.editLine] = new ChatComponentText(s);
        if (n == 1) {
            this.actionPerformed(this.doneBtn);
        }
        if (this.Command.isFocused()) {
            this.Command.textboxKeyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), GuiEditSign.width / 2, 40, 16777215);
        final String s = "§7A Lagg Sign kikapcsol\u00e1s\u00e1hoz";
        final String s2 = "§7A Lagg Sign bekapcsol\u00e1s\u00e1hoz";
        final String s3 = "§7\u00cdrd be az al\u00e1bbi parancsot a chatre:";
        this.drawString(this.fontRendererObj, s2, GuiEditSign.width - this.fontRendererObj.getStringWidth(s2) - 8, 75, 16777215);
        final String s4 = "§7Kattints a gombra!";
        this.drawString(this.fontRendererObj, s4, GuiEditSign.width - this.fontRendererObj.getStringWidth(s4) - 8, 85, 16777215);
        this.drawString(this.fontRendererObj, s, GuiEditSign.width - this.fontRendererObj.getStringWidth(s) - 8, 105, 16777215);
        this.drawString(this.fontRendererObj, s3, GuiEditSign.width - this.fontRendererObj.getStringWidth(s3) - 8, 115, 16777215);
        final String s5 = "§c§l-laggsign kikapcs";
        this.drawString(this.fontRendererObj, s5, GuiEditSign.width - this.fontRendererObj.getStringWidth(s5) - 8, 125, 16777215);
        final String s6 = "§7Kattints ide a parancs";
        this.drawString(this.fontRendererObj, s6, GuiEditSign.width - this.fontRendererObj.getStringWidth(s6) - 110, 8, 16777215);
        final String s7 = "§7Lefuttat\u00e1s\u00e1hoz ---->";
        this.drawString(this.fontRendererObj, s7, GuiEditSign.width - this.fontRendererObj.getStringWidth(s7) - 110, 18, 16777215);
        final String s8 = "§7\u00cdrd be ide";
        this.drawString(this.fontRendererObj, s8, GuiEditSign.width - this.fontRendererObj.getStringWidth(s8) - 110, 31, 16777215);
        final String s9 = "§7a parancsot:";
        this.drawString(this.fontRendererObj, s9, GuiEditSign.width - this.fontRendererObj.getStringWidth(s9) - 110, 40, 16777215);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate((float)(GuiEditSign.width / 2), 0.0f, 50.0f);
        final float n4 = 93.75f;
        GlStateManager.scale(-n4, -n4, -n4);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        if (this.tileSign.getBlockType() == Blocks.standing_sign) {
            GlStateManager.rotate(this.tileSign.getBlockMetadata() * 360 / 16.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int blockMetadata = this.tileSign.getBlockMetadata();
            float n5 = 0.0f;
            if (blockMetadata == 2) {
                n5 = 180.0f;
            }
            if (blockMetadata == 4) {
                n5 = 90.0f;
            }
            if (blockMetadata == 5) {
                n5 = -90.0f;
            }
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        this.tileSign.lineBeingEdited = -1;
        this.Command.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
}
