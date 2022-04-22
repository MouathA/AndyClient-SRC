package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;

@ThreadSafe
public class UriPatternMatcher
{
    @GuardedBy("this")
    private final Map map;
    
    public UriPatternMatcher() {
        this.map = new HashMap();
    }
    
    public synchronized void register(final String s, final Object o) {
        Args.notNull(s, "URI request pattern");
        this.map.put(s, o);
    }
    
    public synchronized void unregister(final String s) {
        if (s == null) {
            return;
        }
        this.map.remove(s);
    }
    
    @Deprecated
    public synchronized void setHandlers(final Map map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }
    
    @Deprecated
    public synchronized void setObjects(final Map map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }
    
    @Deprecated
    public synchronized Map getObjects() {
        return this.map;
    }
    
    public synchronized Object lookup(final String s) {
        Args.notNull(s, "Request path");
        Object o = this.map.get(s);
        if (o == null) {
            String s2 = null;
            for (final String s3 : this.map.keySet()) {
                if (this.matchUriRequestPattern(s3, s) && (s2 == null || s2.length() < s3.length() || (s2.length() == s3.length() && s3.endsWith("*")))) {
                    o = this.map.get(s3);
                    s2 = s3;
                }
            }
        }
        return o;
    }
    
    protected boolean matchUriRequestPattern(final String s, final String s2) {
        return s.equals("*") || (s.endsWith("*") && s2.startsWith(s.substring(0, s.length() - 1))) || (s.startsWith("*") && s2.endsWith(s.substring(1, s.length())));
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
}
