package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.net.*;

@Plugin(name = "Socket", category = "Core", elementType = "appender", printObject = true)
public class SocketAppender extends AbstractOutputStreamAppender
{
    private Object advertisement;
    private final Advertiser advertiser;
    
    protected SocketAppender(final String s, final Layout layout, final Filter filter, final AbstractSocketManager abstractSocketManager, final boolean b, final boolean b2, final Advertiser advertiser) {
        super(s, layout, filter, b, b2, abstractSocketManager);
        if (advertiser != null) {
            final HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.putAll((Map<?, ?>)abstractSocketManager.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", s);
            this.advertisement = advertiser.advertise(hashMap);
        }
        this.advertiser = advertiser;
    }
    
    @Override
    public void stop() {
        super.stop();
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
    }
    
    @PluginFactory
    public static SocketAppender createAppender(@PluginAttribute("host") final String s, @PluginAttribute("port") final String s2, @PluginAttribute("protocol") final String s3, @PluginAttribute("reconnectionDelay") final String s4, @PluginAttribute("immediateFail") final String s5, @PluginAttribute("name") final String s6, @PluginAttribute("immediateFlush") final String s7, @PluginAttribute("ignoreExceptions") final String s8, @PluginElement("Layout") Layout layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("advertise") final String s9, @PluginConfiguration final Configuration configuration) {
        Booleans.parseBoolean(s7, true);
        final boolean boolean1 = Boolean.parseBoolean(s9);
        final boolean boolean2 = Booleans.parseBoolean(s8, true);
        final boolean boolean3 = Booleans.parseBoolean(s5, true);
        final int int1 = AbstractAppender.parseInt(s4, 0);
        final int int2 = AbstractAppender.parseInt(s2, 0);
        if (layout == null) {
            layout = SerializedLayout.createLayout();
        }
        if (s6 == null) {
            SocketAppender.LOGGER.error("No name provided for SocketAppender");
            return null;
        }
        final Protocol protocol = (Protocol)EnglishEnums.valueOf(Protocol.class, (s3 != null) ? s3 : Protocol.TCP.name());
        if (protocol.equals(Protocol.UDP)) {}
        final AbstractSocketManager socketManager = createSocketManager(protocol, s, int2, int1, boolean3, layout);
        if (socketManager == null) {
            return null;
        }
        return new SocketAppender(s6, layout, filter, socketManager, boolean2, true, boolean1 ? configuration.getAdvertiser() : null);
    }
    
    protected static AbstractSocketManager createSocketManager(final Protocol protocol, final String s, final int n, final int n2, final boolean b, final Layout layout) {
        switch (protocol) {
            case TCP: {
                return TCPSocketManager.getSocketManager(s, n, n2, b, layout);
            }
            case UDP: {
                return DatagramSocketManager.getSocketManager(s, n, layout);
            }
            default: {
                return null;
            }
        }
    }
}
