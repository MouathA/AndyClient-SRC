package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class LinuxPbufferPeerInfo extends LinuxPeerInfo
{
    LinuxPbufferPeerInfo(final int n, final int n2, final PixelFormat pixelFormat) throws LWJGLException {
        nInitHandle(LinuxDisplay.getDisplay(), LinuxDisplay.getDefaultScreen(), this.getHandle(), n, n2, pixelFormat);
    }
    
    private static native void nInitHandle(final long p0, final int p1, final ByteBuffer p2, final int p3, final int p4, final PixelFormat p5) throws LWJGLException;
    
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
