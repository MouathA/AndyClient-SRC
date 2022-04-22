package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.longs.*;
import com.viaversion.viaversion.libs.fastutil.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.io.*;
import java.util.*;

public class IntArrayFrontCodedList extends AbstractObjectList implements Serializable, Cloneable, RandomAccess
{
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final int[][] array;
    protected transient long[] p;
    
    public IntArrayFrontCodedList(final Iterator iterator, final int ratio) {
        if (ratio < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + ratio + ")");
        }
        int[][] array = IntBigArrays.EMPTY_BIG_ARRAY;
        long[] array2 = LongArrays.EMPTY_ARRAY;
        final int[][] array3 = new int[2][];
        long n = 0L;
        while (iterator.hasNext()) {
            array3[0] = iterator.next();
            final int length = array3[0].length;
            if (0 % ratio == 0) {
                array2 = LongArrays.grow(array2, 0 / ratio + 1);
                array2[0 / ratio] = n;
                array = BigArrays.grow(array, n + count(length) + length, n);
                final long n2 = n + writeInt(array, length, n);
                BigArrays.copyToBig(array3[0], 0, array, n2, length);
                n = n2 + length;
            }
            else {
                int length2 = array3[1].length;
                if (length < length2) {
                    length2 = length;
                }
                while (0 < length2 && array3[0][0] == array3[1][0]) {
                    int n3 = 0;
                    ++n3;
                }
                final int n4 = length - 0;
                array = BigArrays.grow(array, n + count(n4) + count(0) + n4, n);
                final long n5 = n + writeInt(array, n4, n);
                final long n6 = n5 + writeInt(array, 0, n5);
                BigArrays.copyToBig(array3[0], 0, array, n6, n4);
                n = n6 + n4;
            }
            int n7 = 0;
            ++n7;
        }
        this.n = 0;
        this.ratio = ratio;
        this.array = BigArrays.trim(array, n);
        this.p = LongArrays.trim(array2, (0 + ratio - 1) / ratio);
    }
    
    public IntArrayFrontCodedList(final Collection collection, final int n) {
        this(collection.iterator(), n);
    }
    
    static int readInt(final int[][] array, final long n) {
        return BigArrays.get(array, n);
    }
    
    static int count(final int n) {
        return 1;
    }
    
    static int writeInt(final int[][] array, final int n, final long n2) {
        BigArrays.set(array, n2, n);
        return 1;
    }
    
    public int ratio() {
        return this.ratio;
    }
    
    private int length(final int n) {
        final int[][] array = this.array;
        final int n2 = n % this.ratio;
        final long n3 = this.p[n / this.ratio];
        final int int1 = readInt(array, n3);
        if (n2 == 0) {
            return int1;
        }
        long n4 = n3 + (count(int1) + int1);
        int n5 = readInt(array, n4);
        int n6 = readInt(array, n4 + count(n5));
        while (0 < n2 - 1) {
            n4 += count(n5) + count(n6) + n5;
            n5 = readInt(array, n4);
            n6 = readInt(array, n4 + count(n5));
            int n7 = 0;
            ++n7;
        }
        return n5 + n6;
    }
    
    public int arrayLength(final int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }
    
    private int extract(final int n, final int[] array, final int n2, final int n3) {
        final int n4 = n % this.ratio;
        long n6;
        int n5 = readInt(this.array, n6 = this.p[n / this.ratio]);
        if (n4 == 0) {
            BigArrays.copyFromBig(this.array, this.p[n / this.ratio] + count(n5), array, n2, Math.min(n3, n5));
            return n5;
        }
        while (0 < n4) {
            final long n7 = n6 + count(n5) + (false ? count(0) : 0);
            n6 = n7 + n5;
            n5 = readInt(this.array, n6);
            readInt(this.array, n6 + count(n5));
            final int min = Math.min(0, n3);
            if (min > 0) {
                BigArrays.copyFromBig(this.array, n7, array, 0 + n2, min - 0);
            }
            int n8 = 0;
            ++n8;
        }
        if (0 < n3) {
            BigArrays.copyFromBig(this.array, n6 + count(n5) + count(0), array, 0 + n2, Math.min(n5, n3 - 0));
        }
        return n5 + 0;
    }
    
    @Override
    public int[] get(final int n) {
        return this.getArray(n);
    }
    
    public int[] getArray(final int n) {
        this.ensureRestrictedIndex(n);
        final int length = this.length(n);
        final int[] array = new int[length];
        this.extract(n, array, 0, length);
        return array;
    }
    
    public int get(final int n, final int[] array, final int n2, final int n3) {
        this.ensureRestrictedIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        final int extract = this.extract(n, array, n2, n3);
        if (n3 >= extract) {
            return extract;
        }
        return n3 - extract;
    }
    
    public int get(final int n, final int[] array) {
        return this.get(n, array, 0, array.length);
    }
    
    @Override
    public int size() {
        return this.n;
    }
    
    @Override
    public ObjectListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new ObjectListIterator(n) {
            int[] s = IntArrays.EMPTY_ARRAY;
            int i = 0;
            long pos = 0L;
            boolean inSync;
            final int val$start;
            final IntArrayFrontCodedList this$0;
            
            {
                if (this.val$start != 0) {
                    if (this.val$start == this.this$0.n) {
                        this.i = this.val$start;
                    }
                    else {
                        this.pos = this.this$0.p[this.val$start / this.this$0.ratio];
                        int n = this.val$start % this.this$0.ratio;
                        this.i = this.val$start - n;
                        while (n-- != 0) {
                            this.next();
                        }
                    }
                }
            }
            
            @Override
            public boolean hasNext() {
                return this.i < this.this$0.n;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.i > 0;
            }
            
            @Override
            public int previousIndex() {
                return this.i - 1;
            }
            
            @Override
            public int nextIndex() {
                return this.i;
            }
            
            @Override
            public int[] next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                int n;
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = IntArrayFrontCodedList.readInt(this.this$0.array, this.pos);
                    this.s = IntArrays.ensureCapacity(this.s, n, 0);
                    BigArrays.copyFromBig(this.this$0.array, this.pos + IntArrayFrontCodedList.count(n), this.s, 0, n);
                    this.pos += n + IntArrayFrontCodedList.count(n);
                    this.inSync = true;
                }
                else if (this.inSync) {
                    final int int1 = IntArrayFrontCodedList.readInt(this.this$0.array, this.pos);
                    final int int2 = IntArrayFrontCodedList.readInt(this.this$0.array, this.pos + IntArrayFrontCodedList.count(int1));
                    this.s = IntArrays.ensureCapacity(this.s, int1 + int2, int2);
                    BigArrays.copyFromBig(this.this$0.array, this.pos + IntArrayFrontCodedList.count(int1) + IntArrayFrontCodedList.count(int2), this.s, int2, int1);
                    this.pos += IntArrayFrontCodedList.count(int1) + IntArrayFrontCodedList.count(int2) + int1;
                    n = int1 + int2;
                }
                else {
                    this.s = IntArrays.ensureCapacity(this.s, n = IntArrayFrontCodedList.access$000(this.this$0, this.i), 0);
                    IntArrayFrontCodedList.access$100(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return IntArrays.copy(this.s, 0, n);
            }
            
            @Override
            public int[] previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.inSync = false;
                final IntArrayFrontCodedList this$0 = this.this$0;
                final int i = this.i - 1;
                this.i = i;
                return this$0.getArray(i);
            }
            
            @Override
            public Object next() {
                return this.next();
            }
            
            @Override
            public Object previous() {
                return this.previous();
            }
        };
    }
    
    public IntArrayFrontCodedList clone() {
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        while (0 < this.n) {
            if (false) {
                sb.append(", ");
            }
            sb.append(IntArrayList.wrap(this.getArray(0)).toString());
            int n = 0;
            ++n;
        }
        sb.append("]");
        return sb.toString();
    }
    
    protected long[] rebuildPointerArray() {
        final long[] array = new long[(this.n + this.ratio - 1) / this.ratio];
        final int[][] array2 = this.array;
        long n = 0L;
        int n2 = this.ratio - 1;
        while (0 < this.n) {
            final int int1 = readInt(array2, n);
            final int count = count(int1);
            ++n2;
            if (0 == this.ratio) {
                final long[] array3 = array;
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                array3[n3] = n;
                n += count + int1;
            }
            else {
                n += count + count(readInt(array2, n + count)) + int1;
            }
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.p = this.rebuildPointerArray();
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
    
    @Override
    public Object get(final int n) {
        return this.get(n);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$000(final IntArrayFrontCodedList list, final int n) {
        return list.length(n);
    }
    
    static int access$100(final IntArrayFrontCodedList list, final int n, final int[] array, final int n2, final int n3) {
        return list.extract(n, array, n2, n3);
    }
}
