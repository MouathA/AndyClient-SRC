package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible(emulated = true)
public final class Iterables
{
    private Iterables() {
    }
    
    public static Iterable unmodifiableIterable(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof UnmodifiableIterable || iterable instanceof ImmutableCollection) {
            return iterable;
        }
        return new UnmodifiableIterable(iterable, null);
    }
    
    @Deprecated
    public static Iterable unmodifiableIterable(final ImmutableCollection collection) {
        return (Iterable)Preconditions.checkNotNull(collection);
    }
    
    public static int size(final Iterable iterable) {
        return (iterable instanceof Collection) ? ((Collection)iterable).size() : Iterators.size(iterable.iterator());
    }
    
    public static boolean contains(final Iterable iterable, @Nullable final Object o) {
        if (iterable instanceof Collection) {
            return Collections2.safeContains((Collection)iterable, o);
        }
        return Iterators.contains(iterable.iterator(), o);
    }
    
    public static boolean removeAll(final Iterable iterable, final Collection collection) {
        return (iterable instanceof Collection) ? ((Collection)iterable).removeAll((Collection)Preconditions.checkNotNull(collection)) : Iterators.removeAll(iterable.iterator(), collection);
    }
    
    public static boolean retainAll(final Iterable iterable, final Collection collection) {
        return (iterable instanceof Collection) ? ((Collection)iterable).retainAll((Collection)Preconditions.checkNotNull(collection)) : Iterators.retainAll(iterable.iterator(), collection);
    }
    
    public static boolean removeIf(final Iterable iterable, final Predicate predicate) {
        if (iterable instanceof RandomAccess && iterable instanceof List) {
            return removeIfFromRandomAccessList((List)iterable, (Predicate)Preconditions.checkNotNull(predicate));
        }
        return Iterators.removeIf(iterable.iterator(), predicate);
    }
    
    private static boolean removeIfFromRandomAccessList(final List list, final Predicate predicate) {
        while (0 < list.size()) {
            if (!predicate.apply(list.get(0))) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        list.subList(0, list.size()).clear();
        return false;
    }
    
    private static void slowRemoveIfForRemainingElements(final List list, final Predicate predicate, final int n, final int n2) {
        for (int i = list.size() - 1; i > n2; --i) {
            if (predicate.apply(list.get(i))) {
                list.remove(i);
            }
        }
        for (int j = n2 - 1; j >= n; --j) {
            list.remove(j);
        }
    }
    
    @Nullable
    static Object removeFirstMatching(final Iterable iterable, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (predicate.apply(next)) {
                iterator.remove();
                return next;
            }
        }
        return null;
    }
    
    public static boolean elementsEqual(final Iterable iterable, final Iterable iterable2) {
        return (!(iterable instanceof Collection) || !(iterable2 instanceof Collection) || ((Collection)iterable).size() == ((Collection)iterable2).size()) && Iterators.elementsEqual(iterable.iterator(), iterable2.iterator());
    }
    
    public static String toString(final Iterable iterable) {
        return Iterators.toString(iterable.iterator());
    }
    
    public static Object getOnlyElement(final Iterable iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }
    
    @Nullable
    public static Object getOnlyElement(final Iterable iterable, @Nullable final Object o) {
        return Iterators.getOnlyElement(iterable.iterator(), o);
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static Object[] toArray(final Iterable iterable, final Class clazz) {
        final Collection collection = toCollection(iterable);
        return collection.toArray(ObjectArrays.newArray(clazz, collection.size()));
    }
    
    static Object[] toArray(final Iterable iterable) {
        return toCollection(iterable).toArray();
    }
    
    private static Collection toCollection(final Iterable iterable) {
        return (iterable instanceof Collection) ? ((Collection)iterable) : Lists.newArrayList(iterable.iterator());
    }
    
    public static boolean addAll(final Collection collection, final Iterable iterable) {
        if (iterable instanceof Collection) {
            return collection.addAll(Collections2.cast(iterable));
        }
        return Iterators.addAll(collection, ((Iterable)Preconditions.checkNotNull(iterable)).iterator());
    }
    
    public static int frequency(final Iterable iterable, @Nullable final Object o) {
        if (iterable instanceof Multiset) {
            return ((Multiset)iterable).count(o);
        }
        if (iterable instanceof Set) {
            return ((Set<Object>)iterable).contains(o) ? 1 : 0;
        }
        return Iterators.frequency(iterable.iterator(), o);
    }
    
    public static Iterable cycle(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable(iterable) {
            final Iterable val$iterable;
            
            @Override
            public Iterator iterator() {
                return Iterators.cycle(this.val$iterable);
            }
            
            @Override
            public String toString() {
                return this.val$iterable.toString() + " (cycled)";
            }
        };
    }
    
    public static Iterable cycle(final Object... array) {
        return cycle(Lists.newArrayList(array));
    }
    
    public static Iterable concat(final Iterable iterable, final Iterable iterable2) {
        return concat(ImmutableList.of(iterable, iterable2));
    }
    
    public static Iterable concat(final Iterable iterable, final Iterable iterable2, final Iterable iterable3) {
        return concat(ImmutableList.of(iterable, iterable2, iterable3));
    }
    
    public static Iterable concat(final Iterable iterable, final Iterable iterable2, final Iterable iterable3, final Iterable iterable4) {
        return concat(ImmutableList.of(iterable, iterable2, iterable3, iterable4));
    }
    
    public static Iterable concat(final Iterable... array) {
        return concat(ImmutableList.copyOf((Object[])array));
    }
    
    public static Iterable concat(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable(iterable) {
            final Iterable val$inputs;
            
            @Override
            public Iterator iterator() {
                return Iterators.concat(Iterables.access$100(this.val$inputs));
            }
        };
    }
    
    private static Iterator iterators(final Iterable iterable) {
        return new TransformedIterator(iterable.iterator()) {
            Iterator transform(final Iterable iterable) {
                return iterable.iterator();
            }
            
            @Override
            Object transform(final Object o) {
                return this.transform((Iterable)o);
            }
        };
    }
    
    public static Iterable partition(final Iterable iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n > 0);
        return new FluentIterable(iterable, n) {
            final Iterable val$iterable;
            final int val$size;
            
            @Override
            public Iterator iterator() {
                return Iterators.partition(this.val$iterable.iterator(), this.val$size);
            }
        };
    }
    
    public static Iterable paddedPartition(final Iterable iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n > 0);
        return new FluentIterable(iterable, n) {
            final Iterable val$iterable;
            final int val$size;
            
            @Override
            public Iterator iterator() {
                return Iterators.paddedPartition(this.val$iterable.iterator(), this.val$size);
            }
        };
    }
    
    public static Iterable filter(final Iterable iterable, final Predicate predicate) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(predicate);
        return new FluentIterable(iterable, predicate) {
            final Iterable val$unfiltered;
            final Predicate val$predicate;
            
            @Override
            public Iterator iterator() {
                return Iterators.filter(this.val$unfiltered.iterator(), this.val$predicate);
            }
        };
    }
    
    @GwtIncompatible("Class.isInstance")
    public static Iterable filter(final Iterable iterable, final Class clazz) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(clazz);
        return new FluentIterable(iterable, clazz) {
            final Iterable val$unfiltered;
            final Class val$type;
            
            @Override
            public Iterator iterator() {
                return Iterators.filter(this.val$unfiltered.iterator(), this.val$type);
            }
        };
    }
    
    public static boolean any(final Iterable iterable, final Predicate predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }
    
    public static boolean all(final Iterable iterable, final Predicate predicate) {
        return Iterators.all(iterable.iterator(), predicate);
    }
    
    public static Object find(final Iterable iterable, final Predicate predicate) {
        return Iterators.find(iterable.iterator(), predicate);
    }
    
    @Nullable
    public static Object find(final Iterable iterable, final Predicate predicate, @Nullable final Object o) {
        return Iterators.find(iterable.iterator(), predicate, o);
    }
    
    public static Optional tryFind(final Iterable iterable, final Predicate predicate) {
        return Iterators.tryFind(iterable.iterator(), predicate);
    }
    
    public static int indexOf(final Iterable iterable, final Predicate predicate) {
        return Iterators.indexOf(iterable.iterator(), predicate);
    }
    
    public static Iterable transform(final Iterable iterable, final Function function) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(function);
        return new FluentIterable(iterable, function) {
            final Iterable val$fromIterable;
            final Function val$function;
            
            @Override
            public Iterator iterator() {
                return Iterators.transform(this.val$fromIterable.iterator(), this.val$function);
            }
        };
    }
    
    public static Object get(final Iterable iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        return (iterable instanceof List) ? ((List<Object>)iterable).get(n) : Iterators.get(iterable.iterator(), n);
    }
    
    @Nullable
    public static Object get(final Iterable iterable, final int n, @Nullable final Object o) {
        Preconditions.checkNotNull(iterable);
        Iterators.checkNonnegative(n);
        if (iterable instanceof List) {
            final List cast = Lists.cast(iterable);
            return (n < cast.size()) ? cast.get(n) : o;
        }
        final Iterator iterator = iterable.iterator();
        Iterators.advance(iterator, n);
        return Iterators.getNext(iterator, o);
    }
    
    @Nullable
    public static Object getFirst(final Iterable iterable, @Nullable final Object o) {
        return Iterators.getNext(iterable.iterator(), o);
    }
    
    public static Object getLast(final Iterable iterable) {
        if (!(iterable instanceof List)) {
            return Iterators.getLast(iterable.iterator());
        }
        final List list = (List)iterable;
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        return getLastInNonemptyList(list);
    }
    
    @Nullable
    public static Object getLast(final Iterable iterable, @Nullable final Object o) {
        if (iterable instanceof Collection) {
            if (Collections2.cast(iterable).isEmpty()) {
                return o;
            }
            if (iterable instanceof List) {
                return getLastInNonemptyList(Lists.cast(iterable));
            }
        }
        return Iterators.getLast(iterable.iterator(), o);
    }
    
    private static Object getLastInNonemptyList(final List list) {
        return list.get(list.size() - 1);
    }
    
    public static Iterable skip(final Iterable iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n >= 0, (Object)"number to skip cannot be negative");
        if (iterable instanceof List) {
            return new FluentIterable((List)iterable, n) {
                final List val$list;
                final int val$numberToSkip;
                
                @Override
                public Iterator iterator() {
                    return this.val$list.subList(Math.min(this.val$list.size(), this.val$numberToSkip), this.val$list.size()).iterator();
                }
            };
        }
        return new FluentIterable(iterable, n) {
            final Iterable val$iterable;
            final int val$numberToSkip;
            
            @Override
            public Iterator iterator() {
                final Iterator iterator = this.val$iterable.iterator();
                Iterators.advance(iterator, this.val$numberToSkip);
                return new Iterator((Iterator)iterator) {
                    boolean atStart = true;
                    final Iterator val$iterator;
                    final Iterables$10 this$0;
                    
                    @Override
                    public boolean hasNext() {
                        return this.val$iterator.hasNext();
                    }
                    
                    @Override
                    public Object next() {
                        final Object next = this.val$iterator.next();
                        this.atStart = false;
                        return next;
                    }
                    
                    @Override
                    public void remove() {
                        CollectPreconditions.checkRemove(!this.atStart);
                        this.val$iterator.remove();
                    }
                };
            }
        };
    }
    
    public static Iterable limit(final Iterable iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(n >= 0, (Object)"limit is negative");
        return new FluentIterable(iterable, n) {
            final Iterable val$iterable;
            final int val$limitSize;
            
            @Override
            public Iterator iterator() {
                return Iterators.limit(this.val$iterable.iterator(), this.val$limitSize);
            }
        };
    }
    
    public static Iterable consumingIterable(final Iterable iterable) {
        if (iterable instanceof Queue) {
            return new FluentIterable(iterable) {
                final Iterable val$iterable;
                
                @Override
                public Iterator iterator() {
                    return new ConsumingQueueIterator((Queue)this.val$iterable, null);
                }
                
                @Override
                public String toString() {
                    return "Iterables.consumingIterable(...)";
                }
            };
        }
        Preconditions.checkNotNull(iterable);
        return new FluentIterable(iterable) {
            final Iterable val$iterable;
            
            @Override
            public Iterator iterator() {
                return Iterators.consumingIterator(this.val$iterable.iterator());
            }
            
            @Override
            public String toString() {
                return "Iterables.consumingIterable(...)";
            }
        };
    }
    
    public static boolean isEmpty(final Iterable iterable) {
        if (iterable instanceof Collection) {
            return ((Collection)iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }
    
    @Beta
    public static Iterable mergeSorted(final Iterable iterable, final Comparator comparator) {
        Preconditions.checkNotNull(iterable, (Object)"iterables");
        Preconditions.checkNotNull(comparator, (Object)"comparator");
        return new UnmodifiableIterable(new FluentIterable(iterable, comparator) {
            final Iterable val$iterables;
            final Comparator val$comparator;
            
            @Override
            public Iterator iterator() {
                return Iterators.mergeSorted(Iterables.transform(this.val$iterables, Iterables.access$300()), this.val$comparator);
            }
        }, null);
    }
    
    private static Function toIterator() {
        return new Function() {
            public Iterator apply(final Iterable iterable) {
                return iterable.iterator();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Iterable)o);
            }
        };
    }
    
    static Iterator access$100(final Iterable iterable) {
        return iterators(iterable);
    }
    
    static Function access$300() {
        return toIterator();
    }
    
    private static class ConsumingQueueIterator extends AbstractIterator
    {
        private final Queue queue;
        
        private ConsumingQueueIterator(final Queue queue) {
            this.queue = queue;
        }
        
        public Object computeNext() {
            return this.queue.remove();
        }
        
        ConsumingQueueIterator(final Queue queue, final Iterables$1 fluentIterable) {
            this(queue);
        }
    }
    
    private static final class UnmodifiableIterable extends FluentIterable
    {
        private final Iterable iterable;
        
        private UnmodifiableIterable(final Iterable iterable) {
            this.iterable = iterable;
        }
        
        @Override
        public Iterator iterator() {
            return Iterators.unmodifiableIterator(this.iterable.iterator());
        }
        
        @Override
        public String toString() {
            return this.iterable.toString();
        }
        
        UnmodifiableIterable(final Iterable iterable, final Iterables$1 fluentIterable) {
            this(iterable);
        }
    }
}
