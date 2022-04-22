package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.primitives.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class TreeMultiset extends AbstractSortedMultiset implements Serializable
{
    private final transient Reference rootReference;
    private final transient GeneralRange range;
    private final transient AvlNode header;
    @GwtIncompatible("not needed in emulated source")
    private static final long serialVersionUID = 1L;
    
    public static TreeMultiset create() {
        return new TreeMultiset(Ordering.natural());
    }
    
    public static TreeMultiset create(@Nullable final Comparator comparator) {
        return (comparator == null) ? new TreeMultiset(Ordering.natural()) : new TreeMultiset(comparator);
    }
    
    public static TreeMultiset create(final Iterable iterable) {
        final TreeMultiset create = create();
        Iterables.addAll(create, iterable);
        return create;
    }
    
    TreeMultiset(final Reference rootReference, final GeneralRange range, final AvlNode header) {
        super(range.comparator());
        this.rootReference = rootReference;
        this.range = range;
        this.header = header;
    }
    
    TreeMultiset(final Comparator comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        successor(this.header = new AvlNode(null, 1), this.header);
        this.rootReference = new Reference(null);
    }
    
    private long aggregateForEntries(final Aggregate aggregate) {
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        long treeAggregate = aggregate.treeAggregate(avlNode);
        if (this.range.hasLowerBound()) {
            treeAggregate -= this.aggregateBelowRange(aggregate, avlNode);
        }
        if (this.range.hasUpperBound()) {
            treeAggregate -= this.aggregateAboveRange(aggregate, avlNode);
        }
        return treeAggregate;
    }
    
    private long aggregateBelowRange(final Aggregate aggregate, @Nullable final AvlNode avlNode) {
        if (avlNode == null) {
            return 0L;
        }
        final int compare = this.comparator().compare(this.range.getLowerEndpoint(), AvlNode.access$500(avlNode));
        if (compare < 0) {
            return this.aggregateBelowRange(aggregate, AvlNode.access$600(avlNode));
        }
        if (compare != 0) {
            return aggregate.treeAggregate(AvlNode.access$600(avlNode)) + aggregate.nodeAggregate(avlNode) + this.aggregateBelowRange(aggregate, AvlNode.access$700(avlNode));
        }
        switch (this.range.getLowerBoundType()) {
            case OPEN: {
                return aggregate.nodeAggregate(avlNode) + aggregate.treeAggregate(AvlNode.access$600(avlNode));
            }
            case CLOSED: {
                return aggregate.treeAggregate(AvlNode.access$600(avlNode));
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private long aggregateAboveRange(final Aggregate aggregate, @Nullable final AvlNode avlNode) {
        if (avlNode == null) {
            return 0L;
        }
        final int compare = this.comparator().compare(this.range.getUpperEndpoint(), AvlNode.access$500(avlNode));
        if (compare > 0) {
            return this.aggregateAboveRange(aggregate, AvlNode.access$700(avlNode));
        }
        if (compare != 0) {
            return aggregate.treeAggregate(AvlNode.access$700(avlNode)) + aggregate.nodeAggregate(avlNode) + this.aggregateAboveRange(aggregate, AvlNode.access$600(avlNode));
        }
        switch (this.range.getUpperBoundType()) {
            case OPEN: {
                return aggregate.nodeAggregate(avlNode) + aggregate.treeAggregate(AvlNode.access$700(avlNode));
            }
            case CLOSED: {
                return aggregate.treeAggregate(AvlNode.access$700(avlNode));
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @Override
    public int size() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.SIZE));
    }
    
    @Override
    int distinctElements() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.DISTINCT));
    }
    
    @Override
    public int count(@Nullable final Object o) {
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        if (!this.range.contains(o) || avlNode == null) {
            return 0;
        }
        return avlNode.count(this.comparator(), o);
    }
    
    @Override
    public int add(@Nullable final Object o, final int n) {
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(o);
        }
        Preconditions.checkArgument(this.range.contains(o));
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        if (avlNode == null) {
            this.comparator().compare(o, o);
            final AvlNode avlNode2 = new AvlNode(o, n);
            successor(this.header, avlNode2, this.header);
            this.rootReference.checkAndSet(avlNode, avlNode2);
            return 0;
        }
        final int[] array = { 0 };
        this.rootReference.checkAndSet(avlNode, avlNode.add(this.comparator(), o, n, array));
        return array[0];
    }
    
    @Override
    public int remove(@Nullable final Object o, final int n) {
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(o);
        }
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        final int[] array = { 0 };
        if (!this.range.contains(o) || avlNode == null) {
            return 0;
        }
        this.rootReference.checkAndSet(avlNode, avlNode.remove(this.comparator(), o, n, array));
        return array[0];
    }
    
    @Override
    public int setCount(@Nullable final Object o, final int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        if (!this.range.contains(o)) {
            Preconditions.checkArgument(n == 0);
            return 0;
        }
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        if (avlNode == null) {
            if (n > 0) {
                this.add(o, n);
            }
            return 0;
        }
        final int[] array = { 0 };
        this.rootReference.checkAndSet(avlNode, avlNode.setCount(this.comparator(), o, n, array));
        return array[0];
    }
    
    @Override
    public boolean setCount(@Nullable final Object o, final int n, final int n2) {
        CollectPreconditions.checkNonnegative(n2, "newCount");
        CollectPreconditions.checkNonnegative(n, "oldCount");
        Preconditions.checkArgument(this.range.contains(o));
        final AvlNode avlNode = (AvlNode)this.rootReference.get();
        if (avlNode != null) {
            final int[] array = { 0 };
            this.rootReference.checkAndSet(avlNode, avlNode.setCount(this.comparator(), o, n, n2, array));
            return array[0] == n;
        }
        if (n == 0) {
            if (n2 > 0) {
                this.add(o, n2);
            }
            return true;
        }
        return false;
    }
    
    private Multiset.Entry wrapEntry(final AvlNode avlNode) {
        return new Multisets.AbstractEntry(avlNode) {
            final AvlNode val$baseEntry;
            final TreeMultiset this$0;
            
            @Override
            public Object getElement() {
                return this.val$baseEntry.getElement();
            }
            
            @Override
            public int getCount() {
                final int count = this.val$baseEntry.getCount();
                if (count == 0) {
                    return this.this$0.count(this.getElement());
                }
                return count;
            }
        };
    }
    
    @Nullable
    private AvlNode firstNode() {
        if (this.rootReference.get() == null) {
            return null;
        }
        AvlNode avlNode;
        if (this.range.hasLowerBound()) {
            final Object lowerEndpoint = this.range.getLowerEndpoint();
            avlNode = AvlNode.access$800((AvlNode)this.rootReference.get(), this.comparator(), lowerEndpoint);
            if (avlNode == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && this.comparator().compare(lowerEndpoint, avlNode.getElement()) == 0) {
                avlNode = AvlNode.access$900(avlNode);
            }
        }
        else {
            avlNode = AvlNode.access$900(this.header);
        }
        return (avlNode == this.header || !this.range.contains(avlNode.getElement())) ? null : avlNode;
    }
    
    @Nullable
    private AvlNode lastNode() {
        if (this.rootReference.get() == null) {
            return null;
        }
        AvlNode avlNode;
        if (this.range.hasUpperBound()) {
            final Object upperEndpoint = this.range.getUpperEndpoint();
            avlNode = AvlNode.access$1000((AvlNode)this.rootReference.get(), this.comparator(), upperEndpoint);
            if (avlNode == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && this.comparator().compare(upperEndpoint, avlNode.getElement()) == 0) {
                avlNode = AvlNode.access$1100(avlNode);
            }
        }
        else {
            avlNode = AvlNode.access$1100(this.header);
        }
        return (avlNode == this.header || !this.range.contains(avlNode.getElement())) ? null : avlNode;
    }
    
    @Override
    Iterator entryIterator() {
        return new Iterator() {
            AvlNode current = TreeMultiset.access$1200(this.this$0);
            Multiset.Entry prevEntry;
            final TreeMultiset this$0;
            
            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (TreeMultiset.access$1300(this.this$0).tooHigh(this.current.getElement())) {
                    this.current = null;
                    return false;
                }
                return true;
            }
            
            @Override
            public Multiset.Entry next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Multiset.Entry access$1400 = TreeMultiset.access$1400(this.this$0, this.current);
                this.prevEntry = access$1400;
                if (AvlNode.access$900(this.current) == TreeMultiset.access$1500(this.this$0)) {
                    this.current = null;
                }
                else {
                    this.current = AvlNode.access$900(this.current);
                }
                return access$1400;
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                this.this$0.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    @Override
    Iterator descendingEntryIterator() {
        return new Iterator() {
            AvlNode current = TreeMultiset.access$1600(this.this$0);
            Multiset.Entry prevEntry = null;
            final TreeMultiset this$0;
            
            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (TreeMultiset.access$1300(this.this$0).tooLow(this.current.getElement())) {
                    this.current = null;
                    return false;
                }
                return true;
            }
            
            @Override
            public Multiset.Entry next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Multiset.Entry access$1400 = TreeMultiset.access$1400(this.this$0, this.current);
                this.prevEntry = access$1400;
                if (AvlNode.access$1100(this.current) == TreeMultiset.access$1500(this.this$0)) {
                    this.current = null;
                }
                else {
                    this.current = AvlNode.access$1100(this.current);
                }
                return access$1400;
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                this.this$0.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    @Override
    public SortedMultiset headMultiset(@Nullable final Object o, final BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(this.comparator(), o, boundType)), this.header);
    }
    
    @Override
    public SortedMultiset tailMultiset(@Nullable final Object o, final BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(this.comparator(), o, boundType)), this.header);
    }
    
    static int distinctElements(@Nullable final AvlNode avlNode) {
        return (avlNode == null) ? 0 : AvlNode.access$400(avlNode);
    }
    
    private static void successor(final AvlNode avlNode, final AvlNode avlNode2) {
        AvlNode.access$902(avlNode, avlNode2);
        AvlNode.access$1102(avlNode2, avlNode);
    }
    
    private static void successor(final AvlNode avlNode, final AvlNode avlNode2, final AvlNode avlNode3) {
        successor(avlNode, avlNode2);
        successor(avlNode2, avlNode3);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.elementSet().comparator());
        Serialization.writeMultiset(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final Comparator comparator = (Comparator)objectInputStream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set(this, comparator);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set(this, GeneralRange.all(comparator));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set(this, new Reference(null));
        final AvlNode avlNode = new AvlNode(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set(this, avlNode);
        successor(avlNode, avlNode);
        Serialization.populateMultiset(this, objectInputStream);
    }
    
    @Override
    public SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }
    
    @Override
    public SortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return super.subMultiset(o, boundType, o2, boundType2);
    }
    
    @Override
    public Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }
    
    @Override
    public Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return super.lastEntry();
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return super.firstEntry();
    }
    
    @Override
    public Comparator comparator() {
        return super.comparator();
    }
    
    @Override
    public NavigableSet elementSet() {
        return super.elementSet();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Set entrySet() {
        return super.entrySet();
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return super.retainAll(collection);
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return super.removeAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return super.addAll(collection);
    }
    
    @Override
    public boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Override
    public boolean add(final Object o) {
        return super.add(o);
    }
    
    @Override
    public Iterator iterator() {
        return super.iterator();
    }
    
    @Override
    public boolean contains(final Object o) {
        return super.contains(o);
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    static AvlNode access$1200(final TreeMultiset treeMultiset) {
        return treeMultiset.firstNode();
    }
    
    static GeneralRange access$1300(final TreeMultiset treeMultiset) {
        return treeMultiset.range;
    }
    
    static Multiset.Entry access$1400(final TreeMultiset treeMultiset, final AvlNode avlNode) {
        return treeMultiset.wrapEntry(avlNode);
    }
    
    static AvlNode access$1500(final TreeMultiset treeMultiset) {
        return treeMultiset.header;
    }
    
    static AvlNode access$1600(final TreeMultiset treeMultiset) {
        return treeMultiset.lastNode();
    }
    
    static void access$1700(final AvlNode avlNode, final AvlNode avlNode2, final AvlNode avlNode3) {
        successor(avlNode, avlNode2, avlNode3);
    }
    
    static void access$1800(final AvlNode avlNode, final AvlNode avlNode2) {
        successor(avlNode, avlNode2);
    }
    
    private static final class AvlNode extends Multisets.AbstractEntry
    {
        @Nullable
        private final Object elem;
        private int elemCount;
        private int distinctElements;
        private long totalCount;
        private int height;
        private AvlNode left;
        private AvlNode right;
        private AvlNode pred;
        private AvlNode succ;
        
        AvlNode(@Nullable final Object elem, final int elemCount) {
            Preconditions.checkArgument(elemCount > 0);
            this.elem = elem;
            this.elemCount = elemCount;
            this.totalCount = elemCount;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
        
        public int count(final Comparator comparator, final Object o) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                return (this.left == null) ? 0 : this.left.count(comparator, o);
            }
            if (compare > 0) {
                return (this.right == null) ? 0 : this.right.count(comparator, o);
            }
            return this.elemCount;
        }
        
        private AvlNode addRightChild(final Object o, final int n) {
            TreeMultiset.access$1700(this, this.right = new AvlNode(o, n), this.succ);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += n;
            return this;
        }
        
        private AvlNode addLeftChild(final Object o, final int n) {
            this.left = new AvlNode(o, n);
            TreeMultiset.access$1700(this.pred, this.left, this);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += n;
            return this;
        }
        
        AvlNode add(final Comparator comparator, @Nullable final Object o, final int n, final int[] array) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                final AvlNode left = this.left;
                if (left == null) {
                    array[0] = 0;
                    return this.addLeftChild(o, n);
                }
                final int height = left.height;
                this.left = left.add(comparator, o, n, array);
                if (array[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += n;
                return (this.left.height == height) ? this : this.rebalance();
            }
            else {
                if (compare <= 0) {
                    array[0] = this.elemCount;
                    Preconditions.checkArgument(this.elemCount + (long)n <= 2147483647L);
                    this.elemCount += n;
                    this.totalCount += n;
                    return this;
                }
                final AvlNode right = this.right;
                if (right == null) {
                    array[0] = 0;
                    return this.addRightChild(o, n);
                }
                final int height2 = right.height;
                this.right = right.add(comparator, o, n, array);
                if (array[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += n;
                return (this.right.height == height2) ? this : this.rebalance();
            }
        }
        
        AvlNode remove(final Comparator comparator, @Nullable final Object o, final int n, final int[] array) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                final AvlNode left = this.left;
                if (left == null) {
                    array[0] = 0;
                    return this;
                }
                this.left = left.remove(comparator, o, n, array);
                if (array[0] > 0) {
                    if (n >= array[0]) {
                        --this.distinctElements;
                        this.totalCount -= array[0];
                    }
                    else {
                        this.totalCount -= n;
                    }
                }
                return (array[0] == 0) ? this : this.rebalance();
            }
            else if (compare > 0) {
                final AvlNode right = this.right;
                if (right == null) {
                    array[0] = 0;
                    return this;
                }
                this.right = right.remove(comparator, o, n, array);
                if (array[0] > 0) {
                    if (n >= array[0]) {
                        --this.distinctElements;
                        this.totalCount -= array[0];
                    }
                    else {
                        this.totalCount -= n;
                    }
                }
                return this.rebalance();
            }
            else {
                array[0] = this.elemCount;
                if (n >= this.elemCount) {
                    return this.deleteMe();
                }
                this.elemCount -= n;
                this.totalCount -= n;
                return this;
            }
        }
        
        AvlNode setCount(final Comparator comparator, @Nullable final Object o, final int elemCount, final int[] array) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                final AvlNode left = this.left;
                if (left == null) {
                    array[0] = 0;
                    return (elemCount > 0) ? this.addLeftChild(o, elemCount) : this;
                }
                this.left = left.setCount(comparator, o, elemCount, array);
                if (elemCount == 0 && array[0] != 0) {
                    --this.distinctElements;
                }
                else if (elemCount > 0 && array[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += elemCount - array[0];
                return this.rebalance();
            }
            else if (compare > 0) {
                final AvlNode right = this.right;
                if (right == null) {
                    array[0] = 0;
                    return (elemCount > 0) ? this.addRightChild(o, elemCount) : this;
                }
                this.right = right.setCount(comparator, o, elemCount, array);
                if (elemCount == 0 && array[0] != 0) {
                    --this.distinctElements;
                }
                else if (elemCount > 0 && array[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += elemCount - array[0];
                return this.rebalance();
            }
            else {
                array[0] = this.elemCount;
                if (elemCount == 0) {
                    return this.deleteMe();
                }
                this.totalCount += elemCount - this.elemCount;
                this.elemCount = elemCount;
                return this;
            }
        }
        
        AvlNode setCount(final Comparator comparator, @Nullable final Object o, final int n, final int elemCount, final int[] array) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                final AvlNode left = this.left;
                if (left != null) {
                    this.left = left.setCount(comparator, o, n, elemCount, array);
                    if (array[0] == n) {
                        if (elemCount == 0 && array[0] != 0) {
                            --this.distinctElements;
                        }
                        else if (elemCount > 0 && array[0] == 0) {
                            ++this.distinctElements;
                        }
                        this.totalCount += elemCount - array[0];
                    }
                    return this.rebalance();
                }
                array[0] = 0;
                if (n == 0 && elemCount > 0) {
                    return this.addLeftChild(o, elemCount);
                }
                return this;
            }
            else {
                if (compare <= 0) {
                    array[0] = this.elemCount;
                    if (n == this.elemCount) {
                        if (elemCount == 0) {
                            return this.deleteMe();
                        }
                        this.totalCount += elemCount - this.elemCount;
                        this.elemCount = elemCount;
                    }
                    return this;
                }
                final AvlNode right = this.right;
                if (right != null) {
                    this.right = right.setCount(comparator, o, n, elemCount, array);
                    if (array[0] == n) {
                        if (elemCount == 0 && array[0] != 0) {
                            --this.distinctElements;
                        }
                        else if (elemCount > 0 && array[0] == 0) {
                            ++this.distinctElements;
                        }
                        this.totalCount += elemCount - array[0];
                    }
                    return this.rebalance();
                }
                array[0] = 0;
                if (n == 0 && elemCount > 0) {
                    return this.addRightChild(o, elemCount);
                }
                return this;
            }
        }
        
        private AvlNode deleteMe() {
            final int elemCount = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.access$1800(this.pred, this.succ);
            if (this.left == null) {
                return this.right;
            }
            if (this.right == null) {
                return this.left;
            }
            if (this.left.height >= this.right.height) {
                final AvlNode pred = this.pred;
                pred.left = this.left.removeMax(pred);
                pred.right = this.right;
                pred.distinctElements = this.distinctElements - 1;
                pred.totalCount = this.totalCount - elemCount;
                return pred.rebalance();
            }
            final AvlNode succ = this.succ;
            succ.right = this.right.removeMin(succ);
            succ.left = this.left;
            succ.distinctElements = this.distinctElements - 1;
            succ.totalCount = this.totalCount - elemCount;
            return succ.rebalance();
        }
        
        private AvlNode removeMin(final AvlNode avlNode) {
            if (this.left == null) {
                return this.right;
            }
            this.left = this.left.removeMin(avlNode);
            --this.distinctElements;
            this.totalCount -= avlNode.elemCount;
            return this.rebalance();
        }
        
        private AvlNode removeMax(final AvlNode avlNode) {
            if (this.right == null) {
                return this.left;
            }
            this.right = this.right.removeMax(avlNode);
            --this.distinctElements;
            this.totalCount -= avlNode.elemCount;
            return this.rebalance();
        }
        
        private void recomputeMultiset() {
            this.distinctElements = 1 + TreeMultiset.distinctElements(this.left) + TreeMultiset.distinctElements(this.right);
            this.totalCount = this.elemCount + totalCount(this.left) + totalCount(this.right);
        }
        
        private void recomputeHeight() {
            this.height = 1 + Math.max(height(this.left), height(this.right));
        }
        
        private void recompute() {
            this.recomputeMultiset();
            this.recomputeHeight();
        }
        
        private AvlNode rebalance() {
            switch (this.balanceFactor()) {
                case -2: {
                    if (this.right.balanceFactor() > 0) {
                        this.right = this.right.rotateRight();
                    }
                    return this.rotateLeft();
                }
                case 2: {
                    if (this.left.balanceFactor() < 0) {
                        this.left = this.left.rotateLeft();
                    }
                    return this.rotateRight();
                }
                default: {
                    this.recomputeHeight();
                    return this;
                }
            }
        }
        
        private int balanceFactor() {
            return height(this.left) - height(this.right);
        }
        
        private AvlNode rotateLeft() {
            Preconditions.checkState(this.right != null);
            final AvlNode right = this.right;
            this.right = right.left;
            right.left = this;
            right.totalCount = this.totalCount;
            right.distinctElements = this.distinctElements;
            this.recompute();
            right.recomputeHeight();
            return right;
        }
        
        private AvlNode rotateRight() {
            Preconditions.checkState(this.left != null);
            final AvlNode left = this.left;
            this.left = left.right;
            left.right = this;
            left.totalCount = this.totalCount;
            left.distinctElements = this.distinctElements;
            this.recompute();
            left.recomputeHeight();
            return left;
        }
        
        private static long totalCount(@Nullable final AvlNode avlNode) {
            return (avlNode == null) ? 0L : avlNode.totalCount;
        }
        
        private static int height(@Nullable final AvlNode avlNode) {
            return (avlNode == null) ? 0 : avlNode.height;
        }
        
        @Nullable
        private AvlNode ceiling(final Comparator comparator, final Object o) {
            final int compare = comparator.compare(o, this.elem);
            if (compare < 0) {
                return (AvlNode)((this.left == null) ? this : Objects.firstNonNull(this.left.ceiling(comparator, o), this));
            }
            if (compare == 0) {
                return this;
            }
            return (this.right == null) ? null : this.right.ceiling(comparator, o);
        }
        
        @Nullable
        private AvlNode floor(final Comparator comparator, final Object o) {
            final int compare = comparator.compare(o, this.elem);
            if (compare > 0) {
                return (AvlNode)((this.right == null) ? this : Objects.firstNonNull(this.right.floor(comparator, o), this));
            }
            if (compare == 0) {
                return this;
            }
            return (this.left == null) ? null : this.left.floor(comparator, o);
        }
        
        @Override
        public Object getElement() {
            return this.elem;
        }
        
        @Override
        public int getCount() {
            return this.elemCount;
        }
        
        @Override
        public String toString() {
            return Multisets.immutableEntry(this.getElement(), this.getCount()).toString();
        }
        
        static int access$200(final AvlNode avlNode) {
            return avlNode.elemCount;
        }
        
        static long access$300(final AvlNode avlNode) {
            return avlNode.totalCount;
        }
        
        static int access$400(final AvlNode avlNode) {
            return avlNode.distinctElements;
        }
        
        static Object access$500(final AvlNode avlNode) {
            return avlNode.elem;
        }
        
        static AvlNode access$600(final AvlNode avlNode) {
            return avlNode.left;
        }
        
        static AvlNode access$700(final AvlNode avlNode) {
            return avlNode.right;
        }
        
        static AvlNode access$800(final AvlNode avlNode, final Comparator comparator, final Object o) {
            return avlNode.ceiling(comparator, o);
        }
        
        static AvlNode access$900(final AvlNode avlNode) {
            return avlNode.succ;
        }
        
        static AvlNode access$1000(final AvlNode avlNode, final Comparator comparator, final Object o) {
            return avlNode.floor(comparator, o);
        }
        
        static AvlNode access$1100(final AvlNode avlNode) {
            return avlNode.pred;
        }
        
        static AvlNode access$902(final AvlNode avlNode, final AvlNode succ) {
            return avlNode.succ = succ;
        }
        
        static AvlNode access$1102(final AvlNode avlNode, final AvlNode pred) {
            return avlNode.pred = pred;
        }
    }
    
    private static final class Reference
    {
        @Nullable
        private Object value;
        
        private Reference() {
        }
        
        @Nullable
        public Object get() {
            return this.value;
        }
        
        public void checkAndSet(@Nullable final Object o, final Object value) {
            if (this.value != o) {
                throw new ConcurrentModificationException();
            }
            this.value = value;
        }
        
        Reference(final TreeMultiset$1 abstractEntry) {
            this();
        }
    }
    
    private enum Aggregate
    {
        SIZE {
            @Override
            int nodeAggregate(final AvlNode avlNode) {
                return AvlNode.access$200(avlNode);
            }
            
            @Override
            long treeAggregate(@Nullable final AvlNode avlNode) {
                return (avlNode == null) ? 0L : AvlNode.access$300(avlNode);
            }
        }, 
        DISTINCT {
            @Override
            int nodeAggregate(final AvlNode avlNode) {
                return 1;
            }
            
            @Override
            long treeAggregate(@Nullable final AvlNode avlNode) {
                return (avlNode == null) ? 0L : AvlNode.access$400(avlNode);
            }
        };
        
        private static final Aggregate[] $VALUES;
        
        private Aggregate(final String s, final int n) {
        }
        
        abstract int nodeAggregate(final AvlNode p0);
        
        abstract long treeAggregate(@Nullable final AvlNode p0);
        
        Aggregate(final String s, final int n, final TreeMultiset$1 abstractEntry) {
            this(s, n);
        }
        
        static {
            $VALUES = new Aggregate[] { Aggregate.SIZE, Aggregate.DISTINCT };
        }
    }
}
