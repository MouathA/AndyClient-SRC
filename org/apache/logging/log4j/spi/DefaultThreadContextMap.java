package org.apache.logging.log4j.spi;

import java.util.*;

public class DefaultThreadContextMap implements ThreadContextMap
{
    private final boolean useMap;
    private final ThreadLocal localMap;
    
    public DefaultThreadContextMap(final boolean useMap) {
        this.localMap = new InheritableThreadLocal() {
            final DefaultThreadContextMap this$0;
            
            protected Map childValue(final Map map) {
                return (map == null || !DefaultThreadContextMap.access$000(this.this$0)) ? null : Collections.unmodifiableMap((Map<?, ?>)new HashMap<Object, Object>(map));
            }
            
            @Override
            protected Object childValue(final Object o) {
                return this.childValue((Map)o);
            }
        };
        this.useMap = useMap;
    }
    
    @Override
    public void put(final String s, final String s2) {
        if (!this.useMap) {
            return;
        }
        final Map<? extends K, ? extends V> map = this.localMap.get();
        final HashMap hashMap = (map == null) ? new HashMap<String, String>() : new HashMap<String, String>(map);
        hashMap.put(s, s2);
        this.localMap.set(Collections.unmodifiableMap((Map<?, ?>)hashMap));
    }
    
    @Override
    public String get(final String s) {
        final Map<K, String> map = this.localMap.get();
        return (map == null) ? null : map.get(s);
    }
    
    @Override
    public void remove(final String s) {
        final Map<? extends K, ? extends V> map = this.localMap.get();
        if (map != null) {
            final HashMap hashMap = new HashMap<Object, Object>(map);
            hashMap.remove(s);
            this.localMap.set(Collections.unmodifiableMap((Map<?, ?>)hashMap));
        }
    }
    
    @Override
    public void clear() {
        this.localMap.remove();
    }
    
    @Override
    public boolean containsKey(final String s) {
        final Map map = this.localMap.get();
        return map != null && map.containsKey(s);
    }
    
    @Override
    public Map getCopy() {
        final Map map = this.localMap.get();
        return (map == null) ? new HashMap() : new HashMap(map);
    }
    
    @Override
    public Map getImmutableMapOrNull() {
        return this.localMap.get();
    }
    
    @Override
    public boolean isEmpty() {
        final Map map = this.localMap.get();
        return map == null || map.size() == 0;
    }
    
    @Override
    public String toString() {
        final Map map = this.localMap.get();
        return (map == null) ? "{}" : map.toString();
    }
    
    static boolean access$000(final DefaultThreadContextMap defaultThreadContextMap) {
        return defaultThreadContextMap.useMap;
    }
}
