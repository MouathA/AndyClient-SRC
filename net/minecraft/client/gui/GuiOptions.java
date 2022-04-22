package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.stream.*;
import java.io.*;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
    private static final GameSettings.Options[] field_146440_f;
    private final GuiScreen field_146441_g;
    private final GameSettings game_settings_1;
    private GuiButton field_175357_i;
    private GuiLockIconButton field_175356_r;
    protected String field_146442_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000700";
        field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
    }
    
    public GuiOptions(final GuiScreen field_146441_g, final GameSettings game_settings_1) {
        this.field_146442_a = "Options";
        this.field_146441_g = field_146441_g;
        this.game_settings_1 = game_settings_1;
    }
    
    @Override
    public void initGui() {
        this.field_146442_a = I18n.format("options.title", new Object[0]);
        final GameSettings.Options[] field_146440_f = GuiOptions.field_146440_f;
        while (0 < field_146440_f.length) {
            final GameSettings.Options options = field_146440_f[0];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, options));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, options, this.game_settings_1.getKeyBinding(options)));
            }
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        final Minecraft mc = GuiOptions.mc;
        Label_0403: {
            if (Minecraft.theWorld != null) {
                final Minecraft mc2 = GuiOptions.mc;
                this.field_175357_i = new GuiButton(108, GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, 150, 20, this.func_175355_a(Minecraft.theWorld.getDifficulty()));
                this.buttonList.add(this.field_175357_i);
                if (GuiOptions.mc.isSingleplayer()) {
                    final Minecraft mc3 = GuiOptions.mc;
                    if (!Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                        this.field_175357_i.func_175211_a(this.field_175357_i.getButtonWidth() - 20);
                        this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
                        this.buttonList.add(this.field_175356_r);
                        final GuiLockIconButton field_175356_r = this.field_175356_r;
                        final Minecraft mc4 = GuiOptions.mc;
                        field_175356_r.func_175229_b(Minecraft.theWorld.getWorldInfo().isDifficultyLocked());
                        this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
                        this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
                        break Label_0403;
                    }
                }
                this.field_175357_i.enabled = false;
            }
        }
        this.buttonList.add(new GuiButton(110, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
        this.buttonList.add(new GuiButton(8675309, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
            private static final String __OBFID;
            final GuiOptions this$0;
            
            @Override
            public void playPressSound(final SoundHandler soundHandler) {
                final SoundEventAccessorComposite randomSoundFromCategories = soundHandler.getRandomSoundFromCategories(SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER);
                if (randomSoundFromCategories != null) {
                    soundHandler.playSound(PositionedSoundRecord.createPositionedSoundRecord(randomSoundFromCategories.getSoundEventLocation(), 0.5f));
                }
            }
            
            static {
                __OBFID = "CL_00000701";
            }
        });
        this.buttonList.add(new GuiButton(106, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
        this.buttonList.add(new GuiButton(107, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
        this.buttonList.add(new GuiButton(101, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
        this.buttonList.add(new GuiButton(100, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
        this.buttonList.add(new GuiButton(102, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
        this.buttonList.add(new GuiButton(103, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title", new Object[0])));
        this.buttonList.add(new GuiButton(105, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
        this.buttonList.add(new GuiButton(104, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiOptions.width / 2 - 100, GuiOptions.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }
    
    public String func_175355_a(final EnumDifficulty enumDifficulty) {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        chatComponentText.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
        chatComponentText.appendText(": ");
        chatComponentText.appendSibling(new ChatComponentTranslation(enumDifficulty.getDifficultyResourceKey(), new Object[0]));
        return chatComponentText.getFormattedText();
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        GuiOptions.mc.displayGuiScreen(this);
        if (n == 109 && b) {
            final Minecraft mc = GuiOptions.mc;
            if (Minecraft.theWorld != null) {
                final Minecraft mc2 = GuiOptions.mc;
                Minecraft.theWorld.getWorldInfo().setDifficultyLocked(true);
                this.field_175356_r.func_175229_b(true);
                this.field_175356_r.enabled = false;
                this.field_175357_i.enabled = false;
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                this.game_settings_1.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 108) {
                final Minecraft mc = GuiOptions.mc;
                final WorldInfo worldInfo = Minecraft.theWorld.getWorldInfo();
                final Minecraft mc2 = GuiOptions.mc;
                worldInfo.setDifficulty(EnumDifficulty.getDifficultyEnum(Minecraft.theWorld.getDifficulty().getDifficultyId() + 1));
                final GuiButton field_175357_i = this.field_175357_i;
                final Minecraft mc3 = GuiOptions.mc;
                field_175357_i.displayString = this.func_175355_a(Minecraft.theWorld.getDifficulty());
            }
            if (guiButton.id == 109) {
                final Minecraft mc4 = GuiOptions.mc;
                final String formattedText = new ChatComponentTranslation("difficulty.lock.title", new Object[0]).getFormattedText();
                final String s = "difficulty.lock.question";
                final Object[] array = { null };
                final int n = 0;
                final Minecraft mc5 = GuiOptions.mc;
                array[n] = new ChatComponentTranslation(Minecraft.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]);
                mc4.displayGuiScreen(new GuiYesNo(this, formattedText, new ChatComponentTranslation(s, array).getFormattedText(), 109));
            }
            if (guiButton.id == 110) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiCustomizeSkin(this));
            }
            if (guiButton.id == 8675309) {
                GuiOptions.mc.entityRenderer.activateNextShader();
            }
            if (guiButton.id == 101) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
            }
            if (guiButton.id == 100) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
            }
            if (guiButton.id == 102) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, GuiOptions.mc.getLanguageManager()));
            }
            if (guiButton.id == 103) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
            }
            if (guiButton.id == 104) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
            }
            if (guiButton.id == 200) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(this.field_146441_g);
            }
            if (guiButton.id == 105) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            if (guiButton.id == 106) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
            }
            if (guiButton.id == 107) {
                GuiOptions.mc.gameSettings.saveOptions();
                final IStream twitchStream = GuiOptions.mc.getTwitchStream();
                if (twitchStream.func_152936_l() && twitchStream.func_152928_D()) {
                    GuiOptions.mc.displayGuiScreen(new GuiStreamOptions(this, this.game_settings_1));
                }
                else {
                    GuiStreamUnavailable.func_152321_a(this);
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_146442_a, GuiOptions.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
    }
}
