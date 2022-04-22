package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.status.*;

public class Interpolator implements StrLookup
{
    private static final Logger LOGGER;
    private static final char PREFIX_SEPARATOR = ':';
    private final Map lookups;
    private final StrLookup defaultLookup;
    
    public Interpolator(final StrLookup strLookup) {
        this.lookups = new HashMap();
        this.defaultLookup = ((strLookup == null) ? new MapLookup(new HashMap()) : strLookup);
        final PluginManager pluginManager = new PluginManager("Lookup");
        pluginManager.collectPlugins();
        for (final Map.Entry<K, PluginType> entry : pluginManager.getPlugins().entrySet()) {
            this.lookups.put(entry.getKey(), entry.getValue().getPluginClass().newInstance());
        }
    }
    
    public Interpolator() {
        this.lookups = new HashMap();
        this.defaultLookup = new MapLookup(new HashMap());
        this.lookups.put("sys", new SystemPropertiesLookup());
        this.lookups.put("env", new EnvironmentLookup());
        this.lookups.put("jndi", new JndiLookup());
        if (Class.forName("javax.servlet.ServletContext") != null) {
            this.lookups.put("web", new WebLookup());
        }
    }
    
    @Override
    public String lookup(final String s) {
        return this.lookup(null, s);
    }
    
    @Override
    public String lookup(final LogEvent logEvent, String substring) {
        if (substring == null) {
            return null;
        }
        final int index = substring.indexOf(58);
        if (index >= 0) {
            final String substring2 = substring.substring(0, index);
            final String substring3 = substring.substring(index + 1);
            final StrLookup strLookup = this.lookups.get(substring2);
            String s = null;
            if (strLookup != null) {
                s = ((logEvent == null) ? strLookup.lookup(substring3) : strLookup.lookup(logEvent, substring3));
            }
            if (s != null) {
                return s;
            }
            substring = substring.substring(index + 1);
        }
        if (this.defaultLookup != null) {
            return (logEvent == null) ? this.defaultLookup.lookup(substring) : this.defaultLookup.lookup(logEvent, substring);
        }
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final String s : this.lookups.keySet()) {
            if (sb.length() == 0) {
                sb.append("{");
            }
            else {
                sb.append(", ");
            }
            sb.append(s);
        }
        if (sb.length() > 0) {
            sb.append("}");
        }
        return sb.toString();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
