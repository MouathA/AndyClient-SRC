package com.google.common.collect;

import java.util.concurrent.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;
import com.google.common.base.*;

@GwtCompatible(emulated = true)
public final class Maps
{
    static final Joiner.MapJoiner STANDARD_JOINER;
    
    private Maps() {
    }
    
    static Function keyFunction() {
        return EntryFunction.KEY;
    }
    
    static Function valueFunction() {
        return EntryFunction.VALUE;
    }
    
    static Iterator keyIterator(final Iterator iterator) {
        return Iterators.transform(iterator, keyFunction());
    }
    
    static Iterator valueIterator(final Iterator iterator) {
        return Iterators.transform(iterator, valueFunction());
    }
    
    static UnmodifiableIterator valueIterator(final UnmodifiableIterator unmodifiableIterator) {
        return new UnmodifiableIterator(unmodifiableIterator) {
            final UnmodifiableIterator val$entryIterator;
            
            @Override
            public boolean hasNext() {
                return this.val$entryIterator.hasNext();
            }
            
            @Override
            public Object next() {
                return this.val$entryIterator.next().getValue();
            }
        };
    }
    
    @GwtCompatible(serializable = true)
    @Beta
    public static ImmutableMap immutableEnumMap(final Map map) {
        if (map instanceof ImmutableEnumMap) {
            return (ImmutableEnumMap)map;
        }
        if (map.isEmpty()) {
            return ImmutableMap.of();
        }
        for (final Map.Entry<Object, ? extends V> entry : map.entrySet()) {
            Preconditions.checkNotNull(entry.getKey());
            Preconditions.checkNotNull(entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(new EnumMap(map));
    }
    
    public static HashMap newHashMap() {
        return new HashMap();
    }
    
    public static HashMap newHashMapWithExpectedSize(final int n) {
        return new HashMap(capacity(n));
    }
    
    static int capacity(final int n) {
        if (n < 3) {
            CollectPreconditions.checkNonnegative(n, "expectedSize");
            return n + 1;
        }
        if (n < 1073741824) {
            return n + n / 3;
        }
        return Integer.MAX_VALUE;
    }
    
    public static HashMap newHashMap(final Map map) {
        return new HashMap(map);
    }
    
    public static LinkedHashMap newLinkedHashMap() {
        return new LinkedHashMap();
    }
    
    public static LinkedHashMap newLinkedHashMap(final Map map) {
        return new LinkedHashMap(map);
    }
    
    public static ConcurrentMap newConcurrentMap() {
        return new MapMaker().makeMap();
    }
    
    public static TreeMap newTreeMap() {
        return new TreeMap();
    }
    
    public static TreeMap newTreeMap(final SortedMap sortedMap) {
        return new TreeMap((SortedMap<K, ? extends V>)sortedMap);
    }
    
    public static TreeMap newTreeMap(@Nullable final Comparator comparator) {
        return new TreeMap(comparator);
    }
    
    public static EnumMap newEnumMap(final Class clazz) {
        return new EnumMap((Class<K>)Preconditions.checkNotNull(clazz));
    }
    
    public static EnumMap newEnumMap(final Map map) {
        return new EnumMap(map);
    }
    
    public static IdentityHashMap newIdentityHashMap() {
        return new IdentityHashMap();
    }
    
    public static MapDifference difference(final Map map, final Map map2) {
        if (map instanceof SortedMap) {
            return difference((SortedMap)map, map2);
        }
        return difference(map, map2, Equivalence.equals());
    }
    
    @Beta
    public static MapDifference difference(final Map map, final Map map2, final Equivalence equivalence) {
        Preconditions.checkNotNull(equivalence);
        final HashMap hashMap = newHashMap();
        final HashMap hashMap2 = new HashMap(map2);
        final HashMap hashMap3 = newHashMap();
        final HashMap hashMap4 = newHashMap();
        doDifference(map, map2, equivalence, hashMap, hashMap2, hashMap3, hashMap4);
        return new MapDifferenceImpl(hashMap, hashMap2, hashMap3, hashMap4);
    }
    
    private static void doDifference(final Map map, final Map map2, final Equivalence equivalence, final Map map3, final Map map4, final Map map5, final Map map6) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            final Object key = entry.getKey();
            final V value = entry.getValue();
            if (map2.containsKey(key)) {
                final Object remove = map4.remove(key);
                if (equivalence.equivalent(value, remove)) {
                    map5.put(key, value);
                }
                else {
                    map6.put(key, ValueDifferenceImpl.create(value, remove));
                }
            }
            else {
                map3.put(key, value);
            }
        }
    }
    
    private static Map unmodifiableMap(final Map map) {
        if (map instanceof SortedMap) {
            return Collections.unmodifiableSortedMap((SortedMap<Object, ?>)(SortedMap<Object, ? extends V>)map);
        }
        return Collections.unmodifiableMap((Map<?, ?>)map);
    }
    
    public static SortedMapDifference difference(final SortedMap sortedMap, final Map map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        final Comparator orNaturalOrder = orNaturalOrder(sortedMap.comparator());
        final TreeMap treeMap = newTreeMap(orNaturalOrder);
        final TreeMap treeMap2 = newTreeMap(orNaturalOrder);
        treeMap2.putAll(map);
        final TreeMap treeMap3 = newTreeMap(orNaturalOrder);
        final TreeMap treeMap4 = newTreeMap(orNaturalOrder);
        doDifference(sortedMap, map, Equivalence.equals(), treeMap, treeMap2, treeMap3, treeMap4);
        return new SortedMapDifferenceImpl(treeMap, treeMap2, treeMap3, treeMap4);
    }
    
    static Comparator orNaturalOrder(@Nullable final Comparator comparator) {
        if (comparator != null) {
            return comparator;
        }
        return Ordering.natural();
    }
    
    @Beta
    public static Map asMap(final Set set, final Function function) {
        if (set instanceof SortedSet) {
            return asMap((SortedSet)set, function);
        }
        return new AsMapView(set, function);
    }
    
    @Beta
    public static SortedMap asMap(final SortedSet set, final Function function) {
        return Platform.mapsAsMapSortedSet(set, function);
    }
    
    static SortedMap asMapSortedIgnoreNavigable(final SortedSet set, final Function function) {
        return new SortedAsMapView(set, function);
    }
    
    @Beta
    @GwtIncompatible("NavigableMap")
    public static NavigableMap asMap(final NavigableSet set, final Function function) {
        return new NavigableAsMapView(set, function);
    }
    
    static Iterator asMapEntryIterator(final Set set, final Function function) {
        return new TransformedIterator(set.iterator(), function) {
            final Function val$function;
            
            @Override
            Map.Entry transform(final Object o) {
                return Maps.immutableEntry(o, this.val$function.apply(o));
            }
            
            @Override
            Object transform(final Object o) {
                return this.transform(o);
            }
        };
    }
    
    private static Set removeOnlySet(final Set set) {
        return new ForwardingSet(set) {
            final Set val$set;
            
            @Override
            protected Set delegate() {
                return this.val$set;
            }
            
            @Override
            public boolean add(final Object o) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean addAll(final Collection collection) {
                throw new UnsupportedOperationException();
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
    
    private static SortedSet removeOnlySortedSet(final SortedSet set) {
        return new ForwardingSortedSet(set) {
            final SortedSet val$set;
            
            @Override
            protected SortedSet delegate() {
                return this.val$set;
            }
            
            @Override
            public boolean add(final Object o) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean addAll(final Collection collection) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public SortedSet headSet(final Object o) {
                return Maps.access$300(super.headSet(o));
            }
            
            @Override
            public SortedSet subSet(final Object o, final Object o2) {
                return Maps.access$300(super.subSet(o, o2));
            }
            
            @Override
            public SortedSet tailSet(final Object o) {
                return Maps.access$300(super.tailSet(o));
            }
            
            @Override
            protected Set delegate() {
                return this.delegate();
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
    
    @GwtIncompatible("NavigableSet")
    private static NavigableSet removeOnlyNavigableSet(final NavigableSet set) {
        return new ForwardingNavigableSet(set) {
            final NavigableSet val$set;
            
            @Override
            protected NavigableSet delegate() {
                return this.val$set;
            }
            
            @Override
            public boolean add(final Object o) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean addAll(final Collection collection) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public SortedSet headSet(final Object o) {
                return Maps.access$300(super.headSet(o));
            }
            
            @Override
            public SortedSet subSet(final Object o, final Object o2) {
                return Maps.access$300(super.subSet(o, o2));
            }
            
            @Override
            public SortedSet tailSet(final Object o) {
                return Maps.access$300(super.tailSet(o));
            }
            
            @Override
            public NavigableSet headSet(final Object o, final boolean b) {
                return Maps.access$400(super.headSet(o, b));
            }
            
            @Override
            public NavigableSet tailSet(final Object o, final boolean b) {
                return Maps.access$400(super.tailSet(o, b));
            }
            
            @Override
            public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
                return Maps.access$400(super.subSet(o, b, o2, b2));
            }
            
            @Override
            public NavigableSet descendingSet() {
                return Maps.access$400(super.descendingSet());
            }
            
            @Override
            protected SortedSet delegate() {
                return this.delegate();
            }
            
            @Override
            protected Set delegate() {
                return this.delegate();
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
    
    @Beta
    public static ImmutableMap toMap(final Iterable iterable, final Function function) {
        return toMap(iterable.iterator(), function);
    }
    
    @Beta
    public static ImmutableMap toMap(final Iterator iterator, final Function function) {
        Preconditions.checkNotNull(function);
        final LinkedHashMap linkedHashMap = newLinkedHashMap();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            linkedHashMap.put(next, function.apply(next));
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }
    
    public static ImmutableMap uniqueIndex(final Iterable iterable, final Function function) {
        return uniqueIndex(iterable.iterator(), function);
    }
    
    public static ImmutableMap uniqueIndex(final Iterator iterator, final Function function) {
        Preconditions.checkNotNull(function);
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            builder.put(function.apply(next), next);
        }
        return builder.build();
    }
    
    @GwtIncompatible("java.util.Properties")
    public static ImmutableMap fromProperties(final Properties properties) {
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        final Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = (String)propertyNames.nextElement();
            builder.put(s, properties.getProperty(s));
        }
        return builder.build();
    }
    
    @GwtCompatible(serializable = true)
    public static Map.Entry immutableEntry(@Nullable final Object o, @Nullable final Object o2) {
        return new ImmutableEntry(o, o2);
    }
    
    static Set unmodifiableEntrySet(final Set set) {
        return new UnmodifiableEntrySet(Collections.unmodifiableSet((Set<?>)set));
    }
    
    static Map.Entry unmodifiableEntry(final Map.Entry entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry(entry) {
            final Map.Entry val$entry;
            
            @Override
            public Object getKey() {
                return this.val$entry.getKey();
            }
            
            @Override
            public Object getValue() {
                return this.val$entry.getValue();
            }
        };
    }
    
    @Beta
    public static Converter asConverter(final BiMap biMap) {
        return new BiMapConverter(biMap);
    }
    
    public static BiMap synchronizedBiMap(final BiMap biMap) {
        return Synchronized.biMap(biMap, null);
    }
    
    public static BiMap unmodifiableBiMap(final BiMap biMap) {
        return new UnmodifiableBiMap(biMap, null);
    }
    
    public static Map transformValues(final Map map, final Function function) {
        return transformEntries(map, asEntryTransformer(function));
    }
    
    public static SortedMap transformValues(final SortedMap sortedMap, final Function function) {
        return transformEntries(sortedMap, asEntryTransformer(function));
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap transformValues(final NavigableMap navigableMap, final Function function) {
        return transformEntries(navigableMap, asEntryTransformer(function));
    }
    
    public static Map transformEntries(final Map map, final EntryTransformer entryTransformer) {
        if (map instanceof SortedMap) {
            return transformEntries((SortedMap)map, entryTransformer);
        }
        return new TransformedEntriesMap(map, entryTransformer);
    }
    
    public static SortedMap transformEntries(final SortedMap sortedMap, final EntryTransformer entryTransformer) {
        return Platform.mapsTransformEntriesSortedMap(sortedMap, entryTransformer);
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap transformEntries(final NavigableMap navigableMap, final EntryTransformer entryTransformer) {
        return new TransformedEntriesNavigableMap(navigableMap, entryTransformer);
    }
    
    static SortedMap transformEntriesIgnoreNavigable(final SortedMap sortedMap, final EntryTransformer entryTransformer) {
        return new TransformedEntriesSortedMap(sortedMap, entryTransformer);
    }
    
    static EntryTransformer asEntryTransformer(final Function function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer(function) {
            final Function val$function;
            
            @Override
            public Object transformEntry(final Object o, final Object o2) {
                return this.val$function.apply(o2);
            }
        };
    }
    
    static Function asValueToValueFunction(final EntryTransformer entryTransformer, final Object o) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function(entryTransformer, o) {
            final EntryTransformer val$transformer;
            final Object val$key;
            
            @Override
            public Object apply(@Nullable final Object o) {
                return this.val$transformer.transformEntry(this.val$key, o);
            }
        };
    }
    
    static Function asEntryToValueFunction(final EntryTransformer entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function(entryTransformer) {
            final EntryTransformer val$transformer;
            
            public Object apply(final Map.Entry entry) {
                return this.val$transformer.transformEntry(entry.getKey(), entry.getValue());
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
        };
    }
    
    static Map.Entry transformEntry(final EntryTransformer entryTransformer, final Map.Entry entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry(entry, entryTransformer) {
            final Map.Entry val$entry;
            final EntryTransformer val$transformer;
            
            @Override
            public Object getKey() {
                return this.val$entry.getKey();
            }
            
            @Override
            public Object getValue() {
                return this.val$transformer.transformEntry(this.val$entry.getKey(), this.val$entry.getValue());
            }
        };
    }
    
    static Function asEntryToEntryFunction(final EntryTransformer entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function(entryTransformer) {
            final EntryTransformer val$transformer;
            
            public Map.Entry apply(final Map.Entry entry) {
                return Maps.transformEntry(this.val$transformer, entry);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
        };
    }
    
    static Predicate keyPredicateOnEntries(final Predicate predicate) {
        return Predicates.compose(predicate, keyFunction());
    }
    
    static Predicate valuePredicateOnEntries(final Predicate predicate) {
        return Predicates.compose(predicate, valueFunction());
    }
    
    public static Map filterKeys(final Map map, final Predicate predicate) {
        if (map instanceof SortedMap) {
            return filterKeys((SortedMap)map, predicate);
        }
        if (map instanceof BiMap) {
            return filterKeys((BiMap)map, predicate);
        }
        Preconditions.checkNotNull(predicate);
        final Predicate keyPredicateOnEntries = keyPredicateOnEntries(predicate);
        return (map instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap)map, keyPredicateOnEntries) : new FilteredKeyMap((Map)Preconditions.checkNotNull(map), predicate, keyPredicateOnEntries);
    }
    
    public static SortedMap filterKeys(final SortedMap sortedMap, final Predicate predicate) {
        return filterEntries(sortedMap, keyPredicateOnEntries(predicate));
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap filterKeys(final NavigableMap navigableMap, final Predicate predicate) {
        return filterEntries(navigableMap, keyPredicateOnEntries(predicate));
    }
    
    public static BiMap filterKeys(final BiMap biMap, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        return filterEntries(biMap, keyPredicateOnEntries(predicate));
    }
    
    public static Map filterValues(final Map map, final Predicate predicate) {
        if (map instanceof SortedMap) {
            return filterValues((SortedMap)map, predicate);
        }
        if (map instanceof BiMap) {
            return filterValues((BiMap)map, predicate);
        }
        return filterEntries(map, valuePredicateOnEntries(predicate));
    }
    
    public static SortedMap filterValues(final SortedMap sortedMap, final Predicate predicate) {
        return filterEntries(sortedMap, valuePredicateOnEntries(predicate));
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap filterValues(final NavigableMap navigableMap, final Predicate predicate) {
        return filterEntries(navigableMap, valuePredicateOnEntries(predicate));
    }
    
    public static BiMap filterValues(final BiMap biMap, final Predicate predicate) {
        return filterEntries(biMap, valuePredicateOnEntries(predicate));
    }
    
    public static Map filterEntries(final Map map, final Predicate predicate) {
        if (map instanceof SortedMap) {
            return filterEntries((SortedMap)map, predicate);
        }
        if (map instanceof BiMap) {
            return filterEntries((BiMap)map, predicate);
        }
        Preconditions.checkNotNull(predicate);
        return (map instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap)map, predicate) : new FilteredEntryMap((Map)Preconditions.checkNotNull(map), predicate);
    }
    
    public static SortedMap filterEntries(final SortedMap sortedMap, final Predicate predicate) {
        return Platform.mapsFilterSortedMap(sortedMap, predicate);
    }
    
    static SortedMap filterSortedIgnoreNavigable(final SortedMap sortedMap, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        return (sortedMap instanceof FilteredEntrySortedMap) ? filterFiltered((FilteredEntrySortedMap)sortedMap, predicate) : new FilteredEntrySortedMap((SortedMap)Preconditions.checkNotNull(sortedMap), predicate);
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap filterEntries(final NavigableMap navigableMap, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        return (navigableMap instanceof FilteredEntryNavigableMap) ? filterFiltered((FilteredEntryNavigableMap)navigableMap, predicate) : new FilteredEntryNavigableMap((NavigableMap)Preconditions.checkNotNull(navigableMap), predicate);
    }
    
    public static BiMap filterEntries(final BiMap biMap, final Predicate predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        return (biMap instanceof FilteredEntryBiMap) ? filterFiltered((FilteredEntryBiMap)biMap, predicate) : new FilteredEntryBiMap(biMap, predicate);
    }
    
    private static Map filterFiltered(final AbstractFilteredMap abstractFilteredMap, final Predicate predicate) {
        return new FilteredEntryMap(abstractFilteredMap.unfiltered, Predicates.and(abstractFilteredMap.predicate, predicate));
    }
    
    private static SortedMap filterFiltered(final FilteredEntrySortedMap filteredEntrySortedMap, final Predicate predicate) {
        return new FilteredEntrySortedMap(filteredEntrySortedMap.sortedMap(), Predicates.and(filteredEntrySortedMap.predicate, predicate));
    }
    
    @GwtIncompatible("NavigableMap")
    private static NavigableMap filterFiltered(final FilteredEntryNavigableMap filteredEntryNavigableMap, final Predicate predicate) {
        return new FilteredEntryNavigableMap(FilteredEntryNavigableMap.access$700(filteredEntryNavigableMap), Predicates.and(FilteredEntryNavigableMap.access$600(filteredEntryNavigableMap), predicate));
    }
    
    private static BiMap filterFiltered(final FilteredEntryBiMap filteredEntryBiMap, final Predicate predicate) {
        return new FilteredEntryBiMap(filteredEntryBiMap.unfiltered(), Predicates.and(filteredEntryBiMap.predicate, predicate));
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap unmodifiableNavigableMap(final NavigableMap navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        if (navigableMap instanceof UnmodifiableNavigableMap) {
            return navigableMap;
        }
        return new UnmodifiableNavigableMap(navigableMap);
    }
    
    @Nullable
    private static Map.Entry unmodifiableOrNull(@Nullable final Map.Entry entry) {
        return (entry == null) ? null : unmodifiableEntry(entry);
    }
    
    @GwtIncompatible("NavigableMap")
    public static NavigableMap synchronizedNavigableMap(final NavigableMap navigableMap) {
        return Synchronized.navigableMap(navigableMap);
    }
    
    static Object safeGet(final Map map, @Nullable final Object o) {
        Preconditions.checkNotNull(map);
        return map.get(o);
    }
    
    static boolean safeContainsKey(final Map map, final Object o) {
        Preconditions.checkNotNull(map);
        return map.containsKey(o);
    }
    
    static Object safeRemove(final Map map, final Object o) {
        Preconditions.checkNotNull(map);
        return map.remove(o);
    }
    
    static boolean containsKeyImpl(final Map map, @Nullable final Object o) {
        return Iterators.contains(keyIterator(map.entrySet().iterator()), o);
    }
    
    static boolean containsValueImpl(final Map map, @Nullable final Object o) {
        return Iterators.contains(valueIterator(map.entrySet().iterator()), o);
    }
    
    static boolean containsEntryImpl(final Collection collection, final Object o) {
        return o instanceof Map.Entry && collection.contains(unmodifiableEntry((Map.Entry)o));
    }
    
    static boolean removeEntryImpl(final Collection collection, final Object o) {
        return o instanceof Map.Entry && collection.remove(unmodifiableEntry((Map.Entry)o));
    }
    
    static boolean equalsImpl(final Map map, final Object o) {
        return map == o || (o instanceof Map && map.entrySet().equals(((Map)o).entrySet()));
    }
    
    static String toStringImpl(final Map map) {
        final StringBuilder append = Collections2.newStringBuilderForCollection(map.size()).append('{');
        Maps.STANDARD_JOINER.appendTo(append, map);
        return append.append('}').toString();
    }
    
    static void putAllImpl(final Map map, final Map map2) {
        for (final Map.Entry<Object, V> entry : map2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Nullable
    static Object keyOrNull(@Nullable final Map.Entry entry) {
        return (entry == null) ? null : entry.getKey();
    }
    
    @Nullable
    static Object valueOrNull(@Nullable final Map.Entry entry) {
        return (entry == null) ? null : entry.getValue();
    }
    
    static Map access$100(final Map map) {
        return unmodifiableMap(map);
    }
    
    static Set access$200(final Set set) {
        return removeOnlySet(set);
    }
    
    static SortedSet access$300(final SortedSet set) {
        return removeOnlySortedSet(set);
    }
    
    static NavigableSet access$400(final NavigableSet set) {
        return removeOnlyNavigableSet(set);
    }
    
    static Map.Entry access$800(final Map.Entry entry) {
        return unmodifiableOrNull(entry);
    }
    
    static {
        STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");
    }
    
    @GwtIncompatible("NavigableMap")
    abstract static class DescendingMap extends ForwardingMap implements NavigableMap
    {
        private transient Comparator comparator;
        private transient Set entrySet;
        private transient NavigableSet navigableKeySet;
        
        abstract NavigableMap forward();
        
        @Override
        protected final Map delegate() {
            return this.forward();
        }
        
        @Override
        public Comparator comparator() {
            Comparator comparator = this.comparator;
            if (comparator == null) {
                Comparator comparator2 = this.forward().comparator();
                if (comparator2 == null) {
                    comparator2 = Ordering.natural();
                }
                final Ordering reverse = reverse(comparator2);
                this.comparator = reverse;
                comparator = reverse;
            }
            return comparator;
        }
        
        private static Ordering reverse(final Comparator comparator) {
            return Ordering.from(comparator).reverse();
        }
        
        @Override
        public Object firstKey() {
            return this.forward().lastKey();
        }
        
        @Override
        public Object lastKey() {
            return this.forward().firstKey();
        }
        
        @Override
        public Map.Entry lowerEntry(final Object o) {
            return this.forward().higherEntry(o);
        }
        
        @Override
        public Object lowerKey(final Object o) {
            return this.forward().higherKey(o);
        }
        
        @Override
        public Map.Entry floorEntry(final Object o) {
            return this.forward().ceilingEntry(o);
        }
        
        @Override
        public Object floorKey(final Object o) {
            return this.forward().ceilingKey(o);
        }
        
        @Override
        public Map.Entry ceilingEntry(final Object o) {
            return this.forward().floorEntry(o);
        }
        
        @Override
        public Object ceilingKey(final Object o) {
            return this.forward().floorKey(o);
        }
        
        @Override
        public Map.Entry higherEntry(final Object o) {
            return this.forward().lowerEntry(o);
        }
        
        @Override
        public Object higherKey(final Object o) {
            return this.forward().lowerKey(o);
        }
        
        @Override
        public Map.Entry firstEntry() {
            return this.forward().lastEntry();
        }
        
        @Override
        public Map.Entry lastEntry() {
            return this.forward().firstEntry();
        }
        
        @Override
        public Map.Entry pollFirstEntry() {
            return this.forward().pollLastEntry();
        }
        
        @Override
        public Map.Entry pollLastEntry() {
            return this.forward().pollFirstEntry();
        }
        
        @Override
        public NavigableMap descendingMap() {
            return this.forward();
        }
        
        @Override
        public Set entrySet() {
            final Set entrySet = this.entrySet;
            return (entrySet == null) ? (this.entrySet = this.createEntrySet()) : entrySet;
        }
        
        abstract Iterator entryIterator();
        
        Set createEntrySet() {
            return new EntrySet() {
                final DescendingMap this$0;
                
                @Override
                Map map() {
                    return this.this$0;
                }
                
                @Override
                public Iterator iterator() {
                    return this.this$0.entryIterator();
                }
            };
        }
        
        @Override
        public Set keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            final NavigableSet navigableKeySet = this.navigableKeySet;
            return (navigableKeySet == null) ? (this.navigableKeySet = new NavigableKeySet(this)) : navigableKeySet;
        }
        
        @Override
        public NavigableSet descendingKeySet() {
            return this.forward().navigableKeySet();
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.forward().subMap(o2, b2, o, b).descendingMap();
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return this.forward().tailMap(o, b).descendingMap();
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return this.forward().headMap(o, b).descendingMap();
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, true, o2, false);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o, false);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o, true);
        }
        
        @Override
        public Collection values() {
            return new Values(this);
        }
        
        @Override
        public String toString() {
            return this.standardToString();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    @GwtIncompatible("NavigableMap")
    static class NavigableKeySet extends SortedKeySet implements NavigableSet
    {
        NavigableKeySet(final NavigableMap navigableMap) {
            super(navigableMap);
        }
        
        @Override
        NavigableMap map() {
            return (NavigableMap)this.map;
        }
        
        @Override
        public Object lower(final Object o) {
            return this.map().lowerKey(o);
        }
        
        @Override
        public Object floor(final Object o) {
            return this.map().floorKey(o);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return this.map().ceilingKey(o);
        }
        
        @Override
        public Object higher(final Object o) {
            return this.map().higherKey(o);
        }
        
        @Override
        public Object pollFirst() {
            return Maps.keyOrNull(this.map().pollFirstEntry());
        }
        
        @Override
        public Object pollLast() {
            return Maps.keyOrNull(this.map().pollLastEntry());
        }
        
        @Override
        public NavigableSet descendingSet() {
            return this.map().descendingKeySet();
        }
        
        @Override
        public Iterator descendingIterator() {
            return this.descendingSet().iterator();
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.map().subMap(o, b, o2, b2).navigableKeySet();
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return this.map().headMap(o, b).navigableKeySet();
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return this.map().tailMap(o, b).navigableKeySet();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, true, o2, false);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o, false);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o, true);
        }
        
        @Override
        SortedMap map() {
            return this.map();
        }
        
        @Override
        Map map() {
            return this.map();
        }
    }
    
    static class SortedKeySet extends KeySet implements SortedSet
    {
        SortedKeySet(final SortedMap sortedMap) {
            super(sortedMap);
        }
        
        @Override
        SortedMap map() {
            return (SortedMap)super.map();
        }
        
        @Override
        public Comparator comparator() {
            return this.map().comparator();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return new SortedKeySet(this.map().subMap(o, o2));
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return new SortedKeySet(this.map().headMap(o));
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return new SortedKeySet(this.map().tailMap(o));
        }
        
        @Override
        public Object first() {
            return this.map().firstKey();
        }
        
        @Override
        public Object last() {
            return this.map().lastKey();
        }
        
        @Override
        Map map() {
            return this.map();
        }
    }
    
    static class KeySet extends Sets.ImprovedAbstractSet
    {
        final Map map;
        
        KeySet(final Map map) {
            this.map = (Map)Preconditions.checkNotNull(map);
        }
        
        Map map() {
            return this.map;
        }
        
        @Override
        public Iterator iterator() {
            return Maps.keyIterator(this.map().entrySet().iterator());
        }
        
        @Override
        public int size() {
            return this.map().size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.map().containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (this.contains(o)) {
                this.map().remove(o);
                return true;
            }
            return false;
        }
        
        @Override
        public void clear() {
            this.map().clear();
        }
    }
    
    static class Values extends AbstractCollection
    {
        final Map map;
        
        Values(final Map map) {
            this.map = (Map)Preconditions.checkNotNull(map);
        }
        
        final Map map() {
            return this.map;
        }
        
        @Override
        public Iterator iterator() {
            return Maps.valueIterator(this.map().entrySet().iterator());
        }
        
        @Override
        public boolean remove(final Object o) {
            return super.remove(o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return super.removeAll((Collection<?>)Preconditions.checkNotNull(collection));
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return super.retainAll((Collection<?>)Preconditions.checkNotNull(collection));
        }
        
        @Override
        public int size() {
            return this.map().size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.map().containsValue(o);
        }
        
        @Override
        public void clear() {
            this.map().clear();
        }
    }
    
    abstract static class EntrySet extends Sets.ImprovedAbstractSet
    {
        abstract Map map();
        
        @Override
        public int size() {
            return this.map().size();
        }
        
        @Override
        public void clear() {
            this.map().clear();
        }
        
        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        @Override
        public boolean remove(final Object o) {
            return o != 0 && this.map().keySet().remove(((Map.Entry)o).getKey());
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return super.removeAll((Collection)Preconditions.checkNotNull(collection));
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return super.retainAll((Collection)Preconditions.checkNotNull(collection));
        }
    }
    
    @GwtCompatible
    abstract static class ImprovedAbstractMap extends AbstractMap
    {
        private transient Set entrySet;
        private transient Set keySet;
        private transient Collection values;
        
        abstract Set createEntrySet();
        
        @Override
        public Set entrySet() {
            final Set entrySet = this.entrySet;
            return (entrySet == null) ? (this.entrySet = this.createEntrySet()) : entrySet;
        }
        
        @Override
        public Set keySet() {
            final Set keySet = this.keySet;
            return (keySet == null) ? (this.keySet = this.createKeySet()) : keySet;
        }
        
        Set createKeySet() {
            return new KeySet(this);
        }
        
        @Override
        public Collection values() {
            final Collection values = this.values;
            return (values == null) ? (this.values = this.createValues()) : values;
        }
        
        Collection createValues() {
            return new Values(this);
        }
    }
    
    @GwtIncompatible("NavigableMap")
    static class UnmodifiableNavigableMap extends ForwardingSortedMap implements NavigableMap, Serializable
    {
        private final NavigableMap delegate;
        private transient UnmodifiableNavigableMap descendingMap;
        
        UnmodifiableNavigableMap(final NavigableMap delegate) {
            this.delegate = delegate;
        }
        
        UnmodifiableNavigableMap(final NavigableMap delegate, final UnmodifiableNavigableMap descendingMap) {
            this.delegate = delegate;
            this.descendingMap = descendingMap;
        }
        
        @Override
        protected SortedMap delegate() {
            return Collections.unmodifiableSortedMap((SortedMap<Object, ?>)this.delegate);
        }
        
        @Override
        public Map.Entry lowerEntry(final Object o) {
            return Maps.access$800(this.delegate.lowerEntry(o));
        }
        
        @Override
        public Object lowerKey(final Object o) {
            return this.delegate.lowerKey(o);
        }
        
        @Override
        public Map.Entry floorEntry(final Object o) {
            return Maps.access$800(this.delegate.floorEntry(o));
        }
        
        @Override
        public Object floorKey(final Object o) {
            return this.delegate.floorKey(o);
        }
        
        @Override
        public Map.Entry ceilingEntry(final Object o) {
            return Maps.access$800(this.delegate.ceilingEntry(o));
        }
        
        @Override
        public Object ceilingKey(final Object o) {
            return this.delegate.ceilingKey(o);
        }
        
        @Override
        public Map.Entry higherEntry(final Object o) {
            return Maps.access$800(this.delegate.higherEntry(o));
        }
        
        @Override
        public Object higherKey(final Object o) {
            return this.delegate.higherKey(o);
        }
        
        @Override
        public Map.Entry firstEntry() {
            return Maps.access$800(this.delegate.firstEntry());
        }
        
        @Override
        public Map.Entry lastEntry() {
            return Maps.access$800(this.delegate.lastEntry());
        }
        
        @Override
        public final Map.Entry pollFirstEntry() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public final Map.Entry pollLastEntry() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public NavigableMap descendingMap() {
            final UnmodifiableNavigableMap descendingMap = this.descendingMap;
            return (descendingMap == null) ? (this.descendingMap = new UnmodifiableNavigableMap(this.delegate.descendingMap(), this)) : descendingMap;
        }
        
        @Override
        public Set keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }
        
        @Override
        public NavigableSet descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, true, o2, false);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o, false);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o, true);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(o, b, o2, b2));
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(o, b));
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(o, b));
        }
        
        @Override
        protected Map delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    static final class FilteredEntryBiMap extends FilteredEntryMap implements BiMap
    {
        private final BiMap inverse;
        
        private static Predicate inversePredicate(final Predicate predicate) {
            return new Predicate(predicate) {
                final Predicate val$forwardPredicate;
                
                public boolean apply(final Map.Entry entry) {
                    return this.val$forwardPredicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.apply((Map.Entry)o);
                }
            };
        }
        
        FilteredEntryBiMap(final BiMap biMap, final Predicate predicate) {
            super(biMap, predicate);
            this.inverse = new FilteredEntryBiMap(biMap.inverse(), inversePredicate(predicate), this);
        }
        
        private FilteredEntryBiMap(final BiMap biMap, final Predicate predicate, final BiMap inverse) {
            super(biMap, predicate);
            this.inverse = inverse;
        }
        
        BiMap unfiltered() {
            return (BiMap)this.unfiltered;
        }
        
        @Override
        public Object forcePut(@Nullable final Object o, @Nullable final Object o2) {
            Preconditions.checkArgument(this.apply(o, o2));
            return this.unfiltered().forcePut(o, o2);
        }
        
        @Override
        public BiMap inverse() {
            return this.inverse;
        }
        
        @Override
        public Set values() {
            return this.inverse.keySet();
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
    }
    
    static class FilteredEntryMap extends AbstractFilteredMap
    {
        final Set filteredEntrySet;
        
        FilteredEntryMap(final Map map, final Predicate predicate) {
            super(map, predicate);
            this.filteredEntrySet = Sets.filter(map.entrySet(), this.predicate);
        }
        
        protected Set createEntrySet() {
            return new EntrySet(null);
        }
        
        @Override
        Set createKeySet() {
            return new KeySet();
        }
        
        class KeySet extends Maps.KeySet
        {
            final FilteredEntryMap this$0;
            
            KeySet(final FilteredEntryMap this$0) {
                super(this.this$0 = this$0);
            }
            
            @Override
            public boolean remove(final Object o) {
                if (this.this$0.containsKey(o)) {
                    this.this$0.unfiltered.remove(o);
                    return true;
                }
                return false;
            }
            
            private boolean removeIf(final Predicate predicate) {
                return Iterables.removeIf(this.this$0.unfiltered.entrySet(), Predicates.and(this.this$0.predicate, Maps.keyPredicateOnEntries(predicate)));
            }
            
            @Override
            public boolean removeAll(final Collection collection) {
                return this.removeIf(Predicates.in(collection));
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                return this.removeIf(Predicates.not(Predicates.in(collection)));
            }
            
            @Override
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }
            
            @Override
            public Object[] toArray(final Object[] array) {
                return Lists.newArrayList(this.iterator()).toArray(array);
            }
        }
        
        private class EntrySet extends ForwardingSet
        {
            final FilteredEntryMap this$0;
            
            private EntrySet(final FilteredEntryMap this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            protected Set delegate() {
                return this.this$0.filteredEntrySet;
            }
            
            @Override
            public Iterator iterator() {
                return new TransformedIterator((Iterator)this.this$0.filteredEntrySet.iterator()) {
                    final EntrySet this$1;
                    
                    Map.Entry transform(final Map.Entry entry) {
                        return new ForwardingMapEntry(entry) {
                            final Map.Entry val$entry;
                            final Maps$FilteredEntryMap$EntrySet$1 this$2;
                            
                            @Override
                            protected Map.Entry delegate() {
                                return this.val$entry;
                            }
                            
                            @Override
                            public Object setValue(final Object value) {
                                Preconditions.checkArgument(this.this$2.this$1.this$0.apply(this.getKey(), value));
                                return super.setValue(value);
                            }
                            
                            @Override
                            protected Object delegate() {
                                return this.delegate();
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
            protected Collection delegate() {
                return this.delegate();
            }
            
            @Override
            protected Object delegate() {
                return this.delegate();
            }
            
            EntrySet(final FilteredEntryMap filteredEntryMap, final Maps$1 unmodifiableIterator) {
                this(filteredEntryMap);
            }
        }
    }
    
    private abstract static class AbstractFilteredMap extends ImprovedAbstractMap
    {
        final Map unfiltered;
        final Predicate predicate;
        
        AbstractFilteredMap(final Map unfiltered, final Predicate predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }
        
        boolean apply(@Nullable final Object o, @Nullable final Object o2) {
            return this.predicate.apply(Maps.immutableEntry(o, o2));
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            Preconditions.checkArgument(this.apply(o, o2));
            return this.unfiltered.put(o, o2);
        }
        
        @Override
        public void putAll(final Map map) {
            for (final Map.Entry<Object, V> entry : map.entrySet()) {
                Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }
        
        @Override
        public Object get(final Object o) {
            final Object value = this.unfiltered.get(o);
            return (value != null && this.apply(o, value)) ? value : null;
        }
        
        @Override
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }
        
        @Override
        public Object remove(final Object o) {
            return (o != 0) ? this.unfiltered.remove(o) : null;
        }
        
        @Override
        Collection createValues() {
            return new FilteredMapValues(this, this.unfiltered, this.predicate);
        }
    }
    
    private static final class FilteredMapValues extends Values
    {
        Map unfiltered;
        Predicate predicate;
        
        FilteredMapValues(final Map map, final Map unfiltered, final Predicate predicate) {
            super(map);
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }
        
        @Override
        public boolean remove(final Object o) {
            return Iterables.removeFirstMatching(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(Predicates.equalTo(o)))) != null;
        }
        
        private boolean removeIf(final Predicate predicate) {
            return Iterables.removeIf(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(predicate)));
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.removeIf(Predicates.in(collection));
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.removeIf(Predicates.not(Predicates.in(collection)));
        }
        
        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return Lists.newArrayList(this.iterator()).toArray(array);
        }
    }
    
    @GwtIncompatible("NavigableMap")
    private static class FilteredEntryNavigableMap extends AbstractNavigableMap
    {
        private final NavigableMap unfiltered;
        private final Predicate entryPredicate;
        private final Map filteredDelegate;
        
        FilteredEntryNavigableMap(final NavigableMap navigableMap, final Predicate entryPredicate) {
            this.unfiltered = (NavigableMap)Preconditions.checkNotNull(navigableMap);
            this.entryPredicate = entryPredicate;
            this.filteredDelegate = new FilteredEntryMap(navigableMap, entryPredicate);
        }
        
        @Override
        public Comparator comparator() {
            return this.unfiltered.comparator();
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            return new NavigableKeySet((NavigableMap)this) {
                final FilteredEntryNavigableMap this$0;
                
                @Override
                public boolean removeAll(final Collection collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.access$700(this.this$0).entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.access$600(this.this$0), Maps.keyPredicateOnEntries(Predicates.in(collection))));
                }
                
                @Override
                public boolean retainAll(final Collection collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.access$700(this.this$0).entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.access$600(this.this$0), Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection)))));
                }
            };
        }
        
        @Override
        public Collection values() {
            return new FilteredMapValues(this, this.unfiltered, this.entryPredicate);
        }
        
        @Override
        Iterator entryIterator() {
            return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
        }
        
        @Override
        Iterator descendingEntryIterator() {
            return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
        }
        
        @Override
        public int size() {
            return this.filteredDelegate.size();
        }
        
        @Nullable
        @Override
        public Object get(@Nullable final Object o) {
            return this.filteredDelegate.get(o);
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.filteredDelegate.containsKey(o);
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            return this.filteredDelegate.put(o, o2);
        }
        
        @Override
        public Object remove(@Nullable final Object o) {
            return this.filteredDelegate.remove(o);
        }
        
        @Override
        public void putAll(final Map map) {
            this.filteredDelegate.putAll(map);
        }
        
        @Override
        public void clear() {
            this.filteredDelegate.clear();
        }
        
        @Override
        public Set entrySet() {
            return this.filteredDelegate.entrySet();
        }
        
        @Override
        public Map.Entry pollFirstEntry() {
            return (Map.Entry)Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
        }
        
        @Override
        public Map.Entry pollLastEntry() {
            return (Map.Entry)Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
        }
        
        @Override
        public NavigableMap descendingMap() {
            return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Maps.filterEntries(this.unfiltered.subMap(o, b, o2, b2), this.entryPredicate);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return Maps.filterEntries(this.unfiltered.headMap(o, b), this.entryPredicate);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return Maps.filterEntries(this.unfiltered.tailMap(o, b), this.entryPredicate);
        }
        
        static Predicate access$600(final FilteredEntryNavigableMap filteredEntryNavigableMap) {
            return filteredEntryNavigableMap.entryPredicate;
        }
        
        static NavigableMap access$700(final FilteredEntryNavigableMap filteredEntryNavigableMap) {
            return filteredEntryNavigableMap.unfiltered;
        }
    }
    
    private static class FilteredEntrySortedMap extends FilteredEntryMap implements SortedMap
    {
        FilteredEntrySortedMap(final SortedMap sortedMap, final Predicate predicate) {
            super(sortedMap, predicate);
        }
        
        SortedMap sortedMap() {
            return (SortedMap)this.unfiltered;
        }
        
        @Override
        public SortedSet keySet() {
            return (SortedSet)super.keySet();
        }
        
        @Override
        SortedSet createKeySet() {
            return new SortedKeySet();
        }
        
        @Override
        public Comparator comparator() {
            return this.sortedMap().comparator();
        }
        
        @Override
        public Object firstKey() {
            return this.keySet().iterator().next();
        }
        
        @Override
        public Object lastKey() {
            SortedMap<Object, Object> sortedMap = (SortedMap<Object, Object>)this.sortedMap();
            Object lastKey;
            while (true) {
                lastKey = sortedMap.lastKey();
                if (this.apply(lastKey, this.unfiltered.get(lastKey))) {
                    break;
                }
                sortedMap = this.sortedMap().headMap(lastKey);
            }
            return lastKey;
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return new FilteredEntrySortedMap(this.sortedMap().headMap(o), this.predicate);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return new FilteredEntrySortedMap(this.sortedMap().subMap(o, o2), this.predicate);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return new FilteredEntrySortedMap(this.sortedMap().tailMap(o), this.predicate);
        }
        
        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        class SortedKeySet extends FilteredEntryMap.KeySet implements SortedSet
        {
            final FilteredEntrySortedMap this$0;
            
            SortedKeySet(final FilteredEntrySortedMap this$0) {
                this.this$0 = this$0.super();
            }
            
            @Override
            public Comparator comparator() {
                return this.this$0.sortedMap().comparator();
            }
            
            @Override
            public SortedSet subSet(final Object o, final Object o2) {
                return (SortedSet)this.this$0.subMap(o, o2).keySet();
            }
            
            @Override
            public SortedSet headSet(final Object o) {
                return (SortedSet)this.this$0.headMap(o).keySet();
            }
            
            @Override
            public SortedSet tailSet(final Object o) {
                return (SortedSet)this.this$0.tailMap(o).keySet();
            }
            
            @Override
            public Object first() {
                return this.this$0.firstKey();
            }
            
            @Override
            public Object last() {
                return this.this$0.lastKey();
            }
        }
    }
    
    private static class FilteredKeyMap extends AbstractFilteredMap
    {
        Predicate keyPredicate;
        
        FilteredKeyMap(final Map map, final Predicate keyPredicate, final Predicate predicate) {
            super(map, predicate);
            this.keyPredicate = keyPredicate;
        }
        
        protected Set createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), this.predicate);
        }
        
        @Override
        Set createKeySet() {
            return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.unfiltered.containsKey(o) && this.keyPredicate.apply(o);
        }
    }
    
    @GwtIncompatible("NavigableMap")
    private static class TransformedEntriesNavigableMap extends TransformedEntriesSortedMap implements NavigableMap
    {
        TransformedEntriesNavigableMap(final NavigableMap navigableMap, final EntryTransformer entryTransformer) {
            super(navigableMap, entryTransformer);
        }
        
        @Override
        public Map.Entry ceilingEntry(final Object o) {
            return this.transformEntry(this.fromMap().ceilingEntry(o));
        }
        
        @Override
        public Object ceilingKey(final Object o) {
            return this.fromMap().ceilingKey(o);
        }
        
        @Override
        public NavigableSet descendingKeySet() {
            return this.fromMap().descendingKeySet();
        }
        
        @Override
        public NavigableMap descendingMap() {
            return Maps.transformEntries(this.fromMap().descendingMap(), this.transformer);
        }
        
        @Override
        public Map.Entry firstEntry() {
            return this.transformEntry(this.fromMap().firstEntry());
        }
        
        @Override
        public Map.Entry floorEntry(final Object o) {
            return this.transformEntry(this.fromMap().floorEntry(o));
        }
        
        @Override
        public Object floorKey(final Object o) {
            return this.fromMap().floorKey(o);
        }
        
        @Override
        public NavigableMap headMap(final Object o) {
            return this.headMap(o, false);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return Maps.transformEntries(this.fromMap().headMap(o, b), this.transformer);
        }
        
        @Override
        public Map.Entry higherEntry(final Object o) {
            return this.transformEntry(this.fromMap().higherEntry(o));
        }
        
        @Override
        public Object higherKey(final Object o) {
            return this.fromMap().higherKey(o);
        }
        
        @Override
        public Map.Entry lastEntry() {
            return this.transformEntry(this.fromMap().lastEntry());
        }
        
        @Override
        public Map.Entry lowerEntry(final Object o) {
            return this.transformEntry(this.fromMap().lowerEntry(o));
        }
        
        @Override
        public Object lowerKey(final Object o) {
            return this.fromMap().lowerKey(o);
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            return this.fromMap().navigableKeySet();
        }
        
        @Override
        public Map.Entry pollFirstEntry() {
            return this.transformEntry(this.fromMap().pollFirstEntry());
        }
        
        @Override
        public Map.Entry pollLastEntry() {
            return this.transformEntry(this.fromMap().pollLastEntry());
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Maps.transformEntries(this.fromMap().subMap(o, b, o2, b2), this.transformer);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final Object o2) {
            return this.subMap(o, true, o2, false);
        }
        
        @Override
        public NavigableMap tailMap(final Object o) {
            return this.tailMap(o, true);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return Maps.transformEntries(this.fromMap().tailMap(o, b), this.transformer);
        }
        
        @Nullable
        private Map.Entry transformEntry(@Nullable final Map.Entry entry) {
            return (entry == null) ? null : Maps.transformEntry(this.transformer, entry);
        }
        
        @Override
        protected NavigableMap fromMap() {
            return (NavigableMap)super.fromMap();
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, o2);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o);
        }
        
        @Override
        protected SortedMap fromMap() {
            return this.fromMap();
        }
    }
    
    public interface EntryTransformer
    {
        Object transformEntry(@Nullable final Object p0, @Nullable final Object p1);
    }
    
    static class TransformedEntriesSortedMap extends TransformedEntriesMap implements SortedMap
    {
        protected SortedMap fromMap() {
            return (SortedMap)this.fromMap;
        }
        
        TransformedEntriesSortedMap(final SortedMap sortedMap, final EntryTransformer entryTransformer) {
            super(sortedMap, entryTransformer);
        }
        
        @Override
        public Comparator comparator() {
            return this.fromMap().comparator();
        }
        
        @Override
        public Object firstKey() {
            return this.fromMap().firstKey();
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return Maps.transformEntries(this.fromMap().headMap(o), this.transformer);
        }
        
        @Override
        public Object lastKey() {
            return this.fromMap().lastKey();
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return Maps.transformEntries(this.fromMap().subMap(o, o2), this.transformer);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return Maps.transformEntries(this.fromMap().tailMap(o), this.transformer);
        }
    }
    
    static class TransformedEntriesMap extends ImprovedAbstractMap
    {
        final Map fromMap;
        final EntryTransformer transformer;
        
        TransformedEntriesMap(final Map map, final EntryTransformer entryTransformer) {
            this.fromMap = (Map)Preconditions.checkNotNull(map);
            this.transformer = (EntryTransformer)Preconditions.checkNotNull(entryTransformer);
        }
        
        @Override
        public int size() {
            return this.fromMap.size();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.fromMap.containsKey(o);
        }
        
        @Override
        public Object get(final Object o) {
            final Object value = this.fromMap.get(o);
            return (value != null || this.fromMap.containsKey(o)) ? this.transformer.transformEntry(o, value) : null;
        }
        
        @Override
        public Object remove(final Object o) {
            return this.fromMap.containsKey(o) ? this.transformer.transformEntry(o, this.fromMap.remove(o)) : null;
        }
        
        @Override
        public void clear() {
            this.fromMap.clear();
        }
        
        @Override
        public Set keySet() {
            return this.fromMap.keySet();
        }
        
        protected Set createEntrySet() {
            return new EntrySet() {
                final TransformedEntriesMap this$0;
                
                @Override
                Map map() {
                    return this.this$0;
                }
                
                @Override
                public Iterator iterator() {
                    return Iterators.transform(this.this$0.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.this$0.transformer));
                }
            };
        }
    }
    
    private static class UnmodifiableBiMap extends ForwardingMap implements BiMap, Serializable
    {
        final Map unmodifiableMap;
        final BiMap delegate;
        BiMap inverse;
        transient Set values;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableBiMap(final BiMap delegate, @Nullable final BiMap inverse) {
            this.unmodifiableMap = Collections.unmodifiableMap((Map<?, ?>)delegate);
            this.delegate = delegate;
            this.inverse = inverse;
        }
        
        @Override
        protected Map delegate() {
            return this.unmodifiableMap;
        }
        
        @Override
        public Object forcePut(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public BiMap inverse() {
            final BiMap inverse = this.inverse;
            return (inverse == null) ? (this.inverse = new UnmodifiableBiMap(this.delegate.inverse(), this)) : inverse;
        }
        
        @Override
        public Set values() {
            final Set values = this.values;
            return (values == null) ? (this.values = Collections.unmodifiableSet((Set<?>)this.delegate.values())) : values;
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static final class BiMapConverter extends Converter implements Serializable
    {
        private final BiMap bimap;
        private static final long serialVersionUID = 0L;
        
        BiMapConverter(final BiMap biMap) {
            this.bimap = (BiMap)Preconditions.checkNotNull(biMap);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return convert(this.bimap, o);
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return convert(this.bimap.inverse(), o);
        }
        
        private static Object convert(final BiMap biMap, final Object o) {
            final Object value = biMap.get(o);
            Preconditions.checkArgument(value != null, "No non-null mapping present for input: %s", o);
            return value;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof BiMapConverter && this.bimap.equals(((BiMapConverter)o).bimap);
        }
        
        @Override
        public int hashCode() {
            return this.bimap.hashCode();
        }
        
        @Override
        public String toString() {
            return "Maps.asConverter(" + this.bimap + ")";
        }
    }
    
    static class UnmodifiableEntrySet extends UnmodifiableEntries implements Set
    {
        UnmodifiableEntrySet(final Set set) {
            super(set);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return Sets.equalsImpl(this, o);
        }
        
        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }
    
    static class UnmodifiableEntries extends ForwardingCollection
    {
        private final Collection entries;
        
        UnmodifiableEntries(final Collection entries) {
            this.entries = entries;
        }
        
        @Override
        protected Collection delegate() {
            return this.entries;
        }
        
        @Override
        public Iterator iterator() {
            return new UnmodifiableIterator(super.iterator()) {
                final Iterator val$delegate;
                final UnmodifiableEntries this$0;
                
                @Override
                public boolean hasNext() {
                    return this.val$delegate.hasNext();
                }
                
                @Override
                public Map.Entry next() {
                    return Maps.unmodifiableEntry(this.val$delegate.next());
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
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
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    @GwtIncompatible("NavigableMap")
    private static final class NavigableAsMapView extends AbstractNavigableMap
    {
        private final NavigableSet set;
        private final Function function;
        
        NavigableAsMapView(final NavigableSet set, final Function function) {
            this.set = (NavigableSet)Preconditions.checkNotNull(set);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Maps.asMap(this.set.subSet(o, b, o2, b2), this.function);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return Maps.asMap(this.set.headSet(o, b), this.function);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return Maps.asMap(this.set.tailSet(o, b), this.function);
        }
        
        @Override
        public Comparator comparator() {
            return this.set.comparator();
        }
        
        @Nullable
        @Override
        public Object get(@Nullable final Object o) {
            if (Collections2.safeContains(this.set, o)) {
                return this.function.apply(o);
            }
            return null;
        }
        
        @Override
        public void clear() {
            this.set.clear();
        }
        
        @Override
        Iterator entryIterator() {
            return Maps.asMapEntryIterator(this.set, this.function);
        }
        
        @Override
        Iterator descendingEntryIterator() {
            return this.descendingMap().entrySet().iterator();
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            return Maps.access$400(this.set);
        }
        
        @Override
        public int size() {
            return this.set.size();
        }
        
        @Override
        public NavigableMap descendingMap() {
            return Maps.asMap(this.set.descendingSet(), this.function);
        }
    }
    
    private static class SortedAsMapView extends AsMapView implements SortedMap
    {
        SortedAsMapView(final SortedSet set, final Function function) {
            super(set, function);
        }
        
        @Override
        SortedSet backingSet() {
            return (SortedSet)super.backingSet();
        }
        
        @Override
        public Comparator comparator() {
            return this.backingSet().comparator();
        }
        
        @Override
        public Set keySet() {
            return Maps.access$300(this.backingSet());
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return Maps.asMap(this.backingSet().subSet(o, o2), this.function);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return Maps.asMap(this.backingSet().headSet(o), this.function);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return Maps.asMap(this.backingSet().tailSet(o), this.function);
        }
        
        @Override
        public Object firstKey() {
            return this.backingSet().first();
        }
        
        @Override
        public Object lastKey() {
            return this.backingSet().last();
        }
        
        @Override
        Set backingSet() {
            return this.backingSet();
        }
    }
    
    private static class AsMapView extends ImprovedAbstractMap
    {
        private final Set set;
        final Function function;
        
        Set backingSet() {
            return this.set;
        }
        
        AsMapView(final Set set, final Function function) {
            this.set = (Set)Preconditions.checkNotNull(set);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        public Set createKeySet() {
            return Maps.access$200(this.backingSet());
        }
        
        @Override
        Collection createValues() {
            return Collections2.transform(this.set, this.function);
        }
        
        @Override
        public int size() {
            return this.backingSet().size();
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.backingSet().contains(o);
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            if (Collections2.safeContains(this.backingSet(), o)) {
                return this.function.apply(o);
            }
            return null;
        }
        
        @Override
        public Object remove(@Nullable final Object o) {
            if (this.backingSet().remove(o)) {
                return this.function.apply(o);
            }
            return null;
        }
        
        @Override
        public void clear() {
            this.backingSet().clear();
        }
        
        protected Set createEntrySet() {
            return new EntrySet() {
                final AsMapView this$0;
                
                @Override
                Map map() {
                    return this.this$0;
                }
                
                @Override
                public Iterator iterator() {
                    return Maps.asMapEntryIterator(this.this$0.backingSet(), this.this$0.function);
                }
            };
        }
    }
    
    static class SortedMapDifferenceImpl extends MapDifferenceImpl implements SortedMapDifference
    {
        SortedMapDifferenceImpl(final SortedMap sortedMap, final SortedMap sortedMap2, final SortedMap sortedMap3, final SortedMap sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }
        
        @Override
        public SortedMap entriesDiffering() {
            return (SortedMap)super.entriesDiffering();
        }
        
        @Override
        public SortedMap entriesInCommon() {
            return (SortedMap)super.entriesInCommon();
        }
        
        @Override
        public SortedMap entriesOnlyOnLeft() {
            return (SortedMap)super.entriesOnlyOnLeft();
        }
        
        @Override
        public SortedMap entriesOnlyOnRight() {
            return (SortedMap)super.entriesOnlyOnRight();
        }
        
        @Override
        public Map entriesDiffering() {
            return this.entriesDiffering();
        }
        
        @Override
        public Map entriesInCommon() {
            return this.entriesInCommon();
        }
        
        @Override
        public Map entriesOnlyOnRight() {
            return this.entriesOnlyOnRight();
        }
        
        @Override
        public Map entriesOnlyOnLeft() {
            return this.entriesOnlyOnLeft();
        }
    }
    
    static class MapDifferenceImpl implements MapDifference
    {
        final Map onlyOnLeft;
        final Map onlyOnRight;
        final Map onBoth;
        final Map differences;
        
        MapDifferenceImpl(final Map map, final Map map2, final Map map3, final Map map4) {
            this.onlyOnLeft = Maps.access$100(map);
            this.onlyOnRight = Maps.access$100(map2);
            this.onBoth = Maps.access$100(map3);
            this.differences = Maps.access$100(map4);
        }
        
        @Override
        public boolean areEqual() {
            return this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty();
        }
        
        @Override
        public Map entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }
        
        @Override
        public Map entriesOnlyOnRight() {
            return this.onlyOnRight;
        }
        
        @Override
        public Map entriesInCommon() {
            return this.onBoth;
        }
        
        @Override
        public Map entriesDiffering() {
            return this.differences;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof MapDifference) {
                final MapDifference mapDifference = (MapDifference)o;
                return this.entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) && this.entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) && this.entriesInCommon().equals(mapDifference.entriesInCommon()) && this.entriesDiffering().equals(mapDifference.entriesDiffering());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
        }
        
        @Override
        public String toString() {
            if (this.areEqual()) {
                return "equal";
            }
            final StringBuilder sb = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                sb.append(": only on left=").append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                sb.append(": only on right=").append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                sb.append(": value differences=").append(this.differences);
            }
            return sb.toString();
        }
    }
    
    static class ValueDifferenceImpl implements MapDifference.ValueDifference
    {
        private final Object left;
        private final Object right;
        
        static MapDifference.ValueDifference create(@Nullable final Object o, @Nullable final Object o2) {
            return new ValueDifferenceImpl(o, o2);
        }
        
        private ValueDifferenceImpl(@Nullable final Object left, @Nullable final Object right) {
            this.left = left;
            this.right = right;
        }
        
        @Override
        public Object leftValue() {
            return this.left;
        }
        
        @Override
        public Object rightValue() {
            return this.right;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof MapDifference.ValueDifference) {
                final MapDifference.ValueDifference valueDifference = (MapDifference.ValueDifference)o;
                return Objects.equal(this.left, valueDifference.leftValue()) && Objects.equal(this.right, valueDifference.rightValue());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }
        
        @Override
        public String toString() {
            return "(" + this.left + ", " + this.right + ")";
        }
    }
    
    private enum EntryFunction implements Function
    {
        KEY {
            @Nullable
            public Object apply(final Map.Entry entry) {
                return entry.getKey();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
        }, 
        VALUE {
            @Nullable
            public Object apply(final Map.Entry entry) {
                return entry.getValue();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
        };
        
        private static final EntryFunction[] $VALUES;
        
        private EntryFunction(final String s, final int n) {
        }
        
        EntryFunction(final String s, final int n, final Maps$1 unmodifiableIterator) {
            this(s, n);
        }
        
        static {
            $VALUES = new EntryFunction[] { EntryFunction.KEY, EntryFunction.VALUE };
        }
    }
}
