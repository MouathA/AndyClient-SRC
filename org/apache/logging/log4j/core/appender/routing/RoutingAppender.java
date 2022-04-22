package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.appender.rewrite.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "Routing", category = "Core", elementType = "appender", printObject = true)
public final class RoutingAppender extends AbstractAppender
{
    private static final String DEFAULT_KEY = "ROUTING_APPENDER_DEFAULT";
    private final Routes routes;
    private final Route defaultRoute;
    private final Configuration config;
    private final ConcurrentMap appenders;
    private final RewritePolicy rewritePolicy;
    
    private RoutingAppender(final String s, final Filter filter, final boolean b, final Routes routes, final RewritePolicy rewritePolicy, final Configuration config) {
        super(s, filter, null, b);
        this.appenders = new ConcurrentHashMap();
        this.routes = routes;
        this.config = config;
        this.rewritePolicy = rewritePolicy;
        Route defaultRoute = null;
        final Route[] routes2 = routes.getRoutes();
        while (0 < routes2.length) {
            final Route route = routes2[0];
            if (route.getKey() == null) {
                if (defaultRoute == null) {
                    defaultRoute = route;
                }
                else {
                    this.error("Multiple default routes. Route " + route.toString() + " will be ignored");
                }
            }
            int n = 0;
            ++n;
        }
        this.defaultRoute = defaultRoute;
    }
    
    @Override
    public void start() {
        final Map appenders = this.config.getAppenders();
        final Route[] routes = this.routes.getRoutes();
        while (0 < routes.length) {
            final Route route = routes[0];
            if (route.getAppenderRef() != null) {
                final Appender appender = appenders.get(route.getAppenderRef());
                if (appender != null) {
                    this.appenders.put((route == this.defaultRoute) ? "ROUTING_APPENDER_DEFAULT" : route.getKey(), new AppenderControl(appender, null, null));
                }
                else {
                    RoutingAppender.LOGGER.error("Appender " + route.getAppenderRef() + " cannot be located. Route ignored");
                }
            }
            int n = 0;
            ++n;
        }
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
        final Map appenders = this.config.getAppenders();
        for (final Map.Entry<K, AppenderControl> entry : this.appenders.entrySet()) {
            if (!appenders.containsKey(entry.getValue().getAppender().getName())) {
                entry.getValue().getAppender().stop();
            }
        }
    }
    
    @Override
    public void append(LogEvent rewrite) {
        if (this.rewritePolicy != null) {
            rewrite = this.rewritePolicy.rewrite(rewrite);
        }
        final AppenderControl control = this.getControl(this.config.getStrSubstitutor().replace(rewrite, this.routes.getPattern()), rewrite);
        if (control != null) {
            control.callAppender(rewrite);
        }
    }
    
    private synchronized AppenderControl getControl(final String s, final LogEvent logEvent) {
        AppenderControl appenderControl = (AppenderControl)this.appenders.get(s);
        if (appenderControl != null) {
            return appenderControl;
        }
        Route defaultRoute = null;
        final Route[] routes = this.routes.getRoutes();
        while (0 < routes.length) {
            final Route route = routes[0];
            if (route.getAppenderRef() == null && s.equals(route.getKey())) {
                defaultRoute = route;
                break;
            }
            int n = 0;
            ++n;
        }
        if (defaultRoute == null) {
            defaultRoute = this.defaultRoute;
            appenderControl = (AppenderControl)this.appenders.get("ROUTING_APPENDER_DEFAULT");
            if (appenderControl != null) {
                return appenderControl;
            }
        }
        if (defaultRoute != null) {
            final Appender appender = this.createAppender(defaultRoute, logEvent);
            if (appender == null) {
                return null;
            }
            appenderControl = new AppenderControl(appender, null, null);
            this.appenders.put(s, appenderControl);
        }
        return appenderControl;
    }
    
    private Appender createAppender(final Route route, final LogEvent logEvent) {
        for (final Node node : route.getNode().getChildren()) {
            if (node.getType().getElementName().equals("appender")) {
                final Node node2 = new Node(node);
                this.config.createConfiguration(node2, logEvent);
                if (node2.getObject() instanceof Appender) {
                    final Appender appender = (Appender)node2.getObject();
                    appender.start();
                    return appender;
                }
                RoutingAppender.LOGGER.error("Unable to create Appender of type " + node.getName());
                return null;
            }
        }
        RoutingAppender.LOGGER.error("No Appender was configured for route " + route.getKey());
        return null;
    }
    
    @PluginFactory
    public static RoutingAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("Routes") final Routes routes, @PluginConfiguration final Configuration configuration, @PluginElement("RewritePolicy") final RewritePolicy rewritePolicy, @PluginElement("Filters") final Filter filter) {
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        if (s == null) {
            RoutingAppender.LOGGER.error("No name provided for RoutingAppender");
            return null;
        }
        if (routes == null) {
            RoutingAppender.LOGGER.error("No routes defined for RoutingAppender");
            return null;
        }
        return new RoutingAppender(s, filter, boolean1, routes, rewritePolicy, configuration);
    }
}
