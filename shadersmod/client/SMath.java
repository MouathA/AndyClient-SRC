package shadersmod.client;

import java.util.*;
import java.nio.*;

public class SMath
{
    static void multiplyMat4xMat4(final float[] array, final float[] array2, final float[] array3) {
        while (0 < 4) {
            while (0 < 4) {
                array[0] = array2[0] * array3[0] + array2[1] * array3[4] + array2[2] * array3[8] + array2[3] * array3[12];
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    static void multiplyMat4xVec4(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] * array3[0] + array2[4] * array3[1] + array2[8] * array3[2] + array2[12] * array3[3];
        array[1] = array2[1] * array3[0] + array2[5] * array3[1] + array2[9] * array3[2] + array2[13] * array3[3];
        array[2] = array2[2] * array3[0] + array2[6] * array3[1] + array2[10] * array3[2] + array2[14] * array3[3];
        array[3] = array2[3] * array3[0] + array2[7] * array3[1] + array2[11] * array3[2] + array2[15] * array3[3];
    }
    
    static void invertMat4(final float[] array, final float[] array2) {
        array[0] = array2[5] * array2[10] * array2[15] - array2[5] * array2[11] * array2[14] - array2[9] * array2[6] * array2[15] + array2[9] * array2[7] * array2[14] + array2[13] * array2[6] * array2[11] - array2[13] * array2[7] * array2[10];
        array[1] = -array2[1] * array2[10] * array2[15] + array2[1] * array2[11] * array2[14] + array2[9] * array2[2] * array2[15] - array2[9] * array2[3] * array2[14] - array2[13] * array2[2] * array2[11] + array2[13] * array2[3] * array2[10];
        array[2] = array2[1] * array2[6] * array2[15] - array2[1] * array2[7] * array2[14] - array2[5] * array2[2] * array2[15] + array2[5] * array2[3] * array2[14] + array2[13] * array2[2] * array2[7] - array2[13] * array2[3] * array2[6];
        array[3] = -array2[1] * array2[6] * array2[11] + array2[1] * array2[7] * array2[10] + array2[5] * array2[2] * array2[11] - array2[5] * array2[3] * array2[10] - array2[9] * array2[2] * array2[7] + array2[9] * array2[3] * array2[6];
        array[4] = -array2[4] * array2[10] * array2[15] + array2[4] * array2[11] * array2[14] + array2[8] * array2[6] * array2[15] - array2[8] * array2[7] * array2[14] - array2[12] * array2[6] * array2[11] + array2[12] * array2[7] * array2[10];
        array[5] = array2[0] * array2[10] * array2[15] - array2[0] * array2[11] * array2[14] - array2[8] * array2[2] * array2[15] + array2[8] * array2[3] * array2[14] + array2[12] * array2[2] * array2[11] - array2[12] * array2[3] * array2[10];
        array[6] = -array2[0] * array2[6] * array2[15] + array2[0] * array2[7] * array2[14] + array2[4] * array2[2] * array2[15] - array2[4] * array2[3] * array2[14] - array2[12] * array2[2] * array2[7] + array2[12] * array2[3] * array2[6];
        array[7] = array2[0] * array2[6] * array2[11] - array2[0] * array2[7] * array2[10] - array2[4] * array2[2] * array2[11] + array2[4] * array2[3] * array2[10] + array2[8] * array2[2] * array2[7] - array2[8] * array2[3] * array2[6];
        array[8] = array2[4] * array2[9] * array2[15] - array2[4] * array2[11] * array2[13] - array2[8] * array2[5] * array2[15] + array2[8] * array2[7] * array2[13] + array2[12] * array2[5] * array2[11] - array2[12] * array2[7] * array2[9];
        array[9] = -array2[0] * array2[9] * array2[15] + array2[0] * array2[11] * array2[13] + array2[8] * array2[1] * array2[15] - array2[8] * array2[3] * array2[13] - array2[12] * array2[1] * array2[11] + array2[12] * array2[3] * array2[9];
        array[10] = array2[0] * array2[5] * array2[15] - array2[0] * array2[7] * array2[13] - array2[4] * array2[1] * array2[15] + array2[4] * array2[3] * array2[13] + array2[12] * array2[1] * array2[7] - array2[12] * array2[3] * array2[5];
        array[11] = -array2[0] * array2[5] * array2[11] + array2[0] * array2[7] * array2[9] + array2[4] * array2[1] * array2[11] - array2[4] * array2[3] * array2[9] - array2[8] * array2[1] * array2[7] + array2[8] * array2[3] * array2[5];
        array[12] = -array2[4] * array2[9] * array2[14] + array2[4] * array2[10] * array2[13] + array2[8] * array2[5] * array2[14] - array2[8] * array2[6] * array2[13] - array2[12] * array2[5] * array2[10] + array2[12] * array2[6] * array2[9];
        array[13] = array2[0] * array2[9] * array2[14] - array2[0] * array2[10] * array2[13] - array2[8] * array2[1] * array2[14] + array2[8] * array2[2] * array2[13] + array2[12] * array2[1] * array2[10] - array2[12] * array2[2] * array2[9];
        array[14] = -array2[0] * array2[5] * array2[14] + array2[0] * array2[6] * array2[13] + array2[4] * array2[1] * array2[14] - array2[4] * array2[2] * array2[13] - array2[12] * array2[1] * array2[6] + array2[12] * array2[2] * array2[5];
        array[15] = array2[0] * array2[5] * array2[10] - array2[0] * array2[6] * array2[9] - array2[4] * array2[1] * array2[10] + array2[4] * array2[2] * array2[9] + array2[8] * array2[1] * array2[6] - array2[8] * array2[2] * array2[5];
        final float n = array2[0] * array[0] + array2[1] * array[4] + array2[2] * array[8] + array2[3] * array[12];
        if (n != 0.0) {
            while (0 < 16) {
                final int n2 = 0;
                array[n2] /= n;
                int n3 = 0;
                ++n3;
            }
        }
        else {
            Arrays.fill(array, 0.0f);
        }
    }
    
    static void invertMat4FBFA(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final float[] array, final float[] array2) {
        floatBuffer2.get(array2);
        invertMat4(array, array2);
        floatBuffer.put(array);
    }
}
