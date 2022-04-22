package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class APPLEVertexProgramEvaluators
{
    public static final int GL_VERTEX_ATTRIB_MAP1_APPLE = 35328;
    public static final int GL_VERTEX_ATTRIB_MAP2_APPLE = 35329;
    public static final int GL_VERTEX_ATTRIB_MAP1_SIZE_APPLE = 35330;
    public static final int GL_VERTEX_ATTRIB_MAP1_COEFF_APPLE = 35331;
    public static final int GL_VERTEX_ATTRIB_MAP1_ORDER_APPLE = 35332;
    public static final int GL_VERTEX_ATTRIB_MAP1_DOMAIN_APPLE = 35333;
    public static final int GL_VERTEX_ATTRIB_MAP2_SIZE_APPLE = 35334;
    public static final int GL_VERTEX_ATTRIB_MAP2_COEFF_APPLE = 35335;
    public static final int GL_VERTEX_ATTRIB_MAP2_ORDER_APPLE = 35336;
    public static final int GL_VERTEX_ATTRIB_MAP2_DOMAIN_APPLE = 35337;
    
    private APPLEVertexProgramEvaluators() {
    }
    
    public static void glEnableVertexAttribAPPLE(final int n, final int n2) {
        final long glEnableVertexAttribAPPLE = GLContext.getCapabilities().glEnableVertexAttribAPPLE;
        BufferChecks.checkFunctionAddress(glEnableVertexAttribAPPLE);
        nglEnableVertexAttribAPPLE(n, n2, glEnableVertexAttribAPPLE);
    }
    
    static native void nglEnableVertexAttribAPPLE(final int p0, final int p1, final long p2);
    
    public static void glDisableVertexAttribAPPLE(final int n, final int n2) {
        final long glDisableVertexAttribAPPLE = GLContext.getCapabilities().glDisableVertexAttribAPPLE;
        BufferChecks.checkFunctionAddress(glDisableVertexAttribAPPLE);
        nglDisableVertexAttribAPPLE(n, n2, glDisableVertexAttribAPPLE);
    }
    
    static native void nglDisableVertexAttribAPPLE(final int p0, final int p1, final long p2);
    
    public static boolean glIsVertexAttribEnabledAPPLE(final int n, final int n2) {
        final long glIsVertexAttribEnabledAPPLE = GLContext.getCapabilities().glIsVertexAttribEnabledAPPLE;
        BufferChecks.checkFunctionAddress(glIsVertexAttribEnabledAPPLE);
        return nglIsVertexAttribEnabledAPPLE(n, n2, glIsVertexAttribEnabledAPPLE);
    }
    
    static native boolean nglIsVertexAttribEnabledAPPLE(final int p0, final int p1, final long p2);
    
    public static void glMapVertexAttrib1dAPPLE(final int n, final int n2, final double n3, final double n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final long glMapVertexAttrib1dAPPLE = GLContext.getCapabilities().glMapVertexAttrib1dAPPLE;
        BufferChecks.checkFunctionAddress(glMapVertexAttrib1dAPPLE);
        BufferChecks.checkDirect(doubleBuffer);
        nglMapVertexAttrib1dAPPLE(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), glMapVertexAttrib1dAPPLE);
    }
    
    static native void nglMapVertexAttrib1dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glMapVertexAttrib1fAPPLE(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final long glMapVertexAttrib1fAPPLE = GLContext.getCapabilities().glMapVertexAttrib1fAPPLE;
        BufferChecks.checkFunctionAddress(glMapVertexAttrib1fAPPLE);
        BufferChecks.checkDirect(floatBuffer);
        nglMapVertexAttrib1fAPPLE(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), glMapVertexAttrib1fAPPLE);
    }
    
    static native void nglMapVertexAttrib1fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glMapVertexAttrib2dAPPLE(final int n, final int n2, final double n3, final double n4, final int n5, final int n6, final double n7, final double n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final long glMapVertexAttrib2dAPPLE = GLContext.getCapabilities().glMapVertexAttrib2dAPPLE;
        BufferChecks.checkFunctionAddress(glMapVertexAttrib2dAPPLE);
        BufferChecks.checkDirect(doubleBuffer);
        nglMapVertexAttrib2dAPPLE(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(doubleBuffer), glMapVertexAttrib2dAPPLE);
    }
    
    static native void nglMapVertexAttrib2dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final double p6, final double p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glMapVertexAttrib2fAPPLE(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final long glMapVertexAttrib2fAPPLE = GLContext.getCapabilities().glMapVertexAttrib2fAPPLE;
        BufferChecks.checkFunctionAddress(glMapVertexAttrib2fAPPLE);
        BufferChecks.checkDirect(floatBuffer);
        nglMapVertexAttrib2fAPPLE(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(floatBuffer), glMapVertexAttrib2fAPPLE);
    }
    
    static native void nglMapVertexAttrib2fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final float p6, final float p7, final int p8, final int p9, final long p10, final long p11);
}
