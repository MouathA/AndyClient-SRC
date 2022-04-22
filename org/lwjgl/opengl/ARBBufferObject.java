package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public class ARBBufferObject
{
    public static final int GL_STREAM_DRAW_ARB = 35040;
    public static final int GL_STREAM_READ_ARB = 35041;
    public static final int GL_STREAM_COPY_ARB = 35042;
    public static final int GL_STATIC_DRAW_ARB = 35044;
    public static final int GL_STATIC_READ_ARB = 35045;
    public static final int GL_STATIC_COPY_ARB = 35046;
    public static final int GL_DYNAMIC_DRAW_ARB = 35048;
    public static final int GL_DYNAMIC_READ_ARB = 35049;
    public static final int GL_DYNAMIC_COPY_ARB = 35050;
    public static final int GL_READ_ONLY_ARB = 35000;
    public static final int GL_WRITE_ONLY_ARB = 35001;
    public static final int GL_READ_WRITE_ARB = 35002;
    public static final int GL_BUFFER_SIZE_ARB = 34660;
    public static final int GL_BUFFER_USAGE_ARB = 34661;
    public static final int GL_BUFFER_ACCESS_ARB = 35003;
    public static final int GL_BUFFER_MAPPED_ARB = 35004;
    public static final int GL_BUFFER_MAP_POINTER_ARB = 35005;
    
    public static void glBindBufferARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindBufferARB = capabilities.glBindBufferARB;
        BufferChecks.checkFunctionAddress(glBindBufferARB);
        StateTracker.bindBuffer(capabilities, n, n2);
        nglBindBufferARB(n, n2, glBindBufferARB);
    }
    
    static native void nglBindBufferARB(final int p0, final int p1, final long p2);
    
    public static void glDeleteBuffersARB(final IntBuffer intBuffer) {
        final long glDeleteBuffersARB = GLContext.getCapabilities().glDeleteBuffersARB;
        BufferChecks.checkFunctionAddress(glDeleteBuffersARB);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteBuffersARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteBuffersARB);
    }
    
    static native void nglDeleteBuffersARB(final int p0, final long p1, final long p2);
    
    public static void glDeleteBuffersARB(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteBuffersARB = capabilities.glDeleteBuffersARB;
        BufferChecks.checkFunctionAddress(glDeleteBuffersARB);
        nglDeleteBuffersARB(1, APIUtil.getInt(capabilities, n), glDeleteBuffersARB);
    }
    
    public static void glGenBuffersARB(final IntBuffer intBuffer) {
        final long glGenBuffersARB = GLContext.getCapabilities().glGenBuffersARB;
        BufferChecks.checkFunctionAddress(glGenBuffersARB);
        BufferChecks.checkDirect(intBuffer);
        nglGenBuffersARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenBuffersARB);
    }
    
    static native void nglGenBuffersARB(final int p0, final long p1, final long p2);
    
    public static int glGenBuffersARB() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenBuffersARB = capabilities.glGenBuffersARB;
        BufferChecks.checkFunctionAddress(glGenBuffersARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenBuffersARB(1, MemoryUtil.getAddress(bufferInt), glGenBuffersARB);
        return bufferInt.get(0);
    }
    
    public static boolean glIsBufferARB(final int n) {
        final long glIsBufferARB = GLContext.getCapabilities().glIsBufferARB;
        BufferChecks.checkFunctionAddress(glIsBufferARB);
        return nglIsBufferARB(n, glIsBufferARB);
    }
    
    static native boolean nglIsBufferARB(final int p0, final long p1);
    
    public static void glBufferDataARB(final int n, final long n2, final int n3) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        nglBufferDataARB(n, n2, 0L, n3, glBufferDataARB);
    }
    
    public static void glBufferDataARB(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        BufferChecks.checkDirect(byteBuffer);
        nglBufferDataARB(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glBufferDataARB);
    }
    
    public static void glBufferDataARB(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglBufferDataARB(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glBufferDataARB);
    }
    
    public static void glBufferDataARB(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        BufferChecks.checkDirect(floatBuffer);
        nglBufferDataARB(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glBufferDataARB);
    }
    
    public static void glBufferDataARB(final int n, final IntBuffer intBuffer, final int n2) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        BufferChecks.checkDirect(intBuffer);
        nglBufferDataARB(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glBufferDataARB);
    }
    
    public static void glBufferDataARB(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glBufferDataARB = GLContext.getCapabilities().glBufferDataARB;
        BufferChecks.checkFunctionAddress(glBufferDataARB);
        BufferChecks.checkDirect(shortBuffer);
        nglBufferDataARB(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glBufferDataARB);
    }
    
    static native void nglBufferDataARB(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glBufferSubDataARB(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glBufferSubDataARB = GLContext.getCapabilities().glBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glBufferSubDataARB);
        BufferChecks.checkDirect(byteBuffer);
        nglBufferSubDataARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glBufferSubDataARB);
    }
    
    public static void glBufferSubDataARB(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glBufferSubDataARB = GLContext.getCapabilities().glBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glBufferSubDataARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglBufferSubDataARB(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glBufferSubDataARB);
    }
    
    public static void glBufferSubDataARB(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glBufferSubDataARB = GLContext.getCapabilities().glBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glBufferSubDataARB);
        BufferChecks.checkDirect(floatBuffer);
        nglBufferSubDataARB(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glBufferSubDataARB);
    }
    
    public static void glBufferSubDataARB(final int n, final long n2, final IntBuffer intBuffer) {
        final long glBufferSubDataARB = GLContext.getCapabilities().glBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glBufferSubDataARB);
        BufferChecks.checkDirect(intBuffer);
        nglBufferSubDataARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glBufferSubDataARB);
    }
    
    public static void glBufferSubDataARB(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glBufferSubDataARB = GLContext.getCapabilities().glBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glBufferSubDataARB);
        BufferChecks.checkDirect(shortBuffer);
        nglBufferSubDataARB(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glBufferSubDataARB);
    }
    
    static native void nglBufferSubDataARB(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glGetBufferSubDataARB(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glGetBufferSubDataARB = GLContext.getCapabilities().glGetBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glGetBufferSubDataARB);
        BufferChecks.checkDirect(byteBuffer);
        nglGetBufferSubDataARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetBufferSubDataARB);
    }
    
    public static void glGetBufferSubDataARB(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glGetBufferSubDataARB = GLContext.getCapabilities().glGetBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glGetBufferSubDataARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetBufferSubDataARB(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetBufferSubDataARB);
    }
    
    public static void glGetBufferSubDataARB(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glGetBufferSubDataARB = GLContext.getCapabilities().glGetBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glGetBufferSubDataARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetBufferSubDataARB(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetBufferSubDataARB);
    }
    
    public static void glGetBufferSubDataARB(final int n, final long n2, final IntBuffer intBuffer) {
        final long glGetBufferSubDataARB = GLContext.getCapabilities().glGetBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glGetBufferSubDataARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetBufferSubDataARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetBufferSubDataARB);
    }
    
    public static void glGetBufferSubDataARB(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glGetBufferSubDataARB = GLContext.getCapabilities().glGetBufferSubDataARB;
        BufferChecks.checkFunctionAddress(glGetBufferSubDataARB);
        BufferChecks.checkDirect(shortBuffer);
        nglGetBufferSubDataARB(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetBufferSubDataARB);
    }
    
    static native void nglGetBufferSubDataARB(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static ByteBuffer glMapBufferARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glMapBufferARB = GLContext.getCapabilities().glMapBufferARB;
        BufferChecks.checkFunctionAddress(glMapBufferARB);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapBufferARB = nglMapBufferARB(n, n2, glGetBufferParameteriARB(n, 34660), byteBuffer, glMapBufferARB);
        return (LWJGLUtil.CHECKS && nglMapBufferARB == null) ? null : nglMapBufferARB.order(ByteOrder.nativeOrder());
    }
    
    public static ByteBuffer glMapBufferARB(final int n, final int n2, final long n3, final ByteBuffer byteBuffer) {
        final long glMapBufferARB = GLContext.getCapabilities().glMapBufferARB;
        BufferChecks.checkFunctionAddress(glMapBufferARB);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapBufferARB = nglMapBufferARB(n, n2, n3, byteBuffer, glMapBufferARB);
        return (LWJGLUtil.CHECKS && nglMapBufferARB == null) ? null : nglMapBufferARB.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapBufferARB(final int p0, final int p1, final long p2, final ByteBuffer p3, final long p4);
    
    public static boolean glUnmapBufferARB(final int n) {
        final long glUnmapBufferARB = GLContext.getCapabilities().glUnmapBufferARB;
        BufferChecks.checkFunctionAddress(glUnmapBufferARB);
        return nglUnmapBufferARB(n, glUnmapBufferARB);
    }
    
    static native boolean nglUnmapBufferARB(final int p0, final long p1);
    
    public static void glGetBufferParameterARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetBufferParameterivARB = GLContext.getCapabilities().glGetBufferParameterivARB;
        BufferChecks.checkFunctionAddress(glGetBufferParameterivARB);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetBufferParameterivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetBufferParameterivARB);
    }
    
    static native void nglGetBufferParameterivARB(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetBufferParameterARB(final int n, final int n2) {
        return glGetBufferParameteriARB(n, n2);
    }
    
    public static int glGetBufferParameteriARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBufferParameterivARB = capabilities.glGetBufferParameterivARB;
        BufferChecks.checkFunctionAddress(glGetBufferParameterivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetBufferParameterivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetBufferParameterivARB);
        return bufferInt.get(0);
    }
    
    public static ByteBuffer glGetBufferPointerARB(final int n, final int n2) {
        final long glGetBufferPointervARB = GLContext.getCapabilities().glGetBufferPointervARB;
        BufferChecks.checkFunctionAddress(glGetBufferPointervARB);
        final ByteBuffer nglGetBufferPointervARB = nglGetBufferPointervARB(n, n2, glGetBufferParameteriARB(n, 34660), glGetBufferPointervARB);
        return (LWJGLUtil.CHECKS && nglGetBufferPointervARB == null) ? null : nglGetBufferPointervARB.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetBufferPointervARB(final int p0, final int p1, final long p2, final long p3);
}
