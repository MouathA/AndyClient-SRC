package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableSortedMap extends ImmutableSortedMapFauxverideShim implements NavigableMap
{
    private static final Comparator NATURAL_ORDER;
    private static final ImmutableSortedMap NATURAL_EMPTY_MAP;
    private transient ImmutableSortedMap descendingMap;
    private static final long serialVersionUID = 0L;
    
    static ImmutableSortedMap emptyMap(final Comparator comparator) {
        if (Ordering.natural().equals(comparator)) {
            return of();
        }
        return new EmptyImmutableSortedMap(comparator);
    }
    
    static ImmutableSortedMap fromSortedEntries(final Comparator comparator, final int n, final Map.Entry[] array) {
        if (n == 0) {
            return emptyMap(comparator);
        }
        final ImmutableList.Builder builder = ImmutableList.builder();
        final ImmutableList.Builder builder2 = ImmutableList.builder();
        while (0 < n) {
            final Map.Entry entry = array[0];
            builder.add(entry.getKey());
            builder2.add((Object)entry.getValue());
            int n2 = 0;
            ++n2;
        }
        return new RegularImmutableSortedMap(new RegularImmutableSortedSet(builder.build(), comparator), builder2.build());
    }
    
    static ImmutableSortedMap from(final ImmutableSortedSet set, final ImmutableList list) {
        if (set.isEmpty()) {
            return emptyMap(set.comparator());
        }
        return new RegularImmutableSortedMap((RegularImmutableSortedSet)set, list);
    }
    
    public static ImmutableSortedMap of() {
        return ImmutableSortedMap.NATURAL_EMPTY_MAP;
    }
    
    public static ImmutableSortedMap of(final Comparable comparable, final Object o) {
        return from(ImmutableSortedSet.of(comparable), ImmutableList.of(o));
    }
    
    public static ImmutableSortedMap of(final Comparable comparable, final Object o, final Comparable comparable2, final Object o2) {
        return fromEntries(Ordering.natural(), false, 2, ImmutableMap.entryOf(comparable, o), ImmutableMap.entryOf(comparable2, o2));
    }
    
    public static ImmutableSortedMap of(final Comparable comparable, final Object o, final Comparable comparable2, final Object o2, final Comparable comparable3, final Object o3) {
        return fromEntries(Ordering.natural(), false, 3, ImmutableMap.entryOf(comparable, o), ImmutableMap.entryOf(comparable2, o2), ImmutableMap.entryOf(comparable3, o3));
    }
    
    public static ImmutableSortedMap of(final Comparable comparable, final Object o, final Comparable comparable2, final Object o2, final Comparable comparable3, final Object o3, final Comparable comparable4, final Object o4) {
        return fromEntries(Ordering.natural(), false, 4, ImmutableMap.entryOf(comparable, o), ImmutableMap.entryOf(comparable2, o2), ImmutableMap.entryOf(comparable3, o3), ImmutableMap.entryOf(comparable4, o4));
    }
    
    public static ImmutableSortedMap of(final Comparable comparable, final Object o, final Comparable comparable2, final Object o2, final Comparable comparable3, final Object o3, final Comparable comparable4, final Object o4, final Comparable comparable5, final Object o5) {
        return fromEntries(Ordering.natural(), false, 5, ImmutableMap.entryOf(comparable, o), ImmutableMap.entryOf(comparable2, o2), ImmutableMap.entryOf(comparable3, o3), ImmutableMap.entryOf(comparable4, o4), ImmutableMap.entryOf(comparable5, o5));
    }
    
    public static ImmutableSortedMap copyOf(final Map map) {
        return copyOfInternal(map, Ordering.natural());
    }
    
    public static ImmutableSortedMap copyOf(final Map map, final Comparator comparator) {
        return copyOfInternal(map, (Comparator)Preconditions.checkNotNull(comparator));
    }
    
    public static ImmutableSortedMap copyOfSorted(final SortedMap sortedMap) {
        Comparator comparator = sortedMap.comparator();
        if (comparator == null) {
            comparator = ImmutableSortedMap.NATURAL_ORDER;
        }
        return copyOfInternal(sortedMap, comparator);
    }
    
    private static ImmutableSortedMap copyOfInternal(final Map map, final Comparator comparator) {
        if (map instanceof SortedMap) {
            final Comparator comparator2 = ((SortedMap)map).comparator();
            final boolean b = (comparator2 == null) ? (comparator == ImmutableSortedMap.NATURAL_ORDER) : comparator.equals(comparator2);
        }
        if (false && map instanceof ImmutableSortedMap) {
            final ImmutableSortedMap immutableSortedMap = (ImmutableSortedMap)map;
            if (!immutableSortedMap.isPartialView()) {
                return immutableSortedMap;
            }
        }
        final Map.Entry[] array = map.entrySet().toArray(new Map.Entry[0]);
        return fromEntries(comparator, false, array.length, (Map.Entry[])array);
    }
    
    static ImmutableSortedMap fromEntries(final Comparator comparator, final boolean b, final int n, final Map.Entry... array) {
        while (0 < n) {
            final Map.Entry entry = array[0];
            array[0] = ImmutableMap.entryOf(entry.getKey(), entry.getValue());
            int n2 = 0;
            ++n2;
        }
        if (!b) {
            sortEntries(comparator, n, array);
            validateEntries(n, array, comparator);
        }
        return fromSortedEntries(comparator, n, array);
    }
    
    private static void sortEntries(final Comparator comparator, final int n, final Map.Entry[] array) {
        Arrays.sort(array, 0, n, Ordering.from(comparator).onKeys());
    }
    
    private static void validateEntries(final int n, final Map.Entry[] array, final Comparator comparator) {
        while (1 < n) {
            ImmutableMap.checkNoConflict(comparator.compare(array[0].getKey(), array[1].getKey()) != 0, "key", array[0], array[1]);
            int n2 = 0;
            ++n2;
        }
    }
    
    public static Builder naturalOrder() {
        return new Builder(Ordering.natural());
    }
    
    public static Builder orderedBy(final Comparator comparator) {
        return new Builder(comparator);
    }
    
    public static Builder reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }
    
    ImmutableSortedMap() {
    }
    
    ImmutableSortedMap(final ImmutableSortedMap descendingMap) {
        this.descendingMap = descendingMap;
    }
    
    @Override
    public int size() {
        return this.values().size();
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.values().contains(o);
    }
    
    @Override
    boolean isPartialView() {
        return this.keySet().isPartialView() || this.values().isPartialView();
    }
    
    @Override
    public ImmutableSet entrySet() {
        return super.entrySet();
    }
    
    @Override
    public abstract ImmutableSortedSet keySet();
    
    @Override
    public abstract ImmutableCollection values();
    
    @Override
    public Comparator comparator() {
        return this.keySet().comparator();
    }
    
    @Override
    public Object firstKey() {
        return this.keySet().first();
    }
    
    @Override
    public Object lastKey() {
        return this.keySet().last();
    }
    
    @Override
    public ImmutableSortedMap headMap(final Object o) {
        return this.headMap(o, false);
    }
    
    @Override
    public abstract ImmutableSortedMap headMap(final Object p0, final boolean p1);
    
    @Override
    public ImmutableSortedMap subMap(final Object o, final Object o2) {
        return this.subMap(o, true, o2, false);
    }
    
    @Override
    public ImmutableSortedMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        Preconditions.checkArgument(this.comparator().compare(o, o2) <= 0, "expected fromKey <= toKey but %s > %s", o, o2);
        return this.headMap(o2, b2).tailMap(o, b);
    }
    
    @Override
    public ImmutableSortedMap tailMap(final Object o) {
        return this.tailMap(o, true);
    }
    
    @Override
    public abstract ImmutableSortedMap tailMap(final Object p0, final boolean p1);
    
    @Override
    public Map.Entry lowerEntry(final Object o) {
        return this.headMap(o, false).lastEntry();
    }
    
    @Override
    public Object lowerKey(final Object o) {
        return Maps.keyOrNull(this.lowerEntry(o));
    }
    
    @Override
    public Map.Entry floorEntry(final Object o) {
        return this.headMap(o, true).lastEntry();
    }
    
    @Override
    public Object floorKey(final Object o) {
        return Maps.keyOrNull(this.floorEntry(o));
    }
    
    @Override
    public Map.Entry ceilingEntry(final Object o) {
        return this.tailMap(o, true).firstEntry();
    }
    
    @Override
    public Object ceilingKey(final Object o) {
        return Maps.keyOrNull(this.ceilingEntry(o));
    }
    
    @Override
    public Map.Entry higherEntry(final Object o) {
        return this.tailMap(o, false).firstEntry();
    }
    
    @Override
    public Object higherKey(final Object o) {
        return Maps.keyOrNull(this.higherEntry(o));
    }
    
    @Override
    public Map.Entry firstEntry() {
        return this.isEmpty() ? null : this.entrySet().asList().get(0);
    }
    
    @Override
    public Map.Entry lastEntry() {
        return this.isEmpty() ? null : this.entrySet().asList().get(this.size() - 1);
    }
    
    @Deprecated
    @Override
    public final Map.Entry pollFirstEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Map.Entry pollLastEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ImmutableSortedMap descendingMap() {
        ImmutableSortedMap descendingMap = this.descendingMap;
        if (descendingMap == null) {
            final ImmutableSortedMap descendingMap2 = this.createDescendingMap();
            this.descendingMap = descendingMap2;
            descendingMap = descendingMap2;
        }
        return descendingMap;
    }
    
    abstract ImmutableSortedMap createDescendingMap();
    
    @Override
    public ImmutableSortedSet navigableKeySet() {
        return this.keySet();
    }
    
    @Override
    public ImmutableSortedSet descendingKeySet() {
        return this.keySet().descendingSet();
    }
    
    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    @Override
    public ImmutableSet keySet() {
        return this.keySet();
    }
    
    @Override
    public Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    public SortedMap tailMap(final Object o) {
        return this.tailMap(o);
    }
    
    @Override
    public SortedMap headMap(final Object o) {
        return this.headMap(o);
    }
    
    @Override
    public SortedMap subMap(final Object o, final Object o2) {
        return this.subMap(o, o2);
    }
    
    @Override
    public NavigableMap tailMap(final Object o, final boolean b) {
        return this.tailMap(o, b);
    }
    
    @Override
    public NavigableMap headMap(final Object o, final boolean b) {
        return this.headMap(o, b);
    }
    
    @Override
    public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.subMap(o, b, o2, b2);
    }
    
    @Override
    public NavigableSet descendingKeySet() {
        return this.descendingKeySet();
    }
    
    @Override
    public NavigableSet navigableKeySet() {
        return this.navigableKeySet();
    }
    
    @Override
    public NavigableMap descendingMap() {
        return this.descendingMap();
    }
    
    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_MAP = new EmptyImmutableSortedMap(ImmutableSortedMap.NATURAL_ORDER);
    }
    
    private static class SerializedForm extends ImmutableMap.SerializedForm
    {
        private final Comparator comparator;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableSortedMap immutableSortedMap) {
            super(immutableSortedMap);
            this.comparator = immutableSortedMap.comparator();
        }
        
        @Override
        Object readResolve() {
            return this.createMap(new ImmutableSortedMap.Builder(this.comparator));
        }
    }
    
    public static class Builder extends ImmutableMap.Builder
    {
        private final Comparator comparator;
        
        public Builder(final Comparator comparator) {
            this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
        }
        
        @Override
        public Builder put(final Object o, final Object o2) {
            super.put(o, o2);
            return this;
        }
        
        @Override
        public Builder put(final Map.Entry entry) {
            super.put(entry);
            return this;
        }
        
        @Override
        public Builder putAll(final Map map) {
            super.putAll(map);
            return this;
        }
        
        @Override
        public ImmutableSortedMap build() {
            return ImmutableSortedMap.fromEntries(this.comparator, false, this.size, (Map.Entry[])this.entries);
        }
        
        @Override
        public ImmutableMap build() {
            return this.build();
        }
        
        @Override
        public ImmutableMap.Builder putAll(final Map map) {
            return this.putAll(map);
        }
        
        @Override
        public ImmutableMap.Builder put(final Map.Entry entry) {
            return this.put(entry);
        }
        
        @Override
        public ImmutableMap.Builder put(final Object o, final Object o2) {
            return this.put(o, o2);
        }
    }
}
