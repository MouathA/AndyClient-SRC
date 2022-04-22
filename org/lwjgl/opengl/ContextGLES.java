package org.lwjgl.opengl;

import org.lwjgl.*;
import org.lwjgl.opengles.*;

final class ContextGLES implements Context
{
    private static final ThreadLocal current_context_local;
    private final DrawableGLES drawable;
    private final EGLContext eglContext;
    private final ContextAttribs contextAttribs;
    private boolean destroyed;
    private boolean destroy_requested;
    private Thread thread;
    
    public EGLContext getEGLContext() {
        return this.eglContext;
    }
    
    ContextAttribs getContextAttribs() {
        return this.contextAttribs;
    }
    
    static ContextGLES getCurrentContext() {
        return ContextGLES.current_context_local.get();
    }
    
    ContextGLES(final DrawableGLES drawable, final ContextAttribs contextAttribs, final ContextGLES contextGLES) throws LWJGLException {
        if (drawable == null) {
            throw new IllegalArgumentException();
        }
        // monitorenter(contextGLES2 = contextGLES != null ? contextGLES : this)
        if (contextGLES != null && contextGLES.destroyed) {
            throw new IllegalArgumentException("Shared context is destroyed");
        }
        this.drawable = drawable;
        this.contextAttribs = contextAttribs;
        this.eglContext = drawable.getEGLDisplay().createContext(drawable.getEGLConfig(), (contextGLES == null) ? null : contextGLES.eglContext, (contextAttribs == null) ? new ContextAttribs(2).getAttribList() : contextAttribs.getAttribList());
    }
    // monitorexit(contextGLES2)
    
    public void releaseCurrent() throws LWJGLException, PowerManagementEventException {
        EGL.eglReleaseCurrent(this.drawable.getEGLDisplay());
        GLContext.useContext((Object)null);
        ContextGLES.current_context_local.set(null);
        // monitorenter(this)
        this.thread = null;
        this.checkDestroy();
    }
    // monitorexit(this)
    
    public static void swapBuffers() throws LWJGLException, PowerManagementEventException {
        final ContextGLES currentContext = getCurrentContext();
        if (currentContext != null) {
            currentContext.drawable.getEGLSurface().swapBuffers();
        }
    }
    
    private boolean canAccess() {
        return this.thread == null || Thread.currentThread() == this.thread;
    }
    
    private void checkAccess() {
        if (!this.canAccess()) {
            throw new IllegalStateException("From thread " + Thread.currentThread() + ": " + this.thread + " already has the context current");
        }
    }
    
    public synchronized void makeCurrent() throws LWJGLException, PowerManagementEventException {
        this.checkAccess();
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        this.thread = Thread.currentThread();
        ContextGLES.current_context_local.set(this);
        this.eglContext.makeCurrent(this.drawable.getEGLSurface());
        GLContext.useContext((Object)this);
    }
    
    public synchronized boolean isCurrent() throws LWJGLException {
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        return EGL.eglIsCurrentContext(this.eglContext);
    }
    
    private void checkDestroy() {
        if (!this.destroyed && this.destroy_requested) {
            this.eglContext.destroy();
            this.destroyed = true;
            this.thread = null;
        }
    }
    
    public static void setSwapInterval(final int swapInterval) {
        final ContextGLES currentContext = getCurrentContext();
        if (currentContext != null) {
            currentContext.drawable.getEGLDisplay().setSwapInterval(swapInterval);
        }
    }
    
    public synchronized void forceDestroy() throws LWJGLException {
        this.checkAccess();
        this.destroy();
    }
    
    public synchronized void destroy() throws LWJGLException {
        if (this.destroyed) {
            return;
        }
        this.destroy_requested = true;
        final boolean current = this.isCurrent();
        if (current) {
            if (GLContext.getCapabilities() != null && GLContext.getCapabilities().OpenGLES20) {
                GLES20.glGetError();
            }
            this.releaseCurrent();
        }
        this.checkDestroy();
        if (current && false) {
            throw new OpenGLException(0);
        }
    }
    
    public void releaseDrawable() throws LWJGLException {
    }
    
    static {
        current_context_local = new ThreadLocal();
    }
}
