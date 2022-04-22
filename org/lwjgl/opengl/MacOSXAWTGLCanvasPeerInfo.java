package org.lwjgl.opengl;

import java.awt.*;
import org.lwjgl.*;

final class MacOSXAWTGLCanvasPeerInfo extends MacOSXCanvasPeerInfo
{
    private final Canvas component;
    
    MacOSXAWTGLCanvasPeerInfo(final Canvas component, final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final boolean b) throws LWJGLException {
        super(pixelFormat, contextAttribs, b);
        this.component = component;
    }
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
        this.initHandle(this.component);
    }
}
