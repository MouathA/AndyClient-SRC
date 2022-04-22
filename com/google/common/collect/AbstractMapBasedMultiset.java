package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import com.google.common.primitives.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultiset extends AbstractMultiset implements Serializable
{
    private transient Map backingMap;
    private transient long size;
    @GwtIncompatible("not needed in emulated source.")
    private static final long serialVersionUID = -2250766705698539974L;
    
    protected AbstractMapBasedMultiset(final Map map) {
        this.backingMap = (Map)Preconditions.checkNotNull(map);
        this.size = super.size();
    }
    
    void setBackingMap(final Map backingMap) {
        this.backingMap = backingMap;
    }
    
    @Override
    public Set entrySet() {
        return super.entrySet();
    }
    
    @Override
    Iterator entryIterator() {
        return new Iterator((Iterator)this.backingMap.entrySet().iterator()) {
            Map.Entry toRemove;
            final Iterator val$backingEntries;
            final AbstractMapBasedMultiset this$0;
            
            @Override
            public boolean hasNext() {
                return this.val$backingEntries.hasNext();
            }
            
            @Override
            public Multiset.Entry next() {
                final Map.Entry toRemove = this.val$backingEntries.next();
                this.toRemove = toRemove;
                return new Multisets.AbstractEntry((Map.Entry)toRemove) {
                    final Map.Entry val$mapEntry;
                    final AbstractMapBasedMultiset$1 this$1;
                    
                    @Override
                    public Object getElement() {
                        return this.val$mapEntry.getKey();
                    }
                    
                    @Override
                    public int getCount() {
                        final Count count = this.val$mapEntry.getValue();
                        if (count == null || count.get() == 0) {
                            final Count count2 = AbstractMapBasedMultiset.access$000(this.this$1.this$0).get(this.getElement());
                            if (count2 != null) {
                                return count2.get();
                            }
                        }
                        return (count == null) ? 0 : count.get();
                    }
                };
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.toRemove != null);
                AbstractMapBasedMultiset.access$122(this.this$0, this.toRemove.getValue().getAndSet(0));
                this.val$backingEntries.remove();
                this.toRemove = null;
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    @Override
    public void clear() {
        final Iterator<Count> iterator = this.backingMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().set(0);
        }
        this.backingMap.clear();
        this.size = 0L;
    }
    
    @Override
    int distinctElements() {
        return this.backingMap.size();
    }
    
    @Override
    public int size() {
        return Ints.saturatedCast(this.size);
    }
    
    @Override
    public Iterator iterator() {
        return new MapBasedMultisetIterator();
    }
    
    @Override
    public int count(@Nullable final Object o) {
        final Count count = (Count)Maps.safeGet(this.backingMap, o);
        return (count == null) ? 0 : count.get();
    }
    
    @Override
    public int add(@Nullable final Object o, final int n) {
        if (n == 0) {
            return this.count(o);
        }
        Preconditions.checkArgument(n > 0, "occurrences cannot be negative: %s", n);
        final Count count = this.backingMap.get(o);
        if (count == null) {
            this.backingMap.put(o, new Count(n));
        }
        else {
            count.get();
            final long n2 = 0 + (long)n;
            Preconditions.checkArgument(n2 <= 2147483647L, "too many occurrences: %s", n2);
            count.getAndAdd(n);
        }
        this.size += n;
        return 0;
    }
    
    @Override
    public int remove(@Nullable final Object o, final int n) {
        if (n == 0) {
            return this.count(o);
        }
        Preconditions.checkArgument(n > 0, "occurrences cannot be negative: %s", n);
        final Count count = this.backingMap.get(o);
        if (count == null) {
            return 0;
        }
        final int value = count.get();
        int n2;
        if (value > n) {
            n2 = n;
        }
        else {
            n2 = value;
            this.backingMap.remove(o);
        }
        count.addAndGet(-n2);
        this.size -= n2;
        return value;
    }
    
    @Override
    public int setCount(@Nullable final Object o, final int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        int n2;
        if (n == 0) {
            n2 = getAndSet(this.backingMap.remove(o), n);
        }
        else {
            final Count count = this.backingMap.get(o);
            n2 = getAndSet(count, n);
            if (count == null) {
                this.backingMap.put(o, new Count(n));
            }
        }
        this.size += n - n2;
        return n2;
    }
    
    private static int getAndSet(final Count count, final int n) {
        if (count == null) {
            return 0;
        }
        return count.getAndSet(n);
    }
    
    @GwtIncompatible("java.io.ObjectStreamException")
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
    
    static Map access$000(final AbstractMapBasedMultiset abstractMapBasedMultiset) {
        return abstractMapBasedMultiset.backingMap;
    }
    
    static long access$122(final AbstractMapBasedMultiset abstractMapBasedMultiset, final long n) {
        return abstractMapBasedMultiset.size -= n;
    }
    
    static long access$110(final AbstractMapBasedMultiset abstractMapBasedMultiset) {
        return abstractMapBasedMultiset.size--;
    }
    
    private class MapBasedMultisetIterator implements Iterator
    {
        final Iterator entryIterator;
        Map.Entry currentEntry;
        int occurrencesLeft;
        boolean canRemove;
        final AbstractMapBasedMultiset this$0;
        
        MapBasedMultisetIterator(final AbstractMapBasedMultiset this$0) {
            this.this$0 = this$0;
            this.entryIterator = AbstractMapBasedMultiset.access$000(this$0).entrySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }
        
        @Override
        public Object next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = this.entryIterator.next();
                this.occurrencesLeft = this.currentEntry.getValue().get();
            }
            --this.occurrencesLeft;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }
        
        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (this.currentEntry.getValue().get() <= 0) {
                throw new ConcurrentModificationException();
            }
            if (this.currentEntry.getValue().addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.access$110(this.this$0);
            this.canRemove = false;
        }
    }
}
