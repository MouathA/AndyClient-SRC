package org.lwjgl.util.glu;

import org.lwjgl.opengl.*;
import java.nio.*;
import org.lwjgl.*;

public class Project extends Util
{
    private static final float[] IDENTITY_MATRIX;
    private static final FloatBuffer matrix;
    private static final FloatBuffer finalMatrix;
    private static final FloatBuffer tempMatrix;
    private static final float[] in;
    private static final float[] out;
    private static final float[] forward;
    private static final float[] side;
    private static final float[] up;
    
    private static void __gluMakeIdentityf(final FloatBuffer floatBuffer) {
        final int position = floatBuffer.position();
        floatBuffer.put(Project.IDENTITY_MATRIX);
        floatBuffer.position(position);
    }
    
    private static void __gluMultMatrixVecf(final FloatBuffer floatBuffer, final float[] array, final float[] array2) {
        while (0 < 4) {
            array2[0] = array[0] * floatBuffer.get(floatBuffer.position() + 0 + 0) + array[1] * floatBuffer.get(floatBuffer.position() + 4 + 0) + array[2] * floatBuffer.get(floatBuffer.position() + 8 + 0) + array[3] * floatBuffer.get(floatBuffer.position() + 12 + 0);
            int n = 0;
            ++n;
        }
    }
    
    private static boolean __gluInvertMatrixf(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final FloatBuffer tempMatrix = Project.tempMatrix;
        int n = 0;
        while (0 < 16) {
            tempMatrix.put(0, floatBuffer.get(0 + floatBuffer.position()));
            ++n;
        }
        __gluMakeIdentityf(floatBuffer2);
        while (0 < 4) {
            int n2 = 0;
            while (0 < 4) {
                if (Math.abs(tempMatrix.get(0)) > Math.abs(tempMatrix.get(0))) {}
                ++n2;
            }
            int n3 = 0;
            if (false) {
                while (0 < 4) {
                    final float value = tempMatrix.get(0);
                    tempMatrix.put(0, tempMatrix.get(0));
                    tempMatrix.put(0, value);
                    final float value2 = floatBuffer2.get(0);
                    floatBuffer2.put(0, floatBuffer2.get(0));
                    floatBuffer2.put(0, value2);
                    ++n3;
                }
            }
            if (tempMatrix.get(0) == 0.0f) {
                return false;
            }
            final float value3 = tempMatrix.get(0);
            while (0 < 4) {
                tempMatrix.put(0, tempMatrix.get(0) / value3);
                floatBuffer2.put(0, floatBuffer2.get(0) / value3);
                ++n3;
            }
            while (0 < 4) {
                if (false) {
                    final float value4 = tempMatrix.get(0);
                    while (0 < 4) {
                        tempMatrix.put(0, tempMatrix.get(0) - tempMatrix.get(0) * value4);
                        floatBuffer2.put(0, floatBuffer2.get(0) - floatBuffer2.get(0) * value4);
                        ++n3;
                    }
                }
                ++n2;
            }
            ++n;
        }
        return true;
    }
    
    private static void __gluMultMatricesf(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final FloatBuffer floatBuffer3) {
        while (0 < 4) {
            while (0 < 4) {
                floatBuffer3.put(floatBuffer3.position() + 0 + 0, floatBuffer.get(floatBuffer.position() + 0 + 0) * floatBuffer2.get(floatBuffer2.position() + 0 + 0) + floatBuffer.get(floatBuffer.position() + 0 + 1) * floatBuffer2.get(floatBuffer2.position() + 4 + 0) + floatBuffer.get(floatBuffer.position() + 0 + 2) * floatBuffer2.get(floatBuffer2.position() + 8 + 0) + floatBuffer.get(floatBuffer.position() + 0 + 3) * floatBuffer2.get(floatBuffer2.position() + 12 + 0));
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public static void gluPerspective(final float n, final float n2, final float n3, final float n4) {
        final float n5 = n / 2.0f * 3.1415927f / 180.0f;
        final float n6 = n4 - n3;
        final float n7 = (float)Math.sin(n5);
        if (n6 == 0.0f || n7 == 0.0f || n2 == 0.0f) {
            return;
        }
        final float n8 = (float)Math.cos(n5) / n7;
        __gluMakeIdentityf(Project.matrix);
        Project.matrix.put(0, n8 / n2);
        Project.matrix.put(5, n8);
        Project.matrix.put(10, -(n4 + n3) / n6);
        Project.matrix.put(11, -1.0f);
        Project.matrix.put(14, -2.0f * n3 * n4 / n6);
        Project.matrix.put(15, 0.0f);
        GL11.glMultMatrix(Project.matrix);
    }
    
    public static void gluLookAt(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        final float[] forward = Project.forward;
        final float[] side = Project.side;
        final float[] up = Project.up;
        forward[0] = n4 - n;
        forward[1] = n5 - n2;
        forward[2] = n6 - n3;
        up[0] = n7;
        up[1] = n8;
        up[2] = n9;
        Util.normalize(forward);
        Util.cross(forward, up, side);
        Util.normalize(side);
        Util.cross(side, forward, up);
        __gluMakeIdentityf(Project.matrix);
        Project.matrix.put(0, side[0]);
        Project.matrix.put(4, side[1]);
        Project.matrix.put(8, side[2]);
        Project.matrix.put(1, up[0]);
        Project.matrix.put(5, up[1]);
        Project.matrix.put(9, up[2]);
        Project.matrix.put(2, -forward[0]);
        Project.matrix.put(6, -forward[1]);
        Project.matrix.put(10, -forward[2]);
        GL11.glMultMatrix(Project.matrix);
        GL11.glTranslatef(-n, -n2, -n3);
    }
    
    public static boolean gluProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        final float[] in = Project.in;
        final float[] out = Project.out;
        in[0] = n;
        in[1] = n2;
        in[2] = n3;
        in[3] = 1.0f;
        __gluMultMatrixVecf(floatBuffer, in, out);
        __gluMultMatrixVecf(floatBuffer2, out, in);
        if (in[3] == 0.0) {
            return false;
        }
        in[3] = 1.0f / in[3] * 0.5f;
        in[0] = in[0] * in[3] + 0.5f;
        in[1] = in[1] * in[3] + 0.5f;
        in[2] = in[2] * in[3] + 0.5f;
        floatBuffer3.put(0, in[0] * intBuffer.get(intBuffer.position() + 2) + intBuffer.get(intBuffer.position() + 0));
        floatBuffer3.put(1, in[1] * intBuffer.get(intBuffer.position() + 3) + intBuffer.get(intBuffer.position() + 1));
        floatBuffer3.put(2, in[2]);
        return true;
    }
    
    public static boolean gluUnProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        final float[] in = Project.in;
        final float[] out = Project.out;
        __gluMultMatricesf(floatBuffer, floatBuffer2, Project.finalMatrix);
        if (!__gluInvertMatrixf(Project.finalMatrix, Project.finalMatrix)) {
            return false;
        }
        in[0] = n;
        in[1] = n2;
        in[2] = n3;
        in[3] = 1.0f;
        in[0] = (in[0] - intBuffer.get(intBuffer.position() + 0)) / intBuffer.get(intBuffer.position() + 2);
        in[1] = (in[1] - intBuffer.get(intBuffer.position() + 1)) / intBuffer.get(intBuffer.position() + 3);
        in[0] = in[0] * 2.0f - 1.0f;
        in[1] = in[1] * 2.0f - 1.0f;
        in[2] = in[2] * 2.0f - 1.0f;
        __gluMultMatrixVecf(Project.finalMatrix, in, out);
        if (out[3] == 0.0) {
            return false;
        }
        out[3] = 1.0f / out[3];
        floatBuffer3.put(floatBuffer3.position() + 0, out[0] * out[3]);
        floatBuffer3.put(floatBuffer3.position() + 1, out[1] * out[3]);
        floatBuffer3.put(floatBuffer3.position() + 2, out[2] * out[3]);
        return true;
    }
    
    public static void gluPickMatrix(final float n, final float n2, final float n3, final float n4, final IntBuffer intBuffer) {
        if (n3 <= 0.0f || n4 <= 0.0f) {
            return;
        }
        GL11.glTranslatef((intBuffer.get(intBuffer.position() + 2) - 2.0f * (n - intBuffer.get(intBuffer.position() + 0))) / n3, (intBuffer.get(intBuffer.position() + 3) - 2.0f * (n2 - intBuffer.get(intBuffer.position() + 1))) / n4, 0.0f);
        GL11.glScalef(intBuffer.get(intBuffer.position() + 2) / n3, intBuffer.get(intBuffer.position() + 3) / n4, 1.0f);
    }
    
    static {
        IDENTITY_MATRIX = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        matrix = BufferUtils.createFloatBuffer(16);
        finalMatrix = BufferUtils.createFloatBuffer(16);
        tempMatrix = BufferUtils.createFloatBuffer(16);
        in = new float[4];
        out = new float[4];
        forward = new float[3];
        side = new float[3];
        up = new float[3];
    }
}
