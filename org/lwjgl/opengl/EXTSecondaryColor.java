package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTSecondaryColor
{
    public static final int GL_COLOR_SUM_EXT = 33880;
    public static final int GL_CURRENT_SECONDARY_COLOR_EXT = 33881;
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE_EXT = 33882;
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE_EXT = 33883;
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE_EXT = 33884;
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER_EXT = 33885;
    public static final int GL_SECONDARY_COLOR_ARRAY_EXT = 33886;
    
    private EXTSecondaryColor() {
    }
    
    public static void glSecondaryColor3bEXT(final byte b, final byte b2, final byte b3) {
        final long glSecondaryColor3bEXT = GLContext.getCapabilities().glSecondaryColor3bEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColor3bEXT);
        nglSecondaryColor3bEXT(b, b2, b3, glSecondaryColor3bEXT);
    }
    
    static native void nglSecondaryColor3bEXT(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glSecondaryColor3fEXT(final float n, final float n2, final float n3) {
        final long glSecondaryColor3fEXT = GLContext.getCapabilities().glSecondaryColor3fEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColor3fEXT);
        nglSecondaryColor3fEXT(n, n2, n3, glSecondaryColor3fEXT);
    }
    
    static native void nglSecondaryColor3fEXT(final float p0, final float p1, final float p2, final long p3);
    
    public static void glSecondaryColor3dEXT(final double n, final double n2, final double n3) {
        final long glSecondaryColor3dEXT = GLContext.getCapabilities().glSecondaryColor3dEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColor3dEXT);
        nglSecondaryColor3dEXT(n, n2, n3, glSecondaryColor3dEXT);
    }
    
    static native void nglSecondaryColor3dEXT(final double p0, final double p1, final double p2, final long p3);
    
    public static void glSecondaryColor3ubEXT(final byte b, final byte b2, final byte b3) {
        final long glSecondaryColor3ubEXT = GLContext.getCapabilities().glSecondaryColor3ubEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColor3ubEXT);
        nglSecondaryColor3ubEXT(b, b2, b3, glSecondaryColor3ubEXT);
    }
    
    static native void nglSecondaryColor3ubEXT(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glSecondaryColorPointerEXT(final int n, final int n2, final DoubleBuffer ext_secondary_color_glSecondaryColorPointerEXT_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointerEXT = capabilities.glSecondaryColorPointerEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_secondary_color_glSecondaryColorPointerEXT_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_secondary_color_glSecondaryColorPointerEXT_pPointer = ext_secondary_color_glSecondaryColorPointerEXT_pPointer;
        }
        nglSecondaryColorPointerEXT(n, 5130, n2, MemoryUtil.getAddress(ext_secondary_color_glSecondaryColorPointerEXT_pPointer), glSecondaryColorPointerEXT);
    }
    
    public static void glSecondaryColorPointerEXT(final int n, final int n2, final FloatBuffer ext_secondary_color_glSecondaryColorPointerEXT_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointerEXT = capabilities.glSecondaryColorPointerEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_secondary_color_glSecondaryColorPointerEXT_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_secondary_color_glSecondaryColorPointerEXT_pPointer = ext_secondary_color_glSecondaryColorPointerEXT_pPointer;
        }
        nglSecondaryColorPointerEXT(n, 5126, n2, MemoryUtil.getAddress(ext_secondary_color_glSecondaryColorPointerEXT_pPointer), glSecondaryColorPointerEXT);
    }
    
    public static void glSecondaryColorPointerEXT(final int n, final boolean b, final int n2, final ByteBuffer ext_secondary_color_glSecondaryColorPointerEXT_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointerEXT = capabilities.glSecondaryColorPointerEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_secondary_color_glSecondaryColorPointerEXT_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_secondary_color_glSecondaryColorPointerEXT_pPointer = ext_secondary_color_glSecondaryColorPointerEXT_pPointer;
        }
        nglSecondaryColorPointerEXT(n, b ? 5121 : 5120, n2, MemoryUtil.getAddress(ext_secondary_color_glSecondaryColorPointerEXT_pPointer), glSecondaryColorPointerEXT);
    }
    
    static native void nglSecondaryColorPointerEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glSecondaryColorPointerEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointerEXT = capabilities.glSecondaryColorPointerEXT;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglSecondaryColorPointerEXTBO(n, n2, n3, n4, glSecondaryColorPointerEXT);
    }
    
    static native void nglSecondaryColorPointerEXTBO(final int p0, final int p1, final int p2, final long p3, final long p4);
}
