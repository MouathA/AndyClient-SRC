package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
final class FilteredMultimapValues extends AbstractCollection
{
    private final FilteredMultimap multimap;
    
    FilteredMultimapValues(final FilteredMultimap filteredMultimap) {
        this.multimap = (FilteredMultimap)Preconditions.checkNotNull(filteredMultimap);
    }
    
    @Override
    public Iterator iterator() {
        return Maps.valueIterator(this.multimap.entries().iterator());
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.multimap.containsValue(o);
    }
    
    @Override
    public int size() {
        return this.multimap.size();
    }
    
    @Override
    public boolean remove(@Nullable final Object o) {
        final Predicate entryPredicate = this.multimap.entryPredicate();
        final Iterator<Map.Entry<K, Object>> iterator = (Iterator<Map.Entry<K, Object>>)this.multimap.unfiltered().entries().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<K, Object> entry = iterator.next();
            if (entryPredicate.apply(entry) && Objects.equal(entry.getValue(), o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.in(collection))));
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection)))));
    }
    
    @Override
    public void clear() {
        this.multimap.clear();
    }
}
