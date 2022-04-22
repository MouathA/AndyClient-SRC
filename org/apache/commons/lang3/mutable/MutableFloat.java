package org.apache.commons.lang3.mutable;

public class MutableFloat extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 5787169186L;
    private float value;
    
    public MutableFloat() {
    }
    
    public MutableFloat(final float value) {
        this.value = value;
    }
    
    public MutableFloat(final Number n) {
        this.value = n.floatValue();
    }
    
    public MutableFloat(final String s) throws NumberFormatException {
        this.value = Float.parseFloat(s);
    }
    
    @Override
    public Float getValue() {
        return this.value;
    }
    
    public void setValue(final float value) {
        this.value = value;
    }
    
    public void setValue(final Number n) {
        this.value = n.floatValue();
    }
    
    public boolean isNaN() {
        return Float.isNaN(this.value);
    }
    
    public boolean isInfinite() {
        return Float.isInfinite(this.value);
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final float n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.floatValue();
    }
    
    public void subtract(final float n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.floatValue();
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
        return this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    public Float toFloat() {
        return this.floatValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableFloat && Float.floatToIntBits(((MutableFloat)o).value) == Float.floatToIntBits(this.value);
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }
    
    public int compareTo(final MutableFloat mutableFloat) {
        return Float.compare(this.value, mutableFloat.value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableFloat)o);
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
