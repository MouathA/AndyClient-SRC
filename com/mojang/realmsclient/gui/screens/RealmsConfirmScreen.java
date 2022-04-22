package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;
import java.util.*;

public class RealmsConfirmScreen extends RealmsScreen
{
    protected RealmsScreen parent;
    protected String title1;
    private String title2;
    protected String yesButton;
    protected String noButton;
    protected int id;
    private int delayTicker;
    
    public RealmsConfirmScreen(final RealmsScreen parent, final String title1, final String title2, final int id) {
        this.parent = parent;
        this.title1 = title1;
        this.title2 = title2;
        this.id = id;
        this.yesButton = RealmsScreen.getLocalizedString("gui.yes");
        this.noButton = RealmsScreen.getLocalizedString("gui.no");
    }
    
    public RealmsConfirmScreen(final RealmsScreen parent, final String title1, final String title2, final String yesButton, final String noButton, final int id) {
        this.parent = parent;
        this.title1 = title1;
        this.title2 = title2;
        this.yesButton = yesButton;
        this.noButton = noButton;
        this.id = id;
    }
    
    @Override
    public void init() {
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 105, RealmsConstants.row(9), 100, 20, this.yesButton));
        this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 + 5, RealmsConstants.row(9), 100, 20, this.noButton));
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        this.parent.confirmResult(realmsButton.id() == 0, this.id);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.title1, this.width() / 2, RealmsConstants.row(3), 16777215);
        this.drawCenteredString(this.title2, this.width() / 2, RealmsConstants.row(5), 16777215);
        super.render(n, n2, n3);
    }
    
    public void setDelay(final int delayTicker) {
        this.delayTicker = delayTicker;
        final Iterator<RealmsButton> iterator = this.buttons().iterator();
        while (iterator.hasNext()) {
            iterator.next().active(false);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        final int delayTicker = this.delayTicker - 1;
        this.delayTicker = delayTicker;
        if (delayTicker == 0) {
            final Iterator<RealmsButton> iterator = (Iterator<RealmsButton>)this.buttons().iterator();
            while (iterator.hasNext()) {
                iterator.next().active(true);
            }
        }
    }
}
