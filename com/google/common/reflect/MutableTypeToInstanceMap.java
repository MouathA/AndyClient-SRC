package com.google.common.reflect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

@Beta
public final class MutableTypeToInstanceMap extends ForwardingMap implements TypeToInstanceMap
{
    private final Map backingMap;
    
    public MutableTypeToInstanceMap() {
        this.backingMap = Maps.newHashMap();
    }
    
    @Nullable
    @Override
    public Object getInstance(final Class clazz) {
        return this.trustedGet(TypeToken.of(clazz));
    }
    
    @Nullable
    @Override
    public Object putInstance(final Class clazz, @Nullable final Object o) {
        return this.trustedPut(TypeToken.of(clazz), o);
    }
    
    @Nullable
    @Override
    public Object getInstance(final TypeToken typeToken) {
        return this.trustedGet(typeToken.rejectTypeVariables());
    }
    
    @Nullable
    @Override
    public Object putInstance(final TypeToken typeToken, @Nullable final Object o) {
        return this.trustedPut(typeToken.rejectTypeVariables(), o);
    }
    
    public Object put(final TypeToken typeToken, final Object o) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }
    
    @Override
    public void putAll(final Map map) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }
    
    @Override
    public Set entrySet() {
        return UnmodifiableEntry.transformEntries(super.entrySet());
    }
    
    @Override
    protected Map delegate() {
        return this.backingMap;
    }
    
    @Nullable
    private Object trustedPut(final TypeToken typeToken, @Nullable final Object o) {
        return this.backingMap.put(typeToken, o);
    }
    
    @Nullable
    private Object trustedGet(final TypeToken typeToken) {
        return this.backingMap.get(typeToken);
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.put((TypeToken)o, o2);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    private static final class UnmodifiableEntry extends ForwardingMapEntry
    {
        private final Map.Entry delegate;
        
        static Set transformEntries(final Set set) {
            return new ForwardingSet(set) {
                final Set val$entries;
                
                @Override
                protected Set delegate() {
                    return this.val$entries;
                }
                
                @Override
                public Iterator iterator() {
                    return UnmodifiableEntry.access$000(super.iterator());
                }
                
                @Override
                public Object[] toArray() {
                    return this.standardToArray();
                }
                
                @Override
                public Object[] toArray(final Object[] array) {
                    return this.standardToArray(array);
                }
                
                @Override
                protected Collection delegate() {
                    return this.delegate();
                }
                
                @Override
                protected Object delegate() {
                    return this.delegate();
                }
            };
        }
        
        private static Iterator transformEntries(final Iterator iterator) {
            return Iterators.transform(iterator, new Function() {
                public Map.Entry apply(final Map.Entry entry) {
                    return new UnmodifiableEntry(entry, null);
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Map.Entry)o);
                }
            });
        }
        
        private UnmodifiableEntry(final Map.Entry entry) {
            this.delegate = (Map.Entry)Preconditions.checkNotNull(entry);
        }
        
        @Override
        protected Map.Entry delegate() {
            return this.delegate;
        }
        
        @Override
        public Object setValue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        static Iterator access$000(final Iterator iterator) {
            return transformEntries(iterator);
        }
        
        UnmodifiableEntry(final Map.Entry entry, final MutableTypeToInstanceMap$1 object) {
            this(entry);
        }
    }
}
