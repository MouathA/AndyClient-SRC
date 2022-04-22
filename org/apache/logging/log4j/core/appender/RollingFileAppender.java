package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.net.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "RollingFile", category = "Core", elementType = "appender", printObject = true)
public final class RollingFileAppender extends AbstractOutputStreamAppender
{
    private final String fileName;
    private final String filePattern;
    private Object advertisement;
    private final Advertiser advertiser;
    
    private RollingFileAppender(final String s, final Layout layout, final Filter filter, final RollingFileManager rollingFileManager, final String fileName, final String filePattern, final boolean b, final boolean b2, final Advertiser advertiser) {
        super(s, layout, filter, b, b2, rollingFileManager);
        if (advertiser != null) {
            final HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", s);
            this.advertisement = advertiser.advertise(hashMap);
        }
        this.fileName = fileName;
        this.filePattern = filePattern;
        this.advertiser = advertiser;
    }
    
    @Override
    public void stop() {
        super.stop();
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        ((RollingFileManager)this.getManager()).checkRollover(logEvent);
        super.append(logEvent);
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public String getFilePattern() {
        return this.filePattern;
    }
    
    @PluginFactory
    public static RollingFileAppender createAppender(@PluginAttribute("fileName") final String s, @PluginAttribute("filePattern") final String s2, @PluginAttribute("append") final String s3, @PluginAttribute("name") final String s4, @PluginAttribute("bufferedIO") final String s5, @PluginAttribute("immediateFlush") final String s6, @PluginElement("Policy") final TriggeringPolicy triggeringPolicy, @PluginElement("Strategy") RolloverStrategy strategy, @PluginElement("Layout") Layout layout, @PluginElement("Filter") final Filter filter, @PluginAttribute("ignoreExceptions") final String s7, @PluginAttribute("advertise") final String s8, @PluginAttribute("advertiseURI") final String s9, @PluginConfiguration final Configuration configuration) {
        final boolean boolean1 = Booleans.parseBoolean(s3, true);
        final boolean boolean2 = Booleans.parseBoolean(s7, true);
        final boolean boolean3 = Booleans.parseBoolean(s5, true);
        final boolean boolean4 = Booleans.parseBoolean(s6, true);
        final boolean boolean5 = Boolean.parseBoolean(s8);
        if (s4 == null) {
            RollingFileAppender.LOGGER.error("No name provided for FileAppender");
            return null;
        }
        if (s == null) {
            RollingFileAppender.LOGGER.error("No filename was provided for FileAppender with name " + s4);
            return null;
        }
        if (s2 == null) {
            RollingFileAppender.LOGGER.error("No filename pattern provided for FileAppender with name " + s4);
            return null;
        }
        if (triggeringPolicy == null) {
            RollingFileAppender.LOGGER.error("A TriggeringPolicy must be provided");
            return null;
        }
        if (strategy == null) {
            strategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), configuration);
        }
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        final RollingFileManager fileManager = RollingFileManager.getFileManager(s, s2, boolean1, boolean3, triggeringPolicy, strategy, s9, layout);
        if (fileManager == null) {
            return null;
        }
        return new RollingFileAppender(s4, layout, filter, fileManager, s, s2, boolean2, boolean4, boolean5 ? configuration.getAdvertiser() : null);
    }
}
