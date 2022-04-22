package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.opengles.*;
import org.lwjgl.*;

abstract class DrawableGLES implements DrawableLWJGL
{
    protected PixelFormat pixel_format;
    protected EGLDisplay eglDisplay;
    protected EGLConfig eglConfig;
    protected EGLSurface eglSurface;
    protected ContextGLES context;
    protected Drawable shared_drawable;
    
    protected DrawableGLES() {
    }
    
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.pixel_format = (PixelFormat)pixelFormatLWJGL;
    }
    // monitorexit(lock)
    
    public PixelFormatLWJGL getPixelFormat() {
        // monitorenter(lock = GlobalLock.lock)
        final PixelFormat pixel_format = this.pixel_format;
        // monitorexit(lock)
        return (PixelFormatLWJGL)pixel_format;
    }
    
    public void initialize(final long n, final long n2, final int n3, final PixelFormat pixelFormat) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (this.eglSurface != null) {
            this.eglSurface.destroy();
            this.eglSurface = null;
        }
        if (this.eglDisplay != null) {
            this.eglDisplay.terminate();
            this.eglDisplay = null;
        }
        final EGLDisplay eglGetDisplay = EGL.eglGetDisplay((long)(int)n2);
        final EGLConfig[] chooseConfig = eglGetDisplay.chooseConfig(pixelFormat.getAttribBuffer(eglGetDisplay, n3, new int[] { 12329, 0, 12352, 4, 12333, 0 }), (EGLConfig[])null, BufferUtils.createIntBuffer(1));
        if (chooseConfig.length == 0) {
            throw new LWJGLException("No EGLConfigs found for the specified PixelFormat.");
        }
        final EGLConfig bestMatch = pixelFormat.getBestMatch(chooseConfig);
        final EGLSurface windowSurface = eglGetDisplay.createWindowSurface(bestMatch, n, (IntBuffer)null);
        pixelFormat.setSurfaceAttribs(windowSurface);
        this.eglDisplay = eglGetDisplay;
        this.eglConfig = bestMatch;
        this.eglSurface = windowSurface;
        if (this.context != null) {
            this.context.getEGLContext().setDisplay(eglGetDisplay);
        }
    }
    // monitorexit(lock)
    
    public void createContext(final ContextAttribs contextAttribs, final Drawable shared_drawable) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.context = new ContextGLES(this, contextAttribs, (shared_drawable != null) ? ((DrawableGLES)shared_drawable).getContext() : null);
        this.shared_drawable = shared_drawable;
    }
    // monitorexit(lock)
    
    Drawable getSharedDrawable() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.shared_drawable;
    }
    
    public EGLDisplay getEGLDisplay() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.eglDisplay;
    }
    
    public EGLConfig getEGLConfig() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.eglConfig;
    }
    
    public EGLSurface getEGLSurface() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.eglSurface;
    }
    
    public ContextGLES getContext() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return this.context;
    }
    
    public Context createSharedContext() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        // monitorexit(lock)
        return new ContextGLES(this, this.context.getContextAttribs(), this.context);
    }
    
    public void checkGLError() {
        Util.checkGLError();
    }
    
    public void setSwapInterval(final int swapInterval) {
        ContextGLES.setSwapInterval(swapInterval);
    }
    
    public void swapBuffers() throws LWJGLException {
    }
    
    public void initContext(final float n, final float n2, final float n3) {
        GLES20.glClearColor(n, n2, n3, 0.0f);
        GLES20.glClear(16384);
    }
    
    public boolean isCurrent() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        // monitorexit(lock)
        return this.context.isCurrent();
    }
    
    public void makeCurrent() throws LWJGLException, PowerManagementEventException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        this.context.makeCurrent();
    }
    // monitorexit(lock)
    
    public void releaseContext() throws LWJGLException, PowerManagementEventException {
        // monitorenter(lock = GlobalLock.lock)
        this.checkDestroyed();
        if (this.context.isCurrent()) {
            this.context.releaseCurrent();
        }
    }
    // monitorexit(lock)
    
    public void destroy() {
        // monitorenter(lock = GlobalLock.lock)
        if (this.context != null) {
            this.releaseContext();
            this.context.forceDestroy();
            this.context = null;
        }
        if (this.eglSurface != null) {
            this.eglSurface.destroy();
            this.eglSurface = null;
        }
        if (this.eglDisplay != null) {
            this.eglDisplay.terminate();
            this.eglDisplay = null;
        }
        this.pixel_format = null;
        this.shared_drawable = null;
    }
    // monitorexit(lock)
    
    protected void checkDestroyed() {
        if (this.context == null) {
            throw new IllegalStateException("The Drawable has no context available.");
        }
    }
    
    public void setCLSharingProperties(final PointerBuffer pointerBuffer) throws LWJGLException {
        throw new UnsupportedOperationException();
    }
    
    public Context getContext() {
        return this.getContext();
    }
}
