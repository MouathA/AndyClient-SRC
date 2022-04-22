package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    static {
        GuiPerformanceSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
    }
    
    public GuiPerformanceSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.performanceTitle", new Object[0]);
        this.buttonList.clear();
        while (0 < GuiPerformanceSettingsOF.enumOptions.length) {
            final GameSettings.Options options = GuiPerformanceSettingsOF.enumOptions[0];
            final int n = GuiPerformanceSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiPerformanceSettingsOF.height / 6 + 0 - 12;
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(options.returnEnumOrdinal(), n, n2, options));
            }
            int n3 = 0;
            ++n3;
        }
        this.buttonList.add(new GuiButton(200, GuiPerformanceSettingsOF.width / 2 - 100, GuiPerformanceSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                GuiPerformanceSettingsOF.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiPerformanceSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
        this.tooltipManager.drawTooltips(n, n2, this.buttonList);
    }
}
