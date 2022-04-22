package org.apache.commons.lang3.mutable;

public class MutableLong extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 62986528375L;
    private long value;
    
    public MutableLong() {
    }
    
    public MutableLong(final long value) {
        this.value = value;
    }
    
    public MutableLong(final Number n) {
        this.value = n.longValue();
    }
    
    public MutableLong(final String s) throws NumberFormatException {
        this.value = Long.parseLong(s);
    }
    
    @Override
    public Long getValue() {
        return this.value;
    }
    
    public void setValue(final long value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.longValue();
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final long n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.longValue();
    }
    
    public void subtract(final long n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.longValue();
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        return (float)this.value;
    }
    
    @Override
    public double doubleValue() {
        return (double)this.value;
    }
    
    public Long toLong() {
        return this.longValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableLong && this.value == ((MutableLong)o).longValue();
    }
    
    @Override
    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }
    
    public int compareTo(final MutableLong mutableLong) {
        final long value = mutableLong.value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableLong)o);
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
