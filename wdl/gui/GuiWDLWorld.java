package wdl.gui;

import wdl.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiWDLWorld extends GuiScreen
{
    private String title;
    private GuiScreen parent;
    private GuiButton allowCheatsBtn;
    private GuiButton gamemodeBtn;
    private GuiButton timeBtn;
    private GuiButton weatherBtn;
    private GuiButton spawnBtn;
    private GuiButton pickSpawnBtn;
    private boolean showSpawnFields;
    private GuiNumericTextField spawnX;
    private GuiNumericTextField spawnY;
    private GuiNumericTextField spawnZ;
    private int spawnTextY;
    
    public GuiWDLWorld(final GuiScreen parent) {
        this.showSpawnFields = false;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.title = I18n.format("wdl.gui.world.title", WDL.baseFolderName.replace('@', ':'));
        int n = GuiWDLWorld.height / 4 - 15;
        this.gamemodeBtn = new GuiButton(1, GuiWDLWorld.width / 2 - 100, n, this.getGamemodeText());
        this.buttonList.add(this.gamemodeBtn);
        n += 22;
        this.allowCheatsBtn = new GuiButton(6, GuiWDLWorld.width / 2 - 100, n, this.getAllowCheatsText());
        this.buttonList.add(this.allowCheatsBtn);
        n += 22;
        this.timeBtn = new GuiButton(2, GuiWDLWorld.width / 2 - 100, n, this.getTimeText());
        this.buttonList.add(this.timeBtn);
        n += 22;
        this.weatherBtn = new GuiButton(3, GuiWDLWorld.width / 2 - 100, n, this.getWeatherText());
        this.buttonList.add(this.weatherBtn);
        n += 22;
        this.spawnBtn = new GuiButton(4, GuiWDLWorld.width / 2 - 100, n, this.getSpawnText());
        this.buttonList.add(this.spawnBtn);
        n += 22;
        this.spawnTextY = n + 4;
        this.spawnX = new GuiNumericTextField(40, this.fontRendererObj, GuiWDLWorld.width / 2 - 87, n, 50, 16);
        this.spawnY = new GuiNumericTextField(41, this.fontRendererObj, GuiWDLWorld.width / 2 - 19, n, 50, 16);
        this.spawnZ = new GuiNumericTextField(42, this.fontRendererObj, GuiWDLWorld.width / 2 + 48, n, 50, 16);
        this.spawnX.setText(WDL.worldProps.getProperty("SpawnX"));
        this.spawnY.setText(WDL.worldProps.getProperty("SpawnY"));
        this.spawnZ.setText(WDL.worldProps.getProperty("SpawnZ"));
        this.spawnX.setMaxStringLength(7);
        this.spawnY.setMaxStringLength(7);
        this.spawnZ.setMaxStringLength(7);
        n += 18;
        this.pickSpawnBtn = new GuiButton(5, GuiWDLWorld.width / 2, n, 100, 20, I18n.format("wdl.gui.world.setSpawnToCurrentPosition", new Object[0]));
        this.buttonList.add(this.pickSpawnBtn);
        this.updateSpawnTextBoxVisibility();
        this.buttonList.add(new GuiButton(100, GuiWDLWorld.width / 2 - 100, GuiWDLWorld.height - 29, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.cycleGamemode();
            }
            else if (guiButton.id == 2) {
                this.cycleTime();
            }
            else if (guiButton.id == 3) {
                this.cycleWeather();
            }
            else if (guiButton.id == 4) {
                this.cycleSpawn();
            }
            else if (guiButton.id == 5) {
                this.setSpawnToPlayerPosition();
            }
            else if (guiButton.id == 6) {
                this.cycleAllowCheats();
            }
            else if (guiButton.id == 100) {
                GuiWDLWorld.mc.displayGuiScreen(this.parent);
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (this.showSpawnFields) {
            WDL.worldProps.setProperty("SpawnX", this.spawnX.getText());
            WDL.worldProps.setProperty("SpawnY", this.spawnY.getText());
            WDL.worldProps.setProperty("SpawnZ", this.spawnZ.getText());
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.showSpawnFields) {
            this.spawnX.mouseClicked(n, n2, n3);
            this.spawnY.mouseClicked(n, n2, n3);
            this.spawnZ.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.spawnX.textboxKeyTyped(c, n);
        this.spawnY.textboxKeyTyped(c, n);
        this.spawnZ.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        this.spawnX.updateCursorCounter();
        this.spawnY.updateCursorCounter();
        this.spawnZ.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawListBackground(23, 32, 0, 0, GuiWDLWorld.height, GuiWDLWorld.width);
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiWDLWorld.width / 2, 8, 16777215);
        if (this.showSpawnFields) {
            this.drawString(this.fontRendererObj, "X:", GuiWDLWorld.width / 2 - 99, this.spawnTextY, 16777215);
            this.drawString(this.fontRendererObj, "Y:", GuiWDLWorld.width / 2 - 31, this.spawnTextY, 16777215);
            this.drawString(this.fontRendererObj, "Z:", GuiWDLWorld.width / 2 + 37, this.spawnTextY, 16777215);
            this.spawnX.drawTextBox();
            this.spawnY.drawTextBox();
            this.spawnZ.drawTextBox();
        }
        super.drawScreen(n, n2, n3);
        String s = null;
        if (this.allowCheatsBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.allowCheats.description", new Object[0]);
        }
        else if (this.gamemodeBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.gamemode.description", new Object[0]);
        }
        else if (this.timeBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.time.description", new Object[0]);
        }
        else if (this.weatherBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.weather.description", new Object[0]);
        }
        else if (this.spawnBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.spawn.description", new Object[0]);
        }
        else if (this.pickSpawnBtn.isMouseOver()) {
            s = I18n.format("wdl.gui.world.setSpawnToCurrentPosition.description", new Object[0]);
        }
        if (this.showSpawnFields) {
            if (Utils.isMouseOverTextBox(n, n2, this.spawnX)) {
                s = I18n.format("wdl.gui.world.spawnPos.description", "X");
            }
            else if (Utils.isMouseOverTextBox(n, n2, this.spawnY)) {
                s = I18n.format("wdl.gui.world.spawnPos.description", "Y");
            }
            else if (Utils.isMouseOverTextBox(n, n2, this.spawnZ)) {
                s = I18n.format("wdl.gui.world.spawnPos.description", "Z");
            }
        }
        Utils.drawGuiInfoBox(s, GuiWDLWorld.width, GuiWDLWorld.height, 48);
    }
    
    private void cycleAllowCheats() {
        if (WDL.baseProps.getProperty("AllowCheats").equals("true")) {
            WDL.baseProps.setProperty("AllowCheats", "false");
        }
        else {
            WDL.baseProps.setProperty("AllowCheats", "true");
        }
        this.allowCheatsBtn.displayString = this.getAllowCheatsText();
    }
    
    private void cycleGamemode() {
        final String property = WDL.baseProps.getProperty("GameType");
        if (property.equals("keep")) {
            WDL.baseProps.setProperty("GameType", "creative");
        }
        else if (property.equals("creative")) {
            WDL.baseProps.setProperty("GameType", "survival");
        }
        else if (property.equals("survival")) {
            WDL.baseProps.setProperty("GameType", "hardcore");
        }
        else if (property.equals("hardcore")) {
            WDL.baseProps.setProperty("GameType", "keep");
        }
        this.gamemodeBtn.displayString = this.getGamemodeText();
    }
    
    private void cycleTime() {
        final String property = WDL.baseProps.getProperty("Time");
        if (property.equals("keep")) {
            WDL.baseProps.setProperty("Time", "23000");
        }
        else if (property.equals("23000")) {
            WDL.baseProps.setProperty("Time", "0");
        }
        else if (property.equals("0")) {
            WDL.baseProps.setProperty("Time", "6000");
        }
        else if (property.equals("6000")) {
            WDL.baseProps.setProperty("Time", "11500");
        }
        else if (property.equals("11500")) {
            WDL.baseProps.setProperty("Time", "12500");
        }
        else if (property.equals("12500")) {
            WDL.baseProps.setProperty("Time", "18000");
        }
        else {
            WDL.baseProps.setProperty("Time", "keep");
        }
        this.timeBtn.displayString = this.getTimeText();
    }
    
    private void cycleWeather() {
        final String property = WDL.baseProps.getProperty("Weather");
        if (property.equals("keep")) {
            WDL.baseProps.setProperty("Weather", "sunny");
        }
        else if (property.equals("sunny")) {
            WDL.baseProps.setProperty("Weather", "rain");
        }
        else if (property.equals("rain")) {
            WDL.baseProps.setProperty("Weather", "thunderstorm");
        }
        else if (property.equals("thunderstorm")) {
            WDL.baseProps.setProperty("Weather", "keep");
        }
        this.weatherBtn.displayString = this.getWeatherText();
    }
    
    private void cycleSpawn() {
        final String property = WDL.worldProps.getProperty("Spawn");
        if (property.equals("auto")) {
            WDL.worldProps.setProperty("Spawn", "player");
        }
        else if (property.equals("player")) {
            WDL.worldProps.setProperty("Spawn", "xyz");
        }
        else if (property.equals("xyz")) {
            WDL.worldProps.setProperty("Spawn", "auto");
        }
        this.spawnBtn.displayString = this.getSpawnText();
        this.updateSpawnTextBoxVisibility();
    }
    
    private String getAllowCheatsText() {
        return I18n.format("wdl.gui.world.allowCheats." + WDL.baseProps.getProperty("AllowCheats"), new Object[0]);
    }
    
    private String getGamemodeText() {
        return I18n.format("wdl.gui.world.gamemode." + WDL.baseProps.getProperty("GameType"), new Object[0]);
    }
    
    private String getTimeText() {
        String s = I18n.format("wdl.gui.world.time." + WDL.baseProps.getProperty("Time"), new Object[0]);
        if (s.startsWith("wdl.gui.world.time.")) {
            s = I18n.format("wdl.gui.world.time.custom", WDL.baseProps.getProperty("Time"));
        }
        return s;
    }
    
    private String getWeatherText() {
        return I18n.format("wdl.gui.world.weather." + WDL.baseProps.getProperty("Weather"), new Object[0]);
    }
    
    private String getSpawnText() {
        return I18n.format("wdl.gui.world.spawn." + WDL.worldProps.getProperty("Spawn"), new Object[0]);
    }
    
    private void updateSpawnTextBoxVisibility() {
        final boolean equals = WDL.worldProps.getProperty("Spawn").equals("xyz");
        this.showSpawnFields = equals;
        this.pickSpawnBtn.visible = equals;
    }
    
    private void setSpawnToPlayerPosition() {
        this.spawnX.setValue((int)WDL.thePlayer.posX);
        this.spawnY.setValue((int)WDL.thePlayer.posY);
        this.spawnZ.setValue((int)WDL.thePlayer.posZ);
    }
}
