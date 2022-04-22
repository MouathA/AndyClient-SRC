package org.lwjgl.opengl;

import org.lwjgl.*;

abstract class DrawableGL implements DrawableLWJGL
{
    protected PixelFormat pixel_format;
    protected PeerInfo peer_info;
    protected ContextGL context;
    
    protected DrawableGL() {
    }
    
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL) throws LWJGLException {
        throw new UnsupportedOperationException();
    }
    
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL, final ContextAttribs contextAttribs) throws LWJGLException {
        this.pixel_format = (PixelFormat)pixelFormatLWJGL;
        this.peer_info = Display.getImplementation().createPeerInfo(this.pixel_format, contextAttribs);
    }
    
    public PixelFormatLWJGL getPixelFormat() {
        return this.pixel_format;
    }
    
    public ContextGL getContext() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.context;
    }
    
    public ContextGL createSharedContext() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        // monitorexit(lock)
        return new ContextGL(this.peer_info, this.context.getContextAttribs(), this.context);
    }
    
    public void checkGLError() {
    }
    
    public void setSwapInterval(final int swapInterval) {
        ContextGL.setSwapInterval(swapInterval);
    }
    
    public void swapBuffers() throws LWJGLException {
    }
    
    public void initContext(final float n, final float n2, final float n3) {
        GL11.glClearColor(n, n2, n3, 0.0f);
        GL11.glClear(16384);
    }
    
    public boolean isCurrent() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        // monitorexit(lock)
        return this.context.isCurrent();
    }
    
    public void makeCurrent() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        this.context.makeCurrent();
    }
    // monitorexit(lock)
    
    public void releaseContext() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        if (this.context.isCurrent()) {
            this.context.releaseCurrent();
        }
    }
    // monitorexit(lock)
    
    public void destroy() {
        // monitorenter(lock = GlobalLock.lock)
        if (this.context == null) {
            // monitorexit(lock)
            return;
        }
        this.releaseContext();
        this.context.forceDestroy();
        this.context = null;
        if (this.peer_info != null) {
            this.peer_info.destroy();
            this.peer_info = null;
        }
    }
    // monitorexit(lock)
    
    public void setCLSharingProperties(final PointerBuffer clSharingProperties) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        this.context.setCLSharingProperties(clSharingProperties);
    }
    // monitorexit(lock)
    
    protected final void checkDestroyed() {
        if (this.context == null) {
            throw new IllegalStateException("The Drawable has no context available.");
        }
    }
    
    public Context createSharedContext() throws LWJGLException {
        return this.createSharedContext();
    }
    
    public Context getContext() {
        return this.getContext();
    }
}
