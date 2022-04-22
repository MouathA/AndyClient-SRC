package org.lwjgl.opengl;

import java.nio.*;
import java.nio.charset.*;
import org.lwjgl.*;

final class LinuxKeyboard
{
    private static final int LockMapIndex = 1;
    private static final long NoSymbol = 0L;
    private static final long ShiftMask = 1L;
    private static final long LockMask = 2L;
    private static final int XLookupChars = 2;
    private static final int XLookupBoth = 4;
    private static final int KEYBOARD_BUFFER_SIZE = 50;
    private final long xim;
    private final long xic;
    private final int numlock_mask;
    private final int modeswitch_mask;
    private final int caps_lock_mask;
    private final int shift_lock_mask;
    private final ByteBuffer compose_status;
    private final byte[] key_down_buffer;
    private final EventQueue event_queue;
    private final ByteBuffer tmp_event;
    private final int[] temp_translation_buffer;
    private final ByteBuffer native_translation_buffer;
    private final CharsetDecoder utf8_decoder;
    private final CharBuffer char_buffer;
    private boolean has_deferred_event;
    private int deferred_keycode;
    private int deferred_event_keycode;
    private long deferred_nanos;
    private byte deferred_key_state;
    
    LinuxKeyboard(final long n, final long n2) {
        this.key_down_buffer = new byte[256];
        this.event_queue = new EventQueue(18);
        this.tmp_event = ByteBuffer.allocate(18);
        this.temp_translation_buffer = new int[50];
        this.native_translation_buffer = BufferUtils.createByteBuffer(50);
        this.utf8_decoder = Charset.forName("UTF-8").newDecoder();
        this.char_buffer = CharBuffer.allocate(50);
        final long modifierMapping = getModifierMapping(n);
        if (modifierMapping != 0L) {
            final int maxKeyPerMod = getMaxKeyPerMod(modifierMapping);
            while (0 < 8) {
                while (0 < maxKeyPerMod) {
                    switch ((int)keycodeToKeySym(n, lookupModifierMap(modifierMapping, 0 * maxKeyPerMod + 0))) {
                        case 65407: {}
                        case 65509: {
                            if (false == true) {
                                break;
                            }
                            break;
                        }
                        case 65510: {
                            if (false != true || !false) {}
                            break;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                int n4 = 0;
                ++n4;
            }
            freeModifierMapping(modifierMapping);
        }
        this.numlock_mask = 0;
        this.modeswitch_mask = 0;
        this.caps_lock_mask = 0;
        this.shift_lock_mask = 0;
        setDetectableKeyRepeat(n, true);
        this.xim = openIM(n);
        if (this.xim != 0L) {
            this.xic = createIC(this.xim, n2);
            if (this.xic != 0L) {
                setupIMEventMask(n, n2, this.xic);
            }
            else {
                this.destroy(n);
            }
        }
        else {
            this.xic = 0L;
        }
        this.compose_status = allocateComposeStatus();
    }
    
    private static native long getModifierMapping(final long p0);
    
    private static native void freeModifierMapping(final long p0);
    
    private static native int getMaxKeyPerMod(final long p0);
    
    private static native int lookupModifierMap(final long p0, final int p1);
    
    private static native long keycodeToKeySym(final long p0, final int p1);
    
    private static native long openIM(final long p0);
    
    private static native long createIC(final long p0, final long p1);
    
    private static native void setupIMEventMask(final long p0, final long p1, final long p2);
    
    private static native ByteBuffer allocateComposeStatus();
    
    private static void setDetectableKeyRepeat(final long n, final boolean b) {
        if (!nSetDetectableKeyRepeat(n, b)) {
            LWJGLUtil.log("Failed to set detectable key repeat to " + b);
        }
    }
    
    private static native boolean nSetDetectableKeyRepeat(final long p0, final boolean p1);
    
    public void destroy(final long n) {
        if (this.xic != 0L) {
            destroyIC(this.xic);
        }
        if (this.xim != 0L) {
            closeIM(this.xim);
        }
        setDetectableKeyRepeat(n, false);
    }
    
    private static native void destroyIC(final long p0);
    
    private static native void closeIM(final long p0);
    
    public void read(final ByteBuffer byteBuffer) {
        this.flushDeferredEvent();
        this.event_queue.copyEvents(byteBuffer);
    }
    
    public void poll(final ByteBuffer byteBuffer) {
        this.flushDeferredEvent();
        final int position = byteBuffer.position();
        byteBuffer.put(this.key_down_buffer);
        byteBuffer.position(position);
    }
    
    private void putKeyboardEvent(final int n, final byte b, final int n2, final long n3, final boolean b2) {
        this.tmp_event.clear();
        this.tmp_event.putInt(n).put(b).putInt(n2).putLong(n3).put((byte)(b2 ? 1 : 0));
        this.tmp_event.flip();
        this.event_queue.putEvent(this.tmp_event);
    }
    
    private int lookupStringISO88591(final long n, final int[] array) {
        final int lookupString = lookupString(n, this.native_translation_buffer, this.compose_status);
        while (0 < lookupString) {
            array[0] = (this.native_translation_buffer.get(0) & 0xFF);
            int n2 = 0;
            ++n2;
        }
        return lookupString;
    }
    
    private static native int lookupString(final long p0, final ByteBuffer p1, final ByteBuffer p2);
    
    private int lookupStringUnicode(final long n, final int[] array) {
        final int utf8LookupString = utf8LookupString(this.xic, n, this.native_translation_buffer, this.native_translation_buffer.position(), this.native_translation_buffer.remaining());
        if (utf8LookupString != 2 && utf8LookupString != 4) {
            return 0;
        }
        this.native_translation_buffer.flip();
        this.utf8_decoder.decode(this.native_translation_buffer, this.char_buffer, true);
        this.native_translation_buffer.compact();
        this.char_buffer.flip();
        while (this.char_buffer.hasRemaining() && 0 < array.length) {
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            array[n2] = this.char_buffer.get();
        }
        this.char_buffer.compact();
        return 0;
    }
    
    private static native int utf8LookupString(final long p0, final long p1, final ByteBuffer p2, final int p3, final int p4);
    
    private int lookupString(final long n, final int[] array) {
        if (this.xic != 0L) {
            return this.lookupStringUnicode(n, array);
        }
        return this.lookupStringISO88591(n, array);
    }
    
    private void translateEvent(final long n, final int n2, final byte b, final long n3, final boolean b2) {
        final int lookupString = this.lookupString(n, this.temp_translation_buffer);
        if (lookupString > 0) {
            this.putKeyboardEvent(n2, b, this.temp_translation_buffer[0], n3, b2);
            while (1 < lookupString) {
                this.putKeyboardEvent(0, (byte)0, this.temp_translation_buffer[1], n3, b2);
                int n4 = 0;
                ++n4;
            }
        }
        else {
            this.putKeyboardEvent(n2, b, 0, n3, b2);
        }
    }
    
    private static boolean isKeypadKeysym(final long n) {
        return (65408L <= n && n <= 65469L) || (285212672L <= n && n <= 285278207L);
    }
    
    private static boolean isNoSymbolOrVendorSpecific(final long n) {
        return n == 0L || (n & 0x10000000L) != 0x0L;
    }
    
    private static long getKeySym(final long n, final int n2, final int n3) {
        long n4 = lookupKeysym(n, n2 * 2 + n3);
        if (isNoSymbolOrVendorSpecific(n4) && n3 == 1) {
            n4 = lookupKeysym(n, n2 * 2 + 0);
        }
        if (isNoSymbolOrVendorSpecific(n4) && n2 == 1) {
            n4 = getKeySym(n, 0, n3);
        }
        return n4;
    }
    
    private static native long lookupKeysym(final long p0, final int p1);
    
    private static native long toUpper(final long p0);
    
    private long mapEventToKeySym(final long n, final int n2) {
        if ((n2 & this.modeswitch_mask) != 0x0) {}
        final long keySym;
        if ((n2 & this.numlock_mask) != 0x0 && isKeypadKeysym(keySym = getKeySym(n, 0, 1))) {
            if (((long)n2 & (0x1L | (long)this.shift_lock_mask)) != 0x0L) {
                return getKeySym(n, 0, 0);
            }
            return keySym;
        }
        else {
            if (((long)n2 & 0x3L) == 0x0L) {
                return getKeySym(n, 0, 0);
            }
            if (((long)n2 & 0x1L) == 0x0L) {
                long n3 = getKeySym(n, 0, 0);
                if ((n2 & this.caps_lock_mask) != 0x0) {
                    n3 = toUpper(n3);
                }
                return n3;
            }
            long n4 = getKeySym(n, 0, 1);
            if ((n2 & this.caps_lock_mask) != 0x0) {
                n4 = toUpper(n4);
            }
            return n4;
        }
    }
    
    private int getKeycode(final long n, final int n2) {
        int n3 = LinuxKeycodes.mapKeySymToLWJGLKeyCode(this.mapEventToKeySym(n, n2));
        if (n3 == 0) {
            n3 = LinuxKeycodes.mapKeySymToLWJGLKeyCode(lookupKeysym(n, 0));
        }
        return n3;
    }
    
    private static byte getKeyState(final int n) {
        switch (n) {
            case 2: {
                return 1;
            }
            case 3: {
                return 0;
            }
            default: {
                throw new IllegalArgumentException("Unknown event_type: " + n);
            }
        }
    }
    
    void releaseAll() {
        while (0 < this.key_down_buffer.length) {
            if (this.key_down_buffer[0] != 0) {
                this.putKeyboardEvent(0, this.key_down_buffer[0] = 0, 0, 0L, false);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void handleKeyEvent(final long n, final long n2, final int n3, final int deferred_event_keycode, final int n4) {
        final int keycode = this.getKeycode(n, n4);
        final byte keyState = getKeyState(n3);
        final boolean b = keyState == this.key_down_buffer[keycode];
        if (true && n3 == 3) {
            return;
        }
        this.key_down_buffer[keycode] = keyState;
        final long deferred_nanos = n2 * 1000000L;
        if (n3 == 2) {
            if (this.has_deferred_event) {
                if (deferred_nanos == this.deferred_nanos && deferred_event_keycode == this.deferred_event_keycode) {
                    this.has_deferred_event = false;
                }
                else {
                    this.flushDeferredEvent();
                }
            }
            this.translateEvent(n, keycode, keyState, deferred_nanos, true);
        }
        else {
            this.flushDeferredEvent();
            this.has_deferred_event = true;
            this.deferred_keycode = keycode;
            this.deferred_event_keycode = deferred_event_keycode;
            this.deferred_nanos = deferred_nanos;
            this.deferred_key_state = keyState;
        }
    }
    
    private void flushDeferredEvent() {
        if (this.has_deferred_event) {
            this.putKeyboardEvent(this.deferred_keycode, this.deferred_key_state, 0, this.deferred_nanos, false);
            this.has_deferred_event = false;
        }
    }
    
    public boolean filterEvent(final LinuxEvent linuxEvent) {
        switch (linuxEvent.getType()) {
            case 2:
            case 3: {
                this.handleKeyEvent(linuxEvent.getKeyAddress(), linuxEvent.getKeyTime(), linuxEvent.getKeyType(), linuxEvent.getKeyKeyCode(), linuxEvent.getKeyState());
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
