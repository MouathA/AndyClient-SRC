package net.minecraft.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class GuiRenameWorld extends GuiScreen
{
    private GuiScreen field_146585_a;
    private GuiTextField field_146583_f;
    private final String field_146584_g;
    private static final String __OBFID;
    
    public GuiRenameWorld(final GuiScreen field_146585_a, final String field_146584_g) {
        this.field_146585_a = field_146585_a;
        this.field_146584_g = field_146584_g;
    }
    
    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        final String worldName = GuiRenameWorld.mc.getSaveLoader().getWorldInfo(this.field_146584_g).getWorldName();
        (this.field_146583_f = new GuiTextField(2, this.fontRendererObj, GuiRenameWorld.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146583_f.setText(worldName);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                GuiRenameWorld.mc.displayGuiScreen(this.field_146585_a);
            }
            else if (guiButton.id == 0) {
                GuiRenameWorld.mc.getSaveLoader().renameWorld(this.field_146584_g, this.field_146583_f.getText().trim());
                GuiRenameWorld.mc.displayGuiScreen(this.field_146585_a);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.field_146583_f.textboxKeyTyped(c, n);
        this.buttonList.get(0).enabled = (this.field_146583_f.getText().trim().length() > 0);
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field_146583_f.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), GuiRenameWorld.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), GuiRenameWorld.width / 2 - 100, 47, 10526880);
        this.field_146583_f.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00000709";
    }
}
