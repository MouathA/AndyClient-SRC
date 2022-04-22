package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.net.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "Syslog", category = "Core", elementType = "appender", printObject = true)
public class SyslogAppender extends SocketAppender
{
    protected static final String RFC5424 = "RFC5424";
    
    protected SyslogAppender(final String s, final Layout layout, final Filter filter, final boolean b, final boolean b2, final AbstractSocketManager abstractSocketManager, final Advertiser advertiser) {
        super(s, layout, filter, abstractSocketManager, b, b2, advertiser);
    }
    
    @PluginFactory
    public static SyslogAppender createAppender(@PluginAttribute("host") final String s, @PluginAttribute("port") final String s2, @PluginAttribute("protocol") final String s3, @PluginAttribute("reconnectionDelay") final String s4, @PluginAttribute("immediateFail") final String s5, @PluginAttribute("name") final String s6, @PluginAttribute("immediateFlush") final String s7, @PluginAttribute("ignoreExceptions") final String s8, @PluginAttribute("facility") final String s9, @PluginAttribute("id") final String s10, @PluginAttribute("enterpriseNumber") final String s11, @PluginAttribute("includeMDC") final String s12, @PluginAttribute("mdcId") final String s13, @PluginAttribute("mdcPrefix") final String s14, @PluginAttribute("eventPrefix") final String s15, @PluginAttribute("newLine") final String s16, @PluginAttribute("newLineEscape") final String s17, @PluginAttribute("appName") final String s18, @PluginAttribute("messageId") final String s19, @PluginAttribute("mdcExcludes") final String s20, @PluginAttribute("mdcIncludes") final String s21, @PluginAttribute("mdcRequired") final String s22, @PluginAttribute("format") final String s23, @PluginElement("Filters") final Filter filter, @PluginConfiguration final Configuration configuration, @PluginAttribute("charset") final String s24, @PluginAttribute("exceptionPattern") final String s25, @PluginElement("LoggerFields") final LoggerFields[] array, @PluginAttribute("advertise") final String s26) {
        final boolean boolean1 = Booleans.parseBoolean(s7, true);
        final boolean boolean2 = Booleans.parseBoolean(s8, true);
        final int int1 = AbstractAppender.parseInt(s4, 0);
        final boolean boolean3 = Booleans.parseBoolean(s5, true);
        final int int2 = AbstractAppender.parseInt(s2, 0);
        final boolean boolean4 = Boolean.parseBoolean(s26);
        final AbstractStringLayout abstractStringLayout = "RFC5424".equalsIgnoreCase(s23) ? RFC5424Layout.createLayout(s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s25, "false", array, configuration) : SyslogLayout.createLayout(s9, s16, s17, s24);
        if (s6 == null) {
            SyslogAppender.LOGGER.error("No name provided for SyslogAppender");
            return null;
        }
        final AbstractSocketManager socketManager = SocketAppender.createSocketManager((Protocol)EnglishEnums.valueOf(Protocol.class, s3), s, int2, int1, boolean3, abstractStringLayout);
        if (socketManager == null) {
            return null;
        }
        return new SyslogAppender(s6, abstractStringLayout, filter, boolean2, boolean1, socketManager, boolean4 ? configuration.getAdvertiser() : null);
    }
}
