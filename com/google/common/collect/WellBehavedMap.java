package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
final class WellBehavedMap extends ForwardingMap
{
    private final Map delegate;
    private Set entrySet;
    
    private WellBehavedMap(final Map delegate) {
        this.delegate = delegate;
    }
    
    static WellBehavedMap wrap(final Map map) {
        return new WellBehavedMap(map);
    }
    
    @Override
    protected Map delegate() {
        return this.delegate;
    }
    
    @Override
    public Set entrySet() {
        final Set entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        return this.entrySet = new EntrySet(null);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    private final class EntrySet extends Maps.EntrySet
    {
        final WellBehavedMap this$0;
        
        private EntrySet(final WellBehavedMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        Map map() {
            return this.this$0;
        }
        
        @Override
        public Iterator iterator() {
            return new TransformedIterator((Iterator)this.this$0.keySet().iterator()) {
                final EntrySet this$1;
                
                @Override
                Map.Entry transform(final Object o) {
                    return new AbstractMapEntry(o) {
                        final Object val$key;
                        final WellBehavedMap$EntrySet$1 this$2;
                        
                        @Override
                        public Object getKey() {
                            return this.val$key;
                        }
                        
                        @Override
                        public Object getValue() {
                            return this.this$2.this$1.this$0.get(this.val$key);
                        }
                        
                        @Override
                        public Object setValue(final Object o) {
                            return this.this$2.this$1.this$0.put(this.val$key, o);
                        }
                    };
                }
                
                @Override
                Object transform(final Object o) {
                    return this.transform(o);
                }
            };
        }
        
        EntrySet(final WellBehavedMap wellBehavedMap, final WellBehavedMap$1 object) {
            this(wellBehavedMap);
        }
    }
}
