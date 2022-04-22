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

public class TObjectDoubleHashMap extends TObjectHash implements TObjectDoubleMap, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectDoubleProcedure PUT_ALL_PROC;
    protected transient double[] _values;
    protected double no_entry_value;
    
    public TObjectDoubleHashMap() {
        this.PUT_ALL_PROC = new TObjectDoubleProcedure() {
            final TObjectDoubleHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final double n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    }
    
    public TObjectDoubleHashMap(final int n) {
        super(n);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure() {
            final TObjectDoubleHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final double n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    }
    
    public TObjectDoubleHashMap(final int n, final float n2) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure() {
            final TObjectDoubleHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final double n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    }
    
    public TObjectDoubleHashMap(final int n, final float n2, final double no_entry_value) {
        super(n, n2);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure() {
            final TObjectDoubleHashMap this$0;
            
            @Override
            public boolean execute(final Object o, final double n) {
                this.this$0.put(o, n);
                return true;
            }
        };
        this.no_entry_value = no_entry_value;
        if (this.no_entry_value != 0.0) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectDoubleHashMap(final TObjectDoubleMap tObjectDoubleMap) {
        this(tObjectDoubleMap.size(), 0.5f, tObjectDoubleMap.getNoEntryValue());
        if (tObjectDoubleMap instanceof TObjectDoubleHashMap) {
            final TObjectDoubleHashMap tObjectDoubleHashMap = (TObjectDoubleHashMap)tObjectDoubleMap;
            this._loadFactor = tObjectDoubleHashMap._loadFactor;
            this.no_entry_value = tObjectDoubleHashMap.no_entry_value;
            if (this.no_entry_value != 0.0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(tObjectDoubleMap);
    }
    
    @Override
    public int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new double[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final Object[] set = this._set;
        final double[] values = this._values;
        Arrays.fill(this._set = new Object[n], TObjectDoubleHashMap.FREE);
        Arrays.fill(this._values = new double[n], this.no_entry_value);
        int n2 = length;
        while (n2-- > 0) {
            if (set[n2] != TObjectDoubleHashMap.FREE && set[n2] != TObjectDoubleHashMap.REMOVED) {
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
    public double getNoEntryValue() {
        return this.no_entry_value;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.contains(o);
    }
    
    @Override
    public boolean containsValue(final double n) {
        final Object[] set = this._set;
        final double[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && n == values[length]) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public double get(final Object o) {
        final int index = this.index(o);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    @Override
    public double put(final Object o, final double n) {
        return this.doPut(n, this.insertKey(o));
    }
    
    @Override
    public double putIfAbsent(final Object o, final double n) {
        final int insertKey = this.insertKey(o);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(n, insertKey);
    }
    
    private double doPut(final double n, int n2) {
        double no_entry_value = this.no_entry_value;
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
    public double remove(final Object o) {
        double no_entry_value = this.no_entry_value;
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
            this.put(entry.getKey(), (double)entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TObjectDoubleMap tObjectDoubleMap) {
        tObjectDoubleMap.forEachEntry(this.PUT_ALL_PROC);
    }
    
    @Override
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectDoubleHashMap.FREE);
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
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED) {
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
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED) {
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
    public TDoubleCollection valueCollection() {
        return new TDoubleValueCollection();
    }
    
    @Override
    public double[] values() {
        final double[] array = new double[this.size()];
        final double[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED) {
                final double[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public double[] values(double[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new double[size];
        }
        final double[] values = this._values;
        final Object[] set = this._set;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED) {
                final double[] array2 = array;
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
    public TObjectDoubleIterator iterator() {
        return new TObjectDoubleHashIterator(this);
    }
    
    @Override
    public boolean increment(final Object o) {
        return this.adjustValue(o, 1.0);
    }
    
    @Override
    public boolean adjustValue(final Object o, final double n) {
        final int index = this.index(o);
        if (index < 0) {
            return false;
        }
        final double[] values = this._values;
        final int n2 = index;
        values[n2] += n;
        return true;
    }
    
    @Override
    public double adjustOrPutValue(final Object o, final double n, final double n2) {
        final int insertKey = this.insertKey(o);
        double n6;
        if (insertKey < 0) {
            final int n3 = -insertKey - 1;
            final double[] values = this._values;
            final int n4 = n3;
            final double n5 = values[n4] + n;
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
    public boolean forEachValue(final TDoubleProcedure tDoubleProcedure) {
        final Object[] set = this._set;
        final double[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && !tDoubleProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TObjectDoubleProcedure tObjectDoubleProcedure) {
        final Object[] set = this._set;
        final double[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && !tObjectDoubleProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean retainEntries(final TObjectDoubleProcedure tObjectDoubleProcedure) {
        final Object[] set = this._set;
        final double[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && !tObjectDoubleProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public void transformValues(final TDoubleFunction tDoubleFunction) {
        final Object[] set = this._set;
        final double[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != null && set[length] != TObjectDoubleHashMap.REMOVED) {
                values[length] = tDoubleFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TObjectDoubleMap)) {
            return false;
        }
        final TObjectDoubleMap tObjectDoubleMap = (TObjectDoubleMap)o;
        if (tObjectDoubleMap.size() != this.size()) {
            return false;
        }
        final TObjectDoubleIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            final Object key = iterator.key();
            final double value = iterator.value();
            if (value == this.no_entry_value) {
                if (tObjectDoubleMap.get(key) != tObjectDoubleMap.getNoEntryValue() || !tObjectDoubleMap.containsKey(key)) {
                    return false;
                }
                continue;
            }
            else {
                if (value != tObjectDoubleMap.get(key)) {
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
        final double[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED) {
                final int n = 0 + (HashFunctions.hash(values[length]) ^ ((set[length] == null) ? 0 : set[length].hashCode()));
            }
        }
        return 0;
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeDouble(this.no_entry_value);
        objectOutput.writeInt(this._size);
        int length = this._set.length;
        while (length-- > 0) {
            if (this._set[length] != TObjectDoubleHashMap.REMOVED && this._set[length] != TObjectDoubleHashMap.FREE) {
                objectOutput.writeObject(this._set[length]);
                objectOutput.writeDouble(this._values[length]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        this.no_entry_value = objectInput.readDouble();
        int int1 = objectInput.readInt();
        this.setUp(int1);
        while (int1-- > 0) {
            this.put(objectInput.readObject(), objectInput.readDouble());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        this.forEachEntry(new TObjectDoubleProcedure(sb) {
            private boolean first = true;
            final TObjectDoubleHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final Object o, final double n) {
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
    
    static int access$0(final TObjectDoubleHashMap tObjectDoubleHashMap) {
        return tObjectDoubleHashMap._size;
    }
    
    protected class KeyView extends MapBackedView
    {
        final TObjectDoubleHashMap this$0;
        
        protected KeyView(final TObjectDoubleHashMap this$0) {
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
        final TObjectDoubleHashMap this$0;
        
        private MapBackedView(final TObjectDoubleHashMap this$0) {
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
        
        MapBackedView(final TObjectDoubleHashMap tObjectDoubleHashMap, final MapBackedView mapBackedView) {
            this(tObjectDoubleHashMap);
        }
    }
    
    class TDoubleValueCollection implements TDoubleCollection
    {
        final TObjectDoubleHashMap this$0;
        
        TDoubleValueCollection(final TObjectDoubleHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TDoubleIterator iterator() {
            return new TObjectDoubleValueHashIterator();
        }
        
        @Override
        public double getNoEntryValue() {
            return this.this$0.no_entry_value;
        }
        
        @Override
        public int size() {
            return TObjectDoubleHashMap.access$0(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TObjectDoubleHashMap.access$0(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final double n) {
            return this.this$0.containsValue(n);
        }
        
        @Override
        public double[] toArray() {
            return this.this$0.values();
        }
        
        @Override
        public double[] toArray(final double[] array) {
            return this.this$0.values(array);
        }
        
        @Override
        public boolean add(final double n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final double n) {
            final double[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = values.length;
            while (length-- > 0) {
                if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && n == values[length]) {
                    this.this$0.removeAt(length);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            for (final Double next : collection) {
                if (!(next instanceof Double)) {
                    return false;
                }
                if (!this.this$0.containsValue(next)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TDoubleCollection collection) {
            final TDoubleIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsValue(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final double[] array) {
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
        public boolean addAll(final TDoubleCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final double[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final TDoubleIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final TDoubleCollection collection) {
            if (this == collection) {
                return false;
            }
            final TDoubleIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final double[] array) {
            Arrays.sort(array);
            final double[] values = this.this$0._values;
            final Object[] set = this.this$0._set;
            int length = set.length;
            while (length-- > 0) {
                if (set[length] != TObjectDoubleHashMap.FREE && set[length] != TObjectDoubleHashMap.REMOVED && Arrays.binarySearch(array, values[length]) < 0) {
                    this.this$0.removeAt(length);
                }
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            for (final Double next : collection) {
                if (!(next instanceof Double) || this.remove(next)) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final TDoubleCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            final TDoubleIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.remove(iterator.next())) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final double[] array) {
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
        public boolean forEach(final TDoubleProcedure tDoubleProcedure) {
            return this.this$0.forEachValue(tDoubleProcedure);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            this.this$0.forEachValue(new TDoubleProcedure(sb) {
                private boolean first = true;
                final TDoubleValueCollection this$1;
                private final StringBuilder val$buf;
                
                @Override
                public boolean execute(final double n) {
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
        
        static TObjectDoubleHashMap access$0(final TDoubleValueCollection collection) {
            return collection.this$0;
        }
        
        class TObjectDoubleValueHashIterator implements TDoubleIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            final TDoubleValueCollection this$1;
            
            TObjectDoubleValueHashIterator(final TDoubleValueCollection this$1) {
                this.this$1 = this$1;
                this._hash = TDoubleValueCollection.access$0(this$1);
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            @Override
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            @Override
            public double next() {
                this.moveToNextIndex();
                return TDoubleValueCollection.access$0(this.this$1)._values[this._index];
            }
            
            @Override
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                this._hash.tempDisableAutoCompaction();
                TDoubleValueCollection.access$0(this.this$1).removeAt(this._index);
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
                final Object[] set = TDoubleValueCollection.access$0(this.this$1)._set;
                int index = this._index;
                while (index-- > 0 && (set[index] == TObjectHash.FREE || set[index] == TObjectHash.REMOVED)) {}
                return index;
            }
        }
    }
    
    class TObjectDoubleHashIterator extends TObjectHashIterator implements TObjectDoubleIterator
    {
        private final TObjectDoubleHashMap _map;
        final TObjectDoubleHashMap this$0;
        
        public TObjectDoubleHashIterator(final TObjectDoubleHashMap this$0, final TObjectDoubleHashMap map) {
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
        public double value() {
            return this._map._values[this._index];
        }
        
        @Override
        public double setValue(final double n) {
            final double value = this.value();
            this._map._values[this._index] = n;
            return value;
        }
    }
}
