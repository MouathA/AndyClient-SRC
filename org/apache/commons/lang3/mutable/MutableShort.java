package org.apache.commons.lang3.mutable;

public class MutableShort extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = -2135791679L;
    private short value;
    
    public MutableShort() {
    }
    
    public MutableShort(final short value) {
        this.value = value;
    }
    
    public MutableShort(final Number n) {
        this.value = n.shortValue();
    }
    
    public MutableShort(final String s) throws NumberFormatException {
        this.value = Short.parseShort(s);
    }
    
    @Override
    public Short getValue() {
        return this.value;
    }
    
    public void setValue(final short value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.shortValue();
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final short n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.shortValue();
    }
    
    public void subtract(final short n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.shortValue();
    }
    
    @Override
    public short shortValue() {
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
    
    public Short toShort() {
        return this.shortValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableShort && this.value == ((MutableShort)o).shortValue();
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final MutableShort mutableShort) {
        final short value = mutableShort.value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableShort)o);
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
