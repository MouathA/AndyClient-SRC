package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ATIElementArray
{
    public static final int GL_ELEMENT_ARRAY_ATI = 34664;
    public static final int GL_ELEMENT_ARRAY_TYPE_ATI = 34665;
    public static final int GL_ELEMENT_ARRAY_POINTER_ATI = 34666;
    
    private ATIElementArray() {
    }
    
    public static void glElementPointerATI(final ByteBuffer byteBuffer) {
        final long glElementPointerATI = GLContext.getCapabilities().glElementPointerATI;
        BufferChecks.checkFunctionAddress(glElementPointerATI);
        BufferChecks.checkDirect(byteBuffer);
        nglElementPointerATI(5121, MemoryUtil.getAddress(byteBuffer), glElementPointerATI);
    }
    
    public static void glElementPointerATI(final IntBuffer intBuffer) {
        final long glElementPointerATI = GLContext.getCapabilities().glElementPointerATI;
        BufferChecks.checkFunctionAddress(glElementPointerATI);
        BufferChecks.checkDirect(intBuffer);
        nglElementPointerATI(5125, MemoryUtil.getAddress(intBuffer), glElementPointerATI);
    }
    
    public static void glElementPointerATI(final ShortBuffer shortBuffer) {
        final long glElementPointerATI = GLContext.getCapabilities().glElementPointerATI;
        BufferChecks.checkFunctionAddress(glElementPointerATI);
        BufferChecks.checkDirect(shortBuffer);
        nglElementPointerATI(5123, MemoryUtil.getAddress(shortBuffer), glElementPointerATI);
    }
    
    static native void nglElementPointerATI(final int p0, final long p1, final long p2);
    
    public static void glDrawElementArrayATI(final int n, final int n2) {
        final long glDrawElementArrayATI = GLContext.getCapabilities().glDrawElementArrayATI;
        BufferChecks.checkFunctionAddress(glDrawElementArrayATI);
        nglDrawElementArrayATI(n, n2, glDrawElementArrayATI);
    }
    
    static native void nglDrawElementArrayATI(final int p0, final int p1, final long p2);
    
    public static void glDrawRangeElementArrayATI(final int n, final int n2, final int n3, final int n4) {
        final long glDrawRangeElementArrayATI = GLContext.getCapabilities().glDrawRangeElementArrayATI;
        BufferChecks.checkFunctionAddress(glDrawRangeElementArrayATI);
        nglDrawRangeElementArrayATI(n, n2, n3, n4, glDrawRangeElementArrayATI);
    }
    
    static native void nglDrawRangeElementArrayATI(final int p0, final int p1, final int p2, final int p3, final long p4);
}
