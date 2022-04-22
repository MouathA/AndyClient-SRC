package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;

@Plugin(name = "map", category = "Lookup")
public class MapLookup implements StrLookup
{
    private final Map map;
    
    public MapLookup(final Map map) {
        this.map = map;
    }
    
    public MapLookup() {
        this.map = null;
    }
    
    @Override
    public String lookup(final String s) {
        if (this.map == null) {
            return null;
        }
        final String s2 = this.map.get(s);
        if (s2 == null) {
            return null;
        }
        return s2;
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        if (this.map == null && !(logEvent.getMessage() instanceof MapMessage)) {
            return null;
        }
        if (this.map != null && this.map.containsKey(s)) {
            final String s2 = this.map.get(s);
            if (s2 != null) {
                return s2;
            }
        }
        if (logEvent.getMessage() instanceof MapMessage) {
            return ((MapMessage)logEvent.getMessage()).get(s);
        }
        return null;
    }
}
