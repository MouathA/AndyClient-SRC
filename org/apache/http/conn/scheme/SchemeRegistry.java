package org.apache.http.conn.scheme;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.util.*;

@Deprecated
@ThreadSafe
public final class SchemeRegistry
{
    private final ConcurrentHashMap registeredSchemes;
    
    public SchemeRegistry() {
        this.registeredSchemes = new ConcurrentHashMap();
    }
    
    public final Scheme getScheme(final String s) {
        final Scheme value = this.get(s);
        if (value == null) {
            throw new IllegalStateException("Scheme '" + s + "' not registered.");
        }
        return value;
    }
    
    public final Scheme getScheme(final HttpHost httpHost) {
        Args.notNull(httpHost, "Host");
        return this.getScheme(httpHost.getSchemeName());
    }
    
    public final Scheme get(final String s) {
        Args.notNull(s, "Scheme name");
        return this.registeredSchemes.get(s);
    }
    
    public final Scheme register(final Scheme scheme) {
        Args.notNull(scheme, "Scheme");
        return this.registeredSchemes.put(scheme.getName(), scheme);
    }
    
    public final Scheme unregister(final String s) {
        Args.notNull(s, "Scheme name");
        return this.registeredSchemes.remove(s);
    }
    
    public final List getSchemeNames() {
        return new ArrayList(this.registeredSchemes.keySet());
    }
    
    public void setItems(final Map map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }
}
