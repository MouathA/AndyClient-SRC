package com.google.common.collect;

import java.io.*;
import com.google.common.base.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultimap extends AbstractMultimap implements Serializable
{
    private transient Map map;
    private transient int totalSize;
    private static final long serialVersionUID = 2447537837011683357L;
    
    protected AbstractMapBasedMultimap(final Map map) {
        Preconditions.checkArgument(map.isEmpty());
        this.map = map;
    }
    
    final void setMap(final Map map) {
        this.map = map;
        this.totalSize = 0;
        for (final Collection collection : map.values()) {
            Preconditions.checkArgument(!collection.isEmpty());
            this.totalSize += collection.size();
        }
    }
    
    Collection createUnmodifiableEmptyCollection() {
        return this.unmodifiableCollectionSubclass(this.createCollection());
    }
    
    abstract Collection createCollection();
    
    Collection createCollection(@Nullable final Object o) {
        return this.createCollection();
    }
    
    Map backingMap() {
        return this.map;
    }
    
    @Override
    public int size() {
        return this.totalSize;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean put(@Nullable final Object o, @Nullable final Object o2) {
        final Collection<Object> collection = this.map.get(o);
        if (collection == null) {
            final Collection collection2 = this.createCollection(o);
            if (collection2.add(o2)) {
                ++this.totalSize;
                this.map.put(o, collection2);
                return true;
            }
            throw new AssertionError((Object)"New Collection violated the Collection spec");
        }
        else {
            if (collection.add(o2)) {
                ++this.totalSize;
                return true;
            }
            return false;
        }
    }
    
    private Collection getOrCreateCollection(@Nullable final Object o) {
        Collection collection = this.map.get(o);
        if (collection == null) {
            collection = this.createCollection(o);
            this.map.put(o, collection);
        }
        return collection;
    }
    
    @Override
    public Collection replaceValues(@Nullable final Object o, final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return this.removeAll(o);
        }
        final Collection orCreateCollection = this.getOrCreateCollection(o);
        final Collection collection = this.createCollection();
        collection.addAll(orCreateCollection);
        this.totalSize -= orCreateCollection.size();
        orCreateCollection.clear();
        while (iterator.hasNext()) {
            if (orCreateCollection.add(iterator.next())) {
                ++this.totalSize;
            }
        }
        return this.unmodifiableCollectionSubclass(collection);
    }
    
    @Override
    public Collection removeAll(@Nullable final Object o) {
        final Collection collection = this.map.remove(o);
        if (collection == null) {
            return this.createUnmodifiableEmptyCollection();
        }
        final Collection collection2 = this.createCollection();
        collection2.addAll(collection);
        this.totalSize -= collection.size();
        collection.clear();
        return this.unmodifiableCollectionSubclass(collection2);
    }
    
    Collection unmodifiableCollectionSubclass(final Collection collection) {
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
    
    @Override
    public void clear() {
        final Iterator<Collection> iterator = this.map.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }
    
    @Override
    public Collection get(@Nullable final Object o) {
        Collection collection = this.map.get(o);
        if (collection == null) {
            collection = this.createCollection(o);
        }
        return this.wrapCollection(o, collection);
    }
    
    Collection wrapCollection(@Nullable final Object o, final Collection collection) {
        if (collection instanceof SortedSet) {
            return new WrappedSortedSet(o, (SortedSet)collection, null);
        }
        if (collection instanceof Set) {
            return new WrappedSet(o, (Set)collection);
        }
        if (collection instanceof List) {
            return this.wrapList(o, (List)collection, null);
        }
        return new WrappedCollection(o, collection, null);
    }
    
    private List wrapList(@Nullable final Object o, final List list, @Nullable final WrappedCollection collection) {
        return (list instanceof RandomAccess) ? new RandomAccessWrappedList(o, list, collection) : new WrappedList(o, list, collection);
    }
    
    private Iterator iteratorOrListIterator(final Collection collection) {
        return (collection instanceof List) ? ((List)collection).listIterator() : collection.iterator();
    }
    
    @Override
    Set createKeySet() {
        return (this.map instanceof SortedMap) ? new SortedKeySet((SortedMap)this.map) : new KeySet(this.map);
    }
    
    private int removeValuesForKey(final Object o) {
        final Collection collection = (Collection)Maps.safeRemove(this.map, o);
        if (collection != null) {
            collection.size();
            collection.clear();
            this.totalSize -= 0;
        }
        return 0;
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    Iterator valueIterator() {
        return new Itr() {
            final AbstractMapBasedMultimap this$0;
            
            @Override
            Object output(final Object o, final Object o2) {
                return o2;
            }
        };
    }
    
    @Override
    public Collection entries() {
        return super.entries();
    }
    
    @Override
    Iterator entryIterator() {
        return new Itr() {
            final AbstractMapBasedMultimap this$0;
            
            @Override
            Map.Entry output(final Object o, final Object o2) {
                return Maps.immutableEntry(o, o2);
            }
            
            @Override
            Object output(final Object o, final Object o2) {
                return this.output(o, o2);
            }
        };
    }
    
    @Override
    Map createAsMap() {
        return (this.map instanceof SortedMap) ? new SortedAsMap((SortedMap)this.map) : new AsMap(this.map);
    }
    
    static Map access$000(final AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.map;
    }
    
    static Iterator access$100(final AbstractMapBasedMultimap abstractMapBasedMultimap, final Collection collection) {
        return abstractMapBasedMultimap.iteratorOrListIterator(collection);
    }
    
    static int access$210(final AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.totalSize--;
    }
    
    static int access$208(final AbstractMapBasedMultimap abstractMapBasedMultimap) {
        return abstractMapBasedMultimap.totalSize++;
    }
    
    static int access$212(final AbstractMapBasedMultimap abstractMapBasedMultimap, final int n) {
        return abstractMapBasedMultimap.totalSize += n;
    }
    
    static int access$220(final AbstractMapBasedMultimap abstractMapBasedMultimap, final int n) {
        return abstractMapBasedMultimap.totalSize -= n;
    }
    
    static List access$300(final AbstractMapBasedMultimap abstractMapBasedMultimap, final Object o, final List list, final WrappedCollection collection) {
        return abstractMapBasedMultimap.wrapList(o, list, collection);
    }
    
    static int access$400(final AbstractMapBasedMultimap abstractMapBasedMultimap, final Object o) {
        return abstractMapBasedMultimap.removeValuesForKey(o);
    }
    
    @GwtIncompatible("NavigableAsMap")
    class NavigableAsMap extends SortedAsMap implements NavigableMap
    {
        final AbstractMapBasedMultimap this$0;
        
        NavigableAsMap(final AbstractMapBasedMultimap this$0, final NavigableMap navigableMap) {
            this.this$0 = this$0.super(navigableMap);
        }
        
        @Override
        NavigableMap sortedMap() {
            return (NavigableMap)super.sortedMap();
        }
        
        @Override
        public Map.Entry lowerEntry(final Object o) {
            final Map.Entry<Object, Object> lowerEntry = this.sortedMap().lowerEntry(o);
            return (lowerEntry == null) ? null : this.wrapEntry(lowerEntry);
        }
        
        @Override
        public Object lowerKey(final Object o) {
            return this.sortedMap().lowerKey(o);
        }
        
        @Override
        public Map.Entry floorEntry(final Object o) {
            final Map.Entry<Object, Object> floorEntry = this.sortedMap().floorEntry(o);
            return (floorEntry == null) ? null : this.wrapEntry(floorEntry);
        }
        
        @Override
        public Object floorKey(final Object o) {
            return this.sortedMap().floorKey(o);
        }
        
        @Override
        public Map.Entry ceilingEntry(final Object o) {
            final Map.Entry<Object, Object> ceilingEntry = this.sortedMap().ceilingEntry(o);
            return (ceilingEntry == null) ? null : this.wrapEntry(ceilingEntry);
        }
        
        @Override
        public Object ceilingKey(final Object o) {
            return this.sortedMap().ceilingKey(o);
        }
        
        @Override
        public Map.Entry higherEntry(final Object o) {
            final Map.Entry<Object, Object> higherEntry = this.sortedMap().higherEntry(o);
            return (higherEntry == null) ? null : this.wrapEntry(higherEntry);
        }
        
        @Override
        public Object higherKey(final Object o) {
            return this.sortedMap().higherKey(o);
        }
        
        @Override
        public Map.Entry firstEntry() {
            final Map.Entry firstEntry = this.sortedMap().firstEntry();
            return (firstEntry == null) ? null : this.wrapEntry(firstEntry);
        }
        
        @Override
        public Map.Entry lastEntry() {
            final Map.Entry lastEntry = this.sortedMap().lastEntry();
            return (lastEntry == null) ? null : this.wrapEntry(lastEntry);
        }
        
        @Override
        public Map.Entry pollFirstEntry() {
            return this.pollAsMapEntry(this.entrySet().iterator());
        }
        
        @Override
        public Map.Entry pollLastEntry() {
            return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
        }
        
        Map.Entry pollAsMapEntry(final Iterator iterator) {
            if (!iterator.hasNext()) {
                return null;
            }
            final Map.Entry<K, Collection> entry = iterator.next();
            final Collection collection = this.this$0.createCollection();
            collection.addAll(entry.getValue());
            iterator.remove();
            return Maps.immutableEntry(entry.getKey(), this.this$0.unmodifiableCollectionSubclass(collection));
        }
        
        @Override
        public NavigableMap descendingMap() {
            return this.this$0.new NavigableAsMap(this.sortedMap().descendingMap());
        }
        
        @Override
        public NavigableSet keySet() {
            return (NavigableSet)super.keySet();
        }
        
        @Override
        NavigableSet createKeySet() {
            return this.this$0.new NavigableKeySet(this.sortedMap());
        }
        
        @Override
        public NavigableSet navigableKeySet() {
            return this.keySet();
        }
        
        @Override
        public NavigableSet descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }
        
        @Override
        public NavigableMap subMap(final Object o, final Object o2) {
            return this.subMap(o, true, o2, false);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.this$0.new NavigableAsMap(this.sortedMap().subMap(o, b, o2, b2));
        }
        
        @Override
        public NavigableMap headMap(final Object o) {
            return this.headMap(o, false);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return this.this$0.new NavigableAsMap(this.sortedMap().headMap(o, b));
        }
        
        @Override
        public NavigableMap tailMap(final Object o) {
            return this.tailMap(o, true);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return this.this$0.new NavigableAsMap(this.sortedMap().tailMap(o, b));
        }
        
        @Override
        SortedSet createKeySet() {
            return this.createKeySet();
        }
        
        @Override
        public SortedSet keySet() {
            return this.keySet();
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
        SortedMap sortedMap() {
            return this.sortedMap();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
    }
    
    private class SortedAsMap extends AsMap implements SortedMap
    {
        SortedSet sortedKeySet;
        final AbstractMapBasedMultimap this$0;
        
        SortedAsMap(final AbstractMapBasedMultimap this$0, final SortedMap sortedMap) {
            this.this$0 = this$0.super(sortedMap);
        }
        
        SortedMap sortedMap() {
            return (SortedMap)this.submap;
        }
        
        @Override
        public Comparator comparator() {
            return this.sortedMap().comparator();
        }
        
        @Override
        public Object firstKey() {
            return this.sortedMap().firstKey();
        }
        
        @Override
        public Object lastKey() {
            return this.sortedMap().lastKey();
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.this$0.new SortedAsMap(this.sortedMap().headMap(o));
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.this$0.new SortedAsMap(this.sortedMap().subMap(o, o2));
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.this$0.new SortedAsMap(this.sortedMap().tailMap(o));
        }
        
        @Override
        public SortedSet keySet() {
            final SortedSet sortedKeySet = this.sortedKeySet;
            return (sortedKeySet == null) ? (this.sortedKeySet = this.createKeySet()) : sortedKeySet;
        }
        
        @Override
        SortedSet createKeySet() {
            return this.this$0.new SortedKeySet(this.sortedMap());
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
    }
    
    private class AsMap extends Maps.ImprovedAbstractMap
    {
        final transient Map submap;
        final AbstractMapBasedMultimap this$0;
        
        AsMap(final AbstractMapBasedMultimap this$0, final Map submap) {
            this.this$0 = this$0;
            this.submap = submap;
        }
        
        protected Set createEntrySet() {
            return new AsMapEntries();
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return Maps.safeContainsKey(this.submap, o);
        }
        
        @Override
        public Collection get(final Object o) {
            final Collection collection = (Collection)Maps.safeGet(this.submap, o);
            if (collection == null) {
                return null;
            }
            return this.this$0.wrapCollection(o, collection);
        }
        
        @Override
        public Set keySet() {
            return this.this$0.keySet();
        }
        
        @Override
        public int size() {
            return this.submap.size();
        }
        
        @Override
        public Collection remove(final Object o) {
            final Collection collection = this.submap.remove(o);
            if (collection == null) {
                return null;
            }
            final Collection collection2 = this.this$0.createCollection();
            collection2.addAll(collection);
            AbstractMapBasedMultimap.access$220(this.this$0, collection.size());
            collection.clear();
            return collection2;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return this == o || this.submap.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.submap.hashCode();
        }
        
        @Override
        public String toString() {
            return this.submap.toString();
        }
        
        @Override
        public void clear() {
            if (this.submap == AbstractMapBasedMultimap.access$000(this.this$0)) {
                this.this$0.clear();
            }
            else {
                Iterators.clear(new AsMapIterator());
            }
        }
        
        Map.Entry wrapEntry(final Map.Entry entry) {
            final Object key = entry.getKey();
            return Maps.immutableEntry(key, this.this$0.wrapCollection(key, (Collection)entry.getValue()));
        }
        
        @Override
        public Object remove(final Object o) {
            return this.remove(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        class AsMapIterator implements Iterator
        {
            final Iterator delegateIterator;
            Collection collection;
            final AsMap this$1;
            
            AsMapIterator(final AsMap this$1) {
                this.this$1 = this$1;
                this.delegateIterator = this.this$1.submap.entrySet().iterator();
            }
            
            @Override
            public boolean hasNext() {
                return this.delegateIterator.hasNext();
            }
            
            @Override
            public Map.Entry next() {
                final Map.Entry<K, Collection> entry = this.delegateIterator.next();
                this.collection = entry.getValue();
                return this.this$1.wrapEntry(entry);
            }
            
            @Override
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$220(this.this$1.this$0, this.collection.size());
                this.collection.clear();
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        }
        
        class AsMapEntries extends Maps.EntrySet
        {
            final AsMap this$1;
            
            AsMapEntries(final AsMap this$1) {
                this.this$1 = this$1;
            }
            
            @Override
            Map map() {
                return this.this$1;
            }
            
            @Override
            public Iterator iterator() {
                return this.this$1.new AsMapIterator();
            }
            
            @Override
            public boolean contains(final Object o) {
                return Collections2.safeContains(this.this$1.submap.entrySet(), o);
            }
            
            @Override
            public boolean remove(final Object o) {
                if (!this.contains(o)) {
                    return false;
                }
                AbstractMapBasedMultimap.access$400(this.this$1.this$0, ((Map.Entry)o).getKey());
                return true;
            }
        }
    }
    
    private class SortedKeySet extends KeySet implements SortedSet
    {
        final AbstractMapBasedMultimap this$0;
        
        SortedKeySet(final AbstractMapBasedMultimap this$0, final SortedMap sortedMap) {
            this.this$0 = this$0.super(sortedMap);
        }
        
        SortedMap sortedMap() {
            return (SortedMap)super.map();
        }
        
        @Override
        public Comparator comparator() {
            return this.sortedMap().comparator();
        }
        
        @Override
        public Object first() {
            return this.sortedMap().firstKey();
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.this$0.new SortedKeySet(this.sortedMap().headMap(o));
        }
        
        @Override
        public Object last() {
            return this.sortedMap().lastKey();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.this$0.new SortedKeySet(this.sortedMap().subMap(o, o2));
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.this$0.new SortedKeySet(this.sortedMap().tailMap(o));
        }
    }
    
    private class KeySet extends Maps.KeySet
    {
        final AbstractMapBasedMultimap this$0;
        
        KeySet(final AbstractMapBasedMultimap this$0, final Map map) {
            this.this$0 = this$0;
            super(map);
        }
        
        @Override
        public Iterator iterator() {
            return new Iterator((Iterator)this.map().entrySet().iterator()) {
                Map.Entry entry;
                final Iterator val$entryIterator;
                final KeySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.val$entryIterator.hasNext();
                }
                
                @Override
                public Object next() {
                    this.entry = this.val$entryIterator.next();
                    return this.entry.getKey();
                }
                
                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.entry != null);
                    final Collection collection = this.entry.getValue();
                    this.val$entryIterator.remove();
                    AbstractMapBasedMultimap.access$220(this.this$1.this$0, collection.size());
                    collection.clear();
                }
            };
        }
        
        @Override
        public boolean remove(final Object o) {
            final Collection collection = this.map().remove(o);
            if (collection != null) {
                collection.size();
                collection.clear();
                AbstractMapBasedMultimap.access$220(this.this$0, 0);
            }
            return 0 > 0;
        }
        
        @Override
        public void clear() {
            Iterators.clear(this.iterator());
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.map().keySet().containsAll(collection);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return this == o || this.map().keySet().equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.map().keySet().hashCode();
        }
    }
    
    @GwtIncompatible("NavigableSet")
    class NavigableKeySet extends SortedKeySet implements NavigableSet
    {
        final AbstractMapBasedMultimap this$0;
        
        NavigableKeySet(final AbstractMapBasedMultimap this$0, final NavigableMap navigableMap) {
            this.this$0 = this$0.super(navigableMap);
        }
        
        @Override
        NavigableMap sortedMap() {
            return (NavigableMap)super.sortedMap();
        }
        
        @Override
        public Object lower(final Object o) {
            return this.sortedMap().lowerKey(o);
        }
        
        @Override
        public Object floor(final Object o) {
            return this.sortedMap().floorKey(o);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return this.sortedMap().ceilingKey(o);
        }
        
        @Override
        public Object higher(final Object o) {
            return this.sortedMap().higherKey(o);
        }
        
        @Override
        public Object pollFirst() {
            return Iterators.pollNext(this.iterator());
        }
        
        @Override
        public Object pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }
        
        @Override
        public NavigableSet descendingSet() {
            return this.this$0.new NavigableKeySet(this.sortedMap().descendingMap());
        }
        
        @Override
        public Iterator descendingIterator() {
            return this.descendingSet().iterator();
        }
        
        @Override
        public NavigableSet headSet(final Object o) {
            return this.headSet(o, false);
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return this.this$0.new NavigableKeySet(this.sortedMap().headMap(o, b));
        }
        
        @Override
        public NavigableSet subSet(final Object o, final Object o2) {
            return this.subSet(o, true, o2, false);
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.this$0.new NavigableKeySet(this.sortedMap().subMap(o, b, o2, b2));
        }
        
        @Override
        public NavigableSet tailSet(final Object o) {
            return this.tailSet(o, true);
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return this.this$0.new NavigableKeySet(this.sortedMap().tailMap(o, b));
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, o2);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o);
        }
        
        @Override
        SortedMap sortedMap() {
            return this.sortedMap();
        }
    }
    
    private abstract class Itr implements Iterator
    {
        final Iterator keyIterator;
        Object key;
        Collection collection;
        Iterator valueIterator;
        final AbstractMapBasedMultimap this$0;
        
        Itr(final AbstractMapBasedMultimap this$0) {
            this.this$0 = this$0;
            this.keyIterator = AbstractMapBasedMultimap.access$000(this$0).entrySet().iterator();
            this.key = null;
            this.collection = null;
            this.valueIterator = Iterators.emptyModifiableIterator();
        }
        
        abstract Object output(final Object p0, final Object p1);
        
        @Override
        public boolean hasNext() {
            return this.keyIterator.hasNext() || this.valueIterator.hasNext();
        }
        
        @Override
        public Object next() {
            if (!this.valueIterator.hasNext()) {
                final Map.Entry<Object, V> entry = this.keyIterator.next();
                this.key = entry.getKey();
                this.collection = (Collection)entry.getValue();
                this.valueIterator = this.collection.iterator();
            }
            return this.output(this.key, this.valueIterator.next());
        }
        
        @Override
        public void remove() {
            this.valueIterator.remove();
            if (this.collection.isEmpty()) {
                this.keyIterator.remove();
            }
            AbstractMapBasedMultimap.access$210(this.this$0);
        }
    }
    
    private class RandomAccessWrappedList extends WrappedList implements RandomAccess
    {
        final AbstractMapBasedMultimap this$0;
        
        RandomAccessWrappedList(@Nullable final AbstractMapBasedMultimap this$0, final Object o, @Nullable final List list, final WrappedCollection collection) {
            this.this$0 = this$0.super(o, list, collection);
        }
    }
    
    private class WrappedCollection extends AbstractCollection
    {
        final Object key;
        Collection delegate;
        final WrappedCollection ancestor;
        final Collection ancestorDelegate;
        final AbstractMapBasedMultimap this$0;
        
        WrappedCollection(@Nullable final AbstractMapBasedMultimap this$0, final Object key, @Nullable final Collection delegate, final WrappedCollection ancestor) {
            this.this$0 = this$0;
            this.key = key;
            this.delegate = delegate;
            this.ancestor = ancestor;
            this.ancestorDelegate = ((ancestor == null) ? null : ancestor.getDelegate());
        }
        
        void refreshIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.refreshIfEmpty();
                if (this.ancestor.getDelegate() != this.ancestorDelegate) {
                    throw new ConcurrentModificationException();
                }
            }
            else if (this.delegate.isEmpty()) {
                final Collection delegate = AbstractMapBasedMultimap.access$000(this.this$0).get(this.key);
                if (delegate != null) {
                    this.delegate = delegate;
                }
            }
        }
        
        void removeIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.removeIfEmpty();
            }
            else if (this.delegate.isEmpty()) {
                AbstractMapBasedMultimap.access$000(this.this$0).remove(this.key);
            }
        }
        
        Object getKey() {
            return this.key;
        }
        
        void addToMap() {
            if (this.ancestor != null) {
                this.ancestor.addToMap();
            }
            else {
                AbstractMapBasedMultimap.access$000(this.this$0).put(this.key, this.delegate);
            }
        }
        
        @Override
        public int size() {
            this.refreshIfEmpty();
            return this.delegate.size();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o == this) {
                return true;
            }
            this.refreshIfEmpty();
            return this.delegate.equals(o);
        }
        
        @Override
        public int hashCode() {
            this.refreshIfEmpty();
            return this.delegate.hashCode();
        }
        
        @Override
        public String toString() {
            this.refreshIfEmpty();
            return this.delegate.toString();
        }
        
        Collection getDelegate() {
            return this.delegate;
        }
        
        @Override
        public Iterator iterator() {
            this.refreshIfEmpty();
            return new WrappedIterator();
        }
        
        @Override
        public boolean add(final Object o) {
            this.refreshIfEmpty();
            final boolean empty = this.delegate.isEmpty();
            final boolean add = this.delegate.add(o);
            if (add) {
                AbstractMapBasedMultimap.access$208(this.this$0);
                if (empty) {
                    this.addToMap();
                }
            }
            return add;
        }
        
        WrappedCollection getAncestor() {
            return this.ancestor;
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            final int size = this.size();
            final boolean addAll = this.delegate.addAll(collection);
            if (addAll) {
                AbstractMapBasedMultimap.access$212(this.this$0, this.delegate.size() - size);
                if (size == 0) {
                    this.addToMap();
                }
            }
            return addAll;
        }
        
        @Override
        public boolean contains(final Object o) {
            this.refreshIfEmpty();
            return this.delegate.contains(o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            this.refreshIfEmpty();
            return this.delegate.containsAll(collection);
        }
        
        @Override
        public void clear() {
            final int size = this.size();
            if (size == 0) {
                return;
            }
            this.delegate.clear();
            AbstractMapBasedMultimap.access$220(this.this$0, size);
            this.removeIfEmpty();
        }
        
        @Override
        public boolean remove(final Object o) {
            this.refreshIfEmpty();
            final boolean remove = this.delegate.remove(o);
            if (remove) {
                AbstractMapBasedMultimap.access$210(this.this$0);
                this.removeIfEmpty();
            }
            return remove;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            final int size = this.size();
            final boolean removeAll = this.delegate.removeAll(collection);
            if (removeAll) {
                AbstractMapBasedMultimap.access$212(this.this$0, this.delegate.size() - size);
                this.removeIfEmpty();
            }
            return removeAll;
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            Preconditions.checkNotNull(collection);
            final int size = this.size();
            final boolean retainAll = this.delegate.retainAll(collection);
            if (retainAll) {
                AbstractMapBasedMultimap.access$212(this.this$0, this.delegate.size() - size);
                this.removeIfEmpty();
            }
            return retainAll;
        }
        
        class WrappedIterator implements Iterator
        {
            final Iterator delegateIterator;
            final Collection originalDelegate;
            final WrappedCollection this$1;
            
            WrappedIterator(final WrappedCollection this$1) {
                this.this$1 = this$1;
                this.originalDelegate = this.this$1.delegate;
                this.delegateIterator = AbstractMapBasedMultimap.access$100(this$1.this$0, this$1.delegate);
            }
            
            WrappedIterator(final WrappedCollection this$1, final Iterator delegateIterator) {
                this.this$1 = this$1;
                this.originalDelegate = this.this$1.delegate;
                this.delegateIterator = delegateIterator;
            }
            
            void validateIterator() {
                this.this$1.refreshIfEmpty();
                if (this.this$1.delegate != this.originalDelegate) {
                    throw new ConcurrentModificationException();
                }
            }
            
            @Override
            public boolean hasNext() {
                this.validateIterator();
                return this.delegateIterator.hasNext();
            }
            
            @Override
            public Object next() {
                this.validateIterator();
                return this.delegateIterator.next();
            }
            
            @Override
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$210(this.this$1.this$0);
                this.this$1.removeIfEmpty();
            }
            
            Iterator getDelegateIterator() {
                this.validateIterator();
                return this.delegateIterator;
            }
        }
    }
    
    private class WrappedList extends WrappedCollection implements List
    {
        final AbstractMapBasedMultimap this$0;
        
        WrappedList(@Nullable final AbstractMapBasedMultimap this$0, final Object o, @Nullable final List list, final WrappedCollection collection) {
            this.this$0 = this$0.super(o, list, collection);
        }
        
        List getListDelegate() {
            return (List)this.getDelegate();
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            final int size = this.size();
            final boolean addAll = this.getListDelegate().addAll(n, collection);
            if (addAll) {
                AbstractMapBasedMultimap.access$212(this.this$0, this.getDelegate().size() - size);
                if (size == 0) {
                    this.addToMap();
                }
            }
            return addAll;
        }
        
        @Override
        public Object get(final int n) {
            this.refreshIfEmpty();
            return this.getListDelegate().get(n);
        }
        
        @Override
        public Object set(final int n, final Object o) {
            this.refreshIfEmpty();
            return this.getListDelegate().set(n, o);
        }
        
        @Override
        public void add(final int n, final Object o) {
            this.refreshIfEmpty();
            final boolean empty = this.getDelegate().isEmpty();
            this.getListDelegate().add(n, o);
            AbstractMapBasedMultimap.access$208(this.this$0);
            if (empty) {
                this.addToMap();
            }
        }
        
        @Override
        public Object remove(final int n) {
            this.refreshIfEmpty();
            final Object remove = this.getListDelegate().remove(n);
            AbstractMapBasedMultimap.access$210(this.this$0);
            this.removeIfEmpty();
            return remove;
        }
        
        @Override
        public int indexOf(final Object o) {
            this.refreshIfEmpty();
            return this.getListDelegate().indexOf(o);
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            this.refreshIfEmpty();
            return this.getListDelegate().lastIndexOf(o);
        }
        
        @Override
        public ListIterator listIterator() {
            this.refreshIfEmpty();
            return new WrappedListIterator();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            this.refreshIfEmpty();
            return new WrappedListIterator(n);
        }
        
        @Override
        public List subList(final int n, final int n2) {
            this.refreshIfEmpty();
            return AbstractMapBasedMultimap.access$300(this.this$0, this.getKey(), this.getListDelegate().subList(n, n2), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        private class WrappedListIterator extends WrappedIterator implements ListIterator
        {
            final WrappedList this$1;
            
            WrappedListIterator(final WrappedList this$1) {
                this.this$1 = this$1.super();
            }
            
            public WrappedListIterator(final WrappedList this$1, final int n) {
                this.this$1 = this$1.super(this$1.getListDelegate().listIterator(n));
            }
            
            private ListIterator getDelegateListIterator() {
                return (ListIterator)this.getDelegateIterator();
            }
            
            @Override
            public boolean hasPrevious() {
                return this.getDelegateListIterator().hasPrevious();
            }
            
            @Override
            public Object previous() {
                return this.getDelegateListIterator().previous();
            }
            
            @Override
            public int nextIndex() {
                return this.getDelegateListIterator().nextIndex();
            }
            
            @Override
            public int previousIndex() {
                return this.getDelegateListIterator().previousIndex();
            }
            
            @Override
            public void set(final Object o) {
                this.getDelegateListIterator().set(o);
            }
            
            @Override
            public void add(final Object o) {
                final boolean empty = this.this$1.isEmpty();
                this.getDelegateListIterator().add(o);
                AbstractMapBasedMultimap.access$208(this.this$1.this$0);
                if (empty) {
                    this.this$1.addToMap();
                }
            }
        }
    }
    
    @GwtIncompatible("NavigableSet")
    class WrappedNavigableSet extends WrappedSortedSet implements NavigableSet
    {
        final AbstractMapBasedMultimap this$0;
        
        WrappedNavigableSet(@Nullable final AbstractMapBasedMultimap this$0, final Object o, @Nullable final NavigableSet set, final WrappedCollection collection) {
            this.this$0 = this$0.super(o, set, collection);
        }
        
        @Override
        NavigableSet getSortedSetDelegate() {
            return (NavigableSet)super.getSortedSetDelegate();
        }
        
        @Override
        public Object lower(final Object o) {
            return this.getSortedSetDelegate().lower(o);
        }
        
        @Override
        public Object floor(final Object o) {
            return this.getSortedSetDelegate().floor(o);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return this.getSortedSetDelegate().ceiling(o);
        }
        
        @Override
        public Object higher(final Object o) {
            return this.getSortedSetDelegate().higher(o);
        }
        
        @Override
        public Object pollFirst() {
            return Iterators.pollNext(this.iterator());
        }
        
        @Override
        public Object pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }
        
        private NavigableSet wrap(final NavigableSet set) {
            return this.this$0.new WrappedNavigableSet(this.key, set, (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        @Override
        public NavigableSet descendingSet() {
            return this.wrap(this.getSortedSetDelegate().descendingSet());
        }
        
        @Override
        public Iterator descendingIterator() {
            return new WrappedIterator(this.getSortedSetDelegate().descendingIterator());
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.wrap(this.getSortedSetDelegate().subSet(o, b, o2, b2));
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return this.wrap(this.getSortedSetDelegate().headSet(o, b));
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return this.wrap(this.getSortedSetDelegate().tailSet(o, b));
        }
        
        @Override
        SortedSet getSortedSetDelegate() {
            return this.getSortedSetDelegate();
        }
    }
    
    private class WrappedSortedSet extends WrappedCollection implements SortedSet
    {
        final AbstractMapBasedMultimap this$0;
        
        WrappedSortedSet(@Nullable final AbstractMapBasedMultimap this$0, final Object o, @Nullable final SortedSet set, final WrappedCollection collection) {
            this.this$0 = this$0.super(o, set, collection);
        }
        
        SortedSet getSortedSetDelegate() {
            return (SortedSet)this.getDelegate();
        }
        
        @Override
        public Comparator comparator() {
            return this.getSortedSetDelegate().comparator();
        }
        
        @Override
        public Object first() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().first();
        }
        
        @Override
        public Object last() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().last();
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            this.refreshIfEmpty();
            return this.this$0.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().headSet(o), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            this.refreshIfEmpty();
            return this.this$0.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().subSet(o, o2), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            this.refreshIfEmpty();
            return this.this$0.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().tailSet(o), (this.getAncestor() == null) ? this : this.getAncestor());
        }
    }
    
    private class WrappedSet extends WrappedCollection implements Set
    {
        final AbstractMapBasedMultimap this$0;
        
        WrappedSet(@Nullable final AbstractMapBasedMultimap this$0, final Object o, final Set set) {
            this.this$0 = this$0.super(o, set, null);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            final int size = this.size();
            final boolean removeAllImpl = Sets.removeAllImpl((Set)this.delegate, collection);
            if (removeAllImpl) {
                AbstractMapBasedMultimap.access$212(this.this$0, this.delegate.size() - size);
                this.removeIfEmpty();
            }
            return removeAllImpl;
        }
    }
}
