package org.apache.commons.lang3.mutable;

public class MutableByte extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = -1585823265L;
    private byte value;
    
    public MutableByte() {
    }
    
    public MutableByte(final byte value) {
        this.value = value;
    }
    
    public MutableByte(final Number n) {
        this.value = n.byteValue();
    }
    
    public MutableByte(final String s) throws NumberFormatException {
        this.value = Byte.parseByte(s);
    }
    
    @Override
    public Byte getValue() {
        return this.value;
    }
    
    public void setValue(final byte value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.byteValue();
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final byte b) {
        this.value += b;
    }
    
    public void add(final Number n) {
        this.value += n.byteValue();
    }
    
    public void subtract(final byte b) {
        this.value -= b;
    }
    
    public void subtract(final Number n) {
        this.value -= n.byteValue();
    }
    
    @Override
    public byte byteValue() {
        return this.value;
    }
    
    @Override
    public int intValue() {
        return this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        return this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    public Byte toByte() {
        return this.byteValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableByte && this.value == ((MutableByte)o).byteValue();
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final MutableByte mutableByte) {
        final byte value = mutableByte.value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableByte)o);
    }
    
    @Override
    public void setValue(final Object o) {
        this.setValue((Number)o);
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
}
