package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBVertexShader
{
    public static final int GL_VERTEX_SHADER_ARB = 35633;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS_ARB = 35658;
    public static final int GL_MAX_VARYING_FLOATS_ARB = 35659;
    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 34930;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS_ARB = 35660;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS_ARB = 35661;
    public static final int GL_MAX_TEXTURE_COORDS_ARB = 34929;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
    public static final int GL_OBJECT_ACTIVE_ATTRIBUTES_ARB = 35721;
    public static final int GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB = 35722;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
    public static final int GL_FLOAT_VEC2_ARB = 35664;
    public static final int GL_FLOAT_VEC3_ARB = 35665;
    public static final int GL_FLOAT_VEC4_ARB = 35666;
    public static final int GL_FLOAT_MAT2_ARB = 35674;
    public static final int GL_FLOAT_MAT3_ARB = 35675;
    public static final int GL_FLOAT_MAT4_ARB = 35676;
    
    private ARBVertexShader() {
    }
    
    public static void glVertexAttrib1sARB(final int n, final short n2) {
        final long glVertexAttrib1sARB = GLContext.getCapabilities().glVertexAttrib1sARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib1sARB);
        nglVertexAttrib1sARB(n, n2, glVertexAttrib1sARB);
    }
    
    static native void nglVertexAttrib1sARB(final int p0, final short p1, final long p2);
    
    public static void glVertexAttrib1fARB(final int n, final float n2) {
        final long glVertexAttrib1fARB = GLContext.getCapabilities().glVertexAttrib1fARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib1fARB);
        nglVertexAttrib1fARB(n, n2, glVertexAttrib1fARB);
    }
    
    static native void nglVertexAttrib1fARB(final int p0, final float p1, final long p2);
    
    public static void glVertexAttrib1dARB(final int n, final double n2) {
        final long glVertexAttrib1dARB = GLContext.getCapabilities().glVertexAttrib1dARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib1dARB);
        nglVertexAttrib1dARB(n, n2, glVertexAttrib1dARB);
    }
    
    static native void nglVertexAttrib1dARB(final int p0, final double p1, final long p2);
    
    public static void glVertexAttrib2sARB(final int n, final short n2, final short n3) {
        final long glVertexAttrib2sARB = GLContext.getCapabilities().glVertexAttrib2sARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib2sARB);
        nglVertexAttrib2sARB(n, n2, n3, glVertexAttrib2sARB);
    }
    
    static native void nglVertexAttrib2sARB(final int p0, final short p1, final short p2, final long p3);
    
    public static void glVertexAttrib2fARB(final int n, final float n2, final float n3) {
        final long glVertexAttrib2fARB = GLContext.getCapabilities().glVertexAttrib2fARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib2fARB);
        nglVertexAttrib2fARB(n, n2, n3, glVertexAttrib2fARB);
    }
    
    static native void nglVertexAttrib2fARB(final int p0, final float p1, final float p2, final long p3);
    
    public static void glVertexAttrib2dARB(final int n, final double n2, final double n3) {
        final long glVertexAttrib2dARB = GLContext.getCapabilities().glVertexAttrib2dARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib2dARB);
        nglVertexAttrib2dARB(n, n2, n3, glVertexAttrib2dARB);
    }
    
    static native void nglVertexAttrib2dARB(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexAttrib3sARB(final int n, final short n2, final short n3, final short n4) {
        final long glVertexAttrib3sARB = GLContext.getCapabilities().glVertexAttrib3sARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib3sARB);
        nglVertexAttrib3sARB(n, n2, n3, n4, glVertexAttrib3sARB);
    }
    
    static native void nglVertexAttrib3sARB(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glVertexAttrib3fARB(final int n, final float n2, final float n3, final float n4) {
        final long glVertexAttrib3fARB = GLContext.getCapabilities().glVertexAttrib3fARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib3fARB);
        nglVertexAttrib3fARB(n, n2, n3, n4, glVertexAttrib3fARB);
    }
    
    static native void nglVertexAttrib3fARB(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glVertexAttrib3dARB(final int n, final double n2, final double n3, final double n4) {
        final long glVertexAttrib3dARB = GLContext.getCapabilities().glVertexAttrib3dARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib3dARB);
        nglVertexAttrib3dARB(n, n2, n3, n4, glVertexAttrib3dARB);
    }
    
    static native void nglVertexAttrib3dARB(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexAttrib4sARB(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glVertexAttrib4sARB = GLContext.getCapabilities().glVertexAttrib4sARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib4sARB);
        nglVertexAttrib4sARB(n, n2, n3, n4, n5, glVertexAttrib4sARB);
    }
    
    static native void nglVertexAttrib4sARB(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glVertexAttrib4fARB(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glVertexAttrib4fARB = GLContext.getCapabilities().glVertexAttrib4fARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib4fARB);
        nglVertexAttrib4fARB(n, n2, n3, n4, n5, glVertexAttrib4fARB);
    }
    
    static native void nglVertexAttrib4fARB(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glVertexAttrib4dARB(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexAttrib4dARB = GLContext.getCapabilities().glVertexAttrib4dARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib4dARB);
        nglVertexAttrib4dARB(n, n2, n3, n4, n5, glVertexAttrib4dARB);
    }
    
    static native void nglVertexAttrib4dARB(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexAttrib4NubARB(final int n, final byte b, final byte b2, final byte b3, final byte b4) {
        final long glVertexAttrib4NubARB = GLContext.getCapabilities().glVertexAttrib4NubARB;
        BufferChecks.checkFunctionAddress(glVertexAttrib4NubARB);
        nglVertexAttrib4NubARB(n, b, b2, b3, b4, glVertexAttrib4NubARB);
    }
    
    static native void nglVertexAttrib4NubARB(final int p0, final byte p1, final byte p2, final byte p3, final byte p4, final long p5);
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = doubleBuffer;
        }
        nglVertexAttribPointerARB(n, n2, 5130, b, n3, MemoryUtil.getAddress(doubleBuffer), glVertexAttribPointerARB);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = floatBuffer;
        }
        nglVertexAttribPointerARB(n, n2, 5126, b, n3, MemoryUtil.getAddress(floatBuffer), glVertexAttribPointerARB);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribPointerARB(n, n2, b ? 5121 : 5120, b2, n3, MemoryUtil.getAddress(byteBuffer), glVertexAttribPointerARB);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = intBuffer;
        }
        nglVertexAttribPointerARB(n, n2, b ? 5125 : 5124, b2, n3, MemoryUtil.getAddress(intBuffer), glVertexAttribPointerARB);
    }
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = shortBuffer;
        }
        nglVertexAttribPointerARB(n, n2, b ? 5123 : 5122, b2, n3, MemoryUtil.getAddress(shortBuffer), glVertexAttribPointerARB);
    }
    
    static native void nglVertexAttribPointerARB(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5, final long p6);
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribPointerARBBO(n, n2, n3, b, n4, n5, glVertexAttribPointerARB);
    }
    
    static native void nglVertexAttribPointerARBBO(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5, final long p6);
    
    public static void glVertexAttribPointerARB(final int n, final int n2, final int n3, final boolean b, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerARB = capabilities.glVertexAttribPointerARB;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribPointerARB(n, n2, n3, b, n4, MemoryUtil.getAddress(byteBuffer), glVertexAttribPointerARB);
    }
    
    public static void glEnableVertexAttribArrayARB(final int n) {
        final long glEnableVertexAttribArrayARB = GLContext.getCapabilities().glEnableVertexAttribArrayARB;
        BufferChecks.checkFunctionAddress(glEnableVertexAttribArrayARB);
        nglEnableVertexAttribArrayARB(n, glEnableVertexAttribArrayARB);
    }
    
    static native void nglEnableVertexAttribArrayARB(final int p0, final long p1);
    
    public static void glDisableVertexAttribArrayARB(final int n) {
        final long glDisableVertexAttribArrayARB = GLContext.getCapabilities().glDisableVertexAttribArrayARB;
        BufferChecks.checkFunctionAddress(glDisableVertexAttribArrayARB);
        nglDisableVertexAttribArrayARB(n, glDisableVertexAttribArrayARB);
    }
    
    static native void nglDisableVertexAttribArrayARB(final int p0, final long p1);
    
    public static void glBindAttribLocationARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glBindAttribLocationARB = GLContext.getCapabilities().glBindAttribLocationARB;
        BufferChecks.checkFunctionAddress(glBindAttribLocationARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglBindAttribLocationARB(n, n2, MemoryUtil.getAddress(byteBuffer), glBindAttribLocationARB);
    }
    
    static native void nglBindAttribLocationARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindAttribLocationARB(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindAttribLocationARB = capabilities.glBindAttribLocationARB;
        BufferChecks.checkFunctionAddress(glBindAttribLocationARB);
        nglBindAttribLocationARB(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glBindAttribLocationARB);
    }
    
    public static void glGetActiveAttribARB(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetActiveAttribARB = GLContext.getCapabilities().glGetActiveAttribARB;
        BufferChecks.checkFunctionAddress(glGetActiveAttribARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveAttribARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetActiveAttribARB);
    }
    
    static native void nglGetActiveAttribARB(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetActiveAttribARB(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttribARB = capabilities.glGetActiveAttribARB;
        BufferChecks.checkFunctionAddress(glGetActiveAttribARB);
        BufferChecks.checkBuffer(intBuffer, 2);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveAttribARB(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer, intBuffer.position() + 1), MemoryUtil.getAddress(bufferByte), glGetActiveAttribARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static String glGetActiveAttribARB(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttribARB = capabilities.glGetActiveAttribARB;
        BufferChecks.checkFunctionAddress(glGetActiveAttribARB);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveAttribARB(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress0(APIUtil.getBufferInt(capabilities)), MemoryUtil.getAddress(APIUtil.getBufferInt(capabilities), 1), MemoryUtil.getAddress(bufferByte), glGetActiveAttribARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetActiveAttribSizeARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttribARB = capabilities.glGetActiveAttribARB;
        BufferChecks.checkFunctionAddress(glGetActiveAttribARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveAttribARB(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt), MemoryUtil.getAddress(bufferInt, 1), APIUtil.getBufferByte0(capabilities), glGetActiveAttribARB);
        return bufferInt.get(0);
    }
    
    public static int glGetActiveAttribTypeARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttribARB = capabilities.glGetActiveAttribARB;
        BufferChecks.checkFunctionAddress(glGetActiveAttribARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveAttribARB(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt, 1), MemoryUtil.getAddress(bufferInt), APIUtil.getBufferByte0(capabilities), glGetActiveAttribARB);
        return bufferInt.get(0);
    }
    
    public static int glGetAttribLocationARB(final int n, final ByteBuffer byteBuffer) {
        final long glGetAttribLocationARB = GLContext.getCapabilities().glGetAttribLocationARB;
        BufferChecks.checkFunctionAddress(glGetAttribLocationARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetAttribLocationARB(n, MemoryUtil.getAddress(byteBuffer), glGetAttribLocationARB);
    }
    
    static native int nglGetAttribLocationARB(final int p0, final long p1, final long p2);
    
    public static int glGetAttribLocationARB(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetAttribLocationARB = capabilities.glGetAttribLocationARB;
        BufferChecks.checkFunctionAddress(glGetAttribLocationARB);
        return nglGetAttribLocationARB(n, APIUtil.getBufferNT(capabilities, charSequence), glGetAttribLocationARB);
    }
    
    public static void glGetVertexAttribARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVertexAttribfvARB = GLContext.getCapabilities().glGetVertexAttribfvARB;
        BufferChecks.checkFunctionAddress(glGetVertexAttribfvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVertexAttribfvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVertexAttribfvARB);
    }
    
    static native void nglGetVertexAttribfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetVertexAttribdvARB = GLContext.getCapabilities().glGetVertexAttribdvARB;
        BufferChecks.checkFunctionAddress(glGetVertexAttribdvARB);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetVertexAttribdvARB(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetVertexAttribdvARB);
    }
    
    static native void nglGetVertexAttribdvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribivARB = GLContext.getCapabilities().glGetVertexAttribivARB;
        BufferChecks.checkFunctionAddress(glGetVertexAttribivARB);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribivARB);
    }
    
    static native void nglGetVertexAttribivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static ByteBuffer glGetVertexAttribPointerARB(final int n, final int n2, final long n3) {
        final long glGetVertexAttribPointervARB = GLContext.getCapabilities().glGetVertexAttribPointervARB;
        BufferChecks.checkFunctionAddress(glGetVertexAttribPointervARB);
        final ByteBuffer nglGetVertexAttribPointervARB = nglGetVertexAttribPointervARB(n, n2, n3, glGetVertexAttribPointervARB);
        return (LWJGLUtil.CHECKS && nglGetVertexAttribPointervARB == null) ? null : nglGetVertexAttribPointervARB.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVertexAttribPointervARB(final int p0, final int p1, final long p2, final long p3);
}
