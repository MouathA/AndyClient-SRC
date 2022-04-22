package optifine;

public class IntegerCache
{
    private static final Integer[] cache;
    
    static {
        cache = makeCache(4096);
    }
    
    private static Integer[] makeCache(final int n) {
        final Integer[] array = new Integer[n];
        while (0 < n) {
            array[0] = new Integer(0);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    public static Integer valueOf(final int n) {
        return (n >= 0 && n < 4096) ? IntegerCache.cache[n] : new Integer(n);
    }
}
