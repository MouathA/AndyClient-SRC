package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;

public class RealmsSettingsScreen extends RealmsScreen
{
    private RealmsConfigureWorldScreen configureWorldScreen;
    private RealmsServer serverData;
    private static final int BUTTON_CANCEL_ID = 0;
    private static final int BUTTON_DONE_ID = 1;
    private static final int NAME_EDIT_BOX = 2;
    private static final int DESC_EDIT_BOX = 3;
    private static final int BUTTON_OPEN_CLOSE_ID = 5;
    private final int COMPONENT_WIDTH = 212;
    private RealmsButton doneButton;
    private RealmsEditBox descEdit;
    private RealmsEditBox nameEdit;
    
    public RealmsSettingsScreen(final RealmsConfigureWorldScreen configureWorldScreen, final RealmsServer serverData) {
        this.configureWorldScreen = configureWorldScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void tick() {
        this.nameEdit.tick();
        this.descEdit.tick();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        final int n = this.width() / 2 - 106;
        this.buttonsAdd(this.doneButton = RealmsScreen.newButton(1, n - 2, RealmsConstants.row(12), 106, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.done")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 + 2, RealmsConstants.row(12), 106, 20, RealmsScreen.getLocalizedString("gui.cancel")));
        this.buttonsAdd(RealmsScreen.newButton(5, this.width() / 2 - 53, RealmsConstants.row(0), 106, 20, this.serverData.state.equals(RealmsServer.State.OPEN) ? RealmsScreen.getLocalizedString("mco.configure.world.buttons.close") : RealmsScreen.getLocalizedString("mco.configure.world.buttons.open")));
        (this.nameEdit = this.newEditBox(2, n, RealmsConstants.row(4), 212, 20)).setFocus(true);
        this.nameEdit.setMaxLength(32);
        if (this.serverData.getName() != null) {
            this.nameEdit.setValue(this.serverData.getName());
        }
        (this.descEdit = this.newEditBox(3, n, RealmsConstants.row(8), 212, 20)).setMaxLength(32);
        if (this.serverData.getDescription() != null) {
            this.descEdit.setValue(this.serverData.getDescription());
        }
    }
    
    @Override
    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        switch (realmsButton.id()) {
            case 0: {
                Realms.setScreen(this.configureWorldScreen);
                break;
            }
            case 1: {
                this.save();
                break;
            }
            case 5: {
                if (this.serverData.state.equals(RealmsServer.State.OPEN)) {
                    Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, RealmsScreen.getLocalizedString("mco.configure.world.close.question.line1"), RealmsScreen.getLocalizedString("mco.configure.world.close.question.line2"), true, 5));
                    break;
                }
                this.configureWorldScreen.openTheWorld(false, this);
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        switch (n) {
            case 5: {
                if (b) {
                    this.configureWorldScreen.closeTheWorld(this);
                    break;
                }
                Realms.setScreen(this);
                break;
            }
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        this.nameEdit.keyPressed(c, n);
        this.descEdit.keyPressed(c, n);
        Label_0122: {
            switch (n) {
                case 15: {
                    this.nameEdit.setFocus(!this.nameEdit.isFocused());
                    this.descEdit.setFocus(!this.descEdit.isFocused());
                    break Label_0122;
                }
                case 28:
                case 156: {
                    this.save();
                    break Label_0122;
                }
                case 1: {
                    Realms.setScreen(this.configureWorldScreen);
                    break;
                }
            }
            return;
        }
        this.doneButton.active(this.nameEdit.getValue() != null && !this.nameEdit.getValue().trim().equals(""));
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.descEdit.mouseClicked(n, n2, n3);
        this.nameEdit.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.settings.title"), this.width() / 2, 17, 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.name"), this.width() / 2 - 106, RealmsConstants.row(3), 10526880);
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.description"), this.width() / 2 - 106, RealmsConstants.row(7), 10526880);
        this.nameEdit.render();
        this.descEdit.render();
        super.render(n, n2, n3);
    }
    
    public void save() {
        this.configureWorldScreen.saveSettings(this.nameEdit.getValue(), this.descEdit.getValue());
    }
}
