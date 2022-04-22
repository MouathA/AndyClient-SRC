package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class Synchronized
{
    private Synchronized() {
    }
    
    private static Collection collection(final Collection collection, @Nullable final Object o) {
        return new SynchronizedCollection(collection, o, null);
    }
    
    @VisibleForTesting
    static Set set(final Set set, @Nullable final Object o) {
        return new SynchronizedSet(set, o);
    }
    
    private static SortedSet sortedSet(final SortedSet set, @Nullable final Object o) {
        return new SynchronizedSortedSet(set, o);
    }
    
    private static List list(final List list, @Nullable final Object o) {
        return (list instanceof RandomAccess) ? new SynchronizedRandomAccessList(list, o) : new SynchronizedList(list, o);
    }
    
    static Multiset multiset(final Multiset multiset, @Nullable final Object o) {
        if (multiset instanceof SynchronizedMultiset || multiset instanceof ImmutableMultiset) {
            return multiset;
        }
        return new SynchronizedMultiset(multiset, o);
    }
    
    static Multimap multimap(final Multimap multimap, @Nullable final Object o) {
        if (multimap instanceof SynchronizedMultimap || multimap instanceof ImmutableMultimap) {
            return multimap;
        }
        return new SynchronizedMultimap(multimap, o);
    }
    
    static ListMultimap listMultimap(final ListMultimap listMultimap, @Nullable final Object o) {
        if (listMultimap instanceof SynchronizedListMultimap || listMultimap instanceof ImmutableListMultimap) {
            return listMultimap;
        }
        return new SynchronizedListMultimap(listMultimap, o);
    }
    
    static SetMultimap setMultimap(final SetMultimap setMultimap, @Nullable final Object o) {
        if (setMultimap instanceof SynchronizedSetMultimap || setMultimap instanceof ImmutableSetMultimap) {
            return setMultimap;
        }
        return new SynchronizedSetMultimap(setMultimap, o);
    }
    
    static SortedSetMultimap sortedSetMultimap(final SortedSetMultimap sortedSetMultimap, @Nullable final Object o) {
        if (sortedSetMultimap instanceof SynchronizedSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new SynchronizedSortedSetMultimap(sortedSetMultimap, o);
    }
    
    private static Collection typePreservingCollection(final Collection collection, @Nullable final Object o) {
        if (collection instanceof SortedSet) {
            return sortedSet((SortedSet)collection, o);
        }
        if (collection instanceof Set) {
            return set((Set)collection, o);
        }
        if (collection instanceof List) {
            return list((List)collection, o);
        }
        return collection(collection, o);
    }
    
    private static Set typePreservingSet(final Set set, @Nullable final Object o) {
        if (set instanceof SortedSet) {
            return sortedSet((SortedSet)set, o);
        }
        return set(set, o);
    }
    
    @VisibleForTesting
    static Map map(final Map map, @Nullable final Object o) {
        return new SynchronizedMap(map, o);
    }
    
    static SortedMap sortedMap(final SortedMap sortedMap, @Nullable final Object o) {
        return new SynchronizedSortedMap(sortedMap, o);
    }
    
    static BiMap biMap(final BiMap biMap, @Nullable final Object o) {
        if (biMap instanceof SynchronizedBiMap || biMap instanceof ImmutableBiMap) {
            return biMap;
        }
        return new SynchronizedBiMap(biMap, o, null, null);
    }
    
    @GwtIncompatible("NavigableSet")
    static NavigableSet navigableSet(final NavigableSet set, @Nullable final Object o) {
        return new SynchronizedNavigableSet(set, o);
    }
    
    @GwtIncompatible("NavigableSet")
    static NavigableSet navigableSet(final NavigableSet set) {
        return navigableSet(set, null);
    }
    
    @GwtIncompatible("NavigableMap")
    static NavigableMap navigableMap(final NavigableMap navigableMap) {
        return navigableMap(navigableMap, null);
    }
    
    @GwtIncompatible("NavigableMap")
    static NavigableMap navigableMap(final NavigableMap navigableMap, @Nullable final Object o) {
        return new SynchronizedNavigableMap(navigableMap, o);
    }
    
    @GwtIncompatible("works but is needed only for NavigableMap")
    private static Map.Entry nullableSynchronizedEntry(@Nullable final Map.Entry entry, @Nullable final Object o) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, o);
    }
    
    static Queue queue(final Queue queue, @Nullable final Object o) {
        return (queue instanceof SynchronizedQueue) ? queue : new SynchronizedQueue(queue, o);
    }
    
    @GwtIncompatible("Deque")
    static Deque deque(final Deque deque, @Nullable final Object o) {
        return new SynchronizedDeque(deque, o);
    }
    
    static SortedSet access$100(final SortedSet set, final Object o) {
        return sortedSet(set, o);
    }
    
    static List access$200(final List list, final Object o) {
        return list(list, o);
    }
    
    static Set access$300(final Set set, final Object o) {
        return typePreservingSet(set, o);
    }
    
    static Collection access$400(final Collection collection, final Object o) {
        return typePreservingCollection(collection, o);
    }
    
    static Collection access$500(final Collection collection, final Object o) {
        return collection(collection, o);
    }
    
    static Map.Entry access$700(final Map.Entry entry, final Object o) {
        return nullableSynchronizedEntry(entry, o);
    }
    
    @GwtIncompatible("Deque")
    private static final class SynchronizedDeque extends SynchronizedQueue implements Deque
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedDeque(final Deque deque, @Nullable final Object o) {
            super(deque, o);
        }
        
        @Override
        Deque delegate() {
            return (Deque)super.delegate();
        }
        
        @Override
        public void addFirst(final Object o) {
            // monitorenter(mutex = this.mutex)
            this.delegate().addFirst(o);
        }
        // monitorexit(mutex)
        
        @Override
        public void addLast(final Object o) {
            // monitorenter(mutex = this.mutex)
            this.delegate().addLast(o);
        }
        // monitorexit(mutex)
        
        @Override
        public boolean offerFirst(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().offerFirst(o);
        }
        
        @Override
        public boolean offerLast(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().offerLast(o);
        }
        
        @Override
        public Object removeFirst() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeFirst();
        }
        
        @Override
        public Object removeLast() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeLast();
        }
        
        @Override
        public Object pollFirst() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().pollFirst();
        }
        
        @Override
        public Object pollLast() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().pollLast();
        }
        
        @Override
        public Object getFirst() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().getFirst();
        }
        
        @Override
        public Object getLast() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().getLast();
        }
        
        @Override
        public Object peekFirst() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().peekFirst();
        }
        
        @Override
        public Object peekLast() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().peekLast();
        }
        
        @Override
        public boolean removeFirstOccurrence(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeFirstOccurrence(o);
        }
        
        @Override
        public boolean removeLastOccurrence(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeLastOccurrence(o);
        }
        
        @Override
        public void push(final Object o) {
            // monitorenter(mutex = this.mutex)
            this.delegate().push(o);
        }
        // monitorexit(mutex)
        
        @Override
        public Object pop() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().pop();
        }
        
        @Override
        public Iterator descendingIterator() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().descendingIterator();
        }
        
        @Override
        Queue delegate() {
            return this.delegate();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedQueue extends SynchronizedCollection implements Queue
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedQueue(final Queue queue, @Nullable final Object o) {
            super(queue, o, null);
        }
        
        @Override
        Queue delegate() {
            return (Queue)super.delegate();
        }
        
        @Override
        public Object element() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().element();
        }
        
        @Override
        public boolean offer(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().offer(o);
        }
        
        @Override
        public Object peek() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().peek();
        }
        
        @Override
        public Object poll() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().poll();
        }
        
        @Override
        public Object remove() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    @VisibleForTesting
    static class SynchronizedCollection extends SynchronizedObject implements Collection
    {
        private static final long serialVersionUID = 0L;
        
        private SynchronizedCollection(final Collection collection, @Nullable final Object o) {
            super(collection, o);
        }
        
        @Override
        Collection delegate() {
            return (Collection)super.delegate();
        }
        
        @Override
        public boolean add(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().add(o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().addAll(collection);
        }
        
        @Override
        public void clear() {
            // monitorenter(mutex = this.mutex)
            this.delegate().clear();
        }
        // monitorexit(mutex)
        
        @Override
        public boolean contains(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().contains(o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsAll(collection);
        }
        
        @Override
        public boolean isEmpty() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().isEmpty();
        }
        
        @Override
        public Iterator iterator() {
            return this.delegate().iterator();
        }
        
        @Override
        public boolean remove(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove(o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().retainAll(collection);
        }
        
        @Override
        public int size() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().size();
        }
        
        @Override
        public Object[] toArray() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().toArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().toArray(array);
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
        
        SynchronizedCollection(final Collection collection, final Object o, final Synchronized$1 object) {
            this(collection, o);
        }
    }
    
    static class SynchronizedObject implements Serializable
    {
        final Object delegate;
        final Object mutex;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        SynchronizedObject(final Object o, @Nullable final Object o2) {
            this.delegate = Preconditions.checkNotNull(o);
            this.mutex = ((o2 == null) ? this : o2);
        }
        
        Object delegate() {
            return this.delegate;
        }
        
        @Override
        public String toString() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate.toString();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            // monitorenter(mutex = this.mutex)
            objectOutputStream.defaultWriteObject();
        }
        // monitorexit(mutex)
    }
    
    @GwtIncompatible("works but is needed only for NavigableMap")
    private static class SynchronizedEntry extends SynchronizedObject implements Map.Entry
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedEntry(final Map.Entry entry, @Nullable final Object o) {
            super(entry, o);
        }
        
        @Override
        Map.Entry delegate() {
            return (Map.Entry)super.delegate();
        }
        
        @Override
        public boolean equals(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        public Object getKey() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().getKey();
        }
        
        @Override
        public Object getValue() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().getValue();
        }
        
        @Override
        public Object setValue(final Object value) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().setValue(value);
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    @GwtIncompatible("NavigableMap")
    @VisibleForTesting
    static class SynchronizedNavigableMap extends SynchronizedSortedMap implements NavigableMap
    {
        transient NavigableSet descendingKeySet;
        transient NavigableMap descendingMap;
        transient NavigableSet navigableKeySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedNavigableMap(final NavigableMap navigableMap, @Nullable final Object o) {
            super(navigableMap, o);
        }
        
        @Override
        NavigableMap delegate() {
            return (NavigableMap)super.delegate();
        }
        
        @Override
        public Map.Entry ceilingEntry(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().ceilingEntry(o), this.mutex);
        }
        
        @Override
        public Object ceilingKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().ceilingKey(o);
        }
        
        @Override
        public NavigableSet descendingKeySet() {
            // monitorenter(mutex = this.mutex)
            if (this.descendingKeySet == null) {
                final NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().descendingKeySet(), this.mutex);
                this.descendingKeySet = navigableSet;
                // monitorexit(mutex)
                return navigableSet;
            }
            // monitorexit(mutex)
            return this.descendingKeySet;
        }
        
        @Override
        public NavigableMap descendingMap() {
            // monitorenter(mutex = this.mutex)
            if (this.descendingMap == null) {
                final NavigableMap navigableMap = Synchronized.navigableMap(this.delegate().descendingMap(), this.mutex);
                this.descendingMap = navigableMap;
                // monitorexit(mutex)
                return navigableMap;
            }
            // monitorexit(mutex)
            return this.descendingMap;
        }
        
        @Override
        public Map.Entry firstEntry() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().firstEntry(), this.mutex);
        }
        
        @Override
        public Map.Entry floorEntry(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().floorEntry(o), this.mutex);
        }
        
        @Override
        public Object floorKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().floorKey(o);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableMap(this.delegate().headMap(o, b), this.mutex);
        }
        
        @Override
        public Map.Entry higherEntry(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().higherEntry(o), this.mutex);
        }
        
        @Override
        public Object higherKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().higherKey(o);
        }
        
        @Override
        public Map.Entry lastEntry() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().lastEntry(), this.mutex);
        }
        
        @Override
        public Map.Entry lowerEntry(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().lowerEntry(o), this.mutex);
        }
        
        @Override
        public Object lowerKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().lowerKey(o);
        }
        
        @Override
        public Set keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            // monitorenter(mutex = this.mutex)
            if (this.navigableKeySet == null) {
                final NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().navigableKeySet(), this.mutex);
                this.navigableKeySet = navigableSet;
                // monitorexit(mutex)
                return navigableSet;
            }
            // monitorexit(mutex)
            return this.navigableKeySet;
        }
        
        @Override
        public Map.Entry pollFirstEntry() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().pollFirstEntry(), this.mutex);
        }
        
        @Override
        public Map.Entry pollLastEntry() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$700(this.delegate().pollLastEntry(), this.mutex);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableMap(this.delegate().subMap(o, b, o2, b2), this.mutex);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableMap(this.delegate().tailMap(o, b), this.mutex);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o, false);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, true, o2, false);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o, true);
        }
        
        @Override
        SortedMap delegate() {
            return this.delegate();
        }
        
        @Override
        Map delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    static class SynchronizedSortedMap extends SynchronizedMap implements SortedMap
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedMap(final SortedMap sortedMap, @Nullable final Object o) {
            super(sortedMap, o);
        }
        
        @Override
        SortedMap delegate() {
            return (SortedMap)super.delegate();
        }
        
        @Override
        public Comparator comparator() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().comparator();
        }
        
        @Override
        public Object firstKey() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().firstKey();
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.sortedMap(this.delegate().headMap(o), this.mutex);
        }
        
        @Override
        public Object lastKey() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().lastKey();
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.sortedMap(this.delegate().subMap(o, o2), this.mutex);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.sortedMap(this.delegate().tailMap(o), this.mutex);
        }
        
        @Override
        Map delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedMap extends SynchronizedObject implements Map
    {
        transient Set keySet;
        transient Collection values;
        transient Set entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedMap(final Map map, @Nullable final Object o) {
            super(map, o);
        }
        
        @Override
        Map delegate() {
            return (Map)super.delegate();
        }
        
        @Override
        public void clear() {
            // monitorenter(mutex = this.mutex)
            this.delegate().clear();
        }
        // monitorexit(mutex)
        
        @Override
        public boolean containsKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsKey(o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsValue(o);
        }
        
        @Override
        public Set entrySet() {
            // monitorenter(mutex = this.mutex)
            if (this.entrySet == null) {
                this.entrySet = Synchronized.set(this.delegate().entrySet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.entrySet;
        }
        
        @Override
        public Object get(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().get(o);
        }
        
        @Override
        public boolean isEmpty() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().isEmpty();
        }
        
        @Override
        public Set keySet() {
            // monitorenter(mutex = this.mutex)
            if (this.keySet == null) {
                this.keySet = Synchronized.set(this.delegate().keySet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.keySet;
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().put(o, o2);
        }
        
        @Override
        public void putAll(final Map map) {
            // monitorenter(mutex = this.mutex)
            this.delegate().putAll(map);
        }
        // monitorexit(mutex)
        
        @Override
        public Object remove(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove(o);
        }
        
        @Override
        public int size() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().size();
        }
        
        @Override
        public Collection values() {
            // monitorenter(mutex = this.mutex)
            if (this.values == null) {
                this.values = Synchronized.access$500(this.delegate().values(), this.mutex);
            }
            // monitorexit(mutex)
            return this.values;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    @GwtIncompatible("NavigableSet")
    @VisibleForTesting
    static class SynchronizedNavigableSet extends SynchronizedSortedSet implements NavigableSet
    {
        transient NavigableSet descendingSet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedNavigableSet(final NavigableSet set, @Nullable final Object o) {
            super(set, o);
        }
        
        @Override
        NavigableSet delegate() {
            return (NavigableSet)super.delegate();
        }
        
        @Override
        public Object ceiling(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().ceiling(o);
        }
        
        @Override
        public Iterator descendingIterator() {
            return this.delegate().descendingIterator();
        }
        
        @Override
        public NavigableSet descendingSet() {
            // monitorenter(mutex = this.mutex)
            if (this.descendingSet == null) {
                final NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().descendingSet(), this.mutex);
                this.descendingSet = navigableSet;
                // monitorexit(mutex)
                return navigableSet;
            }
            // monitorexit(mutex)
            return this.descendingSet;
        }
        
        @Override
        public Object floor(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().floor(o);
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableSet(this.delegate().headSet(o, b), this.mutex);
        }
        
        @Override
        public Object higher(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().higher(o);
        }
        
        @Override
        public Object lower(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().lower(o);
        }
        
        @Override
        public Object pollFirst() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().pollFirst();
        }
        
        @Override
        public Object pollLast() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().pollLast();
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableSet(this.delegate().subSet(o, b, o2, b2), this.mutex);
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.navigableSet(this.delegate().tailSet(o, b), this.mutex);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o, false);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, true, o2, false);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o, true);
        }
        
        @Override
        SortedSet delegate() {
            return this.delegate();
        }
        
        @Override
        Set delegate() {
            return this.delegate();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    static class SynchronizedSortedSet extends SynchronizedSet implements SortedSet
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedSet(final SortedSet set, @Nullable final Object o) {
            super(set, o);
        }
        
        @Override
        SortedSet delegate() {
            return (SortedSet)super.delegate();
        }
        
        @Override
        public Comparator comparator() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().comparator();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$100(this.delegate().subSet(o, o2), this.mutex);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$100(this.delegate().headSet(o), this.mutex);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$100(this.delegate().tailSet(o), this.mutex);
        }
        
        @Override
        public Object first() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().first();
        }
        
        @Override
        public Object last() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().last();
        }
        
        @Override
        Set delegate() {
            return this.delegate();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    static class SynchronizedSet extends SynchronizedCollection implements Set
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSet(final Set set, @Nullable final Object o) {
            super(set, o, null);
        }
        
        @Override
        Set delegate() {
            return (Set)super.delegate();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedAsMapValues extends SynchronizedCollection
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMapValues(final Collection collection, @Nullable final Object o) {
            super(collection, o, null);
        }
        
        @Override
        public Iterator iterator() {
            return new ForwardingIterator(super.iterator()) {
                final Iterator val$iterator;
                final SynchronizedAsMapValues this$0;
                
                @Override
                protected Iterator delegate() {
                    return this.val$iterator;
                }
                
                @Override
                public Collection next() {
                    return Synchronized.access$400((Collection)super.next(), this.this$0.mutex);
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
                
                @Override
                protected Object delegate() {
                    return this.delegate();
                }
            };
        }
    }
    
    private static class SynchronizedAsMap extends SynchronizedMap
    {
        transient Set asMapEntrySet;
        transient Collection asMapValues;
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMap(final Map map, @Nullable final Object o) {
            super(map, o);
        }
        
        @Override
        public Collection get(final Object o) {
            // monitorenter(mutex = this.mutex)
            final Collection collection = (Collection)super.get(o);
            // monitorexit(mutex)
            return (collection == null) ? null : Synchronized.access$400(collection, this.mutex);
        }
        
        @Override
        public Set entrySet() {
            // monitorenter(mutex = this.mutex)
            if (this.asMapEntrySet == null) {
                this.asMapEntrySet = new SynchronizedAsMapEntries(this.delegate().entrySet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.asMapEntrySet;
        }
        
        @Override
        public Collection values() {
            // monitorenter(mutex = this.mutex)
            if (this.asMapValues == null) {
                this.asMapValues = new SynchronizedAsMapValues(this.delegate().values(), this.mutex);
            }
            // monitorexit(mutex)
            return this.asMapValues;
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return this.values().contains(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
    }
    
    private static class SynchronizedAsMapEntries extends SynchronizedSet
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMapEntries(final Set set, @Nullable final Object o) {
            super(set, o);
        }
        
        @Override
        public Iterator iterator() {
            return new ForwardingIterator(super.iterator()) {
                final Iterator val$iterator;
                final SynchronizedAsMapEntries this$0;
                
                @Override
                protected Iterator delegate() {
                    return this.val$iterator;
                }
                
                @Override
                public Map.Entry next() {
                    return new ForwardingMapEntry((Map.Entry)super.next()) {
                        final Map.Entry val$entry;
                        final Synchronized$SynchronizedAsMapEntries$1 this$1;
                        
                        @Override
                        protected Map.Entry delegate() {
                            return this.val$entry;
                        }
                        
                        @Override
                        public Collection getValue() {
                            return Synchronized.access$400(this.val$entry.getValue(), this.this$1.this$0.mutex);
                        }
                        
                        @Override
                        public Object getValue() {
                            return this.getValue();
                        }
                        
                        @Override
                        protected Object delegate() {
                            return this.delegate();
                        }
                    };
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
                
                @Override
                protected Object delegate() {
                    return this.delegate();
                }
            };
        }
        
        @Override
        public Object[] toArray() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return ObjectArrays.toArrayImpl(this.delegate());
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return ObjectArrays.toArrayImpl(this.delegate(), array);
        }
        
        @Override
        public boolean contains(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Maps.containsEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Collections2.containsAllImpl(this.delegate(), collection);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Sets.equalsImpl(this.delegate(), o);
        }
        
        @Override
        public boolean remove(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Maps.removeEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Iterators.removeAll(this.delegate().iterator(), collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Iterators.retainAll(this.delegate().iterator(), collection);
        }
    }
    
    @VisibleForTesting
    static class SynchronizedBiMap extends SynchronizedMap implements BiMap, Serializable
    {
        private transient Set valueSet;
        private transient BiMap inverse;
        private static final long serialVersionUID = 0L;
        
        private SynchronizedBiMap(final BiMap biMap, @Nullable final Object o, @Nullable final BiMap inverse) {
            super(biMap, o);
            this.inverse = inverse;
        }
        
        @Override
        BiMap delegate() {
            return (BiMap)super.delegate();
        }
        
        @Override
        public Set values() {
            // monitorenter(mutex = this.mutex)
            if (this.valueSet == null) {
                this.valueSet = Synchronized.set(this.delegate().values(), this.mutex);
            }
            // monitorexit(mutex)
            return this.valueSet;
        }
        
        @Override
        public Object forcePut(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().forcePut(o, o2);
        }
        
        @Override
        public BiMap inverse() {
            // monitorenter(mutex = this.mutex)
            if (this.inverse == null) {
                this.inverse = new SynchronizedBiMap(this.delegate().inverse(), this.mutex, this);
            }
            // monitorexit(mutex)
            return this.inverse;
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        @Override
        Map delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
        
        SynchronizedBiMap(final BiMap biMap, final Object o, final BiMap biMap2, final Synchronized$1 object) {
            this(biMap, o, biMap2);
        }
    }
    
    private static class SynchronizedSortedSetMultimap extends SynchronizedSetMultimap implements SortedSetMultimap
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedSetMultimap(final SortedSetMultimap sortedSetMultimap, @Nullable final Object o) {
            super(sortedSetMultimap, o);
        }
        
        @Override
        SortedSetMultimap delegate() {
            return (SortedSetMultimap)super.delegate();
        }
        
        @Override
        public SortedSet get(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$100(this.delegate().get(o), this.mutex);
        }
        
        @Override
        public SortedSet removeAll(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeAll(o);
        }
        
        @Override
        public SortedSet replaceValues(final Object o, final Iterable iterable) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().replaceValues(o, iterable);
        }
        
        @Override
        public Comparator valueComparator() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
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
        SetMultimap delegate() {
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
        Multimap delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedSetMultimap extends SynchronizedMultimap implements SetMultimap
    {
        transient Set entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedSetMultimap(final SetMultimap setMultimap, @Nullable final Object o) {
            super(setMultimap, o);
        }
        
        @Override
        SetMultimap delegate() {
            return (SetMultimap)super.delegate();
        }
        
        @Override
        public Set get(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.set(this.delegate().get(o), this.mutex);
        }
        
        @Override
        public Set removeAll(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeAll(o);
        }
        
        @Override
        public Set replaceValues(final Object o, final Iterable iterable) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().replaceValues(o, iterable);
        }
        
        @Override
        public Set entries() {
            // monitorenter(mutex = this.mutex)
            if (this.entrySet == null) {
                this.entrySet = Synchronized.set(this.delegate().entries(), this.mutex);
            }
            // monitorexit(mutex)
            return this.entrySet;
        }
        
        @Override
        public Collection entries() {
            return this.entries();
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
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        Multimap delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedMultimap extends SynchronizedObject implements Multimap
    {
        transient Set keySet;
        transient Collection valuesCollection;
        transient Collection entries;
        transient Map asMap;
        transient Multiset keys;
        private static final long serialVersionUID = 0L;
        
        @Override
        Multimap delegate() {
            return (Multimap)super.delegate();
        }
        
        SynchronizedMultimap(final Multimap multimap, @Nullable final Object o) {
            super(multimap, o);
        }
        
        @Override
        public int size() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().size();
        }
        
        @Override
        public boolean isEmpty() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().isEmpty();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsKey(o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsValue(o);
        }
        
        @Override
        public boolean containsEntry(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().containsEntry(o, o2);
        }
        
        @Override
        public Collection get(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$400(this.delegate().get(o), this.mutex);
        }
        
        @Override
        public boolean put(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().put(o, o2);
        }
        
        @Override
        public boolean putAll(final Object o, final Iterable iterable) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().putAll(o, iterable);
        }
        
        @Override
        public boolean putAll(final Multimap multimap) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().putAll(multimap);
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().replaceValues(o, iterable);
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove(o, o2);
        }
        
        @Override
        public Collection removeAll(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeAll(o);
        }
        
        @Override
        public void clear() {
            // monitorenter(mutex = this.mutex)
            this.delegate().clear();
        }
        // monitorexit(mutex)
        
        @Override
        public Set keySet() {
            // monitorenter(mutex = this.mutex)
            if (this.keySet == null) {
                this.keySet = Synchronized.access$300(this.delegate().keySet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.keySet;
        }
        
        @Override
        public Collection values() {
            // monitorenter(mutex = this.mutex)
            if (this.valuesCollection == null) {
                this.valuesCollection = Synchronized.access$500(this.delegate().values(), this.mutex);
            }
            // monitorexit(mutex)
            return this.valuesCollection;
        }
        
        @Override
        public Collection entries() {
            // monitorenter(mutex = this.mutex)
            if (this.entries == null) {
                this.entries = Synchronized.access$400(this.delegate().entries(), this.mutex);
            }
            // monitorexit(mutex)
            return this.entries;
        }
        
        @Override
        public Map asMap() {
            // monitorenter(mutex = this.mutex)
            if (this.asMap == null) {
                this.asMap = new SynchronizedAsMap(this.delegate().asMap(), this.mutex);
            }
            // monitorexit(mutex)
            return this.asMap;
        }
        
        @Override
        public Multiset keys() {
            // monitorenter(mutex = this.mutex)
            if (this.keys == null) {
                this.keys = Synchronized.multiset(this.delegate().keys(), this.mutex);
            }
            // monitorexit(mutex)
            return this.keys;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedListMultimap extends SynchronizedMultimap implements ListMultimap
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedListMultimap(final ListMultimap listMultimap, @Nullable final Object o) {
            super(listMultimap, o);
        }
        
        @Override
        ListMultimap delegate() {
            return (ListMultimap)super.delegate();
        }
        
        @Override
        public List get(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$200(this.delegate().get(o), this.mutex);
        }
        
        @Override
        public List removeAll(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().removeAll(o);
        }
        
        @Override
        public List replaceValues(final Object o, final Iterable iterable) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().replaceValues(o, iterable);
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
        public Collection get(final Object o) {
            return this.get(o);
        }
        
        @Override
        Multimap delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedMultiset extends SynchronizedCollection implements Multiset
    {
        transient Set elementSet;
        transient Set entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedMultiset(final Multiset multiset, @Nullable final Object o) {
            super(multiset, o, null);
        }
        
        @Override
        Multiset delegate() {
            return (Multiset)super.delegate();
        }
        
        @Override
        public int count(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().count(o);
        }
        
        @Override
        public int add(final Object o, final int n) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().add(o, n);
        }
        
        @Override
        public int remove(final Object o, final int n) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove(o, n);
        }
        
        @Override
        public int setCount(final Object o, final int n) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().setCount(o, n);
        }
        
        @Override
        public boolean setCount(final Object o, final int n, final int n2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().setCount(o, n, n2);
        }
        
        @Override
        public Set elementSet() {
            // monitorenter(mutex = this.mutex)
            if (this.elementSet == null) {
                this.elementSet = Synchronized.access$300(this.delegate().elementSet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.elementSet;
        }
        
        @Override
        public Set entrySet() {
            // monitorenter(mutex = this.mutex)
            if (this.entrySet == null) {
                this.entrySet = Synchronized.access$300(this.delegate().entrySet(), this.mutex);
            }
            // monitorexit(mutex)
            return this.entrySet;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
    
    private static class SynchronizedRandomAccessList extends SynchronizedList implements RandomAccess
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedRandomAccessList(final List list, @Nullable final Object o) {
            super(list, o);
        }
    }
    
    private static class SynchronizedList extends SynchronizedCollection implements List
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedList(final List list, @Nullable final Object o) {
            super(list, o, null);
        }
        
        @Override
        List delegate() {
            return (List)super.delegate();
        }
        
        @Override
        public void add(final int n, final Object o) {
            // monitorenter(mutex = this.mutex)
            this.delegate().add(n, o);
        }
        // monitorexit(mutex)
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().addAll(n, collection);
        }
        
        @Override
        public Object get(final int n) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().get(n);
        }
        
        @Override
        public int indexOf(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().indexOf(o);
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().lastIndexOf(o);
        }
        
        @Override
        public ListIterator listIterator() {
            return this.delegate().listIterator();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return this.delegate().listIterator(n);
        }
        
        @Override
        public Object remove(final int n) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().remove(n);
        }
        
        @Override
        public Object set(final int n, final Object o) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().set(n, o);
        }
        
        @Override
        public List subList(final int n, final int n2) {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return Synchronized.access$200(this.delegate().subList(n, n2), this.mutex);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().equals(o);
        }
        
        @Override
        public int hashCode() {
            // monitorenter(mutex = this.mutex)
            // monitorexit(mutex)
            return this.delegate().hashCode();
        }
        
        @Override
        Collection delegate() {
            return this.delegate();
        }
        
        @Override
        Object delegate() {
            return this.delegate();
        }
    }
}
