package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBVertexBlend
{
    public static final int GL_MAX_VERTEX_UNITS_ARB = 34468;
    public static final int GL_ACTIVE_VERTEX_UNITS_ARB = 34469;
    public static final int GL_WEIGHT_SUM_UNITY_ARB = 34470;
    public static final int GL_VERTEX_BLEND_ARB = 34471;
    public static final int GL_CURRENT_WEIGHT_ARB = 34472;
    public static final int GL_WEIGHT_ARRAY_TYPE_ARB = 34473;
    public static final int GL_WEIGHT_ARRAY_STRIDE_ARB = 34474;
    public static final int GL_WEIGHT_ARRAY_SIZE_ARB = 34475;
    public static final int GL_WEIGHT_ARRAY_POINTER_ARB = 34476;
    public static final int GL_WEIGHT_ARRAY_ARB = 34477;
    public static final int GL_MODELVIEW0_ARB = 5888;
    public static final int GL_MODELVIEW1_ARB = 34058;
    public static final int GL_MODELVIEW2_ARB = 34594;
    public static final int GL_MODELVIEW3_ARB = 34595;
    public static final int GL_MODELVIEW4_ARB = 34596;
    public static final int GL_MODELVIEW5_ARB = 34597;
    public static final int GL_MODELVIEW6_ARB = 34598;
    public static final int GL_MODELVIEW7_ARB = 34599;
    public static final int GL_MODELVIEW8_ARB = 34600;
    public static final int GL_MODELVIEW9_ARB = 34601;
    public static final int GL_MODELVIEW10_ARB = 34602;
    public static final int GL_MODELVIEW11_ARB = 34603;
    public static final int GL_MODELVIEW12_ARB = 34604;
    public static final int GL_MODELVIEW13_ARB = 34605;
    public static final int GL_MODELVIEW14_ARB = 34606;
    public static final int GL_MODELVIEW15_ARB = 34607;
    public static final int GL_MODELVIEW16_ARB = 34608;
    public static final int GL_MODELVIEW17_ARB = 34609;
    public static final int GL_MODELVIEW18_ARB = 34610;
    public static final int GL_MODELVIEW19_ARB = 34611;
    public static final int GL_MODELVIEW20_ARB = 34612;
    public static final int GL_MODELVIEW21_ARB = 34613;
    public static final int GL_MODELVIEW22_ARB = 34614;
    public static final int GL_MODELVIEW23_ARB = 34615;
    public static final int GL_MODELVIEW24_ARB = 34616;
    public static final int GL_MODELVIEW25_ARB = 34617;
    public static final int GL_MODELVIEW26_ARB = 34618;
    public static final int GL_MODELVIEW27_ARB = 34619;
    public static final int GL_MODELVIEW28_ARB = 34620;
    public static final int GL_MODELVIEW29_ARB = 34621;
    public static final int GL_MODELVIEW30_ARB = 34622;
    public static final int GL_MODELVIEW31_ARB = 34623;
    
    private ARBVertexBlend() {
    }
    
    public static void glWeightARB(final ByteBuffer byteBuffer) {
        final long glWeightbvARB = GLContext.getCapabilities().glWeightbvARB;
        BufferChecks.checkFunctionAddress(glWeightbvARB);
        BufferChecks.checkDirect(byteBuffer);
        nglWeightbvARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glWeightbvARB);
    }
    
    static native void nglWeightbvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightARB(final ShortBuffer shortBuffer) {
        final long glWeightsvARB = GLContext.getCapabilities().glWeightsvARB;
        BufferChecks.checkFunctionAddress(glWeightsvARB);
        BufferChecks.checkDirect(shortBuffer);
        nglWeightsvARB(shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glWeightsvARB);
    }
    
    static native void nglWeightsvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightARB(final IntBuffer intBuffer) {
        final long glWeightivARB = GLContext.getCapabilities().glWeightivARB;
        BufferChecks.checkFunctionAddress(glWeightivARB);
        BufferChecks.checkDirect(intBuffer);
        nglWeightivARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glWeightivARB);
    }
    
    static native void nglWeightivARB(final int p0, final long p1, final long p2);
    
    public static void glWeightARB(final FloatBuffer floatBuffer) {
        final long glWeightfvARB = GLContext.getCapabilities().glWeightfvARB;
        BufferChecks.checkFunctionAddress(glWeightfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglWeightfvARB(floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glWeightfvARB);
    }
    
    static native void nglWeightfvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightARB(final DoubleBuffer doubleBuffer) {
        final long glWeightdvARB = GLContext.getCapabilities().glWeightdvARB;
        BufferChecks.checkFunctionAddress(glWeightdvARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglWeightdvARB(doubleBuffer.remaining(), MemoryUtil.getAddress(doubleBuffer), glWeightdvARB);
    }
    
    static native void nglWeightdvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightuARB(final ByteBuffer byteBuffer) {
        final long glWeightubvARB = GLContext.getCapabilities().glWeightubvARB;
        BufferChecks.checkFunctionAddress(glWeightubvARB);
        BufferChecks.checkDirect(byteBuffer);
        nglWeightubvARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glWeightubvARB);
    }
    
    static native void nglWeightubvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightuARB(final ShortBuffer shortBuffer) {
        final long glWeightusvARB = GLContext.getCapabilities().glWeightusvARB;
        BufferChecks.checkFunctionAddress(glWeightusvARB);
        BufferChecks.checkDirect(shortBuffer);
        nglWeightusvARB(shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glWeightusvARB);
    }
    
    static native void nglWeightusvARB(final int p0, final long p1, final long p2);
    
    public static void glWeightuARB(final IntBuffer intBuffer) {
        final long glWeightuivARB = GLContext.getCapabilities().glWeightuivARB;
        BufferChecks.checkFunctionAddress(glWeightuivARB);
        BufferChecks.checkDirect(intBuffer);
        nglWeightuivARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glWeightuivARB);
    }
    
    static native void nglWeightuivARB(final int p0, final long p1, final long p2);
    
    public static void glWeightPointerARB(final int n, final int n2, final DoubleBuffer arb_vertex_blend_glWeightPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_vertex_blend_glWeightPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_vertex_blend_glWeightPointerARB_pPointer = arb_vertex_blend_glWeightPointerARB_pPointer;
        }
        nglWeightPointerARB(n, 5130, n2, MemoryUtil.getAddress(arb_vertex_blend_glWeightPointerARB_pPointer), glWeightPointerARB);
    }
    
    public static void glWeightPointerARB(final int n, final int n2, final FloatBuffer arb_vertex_blend_glWeightPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_vertex_blend_glWeightPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_vertex_blend_glWeightPointerARB_pPointer = arb_vertex_blend_glWeightPointerARB_pPointer;
        }
        nglWeightPointerARB(n, 5126, n2, MemoryUtil.getAddress(arb_vertex_blend_glWeightPointerARB_pPointer), glWeightPointerARB);
    }
    
    public static void glWeightPointerARB(final int n, final boolean b, final int n2, final ByteBuffer arb_vertex_blend_glWeightPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_vertex_blend_glWeightPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_vertex_blend_glWeightPointerARB_pPointer = arb_vertex_blend_glWeightPointerARB_pPointer;
        }
        nglWeightPointerARB(n, b ? 5121 : 5120, n2, MemoryUtil.getAddress(arb_vertex_blend_glWeightPointerARB_pPointer), glWeightPointerARB);
    }
    
    public static void glWeightPointerARB(final int n, final boolean b, final int n2, final IntBuffer arb_vertex_blend_glWeightPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_vertex_blend_glWeightPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_vertex_blend_glWeightPointerARB_pPointer = arb_vertex_blend_glWeightPointerARB_pPointer;
        }
        nglWeightPointerARB(n, b ? 5125 : 5124, n2, MemoryUtil.getAddress(arb_vertex_blend_glWeightPointerARB_pPointer), glWeightPointerARB);
    }
    
    public static void glWeightPointerARB(final int n, final boolean b, final int n2, final ShortBuffer arb_vertex_blend_glWeightPointerARB_pPointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(arb_vertex_blend_glWeightPointerARB_pPointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).ARB_vertex_blend_glWeightPointerARB_pPointer = arb_vertex_blend_glWeightPointerARB_pPointer;
        }
        nglWeightPointerARB(n, b ? 5123 : 5122, n2, MemoryUtil.getAddress(arb_vertex_blend_glWeightPointerARB_pPointer), glWeightPointerARB);
    }
    
    static native void nglWeightPointerARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glWeightPointerARB(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glWeightPointerARB = capabilities.glWeightPointerARB;
        BufferChecks.checkFunctionAddress(glWeightPointerARB);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglWeightPointerARBBO(n, n2, n3, n4, glWeightPointerARB);
    }
    
    static native void nglWeightPointerARBBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVertexBlendARB(final int n) {
        final long glVertexBlendARB = GLContext.getCapabilities().glVertexBlendARB;
        BufferChecks.checkFunctionAddress(glVertexBlendARB);
        nglVertexBlendARB(n, glVertexBlendARB);
    }
    
    static native void nglVertexBlendARB(final int p0, final long p1);
}
