package org.lwjgl.opengl;

import java.awt.*;

final class MacOSXGLCanvas extends Canvas
{
    private static final long serialVersionUID = 6916664741667434870L;
    private boolean canvas_painted;
    private boolean dirty;
    
    @Override
    public void update(final Graphics graphics) {
        this.paint(graphics);
    }
    
    @Override
    public void paint(final Graphics graphics) {
        // monitorenter(this)
        this.dirty = true;
        this.canvas_painted = true;
    }
    // monitorexit(this)
    
    public boolean syncCanvasPainted() {
        // monitorenter(this)
        final boolean canvas_painted = this.canvas_painted;
        this.canvas_painted = false;
        // monitorexit(this)
        return canvas_painted;
    }
    
    public boolean syncIsDirty() {
        // monitorenter(this)
        final boolean dirty = this.dirty;
        this.dirty = false;
        // monitorexit(this)
        return dirty;
    }
}
