package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

final class ContextGL implements Context
{
    private static final ContextImplementation implementation;
    private static final ThreadLocal current_context_local;
    private final ByteBuffer handle;
    private final PeerInfo peer_info;
    private final ContextAttribs contextAttribs;
    private final boolean forwardCompatible;
    private boolean destroyed;
    private boolean destroy_requested;
    private Thread thread;
    
    private static ContextImplementation createImplementation() {
        switch (LWJGLUtil.getPlatform()) {
            case 1: {
                return new LinuxContextImplementation();
            }
            case 3: {
                return new WindowsContextImplementation();
            }
            case 2: {
                return new MacOSXContextImplementation();
            }
            default: {
                throw new IllegalStateException("Unsupported platform");
            }
        }
    }
    
    PeerInfo getPeerInfo() {
        return this.peer_info;
    }
    
    ContextAttribs getContextAttribs() {
        return this.contextAttribs;
    }
    
    static ContextGL getCurrentContext() {
        return ContextGL.current_context_local.get();
    }
    
    ContextGL(final PeerInfo peer_info, final ContextAttribs contextAttribs, final ContextGL contextGL) throws LWJGLException {
        // monitorenter(contextGL2 = contextGL != null ? contextGL : this)
        if (contextGL != null && contextGL.destroyed) {
            throw new IllegalArgumentException("Shared context is destroyed");
        }
        this.peer_info = peer_info;
        IntBuffer attribList;
        if ((this.contextAttribs = contextAttribs) != null) {
            attribList = contextAttribs.getAttribList();
            this.forwardCompatible = contextAttribs.isForwardCompatible();
        }
        else {
            attribList = null;
            this.forwardCompatible = false;
        }
        this.handle = ContextGL.implementation.create(peer_info, attribList, (contextGL != null) ? contextGL.handle : null);
    }
    // monitorexit(contextGL2)
    
    public void releaseCurrent() throws LWJGLException {
        final ContextGL currentContext = getCurrentContext();
        if (currentContext != null) {
            ContextGL.implementation.releaseCurrentContext();
            GLContext.useContext(null);
            ContextGL.current_context_local.set(null);
            // monitorenter(contextGL = currentContext)
            currentContext.thread = null;
            currentContext.checkDestroy();
        }
        // monitorexit(contextGL)
    }
    
    public synchronized void releaseDrawable() throws LWJGLException {
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        ContextGL.implementation.releaseDrawable(this.getHandle());
    }
    
    public synchronized void update() {
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        ContextGL.implementation.update(this.getHandle());
    }
    
    public static void swapBuffers() throws LWJGLException {
        ContextGL.implementation.swapBuffers();
    }
    
    private boolean canAccess() {
        return this.thread == null || Thread.currentThread() == this.thread;
    }
    
    private void checkAccess() {
        if (!this.canAccess()) {
            throw new IllegalStateException("From thread " + Thread.currentThread() + ": " + this.thread + " already has the context current");
        }
    }
    
    public synchronized void makeCurrent() throws LWJGLException {
        this.checkAccess();
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        this.thread = Thread.currentThread();
        ContextGL.current_context_local.set(this);
        ContextGL.implementation.makeCurrent(this.peer_info, this.handle);
        GLContext.useContext(this, this.forwardCompatible);
    }
    
    ByteBuffer getHandle() {
        return this.handle;
    }
    
    public synchronized boolean isCurrent() throws LWJGLException {
        if (this.destroyed) {
            throw new IllegalStateException("Context is destroyed");
        }
        return ContextGL.implementation.isCurrent(this.handle);
    }
    
    private void checkDestroy() {
        if (!this.destroyed && this.destroy_requested) {
            this.releaseDrawable();
            ContextGL.implementation.destroy(this.peer_info, this.handle);
            CallbackUtil.unregisterCallbacks(this);
            this.destroyed = true;
            this.thread = null;
        }
    }
    
    public static void setSwapInterval(final int swapInterval) {
        ContextGL.implementation.setSwapInterval(swapInterval);
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
            GL11.glGetError();
            this.releaseCurrent();
        }
        this.checkDestroy();
        if (current && false) {
            throw new OpenGLException(0);
        }
    }
    
    public synchronized void setCLSharingProperties(final PointerBuffer pointerBuffer) throws LWJGLException {
        final ByteBuffer lockAndGetHandle = this.peer_info.lockAndGetHandle();
        Label_0185: {
            switch (LWJGLUtil.getPlatform()) {
                case 3: {
                    final WindowsContextImplementation windowsContextImplementation = (WindowsContextImplementation)ContextGL.implementation;
                    pointerBuffer.put(8200L).put(windowsContextImplementation.getHGLRC(this.handle));
                    pointerBuffer.put(8203L).put(windowsContextImplementation.getHDC(lockAndGetHandle));
                    break Label_0185;
                }
                case 1: {
                    final LinuxContextImplementation linuxContextImplementation = (LinuxContextImplementation)ContextGL.implementation;
                    pointerBuffer.put(8200L).put(linuxContextImplementation.getGLXContext(this.handle));
                    pointerBuffer.put(8202L).put(linuxContextImplementation.getDisplay(lockAndGetHandle));
                    break Label_0185;
                }
                case 2: {
                    if (LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 6)) {
                        pointerBuffer.put(268435456L).put(((MacOSXContextImplementation)ContextGL.implementation).getCGLShareGroup(this.handle));
                        break Label_0185;
                    }
                    break;
                }
            }
            throw new UnsupportedOperationException("CL/GL context sharing is not supported on this platform.");
        }
        this.peer_info.unlock();
    }
    
    static {
        current_context_local = new ThreadLocal();
        implementation = createImplementation();
    }
}
