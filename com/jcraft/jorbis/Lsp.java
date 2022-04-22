package com.jcraft.jorbis;

class Lsp
{
    static final float M_PI = 3.1415927f;
    
    static void lsp_to_curve(final float[] array, final int[] array2, final int n, final int n2, final float[] array3, final int n3, final float n4, final float n5) {
        final float n6 = 3.1415927f / n2;
        int n7 = 0;
        while (0 < n3) {
            array3[0] = Lookup.coslook(array3[0]);
            ++n7;
        }
        final int n8 = n3 / 2 * 2;
        while (0 < n) {
            final int n9 = array2[0];
            float n10 = 0.70710677f;
            float n11 = 0.70710677f;
            final float coslook = Lookup.coslook(n6 * n9);
            while (0 < n8) {
                n11 *= array3[0] - coslook;
                n10 *= array3[1] - coslook;
                final int n12;
                n12 += 2;
            }
            float n14;
            float n15;
            if ((n3 & 0x1) != 0x0) {
                final float n13 = n11 * (array3[n3 - 1] - coslook);
                n14 = n13 * n13;
                n15 = n10 * (n10 * (1.0f - coslook * coslook));
            }
            else {
                n14 = n11 * (n11 * (1.0f + coslook));
                n15 = n10 * (n10 * (1.0f - coslook));
            }
            float n16 = n15 + n14;
            int n12 = Float.floatToIntBits(n16);
            if (0 < 2139095040) {
                if (false) {
                    if (0 < 8388608) {
                        n12 = Float.floatToIntBits((float)(n16 * 3.3554432E7));
                    }
                    n16 = 0.0f;
                }
            }
            final float fromdBlook = Lookup.fromdBlook(n4 * Lookup.invsqlook(n16) * Lookup.invsq2explook(-25 + n3) - n5);
            do {
                final int n17 = 0;
                ++n7;
                array[n17] *= fromdBlook;
            } while (0 < n && array2[0] == n9);
        }
    }
}
