package gnu.trove.impl.hash;

import gnu.trove.procedure.*;
import gnu.trove.impl.*;
import java.io.*;

public abstract class TIntIntHash extends TPrimitiveHash
{
    static final long serialVersionUID = 1L;
    public transient int[] _set;
    protected int no_entry_key;
    protected int no_entry_value;
    protected boolean consumeFreeSlot;
    
    public TIntIntHash() {
        this.no_entry_key = 0;
        this.no_entry_value = 0;
    }
    
    public TIntIntHash(final int n) {
        super(n);
        this.no_entry_key = 0;
        this.no_entry_value = 0;
    }
    
    public TIntIntHash(final int n, final float n2) {
        super(n, n2);
        this.no_entry_key = 0;
        this.no_entry_value = 0;
    }
    
    public TIntIntHash(final int n, final float n2, final int no_entry_key, final int no_entry_value) {
        super(n, n2);
        this.no_entry_key = no_entry_key;
        this.no_entry_value = no_entry_value;
    }
    
    public int getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public int getNoEntryValue() {
        return this.no_entry_value;
    }
    
    @Override
    protected int setUp(final int up) {
        final int setUp = super.setUp(up);
        this._set = new int[setUp];
        return setUp;
    }
    
    public boolean contains(final int n) {
        return this.index(n) >= 0;
    }
    
    public boolean forEach(final TIntProcedure tIntProcedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        int length = set.length;
        while (length-- > 0) {
            if (states[length] == 1 && !tIntProcedure.execute(set[length])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._set[n] = this.no_entry_key;
        super.removeAt(n);
    }
    
    protected int index(final int n) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int length = states.length;
        final int n2 = HashFunctions.hash(n) & Integer.MAX_VALUE;
        final int n3 = n2 % length;
        final byte b = states[n3];
        if (b == 0) {
            return -1;
        }
        if (b == 1 && set[n3] == n) {
            return n3;
        }
        return this.indexRehashed(n, n3, n2, b);
    }
    
    int indexRehashed(final int n, int i, final int n2, final byte b) {
        final int length = this._set.length;
        final int n3 = 1 + n2 % (length - 2);
        do {
            i -= n3;
            if (i < 0) {
                i += length;
            }
            final byte b2 = this._states[i];
            if (b2 == 0) {
                return -1;
            }
            if (n == this._set[i] && b2 != 2) {
                return i;
            }
        } while (i != i);
        return -1;
    }
    
    protected int insertKey(final int n) {
        final int n2 = HashFunctions.hash(n) & Integer.MAX_VALUE;
        final int n3 = n2 % this._states.length;
        final byte b = this._states[n3];
        this.consumeFreeSlot = false;
        if (b == 0) {
            this.consumeFreeSlot = true;
            this.insertKeyAt(n3, n);
            return n3;
        }
        if (b == 1 && this._set[n3] == n) {
            return -n3 - 1;
        }
        return this.insertKeyRehash(n, n3, n2, b);
    }
    
    int insertKeyRehash(final int n, int i, final int n2, byte b) {
        final int length = this._set.length;
        final int n3 = 1 + n2 % (length - 2);
        do {
            if (b == 2 && -1 == -1) {}
            i -= n3;
            if (i < 0) {
                i += length;
            }
            b = this._states[i];
            if (b == 0) {
                if (-1 != -1) {
                    this.insertKeyAt(-1, n);
                    return -1;
                }
                this.consumeFreeSlot = true;
                this.insertKeyAt(i, n);
                return i;
            }
            else {
                if (b == 1 && this._set[i] == n) {
                    return -i - 1;
                }
                continue;
            }
        } while (i != i);
        if (-1 != -1) {
            this.insertKeyAt(-1, n);
            return -1;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    void insertKeyAt(final int n, final int n2) {
        this._set[n] = n2;
        this._states[n] = 1;
    }
    
    protected int XinsertKey(final int n) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int length = states.length;
        final int n2 = HashFunctions.hash(n) & Integer.MAX_VALUE;
        int n3 = n2 % length;
        byte b = states[n3];
        this.consumeFreeSlot = false;
        if (b == 0) {
            this.consumeFreeSlot = true;
            set[n3] = n;
            states[n3] = 1;
            return n3;
        }
        if (b == 1 && set[n3] == n) {
            return -n3 - 1;
        }
        final int n4 = 1 + n2 % (length - 2);
        if (b != 2) {
            do {
                n3 -= n4;
                if (n3 < 0) {
                    n3 += length;
                }
                b = states[n3];
            } while (b == 1 && set[n3] != n);
        }
        if (b == 2) {
            final int n5 = n3;
            while (b != 0 && (b == 2 || set[n3] != n)) {
                n3 -= n4;
                if (n3 < 0) {
                    n3 += length;
                }
                b = states[n3];
            }
            if (b == 1) {
                return -n3 - 1;
            }
            set[n3] = n;
            states[n3] = 1;
            return n5;
        }
        else {
            if (b == 1) {
                return -n3 - 1;
            }
            this.consumeFreeSlot = true;
            set[n3] = n;
            states[n3] = 1;
            return n3;
        }
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
        objectOutput.writeInt(this.no_entry_key);
        objectOutput.writeInt(this.no_entry_value);
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
        this.no_entry_key = objectInput.readInt();
        this.no_entry_value = objectInput.readInt();
    }
}
