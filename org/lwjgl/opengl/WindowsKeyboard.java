package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class WindowsKeyboard
{
    private final byte[] key_down_buffer;
    private final byte[] virt_key_down_buffer;
    private final EventQueue event_queue;
    private final ByteBuffer tmp_event;
    private boolean has_retained_event;
    private int retained_key_code;
    private byte retained_state;
    private int retained_char;
    private long retained_millis;
    private boolean retained_repeat;
    
    WindowsKeyboard() throws LWJGLException {
        this.key_down_buffer = new byte[256];
        this.virt_key_down_buffer = new byte[256];
        this.event_queue = new EventQueue(18);
        this.tmp_event = ByteBuffer.allocate(18);
    }
    
    private static native boolean isWindowsNT();
    
    boolean isKeyDown(final int n) {
        return this.key_down_buffer[n] == 1;
    }
    
    void poll(final ByteBuffer byteBuffer) {
        if (this.isKeyDown(42) && !isKeyPressedAsync(160)) {
            this.handleKey(16, 42, false, (byte)0, 0L, false);
        }
        if (this.isKeyDown(54) && !isKeyPressedAsync(161)) {
            this.handleKey(16, 54, false, (byte)0, 0L, false);
        }
        final int position = byteBuffer.position();
        byteBuffer.put(this.key_down_buffer);
        byteBuffer.position(position);
    }
    
    private static native int MapVirtualKey(final int p0, final int p1);
    
    private static native int ToUnicode(final int p0, final int p1, final ByteBuffer p2, final CharBuffer p3, final int p4, final int p5);
    
    private static native int ToAscii(final int p0, final int p1, final ByteBuffer p2, final ByteBuffer p3, final int p4);
    
    private static native int GetKeyboardState(final ByteBuffer p0);
    
    private static native short GetKeyState(final int p0);
    
    private static native short GetAsyncKeyState(final int p0);
    
    private void putEvent(final int n, final byte b, final int n2, final long n3, final boolean b2) {
        this.tmp_event.clear();
        this.tmp_event.putInt(n).put(b).putInt(n2).putLong(n3 * 1000000L).put((byte)(b2 ? 1 : 0));
        this.tmp_event.flip();
        this.event_queue.putEvent(this.tmp_event);
    }
    
    private static int translateExtended(final int n, final int n2, final boolean b) {
        switch (n) {
            case 16: {
                return (n2 == 54) ? 161 : 160;
            }
            case 17: {
                return b ? 163 : 162;
            }
            case 18: {
                return b ? 165 : 164;
            }
            default: {
                return n;
            }
        }
    }
    
    private void flushRetained() {
        if (this.has_retained_event) {
            this.has_retained_event = false;
            this.putEvent(this.retained_key_code, this.retained_state, this.retained_char, this.retained_millis, this.retained_repeat);
        }
    }
    
    private static boolean isKeyPressed(final int n) {
        return (n & 0x1) == 0x1;
    }
    
    private static boolean isKeyPressedAsync(final int n) {
        return (GetAsyncKeyState(n) & 0x8000) != 0x0;
    }
    
    void releaseAll(final long n) {
        while (0 < this.virt_key_down_buffer.length) {
            if (isKeyPressed(this.virt_key_down_buffer[0])) {
                this.handleKey(0, 0, false, (byte)0, n, false);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void handleKey(int translateExtended, final int n, final boolean b, final byte retained_state, final long retained_millis, boolean retained_repeat) {
        translateExtended = translateExtended(translateExtended, n, b);
        if (!retained_repeat && isKeyPressed(retained_state) == isKeyPressed(this.virt_key_down_buffer[translateExtended])) {
            return;
        }
        this.flushRetained();
        this.has_retained_event = true;
        final int mapVirtualKeyToLWJGLCode = WindowsKeycodes.mapVirtualKeyToLWJGLCode(translateExtended);
        if (mapVirtualKeyToLWJGLCode < this.key_down_buffer.length) {
            this.key_down_buffer[mapVirtualKeyToLWJGLCode] = retained_state;
            retained_repeat &= isKeyPressed(this.virt_key_down_buffer[translateExtended]);
            this.virt_key_down_buffer[translateExtended] = retained_state;
        }
        this.retained_key_code = mapVirtualKeyToLWJGLCode;
        this.retained_state = retained_state;
        this.retained_millis = retained_millis;
        this.retained_char = 0;
        this.retained_repeat = retained_repeat;
    }
    
    void handleChar(final int retained_char, final long n, final boolean b) {
        if (this.has_retained_event && this.retained_char != 0) {
            this.flushRetained();
        }
        if (!this.has_retained_event) {
            this.putEvent(0, (byte)0, retained_char, n, b);
        }
        else {
            this.retained_char = retained_char;
        }
    }
    
    void read(final ByteBuffer byteBuffer) {
        this.flushRetained();
        this.event_queue.copyEvents(byteBuffer);
    }
}
