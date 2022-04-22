package org.apache.commons.lang3.mutable;

import java.io.*;

public class MutableObject implements Mutable, Serializable
{
    private static final long serialVersionUID = 86241875189L;
    private Object value;
    
    public MutableObject() {
    }
    
    public MutableObject(final Object value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final Object value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && (this == o || (this.getClass() == o.getClass() && this.value.equals(((MutableObject)o).value)));
    }
    
    @Override
    public int hashCode() {
        return (this.value == null) ? 0 : this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return (this.value == null) ? "null" : this.value.toString();
    }
}
