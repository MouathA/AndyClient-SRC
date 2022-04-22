package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.util.*;

public class IntOpenHashSet extends AbstractIntSet implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    
    public IntOpenHashSet(final int n, final float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        final int arraySize = HashCommon.arraySize(n, f);
        this.n = arraySize;
        this.minN = arraySize;
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new int[this.n + 1];
    }
    
    public IntOpenHashSet(final int n) {
        this(n, 0.75f);
    }
    
    public IntOpenHashSet() {
        this(16, 0.75f);
    }
    
    public IntOpenHashSet(final Collection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public IntOpenHashSet(final Collection collection) {
        this(collection, 0.75f);
    }
    
    public IntOpenHashSet(final IntCollection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public IntOpenHashSet(final IntCollection collection) {
        this(collection, 0.75f);
    }
    
    public IntOpenHashSet(final IntIterator intIterator, final float n) {
        this(16, n);
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }
    
    public IntOpenHashSet(final IntIterator intIterator) {
        this(intIterator, 0.75f);
    }
    
    public IntOpenHashSet(final Iterator iterator, final float n) {
        this(IntIterators.asIntIterator(iterator), n);
    }
    
    public IntOpenHashSet(final Iterator iterator) {
        this(IntIterators.asIntIterator(iterator));
    }
    
    public IntOpenHashSet(final int[] array, final int n, final int n2, final float n3) {
        this((n2 < 0) ? 0 : n2, n3);
        IntArrays.ensureOffsetLength(array, n, n2);
        while (0 < n2) {
            this.add(array[n + 0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public IntOpenHashSet(final int[] array, final int n, final int n2) {
        this(array, n, n2, 0.75f);
    }
    
    public IntOpenHashSet(final int[] array, final float n) {
        this(array, 0, array.length, n);
    }
    
    public IntOpenHashSet(final int[] array) {
        this(array, 0.75f);
    }
    
    public static IntOpenHashSet of() {
        return new IntOpenHashSet();
    }
    
    public static IntOpenHashSet of(final int n) {
        final IntOpenHashSet set = new IntOpenHashSet(1, 0.75f);
        set.add(n);
        return set;
    }
    
    public static IntOpenHashSet of(final int n, final int n2) {
        final IntOpenHashSet set = new IntOpenHashSet(2, 0.75f);
        set.add(n);
        if (!set.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        return set;
    }
    
    public static IntOpenHashSet of(final int n, final int n2, final int n3) {
        final IntOpenHashSet set = new IntOpenHashSet(3, 0.75f);
        set.add(n);
        if (!set.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        if (!set.add(n3)) {
            throw new IllegalArgumentException("Duplicate element: " + n3);
        }
        return set;
    }
    
    public static IntOpenHashSet of(final int... array) {
        final IntOpenHashSet set = new IntOpenHashSet(array.length, 0.75f);
        while (0 < array.length) {
            final int n = array[0];
            if (!set.add(n)) {
                throw new IllegalArgumentException("Duplicate element " + n);
            }
            int n2 = 0;
            ++n2;
        }
        return set;
    }
    
    public static IntOpenHashSet toSet(final IntStream intStream) {
        return intStream.collect(IntOpenHashSet::new, IntOpenHashSet::add, IntOpenHashSet::addAll);
    }
    
    public static IntOpenHashSet toSetWithExpectedSize(final IntStream intStream, final int n) {
        if (n <= 16) {
            return toSet(intStream);
        }
        return intStream.collect(new IntCollections.SizeDecreasingSupplier(n, IntOpenHashSet::lambda$toSetWithExpectedSize$0), IntOpenHashSet::add, IntOpenHashSet::addAll);
    }
    
    private int realSize() {
        return this.containsNull ? (this.size - 1) : this.size;
    }
    
    private void ensureCapacity(final int n) {
        final int arraySize = HashCommon.arraySize(n, this.f);
        if (arraySize > this.n) {
            this.rehash(arraySize);
        }
    }
    
    private void tryCapacity(final long n) {
        final int n2 = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(n / this.f))));
        if (n2 > this.n) {
            this.rehash(n2);
        }
    }
    
    @Override
    public boolean addAll(final IntCollection collection) {
        if (this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        }
        else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        if (this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        }
        else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }
    
    @Override
    public boolean add(final int n) {
        if (n == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        }
        else {
            final int[] key = this.key;
            int n3;
            final int n2;
            if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n2 == n) {
                    return false;
                }
                int n4;
                while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                    if (n4 == n) {
                        return false;
                    }
                }
            }
            key[n3] = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return true;
    }
    
    protected final void shiftKeys(int n) {
        final int[] key = this.key;
        int n2 = 0;
    Label_0006:
        while (true) {
            n = ((n2 = n) + 1 & this.mask);
            int n3;
            while ((n3 = key[n]) != 0) {
                final int n4 = HashCommon.mix(n3) & this.mask;
                Label_0087: {
                    if (n2 <= n) {
                        if (n2 >= n4) {
                            break Label_0087;
                        }
                        if (n4 > n) {
                            break Label_0087;
                        }
                    }
                    else if (n2 >= n4 && n4 > n) {
                        break Label_0087;
                    }
                    n = (n + 1 & this.mask);
                    continue;
                }
                key[n2] = n3;
                continue Label_0006;
            }
            break;
        }
        key[n2] = 0;
    }
    
    private boolean removeEntry(final int n) {
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return true;
    }
    
    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = 0;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return true;
    }
    
    @Override
    public boolean remove(final int n) {
        if (n == 0) {
            return this.containsNull && this.removeNullEntry();
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return false;
        }
        if (n == n2) {
            return this.removeEntry(n3);
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return this.removeEntry(n3);
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(final int n) {
        if (n == 0) {
            return this.containsNull;
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return false;
        }
        if (n == n2) {
            return true;
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0);
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
    public IntIterator iterator() {
        return new SetIterator(null);
    }
    
    @Override
    public IntSpliterator spliterator() {
        return new SetSpliterator();
    }
    
    @Override
    public void forEach(final IntConsumer intConsumer) {
        if (this.containsNull) {
            intConsumer.accept(this.key[this.n]);
        }
        final int[] key = this.key;
        int n = this.n;
        while (n-- != 0) {
            if (key[n] != 0) {
                intConsumer.accept(key[n]);
            }
        }
    }
    
    public boolean trim() {
        return this.trim(this.size);
    }
    
    public boolean trim(final int n) {
        final int nextPowerOfTwo = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
        if (nextPowerOfTwo >= this.n || this.size > HashCommon.maxFill(nextPowerOfTwo, this.f)) {
            return true;
        }
        this.rehash(nextPowerOfTwo);
        return true;
    }
    
    protected void rehash(final int n) {
        final int[] key = this.key;
        final int mask = n - 1;
        final int[] key2 = new int[n + 1];
        int n2 = this.n;
        int realSize = this.realSize();
        while (realSize-- != 0) {
            while (key[--n2] == 0) {}
            int n3;
            if (key2[n3 = (HashCommon.mix(key[n2]) & mask)] != 0) {
                while (key2[n3 = (n3 + 1 & mask)] != 0) {}
            }
            key2[n3] = key[n2];
        }
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
    }
    
    public IntOpenHashSet clone() {
        final IntOpenHashSet set = (IntOpenHashSet)super.clone();
        set.key = this.key.clone();
        set.containsNull = this.containsNull;
        return set;
    }
    
    @Override
    public int hashCode() {
        int realSize = this.realSize();
        while (realSize-- != 0) {
            int n = 0;
            while (this.key[0] == 0) {
                ++n;
            }
            final int n2 = 0 + this.key[0];
            ++n;
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final IntIterator iterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            objectOutputStream.writeInt(iterator.nextInt());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        final int[] key = new int[this.n + 1];
        this.key = key;
        final int[] array = key;
        int size = this.size;
        while (size-- != 0) {
            final int int1 = objectInputStream.readInt();
            int n;
            if (int1 == 0) {
                n = this.n;
                this.containsNull = true;
            }
            else if (array[n = (HashCommon.mix(int1) & this.mask)] != 0) {
                while (array[n = (n + 1 & this.mask)] != 0) {}
            }
            array[n] = int1;
        }
    }
    
    private void checkTable() {
    }
    
    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    private static IntOpenHashSet lambda$toSetWithExpectedSize$0(final int n) {
        return (n <= 16) ? new IntOpenHashSet() : new IntOpenHashSet(n);
    }
    
    static int access$100(final IntOpenHashSet set) {
        return set.realSize();
    }
    
    private final class SetIterator implements IntIterator
    {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        IntArrayList wrapped;
        final IntOpenHashSet this$0;
        
        private SetIterator(final IntOpenHashSet this$0) {
            this.this$0 = this$0;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }
        
        @Override
        public boolean hasNext() {
            return this.c != 0;
        }
        
        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            final int[] key = this.this$0.key;
            while (--this.pos >= 0) {
                if (key[this.pos] != 0) {
                    final int[] array = key;
                    final int pos = this.pos;
                    this.last = pos;
                    return array[pos];
                }
            }
            this.last = Integer.MIN_VALUE;
            return this.wrapped.getInt(-this.pos - 1);
        }
        
        private final void shiftKeys(int n) {
            final int[] key = this.this$0.key;
            int n2 = 0;
        Label_0009:
            while (true) {
                n = ((n2 = n) + 1 & this.this$0.mask);
                int n3;
                while ((n3 = key[n]) != 0) {
                    final int n4 = HashCommon.mix(n3) & this.this$0.mask;
                    Label_0099: {
                        if (n2 <= n) {
                            if (n2 >= n4) {
                                break Label_0099;
                            }
                            if (n4 > n) {
                                break Label_0099;
                            }
                        }
                        else if (n2 >= n4 && n4 > n) {
                            break Label_0099;
                        }
                        n = (n + 1 & this.this$0.mask);
                        continue;
                    }
                    if (n < n2) {
                        if (this.wrapped == null) {
                            this.wrapped = new IntArrayList(2);
                        }
                        this.wrapped.add(key[n]);
                    }
                    key[n2] = n3;
                    continue Label_0009;
                }
                break;
            }
            key[n2] = 0;
        }
        
        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = 0;
            }
            else {
                if (this.pos < 0) {
                    this.this$0.remove(this.wrapped.getInt(-this.pos - 1));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            final IntOpenHashSet this$0 = this.this$0;
            --this$0.size;
            this.last = -1;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            final int[] key = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                intConsumer.accept(key[this.this$0.n]);
                --this.c;
            }
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    intConsumer.accept(this.wrapped.getInt(-this.pos - 1));
                    --this.c;
                }
                else {
                    if (key[this.pos] == 0) {
                        continue;
                    }
                    final int[] array = key;
                    final int pos = this.pos;
                    this.last = pos;
                    intConsumer.accept(array[pos]);
                    --this.c;
                }
            }
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        SetIterator(final IntOpenHashSet set, final IntOpenHashSet$1 object) {
            this(set);
        }
    }
    
    private final class SetSpliterator implements IntSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final IntOpenHashSet this$0;
        
        SetSpliterator(final IntOpenHashSet this$0) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
        }
        
        SetSpliterator(final IntOpenHashSet this$0, final int pos, final int max, final boolean mustReturnNull, final boolean hasSplit) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                intConsumer.accept(this.this$0.key[this.this$0.n]);
                return true;
            }
            final int[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    ++this.c;
                    intConsumer.accept(key[this.pos++]);
                    return true;
                }
                ++this.pos;
            }
            return false;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            final int[] key = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                intConsumer.accept(key[this.this$0.n]);
                ++this.c;
            }
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    intConsumer.accept(key[this.pos]);
                    ++this.c;
                }
                ++this.pos;
            }
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 257 : 321;
        }
        
        @Override
        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min(this.this$0.size - this.c, (long)(IntOpenHashSet.access$100(this.this$0) / (double)this.this$0.n * (this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }
        
        @Override
        public SetSpliterator trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            final int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            final int pos = this.pos + n;
            final SetSpliterator setSpliterator = this.this$0.new SetSpliterator(this.pos, pos, this.mustReturnNull, true);
            this.pos = pos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0L) {
                return 0L;
            }
            long n2 = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++n2;
                --n;
            }
            final int[] key = this.this$0.key;
            while (this.pos < this.max && n > 0L) {
                if (key[this.pos++] != 0) {
                    ++n2;
                    --n;
                }
            }
            return n2;
        }
        
        @Override
        public IntSpliterator trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}
