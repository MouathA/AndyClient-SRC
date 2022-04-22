package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    static {
        GuiQualitySettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.DYNAMIC_LIGHTS };
    }
    
    public GuiQualitySettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.qualityTitle", new Object[0]);
        this.buttonList.clear();
        while (0 < GuiQualitySettingsOF.enumOptions.length) {
            final GameSettings.Options options = GuiQualitySettingsOF.enumOptions[0];
            final int n = GuiQualitySettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiQualitySettingsOF.height / 6 + 0 - 12;
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(options.returnEnumOrdinal(), n, n2, options));
            }
            int n3 = 0;
            ++n3;
        }
        this.buttonList.add(new GuiButton(200, GuiQualitySettingsOF.width / 2 - 100, GuiQualitySettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                GuiQualitySettingsOF.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id != GameSettings.Options.AA_LEVEL.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(GuiQualitySettingsOF.mc, GuiQualitySettingsOF.mc.displayWidth, GuiQualitySettingsOF.mc.displayHeight);
                this.setWorldAndResolution(GuiQualitySettingsOF.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiQualitySettingsOF.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
        this.tooltipManager.drawTooltips(n, n2, this.buttonList);
    }
}
