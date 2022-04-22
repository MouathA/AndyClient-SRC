package gnu.trove.impl.hash;

import gnu.trove.impl.*;

public abstract class TPrimitiveHash extends THash
{
    static final long serialVersionUID = 1L;
    public transient byte[] _states;
    public static final byte FREE;
    public static final byte FULL;
    public static final byte REMOVED;
    
    public TPrimitiveHash() {
    }
    
    public TPrimitiveHash(final int n) {
        this(n, 0.5f);
    }
    
    public TPrimitiveHash(int max, final float loadFactor) {
        max = Math.max(1, max);
        this._loadFactor = loadFactor;
        this.setUp(HashFunctions.fastCeil(max / loadFactor));
    }
    
    @Override
    public int capacity() {
        return this._states.length;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._states[n] = 2;
        super.removeAt(n);
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._states = new byte[setUp];
        return setUp;
    }
    
    static {
        FREE = 0;
        REMOVED = 2;
        FULL = 1;
    }
}
