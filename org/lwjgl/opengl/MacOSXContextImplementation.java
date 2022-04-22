package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

final class MacOSXContextImplementation implements ContextImplementation
{
    public ByteBuffer create(final PeerInfo peerInfo, final IntBuffer intBuffer, final ByteBuffer byteBuffer) throws LWJGLException {
        final ByteBuffer nCreate = nCreate(peerInfo.lockAndGetHandle(), byteBuffer);
        peerInfo.unlock();
        return nCreate;
    }
    
    private static native ByteBuffer nCreate(final ByteBuffer p0, final ByteBuffer p1) throws LWJGLException;
    
    public void swapBuffers() throws LWJGLException {
        final ContextGL currentContext = ContextGL.getCurrentContext();
        if (currentContext == null) {
            throw new IllegalStateException("No context is current");
        }
        // monitorenter(contextGL = currentContext)
        nSwapBuffers(currentContext.getHandle());
    }
    // monitorexit(contextGL)
    
    native long getCGLShareGroup(final ByteBuffer p0);
    
    private static native void nSwapBuffers(final ByteBuffer p0) throws LWJGLException;
    
    public void update(final ByteBuffer byteBuffer) {
        nUpdate(byteBuffer);
    }
    
    private static native void nUpdate(final ByteBuffer p0);
    
    public void releaseCurrentContext() throws LWJGLException {
    }
    
    private static native void nReleaseCurrentContext() throws LWJGLException;
    
    public void releaseDrawable(final ByteBuffer byteBuffer) throws LWJGLException {
        clearDrawable(byteBuffer);
    }
    
    private static native void clearDrawable(final ByteBuffer p0) throws LWJGLException;
    
    static void resetView(final PeerInfo peerInfo, final ContextGL contextGL) throws LWJGLException {
        final ByteBuffer lockAndGetHandle = peerInfo.lockAndGetHandle();
        // monitorenter(contextGL)
        clearDrawable(contextGL.getHandle());
        setView(lockAndGetHandle, contextGL.getHandle());
        // monitorexit(contextGL)
        peerInfo.unlock();
    }
    
    public void makeCurrent(final PeerInfo peerInfo, final ByteBuffer byteBuffer) throws LWJGLException {
        setView(peerInfo.lockAndGetHandle(), byteBuffer);
        nMakeCurrent(byteBuffer);
        peerInfo.unlock();
    }
    
    private static native void setView(final ByteBuffer p0, final ByteBuffer p1) throws LWJGLException;
    
    private static native void nMakeCurrent(final ByteBuffer p0) throws LWJGLException;
    
    public boolean isCurrent(final ByteBuffer byteBuffer) throws LWJGLException {
        return nIsCurrent(byteBuffer);
    }
    
    private static native boolean nIsCurrent(final ByteBuffer p0) throws LWJGLException;
    
    public void setSwapInterval(final int n) {
        final ContextGL currentContext = ContextGL.getCurrentContext();
        // monitorenter(contextGL = currentContext)
        nSetSwapInterval(currentContext.getHandle(), n);
    }
    // monitorexit(contextGL)
    
    private static native void nSetSwapInterval(final ByteBuffer p0, final int p1);
    
    public void destroy(final PeerInfo peerInfo, final ByteBuffer byteBuffer) throws LWJGLException {
        nDestroy(byteBuffer);
    }
    
    private static native void nDestroy(final ByteBuffer p0) throws LWJGLException;
}
