package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;

public class IntTag extends NumberTag
{
    public static final int ID = 3;
    private int value;
    
    public IntTag() {
        this(0);
    }
    
    public IntTag(final int value) {
        this.value = value;
    }
    
    @Deprecated
    @Override
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        this.value = value;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = dataInput.readInt();
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value == ((IntTag)o).value);
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public final IntTag clone() {
        return new IntTag(this.value);
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
        return this.value;
    }
    
    @Override
    public long asLong() {
        return this.value;
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
        return 3;
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
