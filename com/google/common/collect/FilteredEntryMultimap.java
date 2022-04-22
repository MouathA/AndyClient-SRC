package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
class FilteredEntryMultimap extends AbstractMultimap implements FilteredMultimap
{
    final Multimap unfiltered;
    final Predicate predicate;
    
    FilteredEntryMultimap(final Multimap multimap, final Predicate predicate) {
        this.unfiltered = (Multimap)Preconditions.checkNotNull(multimap);
        this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
    }
    
    @Override
    public Multimap unfiltered() {
        return this.unfiltered;
    }
    
    @Override
    public Predicate entryPredicate() {
        return this.predicate;
    }
    
    @Override
    public int size() {
        return this.entries().size();
    }
    
    private boolean satisfies(final Object o, final Object o2) {
        return this.predicate.apply(Maps.immutableEntry(o, o2));
    }
    
    static Collection filterCollection(final Collection collection, final Predicate predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set)collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.asMap().get(o) != null;
    }
    
    @Override
    public Collection removeAll(@Nullable final Object o) {
        return (Collection)Objects.firstNonNull(this.asMap().remove(o), this.unmodifiableEmptyCollection());
    }
    
    Collection unmodifiableEmptyCollection() {
        return (this.unfiltered instanceof SetMultimap) ? Collections.emptySet() : Collections.emptyList();
    }
    
    @Override
    public void clear() {
        this.entries().clear();
    }
    
    @Override
    public Collection get(final Object o) {
        return filterCollection(this.unfiltered.get(o), new ValuePredicate(o));
    }
    
    @Override
    Collection createEntries() {
        return filterCollection(this.unfiltered.entries(), this.predicate);
    }
    
    @Override
    Collection createValues() {
        return new FilteredMultimapValues(this);
    }
    
    @Override
    Iterator entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    Map createAsMap() {
        return new AsMap();
    }
    
    @Override
    public Set keySet() {
        return this.asMap().keySet();
    }
    
    boolean removeEntriesIf(final Predicate predicate) {
        final Iterator<Map.Entry<Object, V>> iterator = this.unfiltered.asMap().entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Object, V> entry = iterator.next();
            final Object key = entry.getKey();
            final Collection filterCollection = filterCollection((Collection)entry.getValue(), new ValuePredicate(key));
            if (!filterCollection.isEmpty() && predicate.apply(Maps.immutableEntry(key, filterCollection))) {
                if (filterCollection.size() == ((Collection)entry.getValue()).size()) {
                    iterator.remove();
                }
                else {
                    filterCollection.clear();
                }
            }
        }
        return true;
    }
    
    @Override
    Multiset createKeys() {
        return new Keys();
    }
    
    static boolean access$000(final FilteredEntryMultimap filteredEntryMultimap, final Object o, final Object o2) {
        return filteredEntryMultimap.satisfies(o, o2);
    }
    
    class Keys extends Multimaps.Keys
    {
        final FilteredEntryMultimap this$0;
        
        Keys(final FilteredEntryMultimap this$0) {
            super(this.this$0 = this$0);
        }
        
        @Override
        public int remove(@Nullable final Object o, final int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(o);
            }
            final Collection<Object> collection = this.this$0.unfiltered.asMap().get(o);
            if (collection == null) {
                return 0;
            }
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (FilteredEntryMultimap.access$000(this.this$0, o, iterator.next())) {
                    int n2 = 0;
                    ++n2;
                    if (0 > n) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return 0;
        }
        
        @Override
        public Set entrySet() {
            return new Multisets.EntrySet() {
                final Keys this$1;
                
                @Override
                Multiset multiset() {
                    return this.this$1;
                }
                
                @Override
                public Iterator iterator() {
                    return this.this$1.entryIterator();
                }
                
                @Override
                public int size() {
                    return this.this$1.this$0.keySet().size();
                }
                
                private boolean removeEntriesIf(final Predicate predicate) {
                    return this.this$1.this$0.removeEntriesIf(new Predicate(predicate) {
                        final Predicate val$predicate;
                        final FilteredEntryMultimap$Keys$1 this$2;
                        
                        public boolean apply(final Map.Entry entry) {
                            return this.val$predicate.apply(Multisets.immutableEntry(entry.getKey(), ((Collection)entry.getValue()).size()));
                        }
                        
                        @Override
                        public boolean apply(final Object o) {
                            return this.apply((Map.Entry)o);
                        }
                    });
                }
                
                @Override
                public boolean removeAll(final Collection collection) {
                    return this.removeEntriesIf(Predicates.in(collection));
                }
                
                @Override
                public boolean retainAll(final Collection collection) {
                    return this.removeEntriesIf(Predicates.not(Predicates.in(collection)));
                }
            };
        }
    }
    
    class AsMap extends Maps.ImprovedAbstractMap
    {
        final FilteredEntryMultimap this$0;
        
        AsMap(final FilteredEntryMultimap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.get(o) != null;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public Collection get(@Nullable final Object o) {
            final Collection collection = this.this$0.unfiltered.asMap().get(o);
            if (collection == null) {
                return null;
            }
            final Collection filterCollection = FilteredEntryMultimap.filterCollection(collection, this.this$0.new ValuePredicate(o));
            return filterCollection.isEmpty() ? null : filterCollection;
        }
        
        @Override
        public Collection remove(@Nullable final Object o) {
            final Collection<Object> collection = this.this$0.unfiltered.asMap().get(o);
            if (collection == null) {
                return null;
            }
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                final Object next = iterator.next();
                if (FilteredEntryMultimap.access$000(this.this$0, o, next)) {
                    iterator.remove();
                    arrayList.add(next);
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            if (this.this$0.unfiltered instanceof SetMultimap) {
                return Collections.unmodifiableSet((Set<?>)Sets.newLinkedHashSet(arrayList));
            }
            return Collections.unmodifiableList((List<?>)arrayList);
        }
        
        @Override
        Set createKeySet() {
            return new Maps.KeySet((Map)this) {
                final AsMap this$1;
                
                @Override
                public boolean removeAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in(collection)));
                }
                
                @Override
                public boolean retainAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
                }
                
                @Override
                public boolean remove(@Nullable final Object o) {
                    return this.this$1.remove(o) != null;
                }
            };
        }
        
        @Override
        Set createEntrySet() {
            return new Maps.EntrySet() {
                final AsMap this$1;
                
                @Override
                Map map() {
                    return this.this$1;
                }
                
                @Override
                public Iterator iterator() {
                    return new AbstractIterator() {
                        final Iterator backingIterator = this.this$2.this$1.this$0.unfiltered.asMap().entrySet().iterator();
                        final FilteredEntryMultimap$AsMap$2 this$2;
                        
                        @Override
                        protected Map.Entry computeNext() {
                            while (this.backingIterator.hasNext()) {
                                final Map.Entry<Object, V> entry = this.backingIterator.next();
                                final Object key = entry.getKey();
                                final Collection filterCollection = FilteredEntryMultimap.filterCollection((Collection)entry.getValue(), this.this$2.this$1.this$0.new ValuePredicate(key));
                                if (!filterCollection.isEmpty()) {
                                    return Maps.immutableEntry(key, filterCollection);
                                }
                            }
                            return (Map.Entry)this.endOfData();
                        }
                        
                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }
                
                @Override
                public boolean removeAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Predicates.in(collection));
                }
                
                @Override
                public boolean retainAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Predicates.not(Predicates.in(collection)));
                }
                
                @Override
                public int size() {
                    return Iterators.size(this.iterator());
                }
            };
        }
        
        @Override
        Collection createValues() {
            return new Maps.Values((Map)this) {
                final AsMap this$1;
                
                @Override
                public boolean remove(@Nullable final Object o) {
                    if (o instanceof Collection) {
                        final Collection collection = (Collection)o;
                        final Iterator<Map.Entry<Object, V>> iterator = this.this$1.this$0.unfiltered.asMap().entrySet().iterator();
                        while (iterator.hasNext()) {
                            final Map.Entry<Object, V> entry = iterator.next();
                            final Collection filterCollection = FilteredEntryMultimap.filterCollection((Collection)entry.getValue(), this.this$1.this$0.new ValuePredicate(entry.getKey()));
                            if (!filterCollection.isEmpty() && collection.equals(filterCollection)) {
                                if (filterCollection.size() == ((Collection)entry.getValue()).size()) {
                                    iterator.remove();
                                }
                                else {
                                    filterCollection.clear();
                                }
                                return true;
                            }
                        }
                    }
                    return false;
                }
                
                @Override
                public boolean removeAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
                }
                
                @Override
                public boolean retainAll(final Collection collection) {
                    return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
                }
            };
        }
        
        @Override
        public Object remove(final Object o) {
            return this.remove(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
    }
    
    final class ValuePredicate implements Predicate
    {
        private final Object key;
        final FilteredEntryMultimap this$0;
        
        ValuePredicate(final FilteredEntryMultimap this$0, final Object key) {
            this.this$0 = this$0;
            this.key = key;
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return FilteredEntryMultimap.access$000(this.this$0, this.key, o);
        }
    }
}
