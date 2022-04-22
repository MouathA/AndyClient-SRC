package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.net.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "RandomAccessFile", category = "Core", elementType = "appender", printObject = true)
public final class RandomAccessFileAppender extends AbstractOutputStreamAppender
{
    private final String fileName;
    private Object advertisement;
    private final Advertiser advertiser;
    
    private RandomAccessFileAppender(final String s, final Layout layout, final Filter filter, final RandomAccessFileManager randomAccessFileManager, final String fileName, final boolean b, final boolean b2, final Advertiser advertiser) {
        super(s, layout, filter, b, b2, randomAccessFileManager);
        if (advertiser != null) {
            final HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.putAll((Map<?, ?>)randomAccessFileManager.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", s);
            this.advertisement = advertiser.advertise(hashMap);
        }
        this.fileName = fileName;
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
        ((RandomAccessFileManager)this.getManager()).setEndOfBatch(logEvent.isEndOfBatch());
        super.append(logEvent);
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    @PluginFactory
    public static RandomAccessFileAppender createAppender(@PluginAttribute("fileName") final String s, @PluginAttribute("append") final String s2, @PluginAttribute("name") final String s3, @PluginAttribute("immediateFlush") final String s4, @PluginAttribute("ignoreExceptions") final String s5, @PluginElement("Layout") Layout layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("advertise") final String s6, @PluginAttribute("advertiseURI") final String s7, @PluginConfiguration final Configuration configuration) {
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        final boolean boolean2 = Booleans.parseBoolean(s4, true);
        final boolean boolean3 = Booleans.parseBoolean(s5, true);
        final boolean boolean4 = Boolean.parseBoolean(s6);
        if (s3 == null) {
            RandomAccessFileAppender.LOGGER.error("No name provided for FileAppender");
            return null;
        }
        if (s == null) {
            RandomAccessFileAppender.LOGGER.error("No filename provided for FileAppender with name " + s3);
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        final RandomAccessFileManager fileManager = RandomAccessFileManager.getFileManager(s, boolean1, boolean2, s7, layout);
        if (fileManager == null) {
            return null;
        }
        return new RandomAccessFileAppender(s3, layout, filter, fileManager, s, boolean3, boolean2, boolean4 ? configuration.getAdvertiser() : null);
    }
}
