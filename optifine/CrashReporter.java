package optifine;

import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.client.settings.*;
import shadersmod.client.*;

public class CrashReporter
{
    public static void onCrashReport(final CrashReport crashReport, final CrashReportCategory crashReportCategory) {
        final GameSettings gameSettings = Config.getGameSettings();
        if (gameSettings == null) {
            return;
        }
        if (!gameSettings.snooperEnabled) {
            return;
        }
        final Throwable crashCause = crashReport.getCrashCause();
        if (crashCause == null) {
            return;
        }
        if (crashCause.getClass() == Throwable.class) {
            return;
        }
        if (crashCause.getClass().getName().contains(".fml.client.SplashProgress")) {
            return;
        }
        extendCrashReport(crashReportCategory);
        final String s = "http://optifine.net/crashReport";
        final byte[] bytes = makeReport(crashReport).getBytes("ASCII");
        final IFileUploadListener fileUploadListener = new IFileUploadListener() {
            @Override
            public void fileUploadFinished(final String s, final byte[] array, final Throwable t) {
            }
        };
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("OF-Version", Config.getVersion());
        hashMap.put("OF-Summary", makeSummary(crashReport));
        final FileUploadThread fileUploadThread = new FileUploadThread(s, hashMap, bytes, fileUploadListener);
        fileUploadThread.setPriority(10);
        fileUploadThread.start();
        Thread.sleep(1000L);
    }
    
    private static String makeReport(final CrashReport crashReport) {
        final StringBuffer sb = new StringBuffer();
        sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
        sb.append("Summary: " + makeSummary(crashReport) + "\n");
        sb.append("\n");
        sb.append(crashReport.getCompleteReport());
        sb.append("\n");
        return sb.toString();
    }
    
    private static String makeSummary(final CrashReport crashReport) {
        final Throwable crashCause = crashReport.getCrashCause();
        if (crashCause == null) {
            return "Unknown";
        }
        final StackTraceElement[] stackTrace = crashCause.getStackTrace();
        String trim = "unknown";
        if (stackTrace.length > 0) {
            trim = stackTrace[0].toString().trim();
        }
        return String.valueOf(crashCause.getClass().getName()) + ": " + crashCause.getMessage() + " (" + crashReport.getDescription() + ")" + " [" + trim + "]";
    }
    
    public static void extendCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSection("OptiFine Version", Config.getVersion());
        if (Config.getGameSettings() != null) {
            crashReportCategory.addCrashSection("Render Distance Chunks", new StringBuilder().append(Config.getChunkViewDistance()).toString());
            crashReportCategory.addCrashSection("Mipmaps", new StringBuilder().append(Config.getMipmapLevels()).toString());
            crashReportCategory.addCrashSection("Anisotropic Filtering", new StringBuilder().append(Config.getAnisotropicFilterLevel()).toString());
            crashReportCategory.addCrashSection("Antialiasing", new StringBuilder().append(Config.getAntialiasingLevel()).toString());
            crashReportCategory.addCrashSection("Multitexture", new StringBuilder().append(Config.isMultiTexture()).toString());
        }
        crashReportCategory.addCrashSection("Shaders", new StringBuilder().append(Shaders.getShaderPackName()).toString());
        crashReportCategory.addCrashSection("OpenGlVersion", new StringBuilder().append(Config.openGlVersion).toString());
        crashReportCategory.addCrashSection("OpenGlRenderer", new StringBuilder().append(Config.openGlRenderer).toString());
        crashReportCategory.addCrashSection("OpenGlVendor", new StringBuilder().append(Config.openGlVendor).toString());
        crashReportCategory.addCrashSection("CpuCount", new StringBuilder().append(Config.getAvailableProcessors()).toString());
    }
}
