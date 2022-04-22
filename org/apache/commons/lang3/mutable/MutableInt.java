package org.apache.commons.lang3.mutable;

public class MutableInt extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 512176391864L;
    private int value;
    
    public MutableInt() {
    }
    
    public MutableInt(final int value) {
        this.value = value;
    }
    
    public MutableInt(final Number n) {
        this.value = n.intValue();
    }
    
    public MutableInt(final String s) throws NumberFormatException {
        this.value = Integer.parseInt(s);
    }
    
    @Override
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.intValue();
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final int n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.intValue();
    }
    
    public void subtract(final int n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.intValue();
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
        return (float)this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    public Integer toInteger() {
        return this.intValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableInt && this.value == ((MutableInt)o).intValue();
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final MutableInt mutableInt) {
        final int value = mutableInt.value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableInt)o);
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
