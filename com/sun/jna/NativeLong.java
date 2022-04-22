package com.sun.jna;

public class NativeLong extends IntegerType
{
    public static final int SIZE;
    
    public NativeLong() {
        this(0L);
    }
    
    public NativeLong(final long n) {
        super(NativeLong.SIZE, n);
    }
    
    static {
        SIZE = Native.LONG_SIZE;
    }
}
