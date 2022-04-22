package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    static {
        GuiOtherSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH };
    }
    
    public GuiOtherSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.otherTitle", new Object[0]);
        this.buttonList.clear();
        while (0 < GuiOtherSettingsOF.enumOptions.length) {
            final GameSettings.Options options = GuiOtherSettingsOF.enumOptions[0];
            final int n = GuiOtherSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiOtherSettingsOF.height / 6 + 0 - 12;
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(options.returnEnumOrdinal(), n, n2, options));
            }
            int n3 = 0;
            ++n3;
        }
        this.buttonList.add(new GuiButton(210, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                GuiOtherSettingsOF.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id == 210) {
                GuiOtherSettingsOF.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999));
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            GuiOtherSettingsOF.mc.gameSettings.resetSettings();
        }
        GuiOtherSettingsOF.mc.displayGuiScreen(this);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiOtherSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
        this.tooltipManager.drawTooltips(n, n2, this.buttonList);
    }
}
