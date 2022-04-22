package org.lwjgl.opengl;

import java.nio.*;
import java.awt.*;
import java.awt.event.*;

final class KeyboardEventQueue extends EventQueue implements KeyListener
{
    private final byte[] key_states;
    private final ByteBuffer event;
    private final Component component;
    private boolean has_deferred_event;
    private long deferred_nanos;
    private int deferred_key_code;
    private int deferred_key_location;
    private byte deferred_key_state;
    private int deferred_character;
    
    KeyboardEventQueue(final Component component) {
        super(18);
        this.key_states = new byte[256];
        this.event = ByteBuffer.allocate(18);
        this.component = component;
    }
    
    public void register() {
        this.component.addKeyListener(this);
    }
    
    public void unregister() {
    }
    
    private void putKeyboardEvent(final int n, final byte b, final int n2, final long n3, final boolean b2) {
        this.event.clear();
        this.event.putInt(n).put(b).putInt(n2).putLong(n3).put((byte)(b2 ? 1 : 0));
        this.event.flip();
        this.putEvent(this.event);
    }
    
    public synchronized void poll(final ByteBuffer byteBuffer) {
        this.flushDeferredEvent();
        final int position = byteBuffer.position();
        byteBuffer.put(this.key_states);
        byteBuffer.position(position);
    }
    
    @Override
    public synchronized void copyEvents(final ByteBuffer byteBuffer) {
        this.flushDeferredEvent();
        super.copyEvents(byteBuffer);
    }
    
    private synchronized void handleKey(final int deferred_key_code, final int deferred_key_location, final byte deferred_key_state, final int n, final long deferred_nanos) {
        if (0 == 65535) {}
        if (deferred_key_state == 1) {
            if (this.has_deferred_event) {
                if (deferred_nanos == this.deferred_nanos && this.deferred_key_code == deferred_key_code && this.deferred_key_location == deferred_key_location) {
                    this.has_deferred_event = false;
                }
                else {
                    this.flushDeferredEvent();
                }
            }
            this.putKeyEvent(deferred_key_code, deferred_key_location, deferred_key_state, 0, deferred_nanos, true);
        }
        else {
            this.flushDeferredEvent();
            this.has_deferred_event = true;
            this.deferred_nanos = deferred_nanos;
            this.deferred_key_code = deferred_key_code;
            this.deferred_key_location = deferred_key_location;
            this.deferred_key_state = deferred_key_state;
            this.deferred_character = 0;
        }
    }
    
    private void flushDeferredEvent() {
        if (this.has_deferred_event) {
            this.putKeyEvent(this.deferred_key_code, this.deferred_key_location, this.deferred_key_state, this.deferred_character, this.deferred_nanos, false);
            this.has_deferred_event = false;
        }
    }
    
    private void putKeyEvent(final int n, final int n2, final byte b, final int n3, final long n4, final boolean b2) {
        final int mappedKeyCode = this.getMappedKeyCode(n, n2);
        if (this.key_states[mappedKeyCode] == b) {}
        this.putKeyboardEvent(mappedKeyCode, this.key_states[mappedKeyCode] = b, n3 & 0xFFFF, n4, true);
    }
    
    private int getMappedKeyCode(final int n, final int n2) {
        switch (n) {
            case 18: {
                if (n2 == 3) {
                    return 184;
                }
                return 56;
            }
            case 157: {
                if (n2 == 3) {
                    return 220;
                }
                return 219;
            }
            case 16: {
                if (n2 == 3) {
                    return 54;
                }
                return 42;
            }
            case 17: {
                if (n2 == 3) {
                    return 157;
                }
                return 29;
            }
            default: {
                return KeyboardEventQueue.KEY_MAP[n];
            }
        }
    }
    
    public void keyPressed(final KeyEvent keyEvent) {
        this.handleKey(keyEvent.getKeyCode(), keyEvent.getKeyLocation(), (byte)1, keyEvent.getKeyChar(), keyEvent.getWhen() * 1000000L);
    }
    
    public void keyReleased(final KeyEvent keyEvent) {
        this.handleKey(keyEvent.getKeyCode(), keyEvent.getKeyLocation(), (byte)0, 0, keyEvent.getWhen() * 1000000L);
    }
    
    public void keyTyped(final KeyEvent keyEvent) {
    }
    
    static {
        KeyboardEventQueue.KEY_MAP = new int[65535];
    }
}
