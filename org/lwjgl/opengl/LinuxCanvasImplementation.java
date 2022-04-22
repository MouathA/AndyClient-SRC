package org.lwjgl.opengl;

import java.lang.reflect.*;
import java.security.*;
import org.lwjgl.*;
import java.awt.*;

final class LinuxCanvasImplementation implements AWTCanvasImplementation
{
    static int getScreenFromDevice(final GraphicsDevice graphicsDevice) throws LWJGLException {
        return (int)AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction(graphicsDevice) {
            final GraphicsDevice val$device;
            
            public Method run() throws Exception {
                return this.val$device.getClass().getMethod("getScreen", (Class<?>[])new Class[0]);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        }).invoke(graphicsDevice, new Object[0]);
    }
    
    private static int getVisualIDFromConfiguration(final GraphicsConfiguration graphicsConfiguration) throws LWJGLException {
        return (int)AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction(graphicsConfiguration) {
            final GraphicsConfiguration val$configuration;
            
            public Method run() throws Exception {
                return this.val$configuration.getClass().getMethod("getVisual", (Class<?>[])new Class[0]);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        }).invoke(graphicsConfiguration, new Object[0]);
    }
    
    public PeerInfo createPeerInfo(final Canvas canvas, final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return new LinuxAWTGLCanvasPeerInfo(canvas);
    }
    
    public GraphicsConfiguration findConfiguration(final GraphicsDevice graphicsDevice, final PixelFormat pixelFormat) throws LWJGLException {
        final int visualIDFromFormat = findVisualIDFromFormat(getScreenFromDevice(graphicsDevice), pixelFormat);
        final GraphicsConfiguration[] configurations = graphicsDevice.getConfigurations();
        while (0 < configurations.length) {
            final GraphicsConfiguration graphicsConfiguration = configurations[0];
            if (getVisualIDFromConfiguration(graphicsConfiguration) == visualIDFromFormat) {
                return graphicsConfiguration;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private static int findVisualIDFromFormat(final int n, final PixelFormat pixelFormat) throws LWJGLException {
        return nFindVisualIDFromFormat(LinuxDisplay.getDisplay(), n, pixelFormat);
    }
    
    private static native int nFindVisualIDFromFormat(final long p0, final int p1, final PixelFormat p2) throws LWJGLException;
}
