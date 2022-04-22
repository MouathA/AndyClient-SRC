package net.java.games.input;

final class LinuxJoystickEvent
{
    private long nanos;
    private int value;
    private int type;
    private int number;
    
    public final void set(final long n, final int value, final int type, final int number) {
        this.nanos = n * 1000000L;
        this.value = value;
        this.type = type;
        this.number = number;
    }
    
    public final int getValue() {
        return this.value;
    }
    
    public final int getType() {
        return this.type;
    }
    
    public final int getNumber() {
        return this.number;
    }
    
    public final long getNanos() {
        return this.nanos;
    }
}
