package org.lwjgl.opengl;

import org.lwjgl.*;
import java.awt.*;

final class MacOSXDisplayPeerInfo extends MacOSXCanvasPeerInfo
{
    private boolean locked;
    
    MacOSXDisplayPeerInfo(final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final boolean b) throws LWJGLException {
        super(pixelFormat, contextAttribs, b);
    }
    
    @Override
    protected void doLockAndInitHandle() throws LWJGLException {
        if (this.locked) {
            throw new RuntimeException("Already locked");
        }
        final Canvas canvas = ((MacOSXDisplay)Display.getImplementation()).getCanvas();
        if (canvas != null) {
            this.initHandle(canvas);
            this.locked = true;
        }
    }
    
    @Override
    protected void doUnlock() throws LWJGLException {
        if (this.locked) {
            super.doUnlock();
            this.locked = false;
        }
    }
}
