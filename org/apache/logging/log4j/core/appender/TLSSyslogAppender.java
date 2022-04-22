package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.net.ssl.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.net.*;

@Plugin(name = "TLSSyslog", category = "Core", elementType = "appender", printObject = true)
public final class TLSSyslogAppender extends SyslogAppender
{
    protected TLSSyslogAppender(final String s, final Layout layout, final Filter filter, final boolean b, final boolean b2, final AbstractSocketManager abstractSocketManager, final Advertiser advertiser) {
        super(s, layout, filter, b, b2, abstractSocketManager, advertiser);
    }
    
    @PluginFactory
    public static TLSSyslogAppender createAppender(@PluginAttribute("host") final String s, @PluginAttribute("port") final String s2, @PluginElement("ssl") final SSLConfiguration sslConfiguration, @PluginAttribute("reconnectionDelay") final String s3, @PluginAttribute("immediateFail") final String s4, @PluginAttribute("name") final String s5, @PluginAttribute("immediateFlush") final String s6, @PluginAttribute("ignoreExceptions") final String s7, @PluginAttribute("facility") final String s8, @PluginAttribute("id") final String s9, @PluginAttribute("enterpriseNumber") final String s10, @PluginAttribute("includeMDC") final String s11, @PluginAttribute("mdcId") final String s12, @PluginAttribute("mdcPrefix") final String s13, @PluginAttribute("eventPrefix") final String s14, @PluginAttribute("newLine") final String s15, @PluginAttribute("newLineEscape") final String s16, @PluginAttribute("appName") final String s17, @PluginAttribute("messageId") final String s18, @PluginAttribute("mdcExcludes") final String s19, @PluginAttribute("mdcIncludes") final String s20, @PluginAttribute("mdcRequired") final String s21, @PluginAttribute("format") final String s22, @PluginElement("filters") final Filter filter, @PluginConfiguration final Configuration configuration, @PluginAttribute("charset") final String s23, @PluginAttribute("exceptionPattern") final String s24, @PluginElement("LoggerFields") final LoggerFields[] array, @PluginAttribute("advertise") final String s25) {
        final boolean boolean1 = Booleans.parseBoolean(s6, true);
        final boolean boolean2 = Booleans.parseBoolean(s7, true);
        final int int1 = AbstractAppender.parseInt(s3, 0);
        final boolean boolean3 = Booleans.parseBoolean(s4, true);
        final int int2 = AbstractAppender.parseInt(s2, 0);
        final boolean boolean4 = Boolean.parseBoolean(s25);
        final AbstractStringLayout abstractStringLayout = "RFC5424".equalsIgnoreCase(s22) ? RFC5424Layout.createLayout(s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s24, "true", array, configuration) : SyslogLayout.createLayout(s8, s15, s16, s23);
        if (s5 == null) {
            TLSSyslogAppender.LOGGER.error("No name provided for TLSSyslogAppender");
            return null;
        }
        final AbstractSocketManager socketManager = createSocketManager(sslConfiguration, s, int2, int1, boolean3, abstractStringLayout);
        if (socketManager == null) {
            return null;
        }
        return new TLSSyslogAppender(s5, abstractStringLayout, filter, boolean2, boolean1, socketManager, boolean4 ? configuration.getAdvertiser() : null);
    }
    
    public static AbstractSocketManager createSocketManager(final SSLConfiguration sslConfiguration, final String s, final int n, final int n2, final boolean b, final Layout layout) {
        return TLSSocketManager.getSocketManager(sslConfiguration, s, n, n2, b, layout);
    }
}
