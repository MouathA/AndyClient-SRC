package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;

public class DoubleTag extends NumberTag
{
    public static final int ID = 6;
    private double value;
    
    public DoubleTag() {
        this(0.0);
    }
    
    public DoubleTag(final double value) {
        this.value = value;
    }
    
    @Deprecated
    @Override
    public Double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = dataInput.readDouble();
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value == ((DoubleTag)o).value);
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(this.value);
    }
    
    @Override
    public final DoubleTag clone() {
        return new DoubleTag(this.value);
    }
    
    @Override
    public byte asByte() {
        return (byte)this.value;
    }
    
    @Override
    public short asShort() {
        return (short)this.value;
    }
    
    @Override
    public int asInt() {
        return (int)this.value;
    }
    
    @Override
    public long asLong() {
        return (long)this.value;
    }
    
    @Override
    public float asFloat() {
        return (float)this.value;
    }
    
    @Override
    public double asDouble() {
        return this.value;
    }
    
    @Override
    public int getTagId() {
        return 6;
    }
    
    @Override
    public Tag clone() {
        return this.clone();
    }
    
    @Deprecated
    @Override
    public Object getValue() {
        return this.getValue();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
