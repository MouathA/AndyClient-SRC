package com.google.common.collect;

import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Multimaps
{
    private Multimaps() {
    }
    
    public static Multimap newMultimap(final Map map, final Supplier supplier) {
        return new CustomMultimap(map, supplier);
    }
    
    public static ListMultimap newListMultimap(final Map map, final Supplier supplier) {
        return new CustomListMultimap(map, supplier);
    }
    
    public static SetMultimap newSetMultimap(final Map map, final Supplier supplier) {
        return new CustomSetMultimap(map, supplier);
    }
    
    public static SortedSetMultimap newSortedSetMultimap(final Map map, final Supplier supplier) {
        return new CustomSortedSetMultimap(map, supplier);
    }
    
    public static Multimap invertFrom(final Multimap multimap, final Multimap multimap2) {
        Preconditions.checkNotNull(multimap2);
        for (final Map.Entry<K, Object> entry : multimap.entries()) {
            multimap2.put(entry.getValue(), entry.getKey());
        }
        return multimap2;
    }
    
    public static Multimap synchronizedMultimap(final Multimap multimap) {
        return Synchronized.multimap(multimap, null);
    }
    
    public static Multimap unmodifiableMultimap(final Multimap multimap) {
        if (multimap instanceof UnmodifiableMultimap || multimap instanceof ImmutableMultimap) {
            return multimap;
        }
        return new UnmodifiableMultimap(multimap);
    }
    
    @Deprecated
    public static Multimap unmodifiableMultimap(final ImmutableMultimap immutableMultimap) {
        return (Multimap)Preconditions.checkNotNull(immutableMultimap);
    }
    
    public static SetMultimap synchronizedSetMultimap(final SetMultimap setMultimap) {
        return Synchronized.setMultimap(setMultimap, null);
    }
    
    public static SetMultimap unmodifiableSetMultimap(final SetMultimap setMultimap) {
        if (setMultimap instanceof UnmodifiableSetMultimap || setMultimap instanceof ImmutableSetMultimap) {
            return setMultimap;
        }
        return new UnmodifiableSetMultimap(setMultimap);
    }
    
    @Deprecated
    public static SetMultimap unmodifiableSetMultimap(final ImmutableSetMultimap immutableSetMultimap) {
        return (SetMultimap)Preconditions.checkNotNull(immutableSetMultimap);
    }
    
    public static SortedSetMultimap synchronizedSortedSetMultimap(final SortedSetMultimap sortedSetMultimap) {
        return Synchronized.sortedSetMultimap(sortedSetMultimap, null);
    }
    
    public static SortedSetMultimap unmodifiableSortedSetMultimap(final SortedSetMultimap sortedSetMultimap) {
        if (sortedSetMultimap instanceof UnmodifiableSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new UnmodifiableSortedSetMultimap(sortedSetMultimap);
    }
    
    public static ListMultimap synchronizedListMultimap(final ListMultimap listMultimap) {
        return Synchronized.listMultimap(listMultimap, null);
    }
    
    public static ListMultimap unmodifiableListMultimap(final ListMultimap listMultimap) {
        if (listMultimap instanceof UnmodifiableListMultimap || listMultimap instanceof ImmutableListMultimap) {
            return listMultimap;
        }
        return new UnmodifiableListMultimap(listMultimap);
    }
    
    @Deprecated
    public static ListMultimap unmodifiableListMultimap(final ImmutableListMultimap immutableListMultimap) {
        return (ListMultimap)Preconditions.checkNotNull(immutableListMultimap);
    }
    
    private static Collection unmodifiableValueCollection(final Collection collection) {
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet<Object>)collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set<?>)(SortedSet<? extends T>)collection);
        }
        if (collection instanceof List) {
            return Collections.unmodifiableList((List<?>)(List<? extends T>)collection);
        }
        return Collections.unmodifiableCollection((Collection<?>)collection);
    }
    
    private static Collection unmodifiableEntries(final Collection collection) {
        if (collection instanceof Set) {
            return Maps.unmodifiableEntrySet((Set)collection);
        }
        return new Maps.UnmodifiableEntries(Collections.unmodifiableCollection((Collection<?>)collection));
    }
    
    @Beta
    public static Map asMap(final ListMultimap listMultimap) {
        return listMultimap.asMap();
    }
    
    @Beta
    public static Map asMap(final SetMultimap setMultimap) {
        return setMultimap.asMap();
    }
    
    @Beta
    public static Map asMap(final SortedSetMultimap sortedSetMultimap) {
        return sortedSetMultimap.asMap();
    }
    
    @Beta
    public static Map asMap(final Multimap multimap) {
        return multimap.asMap();
    }
    
    public static SetMultimap forMap(final Map map) {
        return new MapMultimap(map);
    }
    
    public static Multimap transformValues(final Multimap multimap, final Function function) {
        Preconditions.checkNotNull(function);
        return transformEntries(multimap, Maps.asEntryTransformer(function));
    }
    
    public static Multimap transformEntries(final Multimap multimap, final Maps.EntryTransformer entryTransformer) {
        return new TransformedEntriesMultimap(multimap, entryTransformer);
    }
    
    public static ListMultimap transformValues(final ListMultimap listMultimap, final Function function) {
        Preconditions.checkNotNull(function);
        return transformEntries(listMultimap, Maps.asEntryTransformer(function));
    }
    
    public static ListMultimap transformEntries(final ListMultimap listMultimap, final Maps.EntryTransformer entryTransformer) {
        return new TransformedEntriesListMultimap(listMultimap, entryTransformer);
    }
    
    public static ImmutableListMultimap index(final Iterable iterable, final Function function) {
        return index(iterable.iterator(), function);
    }
    
    public static ImmutableListMultimap index(final Iterator iterator, final Function function) {
        Preconditions.checkNotNull(function);
        final ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            Preconditions.checkNotNull(next, iterator);
            builder.put(function.apply(next), next);
        }
        return builder.build();
    }
    
    public static Multimap filterKeys(final Multimap multimap, final Predicate predicate) {
        if (multimap instanceof SetMultimap) {
            return filterKeys((SetMultimap)multimap, predicate);
        }
        if (multimap instanceof ListMultimap) {
            return filterKeys((ListMultimap)multimap, predicate);
        }
        if (multimap instanceof FilteredKeyMultimap) {
            final FilteredKeyMultimap filteredKeyMultimap = (FilteredKeyMultimap)multimap;
            return new FilteredKeyMultimap(filteredKeyMultimap.unfiltered, Predicates.and(filteredKeyMultimap.keyPredicate, predicate));
        }
        if (multimap instanceof FilteredMultimap) {
            return filterFiltered((FilteredMultimap)multimap, Maps.keyPredicateOnEntries(predicate));
        }
        return new FilteredKeyMultimap(multimap, predicate);
    }
    
    public static SetMultimap filterKeys(final SetMultimap setMultimap, final Predicate predicate) {
        if (setMultimap instanceof FilteredKeySetMultimap) {
            final FilteredKeySetMultimap filteredKeySetMultimap = (FilteredKeySetMultimap)setMultimap;
            return new FilteredKeySetMultimap(filteredKeySetMultimap.unfiltered(), Predicates.and(filteredKeySetMultimap.keyPredicate, predicate));
        }
        if (setMultimap instanceof FilteredSetMultimap) {
            return filterFiltered((FilteredSetMultimap)setMultimap, Maps.keyPredicateOnEntries(predicate));
        }
        return new FilteredKeySetMultimap(setMultimap, predicate);
    }
    
    public static ListMultimap filterKeys(final ListMultimap listMultimap, final Predicate predicate) {
        if (listMultimap instanceof FilteredKeyListMultimap) {
            final FilteredKeyListMultimap filteredKeyListMultimap = (FilteredKeyListMultimap)listMultimap;
            return new FilteredKeyListMultimap(filteredKeyListMultimap.unfiltered(), Predicates.and(filteredKeyListMultimap.keyPredicate, predicate));
        }
        return new FilteredKeyListMultimap(listMultimap, predicate);
    }
    
    public static Multimap filterValues(final Multimap multimap, final Predicate predicate) {
        return filterEntries(multimap, Maps.valuePredicateOnEntries(predicate));
    }
    
    public static SetMultimap filterValues(final SetMultimap setMultimap, final Predicate predicate) {
        return filterEntries(setMultimap, Maps.valuePredicateOnEntries(predicate));
    }
    
    public static Multimap filterEntries(final Multimap multimap, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        if (multimap instanceof SetMultimap) {
            return filterEntries((SetMultimap)multimap, predicate);
        }
        return (multimap instanceof FilteredMultimap) ? filterFiltered((FilteredMultimap)multimap, predicate) : new FilteredEntryMultimap((Multimap)Preconditions.checkNotNull(multimap), predicate);
    }
    
    public static SetMultimap filterEntries(final SetMultimap setMultimap, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        return (setMultimap instanceof FilteredSetMultimap) ? filterFiltered((FilteredSetMultimap)setMultimap, predicate) : new FilteredEntrySetMultimap((SetMultimap)Preconditions.checkNotNull(setMultimap), predicate);
    }
    
    private static Multimap filterFiltered(final FilteredMultimap filteredMultimap, final Predicate predicate) {
        return new FilteredEntryMultimap(filteredMultimap.unfiltered(), Predicates.and(filteredMultimap.entryPredicate(), predicate));
    }
    
    private static SetMultimap filterFiltered(final FilteredSetMultimap filteredSetMultimap, final Predicate predicate) {
        return new FilteredEntrySetMultimap(filteredSetMultimap.unfiltered(), Predicates.and(filteredSetMultimap.entryPredicate(), predicate));
    }
    
    static boolean equalsImpl(final Multimap multimap, @Nullable final Object o) {
        return o == multimap || (o instanceof Multimap && multimap.asMap().equals(((Multimap)o).asMap()));
    }
    
    static Collection access$000(final Collection collection) {
        return unmodifiableValueCollection(collection);
    }
    
    static Collection access$100(final Collection collection) {
        return unmodifiableEntries(collection);
    }
    
    static final class AsMap extends Maps.ImprovedAbstractMap
    {
        private final Multimap multimap;
        
        AsMap(final Multimap multimap) {
            this.multimap = (Multimap)Preconditions.checkNotNull(multimap);
        }
        
        @Override
        public int size() {
            return this.multimap.keySet().size();
        }
        
        protected Set createEntrySet() {
            return new EntrySet();
        }
        
        void removeValuesForKey(final Object o) {
            this.multimap.keySet().remove(o);
        }
        
        @Override
        public Collection get(final Object o) {
            return this.containsKey(o) ? this.multimap.get(o) : null;
        }
        
        @Override
        public Collection remove(final Object o) {
            return this.containsKey(o) ? this.multimap.removeAll(o) : null;
        }
        
        @Override
        public Set keySet() {
            return this.multimap.keySet();
        }
        
        @Override
        public boolean isEmpty() {
            return this.multimap.isEmpty();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.multimap.containsKey(o);
        }
        
        @Override
        public void clear() {
            this.multimap.clear();
        }
        
        @Override
        public Object remove(final Object o) {
            return this.remove(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        static Multimap access$200(final AsMap asMap) {
            return asMap.multimap;
        }
        
        class EntrySet extends Maps.EntrySet
        {
            final AsMap this$0;
            
            EntrySet(final AsMap this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            Map map() {
                return this.this$0;
            }
            
            @Override
            public Iterator iterator() {
                return Maps.asMapEntryIterator(AsMap.access$200(this.this$0).keySet(), new Function() {
                    final EntrySet this$1;
                    
                    @Override
                    public Collection apply(final Object o) {
                        return AsMap.access$200(this.this$1.this$0).get(o);
                    }
                    
                    @Override
                    public Object apply(final Object o) {
                        return this.apply(o);
                    }
                });
            }
            
            @Override
            public boolean remove(final Object o) {
                if (!this.contains(o)) {
                    return false;
                }
                this.this$0.removeValuesForKey(((Map.Entry)o).getKey());
                return true;
            }
        }
    }
    
    abstract static class Entries extends AbstractCollection
    {
        abstract Multimap multimap();
        
        @Override
        public int size() {
            return this.multimap().size();
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                return this.multimap().containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        @Override
        public boolean remove(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                return this.multimap().remove(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        @Override
        public void clear() {
            this.multimap().clear();
        }
    }
    
    static class Keys extends AbstractMultiset
    {
        final Multimap multimap;
        
        Keys(final Multimap multimap) {
            this.multimap = multimap;
        }
        
        @Override
        Iterator entryIterator() {
            return new TransformedIterator((Iterator)this.multimap.asMap().entrySet().iterator()) {
                final Keys this$0;
                
                Multiset.Entry transform(final Map.Entry entry) {
                    return new Multisets.AbstractEntry(entry) {
                        final Map.Entry val$backingEntry;
                        final Multimaps$Keys$1 this$1;
                        
                        @Override
                        public Object getElement() {
                            return this.val$backingEntry.getKey();
                        }
                        
                        @Override
                        public int getCount() {
                            return this.val$backingEntry.getValue().size();
                        }
                    };
                }
                
                @Override
                Object transform(final Object o) {
                    return this.transform((Map.Entry)o);
                }
            };
        }
        
        @Override
        int distinctElements() {
            return this.multimap.asMap().size();
        }
        
        @Override
        Set createEntrySet() {
            return new KeysEntrySet();
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.multimap.containsKey(o);
        }
        
        @Override
        public Iterator iterator() {
            return Maps.keyIterator(this.multimap.entries().iterator());
        }
        
        @Override
        public int count(@Nullable final Object o) {
            final Collection collection = (Collection)Maps.safeGet(this.multimap.asMap(), o);
            return (collection == null) ? 0 : collection.size();
        }
        
        @Override
        public int remove(@Nullable final Object o, final int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(o);
            }
            final Collection collection = (Collection)Maps.safeGet(this.multimap.asMap(), o);
            if (collection == null) {
                return 0;
            }
            final int size = collection.size();
            if (n >= size) {
                collection.clear();
            }
            else {
                final Iterator iterator = collection.iterator();
                while (0 < n) {
                    iterator.next();
                    iterator.remove();
                    int n2 = 0;
                    ++n2;
                }
            }
            return size;
        }
        
        @Override
        public void clear() {
            this.multimap.clear();
        }
        
        @Override
        public Set elementSet() {
            return this.multimap.keySet();
        }
        
        class KeysEntrySet extends Multisets.EntrySet
        {
            final Keys this$0;
            
            KeysEntrySet(final Keys this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            Multiset multiset() {
                return this.this$0;
            }
            
            @Override
            public Iterator iterator() {
                return this.this$0.entryIterator();
            }
            
            @Override
            public int size() {
                return this.this$0.distinctElements();
            }
            
            @Override
            public boolean isEmpty() {
                return this.this$0.multimap.isEmpty();
            }
            
            @Override
            public boolean contains(@Nullable final Object o) {
                if (o instanceof Multiset.Entry) {
                    final Multiset.Entry entry = (Multiset.Entry)o;
                    final Collection collection = this.this$0.multimap.asMap().get(entry.getElement());
                    return collection != null && collection.size() == entry.getCount();
                }
                return false;
            }
            
            @Override
            public boolean remove(@Nullable final Object o) {
                if (o instanceof Multiset.Entry) {
                    final Multiset.Entry entry = (Multiset.Entry)o;
                    final Collection collection = this.this$0.multimap.asMap().get(entry.getElement());
                    if (collection != null && collection.size() == entry.getCount()) {
                        collection.clear();
                        return true;
                    }
                }
                return false;
            }
        }
    }
    
    private static final class TransformedEntriesListMultimap extends TransformedEntriesMultimap implements ListMultimap
    {
        TransformedEntriesListMultimap(final ListMultimap listMultimap, final Maps.EntryTransformer entryTransformer) {
            super(listMultimap, entryTransformer);
        }
        
        @Override
        List transform(final Object o, final Collection collection) {
            return Lists.transform((List)collection, Maps.asValueToValueFunction(this.transformer, o));
        }
        
        @Override
        public List get(final Object o) {
            return this.transform(o, this.fromMultimap.get(o));
        }
        
        @Override
        public List removeAll(final Object o) {
            return this.transform(o, this.fromMultimap.removeAll(o));
        }
        
        @Override
        public List replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
        
        @Override
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        Collection transform(final Object o, final Collection collection) {
            return this.transform(o, collection);
        }
    }
    
    private static class TransformedEntriesMultimap extends AbstractMultimap
    {
        final Multimap fromMultimap;
        final Maps.EntryTransformer transformer;
        
        TransformedEntriesMultimap(final Multimap multimap, final Maps.EntryTransformer entryTransformer) {
            this.fromMultimap = (Multimap)Preconditions.checkNotNull(multimap);
            this.transformer = (Maps.EntryTransformer)Preconditions.checkNotNull(entryTransformer);
        }
        
        Collection transform(final Object o, final Collection collection) {
            final Function valueToValueFunction = Maps.asValueToValueFunction(this.transformer, o);
            if (collection instanceof List) {
                return Lists.transform((List)collection, valueToValueFunction);
            }
            return Collections2.transform(collection, valueToValueFunction);
        }
        
        @Override
        Map createAsMap() {
            return Maps.transformEntries(this.fromMultimap.asMap(), new Maps.EntryTransformer() {
                final TransformedEntriesMultimap this$0;
                
                public Collection transformEntry(final Object o, final Collection collection) {
                    return this.this$0.transform(o, collection);
                }
                
                @Override
                public Object transformEntry(final Object o, final Object o2) {
                    return this.transformEntry(o, (Collection)o2);
                }
            });
        }
        
        @Override
        public void clear() {
            this.fromMultimap.clear();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.fromMultimap.containsKey(o);
        }
        
        @Override
        Iterator entryIterator() {
            return Iterators.transform(this.fromMultimap.entries().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }
        
        @Override
        public Collection get(final Object o) {
            return this.transform(o, this.fromMultimap.get(o));
        }
        
        @Override
        public boolean isEmpty() {
            return this.fromMultimap.isEmpty();
        }
        
        @Override
        public Set keySet() {
            return this.fromMultimap.keySet();
        }
        
        @Override
        public Multiset keys() {
            return this.fromMultimap.keys();
        }
        
        @Override
        public boolean put(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Multimap multimap) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            return this.get(o).remove(o2);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.transform(o, this.fromMultimap.removeAll(o));
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int size() {
            return this.fromMultimap.size();
        }
        
        @Override
        Collection createValues() {
            return Collections2.transform(this.fromMultimap.entries(), Maps.asEntryToValueFunction(this.transformer));
        }
    }
    
    private static class MapMultimap extends AbstractMultimap implements SetMultimap, Serializable
    {
        final Map map;
        private static final long serialVersionUID = 7845222491160860175L;
        
        MapMultimap(final Map map) {
            this.map = (Map)Preconditions.checkNotNull(map);
        }
        
        @Override
        public int size() {
            return this.map.size();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.map.containsKey(o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return this.map.containsValue(o);
        }
        
        @Override
        public boolean containsEntry(final Object o, final Object o2) {
            return this.map.entrySet().contains(Maps.immutableEntry(o, o2));
        }
        
        @Override
        public Set get(final Object o) {
            return new Sets.ImprovedAbstractSet(o) {
                final Object val$key;
                final MapMultimap this$0;
                
                @Override
                public Iterator iterator() {
                    return new Iterator() {
                        int i;
                        final Multimaps$MapMultimap$1 this$1;
                        
                        @Override
                        public boolean hasNext() {
                            return this.i == 0 && this.this$1.this$0.map.containsKey(this.this$1.val$key);
                        }
                        
                        @Override
                        public Object next() {
                            if (!this.hasNext()) {
                                throw new NoSuchElementException();
                            }
                            ++this.i;
                            return this.this$1.this$0.map.get(this.this$1.val$key);
                        }
                        
                        @Override
                        public void remove() {
                            CollectPreconditions.checkRemove(this.i == 1);
                            this.i = -1;
                            this.this$1.this$0.map.remove(this.this$1.val$key);
                        }
                    };
                }
                
                @Override
                public int size() {
                    return this.this$0.map.containsKey(this.val$key) ? 1 : 0;
                }
            };
        }
        
        @Override
        public boolean put(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Multimap multimap) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Set replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            return this.map.entrySet().remove(Maps.immutableEntry(o, o2));
        }
        
        @Override
        public Set removeAll(final Object o) {
            final HashSet<Object> set = new HashSet<Object>(2);
            if (!this.map.containsKey(o)) {
                return set;
            }
            set.add(this.map.remove(o));
            return set;
        }
        
        @Override
        public void clear() {
            this.map.clear();
        }
        
        @Override
        public Set keySet() {
            return this.map.keySet();
        }
        
        @Override
        public Collection values() {
            return this.map.values();
        }
        
        @Override
        public Set entries() {
            return this.map.entrySet();
        }
        
        @Override
        Iterator entryIterator() {
            return this.map.entrySet().iterator();
        }
        
        @Override
        Map createAsMap() {
            return new AsMap(this);
        }
        
        @Override
        public int hashCode() {
            return this.map.hashCode();
        }
        
        @Override
        public Collection entries() {
            return this.entries();
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
    }
    
    private static class UnmodifiableSortedSetMultimap extends UnmodifiableSetMultimap implements SortedSetMultimap
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableSortedSetMultimap(final SortedSetMultimap sortedSetMultimap) {
            super(sortedSetMultimap);
        }
        
        @Override
        public SortedSetMultimap delegate() {
            return (SortedSetMultimap)super.delegate();
        }
        
        @Override
        public SortedSet get(final Object o) {
            return Collections.unmodifiableSortedSet((SortedSet<Object>)this.delegate().get(o));
        }
        
        @Override
        public SortedSet removeAll(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public SortedSet replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Comparator valueComparator() {
            return this.delegate().valueComparator();
        }
        
        @Override
        public Set replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Set removeAll(final Object o) {
            return this.removeAll(o);
        }
        
        @Override
        public Set get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public SetMultimap delegate() {
            return this.delegate();
        }
        
        @Override
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Multimap delegate() {
            return this.delegate();
        }
        
        @Override
        public Object delegate() {
            return this.delegate();
        }
    }
    
    private static class UnmodifiableSetMultimap extends UnmodifiableMultimap implements SetMultimap
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableSetMultimap(final SetMultimap setMultimap) {
            super(setMultimap);
        }
        
        public SetMultimap delegate() {
            return (SetMultimap)super.delegate();
        }
        
        @Override
        public Set get(final Object o) {
            return Collections.unmodifiableSet((Set<?>)this.delegate().get(o));
        }
        
        @Override
        public Set entries() {
            return Maps.unmodifiableEntrySet(this.delegate().entries());
        }
        
        @Override
        public Set removeAll(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Set replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
        
        @Override
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public Collection entries() {
            return this.entries();
        }
        
        public Multimap delegate() {
            return this.delegate();
        }
        
        public Object delegate() {
            return this.delegate();
        }
    }
    
    private static class UnmodifiableMultimap extends ForwardingMultimap implements Serializable
    {
        final Multimap delegate;
        transient Collection entries;
        transient Multiset keys;
        transient Set keySet;
        transient Collection values;
        transient Map map;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableMultimap(final Multimap multimap) {
            this.delegate = (Multimap)Preconditions.checkNotNull(multimap);
        }
        
        @Override
        protected Multimap delegate() {
            return this.delegate;
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Map asMap() {
            Map<Object, Object> map = (Map<Object, Object>)this.map;
            if (map == null) {
                final Map<Object, Object> unmodifiableMap = Collections.unmodifiableMap((Map<?, ?>)Maps.transformValues(this.delegate.asMap(), new Function() {
                    final UnmodifiableMultimap this$0;
                    
                    public Collection apply(final Collection collection) {
                        return Multimaps.access$000(collection);
                    }
                    
                    @Override
                    public Object apply(final Object o) {
                        return this.apply((Collection)o);
                    }
                }));
                this.map = unmodifiableMap;
                map = unmodifiableMap;
            }
            return map;
        }
        
        @Override
        public Collection entries() {
            Collection entries = this.entries;
            if (entries == null) {
                entries = (this.entries = Multimaps.access$100(this.delegate.entries()));
            }
            return entries;
        }
        
        @Override
        public Collection get(final Object o) {
            return Multimaps.access$000(this.delegate.get(o));
        }
        
        @Override
        public Multiset keys() {
            Multiset keys = this.keys;
            if (keys == null) {
                keys = (this.keys = Multisets.unmodifiableMultiset(this.delegate.keys()));
            }
            return keys;
        }
        
        @Override
        public Set keySet() {
            Set keySet = this.keySet;
            if (keySet == null) {
                keySet = (this.keySet = Collections.unmodifiableSet((Set<?>)this.delegate.keySet()));
            }
            return keySet;
        }
        
        @Override
        public boolean put(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean putAll(final Multimap multimap) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection removeAll(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection values() {
            Collection values = this.values;
            if (values == null) {
                values = (this.values = Collections.unmodifiableCollection((Collection<?>)this.delegate.values()));
            }
            return values;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class UnmodifiableListMultimap extends UnmodifiableMultimap implements ListMultimap
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableListMultimap(final ListMultimap listMultimap) {
            super(listMultimap);
        }
        
        public ListMultimap delegate() {
            return (ListMultimap)super.delegate();
        }
        
        @Override
        public List get(final Object o) {
            return Collections.unmodifiableList((List<?>)this.delegate().get(o));
        }
        
        @Override
        public List removeAll(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public List replaceValues(final Object o, final Iterable iterable) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.replaceValues(o, iterable);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
        
        @Override
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        public Multimap delegate() {
            return this.delegate();
        }
        
        public Object delegate() {
            return this.delegate();
        }
    }
    
    private static class CustomSortedSetMultimap extends AbstractSortedSetMultimap
    {
        transient Supplier factory;
        transient Comparator valueComparator;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        CustomSortedSetMultimap(final Map map, final Supplier supplier) {
            super(map);
            this.factory = (Supplier)Preconditions.checkNotNull(supplier);
            this.valueComparator = ((SortedSet)supplier.get()).comparator();
        }
        
        protected SortedSet createCollection() {
            return (SortedSet)this.factory.get();
        }
        
        @Override
        public Comparator valueComparator() {
            return this.valueComparator;
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.valueComparator = ((SortedSet)this.factory.get()).comparator();
            this.setMap((Map)objectInputStream.readObject());
        }
        
        protected Set createCollection() {
            return this.createCollection();
        }
        
        protected Collection createCollection() {
            return this.createCollection();
        }
    }
    
    private static class CustomSetMultimap extends AbstractSetMultimap
    {
        transient Supplier factory;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        CustomSetMultimap(final Map map, final Supplier supplier) {
            super(map);
            this.factory = (Supplier)Preconditions.checkNotNull(supplier);
        }
        
        protected Set createCollection() {
            return (Set)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }
        
        protected Collection createCollection() {
            return this.createCollection();
        }
    }
    
    private static class CustomListMultimap extends AbstractListMultimap
    {
        transient Supplier factory;
        @GwtIncompatible("java serialization not supported")
        private static final long serialVersionUID = 0L;
        
        CustomListMultimap(final Map map, final Supplier supplier) {
            super(map);
            this.factory = (Supplier)Preconditions.checkNotNull(supplier);
        }
        
        protected List createCollection() {
            return (List)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }
        
        protected Collection createCollection() {
            return this.createCollection();
        }
    }
    
    private static class CustomMultimap extends AbstractMapBasedMultimap
    {
        transient Supplier factory;
        @GwtIncompatible("java serialization not supported")
        private static final long serialVersionUID = 0L;
        
        CustomMultimap(final Map map, final Supplier supplier) {
            super(map);
            this.factory = (Supplier)Preconditions.checkNotNull(supplier);
        }
        
        protected Collection createCollection() {
            return (Collection)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }
    }
}
