package org.apache.commons.lang3.mutable;

import java.io.*;

public class MutableBoolean implements Mutable, Serializable, Comparable
{
    private static final long serialVersionUID = -4830728138360036487L;
    private boolean value;
    
    public MutableBoolean() {
    }
    
    public MutableBoolean(final boolean value) {
        this.value = value;
    }
    
    public MutableBoolean(final Boolean b) {
        this.value = b;
    }
    
    @Override
    public Boolean getValue() {
        return this.value;
    }
    
    public void setValue(final boolean value) {
        this.value = value;
    }
    
    public void setFalse() {
        this.value = false;
    }
    
    public void setTrue() {
        this.value = true;
    }
    
    public void setValue(final Boolean b) {
        this.value = b;
    }
    
    public boolean isTrue() {
        return this.value;
    }
    
    public boolean isFalse() {
        return !this.value;
    }
    
    public boolean booleanValue() {
        return this.value;
    }
    
    public Boolean toBoolean() {
        return this.booleanValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableBoolean && this.value == ((MutableBoolean)o).booleanValue();
    }
    
    @Override
    public int hashCode() {
        return this.value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }
    
    public int compareTo(final MutableBoolean mutableBoolean) {
        return (this.value == mutableBoolean.value) ? 0 : (this.value ? 1 : -1);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    
    @Override
    public void setValue(final Object o) {
        this.setValue((Boolean)o);
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MutableBoolean)o);
    }
}
