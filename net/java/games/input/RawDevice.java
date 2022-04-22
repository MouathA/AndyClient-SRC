package net.java.games.input;

import java.io.*;

final class RawDevice
{
    private final RawInputEventQueue queue;
    private final long handle;
    private final int type;
    private DataQueue keyboard_events;
    private DataQueue mouse_events;
    private DataQueue processed_keyboard_events;
    private DataQueue processed_mouse_events;
    private final boolean[] button_states;
    private int wheel;
    private int relative_x;
    private int relative_y;
    private int last_x;
    private int last_y;
    private int event_relative_x;
    private int event_relative_y;
    private int event_last_x;
    private int event_last_y;
    private final boolean[] key_states;
    static Class class$net$java$games$input$RawKeyboardEvent;
    static Class class$net$java$games$input$RawMouseEvent;
    
    public RawDevice(final RawInputEventQueue queue, final long handle, final int type) {
        this.button_states = new boolean[5];
        this.key_states = new boolean[255];
        this.queue = queue;
        this.handle = handle;
        this.type = type;
        this.setBufferSize(32);
    }
    
    public final synchronized void addMouseEvent(final long n, final int n2, final int n3, final int n4, final long n5, final long n6, final long n7, final long n8) {
        if (this.mouse_events.hasRemaining()) {
            ((RawMouseEvent)this.mouse_events.get()).set(n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    public final synchronized void addKeyboardEvent(final long n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        if (this.keyboard_events.hasRemaining()) {
            ((RawKeyboardEvent)this.keyboard_events.get()).set(n, n2, n3, n4, n5, n6);
        }
    }
    
    public final synchronized void pollMouse() {
        final int relative_x = 0;
        this.wheel = relative_x;
        this.relative_y = relative_x;
        this.relative_x = relative_x;
        this.mouse_events.flip();
        while (this.mouse_events.hasRemaining()) {
            final RawMouseEvent rawMouseEvent = (RawMouseEvent)this.mouse_events.get();
            if (this.processMouseEvent(rawMouseEvent) && this.processed_mouse_events.hasRemaining()) {
                ((RawMouseEvent)this.processed_mouse_events.get()).set(rawMouseEvent);
            }
        }
        this.mouse_events.compact();
    }
    
    public final synchronized void pollKeyboard() {
        this.keyboard_events.flip();
        while (this.keyboard_events.hasRemaining()) {
            final RawKeyboardEvent rawKeyboardEvent = (RawKeyboardEvent)this.keyboard_events.get();
            if (this.processKeyboardEvent(rawKeyboardEvent) && this.processed_keyboard_events.hasRemaining()) {
                ((RawKeyboardEvent)this.processed_keyboard_events.get()).set(rawKeyboardEvent);
            }
        }
        this.keyboard_events.compact();
    }
    
    private final boolean updateButtonState(final int n, final int n2, final int n3, final int n4) {
        if (n >= this.button_states.length) {
            return false;
        }
        if ((n2 & n3) != 0x0) {
            return this.button_states[n] = true;
        }
        if ((n2 & n4) != 0x0) {
            this.button_states[n] = false;
            return true;
        }
        return false;
    }
    
    private final boolean processKeyboardEvent(final RawKeyboardEvent rawKeyboardEvent) {
        final int message = rawKeyboardEvent.getMessage();
        final int vKey = rawKeyboardEvent.getVKey();
        if (vKey >= this.key_states.length) {
            return false;
        }
        if (message == 256 || message == 260) {
            return this.key_states[vKey] = true;
        }
        if (message == 257 || message == 261) {
            this.key_states[vKey] = false;
            return true;
        }
        return false;
    }
    
    public final boolean isKeyDown(final int n) {
        return this.key_states[n];
    }
    
    private final boolean processMouseEvent(final RawMouseEvent rawMouseEvent) {
        final int buttonFlags = rawMouseEvent.getButtonFlags();
        final boolean b = this.updateButtonState(0, buttonFlags, 1, 2) || false;
        final boolean b2 = this.updateButtonState(1, buttonFlags, 4, 8) || false;
        final boolean b3 = this.updateButtonState(2, buttonFlags, 16, 32) || false;
        final boolean b4 = this.updateButtonState(3, buttonFlags, 64, 128) || false;
        final boolean b5 = this.updateButtonState(4, buttonFlags, 256, 512) || false;
        int lastX;
        int lastY;
        if ((rawMouseEvent.getFlags() & 0x1) != 0x0) {
            lastX = rawMouseEvent.getLastX() - this.last_x;
            lastY = rawMouseEvent.getLastY() - this.last_y;
            this.last_x = rawMouseEvent.getLastX();
            this.last_y = rawMouseEvent.getLastY();
        }
        else {
            lastX = rawMouseEvent.getLastX();
            lastY = rawMouseEvent.getLastY();
        }
        if ((buttonFlags & 0x400) != 0x0) {
            rawMouseEvent.getWheelDelta();
        }
        this.relative_x += lastX;
        this.relative_y += lastY;
        this.wheel += 0;
        final boolean b6 = lastX != 0 || lastY != 0 || false || false;
        return false;
    }
    
    public final int getWheel() {
        return this.wheel;
    }
    
    public final int getEventRelativeX() {
        return this.event_relative_x;
    }
    
    public final int getEventRelativeY() {
        return this.event_relative_y;
    }
    
    public final int getRelativeX() {
        return this.relative_x;
    }
    
    public final int getRelativeY() {
        return this.relative_y;
    }
    
    public final synchronized boolean getNextKeyboardEvent(final RawKeyboardEvent rawKeyboardEvent) {
        this.processed_keyboard_events.flip();
        if (!this.processed_keyboard_events.hasRemaining()) {
            this.processed_keyboard_events.compact();
            return false;
        }
        rawKeyboardEvent.set((RawKeyboardEvent)this.processed_keyboard_events.get());
        this.processed_keyboard_events.compact();
        return true;
    }
    
    public final synchronized boolean getNextMouseEvent(final RawMouseEvent rawMouseEvent) {
        this.processed_mouse_events.flip();
        if (!this.processed_mouse_events.hasRemaining()) {
            this.processed_mouse_events.compact();
            return false;
        }
        final RawMouseEvent rawMouseEvent2 = (RawMouseEvent)this.processed_mouse_events.get();
        if ((rawMouseEvent2.getFlags() & 0x1) != 0x0) {
            this.event_relative_x = rawMouseEvent2.getLastX() - this.event_last_x;
            this.event_relative_y = rawMouseEvent2.getLastY() - this.event_last_y;
            this.event_last_x = rawMouseEvent2.getLastX();
            this.event_last_y = rawMouseEvent2.getLastY();
        }
        else {
            this.event_relative_x = rawMouseEvent2.getLastX();
            this.event_relative_y = rawMouseEvent2.getLastY();
        }
        rawMouseEvent.set(rawMouseEvent2);
        this.processed_mouse_events.compact();
        return true;
    }
    
    public final boolean getButtonState(final int n) {
        return n < this.button_states.length && this.button_states[n];
    }
    
    public final void setBufferSize(final int n) {
        this.keyboard_events = new DataQueue(n, (RawDevice.class$net$java$games$input$RawKeyboardEvent == null) ? (RawDevice.class$net$java$games$input$RawKeyboardEvent = class$("net.java.games.input.RawKeyboardEvent")) : RawDevice.class$net$java$games$input$RawKeyboardEvent);
        this.mouse_events = new DataQueue(n, (RawDevice.class$net$java$games$input$RawMouseEvent == null) ? (RawDevice.class$net$java$games$input$RawMouseEvent = class$("net.java.games.input.RawMouseEvent")) : RawDevice.class$net$java$games$input$RawMouseEvent);
        this.processed_keyboard_events = new DataQueue(n, (RawDevice.class$net$java$games$input$RawKeyboardEvent == null) ? (RawDevice.class$net$java$games$input$RawKeyboardEvent = class$("net.java.games.input.RawKeyboardEvent")) : RawDevice.class$net$java$games$input$RawKeyboardEvent);
        this.processed_mouse_events = new DataQueue(n, (RawDevice.class$net$java$games$input$RawMouseEvent == null) ? (RawDevice.class$net$java$games$input$RawMouseEvent = class$("net.java.games.input.RawMouseEvent")) : RawDevice.class$net$java$games$input$RawMouseEvent);
    }
    
    public final int getType() {
        return this.type;
    }
    
    public final long getHandle() {
        return this.handle;
    }
    
    public final String getName() throws IOException {
        return nGetName(this.handle);
    }
    
    private static final native String nGetName(final long p0) throws IOException;
    
    public final RawDeviceInfo getInfo() throws IOException {
        return nGetInfo(this, this.handle);
    }
    
    private static final native RawDeviceInfo nGetInfo(final RawDevice p0, final long p1) throws IOException;
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
