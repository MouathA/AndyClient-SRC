package gnu.trove.map.hash;

import gnu.trove.map.*;
import gnu.trove.set.*;
import java.lang.reflect.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;
import gnu.trove.impl.*;
import java.io.*;
import gnu.trove.iterator.*;
import gnu.trove.*;
import gnu.trove.impl.hash.*;
import java.util.*;

public class TCharObjectHashMap extends TCharHash implements TCharObjectMap, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TCharObjectProcedure PUT_ALL_PROC;
    protected transient Object[] _values;
    protected char no_entry_key;
    
    public TCharObjectHashMap() {
        this.PUT_ALL_PROC = new TCharObjectProcedure() {
            final TCharObjectHashMap this$0;
            
            @Override
            public boolean execute(final char c, final Object o) {
                this.this$0.put(c, o);
                return true;
            }
        };
    }
    
    public TCharObjectHashMap(final int n) {
        super(n);
        this.PUT_ALL_PROC = new TCharObjectProcedure() {
            final TCharObjectHashMap this$0;
            
            @Override
            public boolean execute(final char c, final Object o) {
                this.this$0.put(c, o);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
    }
    
    public TCharObjectHashMap(final int n, final float n2) {
        super(n, n2);
        this.PUT_ALL_PROC = new TCharObjectProcedure() {
            final TCharObjectHashMap this$0;
            
            @Override
            public boolean execute(final char c, final Object o) {
                this.this$0.put(c, o);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
    }
    
    public TCharObjectHashMap(final int n, final float n2, final char no_entry_key) {
        super(n, n2);
        this.PUT_ALL_PROC = new TCharObjectProcedure() {
            final TCharObjectHashMap this$0;
            
            @Override
            public boolean execute(final char c, final Object o) {
                this.this$0.put(c, o);
                return true;
            }
        };
        this.no_entry_key = no_entry_key;
    }
    
    public TCharObjectHashMap(final TCharObjectMap tCharObjectMap) {
        this(tCharObjectMap.size(), 0.5f, tCharObjectMap.getNoEntryKey());
        this.putAll(tCharObjectMap);
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._values = new Object[setUp];
        return setUp;
    }
    
    @Override
    protected void rehash(final int n) {
        final int length = this._set.length;
        final char[] set = this._set;
        final Object[] values = this._values;
        final byte[] states = this._states;
        this._set = new char[n];
        this._values = new Object[n];
        this._states = new byte[n];
        int n2 = length;
        while (n2-- > 0) {
            if (states[n2] == 1) {
                this._values[this.insertKey(set[n2])] = values[n2];
            }
        }
    }
    
    @Override
    public char getNoEntryKey() {
        return this.no_entry_key;
    }
    
    @Override
    public boolean containsKey(final char c) {
        return this.contains(c);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final byte[] states = this._states;
        final Object[] values = this._values;
        if (o == null) {
            int length = values.length;
            while (length-- > 0) {
                if (states[length] == 1 && values[length] == null) {
                    return true;
                }
            }
        }
        else {
            int length2 = values.length;
            while (length2-- > 0) {
                if (states[length2] == 1 && (o == values[length2] || o.equals(values[length2]))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Object get(final char c) {
        final int index = this.index(c);
        return (index < 0) ? null : this._values[index];
    }
    
    @Override
    public Object put(final char c, final Object o) {
        return this.doPut(o, this.insertKey(c));
    }
    
    @Override
    public Object putIfAbsent(final char c, final Object o) {
        final int insertKey = this.insertKey(c);
        if (insertKey < 0) {
            return this._values[-insertKey - 1];
        }
        return this.doPut(o, insertKey);
    }
    
    private Object doPut(final Object o, int n) {
        Object o2 = null;
        if (n < 0) {
            n = -n - 1;
            o2 = this._values[n];
        }
        this._values[n] = o;
        if (false) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return o2;
    }
    
    @Override
    public Object remove(final char c) {
        Object o = null;
        final int index = this.index(c);
        if (index >= 0) {
            o = this._values[index];
            this.removeAt(index);
        }
        return o;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._values[n] = null;
        super.removeAt(n);
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Map.Entry<Character, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void putAll(final TCharObjectMap tCharObjectMap) {
        tCharObjectMap.forEachEntry(this.PUT_ALL_PROC);
    }
    
    @Override
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    @Override
    public TCharSet keySet() {
        return new KeyView();
    }
    
    @Override
    public char[] keys() {
        final char[] array = new char[this.size()];
        final char[] set = this._set;
        final byte[] states = this._states;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final char[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = set[length];
            }
        }
        return array;
    }
    
    @Override
    public char[] keys(char[] array) {
        if (array.length < this._size) {
            array = new char[this._size];
        }
        final char[] set = this._set;
        final byte[] states = this._states;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final char[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = set[length];
            }
        }
        return array;
    }
    
    @Override
    public Collection valueCollection() {
        return new ValueView();
    }
    
    @Override
    public Object[] values() {
        final Object[] array = new Object[this.size()];
        final Object[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final Object[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public Object[] values(Object[] array) {
        if (array.length < this._size) {
            array = (Object[])Array.newInstance(array.getClass().getComponentType(), this._size);
        }
        final Object[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final Object[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = values[length];
            }
        }
        return array;
    }
    
    @Override
    public TCharObjectIterator iterator() {
        return new TCharObjectHashIterator(this);
    }
    
    @Override
    public boolean forEachKey(final TCharProcedure tCharProcedure) {
        return this.forEach(tCharProcedure);
    }
    
    @Override
    public boolean forEachValue(final TObjectProcedure tObjectProcedure) {
        final byte[] states = this._states;
        final Object[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tObjectProcedure.execute(values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean forEachEntry(final TCharObjectProcedure tCharObjectProcedure) {
        final byte[] states = this._states;
        final char[] set = this._set;
        final Object[] values = this._values;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tCharObjectProcedure.execute(set[length], values[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean retainEntries(final TCharObjectProcedure tCharObjectProcedure) {
        final byte[] states = this._states;
        final char[] set = this._set;
        final Object[] values = this._values;
        this.tempDisableAutoCompaction();
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tCharObjectProcedure.execute(set[length], values[length])) {
                this.removeAt(length);
            }
        }
        this.reenableAutoCompaction(true);
        return true;
    }
    
    @Override
    public void transformValues(final TObjectFunction tObjectFunction) {
        final byte[] states = this._states;
        final Object[] values = this._values;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                values[length] = tObjectFunction.execute(values[length]);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TCharObjectMap)) {
            return false;
        }
        final TCharObjectMap tCharObjectMap = (TCharObjectMap)o;
        if (tCharObjectMap.size() != this.size()) {
            return false;
        }
        final TCharObjectIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            final char key = iterator.key();
            final Object value = iterator.value();
            if (value == null) {
                if (tCharObjectMap.get(key) != null || !tCharObjectMap.containsKey(key)) {
                    return false;
                }
                continue;
            }
            else {
                if (!value.equals(tCharObjectMap.get(key))) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final Object[] values = this._values;
        final byte[] states = this._states;
        int length = values.length;
        while (length-- > 0) {
            if (states[length] == 1) {
                final int n = 0 + (HashFunctions.hash(this._set[length]) ^ ((values[length] == null) ? 0 : values[length].hashCode()));
            }
        }
        return 0;
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeChar(this.no_entry_key);
        objectOutput.writeInt(this._size);
        int length = this._states.length;
        while (length-- > 0) {
            if (this._states[length] == 1) {
                objectOutput.writeChar(this._set[length]);
                objectOutput.writeObject(this._values[length]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        this.no_entry_key = objectInput.readChar();
        int int1 = objectInput.readInt();
        this.setUp(int1);
        while (int1-- > 0) {
            this.put(objectInput.readChar(), objectInput.readObject());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        this.forEachEntry(new TCharObjectProcedure(sb) {
            private boolean first = true;
            final TCharObjectHashMap this$0;
            private final StringBuilder val$buf;
            
            @Override
            public boolean execute(final char c, final Object o) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    this.val$buf.append(",");
                }
                this.val$buf.append(c);
                this.val$buf.append("=");
                this.val$buf.append(o);
                return true;
            }
        });
        sb.append("}");
        return sb.toString();
    }
    
    static int access$0(final TCharObjectHashMap tCharObjectHashMap) {
        return tCharObjectHashMap._size;
    }
    
    class KeyView implements TCharSet
    {
        final TCharObjectHashMap this$0;
        
        KeyView(final TCharObjectHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public char getNoEntryValue() {
            return this.this$0.no_entry_key;
        }
        
        @Override
        public int size() {
            return TCharObjectHashMap.access$0(this.this$0);
        }
        
        @Override
        public boolean isEmpty() {
            return TCharObjectHashMap.access$0(this.this$0) == 0;
        }
        
        @Override
        public boolean contains(final char c) {
            return this.this$0.containsKey(c);
        }
        
        @Override
        public TCharIterator iterator() {
            return new TCharHashIterator(this.this$0);
        }
        
        @Override
        public char[] toArray() {
            return this.this$0.keys();
        }
        
        @Override
        public char[] toArray(final char[] array) {
            return this.this$0.keys(array);
        }
        
        @Override
        public boolean add(final char c) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final char c) {
            return this.this$0.remove(c) != null;
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            final Iterator<Character> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsKey(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final TCharCollection collection) {
            if (collection == this) {
                return true;
            }
            final TCharIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.this$0.containsKey(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean containsAll(final char[] array) {
            while (0 < array.length) {
                if (!this.this$0.containsKey(array[0])) {
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
        public boolean addAll(final TCharCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final char[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            final TCharIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final TCharCollection collection) {
            if (this == collection) {
                return false;
            }
            final TCharIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final char[] array) {
            Arrays.sort(array);
            final char[] set = this.this$0._set;
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
            for (final Character next : collection) {
                if (!(next instanceof Character) || this.remove(next)) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final TCharCollection collection) {
            if (collection == this) {
                this.clear();
                return true;
            }
            final TCharIterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.remove(iterator.next())) {}
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final char[] array) {
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
        public boolean forEach(final TCharProcedure tCharProcedure) {
            return this.this$0.forEachKey(tCharProcedure);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof TCharSet)) {
                return false;
            }
            final TCharSet set = (TCharSet)o;
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
            int length = this.this$0._states.length;
            while (length-- > 0) {
                if (this.this$0._states[length] == 1) {
                    if (!false) {
                        sb.append(",");
                    }
                    sb.append(this.this$0._set[length]);
                }
            }
            return sb.toString();
        }
        
        class TCharHashIterator extends THashPrimitiveIterator implements TCharIterator
        {
            private final TCharHash _hash;
            final KeyView this$1;
            
            public TCharHashIterator(final KeyView this$1, final TCharHash hash) {
                this.this$1 = this$1;
                super(hash);
                this._hash = hash;
            }
            
            @Override
            public char next() {
                this.moveToNextIndex();
                return this._hash._set[this._index];
            }
        }
    }
    
    private abstract class MapBackedView extends AbstractSet implements Set, Iterable
    {
        final TCharObjectHashMap this$0;
        
        private MapBackedView(final TCharObjectHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public abstract Iterator iterator();
        
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
            final Iterator iterator = this.iterator();
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
            final Iterator iterator = this.iterator();
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
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        MapBackedView(final TCharObjectHashMap tCharObjectHashMap, final MapBackedView mapBackedView) {
            this(tCharObjectHashMap);
        }
    }
    
    class TCharObjectHashIterator extends THashPrimitiveIterator implements TCharObjectIterator
    {
        private final TCharObjectHashMap _map;
        final TCharObjectHashMap this$0;
        
        public TCharObjectHashIterator(final TCharObjectHashMap this$0, final TCharObjectHashMap map) {
            this.this$0 = this$0;
            super(map);
            this._map = map;
        }
        
        @Override
        public void advance() {
            this.moveToNextIndex();
        }
        
        @Override
        public char key() {
            return this._map._set[this._index];
        }
        
        @Override
        public Object value() {
            return this._map._values[this._index];
        }
        
        @Override
        public Object setValue(final Object o) {
            final Object value = this.value();
            this._map._values[this._index] = o;
            return value;
        }
    }
    
    protected class ValueView extends MapBackedView
    {
        final TCharObjectHashMap this$0;
        
        protected ValueView(final TCharObjectHashMap this$0) {
            this.this$0 = this$0.super(null);
        }
        
        @Override
        public Iterator iterator() {
            return new TCharObjectValueHashIterator(this, this.this$0) {
                final ValueView this$1;
                
                @Override
                protected Object objectAtIndex(final int n) {
                    return ValueView.access$0(this.this$1)._values[n];
                }
            };
        }
        
        @Override
        public boolean containsElement(final Object o) {
            return this.this$0.containsValue(o);
        }
        
        @Override
        public boolean removeElement(final Object o) {
            final Object[] values = this.this$0._values;
            final byte[] states = this.this$0._states;
            int length = values.length;
            while (length-- > 0) {
                if (states[length] == 1 && (o == values[length] || (values[length] != null && values[length].equals(o)))) {
                    this.this$0.removeAt(length);
                    return true;
                }
            }
            return false;
        }
        
        static TCharObjectHashMap access$0(final ValueView valueView) {
            return valueView.this$0;
        }
        
        class TCharObjectValueHashIterator extends THashPrimitiveIterator implements Iterator
        {
            protected final TCharObjectHashMap _map;
            final ValueView this$1;
            
            public TCharObjectValueHashIterator(final ValueView this$1, final TCharObjectHashMap map) {
                this.this$1 = this$1;
                super(map);
                this._map = map;
            }
            
            protected Object objectAtIndex(final int n) {
                final byte[] states = ValueView.access$0(this.this$1)._states;
                final Object o = this._map._values[n];
                if (states[n] != 1) {
                    return null;
                }
                return o;
            }
            
            @Override
            public Object next() {
                this.moveToNextIndex();
                return this._map._values[this._index];
            }
        }
    }
}