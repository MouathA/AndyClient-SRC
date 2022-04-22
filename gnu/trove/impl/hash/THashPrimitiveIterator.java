package gnu.trove.impl.hash;

import gnu.trove.iterator.*;
import java.util.*;

public abstract class THashPrimitiveIterator implements TPrimitiveIterator
{
    protected final TPrimitiveHash _hash;
    protected int _expectedSize;
    protected int _index;
    
    public THashPrimitiveIterator(final TPrimitiveHash hash) {
        this._hash = hash;
        this._expectedSize = this._hash.size();
        this._index = this._hash.capacity();
    }
    
    protected final int nextIndex() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        final byte[] states = this._hash._states;
        int index = this._index;
        while (index-- > 0 && states[index] != 1) {}
        return index;
    }
    
    @Override
    public boolean hasNext() {
        return this.nextIndex() >= 0;
    }
    
    @Override
    public void remove() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        this._hash.tempDisableAutoCompaction();
        this._hash.removeAt(this._index);
        this._hash.reenableAutoCompaction(false);
        --this._expectedSize;
    }
    
    protected final void moveToNextIndex() {
        final int nextIndex = this.nextIndex();
        this._index = nextIndex;
        if (nextIndex < 0) {
            throw new NoSuchElementException();
        }
    }
}
