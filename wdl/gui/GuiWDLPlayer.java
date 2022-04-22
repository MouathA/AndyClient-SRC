package wdl.gui;

import wdl.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiWDLPlayer extends GuiScreen
{
    private String title;
    private GuiScreen parent;
    private GuiButton healthBtn;
    private GuiButton hungerBtn;
    private GuiButton playerPosBtn;
    private GuiButton pickPosBtn;
    private boolean showPosFields;
    private GuiNumericTextField posX;
    private GuiNumericTextField posY;
    private GuiNumericTextField posZ;
    private int posTextY;
    
    public GuiWDLPlayer(final GuiScreen parent) {
        this.showPosFields = false;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.title = I18n.format("wdl.gui.player.title", WDL.baseFolderName.replace('@', ':'));
        int n = GuiWDLPlayer.height / 4 - 15;
        this.healthBtn = new GuiButton(1, GuiWDLPlayer.width / 2 - 100, n, this.getHealthText());
        this.buttonList.add(this.healthBtn);
        n += 22;
        this.hungerBtn = new GuiButton(2, GuiWDLPlayer.width / 2 - 100, n, this.getHungerText());
        this.buttonList.add(this.hungerBtn);
        n += 22;
        this.playerPosBtn = new GuiButton(3, GuiWDLPlayer.width / 2 - 100, n, this.getPlayerPosText());
        this.buttonList.add(this.playerPosBtn);
        n += 22;
        this.posTextY = n + 4;
        this.posX = new GuiNumericTextField(40, this.fontRendererObj, GuiWDLPlayer.width / 2 - 87, n, 50, 16);
        this.posY = new GuiNumericTextField(41, this.fontRendererObj, GuiWDLPlayer.width / 2 - 19, n, 50, 16);
        this.posZ = new GuiNumericTextField(42, this.fontRendererObj, GuiWDLPlayer.width / 2 + 48, n, 50, 16);
        this.posX.setText(WDL.worldProps.getProperty("PlayerX"));
        this.posY.setText(WDL.worldProps.getProperty("PlayerY"));
        this.posZ.setText(WDL.worldProps.getProperty("PlayerZ"));
        this.posX.setMaxStringLength(7);
        this.posY.setMaxStringLength(7);
        this.posZ.setMaxStringLength(7);
        n += 18;
        this.pickPosBtn = new GuiButton(4, GuiWDLPlayer.width / 2, n, 100, 20, I18n.format("wdl.gui.player.setPositionToCurrentPosition", new Object[0]));
        this.buttonList.add(this.pickPosBtn);
        this.upadatePlayerPosVisibility();
        this.buttonList.add(new GuiButton(100, GuiWDLPlayer.width / 2 - 100, GuiWDLPlayer.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.cycleHealth();
            }
            else if (guiButton.id == 2) {
                this.cycleHunger();
            }
            else if (guiButton.id == 3) {
                this.cyclePlayerPos();
            }
            else if (guiButton.id == 4) {
                this.setPlayerPosToPlayerPosition();
            }
            else if (guiButton.id == 100) {
                GuiWDLPlayer.mc.displayGuiScreen(this.parent);
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (this.showPosFields) {
            WDL.worldProps.setProperty("PlayerX", this.posX.getText());
            WDL.worldProps.setProperty("PlayerY", this.posY.getText());
            WDL.worldProps.setProperty("PlayerZ", this.posZ.getText());
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.showPosFields) {
            this.posX.mouseClicked(n, n2, n3);
            this.posY.mouseClicked(n, n2, n3);
            this.posZ.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.posX.textboxKeyTyped(c, n);
        this.posY.textboxKeyTyped(c, n);
        this.posZ.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        this.posX.updateCursorCounter();
        this.posY.updateCursorCounter();
        this.posZ.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawListBackground(23, 32, 0, 0, GuiWDLPlayer.height, GuiWDLPlayer.width);
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLPlayer.width / 2, 8, 16777215);
        String s = null;
        if (this.showPosFields) {
            this.drawString(this.fontRendererObj, "X:", GuiWDLPlayer.width / 2 - 99, this.posTextY, 16777215);
            this.drawString(this.fontRendererObj, "Y:", GuiWDLPlayer.width / 2 - 31, this.posTextY, 16777215);
            this.drawString(this.fontRendererObj, "Z:", GuiWDLPlayer.width / 2 + 37, this.posTextY, 16777215);
            this.posX.drawTextBox();
            this.posY.drawTextBox();
            this.posZ.drawTextBox();
            if (Utils.isMouseOverTextBox(n, n2, this.posX)) {
                s = I18n.format("wdl.gui.player.positionTextBox.description", "X");
            }
            else if (Utils.isMouseOverTextBox(n, n2, this.posY)) {
                s = I18n.format("wdl.gui.player.positionTextBox.description", "Y");
            }
            else if (Utils.isMouseOverTextBox(n, n2, this.posZ)) {
                s = I18n.format("wdl.gui.player.positionTextBox.description", "Z");
            }
            if (this.pickPosBtn.isMouseOver()) {
                s = I18n.format("wdl.gui.player.setPositionToCurrentPosition.description", new Object[0]);
            }
        }
        if (this.healthBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.player.health.description", new Object[0]);
        }
        if (this.hungerBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.player.hunger.description", new Object[0]);
        }
        if (this.playerPosBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.player.position.description", new Object[0]);
        }
        super.drawScreen(n, n2, n3);
        if (s != null) {
            Utils.drawGuiInfoBox(s, GuiWDLPlayer.width, GuiWDLPlayer.height, 48);
        }
    }
    
    private void cycleHealth() {
        final String property = WDL.baseProps.getProperty("PlayerHealth");
        if (property.equals("keep")) {
            WDL.baseProps.setProperty("PlayerHealth", "20");
        }
        else if (property.equals("20")) {
            WDL.baseProps.setProperty("PlayerHealth", "keep");
        }
        this.healthBtn.displayString = this.getHealthText();
    }
    
    private void cycleHunger() {
        final String property = WDL.baseProps.getProperty("PlayerFood");
        if (property.equals("keep")) {
            WDL.baseProps.setProperty("PlayerFood", "20");
        }
        else if (property.equals("20")) {
            WDL.baseProps.setProperty("PlayerFood", "keep");
        }
        this.hungerBtn.displayString = this.getHungerText();
    }
    
    private void cyclePlayerPos() {
        final String property = WDL.worldProps.getProperty("PlayerPos");
        if (property.equals("keep")) {
            WDL.worldProps.setProperty("PlayerPos", "xyz");
        }
        else if (property.equals("xyz")) {
            WDL.worldProps.setProperty("PlayerPos", "keep");
        }
        this.playerPosBtn.displayString = this.getPlayerPosText();
        this.upadatePlayerPosVisibility();
    }
    
    private String getHealthText() {
        String s = I18n.format("wdl.gui.player.health." + WDL.baseProps.getProperty("PlayerHealth"), new Object[0]);
        if (s.startsWith("wdl.gui.player.health.")) {
            s = I18n.format("wdl.gui.player.health.custom", WDL.baseProps.getProperty("PlayerHealth"));
        }
        return s;
    }
    
    private String getHungerText() {
        String s = I18n.format("wdl.gui.player.hunger." + WDL.baseProps.getProperty("PlayerFood"), new Object[0]);
        if (s.startsWith("wdl.gui.player.hunger.")) {
            s = I18n.format("wdl.gui.player.hunger.custom", WDL.baseProps.getProperty("PlayerFood"));
        }
        return s;
    }
    
    private void upadatePlayerPosVisibility() {
        this.showPosFields = WDL.worldProps.getProperty("PlayerPos").equals("xyz");
        this.pickPosBtn.visible = this.showPosFields;
    }
    
    private String getPlayerPosText() {
        return I18n.format("wdl.gui.player.position." + WDL.worldProps.getProperty("PlayerPos"), new Object[0]);
    }
    
    private void setPlayerPosToPlayerPosition() {
        this.posX.setValue((int)WDL.thePlayer.posX);
        this.posY.setValue((int)WDL.thePlayer.posY);
        this.posZ.setValue((int)WDL.thePlayer.posZ);
    }
}
