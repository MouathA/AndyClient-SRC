package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

final class WindowsContextImplementation implements ContextImplementation
{
    public ByteBuffer create(final PeerInfo peerInfo, final IntBuffer intBuffer, final ByteBuffer byteBuffer) throws LWJGLException {
        final ByteBuffer nCreate = nCreate(peerInfo.lockAndGetHandle(), intBuffer, byteBuffer);
        peerInfo.unlock();
        return nCreate;
    }
    
    private static native ByteBuffer nCreate(final ByteBuffer p0, final IntBuffer p1, final ByteBuffer p2) throws LWJGLException;
    
    native long getHGLRC(final ByteBuffer p0);
    
    native long getHDC(final ByteBuffer p0);
    
    public void swapBuffers() throws LWJGLException {
        final ContextGL currentContext = ContextGL.getCurrentContext();
        if (currentContext == null) {
            throw new IllegalStateException("No context is current");
        }
        // monitorenter(contextGL = currentContext)
        final PeerInfo peerInfo = currentContext.getPeerInfo();
        nSwapBuffers(peerInfo.lockAndGetHandle());
        peerInfo.unlock();
    }
    // monitorexit(contextGL)
    
    private static native void nSwapBuffers(final ByteBuffer p0) throws LWJGLException;
    
    public void releaseDrawable(final ByteBuffer byteBuffer) throws LWJGLException {
    }
    
    public void update(final ByteBuffer byteBuffer) {
    }
    
    public void releaseCurrentContext() throws LWJGLException {
    }
    
    private static native void nReleaseCurrentContext() throws LWJGLException;
    
    public void makeCurrent(final PeerInfo peerInfo, final ByteBuffer byteBuffer) throws LWJGLException {
        nMakeCurrent(peerInfo.lockAndGetHandle(), byteBuffer);
        peerInfo.unlock();
    }
    
    private static native void nMakeCurrent(final ByteBuffer p0, final ByteBuffer p1) throws LWJGLException;
    
    public boolean isCurrent(final ByteBuffer byteBuffer) throws LWJGLException {
        return nIsCurrent(byteBuffer);
    }
    
    private static native boolean nIsCurrent(final ByteBuffer p0) throws LWJGLException;
    
    public void setSwapInterval(final int n) {
        if (!nSetSwapInterval(n)) {
            LWJGLUtil.log("Failed to set swap interval");
        }
    }
    
    private static native boolean nSetSwapInterval(final int p0);
    
    public void destroy(final PeerInfo peerInfo, final ByteBuffer byteBuffer) throws LWJGLException {
        nDestroy(byteBuffer);
    }
    
    private static native void nDestroy(final ByteBuffer p0) throws LWJGLException;
}
