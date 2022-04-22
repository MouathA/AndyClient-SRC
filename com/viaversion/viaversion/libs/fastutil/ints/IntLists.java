package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.function.*;
import java.util.*;

public final class IntLists
{
    public static final EmptyList EMPTY_LIST;
    
    private IntLists() {
    }
    
    public static IntList shuffle(final IntList list, final Random random) {
        int size = list.size();
        while (size-- != 0) {
            final int nextInt = random.nextInt(size + 1);
            final int int1 = list.getInt(size);
            list.set(size, list.getInt(nextInt));
            list.set(nextInt, int1);
        }
        return list;
    }
    
    public static IntList emptyList() {
        return IntLists.EMPTY_LIST;
    }
    
    public static IntList singleton(final int n) {
        return new Singleton(n);
    }
    
    public static IntList singleton(final Object o) {
        return new Singleton((int)o);
    }
    
    public static IntList synchronize(final IntList list) {
        return (IntList)((list instanceof RandomAccess) ? new IntLists.SynchronizedRandomAccessList(list) : new IntLists.SynchronizedList(list));
    }
    
    public static IntList synchronize(final IntList list, final Object o) {
        return (IntList)((list instanceof RandomAccess) ? new IntLists.SynchronizedRandomAccessList(list, o) : new IntLists.SynchronizedList(list, o));
    }
    
    public static IntList unmodifiable(final IntList list) {
        return (IntList)((list instanceof RandomAccess) ? new IntLists.UnmodifiableRandomAccessList(list) : new IntLists.UnmodifiableList(list));
    }
    
    static {
        EMPTY_LIST = new EmptyList();
    }
    
    public static class EmptyList extends IntCollections.EmptyCollection implements IntList, RandomAccess, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyList() {
        }
        
        @Override
        public int getInt(final int n) {
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public boolean rem(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int removeInt(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int set(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int indexOf(final int n) {
            return -1;
        }
        
        @Override
        public int lastIndexOf(final int n) {
            return -1;
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final int n, final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final int n, final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public void add(final int n, final Integer n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Integer get(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public boolean add(final Integer n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Integer set(final int n, final Integer n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Integer remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public int indexOf(final Object o) {
            return -1;
        }
        
        @Deprecated
        @Override
        public int lastIndexOf(final Object o) {
            return -1;
        }
        
        @Override
        public void sort(final IntComparator intComparator) {
        }
        
        @Override
        public void unstableSort(final IntComparator intComparator) {
        }
        
        @Deprecated
        @Override
        public void sort(final Comparator comparator) {
        }
        
        @Deprecated
        @Override
        public void unstableSort(final Comparator comparator) {
        }
        
        @Override
        public IntListIterator listIterator() {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public IntListIterator iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public IntListIterator listIterator(final int n) {
            if (n == 0) {
                return IntIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }
        
        @Override
        public IntList subList(final int n, final int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public void getElements(final int n, final int[] array, final int n2, final int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= array.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public void removeElements(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void size(final int n) {
            throw new UnsupportedOperationException();
        }
        
        public int compareTo(final List list) {
            if (list == this) {
                return 0;
            }
            return list.isEmpty() ? 0 : -1;
        }
        
        public Object clone() {
            return IntLists.EMPTY_LIST;
        }
        
        @Override
        public int hashCode() {
            return 1;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof List && ((List)o).isEmpty();
        }
        
        @Override
        public String toString() {
            return "[]";
        }
        
        private Object readResolve() {
            return IntLists.EMPTY_LIST;
        }
        
        @Override
        public IntBidirectionalIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public IntIterator iterator() {
            return this.iterator();
        }
        
        @Deprecated
        @Override
        public boolean add(final Object o) {
            return this.add((Integer)o);
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return this.listIterator(n);
        }
        
        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }
        
        @Deprecated
        @Override
        public Object remove(final int n) {
            return this.remove(n);
        }
        
        @Deprecated
        @Override
        public void add(final int n, final Object o) {
            this.add(n, (Integer)o);
        }
        
        @Deprecated
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Integer)o);
        }
        
        @Deprecated
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((List)o);
        }
    }
    
    public static class Singleton extends AbstractIntList implements RandomAccess, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        private final int element;
        
        protected Singleton(final int element) {
            this.element = element;
        }
        
        @Override
        public int getInt(final int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public boolean rem(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int removeInt(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean contains(final int n) {
            return n == this.element;
        }
        
        @Override
        public int indexOf(final int n) {
            return (n == this.element) ? 0 : -1;
        }
        
        @Override
        public int[] toIntArray() {
            return new int[] { this.element };
        }
        
        @Override
        public IntListIterator listIterator() {
            return IntIterators.singleton(this.element);
        }
        
        @Override
        public IntListIterator iterator() {
            return this.listIterator();
        }
        
        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element);
        }
        
        @Override
        public IntListIterator listIterator(final int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            final IntListIterator listIterator = this.listIterator();
            if (n == 1) {
                listIterator.nextInt();
            }
            return listIterator;
        }
        
        @Override
        public IntList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return IntLists.EMPTY_LIST;
            }
            return this;
        }
        
        @Deprecated
        @Override
        public void forEach(final Consumer consumer) {
            consumer.accept(this.element);
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public boolean removeIf(final Predicate predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            intConsumer.accept(this.element);
        }
        
        @Override
        public boolean addAll(final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final int n, final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final int n, final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeIf(final IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Object[] toArray() {
            return new Object[] { this.element };
        }
        
        @Override
        public void sort(final IntComparator intComparator) {
        }
        
        @Override
        public void unstableSort(final IntComparator intComparator) {
        }
        
        @Deprecated
        @Override
        public void sort(final Comparator comparator) {
        }
        
        @Deprecated
        @Override
        public void unstableSort(final Comparator comparator) {
        }
        
        @Override
        public void getElements(final int n, final int[] array, final int n2, final int n3) {
            if (n2 < 0) {
                throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
            }
            if (n2 + n3 > array.length) {
                throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + array.length + ")");
            }
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
            }
            if (n3 <= 0) {
                return;
            }
            array[n2] = this.element;
        }
        
        @Override
        public void removeElements(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public void size(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        public Object clone() {
            return this;
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return this.listIterator(n);
        }
        
        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        @Override
        public IntIterator iterator() {
            return this.iterator();
        }
    }
    
    abstract static class ImmutableListBase extends AbstractIntList implements IntList
    {
        @Deprecated
        @Override
        public final void add(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean add(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final int n, final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final int removeInt(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean rem(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean removeAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean retainAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean removeIf(final Predicate predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean removeIf(final IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void replaceAll(final IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void add(final int n, final Integer n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean add(final Integer n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final Integer remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final Integer set(final int n, final Integer n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final int n, final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean addAll(final int n, final IntList list) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean removeAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean retainAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final int set(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void size(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void removeElements(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void addElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void setElements(final int n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void sort(final IntComparator intComparator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void unstableSort(final IntComparator intComparator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void sort(final Comparator comparator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void unstableSort(final Comparator comparator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Object remove(final int n) {
            return this.remove(n);
        }
        
        @Deprecated
        @Override
        public void add(final int n, final Object o) {
            this.add(n, (Integer)o);
        }
        
        @Deprecated
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Integer)o);
        }
        
        @Deprecated
        @Override
        public boolean add(final Object o) {
            return this.add((Integer)o);
        }
    }
}
