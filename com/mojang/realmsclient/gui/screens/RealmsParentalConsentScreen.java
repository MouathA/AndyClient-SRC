package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;
import org.lwjgl.opengl.*;

public class RealmsParentalConsentScreen extends RealmsScreen
{
    private final RealmsScreen nextScreen;
    private static final int BUTTON_BACK_ID = 0;
    private static final int BUTTON_OK_ID = 1;
    private final String line1 = "Recently, Mojang was acquired by Microsoft. Microsoft implements";
    private final String line2 = "certain procedures to help protect children and their privacy,";
    private final String line3 = "including complying with the Children\u2019s Online Privacy Protection Act (COPPA)";
    private final String line4 = "You may need to obtain parental consent before accessing your Realms account.";
    private boolean onLink;
    
    public RealmsParentalConsentScreen(final RealmsScreen nextScreen) {
        this.onLink = false;
        this.nextScreen = nextScreen;
    }
    
    @Override
    public void init() {
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 - 100, RealmsConstants.row(11), 200, 20, "Go to accounts page"));
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, RealmsConstants.row(13), 200, 20, "Back"));
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        switch (realmsButton.id()) {
            case 1: {
                RealmsUtil.browseTo("https://accounts.mojang.com/me/verify/" + Realms.getUUID());
                break;
            }
            case 0: {
                Realms.setScreen(this.nextScreen);
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.onLink) {
            RealmsUtil.browseTo("http://www.ftc.gov/enforcement/rules/rulemaking-regulatory-reform-proceedings/childrens-online-privacy-protection-rule");
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString("Recently, Mojang was acquired by Microsoft. Microsoft implements", this.width() / 2, 30, 16777215);
        this.drawCenteredString("certain procedures to help protect children and their privacy,", this.width() / 2, 45, 16777215);
        this.drawCenteredString("including complying with the Children\u2019s Online Privacy Protection Act (COPPA)", this.width() / 2, 60, 16777215);
        this.drawCenteredString("You may need to obtain parental consent before accessing your Realms account.", this.width() / 2, 120, 16777215);
        this.renderLink(n, n2);
        super.render(n, n2, n3);
    }
    
    private void renderLink(final int n, final int n2) {
        final String localizedString = RealmsScreen.getLocalizedString("Read more about COPPA");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int fontWidth = this.fontWidth(localizedString);
        final int n3 = this.width() / 2 - fontWidth / 2;
        final int n4 = n3 + fontWidth + 1;
        final int n5 = 75 + this.fontLineHeight();
        GL11.glTranslatef((float)n3, 75, 0.0f);
        if (n3 <= n && n <= n4 && 75 <= n2 && n2 <= n5) {
            this.onLink = true;
            this.drawString(localizedString, 0, 0, 7107012);
        }
        else {
            this.onLink = false;
            this.drawString(localizedString, 0, 0, 3368635);
        }
    }
}
