package org.lwjgl.opengl;

import java.awt.*;
import java.nio.*;
import org.lwjgl.*;

final class LinuxAWTGLCanvasPeerInfo extends LinuxPeerInfo
{
    private final Canvas component;
    private final AWTSurfaceLock awt_surface;
    private int screen;
    
    LinuxAWTGLCanvasPeerInfo(final Canvas component) {
        this.awt_surface = new AWTSurfaceLock();
        this.screen = -1;
        this.component = component;
    }
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
        final ByteBuffer lockAndGetHandle = this.awt_surface.lockAndGetHandle(this.component);
        if (this.screen == -1) {
            this.screen = getScreenFromSurfaceInfo(lockAndGetHandle);
        }
        nInitHandle(this.screen, lockAndGetHandle, this.getHandle());
    }
    
    private static native int getScreenFromSurfaceInfo(final ByteBuffer p0) throws LWJGLException;
    
    private static native void nInitHandle(final int p0, final ByteBuffer p1, final ByteBuffer p2) throws LWJGLException;
    
    @Override
    protected void doUnlock() throws LWJGLException {
        this.awt_surface.unlock();
    }
}
