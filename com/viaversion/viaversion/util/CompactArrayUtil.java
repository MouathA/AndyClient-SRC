package com.viaversion.viaversion.util;

import java.util.function.*;

public class CompactArrayUtil
{
    private static final int[] MAGIC;
    
    private CompactArrayUtil() {
        throw new AssertionError();
    }
    
    public static long[] createCompactArrayWithPadding(final int n, final int n2, final IntToLongFunction intToLongFunction) {
        final long n3 = (1L << n) - 1L;
        final char c = (char)(64 / n);
        final int n4 = 3 * (c - '\u0001');
        final long unsignedLong = Integer.toUnsignedLong(CompactArrayUtil.MAGIC[n4]);
        final long unsignedLong2 = Integer.toUnsignedLong(CompactArrayUtil.MAGIC[n4 + 1]);
        final int n5 = CompactArrayUtil.MAGIC[n4 + 2];
        final long[] array = new long[(n2 + c - 1) / c];
        for (int i = 0; i < n2; ++i) {
            final long applyAsLong = intToLongFunction.applyAsLong(i);
            final int n6 = (int)(i * unsignedLong + unsignedLong2 >> 32 >> n5);
            final int n7 = (i - n6 * c) * n;
            array[n6] = ((array[n6] & ~(n3 << n7)) | (applyAsLong & n3) << n7);
        }
        return array;
    }
    
    public static void iterateCompactArrayWithPadding(final int n, final int n2, final long[] array, final BiIntConsumer biIntConsumer) {
        final long n3 = (1L << n) - 1L;
        final char c = (char)(64 / n);
        final int n4 = 3 * (c - '\u0001');
        final long unsignedLong = Integer.toUnsignedLong(CompactArrayUtil.MAGIC[n4]);
        final long unsignedLong2 = Integer.toUnsignedLong(CompactArrayUtil.MAGIC[n4 + 1]);
        final int n5 = CompactArrayUtil.MAGIC[n4 + 2];
        for (int i = 0; i < n2; ++i) {
            final int n6 = (int)(i * unsignedLong + unsignedLong2 >> 32 >> n5);
            biIntConsumer.consume(i, (int)(array[n6] >> (i - n6 * c) * n & n3));
        }
    }
    
    public static long[] createCompactArray(final int n, final int n2, final IntToLongFunction intToLongFunction) {
        final long n3 = (1L << n) - 1L;
        final long[] array = new long[(int)Math.ceil(n2 * n / 64.0)];
        for (int i = 0; i < n2; ++i) {
            final long applyAsLong = intToLongFunction.applyAsLong(i);
            final int n4 = i * n;
            final int n5 = n4 / 64;
            final int n6 = ((i + 1) * n - 1) / 64;
            final int n7 = n4 % 64;
            array[n5] = ((array[n5] & ~(n3 << n7)) | (applyAsLong & n3) << n7);
            if (n5 != n6) {
                final int n8 = 64 - n7;
                array[n6] = (array[n6] >>> n8 << n8 | (applyAsLong & n3) >> n8);
            }
        }
        return array;
    }
    
    public static void iterateCompactArray(final int n, final int n2, final long[] array, final BiIntConsumer biIntConsumer) {
        final long n3 = (1L << n) - 1L;
        for (int i = 0; i < n2; ++i) {
            final int n4 = i * n;
            final int n5 = n4 / 64;
            final int n6 = ((i + 1) * n - 1) / 64;
            final int n7 = n4 % 64;
            int n8;
            if (n5 == n6) {
                n8 = (int)(array[n5] >>> n7 & n3);
            }
            else {
                n8 = (int)((array[n5] >>> n7 | array[n6] << 64 - n7) & n3);
            }
            biIntConsumer.consume(i, n8);
        }
    }
    
    static {
        MAGIC = new int[] { -1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0, 390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0, 204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0, 104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5 };
    }
}
