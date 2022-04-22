package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class APPLEElementArray
{
    public static final int GL_ELEMENT_ARRAY_APPLE = 34664;
    public static final int GL_ELEMENT_ARRAY_TYPE_APPLE = 34665;
    public static final int GL_ELEMENT_ARRAY_POINTER_APPLE = 34666;
    
    private APPLEElementArray() {
    }
    
    public static void glElementPointerAPPLE(final ByteBuffer byteBuffer) {
        final long glElementPointerAPPLE = GLContext.getCapabilities().glElementPointerAPPLE;
        BufferChecks.checkFunctionAddress(glElementPointerAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        nglElementPointerAPPLE(5121, MemoryUtil.getAddress(byteBuffer), glElementPointerAPPLE);
    }
    
    public static void glElementPointerAPPLE(final IntBuffer intBuffer) {
        final long glElementPointerAPPLE = GLContext.getCapabilities().glElementPointerAPPLE;
        BufferChecks.checkFunctionAddress(glElementPointerAPPLE);
        BufferChecks.checkDirect(intBuffer);
        nglElementPointerAPPLE(5125, MemoryUtil.getAddress(intBuffer), glElementPointerAPPLE);
    }
    
    public static void glElementPointerAPPLE(final ShortBuffer shortBuffer) {
        final long glElementPointerAPPLE = GLContext.getCapabilities().glElementPointerAPPLE;
        BufferChecks.checkFunctionAddress(glElementPointerAPPLE);
        BufferChecks.checkDirect(shortBuffer);
        nglElementPointerAPPLE(5123, MemoryUtil.getAddress(shortBuffer), glElementPointerAPPLE);
    }
    
    static native void nglElementPointerAPPLE(final int p0, final long p1, final long p2);
    
    public static void glDrawElementArrayAPPLE(final int n, final int n2, final int n3) {
        final long glDrawElementArrayAPPLE = GLContext.getCapabilities().glDrawElementArrayAPPLE;
        BufferChecks.checkFunctionAddress(glDrawElementArrayAPPLE);
        nglDrawElementArrayAPPLE(n, n2, n3, glDrawElementArrayAPPLE);
    }
    
    static native void nglDrawElementArrayAPPLE(final int p0, final int p1, final int p2, final long p3);
    
    public static void glDrawRangeElementArrayAPPLE(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glDrawRangeElementArrayAPPLE = GLContext.getCapabilities().glDrawRangeElementArrayAPPLE;
        BufferChecks.checkFunctionAddress(glDrawRangeElementArrayAPPLE);
        nglDrawRangeElementArrayAPPLE(n, n2, n3, n4, n5, glDrawRangeElementArrayAPPLE);
    }
    
    static native void nglDrawRangeElementArrayAPPLE(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementArrayAPPLE(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glMultiDrawElementArrayAPPLE = GLContext.getCapabilities().glMultiDrawElementArrayAPPLE;
        BufferChecks.checkFunctionAddress(glMultiDrawElementArrayAPPLE);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(intBuffer2, intBuffer.remaining());
        nglMultiDrawElementArrayAPPLE(n, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), intBuffer.remaining(), glMultiDrawElementArrayAPPLE);
    }
    
    static native void nglMultiDrawElementArrayAPPLE(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glMultiDrawRangeElementArrayAPPLE(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glMultiDrawRangeElementArrayAPPLE = GLContext.getCapabilities().glMultiDrawRangeElementArrayAPPLE;
        BufferChecks.checkFunctionAddress(glMultiDrawRangeElementArrayAPPLE);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(intBuffer2, intBuffer.remaining());
        nglMultiDrawRangeElementArrayAPPLE(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), intBuffer.remaining(), glMultiDrawRangeElementArrayAPPLE);
    }
    
    static native void nglMultiDrawRangeElementArrayAPPLE(final int p0, final int p1, final int p2, final long p3, final long p4, final int p5, final long p6);
}
