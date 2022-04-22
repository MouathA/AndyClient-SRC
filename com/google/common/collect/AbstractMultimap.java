package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
abstract class AbstractMultimap implements Multimap
{
    private transient Collection entries;
    private transient Set keySet;
    private transient Multiset keys;
    private transient Collection values;
    private transient Map asMap;
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        final Iterator<Collection> iterator = this.asMap().values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsEntry(@Nullable final Object o, @Nullable final Object o2) {
        final Collection collection = this.asMap().get(o);
        return collection != null && collection.contains(o2);
    }
    
    @Override
    public boolean remove(@Nullable final Object o, @Nullable final Object o2) {
        final Collection collection = this.asMap().get(o);
        return collection != null && collection.remove(o2);
    }
    
    @Override
    public boolean put(@Nullable final Object o, @Nullable final Object o2) {
        return this.get(o).add(o2);
    }
    
    @Override
    public boolean putAll(@Nullable final Object o, final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            final Collection collection = (Collection)iterable;
            return !collection.isEmpty() && this.get(o).addAll(collection);
        }
        final Iterator iterator = iterable.iterator();
        return iterator.hasNext() && Iterators.addAll(this.get(o), iterator);
    }
    
    @Override
    public boolean putAll(final Multimap multimap) {
        for (final Map.Entry<Object, V> entry : multimap.entries()) {
            final boolean b = false | this.put(entry.getKey(), entry.getValue());
        }
        return false;
    }
    
    @Override
    public Collection replaceValues(@Nullable final Object o, final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        final Collection removeAll = this.removeAll(o);
        this.putAll(o, iterable);
        return removeAll;
    }
    
    @Override
    public Collection entries() {
        final Collection entries = this.entries;
        return (entries == null) ? (this.entries = this.createEntries()) : entries;
    }
    
    Collection createEntries() {
        if (this instanceof SetMultimap) {
            return new EntrySet(null);
        }
        return new Entries(null);
    }
    
    abstract Iterator entryIterator();
    
    @Override
    public Set keySet() {
        final Set keySet = this.keySet;
        return (keySet == null) ? (this.keySet = this.createKeySet()) : keySet;
    }
    
    Set createKeySet() {
        return new Maps.KeySet(this.asMap());
    }
    
    @Override
    public Multiset keys() {
        final Multiset keys = this.keys;
        return (keys == null) ? (this.keys = this.createKeys()) : keys;
    }
    
    Multiset createKeys() {
        return new Multimaps.Keys(this);
    }
    
    @Override
    public Collection values() {
        final Collection values = this.values;
        return (values == null) ? (this.values = this.createValues()) : values;
    }
    
    Collection createValues() {
        return new Values();
    }
    
    Iterator valueIterator() {
        return Maps.valueIterator(this.entries().iterator());
    }
    
    @Override
    public Map asMap() {
        final Map asMap = this.asMap;
        return (asMap == null) ? (this.asMap = this.createAsMap()) : asMap;
    }
    
    abstract Map createAsMap();
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return Multimaps.equalsImpl(this, o);
    }
    
    @Override
    public int hashCode() {
        return this.asMap().hashCode();
    }
    
    @Override
    public String toString() {
        return this.asMap().toString();
    }
    
    class Values extends AbstractCollection
    {
        final AbstractMultimap this$0;
        
        Values(final AbstractMultimap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.valueIterator();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.this$0.containsValue(o);
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
    
    private class EntrySet extends Entries implements Set
    {
        final AbstractMultimap this$0;
        
        private EntrySet(final AbstractMultimap this$0) {
            this.this$0 = this$0.super(null);
        }
        
        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return Sets.equalsImpl(this, o);
        }
        
        EntrySet(final AbstractMultimap abstractMultimap, final AbstractMultimap$1 object) {
            this(abstractMultimap);
        }
    }
    
    private class Entries extends Multimaps.Entries
    {
        final AbstractMultimap this$0;
        
        private Entries(final AbstractMultimap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        Multimap multimap() {
            return this.this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.entryIterator();
        }
        
        Entries(final AbstractMultimap abstractMultimap, final AbstractMultimap$1 object) {
            this(abstractMultimap);
        }
    }
}
