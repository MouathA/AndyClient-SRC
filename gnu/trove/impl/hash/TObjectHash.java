package gnu.trove.impl.hash;

import gnu.trove.procedure.*;
import java.util.*;
import java.io.*;

public abstract class TObjectHash extends THash
{
    static final long serialVersionUID = -3461112548087185871L;
    public transient Object[] _set;
    public static final Object REMOVED;
    public static final Object FREE;
    protected boolean consumeFreeSlot;
    
    static {
        REMOVED = new Object();
        FREE = new Object();
    }
    
    public TObjectHash() {
    }
    
    public TObjectHash(final int n) {
        super(n);
    }
    
    public TObjectHash(final int n, final float n2) {
        super(n, n2);
    }
    
    @Override
    public int capacity() {
        return this._set.length;
    }
    
    @Override
    protected void removeAt(final int n) {
        this._set[n] = TObjectHash.REMOVED;
        super.removeAt(n);
    }
    
    public int setUp(final int up) {
        final int setUp = super.setUp(up);
        Arrays.fill(this._set = new Object[setUp], TObjectHash.FREE);
        return setUp;
    }
    
    public boolean forEach(final TObjectProcedure tObjectProcedure) {
        final Object[] set = this._set;
        int length = set.length;
        while (length-- > 0) {
            if (set[length] != TObjectHash.FREE && set[length] != TObjectHash.REMOVED && !tObjectProcedure.execute(set[length])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contains(final Object o) {
        return this.index(o) >= 0;
    }
    
    protected int index(final Object o) {
        if (o == null) {
            return this.indexForNull();
        }
        final int n = this.hash(o) & Integer.MAX_VALUE;
        final int n2 = n % this._set.length;
        final Object o2 = this._set[n2];
        if (o2 == TObjectHash.FREE) {
            return -1;
        }
        if (o2 == o || this.equals(o, o2)) {
            return n2;
        }
        return this.indexRehashed(o, n2, n, o2);
    }
    
    private int indexRehashed(final Object o, int i, final int n, Object o2) {
        final Object[] set = this._set;
        final int length = set.length;
        final int n2 = 1 + n % (length - 2);
        do {
            i -= n2;
            if (i < 0) {
                i += length;
            }
            o2 = set[i];
            if (o2 == TObjectHash.FREE) {
                return -1;
            }
            if (o2 == o || this.equals(o, o2)) {
                return i;
            }
        } while (i != i);
        return -1;
    }
    
    private int indexForNull() {
        Object[] set;
        while (0 < (set = this._set).length) {
            final Object o = set[0];
            if (o == null) {
                return 0;
            }
            if (o == TObjectHash.FREE) {
                return -1;
            }
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    @Deprecated
    protected int insertionIndex(final Object o) {
        return this.insertKey(o);
    }
    
    protected int insertKey(final Object o) {
        this.consumeFreeSlot = false;
        if (o == null) {
            return this.insertKeyForNull();
        }
        final int n = this.hash(o) & Integer.MAX_VALUE;
        final int n2 = n % this._set.length;
        final Object o2 = this._set[n2];
        if (o2 == TObjectHash.FREE) {
            this.consumeFreeSlot = true;
            this._set[n2] = o;
            return n2;
        }
        if (o2 == o || this.equals(o, o2)) {
            return -n2 - 1;
        }
        return this.insertKeyRehash(o, n2, n, o2);
    }
    
    private int insertKeyRehash(final Object o, int i, final int n, Object o2) {
        final Object[] set = this._set;
        final int length = set.length;
        final int n2 = 1 + n % (length - 2);
        do {
            if (o2 == TObjectHash.REMOVED && -1 == -1) {}
            i -= n2;
            if (i < 0) {
                i += length;
            }
            o2 = set[i];
            if (o2 == TObjectHash.FREE) {
                if (-1 != -1) {
                    this._set[-1] = o;
                    return -1;
                }
                this.consumeFreeSlot = true;
                this._set[i] = o;
                return i;
            }
            else {
                if (o2 == o || this.equals(o, o2)) {
                    return -i - 1;
                }
                continue;
            }
        } while (i != i);
        if (-1 != -1) {
            this._set[-1] = o;
            return -1;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    private int insertKeyForNull() {
        Object[] set;
        while (0 < (set = this._set).length) {
            final Object o = set[0];
            if (o != TObjectHash.REMOVED || -1 == -1) {}
            if (o == TObjectHash.FREE) {
                if (-1 != -1) {
                    this._set[-1] = null;
                    return -1;
                }
                this.consumeFreeSlot = true;
                this._set[0] = null;
                return 0;
            }
            else {
                if (o == null) {
                    return -1;
                }
                int n = 0;
                ++n;
                int n2 = 0;
                ++n2;
            }
        }
        if (-1 != -1) {
            this._set[-1] = null;
            return -1;
        }
        throw new IllegalStateException("Could not find insertion index for null key. Key set full!?!!");
    }
    
    protected final void throwObjectContractViolation(final Object o, final Object o2) throws IllegalArgumentException {
        throw this.buildObjectContractViolation(o, o2, "");
    }
    
    protected final void throwObjectContractViolation(final Object o, final Object o2, final int n, final int n2, final Object[] array) throws IllegalArgumentException {
        throw this.buildObjectContractViolation(o, o2, this.dumpExtraInfo(o, o2, this.size(), n2, array));
    }
    
    protected final IllegalArgumentException buildObjectContractViolation(final Object o, final Object o2, final String s) {
        return new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + objectInfo(o) + "; object #2 =" + objectInfo(o2) + "\n" + s);
    }
    
    protected boolean equals(final Object o, final Object o2) {
        return o2 != null && o2 != TObjectHash.REMOVED && o.equals(o2);
    }
    
    protected int hash(final Object o) {
        return o.hashCode();
    }
    
    protected static String reportPotentialConcurrentMod(final int n, final int n2) {
        if (n != n2) {
            return "[Warning] apparent concurrent modification of the key set. Size before and after rehash() do not match " + n2 + " vs " + n;
        }
        return "";
    }
    
    protected String dumpExtraInfo(final Object o, final Object o2, final int n, final int n2, final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.dumpKeyTypes(o, o2));
        sb.append(reportPotentialConcurrentMod(n, n2));
        sb.append(detectKeyLoss(array, n2));
        if (o == o2) {
            sb.append("Inserting same object twice, rehashing bug. Object= ").append(o2);
        }
        return sb.toString();
    }
    
    private static String detectKeyLoss(final Object[] array, final int n) {
        final StringBuilder sb = new StringBuilder();
        final Set keySet = makeKeySet(array);
        if (keySet.size() != n) {
            sb.append("\nhashCode() and/or equals() have inconsistent implementation");
            sb.append("\nKey set lost entries, now got ").append(keySet.size()).append(" instead of ").append(n);
            sb.append(". This can manifest itself as an apparent duplicate key.");
        }
        return sb.toString();
    }
    
    private static Set makeKeySet(final Object[] array) {
        final HashSet<Object> set = new HashSet<Object>();
        while (0 < array.length) {
            final Object o = array[0];
            if (o != TObjectHash.FREE && o != TObjectHash.REMOVED) {
                set.add(o);
            }
            int n = 0;
            ++n;
        }
        return set;
    }
    
    private static String equalsSymmetryInfo(final Object o, final Object o2) {
        final StringBuilder sb = new StringBuilder();
        if (o == o2) {
            return "a == b";
        }
        if (o.getClass() != o2.getClass()) {
            sb.append("Class of objects differ a=").append(o.getClass()).append(" vs b=").append(o2.getClass());
            final boolean equals = o.equals(o2);
            final boolean equals2 = o2.equals(o);
            if (equals != equals2) {
                sb.append("\nequals() of a or b object are asymmetric");
                sb.append("\na.equals(b) =").append(equals);
                sb.append("\nb.equals(a) =").append(equals2);
            }
        }
        return sb.toString();
    }
    
    protected static String objectInfo(final Object o) {
        return ((o == null) ? "class null" : o.getClass()) + " id= " + System.identityHashCode(o) + " hashCode= " + ((o == null) ? 0 : o.hashCode()) + " toString= " + String.valueOf(o);
    }
    
    private String dumpKeyTypes(final Object o, final Object o2) {
        final StringBuilder sb = new StringBuilder();
        final HashSet<Class<?>> set = new HashSet<Class<?>>();
        Object[] set2;
        while (0 < (set2 = this._set).length) {
            final Object o3 = set2[0];
            if (o3 != TObjectHash.FREE && o3 != TObjectHash.REMOVED) {
                if (o3 != null) {
                    set.add(o3.getClass());
                }
                else {
                    set.add(null);
                }
            }
            int n = 0;
            ++n;
        }
        if (set.size() > 1) {
            sb.append("\nMore than one type used for keys. Watch out for asymmetric equals(). Read about the 'Liskov substitution principle' and the implications for equals() in java.");
            sb.append("\nKey types: ").append(set);
            sb.append(equalsSymmetryInfo(o, o2));
        }
        return sb.toString();
    }
    
    @Override
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(0);
        super.writeExternal(objectOutput);
    }
    
    @Override
    public void readExternal(final ObjectInput objectInput) throws IOException, ClassNotFoundException {
        objectInput.readByte();
        super.readExternal(objectInput);
    }
}
