package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "Routes", category = "Core", printObject = true)
public final class Routes
{
    private static final Logger LOGGER;
    private final String pattern;
    private final Route[] routes;
    
    private Routes(final String pattern, final Route... routes) {
        this.pattern = pattern;
        this.routes = routes;
    }
    
    public String getPattern() {
        return this.pattern;
    }
    
    public Route[] getRoutes() {
        return this.routes;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        final Route[] routes = this.routes;
        while (0 < routes.length) {
            final Route route = routes[0];
            sb.append(",");
            sb.append(route.toString());
            int n = 0;
            ++n;
        }
        sb.append("}");
        return sb.toString();
    }
    
    @PluginFactory
    public static Routes createRoutes(@PluginAttribute("pattern") final String s, @PluginElement("Routes") final Route... array) {
        if (s == null) {
            Routes.LOGGER.error("A pattern is required");
            return null;
        }
        if (array == null || array.length == 0) {
            Routes.LOGGER.error("No routes configured");
            return null;
        }
        return new Routes(s, array);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
