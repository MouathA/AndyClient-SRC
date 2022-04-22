package com.mojang.realmsclient.gui.screens;

import java.util.concurrent.locks.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.realms.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class RealmsDownloadLatestWorldScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private final RealmsScreen lastScreen;
    private final String downloadLink;
    private RealmsButton cancelButton;
    private final String worldName;
    private final DownloadStatus downloadStatus;
    private String errorMessage;
    private String status;
    private String progress;
    private boolean cancelled;
    private boolean showDots;
    private boolean finished;
    private boolean extracting;
    private Long previousWrittenBytes;
    private Long previousTimeSnapshot;
    private long bytesPersSecond;
    private int animTick;
    private int dotIndex;
    private final int WARNING_ID = 100;
    private boolean checked;
    private static final ReentrantLock downloadLock;
    
    public RealmsDownloadLatestWorldScreen(final RealmsScreen lastScreen, final String downloadLink, final String worldName) {
        this.errorMessage = null;
        this.status = null;
        this.progress = null;
        this.cancelled = false;
        this.showDots = true;
        this.finished = false;
        this.extracting = false;
        this.previousWrittenBytes = null;
        this.previousTimeSnapshot = null;
        this.bytesPersSecond = 0L;
        this.animTick = 0;
        this.dotIndex = 0;
        this.checked = false;
        this.lastScreen = lastScreen;
        this.worldName = worldName;
        this.downloadLink = downloadLink;
        this.downloadStatus = new DownloadStatus();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.buttonsAdd(this.cancelButton = RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() - 42, 200, 20, RealmsScreen.getLocalizedString("gui.cancel")));
        this.checkDownloadSize();
    }
    
    private void checkDownloadSize() {
        if (this.finished) {
            return;
        }
        if (!this.checked && this.getContentLength(this.downloadLink) >= 1048576000L) {
            Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Warning, RealmsScreen.getLocalizedString("mco.download.confirmation.line1", humanReadableSize(1048576000L)), RealmsScreen.getLocalizedString("mco.download.confirmation.line2"), false, 100));
        }
        else {
            this.downloadSave();
        }
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        this.checked = true;
        Realms.setScreen(this);
        this.downloadSave();
    }
    
    private long getContentLength(final String s) {
        return new FileDownload().contentLength(s);
    }
    
    @Override
    public void tick() {
        super.tick();
        ++this.animTick;
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (!realmsButton.active()) {
            return;
        }
        if (realmsButton.id() == 0) {
            this.cancelled = true;
            this.backButtonClicked();
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            this.cancelled = true;
            this.backButtonClicked();
        }
    }
    
    private void backButtonClicked() {
        Realms.setScreen(this.lastScreen);
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        if (this.extracting && !this.finished) {
            this.status = RealmsScreen.getLocalizedString("mco.download.extracting");
        }
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.download.title"), this.width() / 2, 20, 16777215);
        this.drawCenteredString(this.status, this.width() / 2, 50, 16777215);
        if (this.showDots) {
            this.drawDots();
        }
        if (this.downloadStatus.bytesWritten != 0L && !this.cancelled) {
            this.drawProgressBar();
            this.drawDownloadSpeed();
        }
        if (this.errorMessage != null) {
            this.drawCenteredString(this.errorMessage, this.width() / 2, 110, 16711680);
        }
        super.render(n, n2, n3);
    }
    
    private void drawDots() {
        final int fontWidth = this.fontWidth(this.status);
        if (this.animTick % 10 == 0) {
            ++this.dotIndex;
        }
        this.drawString(RealmsDownloadLatestWorldScreen.DOTS[this.dotIndex % RealmsDownloadLatestWorldScreen.DOTS.length], this.width() / 2 + fontWidth / 2 + 5, 50, 16777215);
    }
    
    private void drawProgressBar() {
        final double n = this.downloadStatus.bytesWritten / (double)this.downloadStatus.totalBytes * 100.0;
        this.progress = String.format("%.1f", n);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3553);
        final Tezzelator instance = Tezzelator.instance;
        instance.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
        final double n2 = this.width() / 2 - 100;
        instance.vertex(n2 - 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
        instance.vertex(n2 + 200.0 * n / 100.0 + 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
        instance.vertex(n2 + 200.0 * n / 100.0 + 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
        instance.vertex(n2 - 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
        instance.vertex(n2, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
        instance.vertex(n2 + 200.0 * n / 100.0, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
        instance.vertex(n2 + 200.0 * n / 100.0, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
        instance.vertex(n2, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
        instance.end();
        GL11.glEnable(3553);
        this.drawCenteredString(this.progress + " %", this.width() / 2, 84, 16777215);
    }
    
    private void drawDownloadSpeed() {
        if (this.animTick % RealmsSharedConstants.TICKS_PER_SECOND == 0) {
            if (this.previousWrittenBytes != null) {
                long n = System.currentTimeMillis() - this.previousTimeSnapshot;
                if (n == 0L) {
                    n = 1L;
                }
                this.drawDownloadSpeed0(this.bytesPersSecond = 1000L * (this.downloadStatus.bytesWritten - this.previousWrittenBytes) / n);
            }
            this.previousWrittenBytes = this.downloadStatus.bytesWritten;
            this.previousTimeSnapshot = System.currentTimeMillis();
        }
        else {
            this.drawDownloadSpeed0(this.bytesPersSecond);
        }
    }
    
    private void drawDownloadSpeed0(final long n) {
        if (n > 0L) {
            this.drawString("(" + humanReadableSpeed(n) + ")", this.width() / 2 + this.fontWidth(this.progress) / 2 + 15, 84, 16777215);
        }
    }
    
    public static String humanReadableSpeed(final long n) {
        if (n < 1024) {
            return n + " B";
        }
        final int n2 = (int)(Math.log((double)n) / Math.log(1024));
        return String.format("%.1f %sB/s", n / Math.pow(1024, n2), "KMGTPE".charAt(n2 - 1) + "");
    }
    
    public static String humanReadableSize(final long n) {
        if (n < 1024) {
            return n + " B";
        }
        final int n2 = (int)(Math.log((double)n) / Math.log(1024));
        return String.format("%.0f %sB", n / Math.pow(1024, n2), "KMGTPE".charAt(n2 - 1) + "");
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
    }
    
    private void downloadSave() {
        new Thread() {
            final RealmsDownloadLatestWorldScreen this$0;
            
            @Override
            public void run() {
                if (!RealmsDownloadLatestWorldScreen.access$000().tryLock(1L, TimeUnit.SECONDS)) {
                    if (!RealmsDownloadLatestWorldScreen.access$000().isHeldByCurrentThread()) {
                        return;
                    }
                    RealmsDownloadLatestWorldScreen.access$000().unlock();
                    RealmsDownloadLatestWorldScreen.access$1202(this.this$0, false);
                    this.this$0.buttonsRemove(RealmsDownloadLatestWorldScreen.access$800(this.this$0));
                    RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                }
                else {
                    RealmsDownloadLatestWorldScreen.access$102(this.this$0, RealmsScreen.getLocalizedString("mco.download.preparing"));
                    if (RealmsDownloadLatestWorldScreen.access$200(this.this$0)) {
                        RealmsDownloadLatestWorldScreen.access$300(this.this$0);
                        if (!RealmsDownloadLatestWorldScreen.access$000().isHeldByCurrentThread()) {
                            return;
                        }
                        RealmsDownloadLatestWorldScreen.access$000().unlock();
                        RealmsDownloadLatestWorldScreen.access$1202(this.this$0, false);
                        this.this$0.buttonsRemove(RealmsDownloadLatestWorldScreen.access$800(this.this$0));
                        RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                    }
                    else {
                        RealmsDownloadLatestWorldScreen.access$102(this.this$0, RealmsScreen.getLocalizedString("mco.download.downloading", RealmsDownloadLatestWorldScreen.access$400(this.this$0)));
                        final FileDownload fileDownload = new FileDownload();
                        fileDownload.contentLength(RealmsDownloadLatestWorldScreen.access$500(this.this$0));
                        fileDownload.download(RealmsDownloadLatestWorldScreen.access$500(this.this$0), RealmsDownloadLatestWorldScreen.access$400(this.this$0), RealmsDownloadLatestWorldScreen.access$600(this.this$0), this.this$0.getLevelStorageSource());
                        while (!fileDownload.isFinished()) {
                            if (fileDownload.isError()) {
                                fileDownload.cancel();
                                RealmsDownloadLatestWorldScreen.access$702(this.this$0, RealmsScreen.getLocalizedString("mco.download.failed"));
                                RealmsDownloadLatestWorldScreen.access$800(this.this$0).msg(RealmsScreen.getLocalizedString("gui.done"));
                                if (!RealmsDownloadLatestWorldScreen.access$000().isHeldByCurrentThread()) {
                                    return;
                                }
                                RealmsDownloadLatestWorldScreen.access$000().unlock();
                                RealmsDownloadLatestWorldScreen.access$1202(this.this$0, false);
                                this.this$0.buttonsRemove(RealmsDownloadLatestWorldScreen.access$800(this.this$0));
                                RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                                return;
                            }
                            else {
                                if (fileDownload.isExtracting()) {
                                    RealmsDownloadLatestWorldScreen.access$902(this.this$0, true);
                                }
                                if (RealmsDownloadLatestWorldScreen.access$200(this.this$0)) {
                                    fileDownload.cancel();
                                    RealmsDownloadLatestWorldScreen.access$300(this.this$0);
                                    if (!RealmsDownloadLatestWorldScreen.access$000().isHeldByCurrentThread()) {
                                        return;
                                    }
                                    RealmsDownloadLatestWorldScreen.access$000().unlock();
                                    RealmsDownloadLatestWorldScreen.access$1202(this.this$0, false);
                                    this.this$0.buttonsRemove(RealmsDownloadLatestWorldScreen.access$800(this.this$0));
                                    RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                                    return;
                                }
                                else {
                                    Thread.sleep(500L);
                                }
                            }
                        }
                        RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                        RealmsDownloadLatestWorldScreen.access$102(this.this$0, RealmsScreen.getLocalizedString("mco.download.done"));
                        RealmsDownloadLatestWorldScreen.access$800(this.this$0).msg(RealmsScreen.getLocalizedString("gui.done"));
                        if (!RealmsDownloadLatestWorldScreen.access$000().isHeldByCurrentThread()) {
                            return;
                        }
                        RealmsDownloadLatestWorldScreen.access$000().unlock();
                        RealmsDownloadLatestWorldScreen.access$1202(this.this$0, false);
                        this.this$0.buttonsRemove(RealmsDownloadLatestWorldScreen.access$800(this.this$0));
                        RealmsDownloadLatestWorldScreen.access$1102(this.this$0, true);
                    }
                }
            }
        }.start();
    }
    
    private void downloadCancelled() {
        this.status = RealmsScreen.getLocalizedString("mco.download.cancelled");
    }
    
    static ReentrantLock access$000() {
        return RealmsDownloadLatestWorldScreen.downloadLock;
    }
    
    static String access$102(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen, final String status) {
        return realmsDownloadLatestWorldScreen.status = status;
    }
    
    static boolean access$200(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        return realmsDownloadLatestWorldScreen.cancelled;
    }
    
    static void access$300(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        realmsDownloadLatestWorldScreen.downloadCancelled();
    }
    
    static String access$400(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        return realmsDownloadLatestWorldScreen.worldName;
    }
    
    static String access$500(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        return realmsDownloadLatestWorldScreen.downloadLink;
    }
    
    static DownloadStatus access$600(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        return realmsDownloadLatestWorldScreen.downloadStatus;
    }
    
    static String access$702(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen, final String errorMessage) {
        return realmsDownloadLatestWorldScreen.errorMessage = errorMessage;
    }
    
    static RealmsButton access$800(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen) {
        return realmsDownloadLatestWorldScreen.cancelButton;
    }
    
    static boolean access$902(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen, final boolean extracting) {
        return realmsDownloadLatestWorldScreen.extracting = extracting;
    }
    
    static Logger access$1000() {
        return RealmsDownloadLatestWorldScreen.LOGGER;
    }
    
    static boolean access$1102(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen, final boolean finished) {
        return realmsDownloadLatestWorldScreen.finished = finished;
    }
    
    static boolean access$1202(final RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen, final boolean showDots) {
        return realmsDownloadLatestWorldScreen.showDots = showDots;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        RealmsDownloadLatestWorldScreen.DOTS = new String[] { "", ".", ". .", ". . ." };
        downloadLock = new ReentrantLock();
    }
    
    public class DownloadStatus
    {
        public Long bytesWritten;
        public Long totalBytes;
        final RealmsDownloadLatestWorldScreen this$0;
        
        public DownloadStatus(final RealmsDownloadLatestWorldScreen this$0) {
            this.this$0 = this$0;
            this.bytesWritten = 0L;
            this.totalBytes = 0L;
        }
    }
}
