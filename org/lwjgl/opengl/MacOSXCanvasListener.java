package org.lwjgl.opengl;

import java.awt.*;
import java.awt.event.*;

final class MacOSXCanvasListener implements ComponentListener, HierarchyListener
{
    private final Canvas canvas;
    private int width;
    private int height;
    private boolean context_update;
    private boolean resized;
    
    MacOSXCanvasListener(final Canvas canvas) {
        (this.canvas = canvas).addComponentListener(this);
        canvas.addHierarchyListener(this);
        this.setUpdate();
    }
    
    public void disableListeners() {
        EventQueue.invokeLater(new Runnable() {
            final MacOSXCanvasListener this$0;
            
            public void run() {
                MacOSXCanvasListener.access$000(this.this$0).removeComponentListener(this.this$0);
                MacOSXCanvasListener.access$000(this.this$0).removeHierarchyListener(this.this$0);
            }
        });
    }
    
    public boolean syncShouldUpdateContext() {
        // monitorenter(this)
        final boolean context_update = this.context_update;
        this.context_update = false;
        // monitorexit(this)
        return context_update;
    }
    
    private synchronized void setUpdate() {
        // monitorenter(this)
        this.width = this.canvas.getWidth();
        this.height = this.canvas.getHeight();
        this.context_update = true;
    }
    // monitorexit(this)
    
    public int syncGetWidth() {
        // monitorenter(this)
        // monitorexit(this)
        return this.width;
    }
    
    public int syncGetHeight() {
        // monitorenter(this)
        // monitorexit(this)
        return this.height;
    }
    
    public void componentShown(final ComponentEvent componentEvent) {
    }
    
    public void componentHidden(final ComponentEvent componentEvent) {
    }
    
    public void componentResized(final ComponentEvent componentEvent) {
        this.setUpdate();
        this.resized = true;
    }
    
    public void componentMoved(final ComponentEvent componentEvent) {
        this.setUpdate();
    }
    
    public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
        this.setUpdate();
    }
    
    public boolean wasResized() {
        if (this.resized) {
            this.resized = false;
            return true;
        }
        return false;
    }
    
    static Canvas access$000(final MacOSXCanvasListener macOSXCanvasListener) {
        return macOSXCanvasListener.canvas;
    }
}
