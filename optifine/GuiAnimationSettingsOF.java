package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiAnimationSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    
    static {
        GuiAnimationSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
    }
    
    public GuiAnimationSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.animationsTitle", new Object[0]);
        this.buttonList.clear();
        while (0 < GuiAnimationSettingsOF.enumOptions.length) {
            final GameSettings.Options options = GuiAnimationSettingsOF.enumOptions[0];
            final int n = GuiAnimationSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiAnimationSettingsOF.height / 6 + 0 - 12;
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(options.returnEnumOrdinal(), n, n2, options));
            }
            int n3 = 0;
            ++n3;
        }
        this.buttonList.add(new GuiButton(210, GuiAnimationSettingsOF.width / 2 - 155, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, "\u405e\u4057\u401f\u405e\u4041\u4045\u4058\u405e\u405f\u4042\u401f\u4050\u405f\u4058\u405c\u4050\u4045\u4058\u405e\u405f\u401f\u4050\u405d\u405d\u407e\u405f"));
        this.buttonList.add(new GuiButton(211, GuiAnimationSettingsOF.width / 2 - 155 + 80, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, "\u405e\u4057\u401f\u405e\u4041\u4045\u4058\u405e\u405f\u4042\u401f\u4050\u405f\u4058\u405c\u4050\u4045\u4058\u405e\u405f\u401f\u4050\u405d\u405d\u407e\u4057\u4057"));
        this.buttonList.add(new GuiOptionButton(200, GuiAnimationSettingsOF.width / 2 + 5, GuiAnimationSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                GuiAnimationSettingsOF.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id == 210) {
                GuiAnimationSettingsOF.mc.gameSettings.setAllAnimations(true);
            }
            if (guiButton.id == 211) {
                GuiAnimationSettingsOF.mc.gameSettings.setAllAnimations(false);
            }
            final ScaledResolution scaledResolution = new ScaledResolution(GuiAnimationSettingsOF.mc, GuiAnimationSettingsOF.mc.displayWidth, GuiAnimationSettingsOF.mc.displayHeight);
            this.setWorldAndResolution(GuiAnimationSettingsOF.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiAnimationSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
    }
}
