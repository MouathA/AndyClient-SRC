package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "Route", category = "Core", printObject = true, deferChildren = true)
public final class Route
{
    private static final Logger LOGGER;
    private final Node node;
    private final String appenderRef;
    private final String key;
    
    private Route(final Node node, final String appenderRef, final String key) {
        this.node = node;
        this.appenderRef = appenderRef;
        this.key = key;
    }
    
    public Node getNode() {
        return this.node;
    }
    
    public String getAppenderRef() {
        return this.appenderRef;
    }
    
    public String getKey() {
        return this.key;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route(");
        sb.append("type=");
        if (this.appenderRef != null) {
            sb.append("static Reference=").append(this.appenderRef);
        }
        else if (this.node != null) {
            sb.append("dynamic - type=").append(this.node.getName());
        }
        else {
            sb.append("invalid Route");
        }
        if (this.key != null) {
            sb.append(" key='").append(this.key).append("'");
        }
        else {
            sb.append(" default");
        }
        sb.append(")");
        return sb.toString();
    }
    
    @PluginFactory
    public static Route createRoute(@PluginAttribute("ref") final String s, @PluginAttribute("key") final String s2, @PluginNode final Node node) {
        if (node != null && node.hasChildren()) {
            for (Node node2 : node.getChildren()) {}
            if (s != null) {
                Route.LOGGER.error("A route cannot be configured with an appender reference and an appender definition");
                return null;
            }
        }
        else if (s == null) {
            Route.LOGGER.error("A route must specify an appender reference or an appender definition");
            return null;
        }
        return new Route(node, s, s2);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
