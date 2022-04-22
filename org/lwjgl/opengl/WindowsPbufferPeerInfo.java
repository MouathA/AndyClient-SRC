package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class WindowsPbufferPeerInfo extends WindowsPeerInfo
{
    WindowsPbufferPeerInfo(final int n, final int n2, final PixelFormat pixelFormat, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        nCreate(this.getHandle(), n, n2, pixelFormat, intBuffer, intBuffer2);
    }
    
    private static native void nCreate(final ByteBuffer p0, final int p1, final int p2, final PixelFormat p3, final IntBuffer p4, final IntBuffer p5) throws LWJGLException;
    
    public boolean isBufferLost() {
        return nIsBufferLost(this.getHandle());
    }
    
    private static native boolean nIsBufferLost(final ByteBuffer p0);
    
    public void setPbufferAttrib(final int n, final int n2) {
        nSetPbufferAttrib(this.getHandle(), n, n2);
    }
    
    private static native void nSetPbufferAttrib(final ByteBuffer p0, final int p1, final int p2);
    
    public void bindTexImageToPbuffer(final int n) {
        nBindTexImageToPbuffer(this.getHandle(), n);
    }
    
    private static native void nBindTexImageToPbuffer(final ByteBuffer p0, final int p1);
    
    public void releaseTexImageFromPbuffer(final int n) {
        nReleaseTexImageFromPbuffer(this.getHandle(), n);
    }
    
    private static native void nReleaseTexImageFromPbuffer(final ByteBuffer p0, final int p1);
    
    @Override
    public void destroy() {
        nDestroy(this.getHandle());
    }
    
    private static native void nDestroy(final ByteBuffer p0);
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
    }
    
    @Override
    protected void doUnlock() throws LWJGLException {
    }
}
