package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;

public class FloatTag extends NumberTag
{
    public static final int ID = 5;
    private float value;
    
    public FloatTag() {
        this(0.0f);
    }
    
    public FloatTag(final float value) {
        this.value = value;
    }
    
    @Deprecated
    @Override
    public Float getValue() {
        return this.value;
    }
    
    public void setValue(final float value) {
        this.value = value;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = dataInput.readFloat();
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value == ((FloatTag)o).value);
    }
    
    @Override
    public int hashCode() {
        return Float.hashCode(this.value);
    }
    
    @Override
    public final FloatTag clone() {
        return new FloatTag(this.value);
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
        return this.value;
    }
    
    @Override
    public double asDouble() {
        return this.value;
    }
    
    @Override
    public int getTagId() {
        return 5;
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
