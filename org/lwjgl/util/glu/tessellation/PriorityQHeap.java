package org.lwjgl.util.glu.tessellation;

class PriorityQHeap extends PriorityQ
{
    PQnode[] nodes;
    PQhandleElem[] handles;
    int size;
    int max;
    int freeList;
    boolean initialized;
    Leq leq;
    static final boolean $assertionsDisabled;
    
    PriorityQHeap(final Leq leq) {
        this.size = 0;
        this.max = 32;
        this.nodes = new PQnode[33];
        int n = 0;
        while (0 < this.nodes.length) {
            this.nodes[0] = new PQnode();
            ++n;
        }
        this.handles = new PQhandleElem[33];
        while (0 < this.handles.length) {
            this.handles[0] = new PQhandleElem();
            ++n;
        }
        this.initialized = false;
        this.freeList = 0;
        this.leq = leq;
        this.nodes[1].handle = 1;
        this.handles[1].key = null;
    }
    
    @Override
    void pqDeletePriorityQ() {
        this.handles = null;
        this.nodes = null;
    }
    
    void FloatDown(int n) {
        final PQnode[] nodes = this.nodes;
        final PQhandleElem[] handles = this.handles;
        final int handle = nodes[n].handle;
        while (true) {
            int n2 = n << 1;
            if (n2 < this.size && PriorityQ.LEQ(this.leq, handles[nodes[n2 + 1].handle].key, handles[nodes[n2].handle].key)) {
                ++n2;
            }
            assert n2 <= this.max;
            final int handle2 = nodes[n2].handle;
            if (n2 > this.size || PriorityQ.LEQ(this.leq, handles[handle].key, handles[handle2].key)) {
                nodes[n].handle = handle;
                handles[handle].node = n;
                return;
            }
            nodes[n].handle = handle2;
            handles[handle2].node = n;
            n = n2;
        }
    }
    
    void FloatUp(int n) {
        final PQnode[] nodes = this.nodes;
        final PQhandleElem[] handles = this.handles;
        final int handle = nodes[n].handle;
        while (true) {
            final int n2 = n >> 1;
            final int handle2 = nodes[n2].handle;
            if (n2 == 0 || PriorityQ.LEQ(this.leq, handles[handle2].key, handles[handle].key)) {
                break;
            }
            nodes[n].handle = handle2;
            handles[handle2].node = n;
            n = n2;
        }
        nodes[n].handle = handle;
        handles[handle].node = n;
    }
    
    @Override
    boolean pqInit() {
        for (int i = this.size; i >= 1; --i) {
            this.FloatDown(i);
        }
        return this.initialized = true;
    }
    
    @Override
    int pqInsert(final Object key) {
        final int node = ++this.size;
        if (node * 2 > this.max) {
            final PQnode[] nodes = this.nodes;
            final PQhandleElem[] handles = this.handles;
            this.max <<= 1;
            final PQnode[] nodes2 = new PQnode[this.max + 1];
            System.arraycopy(this.nodes, 0, nodes2, 0, this.nodes.length);
            for (int i = this.nodes.length; i < nodes2.length; ++i) {
                nodes2[i] = new PQnode();
            }
            this.nodes = nodes2;
            if (this.nodes == null) {
                this.nodes = nodes;
                return Integer.MAX_VALUE;
            }
            final PQhandleElem[] handles2 = new PQhandleElem[this.max + 1];
            System.arraycopy(this.handles, 0, handles2, 0, this.handles.length);
            for (int j = this.handles.length; j < handles2.length; ++j) {
                handles2[j] = new PQhandleElem();
            }
            this.handles = handles2;
            if (this.handles == null) {
                this.handles = handles;
                return Integer.MAX_VALUE;
            }
        }
        int freeList;
        if (this.freeList == 0) {
            freeList = node;
        }
        else {
            freeList = this.freeList;
            this.freeList = this.handles[freeList].node;
        }
        this.nodes[node].handle = freeList;
        this.handles[freeList].node = node;
        this.handles[freeList].key = key;
        if (this.initialized) {
            this.FloatUp(node);
        }
        assert freeList != Integer.MAX_VALUE;
        return freeList;
    }
    
    @Override
    Object pqExtractMin() {
        final PQnode[] nodes = this.nodes;
        final PQhandleElem[] handles = this.handles;
        final int handle = nodes[1].handle;
        final Object key = handles[handle].key;
        if (this.size > 0) {
            nodes[1].handle = nodes[this.size].handle;
            handles[nodes[1].handle].node = 1;
            handles[handle].key = null;
            handles[handle].node = this.freeList;
            this.freeList = handle;
            if (--this.size > 0) {
                this.FloatDown(1);
            }
        }
        return key;
    }
    
    @Override
    void pqDelete(final int freeList) {
        final PQnode[] nodes = this.nodes;
        final PQhandleElem[] handles = this.handles;
        assert freeList >= 1 && freeList <= this.max && handles[freeList].key != null;
        final int node = handles[freeList].node;
        nodes[node].handle = nodes[this.size].handle;
        if ((handles[nodes[node].handle].node = node) <= --this.size) {
            if (node <= 1 || PriorityQ.LEQ(this.leq, handles[nodes[node >> 1].handle].key, handles[nodes[node].handle].key)) {
                this.FloatDown(node);
            }
            else {
                this.FloatUp(node);
            }
        }
        handles[freeList].key = null;
        handles[freeList].node = this.freeList;
        this.freeList = freeList;
    }
    
    @Override
    Object pqMinimum() {
        return this.handles[this.nodes[1].handle].key;
    }
    
    @Override
    boolean pqIsEmpty() {
        return this.size == 0;
    }
    
    static {
        $assertionsDisabled = !PriorityQHeap.class.desiredAssertionStatus();
    }
}
