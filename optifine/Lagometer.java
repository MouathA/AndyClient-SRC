package optifine;

import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import net.minecraft.profiler.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class Lagometer
{
    private static Minecraft mc;
    private static GameSettings gameSettings;
    private static Profiler profiler;
    public static boolean active;
    public static TimerNano timerTick;
    public static TimerNano timerScheduledExecutables;
    public static TimerNano timerChunkUpload;
    public static TimerNano timerChunkUpdate;
    public static TimerNano timerVisibility;
    public static TimerNano timerTerrain;
    public static TimerNano timerServer;
    private static long[] timesFrame;
    private static long[] timesTick;
    private static long[] timesScheduledExecutables;
    private static long[] timesChunkUpload;
    private static long[] timesChunkUpdate;
    private static long[] timesVisibility;
    private static long[] timesTerrain;
    private static long[] timesServer;
    private static boolean[] gcs;
    private static int numRecordedFrameTimes;
    private static long prevFrameTimeNano;
    private static long renderTimeNano;
    private static long memTimeStartMs;
    private static long memStart;
    private static long memTimeLast;
    private static long memLast;
    private static long memTimeDiffMs;
    private static long memDiff;
    private static int memMbSec;
    private static final String[] lllllIlllIIIIll;
    private static String[] lllllIlllIIIlll;
    
    static {
        lIlllIIllIIlIIlI();
        lIlllIIllIIlIIIl();
        Lagometer.active = false;
        Lagometer.timerTick = new TimerNano();
        Lagometer.timerScheduledExecutables = new TimerNano();
        Lagometer.timerChunkUpload = new TimerNano();
        Lagometer.timerChunkUpdate = new TimerNano();
        Lagometer.timerVisibility = new TimerNano();
        Lagometer.timerTerrain = new TimerNano();
        Lagometer.timerServer = new TimerNano();
        Lagometer.timesFrame = new long[512];
        Lagometer.timesTick = new long[512];
        Lagometer.timesScheduledExecutables = new long[512];
        Lagometer.timesChunkUpload = new long[512];
        Lagometer.timesChunkUpdate = new long[512];
        Lagometer.timesVisibility = new long[512];
        Lagometer.timesTerrain = new long[512];
        Lagometer.timesServer = new long[512];
        Lagometer.gcs = new boolean[512];
        Lagometer.numRecordedFrameTimes = 0;
        Lagometer.prevFrameTimeNano = -1L;
        Lagometer.renderTimeNano = 0L;
        Lagometer.memTimeStartMs = System.currentTimeMillis();
        Lagometer.memStart = getMemoryUsed();
        Lagometer.memTimeLast = Lagometer.memTimeStartMs;
        Lagometer.memLast = Lagometer.memStart;
        Lagometer.memTimeDiffMs = 1L;
        Lagometer.memDiff = 0L;
        Lagometer.memMbSec = 0;
    }
    
    public static boolean updateMemoryAllocation() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long memoryUsed = getMemoryUsed();
        boolean b = false;
        if (memoryUsed < Lagometer.memLast) {
            final int memMbSec = (int)(Lagometer.memDiff / 1000000.0 / (Lagometer.memTimeDiffMs / 1000.0));
            if (memMbSec > 0) {
                Lagometer.memMbSec = memMbSec;
            }
            Lagometer.memTimeStartMs = currentTimeMillis;
            Lagometer.memStart = memoryUsed;
            Lagometer.memTimeDiffMs = 0L;
            Lagometer.memDiff = 0L;
            b = true;
        }
        else {
            Lagometer.memTimeDiffMs = currentTimeMillis - Lagometer.memTimeStartMs;
            Lagometer.memDiff = memoryUsed - Lagometer.memStart;
        }
        Lagometer.memTimeLast = currentTimeMillis;
        Lagometer.memLast = memoryUsed;
        return b;
    }
    
    private static long getMemoryUsed() {
        final Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    public static void updateLagometer() {
        if (Lagometer.mc == null) {
            Lagometer.mc = Minecraft.getMinecraft();
            Lagometer.gameSettings = Lagometer.mc.gameSettings;
            Lagometer.profiler = Lagometer.mc.mcProfiler;
        }
        if (Lagometer.gameSettings.showDebugInfo && Lagometer.gameSettings.ofLagometer) {
            Lagometer.active = true;
            final long nanoTime = System.nanoTime();
            if (Lagometer.prevFrameTimeNano == -1L) {
                Lagometer.prevFrameTimeNano = nanoTime;
            }
            else {
                final int n = Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - 1;
                ++Lagometer.numRecordedFrameTimes;
                final boolean updateMemoryAllocation = updateMemoryAllocation();
                Lagometer.timesFrame[n] = nanoTime - Lagometer.prevFrameTimeNano - Lagometer.renderTimeNano;
                Lagometer.timesTick[n] = Lagometer.timerTick.timeNano;
                Lagometer.timesScheduledExecutables[n] = Lagometer.timerScheduledExecutables.timeNano;
                Lagometer.timesChunkUpload[n] = Lagometer.timerChunkUpload.timeNano;
                Lagometer.timesChunkUpdate[n] = Lagometer.timerChunkUpdate.timeNano;
                Lagometer.timesVisibility[n] = Lagometer.timerVisibility.timeNano;
                Lagometer.timesTerrain[n] = Lagometer.timerTerrain.timeNano;
                Lagometer.timesServer[n] = Lagometer.timerServer.timeNano;
                Lagometer.gcs[n] = updateMemoryAllocation;
                TimerNano.access$0(Lagometer.timerTick);
                TimerNano.access$0(Lagometer.timerScheduledExecutables);
                TimerNano.access$0(Lagometer.timerVisibility);
                TimerNano.access$0(Lagometer.timerChunkUpdate);
                TimerNano.access$0(Lagometer.timerChunkUpload);
                TimerNano.access$0(Lagometer.timerTerrain);
                TimerNano.access$0(Lagometer.timerServer);
                Lagometer.prevFrameTimeNano = System.nanoTime();
            }
        }
        else {
            Lagometer.active = false;
            Lagometer.prevFrameTimeNano = -1L;
        }
    }
    
    public static void showLagometer(final ScaledResolution scaledResolution) {
        if (Lagometer.gameSettings != null && Lagometer.gameSettings.ofLagometer) {
            final long nanoTime = System.nanoTime();
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, Lagometer.mc.displayWidth, Lagometer.mc.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.func_179090_x();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawing(1);
            for (int i = 0; i < Lagometer.timesFrame.length; ++i) {
                int n = (i - Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - 1) * 100 / Lagometer.timesFrame.length;
                n += 155;
                final float n2 = (float)Lagometer.mc.displayHeight;
                if (Lagometer.gcs[i]) {
                    renderTime(i, Lagometer.timesFrame[i], n, n / 2, 0, n2, worldRenderer);
                }
                else {
                    renderTime(i, Lagometer.timesFrame[i], n, n, n, n2, worldRenderer);
                    final float n3 = n2 - renderTime(i, Lagometer.timesServer[i], n / 2, n / 2, n / 2, n2, worldRenderer);
                    final float n4 = n3 - renderTime(i, Lagometer.timesTerrain[i], 0, n, 0, n3, worldRenderer);
                    final float n5 = n4 - renderTime(i, Lagometer.timesVisibility[i], n, n, 0, n4, worldRenderer);
                    final float n6 = n5 - renderTime(i, Lagometer.timesChunkUpdate[i], n, 0, 0, n5, worldRenderer);
                    final float n7 = n6 - renderTime(i, Lagometer.timesChunkUpload[i], n, 0, n, n6, worldRenderer);
                    final float n8 = n7 - renderTime(i, Lagometer.timesScheduledExecutables[i], 0, 0, n, n7, worldRenderer);
                    final float n9 = n8 - renderTime(i, Lagometer.timesTick[i], 0, n, n, n8, worldRenderer);
                }
            }
            renderTimeDivider(0, Lagometer.timesFrame.length, 33333333L, 196, 196, 196, (float)Lagometer.mc.displayHeight, worldRenderer);
            renderTimeDivider(0, Lagometer.timesFrame.length, 16666666L, 196, 196, 196, (float)Lagometer.mc.displayHeight, worldRenderer);
            instance.draw();
            GlStateManager.func_179098_w();
            final int n10 = Lagometer.mc.displayHeight - 80;
            final int n11 = Lagometer.mc.displayHeight - 160;
            Minecraft.fontRendererObj.drawString(Lagometer.lllllIlllIIIIll[0], 2, n11 + 1, -8947849);
            Minecraft.fontRendererObj.drawString(Lagometer.lllllIlllIIIIll[1], 1, n11, -3881788);
            Minecraft.fontRendererObj.drawString(Lagometer.lllllIlllIIIIll[2], 2, n10 + 1, -8947849);
            Minecraft.fontRendererObj.drawString(Lagometer.lllllIlllIIIIll[3], 1, n10, -3881788);
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.func_179098_w();
            final float limit = Config.limit(1.0f - (float)((System.currentTimeMillis() - Lagometer.memTimeStartMs) / 1000.0), 0.0f, 1.0f);
            final int n12 = (int)(170.0f + limit * 85.0f) << 16 | (int)(100.0f + limit * 55.0f) << 8 | (int)(10.0f + limit * 10.0f);
            final int n13 = 512 / scaledResolution.getScaleFactor() + 2;
            final int n14 = Lagometer.mc.displayHeight / scaledResolution.getScaleFactor() - 8;
            final GuiIngame ingameGUI = Minecraft.ingameGUI;
            Gui.drawRect(n13 - 1, n14 - 1, n13 + 50, n14 + 10, -1605349296);
            Minecraft.fontRendererObj.drawString(Lagometer.lllllIlllIIIIll[4] + Lagometer.memMbSec + Lagometer.lllllIlllIIIIll[5], n13, n14, n12);
            Lagometer.renderTimeNano = System.nanoTime() - nanoTime;
        }
    }
    
    private static long renderTime(final int n, final long n2, final int n3, final int n4, final int n5, final float n6, final WorldRenderer worldRenderer) {
        final long n7 = n2 / 200000L;
        if (n7 < 3L) {
            return 0L;
        }
        worldRenderer.func_178961_b(n3, n4, n5, 255);
        worldRenderer.addVertex(n + 0.5f, n6 - n7 + 0.5f, 0.0);
        worldRenderer.addVertex(n + 0.5f, n6 + 0.5f, 0.0);
        return n7;
    }
    
    private static long renderTimeDivider(final int n, final int n2, final long n3, final int n4, final int n5, final int n6, final float n7, final WorldRenderer worldRenderer) {
        final long n8 = n3 / 200000L;
        if (n8 < 3L) {
            return 0L;
        }
        worldRenderer.func_178961_b(n4, n5, n6, 255);
        worldRenderer.addVertex(n + 0.5f, n7 - n8 + 0.5f, 0.0);
        worldRenderer.addVertex(n2 + 0.5f, n7 - n8 + 0.5f, 0.0);
        return n8;
    }
    
    public static boolean isActive() {
        return Lagometer.active;
    }
    
    private static void lIlllIIllIIlIIIl() {
        (lllllIlllIIIIll = new String[6])[0] = lIlllIIllIIIlllI(Lagometer.lllllIlllIIIlll[0], Lagometer.lllllIlllIIIlll[1]);
        Lagometer.lllllIlllIIIIll[1] = lIlllIIllIIIllll(Lagometer.lllllIlllIIIlll[2], Lagometer.lllllIlllIIIlll[3]);
        Lagometer.lllllIlllIIIIll[2] = lIlllIIllIIIlllI(Lagometer.lllllIlllIIIlll[4], Lagometer.lllllIlllIIIlll[5]);
        Lagometer.lllllIlllIIIIll[3] = lIlllIIllIIlIIII(Lagometer.lllllIlllIIIlll[6], Lagometer.lllllIlllIIIlll[7]);
        Lagometer.lllllIlllIIIIll[4] = lIlllIIllIIIllll(Lagometer.lllllIlllIIIlll[8], Lagometer.lllllIlllIIIlll[9]);
        Lagometer.lllllIlllIIIIll[5] = lIlllIIllIIIlllI(Lagometer.lllllIlllIIIlll[10], Lagometer.lllllIlllIIIlll[11]);
        Lagometer.lllllIlllIIIlll = null;
    }
    
    private static void lIlllIIllIIlIIlI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        Lagometer.lllllIlllIIIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIlllIIllIIlIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIlllIIllIIIllll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIlllIIllIIIlllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static class TimerNano
    {
        public long timeStartNano;
        public long timeNano;
        
        public TimerNano() {
            this.timeStartNano = 0L;
            this.timeNano = 0L;
        }
        
        public void start() {
            if (Lagometer.active && this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }
        
        public void end() {
            if (Lagometer.active && this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }
        
        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
        
        static void access$0(final TimerNano timerNano) {
            timerNano.reset();
        }
    }
}
