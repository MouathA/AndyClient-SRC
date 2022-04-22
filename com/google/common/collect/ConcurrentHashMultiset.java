package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.concurrent.atomic.*;
import com.google.common.primitives.*;
import com.google.common.math.*;
import java.util.*;
import java.io.*;

public final class ConcurrentHashMultiset extends AbstractMultiset implements Serializable
{
    private final transient ConcurrentMap countMap;
    private transient EntrySet entrySet;
    private static final long serialVersionUID = 1L;
    
    public static ConcurrentHashMultiset create() {
        return new ConcurrentHashMultiset(new ConcurrentHashMap());
    }
    
    public static ConcurrentHashMultiset create(final Iterable iterable) {
        final ConcurrentHashMultiset create = create();
        Iterables.addAll(create, iterable);
        return create;
    }
    
    @Beta
    public static ConcurrentHashMultiset create(final MapMaker mapMaker) {
        return new ConcurrentHashMultiset(mapMaker.makeMap());
    }
    
    @VisibleForTesting
    ConcurrentHashMultiset(final ConcurrentMap countMap) {
        Preconditions.checkArgument(countMap.isEmpty());
        this.countMap = countMap;
    }
    
    @Override
    public int count(@Nullable final Object o) {
        final AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
        return (atomicInteger == null) ? 0 : atomicInteger.get();
    }
    
    @Override
    public int size() {
        long n = 0L;
        final Iterator iterator = this.countMap.values().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().get();
        }
        return Ints.saturatedCast(n);
    }
    
    @Override
    public Object[] toArray() {
        return this.snapshot().toArray();
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        return this.snapshot().toArray(array);
    }
    
    private List snapshot() {
        final ArrayList arrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(this.size());
        for (final Multiset.Entry entry : this.entrySet()) {
            final Object element = entry.getElement();
            for (int i = entry.getCount(); i > 0; --i) {
                arrayListWithExpectedSize.add(element);
            }
        }
        return arrayListWithExpectedSize;
    }
    
    @Override
    public int add(final Object o, final int n) {
        Preconditions.checkNotNull(o);
        if (n == 0) {
            return this.count(o);
        }
        Preconditions.checkArgument(n > 0, "Invalid occurrences: %s", n);
        while (true) {
            AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
            if (atomicInteger == null) {
                atomicInteger = this.countMap.putIfAbsent(o, new AtomicInteger(n));
                if (atomicInteger == null) {
                    return 0;
                }
            }
            while (true) {
                final int value = atomicInteger.get();
                if (value != 0) {
                    if (atomicInteger.compareAndSet(value, IntMath.checkedAdd(value, n))) {
                        return value;
                    }
                    continue;
                }
                else {
                    final AtomicInteger atomicInteger2 = new AtomicInteger(n);
                    if (this.countMap.putIfAbsent(o, atomicInteger2) == null || this.countMap.replace(o, atomicInteger, atomicInteger2)) {
                        return 0;
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public int remove(@Nullable final Object o, final int n) {
        if (n == 0) {
            return this.count(o);
        }
        Preconditions.checkArgument(n > 0, "Invalid occurrences: %s", n);
        final AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
        if (atomicInteger == null) {
            return 0;
        }
        while (true) {
            final int value = atomicInteger.get();
            if (value == 0) {
                return 0;
            }
            final int max = Math.max(0, value - n);
            if (atomicInteger.compareAndSet(value, max)) {
                if (max == 0) {
                    this.countMap.remove(o, atomicInteger);
                }
                return value;
            }
        }
    }
    
    public boolean removeExactly(@Nullable final Object o, final int n) {
        if (n == 0) {
            return true;
        }
        Preconditions.checkArgument(n > 0, "Invalid occurrences: %s", n);
        final AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
        if (atomicInteger == null) {
            return false;
        }
        while (true) {
            final int value = atomicInteger.get();
            if (value < n) {
                return false;
            }
            final int n2 = value - n;
            if (atomicInteger.compareAndSet(value, n2)) {
                if (n2 == 0) {
                    this.countMap.remove(o, atomicInteger);
                }
                return true;
            }
        }
    }
    
    @Override
    public int setCount(final Object o, final int n) {
        Preconditions.checkNotNull(o);
        CollectPreconditions.checkNonnegative(n, "count");
        while (true) {
            AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
            if (atomicInteger == null) {
                if (n == 0) {
                    return 0;
                }
                atomicInteger = this.countMap.putIfAbsent(o, new AtomicInteger(n));
                if (atomicInteger == null) {
                    return 0;
                }
            }
            while (true) {
                final int value = atomicInteger.get();
                if (value == 0) {
                    if (n == 0) {
                        return 0;
                    }
                    final AtomicInteger atomicInteger2 = new AtomicInteger(n);
                    if (this.countMap.putIfAbsent(o, atomicInteger2) == null || this.countMap.replace(o, atomicInteger, atomicInteger2)) {
                        return 0;
                    }
                    break;
                }
                else {
                    if (atomicInteger.compareAndSet(value, n)) {
                        if (n == 0) {
                            this.countMap.remove(o, atomicInteger);
                        }
                        return value;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public boolean setCount(final Object o, final int n, final int n2) {
        Preconditions.checkNotNull(o);
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        final AtomicInteger atomicInteger = (AtomicInteger)Maps.safeGet(this.countMap, o);
        if (atomicInteger == null) {
            return n == 0 && (n2 == 0 || this.countMap.putIfAbsent(o, new AtomicInteger(n2)) == null);
        }
        final int value = atomicInteger.get();
        if (value == n) {
            if (value == 0) {
                if (n2 == 0) {
                    this.countMap.remove(o, atomicInteger);
                    return true;
                }
                final AtomicInteger atomicInteger2 = new AtomicInteger(n2);
                return this.countMap.putIfAbsent(o, atomicInteger2) == null || this.countMap.replace(o, atomicInteger, atomicInteger2);
            }
            else if (atomicInteger.compareAndSet(value, n2)) {
                if (n2 == 0) {
                    this.countMap.remove(o, atomicInteger);
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    Set createElementSet() {
        return new ForwardingSet((Set)this.countMap.keySet()) {
            final Set val$delegate;
            final ConcurrentHashMultiset this$0;
            
            @Override
            protected Set delegate() {
                return this.val$delegate;
            }
            
            @Override
            public boolean contains(@Nullable final Object o) {
                return o != null && Collections2.safeContains(this.val$delegate, o);
            }
            
            @Override
            public boolean containsAll(final Collection collection) {
                return this.standardContainsAll(collection);
            }
            
            @Override
            public boolean remove(final Object o) {
                return o != null && Collections2.safeRemove(this.val$delegate, o);
            }
            
            @Override
            public boolean removeAll(final Collection collection) {
                return this.standardRemoveAll(collection);
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
    
    @Override
    public Set entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = (this.entrySet = new EntrySet(null));
        }
        return entrySet;
    }
    
    @Override
    int distinctElements() {
        return this.countMap.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }
    
    @Override
    Iterator entryIterator() {
        return new ForwardingIterator((Iterator)new AbstractIterator() {
            private Iterator mapEntries = ConcurrentHashMultiset.access$100(this.this$0).entrySet().iterator();
            final ConcurrentHashMultiset this$0;
            
            @Override
            protected Multiset.Entry computeNext() {
                while (this.mapEntries.hasNext()) {
                    final Map.Entry<K, AtomicInteger> entry = this.mapEntries.next();
                    final int value = entry.getValue().get();
                    if (value != 0) {
                        return Multisets.immutableEntry(entry.getKey(), value);
                    }
                }
                return (Multiset.Entry)this.endOfData();
            }
            
            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        }) {
            private Multiset.Entry last;
            final Iterator val$readOnlyIterator;
            final ConcurrentHashMultiset this$0;
            
            @Override
            protected Iterator delegate() {
                return this.val$readOnlyIterator;
            }
            
            @Override
            public Multiset.Entry next() {
                return this.last = (Multiset.Entry)super.next();
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.last != null);
                this.this$0.setCount(this.last.getElement(), 0);
                this.last = null;
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
    public void clear() {
        this.countMap.clear();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.countMap);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, objectInputStream.readObject());
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
    public Set elementSet() {
        return super.elementSet();
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return super.retainAll(collection);
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return super.removeAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return super.addAll(collection);
    }
    
    @Override
    public boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Override
    public boolean add(final Object o) {
        return super.add(o);
    }
    
    @Override
    public Iterator iterator() {
        return super.iterator();
    }
    
    @Override
    public boolean contains(final Object o) {
        return super.contains(o);
    }
    
    static ConcurrentMap access$100(final ConcurrentHashMultiset concurrentHashMultiset) {
        return concurrentHashMultiset.countMap;
    }
    
    private class EntrySet extends AbstractMultiset.EntrySet
    {
        final ConcurrentHashMultiset this$0;
        
        private EntrySet(final ConcurrentHashMultiset this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        ConcurrentHashMultiset multiset() {
            return this.this$0;
        }
        
        @Override
        public Object[] toArray() {
            return this.snapshot().toArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.snapshot().toArray(array);
        }
        
        private List snapshot() {
            final ArrayList arrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(this.size());
            Iterators.addAll(arrayListWithExpectedSize, this.iterator());
            return arrayListWithExpectedSize;
        }
        
        @Override
        Multiset multiset() {
            return this.multiset();
        }
        
        EntrySet(final ConcurrentHashMultiset concurrentHashMultiset, final ConcurrentHashMultiset$1 forwardingSet) {
            this(concurrentHashMultiset);
        }
    }
    
    private static class FieldSettersHolder
    {
        static final Serialization.FieldSetter COUNT_MAP_FIELD_SETTER;
        
        static {
            COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
        }
    }
}
