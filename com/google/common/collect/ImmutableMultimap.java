package com.google.common.collect;

import java.io.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
public abstract class ImmutableMultimap extends AbstractMultimap implements Serializable
{
    final transient ImmutableMap map;
    final transient int size;
    private static final long serialVersionUID = 0L;
    
    public static ImmutableMultimap of() {
        return ImmutableListMultimap.of();
    }
    
    public static ImmutableMultimap of(final Object o, final Object o2) {
        return ImmutableListMultimap.of(o, o2);
    }
    
    public static ImmutableMultimap of(final Object o, final Object o2, final Object o3, final Object o4) {
        return ImmutableListMultimap.of(o, o2, o3, o4);
    }
    
    public static ImmutableMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        return ImmutableListMultimap.of(o, o2, o3, o4, o5, o6);
    }
    
    public static ImmutableMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        return ImmutableListMultimap.of(o, o2, o3, o4, o5, o6, o7, o8);
    }
    
    public static ImmutableMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        return ImmutableListMultimap.of(o, o2, o3, o4, o5, o6, o7, o8, o9, o10);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ImmutableMultimap copyOf(final Multimap multimap) {
        if (multimap instanceof ImmutableMultimap) {
            final ImmutableMultimap immutableMultimap = (ImmutableMultimap)multimap;
            if (!immutableMultimap.isPartialView()) {
                return immutableMultimap;
            }
        }
        return ImmutableListMultimap.copyOf(multimap);
    }
    
    ImmutableMultimap(final ImmutableMap map, final int size) {
        this.map = map;
        this.size = size;
    }
    
    @Deprecated
    @Override
    public ImmutableCollection removeAll(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public ImmutableCollection replaceValues(final Object o, final Iterable iterable) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public abstract ImmutableCollection get(final Object p0);
    
    public abstract ImmutableMultimap inverse();
    
    @Deprecated
    @Override
    public boolean put(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public boolean putAll(final Object o, final Iterable iterable) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public boolean putAll(final Multimap multimap) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public boolean remove(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    boolean isPartialView() {
        return this.map.isPartialView();
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return o != null && super.containsValue(o);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public ImmutableSet keySet() {
        return this.map.keySet();
    }
    
    @Override
    public ImmutableMap asMap() {
        return this.map;
    }
    
    @Override
    Map createAsMap() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableCollection entries() {
        return (ImmutableCollection)super.entries();
    }
    
    @Override
    ImmutableCollection createEntries() {
        return new EntryCollection(this);
    }
    
    @Override
    UnmodifiableIterator entryIterator() {
        return new Itr() {
            final ImmutableMultimap this$0;
            
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
    public ImmutableMultiset keys() {
        return (ImmutableMultiset)super.keys();
    }
    
    @Override
    ImmutableMultiset createKeys() {
        return new Keys();
    }
    
    @Override
    public ImmutableCollection values() {
        return (ImmutableCollection)super.values();
    }
    
    @Override
    ImmutableCollection createValues() {
        return new Values(this);
    }
    
    @Override
    UnmodifiableIterator valueIterator() {
        return new Itr() {
            final ImmutableMultimap this$0;
            
            @Override
            Object output(final Object o, final Object o2) {
                return o2;
            }
        };
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Map asMap() {
        return this.asMap();
    }
    
    @Override
    Iterator valueIterator() {
        return this.valueIterator();
    }
    
    @Override
    Collection createValues() {
        return this.createValues();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    Multiset createKeys() {
        return this.createKeys();
    }
    
    @Override
    public Multiset keys() {
        return this.keys();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    Iterator entryIterator() {
        return this.entryIterator();
    }
    
    @Override
    Collection createEntries() {
        return this.createEntries();
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
    public boolean containsEntry(final Object o, final Object o2) {
        return super.containsEntry(o, o2);
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    private static final class Values extends ImmutableCollection
    {
        private final transient ImmutableMultimap multimap;
        private static final long serialVersionUID = 0L;
        
        Values(final ImmutableMultimap multimap) {
            this.multimap = multimap;
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.multimap.containsValue(o);
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.multimap.valueIterator();
        }
        
        @GwtIncompatible("not present in emulated superclass")
        @Override
        int copyIntoArray(final Object[] array, int copyIntoArray) {
            final Iterator iterator = this.multimap.map.values().iterator();
            while (iterator.hasNext()) {
                copyIntoArray = iterator.next().copyIntoArray(array, copyIntoArray);
            }
            return copyIntoArray;
        }
        
        @Override
        public int size() {
            return this.multimap.size();
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    class Keys extends ImmutableMultiset
    {
        final ImmutableMultimap this$0;
        
        Keys(final ImmutableMultimap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.this$0.containsKey(o);
        }
        
        @Override
        public int count(@Nullable final Object o) {
            final Collection collection = (Collection)this.this$0.map.get(o);
            return (collection == null) ? 0 : collection.size();
        }
        
        @Override
        public Set elementSet() {
            return this.this$0.keySet();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        Multiset.Entry getEntry(final int n) {
            final Map.Entry<Object, V> entry = this.this$0.map.entrySet().asList().get(n);
            return Multisets.immutableEntry(entry.getKey(), ((Collection)entry.getValue()).size());
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
    }
    
    private abstract class Itr extends UnmodifiableIterator
    {
        final Iterator mapIterator;
        Object key;
        Iterator valueIterator;
        final ImmutableMultimap this$0;
        
        private Itr(final ImmutableMultimap this$0) {
            this.this$0 = this$0;
            this.mapIterator = this.this$0.asMap().entrySet().iterator();
            this.key = null;
            this.valueIterator = Iterators.emptyIterator();
        }
        
        abstract Object output(final Object p0, final Object p1);
        
        @Override
        public boolean hasNext() {
            return this.mapIterator.hasNext() || this.valueIterator.hasNext();
        }
        
        @Override
        public Object next() {
            if (!this.valueIterator.hasNext()) {
                final Map.Entry<Object, V> entry = this.mapIterator.next();
                this.key = entry.getKey();
                this.valueIterator = ((Collection)entry.getValue()).iterator();
            }
            return this.output(this.key, this.valueIterator.next());
        }
        
        Itr(final ImmutableMultimap immutableMultimap, final ImmutableMultimap$1 itr) {
            this(immutableMultimap);
        }
    }
    
    private static class EntryCollection extends ImmutableCollection
    {
        final ImmutableMultimap multimap;
        private static final long serialVersionUID = 0L;
        
        EntryCollection(final ImmutableMultimap multimap) {
            this.multimap = multimap;
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.multimap.entryIterator();
        }
        
        @Override
        boolean isPartialView() {
            return this.multimap.isPartialView();
        }
        
        @Override
        public int size() {
            return this.multimap.size();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    @GwtIncompatible("java serialization is not supported")
    static class FieldSettersHolder
    {
        static final Serialization.FieldSetter MAP_FIELD_SETTER;
        static final Serialization.FieldSetter SIZE_FIELD_SETTER;
        static final Serialization.FieldSetter EMPTY_SET_FIELD_SETTER;
        
        static {
            MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
            SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
            EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");
        }
    }
    
    public static class Builder
    {
        Multimap builderMultimap;
        Comparator keyComparator;
        Comparator valueComparator;
        
        public Builder() {
            this.builderMultimap = new BuilderMultimap();
        }
        
        public Builder put(final Object o, final Object o2) {
            CollectPreconditions.checkEntryNotNull(o, o2);
            this.builderMultimap.put(o, o2);
            return this;
        }
        
        public Builder put(final Map.Entry entry) {
            return this.put(entry.getKey(), entry.getValue());
        }
        
        public Builder putAll(final Object o, final Iterable iterable) {
            if (o == null) {
                throw new NullPointerException("null key in entry: null=" + Iterables.toString(iterable));
            }
            final Collection value = this.builderMultimap.get(o);
            for (final Object next : iterable) {
                CollectPreconditions.checkEntryNotNull(o, next);
                value.add(next);
            }
            return this;
        }
        
        public Builder putAll(final Object o, final Object... array) {
            return this.putAll(o, Arrays.asList(array));
        }
        
        public Builder putAll(final Multimap multimap) {
            for (final Map.Entry<Object, V> entry : multimap.asMap().entrySet()) {
                this.putAll(entry.getKey(), (Iterable)entry.getValue());
            }
            return this;
        }
        
        public Builder orderKeysBy(final Comparator comparator) {
            this.keyComparator = (Comparator)Preconditions.checkNotNull(comparator);
            return this;
        }
        
        public Builder orderValuesBy(final Comparator comparator) {
            this.valueComparator = (Comparator)Preconditions.checkNotNull(comparator);
            return this;
        }
        
        public ImmutableMultimap build() {
            if (this.valueComparator != null) {
                final Iterator<Collection> iterator = this.builderMultimap.asMap().values().iterator();
                while (iterator.hasNext()) {
                    Collections.sort((List<Object>)iterator.next(), this.valueComparator);
                }
            }
            if (this.keyComparator != null) {
                final BuilderMultimap builderMultimap = new BuilderMultimap();
                final ArrayList arrayList = Lists.newArrayList(this.builderMultimap.asMap().entrySet());
                Collections.sort((List<Object>)arrayList, Ordering.from(this.keyComparator).onKeys());
                for (final Map.Entry<Object, V> entry : arrayList) {
                    builderMultimap.putAll(entry.getKey(), (Iterable)entry.getValue());
                }
                this.builderMultimap = builderMultimap;
            }
            return ImmutableMultimap.copyOf(this.builderMultimap);
        }
    }
    
    private static class BuilderMultimap extends AbstractMapBasedMultimap
    {
        private static final long serialVersionUID = 0L;
        
        BuilderMultimap() {
            super(new LinkedHashMap());
        }
        
        @Override
        Collection createCollection() {
            return Lists.newArrayList();
        }
    }
}
