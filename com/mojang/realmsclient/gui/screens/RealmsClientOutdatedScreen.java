package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;

public class RealmsClientOutdatedScreen extends RealmsScreen
{
    private static final int BUTTON_BACK_ID = 0;
    private final RealmsScreen lastScreen;
    private final boolean outdated;
    
    public RealmsClientOutdatedScreen(final RealmsScreen lastScreen, final boolean outdated) {
        this.lastScreen = lastScreen;
        this.outdated = outdated;
    }
    
    @Override
    public void init() {
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, RealmsConstants.row(12), "Back"));
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        final String s = this.outdated ? RealmsScreen.getLocalizedString("mco.client.outdated.title") : RealmsScreen.getLocalizedString("mco.client.incompatible.title");
        final String s2 = this.outdated ? RealmsScreen.getLocalizedString("mco.client.outdated.msg") : RealmsScreen.getLocalizedString("mco.client.incompatible.msg");
        this.drawCenteredString(s, this.width() / 2, RealmsConstants.row(3), 16711680);
        this.drawCenteredString(s2, this.width() / 2, RealmsConstants.row(5), 16777215);
        super.render(n, n2, n3);
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 28 || n == 156 || n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
}
