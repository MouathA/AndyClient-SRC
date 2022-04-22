package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;
import javax.annotation.*;

@Beta
@GwtCompatible
public final class MapConstraints
{
    private MapConstraints() {
    }
    
    public static MapConstraint notNull() {
        return NotNullMapConstraint.INSTANCE;
    }
    
    public static Map constrainedMap(final Map map, final MapConstraint mapConstraint) {
        return new ConstrainedMap(map, mapConstraint);
    }
    
    public static Multimap constrainedMultimap(final Multimap multimap, final MapConstraint mapConstraint) {
        return new ConstrainedMultimap(multimap, mapConstraint);
    }
    
    public static ListMultimap constrainedListMultimap(final ListMultimap listMultimap, final MapConstraint mapConstraint) {
        return new ConstrainedListMultimap(listMultimap, mapConstraint);
    }
    
    public static SetMultimap constrainedSetMultimap(final SetMultimap setMultimap, final MapConstraint mapConstraint) {
        return new ConstrainedSetMultimap(setMultimap, mapConstraint);
    }
    
    public static SortedSetMultimap constrainedSortedSetMultimap(final SortedSetMultimap sortedSetMultimap, final MapConstraint mapConstraint) {
        return new ConstrainedSortedSetMultimap(sortedSetMultimap, mapConstraint);
    }
    
    private static Map.Entry constrainedEntry(final Map.Entry entry, final MapConstraint mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry(entry, mapConstraint) {
            final Map.Entry val$entry;
            final MapConstraint val$constraint;
            
            @Override
            protected Map.Entry delegate() {
                return this.val$entry;
            }
            
            @Override
            public Object setValue(final Object value) {
                this.val$constraint.checkKeyValue(this.getKey(), value);
                return this.val$entry.setValue(value);
            }
            
            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }
    
    private static Map.Entry constrainedAsMapEntry(final Map.Entry entry, final MapConstraint mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry(entry, mapConstraint) {
            final Map.Entry val$entry;
            final MapConstraint val$constraint;
            
            @Override
            protected Map.Entry delegate() {
                return this.val$entry;
            }
            
            @Override
            public Collection getValue() {
                return Constraints.constrainedTypePreservingCollection(this.val$entry.getValue(), new Constraint() {
                    final MapConstraints$2 this$0;
                    
                    @Override
                    public Object checkElement(final Object o) {
                        this.this$0.val$constraint.checkKeyValue(this.this$0.getKey(), o);
                        return o;
                    }
                });
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
    
    private static Set constrainedAsMapEntries(final Set set, final MapConstraint mapConstraint) {
        return new ConstrainedAsMapEntries(set, mapConstraint);
    }
    
    private static Collection constrainedEntries(final Collection collection, final MapConstraint mapConstraint) {
        if (collection instanceof Set) {
            return constrainedEntrySet((Set)collection, mapConstraint);
        }
        return new ConstrainedEntries(collection, mapConstraint);
    }
    
    private static Set constrainedEntrySet(final Set set, final MapConstraint mapConstraint) {
        return new ConstrainedEntrySet(set, mapConstraint);
    }
    
    public static BiMap constrainedBiMap(final BiMap biMap, final MapConstraint mapConstraint) {
        return new ConstrainedBiMap(biMap, null, mapConstraint);
    }
    
    private static Collection checkValues(final Object o, final Iterable iterable, final MapConstraint mapConstraint) {
        final ArrayList arrayList = Lists.newArrayList(iterable);
        final Iterator<Object> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            mapConstraint.checkKeyValue(o, iterator.next());
        }
        return arrayList;
    }
    
    private static Map checkMap(final Map map, final MapConstraint mapConstraint) {
        final LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>(map);
        for (final Map.Entry<Object, V> entry : linkedHashMap.entrySet()) {
            mapConstraint.checkKeyValue(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }
    
    static Set access$000(final Set set, final MapConstraint mapConstraint) {
        return constrainedEntrySet(set, mapConstraint);
    }
    
    static Map access$100(final Map map, final MapConstraint mapConstraint) {
        return checkMap(map, mapConstraint);
    }
    
    static Set access$200(final Set set, final MapConstraint mapConstraint) {
        return constrainedAsMapEntries(set, mapConstraint);
    }
    
    static Collection access$300(final Collection collection, final MapConstraint mapConstraint) {
        return constrainedEntries(collection, mapConstraint);
    }
    
    static Collection access$400(final Object o, final Iterable iterable, final MapConstraint mapConstraint) {
        return checkValues(o, iterable, mapConstraint);
    }
    
    static Map.Entry access$500(final Map.Entry entry, final MapConstraint mapConstraint) {
        return constrainedEntry(entry, mapConstraint);
    }
    
    static Map.Entry access$700(final Map.Entry entry, final MapConstraint mapConstraint) {
        return constrainedAsMapEntry(entry, mapConstraint);
    }
    
    private static class ConstrainedSortedSetMultimap extends ConstrainedSetMultimap implements SortedSetMultimap
    {
        ConstrainedSortedSetMultimap(final SortedSetMultimap sortedSetMultimap, final MapConstraint mapConstraint) {
            super(sortedSetMultimap, mapConstraint);
        }
        
        @Override
        public SortedSet get(final Object o) {
            return (SortedSet)super.get(o);
        }
        
        @Override
        public SortedSet removeAll(final Object o) {
            return (SortedSet)super.removeAll(o);
        }
        
        @Override
        public SortedSet replaceValues(final Object o, final Iterable iterable) {
            return (SortedSet)super.replaceValues(o, iterable);
        }
        
        @Override
        public Comparator valueComparator() {
            return ((SortedSetMultimap)this.delegate()).valueComparator();
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
    }
    
    private static class ConstrainedSetMultimap extends ConstrainedMultimap implements SetMultimap
    {
        ConstrainedSetMultimap(final SetMultimap setMultimap, final MapConstraint mapConstraint) {
            super(setMultimap, mapConstraint);
        }
        
        @Override
        public Set get(final Object o) {
            return (Set)super.get(o);
        }
        
        @Override
        public Set entries() {
            return (Set)super.entries();
        }
        
        @Override
        public Set removeAll(final Object o) {
            return (Set)super.removeAll(o);
        }
        
        @Override
        public Set replaceValues(final Object o, final Iterable iterable) {
            return (Set)super.replaceValues(o, iterable);
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
        public Collection entries() {
            return this.entries();
        }
        
        @Override
        public Collection removeAll(final Object o) {
            return this.removeAll(o);
        }
    }
    
    private static class ConstrainedMultimap extends ForwardingMultimap implements Serializable
    {
        final MapConstraint constraint;
        final Multimap delegate;
        transient Collection entries;
        transient Map asMap;
        
        public ConstrainedMultimap(final Multimap multimap, final MapConstraint mapConstraint) {
            this.delegate = (Multimap)Preconditions.checkNotNull(multimap);
            this.constraint = (MapConstraint)Preconditions.checkNotNull(mapConstraint);
        }
        
        @Override
        protected Multimap delegate() {
            return this.delegate;
        }
        
        @Override
        public Map asMap() {
            Map asMap = this.asMap;
            if (asMap == null) {
                asMap = (this.asMap = new ForwardingMap(this.delegate.asMap()) {
                    Set entrySet;
                    Collection values;
                    final Map val$asMapDelegate;
                    final ConstrainedMultimap this$0;
                    
                    @Override
                    protected Map delegate() {
                        return this.val$asMapDelegate;
                    }
                    
                    @Override
                    public Set entrySet() {
                        Set entrySet = this.entrySet;
                        if (entrySet == null) {
                            entrySet = (this.entrySet = MapConstraints.access$200(this.val$asMapDelegate.entrySet(), this.this$0.constraint));
                        }
                        return entrySet;
                    }
                    
                    @Override
                    public Collection get(final Object o) {
                        final Collection value = this.this$0.get(o);
                        return value.isEmpty() ? null : value;
                    }
                    
                    @Override
                    public Collection values() {
                        Collection values = this.values;
                        if (values == null) {
                            values = (this.values = new ConstrainedAsMapValues(this.delegate().values(), this.entrySet()));
                        }
                        return values;
                    }
                    
                    @Override
                    public boolean containsValue(final Object o) {
                        return this.values().contains(o);
                    }
                    
                    @Override
                    public Object get(final Object o) {
                        return this.get(o);
                    }
                    
                    @Override
                    protected Object delegate() {
                        return this.delegate();
                    }
                });
            }
            return asMap;
        }
        
        @Override
        public Collection entries() {
            Collection entries = this.entries;
            if (entries == null) {
                entries = (this.entries = MapConstraints.access$300(this.delegate.entries(), this.constraint));
            }
            return entries;
        }
        
        @Override
        public Collection get(final Object o) {
            return Constraints.constrainedTypePreservingCollection(this.delegate.get(o), new Constraint(o) {
                final Object val$key;
                final ConstrainedMultimap this$0;
                
                @Override
                public Object checkElement(final Object o) {
                    this.this$0.constraint.checkKeyValue(this.val$key, o);
                    return o;
                }
            });
        }
        
        @Override
        public boolean put(final Object o, final Object o2) {
            this.constraint.checkKeyValue(o, o2);
            return this.delegate.put(o, o2);
        }
        
        @Override
        public boolean putAll(final Object o, final Iterable iterable) {
            return this.delegate.putAll(o, MapConstraints.access$400(o, iterable, this.constraint));
        }
        
        @Override
        public boolean putAll(final Multimap multimap) {
            for (final Map.Entry<Object, V> entry : multimap.entries()) {
                final boolean b = false | this.put(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        @Override
        public Collection replaceValues(final Object o, final Iterable iterable) {
            return this.delegate.replaceValues(o, MapConstraints.access$400(o, iterable, this.constraint));
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class ConstrainedAsMapValues extends ForwardingCollection
    {
        final Collection delegate;
        final Set entrySet;
        
        ConstrainedAsMapValues(final Collection delegate, final Set entrySet) {
            this.delegate = delegate;
            this.entrySet = entrySet;
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate;
        }
        
        @Override
        public Iterator iterator() {
            return new Iterator((Iterator)this.entrySet.iterator()) {
                final Iterator val$iterator;
                final ConstrainedAsMapValues this$0;
                
                @Override
                public boolean hasNext() {
                    return this.val$iterator.hasNext();
                }
                
                @Override
                public Collection next() {
                    return this.val$iterator.next().getValue();
                }
                
                @Override
                public void remove() {
                    this.val$iterator.remove();
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
        public boolean contains(final Object o) {
            return this.standardContains(o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.standardContainsAll(collection);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.standardRemove(o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.standardRemoveAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.standardRetainAll(collection);
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class ConstrainedListMultimap extends ConstrainedMultimap implements ListMultimap
    {
        ConstrainedListMultimap(final ListMultimap listMultimap, final MapConstraint mapConstraint) {
            super(listMultimap, mapConstraint);
        }
        
        @Override
        public List get(final Object o) {
            return (List)super.get(o);
        }
        
        @Override
        public List removeAll(final Object o) {
            return (List)super.removeAll(o);
        }
        
        @Override
        public List replaceValues(final Object o, final Iterable iterable) {
            return (List)super.replaceValues(o, iterable);
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
    
    static class ConstrainedAsMapEntries extends ForwardingSet
    {
        private final MapConstraint constraint;
        private final Set entries;
        
        ConstrainedAsMapEntries(final Set entries, final MapConstraint constraint) {
            this.entries = entries;
            this.constraint = constraint;
        }
        
        @Override
        protected Set delegate() {
            return this.entries;
        }
        
        @Override
        public Iterator iterator() {
            return new ForwardingIterator((Iterator)this.entries.iterator()) {
                final Iterator val$iterator;
                final ConstrainedAsMapEntries this$0;
                
                @Override
                public Map.Entry next() {
                    return MapConstraints.access$700(this.val$iterator.next(), ConstrainedAsMapEntries.access$600(this.this$0));
                }
                
                @Override
                protected Iterator delegate() {
                    return this.val$iterator;
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
            return this.standardToArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.standardToArray(array);
        }
        
        @Override
        public boolean contains(final Object o) {
            return Maps.containsEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.standardContainsAll(collection);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return this.standardEquals(o);
        }
        
        @Override
        public int hashCode() {
            return this.standardHashCode();
        }
        
        @Override
        public boolean remove(final Object o) {
            return Maps.removeEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.standardRemoveAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.standardRetainAll(collection);
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        static MapConstraint access$600(final ConstrainedAsMapEntries constrainedAsMapEntries) {
            return constrainedAsMapEntries.constraint;
        }
    }
    
    static class ConstrainedEntrySet extends ConstrainedEntries implements Set
    {
        ConstrainedEntrySet(final Set set, final MapConstraint mapConstraint) {
            super(set, mapConstraint);
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
    
    private static class ConstrainedEntries extends ForwardingCollection
    {
        final MapConstraint constraint;
        final Collection entries;
        
        ConstrainedEntries(final Collection entries, final MapConstraint constraint) {
            this.entries = entries;
            this.constraint = constraint;
        }
        
        @Override
        protected Collection delegate() {
            return this.entries;
        }
        
        @Override
        public Iterator iterator() {
            return new ForwardingIterator((Iterator)this.entries.iterator()) {
                final Iterator val$iterator;
                final ConstrainedEntries this$0;
                
                @Override
                public Map.Entry next() {
                    return MapConstraints.access$500(this.val$iterator.next(), this.this$0.constraint);
                }
                
                @Override
                protected Iterator delegate() {
                    return this.val$iterator;
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
            return this.standardToArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.standardToArray(array);
        }
        
        @Override
        public boolean contains(final Object o) {
            return Maps.containsEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.standardContainsAll(collection);
        }
        
        @Override
        public boolean remove(final Object o) {
            return Maps.removeEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.standardRemoveAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.standardRetainAll(collection);
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class InverseConstraint implements MapConstraint
    {
        final MapConstraint constraint;
        
        public InverseConstraint(final MapConstraint mapConstraint) {
            this.constraint = (MapConstraint)Preconditions.checkNotNull(mapConstraint);
        }
        
        @Override
        public void checkKeyValue(final Object o, final Object o2) {
            this.constraint.checkKeyValue(o2, o);
        }
    }
    
    private static class ConstrainedBiMap extends ConstrainedMap implements BiMap
    {
        BiMap inverse;
        
        ConstrainedBiMap(final BiMap biMap, @Nullable final BiMap inverse, final MapConstraint mapConstraint) {
            super(biMap, mapConstraint);
            this.inverse = inverse;
        }
        
        @Override
        protected BiMap delegate() {
            return (BiMap)super.delegate();
        }
        
        @Override
        public Object forcePut(final Object o, final Object o2) {
            this.constraint.checkKeyValue(o, o2);
            return this.delegate().forcePut(o, o2);
        }
        
        @Override
        public BiMap inverse() {
            if (this.inverse == null) {
                this.inverse = new ConstrainedBiMap(this.delegate().inverse(), this, new InverseConstraint(this.constraint));
            }
            return this.inverse;
        }
        
        @Override
        public Set values() {
            return this.delegate().values();
        }
        
        @Override
        protected Map delegate() {
            return this.delegate();
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
    
    static class ConstrainedMap extends ForwardingMap
    {
        private final Map delegate;
        final MapConstraint constraint;
        private transient Set entrySet;
        
        ConstrainedMap(final Map map, final MapConstraint mapConstraint) {
            this.delegate = (Map)Preconditions.checkNotNull(map);
            this.constraint = (MapConstraint)Preconditions.checkNotNull(mapConstraint);
        }
        
        @Override
        protected Map delegate() {
            return this.delegate;
        }
        
        @Override
        public Set entrySet() {
            Set entrySet = this.entrySet;
            if (entrySet == null) {
                entrySet = (this.entrySet = MapConstraints.access$000(this.delegate.entrySet(), this.constraint));
            }
            return entrySet;
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            this.constraint.checkKeyValue(o, o2);
            return this.delegate.put(o, o2);
        }
        
        @Override
        public void putAll(final Map map) {
            this.delegate.putAll(MapConstraints.access$100(map, this.constraint));
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private enum NotNullMapConstraint implements MapConstraint
    {
        INSTANCE("INSTANCE", 0);
        
        private static final NotNullMapConstraint[] $VALUES;
        
        private NotNullMapConstraint(final String s, final int n) {
        }
        
        @Override
        public void checkKeyValue(final Object o, final Object o2) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o2);
        }
        
        @Override
        public String toString() {
            return "Not null";
        }
        
        static {
            $VALUES = new NotNullMapConstraint[] { NotNullMapConstraint.INSTANCE };
        }
    }
}
