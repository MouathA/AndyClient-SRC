package viamcp.gui;

import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiCredits extends GuiScreen
{
    private GuiScreen parent;
    
    public GuiCredits(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiCredits.width / 2 - 100, GuiCredits.height - 25, 200, 20, "Back"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            GuiCredits.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GlStateManager.scale(2.0, 2.0, 2.0);
        final String string = EnumChatFormatting.BOLD + "Credits";
        this.drawString(this.fontRendererObj, string, (GuiCredits.width - this.fontRendererObj.getStringWidth(string) * 2) / 4, 5, -1);
        final int n4 = (5 + this.fontRendererObj.FONT_HEIGHT) * 2 + 2;
        final String string2 = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("ViaVersion Team").toString();
        final String string3 = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("FlorianMichael").toString();
        final String string4 = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("LaVache-FR").toString();
        final String string5 = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Hiderichan / Foreheadchan").toString();
        final String string6 = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Contact Info").toString();
        this.drawString(this.fontRendererObj, string2, (GuiCredits.width - this.fontRendererObj.getStringWidth(string2)) / 2, n4, -1);
        this.drawString(this.fontRendererObj, "ViaVersion", (GuiCredits.width - this.fontRendererObj.getStringWidth("ViaVersion")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT, -1);
        this.drawString(this.fontRendererObj, "ViaBackwards", (GuiCredits.width - this.fontRendererObj.getStringWidth("ViaBackwards")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 2, -1);
        this.drawString(this.fontRendererObj, "ViaRewind", (GuiCredits.width - this.fontRendererObj.getStringWidth("ViaRewind")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 3, -1);
        this.drawString(this.fontRendererObj, string3, (GuiCredits.width - this.fontRendererObj.getStringWidth(string3)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 5, -1);
        this.drawString(this.fontRendererObj, "ViaForge", (GuiCredits.width - this.fontRendererObj.getStringWidth("ViaForge")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 6, -1);
        this.drawString(this.fontRendererObj, string4, (GuiCredits.width - this.fontRendererObj.getStringWidth(string4)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 8, -1);
        this.drawString(this.fontRendererObj, "Original ViaMCP", (GuiCredits.width - this.fontRendererObj.getStringWidth("Original ViaMCP")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 9, -1);
        this.drawString(this.fontRendererObj, string5, (GuiCredits.width - this.fontRendererObj.getStringWidth(string5)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 11, -1);
        this.drawString(this.fontRendererObj, "ViaMCP Reborn", (GuiCredits.width - this.fontRendererObj.getStringWidth("ViaMCP Reborn")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 12, -1);
        this.drawString(this.fontRendererObj, string6, (GuiCredits.width - this.fontRendererObj.getStringWidth(string6)) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 14, -1);
        this.drawString(this.fontRendererObj, "Discord: Hideri#9003", (GuiCredits.width - this.fontRendererObj.getStringWidth("Discord: Hideri#9003")) / 2, n4 + this.fontRendererObj.FONT_HEIGHT * 15, -1);
        GlStateManager.scale(0.5, 0.5, 0.5);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaVersion)", GuiCredits.width + this.fontRendererObj.getStringWidth("ViaVersion "), (n4 + this.fontRendererObj.FONT_HEIGHT) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaBackward)", GuiCredits.width + this.fontRendererObj.getStringWidth("ViaBackwards "), (n4 + this.fontRendererObj.FONT_HEIGHT * 2) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaRewind)", GuiCredits.width + this.fontRendererObj.getStringWidth("ViaRewind "), (n4 + this.fontRendererObj.FONT_HEIGHT * 3) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/FlorianMichael/ViaForge)", GuiCredits.width + this.fontRendererObj.getStringWidth("ViaForge "), (n4 + this.fontRendererObj.FONT_HEIGHT * 6) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/LaVache-FR/ViaMCP)", GuiCredits.width + this.fontRendererObj.getStringWidth("Original ViaMCP "), (n4 + this.fontRendererObj.FONT_HEIGHT * 9) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/Foreheadchann/ViaMCP-Reborn)", GuiCredits.width + this.fontRendererObj.getStringWidth("ViaMCP Reborn "), (n4 + this.fontRendererObj.FONT_HEIGHT * 12) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        super.drawScreen(n, n2, n3);
    }
}
