package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTDrawBuffers2
{
    private EXTDrawBuffers2() {
    }
    
    public static void glColorMaskIndexedEXT(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long glColorMaskIndexedEXT = GLContext.getCapabilities().glColorMaskIndexedEXT;
        BufferChecks.checkFunctionAddress(glColorMaskIndexedEXT);
        nglColorMaskIndexedEXT(n, b, b2, b3, b4, glColorMaskIndexedEXT);
    }
    
    static native void nglColorMaskIndexedEXT(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4, final long p5);
    
    public static void glGetBooleanIndexedEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetBooleanIndexedvEXT = GLContext.getCapabilities().glGetBooleanIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetBooleanIndexedvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetBooleanIndexedvEXT(n, n2, MemoryUtil.getAddress(byteBuffer), glGetBooleanIndexedvEXT);
    }
    
    static native void nglGetBooleanIndexedvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static boolean glGetBooleanIndexedEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBooleanIndexedvEXT = capabilities.glGetBooleanIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetBooleanIndexedvEXT);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, 1);
        nglGetBooleanIndexedvEXT(n, n2, MemoryUtil.getAddress(bufferByte), glGetBooleanIndexedvEXT);
        return bufferByte.get(0) == 1;
    }
    
    public static void glGetIntegerIndexedEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetIntegerIndexedvEXT = GLContext.getCapabilities().glGetIntegerIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetIntegerIndexedvEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetIntegerIndexedvEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetIntegerIndexedvEXT);
    }
    
    static native void nglGetIntegerIndexedvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetIntegerIndexedEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetIntegerIndexedvEXT = capabilities.glGetIntegerIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetIntegerIndexedvEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetIntegerIndexedvEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetIntegerIndexedvEXT);
        return bufferInt.get(0);
    }
    
    public static void glEnableIndexedEXT(final int n, final int n2) {
        final long glEnableIndexedEXT = GLContext.getCapabilities().glEnableIndexedEXT;
        BufferChecks.checkFunctionAddress(glEnableIndexedEXT);
        nglEnableIndexedEXT(n, n2, glEnableIndexedEXT);
    }
    
    static native void nglEnableIndexedEXT(final int p0, final int p1, final long p2);
    
    public static void glDisableIndexedEXT(final int n, final int n2) {
        final long glDisableIndexedEXT = GLContext.getCapabilities().glDisableIndexedEXT;
        BufferChecks.checkFunctionAddress(glDisableIndexedEXT);
        nglDisableIndexedEXT(n, n2, glDisableIndexedEXT);
    }
    
    static native void nglDisableIndexedEXT(final int p0, final int p1, final long p2);
    
    public static boolean glIsEnabledIndexedEXT(final int n, final int n2) {
        final long glIsEnabledIndexedEXT = GLContext.getCapabilities().glIsEnabledIndexedEXT;
        BufferChecks.checkFunctionAddress(glIsEnabledIndexedEXT);
        return nglIsEnabledIndexedEXT(n, n2, glIsEnabledIndexedEXT);
    }
    
    static native boolean nglIsEnabledIndexedEXT(final int p0, final int p1, final long p2);
}
