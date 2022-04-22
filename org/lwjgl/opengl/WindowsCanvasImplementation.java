package org.lwjgl.opengl;

import org.lwjgl.*;
import java.awt.*;
import java.security.*;

final class WindowsCanvasImplementation implements AWTCanvasImplementation
{
    public PeerInfo createPeerInfo(final Canvas canvas, final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return new WindowsAWTGLCanvasPeerInfo(canvas, pixelFormat);
    }
    
    public GraphicsConfiguration findConfiguration(final GraphicsDevice graphicsDevice, final PixelFormat pixelFormat) throws LWJGLException {
        return null;
    }
    
    static {
        Toolkit.getDefaultToolkit();
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public Object run() {
                System.loadLibrary("jawt");
                return null;
            }
        });
    }
}
