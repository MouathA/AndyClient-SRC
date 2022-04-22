package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import com.mojang.realmsclient.client.*;
import java.util.*;
import java.text.*;
import net.minecraft.realms.*;
import java.awt.*;
import java.awt.datatransfer.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class RealmsSubscriptionInfoScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private final RealmsScreen lastScreen;
    private final RealmsServer serverData;
    private final RealmsScreen mainScreen;
    private final int BUTTON_BACK_ID = 0;
    private final int BUTTON_DELETE_ID = 1;
    private int daysLeft;
    private String startDate;
    private Subscription.SubscriptionType type;
    private final String PURCHASE_LINK = "https://account.mojang.com/buy/realms";
    private boolean onLink;
    
    public RealmsSubscriptionInfoScreen(final RealmsScreen lastScreen, final RealmsServer serverData, final RealmsScreen mainScreen) {
        this.lastScreen = lastScreen;
        this.serverData = serverData;
        this.mainScreen = mainScreen;
    }
    
    @Override
    public void init() {
        this.getSubscription(this.serverData.id);
        Keyboard.enableRepeatEvents(true);
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, RealmsConstants.row(12), RealmsScreen.getLocalizedString("gui.back")));
        if (this.serverData.expired) {
            this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 - 100, RealmsConstants.row(10), RealmsScreen.getLocalizedString("mco.configure.world.delete.button")));
        }
    }
    
    private void getSubscription(final long n) {
        final Subscription subscription = RealmsClient.createRealmsClient().subscriptionFor(n);
        this.daysLeft = subscription.daysLeft;
        this.startDate = this.localPresentation(subscription.startDate);
        this.type = subscription.type;
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (n == 1 && b) {
            new Thread("Realms-delete-realm") {
                final RealmsSubscriptionInfoScreen this$0;
                
                @Override
                public void run() {
                    RealmsClient.createRealmsClient().deleteWorld(RealmsSubscriptionInfoScreen.access$000(this.this$0).id);
                    Realms.setScreen(RealmsSubscriptionInfoScreen.access$200(this.this$0));
                }
            }.start();
        }
        Realms.setScreen(this);
    }
    
    private String localPresentation(final long timeInMillis) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTimeInMillis(timeInMillis);
        return DateFormat.getDateTimeInstance().format(gregorianCalendar.getTime());
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
        else if (realmsButton.id() == 1) {
            Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Warning, RealmsScreen.getLocalizedString("mco.configure.world.delete.question.line1"), RealmsScreen.getLocalizedString("mco.configure.world.delete.question.line2"), true, 1));
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
            final String string = "https://account.mojang.com/buy/realms?sid=" + this.serverData.remoteSubscriptionId + "&pid=" + Realms.getUUID();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string), null);
            RealmsUtil.browseTo(string);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        final int n4 = this.width() / 2 - 100;
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.subscription.title"), this.width() / 2, 17, 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.subscription.start"), n4, RealmsConstants.row(0), 10526880);
        this.drawString(this.startDate, n4, RealmsConstants.row(1), 16777215);
        if (this.type == Subscription.SubscriptionType.NORMAL) {
            this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.subscription.timeleft"), n4, RealmsConstants.row(3), 10526880);
        }
        else if (this.type == Subscription.SubscriptionType.RECURRING) {
            this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.subscription.recurring.daysleft"), n4, RealmsConstants.row(3), 10526880);
        }
        this.drawString(this.daysLeftPresentation(this.daysLeft), n4, RealmsConstants.row(4), 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.subscription.extendHere"), n4, RealmsConstants.row(6), 10526880);
        final int row = RealmsConstants.row(7);
        final int fontWidth = this.fontWidth("https://account.mojang.com/buy/realms");
        final int n5 = this.width() / 2 - fontWidth / 2 - 1;
        final int n6 = row - 1;
        final int n7 = n5 + fontWidth + 1;
        final int n8 = row + 1 + this.fontLineHeight();
        if (n5 <= n && n <= n7 && n6 <= n2 && n2 <= n8) {
            this.onLink = true;
            this.drawString("https://account.mojang.com/buy/realms", this.width() / 2 - fontWidth / 2, row, 7107012);
        }
        else {
            this.onLink = false;
            this.drawString("https://account.mojang.com/buy/realms", this.width() / 2 - fontWidth / 2, row, 3368635);
        }
        super.render(n, n2, n3);
    }
    
    private String daysLeftPresentation(final int n) {
        if (n == -1) {
            return "Expired";
        }
        if (n <= 1) {
            return RealmsScreen.getLocalizedString("mco.configure.world.subscription.less_than_a_day");
        }
        final int n2 = n / 30;
        final int n3 = n % 30;
        final StringBuilder sb = new StringBuilder();
        if (n2 > 0) {
            sb.append(n2).append(" ");
            if (n2 == 1) {
                sb.append(RealmsScreen.getLocalizedString("mco.configure.world.subscription.month").toLowerCase());
            }
            else {
                sb.append(RealmsScreen.getLocalizedString("mco.configure.world.subscription.months").toLowerCase());
            }
        }
        if (n3 > 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(n3).append(" ");
            if (n3 == 1) {
                sb.append(RealmsScreen.getLocalizedString("mco.configure.world.subscription.day").toLowerCase());
            }
            else {
                sb.append(RealmsScreen.getLocalizedString("mco.configure.world.subscription.days").toLowerCase());
            }
        }
        return sb.toString();
    }
    
    static RealmsServer access$000(final RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.serverData;
    }
    
    static Logger access$100() {
        return RealmsSubscriptionInfoScreen.LOGGER;
    }
    
    static RealmsScreen access$200(final RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.mainScreen;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
