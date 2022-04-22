package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBBufferStorage
{
    public static final int GL_MAP_PERSISTENT_BIT = 64;
    public static final int GL_MAP_COHERENT_BIT = 128;
    public static final int GL_DYNAMIC_STORAGE_BIT = 256;
    public static final int GL_CLIENT_STORAGE_BIT = 512;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
    
    private ARBBufferStorage() {
    }
    
    public static void glBufferStorage(final int n, final ByteBuffer byteBuffer, final int n2) {
        GL44.glBufferStorage(n, byteBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        GL44.glBufferStorage(n, doubleBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final FloatBuffer floatBuffer, final int n2) {
        GL44.glBufferStorage(n, floatBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final IntBuffer intBuffer, final int n2) {
        GL44.glBufferStorage(n, intBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final ShortBuffer shortBuffer, final int n2) {
        GL44.glBufferStorage(n, shortBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final LongBuffer longBuffer, final int n2) {
        GL44.glBufferStorage(n, longBuffer, n2);
    }
    
    public static void glBufferStorage(final int n, final long n2, final int n3) {
        GL44.glBufferStorage(n, n2, n3);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferStorageEXT(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glNamedBufferStorageEXT);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferStorageEXT(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glNamedBufferStorageEXT);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferStorageEXT(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glNamedBufferStorageEXT);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final IntBuffer intBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferStorageEXT(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glNamedBufferStorageEXT);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferStorageEXT(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glNamedBufferStorageEXT);
    }
    
    public static void glNamedBufferStorageEXT(final int n, final LongBuffer longBuffer, final int n2) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        BufferChecks.checkDirect(longBuffer);
        nglNamedBufferStorageEXT(n, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), n2, glNamedBufferStorageEXT);
    }
    
    static native void nglNamedBufferStorageEXT(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glNamedBufferStorageEXT(final int n, final long n2, final int n3) {
        final long glNamedBufferStorageEXT = GLContext.getCapabilities().glNamedBufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferStorageEXT);
        nglNamedBufferStorageEXT(n, n2, 0L, n3, glNamedBufferStorageEXT);
    }
}
