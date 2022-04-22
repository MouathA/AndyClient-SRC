package org.lwjgl.opengl;

import org.lwjgl.opengles.*;
import org.lwjgl.*;
import java.nio.*;

final class LinuxDisplayPeerInfo extends LinuxPeerInfo
{
    final boolean egl;
    
    LinuxDisplayPeerInfo() throws LWJGLException {
        this.egl = true;
        GLContext.loadOpenGLLibrary();
    }
    
    LinuxDisplayPeerInfo(final PixelFormat pixelFormat) throws LWJGLException {
        this.egl = false;
        initDefaultPeerInfo(LinuxDisplay.getDisplay(), LinuxDisplay.getDefaultScreen(), this.getHandle(), pixelFormat);
    }
    
    private static native void initDefaultPeerInfo(final long p0, final int p1, final ByteBuffer p2, final PixelFormat p3) throws LWJGLException;
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
        initDrawable(LinuxDisplay.getWindow(), this.getHandle());
    }
    
    private static native void initDrawable(final long p0, final ByteBuffer p1);
    
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
