package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.client.*;
import java.text.*;
import com.mojang.realmsclient.util.*;
import java.util.*;
import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.gui.*;
import org.apache.logging.log4j.*;
import net.minecraft.realms.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class RealmsBackupScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final String PLUS_ICON_LOCATION = "realms:textures/gui/realms/plus_icon.png";
    private static final String RESTORE_ICON_LOCATION = "realms:textures/gui/realms/restore_icon.png";
    private final RealmsConfigureWorldScreen lastScreen;
    private List backups;
    private String toolTip;
    private BackupSelectionList backupSelectionList;
    private int selectedBackup;
    private static final int BACK_BUTTON_ID = 0;
    private static final int RESTORE_BUTTON_ID = 1;
    private static final int DOWNLOAD_BUTTON_ID = 2;
    private RealmsButton downloadButton;
    private Boolean noBackups;
    private RealmsServer serverData;
    private static final String UPLOADED_KEY = "Uploaded";
    
    public RealmsBackupScreen(final RealmsConfigureWorldScreen lastScreen, final RealmsServer serverData) {
        this.backups = Collections.emptyList();
        this.toolTip = null;
        this.selectedBackup = -1;
        this.noBackups = false;
        this.lastScreen = lastScreen;
        this.serverData = serverData;
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.backupSelectionList.mouseEvent();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.backupSelectionList = new BackupSelectionList();
        if (-1 != -1) {
            this.backupSelectionList.scroll(-1);
        }
        new Thread("Realms-fetch-backups") {
            final RealmsBackupScreen this$0;
            
            @Override
            public void run() {
                RealmsBackupScreen.access$002(this.this$0, RealmsClient.createRealmsClient().backupsFor(RealmsBackupScreen.access$100(this.this$0).id).backups);
                RealmsBackupScreen.access$202(this.this$0, RealmsBackupScreen.access$000(this.this$0).size() == 0);
                RealmsBackupScreen.access$300(this.this$0);
            }
        }.start();
        this.postInit();
    }
    
    private void generateChangeList() {
        if (this.backups.size() <= 1) {
            return;
        }
        while (0 < this.backups.size() - 1) {
            final Backup backup = this.backups.get(0);
            final Backup backup2 = this.backups.get(1);
            if (!backup.metadata.isEmpty()) {
                if (!backup2.metadata.isEmpty()) {
                    for (final String s : backup.metadata.keySet()) {
                        if (!s.contains("Uploaded") && backup2.metadata.containsKey(s)) {
                            if (((String)backup.metadata.get(s)).equals(backup2.metadata.get(s))) {
                                continue;
                            }
                            this.addToChangeList(backup, s);
                        }
                        else {
                            this.addToChangeList(backup, s);
                        }
                    }
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    private void addToChangeList(final Backup backup, final String s) {
        if (s.contains("Uploaded")) {
            backup.changeList.put(s, DateFormat.getDateTimeInstance(3, 3).format(backup.lastModifiedDate));
            backup.setUploadedVersion(true);
        }
        else {
            backup.changeList.put(s, backup.metadata.get(s));
        }
    }
    
    private void postInit() {
        this.buttonsAdd(this.downloadButton = RealmsScreen.newButton(2, this.width() - 125, 32, 100, 20, RealmsScreen.getLocalizedString("mco.backup.button.download")));
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() - 125, this.height() - 35, 85, 20, RealmsScreen.getLocalizedString("gui.back")));
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.lastScreen);
        }
        else if (realmsButton.id() == 2) {
            this.downloadClicked();
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    private void restoreClicked(final int selectedBackup) {
        if (selectedBackup >= 0 && selectedBackup < this.backups.size() && !this.serverData.expired) {
            this.selectedBackup = selectedBackup;
            final Date lastModifiedDate = this.backups.get(selectedBackup).lastModifiedDate;
            Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Warning, RealmsScreen.getLocalizedString("mco.configure.world.restore.question.line1", DateFormat.getDateTimeInstance(3, 3).format(lastModifiedDate), RealmsUtil.convertToAgePresentation(System.currentTimeMillis() - lastModifiedDate.getTime())), RealmsScreen.getLocalizedString("mco.configure.world.restore.question.line2"), true, 1));
        }
    }
    
    private void downloadClicked() {
        Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Info, RealmsScreen.getLocalizedString("mco.configure.world.restore.download.question.line1"), RealmsScreen.getLocalizedString("mco.configure.world.restore.download.question.line2"), true, 2));
    }
    
    private void downloadWorldData() {
        Realms.setScreen(new RealmsDownloadLatestWorldScreen(this, RealmsClient.createRealmsClient().download(this.serverData.id), this.serverData.name + " (" + this.serverData.slots.get(this.serverData.activeSlot).getSlotName(this.serverData.activeSlot) + ")"));
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (b && n == 1) {
            this.restore();
        }
        else if (b && n == 2) {
            this.downloadWorldData();
        }
        else {
            Realms.setScreen(this);
        }
    }
    
    private void restore() {
        final RealmsLongRunningMcoTaskScreen screen = new RealmsLongRunningMcoTaskScreen(this.lastScreen.getNewScreen(), new RestoreTask(this.backups.get(this.selectedBackup), null));
        screen.start();
        Realms.setScreen(screen);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.renderBackground();
        this.backupSelectionList.render(n, n2, n3);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.configure.world.backup"), this.width() / 2, 12, 16777215);
        this.drawString(RealmsScreen.getLocalizedString("mco.configure.world.backup"), (this.width() - 150) / 2 - 90, 20, 10526880);
        if (this.noBackups) {
            this.drawString(RealmsScreen.getLocalizedString("mco.backup.nobackups"), 20, this.height() / 2 - 10, 16777215);
        }
        this.downloadButton.active(!this.noBackups);
        super.render(n, n2, n3);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
    }
    
    protected void renderMousehoverTooltip(final String s, final int n, final int n2) {
        if (s == null) {
            return;
        }
        final int n3 = n + 12;
        final int n4 = n2 - 12;
        this.fillGradient(n3 - 3, n4 - 3, n3 + this.fontWidth(s) + 3, n4 + 8 + 3, -1073741824, -1073741824);
        this.fontDrawShadow(s, n3, n4, 16777215);
    }
    
    static List access$002(final RealmsBackupScreen realmsBackupScreen, final List backups) {
        return realmsBackupScreen.backups = backups;
    }
    
    static RealmsServer access$100(final RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.serverData;
    }
    
    static Boolean access$202(final RealmsBackupScreen realmsBackupScreen, final Boolean noBackups) {
        return realmsBackupScreen.noBackups = noBackups;
    }
    
    static List access$000(final RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.backups;
    }
    
    static void access$300(final RealmsBackupScreen realmsBackupScreen) {
        realmsBackupScreen.generateChangeList();
    }
    
    static Logger access$400() {
        return RealmsBackupScreen.LOGGER;
    }
    
    static RealmsConfigureWorldScreen access$600(final RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.lastScreen;
    }
    
    static int access$702(final int lastScrollPosition) {
        return RealmsBackupScreen.lastScrollPosition = lastScrollPosition;
    }
    
    static void access$800(final RealmsBackupScreen realmsBackupScreen, final int n) {
        realmsBackupScreen.restoreClicked(n);
    }
    
    static String access$902(final RealmsBackupScreen realmsBackupScreen, final String toolTip) {
        return realmsBackupScreen.toolTip = toolTip;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private class BackupSelectionList extends RealmsClickableScrolledSelectionList
    {
        final RealmsBackupScreen this$0;
        
        public BackupSelectionList(final RealmsBackupScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width() - 150, this$0.height(), 32, this$0.height() - 15, 36);
        }
        
        @Override
        public int getItemCount() {
            return RealmsBackupScreen.access$000(this.this$0).size() + 1;
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
        public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
            if (Mouse.isButtonDown(0) && this.ym() >= n && this.ym() <= n2) {
                final int n6 = this.width() / 2 - 92;
                final int width = this.width();
                final int n7 = this.ym() - n - n3 + (int)n4 - 4;
                final int n8 = n7 / n5;
                if (this.xm() >= n6 && this.xm() <= width && n8 >= 0 && n7 >= 0 && n8 < this.getItemCount()) {
                    this.itemClicked(n7, n8, this.xm(), this.ym(), this.width());
                }
            }
        }
        
        @Override
        public void renderItem(final int n, int n2, final int n3, final int n4, final int n5, final int n6) {
            n2 += 16;
            if (n < RealmsBackupScreen.access$000(this.this$0).size()) {
                this.renderBackupItem(n, n2, n3, n4, this.this$0.width);
            }
        }
        
        @Override
        public int getScrollbarPosition() {
            return this.width() - 5;
        }
        
        @Override
        public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
            final int n6 = this.width() - 40;
            final int n7 = n + 30 - this.getScroll();
            final int n8 = n6 + 10;
            final int n9 = n7 - 3;
            if (n3 >= n6 && n3 <= n6 + 9 && n4 >= n7 && n4 <= n7 + 9) {
                if (!RealmsBackupScreen.access$000(this.this$0).get(n2).changeList.isEmpty()) {
                    RealmsBackupScreen.access$702(this.getScroll());
                    Realms.setScreen(new RealmsBackupInfoScreen(this.this$0, RealmsBackupScreen.access$000(this.this$0).get(n2)));
                }
            }
            else if (n3 >= n8 && n3 <= n8 + 9 && n4 >= n9 && n4 <= n9 + 9) {
                RealmsBackupScreen.access$702(this.getScroll());
                RealmsBackupScreen.access$800(this.this$0, n2);
            }
        }
        
        private void renderBackupItem(final int n, final int n2, final int n3, final int n4, final int n5) {
            final Backup backup = RealmsBackupScreen.access$000(this.this$0).get(n);
            this.this$0.drawString("Backup (" + RealmsUtil.convertToAgePresentation(System.currentTimeMillis() - backup.lastModifiedDate.getTime()) + ")", n2 + 2, n3 + 1, backup.isUploadedVersion() ? -8388737 : 16777215);
            this.this$0.drawString(this.getMediumDatePresentation(backup.lastModifiedDate), n2 + 2, n3 + 12, 5000268);
            final int n6 = this.width() - 30;
            final int n7 = n6 - 10;
            if (!RealmsBackupScreen.access$100(this.this$0).expired) {
                this.drawRestore(n6, n3 - 3, this.xm(), this.ym());
            }
            if (!backup.changeList.isEmpty()) {
                this.drawInfo(n7, n3 + 0, this.xm(), this.ym());
            }
        }
        
        private String getMediumDatePresentation(final Date date) {
            return DateFormat.getDateTimeInstance(3, 3).format(date);
        }
        
        private void drawRestore(final int n, final int n2, final int n3, final int n4) {
            final boolean b = n3 >= n && n3 <= n + 12 && n4 >= n2 && n4 <= n2 + 14 && n4 < this.this$0.height() - 15 && n4 > 32;
            RealmsScreen.bind("realms:textures/gui/realms/restore_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            RealmsScreen.blit(n * 2, n2 * 2, 0.0f, b ? 28.0f : 0.0f, 23, 28, 23.0f, 56.0f);
            if (b) {
                RealmsBackupScreen.access$902(this.this$0, "\u17f3\u17fd\u17f1\u17b0\u17fc\u17ff\u17fd\u17f5\u17eb\u17ee\u17b0\u17fc\u17eb\u17ea\u17ea\u17f1\u17f0\u17b0\u17ec\u17fb\u17ed\u17ea\u17f1\u17ec\u17fb");
            }
        }
        
        private void drawInfo(final int n, final int n2, final int n3, final int n4) {
            final boolean b = n3 >= n && n3 <= n + 8 && n4 >= n2 && n4 <= n2 + 8 && n4 < this.this$0.height() - 15 && n4 > 32;
            RealmsScreen.bind("realms:textures/gui/realms/plus_icon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            RealmsScreen.blit(n * 2, n2 * 2, 0.0f, b ? 15.0f : 0.0f, 15, 15, 15.0f, 30.0f);
            if (b) {
                RealmsBackupScreen.access$902(this.this$0, "\u17f3\u17fd\u17f1\u17b0\u17fc\u17ff\u17fd\u17f5\u17eb\u17ee\u17b0\u17fd\u17f6\u17ff\u17f0\u17f9\u17fb\u17ed\u17b0\u17ea\u17f1\u17f1\u17f2\u17ea\u17f7\u17ee");
            }
        }
    }
    
    private class RestoreTask extends LongRunningTask
    {
        private final Backup backup;
        final RealmsBackupScreen this$0;
        
        private RestoreTask(final RealmsBackupScreen this$0, final Backup backup) {
            this.this$0 = this$0;
            this.backup = backup;
        }
        
        @Override
        public void run() {
            this.setTitle(RealmsScreen.getLocalizedString("mco.backup.restoring"));
            if (0 >= 6) {
                return;
            }
            if (this.aborted()) {
                return;
            }
            RealmsClient.createRealmsClient().restoreWorld(RealmsBackupScreen.access$100(this.this$0).id, this.backup.backupId);
            this.pause(1);
            if (this.aborted()) {
                return;
            }
            Realms.setScreen(RealmsBackupScreen.access$600(this.this$0).getNewScreen());
        }
        
        private void pause(final int n) {
            Thread.sleep(n * 1000);
        }
        
        RestoreTask(final RealmsBackupScreen realmsBackupScreen, final Backup backup, final RealmsBackupScreen$1 thread) {
            this(realmsBackupScreen, backup);
        }
    }
}
