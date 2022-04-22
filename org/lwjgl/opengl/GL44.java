package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GL44
{
    public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = 33509;
    public static final int GL_MAP_PERSISTENT_BIT = 64;
    public static final int GL_MAP_COHERENT_BIT = 128;
    public static final int GL_DYNAMIC_STORAGE_BIT = 256;
    public static final int GL_CLIENT_STORAGE_BIT = 512;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
    public static final int GL_CLEAR_TEXTURE = 37733;
    public static final int GL_LOCATION_COMPONENT = 37706;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 37707;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 37708;
    public static final int GL_QUERY_RESULT_NO_WAIT = 37268;
    public static final int GL_QUERY_BUFFER = 37266;
    public static final int GL_QUERY_BUFFER_BINDING = 37267;
    public static final int GL_QUERY_BUFFER_BARRIER_BIT = 32768;
    public static final int GL_MIRROR_CLAMP_TO_EDGE = 34627;
    
    private GL44() {
    }
    
    public static void glBufferStorage(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(byteBuffer);
        nglBufferStorage(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glBufferStorage);
    }
    
    public static void glBufferStorage(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(doubleBuffer);
        nglBufferStorage(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glBufferStorage);
    }
    
    public static void glBufferStorage(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(floatBuffer);
        nglBufferStorage(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glBufferStorage);
    }
    
    public static void glBufferStorage(final int n, final IntBuffer intBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(intBuffer);
        nglBufferStorage(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glBufferStorage);
    }
    
    public static void glBufferStorage(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(shortBuffer);
        nglBufferStorage(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glBufferStorage);
    }
    
    public static void glBufferStorage(final int n, final LongBuffer longBuffer, final int n2) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        BufferChecks.checkDirect(longBuffer);
        nglBufferStorage(n, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), n2, glBufferStorage);
    }
    
    static native void nglBufferStorage(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glBufferStorage(final int n, final long n2, final int n3) {
        final long glBufferStorage = GLContext.getCapabilities().glBufferStorage;
        BufferChecks.checkFunctionAddress(glBufferStorage);
        nglBufferStorage(n, n2, 0L, n3, glBufferStorage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(byteBuffer), glClearTexImage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(doubleBuffer), glClearTexImage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(floatBuffer), glClearTexImage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(intBuffer), glClearTexImage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(shortBuffer), glClearTexImage);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final LongBuffer longBuffer) {
        final long glClearTexImage = GLContext.getCapabilities().glClearTexImage;
        BufferChecks.checkFunctionAddress(glClearTexImage);
        if (longBuffer != null) {
            BufferChecks.checkBuffer(longBuffer, 1);
        }
        nglClearTexImage(n, n2, n3, n4, MemoryUtil.getAddressSafe(longBuffer), glClearTexImage);
    }
    
    static native void nglClearTexImage(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(byteBuffer), glClearTexSubImage);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(doubleBuffer), glClearTexSubImage);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(floatBuffer), glClearTexSubImage);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(intBuffer), glClearTexSubImage);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(shortBuffer), glClearTexSubImage);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final LongBuffer longBuffer) {
        final long glClearTexSubImage = GLContext.getCapabilities().glClearTexSubImage;
        BufferChecks.checkFunctionAddress(glClearTexSubImage);
        if (longBuffer != null) {
            BufferChecks.checkBuffer(longBuffer, 1);
        }
        nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(longBuffer), glClearTexSubImage);
    }
    
    static native void nglClearTexSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glBindBuffersBase(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glBindBuffersBase = GLContext.getCapabilities().glBindBuffersBase;
        BufferChecks.checkFunctionAddress(glBindBuffersBase);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n3);
        }
        nglBindBuffersBase(n, n2, n3, MemoryUtil.getAddressSafe(intBuffer), glBindBuffersBase);
    }
    
    static native void nglBindBuffersBase(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBindBuffersRange(final int n, final int n2, final int n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long glBindBuffersRange = GLContext.getCapabilities().glBindBuffersRange;
        BufferChecks.checkFunctionAddress(glBindBuffersRange);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n3);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, n3);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, n3);
        }
        nglBindBuffersRange(n, n2, n3, MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), glBindBuffersRange);
    }
    
    static native void nglBindBuffersRange(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static void glBindTextures(final int n, final int n2, final IntBuffer intBuffer) {
        final long glBindTextures = GLContext.getCapabilities().glBindTextures;
        BufferChecks.checkFunctionAddress(glBindTextures);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n2);
        }
        nglBindTextures(n, n2, MemoryUtil.getAddressSafe(intBuffer), glBindTextures);
    }
    
    static native void nglBindTextures(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindSamplers(final int n, final int n2, final IntBuffer intBuffer) {
        final long glBindSamplers = GLContext.getCapabilities().glBindSamplers;
        BufferChecks.checkFunctionAddress(glBindSamplers);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n2);
        }
        nglBindSamplers(n, n2, MemoryUtil.getAddressSafe(intBuffer), glBindSamplers);
    }
    
    static native void nglBindSamplers(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindImageTextures(final int n, final int n2, final IntBuffer intBuffer) {
        final long glBindImageTextures = GLContext.getCapabilities().glBindImageTextures;
        BufferChecks.checkFunctionAddress(glBindImageTextures);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n2);
        }
        nglBindImageTextures(n, n2, MemoryUtil.getAddressSafe(intBuffer), glBindImageTextures);
    }
    
    static native void nglBindImageTextures(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindVertexBuffers(final int n, final int n2, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer2) {
        final long glBindVertexBuffers = GLContext.getCapabilities().glBindVertexBuffers;
        BufferChecks.checkFunctionAddress(glBindVertexBuffers);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n2);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, n2);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, n2);
        }
        nglBindVertexBuffers(n, n2, MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer2), glBindVertexBuffers);
    }
    
    static native void nglBindVertexBuffers(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
}
