package gnu.trove.impl;

public final class HashFunctions
{
    static final boolean $assertionsDisabled;
    
    static {
        $assertionsDisabled = !HashFunctions.class.desiredAssertionStatus();
    }
    
    public static int hash(final double n) {
        assert !Double.isNaN(n) : "Values of NaN are not supported.";
        final long doubleToLongBits = Double.doubleToLongBits(n);
        return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    public static int hash(final float n) {
        assert !Float.isNaN(n) : "Values of NaN are not supported.";
        return Float.floatToIntBits(n * 6.6360896E8f);
    }
    
    public static int hash(final int n) {
        return n;
    }
    
    public static int hash(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    public static int hash(final Object o) {
        return (o == null) ? 0 : o.hashCode();
    }
    
    public static int fastCeil(final float n) {
        int n2 = (int)n;
        if (n - n2 > 0.0f) {
            ++n2;
        }
        return n2;
    }
}
