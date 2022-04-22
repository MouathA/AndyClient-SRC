package com.google.common.collect;

import com.google.common.base.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import java.util.concurrent.atomic.*;

@GwtCompatible
public abstract class Ordering implements Comparator
{
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;
    
    @GwtCompatible(serializable = true)
    public static Ordering natural() {
        return NaturalOrdering.INSTANCE;
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering from(final Comparator comparator) {
        return (comparator instanceof Ordering) ? ((Ordering)comparator) : new ComparatorOrdering(comparator);
    }
    
    @Deprecated
    @GwtCompatible(serializable = true)
    public static Ordering from(final Ordering ordering) {
        return (Ordering)Preconditions.checkNotNull(ordering);
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering explicit(final List list) {
        return new ExplicitOrdering(list);
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering explicit(final Object o, final Object... array) {
        return explicit(Lists.asList(o, array));
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering allEqual() {
        return AllEqualOrdering.INSTANCE;
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }
    
    public static Ordering arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }
    
    protected Ordering() {
    }
    
    @GwtCompatible(serializable = true)
    public Ordering reverse() {
        return new ReverseOrdering(this);
    }
    
    @GwtCompatible(serializable = true)
    public Ordering nullsFirst() {
        return new NullsFirstOrdering(this);
    }
    
    @GwtCompatible(serializable = true)
    public Ordering nullsLast() {
        return new NullsLastOrdering(this);
    }
    
    @GwtCompatible(serializable = true)
    public Ordering onResultOf(final Function function) {
        return new ByFunctionOrdering(function, this);
    }
    
    Ordering onKeys() {
        return this.onResultOf(Maps.keyFunction());
    }
    
    @GwtCompatible(serializable = true)
    public Ordering compound(final Comparator comparator) {
        return new CompoundOrdering(this, (Comparator)Preconditions.checkNotNull(comparator));
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering compound(final Iterable iterable) {
        return new CompoundOrdering(iterable);
    }
    
    @GwtCompatible(serializable = true)
    public Ordering lexicographical() {
        return new LexicographicalOrdering(this);
    }
    
    @Override
    public abstract int compare(@Nullable final Object p0, @Nullable final Object p1);
    
    public Object min(final Iterator iterator) {
        Object o = iterator.next();
        while (iterator.hasNext()) {
            o = this.min(o, iterator.next());
        }
        return o;
    }
    
    public Object min(final Iterable iterable) {
        return this.min(iterable.iterator());
    }
    
    public Object min(@Nullable final Object o, @Nullable final Object o2) {
        return (this.compare(o, o2) <= 0) ? o : o2;
    }
    
    public Object min(@Nullable final Object o, @Nullable final Object o2, @Nullable final Object o3, final Object... array) {
        Object o4 = this.min(this.min(o, o2), o3);
        while (0 < array.length) {
            o4 = this.min(o4, array[0]);
            int n = 0;
            ++n;
        }
        return o4;
    }
    
    public Object max(final Iterator iterator) {
        Object o = iterator.next();
        while (iterator.hasNext()) {
            o = this.max(o, iterator.next());
        }
        return o;
    }
    
    public Object max(final Iterable iterable) {
        return this.max(iterable.iterator());
    }
    
    public Object max(@Nullable final Object o, @Nullable final Object o2) {
        return (this.compare(o, o2) >= 0) ? o : o2;
    }
    
    public Object max(@Nullable final Object o, @Nullable final Object o2, @Nullable final Object o3, final Object... array) {
        Object o4 = this.max(this.max(o, o2), o3);
        while (0 < array.length) {
            o4 = this.max(o4, array[0]);
            int n = 0;
            ++n;
        }
        return o4;
    }
    
    public List leastOf(final Iterable iterable, final int n) {
        if (iterable instanceof Collection) {
            final Collection collection = (Collection)iterable;
            if (collection.size() <= 2L * n) {
                Object[] arraysCopy = collection.toArray();
                Arrays.sort(arraysCopy, this);
                if (arraysCopy.length > n) {
                    arraysCopy = ObjectArrays.arraysCopyOf(arraysCopy, n);
                }
                return Collections.unmodifiableList((List<?>)Arrays.asList((T[])arraysCopy));
            }
        }
        return this.leastOf(iterable.iterator(), n);
    }
    
    public List leastOf(final Iterator p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: iload_2        
        //     6: ldc             "k"
        //     8: invokestatic    com/google/common/collect/CollectPreconditions.checkNonnegative:(ILjava/lang/String;)I
        //    11: pop            
        //    12: iload_2        
        //    13: ifeq            25
        //    16: aload_1        
        //    17: invokeinterface java/util/Iterator.hasNext:()Z
        //    22: ifne            29
        //    25: invokestatic    com/google/common/collect/ImmutableList.of:()Lcom/google/common/collect/ImmutableList;
        //    28: areturn        
        //    29: iload_2        
        //    30: ldc             1073741823
        //    32: if_icmplt       76
        //    35: aload_1        
        //    36: invokestatic    com/google/common/collect/Lists.newArrayList:(Ljava/util/Iterator;)Ljava/util/ArrayList;
        //    39: astore_3       
        //    40: aload_3        
        //    41: aload_0        
        //    42: invokestatic    java/util/Collections.sort:(Ljava/util/List;Ljava/util/Comparator;)V
        //    45: aload_3        
        //    46: invokevirtual   java/util/ArrayList.size:()I
        //    49: iload_2        
        //    50: if_icmple       67
        //    53: aload_3        
        //    54: iload_2        
        //    55: aload_3        
        //    56: invokevirtual   java/util/ArrayList.size:()I
        //    59: invokevirtual   java/util/ArrayList.subList:(II)Ljava/util/List;
        //    62: invokeinterface java/util/List.clear:()V
        //    67: aload_3        
        //    68: invokevirtual   java/util/ArrayList.trimToSize:()V
        //    71: aload_3        
        //    72: invokestatic    java/util/Collections.unmodifiableList:(Ljava/util/List;)Ljava/util/List;
        //    75: areturn        
        //    76: iload_2        
        //    77: iconst_2       
        //    78: imul           
        //    79: istore_3       
        //    80: iload_3        
        //    81: anewarray       Ljava/lang/Object;
        //    84: checkcast       [Ljava/lang/Object;
        //    87: astore          4
        //    89: aload_1        
        //    90: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    95: astore          5
        //    97: aload           4
        //    99: iconst_0       
        //   100: aload           5
        //   102: aastore        
        //   103: iconst_1       
        //   104: iload_2        
        //   105: if_icmpge       147
        //   108: aload_1        
        //   109: invokeinterface java/util/Iterator.hasNext:()Z
        //   114: ifeq            147
        //   117: aload_1        
        //   118: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   123: astore          7
        //   125: aload           4
        //   127: iconst_1       
        //   128: iinc            6, 1
        //   131: aload           7
        //   133: aastore        
        //   134: aload_0        
        //   135: aload           5
        //   137: aload           7
        //   139: invokevirtual   com/google/common/collect/Ordering.max:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   142: astore          5
        //   144: goto            103
        //   147: aload_1        
        //   148: invokeinterface java/util/Iterator.hasNext:()Z
        //   153: ifeq            296
        //   156: aload_1        
        //   157: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   162: astore          7
        //   164: aload_0        
        //   165: aload           7
        //   167: aload           5
        //   169: invokevirtual   com/google/common/collect/Ordering.compare:(Ljava/lang/Object;Ljava/lang/Object;)I
        //   172: iflt            178
        //   175: goto            147
        //   178: aload           4
        //   180: iconst_1       
        //   181: iinc            6, 1
        //   184: aload           7
        //   186: aastore        
        //   187: iconst_1       
        //   188: iload_3        
        //   189: if_icmpne       293
        //   192: iload_3        
        //   193: iconst_1       
        //   194: isub           
        //   195: istore          9
        //   197: iconst_0       
        //   198: iload           9
        //   200: if_icmpge       261
        //   203: iconst_0       
        //   204: iload           9
        //   206: iadd           
        //   207: iconst_1       
        //   208: iadd           
        //   209: iconst_1       
        //   210: iushr          
        //   211: istore          11
        //   213: aload_0        
        //   214: aload           4
        //   216: iconst_0       
        //   217: iload           9
        //   219: iconst_1       
        //   220: invokespecial   com/google/common/collect/Ordering.partition:([Ljava/lang/Object;III)I
        //   223: istore          12
        //   225: iload           12
        //   227: iload_2        
        //   228: if_icmple       240
        //   231: iload           12
        //   233: iconst_1       
        //   234: isub           
        //   235: istore          9
        //   237: goto            258
        //   240: iload           12
        //   242: iload_2        
        //   243: if_icmpge       261
        //   246: iload           12
        //   248: iconst_1       
        //   249: invokestatic    java/lang/Math.max:(II)I
        //   252: istore          8
        //   254: iload           12
        //   256: istore          10
        //   258: goto            197
        //   261: iload_2        
        //   262: istore          6
        //   264: aload           4
        //   266: iconst_0       
        //   267: aaload         
        //   268: astore          5
        //   270: iconst_1       
        //   271: iconst_1       
        //   272: if_icmpge       293
        //   275: aload_0        
        //   276: aload           5
        //   278: aload           4
        //   280: iconst_1       
        //   281: aaload         
        //   282: invokevirtual   com/google/common/collect/Ordering.max:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   285: astore          5
        //   287: iinc            11, 1
        //   290: goto            270
        //   293: goto            147
        //   296: aload           4
        //   298: iconst_0       
        //   299: iconst_1       
        //   300: aload_0        
        //   301: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;IILjava/util/Comparator;)V
        //   304: iconst_1       
        //   305: iload_2        
        //   306: invokestatic    java/lang/Math.min:(II)I
        //   309: istore          6
        //   311: aload           4
        //   313: iconst_1       
        //   314: invokestatic    com/google/common/collect/ObjectArrays.arraysCopyOf:([Ljava/lang/Object;I)[Ljava/lang/Object;
        //   317: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   320: invokestatic    java/util/Collections.unmodifiableList:(Ljava/util/List;)Ljava/util/List;
        //   323: areturn        
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private int partition(final Object[] array, final int n, final int n2, final int n3) {
        final Object o = array[n3];
        array[n3] = array[n2];
        array[n2] = o;
        int n4 = n;
        for (int i = n; i < n2; ++i) {
            if (this.compare(array[i], o) < 0) {
                ObjectArrays.swap(array, n4, i);
                ++n4;
            }
        }
        ObjectArrays.swap(array, n2, n4);
        return n4;
    }
    
    public List greatestOf(final Iterable iterable, final int n) {
        return this.reverse().leastOf(iterable, n);
    }
    
    public List greatestOf(final Iterator iterator, final int n) {
        return this.reverse().leastOf(iterator, n);
    }
    
    public List sortedCopy(final Iterable iterable) {
        final Object[] array = Iterables.toArray(iterable);
        Arrays.sort(array, this);
        return Lists.newArrayList(Arrays.asList(array));
    }
    
    public ImmutableList immutableSortedCopy(final Iterable iterable) {
        final Object[] array2;
        final Object[] array = array2 = Iterables.toArray(iterable);
        while (0 < array2.length) {
            Preconditions.checkNotNull(array2[0]);
            int n = 0;
            ++n;
        }
        Arrays.sort(array, this);
        return ImmutableList.asImmutableList(array);
    }
    
    public boolean isOrdered(final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            Object next = iterator.next();
            while (iterator.hasNext()) {
                final Object next2 = iterator.next();
                if (this.compare(next, next2) > 0) {
                    return false;
                }
                next = next2;
            }
        }
        return true;
    }
    
    public boolean isStrictlyOrdered(final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            Object next = iterator.next();
            while (iterator.hasNext()) {
                final Object next2 = iterator.next();
                if (this.compare(next, next2) >= 0) {
                    return false;
                }
                next = next2;
            }
        }
        return true;
    }
    
    public int binarySearch(final List list, @Nullable final Object o) {
        return Collections.binarySearch(list, o, this);
    }
    
    @VisibleForTesting
    static class IncomparableValueException extends ClassCastException
    {
        final Object value;
        private static final long serialVersionUID = 0L;
        
        IncomparableValueException(final Object value) {
            super("Cannot compare value: " + value);
            this.value = value;
        }
    }
    
    @VisibleForTesting
    static class ArbitraryOrdering extends Ordering
    {
        private Map uids;
        
        ArbitraryOrdering() {
            this.uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function() {
                final AtomicInteger counter = new AtomicInteger(0);
                final ArbitraryOrdering this$0;
                
                @Override
                public Integer apply(final Object o) {
                    return this.counter.getAndIncrement();
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply(o);
                }
            });
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            if (o == o2) {
                return 0;
            }
            if (o == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            final int identityHashCode = this.identityHashCode(o);
            final int identityHashCode2 = this.identityHashCode(o2);
            if (identityHashCode != identityHashCode2) {
                return (identityHashCode < identityHashCode2) ? -1 : 1;
            }
            final int compareTo = this.uids.get(o).compareTo(this.uids.get(o2));
            if (compareTo == 0) {
                throw new AssertionError();
            }
            return compareTo;
        }
        
        @Override
        public String toString() {
            return "Ordering.arbitrary()";
        }
        
        int identityHashCode(final Object o) {
            return System.identityHashCode(o);
        }
    }
    
    private static class ArbitraryOrderingHolder
    {
        static final Ordering ARBITRARY_ORDERING;
        
        static {
            ARBITRARY_ORDERING = new ArbitraryOrdering();
        }
    }
}
