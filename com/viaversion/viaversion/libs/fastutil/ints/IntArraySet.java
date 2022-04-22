package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class IntArraySet extends AbstractIntSet implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient int[] a;
    private int size;
    
    public IntArraySet(final int[] a) {
        this.a = a;
        this.size = a.length;
    }
    
    public IntArraySet() {
        this.a = IntArrays.EMPTY_ARRAY;
    }
    
    public IntArraySet(final int n) {
        this.a = new int[n];
    }
    
    public IntArraySet(final IntCollection collection) {
        this(collection.size());
        this.addAll(collection);
    }
    
    public IntArraySet(final Collection collection) {
        this(collection.size());
        this.addAll(collection);
    }
    
    public IntArraySet(final IntSet set) {
        this(set.size());
        final IntIterator iterator = set.iterator();
        while (iterator.hasNext()) {
            this.a[0] = iterator.next();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public IntArraySet(final Set set) {
        this(set.size());
        final Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            this.a[0] = iterator.next();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public IntArraySet(final int[] a, final int size) {
        this.a = a;
        this.size = size;
        if (size > a.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
        }
    }
    
    public static IntArraySet of() {
        return ofUnchecked();
    }
    
    public static IntArraySet of(final int n) {
        return ofUnchecked(n);
    }
    
    public static IntArraySet of(final int... array) {
        if (array.length == 2) {
            if (array[0] == array[1]) {
                throw new IllegalArgumentException("Duplicate element: " + array[1]);
            }
        }
        else if (array.length > 2) {
            IntOpenHashSet.of(array);
        }
        return ofUnchecked(array);
    }
    
    public static IntArraySet ofUnchecked() {
        return new IntArraySet();
    }
    
    public static IntArraySet ofUnchecked(final int... array) {
        return new IntArraySet(array);
    }
    
    private int findKey(final int n) {
        int size = this.size;
        while (size-- != 0) {
            if (this.a[size] == n) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public IntIterator iterator() {
        return new IntIterator() {
            int next = 0;
            final IntArraySet this$0;
            
            @Override
            public int nextInt() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: if_icmpge       12
                //     4: new             Ljava/util/NoSuchElementException;
                //     7: dup            
                //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
                //    11: athrow         
                //    12: aload_0        
                //    13: getfield        com/viaversion/viaversion/libs/fastutil/ints/IntArraySet$1.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/IntArraySet;
                //    16: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/IntArraySet.access$100:(Lcom/viaversion/viaversion/libs/fastutil/ints/IntArraySet;)[I
                //    19: aload_0        
                //    20: dup            
                //    21: getfield        com/viaversion/viaversion/libs/fastutil/ints/IntArraySet$1.next:I
                //    24: dup_x1         
                //    25: iconst_1       
                //    26: iadd           
                //    27: putfield        com/viaversion/viaversion/libs/fastutil/ints/IntArraySet$1.next:I
                //    30: iaload         
                //    31: ireturn        
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public void remove() {
                System.arraycopy(IntArraySet.access$100(this.this$0), this.next + 1, IntArraySet.access$100(this.this$0), this.next, IntArraySet.access$010(this.this$0) - this.next--);
            }
            
            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = IntArraySet.access$000(this.this$0) - this.next;
                if (n < n2) {
                    this.next += n;
                    return n;
                }
                n = n2;
                this.next = IntArraySet.access$000(this.this$0);
                return n;
            }
        };
    }
    
    @Override
    public IntSpliterator spliterator() {
        return new Spliterator();
    }
    
    @Override
    public boolean contains(final int n) {
        return this.findKey(n) != -1;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean remove(final int n) {
        final int key = this.findKey(n);
        if (key == -1) {
            return false;
        }
        while (0 < this.size - key - 1) {
            this.a[key + 0] = this.a[key + 0 + 1];
            int n2 = 0;
            ++n2;
        }
        --this.size;
        return true;
    }
    
    @Override
    public boolean add(final int n) {
        if (this.findKey(n) != -1) {
            return false;
        }
        if (this.size == this.a.length) {
            final int[] a = new int[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                a[size] = this.a[size];
            }
            this.a = a;
        }
        this.a[this.size++] = n;
        return true;
    }
    
    @Override
    public void clear() {
        this.size = 0;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public int[] toIntArray() {
        return Arrays.copyOf(this.a, this.size);
    }
    
    @Override
    public int[] toArray(int[] array) {
        if (array == null || array.length < this.size) {
            array = new int[this.size];
        }
        System.arraycopy(this.a, 0, array, 0, this.size);
        return array;
    }
    
    public IntArraySet clone() {
        final IntArraySet set = (IntArraySet)super.clone();
        set.a = this.a.clone();
        return set;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeInt(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        while (0 < this.size) {
            this.a[0] = objectInputStream.readInt();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$000(final IntArraySet set) {
        return set.size;
    }
    
    static int[] access$100(final IntArraySet set) {
        return set.a;
    }
    
    static int access$010(final IntArraySet set) {
        return set.size--;
    }
    
    private final class Spliterator implements IntSpliterator
    {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final IntArraySet this$0;
        
        public Spliterator(final IntArraySet set) {
            this(set, 0, IntArraySet.access$000(set), false);
        }
        
        private Spliterator(final IntArraySet this$0, final int pos, final int max, final boolean hasSplit) {
            this.this$0 = this$0;
            this.hasSplit = false;
            assert pos <= max : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
            this.hasSplit = hasSplit;
        }
        
        private int getWorkingMax() {
            return this.hasSplit ? this.max : IntArraySet.access$000(this.this$0);
        }
        
        @Override
        public int characteristics() {
            return 16721;
        }
        
        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.pos >= this.getWorkingMax()) {
                return false;
            }
            intConsumer.accept(IntArraySet.access$100(this.this$0)[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.pos < this.getWorkingMax()) {
                intConsumer.accept(IntArraySet.access$100(this.this$0)[this.pos]);
                ++this.pos;
            }
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            final int workingMax = this.getWorkingMax();
            if (this.pos >= workingMax) {
                return 0L;
            }
            final int n2 = workingMax - this.pos;
            if (n < n2) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            n = n2;
            this.pos = workingMax;
            return n;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final int workingMax = this.getWorkingMax();
            final int n = workingMax - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            this.max = workingMax;
            final int pos = this.pos + n;
            final int pos2 = this.pos;
            this.pos = pos;
            this.hasSplit = true;
            return this.this$0.new Spliterator(pos2, pos, true);
        }
        
        @Override
        public OfInt trySplit() {
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
        public OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
        
        static {
            $assertionsDisabled = !IntArraySet.class.desiredAssertionStatus();
        }
    }
}
