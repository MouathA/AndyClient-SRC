package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.*;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.Options[] optionsArr;
    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    public KeyBinding buttonId;
    public long time;
    private GuiKeyBindingList keyBindingList;
    private GuiButton buttonReset;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000736";
        optionsArr = new GameSettings.Options[] { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
    }
    
    public GuiControls(final GuiScreen parentScreen, final GameSettings options) {
        this.screenTitle = "Controls";
        this.buttonId = null;
        this.parentScreen = parentScreen;
        this.options = options;
    }
    
    @Override
    public void initGui() {
        this.keyBindingList = new GuiKeyBindingList(this, GuiControls.mc);
        this.buttonList.add(new GuiButton(200, GuiControls.width / 2 - 155, GuiControls.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.buttonReset = new GuiButton(201, GuiControls.width / 2 - 155 + 160, GuiControls.height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
        this.screenTitle = I18n.format("controls.title", new Object[0]);
        final GameSettings.Options[] optionsArr = GuiControls.optionsArr;
        while (0 < optionsArr.length) {
            final GameSettings.Options options = optionsArr[0];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), GuiControls.width / 2 - 155 + 0, 18, options));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), GuiControls.width / 2 - 155 + 0, 18, options, this.options.getKeyBinding(options)));
            }
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.keyBindingList.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 200) {
            GuiControls.mc.displayGuiScreen(this.parentScreen);
        }
        else if (guiButton.id == 201) {
            final KeyBinding[] keyBindings = GuiControls.mc.gameSettings.keyBindings;
            while (0 < keyBindings.length) {
                final KeyBinding keyBinding = keyBindings[0];
                keyBinding.setKeyCode(keyBinding.getKeyCodeDefault());
                int n = 0;
                ++n;
            }
        }
        else if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
            this.options.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
            guiButton.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (this.buttonId != null) {
            this.options.setOptionKeyBinding(this.buttonId, -100 + n3);
            this.buttonId = null;
        }
        else if (n3 != 0 || !this.keyBindingList.func_148179_a(n, n2, n3)) {
            super.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (n3 != 0 || !this.keyBindingList.func_148181_b(n, n2, n3)) {
            super.mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.buttonId != null) {
            if (n == 1) {
                this.options.setOptionKeyBinding(this.buttonId, 0);
            }
            else if (n != 0) {
                this.options.setOptionKeyBinding(this.buttonId, n);
            }
            else if (c > '\0') {
                this.options.setOptionKeyBinding(this.buttonId, c + '\u0100');
            }
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
        }
        else {
            super.keyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, this.screenTitle, GuiControls.width / 2, 8, 16777215);
        final KeyBinding[] keyBindings = this.options.keyBindings;
        while (0 < keyBindings.length) {
            final KeyBinding keyBinding = keyBindings[0];
            if (keyBinding.getKeyCode() != keyBinding.getKeyCodeDefault()) {
                break;
            }
            int n4 = 0;
            ++n4;
        }
        this.buttonReset.enabled = true;
        super.drawScreen(n, n2, n3);
    }
}
