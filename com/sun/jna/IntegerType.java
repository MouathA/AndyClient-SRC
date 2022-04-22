package com.sun.jna;

public abstract class IntegerType extends Number implements NativeMapped
{
    private int size;
    private Number number;
    private boolean unsigned;
    private long value;
    
    public IntegerType(final int n) {
        this(n, 0L, false);
    }
    
    public IntegerType(final int n, final boolean b) {
        this(n, 0L, b);
    }
    
    public IntegerType(final int n, final long n2) {
        this(n, n2, false);
    }
    
    public IntegerType(final int size, final long value, final boolean unsigned) {
        this.size = size;
        this.unsigned = unsigned;
        this.setValue(value);
    }
    
    public void setValue(final long value) {
        long n = value;
        this.value = value;
        switch (this.size) {
            case 1: {
                if (this.unsigned) {
                    this.value = (value & 0xFFL);
                }
                n = (byte)value;
                this.number = new Byte((byte)value);
                break;
            }
            case 2: {
                if (this.unsigned) {
                    this.value = (value & 0xFFFFL);
                }
                n = (short)value;
                this.number = new Short((short)value);
                break;
            }
            case 4: {
                if (this.unsigned) {
                    this.value = (value & 0xFFFFFFFFL);
                }
                n = (int)value;
                this.number = new Integer((int)value);
                break;
            }
            case 8: {
                this.number = new Long(value);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported size: " + this.size);
            }
        }
        if (this.size < 8) {
            final long n2 = ~((1L << this.size * 8) - 1L);
            if ((value < 0L && n != value) || (value >= 0L && (n2 & value) != 0x0L)) {
                throw new IllegalArgumentException("Argument value 0x" + Long.toHexString(value) + " exceeds native capacity (" + this.size + " bytes) mask=0x" + Long.toHexString(n2));
            }
        }
    }
    
    public Object toNative() {
        return this.number;
    }
    
    public Object fromNative(final Object o, final FromNativeContext fromNativeContext) {
        final long value = (o == null) ? 0L : ((Number)o).longValue();
        final IntegerType integerType = (IntegerType)this.getClass().newInstance();
        integerType.setValue(value);
        return integerType;
    }
    
    public Class nativeType() {
        return this.number.getClass();
    }
    
    public int intValue() {
        return (int)this.value;
    }
    
    public long longValue() {
        return this.value;
    }
    
    public float floatValue() {
        return this.number.floatValue();
    }
    
    public double doubleValue() {
        return this.number.doubleValue();
    }
    
    public boolean equals(final Object o) {
        return o instanceof IntegerType && this.number.equals(((IntegerType)o).number);
    }
    
    public String toString() {
        return this.number.toString();
    }
    
    public int hashCode() {
        return this.number.hashCode();
    }
}
