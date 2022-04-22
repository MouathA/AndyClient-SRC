package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "SizeBasedTriggeringPolicy", category = "Core", printObject = true)
public class SizeBasedTriggeringPolicy implements TriggeringPolicy
{
    protected static final Logger LOGGER;
    private static final long KB = 1024L;
    private static final long MB = 1048576L;
    private static final long GB = 1073741824L;
    private static final long MAX_FILE_SIZE = 10485760L;
    private static final Pattern VALUE_PATTERN;
    private final long maxFileSize;
    private RollingFileManager manager;
    
    protected SizeBasedTriggeringPolicy() {
        this.maxFileSize = 10485760L;
    }
    
    protected SizeBasedTriggeringPolicy(final long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
    
    @Override
    public void initialize(final RollingFileManager manager) {
        this.manager = manager;
    }
    
    @Override
    public boolean isTriggeringEvent(final LogEvent logEvent) {
        return this.manager.getFileSize() > this.maxFileSize;
    }
    
    @Override
    public String toString() {
        return "SizeBasedTriggeringPolicy(size=" + this.maxFileSize + ")";
    }
    
    @PluginFactory
    public static SizeBasedTriggeringPolicy createPolicy(@PluginAttribute("size") final String s) {
        return new SizeBasedTriggeringPolicy((s == null) ? 10485760L : valueOf(s));
    }
    
    private static long valueOf(final String s) {
        final Matcher matcher = SizeBasedTriggeringPolicy.VALUE_PATTERN.matcher(s);
        if (!matcher.matches()) {
            SizeBasedTriggeringPolicy.LOGGER.error("Unable to parse bytes: " + s);
            return 10485760L;
        }
        final long longValue = NumberFormat.getNumberInstance(Locale.getDefault()).parse(matcher.group(1)).longValue();
        final String group = matcher.group(3);
        if (group.equalsIgnoreCase("")) {
            return longValue;
        }
        if (group.equalsIgnoreCase("K")) {
            return longValue * 1024L;
        }
        if (group.equalsIgnoreCase("M")) {
            return longValue * 1048576L;
        }
        if (group.equalsIgnoreCase("G")) {
            return longValue * 1073741824L;
        }
        SizeBasedTriggeringPolicy.LOGGER.error("Units not recognized: " + s);
        return 10485760L;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        VALUE_PATTERN = Pattern.compile("([0-9]+([\\.,][0-9]+)?)\\s*(|K|M|G)B?", 2);
    }
}
