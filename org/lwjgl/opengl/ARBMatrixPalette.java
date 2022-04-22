package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBMatrixPalette
{
    public static final int GL_MATRIX_PALETTE_ARB = 34880;
    public static final int GL_MAX_MATRIX_PALETTE_STACK_DEPTH_ARB = 34881;
    public static final int GL_MAX_PALETTE_MATRICES_ARB = 34882;
    public static final int GL_CURRENT_PALETTE_MATRIX_ARB = 34883;
    public static final int GL_MATRIX_INDEX_ARRAY_ARB = 34884;
    public static final int GL_CURRENT_MATRIX_INDEX_ARB = 34885;
    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_ARB = 34886;
    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_ARB = 34887;
    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_ARB = 34888;
    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_ARB = 34889;
    
    private ARBMatrixPalette() {
    }
    
    public static void glCurrentPaletteMatrixARB(final int n) {
        final long glCurrentPaletteMatrixARB = GLContext.getCapabilities().glCurrentPaletteMatrixARB;
        BufferChecks.checkFunctionAddress(glCurrentPaletteMatrixARB);
        nglCurrentPaletteMatrixARB(n, glCurrentPaletteMatrixARB);
    }
    
    static native void nglCurrentPaletteMatrixARB(final int p0, final long p1);
    
    public static void glMatrixIndexPointerARB(final int n, final int n2, final ByteBuffer arb_matrix_palette_glMatrixIndexPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMatrixIndexPointerARB = capabilities.glMatrixIndexPointerARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_matrix_palette_glMatrixIndexPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_matrix_palette_glMatrixIndexPointerARB_pPointer = arb_matrix_palette_glMatrixIndexPointerARB_pPointer;
        }
        nglMatrixIndexPointerARB(n, 5121, n2, MemoryUtil.getAddress(arb_matrix_palette_glMatrixIndexPointerARB_pPointer), glMatrixIndexPointerARB);
    }
    
    public static void glMatrixIndexPointerARB(final int n, final int n2, final IntBuffer arb_matrix_palette_glMatrixIndexPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMatrixIndexPointerARB = capabilities.glMatrixIndexPointerARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_matrix_palette_glMatrixIndexPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_matrix_palette_glMatrixIndexPointerARB_pPointer = arb_matrix_palette_glMatrixIndexPointerARB_pPointer;
        }
        nglMatrixIndexPointerARB(n, 5125, n2, MemoryUtil.getAddress(arb_matrix_palette_glMatrixIndexPointerARB_pPointer), glMatrixIndexPointerARB);
    }
    
    public static void glMatrixIndexPointerARB(final int n, final int n2, final ShortBuffer arb_matrix_palette_glMatrixIndexPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMatrixIndexPointerARB = capabilities.glMatrixIndexPointerARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_matrix_palette_glMatrixIndexPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_matrix_palette_glMatrixIndexPointerARB_pPointer = arb_matrix_palette_glMatrixIndexPointerARB_pPointer;
        }
        nglMatrixIndexPointerARB(n, 5123, n2, MemoryUtil.getAddress(arb_matrix_palette_glMatrixIndexPointerARB_pPointer), glMatrixIndexPointerARB);
    }
    
    static native void nglMatrixIndexPointerARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMatrixIndexPointerARB(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMatrixIndexPointerARB = capabilities.glMatrixIndexPointerARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexPointerARB);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglMatrixIndexPointerARBBO(n, n2, n3, n4, glMatrixIndexPointerARB);
    }
    
    static native void nglMatrixIndexPointerARBBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMatrixIndexuARB(final ByteBuffer byteBuffer) {
        final long glMatrixIndexubvARB = GLContext.getCapabilities().glMatrixIndexubvARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexubvARB);
        BufferChecks.checkDirect(byteBuffer);
        nglMatrixIndexubvARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glMatrixIndexubvARB);
    }
    
    static native void nglMatrixIndexubvARB(final int p0, final long p1, final long p2);
    
    public static void glMatrixIndexuARB(final ShortBuffer shortBuffer) {
        final long glMatrixIndexusvARB = GLContext.getCapabilities().glMatrixIndexusvARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexusvARB);
        BufferChecks.checkDirect(shortBuffer);
        nglMatrixIndexusvARB(shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glMatrixIndexusvARB);
    }
    
    static native void nglMatrixIndexusvARB(final int p0, final long p1, final long p2);
    
    public static void glMatrixIndexuARB(final IntBuffer intBuffer) {
        final long glMatrixIndexuivARB = GLContext.getCapabilities().glMatrixIndexuivARB;
        BufferChecks.checkFunctionAddress(glMatrixIndexuivARB);
        BufferChecks.checkDirect(intBuffer);
        nglMatrixIndexuivARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glMatrixIndexuivARB);
    }
    
    static native void nglMatrixIndexuivARB(final int p0, final long p1, final long p2);
}
