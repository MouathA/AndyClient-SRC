package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.exception.*;
import net.minecraft.realms.*;

public class RealmsGenericErrorScreen extends RealmsScreen
{
    private final RealmsScreen nextScreen;
    private static final int OK_BUTTON_ID = 10;
    private String line1;
    private String line2;
    
    public RealmsGenericErrorScreen(final RealmsServiceException ex, final RealmsScreen nextScreen) {
        this.nextScreen = nextScreen;
        this.errorMessage(ex);
    }
    
    public RealmsGenericErrorScreen(final String s, final RealmsScreen nextScreen) {
        this.nextScreen = nextScreen;
        this.errorMessage(s);
    }
    
    public RealmsGenericErrorScreen(final String s, final String s2, final RealmsScreen nextScreen) {
        this.nextScreen = nextScreen;
        this.errorMessage(s, s2);
    }
    
    private void errorMessage(final RealmsServiceException ex) {
        if (ex.errorCode != -1) {
            this.line1 = "Realms (" + ex.errorCode + "):";
            final String string = "mco.errorMessage." + ex.errorCode;
            final String localizedString = RealmsScreen.getLocalizedString(string);
            this.line2 = (localizedString.equals(string) ? ex.errorMsg : localizedString);
        }
        else {
            this.line1 = "An error occurred (" + ex.httpResultCode + "):";
            this.line2 = ex.httpResponseContent;
        }
    }
    
    private void errorMessage(final String line2) {
        this.line1 = "An error occurred: ";
        this.line2 = line2;
    }
    
    private void errorMessage(final String line1, final String line2) {
        this.line1 = line1;
        this.line2 = line2;
    }
    
    @Override
    public void init() {
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(10, this.width() / 2 - 100, this.height() - 52, 200, 20, "Ok"));
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == 10) {
            Realms.setScreen(this.nextScreen);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.line1, this.width() / 2, 80, 16777215);
        this.drawCenteredString(this.line2, this.width() / 2, 100, 16711680);
        super.render(n, n2, n3);
    }
}
