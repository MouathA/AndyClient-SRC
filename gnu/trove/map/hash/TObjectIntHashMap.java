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

public class TObjectIntHashMap extends TObjectHash implements TObjectIntMap, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectIntProcedure PUT_ALL_PROC;
    protected transient int[] _values;
    protected int no_entry_value;
    
    public TObjectIntHashMap() {
        this.PUT_ALL_PROC = new TObjectIntProcedure() {
            final TObjectIntHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final int n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TObjectIntHashMap(final int n) {
        super(n);
        this.PUT_ALL_PROC = new TObjectIntProcedure() {
            final TObjectIntHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final int n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TObjectIntHashMap(final int n, final float n2) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectIntProcedure() {
            final TObjectIntHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final int n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TObjectIntHashMap(final int n, final float n2, final int no_entry_value) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectIntProcedure() {
            final TObjectIntHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final int n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = no_entry_value;
        if (this.no_entry_value != 0) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectIntHashMap(final TObjectIntMap tObjectIntMap) {
        this(tObjectIntMap.size(), 0.5f, tObjectIntMap.getNoEntryValue());
        if (tObjectIntMap instanceof TObjectIntHashMap) {
            final TObjectIntHashMap tObjectIntHashMap = (TObjectIntHashMap)tObjectIntMap;
            this._loadFactor = tObjectIntHashMap._loadFactor;
            this.no_entry_value = tObjectIntHashMap.no_entry_value;
            if (this.no_entry_value != 0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(tObjectIntMap);
    }
    
    @Override
    public int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new int[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final Object[] set = this._set;
        final int[] values = this._values;
        Arrays.fill(this._set = new Object[n], TObjectIntHashMap.FREE);
        Arrays.fill(this._values = new int[n], this.no_entry_value);
        int n2 = length;
        while (n2-- > 0) {
            if (set[n2] != TObjectIntHashMap.FREE && set[n2] != TObjectIntHashMap.REMOVED) {
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
    public int getNoEntryValue() {
        return this.no_entry_value;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.contains(o);
    }
    
    @Override
    public boolean containsValue(final int n) {
        final Object[] set = this._set;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && n == values[length]) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int get(final Object o) {
        final int index = this.index(o);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    @Override
    public int put(final Object o, final int n) {
        return this.doPut(n, this.insertKey(o));
    }
    
    @Override
    public int putIfAbsent(final Object o, final int n) {
        final int insertKey = this.insertKey(o);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(n, insertKey);
    }
    
    private int doPut(final int n, int n2) {
        int no_entry_value = this.no_entry_value;
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
    public int remove(final Object o) {
        int no_entry_value = this.no_entry_value;
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
            this.put(entry.getKey(), (int)entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TObjectIntMap tObjectIntMap) {
        tObjectIntMap.forEachEntry(this.PUT_ALL_PROC);
    }
    
    @Override
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectIntHashMap.FREE);
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
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED) {
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
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED) {
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
    public TIntCollection valueCollection() {
        return new TIntValueCollection();
    }
    
    @Override
    public int[] values() {
        final int[] array = new int[this.size()];
        final int[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED) {
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public int[] values(int[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED) {
                final int[] array2 = array;
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
    public TObjectIntIterator iterator() {
        return new TObjectIntHashIterator(this);
    }
    
    @Override
    public boolean increment(final Object o) {
        return this.adjustValue(o, 1);
    }
    
    @Override
    public boolean adjustValue(final Object o, final int n) {
        final int index = this.index(o);
        if (index < 0) {
            return false;
        }
        final int[] values = this._values;
        final int n2 = index;
        values[n2] += n;
        return true;
    }
    
    @Override
    public int adjustOrPutValue(final Object o, final int n, final int n2) {
        final int insertKey = this.insertKey(o);
        int n6;
        if (insertKey < 0) {
            final int n3 = -insertKey - 1;
            final int[] values = this._values;
            final int n4 = n3;
            final int n5 = values[n4] + n;
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
    public boolean forEachValue(final TIntProcedure tIntProcedure) {
        final Object[] set = this._set;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && !tIntProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TObjectIntProcedure tObjectIntProcedure) {
        final Object[] set = this._set;
        final int[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && !tObjectIntProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean retainEntries(final TObjectIntProcedure tObjectIntProcedure) {
        final Object[] set = this._set;
        final int[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && !tObjectIntProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public void transformValues(final TIntFunction tIntFunction) {
        final Object[] set = this._set;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != null && set[length] != TObjectIntHashMap.REMOVED) {
                values[length] = tIntFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TObjectIntMap)) {
            return false;
        }
        final TObjectIntMap tObjectIntMap = (TObjectIntMap)o;
        if (tObjectIntMap.size() != this.size()) {
            return false;
        }
        final TObjectIntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            final Object key = iterator.key();
            final int value = iterator.value();
            if (value == this.no_entry_value) {
                if (tObjectIntMap.get(key) != tObjectIntMap.getNoEntryValue() || !tObjectIntMap.containsKey(key)) {
                    return false;
                }
                continue;
            }
            else {
                if (value != tObjectIntMap.get(key)) {
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
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED) {
                final int n = 0 + (HashFunctions.hash(values[length]) ^ ((set[length] == null) ? 0 : set[length].hashCode()));
            }
        }
        return 0;
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeInt(this.no_entry_value);
        objectOutput.writeInt(this._size);
        int length = this._set.length;
        while (length-- > 0) {
            if (this._set[length] != TObjectIntHashMap.REMOVED && this._set[length] != TObjectIntHashMap.FREE) {
                objectOutput.writeObject(this._set[length]);
                objectOutput.writeInt(this._values[length]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        this.no_entry_value = objectInput.readInt();
        int int1 = objectInput.readInt();
        this.setUp(int1);
        while (int1-- > 0) {
            this.put(objectInput.readObject(), objectInput.readInt());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        this.forEachEntry(new TObjectIntProcedure(sb) {
            private boolean first = true;
            final TObjectIntHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final Object o, final int n) {
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
    
    static int access$0(final TObjectIntHashMap tObjectIntHashMap) {
        return tObjectIntHashMap._size;
    }
    
    protected class KeyView extends MapBackedView
    {
        final TObjectIntHashMap this$0;
        
        protected KeyView(final TObjectIntHashMap this$0) {
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
        final TObjectIntHashMap this$0;
        
        private MapBackedView(final TObjectIntHashMap this$0) {
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
        
        MapBackedView(final TObjectIntHashMap tObjectIntHashMap, final MapBackedView mapBackedView) {
            this(tObjectIntHashMap);
        }
    }
    
    class TIntValueCollection implements TIntCollection
    {
        final TObjectIntHashMap this$0;
        
        TIntValueCollection(final TObjectIntHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TIntIterator iterator() {
            return new TObjectIntValueHashIterator();
        }
        
        @Override
        public int getNoEntryValue() {
            return this.this$0.no_entry_value;
        }
        
        @Override
        public int size() {
            return TObjectIntHashMap.access$0(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TObjectIntHashMap.access$0(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final int n) {
            return this.this$0.containsValue(n);
        }
        
        @Override
        public int[] toArray() {
            return this.this$0.values();
        }
        
        @Override
        public int[] toArray(final int[] array) {
            return this.this$0.values(array);
        }
        
        @Override
        public boolean add(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final int n) {
            final int[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = values.length;
            while (length-- > 0) {
                if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && n == values[length]) {
                    this.this$0.removeAt(length);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            for (final Integer next : collection) {
                if (!(next instanceof Integer)) {
                    return false;
                }
                if (!this.this$0.containsValue(next)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsValue(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final int[] array) {
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
        public boolean addAll(final TIntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final TIntIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final TIntCollection collection) {
            if (this == collection) {
                return false;
            }
            final TIntIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final int[] array) {
            Arrays.sort(array);
            final int[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = set.length;
            while (length-- > 0) {
                if (set[length] != TObjectIntHashMap.FREE && set[length] != TObjectIntHashMap.REMOVED && Arrays.binarySearch(array, values[length]) < 0) {
                    this.this$0.removeAt(length);
                }
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            for (final Integer next : collection) {
                if (!(next instanceof Integer) || this.remove(next)) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final TIntCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            final TIntIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.remove(iterator.next())) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final int[] array) {
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
        public boolean forEach(final TIntProcedure tIntProcedure) {
            return this.this$0.forEachValue(tIntProcedure);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            this.this$0.forEachValue(new TIntProcedure(sb) {
                private boolean first = true;
                final TIntValueCollection this$1;
                private final StringBuilder val$buf;
                
                @Override
                public boolean execute(final int n) {
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
        
        static TObjectIntHashMap access$0(final TIntValueCollection collection) {
            return collection.this$0;
        }
        
        class TObjectIntValueHashIterator implements TIntIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            final TIntValueCollection this$1;
            
            TObjectIntValueHashIterator(final TIntValueCollection this$1) {
                this.this$1 = this$1;
                this._hash = TIntValueCollection.access$0(this$1);
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            @Override
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            @Override
            public int next() {
                this.moveToNextIndex();
                return TIntValueCollection.access$0(this.this$1)._values[this._index];
            }
            
            @Override
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                this._hash.tempDisableAutoCompaction();
                TIntValueCollection.access$0(this.this$1).removeAt(this._index);
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
                final Object[] set = TIntValueCollection.access$0(this.this$1)._set;
                int index = this._index;
                while (index-- > 0 && (set[index] == TObjectHash.FREE || set[index] == TObjectHash.REMOVED)) {}
                return index;
            }
        }
    }
    
    class TObjectIntHashIterator extends TObjectHashIterator implements TObjectIntIterator
    {
        private final TObjectIntHashMap _map;
        final TObjectIntHashMap this$0;
        
        public TObjectIntHashIterator(final TObjectIntHashMap this$0, final TObjectIntHashMap map) {
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
        public int value() {
            return this._map._values[this._index];
        }
        
        @Override
        public int setValue(final int n) {
            final int value = this.value();
            this._map._values[this._index] = n;
            return value;
        }
    }
}
