package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.lookup.*;
import java.io.*;
import org.apache.logging.log4j.core.appender.rolling.helper.*;
import java.util.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "DefaultRolloverStrategy", category = "Core", printObject = true)
public class DefaultRolloverStrategy implements RolloverStrategy
{
    protected static final Logger LOGGER;
    private static final int MIN_WINDOW_SIZE = 1;
    private static final int DEFAULT_WINDOW_SIZE = 7;
    private final int maxIndex;
    private final int minIndex;
    private final boolean useMax;
    private final StrSubstitutor subst;
    private final int compressionLevel;
    
    protected DefaultRolloverStrategy(final int minIndex, final int maxIndex, final boolean useMax, final int compressionLevel, final StrSubstitutor subst) {
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.useMax = useMax;
        this.compressionLevel = compressionLevel;
        this.subst = subst;
    }
    
    @Override
    public RolloverDescription rollover(final RollingFileManager rollingFileManager) throws SecurityException {
        if (this.maxIndex < 0) {
            return null;
        }
        final int purge;
        if ((purge = this.purge(this.minIndex, this.maxIndex, rollingFileManager)) < 0) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        rollingFileManager.getPatternProcessor().formatFileName(this.subst, sb, purge);
        final String fileName = rollingFileManager.getFileName();
        final String string;
        String s = string = sb.toString();
        Action action = null;
        if (s.endsWith(".gz")) {
            s = s.substring(0, s.length() - 3);
            action = new GZCompressAction(new File(s), new File(string), true);
        }
        else if (s.endsWith(".zip")) {
            s = s.substring(0, s.length() - 4);
            action = new ZipCompressAction(new File(s), new File(string), true, this.compressionLevel);
        }
        return new RolloverDescriptionImpl(fileName, false, new FileRenameAction(new File(fileName), new File(s), false), action);
    }
    
    private int purge(final int n, final int n2, final RollingFileManager rollingFileManager) {
        return this.useMax ? this.purgeAscending(n, n2, rollingFileManager) : this.purgeDescending(n, n2, rollingFileManager);
    }
    
    private int purgeDescending(final int n, final int n2, final RollingFileManager rollingFileManager) {
        final ArrayList<FileRenameAction> list = new ArrayList<FileRenameAction>();
        final StringBuilder sb = new StringBuilder();
        rollingFileManager.getPatternProcessor().formatFileName(sb, (Object)n);
        String replace = this.subst.replace(sb);
        if (!replace.endsWith(".gz")) {
            if (replace.endsWith(".zip")) {}
        }
        int i = n;
        while (i <= n2) {
            File file = new File(replace);
            if (4 > 0) {
                final File file2 = new File(replace.substring(0, replace.length() - 4));
                if (file.exists()) {
                    if (file2.exists()) {
                        file2.delete();
                    }
                }
                else {
                    file = file2;
                }
            }
            if (!file.exists()) {
                break;
            }
            if (i == n2) {
                if (!file.delete()) {
                    return -1;
                }
                break;
            }
            else {
                sb.setLength(0);
                rollingFileManager.getPatternProcessor().formatFileName(sb, (Object)(i + 1));
                String s2;
                final String s = s2 = this.subst.replace(sb);
                if (true) {
                    s2 = s.substring(0, s.length() - 4);
                }
                list.add(new FileRenameAction(file, new File(s2), true));
                replace = s;
                ++i;
            }
        }
        for (int j = list.size() - 1; j >= 0; --j) {
            if (!((FileRenameAction)list.get(j)).execute()) {
                return -1;
            }
        }
        return n;
    }
    
    private int purgeAscending(final int n, final int n2, final RollingFileManager rollingFileManager) {
        final ArrayList<FileRenameAction> list = new ArrayList<FileRenameAction>();
        final StringBuilder sb = new StringBuilder();
        rollingFileManager.getPatternProcessor().formatFileName(sb, (Object)n2);
        String s = this.subst.replace(sb);
        if (!s.endsWith(".gz")) {
            if (s.endsWith(".zip")) {}
        }
        for (int i = n2; i >= n; --i) {
            File file = new File(s);
            if (i != n2 || !file.exists()) {
                if (!false && file.exists()) {
                    break;
                }
            }
            if (4 > 0) {
                final File file2 = new File(s.substring(0, s.length() - 4));
                if (file.exists()) {
                    if (file2.exists()) {
                        file2.delete();
                    }
                }
                else {
                    file = file2;
                }
            }
            if (file.exists()) {
                if (i == n) {
                    if (!file.delete()) {
                        return -1;
                    }
                    break;
                }
                else {
                    sb.setLength(0);
                    rollingFileManager.getPatternProcessor().formatFileName(sb, (Object)(i - 1));
                    String s3;
                    final String s2 = s3 = this.subst.replace(sb);
                    if (true) {
                        s3 = s2.substring(0, s2.length() - 4);
                    }
                    list.add(new FileRenameAction(file, new File(s3), true));
                    s = s2;
                }
            }
            else {
                sb.setLength(0);
                rollingFileManager.getPatternProcessor().formatFileName(sb, (Object)(i - 1));
                s = this.subst.replace(sb);
            }
        }
        if (!false) {}
        for (int j = list.size() - 1; j >= 0; --j) {
            if (!((FileRenameAction)list.get(j)).execute()) {
                return -1;
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "DefaultRolloverStrategy(min=" + this.minIndex + ", max=" + this.maxIndex + ")";
    }
    
    @PluginFactory
    public static DefaultRolloverStrategy createStrategy(@PluginAttribute("max") final String s, @PluginAttribute("min") final String s2, @PluginAttribute("fileIndex") final String s3, @PluginAttribute("compressionLevel") final String s4, @PluginConfiguration final Configuration configuration) {
        final boolean b = s3 == null || s3.equalsIgnoreCase("max");
        if (s2 != null) {
            Integer.parseInt(s2);
            if (1 < 1) {
                DefaultRolloverStrategy.LOGGER.error("Minimum window size too small. Limited to 1");
            }
        }
        if (s != null) {
            Integer.parseInt(s);
            if (7 < 1) {
                final int n = (1 < 7) ? 7 : 1;
                DefaultRolloverStrategy.LOGGER.error("Maximum window size must be greater than the minimum windows size. Set to " + 7);
            }
        }
        return new DefaultRolloverStrategy(1, 7, b, Integers.parseInt(s4, -1), configuration.getStrSubstitutor());
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
