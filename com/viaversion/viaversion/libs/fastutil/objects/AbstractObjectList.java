package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.io.*;
import java.util.*;

public abstract class AbstractObjectList extends AbstractObjectCollection implements ObjectList, Stack
{
    protected AbstractObjectList() {
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
    public void add(final int n, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean add(final Object o) {
        this.add(this.size(), o);
        return true;
    }
    
    @Override
    public Object remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object set(final int n, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(int n, final Collection collection) {
        this.ensureIndex(n);
        final Iterator<Object> iterator = collection.iterator();
        final boolean hasNext = iterator.hasNext();
        while (iterator.hasNext()) {
            this.add(n++, iterator.next());
        }
        return hasNext;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return this.addAll(this.size(), collection);
    }
    
    @Override
    public ObjectListIterator iterator() {
        return this.listIterator();
    }
    
    @Override
    public ObjectListIterator listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public ObjectListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new ObjectIterators.AbstractIndexBasedListIterator(0, n) {
            final AbstractObjectList this$0;
            
            @Override
            protected final Object get(final int n) {
                return this.this$0.get(n);
            }
            
            @Override
            protected final void add(final int n, final Object o) {
                this.this$0.add(n, o);
            }
            
            @Override
            protected final void set(final int n, final Object o) {
                this.this$0.set(n, o);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$0.remove(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$0.size();
            }
        };
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o) >= 0;
    }
    
    @Override
    public int indexOf(final Object o) {
        final ObjectListIterator listIterator = this.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equals(o, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        final ObjectListIterator listIterator = this.listIterator(this.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equals(o, listIterator.previous())) {
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
                this.add(null);
            }
        }
        else {
            while (size-- != n) {
                this.remove(size);
            }
        }
    }
    
    @Override
    public ObjectList subList(final int n, final int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return (this instanceof RandomAccess) ? new ObjectRandomAccessSubList(this, n, n2) : new ObjectSubList(this, n, n2);
    }
    
    @Override
    public void forEach(final Consumer consumer) {
        if (this instanceof RandomAccess) {
            while (0 < this.size()) {
                consumer.accept(this.get(0));
                int n = 0;
                ++n;
            }
        }
        else {
            super.forEach(consumer);
        }
    }
    
    @Override
    public void removeElements(final int n, final int n2) {
        this.ensureIndex(n2);
        final ObjectListIterator listIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            listIterator.next();
            listIterator.remove();
        }
    }
    
    @Override
    public void addElements(int n, final Object[] array, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        if (this instanceof RandomAccess) {
            while (n3-- != 0) {
                this.add(n++, array[n2++]);
            }
        }
        else {
            final ObjectListIterator listIterator = this.listIterator(n);
            while (n3-- != 0) {
                listIterator.add(array[n2++]);
            }
        }
    }
    
    @Override
    public void addElements(final int n, final Object[] array) {
        this.addElements(n, array, 0, array.length);
    }
    
    @Override
    public void getElements(final int n, final Object[] array, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int n4 = n;
            while (n3-- != 0) {
                array[n2++] = this.get(n4++);
            }
        }
        else {
            final ObjectListIterator listIterator = this.listIterator(n);
            while (n3-- != 0) {
                array[n2++] = listIterator.next();
            }
        }
    }
    
    @Override
    public void setElements(final int n, final Object[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(array, n2, n3);
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
            final ObjectListIterator listIterator = this.listIterator(n);
            while (0 < n3) {
                listIterator.next();
                final ObjectListIterator objectListIterator = listIterator;
                final int n5 = 0;
                int n6 = 0;
                ++n6;
                objectListIterator.set(array[n2 + n5]);
            }
        }
    }
    
    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }
    
    @Override
    public Object[] toArray() {
        final int size = this.size();
        final Object[] array = new Object[size];
        this.getElements(0, array, 0, size);
        return array;
    }
    
    @Override
    public Object[] toArray(Object[] copy) {
        final int size = this.size();
        if (copy.length < size) {
            copy = Arrays.copyOf(copy, size);
        }
        this.getElements(0, copy, 0, size);
        if (copy.length > size) {
            copy[size] = null;
        }
        return copy;
    }
    
    @Override
    public int hashCode() {
        final ObjectListIterator iterator = this.iterator();
        int size = this.size();
        while (size-- != 0) {
            final Object next = iterator.next();
            final int n = 31 + ((next == null) ? 0 : next.hashCode());
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
        final ObjectListIterator listIterator = this.listIterator();
        final ListIterator<Object> listIterator2 = list.listIterator();
        while (size-- != 0) {
            if (!Objects.equals(listIterator.next(), listIterator2.next())) {
                return false;
            }
        }
        return true;
    }
    
    public int compareTo(final List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof ObjectList) {
            final ObjectListIterator listIterator = this.listIterator();
            final ObjectListIterator listIterator2 = ((AbstractObjectList)list).listIterator();
            while (listIterator.hasNext() && listIterator2.hasNext()) {
                final int compareTo;
                if ((compareTo = listIterator.next().compareTo(listIterator2.next())) != 0) {
                    return compareTo;
                }
            }
            return listIterator2.hasNext() ? -1 : (listIterator.hasNext() ? 1 : 0);
        }
        final ObjectListIterator listIterator3 = this.listIterator();
        final ListIterator<Object> listIterator4 = list.listIterator();
        while (listIterator3.hasNext() && listIterator4.hasNext()) {
            final int compareTo2;
            if ((compareTo2 = listIterator3.next().compareTo(listIterator4.next())) != 0) {
                return compareTo2;
            }
        }
        return listIterator4.hasNext() ? -1 : (listIterator3.hasNext() ? 1 : 0);
    }
    
    @Override
    public void push(final Object o) {
        this.add(o);
    }
    
    @Override
    public Object pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(this.size() - 1);
    }
    
    @Override
    public Object top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.get(this.size() - 1);
    }
    
    @Override
    public Object peek(final int n) {
        return this.get(this.size() - 1 - n);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectListIterator iterator = this.iterator();
        int size = this.size();
        sb.append("[");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            final AbstractObjectList next = iterator.next();
            if (this == next) {
                sb.append("(this list)");
            }
            else {
                sb.append(String.valueOf(next));
            }
        }
        sb.append("]");
        return sb.toString();
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
    
    public static class ObjectRandomAccessSubList extends ObjectSubList implements RandomAccess
    {
        private static final long serialVersionUID = -107070782945191929L;
        
        public ObjectRandomAccessSubList(final ObjectList list, final int n, final int n2) {
            super(list, n, n2);
        }
        
        @Override
        public ObjectList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ObjectRandomAccessSubList(this, n, n2);
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
    }
    
    public static class ObjectSubList extends AbstractObjectList implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled;
        
        public ObjectSubList(final ObjectList l, final int from, final int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }
        
        @Override
        public boolean add(final Object o) {
            this.l.add(this.to, o);
            ++this.to;
            assert this != 0;
            return true;
        }
        
        @Override
        public void add(final int n, final Object o) {
            this.ensureIndex(n);
            this.l.add(this.from + n, o);
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
        public Object get(final int n) {
            this.ensureRestrictedIndex(n);
            return this.l.get(this.from + n);
        }
        
        @Override
        public Object remove(final int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.remove(this.from + n);
        }
        
        @Override
        public Object set(final int n, final Object o) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, o);
        }
        
        @Override
        public int size() {
            return this.to - this.from;
        }
        
        @Override
        public void getElements(final int n, final Object[] array, final int n2, final int n3) {
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
        public void addElements(final int n, final Object[] array, final int n2, final int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, array, n2, n3);
            this.to += n3;
            assert this != 0;
        }
        
        @Override
        public void setElements(final int n, final Object[] array, final int n2, final int n3) {
            this.ensureIndex(n);
            this.l.setElements(this.from + n, array, n2, n3);
            assert this != 0;
        }
        
        @Override
        public ObjectListIterator listIterator(final int n) {
            this.ensureIndex(n);
            return (this.l instanceof RandomAccess) ? new RandomAccessIter(n) : new ParentWrappingIter(this.l.listIterator(n + this.from));
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return (this.l instanceof RandomAccess) ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }
        
        @Override
        public ObjectList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ObjectSubList(this, n, n2);
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
        public ObjectIterator iterator() {
            return super.iterator();
        }
        
        static boolean access$000(final ObjectSubList list) {
            return list.assertRange();
        }
        
        static {
            $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
        }
        
        private final class RandomAccessIter extends ObjectIterators.AbstractIndexBasedListIterator
        {
            static final boolean $assertionsDisabled;
            final ObjectSubList this$0;
            
            RandomAccessIter(final ObjectSubList this$0, final int n) {
                this.this$0 = this$0;
                super(0, n);
            }
            
            @Override
            protected final Object get(final int n) {
                return this.this$0.l.get(this.this$0.from + n);
            }
            
            @Override
            protected final void add(final int n, final Object o) {
                this.this$0.add(n, o);
            }
            
            @Override
            protected final void set(final int n, final Object o) {
                this.this$0.set(n, o);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$0.remove(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$0.to - this.this$0.from;
            }
            
            @Override
            public void add(final Object o) {
                super.add(o);
                assert ObjectSubList.access$000(this.this$0);
            }
            
            @Override
            public void remove() {
                super.remove();
                assert ObjectSubList.access$000(this.this$0);
            }
            
            static {
                $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
            }
        }
        
        private class ParentWrappingIter implements ObjectListIterator
        {
            private ObjectListIterator parent;
            final ObjectSubList this$0;
            
            ParentWrappingIter(final ObjectSubList this$0, final ObjectListIterator parent) {
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
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.next();
            }
            
            @Override
            public Object previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previous();
            }
            
            @Override
            public void add(final Object o) {
                this.parent.add(o);
            }
            
            @Override
            public void set(final Object o) {
                this.parent.set(o);
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
    
    static final class IndexBasedSpliterator extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator
    {
        final ObjectList l;
        
        IndexBasedSpliterator(final ObjectList l, final int n) {
            super(n);
            this.l = l;
        }
        
        IndexBasedSpliterator(final ObjectList l, final int n, final int n2) {
            super(n, n2);
            this.l = l;
        }
        
        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }
        
        @Override
        protected final Object get(final int n) {
            return this.l.get(n);
        }
        
        @Override
        protected final IndexBasedSpliterator makeForSplit(final int n, final int n2) {
            return new IndexBasedSpliterator(this.l, n, n2);
        }
        
        @Override
        protected ObjectSpliterator makeForSplit(final int n, final int n2) {
            return this.makeForSplit(n, n2);
        }
    }
}
