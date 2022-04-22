package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class ScreenChatOptions extends GuiScreen
{
    private static final GameSettings.Options[] field_146399_a;
    private final GuiScreen field_146396_g;
    private final GameSettings game_settings;
    private String field_146401_i;
    private String field_146398_r;
    private int field_146397_s;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000681";
        field_146399_a = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
    }
    
    public ScreenChatOptions(final GuiScreen field_146396_g, final GameSettings game_settings) {
        this.field_146396_g = field_146396_g;
        this.game_settings = game_settings;
    }
    
    @Override
    public void initGui() {
        this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
        this.field_146398_r = I18n.format("options.multiplayer.title", new Object[0]);
        final GameSettings.Options[] field_146399_a = ScreenChatOptions.field_146399_a;
        int n = 0;
        while (0 < field_146399_a.length) {
            final GameSettings.Options options = field_146399_a[0];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), ScreenChatOptions.width / 2 - 155 + 0, ScreenChatOptions.height / 6 + 0, options));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), ScreenChatOptions.width / 2 - 155 + 0, ScreenChatOptions.height / 6 + 0, options, this.game_settings.getKeyBinding(options)));
            }
            ++n;
            int n2 = 0;
            ++n2;
        }
        if (false == true) {
            ++n;
        }
        this.field_146397_s = ScreenChatOptions.height / 6 + 0;
        this.buttonList.add(new GuiButton(200, ScreenChatOptions.width / 2 - 100, ScreenChatOptions.height / 6 + 120, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                ScreenChatOptions.mc.gameSettings.saveOptions();
                ScreenChatOptions.mc.displayGuiScreen(this.field_146396_g);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_146401_i, ScreenChatOptions.width / 2, 20, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146398_r, ScreenChatOptions.width / 2, this.field_146397_s + 7, 16777215);
        super.drawScreen(n, n2, n3);
    }
}
