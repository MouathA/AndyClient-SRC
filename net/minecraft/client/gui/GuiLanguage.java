package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiLanguage extends GuiScreen
{
    protected GuiScreen field_146453_a;
    private List field_146450_f;
    private final GameSettings game_settings_3;
    private final LanguageManager field_146454_h;
    private GuiOptionButton field_146455_i;
    private GuiOptionButton field_146452_r;
    private static final String __OBFID;
    
    public GuiLanguage(final GuiScreen field_146453_a, final GameSettings game_settings_3, final LanguageManager field_146454_h) {
        this.field_146453_a = field_146453_a;
        this.game_settings_3 = game_settings_3;
        this.field_146454_h = field_146454_h;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(this.field_146455_i = new GuiOptionButton(100, GuiLanguage.width / 2 - 155, GuiLanguage.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.field_146452_r = new GuiOptionButton(6, GuiLanguage.width / 2 - 155 + 160, GuiLanguage.height - 38, I18n.format("gui.done", new Object[0])));
        (this.field_146450_f = new List(GuiLanguage.mc)).registerScrollButtons(7, 8);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146450_f.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 5: {
                    break;
                }
                case 6: {
                    GuiLanguage.mc.displayGuiScreen(this.field_146453_a);
                    break;
                }
                case 100: {
                    if (guiButton instanceof GuiOptionButton) {
                        this.game_settings_3.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                        guiButton.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                        final ScaledResolution scaledResolution = new ScaledResolution(GuiLanguage.mc, GuiLanguage.mc.displayWidth, GuiLanguage.mc.displayHeight);
                        this.setWorldAndResolution(GuiLanguage.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
                        break;
                    }
                    break;
                }
                default: {
                    this.field_146450_f.actionPerformed(guiButton);
                    break;
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_146450_f.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), GuiLanguage.width / 2, 16, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", GuiLanguage.width / 2, GuiLanguage.height - 56, 8421504);
        super.drawScreen(n, n2, n3);
    }
    
    static LanguageManager access$0(final GuiLanguage guiLanguage) {
        return guiLanguage.field_146454_h;
    }
    
    static GameSettings access$1(final GuiLanguage guiLanguage) {
        return guiLanguage.game_settings_3;
    }
    
    static GuiOptionButton access$2(final GuiLanguage guiLanguage) {
        return guiLanguage.field_146452_r;
    }
    
    static GuiOptionButton access$3(final GuiLanguage guiLanguage) {
        return guiLanguage.field_146455_i;
    }
    
    static {
        __OBFID = "CL_00000698";
    }
    
    class List extends GuiSlot
    {
        private final java.util.List field_148176_l;
        private final Map field_148177_m;
        private static final String __OBFID;
        final GuiLanguage this$0;
        
        public List(final GuiLanguage this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
            this.field_148176_l = Lists.newArrayList();
            this.field_148177_m = Maps.newHashMap();
            for (final Language language : GuiLanguage.access$0(this$0).getLanguages()) {
                this.field_148177_m.put(language.getLanguageCode(), language);
                this.field_148176_l.add(language.getLanguageCode());
            }
        }
        
        @Override
        protected int getSize() {
            return this.field_148176_l.size();
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            final Language currentLanguage = this.field_148177_m.get(this.field_148176_l.get(n));
            GuiLanguage.access$0(this.this$0).setCurrentLanguage(currentLanguage);
            GameSettings.language = currentLanguage.getLanguageCode();
            this.mc.refreshResources();
            this.this$0.fontRendererObj.setUnicodeFlag(GuiLanguage.access$0(this.this$0).isCurrentLocaleUnicode() || GuiLanguage.access$1(this.this$0).forceUnicodeFont);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).isCurrentLanguageBidirectional());
            GuiLanguage.access$2(this.this$0).displayString = I18n.format("gui.done", new Object[0]);
            GuiLanguage.access$3(this.this$0).displayString = GuiLanguage.access$1(this.this$0).getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.access$1(this.this$0).saveOptions();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return this.field_148176_l.get(n).equals(GuiLanguage.access$0(this.this$0).getCurrentLanguage().getLanguageCode());
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * 18;
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.setBidiFlag(true);
            Gui.drawCenteredString(this.this$0.fontRendererObj, this.field_148177_m.get(this.field_148176_l.get(n)).toString(), this.width / 2, n3 + 1, 16777215);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).getCurrentLanguage().isBidirectional());
        }
        
        static {
            __OBFID = "CL_00000699";
        }
    }
}
