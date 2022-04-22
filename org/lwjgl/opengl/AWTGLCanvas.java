package org.lwjgl.opengl;

import org.lwjgl.*;
import java.awt.*;
import java.awt.event.*;

public class AWTGLCanvas extends Canvas implements DrawableLWJGL, ComponentListener, HierarchyListener
{
    private static final long serialVersionUID = 1L;
    private static final AWTCanvasImplementation implementation;
    private boolean update_context;
    private Object SYNC_LOCK;
    private final PixelFormat pixel_format;
    private final Drawable drawable;
    private final ContextAttribs attribs;
    private PeerInfo peer_info;
    private ContextGL context;
    private int reentry_count;
    private boolean first_run;
    
    static AWTCanvasImplementation createImplementation() {
        switch (LWJGLUtil.getPlatform()) {
            case 1: {
                return new LinuxCanvasImplementation();
            }
            case 3: {
                return new WindowsCanvasImplementation();
            }
            case 2: {
                return new MacOSXCanvasImplementation();
            }
            default: {
                throw new IllegalStateException("Unsupported platform");
            }
        }
    }
    
    private void setUpdate() {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        this.update_context = true;
    }
    // monitorexit(sync_LOCK)
    
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL) throws LWJGLException {
        throw new UnsupportedOperationException();
    }
    
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL, final ContextAttribs contextAttribs) throws LWJGLException {
        throw new UnsupportedOperationException();
    }
    
    public PixelFormatLWJGL getPixelFormat() {
        return this.pixel_format;
    }
    
    public ContextGL getContext() {
        return this.context;
    }
    
    public ContextGL createSharedContext() throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        // monitorexit(sync_LOCK)
        return new ContextGL(this.peer_info, this.context.getContextAttribs(), this.context);
    }
    
    public void checkGLError() {
    }
    
    public void initContext(final float n, final float n2, final float n3) {
        GL11.glClearColor(n, n2, n3, 0.0f);
        GL11.glClear(16384);
    }
    
    public AWTGLCanvas() throws LWJGLException {
        this(new PixelFormat());
    }
    
    public AWTGLCanvas(final PixelFormat pixelFormat) throws LWJGLException {
        this(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), pixelFormat);
    }
    
    public AWTGLCanvas(final GraphicsDevice graphicsDevice, final PixelFormat pixelFormat) throws LWJGLException {
        this(graphicsDevice, pixelFormat, null);
    }
    
    public AWTGLCanvas(final GraphicsDevice graphicsDevice, final PixelFormat pixelFormat, final Drawable drawable) throws LWJGLException {
        this(graphicsDevice, pixelFormat, drawable, null);
    }
    
    public AWTGLCanvas(final GraphicsDevice graphicsDevice, final PixelFormat pixel_format, final Drawable drawable, final ContextAttribs attribs) throws LWJGLException {
        super(AWTGLCanvas.implementation.findConfiguration(graphicsDevice, pixel_format));
        this.SYNC_LOCK = new Object();
        if (pixel_format == null) {
            throw new NullPointerException("Pixel format must be non-null");
        }
        this.addHierarchyListener(this);
        this.addComponentListener(this);
        this.drawable = drawable;
        this.pixel_format = pixel_format;
        this.attribs = attribs;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
    }
    
    @Override
    public void removeNotify() {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        this.destroy();
        super.removeNotify();
    }
    // monitorexit(sync_LOCK)
    
    public void setSwapInterval(final int swapInterval) {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        ContextGL.setSwapInterval(swapInterval);
    }
    // monitorexit(sync_LOCK)
    
    public void setVSyncEnabled(final boolean swapInterval) {
        this.setSwapInterval(swapInterval ? 1 : 0);
    }
    
    public void swapBuffers() throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
    }
    // monitorexit(sync_LOCK)
    
    public boolean isCurrent() throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        // monitorexit(sync_LOCK)
        return this.context.isCurrent();
    }
    
    public void makeCurrent() throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        this.context.makeCurrent();
    }
    // monitorexit(sync_LOCK)
    
    public void releaseContext() throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        if (this.context.isCurrent()) {
            this.context.releaseCurrent();
        }
    }
    // monitorexit(sync_LOCK)
    
    public final void destroy() {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context != null) {
            this.context.forceDestroy();
            this.context = null;
            this.reentry_count = 0;
            this.peer_info.destroy();
            this.peer_info = null;
        }
    }
    // monitorexit(sync_LOCK)
    
    public final void setCLSharingProperties(final PointerBuffer clSharingProperties) throws LWJGLException {
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (this.context == null) {
            throw new IllegalStateException("Canvas not yet displayable");
        }
        this.context.setCLSharingProperties(clSharingProperties);
    }
    // monitorexit(sync_LOCK)
    
    protected void initGL() {
    }
    
    protected void paintGL() {
    }
    
    @Override
    public final void paint(final Graphics graphics) {
        final LWJGLException ex = null;
        // monitorenter(sync_LOCK = this.SYNC_LOCK)
        if (!this.isDisplayable()) {
            // monitorexit(sync_LOCK)
            return;
        }
        if (this.peer_info == null) {
            this.peer_info = AWTGLCanvas.implementation.createPeerInfo(this, this.pixel_format, this.attribs);
        }
        this.peer_info.lockAndGetHandle();
        if (this.context == null) {
            this.context = new ContextGL(this.peer_info, this.attribs, (this.drawable != null) ? ((ContextGL)((DrawableLWJGL)this.drawable).getContext()) : null);
            this.first_run = true;
        }
        if (this.reentry_count == 0) {
            this.context.makeCurrent();
        }
        ++this.reentry_count;
        if (this.update_context) {
            this.context.update();
            this.update_context = false;
        }
        if (this.first_run) {
            this.first_run = false;
            this.initGL();
        }
        this.paintGL();
        --this.reentry_count;
        if (this.reentry_count == 0) {
            this.context.releaseCurrent();
        }
        this.peer_info.unlock();
        // monitorexit(sync_LOCK)
        if (ex != null) {
            this.exceptionOccurred(ex);
        }
    }
    
    protected void exceptionOccurred(final LWJGLException ex) {
        LWJGLUtil.log("Unhandled exception occurred, skipping paint(): " + ex);
    }
    
    @Override
    public void update(final Graphics graphics) {
        this.paint(graphics);
    }
    
    public void componentShown(final ComponentEvent componentEvent) {
    }
    
    public void componentHidden(final ComponentEvent componentEvent) {
    }
    
    public void componentResized(final ComponentEvent componentEvent) {
        this.setUpdate();
    }
    
    public void componentMoved(final ComponentEvent componentEvent) {
        this.setUpdate();
    }
    
    @Override
    public void setLocation(final int n, final int n2) {
        super.setLocation(n, n2);
        this.setUpdate();
    }
    
    @Override
    public void setLocation(final Point location) {
        super.setLocation(location);
        this.setUpdate();
    }
    
    @Override
    public void setSize(final Dimension size) {
        super.setSize(size);
        this.setUpdate();
    }
    
    @Override
    public void setSize(final int n, final int n2) {
        super.setSize(n, n2);
        this.setUpdate();
    }
    
    @Override
    public void setBounds(final int n, final int n2, final int n3, final int n4) {
        super.setBounds(n, n2, n3, n4);
        this.setUpdate();
    }
    
    public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
        this.setUpdate();
    }
    
    public Context createSharedContext() throws LWJGLException {
        return this.createSharedContext();
    }
    
    public Context getContext() {
        return this.getContext();
    }
    
    static {
        implementation = createImplementation();
    }
}
