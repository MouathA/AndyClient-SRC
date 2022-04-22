package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class WindowsMouse
{
    private final long hwnd;
    private final int mouse_button_count;
    private final boolean has_wheel;
    private final EventQueue event_queue;
    private final ByteBuffer mouse_event;
    private final Object blank_cursor;
    private boolean mouse_grabbed;
    private byte[] button_states;
    private int accum_dx;
    private int accum_dy;
    private int accum_dwheel;
    private int last_x;
    private int last_y;
    
    WindowsMouse(final long hwnd) throws LWJGLException {
        this.event_queue = new EventQueue(22);
        this.mouse_event = ByteBuffer.allocate(22);
        this.hwnd = hwnd;
        this.mouse_button_count = Math.min(5, WindowsDisplay.getSystemMetrics(43));
        this.has_wheel = (WindowsDisplay.getSystemMetrics(75) != 0);
        this.blank_cursor = this.createBlankCursor();
        this.button_states = new byte[this.mouse_button_count];
    }
    
    private Object createBlankCursor() throws LWJGLException {
        final int systemMetrics = WindowsDisplay.getSystemMetrics(13);
        final int systemMetrics2 = WindowsDisplay.getSystemMetrics(14);
        return WindowsDisplay.doCreateCursor(systemMetrics, systemMetrics2, 0, 0, 1, BufferUtils.createIntBuffer(systemMetrics * systemMetrics2), null);
    }
    
    public boolean isGrabbed() {
        return this.mouse_grabbed;
    }
    
    public boolean hasWheel() {
        return this.has_wheel;
    }
    
    public int getButtonCount() {
        return this.mouse_button_count;
    }
    
    public void poll(final IntBuffer intBuffer, final ByteBuffer byteBuffer, final WindowsDisplay windowsDisplay) {
        while (0 < intBuffer.remaining()) {
            intBuffer.put(intBuffer.position() + 0, 0);
            int n = 0;
            ++n;
        }
        int n = this.mouse_button_count;
        intBuffer.put(intBuffer.position() + 2, this.accum_dwheel);
        if (0 > this.button_states.length) {
            n = this.button_states.length;
        }
        while (0 < 0) {
            byteBuffer.put(byteBuffer.position() + 0, this.button_states[0]);
            int n2 = 0;
            ++n2;
        }
        if (this.isGrabbed()) {
            intBuffer.put(intBuffer.position() + 0, this.accum_dx);
            intBuffer.put(intBuffer.position() + 1, this.accum_dy);
            if (windowsDisplay.isActive() && windowsDisplay.isVisible() && (this.accum_dx != 0 || this.accum_dy != 0)) {
                WindowsDisplay.centerCursor(this.hwnd);
            }
        }
        else {
            intBuffer.put(intBuffer.position() + 0, this.last_x);
            intBuffer.put(intBuffer.position() + 1, this.last_y);
        }
        final int accum_dx = 0;
        this.accum_dwheel = accum_dx;
        this.accum_dy = accum_dx;
        this.accum_dx = accum_dx;
    }
    
    private void putMouseEventWithCoords(final byte b, final byte b2, final int n, final int n2, final int n3, final long n4) {
        this.mouse_event.clear();
        this.mouse_event.put(b).put(b2).putInt(n).putInt(n2).putInt(n3).putLong(n4);
        this.mouse_event.flip();
        this.event_queue.putEvent(this.mouse_event);
    }
    
    private void putMouseEvent(final byte b, final byte b2, final int n, final long n2) {
        if (this.mouse_grabbed) {
            this.putMouseEventWithCoords(b, b2, 0, 0, n, n2);
        }
        else {
            this.putMouseEventWithCoords(b, b2, this.last_x, this.last_y, n, n2);
        }
    }
    
    public void read(final ByteBuffer byteBuffer) {
        this.event_queue.copyEvents(byteBuffer);
    }
    
    public Object getBlankCursor() {
        return this.blank_cursor;
    }
    
    public void grab(final boolean mouse_grabbed) {
        this.mouse_grabbed = mouse_grabbed;
        this.event_queue.clearEvents();
    }
    
    public void handleMouseScrolled(final int n, final long n2) {
        this.accum_dwheel += n;
        this.putMouseEvent((byte)(-1), (byte)0, n, n2 * 1000000L);
    }
    
    public void setPosition(final int last_x, final int last_y) {
        this.last_x = last_x;
        this.last_y = last_y;
    }
    
    public void destroy() {
        WindowsDisplay.doDestroyCursor(this.blank_cursor);
    }
    
    public void handleMouseMoved(final int last_x, final int last_y, final long n) {
        final int n2 = last_x - this.last_x;
        final int n3 = last_y - this.last_y;
        if (n2 != 0 || n3 != 0) {
            this.accum_dx += n2;
            this.accum_dy += n3;
            this.last_x = last_x;
            this.last_y = last_y;
            final long n4 = n * 1000000L;
            if (this.mouse_grabbed) {
                this.putMouseEventWithCoords((byte)(-1), (byte)0, n2, n3, 0, n4);
            }
            else {
                this.putMouseEventWithCoords((byte)(-1), (byte)0, last_x, last_y, 0, n4);
            }
        }
    }
    
    public void handleMouseButton(final byte b, final byte b2, final long n) {
        this.putMouseEvent(b, b2, 0, n * 1000000L);
        if (b < this.button_states.length) {
            this.button_states[b] = (byte)((b2 != 0) ? 1 : 0);
        }
    }
}
