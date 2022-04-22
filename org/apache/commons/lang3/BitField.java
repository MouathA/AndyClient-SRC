package org.apache.commons.lang3;

public class BitField
{
    private final int _mask;
    private final int _shift_count;
    
    public BitField(final int mask) {
        this._mask = mask;
        int n = mask;
        if (n != 0) {
            while ((n & 0x1) == 0x0) {
                int n2 = 0;
                ++n2;
                n >>= 1;
            }
        }
        this._shift_count = 0;
    }
    
    public int getValue(final int n) {
        return this.getRawValue(n) >> this._shift_count;
    }
    
    public short getShortValue(final short n) {
        return (short)this.getValue(n);
    }
    
    public int getRawValue(final int n) {
        return n & this._mask;
    }
    
    public short getShortRawValue(final short n) {
        return (short)this.getRawValue(n);
    }
    
    public boolean isSet(final int n) {
        return (n & this._mask) != 0x0;
    }
    
    public boolean isAllSet(final int n) {
        return (n & this._mask) == this._mask;
    }
    
    public int setValue(final int n, final int n2) {
        return (n & ~this._mask) | (n2 << this._shift_count & this._mask);
    }
    
    public short setShortValue(final short n, final short n2) {
        return (short)this.setValue(n, n2);
    }
    
    public int clear(final int n) {
        return n & ~this._mask;
    }
    
    public short clearShort(final short n) {
        return (short)this.clear(n);
    }
    
    public byte clearByte(final byte b) {
        return (byte)this.clear(b);
    }
    
    public int set(final int n) {
        return n | this._mask;
    }
    
    public short setShort(final short n) {
        return (short)this.set(n);
    }
    
    public byte setByte(final byte b) {
        return (byte)this.set(b);
    }
    
    public int setBoolean(final int n, final boolean b) {
        return b ? this.set(n) : this.clear(n);
    }
    
    public short setShortBoolean(final short short1, final boolean b) {
        return b ? this.setShort(short1) : this.clearShort(short1);
    }
    
    public byte setByteBoolean(final byte byte1, final boolean b) {
        return b ? this.setByte(byte1) : this.clearByte(byte1);
    }
}
