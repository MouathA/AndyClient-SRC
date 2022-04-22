package com.google.common.collect;

import com.google.common.primitives.*;
import java.util.concurrent.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.math.*;
import com.google.common.math.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Lists
{
    private Lists() {
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayList() {
        return new ArrayList();
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayList(final Object... array) {
        Preconditions.checkNotNull(array);
        final ArrayList<Object> list = new ArrayList<Object>(computeArrayListCapacity(array.length));
        Collections.addAll(list, array);
        return list;
    }
    
    @VisibleForTesting
    static int computeArrayListCapacity(final int n) {
        CollectPreconditions.checkNonnegative(n, "arraySize");
        return Ints.saturatedCast(5L + n + n / 10);
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayList(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return (iterable instanceof Collection) ? new ArrayList(Collections2.cast(iterable)) : newArrayList(iterable.iterator());
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayList(final Iterator iterator) {
        final ArrayList arrayList = newArrayList();
        Iterators.addAll(arrayList, iterator);
        return arrayList;
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayListWithCapacity(final int n) {
        CollectPreconditions.checkNonnegative(n, "initialArraySize");
        return new ArrayList(n);
    }
    
    @GwtCompatible(serializable = true)
    public static ArrayList newArrayListWithExpectedSize(final int n) {
        return new ArrayList(computeArrayListCapacity(n));
    }
    
    @GwtCompatible(serializable = true)
    public static LinkedList newLinkedList() {
        return new LinkedList();
    }
    
    @GwtCompatible(serializable = true)
    public static LinkedList newLinkedList(final Iterable iterable) {
        final LinkedList linkedList = newLinkedList();
        Iterables.addAll(linkedList, iterable);
        return linkedList;
    }
    
    @GwtIncompatible("CopyOnWriteArrayList")
    public static CopyOnWriteArrayList newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }
    
    @GwtIncompatible("CopyOnWriteArrayList")
    public static CopyOnWriteArrayList newCopyOnWriteArrayList(final Iterable iterable) {
        return new CopyOnWriteArrayList((iterable instanceof Collection) ? Collections2.cast(iterable) : newArrayList(iterable));
    }
    
    public static List asList(@Nullable final Object o, final Object[] array) {
        return new OnePlusArrayList(o, array);
    }
    
    public static List asList(@Nullable final Object o, @Nullable final Object o2, final Object[] array) {
        return new TwoPlusArrayList(o, o2, array);
    }
    
    static List cartesianProduct(final List list) {
        return CartesianList.create(list);
    }
    
    static List cartesianProduct(final List... array) {
        return cartesianProduct((List)Arrays.asList((List[])array));
    }
    
    public static List transform(final List list, final Function function) {
        return (list instanceof RandomAccess) ? new TransformingRandomAccessList(list, function) : new TransformingSequentialList(list, function);
    }
    
    public static List partition(final List list, final int n) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(n > 0);
        return (list instanceof RandomAccess) ? new RandomAccessPartition(list, n) : new Partition(list, n);
    }
    
    @Beta
    public static ImmutableList charactersOf(final String s) {
        return new StringAsImmutableList((String)Preconditions.checkNotNull(s));
    }
    
    @Beta
    public static List charactersOf(final CharSequence charSequence) {
        return new CharSequenceAsList((CharSequence)Preconditions.checkNotNull(charSequence));
    }
    
    public static List reverse(final List list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList)list).reverse();
        }
        if (list instanceof ReverseList) {
            return ((ReverseList)list).getForwardList();
        }
        if (list instanceof RandomAccess) {
            return new RandomAccessReverseList(list);
        }
        return new ReverseList(list);
    }
    
    static int hashCodeImpl(final List list) {
        for (final Object next : list) {
            final int n = 31 + ((next == null) ? 0 : next.hashCode());
        }
        return 1;
    }
    
    static boolean equalsImpl(final List list, @Nullable final Object o) {
        if (o == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final List list2 = (List)o;
        return list.size() == list2.size() && Iterators.elementsEqual(list.iterator(), list2.iterator());
    }
    
    static boolean addAllImpl(final List list, final int n, final Iterable iterable) {
        final ListIterator<Object> listIterator = list.listIterator(n);
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            listIterator.add(iterator.next());
        }
        return true;
    }
    
    static int indexOfImpl(final List list, @Nullable final Object o) {
        final ListIterator<Object> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(o, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }
    
    static int lastIndexOfImpl(final List list, @Nullable final Object o) {
        final ListIterator<Object> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(o, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }
    
    static ListIterator listIteratorImpl(final List list, final int n) {
        return new AbstractListWrapper(list).listIterator(n);
    }
    
    static List subListImpl(final List list, final int n, final int n2) {
        AbstractListWrapper abstractListWrapper;
        if (list instanceof RandomAccess) {
            abstractListWrapper = new RandomAccessListWrapper(list) {
                private static final long serialVersionUID = 0L;
                
                @Override
                public ListIterator listIterator(final int n) {
                    return this.backingList.listIterator(n);
                }
            };
        }
        else {
            abstractListWrapper = new AbstractListWrapper(list) {
                private static final long serialVersionUID = 0L;
                
                @Override
                public ListIterator listIterator(final int n) {
                    return this.backingList.listIterator(n);
                }
            };
        }
        return abstractListWrapper.subList(n, n2);
    }
    
    static List cast(final Iterable iterable) {
        return (List)iterable;
    }
    
    private static class RandomAccessListWrapper extends AbstractListWrapper implements RandomAccess
    {
        RandomAccessListWrapper(final List list) {
            super(list);
        }
    }
    
    private static class AbstractListWrapper extends AbstractList
    {
        final List backingList;
        
        AbstractListWrapper(final List list) {
            this.backingList = (List)Preconditions.checkNotNull(list);
        }
        
        @Override
        public void add(final int n, final Object o) {
            this.backingList.add(n, o);
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            return this.backingList.addAll(n, collection);
        }
        
        @Override
        public Object get(final int n) {
            return this.backingList.get(n);
        }
        
        @Override
        public Object remove(final int n) {
            return this.backingList.remove(n);
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.backingList.set(n, o);
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.backingList.contains(o);
        }
        
        @Override
        public int size() {
            return this.backingList.size();
        }
    }
    
    private static class RandomAccessReverseList extends ReverseList implements RandomAccess
    {
        RandomAccessReverseList(final List list) {
            super(list);
        }
    }
    
    private static class ReverseList extends AbstractList
    {
        private final List forwardList;
        
        ReverseList(final List list) {
            this.forwardList = (List)Preconditions.checkNotNull(list);
        }
        
        List getForwardList() {
            return this.forwardList;
        }
        
        private int reverseIndex(final int n) {
            final int size = this.size();
            Preconditions.checkElementIndex(n, size);
            return size - 1 - n;
        }
        
        private int reversePosition(final int n) {
            final int size = this.size();
            Preconditions.checkPositionIndex(n, size);
            return size - n;
        }
        
        @Override
        public void add(final int n, @Nullable final Object o) {
            this.forwardList.add(this.reversePosition(n), o);
        }
        
        @Override
        public void clear() {
            this.forwardList.clear();
        }
        
        @Override
        public Object remove(final int n) {
            return this.forwardList.remove(this.reverseIndex(n));
        }
        
        @Override
        protected void removeRange(final int n, final int n2) {
            this.subList(n, n2).clear();
        }
        
        @Override
        public Object set(final int n, @Nullable final Object o) {
            return this.forwardList.set(this.reverseIndex(n), o);
        }
        
        @Override
        public Object get(final int n) {
            return this.forwardList.get(this.reverseIndex(n));
        }
        
        @Override
        public int size() {
            return this.forwardList.size();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.reverse(this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n)));
        }
        
        @Override
        public Iterator iterator() {
            return this.listIterator();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return new ListIterator((ListIterator)this.forwardList.listIterator(this.reversePosition(n))) {
                boolean canRemoveOrSet;
                final ListIterator val$forwardIterator;
                final ReverseList this$0;
                
                @Override
                public void add(final Object o) {
                    this.val$forwardIterator.add(o);
                    this.val$forwardIterator.previous();
                    this.canRemoveOrSet = false;
                }
                
                @Override
                public boolean hasNext() {
                    return this.val$forwardIterator.hasPrevious();
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.val$forwardIterator.hasNext();
                }
                
                @Override
                public Object next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.canRemoveOrSet = true;
                    return this.val$forwardIterator.previous();
                }
                
                @Override
                public int nextIndex() {
                    return ReverseList.access$000(this.this$0, this.val$forwardIterator.nextIndex());
                }
                
                @Override
                public Object previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.canRemoveOrSet = true;
                    return this.val$forwardIterator.next();
                }
                
                @Override
                public int previousIndex() {
                    return this.nextIndex() - 1;
                }
                
                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.canRemoveOrSet);
                    this.val$forwardIterator.remove();
                    this.canRemoveOrSet = false;
                }
                
                @Override
                public void set(final Object o) {
                    Preconditions.checkState(this.canRemoveOrSet);
                    this.val$forwardIterator.set(o);
                }
            };
        }
        
        static int access$000(final ReverseList list, final int n) {
            return list.reversePosition(n);
        }
    }
    
    private static final class CharSequenceAsList extends AbstractList
    {
        private final CharSequence sequence;
        
        CharSequenceAsList(final CharSequence sequence) {
            this.sequence = sequence;
        }
        
        @Override
        public Character get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.sequence.charAt(n);
        }
        
        @Override
        public int size() {
            return this.sequence.length();
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
    
    private static final class StringAsImmutableList extends ImmutableList
    {
        private final String string;
        
        StringAsImmutableList(final String string) {
            this.string = string;
        }
        
        @Override
        public int indexOf(@Nullable final Object o) {
            return (o instanceof Character) ? this.string.indexOf((char)o) : -1;
        }
        
        @Override
        public int lastIndexOf(@Nullable final Object o) {
            return (o instanceof Character) ? this.string.lastIndexOf((char)o) : -1;
        }
        
        @Override
        public ImmutableList subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.charactersOf(this.string.substring(n, n2));
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        public Character get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.string.charAt(n);
        }
        
        @Override
        public int size() {
            return this.string.length();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
    
    private static class RandomAccessPartition extends Partition implements RandomAccess
    {
        RandomAccessPartition(final List list, final int n) {
            super(list, n);
        }
    }
    
    private static class Partition extends AbstractList
    {
        final List list;
        final int size;
        
        Partition(final List list, final int size) {
            this.list = list;
            this.size = size;
        }
        
        @Override
        public List get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            final int n2 = n * this.size;
            return this.list.subList(n2, Math.min(n2 + this.size, this.list.size()));
        }
        
        @Override
        public int size() {
            return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
        }
        
        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
    
    private static class TransformingRandomAccessList extends AbstractList implements RandomAccess, Serializable
    {
        final List fromList;
        final Function function;
        private static final long serialVersionUID = 0L;
        
        TransformingRandomAccessList(final List list, final Function function) {
            this.fromList = (List)Preconditions.checkNotNull(list);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public void clear() {
            this.fromList.clear();
        }
        
        @Override
        public Object get(final int n) {
            return this.function.apply(this.fromList.get(n));
        }
        
        @Override
        public Iterator iterator() {
            return this.listIterator();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return new TransformedListIterator((ListIterator)this.fromList.listIterator(n)) {
                final TransformingRandomAccessList this$0;
                
                @Override
                Object transform(final Object o) {
                    return this.this$0.function.apply(o);
                }
            };
        }
        
        @Override
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }
        
        @Override
        public Object remove(final int n) {
            return this.function.apply(this.fromList.remove(n));
        }
        
        @Override
        public int size() {
            return this.fromList.size();
        }
    }
    
    private static class TransformingSequentialList extends AbstractSequentialList implements Serializable
    {
        final List fromList;
        final Function function;
        private static final long serialVersionUID = 0L;
        
        TransformingSequentialList(final List list, final Function function) {
            this.fromList = (List)Preconditions.checkNotNull(list);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public void clear() {
            this.fromList.clear();
        }
        
        @Override
        public int size() {
            return this.fromList.size();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return new TransformedListIterator((ListIterator)this.fromList.listIterator(n)) {
                final TransformingSequentialList this$0;
                
                @Override
                Object transform(final Object o) {
                    return this.this$0.function.apply(o);
                }
            };
        }
    }
    
    private static class TwoPlusArrayList extends AbstractList implements Serializable, RandomAccess
    {
        final Object first;
        final Object second;
        final Object[] rest;
        private static final long serialVersionUID = 0L;
        
        TwoPlusArrayList(@Nullable final Object first, @Nullable final Object second, final Object[] array) {
            this.first = first;
            this.second = second;
            this.rest = (Object[])Preconditions.checkNotNull(array);
        }
        
        @Override
        public int size() {
            return this.rest.length + 2;
        }
        
        @Override
        public Object get(final int n) {
            switch (n) {
                case 0: {
                    return this.first;
                }
                case 1: {
                    return this.second;
                }
                default: {
                    Preconditions.checkElementIndex(n, this.size());
                    return this.rest[n - 2];
                }
            }
        }
    }
    
    private static class OnePlusArrayList extends AbstractList implements Serializable, RandomAccess
    {
        final Object first;
        final Object[] rest;
        private static final long serialVersionUID = 0L;
        
        OnePlusArrayList(@Nullable final Object first, final Object[] array) {
            this.first = first;
            this.rest = (Object[])Preconditions.checkNotNull(array);
        }
        
        @Override
        public int size() {
            return this.rest.length + 1;
        }
        
        @Override
        public Object get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return (n == 0) ? this.first : this.rest[n - 1];
        }
    }
}
