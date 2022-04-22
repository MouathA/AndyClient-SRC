package gnu.trove.impl.hash;

import gnu.trove.iterator.*;
import java.util.*;

public abstract class THashIterator implements TIterator, Iterator
{
    private final TObjectHash _object_hash;
    protected final THash _hash;
    protected int _expectedSize;
    protected int _index;
    
    protected THashIterator(final TObjectHash tObjectHash) {
        this._hash = tObjectHash;
        this._expectedSize = this._hash.size();
        this._index = this._hash.capacity();
        this._object_hash = tObjectHash;
    }
    
    @Override
    public Object next() {
        this.moveToNextIndex();
        return this.objectAtIndex(this._index);
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
    
    protected final int nextIndex() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        final Object[] set = this._object_hash._set;
        int index = this._index;
        while (index-- > 0 && (set[index] == TObjectHash.FREE || set[index] == TObjectHash.REMOVED)) {}
        return index;
    }
    
    protected abstract Object objectAtIndex(final int p0);
}
