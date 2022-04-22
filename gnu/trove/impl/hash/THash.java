package gnu.trove.impl.hash;

import gnu.trove.impl.*;
import java.io.*;

public abstract class THash implements Externalizable
{
    static final long serialVersionUID = -1792948471915530295L;
    protected static final float DEFAULT_LOAD_FACTOR = 0.5f;
    protected transient int _size;
    protected transient int _free;
    protected float _loadFactor;
    protected int _maxSize;
    protected int _autoCompactRemovesRemaining;
    protected float _autoCompactionFactor;
    protected transient boolean _autoCompactTemporaryDisable;
    
    public THash() {
        this(10, 0.5f);
    }
    
    public THash(final int n) {
        this(n, 0.5f);
    }
    
    public THash(final int n, final float n2) {
        this._autoCompactTemporaryDisable = false;
        this._loadFactor = n2;
        this._autoCompactionFactor = n2;
        this.setUp(HashFunctions.fastCeil(n / n2));
    }
    
    public boolean isEmpty() {
        return this._size == 0;
    }
    
    public int size() {
        return this._size;
    }
    
    public abstract int capacity();
    
    public void ensureCapacity(final int n) {
        if (n > this._maxSize - this.size()) {
            this.rehash(PrimeFinder.nextPrime(Math.max(this.size() + 1, HashFunctions.fastCeil((n + this.size()) / this._loadFactor) + 1)));
            this.computeMaxSize(this.capacity());
        }
    }
    
    public void compact() {
        this.rehash(PrimeFinder.nextPrime(Math.max(this._size + 1, HashFunctions.fastCeil(this.size() / this._loadFactor) + 1)));
        this.computeMaxSize(this.capacity());
        if (this._autoCompactionFactor != 0.0f) {
            this.computeNextAutoCompactionAmount(this.size());
        }
    }
    
    public void setAutoCompactionFactor(final float autoCompactionFactor) {
        if (autoCompactionFactor < 0.0f) {
            throw new IllegalArgumentException("Factor must be >= 0: " + autoCompactionFactor);
        }
        this._autoCompactionFactor = autoCompactionFactor;
    }
    
    public float getAutoCompactionFactor() {
        return this._autoCompactionFactor;
    }
    
    public final void trimToSize() {
        this.compact();
    }
    
    protected void removeAt(final int n) {
        --this._size;
        if (this._autoCompactionFactor != 0.0f) {
            --this._autoCompactRemovesRemaining;
            if (!this._autoCompactTemporaryDisable && this._autoCompactRemovesRemaining <= 0) {
                this.compact();
            }
        }
    }
    
    public void clear() {
        this._size = 0;
        this._free = this.capacity();
    }
    
    protected int setUp(final int n) {
        final int nextPrime = PrimeFinder.nextPrime(n);
        this.computeMaxSize(nextPrime);
        this.computeNextAutoCompactionAmount(n);
        return nextPrime;
    }
    
    protected abstract void rehash(final int p0);
    
    public void tempDisableAutoCompaction() {
        this._autoCompactTemporaryDisable = true;
    }
    
    public void reenableAutoCompaction(final boolean b) {
        this._autoCompactTemporaryDisable = false;
        if (b && this._autoCompactRemovesRemaining <= 0 && this._autoCompactionFactor != 0.0f) {
            this.compact();
        }
    }
    
    protected void computeMaxSize(final int n) {
        this._maxSize = Math.min(n - 1, (int)(n * this._loadFactor));
        this._free = n - this._size;
    }
    
    protected void computeNextAutoCompactionAmount(final int n) {
        if (this._autoCompactionFactor != 0.0f) {
            this._autoCompactRemovesRemaining = (int)(n * this._autoCompactionFactor + 0.5f);
        }
    }
    
    protected final void postInsertHook(final boolean b) {
        if (b) {
            --this._free;
        }
        if (++this._size > this._maxSize || this._free == 0) {
            this.rehash((this._size > this._maxSize) ? PrimeFinder.nextPrime(this.capacity() << 1) : this.capacity());
            this.computeMaxSize(this.capacity());
        }
    }
    
    protected int calculateGrownCapacity() {
        return this.capacity() << 1;
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        objectOutput.writeFloat(this._loadFactor);
        objectOutput.writeFloat(this._autoCompactionFactor);
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        final float loadFactor = this._loadFactor;
        this._loadFactor = objectInput.readFloat();
        this._autoCompactionFactor = objectInput.readFloat();
        if (loadFactor != this._loadFactor) {
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
    }
}
