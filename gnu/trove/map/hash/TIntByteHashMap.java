package gnu.trove.map.hash;

import gnu.trove.map.*;
import gnu.trove.set.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;
import gnu.trove.impl.*;
import java.io.*;
import gnu.trove.impl.hash.*;
import gnu.trove.iterator.*;
import java.util.*;
import gnu.trove.*;

public class TIntByteHashMap extends TIntByteHash implements TIntByteMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient byte[] _values;
    
    public TIntByteHashMap() {
    }
    
    public TIntByteHashMap(final int n) {
        super(n);
    }
    
    public TIntByteHashMap(final int n, final float n2) {
        super(n, n2);
    }
    
    public TIntByteHashMap(final int n, final float n2, final int n3, final byte b) {
        super(n, n2, n3, b);
    }
    
    public TIntByteHashMap(final int[] array, final byte[] array2) {
        super(Math.max(array.length, array2.length));
        while (0 < Math.min(array.length, array2.length)) {
            this.put(array[0], array2[0]);
            int n = 0;
            ++n;
        }
    }
    
    public TIntByteHashMap(final TIntByteMap tIntByteMap) {
        super(tIntByteMap.size());
        if (tIntByteMap instanceof TIntByteHashMap) {
            final TIntByteHashMap tIntByteHashMap = (TIntByteHashMap)tIntByteMap;
            this._loadFactor = tIntByteHashMap._loadFactor;
            this.no_entry_key = tIntByteHashMap.no_entry_key;
            this.no_entry_value = tIntByteHashMap.no_entry_value;
            if (this.no_entry_key != 0) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(tIntByteMap);
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new byte[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final int[] set = this._set;
        final byte[] values = this._values;
        final byte[] states = this._states;
        this._set = new int[n];
        this._values = new byte[n];
        this._states = new byte[n];
        int n2 = length;
        while (n2-- > 0) {
            if (states[n2] == 1) {
                this._values[this.insertKey(set[n2])] = values[n2];
            }
        }
    }
    
    @Override
    public byte put(final int n, final byte b) {
        return this.doPut(n, b, this.insertKey(n));
    }
    
    @Override
    public byte putIfAbsent(final int n, final byte b) {
        final int insertKey = this.insertKey(n);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(n, b, insertKey);
    }
    
    private byte doPut(final int n, final byte b, int n2) {
        byte no_entry_value = this.no_entry_value;
        if (n2 < 0) {
            n2 = -n2 - 1;
            no_entry_value = this._values[n2];
        }
        this._values[n2] = b;
        if (false) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return no_entry_value;
    }
    
    @Override
    public void putAll(final Map map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<Integer, V> entry : map.entrySet()) {
            this.put(entry.getKey(), (byte)entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TIntByteMap tIntByteMap) {
        this.ensureCapacity(tIntByteMap.size());
        final TIntByteIterator iterator = tIntByteMap.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            this.put(iterator.key(), iterator.value());
        }
    }
    
    @Override
    public byte get(final int n) {
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
    public byte remove(final int n) {
        byte no_entry_value = this.no_entry_value;
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
    public TByteCollection valueCollection() {
        return new TValueView();
    }
    
    @Override
    public byte[] values() {
        final byte[] array = new byte[this.size()];
        final byte[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final byte[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public byte[] values(byte[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new byte[size];
        }
        final byte[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final byte[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public boolean containsValue(final byte b) {
        final byte[] states = this._states;
        final byte[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1 && b == values[length]) {
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
    public TIntByteIterator iterator() {
        return new TIntByteHashIterator(this);
    }
    
    @Override
    public boolean forEachKey(final TIntProcedure tIntProcedure) {
        return this.forEach(tIntProcedure);
    }
    
    @Override
    public boolean forEachValue(final TByteProcedure tByteProcedure) {
        final byte[] states = this._states;
        final byte[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tByteProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TIntByteProcedure tIntByteProcedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final byte[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntByteProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void transformValues(final TByteFunction tByteFunction) {
        final byte[] states = this._states;
        final byte[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                values[length] = tByteFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean retainEntries(final TIntByteProcedure tIntByteProcedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final byte[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntByteProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public boolean increment(final int n) {
        return this.adjustValue(n, (byte)1);
    }
    
    @Override
    public boolean adjustValue(final int n, final byte b) {
        final int index = this.index(n);
        if (index < 0) {
            return false;
        }
        final byte[] values = this._values;
        final int n2 = index;
        values[n2] += b;
        return true;
    }
    
    @Override
    public byte adjustOrPutValue(final int n, final byte b, final byte b2) {
        int insertKey = this.insertKey(n);
        byte b4;
        if (insertKey < 0) {
            insertKey = -insertKey - 1;
            final byte[] values = this._values;
            final int n2 = insertKey;
            final byte b3 = (byte)(values[n2] + b);
            values[n2] = b3;
            b4 = b3;
        }
        else {
            this._values[insertKey] = b2;
            b4 = b2;
        }
        final byte b5 = this._states[insertKey];
        if (true) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return b4;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TIntByteMap)) {
            return false;
        }
        final TIntByteMap tIntByteMap = (TIntByteMap)o;
        if (tIntByteMap.size() != this.size()) {
            return false;
        }
        final byte[] values = this._values;
        final byte[] states = this._states;
        final byte noEntryValue = this.getNoEntryValue();
        final byte noEntryValue2 = tIntByteMap.getNoEntryValue();
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final byte value = tIntByteMap.get(this._set[length]);
                final byte b = values[length];
                if (b != value && b != noEntryValue && value != noEntryValue2) {
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
        this.forEachEntry(new TIntByteProcedure(sb) {
            private boolean first = true;
            final TIntByteHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final int n, final byte b) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    this.val$buf.append(", ");
                }
                this.val$buf.append(n);
                this.val$buf.append("=");
                this.val$buf.append(b);
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
                objectOutput.writeByte(this._values[length]);
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
            this.put(objectInput.readInt(), objectInput.readByte());
        }
    }
    
    static int access$0(final TIntByteHashMap tIntByteHashMap) {
        return tIntByteHashMap.no_entry_key;
    }
    
    static int access$1(final TIntByteHashMap tIntByteHashMap) {
        return tIntByteHashMap._size;
    }
    
    static byte access$2(final TIntByteHashMap tIntByteHashMap) {
        return tIntByteHashMap.no_entry_value;
    }
    
    class TIntByteHashIterator extends THashPrimitiveIterator implements TIntByteIterator
    {
        final TIntByteHashMap this$0;
        
        TIntByteHashIterator(final TIntByteHashMap this$0, final TIntByteHashMap tIntByteHashMap) {
            this.this$0 = this$0;
            super(tIntByteHashMap);
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
        public byte value() {
            return this.this$0._values[this._index];
        }
        
        @Override
        public byte setValue(final byte b) {
            final byte value = this.value();
            this.this$0._values[this._index] = b;
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
    
    class TIntByteKeyHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        final TIntByteHashMap this$0;
        
        TIntByteKeyHashIterator(final TIntByteHashMap this$0, final TPrimitiveHash tPrimitiveHash) {
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
    
    class TIntByteValueHashIterator extends THashPrimitiveIterator implements TByteIterator
    {
        final TIntByteHashMap this$0;
        
        TIntByteValueHashIterator(final TIntByteHashMap this$0, final TPrimitiveHash tPrimitiveHash) {
            this.this$0 = this$0;
            super(tPrimitiveHash);
        }
        
        @Override
        public byte next() {
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
        final TIntByteHashMap this$0;
        
        protected TKeyView(final TIntByteHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TIntIterator iterator() {
            return this.this$0.new TIntByteKeyHashIterator(this.this$0);
        }
        
        @Override
        public int getNoEntryValue() {
            return TIntByteHashMap.access$0(this.this$0);
        }
        
        @Override
        public int size() {
            return TIntByteHashMap.access$1(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TIntByteHashMap.access$1(this.this$0) == 0;
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
            return TIntByteHashMap.access$2(this.this$0) != this.this$0.remove(n);
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
    
    protected class TValueView implements TByteCollection
    {
        final TIntByteHashMap this$0;
        
        protected TValueView(final TIntByteHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public TByteIterator iterator() {
            return this.this$0.new TIntByteValueHashIterator(this.this$0);
        }
        
        @Override
        public byte getNoEntryValue() {
            return TIntByteHashMap.access$2(this.this$0);
        }
        
        @Override
        public int size() {
            return TIntByteHashMap.access$1(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TIntByteHashMap.access$1(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final byte b) {
            return this.this$0.containsValue(b);
        }
        
        @Override
        public byte[] toArray() {
            return this.this$0.values();
        }
        
        @Override
        public byte[] toArray(final byte[] array) {
            return this.this$0.values(array);
        }
        
        @Override
        public boolean add(final byte b) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final byte b) {
            final byte[] values = this.this$0._values;
            final int[] set = this.this$0._set;
            int length = values.length;
            while (length-- > 0) {
                if (set[length] != 0 && set[length] != 2 && b == values[length]) {
                    this.this$0.removeAt(length);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            for (final Byte next : collection) {
                if (!(next instanceof Byte)) {
                    return false;
                }
                if (!this.this$0.containsValue(next)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TByteCollection collection) {
            final TByteIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsValue(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final byte[] array) {
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
        public boolean addAll(final TByteCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final byte[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final TByteIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final TByteCollection collection) {
            if (this == collection) {
                return false;
            }
            final TByteIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final byte[] array) {
            Arrays.sort(array);
            final byte[] values = this.this$0._values;
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
        public boolean removeAll(final Collection collection) {
            for (final Byte next : collection) {
                if (!(next instanceof Byte) || this.remove(next)) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final TByteCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            final TByteIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.remove(iterator.next())) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final byte[] array) {
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
        public boolean forEach(final TByteProcedure tByteProcedure) {
            return this.this$0.forEachValue(tByteProcedure);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            this.this$0.forEachValue(new TByteProcedure(sb) {
                private boolean first = true;
                final TValueView this$1;
                private final StringBuilder val$buf;
                
                @Override
                public boolean execute(final byte b) {
                    if (this.first) {
                        this.first = false;
                    }
                    else {
                        this.val$buf.append(", ");
                    }
                    this.val$buf.append(b);
                    return true;
                }
            });
            sb.append("}");
            return sb.toString();
        }
    }
}
