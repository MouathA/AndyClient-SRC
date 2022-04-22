package com.ibm.icu.util;

public class Output
{
    public Object value;
    
    @Override
    public String toString() {
        return (this.value == null) ? "null" : this.value.toString();
    }
    
    public Output() {
    }
    
    public Output(final Object value) {
        this.value = value;
    }
}
