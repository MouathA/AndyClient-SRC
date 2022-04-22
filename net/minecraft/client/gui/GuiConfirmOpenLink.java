package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;

public class GuiConfirmOpenLink extends GuiYesNo
{
    private final String openLinkWarning;
    private final String copyLinkButtonText;
    private final String linkText;
    private boolean showSecurityWarning;
    private static final String __OBFID;
    
    public GuiConfirmOpenLink(final GuiYesNoCallback guiYesNoCallback, final String linkText, final int n, final boolean b) {
        super(guiYesNoCallback, I18n.format(b ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), linkText, n);
        this.showSecurityWarning = true;
        this.confirmButtonText = I18n.format(b ? "chat.link.open" : "gui.yes", new Object[0]);
        this.cancelButtonText = I18n.format(b ? "gui.cancel" : "gui.no", new Object[0]);
        this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
        this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
        this.linkText = linkText;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, GuiConfirmOpenLink.width / 2 - 50 - 105, GuiConfirmOpenLink.height / 6 + 96, 100, 20, this.confirmButtonText));
        this.buttonList.add(new GuiButton(2, GuiConfirmOpenLink.width / 2 - 50, GuiConfirmOpenLink.height / 6 + 96, 100, 20, this.copyLinkButtonText));
        this.buttonList.add(new GuiButton(1, GuiConfirmOpenLink.width / 2 - 50 + 105, GuiConfirmOpenLink.height / 6 + 96, 100, 20, this.cancelButtonText));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 2) {
            this.copyLinkToClipboard();
        }
        this.parentScreen.confirmClicked(guiButton.id == 0, this.parentButtonClickedId);
    }
    
    public void copyLinkToClipboard() {
        GuiScreen.setClipboardString(this.linkText);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        if (this.showSecurityWarning) {
            Gui.drawCenteredString(this.fontRendererObj, this.openLinkWarning, GuiConfirmOpenLink.width / 2, 110, 16764108);
        }
    }
    
    public void disableSecurityWarning() {
        this.showSecurityWarning = false;
    }
    
    static {
        __OBFID = "CL_00000683";
    }
}
