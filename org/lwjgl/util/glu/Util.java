package org.lwjgl.util.glu;

import java.nio.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class Util
{
    private static IntBuffer scratch;
    
    protected static int ceil(final int n, final int n2) {
        return (n % n2 == 0) ? (n / n2) : (n / n2 + 1);
    }
    
    protected static float[] normalize(final float[] array) {
        final float n = (float)Math.sqrt(array[0] * array[0] + array[1] * array[1] + array[2] * array[2]);
        if (n == 0.0) {
            return array;
        }
        final float n2 = 1.0f / n;
        final int n3 = 0;
        array[n3] *= n2;
        final int n4 = 1;
        array[n4] *= n2;
        final int n5 = 2;
        array[n5] *= n2;
        return array;
    }
    
    protected static void cross(final float[] array, final float[] array2, final float[] array3) {
        array3[0] = array[1] * array2[2] - array[2] * array2[1];
        array3[1] = array[2] * array2[0] - array[0] * array2[2];
        array3[2] = array[0] * array2[1] - array[1] * array2[0];
    }
    
    protected static int compPerPix(final int n) {
        switch (n) {
            case 6400:
            case 6401:
            case 6402:
            case 6403:
            case 6404:
            case 6405:
            case 6406:
            case 6409: {
                return 1;
            }
            case 6410: {
                return 2;
            }
            case 6407:
            case 32992: {
                return 3;
            }
            case 6408:
            case 32993: {
                return 4;
            }
            default: {
                return -1;
            }
        }
    }
    
    protected static int nearestPower(int i) {
        if (i == 0) {
            return -1;
        }
        while (i != 1) {
            if (i == 3) {
                return 4;
            }
            i >>= 1;
        }
        return 1;
    }
    
    protected static int bytesPerPixel(final int n, final int n2) {
        switch (n) {
            case 6400:
            case 6401:
            case 6402:
            case 6403:
            case 6404:
            case 6405:
            case 6406:
            case 6409: {}
            case 6410: {}
            case 6407:
            case 32992: {}
        }
        switch (n2) {
            case 5121: {}
            case 5120: {}
            case 6656: {}
            case 5123: {}
            case 5122: {}
            case 5125: {}
            case 5124: {}
        }
        return 0;
    }
    
    protected static int glGetIntegerv(final int n) {
        Util.scratch.rewind();
        GL11.glGetInteger(n, Util.scratch);
        return Util.scratch.get();
    }
    
    static {
        Util.scratch = BufferUtils.createIntBuffer(16);
    }
}
