package org.lwjgl.util.glu.tessellation;

class PriorityQSort extends PriorityQ
{
    PriorityQHeap heap;
    Object[] keys;
    int[] order;
    int size;
    int max;
    boolean initialized;
    Leq leq;
    static final boolean $assertionsDisabled;
    
    PriorityQSort(final Leq leq) {
        this.heap = new PriorityQHeap(leq);
        this.keys = new Object[32];
        this.size = 0;
        this.max = 32;
        this.initialized = false;
        this.leq = leq;
    }
    
    @Override
    void pqDeletePriorityQ() {
        if (this.heap != null) {
            this.heap.pqDeletePriorityQ();
        }
        this.order = null;
        this.keys = null;
    }
    
    private static boolean LT(final Leq leq, final Object o, final Object o2) {
        return !PriorityQ.LEQ(leq, o2, o);
    }
    
    private static boolean GT(final Leq leq, final Object o, final Object o2) {
        return !PriorityQ.LEQ(leq, o, o2);
    }
    
    private static void Swap(final int[] array, final int n, final int n2) {
        final int n3 = array[n];
        array[n] = array[n2];
        array[n2] = n3;
    }
    
    @Override
    boolean pqInit() {
        final Stack[] array = new Stack[50];
        int n = 0;
        while (0 < array.length) {
            array[0] = new Stack(null);
            ++n;
        }
        this.order = new int[this.size + 1];
        final int n2 = this.size - 1;
        int n4 = 0;
        while (1 <= 0) {
            this.order[1] = 0;
            int n3 = 0;
            ++n3;
            ++n4;
        }
        array[0].p = 0;
        array[0].r = 0;
        ++n;
        while (true) {
            --n;
            if (0 < 0) {
                break;
            }
            final int p = array[0].p;
            final int r = array[0].r;
            int n5 = 0;
            while (0 > 10) {
                Math.abs(1409832680);
                final int n3 = this.order[1];
                this.order[1] = this.order[0];
                this.order[0] = 0;
                while (true) {
                    ++n4;
                    if (!GT(this.leq, this.keys[this.order[1]], this.keys[0])) {
                        do {
                            --n5;
                        } while (LT(this.leq, this.keys[this.order[1]], this.keys[0]));
                        Swap(this.order, 1, 1);
                        if (1 >= 1) {
                            break;
                        }
                        continue;
                    }
                }
                Swap(this.order, 1, 1);
                if (1 < -1) {
                    array[0].p = 2;
                    array[0].r = 0;
                    ++n;
                }
                else {
                    array[0].p = 0;
                    array[0].r = 0;
                    ++n;
                }
            }
            while (1 <= 0) {
                final int n3 = this.order[1];
                while (1 > 0 && LT(this.leq, this.keys[this.order[0]], this.keys[0])) {
                    this.order[1] = this.order[0];
                    --n5;
                }
                this.order[1] = 0;
                ++n4;
            }
        }
        this.max = this.size;
        this.initialized = true;
        this.heap.pqInit();
        return true;
    }
    
    @Override
    int pqInsert(final Object o) {
        if (this.initialized) {
            return this.heap.pqInsert(o);
        }
        final int size = this.size;
        if (++this.size >= this.max) {
            final Object[] keys = this.keys;
            this.max <<= 1;
            final Object[] keys2 = new Object[this.max];
            System.arraycopy(this.keys, 0, keys2, 0, this.keys.length);
            this.keys = keys2;
            if (this.keys == null) {
                this.keys = keys;
                return Integer.MAX_VALUE;
            }
        }
        assert size != Integer.MAX_VALUE;
        this.keys[size] = o;
        return -(size + 1);
    }
    
    @Override
    Object pqExtractMin() {
        if (this.size == 0) {
            return this.heap.pqExtractMin();
        }
        final Object o = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty() && PriorityQ.LEQ(this.leq, this.heap.pqMinimum(), o)) {
            return this.heap.pqExtractMin();
        }
        do {
            --this.size;
        } while (this.size > 0 && this.keys[this.order[this.size - 1]] == null);
        return o;
    }
    
    @Override
    Object pqMinimum() {
        if (this.size == 0) {
            return this.heap.pqMinimum();
        }
        final Object o = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty()) {
            final Object pqMinimum = this.heap.pqMinimum();
            if (PriorityQ.LEQ(this.leq, pqMinimum, o)) {
                return pqMinimum;
            }
        }
        return o;
    }
    
    @Override
    boolean pqIsEmpty() {
        return this.size == 0 && this.heap.pqIsEmpty();
    }
    
    @Override
    void pqDelete(int n) {
        if (n >= 0) {
            this.heap.pqDelete(n);
            return;
        }
        n = -(n + 1);
        assert n < this.max && this.keys[n] != null;
        this.keys[n] = null;
        while (this.size > 0 && this.keys[this.order[this.size - 1]] == null) {
            --this.size;
        }
    }
    
    static {
        $assertionsDisabled = !PriorityQSort.class.desiredAssertionStatus();
    }
    
    private static class Stack
    {
        int p;
        int r;
        
        private Stack() {
        }
        
        Stack(final PriorityQSort$1 object) {
            this();
        }
    }
}
