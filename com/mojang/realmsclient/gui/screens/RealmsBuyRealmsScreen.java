package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import com.mojang.realmsclient.client.*;
import net.minecraft.realms.*;
import java.awt.*;
import java.awt.datatransfer.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class RealmsBuyRealmsScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private RealmsScreen lastScreen;
    private RealmsState realmsStatus;
    private boolean onLink;
    
    public RealmsBuyRealmsScreen(final RealmsScreen lastScreen) {
        this.onLink = false;
        this.lastScreen = lastScreen;
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 1, RealmsConstants.row(12), 212, 20, RealmsScreen.getLocalizedString("gui.back")));
        this.fetchMessage();
    }
    
    private void fetchMessage() {
        new Thread("Realms-stat-message", RealmsClient.createRealmsClient()) {
            final RealmsClient val$client;
            final RealmsBuyRealmsScreen this$0;
            
            @Override
            public void run() {
                RealmsBuyRealmsScreen.access$002(this.this$0, this.val$client.fetchRealmsState());
            }
        }.start();
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
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        if (this.onLink) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.realmsStatus.getBuyLink()), null);
            RealmsUtil.browseTo(this.realmsStatus.getBuyLink());
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.buy.realms.title"), this.width() / 2, 17, 16777215);
        if (this.realmsStatus == null) {
            return;
        }
        final String[] split = this.realmsStatus.getStatusMessage().split("\n");
        while (0 < split.length) {
            this.drawCenteredString(split[0], this.width() / 2, RealmsConstants.row(1), 10526880);
            final int n4;
            n4 += 2;
            int fontWidth = 0;
            ++fontWidth;
        }
        if (this.realmsStatus.getBuyLink() != null) {
            final String buyLink = this.realmsStatus.getBuyLink();
            final int row = RealmsConstants.row(2);
            final int fontWidth = this.fontWidth(buyLink);
            final int n5 = this.width() / 2 - 0 - 1;
            final int n6 = row - 1;
            final int n7 = n5 + 0 + 1;
            final int n8 = row + 1 + this.fontLineHeight();
            if (n5 <= n && n <= n7 && n6 <= n2 && n2 <= n8) {
                this.onLink = true;
                this.drawString(buyLink, this.width() / 2 - 0, row, 7107012);
            }
            else {
                this.onLink = false;
                this.drawString(buyLink, this.width() / 2 - 0, row, 3368635);
            }
        }
        super.render(n, n2, n3);
    }
    
    static RealmsState access$002(final RealmsBuyRealmsScreen realmsBuyRealmsScreen, final RealmsState realmsStatus) {
        return realmsBuyRealmsScreen.realmsStatus = realmsStatus;
    }
    
    static Logger access$100() {
        return RealmsBuyRealmsScreen.LOGGER;
    }
    
    static RealmsScreen access$200(final RealmsBuyRealmsScreen realmsBuyRealmsScreen) {
        return realmsBuyRealmsScreen.lastScreen;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
