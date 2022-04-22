package com.mojang.realmsclient;

import java.util.concurrent.locks.*;
import com.google.common.collect.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.dto.*;
import java.util.*;
import org.lwjgl.opengl.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import com.mojang.realmsclient.gui.screens.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.gui.*;

public class RealmsMainScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static boolean overrideConfigure;
    private static boolean stageEnabled;
    private boolean dontSetConnectedToRealms;
    protected static final int BUTTON_BACK_ID = 0;
    protected static final int BUTTON_PLAY_ID = 1;
    protected static final int BUTTON_CONFIGURE_ID = 2;
    protected static final int BUTTON_LEAVE_ID = 3;
    protected static final int BUTTON_BUY_ID = 4;
    protected static final int RESOURCEPACK_ID = 100;
    private RealmsServer resourcePackServer;
    private static final String ON_ICON_LOCATION = "realms:textures/gui/realms/on_icon.png";
    private static final String OFF_ICON_LOCATION = "realms:textures/gui/realms/off_icon.png";
    private static final String EXPIRED_ICON_LOCATION = "realms:textures/gui/realms/expired_icon.png";
    private static final String INVITATION_ICONS_LOCATION = "realms:textures/gui/realms/invitation_icons.png";
    private static final String INVITE_ICON_LOCATION = "realms:textures/gui/realms/invite_icon.png";
    private static final String WORLDICON_LOCATION = "realms:textures/gui/realms/world_icon.png";
    private static final String LOGO_LOCATION = "realms:textures/gui/title/realms.png";
    private static RealmsDataFetcher realmsDataFetcher;
    private static RealmsServerStatusPinger statusPinger;
    private static final ThreadPoolExecutor THREAD_POOL;
    private RealmsScreen lastScreen;
    private ServerSelectionList serverSelectionList;
    private long selectedServerId;
    private RealmsButton configureButton;
    private RealmsButton leaveButton;
    private RealmsButton playButton;
    private RealmsButton buyButton;
    private String toolTip;
    private List realmsServers;
    private static final String mcoInfoUrl = "https://minecraft.net/realms";
    private int numberOfPendingInvites;
    private int animTick;
    private static boolean mcoEnabled;
    private static boolean mcoEnabledCheck;
    private static boolean checkedMcoAvailability;
    private static boolean trialsAvailable;
    private static boolean createdTrial;
    private static final ReentrantLock trialLock;
    private static RealmsScreen realmsGenericErrorScreen;
    private static boolean regionsPinged;
    private boolean onLink;
    private int mindex;
    private char[] mchars;
    private int sindex;
    private char[] schars;
    
    public RealmsMainScreen(final RealmsScreen lastScreen) {
        this.dontSetConnectedToRealms = false;
        this.selectedServerId = -1L;
        this.realmsServers = Lists.newArrayList();
        this.numberOfPendingInvites = 0;
        this.onLink = false;
        this.mindex = 0;
        this.mchars = new char[] { '3', '2', '1', '4', '5', '6' };
        this.sindex = 0;
        this.schars = new char[] { '9', '8', '7', '1', '2', '3' };
        this.lastScreen = lastScreen;
        this.checkIfMcoEnabled();
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.serverSelectionList.mouseEvent();
    }
    
    @Override
    public void init() {
        if (!this.dontSetConnectedToRealms) {
            Realms.setConnectedToRealms(false);
        }
        if (RealmsMainScreen.realmsGenericErrorScreen != null) {
            Realms.setScreen(RealmsMainScreen.realmsGenericErrorScreen);
            return;
        }
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.postInit();
        if (this.isMcoEnabled()) {
            RealmsMainScreen.realmsDataFetcher.init();
        }
    }
    
    public void postInit() {
        this.buttonsAdd(this.playButton = RealmsScreen.newButton(1, this.width() / 2 - 154, this.height() - 52, 154, 20, RealmsScreen.getLocalizedString("mco.selectServer.play")));
        this.buttonsAdd(this.configureButton = RealmsScreen.newButton(2, this.width() / 2 + 6, this.height() - 52, 154, 20, RealmsScreen.getLocalizedString("mco.selectServer.configure")));
        this.buttonsAdd(this.leaveButton = RealmsScreen.newButton(3, this.width() / 2 - 154, this.height() - 28, 102, 20, RealmsScreen.getLocalizedString("mco.selectServer.leave")));
        this.buttonsAdd(this.buyButton = RealmsScreen.newButton(4, this.width() / 2 - 48, this.height() - 28, 102, 20, RealmsScreen.getLocalizedString("mco.selectServer.buy")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 + 58, this.height() - 28, 102, 20, RealmsScreen.getLocalizedString("gui.back")));
        this.serverSelectionList = new ServerSelectionList();
        if (-1 != -1) {
            this.serverSelectionList.scroll(-1);
        }
        final RealmsServer server = this.findServer(this.selectedServerId);
        this.playButton.active(server != null && server.state == RealmsServer.State.OPEN && !server.expired);
        this.configureButton.active(RealmsMainScreen.overrideConfigure || (server != null && server.state != RealmsServer.State.ADMIN_LOCK && server.ownerUUID.equals(Realms.getUUID())));
        this.leaveButton.active(server != null && !server.ownerUUID.equals(Realms.getUUID()));
    }
    
    @Override
    public void tick() {
        ++this.animTick;
        if (this.noParentalConsent()) {
            Realms.setScreen(new RealmsParentalConsentScreen(this.lastScreen));
        }
        if (this.isMcoEnabled()) {
            RealmsMainScreen.realmsDataFetcher.init();
            if (RealmsMainScreen.realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.SERVER_LIST)) {
                final List servers = RealmsMainScreen.realmsDataFetcher.getServers();
                for (final RealmsServer realmsServer : servers) {
                    if (this.isSelfOwnedNonExpiredServer(realmsServer)) {}
                    for (final RealmsServer realmsServer2 : this.realmsServers) {
                        if (realmsServer.id == realmsServer2.id) {
                            realmsServer.latestStatFrom(realmsServer2);
                            break;
                        }
                    }
                }
                this.realmsServers = servers;
                if (!RealmsMainScreen.regionsPinged && true) {
                    RealmsMainScreen.regionsPinged = true;
                    this.pingRegions();
                }
            }
            if (RealmsMainScreen.realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.PENDING_INVITE)) {
                this.numberOfPendingInvites = RealmsMainScreen.realmsDataFetcher.getPendingInvitesCount();
            }
            if (RealmsMainScreen.realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !RealmsMainScreen.createdTrial) {
                RealmsMainScreen.trialsAvailable = RealmsMainScreen.realmsDataFetcher.isTrialAvailable();
            }
            RealmsMainScreen.realmsDataFetcher.markClean();
        }
    }
    
    private void pingRegions() {
        new Thread() {
            final RealmsMainScreen this$0;
            
            @Override
            public void run() {
                final List pingAllRegions = Ping.pingAllRegions();
                final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                final PingResult pingResult = new PingResult();
                pingResult.pingResults = pingAllRegions;
                pingResult.worldIds = RealmsMainScreen.access$000(this.this$0);
                realmsClient.sendPingResults(pingResult);
            }
        }.start();
    }
    
    private List getOwnedNonExpiredWorldIds() {
        final ArrayList<Long> list = new ArrayList<Long>();
        for (final RealmsServer realmsServer : this.realmsServers) {
            if (this.isSelfOwnedNonExpiredServer(realmsServer)) {
                list.add(realmsServer.id);
            }
        }
        return list;
    }
    
    private boolean isMcoEnabled() {
        return RealmsMainScreen.mcoEnabled;
    }
    
    private boolean noParentalConsent() {
        return RealmsMainScreen.mcoEnabledCheck && !RealmsMainScreen.mcoEnabled;
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
                this.play(this.findServer(this.selectedServerId));
                break;
            }
            case 2: {
                this.configureClicked();
                break;
            }
            case 3: {
                this.leaveClicked();
                break;
            }
            case 4: {
                this.saveListScrollPosition();
                this.stopRealmsFetcherAndPinger();
                Realms.setScreen(new RealmsBuyRealmsScreen(this));
                break;
            }
            case 0: {
                this.stopRealmsFetcherAndPinger();
                Realms.setScreen(this.lastScreen);
                break;
            }
            default: {}
        }
    }
    
    private void createTrial() {
        if (RealmsMainScreen.createdTrial) {
            RealmsMainScreen.trialsAvailable = false;
            return;
        }
        new Thread("Realms-create-trial", (RealmsScreen)this) {
            final RealmsScreen val$mainScreen;
            final RealmsMainScreen this$0;
            
            @Override
            public void run() {
                if (!RealmsMainScreen.access$200().tryLock(10L, TimeUnit.MILLISECONDS)) {
                    if (RealmsMainScreen.access$200().isHeldByCurrentThread()) {
                        RealmsMainScreen.access$200().unlock();
                    }
                    return;
                }
                final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                RealmsMainScreen.access$302(false);
                if (realmsClient.createTrial()) {
                    RealmsMainScreen.access$402(true);
                    RealmsMainScreen.access$500().forceUpdate();
                }
                else {
                    Realms.setScreen(new RealmsGenericErrorScreen(RealmsScreen.getLocalizedString("mco.trial.unavailable"), this.val$mainScreen));
                }
                if (RealmsMainScreen.access$200().isHeldByCurrentThread()) {
                    RealmsMainScreen.access$200().unlock();
                }
            }
        }.start();
    }
    
    private void checkIfMcoEnabled() {
        if (!RealmsMainScreen.checkedMcoAvailability) {
            RealmsMainScreen.checkedMcoAvailability = true;
            new Thread("MCO Availability Checker #1") {
                final RealmsMainScreen this$0;
                
                @Override
                public void run() {
                    final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    final RealmsClient.CompatibleVersionResponse clientCompatible = realmsClient.clientCompatible();
                    if (clientCompatible.equals(RealmsClient.CompatibleVersionResponse.OUTDATED)) {
                        Realms.setScreen(RealmsMainScreen.access$602(new RealmsClientOutdatedScreen(RealmsMainScreen.access$700(this.this$0), true)));
                        return;
                    }
                    if (clientCompatible.equals(RealmsClient.CompatibleVersionResponse.OTHER)) {
                        Realms.setScreen(RealmsMainScreen.access$602(new RealmsClientOutdatedScreen(RealmsMainScreen.access$700(this.this$0), false)));
                        return;
                    }
                    while (0 < 3) {
                        if (realmsClient.mcoEnabled()) {
                            RealmsMainScreen.access$100().info("Realms is available for this user");
                            RealmsMainScreen.access$902(true);
                        }
                        else {
                            RealmsMainScreen.access$100().info("Realms is not available for this user");
                            RealmsMainScreen.access$902(false);
                        }
                        RealmsMainScreen.access$1002(true);
                        if (!true) {
                            break;
                        }
                        Thread.sleep(5000L);
                        int n = 0;
                        ++n;
                    }
                }
            }.start();
        }
    }
    
    private void switchToStage() {
        if (!RealmsMainScreen.stageEnabled) {
            new Thread("MCO Stage Availability Checker #1") {
                final RealmsMainScreen this$0;
                
                @Override
                public void run() {
                    if (RealmsClient.createRealmsClient().stageAvailable()) {
                        RealmsMainScreen.access$1100(this.this$0);
                        RealmsMainScreen.access$100().info("Switched to stage");
                        RealmsMainScreen.access$500().init();
                        RealmsMainScreen.access$1202(true);
                    }
                    else {
                        RealmsMainScreen.access$1202(false);
                    }
                }
            }.start();
        }
    }
    
    private void switchToProd() {
        if (RealmsMainScreen.stageEnabled) {
            RealmsMainScreen.stageEnabled = false;
            this.stopRealmsFetcherAndPinger();
            RealmsMainScreen.realmsDataFetcher.init();
        }
    }
    
    private void stopRealmsFetcherAndPinger() {
        if (this.isMcoEnabled()) {
            RealmsMainScreen.realmsDataFetcher.stop();
            RealmsMainScreen.statusPinger.removeAll();
        }
    }
    
    private void configureClicked() {
        final RealmsServer server = this.findServer(this.selectedServerId);
        if (server != null && (Realms.getUUID().equals(server.ownerUUID) || RealmsMainScreen.overrideConfigure)) {
            this.stopRealmsFetcherAndPinger();
            this.saveListScrollPosition();
            Realms.setScreen(new RealmsConfigureWorldScreen(this, server.id));
        }
    }
    
    private void leaveClicked() {
        final RealmsServer server = this.findServer(this.selectedServerId);
        if (server != null && !Realms.getUUID().equals(server.ownerUUID)) {
            this.saveListScrollPosition();
            Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, RealmsScreen.getLocalizedString("mco.configure.world.leave.question.line1"), RealmsScreen.getLocalizedString("mco.configure.world.leave.question.line2"), true, 3));
        }
    }
    
    private void saveListScrollPosition() {
        RealmsMainScreen.lastScrollYPosition = this.serverSelectionList.getScroll();
    }
    
    private RealmsServer findServer(final long n) {
        for (final RealmsServer realmsServer : this.realmsServers) {
            if (realmsServer.id == n) {
                return realmsServer;
            }
        }
        return null;
    }
    
    private int findIndex(final long n) {
        while (0 < this.realmsServers.size()) {
            if (this.realmsServers.get(0).id == n) {
                return 0;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (n == 3) {
            if (b) {
                new Thread("Realms-leave-server") {
                    final RealmsMainScreen this$0;
                    
                    @Override
                    public void run() {
                        final RealmsServer access$1400 = RealmsMainScreen.access$1400(this.this$0, RealmsMainScreen.access$1300(this.this$0));
                        if (access$1400 != null) {
                            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                            RealmsMainScreen.access$500().removeItem(access$1400);
                            RealmsMainScreen.access$1500(this.this$0).remove(access$1400);
                            realmsClient.uninviteMyselfFrom(access$1400.id);
                            RealmsMainScreen.access$500().removeItem(access$1400);
                            RealmsMainScreen.access$1500(this.this$0).remove(access$1400);
                            RealmsMainScreen.access$1600(this.this$0);
                        }
                    }
                }.start();
            }
            Realms.setScreen(this);
        }
        else if (n == 100) {
            if (!b) {
                Realms.setScreen(this);
            }
            else {
                this.connectToServer(this.resourcePackServer);
            }
        }
    }
    
    private void updateSelectedItemPointer() {
        int index = this.findIndex(this.selectedServerId);
        if (this.realmsServers.size() - 1 == -1) {
            --index;
        }
        if (this.realmsServers.size() == 0) {}
        if (-1 >= 0 && -1 < this.realmsServers.size()) {
            this.selectedServerId = this.realmsServers.get(-1).id;
        }
    }
    
    public void removeSelection() {
        this.selectedServerId = -1L;
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        switch (n) {
            case 28:
            case 156: {
                this.mindex = 0;
                this.sindex = 0;
                this.buttonClicked(this.playButton);
                break;
            }
            case 1: {
                this.mindex = 0;
                this.sindex = 0;
                this.stopRealmsFetcherAndPinger();
                Realms.setScreen(this.lastScreen);
                break;
            }
            default: {
                if (this.mchars[this.mindex] == c) {
                    ++this.mindex;
                    if (this.mindex == this.mchars.length) {
                        this.mindex = 0;
                        RealmsMainScreen.overrideConfigure = true;
                    }
                }
                else {
                    this.mindex = 0;
                }
                if (this.schars[this.sindex] == c) {
                    ++this.sindex;
                    if (this.sindex == this.schars.length) {
                        this.sindex = 0;
                        if (!RealmsMainScreen.stageEnabled) {
                            this.switchToStage();
                        }
                        else {
                            this.switchToProd();
                        }
                    }
                    return;
                }
                this.sindex = 0;
                break;
            }
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.renderBackground();
        this.serverSelectionList.render(n, n2, n3);
        this.drawRealmsLogo(this.width() / 2 - 50, 7);
        this.renderLink(n, n2);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
        this.drawInvitationPendingIcon(n, n2);
        if (RealmsMainScreen.stageEnabled) {
            this.renderStage();
        }
        super.render(n, n2, n3);
    }
    
    private void drawRealmsLogo(final int n, final int n2) {
        RealmsScreen.bind("realms:textures/gui/title/realms.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2 - 5, 0.0f, 0.0f, 200, 50, 200.0f, 50.0f);
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.inPendingInvitationArea(n, n2)) {
            this.stopRealmsFetcherAndPinger();
            Realms.setScreen(new RealmsPendingInvitesScreen(this.lastScreen));
        }
        if (this.onLink) {
            RealmsUtil.browseTo("https://minecraft.net/realms");
        }
    }
    
    private void drawInvitationPendingIcon(final int n, final int n2) {
        final int numberOfPendingInvites = this.numberOfPendingInvites;
        final boolean inPendingInvitationArea = this.inPendingInvitationArea(n, n2);
        final int n3 = this.width() / 2 + 50;
        if (numberOfPendingInvites != 0) {
            final float n4 = 0.25f + (1.0f + RealmsMth.sin(this.animTick * 0.5f)) * 0.25f;
            final int n5 = 0xFF000000 | (int)(n4 * 64.0f) << 16 | (int)(n4 * 64.0f) << 8 | (int)(n4 * 64.0f) << 0;
            this.fillGradient(n3 - 2, 6, n3 + 18, 26, n5, n5);
            final int n6 = 0xFF000000 | (int)(n4 * 255.0f) << 16 | (int)(n4 * 255.0f) << 8 | (int)(n4 * 255.0f) << 0;
            this.fillGradient(n3 - 2, 6, n3 + 18, 7, n6, n6);
            this.fillGradient(n3 - 2, 6, n3 - 1, 26, n6, n6);
            this.fillGradient(n3 + 17, 6, n3 + 18, 26, n6, n6);
            this.fillGradient(n3 - 2, 25, n3 + 18, 26, n6, n6);
        }
        RealmsScreen.bind("realms:textures/gui/realms/invite_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsScreen.blit(n3, 2, inPendingInvitationArea ? 16.0f : 0.0f, 0.0f, 15, 25, 31.0f, 25.0f);
        if (numberOfPendingInvites != 0) {
            final int n7 = (Math.min(numberOfPendingInvites, 6) - 1) * 8;
            final int n8 = (int)(Math.max(0.0f, Math.max(RealmsMth.sin((10 + this.animTick) * 0.57f), RealmsMth.cos(this.animTick * 0.35f))) * -6.0f);
            RealmsScreen.bind("realms:textures/gui/realms/invitation_icons.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n3 + 4, 12 + n8, (float)n7, inPendingInvitationArea ? 8.0f : 0.0f, 8, 8, 48.0f, 16.0f);
        }
        if (inPendingInvitationArea) {
            final int n9 = n + 12;
            final int n10 = n2 - 12;
            final String s = (numberOfPendingInvites == 0) ? RealmsScreen.getLocalizedString("mco.invites.nopending") : RealmsScreen.getLocalizedString("mco.invites.pending");
            this.fillGradient(n9 - 3, n10 - 3, n9 + this.fontWidth(s) + 3, n10 + 8 + 3, -1073741824, -1073741824);
            this.fontDrawShadow(s, n9, n10, -1);
        }
    }
    
    private boolean inPendingInvitationArea(final int n, final int n2) {
        final int n3 = this.width() / 2 + 50;
        final int n4 = this.width() / 2 + 66;
        return n3 <= n && n <= n4 && 13 <= n2 && n2 <= 27;
    }
    
    public void play(final RealmsServer resourcePackServer) {
        if (resourcePackServer != null) {
            this.stopRealmsFetcherAndPinger();
            this.dontSetConnectedToRealms = true;
            if (resourcePackServer.resourcePackUrl != null && resourcePackServer.resourcePackHash != null) {
                this.resourcePackServer = resourcePackServer;
                this.saveListScrollPosition();
                Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, RealmsScreen.getLocalizedString("mco.configure.world.resourcepack.question.line1"), RealmsScreen.getLocalizedString("mco.configure.world.resourcepack.question.line2"), true, 100));
            }
            else {
                this.connectToServer(resourcePackServer);
            }
        }
    }
    
    private void connectToServer(final RealmsServer realmsServer) {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this, new RealmsTasks.RealmsConnectTask(this, realmsServer));
        screen.start();
        Realms.setScreen(screen);
    }
    
    private boolean isSelfOwnedServer(final RealmsServer realmsServer) {
        return realmsServer.ownerUUID != null && realmsServer.ownerUUID.equals(Realms.getUUID());
    }
    
    private boolean isSelfOwnedNonExpiredServer(final RealmsServer realmsServer) {
        return realmsServer.ownerUUID != null && realmsServer.ownerUUID.equals(Realms.getUUID()) && !realmsServer.expired;
    }
    
    private void drawExpired(final int n, final int n2, final int n3, final int n4) {
        RealmsScreen.bind("realms:textures/gui/realms/expired_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 64 && n4 > 32) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.expired");
        }
    }
    
    private void drawExpiring(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (this.animTick % 20 < 10) {
            RealmsScreen.bind("realms:textures/gui/realms/on_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        }
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 64 && n4 > 32) {
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
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 64 && n4 > 32) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.open");
        }
    }
    
    private void drawClose(final int n, final int n2, final int n3, final int n4) {
        RealmsScreen.bind("realms:textures/gui/realms/off_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 64 && n4 > 32) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.closed");
        }
    }
    
    private void drawLocked(final int n, final int n2, final int n3, final int n4) {
        RealmsScreen.bind("realms:textures/gui/realms/off_icon.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RealmsScreen.blit(n * 2, n2 * 2, 0.0f, 0.0f, 15, 15, 15.0f, 15.0f);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < this.height() - 64 && n4 > 32) {
            this.toolTip = RealmsScreen.getLocalizedString("mco.selectServer.locked");
        }
    }
    
    protected void renderMousehoverTooltip(final String p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       5
        //     4: return         
        //     5: iload_2        
        //     6: bipush          12
        //     8: iadd           
        //     9: istore          4
        //    11: iload_3        
        //    12: bipush          12
        //    14: isub           
        //    15: istore          5
        //    17: aload_1        
        //    18: ldc_w           "\n"
        //    21: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    24: astore          8
        //    26: aload           8
        //    28: arraylength    
        //    29: istore          9
        //    31: iconst_0       
        //    32: iload           9
        //    34: if_icmpge       67
        //    37: aload           8
        //    39: iconst_0       
        //    40: aaload         
        //    41: astore          11
        //    43: aload_0        
        //    44: aload           11
        //    46: invokevirtual   com/mojang/realmsclient/RealmsMainScreen.fontWidth:(Ljava/lang/String;)I
        //    49: istore          12
        //    51: iload           12
        //    53: iconst_0       
        //    54: if_icmple       61
        //    57: iload           12
        //    59: istore          7
        //    61: iinc            10, 1
        //    64: goto            31
        //    67: aload_1        
        //    68: ldc_w           "\n"
        //    71: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    74: astore          8
        //    76: aload           8
        //    78: arraylength    
        //    79: istore          9
        //    81: iconst_0       
        //    82: iload           9
        //    84: if_icmpge       158
        //    87: aload           8
        //    89: iconst_0       
        //    90: aaload         
        //    91: astore          11
        //    93: aload_0        
        //    94: iload           4
        //    96: iconst_3       
        //    97: isub           
        //    98: iload           5
        //   100: iconst_0       
        //   101: ifne            108
        //   104: iconst_3       
        //   105: goto            104
        //   108: iconst_0       
        //   109: iadd           
        //   110: iload           4
        //   112: iconst_0       
        //   113: iadd           
        //   114: iconst_3       
        //   115: iadd           
        //   116: iload           5
        //   118: bipush          8
        //   120: iadd           
        //   121: iconst_3       
        //   122: iadd           
        //   123: iconst_0       
        //   124: iadd           
        //   125: ldc_w           -1073741824
        //   128: ldc_w           -1073741824
        //   131: invokevirtual   com/mojang/realmsclient/RealmsMainScreen.fillGradient:(IIIIII)V
        //   134: aload_0        
        //   135: aload           11
        //   137: iload           4
        //   139: iload           5
        //   141: iconst_0       
        //   142: iadd           
        //   143: ldc_w           16777215
        //   146: invokevirtual   com/mojang/realmsclient/RealmsMainScreen.fontDrawShadow:(Ljava/lang/String;III)V
        //   149: iinc            6, 10
        //   152: iinc            10, 1
        //   155: goto            81
        //   158: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0104 (coming from #0105).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void renderLink(final int n, final int n2) {
        final String localizedString = RealmsScreen.getLocalizedString("mco.selectServer.whatisrealms");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int n3 = 10 + this.fontWidth(localizedString) + 1;
        final int n4 = 12 + this.fontLineHeight();
        GL11.glTranslatef(10, 12, 0.0f);
        if (10 <= n && n <= n3 && 12 <= n2 && n2 <= n4) {
            this.onLink = true;
            this.drawString(localizedString, 0, 0, 7107012);
        }
        else {
            this.onLink = false;
            this.drawString(localizedString, 0, 0, 3368635);
        }
    }
    
    private void renderStage() {
        final String s = "STAGE!";
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)(this.width() / 2 - 25), 20.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        this.drawString(s, 0, 0, -256);
    }
    
    public RealmsScreen newScreen() {
        return new RealmsMainScreen(this.lastScreen);
    }
    
    static List access$000(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.getOwnedNonExpiredWorldIds();
    }
    
    static Logger access$100() {
        return RealmsMainScreen.LOGGER;
    }
    
    static ReentrantLock access$200() {
        return RealmsMainScreen.trialLock;
    }
    
    static boolean access$302(final boolean trialsAvailable) {
        return RealmsMainScreen.trialsAvailable = trialsAvailable;
    }
    
    static boolean access$402(final boolean createdTrial) {
        return RealmsMainScreen.createdTrial = createdTrial;
    }
    
    static RealmsDataFetcher access$500() {
        return RealmsMainScreen.realmsDataFetcher;
    }
    
    static RealmsScreen access$602(final RealmsScreen realmsGenericErrorScreen) {
        return RealmsMainScreen.realmsGenericErrorScreen = realmsGenericErrorScreen;
    }
    
    static RealmsScreen access$700(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.lastScreen;
    }
    
    static boolean access$802(final boolean checkedMcoAvailability) {
        return RealmsMainScreen.checkedMcoAvailability = checkedMcoAvailability;
    }
    
    static boolean access$902(final boolean mcoEnabled) {
        return RealmsMainScreen.mcoEnabled = mcoEnabled;
    }
    
    static boolean access$1002(final boolean mcoEnabledCheck) {
        return RealmsMainScreen.mcoEnabledCheck = mcoEnabledCheck;
    }
    
    static void access$1100(final RealmsMainScreen realmsMainScreen) {
        realmsMainScreen.stopRealmsFetcherAndPinger();
    }
    
    static boolean access$1202(final boolean stageEnabled) {
        return RealmsMainScreen.stageEnabled = stageEnabled;
    }
    
    static long access$1300(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.selectedServerId;
    }
    
    static RealmsServer access$1400(final RealmsMainScreen realmsMainScreen, final long n) {
        return realmsMainScreen.findServer(n);
    }
    
    static List access$1500(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.realmsServers;
    }
    
    static void access$1600(final RealmsMainScreen realmsMainScreen) {
        realmsMainScreen.updateSelectedItemPointer();
    }
    
    static boolean access$300() {
        return RealmsMainScreen.trialsAvailable;
    }
    
    static void access$1700(final RealmsMainScreen realmsMainScreen) {
        realmsMainScreen.createTrial();
    }
    
    static long access$1302(final RealmsMainScreen realmsMainScreen, final long selectedServerId) {
        return realmsMainScreen.selectedServerId = selectedServerId;
    }
    
    static boolean access$1800() {
        return RealmsMainScreen.overrideConfigure;
    }
    
    static boolean access$1900(final RealmsMainScreen realmsMainScreen, final RealmsServer realmsServer) {
        return realmsMainScreen.isSelfOwnedServer(realmsServer);
    }
    
    static RealmsButton access$2000(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.configureButton;
    }
    
    static RealmsButton access$2100(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.leaveButton;
    }
    
    static RealmsButton access$2200(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.playButton;
    }
    
    static int access$2300(final RealmsMainScreen realmsMainScreen, final long n) {
        return realmsMainScreen.findIndex(n);
    }
    
    static int access$2400(final RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.animTick;
    }
    
    static RealmsServerStatusPinger access$2500() {
        return RealmsMainScreen.statusPinger;
    }
    
    static ThreadPoolExecutor access$2600() {
        return RealmsMainScreen.THREAD_POOL;
    }
    
    static void access$2700(final RealmsMainScreen realmsMainScreen, final int n, final int n2, final int n3, final int n4) {
        realmsMainScreen.drawExpired(n, n2, n3, n4);
    }
    
    static void access$2800(final RealmsMainScreen realmsMainScreen, final int n, final int n2, final int n3, final int n4) {
        realmsMainScreen.drawClose(n, n2, n3, n4);
    }
    
    static void access$2900(final RealmsMainScreen realmsMainScreen, final int n, final int n2, final int n3, final int n4, final int n5) {
        realmsMainScreen.drawExpiring(n, n2, n3, n4, n5);
    }
    
    static void access$3000(final RealmsMainScreen realmsMainScreen, final int n, final int n2, final int n3, final int n4) {
        realmsMainScreen.drawOpen(n, n2, n3, n4);
    }
    
    static void access$3100(final RealmsMainScreen realmsMainScreen, final int n, final int n2, final int n3, final int n4) {
        realmsMainScreen.drawLocked(n, n2, n3, n4);
    }
    
    static String access$3202(final RealmsMainScreen realmsMainScreen, final String toolTip) {
        return realmsMainScreen.toolTip = toolTip;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        RealmsMainScreen.overrideConfigure = false;
        RealmsMainScreen.stageEnabled = false;
        RealmsMainScreen.realmsDataFetcher = new RealmsDataFetcher();
        RealmsMainScreen.statusPinger = new RealmsServerStatusPinger();
        THREAD_POOL = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
        RealmsMainScreen.createdTrial = false;
        trialLock = new ReentrantLock();
        RealmsMainScreen.realmsGenericErrorScreen = null;
        RealmsMainScreen.regionsPinged = false;
        final String version = RealmsVersion.getVersion();
        if (version != null) {
            RealmsMainScreen.LOGGER.info("Realms library version == " + version);
        }
    }
    
    private class ServerSelectionList extends RealmsScrolledSelectionList
    {
        final RealmsMainScreen this$0;
        
        public ServerSelectionList(final RealmsMainScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width(), this$0.height(), 32, this$0.height() - 64, 36);
        }
        
        @Override
        public int getItemCount() {
            if (RealmsMainScreen.access$300()) {
                return RealmsMainScreen.access$1500(this.this$0).size() + 1;
            }
            return RealmsMainScreen.access$1500(this.this$0).size();
        }
        
        @Override
        public void selectItem(int n, final boolean b, final int n2, final int n3) {
            if (RealmsMainScreen.access$300()) {
                if (n == 0) {
                    RealmsMainScreen.access$1700(this.this$0);
                    return;
                }
                --n;
            }
            if (n >= RealmsMainScreen.access$1500(this.this$0).size()) {
                return;
            }
            final RealmsServer realmsServer = RealmsMainScreen.access$1500(this.this$0).get(n);
            if (realmsServer.state == RealmsServer.State.UNINITIALIZED) {
                RealmsMainScreen.access$1302(this.this$0, -1L);
                RealmsMainScreen.access$1100(this.this$0);
                Realms.setScreen(new RealmsCreateRealmScreen(realmsServer, this.this$0));
            }
            else {
                RealmsMainScreen.access$1302(this.this$0, realmsServer.id);
            }
            RealmsMainScreen.access$2000(this.this$0).active(RealmsMainScreen.access$1800() || (RealmsMainScreen.access$1900(this.this$0, realmsServer) && realmsServer.state != RealmsServer.State.ADMIN_LOCK && realmsServer.state != RealmsServer.State.UNINITIALIZED));
            RealmsMainScreen.access$2100(this.this$0).active(!RealmsMainScreen.access$1900(this.this$0, realmsServer));
            RealmsMainScreen.access$2200(this.this$0).active(realmsServer.state == RealmsServer.State.OPEN && !realmsServer.expired);
            if (b && RealmsMainScreen.access$2200(this.this$0).active()) {
                this.this$0.play(RealmsMainScreen.access$1400(this.this$0, RealmsMainScreen.access$1300(this.this$0)));
            }
        }
        
        @Override
        public boolean isSelectedItem(int n) {
            if (RealmsMainScreen.access$300()) {
                if (n == 0) {
                    return false;
                }
                --n;
            }
            return n == RealmsMainScreen.access$2300(this.this$0, RealmsMainScreen.access$1300(this.this$0));
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }
        
        @Override
        public void renderBackground() {
            this.this$0.renderBackground();
        }
        
        @Override
        protected void renderItem(int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
            if (RealmsMainScreen.access$300()) {
                if (n == 0) {
                    this.renderTrialItem(n, n2, n3);
                    return;
                }
                --n;
            }
            if (n < RealmsMainScreen.access$1500(this.this$0).size()) {
                this.renderMcoServerItem(n, n2, n3);
            }
        }
        
        private void renderTrialItem(final int n, final int n2, final int n3) {
            final int n4 = n3 + 12;
            final String localizedString = RealmsScreen.getLocalizedString("mco.trial.message");
            if (n2 > this.xm() || this.xm() > this.getScrollbarPosition() || n3 > this.ym() || this.ym() <= n3 + 32) {}
            final float n5 = 0.5f + (1.0f + RealmsMth.sin(RealmsMainScreen.access$2400(this.this$0) * 0.25f)) * 0.25f;
            int n6;
            if (true) {
                n6 = (0xFF | (int)(127.0f * n5) << 16 | (int)(255.0f * n5) << 8 | (int)(127.0f * n5));
            }
            else {
                n6 = (0xFF000000 | (int)(127.0f * n5) << 16 | (int)(255.0f * n5) << 8 | (int)(127.0f * n5));
            }
            final String[] split = localizedString.split("\\\\n");
            while (0 < split.length) {
                this.this$0.drawCenteredString(split[0], this.this$0.width() / 2, n4 + 0, n6);
                final int n7;
                n7 += 10;
                int n8 = 0;
                ++n8;
            }
        }
        
        private void renderMcoServerItem(final int n, final int n2, final int n3) {
            final RealmsServer realmsServer = RealmsMainScreen.access$1500(this.this$0).get(n);
            final int n4 = RealmsMainScreen.access$1900(this.this$0, realmsServer) ? 8388479 : 16777215;
            if (realmsServer.state == RealmsServer.State.UNINITIALIZED) {
                RealmsScreen.bind("realms:textures/gui/realms/world_icon.png");
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3008);
                RealmsScreen.blit(n2 + 10, n3 + 6, 0.0f, 0.0f, 40, 20, 40.0f, 20.0f);
                final float n5 = 0.5f + (1.0f + RealmsMth.sin(RealmsMainScreen.access$2400(this.this$0) * 0.25f)) * 0.25f;
                final int n6 = 0xFF000000 | (int)(127.0f * n5) << 16 | (int)(255.0f * n5) << 8 | (int)(127.0f * n5);
                this.this$0.drawCenteredString(RealmsScreen.getLocalizedString("mco.selectServer.uninitialized"), n2 + 10 + 40 + 75, n3 + 12, 1);
                return;
            }
            if (realmsServer.shouldPing(Realms.currentTimeMillis())) {
                realmsServer.serverPing.lastPingSnapshot = Realms.currentTimeMillis();
                RealmsMainScreen.access$2600().submit(new Runnable(realmsServer) {
                    final RealmsServer val$serverData;
                    final ServerSelectionList this$1;
                    
                    @Override
                    public void run() {
                        RealmsMainScreen.access$2500().pingServer(this.val$serverData.ip, this.val$serverData.serverPing);
                    }
                });
            }
            this.this$0.drawString(realmsServer.getName(), n2 + 2, n3 + 1, n4);
            if (realmsServer.expired) {
                RealmsMainScreen.access$2700(this.this$0, n2 + 207, n3 + 1, this.xm(), this.ym());
            }
            else if (realmsServer.state == RealmsServer.State.CLOSED) {
                RealmsMainScreen.access$2800(this.this$0, n2 + 207, n3 + 1, this.xm(), this.ym());
            }
            else if (RealmsMainScreen.access$1900(this.this$0, realmsServer) && realmsServer.daysLeft < 7) {
                this.showStatus(n2 - 14, n3, realmsServer);
                RealmsMainScreen.access$2900(this.this$0, n2 + 207, n3 + 1, this.xm(), this.ym(), realmsServer.daysLeft);
            }
            else if (realmsServer.state == RealmsServer.State.OPEN) {
                RealmsMainScreen.access$3000(this.this$0, n2 + 207, n3 + 1, this.xm(), this.ym());
                this.showStatus(n2 - 14, n3, realmsServer);
            }
            else if (realmsServer.state == RealmsServer.State.ADMIN_LOCK) {
                RealmsMainScreen.access$3100(this.this$0, n2 + 207, n3 + 1, this.xm(), this.ym());
            }
            if (!realmsServer.serverPing.nrOfPlayers.equals("0")) {
                final String string = ChatFormatting.GRAY + "" + realmsServer.serverPing.nrOfPlayers;
                this.this$0.drawString(string, n2 + 200 - this.this$0.fontWidth(string), n3 + 1, 8421504);
                if (this.xm() >= n2 + 200 - this.this$0.fontWidth(string) && this.xm() <= n2 + 200 && this.ym() >= n3 + 1 && this.ym() <= n3 + 9 && this.ym() < this.this$0.height() - 64 && this.ym() > 32) {
                    RealmsMainScreen.access$3202(this.this$0, realmsServer.serverPing.playerList);
                }
            }
            if (realmsServer.worldType.equals(RealmsServer.WorldType.MINIGAME)) {
                if (RealmsMainScreen.access$2400(this.this$0) % 10 < 5) {}
                final String string2 = RealmsScreen.getLocalizedString("mco.selectServer.minigame") + " ";
                final int fontWidth = this.this$0.fontWidth(string2);
                this.this$0.drawString(string2, n2 + 2, n3 + 12, 13413468);
                this.this$0.drawString(realmsServer.getMinigameName(), n2 + 2 + fontWidth, n3 + 12, 7105644);
            }
            else {
                this.this$0.drawString(realmsServer.getDescription(), n2 + 2, n3 + 12, 7105644);
            }
            this.this$0.drawString(realmsServer.owner, n2 + 2, n3 + 12 + 11, 5000268);
            RealmsScreen.bindFace(realmsServer.ownerUUID, realmsServer.owner);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.blit(n2 - 36, n3, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
            RealmsScreen.blit(n2 - 36, n3, 40.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
        }
        
        private void showStatus(final int n, final int n2, final RealmsServer realmsServer) {
            if (realmsServer.ip == null) {
                return;
            }
            if (realmsServer.status != null) {
                this.this$0.drawString(realmsServer.status, n + 215 - this.this$0.fontWidth(realmsServer.status), n2 + 1, 8421504);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsScreen.bind("textures/gui/icons.png");
        }
    }
}
