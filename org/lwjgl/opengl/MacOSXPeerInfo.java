package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

abstract class MacOSXPeerInfo extends PeerInfo
{
    MacOSXPeerInfo(final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final boolean b, final boolean b2, final boolean b3, final boolean b4) throws LWJGLException {
        super(createHandle());
        final boolean b5 = contextAttribs != null && (3 < contextAttribs.getMajorVersion() || (contextAttribs.getMajorVersion() == 3 && 2 <= contextAttribs.getMinorVersion())) && contextAttribs.isProfileCore();
        if (b5 && !LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 7)) {
            throw new LWJGLException("OpenGL 3.2+ requested, but it requires MacOS X 10.7 or newer");
        }
        this.choosePixelFormat(pixelFormat, b5, b, b2, b3, b4);
    }
    
    private static native ByteBuffer createHandle();
    
    private void choosePixelFormat(final PixelFormat pixelFormat, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5) throws LWJGLException {
        nChoosePixelFormat(this.getHandle(), pixelFormat, b, b2, b3, b4, b5);
    }
    
    private static native void nChoosePixelFormat(final ByteBuffer p0, final PixelFormat p1, final boolean p2, final boolean p3, final boolean p4, final boolean p5, final boolean p6) throws LWJGLException;
    
    @Override
    public void destroy() {
        nDestroy(this.getHandle());
    }
    
    private static native void nDestroy(final ByteBuffer p0);
}
