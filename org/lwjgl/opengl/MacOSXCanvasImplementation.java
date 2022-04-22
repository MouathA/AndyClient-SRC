package org.lwjgl.opengl;

import org.lwjgl.*;
import java.awt.*;

final class MacOSXCanvasImplementation implements AWTCanvasImplementation
{
    public PeerInfo createPeerInfo(final Canvas canvas, final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return new MacOSXAWTGLCanvasPeerInfo(canvas, pixelFormat, contextAttribs, true);
    }
    
    public GraphicsConfiguration findConfiguration(final GraphicsDevice graphicsDevice, final PixelFormat pixelFormat) throws LWJGLException {
        return null;
    }
}
