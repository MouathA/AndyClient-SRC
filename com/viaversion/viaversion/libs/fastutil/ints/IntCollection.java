package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public interface IntCollection extends Collection, IntIterable
{
    IntIterator iterator();
    
    default IntIterator intIterator() {
        return this.iterator();
    }
    
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 320);
    }
    
    default IntSpliterator intSpliterator() {
        return this.spliterator();
    }
    
    boolean add(final int p0);
    
    boolean contains(final int p0);
    
    boolean rem(final int p0);
    
    @Deprecated
    default boolean add(final Integer n) {
        return this.add((int)n);
    }
    
    @Deprecated
    default boolean contains(final Object o) {
        return o != null && this.contains((int)o);
    }
    
    @Deprecated
    default boolean remove(final Object o) {
        return o != null && this.rem((int)o);
    }
    
    int[] toIntArray();
    
    @Deprecated
    default int[] toIntArray(final int[] array) {
        return this.toArray(array);
    }
    
    int[] toArray(final int[] p0);
    
    boolean addAll(final IntCollection p0);
    
    boolean containsAll(final IntCollection p0);
    
    boolean removeAll(final IntCollection p0);
    
    @Deprecated
    default boolean removeIf(final Predicate predicate) {
        return this.removeIf((predicate instanceof IntPredicate) ? ((IntPredicate)predicate) : IntCollection::lambda$removeIf$0);
    }
    
    default boolean removeIf(final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        final IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (intPredicate.test(iterator.nextInt())) {
                iterator.remove();
            }
        }
        return true;
    }
    
    default boolean removeIf(final com.viaversion.viaversion.libs.fastutil.ints.IntPredicate intPredicate) {
        return this.removeIf((IntPredicate)intPredicate);
    }
    
    boolean retainAll(final IntCollection p0);
    
    @Deprecated
    default Stream stream() {
        return super.stream();
    }
    
    default IntStream intStream() {
        return StreamSupport.intStream(this.intSpliterator(), false);
    }
    
    @Deprecated
    default Stream parallelStream() {
        return super.parallelStream();
    }
    
    default IntStream intParallelStream() {
        return StreamSupport.intStream(this.intSpliterator(), true);
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Deprecated
    default boolean add(final Object o) {
        return this.add((Integer)o);
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    default boolean lambda$removeIf$0(final Predicate predicate, final int n) {
        return predicate.test(n);
    }
}
