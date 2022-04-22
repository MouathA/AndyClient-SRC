package org.apache.http.config;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import java.util.*;

@ThreadSafe
public final class Registry implements Lookup
{
    private final Map map;
    
    Registry(final Map map) {
        this.map = new ConcurrentHashMap(map);
    }
    
    public Object lookup(final String s) {
        if (s == null) {
            return null;
        }
        return this.map.get(s.toLowerCase(Locale.US));
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
}
