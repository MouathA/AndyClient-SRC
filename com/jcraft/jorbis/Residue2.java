package com.jcraft.jorbis;

class Residue2 extends Residue0
{
    @Override
    int inverse(final Block block, final Object o, final float[][] array, final int[] array2, final int n) {
        while (0 < n && array2[0] == 0) {
            int n2 = 0;
            ++n2;
        }
        if (0 == n) {
            return 0;
        }
        return Residue0._2inverse(block, o, array, n);
    }
}
