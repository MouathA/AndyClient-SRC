package gnu.trove.map.hash;

import gnu.trove.map.*;
import java.lang.reflect.*;
import gnu.trove.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;
import gnu.trove.impl.*;
import java.io.*;
import gnu.trove.iterator.hash.*;
import gnu.trove.iterator.*;
import gnu.trove.impl.hash.*;
import java.util.*;

public class TObjectLongHashMap extends TObjectHash implements TObjectLongMap, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectLongProcedure PUT_ALL_PROC;
    protected transient long[] _values;
    protected long no_entry_value;
    
    public TObjectLongHashMap() {
        this.PUT_ALL_PROC = new TObjectLongProcedure() {
            final TObjectLongHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final long n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
    }
    
    public TObjectLongHashMap(final int n) {
        super(n);
        this.PUT_ALL_PROC = new TObjectLongProcedure() {
            final TObjectLongHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final long n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
    }
    
    public TObjectLongHashMap(final int n, final float n2) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectLongProcedure() {
            final TObjectLongHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final long n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
    }
    
    public TObjectLongHashMap(final int n, final float n2, final long no_entry_value) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectLongProcedure() {
            final TObjectLongHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final long n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = no_entry_value;
        if (this.no_entry_value != 0L) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectLongHashMap(final TObjectLongMap tObjectLongMap) {
        this(tObjectLongMap.size(), 0.5f, tObjectLongMap.getNoEntryValue());
        if (tObjectLongMap instanceof TObjectLongHashMap) {
            final TObjectLongHashMap tObjectLongHashMap = (TObjectLongHashMap)tObjectLongMap;
            this._loadFactor = tObjectLongHashMap._loadFactor;
            this.no_entry_value = tObjectLongHashMap.no_entry_value;
            if (this.no_entry_value != 0L) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(tObjectLongMap);
    }
    
    @Override
    public int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new long[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final Object[] set = this._set;
        final long[] values = this._values;
        Arrays.fill(this._set = new Object[n], TObjectLongHashMap.FREE);
        Arrays.fill(this._values = new long[n], this.no_entry_value);
        int n2 = length;
        while (n2-- > 0) {
            if (set[n2] != TObjectLongHashMap.FREE && set[n2] != TObjectLongHashMap.REMOVED) {
                final Object o = set[n2];
                final int insertKey = this.insertKey(o);
                if (insertKey < 0) {
                    this.throwObjectContractViolation(this._set[-insertKey - 1], o);
                }
                this._set[insertKey] = o;
                this._values[insertKey] = values[n2];
            }
        }
    }
    
    @Override
    public long getNoEntryValue() {
        return this.no_entry_value;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.contains(o);
    }
    
    @Override
    public boolean containsValue(final long n) {
        final Object[] set = this._set;
        final long[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && n == values[length]) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public long get(final Object o) {
        final int index = this.index(o);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    @Override
    public long put(final Object o, final long n) {
        return this.doPut(n, this.insertKey(o));
    }
    
    @Override
    public long putIfAbsent(final Object o, final long n) {
        final int insertKey = this.insertKey(o);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(n, insertKey);
    }
    
    private long doPut(final long n, int n2) {
        long no_entry_value = this.no_entry_value;
        if (n2 < 0) {
            n2 = -n2 - 1;
            no_entry_value = this._values[n2];
        }
        this._values[n2] = n;
        if (false) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return no_entry_value;
    }
    
    @Override
    public long remove(final Object o) {
        long no_entry_value = this.no_entry_value;
        final int index = this.index(o);
        if (index >= 0) {
            no_entry_value = this._values[index];
            this.removeAt(index);
        }
        return no_entry_value;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._values[n] = this.no_entry_value;
        super.removeAt(n);
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), (long)entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TObjectLongMap tObjectLongMap) {
        tObjectLongMap.forEachEntry(this.PUT_ALL_PROC);
    }
    
    @Override
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectLongHashMap.FREE);
        Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
    }
    
    @Override
    public Set keySet() {
        return new KeyView();
    }
    
    @Override
    public Object[] keys() {
        final Object[] array = new Object[this.size()];
        final Object[] set = this._set;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED) {
                final Object[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = set[length];
            }
        }
        return array;
    }
    
    @Override
    public Object[] keys(Object[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
        }
        final Object[] set = this._set;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED) {
                final Object[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = set[length];
            }
        }
        return array;
    }
    
    @Override
    public TLongCollection valueCollection() {
        return new TLongValueCollection();
    }
    
    @Override
    public long[] values() {
        final long[] array = new long[this.size()];
        final long[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED) {
                final long[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public long[] values(long[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new long[size];
        }
        final long[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED) {
                final long[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        if (array.length > size) {
            array[size] = this.no_entry_value;
        }
        return array;
    }
    
    @Override
    public TObjectLongIterator iterator() {
        return new TObjectLongHashIterator(this);
    }
    
    @Override
    public boolean increment(final Object o) {
        return this.adjustValue(o, 1L);
    }
    
    @Override
    public boolean adjustValue(final Object o, final long n) {
        final int index = this.index(o);
        if (index < 0) {
            return false;
        }
        final long[] values = this._values;
        final int n2 = index;
        values[n2] += n;
        return true;
    }
    
    @Override
    public long adjustOrPutValue(final Object o, final long n, final long n2) {
        final int insertKey = this.insertKey(o);
        long n6;
        if (insertKey < 0) {
            final int n3 = -insertKey - 1;
            final long[] values = this._values;
            final int n4 = n3;
            final long n5 = values[n4] + n;
            values[n4] = n5;
            n6 = n5;
        }
        else {
            this._values[insertKey] = n2;
            n6 = n2;
        }
        if (true) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return n6;
    }
    
    @Override
    public boolean forEachKey(final TObjectProcedure tObjectProcedure) {
        return this.forEach(tObjectProcedure);
    }
    
    @Override
    public boolean forEachValue(final TLongProcedure tLongProcedure) {
        final Object[] set = this._set;
        final long[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && !tLongProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TObjectLongProcedure tObjectLongProcedure) {
        final Object[] set = this._set;
        final long[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && !tObjectLongProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean retainEntries(final TObjectLongProcedure tObjectLongProcedure) {
        final Object[] set = this._set;
        final long[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && !tObjectLongProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public void transformValues(final TLongFunction tLongFunction) {
        final Object[] set = this._set;
        final long[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != null && set[length] != TObjectLongHashMap.REMOVED) {
                values[length] = tLongFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TObjectLongMap)) {
            return false;
        }
        final TObjectLongMap tObjectLongMap = (TObjectLongMap)o;
        if (tObjectLongMap.size() != this.size()) {
            return false;
        }
        final TObjectLongIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            final Object key = iterator.key();
            final long value = iterator.value();
            if (value == this.no_entry_value) {
                if (tObjectLongMap.get(key) != tObjectLongMap.getNoEntryValue() || !tObjectLongMap.containsKey(key)) {
                    return false;
                }
                continue;
            }
            else {
                if (value != tObjectLongMap.get(key)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final Object[] set = this._set;
        final long[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED) {
                final int n = 0 + (HashFunctions.hash(values[length]) ^ ((set[length] == null) ? 0 : set[length].hashCode()));
            }
        }
        return 0;
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeLong(this.no_entry_value);
        objectOutput.writeInt(this._size);
        int length = this._set.length;
        while (length-- > 0) {
            if (this._set[length] != TObjectLongHashMap.REMOVED && this._set[length] != TObjectLongHashMap.FREE) {
                objectOutput.writeObject(this._set[length]);
                objectOutput.writeLong(this._values[length]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        this.no_entry_value = objectInput.readLong();
        int int1 = objectInput.readInt();
        this.setUp(int1);
        while (int1-- > 0) {
            this.put(objectInput.readObject(), objectInput.readLong());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        this.forEachEntry(new TObjectLongProcedure(sb) {
            private boolean first = true;
            final TObjectLongHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final Object o, final long n) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    this.val$buf.append(",");
                }
                this.val$buf.append(o).append("=").append(n);
                return true;
            }
        });
        sb.append("}");
        return sb.toString();
    }
    
    static int access$0(final TObjectLongHashMap tObjectLongHashMap) {
        return tObjectLongHashMap._size;
    }
    
    protected class KeyView extends MapBackedView
    {
        final TObjectLongHashMap this$0;
        
        protected KeyView(final TObjectLongHashMap this$0) {
            this.this$0 = this$0.super(null);
        }
        
        @Override
        public Iterator iterator() {
            return new TObjectHashIterator(this.this$0);
        }
        
        @Override
        public boolean removeElement(final Object o) {
            return this.this$0.no_entry_value != this.this$0.remove(o);
        }
        
        @Override
        public boolean containsElement(final Object o) {
            return this.this$0.contains(o);
        }
    }
    
    private abstract class MapBackedView extends AbstractSet implements Set, Iterable
    {
        final TObjectLongHashMap this$0;
        
        private MapBackedView(final TObjectLongHashMap this$0) {
            this.this$0 = this$0;
        }
        
        public abstract boolean removeElement(final Object p0);
        
        public abstract boolean containsElement(final Object p0);
        
        @Override
        public boolean contains(final Object o) {
            return this.containsElement(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.removeElement(o);
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public boolean add(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public Object[] toArray() {
            final Object[] array = new Object[this.size()];
            final Iterator<Object> iterator = this.iterator();
            while (iterator.hasNext()) {
                array[0] = iterator.next();
                int n = 0;
                ++n;
            }
            return array;
        }
        
        @Override
        public Object[] toArray(Object[] array) {
            final int size = this.size();
            if (array.length < size) {
                array = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            final Iterator<Object> iterator = this.iterator();
            final Object[] array2 = array;
            while (0 < size) {
                array2[0] = iterator.next();
                int n = 0;
                ++n;
            }
            if (array.length > size) {
                array[size] = null;
            }
            return array;
        }
        
        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final Iterator<Object> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        MapBackedView(final TObjectLongHashMap tObjectLongHashMap, final MapBackedView mapBackedView) {
            this(tObjectLongHashMap);
        }
    }
    
    class TLongValueCollection implements TLongCollection
    {
        final TObjectLongHashMap this$0;
        
        TLongValueCollection(final TObjectLongHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TLongIterator iterator() {
            return new TObjectLongValueHashIterator();
        }
        
        @Override
        public long getNoEntryValue() {
            return this.this$0.no_entry_value;
        }
        
        @Override
        public int size() {
            return TObjectLongHashMap.access$0(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TObjectLongHashMap.access$0(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final long n) {
            return this.this$0.containsValue(n);
        }
        
        @Override
        public long[] toArray() {
            return this.this$0.values();
        }
        
        @Override
        public long[] toArray(final long[] array) {
            return this.this$0.values(array);
        }
        
        @Override
        public boolean add(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final long n) {
            final long[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = values.length;
            while (length-- > 0) {
                if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && n == values[length]) {
                    this.this$0.removeAt(length);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            for (final Long next : collection) {
                if (!(next instanceof Long)) {
                    return false;
                }
                if (!this.this$0.containsValue(next)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TLongCollection collection) {
            final TLongIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsValue(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final long[] array) {
            while (0 < array.length) {
                if (!this.this$0.containsValue(array[0])) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final TLongCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final long[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final TLongIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final TLongCollection collection) {
            if (this == collection) {
                return false;
            }
            final TLongIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final long[] array) {
            Arrays.sort(array);
            final long[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = set.length;
            while (length-- > 0) {
                if (set[length] != TObjectLongHashMap.FREE && set[length] != TObjectLongHashMap.REMOVED && Arrays.binarySearch(array, values[length]) < 0) {
                    this.this$0.removeAt(length);
                }
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            for (final Long next : collection) {
                if (!(next instanceof Long) || this.remove(next)) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final TLongCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            final TLongIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.remove(iterator.next())) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final long[] array) {
            int length = array.length;
            while (length-- > 0) {
                if (this.remove(array[length])) {}
            }
            return true;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public boolean forEach(final TLongProcedure tLongProcedure) {
            return this.this$0.forEachValue(tLongProcedure);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            this.this$0.forEachValue(new TLongProcedure(sb) {
                private boolean first = true;
                final TLongValueCollection this$1;
                private final StringBuilder val$buf;
                
                @Override
                public boolean execute(final long n) {
                    if (this.first) {
                        this.first = false;
                    }
                    else {
                        this.val$buf.append(", ");
                    }
                    this.val$buf.append(n);
                    return true;
                }
            });
            sb.append("}");
            return sb.toString();
        }
        
        static TObjectLongHashMap access$0(final TLongValueCollection collection) {
            return collection.this$0;
        }
        
        class TObjectLongValueHashIterator implements TLongIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            final TLongValueCollection this$1;
            
            TObjectLongValueHashIterator(final TLongValueCollection this$1) {
                this.this$1 = this$1;
                this._hash = TLongValueCollection.access$0(this$1);
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            @Override
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            @Override
            public long next() {
                this.moveToNextIndex();
                return TLongValueCollection.access$0(this.this$1)._values[this._index];
            }
            
            @Override
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                this._hash.tempDisableAutoCompaction();
                TLongValueCollection.access$0(this.this$1).removeAt(this._index);
                this._hash.reenableAutoCompaction(false);
                --this._expectedSize;
            }
            
            protected final void moveToNextIndex() {
                final int nextIndex = this.nextIndex();
                this._index = nextIndex;
                if (nextIndex < 0) {
                    throw new NoSuchElementException();
                }
            }
            
            protected final int nextIndex() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                final Object[] set = TLongValueCollection.access$0(this.this$1)._set;
                int index = this._index;
                while (index-- > 0 && (set[index] == TObjectHash.FREE || set[index] == TObjectHash.REMOVED)) {}
                return index;
            }
        }
    }
    
    class TObjectLongHashIterator extends TObjectHashIterator implements TObjectLongIterator
    {
        private final TObjectLongHashMap _map;
        final TObjectLongHashMap this$0;
        
        public TObjectLongHashIterator(final TObjectLongHashMap this$0, final TObjectLongHashMap map) {
            this.this$0 = this$0;
            super(map);
            this._map = map;
        }
        
        @Override
        public void advance() {
            this.moveToNextIndex();
        }
        
        @Override
        public Object key() {
            return this._map._set[this._index];
        }
        
        @Override
        public long value() {
            return this._map._values[this._index];
        }
        
        @Override
        public long setValue(final long n) {
            final long value = this.value();
            this._map._values[this._index] = n;
            return value;
        }
    }
}
