package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class MacOSXPbufferPeerInfo extends MacOSXPeerInfo
{
    MacOSXPbufferPeerInfo(final int n, final int n2, final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        super(pixelFormat, contextAttribs, false, false, true, false);
        nCreate(this.getHandle(), n, n2);
    }
    
    private static native void nCreate(final ByteBuffer p0, final int p1, final int p2) throws LWJGLException;
    
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
