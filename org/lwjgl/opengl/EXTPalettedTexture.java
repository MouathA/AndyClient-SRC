package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTPalettedTexture
{
    public static final int GL_COLOR_INDEX1_EXT = 32994;
    public static final int GL_COLOR_INDEX2_EXT = 32995;
    public static final int GL_COLOR_INDEX4_EXT = 32996;
    public static final int GL_COLOR_INDEX8_EXT = 32997;
    public static final int GL_COLOR_INDEX12_EXT = 32998;
    public static final int GL_COLOR_INDEX16_EXT = 32999;
    public static final int GL_COLOR_TABLE_FORMAT_EXT = 32984;
    public static final int GL_COLOR_TABLE_WIDTH_EXT = 32985;
    public static final int GL_COLOR_TABLE_RED_SIZE_EXT = 32986;
    public static final int GL_COLOR_TABLE_GREEN_SIZE_EXT = 32987;
    public static final int GL_COLOR_TABLE_BLUE_SIZE_EXT = 32988;
    public static final int GL_COLOR_TABLE_ALPHA_SIZE_EXT = 32989;
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE_EXT = 32990;
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE_EXT = 32991;
    public static final int GL_TEXTURE_INDEX_SIZE_EXT = 33005;
    
    private EXTPalettedTexture() {
    }
    
    public static void glColorTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final long glColorTableEXT = GLContext.getCapabilities().glColorTableEXT;
        BufferChecks.checkFunctionAddress(glColorTableEXT);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n4, n5, n3, 1, 1));
        nglColorTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glColorTableEXT);
    }
    
    public static void glColorTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final long glColorTableEXT = GLContext.getCapabilities().glColorTableEXT;
        BufferChecks.checkFunctionAddress(glColorTableEXT);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n4, n5, n3, 1, 1));
        nglColorTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glColorTableEXT);
    }
    
    public static void glColorTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final long glColorTableEXT = GLContext.getCapabilities().glColorTableEXT;
        BufferChecks.checkFunctionAddress(glColorTableEXT);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n4, n5, n3, 1, 1));
        nglColorTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glColorTableEXT);
    }
    
    public static void glColorTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer) {
        final long glColorTableEXT = GLContext.getCapabilities().glColorTableEXT;
        BufferChecks.checkFunctionAddress(glColorTableEXT);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n4, n5, n3, 1, 1));
        nglColorTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), glColorTableEXT);
    }
    
    public static void glColorTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ShortBuffer shortBuffer) {
        final long glColorTableEXT = GLContext.getCapabilities().glColorTableEXT;
        BufferChecks.checkFunctionAddress(glColorTableEXT);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n4, n5, n3, 1, 1));
        nglColorTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), glColorTableEXT);
    }
    
    static native void nglColorTableEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glColorSubTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final long glColorSubTableEXT = GLContext.getCapabilities().glColorSubTableEXT;
        BufferChecks.checkFunctionAddress(glColorSubTableEXT);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n4, n5, n3, 1, 1));
        nglColorSubTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glColorSubTableEXT);
    }
    
    public static void glColorSubTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final long glColorSubTableEXT = GLContext.getCapabilities().glColorSubTableEXT;
        BufferChecks.checkFunctionAddress(glColorSubTableEXT);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n4, n5, n3, 1, 1));
        nglColorSubTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glColorSubTableEXT);
    }
    
    public static void glColorSubTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final long glColorSubTableEXT = GLContext.getCapabilities().glColorSubTableEXT;
        BufferChecks.checkFunctionAddress(glColorSubTableEXT);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n4, n5, n3, 1, 1));
        nglColorSubTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glColorSubTableEXT);
    }
    
    public static void glColorSubTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer) {
        final long glColorSubTableEXT = GLContext.getCapabilities().glColorSubTableEXT;
        BufferChecks.checkFunctionAddress(glColorSubTableEXT);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n4, n5, n3, 1, 1));
        nglColorSubTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), glColorSubTableEXT);
    }
    
    public static void glColorSubTableEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ShortBuffer shortBuffer) {
        final long glColorSubTableEXT = GLContext.getCapabilities().glColorSubTableEXT;
        BufferChecks.checkFunctionAddress(glColorSubTableEXT);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n4, n5, n3, 1, 1));
        nglColorSubTableEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), glColorSubTableEXT);
    }
    
    static native void nglColorSubTableEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetColorTableEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glGetColorTableEXT = GLContext.getCapabilities().glGetColorTableEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglGetColorTableEXT(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetColorTableEXT);
    }
    
    public static void glGetColorTableEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetColorTableEXT = GLContext.getCapabilities().glGetColorTableEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetColorTableEXT(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetColorTableEXT);
    }
    
    public static void glGetColorTableEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetColorTableEXT = GLContext.getCapabilities().glGetColorTableEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglGetColorTableEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetColorTableEXT);
    }
    
    public static void glGetColorTableEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetColorTableEXT = GLContext.getCapabilities().glGetColorTableEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableEXT);
        BufferChecks.checkDirect(intBuffer);
        nglGetColorTableEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetColorTableEXT);
    }
    
    public static void glGetColorTableEXT(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final long glGetColorTableEXT = GLContext.getCapabilities().glGetColorTableEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableEXT);
        BufferChecks.checkDirect(shortBuffer);
        nglGetColorTableEXT(n, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetColorTableEXT);
    }
    
    static native void nglGetColorTableEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetColorTableParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetColorTableParameterivEXT = GLContext.getCapabilities().glGetColorTableParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetColorTableParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetColorTableParameterivEXT);
    }
    
    static native void nglGetColorTableParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetColorTableParameterEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetColorTableParameterfvEXT = GLContext.getCapabilities().glGetColorTableParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetColorTableParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetColorTableParameterfvEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetColorTableParameterfvEXT);
    }
    
    static native void nglGetColorTableParameterfvEXT(final int p0, final int p1, final long p2, final long p3);
}
