package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
final class Constraints
{
    private Constraints() {
    }
    
    public static Collection constrainedCollection(final Collection collection, final Constraint constraint) {
        return new ConstrainedCollection(collection, constraint);
    }
    
    public static Set constrainedSet(final Set set, final Constraint constraint) {
        return new ConstrainedSet(set, constraint);
    }
    
    public static SortedSet constrainedSortedSet(final SortedSet set, final Constraint constraint) {
        return new ConstrainedSortedSet(set, constraint);
    }
    
    public static List constrainedList(final List list, final Constraint constraint) {
        return (list instanceof RandomAccess) ? new ConstrainedRandomAccessList(list, constraint) : new ConstrainedList(list, constraint);
    }
    
    private static ListIterator constrainedListIterator(final ListIterator listIterator, final Constraint constraint) {
        return new ConstrainedListIterator(listIterator, constraint);
    }
    
    static Collection constrainedTypePreservingCollection(final Collection collection, final Constraint constraint) {
        if (collection instanceof SortedSet) {
            return constrainedSortedSet((SortedSet)collection, constraint);
        }
        if (collection instanceof Set) {
            return constrainedSet((Set)collection, constraint);
        }
        if (collection instanceof List) {
            return constrainedList((List)collection, constraint);
        }
        return constrainedCollection(collection, constraint);
    }
    
    private static Collection checkElements(final Collection collection, final Constraint constraint) {
        final ArrayList arrayList = Lists.newArrayList(collection);
        final Iterator<Object> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            constraint.checkElement(iterator.next());
        }
        return arrayList;
    }
    
    static Collection access$000(final Collection collection, final Constraint constraint) {
        return checkElements(collection, constraint);
    }
    
    static ListIterator access$100(final ListIterator listIterator, final Constraint constraint) {
        return constrainedListIterator(listIterator, constraint);
    }
    
    static class ConstrainedListIterator extends ForwardingListIterator
    {
        private final ListIterator delegate;
        private final Constraint constraint;
        
        public ConstrainedListIterator(final ListIterator delegate, final Constraint constraint) {
            this.delegate = delegate;
            this.constraint = constraint;
        }
        
        @Override
        protected ListIterator delegate() {
            return this.delegate;
        }
        
        @Override
        public void add(final Object o) {
            this.constraint.checkElement(o);
            this.delegate.add(o);
        }
        
        @Override
        public void set(final Object o) {
            this.constraint.checkElement(o);
            this.delegate.set(o);
        }
        
        @Override
        protected Iterator delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    static class ConstrainedRandomAccessList extends ConstrainedList implements RandomAccess
    {
        ConstrainedRandomAccessList(final List list, final Constraint constraint) {
            super(list, constraint);
        }
    }
    
    @GwtCompatible
    private static class ConstrainedList extends ForwardingList
    {
        final List delegate;
        final Constraint constraint;
        
        ConstrainedList(final List list, final Constraint constraint) {
            this.delegate = (List)Preconditions.checkNotNull(list);
            this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
        }
        
        @Override
        protected List delegate() {
            return this.delegate;
        }
        
        @Override
        public boolean add(final Object o) {
            this.constraint.checkElement(o);
            return this.delegate.add(o);
        }
        
        @Override
        public void add(final int n, final Object o) {
            this.constraint.checkElement(o);
            this.delegate.add(n, o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            return this.delegate.addAll(Constraints.access$000(collection, this.constraint));
        }
        
        @Override
        public boolean addAll(final int n, final Collection collection) {
            return this.delegate.addAll(n, Constraints.access$000(collection, this.constraint));
        }
        
        @Override
        public ListIterator listIterator() {
            return Constraints.access$100(this.delegate.listIterator(), this.constraint);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return Constraints.access$100(this.delegate.listIterator(n), this.constraint);
        }
        
        @Override
        public Object set(final int n, final Object o) {
            this.constraint.checkElement(o);
            return this.delegate.set(n, o);
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return Constraints.constrainedList(this.delegate.subList(n, n2), this.constraint);
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
    
    private static class ConstrainedSortedSet extends ForwardingSortedSet
    {
        final SortedSet delegate;
        final Constraint constraint;
        
        ConstrainedSortedSet(final SortedSet set, final Constraint constraint) {
            this.delegate = (SortedSet)Preconditions.checkNotNull(set);
            this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
        }
        
        @Override
        protected SortedSet delegate() {
            return this.delegate;
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return Constraints.constrainedSortedSet(this.delegate.headSet(o), this.constraint);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return Constraints.constrainedSortedSet(this.delegate.subSet(o, o2), this.constraint);
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return Constraints.constrainedSortedSet(this.delegate.tailSet(o), this.constraint);
        }
        
        @Override
        public boolean add(final Object o) {
            this.constraint.checkElement(o);
            return this.delegate.add(o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            return this.delegate.addAll(Constraints.access$000(collection, this.constraint));
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
    
    static class ConstrainedSet extends ForwardingSet
    {
        private final Set delegate;
        private final Constraint constraint;
        
        public ConstrainedSet(final Set set, final Constraint constraint) {
            this.delegate = (Set)Preconditions.checkNotNull(set);
            this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
        }
        
        @Override
        protected Set delegate() {
            return this.delegate;
        }
        
        @Override
        public boolean add(final Object o) {
            this.constraint.checkElement(o);
            return this.delegate.add(o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            return this.delegate.addAll(Constraints.access$000(collection, this.constraint));
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
    
    static class ConstrainedCollection extends ForwardingCollection
    {
        private final Collection delegate;
        private final Constraint constraint;
        
        public ConstrainedCollection(final Collection collection, final Constraint constraint) {
            this.delegate = (Collection)Preconditions.checkNotNull(collection);
            this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate;
        }
        
        @Override
        public boolean add(final Object o) {
            this.constraint.checkElement(o);
            return this.delegate.add(o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            return this.delegate.addAll(Constraints.access$000(collection, this.constraint));
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
