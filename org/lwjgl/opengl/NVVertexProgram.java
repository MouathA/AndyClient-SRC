package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVVertexProgram extends NVProgram
{
    public static final int GL_VERTEX_PROGRAM_NV = 34336;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_NV = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_NV = 34371;
    public static final int GL_VERTEX_STATE_PROGRAM_NV = 34337;
    public static final int GL_ATTRIB_ARRAY_SIZE_NV = 34339;
    public static final int GL_ATTRIB_ARRAY_STRIDE_NV = 34340;
    public static final int GL_ATTRIB_ARRAY_TYPE_NV = 34341;
    public static final int GL_CURRENT_ATTRIB_NV = 34342;
    public static final int GL_PROGRAM_PARAMETER_NV = 34372;
    public static final int GL_ATTRIB_ARRAY_POINTER_NV = 34373;
    public static final int GL_TRACK_MATRIX_NV = 34376;
    public static final int GL_TRACK_MATRIX_TRANSFORM_NV = 34377;
    public static final int GL_MAX_TRACK_MATRIX_STACK_DEPTH_NV = 34350;
    public static final int GL_MAX_TRACK_MATRICES_NV = 34351;
    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_NV = 34368;
    public static final int GL_CURRENT_MATRIX_NV = 34369;
    public static final int GL_VERTEX_PROGRAM_BINDING_NV = 34378;
    public static final int GL_MODELVIEW_PROJECTION_NV = 34345;
    public static final int GL_MATRIX0_NV = 34352;
    public static final int GL_MATRIX1_NV = 34353;
    public static final int GL_MATRIX2_NV = 34354;
    public static final int GL_MATRIX3_NV = 34355;
    public static final int GL_MATRIX4_NV = 34356;
    public static final int GL_MATRIX5_NV = 34357;
    public static final int GL_MATRIX6_NV = 34358;
    public static final int GL_MATRIX7_NV = 34359;
    public static final int GL_IDENTITY_NV = 34346;
    public static final int GL_INVERSE_NV = 34347;
    public static final int GL_TRANSPOSE_NV = 34348;
    public static final int GL_INVERSE_TRANSPOSE_NV = 34349;
    public static final int GL_VERTEX_ATTRIB_ARRAY0_NV = 34384;
    public static final int GL_VERTEX_ATTRIB_ARRAY1_NV = 34385;
    public static final int GL_VERTEX_ATTRIB_ARRAY2_NV = 34386;
    public static final int GL_VERTEX_ATTRIB_ARRAY3_NV = 34387;
    public static final int GL_VERTEX_ATTRIB_ARRAY4_NV = 34388;
    public static final int GL_VERTEX_ATTRIB_ARRAY5_NV = 34389;
    public static final int GL_VERTEX_ATTRIB_ARRAY6_NV = 34390;
    public static final int GL_VERTEX_ATTRIB_ARRAY7_NV = 34391;
    public static final int GL_VERTEX_ATTRIB_ARRAY8_NV = 34392;
    public static final int GL_VERTEX_ATTRIB_ARRAY9_NV = 34393;
    public static final int GL_VERTEX_ATTRIB_ARRAY10_NV = 34394;
    public static final int GL_VERTEX_ATTRIB_ARRAY11_NV = 34395;
    public static final int GL_VERTEX_ATTRIB_ARRAY12_NV = 34396;
    public static final int GL_VERTEX_ATTRIB_ARRAY13_NV = 34397;
    public static final int GL_VERTEX_ATTRIB_ARRAY14_NV = 34398;
    public static final int GL_VERTEX_ATTRIB_ARRAY15_NV = 34399;
    public static final int GL_MAP1_VERTEX_ATTRIB0_4_NV = 34400;
    public static final int GL_MAP1_VERTEX_ATTRIB1_4_NV = 34401;
    public static final int GL_MAP1_VERTEX_ATTRIB2_4_NV = 34402;
    public static final int GL_MAP1_VERTEX_ATTRIB3_4_NV = 34403;
    public static final int GL_MAP1_VERTEX_ATTRIB4_4_NV = 34404;
    public static final int GL_MAP1_VERTEX_ATTRIB5_4_NV = 34405;
    public static final int GL_MAP1_VERTEX_ATTRIB6_4_NV = 34406;
    public static final int GL_MAP1_VERTEX_ATTRIB7_4_NV = 34407;
    public static final int GL_MAP1_VERTEX_ATTRIB8_4_NV = 34408;
    public static final int GL_MAP1_VERTEX_ATTRIB9_4_NV = 34409;
    public static final int GL_MAP1_VERTEX_ATTRIB10_4_NV = 34410;
    public static final int GL_MAP1_VERTEX_ATTRIB11_4_NV = 34411;
    public static final int GL_MAP1_VERTEX_ATTRIB12_4_NV = 34412;
    public static final int GL_MAP1_VERTEX_ATTRIB13_4_NV = 34413;
    public static final int GL_MAP1_VERTEX_ATTRIB14_4_NV = 34414;
    public static final int GL_MAP1_VERTEX_ATTRIB15_4_NV = 34415;
    public static final int GL_MAP2_VERTEX_ATTRIB0_4_NV = 34416;
    public static final int GL_MAP2_VERTEX_ATTRIB1_4_NV = 34417;
    public static final int GL_MAP2_VERTEX_ATTRIB2_4_NV = 34418;
    public static final int GL_MAP2_VERTEX_ATTRIB3_4_NV = 34419;
    public static final int GL_MAP2_VERTEX_ATTRIB4_4_NV = 34420;
    public static final int GL_MAP2_VERTEX_ATTRIB5_4_NV = 34421;
    public static final int GL_MAP2_VERTEX_ATTRIB6_4_NV = 34422;
    public static final int GL_MAP2_VERTEX_ATTRIB7_4_NV = 34423;
    public static final int GL_MAP2_VERTEX_ATTRIB8_4_NV = 34424;
    public static final int GL_MAP2_VERTEX_ATTRIB9_4_NV = 34425;
    public static final int GL_MAP2_VERTEX_ATTRIB10_4_NV = 34426;
    public static final int GL_MAP2_VERTEX_ATTRIB11_4_NV = 34427;
    public static final int GL_MAP2_VERTEX_ATTRIB12_4_NV = 34428;
    public static final int GL_MAP2_VERTEX_ATTRIB13_4_NV = 34429;
    public static final int GL_MAP2_VERTEX_ATTRIB14_4_NV = 34430;
    public static final int GL_MAP2_VERTEX_ATTRIB15_4_NV = 34431;
    
    private NVVertexProgram() {
    }
    
    public static void glExecuteProgramNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glExecuteProgramNV = GLContext.getCapabilities().glExecuteProgramNV;
        BufferChecks.checkFunctionAddress(glExecuteProgramNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglExecuteProgramNV(n, n2, MemoryUtil.getAddress(floatBuffer), glExecuteProgramNV);
    }
    
    static native void nglExecuteProgramNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramParameterNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetProgramParameterfvNV = GLContext.getCapabilities().glGetProgramParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetProgramParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetProgramParameterfvNV(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetProgramParameterfvNV);
    }
    
    static native void nglGetProgramParameterfvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetProgramParameterNV(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetProgramParameterdvNV = GLContext.getCapabilities().glGetProgramParameterdvNV;
        BufferChecks.checkFunctionAddress(glGetProgramParameterdvNV);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetProgramParameterdvNV(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetProgramParameterdvNV);
    }
    
    static native void nglGetProgramParameterdvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetTrackMatrixNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTrackMatrixivNV = GLContext.getCapabilities().glGetTrackMatrixivNV;
        BufferChecks.checkFunctionAddress(glGetTrackMatrixivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTrackMatrixivNV(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTrackMatrixivNV);
    }
    
    static native void nglGetTrackMatrixivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetVertexAttribNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVertexAttribfvNV = GLContext.getCapabilities().glGetVertexAttribfvNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVertexAttribfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVertexAttribfvNV);
    }
    
    static native void nglGetVertexAttribfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribNV(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetVertexAttribdvNV = GLContext.getCapabilities().glGetVertexAttribdvNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribdvNV);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetVertexAttribdvNV(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetVertexAttribdvNV);
    }
    
    static native void nglGetVertexAttribdvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribivNV = GLContext.getCapabilities().glGetVertexAttribivNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribivNV);
    }
    
    static native void nglGetVertexAttribivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static ByteBuffer glGetVertexAttribPointerNV(final int n, final int n2, final long n3) {
        final long glGetVertexAttribPointervNV = GLContext.getCapabilities().glGetVertexAttribPointervNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribPointervNV);
        final ByteBuffer nglGetVertexAttribPointervNV = nglGetVertexAttribPointervNV(n, n2, n3, glGetVertexAttribPointervNV);
        return (LWJGLUtil.CHECKS && nglGetVertexAttribPointervNV == null) ? null : nglGetVertexAttribPointervNV.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVertexAttribPointervNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramParameter4fNV(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long glProgramParameter4fNV = GLContext.getCapabilities().glProgramParameter4fNV;
        BufferChecks.checkFunctionAddress(glProgramParameter4fNV);
        nglProgramParameter4fNV(n, n2, n3, n4, n5, n6, glProgramParameter4fNV);
    }
    
    static native void nglProgramParameter4fNV(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    public static void glProgramParameter4dNV(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        final long glProgramParameter4dNV = GLContext.getCapabilities().glProgramParameter4dNV;
        BufferChecks.checkFunctionAddress(glProgramParameter4dNV);
        nglProgramParameter4dNV(n, n2, n3, n4, n5, n6, glProgramParameter4dNV);
    }
    
    static native void nglProgramParameter4dNV(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glProgramParameters4NV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramParameters4fvNV = GLContext.getCapabilities().glProgramParameters4fvNV;
        BufferChecks.checkFunctionAddress(glProgramParameters4fvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramParameters4fvNV(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glProgramParameters4fvNV);
    }
    
    static native void nglProgramParameters4fvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramParameters4NV(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramParameters4dvNV = GLContext.getCapabilities().glProgramParameters4dvNV;
        BufferChecks.checkFunctionAddress(glProgramParameters4dvNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramParameters4dvNV(n, n2, doubleBuffer.remaining() >> 2, MemoryUtil.getAddress(doubleBuffer), glProgramParameters4dvNV);
    }
    
    static native void nglProgramParameters4dvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTrackMatrixNV(final int n, final int n2, final int n3, final int n4) {
        final long glTrackMatrixNV = GLContext.getCapabilities().glTrackMatrixNV;
        BufferChecks.checkFunctionAddress(glTrackMatrixNV);
        nglTrackMatrixNV(n, n2, n3, n4, glTrackMatrixNV);
    }
    
    static native void nglTrackMatrixNV(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = doubleBuffer;
        }
        nglVertexAttribPointerNV(n, n2, n3, n4, MemoryUtil.getAddress(doubleBuffer), glVertexAttribPointerNV);
    }
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = floatBuffer;
        }
        nglVertexAttribPointerNV(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glVertexAttribPointerNV);
    }
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribPointerNV(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glVertexAttribPointerNV);
    }
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = intBuffer;
        }
        nglVertexAttribPointerNV(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glVertexAttribPointerNV);
    }
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = shortBuffer;
        }
        nglVertexAttribPointerNV(n, n2, n3, n4, MemoryUtil.getAddress(shortBuffer), glVertexAttribPointerNV);
    }
    
    static native void nglVertexAttribPointerNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttribPointerNV(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointerNV = capabilities.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(glVertexAttribPointerNV);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribPointerNVBO(n, n2, n3, n4, n5, glVertexAttribPointerNV);
    }
    
    static native void nglVertexAttribPointerNVBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttrib1sNV(final int n, final short n2) {
        final long glVertexAttrib1sNV = GLContext.getCapabilities().glVertexAttrib1sNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib1sNV);
        nglVertexAttrib1sNV(n, n2, glVertexAttrib1sNV);
    }
    
    static native void nglVertexAttrib1sNV(final int p0, final short p1, final long p2);
    
    public static void glVertexAttrib1fNV(final int n, final float n2) {
        final long glVertexAttrib1fNV = GLContext.getCapabilities().glVertexAttrib1fNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib1fNV);
        nglVertexAttrib1fNV(n, n2, glVertexAttrib1fNV);
    }
    
    static native void nglVertexAttrib1fNV(final int p0, final float p1, final long p2);
    
    public static void glVertexAttrib1dNV(final int n, final double n2) {
        final long glVertexAttrib1dNV = GLContext.getCapabilities().glVertexAttrib1dNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib1dNV);
        nglVertexAttrib1dNV(n, n2, glVertexAttrib1dNV);
    }
    
    static native void nglVertexAttrib1dNV(final int p0, final double p1, final long p2);
    
    public static void glVertexAttrib2sNV(final int n, final short n2, final short n3) {
        final long glVertexAttrib2sNV = GLContext.getCapabilities().glVertexAttrib2sNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib2sNV);
        nglVertexAttrib2sNV(n, n2, n3, glVertexAttrib2sNV);
    }
    
    static native void nglVertexAttrib2sNV(final int p0, final short p1, final short p2, final long p3);
    
    public static void glVertexAttrib2fNV(final int n, final float n2, final float n3) {
        final long glVertexAttrib2fNV = GLContext.getCapabilities().glVertexAttrib2fNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib2fNV);
        nglVertexAttrib2fNV(n, n2, n3, glVertexAttrib2fNV);
    }
    
    static native void nglVertexAttrib2fNV(final int p0, final float p1, final float p2, final long p3);
    
    public static void glVertexAttrib2dNV(final int n, final double n2, final double n3) {
        final long glVertexAttrib2dNV = GLContext.getCapabilities().glVertexAttrib2dNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib2dNV);
        nglVertexAttrib2dNV(n, n2, n3, glVertexAttrib2dNV);
    }
    
    static native void nglVertexAttrib2dNV(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexAttrib3sNV(final int n, final short n2, final short n3, final short n4) {
        final long glVertexAttrib3sNV = GLContext.getCapabilities().glVertexAttrib3sNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib3sNV);
        nglVertexAttrib3sNV(n, n2, n3, n4, glVertexAttrib3sNV);
    }
    
    static native void nglVertexAttrib3sNV(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glVertexAttrib3fNV(final int n, final float n2, final float n3, final float n4) {
        final long glVertexAttrib3fNV = GLContext.getCapabilities().glVertexAttrib3fNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib3fNV);
        nglVertexAttrib3fNV(n, n2, n3, n4, glVertexAttrib3fNV);
    }
    
    static native void nglVertexAttrib3fNV(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glVertexAttrib3dNV(final int n, final double n2, final double n3, final double n4) {
        final long glVertexAttrib3dNV = GLContext.getCapabilities().glVertexAttrib3dNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib3dNV);
        nglVertexAttrib3dNV(n, n2, n3, n4, glVertexAttrib3dNV);
    }
    
    static native void nglVertexAttrib3dNV(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexAttrib4sNV(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glVertexAttrib4sNV = GLContext.getCapabilities().glVertexAttrib4sNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib4sNV);
        nglVertexAttrib4sNV(n, n2, n3, n4, n5, glVertexAttrib4sNV);
    }
    
    static native void nglVertexAttrib4sNV(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glVertexAttrib4fNV(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glVertexAttrib4fNV = GLContext.getCapabilities().glVertexAttrib4fNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib4fNV);
        nglVertexAttrib4fNV(n, n2, n3, n4, n5, glVertexAttrib4fNV);
    }
    
    static native void nglVertexAttrib4fNV(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glVertexAttrib4dNV(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexAttrib4dNV = GLContext.getCapabilities().glVertexAttrib4dNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib4dNV);
        nglVertexAttrib4dNV(n, n2, n3, n4, n5, glVertexAttrib4dNV);
    }
    
    static native void nglVertexAttrib4dNV(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexAttrib4ubNV(final int n, final byte b, final byte b2, final byte b3, final byte b4) {
        final long glVertexAttrib4ubNV = GLContext.getCapabilities().glVertexAttrib4ubNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib4ubNV);
        nglVertexAttrib4ubNV(n, b, b2, b3, b4, glVertexAttrib4ubNV);
    }
    
    static native void nglVertexAttrib4ubNV(final int p0, final byte p1, final byte p2, final byte p3, final byte p4, final long p5);
    
    public static void glVertexAttribs1NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs1svNV = GLContext.getCapabilities().glVertexAttribs1svNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs1svNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs1svNV(n, shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glVertexAttribs1svNV);
    }
    
    static native void nglVertexAttribs1svNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs1NV(final int n, final FloatBuffer floatBuffer) {
        final long glVertexAttribs1fvNV = GLContext.getCapabilities().glVertexAttribs1fvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs1fvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglVertexAttribs1fvNV(n, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glVertexAttribs1fvNV);
    }
    
    static native void nglVertexAttribs1fvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs1NV(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribs1dvNV = GLContext.getCapabilities().glVertexAttribs1dvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs1dvNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglVertexAttribs1dvNV(n, doubleBuffer.remaining(), MemoryUtil.getAddress(doubleBuffer), glVertexAttribs1dvNV);
    }
    
    static native void nglVertexAttribs1dvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs2NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs2svNV = GLContext.getCapabilities().glVertexAttribs2svNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs2svNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs2svNV(n, shortBuffer.remaining() >> 1, MemoryUtil.getAddress(shortBuffer), glVertexAttribs2svNV);
    }
    
    static native void nglVertexAttribs2svNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs2NV(final int n, final FloatBuffer floatBuffer) {
        final long glVertexAttribs2fvNV = GLContext.getCapabilities().glVertexAttribs2fvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs2fvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglVertexAttribs2fvNV(n, floatBuffer.remaining() >> 1, MemoryUtil.getAddress(floatBuffer), glVertexAttribs2fvNV);
    }
    
    static native void nglVertexAttribs2fvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs2NV(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribs2dvNV = GLContext.getCapabilities().glVertexAttribs2dvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs2dvNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglVertexAttribs2dvNV(n, doubleBuffer.remaining() >> 1, MemoryUtil.getAddress(doubleBuffer), glVertexAttribs2dvNV);
    }
    
    static native void nglVertexAttribs2dvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs3NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs3svNV = GLContext.getCapabilities().glVertexAttribs3svNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs3svNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs3svNV(n, shortBuffer.remaining() / 3, MemoryUtil.getAddress(shortBuffer), glVertexAttribs3svNV);
    }
    
    static native void nglVertexAttribs3svNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs3NV(final int n, final FloatBuffer floatBuffer) {
        final long glVertexAttribs3fvNV = GLContext.getCapabilities().glVertexAttribs3fvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs3fvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglVertexAttribs3fvNV(n, floatBuffer.remaining() / 3, MemoryUtil.getAddress(floatBuffer), glVertexAttribs3fvNV);
    }
    
    static native void nglVertexAttribs3fvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs3NV(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribs3dvNV = GLContext.getCapabilities().glVertexAttribs3dvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs3dvNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglVertexAttribs3dvNV(n, doubleBuffer.remaining() / 3, MemoryUtil.getAddress(doubleBuffer), glVertexAttribs3dvNV);
    }
    
    static native void nglVertexAttribs3dvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs4NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs4svNV = GLContext.getCapabilities().glVertexAttribs4svNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs4svNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs4svNV(n, shortBuffer.remaining() >> 2, MemoryUtil.getAddress(shortBuffer), glVertexAttribs4svNV);
    }
    
    static native void nglVertexAttribs4svNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs4NV(final int n, final FloatBuffer floatBuffer) {
        final long glVertexAttribs4fvNV = GLContext.getCapabilities().glVertexAttribs4fvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs4fvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglVertexAttribs4fvNV(n, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glVertexAttribs4fvNV);
    }
    
    static native void nglVertexAttribs4fvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs4NV(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribs4dvNV = GLContext.getCapabilities().glVertexAttribs4dvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs4dvNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglVertexAttribs4dvNV(n, doubleBuffer.remaining() >> 2, MemoryUtil.getAddress(doubleBuffer), glVertexAttribs4dvNV);
    }
    
    static native void nglVertexAttribs4dvNV(final int p0, final int p1, final long p2, final long p3);
}
