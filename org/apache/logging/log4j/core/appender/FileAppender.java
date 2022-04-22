package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.net.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "File", category = "Core", elementType = "appender", printObject = true)
public final class FileAppender extends AbstractOutputStreamAppender
{
    private final String fileName;
    private final Advertiser advertiser;
    private Object advertisement;
    
    private FileAppender(final String s, final Layout layout, final Filter filter, final FileManager fileManager, final String fileName, final boolean b, final boolean b2, final Advertiser advertiser) {
        super(s, layout, filter, b, b2, fileManager);
        if (advertiser != null) {
            final HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.putAll((Map<?, ?>)fileManager.getContentFormat());
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
    
    public String getFileName() {
        return this.fileName;
    }
    
    @PluginFactory
    public static FileAppender createAppender(@PluginAttribute("fileName") final String s, @PluginAttribute("append") final String s2, @PluginAttribute("locking") final String s3, @PluginAttribute("name") final String s4, @PluginAttribute("immediateFlush") final String s5, @PluginAttribute("ignoreExceptions") final String s6, @PluginAttribute("bufferedIO") final String s7, @PluginElement("Layout") Layout layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("advertise") final String s8, @PluginAttribute("advertiseURI") final String s9, @PluginConfiguration final Configuration configuration) {
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        final boolean boolean2 = Boolean.parseBoolean(s3);
        Booleans.parseBoolean(s7, true);
        final boolean boolean3 = Boolean.parseBoolean(s8);
        if (boolean2 && false && s7 != null) {
            FileAppender.LOGGER.warn("Locking and buffering are mutually exclusive. No buffering will occur for " + s);
        }
        final boolean boolean4 = Booleans.parseBoolean(s5, true);
        final boolean boolean5 = Booleans.parseBoolean(s6, true);
        if (s4 == null) {
            FileAppender.LOGGER.error("No name provided for FileAppender");
            return null;
        }
        if (s == null) {
            FileAppender.LOGGER.error("No filename provided for FileAppender with name " + s4);
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createLayout(null, null, null, null, null);
        }
        final FileManager fileManager = FileManager.getFileManager(s, boolean1, boolean2, false, s9, layout);
        if (fileManager == null) {
            return null;
        }
        return new FileAppender(s4, layout, filter, fileManager, s, boolean5, boolean4, boolean3 ? configuration.getAdvertiser() : null);
    }
}
