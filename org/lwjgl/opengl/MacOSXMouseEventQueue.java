package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;
import java.awt.*;

final class MacOSXMouseEventQueue extends MouseEventQueue
{
    private final IntBuffer delta_buffer;
    private boolean skip_event;
    private static boolean is_grabbed;
    
    MacOSXMouseEventQueue(final Component component) {
        super(component);
        this.delta_buffer = BufferUtils.createIntBuffer(2);
    }
    
    @Override
    public void setGrabbed(final boolean grabbed) {
        if (MacOSXMouseEventQueue.is_grabbed != grabbed) {
            super.setGrabbed(grabbed);
            this.warpCursor();
            grabMouse(grabbed);
        }
    }
    
    private static synchronized void grabMouse(final boolean is_grabbed) {
        if (!(MacOSXMouseEventQueue.is_grabbed = is_grabbed)) {
            nGrabMouse(is_grabbed);
        }
    }
    
    @Override
    protected void resetCursorToCenter() {
        super.resetCursorToCenter();
        getMouseDeltas(this.delta_buffer);
    }
    
    @Override
    protected void updateDeltas(final long n) {
        super.updateDeltas(n);
        // monitorenter(this)
        getMouseDeltas(this.delta_buffer);
        final int value = this.delta_buffer.get(0);
        final int n2 = -this.delta_buffer.get(1);
        if (this.skip_event) {
            this.skip_event = false;
            nGrabMouse(this.isGrabbed());
            // monitorexit(this)
            return;
        }
        if (value != 0 || n2 != 0) {
            this.putMouseEventWithCoords((byte)(-1), (byte)0, value, n2, 0, n);
            this.addDelta(value, n2);
        }
    }
    // monitorexit(this)
    
    void warpCursor() {
        // monitorenter(this)
        this.skip_event = this.isGrabbed();
        // monitorexit(this)
        if (this.isGrabbed()) {
            final Rectangle bounds = this.getComponent().getBounds();
            final Point locationOnScreen = this.getComponent().getLocationOnScreen();
            nWarpCursor(locationOnScreen.x + bounds.width / 2, locationOnScreen.y + bounds.height / 2);
        }
    }
    
    private static native void getMouseDeltas(final IntBuffer p0);
    
    private static native void nWarpCursor(final int p0, final int p1);
    
    static native void nGrabMouse(final boolean p0);
}
