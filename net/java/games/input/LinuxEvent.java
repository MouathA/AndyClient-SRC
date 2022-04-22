package net.java.games.input;

final class LinuxEvent
{
    private long nanos;
    private final LinuxAxisDescriptor descriptor;
    private int value;
    
    LinuxEvent() {
        this.descriptor = new LinuxAxisDescriptor();
    }
    
    public final void set(final long n, final long n2, final int n3, final int n4, final int value) {
        this.nanos = (n * 1000000L + n2) * 1000L;
        this.descriptor.set(n3, n4);
        this.value = value;
    }
    
    public final int getValue() {
        return this.value;
    }
    
    public final LinuxAxisDescriptor getDescriptor() {
        return this.descriptor;
    }
    
    public final long getNanos() {
        return this.nanos;
    }
}
