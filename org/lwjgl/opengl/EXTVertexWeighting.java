package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTVertexWeighting
{
    public static final int GL_MODELVIEW0_STACK_DEPTH_EXT = 2979;
    public static final int GL_MODELVIEW1_STACK_DEPTH_EXT = 34050;
    public static final int GL_MODELVIEW0_MATRIX_EXT = 2982;
    public static final int GL_MODELVIEW1_MATRIX_EXT = 34054;
    public static final int GL_VERTEX_WEIGHTING_EXT = 34057;
    public static final int GL_MODELVIEW0_EXT = 5888;
    public static final int GL_MODELVIEW1_EXT = 34058;
    public static final int GL_CURRENT_VERTEX_WEIGHT_EXT = 34059;
    public static final int GL_VERTEX_WEIGHT_ARRAY_EXT = 34060;
    public static final int GL_VERTEX_WEIGHT_ARRAY_SIZE_EXT = 34061;
    public static final int GL_VERTEX_WEIGHT_ARRAY_TYPE_EXT = 34062;
    public static final int GL_VERTEX_WEIGHT_ARRAY_STRIDE_EXT = 34063;
    public static final int GL_VERTEX_WEIGHT_ARRAY_POINTER_EXT = 34064;
    
    private EXTVertexWeighting() {
    }
    
    public static void glVertexWeightfEXT(final float n) {
        final long glVertexWeightfEXT = GLContext.getCapabilities().glVertexWeightfEXT;
        BufferChecks.checkFunctionAddress(glVertexWeightfEXT);
        nglVertexWeightfEXT(n, glVertexWeightfEXT);
    }
    
    static native void nglVertexWeightfEXT(final float p0, final long p1);
    
    public static void glVertexWeightPointerEXT(final int n, final int n2, final FloatBuffer ext_vertex_weighting_glVertexWeightPointerEXT_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexWeightPointerEXT = capabilities.glVertexWeightPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexWeightPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_weighting_glVertexWeightPointerEXT_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_weighting_glVertexWeightPointerEXT_pPointer = ext_vertex_weighting_glVertexWeightPointerEXT_pPointer;
        }
        nglVertexWeightPointerEXT(n, 5126, n2, MemoryUtil.getAddress(ext_vertex_weighting_glVertexWeightPointerEXT_pPointer), glVertexWeightPointerEXT);
    }
    
    static native void nglVertexWeightPointerEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVertexWeightPointerEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexWeightPointerEXT = capabilities.glVertexWeightPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexWeightPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexWeightPointerEXTBO(n, n2, n3, n4, glVertexWeightPointerEXT);
    }
    
    static native void nglVertexWeightPointerEXTBO(final int p0, final int p1, final int p2, final long p3, final long p4);
}
