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

@Plugin(name = "RollingRandomAccessFile", category = "Core", elementType = "appender", printObject = true)
public final class RollingRandomAccessFileAppender extends AbstractOutputStreamAppender
{
    private final String fileName;
    private final String filePattern;
    private Object advertisement;
    private final Advertiser advertiser;
    
    private RollingRandomAccessFileAppender(final String s, final Layout layout, final Filter filter, final RollingFileManager rollingFileManager, final String fileName, final String filePattern, final boolean b, final boolean b2, final Advertiser advertiser) {
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
        final RollingRandomAccessFileManager rollingRandomAccessFileManager = (RollingRandomAccessFileManager)this.getManager();
        rollingRandomAccessFileManager.checkRollover(logEvent);
        rollingRandomAccessFileManager.setEndOfBatch(logEvent.isEndOfBatch());
        super.append(logEvent);
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public String getFilePattern() {
        return this.filePattern;
    }
    
    @PluginFactory
    public static RollingRandomAccessFileAppender createAppender(@PluginAttribute("fileName") final String s, @PluginAttribute("filePattern") final String s2, @PluginAttribute("append") final String s3, @PluginAttribute("name") final String s4, @PluginAttribute("immediateFlush") final String s5, @PluginElement("Policy") final TriggeringPolicy triggeringPolicy, @PluginElement("Strategy") RolloverStrategy strategy, @PluginElement("Layout") Layout layout, @PluginElement("Filter") final Filter filter, @PluginAttribute("ignoreExceptions") final String s6, @PluginAttribute("advertise") final String s7, @PluginAttribute("advertiseURI") final String s8, @PluginConfiguration final Configuration configuration) {
        final boolean boolean1 = Booleans.parseBoolean(s3, true);
        final boolean boolean2 = Booleans.parseBoolean(s6, true);
        final boolean boolean3 = Booleans.parseBoolean(s5, true);
        final boolean boolean4 = Boolean.parseBoolean(s7);
        if (s4 == null) {
            RollingRandomAccessFileAppender.LOGGER.error("No name provided for FileAppender");
            return null;
        }
        if (s == null) {
            RollingRandomAccessFileAppender.LOGGER.error("No filename was provided for FileAppender with name " + s4);
            return null;
        }
        if (s2 == null) {
            RollingRandomAccessFileAppender.LOGGER.error("No filename pattern provided for FileAppender with name " + s4);
            return null;
        }
        if (triggeringPolicy == null) {
            RollingRandomAccessFileAppender.LOGGER.error("A TriggeringPolicy must be provided");
            return null;
        }
        if (strategy == null) {
            strategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), configuration);
        }
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        final RollingRandomAccessFileManager rollingRandomAccessFileManager = RollingRandomAccessFileManager.getRollingRandomAccessFileManager(s, s2, boolean1, boolean3, triggeringPolicy, strategy, s8, layout);
        if (rollingRandomAccessFileManager == null) {
            return null;
        }
        return new RollingRandomAccessFileAppender(s4, layout, filter, rollingRandomAccessFileManager, s, s2, boolean2, boolean3, boolean4 ? configuration.getAdvertiser() : null);
    }
}
