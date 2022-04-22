package io.netty.channel.nio;

import java.nio.channels.*;
import java.util.*;

final class SelectedSelectionKeySet extends AbstractSet
{
    private SelectionKey[] keysA;
    private int keysASize;
    private SelectionKey[] keysB;
    private int keysBSize;
    private boolean isA;
    
    SelectedSelectionKeySet() {
        this.isA = true;
        this.keysA = new SelectionKey[1024];
        this.keysB = this.keysA.clone();
    }
    
    public boolean add(final SelectionKey selectionKey) {
        if (selectionKey == null) {
            return false;
        }
        if (this.isA) {
            int keysASize = this.keysASize;
            this.keysA[keysASize++] = selectionKey;
            if ((this.keysASize = keysASize) == this.keysA.length) {
                this.doubleCapacityA();
            }
        }
        else {
            int keysBSize = this.keysBSize;
            this.keysB[keysBSize++] = selectionKey;
            if ((this.keysBSize = keysBSize) == this.keysB.length) {
                this.doubleCapacityB();
            }
        }
        return true;
    }
    
    private void doubleCapacityA() {
        final SelectionKey[] keysA = new SelectionKey[this.keysA.length << 1];
        System.arraycopy(this.keysA, 0, keysA, 0, this.keysASize);
        this.keysA = keysA;
    }
    
    private void doubleCapacityB() {
        final SelectionKey[] keysB = new SelectionKey[this.keysB.length << 1];
        System.arraycopy(this.keysB, 0, keysB, 0, this.keysBSize);
        this.keysB = keysB;
    }
    
    SelectionKey[] flip() {
        if (this.isA) {
            this.isA = false;
            this.keysA[this.keysASize] = null;
            this.keysBSize = 0;
            return this.keysA;
        }
        this.isA = true;
        this.keysB[this.keysBSize] = null;
        this.keysASize = 0;
        return this.keysB;
    }
    
    @Override
    public int size() {
        if (this.isA) {
            return this.keysASize;
        }
        return this.keysBSize;
    }
    
    @Override
    public boolean remove(final Object o) {
        return false;
    }
    
    @Override
    public boolean contains(final Object o) {
        return false;
    }
    
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean add(final Object o) {
        return this.add((SelectionKey)o);
    }
}
