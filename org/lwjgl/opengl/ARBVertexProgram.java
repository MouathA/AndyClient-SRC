package org.lwjgl.opengl;

import java.nio.*;

public final class ARBVertexProgram extends ARBProgram
{
    public static final int GL_VERTEX_PROGRAM_ARB = 34336;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
    public static final int GL_COLOR_SUM_ARB = 33880;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
    public static final int GL_PROGRAM_ADDRESS_REGISTERS_ARB = 34992;
    public static final int GL_MAX_PROGRAM_ADDRESS_REGISTERS_ARB = 34993;
    public static final int GL_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34994;
    public static final int GL_MAX_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34995;
    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
    
    private ARBVertexProgram() {
    }
    
    public static void glVertexAttrib1sARB(final int n, final short n2) {
        ARBVertexShader.glVertexAttrib1sARB(n, n2);
    }
    
    public static void glVertexAttrib1fARB(final int n, final float n2) {
        ARBVertexShader.glVertexAttrib1fARB(n, n2);
    }
    
    public static void glVertexAttrib1dARB(final int n, final double n2) {
        ARBVertexShader.glVertexAttrib1dARB(n, n2);
    }
    
    public static void glVertexAttrib2sARB(final int n, final short n2, final short n3) {
        ARBVertexShader.glVertexAttrib2sARB(n, n2, n3);
    }
    
    public static void glVertexAttrib2fARB(final int n, final float n2, final float n3) {
        ARBVertexShader.glVertexAttrib2fARB(n, n2, n3);
    }
    
    public static void glVertexAttrib2dARB(final int n, final double n2, final double n3) {
        ARBVertexShader.glVertexAttrib2dARB(n, n2, n3);
    }
    
    public static void glVertexAttrib3sARB(final int n, final short n2, final short n3, final short n4) {
        ARBVertexShader.glVertexAttrib3sARB(n, n2, n3, n4);
    }
    
    public static void glVertexAttrib3fARB(final int n, final float n2, final float n3, final float n4) {
        ARBVertexShader.glVertexAttrib3fARB(n, n2, n3, n4);
    }
    
    public static void glVertexAttrib3dARB(final int n, final double n2, final double n3, final double n4) {
        ARBVertexShader.glVertexAttrib3dARB(n, n2, n3, n4);
    }
    
    public static void glVertexAttrib4sARB(final int n, final short n2, final short n3, final short n4, final short n5) {
        ARBVertexShader.glVertexAttrib4sARB(n, n2, n3, n4, n5);
    }
    
    public static void glVertexAttrib4fARB(final int n, final float n2, final float n3, final float n4, final float n5) {
        ARBVertexShader.glVertexAttrib4fARB(n, n2, n3, n4, n5);
    }
    
    public static void glVertexAttrib4dARB(final int n, final double n2, final double n3, final double n4, final double n5) {
        ARBVertexShader.glVertexAttrib4dARB(n, n2, n3, n4, n5);
    }
    
    public static void glVertexAttrib4NubARB(final int n, final byte b, final byte b2, final byte b3, final byte b4) {
        ARBVertexShader.glVertexAttrib4NubARB(n, b, b2, b3, b4);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final int n3, final DoubleBuffer doubleBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, b, n3, doubleBuffer);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final int n3, final FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, b, n3, floatBuffer);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, b, b2, n3, byteBuffer);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, b, b2, n3, intBuffer);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, b, b2, n3, shortBuffer);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, b, n4, n5);
    }
    
    public static void glEnableVertexAttribArrayARB(final int n) {
        ARBVertexShader.glEnableVertexAttribArrayARB(n);
    }
    
    public static void glDisableVertexAttribArrayARB(final int n) {
        ARBVertexShader.glDisableVertexAttribArrayARB(n);
    }
    
    public static void glGetVertexAttribARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        ARBVertexShader.glGetVertexAttribARB(n, n2, floatBuffer);
    }
    
    public static void glGetVertexAttribARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        ARBVertexShader.glGetVertexAttribARB(n, n2, doubleBuffer);
    }
    
    public static void glGetVertexAttribARB(final int n, final int n2, final IntBuffer intBuffer) {
        ARBVertexShader.glGetVertexAttribARB(n, n2, intBuffer);
    }
    
    public static ByteBuffer glGetVertexAttribPointerARB(final int n, final int n2, final long n3) {
        return ARBVertexShader.glGetVertexAttribPointerARB(n, n2, n3);
    }
}
