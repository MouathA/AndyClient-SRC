package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTTextureInteger
{
    public static final int GL_RGBA_INTEGER_MODE_EXT = 36254;
    public static final int GL_RGBA32UI_EXT = 36208;
    public static final int GL_RGB32UI_EXT = 36209;
    public static final int GL_ALPHA32UI_EXT = 36210;
    public static final int GL_INTENSITY32UI_EXT = 36211;
    public static final int GL_LUMINANCE32UI_EXT = 36212;
    public static final int GL_LUMINANCE_ALPHA32UI_EXT = 36213;
    public static final int GL_RGBA16UI_EXT = 36214;
    public static final int GL_RGB16UI_EXT = 36215;
    public static final int GL_ALPHA16UI_EXT = 36216;
    public static final int GL_INTENSITY16UI_EXT = 36217;
    public static final int GL_LUMINANCE16UI_EXT = 36218;
    public static final int GL_LUMINANCE_ALPHA16UI_EXT = 36219;
    public static final int GL_RGBA8UI_EXT = 36220;
    public static final int GL_RGB8UI_EXT = 36221;
    public static final int GL_ALPHA8UI_EXT = 36222;
    public static final int GL_INTENSITY8UI_EXT = 36223;
    public static final int GL_LUMINANCE8UI_EXT = 36224;
    public static final int GL_LUMINANCE_ALPHA8UI_EXT = 36225;
    public static final int GL_RGBA32I_EXT = 36226;
    public static final int GL_RGB32I_EXT = 36227;
    public static final int GL_ALPHA32I_EXT = 36228;
    public static final int GL_INTENSITY32I_EXT = 36229;
    public static final int GL_LUMINANCE32I_EXT = 36230;
    public static final int GL_LUMINANCE_ALPHA32I_EXT = 36231;
    public static final int GL_RGBA16I_EXT = 36232;
    public static final int GL_RGB16I_EXT = 36233;
    public static final int GL_ALPHA16I_EXT = 36234;
    public static final int GL_INTENSITY16I_EXT = 36235;
    public static final int GL_LUMINANCE16I_EXT = 36236;
    public static final int GL_LUMINANCE_ALPHA16I_EXT = 36237;
    public static final int GL_RGBA8I_EXT = 36238;
    public static final int GL_RGB8I_EXT = 36239;
    public static final int GL_ALPHA8I_EXT = 36240;
    public static final int GL_INTENSITY8I_EXT = 36241;
    public static final int GL_LUMINANCE8I_EXT = 36242;
    public static final int GL_LUMINANCE_ALPHA8I_EXT = 36243;
    public static final int GL_RED_INTEGER_EXT = 36244;
    public static final int GL_GREEN_INTEGER_EXT = 36245;
    public static final int GL_BLUE_INTEGER_EXT = 36246;
    public static final int GL_ALPHA_INTEGER_EXT = 36247;
    public static final int GL_RGB_INTEGER_EXT = 36248;
    public static final int GL_RGBA_INTEGER_EXT = 36249;
    public static final int GL_BGR_INTEGER_EXT = 36250;
    public static final int GL_BGRA_INTEGER_EXT = 36251;
    public static final int GL_LUMINANCE_INTEGER_EXT = 36252;
    public static final int GL_LUMINANCE_ALPHA_INTEGER_EXT = 36253;
    
    private EXTTextureInteger() {
    }
    
    public static void glClearColorIiEXT(final int n, final int n2, final int n3, final int n4) {
        final long glClearColorIiEXT = GLContext.getCapabilities().glClearColorIiEXT;
        BufferChecks.checkFunctionAddress(glClearColorIiEXT);
        nglClearColorIiEXT(n, n2, n3, n4, glClearColorIiEXT);
    }
    
    static native void nglClearColorIiEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glClearColorIuiEXT(final int n, final int n2, final int n3, final int n4) {
        final long glClearColorIuiEXT = GLContext.getCapabilities().glClearColorIuiEXT;
        BufferChecks.checkFunctionAddress(glClearColorIuiEXT);
        nglClearColorIuiEXT(n, n2, n3, n4, glClearColorIuiEXT);
    }
    
    static native void nglClearColorIuiEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTexParameterIEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexParameterIivEXT = GLContext.getCapabilities().glTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glTexParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexParameterIivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glTexParameterIivEXT);
    }
    
    static native void nglTexParameterIivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexParameterIiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexParameterIivEXT = capabilities.glTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glTexParameterIivEXT);
        nglTexParameterIivEXT(n, n2, APIUtil.getInt(capabilities, n3), glTexParameterIivEXT);
    }
    
    public static void glTexParameterIuEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexParameterIuivEXT = GLContext.getCapabilities().glTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glTexParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexParameterIuivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glTexParameterIuivEXT);
    }
    
    static native void nglTexParameterIuivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexParameterIuiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexParameterIuivEXT = capabilities.glTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glTexParameterIuivEXT);
        nglTexParameterIuivEXT(n, n2, APIUtil.getInt(capabilities, n3), glTexParameterIuivEXT);
    }
    
    public static void glGetTexParameterIEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexParameterIivEXT = GLContext.getCapabilities().glGetTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetTexParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexParameterIivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexParameterIivEXT);
    }
    
    static native void nglGetTexParameterIivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexParameterIiEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameterIivEXT = capabilities.glGetTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetTexParameterIivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexParameterIivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexParameterIivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetTexParameterIuEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexParameterIuivEXT = GLContext.getCapabilities().glGetTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetTexParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexParameterIuivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexParameterIuivEXT);
    }
    
    static native void nglGetTexParameterIuivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexParameterIuiEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameterIuivEXT = capabilities.glGetTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetTexParameterIuivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexParameterIuivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexParameterIuivEXT);
        return bufferInt.get(0);
    }
}
