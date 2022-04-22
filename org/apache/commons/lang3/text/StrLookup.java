package org.apache.commons.lang3.text;

import java.util.*;

public abstract class StrLookup
{
    private static final StrLookup NONE_LOOKUP;
    private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;
    
    public static StrLookup noneLookup() {
        return StrLookup.NONE_LOOKUP;
    }
    
    public static StrLookup systemPropertiesLookup() {
        return StrLookup.SYSTEM_PROPERTIES_LOOKUP;
    }
    
    public static StrLookup mapLookup(final Map map) {
        return new MapStrLookup(map);
    }
    
    protected StrLookup() {
    }
    
    public abstract String lookup(final String p0);
    
    static {
        NONE_LOOKUP = new MapStrLookup(null);
        SYSTEM_PROPERTIES_LOOKUP = new MapStrLookup(System.getProperties());
    }
    
    static class MapStrLookup extends StrLookup
    {
        private final Map map;
        
        MapStrLookup(final Map map) {
            this.map = map;
        }
        
        @Override
        public String lookup(final String s) {
            if (this.map == null) {
                return null;
            }
            final Object value = this.map.get(s);
            if (value == null) {
                return null;
            }
            return value.toString();
        }
    }
}
