package org.lwjgl.opengl;

import java.util.*;

final class FastIntMap implements Iterable
{
    private Entry[] table;
    private int size;
    private int mask;
    private int capacity;
    private int threshold;
    
    FastIntMap() {
        this(16, 0.75f);
    }
    
    FastIntMap(final int n) {
        this(n, 0.75f);
    }
    
    FastIntMap(final int n, final float n2) {
        if (n > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity must be greater than zero.");
        }
        if (n2 <= 0.0f) {
            throw new IllegalArgumentException("initialCapacity must be greater than zero.");
        }
        this.capacity = 1;
        while (this.capacity < n) {
            this.capacity <<= 1;
        }
        this.threshold = (int)(this.capacity * n2);
        this.table = new Entry[this.capacity];
        this.mask = this.capacity - 1;
    }
    
    private int index(final int n) {
        return index(n, this.mask);
    }
    
    private static int index(final int n, final int n2) {
        return n & n2;
    }
    
    public Object put(final int n, final Object value) {
        final Entry[] table = this.table;
        final int index = this.index(n);
        for (Entry next = table[index]; next != null; next = next.next) {
            if (next.key == n) {
                final Object value2 = next.value;
                next.value = value;
                return value2;
            }
        }
        table[index] = new Entry(n, value, table[index]);
        if (this.size++ >= this.threshold) {
            this.rehash(table);
        }
        return null;
    }
    
    private void rehash(final Entry[] array) {
        final int capacity = 2 * this.capacity;
        final int mask = capacity - 1;
        final Entry[] table = new Entry[capacity];
        while (0 < array.length) {
            Entry entry = array[0];
            if (entry != null) {
                do {
                    final Entry next = entry.next;
                    final int index = index(entry.key, mask);
                    entry.next = table[index];
                    table[index] = entry;
                    entry = next;
                } while (entry != null);
            }
            int n = 0;
            ++n;
        }
        this.table = table;
        this.capacity = capacity;
        this.mask = mask;
        this.threshold *= 2;
    }
    
    public Object get(final int n) {
        for (Entry next = this.table[this.index(n)]; next != null; next = next.next) {
            if (next.key == n) {
                return next.value;
            }
        }
        return null;
    }
    
    public boolean containsValue(final Object o) {
        final Entry[] table = this.table;
        for (int i = table.length - 1; i >= 0; --i) {
            for (Entry next = table[i]; next != null; next = next.next) {
                if (next.value.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsKey(final int n) {
        for (Entry next = this.table[this.index(n)]; next != null; next = next.next) {
            if (next.key == n) {
                return true;
            }
        }
        return false;
    }
    
    public Object remove(final int n) {
        final int index = this.index(n);
        Entry entry2;
        Entry next;
        for (Entry entry = entry2 = this.table[index]; entry2 != null; entry2 = next) {
            next = entry2.next;
            if (entry2.key == n) {
                --this.size;
                if (entry == entry2) {
                    this.table[index] = next;
                }
                else {
                    entry.next = next;
                }
                return entry2.value;
            }
            entry = entry2;
        }
        return null;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void clear() {
        final Entry[] table = this.table;
        for (int i = table.length - 1; i >= 0; --i) {
            table[i] = null;
        }
        this.size = 0;
    }
    
    public EntryIterator iterator() {
        return new EntryIterator();
    }
    
    public Iterator iterator() {
        return this.iterator();
    }
    
    static Entry[] access$000(final FastIntMap fastIntMap) {
        return fastIntMap.table;
    }
    
    static final class Entry
    {
        final int key;
        Object value;
        Entry next;
        
        Entry(final int key, final Object value, final Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public int getKey() {
            return this.key;
        }
        
        public Object getValue() {
            return this.value;
        }
    }
    
    public class EntryIterator implements Iterator
    {
        private int nextIndex;
        private Entry current;
        final FastIntMap this$0;
        
        EntryIterator(final FastIntMap this$0) {
            this.this$0 = this$0;
            this.reset();
        }
        
        public void reset() {
            this.current = null;
            Entry[] access$000;
            int nextIndex;
            for (access$000 = FastIntMap.access$000(this.this$0), nextIndex = access$000.length - 1; nextIndex >= 0 && access$000[nextIndex] == null; --nextIndex) {}
            this.nextIndex = nextIndex;
        }
        
        public boolean hasNext() {
            if (this.nextIndex >= 0) {
                return true;
            }
            final Entry current = this.current;
            return current != null && current.next != null;
        }
        
        public Entry next() {
            final Entry current = this.current;
            if (current != null) {
                final Entry next = current.next;
                if (next != null) {
                    return this.current = next;
                }
            }
            final Entry[] access$000 = FastIntMap.access$000(this.this$0);
            int nextIndex = this.nextIndex;
            final Entry current2 = access$000[nextIndex];
            this.current = current2;
            final Entry entry = current2;
            while (--nextIndex >= 0 && access$000[nextIndex] == null) {}
            this.nextIndex = nextIndex;
            return entry;
        }
        
        public void remove() {
            this.this$0.remove(this.current.key);
        }
        
        public Object next() {
            return this.next();
        }
    }
}
