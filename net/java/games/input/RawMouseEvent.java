package net.java.games.input;

final class RawMouseEvent
{
    private long millis;
    private int flags;
    private int button_flags;
    private int button_data;
    private long raw_buttons;
    private long last_x;
    private long last_y;
    private long extra_information;
    
    public final void set(final long millis, final int flags, final int button_flags, final int button_data, final long raw_buttons, final long last_x, final long last_y, final long extra_information) {
        this.millis = millis;
        this.flags = flags;
        this.button_flags = button_flags;
        this.button_data = button_data;
        this.raw_buttons = raw_buttons;
        this.last_x = last_x;
        this.last_y = last_y;
        this.extra_information = extra_information;
    }
    
    public final void set(final RawMouseEvent rawMouseEvent) {
        this.set(rawMouseEvent.millis, rawMouseEvent.flags, rawMouseEvent.button_flags, rawMouseEvent.button_data, rawMouseEvent.raw_buttons, rawMouseEvent.last_x, rawMouseEvent.last_y, rawMouseEvent.extra_information);
    }
    
    public final int getWheelDelta() {
        return this.button_data / 120;
    }
    
    private final int getButtonData() {
        return this.button_data;
    }
    
    public final int getFlags() {
        return this.flags;
    }
    
    public final int getButtonFlags() {
        return this.button_flags;
    }
    
    public final int getLastX() {
        return (int)this.last_x;
    }
    
    public final int getLastY() {
        return (int)this.last_y;
    }
    
    public final long getRawButtons() {
        return this.raw_buttons;
    }
    
    public final long getNanos() {
        return this.millis * 1000000L;
    }
}
