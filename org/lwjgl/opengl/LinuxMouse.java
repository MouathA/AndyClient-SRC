package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

final class LinuxMouse
{
    private static final int POINTER_WARP_BORDER = 10;
    private static final int WHEEL_SCALE = 120;
    private int button_count;
    private static final int Button1 = 1;
    private static final int Button2 = 2;
    private static final int Button3 = 3;
    private static final int Button4 = 4;
    private static final int Button5 = 5;
    private static final int Button6 = 6;
    private static final int Button7 = 7;
    private static final int Button8 = 8;
    private static final int Button9 = 9;
    private static final int ButtonPress = 4;
    private static final int ButtonRelease = 5;
    private final long display;
    private final long window;
    private final long input_window;
    private final long warp_atom;
    private final IntBuffer query_pointer_buffer;
    private final ByteBuffer event_buffer;
    private int last_x;
    private int last_y;
    private int accum_dx;
    private int accum_dy;
    private int accum_dz;
    private byte[] buttons;
    private EventQueue event_queue;
    private long last_event_nanos;
    
    LinuxMouse(final long display, final long window, final long input_window) throws LWJGLException {
        this.query_pointer_buffer = BufferUtils.createIntBuffer(4);
        this.event_buffer = ByteBuffer.allocate(22);
        this.display = display;
        this.window = window;
        this.input_window = input_window;
        this.warp_atom = LinuxDisplay.nInternAtom(display, "_LWJGL", false);
        this.button_count = nGetButtonCount(display);
        this.buttons = new byte[this.button_count];
        this.reset(false, false);
    }
    
    private void reset(final boolean b, final boolean b2) {
        this.event_queue = new EventQueue(this.event_buffer.capacity());
        final int n = 0;
        this.accum_dy = n;
        this.accum_dx = n;
        final long nQueryPointer = nQueryPointer(this.display, this.window, this.query_pointer_buffer);
        final int value = this.query_pointer_buffer.get(0);
        final int value2 = this.query_pointer_buffer.get(1);
        final int value3 = this.query_pointer_buffer.get(2);
        final int value4 = this.query_pointer_buffer.get(3);
        this.last_x = value3;
        this.last_y = this.transformY(value4);
        this.doHandlePointerMotion(b, b2, nQueryPointer, value, value2, value3, value4, this.last_event_nanos);
    }
    
    public void read(final ByteBuffer byteBuffer) {
        this.event_queue.copyEvents(byteBuffer);
    }
    
    public void poll(final boolean b, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        if (b) {
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
        while (0 < this.buttons.length) {
            byteBuffer.put(0, this.buttons[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void putMouseEventWithCoords(final byte b, final byte b2, final int n, final int n2, final int n3, final long last_event_nanos) {
        this.event_buffer.clear();
        this.event_buffer.put(b).put(b2).putInt(n).putInt(n2).putInt(n3).putLong(last_event_nanos);
        this.event_buffer.flip();
        this.event_queue.putEvent(this.event_buffer);
        this.last_event_nanos = last_event_nanos;
    }
    
    private void setCursorPos(final boolean b, final int last_x, int transformY, final long n) {
        transformY = this.transformY(transformY);
        final int n2 = last_x - this.last_x;
        final int n3 = transformY - this.last_y;
        if (n2 != 0 || n3 != 0) {
            this.accum_dx += n2;
            this.accum_dy += n3;
            this.last_x = last_x;
            this.last_y = transformY;
            if (b) {
                this.putMouseEventWithCoords((byte)(-1), (byte)0, n2, n3, 0, n);
            }
            else {
                this.putMouseEventWithCoords((byte)(-1), (byte)0, last_x, transformY, 0, n);
            }
        }
    }
    
    private void doWarpPointer(final int n, final int n2) {
        nSendWarpEvent(this.display, this.input_window, this.warp_atom, n, n2);
        nWarpCursor(this.display, this.window, n, n2);
    }
    
    private static native void nSendWarpEvent(final long p0, final long p1, final long p2, final int p3, final int p4);
    
    private void doHandlePointerMotion(final boolean b, final boolean b2, final long n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        this.setCursorPos(b, n4, n5, n6);
        if (!b2) {
            return;
        }
        final int nGetWindowHeight = nGetWindowHeight(this.display, n);
        final int nGetWindowWidth = nGetWindowWidth(this.display, n);
        final int nGetWindowHeight2 = nGetWindowHeight(this.display, this.window);
        final int nGetWindowWidth2 = nGetWindowWidth(this.display, this.window);
        final int n7 = n2 - n4;
        final int n8 = n3 - n5;
        final int n9 = n7 + nGetWindowWidth2;
        final int n10 = n8 + nGetWindowHeight2;
        final int max = Math.max(0, n7);
        final int max2 = Math.max(0, n8);
        final int min = Math.min(nGetWindowWidth, n9);
        final int min2 = Math.min(nGetWindowHeight, n10);
        if (n2 < max + 10 || n3 < max2 + 10 || n2 > min - 10 || n3 > min2 - 10) {
            this.doWarpPointer((min - max) / 2, (min2 - max2) / 2);
        }
    }
    
    public void changeGrabbed(final boolean b, final boolean b2) {
        this.reset(b, b2);
    }
    
    public int getButtonCount() {
        return this.buttons.length;
    }
    
    private int transformY(final int n) {
        return nGetWindowHeight(this.display, this.window) - 1 - n;
    }
    
    private static native int nGetWindowHeight(final long p0, final long p1);
    
    private static native int nGetWindowWidth(final long p0, final long p1);
    
    private static native int nGetButtonCount(final long p0);
    
    private static native long nQueryPointer(final long p0, final long p1, final IntBuffer p2);
    
    public void setCursorPosition(final int n, final int n2) {
        nWarpCursor(this.display, this.window, n, this.transformY(n2));
    }
    
    private static native void nWarpCursor(final long p0, final long p1, final int p2, final int p3);
    
    private void handlePointerMotion(final boolean b, final boolean b2, final long n, final long n2, final int n3, final int n4, final int n5, final int n6) {
        this.doHandlePointerMotion(b, b2, n2, n3, n4, n5, n6, n * 1000000L);
    }
    
    private void handleButton(final boolean b, final int n, final byte b2, final long n2) {
        switch (n) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                break;
            }
            case 9: {
                break;
            }
            default: {
                if (n > 9 && n <= this.button_count) {
                    final byte b3 = (byte)(n - 1);
                    break;
                }
                return;
            }
        }
        this.putMouseEvent(b, (byte)4, this.buttons[4] = b2, 0, n2);
    }
    
    private void putMouseEvent(final boolean b, final byte b2, final byte b3, final int n, final long n2) {
        if (b) {
            this.putMouseEventWithCoords(b2, b3, 0, 0, n, n2);
        }
        else {
            this.putMouseEventWithCoords(b2, b3, this.last_x, this.last_y, n, n2);
        }
    }
    
    private void handleButtonPress(final boolean b, final byte b2, final long n) {
        switch (b2) {
            case 4: {
                this.putMouseEvent(b, (byte)(-1), (byte)0, -120, n);
                this.accum_dz -= 120;
                break;
            }
            case 5: {
                this.putMouseEvent(b, (byte)(-1), (byte)0, -120, n);
                this.accum_dz -= 120;
                break;
            }
            default: {
                this.handleButton(b, b2, (byte)1, n);
                break;
            }
        }
    }
    
    private void handleButtonEvent(final boolean b, final long n, final int n2, final byte b2) {
        final long n3 = n * 1000000L;
        switch (n2) {
            case 5: {
                this.handleButton(b, b2, (byte)0, n3);
                break;
            }
            case 4: {
                this.handleButtonPress(b, b2, n3);
                break;
            }
        }
    }
    
    private void resetCursor(final int last_x, final int n) {
        this.last_x = last_x;
        this.last_y = this.transformY(n);
    }
    
    private void handleWarpEvent(final int n, final int n2) {
        this.resetCursor(n, n2);
    }
    
    public boolean filterEvent(final boolean b, final boolean b2, final LinuxEvent linuxEvent) {
        switch (linuxEvent.getType()) {
            case 33: {
                if (linuxEvent.getClientMessageType() == this.warp_atom) {
                    this.handleWarpEvent(linuxEvent.getClientData(0), linuxEvent.getClientData(1));
                    return true;
                }
                break;
            }
            case 4:
            case 5: {
                this.handleButtonEvent(b, linuxEvent.getButtonTime(), linuxEvent.getButtonType(), (byte)linuxEvent.getButtonButton());
                return true;
            }
            case 6: {
                this.handlePointerMotion(b, b2, linuxEvent.getButtonTime(), linuxEvent.getButtonRoot(), linuxEvent.getButtonXRoot(), linuxEvent.getButtonYRoot(), linuxEvent.getButtonX(), linuxEvent.getButtonY());
                return true;
            }
        }
        return false;
    }
}
