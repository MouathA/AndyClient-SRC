package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.primitives.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;

@GwtCompatible
public final class Multisets
{
    private static final Ordering DECREASING_COUNT_ORDERING;
    
    private Multisets() {
    }
    
    public static Multiset unmodifiableMultiset(final Multiset multiset) {
        if (multiset instanceof UnmodifiableMultiset || multiset instanceof ImmutableMultiset) {
            return multiset;
        }
        return new UnmodifiableMultiset((Multiset)Preconditions.checkNotNull(multiset));
    }
    
    @Deprecated
    public static Multiset unmodifiableMultiset(final ImmutableMultiset immutableMultiset) {
        return (Multiset)Preconditions.checkNotNull(immutableMultiset);
    }
    
    @Beta
    public static SortedMultiset unmodifiableSortedMultiset(final SortedMultiset sortedMultiset) {
        return new UnmodifiableSortedMultiset((SortedMultiset)Preconditions.checkNotNull(sortedMultiset));
    }
    
    public static Multiset.Entry immutableEntry(@Nullable final Object o, final int n) {
        return new ImmutableEntry(o, n);
    }
    
    @Beta
    public static Multiset filter(final Multiset multiset, final Predicate predicate) {
        if (multiset instanceof FilteredMultiset) {
            final FilteredMultiset filteredMultiset = (FilteredMultiset)multiset;
            return new FilteredMultiset(filteredMultiset.unfiltered, Predicates.and(filteredMultiset.predicate, predicate));
        }
        return new FilteredMultiset(multiset, predicate);
    }
    
    static int inferDistinctElements(final Iterable iterable) {
        if (iterable instanceof Multiset) {
            return ((Multiset)iterable).elementSet().size();
        }
        return 11;
    }
    
    @Beta
    public static Multiset union(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset(multiset, multiset2) {
            final Multiset val$multiset1;
            final Multiset val$multiset2;
            
            @Override
            public boolean contains(@Nullable final Object o) {
                return this.val$multiset1.contains(o) || this.val$multiset2.contains(o);
            }
            
            @Override
            public boolean isEmpty() {
                return this.val$multiset1.isEmpty() && this.val$multiset2.isEmpty();
            }
            
            @Override
            public int count(final Object o) {
                return Math.max(this.val$multiset1.count(o), this.val$multiset2.count(o));
            }
            
            @Override
            Set createElementSet() {
                return Sets.union(this.val$multiset1.elementSet(), this.val$multiset2.elementSet());
            }
            
            @Override
            Iterator entryIterator() {
                return new AbstractIterator((Iterator)this.val$multiset1.entrySet().iterator(), (Iterator)this.val$multiset2.entrySet().iterator()) {
                    final Iterator val$iterator1;
                    final Iterator val$iterator2;
                    final Multisets$1 this$0;
                    
                    @Override
                    protected Multiset.Entry computeNext() {
                        if (this.val$iterator1.hasNext()) {
                            final Multiset.Entry entry = this.val$iterator1.next();
                            final Object element = entry.getElement();
                            return Multisets.immutableEntry(element, Math.max(entry.getCount(), this.this$0.val$multiset2.count(element)));
                        }
                        while (this.val$iterator2.hasNext()) {
                            final Multiset.Entry entry2 = this.val$iterator2.next();
                            final Object element2 = entry2.getElement();
                            if (!this.this$0.val$multiset1.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Multiset.Entry)this.endOfData();
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
            
            @Override
            int distinctElements() {
                return this.elementSet().size();
            }
        };
    }
    
    public static Multiset intersection(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset(multiset, multiset2) {
            final Multiset val$multiset1;
            final Multiset val$multiset2;
            
            @Override
            public int count(final Object o) {
                final int count = this.val$multiset1.count(o);
                return (count == 0) ? 0 : Math.min(count, this.val$multiset2.count(o));
            }
            
            @Override
            Set createElementSet() {
                return Sets.intersection(this.val$multiset1.elementSet(), this.val$multiset2.elementSet());
            }
            
            @Override
            Iterator entryIterator() {
                return new AbstractIterator((Iterator)this.val$multiset1.entrySet().iterator()) {
                    final Iterator val$iterator1;
                    final Multisets$2 this$0;
                    
                    @Override
                    protected Multiset.Entry computeNext() {
                        while (this.val$iterator1.hasNext()) {
                            final Multiset.Entry entry = this.val$iterator1.next();
                            final Object element = entry.getElement();
                            final int min = Math.min(entry.getCount(), this.this$0.val$multiset2.count(element));
                            if (min > 0) {
                                return Multisets.immutableEntry(element, min);
                            }
                        }
                        return (Multiset.Entry)this.endOfData();
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
            
            @Override
            int distinctElements() {
                return this.elementSet().size();
            }
        };
    }
    
    @Beta
    public static Multiset sum(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset(multiset, multiset2) {
            final Multiset val$multiset1;
            final Multiset val$multiset2;
            
            @Override
            public boolean contains(@Nullable final Object o) {
                return this.val$multiset1.contains(o) || this.val$multiset2.contains(o);
            }
            
            @Override
            public boolean isEmpty() {
                return this.val$multiset1.isEmpty() && this.val$multiset2.isEmpty();
            }
            
            @Override
            public int size() {
                return this.val$multiset1.size() + this.val$multiset2.size();
            }
            
            @Override
            public int count(final Object o) {
                return this.val$multiset1.count(o) + this.val$multiset2.count(o);
            }
            
            @Override
            Set createElementSet() {
                return Sets.union(this.val$multiset1.elementSet(), this.val$multiset2.elementSet());
            }
            
            @Override
            Iterator entryIterator() {
                return new AbstractIterator((Iterator)this.val$multiset1.entrySet().iterator(), (Iterator)this.val$multiset2.entrySet().iterator()) {
                    final Iterator val$iterator1;
                    final Iterator val$iterator2;
                    final Multisets$3 this$0;
                    
                    @Override
                    protected Multiset.Entry computeNext() {
                        if (this.val$iterator1.hasNext()) {
                            final Multiset.Entry entry = this.val$iterator1.next();
                            final Object element = entry.getElement();
                            return Multisets.immutableEntry(element, entry.getCount() + this.this$0.val$multiset2.count(element));
                        }
                        while (this.val$iterator2.hasNext()) {
                            final Multiset.Entry entry2 = this.val$iterator2.next();
                            final Object element2 = entry2.getElement();
                            if (!this.this$0.val$multiset1.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Multiset.Entry)this.endOfData();
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
            
            @Override
            int distinctElements() {
                return this.elementSet().size();
            }
        };
    }
    
    @Beta
    public static Multiset difference(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset(multiset, multiset2) {
            final Multiset val$multiset1;
            final Multiset val$multiset2;
            
            @Override
            public int count(@Nullable final Object o) {
                final int count = this.val$multiset1.count(o);
                return (count == 0) ? 0 : Math.max(0, count - this.val$multiset2.count(o));
            }
            
            @Override
            Iterator entryIterator() {
                return new AbstractIterator((Iterator)this.val$multiset1.entrySet().iterator()) {
                    final Iterator val$iterator1;
                    final Multisets$4 this$0;
                    
                    @Override
                    protected Multiset.Entry computeNext() {
                        while (this.val$iterator1.hasNext()) {
                            final Multiset.Entry entry = this.val$iterator1.next();
                            final Object element = entry.getElement();
                            final int n = entry.getCount() - this.this$0.val$multiset2.count(element);
                            if (n > 0) {
                                return Multisets.immutableEntry(element, n);
                            }
                        }
                        return (Multiset.Entry)this.endOfData();
                    }
                    
                    @Override
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
            
            @Override
            int distinctElements() {
                return Iterators.size(this.entryIterator());
            }
        };
    }
    
    public static boolean containsOccurrences(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        for (final Multiset.Entry entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) < entry.getCount()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean retainOccurrences(final Multiset multiset, final Multiset multiset2) {
        return retainOccurrencesImpl(multiset, multiset2);
    }
    
    private static boolean retainOccurrencesImpl(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        final Iterator<Multiset.Entry> iterator = (Iterator<Multiset.Entry>)multiset.entrySet().iterator();
        while (iterator.hasNext()) {
            final Multiset.Entry entry = iterator.next();
            final int count = multiset2.count(entry.getElement());
            if (count == 0) {
                iterator.remove();
            }
            else {
                if (count >= entry.getCount()) {
                    continue;
                }
                multiset.setCount(entry.getElement(), count);
            }
        }
        return true;
    }
    
    public static boolean removeOccurrences(final Multiset multiset, final Multiset multiset2) {
        return removeOccurrencesImpl(multiset, multiset2);
    }
    
    private static boolean removeOccurrencesImpl(final Multiset multiset, final Multiset multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        final Iterator<Multiset.Entry> iterator = (Iterator<Multiset.Entry>)multiset.entrySet().iterator();
        while (iterator.hasNext()) {
            final Multiset.Entry entry = iterator.next();
            final int count = multiset2.count(entry.getElement());
            if (count >= entry.getCount()) {
                iterator.remove();
            }
            else {
                if (count <= 0) {
                    continue;
                }
                multiset.remove(entry.getElement(), count);
            }
        }
        return true;
    }
    
    static boolean equalsImpl(final Multiset multiset, @Nullable final Object o) {
        if (o == multiset) {
            return true;
        }
        if (!(o instanceof Multiset)) {
            return false;
        }
        final Multiset multiset2 = (Multiset)o;
        if (multiset.size() != multiset2.size() || multiset.entrySet().size() != multiset2.entrySet().size()) {
            return false;
        }
        for (final Multiset.Entry entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) != entry.getCount()) {
                return false;
            }
        }
        return true;
    }
    
    static boolean addAllImpl(final Multiset multiset, final Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        if (collection instanceof Multiset) {
            for (final Multiset.Entry entry : cast(collection).entrySet()) {
                multiset.add(entry.getElement(), entry.getCount());
            }
        }
        else {
            Iterators.addAll(multiset, collection.iterator());
        }
        return true;
    }
    
    static boolean removeAllImpl(final Multiset multiset, final Collection collection) {
        return multiset.elementSet().removeAll((collection instanceof Multiset) ? ((Multiset)collection).elementSet() : collection);
    }
    
    static boolean retainAllImpl(final Multiset multiset, final Collection collection) {
        Preconditions.checkNotNull(collection);
        return multiset.elementSet().retainAll((collection instanceof Multiset) ? ((Multiset)collection).elementSet() : collection);
    }
    
    static int setCountImpl(final Multiset multiset, final Object o, final int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        final int count = multiset.count(o);
        final int n2 = n - count;
        if (n2 > 0) {
            multiset.add(o, n2);
        }
        else if (n2 < 0) {
            multiset.remove(o, -n2);
        }
        return count;
    }
    
    static boolean setCountImpl(final Multiset multiset, final Object o, final int n, final int n2) {
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        if (multiset.count(o) == n) {
            multiset.setCount(o, n2);
            return true;
        }
        return false;
    }
    
    static Iterator iteratorImpl(final Multiset multiset) {
        return new MultisetIteratorImpl(multiset, multiset.entrySet().iterator());
    }
    
    static int sizeImpl(final Multiset multiset) {
        long n = 0L;
        final Iterator<Multiset.Entry> iterator = multiset.entrySet().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getCount();
        }
        return Ints.saturatedCast(n);
    }
    
    static Multiset cast(final Iterable iterable) {
        return (Multiset)iterable;
    }
    
    @Beta
    public static ImmutableMultiset copyHighestCountFirst(final Multiset multiset) {
        return ImmutableMultiset.copyFromEntries(Multisets.DECREASING_COUNT_ORDERING.immutableSortedCopy(multiset.entrySet()));
    }
    
    static {
        DECREASING_COUNT_ORDERING = new Ordering() {
            public int compare(final Multiset.Entry entry, final Multiset.Entry entry2) {
                return Ints.compare(entry2.getCount(), entry.getCount());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Multiset.Entry)o, (Multiset.Entry)o2);
            }
        };
    }
    
    static final class MultisetIteratorImpl implements Iterator
    {
        private final Multiset multiset;
        private final Iterator entryIterator;
        private Multiset.Entry currentEntry;
        private int laterCount;
        private int totalCount;
        private boolean canRemove;
        
        MultisetIteratorImpl(final Multiset multiset, final Iterator entryIterator) {
            this.multiset = multiset;
            this.entryIterator = entryIterator;
        }
        
        @Override
        public boolean hasNext() {
            return this.laterCount > 0 || this.entryIterator.hasNext();
        }
        
        @Override
        public Object next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.laterCount == 0) {
                this.currentEntry = this.entryIterator.next();
                final int count = this.currentEntry.getCount();
                this.laterCount = count;
                this.totalCount = count;
            }
            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }
        
        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (this.totalCount == 1) {
                this.entryIterator.remove();
            }
            else {
                this.multiset.remove(this.currentEntry.getElement());
            }
            --this.totalCount;
            this.canRemove = false;
        }
    }
    
    abstract static class EntrySet extends Sets.ImprovedAbstractSet
    {
        abstract Multiset multiset();
        
        @Override
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Multiset.Entry) {
                final Multiset.Entry entry = (Multiset.Entry)o;
                return entry.getCount() > 0 && this.multiset().count(entry.getElement()) == entry.getCount();
            }
            return false;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (o instanceof Multiset.Entry) {
                final Multiset.Entry entry = (Multiset.Entry)o;
                final Object element = entry.getElement();
                final int count = entry.getCount();
                if (count != 0) {
                    return this.multiset().setCount(element, count, 0);
                }
            }
            return false;
        }
        
        @Override
        public void clear() {
            this.multiset().clear();
        }
    }
    
    abstract static class ElementSet extends Sets.ImprovedAbstractSet
    {
        abstract Multiset multiset();
        
        @Override
        public void clear() {
            this.multiset().clear();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.multiset().contains(o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.multiset().containsAll(collection);
        }
        
        @Override
        public boolean isEmpty() {
            return this.multiset().isEmpty();
        }
        
        @Override
        public Iterator iterator() {
            return new TransformedIterator((Iterator)this.multiset().entrySet().iterator()) {
                final ElementSet this$0;
                
                Object transform(final Multiset.Entry entry) {
                    return entry.getElement();
                }
                
                @Override
                Object transform(final Object o) {
                    return this.transform((Multiset.Entry)o);
                }
            };
        }
        
        @Override
        public boolean remove(final Object o) {
            final int count = this.multiset().count(o);
            if (count > 0) {
                this.multiset().remove(o, count);
                return true;
            }
            return false;
        }
        
        @Override
        public int size() {
            return this.multiset().entrySet().size();
        }
    }
    
    abstract static class AbstractEntry implements Multiset.Entry
    {
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof Multiset.Entry) {
                final Multiset.Entry entry = (Multiset.Entry)o;
                return this.getCount() == entry.getCount() && Objects.equal(this.getElement(), entry.getElement());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            final Object element = this.getElement();
            return ((element == null) ? 0 : element.hashCode()) ^ this.getCount();
        }
        
        @Override
        public String toString() {
            final String value = String.valueOf(this.getElement());
            final int count = this.getCount();
            return (count == 1) ? value : (value + " x " + count);
        }
    }
    
    private static final class FilteredMultiset extends AbstractMultiset
    {
        final Multiset unfiltered;
        final Predicate predicate;
        
        FilteredMultiset(final Multiset multiset, final Predicate predicate) {
            this.unfiltered = (Multiset)Preconditions.checkNotNull(multiset);
            this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }
        
        @Override
        Set createElementSet() {
            return Sets.filter(this.unfiltered.elementSet(), this.predicate);
        }
        
        @Override
        Set createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), new Predicate() {
                final FilteredMultiset this$0;
                
                public boolean apply(final Multiset.Entry entry) {
                    return this.this$0.predicate.apply(entry.getElement());
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.apply((Multiset.Entry)o);
                }
            });
        }
        
        @Override
        Iterator entryIterator() {
            throw new AssertionError((Object)"should never be called");
        }
        
        @Override
        int distinctElements() {
            return this.elementSet().size();
        }
        
        @Override
        public int count(@Nullable final Object o) {
            final int count = this.unfiltered.count(o);
            if (count > 0) {
                return this.predicate.apply(o) ? count : false;
            }
            return 0;
        }
        
        @Override
        public int add(@Nullable final Object o, final int n) {
            Preconditions.checkArgument(this.predicate.apply(o), "Element %s does not match predicate %s", o, this.predicate);
            return this.unfiltered.add(o, n);
        }
        
        @Override
        public int remove(@Nullable final Object o, final int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(o);
            }
            return this.contains(o) ? this.unfiltered.remove(o, n) : 0;
        }
        
        @Override
        public void clear() {
            this.elementSet().clear();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    static final class ImmutableEntry extends AbstractEntry implements Serializable
    {
        @Nullable
        final Object element;
        final int count;
        private static final long serialVersionUID = 0L;
        
        ImmutableEntry(@Nullable final Object element, final int count) {
            this.element = element;
            CollectPreconditions.checkNonnegative(this.count = count, "count");
        }
        
        @Nullable
        @Override
        public Object getElement() {
            return this.element;
        }
        
        @Override
        public int getCount() {
            return this.count;
        }
    }
    
    static class UnmodifiableMultiset extends ForwardingMultiset implements Serializable
    {
        final Multiset delegate;
        transient Set elementSet;
        transient Set entrySet;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableMultiset(final Multiset delegate) {
            this.delegate = delegate;
        }
        
        @Override
        protected Multiset delegate() {
            return this.delegate;
        }
        
        Set createElementSet() {
            return Collections.unmodifiableSet((Set<?>)this.delegate.elementSet());
        }
        
        @Override
        public Set elementSet() {
            final Set elementSet = this.elementSet;
            return (elementSet == null) ? (this.elementSet = this.createElementSet()) : elementSet;
        }
        
        @Override
        public Set entrySet() {
            final Set entrySet = this.entrySet;
            return (entrySet == null) ? (this.entrySet = Collections.unmodifiableSet((Set<?>)this.delegate.entrySet())) : entrySet;
        }
        
        @Override
        public Iterator iterator() {
            return Iterators.unmodifiableIterator(this.delegate.iterator());
        }
        
        @Override
        public boolean add(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int add(final Object o, final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int remove(final Object o, final int n) {
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
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int setCount(final Object o, final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean setCount(final Object o, final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
