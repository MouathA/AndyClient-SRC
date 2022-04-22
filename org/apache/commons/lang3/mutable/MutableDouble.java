package org.apache.commons.lang3.mutable;

public class MutableDouble extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 1587163916L;
    private double value;
    
    public MutableDouble() {
    }
    
    public MutableDouble(final double value) {
        this.value = value;
    }
    
    public MutableDouble(final Number n) {
        this.value = n.doubleValue();
    }
    
    public MutableDouble(final String s) throws NumberFormatException {
        this.value = Double.parseDouble(s);
    }
    
    @Override
    public Double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.doubleValue();
    }
    
    public boolean isNaN() {
        return Double.isNaN(this.value);
    }
    
    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final double n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.doubleValue();
    }
    
    public void subtract(final double n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.doubleValue();
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return (long)this.value;
    }
    
    @Override
    public float floatValue() {
        return (float)this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    public Double toDouble() {
        return this.doubleValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableDouble && Double.doubleToLongBits(((MutableDouble)o).value) == Double.doubleToLongBits(this.value);
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.value);
        return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    public int compareTo(final MutableDouble mutableDouble) {
        return Double.compare(this.value, mutableDouble.value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableDouble)o);
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
