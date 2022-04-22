package io.netty.util.collection;

import java.lang.reflect.*;
import java.util.*;

public class IntObjectHashMap implements IntObjectMap, Iterable
{
    private static final int DEFAULT_CAPACITY = 11;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE;
    private int maxSize;
    private final float loadFactor;
    private int[] keys;
    private Object[] values;
    private int size;
    
    public IntObjectHashMap() {
        this(11, 0.5f);
    }
    
    public IntObjectHashMap(final int n) {
        this(n, 0.5f);
    }
    
    public IntObjectHashMap(final int n, final float loadFactor) {
        if (n < 1) {
            throw new IllegalArgumentException("initialCapacity must be >= 1");
        }
        if (loadFactor <= 0.0f || loadFactor > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }
        this.loadFactor = loadFactor;
        final int adjustCapacity = adjustCapacity(n);
        this.keys = new int[adjustCapacity];
        this.values = new Object[adjustCapacity];
        this.maxSize = this.calcMaxSize(adjustCapacity);
    }
    
    private static Object toExternal(final Object o) {
        return (o == IntObjectHashMap.NULL_VALUE) ? null : o;
    }
    
    private static Object toInternal(final Object o) {
        return (o == null) ? IntObjectHashMap.NULL_VALUE : o;
    }
    
    @Override
    public Object get(final int n) {
        final int index = this.indexOf(n);
        return (index == -1) ? null : toExternal(this.values[index]);
    }
    
    @Override
    public Object put(final int n, final Object o) {
        int n3;
        final int n2 = n3 = this.hashIndex(n);
        while (this.values[n3] != null) {
            if (this.keys[n3] == n) {
                final Object o2 = this.values[n3];
                this.values[n3] = toInternal(o);
                return toExternal(o2);
            }
            if ((n3 = this.probeNext(n3)) == n2) {
                throw new IllegalStateException("Unable to insert");
            }
        }
        this.keys[n3] = n;
        this.values[n3] = toInternal(o);
        this.growSize();
        return null;
    }
    
    private int probeNext(final int n) {
        return (n == this.values.length - 1) ? 0 : (n + 1);
    }
    
    @Override
    public void putAll(final IntObjectMap intObjectMap) {
        if (intObjectMap instanceof IntObjectHashMap) {
            final IntObjectHashMap intObjectHashMap = (IntObjectHashMap)intObjectMap;
            while (0 < intObjectHashMap.values.length) {
                final Object o = intObjectHashMap.values[0];
                if (o != null) {
                    this.put(intObjectHashMap.keys[0], o);
                }
                int n = 0;
                ++n;
            }
            return;
        }
        for (final Entry entry : intObjectMap.entries()) {
            this.put(entry.key(), entry.value());
        }
    }
    
    @Override
    public Object remove(final int n) {
        final int index = this.indexOf(n);
        if (index == -1) {
            return null;
        }
        final Object o = this.values[index];
        this.removeAt(index);
        return toExternal(o);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public void clear() {
        Arrays.fill(this.keys, 0);
        Arrays.fill(this.values, null);
        this.size = 0;
    }
    
    @Override
    public boolean containsKey(final int n) {
        return this.indexOf(n) >= 0;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final Object internal = toInternal(o);
        while (0 < this.values.length) {
            if (this.values[0] != null && this.values[0].equals(internal)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public Iterable entries() {
        return this;
    }
    
    @Override
    public Iterator iterator() {
        return new IteratorImpl(null);
    }
    
    @Override
    public int[] keys() {
        final int[] array = new int[this.size()];
        while (0 < this.values.length) {
            if (this.values[0] != null) {
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = this.keys[0];
            }
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    @Override
    public Object[] values(final Class clazz) {
        final Object[] array = (Object[])Array.newInstance(clazz, this.size());
        while (0 < this.values.length) {
            if (this.values[0] != null) {
                final Object[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = this.values[0];
            }
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    @Override
    public int hashCode() {
        int size = this.size;
        while (0 < this.keys.length) {
            size ^= this.keys[0];
            int n = 0;
            ++n;
        }
        return size;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntObjectMap)) {
            return false;
        }
        final IntObjectMap intObjectMap = (IntObjectMap)o;
        if (this.size != intObjectMap.size()) {
            return false;
        }
        while (0 < this.values.length) {
            final Object o2 = this.values[0];
            if (o2 != null) {
                final Object value = intObjectMap.get(this.keys[0]);
                if (o2 == IntObjectHashMap.NULL_VALUE) {
                    if (value != null) {
                        return false;
                    }
                }
                else if (!o2.equals(value)) {
                    return false;
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private int indexOf(final int n) {
        int n3;
        final int n2 = n3 = this.hashIndex(n);
        while (this.values[n3] != null) {
            if (n == this.keys[n3]) {
                return n3;
            }
            if ((n3 = this.probeNext(n3)) == n2) {
                return -1;
            }
        }
        return -1;
    }
    
    private int hashIndex(final int n) {
        return n % this.keys.length;
    }
    
    private void growSize() {
        ++this.size;
        if (this.size > this.maxSize) {
            this.rehash(adjustCapacity((int)Math.min(this.keys.length * 2.0, 2.147483639E9)));
        }
        else if (this.size == this.keys.length) {
            this.rehash(this.keys.length);
        }
    }
    
    private static int adjustCapacity(final int n) {
        return n | 0x1;
    }
    
    private void removeAt(final int n) {
        --this.size;
        this.keys[n] = 0;
        this.values[n] = null;
        int n2 = n;
        for (int n3 = this.probeNext(n); this.values[n3] != null; n3 = this.probeNext(n3)) {
            final int hashIndex = this.hashIndex(this.keys[n3]);
            if ((n3 < hashIndex && (hashIndex <= n2 || n2 <= n3)) || (hashIndex <= n2 && n2 <= n3)) {
                this.keys[n2] = this.keys[n3];
                this.values[n2] = this.values[n3];
                this.keys[n3] = 0;
                this.values[n3] = null;
                n2 = n3;
            }
        }
    }
    
    private int calcMaxSize(final int n) {
        return Math.min(n - 1, (int)(n * this.loadFactor));
    }
    
    private void rehash(final int n) {
        final int[] keys = this.keys;
        final Object[] values = this.values;
        this.keys = new int[n];
        this.values = new Object[n];
        this.maxSize = this.calcMaxSize(n);
        while (0 < values.length) {
            final Object o = values[0];
            if (o != null) {
                final int n2 = keys[0];
                int n3;
                for (n3 = this.hashIndex(n2); this.values[n3] != null; n3 = this.probeNext(n3)) {}
                this.keys[n3] = n2;
                this.values[n3] = toInternal(o);
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        final StringBuilder sb = new StringBuilder(4 * this.size);
        while (0 < this.values.length) {
            final Object o = this.values[0];
            if (o != null) {
                sb.append((sb.length() == 0) ? "{" : ", ");
                sb.append(this.keys[0]).append('=').append((o == this) ? "(this Map)" : o);
            }
            int n = 0;
            ++n;
        }
        return sb.append('}').toString();
    }
    
    static Object[] access$100(final IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.values;
    }
    
    static int[] access$200(final IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.keys;
    }
    
    static void access$300(final IntObjectHashMap intObjectHashMap, final int n) {
        intObjectHashMap.removeAt(n);
    }
    
    static Object access$400(final Object o) {
        return toExternal(o);
    }
    
    static Object access$500(final Object o) {
        return toInternal(o);
    }
    
    static {
        NULL_VALUE = new Object();
    }
    
    private final class IteratorImpl implements Iterator, Entry
    {
        private int prevIndex;
        private int nextIndex;
        private int entryIndex;
        final IntObjectHashMap this$0;
        
        private IteratorImpl(final IntObjectHashMap this$0) {
            this.this$0 = this$0;
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }
        
        private void scanNext() {
            while (++this.nextIndex != IntObjectHashMap.access$100(this.this$0).length && IntObjectHashMap.access$100(this.this$0)[this.nextIndex] == null) {}
        }
        
        @Override
        public boolean hasNext() {
            if (this.nextIndex == -1) {
                this.scanNext();
            }
            return this.nextIndex < IntObjectHashMap.access$200(this.this$0).length;
        }
        
        @Override
        public Entry next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.prevIndex = this.nextIndex;
            this.scanNext();
            this.entryIndex = this.prevIndex;
            return this;
        }
        
        @Override
        public void remove() {
            if (this.prevIndex < 0) {
                throw new IllegalStateException("next must be called before each remove.");
            }
            IntObjectHashMap.access$300(this.this$0, this.prevIndex);
            this.prevIndex = -1;
        }
        
        @Override
        public int key() {
            return IntObjectHashMap.access$200(this.this$0)[this.entryIndex];
        }
        
        @Override
        public Object value() {
            return IntObjectHashMap.access$400(IntObjectHashMap.access$100(this.this$0)[this.entryIndex]);
        }
        
        @Override
        public void setValue(final Object o) {
            IntObjectHashMap.access$100(this.this$0)[this.entryIndex] = IntObjectHashMap.access$500(o);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        IteratorImpl(final IntObjectHashMap intObjectHashMap, final IntObjectHashMap$1 object) {
            this(intObjectHashMap);
        }
    }
}
