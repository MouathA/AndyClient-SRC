package net.java.games.input;

final class RawKeyboardEvent
{
    private long millis;
    private int make_code;
    private int flags;
    private int vkey;
    private int message;
    private long extra_information;
    
    public final void set(final long millis, final int make_code, final int flags, final int vkey, final int message, final long extra_information) {
        this.millis = millis;
        this.make_code = make_code;
        this.flags = flags;
        this.vkey = vkey;
        this.message = message;
        this.extra_information = extra_information;
    }
    
    public final void set(final RawKeyboardEvent rawKeyboardEvent) {
        this.set(rawKeyboardEvent.millis, rawKeyboardEvent.make_code, rawKeyboardEvent.flags, rawKeyboardEvent.vkey, rawKeyboardEvent.message, rawKeyboardEvent.extra_information);
    }
    
    public final int getVKey() {
        return this.vkey;
    }
    
    public final int getMessage() {
        return this.message;
    }
    
    public final long getNanos() {
        return this.millis * 1000000L;
    }
}
