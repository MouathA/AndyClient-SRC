package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.stream.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.io.*;
import java.util.*;

public class IntLinkedOpenHashSet extends AbstractIntSortedSet implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    private static final int SPLITERATOR_CHARACTERISTICS = 337;
    
    public IntLinkedOpenHashSet(final int n, final float f) {
        this.first = -1;
        this.last = -1;
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
        this.link = new long[this.n + 1];
    }
    
    public IntLinkedOpenHashSet(final int n) {
        this(n, 0.75f);
    }
    
    public IntLinkedOpenHashSet() {
        this(16, 0.75f);
    }
    
    public IntLinkedOpenHashSet(final Collection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public IntLinkedOpenHashSet(final Collection collection) {
        this(collection, 0.75f);
    }
    
    public IntLinkedOpenHashSet(final IntCollection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public IntLinkedOpenHashSet(final IntCollection collection) {
        this(collection, 0.75f);
    }
    
    public IntLinkedOpenHashSet(final IntIterator intIterator, final float n) {
        this(16, n);
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }
    
    public IntLinkedOpenHashSet(final IntIterator intIterator) {
        this(intIterator, 0.75f);
    }
    
    public IntLinkedOpenHashSet(final Iterator iterator, final float n) {
        this(IntIterators.asIntIterator(iterator), n);
    }
    
    public IntLinkedOpenHashSet(final Iterator iterator) {
        this(IntIterators.asIntIterator(iterator));
    }
    
    public IntLinkedOpenHashSet(final int[] array, final int n, final int n2, final float n3) {
        this((n2 < 0) ? 0 : n2, n3);
        IntArrays.ensureOffsetLength(array, n, n2);
        while (0 < n2) {
            this.add(array[n + 0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public IntLinkedOpenHashSet(final int[] array, final int n, final int n2) {
        this(array, n, n2, 0.75f);
    }
    
    public IntLinkedOpenHashSet(final int[] array, final float n) {
        this(array, 0, array.length, n);
    }
    
    public IntLinkedOpenHashSet(final int[] array) {
        this(array, 0.75f);
    }
    
    public static IntLinkedOpenHashSet of() {
        return new IntLinkedOpenHashSet();
    }
    
    public static IntLinkedOpenHashSet of(final int n) {
        final IntLinkedOpenHashSet set = new IntLinkedOpenHashSet(1, 0.75f);
        set.add(n);
        return set;
    }
    
    public static IntLinkedOpenHashSet of(final int n, final int n2) {
        final IntLinkedOpenHashSet set = new IntLinkedOpenHashSet(2, 0.75f);
        set.add(n);
        if (n2 == 0) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        return set;
    }
    
    public static IntLinkedOpenHashSet of(final int n, final int n2, final int n3) {
        final IntLinkedOpenHashSet set = new IntLinkedOpenHashSet(3, 0.75f);
        set.add(n);
        if (n2 == 0) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        if (n3 == 0) {
            throw new IllegalArgumentException("Duplicate element: " + n3);
        }
        return set;
    }
    
    public static IntLinkedOpenHashSet of(final int... p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: arraylength    
        //     6: ldc             0.75
        //     8: invokespecial   com/viaversion/viaversion/libs/fastutil/ints/IntLinkedOpenHashSet.<init>:(IF)V
        //    11: astore_1       
        //    12: aload_0        
        //    13: astore_2       
        //    14: aload_2        
        //    15: arraylength    
        //    16: istore_3       
        //    17: iconst_0       
        //    18: iload_3        
        //    19: if_icmpge       67
        //    22: aload_2        
        //    23: iconst_0       
        //    24: iaload         
        //    25: istore          5
        //    27: aload_1        
        //    28: iload           5
        //    30: ifne            61
        //    33: new             Ljava/lang/IllegalArgumentException;
        //    36: dup            
        //    37: new             Ljava/lang/StringBuilder;
        //    40: dup            
        //    41: invokespecial   java/lang/StringBuilder.<init>:()V
        //    44: ldc             "Duplicate element "
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    49: iload           5
        //    51: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    54: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    57: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    60: athrow         
        //    61: iinc            4, 1
        //    64: goto            17
        //    67: aload_1        
        //    68: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0017 (coming from #0064).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    public static IntLinkedOpenHashSet toSet(final IntStream intStream) {
        return intStream.collect(IntLinkedOpenHashSet::new, IntLinkedOpenHashSet::add, IntLinkedOpenHashSet::addAll);
    }
    
    public static IntLinkedOpenHashSet toSetWithExpectedSize(final IntStream intStream, final int n) {
        if (n <= 16) {
            return toSet(intStream);
        }
        return intStream.collect(new IntCollections.SizeDecreasingSupplier(n, IntLinkedOpenHashSet::lambda$toSetWithExpectedSize$0), IntLinkedOpenHashSet::add, IntLinkedOpenHashSet::addAll);
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
                this.fixPointers(n, n2);
                continue Label_0006;
            }
            break;
        }
        key[n2] = 0;
    }
    
    private boolean removeEntry(final int n) {
        --this.size;
        this.fixPointers(n);
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
        this.fixPointers(this.n);
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
    
    public int removeFirstInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        final int first = this.first;
        this.first = (int)this.link[first];
        if (0 <= this.first) {
            final long[] link = this.link;
            final int first2 = this.first;
            link[first2] |= 0xFFFFFFFF00000000L;
        }
        final int n = this.key[first];
        --this.size;
        if (n == 0) {
            this.containsNull = false;
            this.key[this.n] = 0;
        }
        else {
            this.shiftKeys(first);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }
    
    public int removeLastInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        final int last = this.last;
        this.last = (int)(this.link[last] >>> 32);
        if (0 <= this.last) {
            final long[] link = this.link;
            final int last2 = this.last;
            link[last2] |= 0xFFFFFFFFL;
        }
        final int n = this.key[last];
        --this.size;
        if (n == 0) {
            this.containsNull = false;
            this.key[this.n] = 0;
        }
        else {
            this.shiftKeys(last);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }
    
    private void moveIndexToFirst(final int first) {
        if (this.size == 1 || this.first == first) {
            return;
        }
        if (this.last == first) {
            this.last = (int)(this.link[first] >>> 32);
            final long[] link = this.link;
            final int last = this.last;
            link[last] |= 0xFFFFFFFFL;
        }
        else {
            final long n = this.link[first];
            final int n2 = (int)(n >>> 32);
            final int n3 = (int)n;
            final long[] link2 = this.link;
            final int n4 = n2;
            link2[n4] ^= ((this.link[n2] ^ (n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            final long[] link3 = this.link;
            final int n5 = n3;
            link3[n5] ^= ((this.link[n3] ^ (n & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
        }
        final long[] link4 = this.link;
        final int first2 = this.first;
        link4[first2] ^= ((this.link[this.first] ^ ((long)first & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
        this.link[first] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
        this.first = first;
    }
    
    private void moveIndexToLast(final int last) {
        if (this.size == 1 || this.last == last) {
            return;
        }
        if (this.first == last) {
            this.first = (int)this.link[last];
            final long[] link = this.link;
            final int first = this.first;
            link[first] |= 0xFFFFFFFF00000000L;
        }
        else {
            final long n = this.link[last];
            final int n2 = (int)(n >>> 32);
            final int n3 = (int)n;
            final long[] link2 = this.link;
            final int n4 = n2;
            link2[n4] ^= ((this.link[n2] ^ (n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            final long[] link3 = this.link;
            final int n5 = n3;
            link3[n5] ^= ((this.link[n3] ^ (n & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
        }
        final long[] link4 = this.link;
        final int last2 = this.last;
        link4[last2] ^= ((this.link[this.last] ^ ((long)last & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        this.link[last] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
        this.last = last;
    }
    
    public boolean addAndMoveToFirst(final int n) {
        int n2;
        if (n == 0) {
            if (this.containsNull) {
                this.moveIndexToFirst(this.n);
                return false;
            }
            this.containsNull = true;
            n2 = this.n;
        }
        else {
            int[] key;
            for (key = this.key, n2 = (HashCommon.mix(n) & this.mask); key[n2] != 0; n2 = (n2 + 1 & this.mask)) {
                if (n == key[n2]) {
                    this.moveIndexToFirst(n2);
                    return false;
                }
            }
        }
        this.key[n2] = n;
        if (this.size == 0) {
            final int n3 = n2;
            this.last = n3;
            this.first = n3;
            this.link[n2] = -1L;
        }
        else {
            final long[] link = this.link;
            final int first = this.first;
            link[first] ^= ((this.link[this.first] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n2] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
            this.first = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return true;
    }
    
    public boolean addAndMoveToLast(final int n) {
        int n2;
        if (n == 0) {
            if (this.containsNull) {
                this.moveIndexToLast(this.n);
                return false;
            }
            this.containsNull = true;
            n2 = this.n;
        }
        else {
            int[] key;
            for (key = this.key, n2 = (HashCommon.mix(n) & this.mask); key[n2] != 0; n2 = (n2 + 1 & this.mask)) {
                if (n == key[n2]) {
                    this.moveIndexToLast(n2);
                    return false;
                }
            }
        }
        this.key[n2] = n;
        if (this.size == 0) {
            final int n3 = n2;
            this.last = n3;
            this.first = n3;
            this.link[n2] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n2] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return true;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0);
        final int n = -1;
        this.last = n;
        this.first = n;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    protected void fixPointers(final int n) {
        if (this.size == 0) {
            final int n2 = -1;
            this.last = n2;
            this.first = n2;
            return;
        }
        if (this.first == n) {
            this.first = (int)this.link[n];
            if (0 <= this.first) {
                final long[] link = this.link;
                final int first = this.first;
                link[first] |= 0xFFFFFFFF00000000L;
            }
            return;
        }
        if (this.last == n) {
            this.last = (int)(this.link[n] >>> 32);
            if (0 <= this.last) {
                final long[] link2 = this.link;
                final int last = this.last;
                link2[last] |= 0xFFFFFFFFL;
            }
            return;
        }
        final long n3 = this.link[n];
        final int n4 = (int)(n3 >>> 32);
        final int n5 = (int)n3;
        final long[] link3 = this.link;
        final int n6 = n4;
        link3[n6] ^= ((this.link[n4] ^ (n3 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        final long[] link4 = this.link;
        final int n7 = n5;
        link4[n7] ^= ((this.link[n5] ^ (n3 & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
    }
    
    protected void fixPointers(final int n, final int n2) {
        if (this.size == 1) {
            this.last = n2;
            this.first = n2;
            this.link[n2] = -1L;
            return;
        }
        if (this.first == n) {
            this.first = n2;
            final long[] link = this.link;
            final int n3 = (int)this.link[n];
            link[n3] ^= ((this.link[(int)this.link[n]] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n2] = this.link[n];
            return;
        }
        if (this.last == n) {
            this.last = n2;
            final long[] link2 = this.link;
            final int n4 = (int)(this.link[n] >>> 32);
            link2[n4] ^= ((this.link[(int)(this.link[n] >>> 32)] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n2] = this.link[n];
            return;
        }
        final long n5 = this.link[n];
        final int n6 = (int)(n5 >>> 32);
        final int n7 = (int)n5;
        final long[] link3 = this.link;
        final int n8 = n6;
        link3[n8] ^= ((this.link[n6] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        final long[] link4 = this.link;
        final int n9 = n7;
        link4[n9] ^= ((this.link[n7] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
        this.link[n2] = n5;
    }
    
    @Override
    public int firstInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }
    
    @Override
    public int lastInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }
    
    @Override
    public IntSortedSet tailSet(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IntSortedSet headSet(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IntSortedSet subSet(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IntComparator comparator() {
        return null;
    }
    
    @Override
    public IntListIterator iterator(final int n) {
        return new SetIterator(n);
    }
    
    @Override
    public IntListIterator iterator() {
        return new SetIterator();
    }
    
    @Override
    public IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 337);
    }
    
    @Override
    public void forEach(final IntConsumer intConsumer) {
        int i = this.first;
        while (i != -1) {
            final int n = i;
            i = (int)this.link[n];
            intConsumer.accept(this.key[n]);
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
        int first = this.first;
        final long[] link = this.link;
        final long[] link2 = new long[n + 1];
        this.first = -1;
        int size = this.size;
        while (size-- != 0) {
            int first2;
            if (key[first] == 0) {
                first2 = n;
            }
            else {
                for (first2 = (HashCommon.mix(key[first]) & mask); key2[first2] != 0; first2 = (first2 + 1 & mask)) {}
            }
            key2[first2] = key[first];
            link2[this.first = first2] = -1L;
            first = (int)link[first];
        }
        this.link = link2;
        this.last = -1;
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
    }
    
    public IntLinkedOpenHashSet clone() {
        final IntLinkedOpenHashSet set = (IntLinkedOpenHashSet)super.clone();
        set.key = this.key.clone();
        set.containsNull = this.containsNull;
        set.link = this.link.clone();
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
        final IntListIterator iterator = this.iterator();
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
        final long[] link = new long[this.n + 1];
        this.link = link;
        final long[] array2 = link;
        final int n = -1;
        this.last = n;
        this.first = n;
        int size = this.size;
        while (size-- != 0) {
            final int int1 = objectInputStream.readInt();
            int n2;
            if (int1 == 0) {
                n2 = this.n;
                this.containsNull = true;
            }
            else if (array[n2 = (HashCommon.mix(int1) & this.mask)] != 0) {
                while (array[n2 = (n2 + 1 & this.mask)] != 0) {}
            }
            array[n2] = int1;
            if (this.first != -1) {
                final long[] array3 = array2;
                final int n3 = -1;
                array3[n3] ^= ((array2[-1] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
                final long[] array4 = array2;
                final int n4 = n2;
                array4[n4] ^= ((array2[n2] ^ ((long)(-1) & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            else {
                this.first = n2;
                final long[] array5 = array2;
                final int n5 = n2;
                array5[n5] |= 0xFFFFFFFF00000000L;
            }
        }
        this.last = -1;
    }
    
    private void checkTable() {
    }
    
    @Override
    public IntBidirectionalIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public IntBidirectionalIterator iterator(final int n) {
        return this.iterator(n);
    }
    
    @Override
    public IntIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Comparator comparator() {
        return this.comparator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    private static IntLinkedOpenHashSet lambda$toSetWithExpectedSize$0(final int n) {
        return (n <= 16) ? new IntLinkedOpenHashSet() : new IntLinkedOpenHashSet(n);
    }
    
    private final class SetIterator implements IntListIterator
    {
        int prev;
        int next;
        int curr;
        int index;
        final IntLinkedOpenHashSet this$0;
        
        SetIterator(final IntLinkedOpenHashSet this$0) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = this$0.first;
            this.index = 0;
        }
        
        SetIterator(final IntLinkedOpenHashSet this$0, final int n) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (n == 0) {
                if (this$0.containsNull) {
                    this.next = (int)this$0.link[this$0.n];
                    this.prev = this$0.n;
                    return;
                }
                throw new NoSuchElementException("The key " + n + " does not belong to this set.");
            }
            else {
                if (this$0.key[this$0.last] == n) {
                    this.prev = this$0.last;
                    this.index = this$0.size;
                    return;
                }
                final int[] key = this$0.key;
                for (int prev = HashCommon.mix(n) & this$0.mask; key[prev] != 0; prev = (prev + 1 & this$0.mask)) {
                    if (key[prev] == n) {
                        this.next = (int)this$0.link[prev];
                        this.prev = prev;
                        return;
                    }
                }
                throw new NoSuchElementException("The key " + n + " does not belong to this set.");
            }
        }
        
        @Override
        public boolean hasNext() {
            return this.next != -1;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.prev != -1;
        }
        
        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int)this.this$0.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                ++this.index;
            }
            return this.this$0.key[this.curr];
        }
        
        @Override
        public int previousInt() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                --this.index;
            }
            return this.this$0.key[this.curr];
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            final int[] key = this.this$0.key;
            final long[] link = this.this$0.link;
            while (this.next != -1) {
                this.curr = this.next;
                this.next = (int)link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    ++this.index;
                }
                intConsumer.accept(key[this.curr]);
            }
        }
        
        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
                return;
            }
            if (this.next == -1) {
                this.index = this.this$0.size;
                return;
            }
            int i = this.this$0.first;
            this.index = 1;
            while (i != this.prev) {
                i = (int)this.this$0.link[i];
                ++this.index;
            }
        }
        
        @Override
        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }
        
        @Override
        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }
        
        @Override
        public void remove() {
            this.ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                --this.index;
                this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            }
            else {
                this.next = (int)this.this$0.link[this.curr];
            }
            final IntLinkedOpenHashSet this$0 = this.this$0;
            --this$0.size;
            if (this.prev == -1) {
                this.this$0.first = this.next;
            }
            else {
                final long[] link = this.this$0.link;
                final int prev = this.prev;
                link[prev] ^= ((this.this$0.link[this.prev] ^ ((long)this.next & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            }
            if (this.next == -1) {
                this.this$0.last = this.prev;
            }
            else {
                final long[] link2 = this.this$0.link;
                final int next = this.next;
                link2[next] ^= ((this.this$0.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            int curr = this.curr;
            this.curr = -1;
            if (curr == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = 0;
                return;
            }
            final int[] key = this.this$0.key;
            int n = 0;
        Label_0280:
            while (true) {
                curr = ((n = curr) + 1 & this.this$0.mask);
                int n2;
                while ((n2 = key[curr]) != 0) {
                    final int n3 = HashCommon.mix(n2) & this.this$0.mask;
                    Label_0370: {
                        if (n <= curr) {
                            if (n >= n3) {
                                break Label_0370;
                            }
                            if (n3 > curr) {
                                break Label_0370;
                            }
                        }
                        else if (n >= n3 && n3 > curr) {
                            break Label_0370;
                        }
                        curr = (curr + 1 & this.this$0.mask);
                        continue;
                    }
                    key[n] = n2;
                    if (this.next == curr) {
                        this.next = n;
                    }
                    if (this.prev == curr) {
                        this.prev = n;
                    }
                    this.this$0.fixPointers(curr, n);
                    continue Label_0280;
                }
                break;
            }
            key[n] = 0;
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
}
