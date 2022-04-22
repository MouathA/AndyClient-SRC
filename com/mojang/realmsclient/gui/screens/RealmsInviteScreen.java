package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.client.*;
import org.apache.logging.log4j.*;

public class RealmsInviteScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private RealmsEditBox profileName;
    private RealmsServer serverData;
    private final RealmsConfigureWorldScreen configureScreen;
    private final RealmsScreen lastScreen;
    private final int BUTTON_INVITE_ID = 0;
    private final int BUTTON_CANCEL_ID = 1;
    private RealmsButton inviteButton;
    private final int PROFILENAME_EDIT_BOX = 2;
    private String errorMsg;
    private boolean showError;
    
    public RealmsInviteScreen(final RealmsConfigureWorldScreen configureScreen, final RealmsScreen lastScreen, final RealmsServer serverData) {
        this.configureScreen = configureScreen;
        this.lastScreen = lastScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void tick() {
        this.profileName.tick();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(this.inviteButton = RealmsScreen.newButton(0, this.width() / 2 - 100, RealmsConstants.row(10), RealmsScreen.getLocalizedString("mco.configure.world.buttons.invite")));
        this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 - 100, RealmsConstants.row(12), RealmsScreen.getLocalizedString("gui.cancel")));
        (this.profileName = this.newEditBox(2, this.width() / 2 - 100, RealmsConstants.row(2), 200, 20)).setFocus(true);
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
            case 1: {
                Realms.setScreen(this.lastScreen);
                break;
            }
            case 0: {
                final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                if (this.profileName.getValue() == null || this.profileName.getValue().isEmpty()) {
                    return;
                }
                final RealmsServer invite = realmsClient.invite(this.serverData.id, this.profileName.getValue());
                if (invite != null) {
                    this.serverData.players = invite.players;
                    Realms.setScreen(new RealmsPlayerScreen(this.configureScreen, this.serverData));
                }
                else {
                    this.showError(RealmsScreen.getLocalizedString("mco.configure.world.players.error"));
                }
                break;
            }
            default: {}
        }
    }
    
    private void showError(final String errorMsg) {
        this.showError = true;
        this.errorMsg = errorMsg;
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        this.profileName.keyPressed(c, n);
        if (n == 15) {
            if (this.profileName.isFocused()) {
                this.profileName.setFocus(false);
            }
            else {
                this.profileName.setFocus(true);
            }
        }
        if (n == 28 || n == 156) {
            this.buttonClicked(this.inviteButton);
        }
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        this.profileName.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.invite.profile.name"), this.width() / 2 - 100, RealmsConstants.row(1), 10526880);
        if (this.showError) {
            this.drawCenteredString(this.errorMsg, this.width() / 2, RealmsConstants.row(5), 16711680);
        }
        this.profileName.render();
        super.render(n, n2, n3);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
