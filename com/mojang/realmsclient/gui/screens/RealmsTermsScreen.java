package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.gui.*;
import java.awt.*;
import java.awt.datatransfer.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class RealmsTermsScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final int BUTTON_AGREE_ID = 1;
    private static final int BUTTON_DISAGREE_ID = 2;
    private final RealmsScreen lastScreen;
    private final RealmsServer realmsServer;
    private RealmsButton agreeButton;
    private boolean onLink;
    private String realmsToSUrl;
    
    public RealmsTermsScreen(final RealmsScreen lastScreen, final RealmsServer realmsServer) {
        this.onLink = false;
        this.realmsToSUrl = "https://minecraft.net/realms/terms";
        this.lastScreen = lastScreen;
        this.realmsServer = realmsServer;
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        final int n = this.width() / 4;
        final int n2 = this.width() / 4 - 2;
        final int n3 = this.width() / 2 + 4;
        this.buttonsAdd(this.agreeButton = RealmsScreen.newButton(1, n, RealmsConstants.row(12), n2, 20, RealmsScreen.getLocalizedString("mco.terms.buttons.agree")));
        this.buttonsAdd(RealmsScreen.newButton(2, n3, RealmsConstants.row(12), n2, 20, RealmsScreen.getLocalizedString("mco.terms.buttons.disagree")));
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
            case 2: {
                Realms.setScreen(this.lastScreen);
                break;
            }
            case 1: {
                this.agreedToTos();
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    private void agreedToTos() {
        RealmsClient.createRealmsClient().agreeToTos();
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsTasks.RealmsConnectTask(this.lastScreen, this.realmsServer));
        screen.start();
        Realms.setScreen(screen);
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        if (this.onLink) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.realmsToSUrl), null);
            RealmsUtil.browseTo(this.realmsToSUrl);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.terms.title"), this.width() / 2, 17, 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.terms.sentence.1"), this.width() / 2 - 120, RealmsConstants.row(5), 16777215);
        final int fontWidth = this.fontWidth(RealmsScreen.getLocalizedString("mco.terms.sentence.1"));
        final int n4 = this.width() / 2 - 121 + fontWidth;
        final int row = RealmsConstants.row(5);
        final int n5 = n4 + this.fontWidth("mco.terms.sentence.2") + 1;
        final int n6 = row + 1 + this.fontLineHeight();
        if (n4 <= n && n <= n5 && row <= n2 && n2 <= n6) {
            this.onLink = true;
            this.drawString(" " + RealmsScreen.getLocalizedString("mco.terms.sentence.2"), this.width() / 2 - 120 + fontWidth, RealmsConstants.row(5), 7107012);
        }
        else {
            this.onLink = false;
            this.drawString(" " + RealmsScreen.getLocalizedString("mco.terms.sentence.2"), this.width() / 2 - 120 + fontWidth, RealmsConstants.row(5), 3368635);
        }
        super.render(n, n2, n3);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
