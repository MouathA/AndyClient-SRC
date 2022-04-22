package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.net.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.filter.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "SMTP", category = "Core", elementType = "appender", printObject = true)
public final class SMTPAppender extends AbstractAppender
{
    private static final int DEFAULT_BUFFER_SIZE = 512;
    protected final SMTPManager manager;
    
    private SMTPAppender(final String s, final Filter filter, final Layout layout, final SMTPManager manager, final boolean b) {
        super(s, filter, layout, b);
        this.manager = manager;
    }
    
    @PluginFactory
    public static SMTPAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("to") final String s2, @PluginAttribute("cc") final String s3, @PluginAttribute("bcc") final String s4, @PluginAttribute("from") final String s5, @PluginAttribute("replyTo") final String s6, @PluginAttribute("subject") final String s7, @PluginAttribute("smtpProtocol") final String s8, @PluginAttribute("smtpHost") final String s9, @PluginAttribute("smtpPort") final String s10, @PluginAttribute("smtpUsername") final String s11, @PluginAttribute("smtpPassword") final String s12, @PluginAttribute("smtpDebug") final String s13, @PluginAttribute("bufferSize") final String s14, @PluginElement("Layout") Layout layout, @PluginElement("Filter") Filter filter, @PluginAttribute("ignoreExceptions") final String s15) {
        if (s == null) {
            SMTPAppender.LOGGER.error("No name provided for SMTPAppender");
            return null;
        }
        final boolean boolean1 = Booleans.parseBoolean(s15, true);
        final int int1 = AbstractAppender.parseInt(s10, 0);
        final boolean boolean2 = Boolean.parseBoolean(s13);
        final int n = (s14 == null) ? 512 : Integer.parseInt(s14);
        if (layout == null) {
            layout = HTMLLayout.createLayout(null, null, null, null, null, null);
        }
        if (filter == null) {
            filter = ThresholdFilter.createFilter(null, null, null);
        }
        final SMTPManager smtpManager = SMTPManager.getSMTPManager(s2, s3, s4, s5, s6, s7, s8, s9, int1, s11, s12, boolean2, filter.toString(), n);
        if (smtpManager == null) {
            return null;
        }
        return new SMTPAppender(s, filter, layout, smtpManager, boolean1);
    }
    
    @Override
    public boolean isFiltered(final LogEvent logEvent) {
        final boolean filtered = super.isFiltered(logEvent);
        if (filtered) {
            this.manager.add(logEvent);
        }
        return filtered;
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        this.manager.sendEvents(this.getLayout(), logEvent);
    }
}
