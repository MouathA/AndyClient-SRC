package org.lwjgl.opengl;

import org.lwjgl.opengles.*;
import org.lwjgl.*;
import java.nio.*;

final class WindowsDisplayPeerInfo extends WindowsPeerInfo
{
    final boolean egl;
    
    WindowsDisplayPeerInfo(final boolean egl) throws LWJGLException {
        this.egl = egl;
        if (egl) {
            GLContext.loadOpenGLLibrary();
        }
    }
    
    void initDC(final long n, final long n2) throws LWJGLException {
        nInitDC(this.getHandle(), n, n2);
    }
    
    private static native void nInitDC(final ByteBuffer p0, final long p1, final long p2);
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
    }
    
    @Override
    protected void doUnlock() throws LWJGLException {
    }
    
    @Override
    public void destroy() {
        super.destroy();
        if (this.egl) {
            GLContext.unloadOpenGLLibrary();
        }
    }
}
