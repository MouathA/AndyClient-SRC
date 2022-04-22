package com.google.gson.internal;

import java.math.*;
import java.io.*;

public final class LazilyParsedNumber extends Number
{
    private final String value;
    
    public LazilyParsedNumber(final String value) {
        this.value = value;
    }
    
    @Override
    public int intValue() {
        return Integer.parseInt(this.value);
    }
    
    @Override
    public long longValue() {
        return Long.parseLong(this.value);
    }
    
    @Override
    public float floatValue() {
        return Float.parseFloat(this.value);
    }
    
    @Override
    public double doubleValue() {
        return Double.parseDouble(this.value);
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new BigDecimal(this.value);
    }
}
