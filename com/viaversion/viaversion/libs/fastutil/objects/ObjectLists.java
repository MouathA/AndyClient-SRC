package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.function.*;
import java.util.*;

public final class ObjectLists
{
    public static final EmptyList EMPTY_LIST;
    
    private ObjectLists() {
    }
    
    public static ObjectList shuffle(final ObjectList list, final Random random) {
        int size = list.size();
        while (size-- != 0) {
            final int nextInt = random.nextInt(size + 1);
            final Object value = list.get(size);
            list.set(size, list.get(nextInt));
            list.set(nextInt, value);
        }
        return list;
    }
    
    public static ObjectList emptyList() {
        return ObjectLists.EMPTY_LIST;
    }
    
    public static ObjectList singleton(final Object o) {
        return new Singleton(o);
    }
    
    public static ObjectList synchronize(final ObjectList list) {
        return (ObjectList)((list instanceof RandomAccess) ? new ObjectLists.SynchronizedRandomAccessList(list) : new ObjectLists.SynchronizedList(list));
    }
    
    public static ObjectList synchronize(final ObjectList list, final Object o) {
        return (ObjectList)((list instanceof RandomAccess) ? new ObjectLists.SynchronizedRandomAccessList(list, o) : new ObjectLists.SynchronizedList(list, o));
    }
    
    public static ObjectList unmodifiable(final ObjectList list) {
        return (ObjectList)((list instanceof RandomAccess) ? new ObjectLists.UnmodifiableRandomAccessList(list) : new ObjectLists.UnmodifiableList(list));
    }
    
    static {
        EMPTY_LIST = new EmptyList();
    }
    
    public static class EmptyList extends ObjectCollections.EmptyCollection implements ObjectList, RandomAccess, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyList() {
        }
        
        @Override
        public Object get(final int n) {
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(final int n, final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object set(final int n, final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int indexOf(final Object o) {
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            return -1;
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void sort(final Comparator comparator) {
        }
        
        @Override
        public void unstableSort(final Comparator comparator) {
        }
        
        @Override
        public ObjectListIterator listIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public ObjectListIterator iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public ObjectListIterator listIterator(final int n) {
            if (n == 0) {
                return ObjectIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }
        
        @Override
        public ObjectList subList(final int n, final int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public void getElements(final int n, final Object[] array, final int n2, final int n3) {
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
        public void addElements(final int n, final Object[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final Object[] array, final int n2, final int n3) {
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
            return ObjectLists.EMPTY_LIST;
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
            return ObjectLists.EMPTY_LIST;
        }
        
        @Override
        public ObjectBidirectionalIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.iterator();
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
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((List)o);
        }
    }
    
    public static class Singleton extends AbstractObjectList implements RandomAccess, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        private final Object element;
        
        protected Singleton(final Object element) {
            this.element = element;
        }
        
        @Override
        public Object get(final int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean contains(final Object o) {
            return Objects.equals(o, this.element);
        }
        
        @Override
        public int indexOf(final Object o) {
            return Objects.equals(o, this.element) ? 0 : -1;
        }
        
        @Override
        public Object[] toArray() {
            return new Object[] { this.element };
        }
        
        @Override
        public ObjectListIterator listIterator() {
            return ObjectIterators.singleton(this.element);
        }
        
        @Override
        public ObjectListIterator iterator() {
            return this.listIterator();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }
        
        @Override
        public ObjectListIterator listIterator(final int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            final ObjectListIterator listIterator = this.listIterator();
            if (n == 1) {
                listIterator.next();
            }
            return listIterator;
        }
        
        @Override
        public ObjectList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return ObjectLists.EMPTY_LIST;
            }
            return this;
        }
        
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
        
        @Override
        public boolean removeIf(final Predicate predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void sort(final Comparator comparator) {
        }
        
        @Override
        public void unstableSort(final Comparator comparator) {
        }
        
        @Override
        public void getElements(final int n, final Object[] array, final int n2, final int n3) {
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
        public void addElements(final int n, final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void addElements(final int n, final Object[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final Object[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setElements(final int n, final Object[] array, final int n2, final int n3) {
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
        public ObjectIterator iterator() {
            return this.iterator();
        }
    }
    
    abstract static class ImmutableListBase extends AbstractObjectList implements ObjectList
    {
        @Deprecated
        @Override
        public final void add(final int n, final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean add(final Object o) {
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
        public final Object remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final boolean remove(final Object o) {
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
        public final void replaceAll(final UnaryOperator unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final Object set(final int n, final Object o) {
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
        public final void addElements(final int n, final Object[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public final void setElements(final int n, final Object[] array, final int n2, final int n3) {
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
    }
}
