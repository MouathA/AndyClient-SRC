package org.apache.logging.log4j.core.appender.rewrite;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "MapRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class MapRewritePolicy implements RewritePolicy
{
    protected static final Logger LOGGER;
    private final Map map;
    private final Mode mode;
    
    private MapRewritePolicy(final Map map, final Mode mode) {
        this.map = map;
        this.mode = mode;
    }
    
    @Override
    public LogEvent rewrite(final LogEvent logEvent) {
        final Message message = logEvent.getMessage();
        if (message == null || !(message instanceof MapMessage)) {
            return logEvent;
        }
        final HashMap hashMap = new HashMap<Object, Object>(((MapMessage)message).getData());
        switch (this.mode) {
            case Add: {
                hashMap.putAll((Map<?, ?>)this.map);
                break;
            }
            default: {
                for (final Map.Entry<Object, V> entry : this.map.entrySet()) {
                    if (hashMap.containsKey(entry.getKey())) {
                        hashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                break;
            }
        }
        final MapMessage instance = ((MapMessage)message).newInstance(hashMap);
        if (logEvent instanceof Log4jLogEvent) {
            final Log4jLogEvent log4jLogEvent = (Log4jLogEvent)logEvent;
            return Log4jLogEvent.createEvent(log4jLogEvent.getLoggerName(), log4jLogEvent.getMarker(), log4jLogEvent.getFQCN(), log4jLogEvent.getLevel(), instance, log4jLogEvent.getThrownProxy(), log4jLogEvent.getContextMap(), log4jLogEvent.getContextStack(), log4jLogEvent.getThreadName(), log4jLogEvent.getSource(), log4jLogEvent.getMillis());
        }
        return new Log4jLogEvent(logEvent.getLoggerName(), logEvent.getMarker(), logEvent.getFQCN(), logEvent.getLevel(), instance, logEvent.getThrown(), logEvent.getContextMap(), logEvent.getContextStack(), logEvent.getThreadName(), logEvent.getSource(), logEvent.getMillis());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("mode=").append(this.mode);
        sb.append(" {");
        for (final Map.Entry<String, V> entry : this.map.entrySet()) {
            if (!false) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append((String)entry.getValue());
        }
        sb.append("}");
        return sb.toString();
    }
    
    @PluginFactory
    public static MapRewritePolicy createPolicy(@PluginAttribute("mode") final String s, @PluginElement("KeyValuePair") final KeyValuePair[] array) {
        Mode mode;
        if (s == null) {
            mode = Mode.Add;
        }
        else {
            mode = Mode.valueOf(s);
            if (mode == null) {
                MapRewritePolicy.LOGGER.error("Undefined mode " + s);
                return null;
            }
        }
        if (array == null || array.length == 0) {
            MapRewritePolicy.LOGGER.error("keys and values must be specified for the MapRewritePolicy");
            return null;
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            final String key = keyValuePair.getKey();
            if (key == null) {
                MapRewritePolicy.LOGGER.error("A null key is not valid in MapRewritePolicy");
            }
            else if (keyValuePair.getValue() == null) {
                MapRewritePolicy.LOGGER.error("A null value for key " + key + " is not allowed in MapRewritePolicy");
            }
            else {
                hashMap.put(keyValuePair.getKey(), keyValuePair.getValue());
            }
            int n = 0;
            ++n;
        }
        if (hashMap.size() == 0) {
            MapRewritePolicy.LOGGER.error("MapRewritePolicy is not configured with any valid key value pairs");
            return null;
        }
        return new MapRewritePolicy(hashMap, mode);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
    
    public enum Mode
    {
        Add("Add", 0), 
        Update("Update", 1);
        
        private static final Mode[] $VALUES;
        
        private Mode(final String s, final int n) {
        }
        
        static {
            $VALUES = new Mode[] { Mode.Add, Mode.Update };
        }
    }
}
