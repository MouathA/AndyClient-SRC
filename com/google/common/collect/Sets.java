package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Sets
{
    private Sets() {
    }
    
    @GwtCompatible(serializable = true)
    public static ImmutableSet immutableEnumSet(final Enum enum1, final Enum... array) {
        return ImmutableEnumSet.asImmutable(EnumSet.of(enum1, (Enum[])array));
    }
    
    @GwtCompatible(serializable = true)
    public static ImmutableSet immutableEnumSet(final Iterable iterable) {
        if (iterable instanceof ImmutableEnumSet) {
            return (ImmutableEnumSet)iterable;
        }
        if (iterable instanceof Collection) {
            final ImmutableEnumSet set = (ImmutableEnumSet)iterable;
            if (set.isEmpty()) {
                return ImmutableSet.of();
            }
            return ImmutableEnumSet.asImmutable(EnumSet.copyOf((Collection<Enum>)set));
        }
        else {
            final Iterator<E> iterator = iterable.iterator();
            if (iterator.hasNext()) {
                final EnumSet<Enum> of = EnumSet.of((Enum)iterator.next());
                Iterators.addAll(of, iterator);
                return ImmutableEnumSet.asImmutable(of);
            }
            return ImmutableSet.of();
        }
    }
    
    public static EnumSet newEnumSet(final Iterable iterable, final Class clazz) {
        final EnumSet<Enum> none = EnumSet.noneOf((Class<Enum>)clazz);
        Iterables.addAll(none, iterable);
        return none;
    }
    
    public static HashSet newHashSet() {
        return new HashSet();
    }
    
    public static HashSet newHashSet(final Object... array) {
        final HashSet hashSetWithExpectedSize = newHashSetWithExpectedSize(array.length);
        Collections.addAll(hashSetWithExpectedSize, array);
        return hashSetWithExpectedSize;
    }
    
    public static HashSet newHashSetWithExpectedSize(final int n) {
        return new HashSet(Maps.capacity(n));
    }
    
    public static HashSet newHashSet(final Iterable iterable) {
        return (iterable instanceof Collection) ? new HashSet(Collections2.cast(iterable)) : newHashSet(iterable.iterator());
    }
    
    public static HashSet newHashSet(final Iterator iterator) {
        final HashSet hashSet = newHashSet();
        Iterators.addAll(hashSet, iterator);
        return hashSet;
    }
    
    public static Set newConcurrentHashSet() {
        return newSetFromMap(new ConcurrentHashMap());
    }
    
    public static Set newConcurrentHashSet(final Iterable iterable) {
        final Set concurrentHashSet = newConcurrentHashSet();
        Iterables.addAll(concurrentHashSet, iterable);
        return concurrentHashSet;
    }
    
    public static LinkedHashSet newLinkedHashSet() {
        return new LinkedHashSet();
    }
    
    public static LinkedHashSet newLinkedHashSetWithExpectedSize(final int n) {
        return new LinkedHashSet(Maps.capacity(n));
    }
    
    public static LinkedHashSet newLinkedHashSet(final Iterable iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet(Collections2.cast(iterable));
        }
        final LinkedHashSet linkedHashSet = newLinkedHashSet();
        Iterables.addAll(linkedHashSet, iterable);
        return linkedHashSet;
    }
    
    public static TreeSet newTreeSet() {
        return new TreeSet();
    }
    
    public static TreeSet newTreeSet(final Iterable iterable) {
        final TreeSet treeSet = newTreeSet();
        Iterables.addAll(treeSet, iterable);
        return treeSet;
    }
    
    public static TreeSet newTreeSet(final Comparator comparator) {
        return new TreeSet((Comparator<? super E>)Preconditions.checkNotNull(comparator));
    }
    
    public static Set newIdentityHashSet() {
        return newSetFromMap(Maps.newIdentityHashMap());
    }
    
    @GwtIncompatible("CopyOnWriteArraySet")
    public static CopyOnWriteArraySet newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }
    
    @GwtIncompatible("CopyOnWriteArraySet")
    public static CopyOnWriteArraySet newCopyOnWriteArraySet(final Iterable iterable) {
        return new CopyOnWriteArraySet((iterable instanceof Collection) ? Collections2.cast(iterable) : Lists.newArrayList(iterable));
    }
    
    public static EnumSet complementOf(final Collection collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet<Enum>)collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), (Object)"collection is empty; use the other version of this method");
        return makeComplementByHand(collection, collection.iterator().next().getDeclaringClass());
    }
    
    public static EnumSet complementOf(final Collection collection, final Class clazz) {
        Preconditions.checkNotNull(collection);
        return (collection instanceof EnumSet) ? EnumSet.complementOf((EnumSet<Enum>)collection) : makeComplementByHand(collection, clazz);
    }
    
    private static EnumSet makeComplementByHand(final Collection collection, final Class clazz) {
        final EnumSet<Enum> all = EnumSet.allOf((Class<Enum>)clazz);
        all.removeAll(collection);
        return all;
    }
    
    public static Set newSetFromMap(final Map map) {
        return Platform.newSetFromMap(map);
    }
    
    public static SetView union(final Set set, final Set set2) {
        Preconditions.checkNotNull(set, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        return new SetView(set, difference(set2, set), set2) {
            final Set val$set1;
            final Set val$set2minus1;
            final Set val$set2;
            
            @Override
            public int size() {
                return this.val$set1.size() + this.val$set2minus1.size();
            }
            
            @Override
            public boolean isEmpty() {
                return this.val$set1.isEmpty() && this.val$set2.isEmpty();
            }
            
            @Override
            public Iterator iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(this.val$set1.iterator(), this.val$set2minus1.iterator()));
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.val$set1.contains(o) || this.val$set2.contains(o);
            }
            
            @Override
            public Set copyInto(final Set set) {
                set.addAll(this.val$set1);
                set.addAll(this.val$set2);
                return set;
            }
            
            @Override
            public ImmutableSet immutableCopy() {
                return new ImmutableSet.Builder().addAll((Iterable)this.val$set1).addAll((Iterable)this.val$set2).build();
            }
        };
    }
    
    public static SetView intersection(final Set set, final Set set2) {
        Preconditions.checkNotNull(set, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        return new SetView(set, Predicates.in(set2), set2) {
            final Set val$set1;
            final Predicate val$inSet2;
            final Set val$set2;
            
            @Override
            public Iterator iterator() {
                return Iterators.filter(this.val$set1.iterator(), this.val$inSet2);
            }
            
            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }
            
            @Override
            public boolean isEmpty() {
                return !this.iterator().hasNext();
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.val$set1.contains(o) && this.val$set2.contains(o);
            }
            
            @Override
            public boolean containsAll(final Collection collection) {
                return this.val$set1.containsAll(collection) && this.val$set2.containsAll(collection);
            }
        };
    }
    
    public static SetView difference(final Set set, final Set set2) {
        Preconditions.checkNotNull(set, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        return new SetView(set, Predicates.not(Predicates.in(set2)), set2) {
            final Set val$set1;
            final Predicate val$notInSet2;
            final Set val$set2;
            
            @Override
            public Iterator iterator() {
                return Iterators.filter(this.val$set1.iterator(), this.val$notInSet2);
            }
            
            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }
            
            @Override
            public boolean isEmpty() {
                return this.val$set2.containsAll(this.val$set1);
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.val$set1.contains(o) && !this.val$set2.contains(o);
            }
        };
    }
    
    public static SetView symmetricDifference(final Set set, final Set set2) {
        Preconditions.checkNotNull(set, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        return difference(union(set, set2), intersection(set, set2));
    }
    
    public static Set filter(final Set set, final Predicate predicate) {
        if (set instanceof SortedSet) {
            return filter((SortedSet)set, predicate);
        }
        if (set instanceof FilteredSet) {
            final FilteredSet set2 = (FilteredSet)set;
            return new FilteredSet((Set)set2.unfiltered, Predicates.and(set2.predicate, predicate));
        }
        return new FilteredSet((Set)Preconditions.checkNotNull(set), (Predicate)Preconditions.checkNotNull(predicate));
    }
    
    public static SortedSet filter(final SortedSet set, final Predicate predicate) {
        return Platform.setsFilterSortedSet(set, predicate);
    }
    
    static SortedSet filterSortedIgnoreNavigable(final SortedSet set, final Predicate predicate) {
        if (set instanceof FilteredSet) {
            final FilteredSet set2 = (FilteredSet)set;
            return new FilteredSortedSet((SortedSet)set2.unfiltered, Predicates.and(set2.predicate, predicate));
        }
        return new FilteredSortedSet((SortedSet)Preconditions.checkNotNull(set), (Predicate)Preconditions.checkNotNull(predicate));
    }
    
    @GwtIncompatible("NavigableSet")
    public static NavigableSet filter(final NavigableSet set, final Predicate predicate) {
        if (set instanceof FilteredSet) {
            final FilteredSet set2 = (FilteredSet)set;
            return new FilteredNavigableSet((NavigableSet)set2.unfiltered, Predicates.and(set2.predicate, predicate));
        }
        return new FilteredNavigableSet((NavigableSet)Preconditions.checkNotNull(set), (Predicate)Preconditions.checkNotNull(predicate));
    }
    
    public static Set cartesianProduct(final List list) {
        return CartesianSet.create(list);
    }
    
    public static Set cartesianProduct(final Set... array) {
        return cartesianProduct(Arrays.asList((Set[])array));
    }
    
    @GwtCompatible(serializable = false)
    public static Set powerSet(final Set set) {
        return new PowerSet(set);
    }
    
    static int hashCodeImpl(final Set set) {
        for (final Object next : set) {
            final int n = 0 + ((next != null) ? next.hashCode() : 0);
        }
        return 0;
    }
    
    static boolean equalsImpl(final Set set, @Nullable final Object o) {
        if (set == o) {
            return true;
        }
        if (o instanceof Set) {
            final Set set2 = (Set)o;
            return set.size() == set2.size() && set.containsAll(set2);
        }
        return false;
    }
    
    @GwtIncompatible("NavigableSet")
    public static NavigableSet unmodifiableNavigableSet(final NavigableSet set) {
        if (set instanceof ImmutableSortedSet || set instanceof UnmodifiableNavigableSet) {
            return set;
        }
        return new UnmodifiableNavigableSet(set);
    }
    
    @GwtIncompatible("NavigableSet")
    public static NavigableSet synchronizedNavigableSet(final NavigableSet set) {
        return Synchronized.navigableSet(set);
    }
    
    static boolean removeAllImpl(final Set set, final Iterator iterator) {
        while (iterator.hasNext()) {
            final boolean b = false | set.remove(iterator.next());
        }
        return false;
    }
    
    static boolean removeAllImpl(final Set set, Collection elementSet) {
        Preconditions.checkNotNull(elementSet);
        if (elementSet instanceof Multiset) {
            elementSet = ((Multiset)elementSet).elementSet();
        }
        if (elementSet instanceof Set && elementSet.size() > set.size()) {
            return Iterators.removeAll(set.iterator(), elementSet);
        }
        return removeAllImpl(set, elementSet.iterator());
    }
    
    @GwtIncompatible("NavigableSet")
    static class DescendingSet extends ForwardingNavigableSet
    {
        private final NavigableSet forward;
        
        DescendingSet(final NavigableSet forward) {
            this.forward = forward;
        }
        
        @Override
        protected NavigableSet delegate() {
            return this.forward;
        }
        
        @Override
        public Object lower(final Object o) {
            return this.forward.higher(o);
        }
        
        @Override
        public Object floor(final Object o) {
            return this.forward.ceiling(o);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return this.forward.floor(o);
        }
        
        @Override
        public Object higher(final Object o) {
            return this.forward.lower(o);
        }
        
        @Override
        public Object pollFirst() {
            return this.forward.pollLast();
        }
        
        @Override
        public Object pollLast() {
            return this.forward.pollFirst();
        }
        
        @Override
        public NavigableSet descendingSet() {
            return this.forward;
        }
        
        @Override
        public Iterator descendingIterator() {
            return this.forward.iterator();
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.forward.subSet(o2, b2, o, b).descendingSet();
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return this.forward.tailSet(o, b).descendingSet();
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return this.forward.headSet(o, b).descendingSet();
        }
        
        @Override
        public Comparator comparator() {
            final Comparator comparator = this.forward.comparator();
            if (comparator == null) {
                return Ordering.natural().reverse();
            }
            return reverse(comparator);
        }
        
        private static Ordering reverse(final Comparator comparator) {
            return Ordering.from(comparator).reverse();
        }
        
        @Override
        public Object first() {
            return this.forward.last();
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.standardHeadSet(o);
        }
        
        @Override
        public Object last() {
            return this.forward.first();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.standardSubSet(o, o2);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.standardTailSet(o);
        }
        
        @Override
        public Iterator iterator() {
            return this.forward.descendingIterator();
        }
        
        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.standardToArray(array);
        }
        
        @Override
        public String toString() {
            return this.standardToString();
        }
        
        @Override
        protected SortedSet delegate() {
            return this.delegate();
        }
        
        @Override
        protected Set delegate() {
            return this.delegate();
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
    
    @GwtIncompatible("NavigableSet")
    static final class UnmodifiableNavigableSet extends ForwardingSortedSet implements NavigableSet, Serializable
    {
        private final NavigableSet delegate;
        private transient UnmodifiableNavigableSet descendingSet;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableNavigableSet(final NavigableSet set) {
            this.delegate = (NavigableSet)Preconditions.checkNotNull(set);
        }
        
        @Override
        protected SortedSet delegate() {
            return Collections.unmodifiableSortedSet((SortedSet<Object>)this.delegate);
        }
        
        @Override
        public Object lower(final Object o) {
            return this.delegate.lower(o);
        }
        
        @Override
        public Object floor(final Object o) {
            return this.delegate.floor(o);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return this.delegate.ceiling(o);
        }
        
        @Override
        public Object higher(final Object o) {
            return this.delegate.higher(o);
        }
        
        @Override
        public Object pollFirst() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object pollLast() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public NavigableSet descendingSet() {
            UnmodifiableNavigableSet descendingSet = this.descendingSet;
            if (descendingSet == null) {
                final UnmodifiableNavigableSet descendingSet2 = new UnmodifiableNavigableSet(this.delegate.descendingSet());
                this.descendingSet = descendingSet2;
                descendingSet = descendingSet2;
                descendingSet.descendingSet = this;
            }
            return descendingSet;
        }
        
        @Override
        public Iterator descendingIterator() {
            return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Sets.unmodifiableNavigableSet(this.delegate.subSet(o, b, o2, b2));
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return Sets.unmodifiableNavigableSet(this.delegate.headSet(o, b));
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return Sets.unmodifiableNavigableSet(this.delegate.tailSet(o, b));
        }
        
        @Override
        protected Set delegate() {
            return this.delegate();
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
    
    private static final class PowerSet extends AbstractSet
    {
        final ImmutableMap inputSet;
        
        PowerSet(final Set set) {
            final ImmutableMap.Builder builder = ImmutableMap.builder();
            for (final Object next : (Set)Preconditions.checkNotNull(set)) {
                final ImmutableMap.Builder builder2 = builder;
                final Object o = next;
                final int n = 0;
                int n2 = 0;
                ++n2;
                builder2.put(o, n);
            }
            this.inputSet = builder.build();
            Preconditions.checkArgument(this.inputSet.size() <= 30, "Too many elements to create power set: %s > 30", this.inputSet.size());
        }
        
        @Override
        public int size() {
            return 1 << this.inputSet.size();
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return new AbstractIndexedListIterator(this.size()) {
                final PowerSet this$0;
                
                @Override
                protected Set get(final int n) {
                    return new SubSet(this.this$0.inputSet, n);
                }
                
                @Override
                protected Object get(final int n) {
                    return this.get(n);
                }
            };
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return o instanceof Set && this.inputSet.keySet().containsAll((Collection<?>)o);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof PowerSet) {
                return this.inputSet.equals(((PowerSet)o).inputSet);
            }
            return super.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.inputSet.keySet().hashCode() << this.inputSet.size() - 1;
        }
        
        @Override
        public String toString() {
            return "powerSet(" + this.inputSet + ")";
        }
    }
    
    private static final class SubSet extends AbstractSet
    {
        private final ImmutableMap inputSet;
        private final int mask;
        
        SubSet(final ImmutableMap inputSet, final int mask) {
            this.inputSet = inputSet;
            this.mask = mask;
        }
        
        @Override
        public Iterator iterator() {
            return new UnmodifiableIterator() {
                final ImmutableList elements = SubSet.access$100(this.this$0).keySet().asList();
                int remainingSetBits = SubSet.access$200(this.this$0);
                final SubSet this$0;
                
                @Override
                public boolean hasNext() {
                    return this.remainingSetBits != 0;
                }
                
                @Override
                public Object next() {
                    final int numberOfTrailingZeros = Integer.numberOfTrailingZeros(this.remainingSetBits);
                    if (numberOfTrailingZeros == 32) {
                        throw new NoSuchElementException();
                    }
                    this.remainingSetBits &= ~(1 << numberOfTrailingZeros);
                    return this.elements.get(numberOfTrailingZeros);
                }
            };
        }
        
        @Override
        public int size() {
            return Integer.bitCount(this.mask);
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            final Integer n = (Integer)this.inputSet.get(o);
            return n != null && (this.mask & 1 << n) != 0x0;
        }
        
        static ImmutableMap access$100(final SubSet set) {
            return set.inputSet;
        }
        
        static int access$200(final SubSet set) {
            return set.mask;
        }
    }
    
    private static final class CartesianSet extends ForwardingCollection implements Set
    {
        private final transient ImmutableList axes;
        private final transient CartesianList delegate;
        
        static Set create(final List list) {
            final ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
            final Iterator<Set> iterator = list.iterator();
            while (iterator.hasNext()) {
                final ImmutableSet copy = ImmutableSet.copyOf(iterator.next());
                if (copy.isEmpty()) {
                    return ImmutableSet.of();
                }
                builder.add((Object)copy);
            }
            final ImmutableList build = builder.build();
            return new CartesianSet(build, new CartesianList(new ImmutableList(build) {
                final ImmutableList val$axes;
                
                @Override
                public int size() {
                    return this.val$axes.size();
                }
                
                @Override
                public List get(final int n) {
                    return this.val$axes.get(n).asList();
                }
                
                @Override
                boolean isPartialView() {
                    return true;
                }
                
                @Override
                public Object get(final int n) {
                    return this.get(n);
                }
            }));
        }
        
        private CartesianSet(final ImmutableList axes, final CartesianList delegate) {
            this.axes = axes;
            this.delegate = delegate;
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof CartesianSet) {
                return this.axes.equals(((CartesianSet)o).axes);
            }
            return super.equals(o);
        }
        
        @Override
        public int hashCode() {
            int n = this.size() - 1;
            while (1 < this.axes.size()) {
                n = ~(~(n * 31));
                int n2 = 0;
                ++n2;
            }
            for (final Set set : this.axes) {
                final int n2 = 31 + this.size() / set.size() * set.hashCode();
            }
            int n2 = 1 + n;
            return 1;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    @GwtIncompatible("NavigableSet")
    private static class FilteredNavigableSet extends FilteredSortedSet implements NavigableSet
    {
        FilteredNavigableSet(final NavigableSet set, final Predicate predicate) {
            super(set, predicate);
        }
        
        NavigableSet unfiltered() {
            return (NavigableSet)this.unfiltered;
        }
        
        @Nullable
        @Override
        public Object lower(final Object o) {
            return Iterators.getNext(this.headSet(o, false).descendingIterator(), null);
        }
        
        @Nullable
        @Override
        public Object floor(final Object o) {
            return Iterators.getNext(this.headSet(o, true).descendingIterator(), null);
        }
        
        @Override
        public Object ceiling(final Object o) {
            return Iterables.getFirst(this.tailSet(o, true), null);
        }
        
        @Override
        public Object higher(final Object o) {
            return Iterables.getFirst(this.tailSet(o, false), null);
        }
        
        @Override
        public Object pollFirst() {
            return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
        }
        
        @Override
        public Object pollLast() {
            return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
        }
        
        @Override
        public NavigableSet descendingSet() {
            return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
        }
        
        @Override
        public Iterator descendingIterator() {
            return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
        }
        
        @Override
        public Object last() {
            return this.descendingIterator().next();
        }
        
        @Override
        public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
            return Sets.filter(this.unfiltered().subSet(o, b, o2, b2), this.predicate);
        }
        
        @Override
        public NavigableSet headSet(final Object o, final boolean b) {
            return Sets.filter(this.unfiltered().headSet(o, b), this.predicate);
        }
        
        @Override
        public NavigableSet tailSet(final Object o, final boolean b) {
            return Sets.filter(this.unfiltered().tailSet(o, b), this.predicate);
        }
    }
    
    private static class FilteredSortedSet extends FilteredSet implements SortedSet
    {
        FilteredSortedSet(final SortedSet set, final Predicate predicate) {
            super(set, predicate);
        }
        
        @Override
        public Comparator comparator() {
            return ((SortedSet)this.unfiltered).comparator();
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return new FilteredSortedSet(((SortedSet)this.unfiltered).subSet(o, o2), this.predicate);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return new FilteredSortedSet(((SortedSet)this.unfiltered).headSet(o), this.predicate);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return new FilteredSortedSet(((SortedSet)this.unfiltered).tailSet(o), this.predicate);
        }
        
        @Override
        public Object first() {
            return this.iterator().next();
        }
        
        @Override
        public Object last() {
            SortedSet<Object> headSet = (SortedSet<Object>)this.unfiltered;
            Object last;
            while (true) {
                last = headSet.last();
                if (this.predicate.apply(last)) {
                    break;
                }
                headSet = headSet.headSet(last);
            }
            return last;
        }
    }
    
    private static class FilteredSet extends Collections2.FilteredCollection implements Set
    {
        FilteredSet(final Set set, final Predicate predicate) {
            super(set, predicate);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return Sets.equalsImpl(this, o);
        }
        
        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }
    
    public abstract static class SetView extends AbstractSet
    {
        private SetView() {
        }
        
        public ImmutableSet immutableCopy() {
            return ImmutableSet.copyOf(this);
        }
        
        public Set copyInto(final Set set) {
            set.addAll(this);
            return set;
        }
        
        SetView(final Sets$1 setView) {
            this();
        }
    }
    
    abstract static class ImprovedAbstractSet extends AbstractSet
    {
        @Override
        public boolean removeAll(final Collection collection) {
            return Sets.removeAllImpl(this, collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return super.retainAll((Collection<?>)Preconditions.checkNotNull(collection));
        }
    }
}
