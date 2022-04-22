package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class ForwardingMap extends ForwardingObject implements Map
{
    protected ForwardingMap() {
    }
    
    @Override
    protected abstract Map delegate();
    
    @Override
    public int size() {
        return this.delegate().size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    @Override
    public Object remove(final Object o) {
        return this.delegate().remove(o);
    }
    
    @Override
    public void clear() {
        this.delegate().clear();
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.delegate().containsKey(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.delegate().containsValue(o);
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        return this.delegate().get(o);
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.delegate().put(o, o2);
    }
    
    @Override
    public void putAll(final Map map) {
        this.delegate().putAll(map);
    }
    
    @Override
    public Set keySet() {
        return this.delegate().keySet();
    }
    
    @Override
    public Collection values() {
        return this.delegate().values();
    }
    
    @Override
    public Set entrySet() {
        return this.delegate().entrySet();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    protected void standardPutAll(final Map map) {
        Maps.putAllImpl(this, map);
    }
    
    @Beta
    protected Object standardRemove(@Nullable final Object o) {
        final Iterator iterator = this.entrySet().iterator();
        while (iterator.hasNext()) {
            final Entry<Object, V> entry = iterator.next();
            if (Objects.equal(entry.getKey(), o)) {
                final V value = entry.getValue();
                iterator.remove();
                return value;
            }
        }
        return null;
    }
    
    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }
    
    @Beta
    protected boolean standardContainsKey(@Nullable final Object o) {
        return Maps.containsKeyImpl(this, o);
    }
    
    protected boolean standardContainsValue(@Nullable final Object o) {
        return Maps.containsValueImpl(this, o);
    }
    
    protected boolean standardIsEmpty() {
        return !this.entrySet().iterator().hasNext();
    }
    
    protected boolean standardEquals(@Nullable final Object o) {
        return Maps.equalsImpl(this, o);
    }
    
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }
    
    protected String standardToString() {
        return Maps.toStringImpl(this);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    protected abstract class StandardEntrySet extends Maps.EntrySet
    {
        final ForwardingMap this$0;
        
        public StandardEntrySet(final ForwardingMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        Map map() {
            return this.this$0;
        }
    }
    
    @Beta
    protected class StandardValues extends Maps.Values
    {
        final ForwardingMap this$0;
        
        public StandardValues(final ForwardingMap this$0) {
            super(this.this$0 = this$0);
        }
    }
    
    @Beta
    protected class StandardKeySet extends Maps.KeySet
    {
        final ForwardingMap this$0;
        
        public StandardKeySet(final ForwardingMap this$0) {
            super(this.this$0 = this$0);
        }
    }
}
