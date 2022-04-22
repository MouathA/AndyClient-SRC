package wdl.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiWDLMultiworld extends GuiScreen
{
    private final MultiworldCallback callback;
    private GuiButton multiworldEnabledBtn;
    private boolean enableMultiworld;
    private int infoBoxWidth;
    private int infoBoxHeight;
    private int infoBoxX;
    private int infoBoxY;
    private List infoBoxLines;
    
    public GuiWDLMultiworld(final MultiworldCallback callback) {
        this.enableMultiworld = false;
        this.callback = callback;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final String string = String.valueOf(I18n.format("wdl.gui.multiworld.descirption.requiredWhen", new Object[0])) + "\n\n" + I18n.format("wdl.gui.multiworld.descirption.whatIs", new Object[0]);
        this.infoBoxWidth = 320;
        this.infoBoxLines = Utils.wordWrap(string, this.infoBoxWidth - 20);
        this.infoBoxHeight = this.fontRendererObj.FONT_HEIGHT * (this.infoBoxLines.size() + 1) + 40;
        this.infoBoxX = GuiWDLMultiworld.width / 2 - this.infoBoxWidth / 2;
        this.infoBoxY = GuiWDLMultiworld.height / 2 - this.infoBoxHeight / 2;
        this.multiworldEnabledBtn = new GuiButton(1, GuiWDLMultiworld.width / 2 - 100, this.infoBoxY + this.infoBoxHeight - 30, this.getMultiworldEnabledText());
        this.buttonList.add(this.multiworldEnabledBtn);
        this.buttonList.add(new GuiButton(100, GuiWDLMultiworld.width / 2 - 155, GuiWDLMultiworld.height - 29, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(101, GuiWDLMultiworld.width / 2 + 5, GuiWDLMultiworld.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            this.toggleMultiworldEnabled();
        }
        else if (guiButton.id == 100) {
            this.callback.onCancel();
        }
        else if (guiButton.id == 101) {
            this.callback.onSelect(this.enableMultiworld);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Utils.drawBorder(32, 32, 0, 0, GuiWDLMultiworld.height, GuiWDLMultiworld.width);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.multiworld.title", new Object[0]), GuiWDLMultiworld.width / 2, 8, 16777215);
        Gui.drawRect(this.infoBoxX, this.infoBoxY, this.infoBoxX + this.infoBoxWidth, this.infoBoxY + this.infoBoxHeight, -1342177280);
        final int n4 = this.infoBoxX + 10;
        int n5 = this.infoBoxY + 10;
        final Iterator<String> iterator = (Iterator<String>)this.infoBoxLines.iterator();
        while (iterator.hasNext()) {
            this.drawString(this.fontRendererObj, iterator.next(), n4, n5, 16777215);
            n5 += this.fontRendererObj.FONT_HEIGHT;
        }
        Gui.drawRect(this.multiworldEnabledBtn.xPosition - 2, this.multiworldEnabledBtn.yPosition - 2, this.multiworldEnabledBtn.xPosition + this.multiworldEnabledBtn.getButtonWidth() + 2, this.multiworldEnabledBtn.yPosition + 20 + 2, -65536);
        super.drawScreen(n, n2, n3);
    }
    
    private void toggleMultiworldEnabled() {
        if (this.enableMultiworld) {
            this.enableMultiworld = false;
        }
        else {
            this.enableMultiworld = true;
        }
        this.multiworldEnabledBtn.displayString = this.getMultiworldEnabledText();
    }
    
    private String getMultiworldEnabledText() {
        return I18n.format("wdl.gui.multiworld." + this.enableMultiworld, new Object[0]);
    }
    
    public interface MultiworldCallback
    {
        void onCancel();
        
        void onSelect(final boolean p0);
    }
}
