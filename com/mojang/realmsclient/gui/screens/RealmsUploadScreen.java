package com.mojang.realmsclient.gui.screens;

import java.util.concurrent.locks.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.realms.*;
import java.util.concurrent.*;
import com.mojang.realmsclient.util.*;
import com.mojang.realmsclient.client.*;
import com.mojang.realmsclient.dto.*;
import java.util.zip.*;
import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.utils.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class RealmsUploadScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final int CANCEL_BUTTON = 0;
    private static final int BACK_BUTTON = 1;
    private final RealmsResetWorldScreen lastScreen;
    private final RealmsLevelSummary selectedLevel;
    private final long worldId;
    private final int slotId;
    private final UploadStatus uploadStatus;
    private String errorMessage;
    private String status;
    private String progress;
    private boolean cancelled;
    private boolean uploadFinished;
    private boolean showDots;
    private boolean uploadStarted;
    private RealmsButton backButton;
    private RealmsButton cancelButton;
    private int animTick;
    private int dotIndex;
    private Long previousWrittenBytes;
    private Long previousTimeSnapshot;
    private long bytesPersSecond;
    private static final ReentrantLock uploadLock;
    
    public RealmsUploadScreen(final long worldId, final int slotId, final RealmsResetWorldScreen lastScreen, final RealmsLevelSummary selectedLevel) {
        this.errorMessage = null;
        this.status = null;
        this.progress = null;
        this.cancelled = false;
        this.uploadFinished = false;
        this.showDots = true;
        this.uploadStarted = false;
        this.animTick = 0;
        this.dotIndex = 0;
        this.previousWrittenBytes = null;
        this.previousTimeSnapshot = null;
        this.bytesPersSecond = 0L;
        this.worldId = worldId;
        this.slotId = slotId;
        this.lastScreen = lastScreen;
        this.selectedLevel = selectedLevel;
        this.uploadStatus = new UploadStatus();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.backButton = RealmsScreen.newButton(1, this.width() / 2 - 100, this.height() - 42, 200, 20, RealmsScreen.getLocalizedString("gui.back"));
        this.buttonsAdd(this.cancelButton = RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() - 42, 200, 20, RealmsScreen.getLocalizedString("gui.cancel")));
        if (!this.uploadStarted) {
            if (this.lastScreen.slot != -1) {
                this.lastScreen.switchSlot(this);
            }
            else {
                this.upload();
            }
        }
    }
    
    @Override
    public void confirmResult(final boolean b, final int n) {
        if (b && !this.uploadStarted) {
            this.uploadStarted = true;
            Realms.setScreen(this);
            this.upload();
        }
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
            this.lastScreen.confirmResult(true, 0);
        }
        else if (realmsButton.id() == 0) {
            this.cancelled = true;
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            this.cancelled = true;
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        if (!this.uploadFinished && this.uploadStatus.bytesWritten != 0L && this.uploadStatus.bytesWritten == (long)this.uploadStatus.totalBytes) {
            this.status = RealmsScreen.getLocalizedString("mco.upload.verifying");
        }
        this.drawCenteredString(this.status, this.width() / 2, 50, 16777215);
        if (this.showDots) {
            this.drawDots();
        }
        if (this.uploadStatus.bytesWritten != 0L && !this.cancelled) {
            this.drawProgressBar();
            this.drawUploadSpeed();
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
        this.drawString(RealmsUploadScreen.DOTS[this.dotIndex % RealmsUploadScreen.DOTS.length], this.width() / 2 + fontWidth / 2 + 5, 50, 16777215);
    }
    
    private void drawProgressBar() {
        double n = this.uploadStatus.bytesWritten / (double)this.uploadStatus.totalBytes * 100.0;
        if (n > 100.0) {
            n = 100.0;
        }
        this.progress = String.format("%.1f", n);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3553);
        final double n2 = this.width() / 2 - 100;
        final Tezzelator instance = Tezzelator.instance;
        instance.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
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
    
    private void drawUploadSpeed() {
        if (this.animTick % RealmsSharedConstants.TICKS_PER_SECOND == 0) {
            if (this.previousWrittenBytes != null) {
                long n = System.currentTimeMillis() - this.previousTimeSnapshot;
                if (n == 0L) {
                    n = 1L;
                }
                this.drawUploadSpeed0(this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes) / n);
            }
            this.previousWrittenBytes = this.uploadStatus.bytesWritten;
            this.previousTimeSnapshot = System.currentTimeMillis();
        }
        else {
            this.drawUploadSpeed0(this.bytesPersSecond);
        }
    }
    
    private void drawUploadSpeed0(final long n) {
        if (n > 0L) {
            this.drawString("(" + humanReadableByteCount(n) + ")", this.width() / 2 + this.fontWidth(this.progress) / 2 + 15, 84, 16777215);
        }
    }
    
    public static String humanReadableByteCount(final long n) {
        if (n < 1024) {
            return n + " B";
        }
        final int n2 = (int)(Math.log((double)n) / Math.log(1024));
        return String.format("%.1f %sB/s", n / Math.pow(1024, n2), "KMGTPE".charAt(n2 - 1) + "");
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
    }
    
    @Override
    public void tick() {
        super.tick();
        ++this.animTick;
    }
    
    private void upload() {
        this.uploadStarted = true;
        new Thread() {
            final RealmsUploadScreen this$0;
            
            @Override
            public void run() {
                final File file = null;
                final RealmsClient realmsClient = RealmsClient.createRealmsClient();
                final long access$000 = RealmsUploadScreen.access$000(this.this$0);
                if (!RealmsUploadScreen.access$100().tryLock(1L, TimeUnit.SECONDS)) {
                    RealmsUploadScreen.access$1202(this.this$0, true);
                    if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                        return;
                    }
                    RealmsUploadScreen.access$100().unlock();
                    RealmsUploadScreen.access$1402(this.this$0, false);
                    this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                    this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                    if (file != null) {
                        RealmsUploadScreen.access$1100().debug("Deleting file " + file.getAbsolutePath());
                        file.delete();
                    }
                    if (RealmsUploadScreen.access$300(this.this$0)) {
                        return;
                    }
                    realmsClient.uploadFinished(access$000);
                }
                else {
                    RealmsUploadScreen.access$202(this.this$0, RealmsScreen.getLocalizedString("mco.upload.preparing"));
                    UploadInfo upload = null;
                    if (0 < 20) {
                        if (RealmsUploadScreen.access$300(this.this$0)) {
                            RealmsUploadScreen.access$400(this.this$0, access$000);
                            RealmsUploadScreen.access$1202(this.this$0, true);
                            if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                return;
                            }
                            RealmsUploadScreen.access$100().unlock();
                            RealmsUploadScreen.access$1402(this.this$0, false);
                            this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                            this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                            if (file != null) {
                                RealmsUploadScreen.access$1100().debug("Deleting file " + file.getAbsolutePath());
                                file.delete();
                            }
                            if (RealmsUploadScreen.access$300(this.this$0)) {
                                return;
                            }
                            realmsClient.uploadFinished(access$000);
                            return;
                        }
                        else {
                            upload = realmsClient.upload(access$000, UploadTokenCache.get(access$000));
                        }
                    }
                    if (upload == null) {
                        RealmsUploadScreen.access$202(this.this$0, RealmsScreen.getLocalizedString("mco.upload.close.failure"));
                        RealmsUploadScreen.access$1202(this.this$0, true);
                        if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                            return;
                        }
                        RealmsUploadScreen.access$100().unlock();
                        RealmsUploadScreen.access$1402(this.this$0, false);
                        this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                        this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                        if (file != null) {
                            RealmsUploadScreen.access$1100().debug("Deleting file " + file.getAbsolutePath());
                            file.delete();
                        }
                        if (RealmsUploadScreen.access$300(this.this$0)) {
                            return;
                        }
                        realmsClient.uploadFinished(access$000);
                    }
                    else {
                        UploadTokenCache.put(access$000, upload.getToken());
                        if (!upload.isWorldClosed()) {
                            RealmsUploadScreen.access$202(this.this$0, RealmsScreen.getLocalizedString("mco.upload.close.failure"));
                            RealmsUploadScreen.access$1202(this.this$0, true);
                            if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                return;
                            }
                            RealmsUploadScreen.access$100().unlock();
                            RealmsUploadScreen.access$1402(this.this$0, false);
                            this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                            this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                            if (file != null) {
                                RealmsUploadScreen.access$1100().debug("Deleting file " + file.getAbsolutePath());
                                file.delete();
                            }
                            if (RealmsUploadScreen.access$300(this.this$0)) {
                                return;
                            }
                            realmsClient.uploadFinished(access$000);
                        }
                        else if (RealmsUploadScreen.access$300(this.this$0)) {
                            RealmsUploadScreen.access$400(this.this$0, access$000);
                            RealmsUploadScreen.access$1202(this.this$0, true);
                            if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                return;
                            }
                            RealmsUploadScreen.access$100().unlock();
                            RealmsUploadScreen.access$1402(this.this$0, false);
                            this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                            this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                            if (file != null) {
                                RealmsUploadScreen.access$1100().debug("Deleting file " + file.getAbsolutePath());
                                file.delete();
                            }
                            if (RealmsUploadScreen.access$300(this.this$0)) {
                                return;
                            }
                            realmsClient.uploadFinished(access$000);
                        }
                        else {
                            final File access$2 = RealmsUploadScreen.access$600(this.this$0, new File(new File(Realms.getGameDirectoryPath(), "saves"), RealmsUploadScreen.access$500(this.this$0).getLevelId()));
                            if (RealmsUploadScreen.access$300(this.this$0)) {
                                RealmsUploadScreen.access$400(this.this$0, access$000);
                                RealmsUploadScreen.access$1202(this.this$0, true);
                                if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                    return;
                                }
                                RealmsUploadScreen.access$100().unlock();
                                RealmsUploadScreen.access$1402(this.this$0, false);
                                this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                                this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                                if (access$2 != null) {
                                    RealmsUploadScreen.access$1100().debug("Deleting file " + access$2.getAbsolutePath());
                                    access$2.delete();
                                }
                                if (RealmsUploadScreen.access$300(this.this$0)) {
                                    return;
                                }
                                realmsClient.uploadFinished(access$000);
                            }
                            else if (!RealmsUploadScreen.access$700(this.this$0, access$2)) {
                                RealmsUploadScreen.access$802(this.this$0, RealmsScreen.getLocalizedString("mco.upload.size.failure", RealmsUploadScreen.access$500(this.this$0).getLevelName()));
                                RealmsUploadScreen.access$1202(this.this$0, true);
                                if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                    return;
                                }
                                RealmsUploadScreen.access$100().unlock();
                                RealmsUploadScreen.access$1402(this.this$0, false);
                                this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                                this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                                if (access$2 != null) {
                                    RealmsUploadScreen.access$1100().debug("Deleting file " + access$2.getAbsolutePath());
                                    access$2.delete();
                                }
                                if (RealmsUploadScreen.access$300(this.this$0)) {
                                    return;
                                }
                                realmsClient.uploadFinished(access$000);
                            }
                            else {
                                RealmsUploadScreen.access$202(this.this$0, RealmsScreen.getLocalizedString("mco.upload.uploading", RealmsUploadScreen.access$500(this.this$0).getLevelName()));
                                final FileUpload fileUpload = new FileUpload();
                                fileUpload.upload(access$2, RealmsUploadScreen.access$000(this.this$0), RealmsUploadScreen.access$900(this.this$0), upload, Realms.getSessionId(), Realms.getName(), RealmsSharedConstants.VERSION_STRING, RealmsUploadScreen.access$1000(this.this$0));
                                while (!fileUpload.isFinished()) {
                                    if (RealmsUploadScreen.access$300(this.this$0)) {
                                        fileUpload.cancel();
                                        RealmsUploadScreen.access$400(this.this$0, access$000);
                                        RealmsUploadScreen.access$1202(this.this$0, true);
                                        if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                            return;
                                        }
                                        RealmsUploadScreen.access$100().unlock();
                                        RealmsUploadScreen.access$1402(this.this$0, false);
                                        this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                                        this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                                        if (access$2 != null) {
                                            RealmsUploadScreen.access$1100().debug("Deleting file " + access$2.getAbsolutePath());
                                            access$2.delete();
                                        }
                                        if (RealmsUploadScreen.access$300(this.this$0)) {
                                            return;
                                        }
                                        realmsClient.uploadFinished(access$000);
                                        return;
                                    }
                                    else {
                                        Thread.sleep(500L);
                                    }
                                }
                                if (fileUpload.getStatusCode() >= 200 && fileUpload.getStatusCode() < 300) {
                                    RealmsUploadScreen.access$1202(this.this$0, true);
                                    RealmsUploadScreen.access$202(this.this$0, RealmsScreen.getLocalizedString("mco.upload.done"));
                                    RealmsUploadScreen.access$1300(this.this$0).msg(RealmsScreen.getLocalizedString("gui.done"));
                                    UploadTokenCache.invalidate(access$000);
                                }
                                else if (fileUpload.getStatusCode() == 400 && fileUpload.getErrorMessage() != null) {
                                    RealmsUploadScreen.access$802(this.this$0, RealmsScreen.getLocalizedString("mco.upload.failed", fileUpload.getErrorMessage()));
                                }
                                else {
                                    RealmsUploadScreen.access$802(this.this$0, RealmsScreen.getLocalizedString("mco.upload.failed", fileUpload.getStatusCode()));
                                }
                                RealmsUploadScreen.access$1202(this.this$0, true);
                                if (!RealmsUploadScreen.access$100().isHeldByCurrentThread()) {
                                    return;
                                }
                                RealmsUploadScreen.access$100().unlock();
                                RealmsUploadScreen.access$1402(this.this$0, false);
                                this.this$0.buttonsRemove(RealmsUploadScreen.access$1500(this.this$0));
                                this.this$0.buttonsAdd(RealmsUploadScreen.access$1300(this.this$0));
                                if (access$2 != null) {
                                    RealmsUploadScreen.access$1100().debug("Deleting file " + access$2.getAbsolutePath());
                                    access$2.delete();
                                }
                                if (RealmsUploadScreen.access$300(this.this$0)) {
                                    return;
                                }
                                realmsClient.uploadFinished(access$000);
                            }
                        }
                    }
                }
            }
        }.start();
    }
    
    private void uploadCancelled(final long n) {
        this.status = RealmsScreen.getLocalizedString("mco.upload.cancelled");
        final String value = UploadTokenCache.get(n);
        UploadTokenCache.invalidate(n);
        RealmsClient.createRealmsClient().uploadCancelled(n, value);
    }
    
    private boolean verify(final File file) {
        return file.length() < 1048576000L;
    }
    
    private File tarGzipArchive(final File file) throws IOException {
        final File tempFile = File.createTempFile("realms-upload-file", ".tar.gz");
        final TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(tempFile)));
        this.addFileToTarGz(tarArchiveOutputStream, file.getAbsolutePath(), "world", true);
        tarArchiveOutputStream.finish();
        final File file2 = tempFile;
        if (tarArchiveOutputStream != null) {
            tarArchiveOutputStream.close();
        }
        return file2;
    }
    
    private void addFileToTarGz(final TarArchiveOutputStream tarArchiveOutputStream, final String s, final String s2, final boolean b) throws IOException {
        if (this.cancelled) {
            return;
        }
        final File file = new File(s);
        final String s3 = b ? s2 : (s2 + file.getName());
        tarArchiveOutputStream.putArchiveEntry(new TarArchiveEntry(file, s3));
        if (file.isFile()) {
            IOUtils.copy(new FileInputStream(file), tarArchiveOutputStream);
            tarArchiveOutputStream.closeArchiveEntry();
        }
        else {
            tarArchiveOutputStream.closeArchiveEntry();
            final File[] listFiles = file.listFiles();
            if (listFiles != null) {
                final File[] array = listFiles;
                while (0 < array.length) {
                    this.addFileToTarGz(tarArchiveOutputStream, array[0].getAbsolutePath(), s3 + "/", false);
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    static long access$000(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.worldId;
    }
    
    static ReentrantLock access$100() {
        return RealmsUploadScreen.uploadLock;
    }
    
    static String access$202(final RealmsUploadScreen realmsUploadScreen, final String status) {
        return realmsUploadScreen.status = status;
    }
    
    static boolean access$300(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.cancelled;
    }
    
    static void access$400(final RealmsUploadScreen realmsUploadScreen, final long n) {
        realmsUploadScreen.uploadCancelled(n);
    }
    
    static RealmsLevelSummary access$500(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.selectedLevel;
    }
    
    static File access$600(final RealmsUploadScreen realmsUploadScreen, final File file) throws IOException {
        return realmsUploadScreen.tarGzipArchive(file);
    }
    
    static boolean access$700(final RealmsUploadScreen realmsUploadScreen, final File file) {
        return realmsUploadScreen.verify(file);
    }
    
    static String access$802(final RealmsUploadScreen realmsUploadScreen, final String errorMessage) {
        return realmsUploadScreen.errorMessage = errorMessage;
    }
    
    static int access$900(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.slotId;
    }
    
    static UploadStatus access$1000(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.uploadStatus;
    }
    
    static Logger access$1100() {
        return RealmsUploadScreen.LOGGER;
    }
    
    static boolean access$1202(final RealmsUploadScreen realmsUploadScreen, final boolean uploadFinished) {
        return realmsUploadScreen.uploadFinished = uploadFinished;
    }
    
    static RealmsButton access$1300(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.backButton;
    }
    
    static boolean access$1402(final RealmsUploadScreen realmsUploadScreen, final boolean showDots) {
        return realmsUploadScreen.showDots = showDots;
    }
    
    static RealmsButton access$1500(final RealmsUploadScreen realmsUploadScreen) {
        return realmsUploadScreen.cancelButton;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        RealmsUploadScreen.DOTS = new String[] { "", ".", ". .", ". . ." };
        uploadLock = new ReentrantLock();
    }
}
