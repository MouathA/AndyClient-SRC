package net.minecraft.crash;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import java.lang.management.*;
import net.minecraft.world.gen.layer.*;
import wdl.*;
import org.apache.commons.lang3.*;
import org.apache.commons.io.*;
import java.text.*;
import java.util.*;
import java.io.*;
import net.minecraft.util.*;

public class CrashReport
{
    private static final Logger logger;
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory theReportCategory;
    private final List crashReportSections;
    private File crashReportFile;
    private boolean field_85059_f;
    private StackTraceElement[] stacktrace;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000990";
        logger = LogManager.getLogger();
    }
    
    public CrashReport(final String description, final Throwable cause) {
        this.theReportCategory = new CrashReportCategory(this, "System Details");
        this.crashReportSections = Lists.newArrayList();
        this.field_85059_f = true;
        this.stacktrace = new StackTraceElement[0];
        this.description = description;
        this.cause = cause;
        this.populateEnvironment();
    }
    
    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                return "1.8";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001197";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Operating System", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001222";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Java Version", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001248";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001275";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Memory", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                final Runtime runtime = Runtime.getRuntime();
                final long maxMemory = runtime.maxMemory();
                final long totalMemory = runtime.totalMemory();
                final long freeMemory = runtime.freeMemory();
                return String.valueOf(freeMemory) + " bytes (" + freeMemory / 1024L / 1024L + " MB) / " + totalMemory + " bytes (" + totalMemory / 1024L / 1024L + " MB) up to " + maxMemory + " bytes (" + maxMemory / 1024L / 1024L + " MB)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001302";
            }
        });
        this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
                final StringBuilder sb = new StringBuilder();
                for (final String s : inputArguments) {
                    if (s.startsWith("-X")) {
                        final int n = 0;
                        int n2 = 0;
                        ++n2;
                        if (n > 0) {
                            sb.append(" ");
                        }
                        sb.append(s);
                    }
                }
                return String.format("%d total; %s", 0, sb.toString());
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001329";
            }
        });
        this.theReportCategory.addCrashSectionCallable("IntCache", new Callable() {
            private static final String __OBFID;
            final CrashReport this$0;
            
            @Override
            public String call() {
                return IntCache.getCacheSizes();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001355";
            }
        });
        WDLHooks.onCrashReportPopulateEnvironment(this);
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Throwable getCrashCause() {
        return this.cause;
    }
    
    public void getSectionsInStringBuilder(final StringBuilder sb) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
            this.stacktrace = (StackTraceElement[])ArrayUtils.subarray(this.crashReportSections.get(0).getStackTrace(), 0, 1);
        }
        if (this.stacktrace != null && this.stacktrace.length > 0) {
            sb.append("-- Head --\n");
            sb.append("Stacktrace:\n");
            final StackTraceElement[] stacktrace = this.stacktrace;
            while (0 < stacktrace.length) {
                sb.append("\t").append("at ").append(stacktrace[0].toString());
                sb.append("\n");
                int n = 0;
                ++n;
            }
            sb.append("\n");
        }
        final Iterator<CrashReportCategory> iterator = this.crashReportSections.iterator();
        while (iterator.hasNext()) {
            iterator.next().appendToStringBuilder(sb);
            sb.append("\n\n");
        }
        this.theReportCategory.appendToStringBuilder(sb);
    }
    
    public String getCauseStackTraceOrString() {
        Throwable cause = this.cause;
        if (cause.getMessage() == null) {
            if (cause instanceof NullPointerException) {
                cause = new NullPointerException(this.description);
            }
            else if (cause instanceof StackOverflowError) {
                cause = new StackOverflowError(this.description);
            }
            else if (cause instanceof OutOfMemoryError) {
                cause = new OutOfMemoryError(this.description);
            }
            cause.setStackTrace(this.cause.getStackTrace());
        }
        cause.toString();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        cause.printStackTrace(printWriter);
        final String string = stringWriter.toString();
        IOUtils.closeQuietly(stringWriter);
        IOUtils.closeQuietly(printWriter);
        return string;
    }
    
    public String getCompleteReport() {
        final StringBuilder sb = new StringBuilder();
        sb.append("---- Minecraft Crash Report ----\n");
        sb.append("// ");
        sb.append(getWittyComment());
        sb.append("\n\n");
        sb.append("Time: ");
        sb.append(new SimpleDateFormat().format(new Date()));
        sb.append("\n");
        sb.append("Description: ");
        sb.append(this.description);
        sb.append("\n\n");
        sb.append(this.getCauseStackTraceOrString());
        sb.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        sb.append("\n\n");
        this.getSectionsInStringBuilder(sb);
        return sb.toString();
    }
    
    public File getFile() {
        return this.crashReportFile;
    }
    
    public boolean saveToFile(final File crashReportFile) {
        if (this.crashReportFile != null) {
            return false;
        }
        if (crashReportFile.getParentFile() != null) {
            crashReportFile.getParentFile().mkdirs();
        }
        final FileWriter fileWriter = new FileWriter(crashReportFile);
        fileWriter.write(this.getCompleteReport());
        fileWriter.close();
        this.crashReportFile = crashReportFile;
        return true;
    }
    
    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }
    
    public CrashReportCategory makeCategory(final String s) {
        return this.makeCategoryDepth(s, 1);
    }
    
    public CrashReportCategory makeCategoryDepth(final String s, final int n) {
        final CrashReportCategory crashReportCategory = new CrashReportCategory(this, s);
        if (this.field_85059_f) {
            final int prunedStackTrace = crashReportCategory.getPrunedStackTrace(n);
            final StackTraceElement[] stackTrace = this.cause.getStackTrace();
            StackTraceElement stackTraceElement = null;
            StackTraceElement stackTraceElement2 = null;
            final int n2 = stackTrace.length - prunedStackTrace;
            if (n2 < 0) {
                System.out.println("Negative index in crash report handler (" + stackTrace.length + "/" + prunedStackTrace + ")");
            }
            if (stackTrace != null && n2 >= 0 && n2 < stackTrace.length) {
                stackTraceElement = stackTrace[n2];
                if (stackTrace.length + 1 - prunedStackTrace < stackTrace.length) {
                    stackTraceElement2 = stackTrace[stackTrace.length + 1 - prunedStackTrace];
                }
            }
            this.field_85059_f = crashReportCategory.firstTwoElementsOfStackTraceMatch(stackTraceElement, stackTraceElement2);
            if (prunedStackTrace > 0 && !this.crashReportSections.isEmpty()) {
                this.crashReportSections.get(this.crashReportSections.size() - 1).trimStackTraceEntriesFromBottom(prunedStackTrace);
            }
            else if (stackTrace != null && stackTrace.length >= prunedStackTrace && n2 >= 0 && n2 < stackTrace.length) {
                System.arraycopy(stackTrace, 0, this.stacktrace = new StackTraceElement[n2], 0, this.stacktrace.length);
            }
            else {
                this.field_85059_f = false;
            }
        }
        this.crashReportSections.add(crashReportCategory);
        return crashReportCategory;
    }
    
    private static String getWittyComment() {
        final String[] array = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
        return array[(int)(System.nanoTime() % array.length)];
    }
    
    public static CrashReport makeCrashReport(final Throwable t, final String s) {
        CrashReport crashReport;
        if (t instanceof ReportedException) {
            crashReport = ((ReportedException)t).getCrashReport();
        }
        else {
            crashReport = new CrashReport(s, t);
        }
        return crashReport;
    }
}
