package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
class FilteredKeyMultimap extends AbstractMultimap implements FilteredMultimap
{
    final Multimap unfiltered;
    final Predicate keyPredicate;
    
    FilteredKeyMultimap(final Multimap multimap, final Predicate predicate) {
        this.unfiltered = (Multimap)Preconditions.checkNotNull(multimap);
        this.keyPredicate = (Predicate)Preconditions.checkNotNull(predicate);
    }
    
    @Override
    public Multimap unfiltered() {
        return this.unfiltered;
    }
    
    @Override
    public Predicate entryPredicate() {
        return Maps.keyPredicateOnEntries(this.keyPredicate);
    }
    
    @Override
    public int size() {
        final Iterator<Collection> iterator = this.asMap().values().iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().size();
        }
        return 0;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.unfiltered.containsKey(o) && this.keyPredicate.apply(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.containsKey(o) ? this.unfiltered.removeAll(o) : this.unmodifiableEmptyCollection();
    }
    
    Collection unmodifiableEmptyCollection() {
        if (this.unfiltered instanceof SetMultimap) {
            return ImmutableSet.of();
        }
        return ImmutableList.of();
    }
    
    @Override
    public void clear() {
        this.keySet().clear();
    }
    
    @Override
    Set createKeySet() {
        return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
    }
    
    @Override
    public Collection get(final Object o) {
        if (this.keyPredicate.apply(o)) {
            return this.unfiltered.get(o);
        }
        if (this.unfiltered instanceof SetMultimap) {
            return new AddRejectingSet(o);
        }
        return new AddRejectingList(o);
    }
    
    @Override
    Iterator entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    Collection createEntries() {
        return new Entries();
    }
    
    @Override
    Collection createValues() {
        return new FilteredMultimapValues(this);
    }
    
    @Override
    Map createAsMap() {
        return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
    }
    
    @Override
    Multiset createKeys() {
        return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
    }
    
    class Entries extends ForwardingCollection
    {
        final FilteredKeyMultimap this$0;
        
        Entries(final FilteredKeyMultimap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected Collection delegate() {
            return Collections2.filter(this.this$0.unfiltered.entries(), this.this$0.entryPredicate());
        }
        
        @Override
        public boolean remove(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                if (this.this$0.unfiltered.containsKey(entry.getKey()) && this.this$0.keyPredicate.apply(entry.getKey())) {
                    return this.this$0.unfiltered.remove(entry.getKey(), entry.getValue());
                }
            }
            return false;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    static class AddRejectingList extends ForwardingList
    {
        final Object key;
        
        AddRejectingList(final Object key) {
            this.key = key;
        }
        
        @Override
        public boolean add(final Object o) {
            this.add(0, o);
            return true;
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            this.addAll(0, collection);
            return true;
        }
        
        @Override
        public void add(final int n, final Object o) {
            Preconditions.checkPositionIndex(n, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(n, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }
        
        @Override
        protected List delegate() {
            return Collections.emptyList();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    static class AddRejectingSet extends ForwardingSet
    {
        final Object key;
        
        AddRejectingSet(final Object key) {
            this.key = key;
        }
        
        @Override
        public boolean add(final Object o) {
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            Preconditions.checkNotNull(collection);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.key);
        }
        
        @Override
        protected Set delegate() {
            return Collections.emptySet();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
