package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

abstract class WindowsPeerInfo extends PeerInfo
{
    protected WindowsPeerInfo() {
        super(createHandle());
    }
    
    private static native ByteBuffer createHandle();
    
    protected static int choosePixelFormat(final long n, final int n2, final int n3, final PixelFormat pixelFormat, final IntBuffer intBuffer, final boolean b, final boolean b2, final boolean b3, final boolean b4) throws LWJGLException {
        return nChoosePixelFormat(n, n2, n3, pixelFormat, intBuffer, b, b2, b3, b4);
    }
    
    private static native int nChoosePixelFormat(final long p0, final int p1, final int p2, final PixelFormat p3, final IntBuffer p4, final boolean p5, final boolean p6, final boolean p7, final boolean p8) throws LWJGLException;
    
    protected static native void setPixelFormat(final long p0, final int p1) throws LWJGLException;
    
    public final long getHdc() {
        return nGetHdc(this.getHandle());
    }
    
    private static native long nGetHdc(final ByteBuffer p0);
    
    public final long getHwnd() {
        return nGetHwnd(this.getHandle());
    }
    
    private static native long nGetHwnd(final ByteBuffer p0);
}
