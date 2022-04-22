package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.*;
import org.lwjgl.input.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;
import com.mojang.realmsclient.gui.*;

public class RealmsCreateRealmScreen extends RealmsScreen
{
    private final RealmsServer server;
    private RealmsMainScreen lastScreen;
    private RealmsEditBox nameBox;
    private RealmsEditBox descriptionBox;
    private RealmsButton createButton;
    
    public RealmsCreateRealmScreen(final RealmsServer server, final RealmsMainScreen lastScreen) {
        this.server = server;
        this.lastScreen = lastScreen;
    }
    
    @Override
    public void tick() {
        this.nameBox.tick();
        this.descriptionBox.tick();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(this.createButton = RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() / 4 + 120 + 17, 97, 20, "\ue193\ue19d\ue191\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a"));
        this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 + 5, this.height() / 4 + 120 + 17, 95, 20, "\ue199\ue18b\ue197\ue1d0\ue19d\ue19f\ue190\ue19d\ue19b\ue192"));
        this.createButton.active(false);
        (this.nameBox = this.newEditBox(3, this.width() / 2 - 100, 65, 200, 20)).setFocus(true);
        this.descriptionBox = this.newEditBox(4, this.width() / 2 - 100, 115, 200, 20);
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
        if (realmsButton.id() == 1) {
            Realms.setScreen(this.lastScreen);
        }
        else if (realmsButton.id() == 0) {
            this.createWorld();
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        this.nameBox.keyPressed(c, n);
        this.descriptionBox.keyPressed(c, n);
        this.createButton.active(this.valid());
        switch (n) {
            case 15: {
                this.nameBox.setFocus(!this.nameBox.isFocused());
                this.descriptionBox.setFocus(!this.descriptionBox.isFocused());
                break;
            }
            case 28:
            case 156: {
                this.buttonClicked(this.createButton);
                break;
            }
            case 1: {
                Realms.setScreen(this.lastScreen);
                break;
            }
        }
    }
    
    private void createWorld() {
        if (this != null) {
            final RealmsResetWorldScreen realmsResetWorldScreen = new RealmsResetWorldScreen(this.lastScreen, this.server, this.lastScreen.newScreen(), "\ue193\ue19d\ue191\ue1d0\ue18d\ue19b\ue192\ue19b\ue19d\ue18a\ue1ad\ue19b\ue18c\ue188\ue19b\ue18c\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b", "\ue193\ue19d\ue191\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a\ue1d0\ue18d\ue18b\ue19c\ue18a\ue197\ue18a\ue192\ue19b", 10526880, "\ue193\ue19d\ue191\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a\ue1d0\ue18d\ue195\ue197\ue18e");
            realmsResetWorldScreen.setResetTitle("\ue193\ue19d\ue191\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a\ue1d0\ue18c\ue19b\ue18d\ue19b\ue18a\ue1d0\ue18a\ue197\ue18a\ue192\ue19b");
            final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsTasks.WorldCreationTask(this.server.id, this.nameBox.getValue(), this.descriptionBox.getValue(), realmsResetWorldScreen));
            screen.start();
            Realms.setScreen(screen);
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        this.nameBox.mouseClicked(n, n2, n3);
        this.descriptionBox.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString("\ue193\ue19d\ue191\ue1d0\ue18d\ue19b\ue192\ue19b\ue19d\ue18a\ue1ad\ue19b\ue18c\ue188\ue19b\ue18c\ue1d0\ue19d\ue18c\ue19b\ue19f\ue18a\ue19b", this.width() / 2, 11, 16777215);
        this.drawString("\ue193\ue19d\ue191\ue1d0\ue19d\ue191\ue190\ue198\ue197\ue199\ue18b\ue18c\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a\ue1d0\ue190\ue19f\ue193\ue19b", this.width() / 2 - 100, 52, 10526880);
        this.drawString("\ue193\ue19d\ue191\ue1d0\ue19d\ue191\ue190\ue198\ue197\ue199\ue18b\ue18c\ue19b\ue1d0\ue189\ue191\ue18c\ue192\ue19a\ue1d0\ue19a\ue19b\ue18d\ue19d\ue18c\ue197\ue18e\ue18a\ue197\ue191\ue190", this.width() / 2 - 100, 102, 10526880);
        this.nameBox.render();
        this.descriptionBox.render();
        super.render(n, n2, n3);
    }
}
