package com.ibm.icu.util;

public abstract class Measure
{
    private Number number;
    private MeasureUnit unit;
    
    protected Measure(final Number number, final MeasureUnit unit) {
        if (number == null || unit == null) {
            throw new NullPointerException();
        }
        this.number = number;
        this.unit = unit;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        final Measure measure = (Measure)o;
        return this.unit.equals(measure.unit) && numbersEqual(this.number, measure.number);
    }
    
    private static boolean numbersEqual(final Number n, final Number n2) {
        return n.equals(n2) || n.doubleValue() == n2.doubleValue();
    }
    
    @Override
    public int hashCode() {
        return this.number.hashCode() ^ this.unit.hashCode();
    }
    
    @Override
    public String toString() {
        return this.number.toString() + ' ' + this.unit.toString();
    }
    
    public Number getNumber() {
        return this.number;
    }
    
    public MeasureUnit getUnit() {
        return this.unit;
    }
}
