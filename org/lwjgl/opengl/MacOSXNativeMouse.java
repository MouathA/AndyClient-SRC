package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

final class MacOSXNativeMouse extends EventQueue
{
    private static final int WHEEL_SCALE = 120;
    private static final int NUM_BUTTONS = 3;
    private ByteBuffer window_handle;
    private MacOSXDisplay display;
    private boolean grabbed;
    private float accum_dx;
    private float accum_dy;
    private int accum_dz;
    private float last_x;
    private float last_y;
    private boolean saved_control_state;
    private final ByteBuffer event;
    private IntBuffer delta_buffer;
    private int skip_event;
    private final byte[] buttons;
    
    MacOSXNativeMouse(final MacOSXDisplay display, final ByteBuffer window_handle) {
        super(22);
        this.event = ByteBuffer.allocate(22);
        this.delta_buffer = BufferUtils.createIntBuffer(2);
        this.buttons = new byte[3];
        this.display = display;
        this.window_handle = window_handle;
    }
    
    private native void nSetCursorPosition(final ByteBuffer p0, final int p1, final int p2);
    
    public static native void nGrabMouse(final boolean p0);
    
    private native void nRegisterMouseListener(final ByteBuffer p0);
    
    private native void nUnregisterMouseListener(final ByteBuffer p0);
    
    private static native long nCreateCursor(final int p0, final int p1, final int p2, final int p3, final int p4, final IntBuffer p5, final int p6, final IntBuffer p7, final int p8) throws LWJGLException;
    
    private static native void nDestroyCursor(final long p0);
    
    private static native void nSetCursor(final long p0) throws LWJGLException;
    
    public synchronized void register() {
        this.nRegisterMouseListener(this.window_handle);
    }
    
    public static long createCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return nCreateCursor(n, n2, n3, n4, n5, intBuffer, intBuffer.position(), intBuffer2, (intBuffer2 != null) ? intBuffer2.position() : -1);
    }
    
    public static void destroyCursor(final long n) {
        nDestroyCursor(n);
    }
    
    public static void setCursor(final long n) throws LWJGLException {
        nSetCursor(n);
    }
    
    public synchronized void setCursorPosition(final int n, final int n2) {
        this.nSetCursorPosition(this.window_handle, n, n2);
    }
    
    public synchronized void unregister() {
        this.nUnregisterMouseListener(this.window_handle);
    }
    
    public synchronized void setGrabbed(final boolean grabbed) {
        nGrabMouse(this.grabbed = grabbed);
        this.skip_event = 1;
        final float n = 0.0f;
        this.accum_dy = n;
        this.accum_dx = n;
    }
    
    public synchronized boolean isGrabbed() {
        return this.grabbed;
    }
    
    protected void resetCursorToCenter() {
        this.clearEvents();
        final float n = 0.0f;
        this.accum_dy = n;
        this.accum_dx = n;
        if (this.display != null) {
            this.last_x = (float)(this.display.getWidth() / 2);
            this.last_y = (float)(this.display.getHeight() / 2);
        }
    }
    
    private void putMouseEvent(final byte b, final byte b2, final int n, final long n2) {
        if (this.grabbed) {
            this.putMouseEventWithCoords(b, b2, 0, 0, n, n2);
        }
        else {
            this.putMouseEventWithCoords(b, b2, (int)this.last_x, (int)this.last_y, n, n2);
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
            intBuffer.put(0, (int)this.accum_dx);
            intBuffer.put(1, (int)this.accum_dy);
        }
        else {
            intBuffer.put(0, (int)this.last_x);
            intBuffer.put(1, (int)this.last_y);
        }
        intBuffer.put(2, this.accum_dz);
        final int accum_dz = 0;
        this.accum_dz = accum_dz;
        final float n = (float)accum_dz;
        this.accum_dy = n;
        this.accum_dx = n;
        final int position = byteBuffer.position();
        byteBuffer.put(this.buttons, 0, this.buttons.length);
        byteBuffer.position(position);
    }
    
    private void setCursorPos(final float last_x, final float last_y, final long n) {
        if (this.grabbed) {
            return;
        }
        this.addDelta(last_x - this.last_x, last_y - this.last_y);
        this.last_x = last_x;
        this.last_y = last_y;
        this.putMouseEventWithCoords((byte)(-1), (byte)0, (int)last_x, (int)last_y, 0, n);
    }
    
    protected void addDelta(final float n, final float n2) {
        this.accum_dx += n;
        this.accum_dy += -n2;
    }
    
    public synchronized void setButton(final int n, final int n2, final long n3) {
        this.buttons[n] = (byte)n2;
        this.putMouseEvent((byte)n, (byte)n2, 0, n3);
    }
    
    public synchronized void mouseMoved(final float last_x, final float last_y, final float n, float n2, final float n3, final long n4) {
        if (this.skip_event > 0) {
            --this.skip_event;
            if (this.skip_event == 0) {
                this.last_x = last_x;
                this.last_y = last_y;
            }
            return;
        }
        if (n3 != 0.0f) {
            if (n2 == 0.0f) {
                n2 = n;
            }
            final int n5 = (int)(n2 * 120.0f);
            this.accum_dz += n5;
            this.putMouseEvent((byte)(-1), (byte)0, n5, n4);
        }
        else if (this.grabbed) {
            if (n != 0.0f || n2 != 0.0f) {
                this.putMouseEventWithCoords((byte)(-1), (byte)0, (int)n, (int)(-n2), 0, n4);
                this.addDelta(n, n2);
            }
        }
        else {
            this.setCursorPos(last_x, last_y, n4);
        }
    }
}
