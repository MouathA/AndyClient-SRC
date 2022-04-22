package com.mojang.realmsclient.gui.screens;

import org.lwjgl.input.*;
import java.util.*;
import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.*;
import com.mojang.realmsclient.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;
import com.mojang.realmsclient.gui.*;
import org.apache.logging.log4j.*;

public class RealmsConfigureWorldScreen extends RealmsScreenWithCallback
{
    private static final Logger LOGGER;
    private static final String ON_ICON_LOCATION = "realms:textures/gui/realms/on_icon.png";
    private static final String OFF_ICON_LOCATION = "realms:textures/gui/realms/off_icon.png";
    private static final String EXPIRED_ICON_LOCATION = "realms:textures/gui/realms/expired_icon.png";
    private static final String SLOT_FRAME_LOCATION = "realms:textures/gui/realms/slot_frame.png";
    private static final String EMPTY_FRAME_LOCATION = "realms:textures/gui/realms/empty_frame.png";
    private String toolTip;
    private final RealmsScreen lastScreen;
    private RealmsServer serverData;
    private long serverId;
    private int left_x;
    private int right_x;
    private int default_button_width;
    private int default_button_offset;
    private static final int BUTTON_BACK_ID = 0;
    private static final int BUTTON_PLAYERS_ID = 2;
    private static final int BUTTON_SETTINGS_ID = 3;
    private static final int BUTTON_SUBSCRIPTION_ID = 4;
    private static final int BUTTON_OPTIONS_ID = 5;
    private static final int BUTTON_BACKUP_ID = 6;
    private static final int BUTTON_RESET_WORLD_ID = 7;
    private static final int BUTTON_SWITCH_MINIGAME_ID = 8;
    private static final int SWITCH_SLOT_ID = 9;
    private static final int SWITCH_SLOT_ID_EMPTY = 10;
    private static final int SWITCH_SLOT_ID_RESULT = 11;
    private RealmsButton playersButton;
    private RealmsButton settingsButton;
    private RealmsButton subscriptionButton;
    private RealmsHideableButton optionsButton;
    private RealmsHideableButton backupButton;
    private RealmsHideableButton resetWorldButton;
    private RealmsHideableButton switchMinigameButton;
    private boolean stateChanged;
    private int hoveredSlot;
    private int animTick;
    private int clicks;
    private boolean hoveredActiveSlot;
    
    public RealmsConfigureWorldScreen(final RealmsScreen lastScreen, final long serverId) {
        this.default_button_width = 80;
        this.default_button_offset = 5;
        this.hoveredSlot = -1;
        this.clicks = 0;
        this.hoveredActiveSlot = false;
        this.lastScreen = lastScreen;
        this.serverId = serverId;
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
    }
    
    @Override
    public void init() {
        if (this.serverData == null) {
            this.fetchServerData(this.serverId);
        }
        this.left_x = this.width() / 2 - 187;
        this.right_x = this.width() / 2 + 190;
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(this.playersButton = RealmsScreen.newButton(2, this.centerButton(0, 3), RealmsConstants.row(0), this.default_button_width, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.players")));
        this.buttonsAdd(this.settingsButton = RealmsScreen.newButton(3, this.centerButton(1, 3), RealmsConstants.row(0), this.default_button_width, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.settings")));
        this.buttonsAdd(this.subscriptionButton = RealmsScreen.newButton(4, this.centerButton(2, 3), RealmsConstants.row(0), this.default_button_width, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.subscription")));
        this.buttonsAdd(this.optionsButton = new RealmsHideableButton(5, this.leftButton(0), RealmsConstants.row(13) - 5, this.default_button_width + 10, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.options")));
        this.buttonsAdd(this.backupButton = new RealmsHideableButton(6, this.leftButton(1), RealmsConstants.row(13) - 5, this.default_button_width + 10, 20, RealmsScreen.getLocalizedString("mco.configure.world.backup")));
        this.buttonsAdd(this.resetWorldButton = new RealmsHideableButton(7, this.leftButton(2), RealmsConstants.row(13) - 5, this.default_button_width + 10, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.resetworld")));
        this.buttonsAdd(this.switchMinigameButton = new RealmsHideableButton(8, this.leftButton(0), RealmsConstants.row(13) - 5, this.default_button_width + 20, 20, RealmsScreen.getLocalizedString("mco.configure.world.buttons.switchminigame")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.right_x - this.default_button_width + 8, RealmsConstants.row(13) - 5, this.default_button_width - 10, 20, RealmsScreen.getLocalizedString("gui.back")));
        this.backupButton.active(true);
        if (this.serverData == null) {
            this.hideMinigameButtons();
            this.hideRegularButtons();
            this.playersButton.active(false);
            this.settingsButton.active(false);
            this.subscriptionButton.active(false);
        }
        else {
            this.disableButtons();
            if (this != null) {
                this.hideRegularButtons();
            }
            else {
                this.hideMinigameButtons();
            }
        }
    }
    
    private int leftButton(final int n) {
        return this.left_x + n * (this.default_button_width + 10 + this.default_button_offset);
    }
    
    private int centerButton(final int n, final int n2) {
        return this.width() / 2 - (n2 * (this.default_button_width + this.default_button_offset) - this.default_button_offset) / 2 + n * (this.default_button_width + this.default_button_offset);
    }
    
    @Override
    public void tick() {
        ++this.animTick;
        --this.clicks;
        if (this.clicks < 0) {
            this.clicks = 0;
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.hoveredActiveSlot = false;
        this.hoveredSlot = -1;
        this.renderBackground();
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.worlds.title"), this.width() / 2, RealmsConstants.row(4), 16777215);
        super.render(n, n2, n3);
        if (this.serverData == null) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.title"), this.width() / 2, 17, 16777215);
            return;
        }
        final String name = this.serverData.getName();
        final int fontWidth = this.fontWidth(name);
        final int n4 = (this.serverData.state == RealmsServer.State.CLOSED) ? 10526880 : 8388479;
        final int fontWidth2 = this.fontWidth(RealmsScreen.getLocalizedString("mco.configure.world.title"));
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.title"), this.width() / 2 - fontWidth / 2 - 2, 17, 16777215);
        this.drawCenteredString(name, this.width() / 2 + fontWidth2 / 2 + 2, 17, n4);
        this.drawServerStatus(this.width() / 2 + fontWidth / 2 + fontWidth2 / 2 + 5, 17, n, n2);
        for (final Map.Entry<K, RealmsOptions> entry : this.serverData.slots.entrySet()) {
            if (entry.getValue().templateImage != null && entry.getValue().templateId != -1L) {
                this.drawSlotFrame(this.frame((int)entry.getKey()), RealmsConstants.row(5) + 5, n, n2, this.serverData.activeSlot == (int)entry.getKey() && this != null, entry.getValue().getSlotName((int)entry.getKey()), (int)entry.getKey(), entry.getValue().templateId, entry.getValue().templateImage, entry.getValue().empty);
            }
            else {
                this.drawSlotFrame(this.frame((int)entry.getKey()), RealmsConstants.row(5) + 5, n, n2, this.serverData.activeSlot == (int)entry.getKey() && this != null, entry.getValue().getSlotName((int)entry.getKey()), (int)entry.getKey(), -1L, null, entry.getValue().empty);
            }
        }
        this.drawSlotFrame(this.frame(4), RealmsConstants.row(5) + 5, n, n2, this.isMinigame(), "Minigame", 4, -1L, null, false);
        if (this != null) {
            this.drawString(RealmsScreen.getLocalizedString("mco.configure.current.minigame") + ": " + this.serverData.getMinigameName(), this.left_x + this.default_button_width + 20 + this.default_button_offset * 2, RealmsConstants.row(13), 16777215);
        }
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
    }
    
    private int frame(final int n) {
        return this.left_x + (n - 1) * 98;
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
        if (realmsButton instanceof RealmsHideableButton && !((RealmsHideableButton)realmsButton).getVisible()) {
            return;
        }
        switch (realmsButton.id()) {
            case 2: {
                Realms.setScreen(new RealmsPlayerScreen(this, this.serverData));
                break;
            }
            case 3: {
                Realms.setScreen(new RealmsSettingsScreen(this, this.serverData.clone()));
                break;
            }
            case 4: {
                Realms.setScreen(new RealmsSubscriptionInfoScreen(this, this.serverData.clone(), this.lastScreen));
                break;
            }
            case 0: {
                this.backButtonClicked();
                break;
            }
            case 8: {
                Realms.setScreen(new RealmsSelectWorldTemplateScreen(this, null, true));
                break;
            }
            case 6: {
                Realms.setScreen(new RealmsBackupScreen(this, this.serverData.clone()));
                break;
            }
            case 5: {
                Realms.setScreen(new RealmsSlotOptionsScreen(this, this.serverData.slots.get(this.serverData.activeSlot).clone(), this.serverData.worldType, this.serverData.activeSlot));
                break;
            }
            case 7: {
                Realms.setScreen(new RealmsResetWorldScreen(this, this.serverData.clone(), this.getNewScreen()));
                break;
            }
            default: {}
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            this.backButtonClicked();
        }
    }
    
    private void backButtonClicked() {
        if (this.stateChanged) {
            ((RealmsMainScreen)this.lastScreen).removeSelection();
        }
        Realms.setScreen(this.lastScreen);
    }
    
    private void fetchServerData(final long n) {
        new Thread(n) {
            final long val$worldId;
            final RealmsConfigureWorldScreen this$0;
            
            @Override
            public void run() {
                RealmsConfigureWorldScreen.access$002(this.this$0, RealmsClient.createRealmsClient().getOwnWorld(this.val$worldId));
                RealmsConfigureWorldScreen.access$100(this.this$0);
                if (RealmsConfigureWorldScreen.access$200(this.this$0)) {
                    RealmsConfigureWorldScreen.access$300(this.this$0);
                }
                else {
                    RealmsConfigureWorldScreen.access$400(this.this$0);
                }
            }
        }.start();
    }
    
    private void disableButtons() {
        this.playersButton.active(!this.serverData.expired);
        this.settingsButton.active(!this.serverData.expired);
        this.subscriptionButton.active(true);
        this.switchMinigameButton.active(!this.serverData.expired);
        this.optionsButton.active(!this.serverData.expired);
        this.resetWorldButton.active(!this.serverData.expired);
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        switch (n) {
            case 9: {
                if (b) {
                    this.switchSlot();
                    break;
                }
                Realms.setScreen(this);
                break;
            }
            case 10: {
                if (b) {
                    final RealmsResetWorldScreen screen = new RealmsResetWorldScreen(this, this.serverData, this.getNewScreen(), RealmsScreen.getLocalizedString("mco.configure.world.switch.slot"), RealmsScreen.getLocalizedString("mco.configure.world.switch.slot.subtitle"), 10526880, RealmsScreen.getLocalizedString("gui.cancel"));
                    screen.setSlot(this.hoveredSlot);
                    screen.setResetTitle(RealmsScreen.getLocalizedString("mco.create.world.reset.title"));
                    Realms.setScreen(screen);
                    break;
                }
                Realms.setScreen(this);
                break;
            }
            case 11: {
                Realms.setScreen(this);
                break;
            }
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            this.clicks += RealmsSharedConstants.TICKS_PER_SECOND / 3 + 1;
            if (this.hoveredSlot != -1) {
                if (this.hoveredSlot < 4) {
                    final String localizedString = RealmsScreen.getLocalizedString("mco.configure.world.slot.switch.question.line1");
                    final String localizedString2 = RealmsScreen.getLocalizedString("mco.configure.world.slot.switch.question.line2");
                    if (this.serverData.slots.get(this.hoveredSlot).empty) {
                        Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, localizedString, localizedString2, true, 10));
                    }
                    else {
                        Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, localizedString, localizedString2, true, 9));
                    }
                }
                else if (this != null && !this.serverData.expired) {
                    Realms.setScreen(new RealmsSelectWorldTemplateScreen(this, null, true, true));
                }
            }
            else if (this.clicks >= RealmsSharedConstants.TICKS_PER_SECOND / 2 && this.hoveredActiveSlot && (this.serverData.state == RealmsServer.State.OPEN || this.serverData.state == RealmsServer.State.CLOSED)) {
                if (this.serverData.state == RealmsServer.State.OPEN) {
                    ((RealmsMainScreen)this.lastScreen).play(this.serverData);
                }
                else {
                    this.openTheWorld(true, this);
                }
            }
            super.mouseClicked(n, n2, n3);
        }
    }
    
    protected void renderMousehoverTooltip(final String s, final int n, final int n2) {
        if (s == null) {
            return;
        }
        int n3 = n + 12;
        final int n4 = n2 - 12;
        final int fontWidth = this.fontWidth(s);
        if (n3 + fontWidth + 3 > this.right_x) {
            n3 = n3 - fontWidth - 20;
        }
        this.fillGradient(n3 - 3, n4 - 3, n3 + fontWidth + 3, n4 + 8 + 3, -1073741824, -1073741824);
        this.fontDrawShadow(s, n3, n4, 16777215);
    }
    
    private void drawServerStatus(final int n, final int n2, final int n3, final int n4) {
        if (this.serverData.expired) {
            this.drawExpired(n, n2, n3, n4);
        }
        else if (this.serverData.state == RealmsServer.State.ADMIN_LOCK) {
            this.drawLocked(n, n2, n3, n4, false);
        }
        else if (this.serverData.state == RealmsServer.State.CLOSED) {
            this.drawLocked(n, n2, n3, n4, true);
        }
        else if (this.serverData.state == RealmsServer.State.OPEN) {
            if (this.serverData.daysLeft < 7) {
                this.drawExpiring(n, n2, n3, n4, this.serverData.daysLeft);
            }
            else {
                this.drawOpen(n, n2, n3, n4);
            }
        }
    }
    
    private void drawExpiring(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (this.animTick % 20 < 10) {
            RealmsScreen.bind("realms:textures/gui/realms/on_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        }
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9) {
            if (n5 == 0) {
                this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.expires.soon");
            }
            else if (n5 == 1) {
                this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.expires.day");
            }
            else {
                this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.expires.days", n5);
            }
        }
    }
    
    private void drawOpen(final int n, final int n2, final int n3, final int n4) {
        RealmsScreen.bind("realms:textures/gui/realms/on_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.open");
        }
    }
    
    private void drawExpired(final int n, final int n2, final int n3, final int n4) {
        RealmsScreen.bind("realms:textures/gui/realms/expired_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.expired");
        }
    }
    
    private void drawLocked(final int n, final int n2, final int n3, final int n4, final boolean b) {
        RealmsScreen.bind("realms:textures/gui/realms/off_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (b && n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.closed");
        }
    }
    
    private void drawSlotFrame(final int n, final int n2, final int n3, final int n4, final boolean b, final String s, final int hoveredSlot, final long n5, final String s2, final boolean b2) {
        if (n3 >= n && n3 <= n + 80 && n4 >= n2 && n4 <= n2 + 80 && ((this != null && this.serverData.activeSlot != hoveredSlot) || (this != null && hoveredSlot != 4))) {
            if (hoveredSlot != 4 || !this.serverData.expired) {
                this.toolTip = (((this.hoveredSlot = hoveredSlot) == 4) ? RealmsScreen.getLocalizedString("mco.configure.world.slot.tooltip.minigame") : RealmsScreen.getLocalizedString("mco.configure.world.slot.tooltip"));
            }
        }
        if (n3 >= n && n3 <= n + 80 && n4 >= n2 && n4 <= n2 + 80 && ((this != null && this.serverData.activeSlot == hoveredSlot) || (this != null && hoveredSlot == 4)) && !this.serverData.expired && (this.serverData.state == RealmsServer.State.OPEN || this.serverData.state == RealmsServer.State.CLOSED)) {
            this.hoveredActiveSlot = true;
            this.toolTip = RealmsScreen.getLocalizedString("mco.configure.world.slot.tooltip.active");
        }
        if (b2) {
            RealmsScreen.bind("realms:textures/gui/realms/empty_frame.png");
        }
        else if (s2 != null && n5 != -1L) {
            RealmsTextureManager.bindWorldTemplate(String.valueOf(n5), s2);
        }
        else if (hoveredSlot == 1) {
            RealmsScreen.bind("textures/gui/title/background/panorama_0.png");
        }
        else if (hoveredSlot == 2) {
            RealmsScreen.bind("textures/gui/title/background/panorama_2.png");
        }
        else if (hoveredSlot == 3) {
            RealmsScreen.bind("textures/gui/title/background/panorama_3.png");
        }
        else {
            RealmsTextureManager.bindWorldTemplate(String.valueOf(this.serverData.minigameId), this.serverData.minigameImage);
        }
        if (!b) {
            GL11.glColor4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        else if (b) {
            final float n6 = 0.9f + 0.1f * RealmsMth.cos(this.animTick * 0.2f);
            GL11.glColor4f(n6, n6, n6, 1.0f);
        }
        RealmsScreen.blit(n + 3, n2 + 3, 0.0f, 0.0f, 74, 74, 74.0f, 74.0f);
        RealmsScreen.bind("realms:textures/gui/realms/slot_frame.png");
        if (this.hoveredSlot == hoveredSlot) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (!b) {
            GL11.glColor4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsScreen.blit(n, n2, 0.0f, 0.0f, 80, 80, 80.0f, 80.0f);
        this.drawCenteredString(s, n + 40, n2 + 66, 16777215);
    }
    
    private void hideRegularButtons() {
        this.optionsButton.setVisible(false);
        this.backupButton.setVisible(false);
        this.resetWorldButton.setVisible(false);
    }
    
    private void hideMinigameButtons() {
        this.switchMinigameButton.setVisible(false);
    }
    
    private void showRegularButtons() {
        this.optionsButton.setVisible(true);
        this.backupButton.setVisible(true);
        this.resetWorldButton.setVisible(true);
    }
    
    private void showMinigameButtons() {
        this.switchMinigameButton.setVisible(true);
    }
    
    public void saveSlotSettings() {
        RealmsClient.createRealmsClient().updateSlot(this.serverData.id, this.serverData.slots.get(this.serverData.activeSlot));
        Realms.setScreen(this);
    }
    
    public void saveSlotSettings(final RealmsOptions realmsOptions) {
        final RealmsOptions realmsOptions2 = this.serverData.slots.get(this.serverData.activeSlot);
        realmsOptions.templateId = realmsOptions2.templateId;
        realmsOptions.templateImage = realmsOptions2.templateImage;
        this.serverData.slots.put(this.serverData.activeSlot, realmsOptions);
        this.saveSlotSettings();
    }
    
    public void saveServerData() {
        RealmsClient.createRealmsClient().update(this.serverData.id, this.serverData.getName(), this.serverData.getDescription());
        Realms.setScreen(this);
    }
    
    public void saveSettings(final String name, final String s) {
        final String description = (s == null || s.trim().equals("")) ? null : s;
        this.serverData.setName(name);
        this.serverData.setDescription(description);
        this.saveServerData();
    }
    
    public void openTheWorld(final boolean b, final RealmsScreen realmsScreen) {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(realmsScreen, new RealmsTasks.OpenServerTask(this.serverData, this, this.lastScreen, b));
        screen.start();
        Realms.setScreen(screen);
    }
    
    public void closeTheWorld(final RealmsScreen realmsScreen) {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(realmsScreen, new RealmsTasks.CloseServerTask(this.serverData, this));
        screen.start();
        Realms.setScreen(screen);
    }
    
    public void stateChanged() {
        this.stateChanged = true;
    }
    
    void callback(final WorldTemplate worldTemplate) {
        if (worldTemplate == null) {
            return;
        }
        if (worldTemplate.minigame) {
            this.switchMinigame(worldTemplate);
        }
    }
    
    private void switchSlot() {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsTasks.SwitchSlotTask(this.serverData.id, this.hoveredSlot, this.getNewScreen(), 11));
        screen.start();
        Realms.setScreen(screen);
    }
    
    private void switchMinigame(final WorldTemplate worldTemplate) {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsTasks.SwitchMinigameTask(this.serverData.id, worldTemplate, this.getNewScreen()));
        screen.start();
        Realms.setScreen(screen);
    }
    
    public RealmsConfigureWorldScreen getNewScreen() {
        return new RealmsConfigureWorldScreen(this.lastScreen, this.serverId);
    }
    
    @Override
    void callback(final Object o) {
        this.callback((WorldTemplate)o);
    }
    
    static RealmsServer access$002(final RealmsConfigureWorldScreen realmsConfigureWorldScreen, final RealmsServer serverData) {
        return realmsConfigureWorldScreen.serverData = serverData;
    }
    
    static void access$100(final RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        realmsConfigureWorldScreen.disableButtons();
    }
    
    static boolean access$200(final RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        return realmsConfigureWorldScreen.isMinigame();
    }
    
    static void access$300(final RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        realmsConfigureWorldScreen.showMinigameButtons();
    }
    
    static void access$400(final RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        realmsConfigureWorldScreen.showRegularButtons();
    }
    
    static Logger access$500() {
        return RealmsConfigureWorldScreen.LOGGER;
    }
    
    static RealmsScreen access$600(final RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        return realmsConfigureWorldScreen.lastScreen;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
