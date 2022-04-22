package org.lwjgl.opengl;

import java.awt.*;
import org.lwjgl.*;
import java.nio.*;

final class WindowsAWTGLCanvasPeerInfo extends WindowsPeerInfo
{
    private final Canvas component;
    private final AWTSurfaceLock awt_surface;
    private final PixelFormat pixel_format;
    private boolean has_pixel_format;
    
    WindowsAWTGLCanvasPeerInfo(final Canvas component, final PixelFormat pixel_format) {
        this.awt_surface = new AWTSurfaceLock();
        this.component = component;
        this.pixel_format = pixel_format;
    }
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
        nInitHandle(this.awt_surface.lockAndGetHandle(this.component), this.getHandle());
        if (!this.has_pixel_format && this.pixel_format != null) {
            WindowsPeerInfo.setPixelFormat(this.getHdc(), WindowsPeerInfo.choosePixelFormat(this.getHdc(), this.component.getX(), this.component.getY(), this.pixel_format, null, true, true, false, true));
            this.has_pixel_format = true;
        }
    }
    
    private static native void nInitHandle(final ByteBuffer p0, final ByteBuffer p1) throws LWJGLException;
    
    @Override
    protected void doUnlock() throws LWJGLException {
        this.awt_surface.unlock();
    }
}
