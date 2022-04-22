package org.lwjgl.opengl;

import java.awt.*;
import java.nio.*;
import java.awt.event.*;

class MouseEventQueue extends EventQueue implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private static final int WHEEL_SCALE = 120;
    public static final int NUM_BUTTONS = 3;
    private final Component component;
    private boolean grabbed;
    private int accum_dx;
    private int accum_dy;
    private int accum_dz;
    private int last_x;
    private int last_y;
    private boolean saved_control_state;
    private final ByteBuffer event;
    private final byte[] buttons;
    
    MouseEventQueue(final Component component) {
        super(22);
        this.event = ByteBuffer.allocate(22);
        this.buttons = new byte[3];
        this.component = component;
    }
    
    public synchronized void register() {
        this.resetCursorToCenter();
        if (this.component != null) {
            this.component.addMouseListener(this);
            this.component.addMouseMotionListener(this);
            this.component.addMouseWheelListener(this);
        }
    }
    
    public synchronized void unregister() {
        if (this.component != null) {
            this.component.removeMouseListener(this);
            this.component.removeMouseMotionListener(this);
            this.component.removeMouseWheelListener(this);
        }
    }
    
    protected Component getComponent() {
        return this.component;
    }
    
    public synchronized void setGrabbed(final boolean grabbed) {
        this.grabbed = grabbed;
        this.resetCursorToCenter();
    }
    
    public synchronized boolean isGrabbed() {
        return this.grabbed;
    }
    
    protected int transformY(final int n) {
        if (this.component != null) {
            return this.component.getHeight() - 1 - n;
        }
        return n;
    }
    
    protected void resetCursorToCenter() {
        this.clearEvents();
        final int n = 0;
        this.accum_dy = n;
        this.accum_dx = n;
        if (this.component != null) {
            final Point cursorPosition = AWTUtil.getCursorPosition(this.component);
            if (cursorPosition != null) {
                this.last_x = cursorPosition.x;
                this.last_y = cursorPosition.y;
            }
        }
    }
    
    private void putMouseEvent(final byte b, final byte b2, final int n, final long n2) {
        if (this.grabbed) {
            this.putMouseEventWithCoords(b, b2, 0, 0, n, n2);
        }
        else {
            this.putMouseEventWithCoords(b, b2, this.last_x, this.last_y, n, n2);
        }
    }
    
    protected void putMouseEventWithCoords(final byte b, final byte b2, final int n, final int n2, final int n3, final long n4) {
        this.event.clear();
        this.event.put(b).put(b2).putInt(n).putInt(n2).putInt(n3).putLong(n4);
        this.event.flip();
        this.putEvent(this.event);
    }
    
    public synchronized void poll(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        if (this.grabbed) {
            intBuffer.put(0, this.accum_dx);
            intBuffer.put(1, this.accum_dy);
        }
        else {
            intBuffer.put(0, this.last_x);
            intBuffer.put(1, this.last_y);
        }
        intBuffer.put(2, this.accum_dz);
        final int accum_dx = 0;
        this.accum_dz = accum_dx;
        this.accum_dy = accum_dx;
        this.accum_dx = accum_dx;
        final int position = byteBuffer.position();
        byteBuffer.put(this.buttons, 0, this.buttons.length);
        byteBuffer.position(position);
    }
    
    private void setCursorPos(final int last_x, int transformY, final long n) {
        transformY = this.transformY(transformY);
        if (this.grabbed) {
            return;
        }
        this.addDelta(last_x - this.last_x, transformY - this.last_y);
        this.putMouseEventWithCoords((byte)(-1), (byte)0, this.last_x = last_x, this.last_y = transformY, 0, n);
    }
    
    protected void addDelta(final int n, final int n2) {
        this.accum_dx += n;
        this.accum_dy += n2;
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
    }
    
    private void handleButton(final MouseEvent mouseEvent) {
        switch (mouseEvent.getID()) {
            case 501: {
                break;
            }
            case 502: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Not a valid event ID: " + mouseEvent.getID());
            }
        }
        switch (mouseEvent.getButton()) {
            case 0: {
                return;
            }
            case 1: {
                if (false == true) {
                    this.saved_control_state = mouseEvent.isControlDown();
                }
                if (!this.saved_control_state) {
                    break;
                }
                if (this.buttons[1] == 0) {
                    return;
                }
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                if (this.buttons[1] == 0) {
                    return;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Not a valid button: " + mouseEvent.getButton());
            }
        }
        this.setButton((byte)1, (byte)0, mouseEvent.getWhen() * 1000000L);
    }
    
    public synchronized void mousePressed(final MouseEvent mouseEvent) {
        this.handleButton(mouseEvent);
    }
    
    private void setButton(final byte b, final byte b2, final long n) {
        this.putMouseEvent(b, this.buttons[b] = b2, 0, n);
    }
    
    public synchronized void mouseReleased(final MouseEvent mouseEvent) {
        this.handleButton(mouseEvent);
    }
    
    private void handleMotion(final MouseEvent mouseEvent) {
        if (this.grabbed) {
            this.updateDeltas(mouseEvent.getWhen() * 1000000L);
        }
        else {
            this.setCursorPos(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getWhen() * 1000000L);
        }
    }
    
    public synchronized void mouseDragged(final MouseEvent mouseEvent) {
        this.handleMotion(mouseEvent);
    }
    
    public synchronized void mouseMoved(final MouseEvent mouseEvent) {
        this.handleMotion(mouseEvent);
    }
    
    private void handleWheel(final int n, final long n2) {
        this.accum_dz += n;
        this.putMouseEvent((byte)(-1), (byte)0, n, n2);
    }
    
    protected void updateDeltas(final long n) {
    }
    
    public synchronized void mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
        this.handleWheel(-mouseWheelEvent.getWheelRotation() * 120, mouseWheelEvent.getWhen() * 1000000L);
    }
}
