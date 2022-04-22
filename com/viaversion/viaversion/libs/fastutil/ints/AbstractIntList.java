package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.io.*;
import java.util.*;

public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack
{
    protected AbstractIntList() {
    }
    
    protected void ensureIndex(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
    }
    
    protected void ensureRestrictedIndex(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }
    
    @Override
    public void add(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean add(final int n) {
        this.add(this.size(), n);
        return true;
    }
    
    @Override
    public int removeInt(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int set(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(int n, final Collection collection) {
        if (collection instanceof IntCollection) {
            return this.addAll(n, (IntCollection)collection);
        }
        this.ensureIndex(n);
        final Iterator<Integer> iterator = collection.iterator();
        final boolean hasNext = iterator.hasNext();
        while (iterator.hasNext()) {
            this.add(n++, (int)iterator.next());
        }
        return hasNext;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return this.addAll(this.size(), collection);
    }
    
    @Override
    public IntListIterator iterator() {
        return this.listIterator();
    }
    
    @Override
    public IntListIterator listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public IntListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new IntIterators.AbstractIndexBasedListIterator(0, n) {
            final AbstractIntList this$0;
            
            @Override
            protected final int get(final int n) {
                return this.this$0.getInt(n);
            }
            
            @Override
            protected final void add(final int n, final int n2) {
                this.this$0.add(n, n2);
            }
            
            @Override
            protected final void set(final int n, final int n2) {
                this.this$0.set(n, n2);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$0.removeInt(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$0.size();
            }
        };
    }
    
    @Override
    public boolean contains(final int n) {
        return this.indexOf(n) >= 0;
    }
    
    @Override
    public int indexOf(final int n) {
        final IntListIterator listIterator = this.listIterator();
        while (listIterator.hasNext()) {
            if (n == listIterator.nextInt()) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final int n) {
        final IntListIterator listIterator = this.listIterator(this.size());
        while (listIterator.hasPrevious()) {
            if (n == listIterator.previousInt()) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }
    
    @Override
    public void size(final int n) {
        int size = this.size();
        if (n > size) {
            while (size++ < n) {
                this.add(0);
            }
        }
        else {
            while (size-- != n) {
                this.removeInt(size);
            }
        }
    }
    
    @Override
    public IntList subList(final int n, final int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return (this instanceof RandomAccess) ? new IntRandomAccessSubList(this, n, n2) : new IntSubList(this, n, n2);
    }
    
    @Override
    public void forEach(final IntConsumer intConsumer) {
        if (this instanceof RandomAccess) {
            while (0 < this.size()) {
                intConsumer.accept(this.getInt(0));
                int n = 0;
                ++n;
            }
        }
        else {
            super.forEach(intConsumer);
        }
    }
    
    @Override
    public void removeElements(final int n, final int n2) {
        this.ensureIndex(n2);
        final IntListIterator listIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            listIterator.nextInt();
            listIterator.remove();
        }
    }
    
    @Override
    public void addElements(int n, final int[] array, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        if (this instanceof RandomAccess) {
            while (n3-- != 0) {
                this.add(n++, array[n2++]);
            }
        }
        else {
            final IntListIterator listIterator = this.listIterator(n);
            while (n3-- != 0) {
                listIterator.add(array[n2++]);
            }
        }
    }
    
    @Override
    public void addElements(final int n, final int[] array) {
        this.addElements(n, array, 0, array.length);
    }
    
    @Override
    public void getElements(final int n, final int[] array, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int n4 = n;
            while (n3-- != 0) {
                array[n2++] = this.getInt(n4++);
            }
        }
        else {
            final IntListIterator listIterator = this.listIterator(n);
            while (n3-- != 0) {
                array[n2++] = listIterator.nextInt();
            }
        }
    }
    
    @Override
    public void setElements(final int n, final int[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            while (0 < n3) {
                this.set(0 + n, array[0 + n2]);
                int n4 = 0;
                ++n4;
            }
        }
        else {
            final IntListIterator listIterator = this.listIterator(n);
            while (0 < n3) {
                listIterator.nextInt();
                final IntListIterator intListIterator = listIterator;
                final int n5 = 0;
                int n6 = 0;
                ++n6;
                intListIterator.set(array[n2 + n5]);
            }
        }
    }
    
    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }
    
    @Override
    public int hashCode() {
        final IntListIterator iterator = this.iterator();
        int size = this.size();
        while (size-- != 0) {
            final int n = 31 + iterator.nextInt();
        }
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final List list = (List)o;
        int size = this.size();
        if (size != list.size()) {
            return false;
        }
        if (list instanceof IntList) {
            final IntListIterator listIterator = this.listIterator();
            final IntListIterator listIterator2 = ((IntList)list).listIterator();
            while (size-- != 0) {
                if (listIterator.nextInt() != listIterator2.nextInt()) {
                    return false;
                }
            }
            return true;
        }
        final IntListIterator listIterator3 = this.listIterator();
        final ListIterator<Object> listIterator4 = list.listIterator();
        while (size-- != 0) {
            if (!Objects.equals(listIterator3.next(), listIterator4.next())) {
                return false;
            }
        }
        return true;
    }
    
    public int compareTo(final List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof IntList) {
            final IntListIterator listIterator = this.listIterator();
            final IntListIterator listIterator2 = ((AbstractIntList)list).listIterator();
            while (listIterator.hasNext() && listIterator2.hasNext()) {
                final int compare;
                if ((compare = Integer.compare(listIterator.nextInt(), listIterator2.nextInt())) != 0) {
                    return compare;
                }
            }
            return listIterator2.hasNext() ? -1 : (listIterator.hasNext() ? 1 : 0);
        }
        final IntListIterator listIterator3 = this.listIterator();
        final ListIterator<Object> listIterator4 = list.listIterator();
        while (listIterator3.hasNext() && listIterator4.hasNext()) {
            final int compareTo;
            if ((compareTo = listIterator3.next().compareTo(listIterator4.next())) != 0) {
                return compareTo;
            }
        }
        return listIterator4.hasNext() ? -1 : (listIterator3.hasNext() ? 1 : 0);
    }
    
    @Override
    public void push(final int n) {
        this.add(n);
    }
    
    @Override
    public int popInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeInt(this.size() - 1);
    }
    
    @Override
    public int topInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getInt(this.size() - 1);
    }
    
    @Override
    public int peekInt(final int n) {
        return this.getInt(this.size() - 1 - n);
    }
    
    @Override
    public boolean rem(final int n) {
        final int index = this.indexOf(n);
        if (index == -1) {
            return false;
        }
        this.removeInt(index);
        return true;
    }
    
    @Override
    public int[] toIntArray() {
        final int size = this.size();
        final int[] array = new int[size];
        this.getElements(0, array, 0, size);
        return array;
    }
    
    @Override
    public int[] toArray(int[] copy) {
        final int size = this.size();
        if (copy.length < size) {
            copy = Arrays.copyOf(copy, size);
        }
        this.getElements(0, copy, 0, size);
        return copy;
    }
    
    @Override
    public boolean addAll(int n, final IntCollection collection) {
        this.ensureIndex(n);
        final IntIterator iterator = collection.iterator();
        final boolean hasNext = iterator.hasNext();
        while (iterator.hasNext()) {
            this.add(n++, iterator.nextInt());
        }
        return hasNext;
    }
    
    @Override
    public boolean addAll(final IntCollection collection) {
        return this.addAll(this.size(), collection);
    }
    
    @Override
    public final void replaceAll(final IntUnaryOperator intUnaryOperator) {
        this.replaceAll((java.util.function.IntUnaryOperator)intUnaryOperator);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final IntListIterator iterator = this.iterator();
        int size = this.size();
        sb.append("[");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            sb.append(String.valueOf(iterator.nextInt()));
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public IntIterator iterator() {
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
    
    public static class IntRandomAccessSubList extends IntSubList implements RandomAccess
    {
        private static final long serialVersionUID = -107070782945191929L;
        
        public IntRandomAccessSubList(final IntList list, final int n, final int n2) {
            super(list, n, n2);
        }
        
        @Override
        public IntList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new IntRandomAccessSubList(this, n, n2);
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
    }
    
    public static class IntSubList extends AbstractIntList implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled;
        
        public IntSubList(final IntList l, final int from, final int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }
        
        @Override
        public boolean add(final int n) {
            this.l.add(this.to, n);
            ++this.to;
            assert this != 0;
            return true;
        }
        
        @Override
        public void add(final int n, final int n2) {
            this.ensureIndex(n);
            this.l.add(this.from + n, n2);
            ++this.to;
            assert this != 0;
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }
        
        @Override
        public int getInt(final int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getInt(this.from + n);
        }
        
        @Override
        public int removeInt(final int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeInt(this.from + n);
        }
        
        @Override
        public int set(final int n, final int n2) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, n2);
        }
        
        @Override
        public int size() {
            return this.to - this.from;
        }
        
        @Override
        public void getElements(final int n, final int[] array, final int n2, final int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, array, n2, n3);
        }
        
        @Override
        public void removeElements(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            this.l.removeElements(this.from + n, this.from + n2);
            this.to -= n2 - n;
            assert this != 0;
        }
        
        @Override
        public void addElements(final int n, final int[] array, final int n2, final int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, array, n2, n3);
            this.to += n3;
            assert this != 0;
        }
        
        @Override
        public void setElements(final int n, final int[] array, final int n2, final int n3) {
            this.ensureIndex(n);
            this.l.setElements(this.from + n, array, n2, n3);
            assert this != 0;
        }
        
        @Override
        public IntListIterator listIterator(final int n) {
            this.ensureIndex(n);
            return (this.l instanceof RandomAccess) ? new RandomAccessIter(n) : new ParentWrappingIter(this.l.listIterator(n + this.from));
        }
        
        @Override
        public IntSpliterator spliterator() {
            return (this.l instanceof RandomAccess) ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }
        
        @Override
        public IntList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new IntSubList(this, n, n2);
        }
        
        @Override
        public boolean rem(final int n) {
            final int index = this.indexOf(n);
            if (index == -1) {
                return false;
            }
            --this.to;
            this.l.removeInt(this.from + index);
            assert this != 0;
            return true;
        }
        
        @Override
        public boolean addAll(final int n, final IntCollection collection) {
            this.ensureIndex(n);
            return super.addAll(n, collection);
        }
        
        @Override
        public boolean addAll(final int n, final IntList list) {
            this.ensureIndex(n);
            return super.addAll(n, list);
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
            return super.listIterator();
        }
        
        @Override
        public Iterator iterator() {
            return super.iterator();
        }
        
        @Override
        public int compareTo(final Object o) {
            return super.compareTo((List)o);
        }
        
        @Override
        public IntIterator iterator() {
            return super.iterator();
        }
        
        static boolean access$000(final IntSubList list) {
            return list.assertRange();
        }
        
        static {
            $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
        }
        
        private final class RandomAccessIter extends IntIterators.AbstractIndexBasedListIterator
        {
            static final boolean $assertionsDisabled;
            final IntSubList this$0;
            
            RandomAccessIter(final IntSubList this$0, final int n) {
                this.this$0 = this$0;
                super(0, n);
            }
            
            @Override
            protected final int get(final int n) {
                return this.this$0.l.getInt(this.this$0.from + n);
            }
            
            @Override
            protected final void add(final int n, final int n2) {
                this.this$0.add(n, n2);
            }
            
            @Override
            protected final void set(final int n, final int n2) {
                this.this$0.set(n, n2);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$0.removeInt(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$0.to - this.this$0.from;
            }
            
            @Override
            public void add(final int n) {
                super.add(n);
                assert IntSubList.access$000(this.this$0);
            }
            
            @Override
            public void remove() {
                super.remove();
                assert IntSubList.access$000(this.this$0);
            }
            
            static {
                $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
            }
        }
        
        private class ParentWrappingIter implements IntListIterator
        {
            private IntListIterator parent;
            final IntSubList this$0;
            
            ParentWrappingIter(final IntSubList this$0, final IntListIterator parent) {
                this.this$0 = this$0;
                this.parent = parent;
            }
            
            @Override
            public int nextIndex() {
                return this.parent.nextIndex() - this.this$0.from;
            }
            
            @Override
            public int previousIndex() {
                return this.parent.previousIndex() - this.this$0.from;
            }
            
            @Override
            public boolean hasNext() {
                return this.parent.nextIndex() < this.this$0.to;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= this.this$0.from;
            }
            
            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.nextInt();
            }
            
            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previousInt();
            }
            
            @Override
            public void add(final int n) {
                this.parent.add(n);
            }
            
            @Override
            public void set(final int n) {
                this.parent.set(n);
            }
            
            @Override
            public void remove() {
                this.parent.remove();
            }
            
            @Override
            public int back(final int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int previousIndex = this.parent.previousIndex();
                int n2 = previousIndex - n;
                if (n2 < this.this$0.from - 1) {
                    n2 = this.this$0.from - 1;
                }
                return this.parent.back(n2 - previousIndex);
            }
            
            @Override
            public int skip(final int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int nextIndex = this.parent.nextIndex();
                int to = nextIndex + n;
                if (to > this.this$0.to) {
                    to = this.this$0.to;
                }
                return this.parent.skip(to - nextIndex);
            }
        }
    }
    
    static final class IndexBasedSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator
    {
        final IntList l;
        
        IndexBasedSpliterator(final IntList l, final int n) {
            super(n);
            this.l = l;
        }
        
        IndexBasedSpliterator(final IntList l, final int n, final int n2) {
            super(n, n2);
            this.l = l;
        }
        
        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }
        
        @Override
        protected final int get(final int n) {
            return this.l.getInt(n);
        }
        
        @Override
        protected final IndexBasedSpliterator makeForSplit(final int n, final int n2) {
            return new IndexBasedSpliterator(this.l, n, n2);
        }
        
        @Override
        protected IntSpliterator makeForSplit(final int n, final int n2) {
            return this.makeForSplit(n, n2);
        }
    }
}
