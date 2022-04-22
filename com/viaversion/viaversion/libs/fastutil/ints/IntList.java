package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.util.*;

public interface IntList extends List, Comparable, IntCollection
{
    IntListIterator iterator();
    
    default IntSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractIntList.IndexBasedSpliterator(this, 0);
        }
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 16720);
    }
    
    IntListIterator listIterator();
    
    IntListIterator listIterator(final int p0);
    
    IntList subList(final int p0, final int p1);
    
    void size(final int p0);
    
    void getElements(final int p0, final int[] p1, final int p2, final int p3);
    
    void removeElements(final int p0, final int p1);
    
    void addElements(final int p0, final int[] p1);
    
    void addElements(final int p0, final int[] p1, final int p2, final int p3);
    
    default void setElements(final int[] array) {
        this.setElements(0, array);
    }
    
    default void setElements(final int n, final int[] array) {
        this.setElements(n, array, 0, array.length);
    }
    
    default void setElements(final int n, final int[] array, final int n2, final int n3) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
        IntArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        final IntListIterator listIterator = this.listIterator(n);
        while (0 < n3) {
            listIterator.nextInt();
            final IntListIterator intListIterator = listIterator;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            intListIterator.set(array[n2 + n4]);
        }
    }
    
    boolean add(final int p0);
    
    void add(final int p0, final int p1);
    
    @Deprecated
    default void add(final int n, final Integer n2) {
        this.add(n, (int)n2);
    }
    
    boolean addAll(final int p0, final IntCollection p1);
    
    int set(final int p0, final int p1);
    
    default void replaceAll(final IntUnaryOperator intUnaryOperator) {
        final IntListIterator listIterator = this.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set(intUnaryOperator.applyAsInt(listIterator.nextInt()));
        }
    }
    
    default void replaceAll(final com.viaversion.viaversion.libs.fastutil.ints.IntUnaryOperator intUnaryOperator) {
        this.replaceAll((IntUnaryOperator)intUnaryOperator);
    }
    
    @Deprecated
    default void replaceAll(final UnaryOperator unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        IntUnaryOperator intUnaryOperator;
        if (unaryOperator instanceof IntUnaryOperator) {
            intUnaryOperator = (IntUnaryOperator)unaryOperator;
        }
        else {
            Objects.requireNonNull(unaryOperator);
            intUnaryOperator = unaryOperator::apply;
        }
        this.replaceAll(intUnaryOperator);
    }
    
    int getInt(final int p0);
    
    int indexOf(final int p0);
    
    int lastIndexOf(final int p0);
    
    @Deprecated
    default boolean contains(final Object o) {
        return super.contains(o);
    }
    
    @Deprecated
    default Integer get(final int n) {
        return this.getInt(n);
    }
    
    @Deprecated
    default int indexOf(final Object o) {
        return this.indexOf((int)o);
    }
    
    @Deprecated
    default int lastIndexOf(final Object o) {
        return this.lastIndexOf((int)o);
    }
    
    @Deprecated
    default boolean add(final Integer n) {
        return this.add((int)n);
    }
    
    int removeInt(final int p0);
    
    @Deprecated
    default boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Deprecated
    default Integer remove(final int n) {
        return this.removeInt(n);
    }
    
    @Deprecated
    default Integer set(final int n, final Integer n2) {
        return this.set(n, (int)n2);
    }
    
    default boolean addAll(final int n, final IntList list) {
        return this.addAll(n, (IntCollection)list);
    }
    
    default boolean addAll(final IntList list) {
        return this.addAll(this.size(), list);
    }
    
    default IntList of() {
        return IntImmutableList.of();
    }
    
    default IntList of(final int n) {
        return IntLists.singleton(n);
    }
    
    default IntList of(final int n, final int n2) {
        return IntImmutableList.of(new int[] { n, n2 });
    }
    
    default IntList of(final int n, final int n2, final int n3) {
        return IntImmutableList.of(new int[] { n, n2, n3 });
    }
    
    default IntList of(final int... array) {
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                return of(array[0]);
            }
            default: {
                return IntImmutableList.of(array);
            }
        }
    }
    
    @Deprecated
    default void sort(final Comparator comparator) {
        this.sort(IntComparators.asIntComparator(comparator));
    }
    
    default void sort(final IntComparator intComparator) {
        if (intComparator == null) {
            this.unstableSort(intComparator);
        }
        else {
            final int[] intArray = this.toIntArray();
            IntArrays.stableSort(intArray, intComparator);
            this.setElements(intArray);
        }
    }
    
    @Deprecated
    default void unstableSort(final Comparator comparator) {
        this.unstableSort(IntComparators.asIntComparator(comparator));
    }
    
    default void unstableSort(final IntComparator intComparator) {
        final int[] intArray = this.toIntArray();
        if (intComparator == null) {
            IntArrays.unstableSort(intArray);
        }
        else {
            IntArrays.unstableSort(intArray, intComparator);
        }
        this.setElements(intArray);
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default List subList(final int n, final int n2) {
        return this.subList(n, n2);
    }
    
    default ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
    
    default ListIterator listIterator() {
        return this.listIterator();
    }
    
    @Deprecated
    default Object remove(final int n) {
        return this.remove(n);
    }
    
    @Deprecated
    default void add(final int n, final Object o) {
        this.add(n, (Integer)o);
    }
    
    @Deprecated
    default Object set(final int n, final Object o) {
        return this.set(n, (Integer)o);
    }
    
    @Deprecated
    default Object get(final int n) {
        return this.get(n);
    }
    
    @Deprecated
    default boolean add(final Object o) {
        return this.add((Integer)o);
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    default IntIterator iterator() {
        return this.iterator();
    }
}
