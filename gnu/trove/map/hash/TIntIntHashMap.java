package gnu.trove.map.hash;

import gnu.trove.map.*;
import gnu.trove.set.*;
import gnu.trove.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;
import gnu.trove.impl.*;
import java.io.*;
import gnu.trove.impl.hash.*;
import gnu.trove.iterator.*;
import java.util.*;

public class TIntIntHashMap extends TIntIntHash implements TIntIntMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient int[] _values;
    
    public TIntIntHashMap() {
    }
    
    public TIntIntHashMap(final int n) {
        super(n);
    }
    
    public TIntIntHashMap(final int n, final float n2) {
        super(n, n2);
    }
    
    public TIntIntHashMap(final int n, final float n2, final int n3, final int n4) {
        super(n, n2, n3, n4);
    }
    
    public TIntIntHashMap(final int[] array, final int[] array2) {
        super(Math.max(array.length, array2.length));
        while (0 < Math.min(array.length, array2.length)) {
            this.put(array[0], array2[0]);
            int n = 0;
            ++n;
        }
    }
    
    public TIntIntHashMap(final TIntIntMap tIntIntMap) {
        super(tIntIntMap.size());
        if (tIntIntMap instanceof TIntIntHashMap) {
            final TIntIntHashMap tIntIntHashMap = (TIntIntHashMap)tIntIntMap;
            this._loadFactor = tIntIntHashMap._loadFactor;
            this.no_entry_key = tIntIntHashMap.no_entry_key;
            this.no_entry_value = tIntIntHashMap.no_entry_value;
            if (this.no_entry_key != 0) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(tIntIntMap);
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new int[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final int[] set = this._set;
        final int[] values = this._values;
        final byte[] states = this._states;
        this._set = new int[n];
        this._values = new int[n];
        this._states = new byte[n];
        int n2 = length;
        while (n2-- > 0) {
            if (states[n2] == 1) {
                this._values[this.insertKey(set[n2])] = values[n2];
            }
        }
    }
    
    @Override
    public int put(final int n, final int n2) {
        return this.doPut(n, n2, this.insertKey(n));
    }
    
    @Override
    public int putIfAbsent(final int n, final int n2) {
        final int insertKey = this.insertKey(n);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(n, n2, insertKey);
    }
    
    private int doPut(final int n, final int n2, int n3) {
        int no_entry_value = this.no_entry_value;
        if (n3 < 0) {
            n3 = -n3 - 1;
            no_entry_value = this._values[n3];
        }
        this._values[n3] = n2;
        if (false) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return no_entry_value;
    }
    
    @Override
    public void putAll(final Map map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<Integer, V> entry : map.entrySet()) {
            this.put(entry.getKey(), (int)entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TIntIntMap tIntIntMap) {
        this.ensureCapacity(tIntIntMap.size());
        final TIntIntIterator iterator = tIntIntMap.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            this.put(iterator.key(), iterator.value());
        }
    }
    
    @Override
    public int get(final int n) {
        final int index = this.index(n);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    @Override
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
    }
    
    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }
    
    @Override
    public int remove(final int n) {
        int no_entry_value = this.no_entry_value;
        final int index = this.index(n);
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
    public TIntSet keySet() {
        return new TKeyView();
    }
    
    @Override
    public int[] keys() {
        final int[] array = new int[this.size()];
        final int[] set = this._set;
        final byte[] states = this._states;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = set[length];
            }
        }
        return array;
    }
    
    @Override
    public int[] keys(int[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] set = this._set;
        final byte[] states = this._states;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final int[] array2 = array;
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
        return new TValueView();
    }
    
    @Override
    public int[] values() {
        final int[] array = new int[this.size()];
        final int[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
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
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
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
    public boolean containsValue(final int n) {
        final byte[] states = this._states;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1 && n == values[length]) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(final int n) {
        return this.contains(n);
    }
    
    @Override
    public TIntIntIterator iterator() {
        return new TIntIntHashIterator(this);
    }
    
    @Override
    public boolean forEachKey(final TIntProcedure tIntProcedure) {
        return this.forEach(tIntProcedure);
    }
    
    @Override
    public boolean forEachValue(final TIntProcedure tIntProcedure) {
        final byte[] states = this._states;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TIntIntProcedure tIntIntProcedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntIntProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void transformValues(final TIntFunction tIntFunction) {
        final byte[] states = this._states;
        final int[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                values[length] = tIntFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean retainEntries(final TIntIntProcedure tIntIntProcedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntIntProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public boolean increment(final int n) {
        return this.adjustValue(n, 1);
    }
    
    @Override
    public boolean adjustValue(final int n, final int n2) {
        final int index = this.index(n);
        if (index < 0) {
            return false;
        }
        final int[] values = this._values;
        final int n3 = index;
        values[n3] += n2;
        return true;
    }
    
    @Override
    public int adjustOrPutValue(final int n, final int n2, final int n3) {
        int insertKey = this.insertKey(n);
        int n6;
        if (insertKey < 0) {
            insertKey = -insertKey - 1;
            final int[] values = this._values;
            final int n4 = insertKey;
            final int n5 = values[n4] + n2;
            values[n4] = n5;
            n6 = n5;
        }
        else {
            this._values[insertKey] = n3;
            n6 = n3;
        }
        final byte b = this._states[insertKey];
        if (true) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return n6;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TIntIntMap)) {
            return false;
        }
        final TIntIntMap tIntIntMap = (TIntIntMap)o;
        if (tIntIntMap.size() != this.size()) {
            return false;
        }
        final int[] values = this._values;
        final byte[] states = this._states;
        final int noEntryValue = this.getNoEntryValue();
        final int noEntryValue2 = tIntIntMap.getNoEntryValue();
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final int value = tIntIntMap.get(this._set[length]);
                final int n = values[length];
                if (n != value && n != noEntryValue && value != noEntryValue2) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final byte[] states = this._states;
        int length = this._values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final int n = 0 + (HashFunctions.hash(this._set[length]) ^ HashFunctions.hash(this._values[length]));
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        this.forEachEntry(new TIntIntProcedure(sb) {
            private boolean first = true;
            final TIntIntHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final int n, final int n2) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    this.val$buf.append(", ");
                }
                this.val$buf.append(n);
                this.val$buf.append("=");
                this.val$buf.append(n2);
                return true;
            }
        });
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeInt(this._size);
        int length = this._states.length;
        while (length-- > 0) {
            if (this._states[length] == 1) {
                objectOutput.writeInt(this._set[length]);
                objectOutput.writeInt(this._values[length]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        int int1 = objectInput.readInt();
        this.setUp(int1);
        while (int1-- > 0) {
            this.put(objectInput.readInt(), objectInput.readInt());
        }
    }
    
    static int access$0(final TIntIntHashMap tIntIntHashMap) {
        return tIntIntHashMap.no_entry_key;
    }
    
    static int access$1(final TIntIntHashMap tIntIntHashMap) {
        return tIntIntHashMap._size;
    }
    
    static int access$2(final TIntIntHashMap tIntIntHashMap) {
        return tIntIntHashMap.no_entry_value;
    }
    
    class TIntIntHashIterator extends THashPrimitiveIterator implements TIntIntIterator
    {
        final TIntIntHashMap this$0;
        
        TIntIntHashIterator(final TIntIntHashMap this$0, final TIntIntHashMap tIntIntHashMap) {
            this.this$0 = this$0;
            super(tIntIntHashMap);
        }
        
        @Override
        public void advance() {
            this.moveToNextIndex();
        }
        
        @Override
        public int key() {
            return this.this$0._set[this._index];
        }
        
        @Override
        public int value() {
            return this.this$0._values[this._index];
        }
        
        @Override
        public int setValue(final int n) {
            final int value = this.value();
            this.this$0._values[this._index] = n;
            return value;
        }
        
        @Override
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            this._hash.tempDisableAutoCompaction();
            this.this$0.removeAt(this._index);
            this._hash.reenableAutoCompaction(false);
            --this._expectedSize;
        }
    }
    
    class TIntIntKeyHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        final TIntIntHashMap this$0;
        
        TIntIntKeyHashIterator(final TIntIntHashMap this$0, final TPrimitiveHash tPrimitiveHash) {
            this.this$0 = this$0;
            super(tPrimitiveHash);
        }
        
        @Override
        public int next() {
            this.moveToNextIndex();
            return this.this$0._set[this._index];
        }
        
        @Override
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            this._hash.tempDisableAutoCompaction();
            this.this$0.removeAt(this._index);
            this._hash.reenableAutoCompaction(false);
            --this._expectedSize;
        }
    }
    
    class TIntIntValueHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        final TIntIntHashMap this$0;
        
        TIntIntValueHashIterator(final TIntIntHashMap this$0, final TPrimitiveHash tPrimitiveHash) {
            this.this$0 = this$0;
            super(tPrimitiveHash);
        }
        
        @Override
        public int next() {
            this.moveToNextIndex();
            return this.this$0._values[this._index];
        }
        
        @Override
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            this._hash.tempDisableAutoCompaction();
            this.this$0.removeAt(this._index);
            this._hash.reenableAutoCompaction(false);
            --this._expectedSize;
        }
    }
    
    protected class TKeyView implements TIntSet
    {
        final TIntIntHashMap this$0;
        
        protected TKeyView(final TIntIntHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TIntIterator iterator() {
            return this.this$0.new TIntIntKeyHashIterator(this.this$0);
        }
        
        @Override
        public int getNoEntryValue() {
            return TIntIntHashMap.access$0(this.this$0);
        }
        
        @Override
        public int size() {
            return TIntIntHashMap.access$1(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TIntIntHashMap.access$1(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final int n) {
            return this.this$0.contains(n);
        }
        
        @Override
        public int[] toArray() {
            return this.this$0.keys();
        }
        
        @Override
        public int[] toArray(final int[] array) {
            return this.this$0.keys(array);
        }
        
        @Override
        public boolean add(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final int n) {
            return TIntIntHashMap.access$2(this.this$0) != this.this$0.remove(n);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            for (final Integer next : collection) {
                if (!(next instanceof Integer)) {
                    return false;
                }
                if (!this.this$0.containsKey(next)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsKey(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final int[] array) {
            while (0 < array.length) {
                if (!this.this$0.contains(array[0])) {
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
            final int[] set = this.this$0._set;
            final byte[] states = this.this$0._states;
            int length = set.length;
            while (length-- > 0) {
                if (states[length] == 1 && Arrays.binarySearch(array, set[length]) < 0) {
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
            return this.this$0.forEachKey(tIntProcedure);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof TIntSet)) {
                return false;
            }
            final TIntSet set = (TIntSet)o;
            if (set.size() != this.size()) {
                return false;
            }
            int length = this.this$0._states.length;
            while (length-- > 0) {
                if (this.this$0._states[length] == 1 && !set.contains(this.this$0._set[length])) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            int length = this.this$0._states.length;
            while (length-- > 0) {
                if (this.this$0._states[length] == 1) {
                    final int n = 0 + HashFunctions.hash(this.this$0._set[length]);
                }
            }
            return 0;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            this.this$0.forEachKey(new TIntProcedure(sb) {
                private boolean first = true;
                final TKeyView this$1;
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
    }
    
    protected class TValueView implements TIntCollection
    {
        final TIntIntHashMap this$0;
        
        protected TValueView(final TIntIntHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TIntIterator iterator() {
            return this.this$0.new TIntIntValueHashIterator(this.this$0);
        }
        
        @Override
        public int getNoEntryValue() {
            return TIntIntHashMap.access$2(this.this$0);
        }
        
        @Override
        public int size() {
            return TIntIntHashMap.access$1(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TIntIntHashMap.access$1(this.this$0) == 0;
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
            final byte[] states = this.this$0._states;
            int length = values.length;
            while (length-- > 0) {
                if (states[length] == 1 && Arrays.binarySearch(array, values[length]) < 0) {
                    this.this$0.removeAt(length);
                }
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
            //     6: astore          4
            //     8: goto            41
            //    11: aload           4
            //    13: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    18: astore_3       
            //    19: aload_3        
            //    20: instanceof      Ljava/lang/Integer;
            //    23: ifeq            41
            //    26: aload_3        
            //    27: checkcast       Ljava/lang/Integer;
            //    30: invokevirtual   java/lang/Integer.intValue:()I
            //    33: istore          5
            //    35: aload_0        
            //    36: iload           5
            //    38: ifeq            41
            //    41: aload           4
            //    43: invokeinterface java/util/Iterator.hasNext:()Z
            //    48: ifne            11
            //    51: iconst_1       
            //    52: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0041 (coming from #0038).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public boolean removeAll(final TIntCollection p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: aload_1        
            //     2: if_acmpne       11
            //     5: aload_0        
            //     6: invokevirtual   gnu/trove/map/hash/TIntIntHashMap$TValueView.clear:()V
            //     9: iconst_1       
            //    10: ireturn        
            //    11: aload_1        
            //    12: invokeinterface gnu/trove/TIntCollection.iterator:()Lgnu/trove/iterator/TIntIterator;
            //    17: astore_3       
            //    18: goto            35
            //    21: aload_3        
            //    22: invokeinterface gnu/trove/iterator/TIntIterator.next:()I
            //    27: istore          4
            //    29: aload_0        
            //    30: iload           4
            //    32: ifeq            35
            //    35: aload_3        
            //    36: invokeinterface gnu/trove/iterator/TIntIterator.hasNext:()Z
            //    41: ifne            21
            //    44: iconst_1       
            //    45: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0035 (coming from #0032).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public boolean removeAll(final int[] p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: arraylength    
            //     2: istore_3       
            //     3: goto            13
            //     6: aload_0        
            //     7: aload_1        
            //     8: iload_3        
            //     9: iaload         
            //    10: ifeq            13
            //    13: iload_3        
            //    14: iinc            3, -1
            //    17: ifgt            6
            //    20: iconst_1       
            //    21: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0013 (coming from #0010).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
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
                final TValueView this$1;
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
    }
}
