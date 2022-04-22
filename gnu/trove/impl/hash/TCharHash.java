package gnu.trove.impl.hash;

import java.util.*;
import gnu.trove.procedure.*;
import gnu.trove.impl.*;

public abstract class TCharHash extends TPrimitiveHash
{
    static final long serialVersionUID = 1L;
    public transient char[] _set;
    protected char no_entry_value;
    protected boolean consumeFreeSlot;
    
    public TCharHash() {
        this.no_entry_value = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
        if (this.no_entry_value != '\0') {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TCharHash(final int n) {
        super(n);
        this.no_entry_value = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
        if (this.no_entry_value != '\0') {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TCharHash(final int n, final float n2) {
        super(n, n2);
        this.no_entry_value = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
        if (this.no_entry_value != '\0') {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TCharHash(final int n, final float n2, final char no_entry_value) {
        super(n, n2);
        this.no_entry_value = no_entry_value;
        if (no_entry_value != '\0') {
            Arrays.fill(this._set, no_entry_value);
        }
    }
    
    public char getNoEntryValue() {
        return this.no_entry_value;
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._set = new char[setUp];
        return setUp;
    }
    
    public boolean contains(final char c) {
        return this.index(c) >= 0;
    }
    
    public boolean forEach(final TCharProcedure tCharProcedure) {
        final byte[] states = this._states;
        final char[] set = this._set;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tCharProcedure.execute(set[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._set[n] = this.no_entry_value;
        super.removeAt(n);
    }
    
    protected int index(final char c) {
        final byte[] states = this._states;
        final char[] set = this._set;
        final int length = states.length;
        final int n = HashFunctions.hash(c) & Integer.MAX_VALUE;
        final int n2 = n % length;
        final byte b = states[n2];
        if (b == 0) {
            return -1;
        }
        if (b == 1 && set[n2] == c) {
            return n2;
        }
        return this.indexRehashed(c, n2, n, b);
    }
    
    int indexRehashed(final char c, int i, final int n, final byte b) {
        final int length = this._set.length;
        final int n2 = 1 + n % (length - 2);
        do {
            i -= n2;
            if (i < 0) {
                i += length;
            }
            final byte b2 = this._states[i];
            if (b2 == 0) {
                return -1;
            }
            if (c == this._set[i] && b2 != 2) {
                return i;
            }
        } while (i != i);
        return -1;
    }
    
    protected int insertKey(final char c) {
        final int n = HashFunctions.hash(c) & Integer.MAX_VALUE;
        final int n2 = n % this._states.length;
        final byte b = this._states[n2];
        this.consumeFreeSlot = false;
        if (b == 0) {
            this.consumeFreeSlot = true;
            this.insertKeyAt(n2, c);
            return n2;
        }
        if (b == 1 && this._set[n2] == c) {
            return -n2 - 1;
        }
        return this.insertKeyRehash(c, n2, n, b);
    }
    
    int insertKeyRehash(final char c, int i, final int n, byte b) {
        final int length = this._set.length;
        final int n2 = 1 + n % (length - 2);
        do {
            if (b == 2) {}
            i -= n2;
            if (i < 0) {
                i += length;
            }
            b = this._states[i];
            if (b == 0) {
                this.consumeFreeSlot = true;
                this.insertKeyAt(i, c);
                return i;
            }
            if (b == 1 && this._set[i] == c) {
                return -i - 1;
            }
        } while (i != i);
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    void insertKeyAt(final int n, final char c) {
        this._set[n] = c;
        this._states[n] = 1;
    }
}
