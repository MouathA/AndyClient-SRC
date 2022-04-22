package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectList extends List, Comparable, ObjectCollection
{
    ObjectListIterator iterator();
    
    default ObjectSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractObjectList.IndexBasedSpliterator(this, 0);
        }
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 16464);
    }
    
    ObjectListIterator listIterator();
    
    ObjectListIterator listIterator(final int p0);
    
    ObjectList subList(final int p0, final int p1);
    
    void size(final int p0);
    
    void getElements(final int p0, final Object[] p1, final int p2, final int p3);
    
    void removeElements(final int p0, final int p1);
    
    void addElements(final int p0, final Object[] p1);
    
    void addElements(final int p0, final Object[] p1, final int p2, final int p3);
    
    default void setElements(final Object[] array) {
        this.setElements(0, array);
    }
    
    default void setElements(final int n, final Object[] array) {
        this.setElements(n, array, 0, array.length);
    }
    
    default void setElements(final int n, final Object[] array, final int n2, final int n3) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        final ObjectListIterator listIterator = this.listIterator(n);
        while (0 < n3) {
            listIterator.next();
            final ObjectListIterator objectListIterator = listIterator;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            objectListIterator.set(array[n2 + n4]);
        }
    }
    
    default boolean addAll(final int n, final ObjectList list) {
        return this.addAll(n, list);
    }
    
    default boolean addAll(final ObjectList list) {
        return this.addAll(this.size(), list);
    }
    
    default ObjectList of() {
        return ObjectImmutableList.of();
    }
    
    default ObjectList of(final Object o) {
        return ObjectLists.singleton(o);
    }
    
    default ObjectList of(final Object o, final Object o2) {
        return ObjectImmutableList.of(new Object[] { o, o2 });
    }
    
    default ObjectList of(final Object o, final Object o2, final Object o3) {
        return ObjectImmutableList.of(new Object[] { o, o2, o3 });
    }
    
    @SafeVarargs
    default ObjectList of(final Object... array) {
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                return of(array[0]);
            }
            default: {
                return ObjectImmutableList.of(array);
            }
        }
    }
    
    default void sort(final Comparator comparator) {
        final Object[] array = this.toArray();
        if (comparator == null) {
            ObjectArrays.stableSort(array);
        }
        else {
            ObjectArrays.stableSort(array, comparator);
        }
        this.setElements(array);
    }
    
    default void unstableSort(final Comparator comparator) {
        final Object[] array = this.toArray();
        if (comparator == null) {
            ObjectArrays.unstableSort(array);
        }
        else {
            ObjectArrays.unstableSort(array, comparator);
        }
        this.setElements(array);
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
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    default ObjectIterator iterator() {
        return this.iterator();
    }
}
