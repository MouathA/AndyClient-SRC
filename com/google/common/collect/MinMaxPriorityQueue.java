package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import com.google.common.math.*;
import java.util.*;

@Beta
public final class MinMaxPriorityQueue extends AbstractQueue
{
    private final Heap minHeap;
    private final Heap maxHeap;
    @VisibleForTesting
    final int maximumSize;
    private Object[] queue;
    private int size;
    private int modCount;
    private static final int EVEN_POWERS_OF_TWO = 1431655765;
    private static final int ODD_POWERS_OF_TWO = -1431655766;
    private static final int DEFAULT_CAPACITY = 11;
    
    public static MinMaxPriorityQueue create() {
        return new Builder(Ordering.natural(), null).create();
    }
    
    public static MinMaxPriorityQueue create(final Iterable iterable) {
        return new Builder(Ordering.natural(), null).create(iterable);
    }
    
    public static Builder orderedBy(final Comparator comparator) {
        return new Builder(comparator, null);
    }
    
    public static Builder expectedSize(final int n) {
        return new Builder(Ordering.natural(), null).expectedSize(n);
    }
    
    public static Builder maximumSize(final int n) {
        return new Builder(Ordering.natural(), null).maximumSize(n);
    }
    
    private MinMaxPriorityQueue(final Builder builder, final int n) {
        final Ordering access$200 = Builder.access$200(builder);
        this.minHeap = new Heap(access$200);
        this.maxHeap = new Heap(access$200.reverse());
        this.minHeap.otherHeap = this.maxHeap;
        this.maxHeap.otherHeap = this.minHeap;
        this.maximumSize = Builder.access$300(builder);
        this.queue = new Object[n];
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean add(final Object o) {
        this.offer(o);
        return true;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.offer(iterator.next());
        }
        return true;
    }
    
    @Override
    public boolean offer(final Object o) {
        Preconditions.checkNotNull(o);
        ++this.modCount;
        final int n = this.size++;
        this.growIfNeeded();
        this.heapForIndex(n).bubbleUp(n, o);
        return this.size <= this.maximumSize || this.pollLast() != o;
    }
    
    @Override
    public Object poll() {
        return this.isEmpty() ? null : this.removeAndGet(0);
    }
    
    Object elementData(final int n) {
        return this.queue[n];
    }
    
    @Override
    public Object peek() {
        return this.isEmpty() ? null : this.elementData(0);
    }
    
    private int getMaxElementIndex() {
        switch (this.size) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            default: {
                return (this.maxHeap.compareElements(1, 2) <= 0) ? 1 : 2;
            }
        }
    }
    
    public Object pollFirst() {
        return this.poll();
    }
    
    public Object removeFirst() {
        return this.remove();
    }
    
    public Object peekFirst() {
        return this.peek();
    }
    
    public Object pollLast() {
        return this.isEmpty() ? null : this.removeAndGet(this.getMaxElementIndex());
    }
    
    public Object removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeAndGet(this.getMaxElementIndex());
    }
    
    public Object peekLast() {
        return this.isEmpty() ? null : this.elementData(this.getMaxElementIndex());
    }
    
    @VisibleForTesting
    MoveDesc removeAt(final int n) {
        Preconditions.checkPositionIndex(n, this.size);
        ++this.modCount;
        --this.size;
        if (this.size == n) {
            this.queue[this.size] = null;
            return null;
        }
        final Object elementData = this.elementData(this.size);
        final int correctLastElement = this.heapForIndex(this.size).getCorrectLastElement(elementData);
        final Object elementData2 = this.elementData(this.size);
        this.queue[this.size] = null;
        final MoveDesc fillHole = this.fillHole(n, elementData2);
        if (correctLastElement >= n) {
            return fillHole;
        }
        if (fillHole == null) {
            return new MoveDesc(elementData, elementData2);
        }
        return new MoveDesc(elementData, fillHole.replaced);
    }
    
    private MoveDesc fillHole(final int n, final Object o) {
        final Heap heapForIndex = this.heapForIndex(n);
        final int fillHole = heapForIndex.fillHoleAt(n);
        final int bubbleUpAlternatingLevels = heapForIndex.bubbleUpAlternatingLevels(fillHole, o);
        if (bubbleUpAlternatingLevels == fillHole) {
            return heapForIndex.tryCrossOverAndBubbleUp(n, fillHole, o);
        }
        return (bubbleUpAlternatingLevels < n) ? new MoveDesc(o, this.elementData(n)) : null;
    }
    
    private Object removeAndGet(final int n) {
        final Object elementData = this.elementData(n);
        this.removeAt(n);
        return elementData;
    }
    
    private Heap heapForIndex(final int n) {
        return isEvenLevel(n) ? this.minHeap : this.maxHeap;
    }
    
    @VisibleForTesting
    static boolean isEvenLevel(final int n) {
        final int n2 = n + 1;
        Preconditions.checkState(n2 > 0, (Object)"negative index");
        return (n2 & 0x55555555) > (n2 & 0xAAAAAAAA);
    }
    
    @VisibleForTesting
    boolean isIntact() {
        while (1 < this.size) {
            if (!Heap.access$400(this.heapForIndex(1), 1)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public Iterator iterator() {
        return new QueueIterator(null);
    }
    
    @Override
    public void clear() {
        while (0 < this.size) {
            this.queue[0] = null;
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[this.size];
        System.arraycopy(this.queue, 0, array, 0, this.size);
        return array;
    }
    
    public Comparator comparator() {
        return this.minHeap.ordering;
    }
    
    @VisibleForTesting
    int capacity() {
        return this.queue.length;
    }
    
    @VisibleForTesting
    static int initialQueueSize(final int n, final int n2, final Iterable iterable) {
        int max = (n == -1) ? 11 : n;
        if (iterable instanceof Collection) {
            max = Math.max(max, ((Collection)iterable).size());
        }
        return capAtMaximumSize(max, n2);
    }
    
    private void growIfNeeded() {
        if (this.size > this.queue.length) {
            final Object[] queue = new Object[this.calculateNewCapacity()];
            System.arraycopy(this.queue, 0, queue, 0, this.queue.length);
            this.queue = queue;
        }
    }
    
    private int calculateNewCapacity() {
        final int length = this.queue.length;
        return capAtMaximumSize((length < 64) ? ((length + 1) * 2) : IntMath.checkedMultiply(length / 2, 3), this.maximumSize);
    }
    
    private static int capAtMaximumSize(final int n, final int n2) {
        return Math.min(n - 1, n2) + 1;
    }
    
    MinMaxPriorityQueue(final Builder builder, final int n, final MinMaxPriorityQueue$1 object) {
        this(builder, n);
    }
    
    static Object[] access$500(final MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.queue;
    }
    
    static int access$600(final MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.size;
    }
    
    static int access$700(final MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.modCount;
    }
    
    private class QueueIterator implements Iterator
    {
        private int cursor;
        private int expectedModCount;
        private Queue forgetMeNot;
        private List skipMe;
        private Object lastFromForgetMeNot;
        private boolean canRemove;
        final MinMaxPriorityQueue this$0;
        
        private QueueIterator(final MinMaxPriorityQueue this$0) {
            this.this$0 = this$0;
            this.cursor = -1;
            this.expectedModCount = MinMaxPriorityQueue.access$700(this.this$0);
        }
        
        @Override
        public boolean hasNext() {
            this.checkModCount();
            return this.nextNotInSkipMe(this.cursor + 1) < this.this$0.size() || (this.forgetMeNot != null && !this.forgetMeNot.isEmpty());
        }
        
        @Override
        public Object next() {
            this.checkModCount();
            final int nextNotInSkipMe = this.nextNotInSkipMe(this.cursor + 1);
            if (nextNotInSkipMe < this.this$0.size()) {
                this.cursor = nextNotInSkipMe;
                this.canRemove = true;
                return this.this$0.elementData(this.cursor);
            }
            if (this.forgetMeNot != null) {
                this.cursor = this.this$0.size();
                this.lastFromForgetMeNot = this.forgetMeNot.poll();
                if (this.lastFromForgetMeNot != null) {
                    this.canRemove = true;
                    return this.lastFromForgetMeNot;
                }
            }
            throw new NoSuchElementException("iterator moved past last element in queue.");
        }
        
        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            this.checkModCount();
            this.canRemove = false;
            ++this.expectedModCount;
            if (this.cursor < this.this$0.size()) {
                final MoveDesc remove = this.this$0.removeAt(this.cursor);
                if (remove != null) {
                    if (this.forgetMeNot == null) {
                        this.forgetMeNot = new ArrayDeque();
                        this.skipMe = new ArrayList(3);
                    }
                    this.forgetMeNot.add(remove.toTrickle);
                    this.skipMe.add(remove.replaced);
                }
                --this.cursor;
            }
            else {
                Preconditions.checkState(this.removeExact(this.lastFromForgetMeNot));
                this.lastFromForgetMeNot = null;
            }
        }
        
        private boolean containsExact(final Iterable iterable, final Object o) {
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == o) {
                    return true;
                }
            }
            return false;
        }
        
        boolean removeExact(final Object o) {
            while (0 < MinMaxPriorityQueue.access$600(this.this$0)) {
                if (MinMaxPriorityQueue.access$500(this.this$0)[0] == o) {
                    this.this$0.removeAt(0);
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        void checkModCount() {
            if (MinMaxPriorityQueue.access$700(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        private int nextNotInSkipMe(int n) {
            if (this.skipMe != null) {
                while (n < this.this$0.size() && this.containsExact(this.skipMe, this.this$0.elementData(n))) {
                    ++n;
                }
            }
            return n;
        }
        
        QueueIterator(final MinMaxPriorityQueue minMaxPriorityQueue, final MinMaxPriorityQueue$1 object) {
            this(minMaxPriorityQueue);
        }
    }
    
    static class MoveDesc
    {
        final Object toTrickle;
        final Object replaced;
        
        MoveDesc(final Object toTrickle, final Object replaced) {
            this.toTrickle = toTrickle;
            this.replaced = replaced;
        }
    }
    
    private class Heap
    {
        final Ordering ordering;
        Heap otherHeap;
        final MinMaxPriorityQueue this$0;
        
        Heap(final MinMaxPriorityQueue this$0, final Ordering ordering) {
            this.this$0 = this$0;
            this.ordering = ordering;
        }
        
        int compareElements(final int n, final int n2) {
            return this.ordering.compare(this.this$0.elementData(n), this.this$0.elementData(n2));
        }
        
        MoveDesc tryCrossOverAndBubbleUp(final int n, final int n2, final Object o) {
            final int crossOver = this.crossOver(n2, o);
            if (crossOver == n2) {
                return null;
            }
            Object o2;
            if (crossOver < n) {
                o2 = this.this$0.elementData(n);
            }
            else {
                o2 = this.this$0.elementData(this.getParentIndex(n));
            }
            if (this.otherHeap.bubbleUpAlternatingLevels(crossOver, o) < n) {
                return new MoveDesc(o, o2);
            }
            return null;
        }
        
        void bubbleUp(int n, final Object o) {
            final int crossOverUp = this.crossOverUp(n, o);
            Heap otherHeap;
            if (crossOverUp == n) {
                otherHeap = this;
            }
            else {
                n = crossOverUp;
                otherHeap = this.otherHeap;
            }
            otherHeap.bubbleUpAlternatingLevels(n, o);
        }
        
        int bubbleUpAlternatingLevels(int i, final Object o) {
            while (i > 2) {
                final int grandparentIndex = this.getGrandparentIndex(i);
                final Object elementData = this.this$0.elementData(grandparentIndex);
                if (this.ordering.compare(elementData, o) <= 0) {
                    break;
                }
                MinMaxPriorityQueue.access$500(this.this$0)[i] = elementData;
                i = grandparentIndex;
            }
            MinMaxPriorityQueue.access$500(this.this$0)[i] = o;
            return i;
        }
        
        int findMin(final int n, final int n2) {
            if (n >= MinMaxPriorityQueue.access$600(this.this$0)) {
                return -1;
            }
            Preconditions.checkState(n > 0);
            final int n3 = Math.min(n, MinMaxPriorityQueue.access$600(this.this$0) - n2) + n2;
            int n4 = n;
            for (int i = n + 1; i < n3; ++i) {
                if (this.compareElements(i, n4) < 0) {
                    n4 = i;
                }
            }
            return n4;
        }
        
        int findMinChild(final int n) {
            return this.findMin(this.getLeftChildIndex(n), 2);
        }
        
        int findMinGrandChild(final int n) {
            final int leftChildIndex = this.getLeftChildIndex(n);
            if (leftChildIndex < 0) {
                return -1;
            }
            return this.findMin(this.getLeftChildIndex(leftChildIndex), 4);
        }
        
        int crossOverUp(final int n, final Object o) {
            if (n == 0) {
                MinMaxPriorityQueue.access$500(this.this$0)[0] = o;
                return 0;
            }
            int parentIndex = this.getParentIndex(n);
            Object elementData = this.this$0.elementData(parentIndex);
            if (parentIndex != 0) {
                final int rightChildIndex = this.getRightChildIndex(this.getParentIndex(parentIndex));
                if (rightChildIndex != parentIndex && this.getLeftChildIndex(rightChildIndex) >= MinMaxPriorityQueue.access$600(this.this$0)) {
                    final Object elementData2 = this.this$0.elementData(rightChildIndex);
                    if (this.ordering.compare(elementData2, elementData) < 0) {
                        parentIndex = rightChildIndex;
                        elementData = elementData2;
                    }
                }
            }
            if (this.ordering.compare(elementData, o) < 0) {
                MinMaxPriorityQueue.access$500(this.this$0)[n] = elementData;
                MinMaxPriorityQueue.access$500(this.this$0)[parentIndex] = o;
                return parentIndex;
            }
            MinMaxPriorityQueue.access$500(this.this$0)[n] = o;
            return n;
        }
        
        int getCorrectLastElement(final Object o) {
            final int parentIndex = this.getParentIndex(MinMaxPriorityQueue.access$600(this.this$0));
            if (parentIndex != 0) {
                final int rightChildIndex = this.getRightChildIndex(this.getParentIndex(parentIndex));
                if (rightChildIndex != parentIndex && this.getLeftChildIndex(rightChildIndex) >= MinMaxPriorityQueue.access$600(this.this$0)) {
                    final Object elementData = this.this$0.elementData(rightChildIndex);
                    if (this.ordering.compare(elementData, o) < 0) {
                        MinMaxPriorityQueue.access$500(this.this$0)[rightChildIndex] = o;
                        MinMaxPriorityQueue.access$500(this.this$0)[MinMaxPriorityQueue.access$600(this.this$0)] = elementData;
                        return rightChildIndex;
                    }
                }
            }
            return MinMaxPriorityQueue.access$600(this.this$0);
        }
        
        int crossOver(final int n, final Object o) {
            final int minChild = this.findMinChild(n);
            if (minChild > 0 && this.ordering.compare(this.this$0.elementData(minChild), o) < 0) {
                MinMaxPriorityQueue.access$500(this.this$0)[n] = this.this$0.elementData(minChild);
                MinMaxPriorityQueue.access$500(this.this$0)[minChild] = o;
                return minChild;
            }
            return this.crossOverUp(n, o);
        }
        
        int fillHoleAt(int n) {
            int minGrandChild;
            while ((minGrandChild = this.findMinGrandChild(n)) > 0) {
                MinMaxPriorityQueue.access$500(this.this$0)[n] = this.this$0.elementData(minGrandChild);
                n = minGrandChild;
            }
            return n;
        }
        
        private boolean verifyIndex(final int n) {
            return (this.getLeftChildIndex(n) >= MinMaxPriorityQueue.access$600(this.this$0) || this.compareElements(n, this.getLeftChildIndex(n)) <= 0) && (this.getRightChildIndex(n) >= MinMaxPriorityQueue.access$600(this.this$0) || this.compareElements(n, this.getRightChildIndex(n)) <= 0) && (n <= 0 || this.compareElements(n, this.getParentIndex(n)) <= 0) && (n <= 2 || this.compareElements(this.getGrandparentIndex(n), n) <= 0);
        }
        
        private int getLeftChildIndex(final int n) {
            return n * 2 + 1;
        }
        
        private int getRightChildIndex(final int n) {
            return n * 2 + 2;
        }
        
        private int getParentIndex(final int n) {
            return (n - 1) / 2;
        }
        
        private int getGrandparentIndex(final int n) {
            return this.getParentIndex(this.getParentIndex(n));
        }
        
        static boolean access$400(final Heap heap, final int n) {
            return heap.verifyIndex(n);
        }
    }
    
    @Beta
    public static final class Builder
    {
        private static final int UNSET_EXPECTED_SIZE = -1;
        private final Comparator comparator;
        private int expectedSize;
        private int maximumSize;
        
        private Builder(final Comparator comparator) {
            this.expectedSize = -1;
            this.maximumSize = Integer.MAX_VALUE;
            this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
        }
        
        public Builder expectedSize(final int expectedSize) {
            Preconditions.checkArgument(expectedSize >= 0);
            this.expectedSize = expectedSize;
            return this;
        }
        
        public Builder maximumSize(final int maximumSize) {
            Preconditions.checkArgument(maximumSize > 0);
            this.maximumSize = maximumSize;
            return this;
        }
        
        public MinMaxPriorityQueue create() {
            return this.create(Collections.emptySet());
        }
        
        public MinMaxPriorityQueue create(final Iterable iterable) {
            final MinMaxPriorityQueue minMaxPriorityQueue = new MinMaxPriorityQueue(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, iterable), null);
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                minMaxPriorityQueue.offer(iterator.next());
            }
            return minMaxPriorityQueue;
        }
        
        private Ordering ordering() {
            return Ordering.from(this.comparator);
        }
        
        Builder(final Comparator comparator, final MinMaxPriorityQueue$1 object) {
            this(comparator);
        }
        
        static Ordering access$200(final Builder builder) {
            return builder.ordering();
        }
        
        static int access$300(final Builder builder) {
            return builder.maximumSize;
        }
    }
}
